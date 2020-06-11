package com.viettel.construction.screens.plan;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.plan.WoMappingPlanDTO;
import com.viettel.construction.model.api.version.AppParamDTO;

import java.util.List;

public class PlanWoAdapter extends RecyclerView.Adapter<PlanWoAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<WoMappingPlanDTO> woMappingPlanDTOList;


    public PlanWoAdapter(Context context, List<WoMappingPlanDTO> list) {
        this.context = context;
        this.woMappingPlanDTOList = list;
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

        public void bindData(WoMappingPlanDTO item){
            tvName.setText(item.getWoName() == null ? "Ten ke hoach" : item.getWoName());
            tvCode.setText(item.getWoCode() == null ? "ma ke hoach" : item.getWoCode());
        }
    }



}
