package com.viettel.construction.screens.plan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.plan.WoDTO;

import java.util.List;

public class SelectWoAdapter extends RecyclerView.Adapter<SelectWoAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<WoDTO> woMappingPlanDTOList;
    private callBackInterface callBackInterface;
    private List<WoDTO> listOld;

    public SelectWoAdapter(Context context, List<WoDTO> listOld, List<WoDTO> list, callBackInterface callBackInterface) {
        this.context = context;
        this.woMappingPlanDTOList = list;
        this.listOld = listOld;
        this.callBackInterface = callBackInterface;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SelectWoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_dialog_plan_wo, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(woMappingPlanDTOList.get(position));
        if (listOld != null && listOld.size() > 0) {
//            listAdd = listOld;
            for (WoDTO dto : listOld) {
                if (dto.getWoCode().equals(woMappingPlanDTOList.get(position).getWoCode())) {
                    holder.cbPlan.setChecked(true);
                }
            }
        }
        holder.cbPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    callBackInterface.add(woMappingPlanDTOList.get(position));
                }else {
                    callBackInterface.remove(woMappingPlanDTOList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return woMappingPlanDTOList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvCode;
        private CheckBox cbPlan;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txtName);
            tvCode = itemView.findViewById(R.id.txtCode);
            cbPlan = itemView.findViewById(R.id.cbPlan);
        }



        public void bindData(WoDTO item){
            tvName.setText(item.getWoName() == null ? "Ten ke hoach" : item.getWoName());
            tvCode.setText(item.getWoCode() == null ? "ma ke hoach" : item.getWoCode());
        }
    }


    public interface callBackInterface {
        void add(WoDTO dto);
        void remove(WoDTO dto);
    }
}
