package com.viettel.construction.screens.plan;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.daimajia.swipe.SwipeLayout;
import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.plan.WoPlanDTO;

import java.util.List;

import butterknife.BindView;

public class PlanItemAdapter
        extends AdapterFragmentListBase<WoPlanDTO,
        PlanItemAdapter.PlanViewHolder> {

    private IItemPlanClick<WoPlanDTO> itemPlanClick;

    public PlanItemAdapter(Context context, List<WoPlanDTO> listData, IItemPlanClick<WoPlanDTO> itemPlanClick) {
        super(context, listData);

        this.itemPlanClick = itemPlanClick;
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        WoPlanDTO item = getListData().get(position);

        holder.tvCodeWo.setText(item.getCode()!= null ? item.getCode() : "COGTRIH_SL");
        holder.tvName.setText(item.getName()!= null ? item.getName() :"XD mong cot duoi dat");
        holder.tvStatus.setText("3/10");
        holder.itemView.setOnClickListener((v) -> {
            try {
                itemRecyclerviewClick.onItemRecyclerViewclick((WoPlanDTO) v.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        holder.txtEdit.setOnClickListener((v) -> {
            try {
                itemPlanClick.onItemEditClick(getListData().get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        holder.txtDelete.setOnClickListener((v) -> {
            try {
                itemPlanClick.onItemDeleteClick(getListData().get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        return new PlanViewHolder(view);
    }



    class PlanViewHolder extends ViewModelFragmentListBase {

        @BindView(R.id.tv_code_wo)
        TextView tvCodeWo;
        @BindView(R.id.tv_name_wo)
        TextView tvName;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        @BindView(R.id.txtEdit)
        TextView txtEdit;
        @BindView(R.id.txtDelete)
        TextView txtDelete;

        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;

        public PlanViewHolder(View itemView) {
            super(itemView);
        }


    }

    public interface IItemPlanClick<T> {
        void onItemEditClick(T item);
        void onItemDeleteClick(T item);
    }
}
