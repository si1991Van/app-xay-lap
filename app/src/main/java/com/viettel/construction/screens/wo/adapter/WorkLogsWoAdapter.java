package com.viettel.construction.screens.wo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;
import com.viettel.construction.model.api.wo.WoWorkLogsBO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ramona on 2/21/2018.
 */

public class WorkLogsWoAdapter extends RecyclerView.Adapter<WorkLogsWoAdapter.WorkLogsViewHolder> {

    private List<WoWorkLogsBO> list;
    private Context mContext;
    private LayoutInflater inflater;


    public WorkLogsWoAdapter(Context context, List<WoWorkLogsBO> list) {
        this.mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    public void setData(List<WoWorkLogsBO> mData){
        this.list = mData;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public WorkLogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_work_logs_wo, parent, false);
        return new WorkLogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkLogsViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null || list.size() == 0 ? 0 : list.size();
    }


    class WorkLogsViewHolder extends ViewModelFragmentListBase {
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtContent)
        TextView txtContent;

        public WorkLogsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);

        }

        public void setData(WoWorkLogsBO item){
            if (item != null) {
                txtName.setText(VConstant.getDTO().getEmail() + "-" + item.getLogTime());
                txtContent.setText(item.getContent());
            }
        }

    }

}
