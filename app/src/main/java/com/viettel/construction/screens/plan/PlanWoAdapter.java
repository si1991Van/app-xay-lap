package com.viettel.construction.screens.plan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.plan.WoDTO;

import java.util.List;

public class PlanWoAdapter extends RecyclerView.Adapter<PlanWoAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<WoDTO> woMappingPlanDTOList;
    private CallbackInterface callbackInterface;

    public PlanWoAdapter(Context context, List<WoDTO> list, CallbackInterface callback) {
        this.context = context;
        this.woMappingPlanDTOList = list;
        this.callbackInterface = callback;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PlanWoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_plan_wo, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(woMappingPlanDTOList.get(position));
        holder.img_delete.setOnClickListener(view ->{
            callbackInterface.onDeleteWo(woMappingPlanDTOList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return woMappingPlanDTOList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvCode;
        private ImageView img_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txtName);
            tvCode = itemView.findViewById(R.id.txtCode);
            img_delete = itemView.findViewById(R.id.img_delete);
        }

        public void bindData(WoDTO item){
            tvName.setText(item.getWoName() == null ? "..." : item.getWoName());
            tvCode.setText(item.getWoCode() == null ? "..." : item.getWoCode());
        }
    }


    public interface CallbackInterface{

        void onDeleteWo(WoDTO woDTO);
    }


}
