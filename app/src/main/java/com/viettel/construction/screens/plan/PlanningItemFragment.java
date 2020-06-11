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
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.plan.PlanDTOResponse;
import com.viettel.construction.model.api.plan.WoPlanDTO;
import com.viettel.construction.model.api.plan.WoPlanDTORequest;
import com.viettel.construction.model.api.plan.WoPlanDTOResponse;
import com.viettel.construction.screens.custom.dialog.DialogDeletePlan;
import com.viettel.construction.screens.wo.WOItemFragment;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class PlanningItemFragment extends FragmentListBase<WoPlanDTO, PlanDTOResponse> implements
        PlanItemAdapter.IItemPlanClick<WoPlanDTO>,
        DialogDeletePlan.OnClickDialogForConfirm {


    private DialogDeletePlan dialogDeletePlan;
    private WoPlanDTORequest woPlanDTORequest;


    @Override
    public void initData() {
        super.initData();
        woPlanDTORequest = new WoPlanDTORequest();
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
        return new PlanItemAdapter(getContext(), listData, this);
    }



    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<WoPlanDTO> dataSearch(String keyWord) {
        return null;
    }

    @Override
    public List<ExpandableListModel<String, WoPlanDTO>> dataSearchExpandableList(String keyWord) {
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
    public List<WoPlanDTO> menuItemClick(int menuItem) {
        //

        return null;
    }

    @Override
    public List<ExpandableListModel<String, WoPlanDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<WoPlanDTO> getResponseData(PlanDTOResponse result) {
        List<WoPlanDTO> data = new ArrayList<>();
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
    public void onItemRecyclerViewclick(WoPlanDTO item) {
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
    public void onItemRecyclerViewLongclick(WoPlanDTO item) {

    }



    @OnClick(R.id.btnAddItem)
    public void add() {
        Intent addIssue = new Intent(getContext(), CreatePlanActivity.class);
        startActivity(addIssue);
    }

    @Override
    public void onItemEditClick(WoPlanDTO item) {
        adapter.notifyDataSetChanged();
        Intent addIssue = new Intent(getContext(), CreatePlanActivity.class);
        addIssue.putExtra("EDIT_PLAN", item);
        startActivity(addIssue);
    }

    @Override
    public void onItemDeleteClick(WoPlanDTO item) {
        adapter.notifyDataSetChanged();
        dialogDeletePlan.show();

        woPlanDTORequest.setSysUserRequest(VConstant.getUser());
        WoPlanDTO woPlanDTO = new WoPlanDTO();

        woPlanDTO.setId(item.getId());
        woPlanDTORequest.setWoPlanDTO(woPlanDTO);
    }


    private void deletePlan(){
        ApiManager.getInstance().deletePlan(woPlanDTORequest, WoPlanDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoPlanDTOResponse response = WoPlanDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        App.getInstance().setNeedUpdateRefund(true);
                        Toast.makeText(getContext(), getString(R.string.delete_plan), Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.delete_fail_plan), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getContext(), getString(R.string.server_error), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClickConfirmOfConfirm() {
        listData.clear();
        deletePlan();
        dialogDeletePlan.dismiss();
    }
}
