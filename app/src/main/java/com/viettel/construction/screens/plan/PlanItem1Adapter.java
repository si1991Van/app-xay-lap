package com.viettel.construction.screens.plan;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.AdapterFragmentSwipeBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;

import java.util.List;

import butterknife.BindView;

public class PlanItem1Adapter
        extends AdapterFragmentSwipeBase<ConstructionAcceptanceCertDetailDTO,
        PlanItem1Adapter.PlanViewHolder> {


    public PlanItem1Adapter(Context context, List<ConstructionAcceptanceCertDetailDTO> listData) {
        super(context, listData);
    }

    @Override
    public PlanItem1Adapter.PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        return new PlanItem1Adapter.PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanItem1Adapter.PlanViewHolder viewHolder, int position) {

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addDrag(
                SwipeLayout.DragEdge.Right,
                viewHolder.swipeLayout.findViewById(R.id.swipe)
        );

        viewHolder.txtEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //doi
                return false;
            }
        });
        viewHolder.txtDelete.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                getListData().remove(position);
                notifyDataSetChanged();
                notifyItemRangeChanged(position, getListData().size());
                mItemManger.closeAllItems();

                return false;
            }
        });
    }



    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
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
}
