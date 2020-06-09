package com.viettel.construction.screens.plan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.plan.ItemPlanRequest;
import com.viettel.construction.model.api.plan.PlanDTOResponse;
import com.viettel.construction.screens.custom.dialog.DialogDeletePlan;
import com.viettel.construction.screens.wo.WOItemFragment;
import com.viettel.construction.server.api.APIType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class PlanningItemFragment extends FragmentListBase<ItemPlanRequest, PlanDTOResponse> implements
        PlanItemAdapter.IItemPlanClick<ItemPlanRequest>,
        DialogDeletePlan.OnClickDialogForConfirm {


    private DialogDeletePlan dialogDeletePlan;

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            // load lại nếu như có công việc đang thực hiện chuyển sang tạm dừng
            if (App.getInstance().isNeedUpdate()) {
                loadData();
            }
            // load lại nếu có công việc tạo mới
            if (App.getInstance().isNeedUpdateAfterCreateNewWork()) {
                loadData();
            }
            // load lại nếu có công việc thay đổi sau khi xem detail
            if (App.getInstance().isNeedUpdateAfterSaveDetail()) {
                loadData();
            }
            // load lại nếu có công việc đang tạm dừng chuyển sang tiếp tục thực hiện
            if (App.getInstance().isNeedUpdateAfterContinue()) {
                loadData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_plan;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dialogDeletePlan = new DialogDeletePlan(getActivity(), this);
        dialogDeletePlan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        Log.e("Tag: ", listData.size() + "");
//        return new AcceptanceLevel1Adapter(getContext(), listData);
        if (listData.size() == 0){
            listData.add(new ItemPlanRequest());
            listData.add(new ItemPlanRequest());
        }

        return new PlanItemAdapter(getContext(), listData, this);
    }



    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<ItemPlanRequest> dataSearch(String keyWord) {
        return null;
    }

    @Override
    public List<ExpandableListModel<String, ItemPlanRequest>> dataSearchExpandableList(String keyWord) {
        return null;
    }


    @Override
    public String getHeaderTitle() {
         String title = "";
        if (listData != null) {
            title = "Kế hoạch (" + listData.size() + ")";
        }
        return title;
    }

    @Override
    public int getMenuID() {
        return 0;
    }

    @Override
    public List<ItemPlanRequest> menuItemClick(int menuItem) {
        //

        return null;
    }

    @Override
    public List<ExpandableListModel<String, ItemPlanRequest>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<ItemPlanRequest> getResponseData(PlanDTOResponse result) {
        List<ItemPlanRequest> data = new ArrayList<>();
        ResultInfo resultInfo = result.getResultInfo();
        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            if (result.getWoPlan() != null) {
                data = result.getWoPlan();
            }
        }
        return data;
    }


    @Override
    public Object[] getParramLoading() {
        return new Object[0];
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_GET_PLAN_ALL;
    }

    @Override
    public Class<PlanDTOResponse> responseEntityClass() {
        return PlanDTOResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(ItemPlanRequest item) {
        try {
            Fragment frag = new WOItemFragment();
            Bundle bundle = new Bundle();
//            bundle.putSerializable("CONSTRUCTION_MANAGEMENT_OBJ", item);
            bundle.putSerializable("SYS_USER_1", sysUser);
            bundle.putString("type", "1");
            frag.setArguments(bundle);
            commitChange(frag, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemRecyclerViewLongclick(ItemPlanRequest item) {

    }



    @OnClick(R.id.btnAddItem)
    public void add() {
        Intent addIssue = new Intent(getContext(), CreatePlanActivity.class);
        startActivity(addIssue);
    }

    @Override
    public void onItemEditClick(ItemPlanRequest item) {

        adapter.notifyDataSetChanged();
        Intent addIssue = new Intent(getContext(), CreatePlanActivity.class);
        addIssue.putExtra("EDIT_PLAN", item);
        startActivity(addIssue);
    }

    @Override
    public void onItemDeleteClick(ItemPlanRequest item) {
        adapter.notifyDataSetChanged();
        dialogDeletePlan.show();
        Toast.makeText(getContext(), "xoa ke hoach", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickConfirmOfConfirm() {

        // call api delete plan
        listData.clear();
        loadData();
        dialogDeletePlan.dismiss();
    }
}
