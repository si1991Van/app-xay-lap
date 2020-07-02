package com.viettel.construction.screens.wo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.plan.WoDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WOItemAdapter
        extends AdapterFragmentListBase<WoDTO,
        WOItemAdapter.WOViewHolder> {

    public WOItemAdapter(Context context, List<WoDTO> listData) {
        super(context, listData);
    }

    @Override
    public void onBindViewHolder(WOViewHolder holder, int position) {

        WoDTO woDTO = getListData().get(position);

        holder.tvCodeWo.setText(woDTO.getWoCode());
        holder.tvName.setText(woDTO.getWoName());
        holder.tvProgress.setText(woDTO.getDoneCheckListNumber());
        holder.tvPerformer.setText(VConstant.getDTO().getFullName());
        if (woDTO == null || woDTO.getState() == null) return;
        switch (woDTO.getState()){
            case VConstant.StateWO.Assign_cd:
                holder.tvStatus.setText(context.getString(R.string.assign_cd));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case VConstant.StateWO.Accept_cd:
                holder.tvStatus.setText(context.getString(R.string.accept_cd));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case VConstant.StateWO.Reject_cd:
                holder.tvStatus.setText(context.getString(R.string.reject_cd));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;

            case VConstant.StateWO.Assign_ft:
                holder.tvStatus.setText(context.getString(R.string.assign_ft));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case VConstant.StateWO.Accept_ft:
                holder.tvStatus.setText(context.getString(R.string.accept_ft));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case VConstant.StateWO.Reject_ft:
                holder.tvStatus.setText(context.getString(R.string.reject_ft));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;

            case VConstant.StateWO.Processing:
                holder.tvStatus.setText(context.getString(R.string.processing));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case VConstant.StateWO.Opinion_rq:
                holder.tvStatus.setText(context.getString(R.string.opinion_rq));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case VConstant.StateWO.Ok:
                holder.tvStatus.setText(context.getString(R.string.ok));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case VConstant.StateWO.Ng:
                holder.tvStatus.setText(context.getString(R.string.ng));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case VConstant.StateWO.Done:
                holder.tvStatus.setText(context.getString(R.string.done));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            default:
                break;
        }



        holder.itemView.setOnClickListener((v) -> {
            try {
                itemRecyclerviewClick.onItemRecyclerViewclick(getListData().get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public WOViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wo, parent, false);
        return new WOViewHolder(view);
    }

    class WOViewHolder extends ViewModelFragmentListBase {

        @BindView(R.id.tv_code_wo)
        TextView tvCodeWo;
        @BindView(R.id.tv_name_wo)
        TextView tvName;
        @BindView(R.id.tv_progress)
        TextView tvProgress;
        @BindView(R.id.tv_performer)
        TextView tvPerformer;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        public WOViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }


    }
}
