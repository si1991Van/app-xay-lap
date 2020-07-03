package com.viettel.construction.screens.wo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.wo.WoSimpleFtDTO;
import com.viettel.construction.model.api.wo.WoWorkLogsBO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ramona on 2/21/2018.
 */

public class WoSimpleFtAdapter extends RecyclerView.Adapter<WoSimpleFtAdapter.WorkLogsViewHolder> {

    private List<WoSimpleFtDTO> list;
    private Context mContext;
    private LayoutInflater inflater;

    private OnItemClickRecycleView call;


    public WoSimpleFtAdapter(Context context, List<WoSimpleFtDTO> list, OnItemClickRecycleView call) {
        this.mContext = context;
        this.list = list;
        this.call = call;
        inflater = LayoutInflater.from(context);
    }


    public void setData(List<WoSimpleFtDTO> mData){
        this.list = mData;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public WorkLogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_wo_simple_ft, parent, false);
        return new WorkLogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkLogsViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.itemView.setOnClickListener(view -> {
            call.onClick(list.get(position).getFtId());
        });
    }

    @Override
    public int getItemCount() {
        return list == null || list.size() == 0 ? 0 : list.size();
    }


    class WorkLogsViewHolder extends ViewModelFragmentListBase {
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtCodeName)
        TextView txtCodeName;
        @BindView(R.id.txtEmail)
        TextView txtEmail;

        public WorkLogsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);

        }

        public void setData(WoSimpleFtDTO item){
            if (item != null) {
                txtName.setText(item.getFullName());
                txtCodeName.setText(item.getEmployeeCode());
                txtEmail.setText(item.getEmail());
            }
        }

    }

    public interface OnItemClickRecycleView {

        void onClick(long ftId);
    }
}
