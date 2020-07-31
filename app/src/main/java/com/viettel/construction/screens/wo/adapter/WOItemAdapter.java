package com.viettel.construction.screens.wo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        holder.tvCD1.setText(woDTO.getConstructionCode());
        if (woDTO.getProjectCode() != null && woDTO.getContractCode() != null) {
            holder.tvCD2.setText(woDTO.getProjectCode() + "/" + woDTO.getContractCode());
        }else if (woDTO.getProjectCode() != null){
            holder.tvCD2.setText(woDTO.getProjectCode());
        }else if(woDTO.getContractCode() != null){
            holder.tvCD2.setText(woDTO.getContractCode());
        }
        holder.tvCD3.setText(woDTO.getConstructionName());
        holder.tvCD4.setText(woDTO.getCdLevel4Name());
        holder.lnCD1.setVisibility(woDTO.getConstructionCode() == null ? View.GONE : View.VISIBLE);
        holder.lnCd2.setVisibility(woDTO.getProjectCode() == null && woDTO.getContractCode() == null ? View.GONE : View.VISIBLE);
        holder.lnCd3.setVisibility(woDTO.getConstructionName() == null ? View.GONE : View.VISIBLE);
        holder.lnCd4.setVisibility(woDTO.getCdLevel4Name() == null ? View.GONE : View.VISIBLE);
        holder.lnProgress.setVisibility(woDTO.getDoneCheckListNumber() == null ? View.GONE : View.VISIBLE);

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
            case VConstant.StateWO.Opinion_rq1:
            case VConstant.StateWO.Opinion_rq2:
            case VConstant.StateWO.Opinion_rq3:
            case VConstant.StateWO.Opinion_rq4:
                if (VConstant.StateWO.Accepted.equals(woDTO.getOpinionResult())){
                    holder.tvStatus.setText(context.getString(R.string.opinion_accepted));
                }else if (VConstant.StateWO.Rejected.equals(woDTO.getOpinionResult())){
                    holder.tvStatus.setText(context.getString(R.string.opinion_reject));
                }else {
                    holder.tvStatus.setText(context.getString(R.string.opinion_rq));
                }
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
            case VConstant.StateWO.CD_NG:
                holder.tvStatus.setText(context.getString(R.string.cd_ng));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            case VConstant.StateWO.CD_OK:
                holder.tvStatus.setText(context.getString(R.string.cd_ok));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
            default:
                break;
        }
        holder.lnEndTime.setVisibility(woDTO.getFinishDate() == null ? View.GONE : View.VISIBLE);
        holder.lnStartTime.setVisibility(View.GONE);
//        holder.tvStartTime.setText(woDTO.getFinishDate());
        holder.tvEndTime.setText(woDTO.getFinishDate());
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
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_cd1)
        TextView tvCD1;
        @BindView(R.id.tv_cd2)
        TextView tvCD2;
        @BindView(R.id.tv_cd3)
        TextView tvCD3;
        @BindView(R.id.tv_cd4)
        TextView tvCD4;

        @BindView(R.id.lnCD1)
        LinearLayout lnCD1;
        @BindView(R.id.lnCD2)
        LinearLayout lnCd2;
        @BindView(R.id.lnCD3)
        LinearLayout lnCd3;
        @BindView(R.id.lnCD4)
        LinearLayout lnCd4;
        @BindView(R.id.lnProgress)
        LinearLayout lnProgress;
        @BindView(R.id.lnStartTime)
        LinearLayout lnStartTime;
        @BindView(R.id.lnEndTime)
        LinearLayout lnEndTime;
        @BindView(R.id.tv_startTime)
        TextView tvStartTime;
        @BindView(R.id.tv_EndTime)
        TextView tvEndTime;

        public WOViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }


    }
}
