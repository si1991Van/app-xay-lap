package com.viettel.construction.screens.wo;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.plan.FilterDTORequest;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTORequest;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.plan.WoPlanDTO;
import com.viettel.construction.model.api.plan.WoPlanDTORequest;
import com.viettel.construction.model.api.plan.WoPlanDTOResponse;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.screens.plan.CreatePlanActivity;
import com.viettel.construction.screens.wo.activity.DetailItemWoActivity;
import com.viettel.construction.screens.wo.adapter.SpinnerWoAdapter;
import com.viettel.construction.screens.wo.adapter.WOItemAdapter;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WOItemFragment extends FragmentListBase<WoDTO,
        WoDTOResponse> {

    @Nullable
    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.sp_construction)
    Spinner spConstruction;
    @BindView(R.id.sp_type_wo)
    Spinner spTypeWo;
    @BindView(R.id.ln_file)
    LinearLayout lnFile;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;

    @Nullable
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    private String scheduleType = "0";

    private WoPlanDTO item;
    private List<AppParamDTO> lstApContructionType = new ArrayList<>();
    private List<AppParamDTO> lstApWorkSrc = new ArrayList<>();
    private SpinnerWoAdapter adapterApWorkSrc;
    private SpinnerWoAdapter adapterApContructionType;

    @Override
    public void initData() {
        super.initData();
        if (getArguments() != null) {
            scheduleType = getArguments().getString("type");
            item = (WoPlanDTO) getArguments().getSerializable("PLAN_WO");
            lnFile.setVisibility(scheduleType.equals("1") ? View.GONE : View.VISIBLE);
            imgFilter.setVisibility(scheduleType.equals("1") ? View.INVISIBLE : View.VISIBLE);

        } else {
            getForComboBox(VConstant.ParTypeWo.AP_CONSTRUCTION_TYPE);
            getForComboBox(VConstant.ParTypeWo.AP_WORK_SRC);
            spApWorkSrc();
            spApConstructionType();
        }
    }


    @Override
    public void onResume() {
        try {
            super.onResume();
            if (scheduleType.equals("1")) {
                getListWoByPlanId();
            } else {
                loadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_wo;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new WOItemAdapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<WoDTO> dataSearch(String keyWord) {
        return null;
    }

    @Override
    public List<ExpandableListModel<String, WoDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    private int type = 0;
    String title;

    @Override
    public String getHeaderTitle() {
        Log.e("Tag: ", "1");

        switch (type) {
            case 0:
                title = "Tất cả (" + listData.size() + ")";
                break;
            case 1:
                title = getString(R.string.assign_cd) + "(" + data.size() + ")";
                break;
            case 2:
                title = getString(R.string.accept_cd) + "(" + data.size() + ")";
                break;
            case 3:
                title = getString(R.string.reject_cd) + "(" + data.size() + ")";
                break;
            case 4:
                title = getString(R.string.assign_ft) + "(" + data.size() + ")";
                break;
            case 5:
                title = getString(R.string.accept_ft) + "(" + data.size() + ")";
                break;

            case 6:
                title = getString(R.string.reject_ft) + "(" + data.size() + ")";
                break;
            case 7:
                title = getString(R.string.processing) + "(" + data.size() + ")";
                break;
            case 8:
                title = getString(R.string.opinion_rq) + "(" + data.size() + ")";
                break;
            case 9:
                title = getString(R.string.ok) + "(" + data.size() + ")";
                break;
            case 10:
                title = getString(R.string.ng) + "(" + data.size() + ")";
                break;
            case 11:
                title = getString(R.string.done) + "(" + data.size() + ")";
                break;
        }
        return title;
    }

    @Override
    public int getMenuID() {
        return R.menu.menu_wo_status;
    }

    private List<WoDTO> data;
    private String menuStatus = "Tất cả";
    private String apWorkSrc = "Nguồn việc";
    private String apConstructionType = "Loại công trình";

    @Override
    public List<WoDTO> menuItemClick(int menuItem) {

        switch (menuItem) {
            default://case R.id.all:
                menuStatus = "Tất cả";
                data = filterByItem(menuStatus, apWorkSrc, apConstructionType);
//                data = listData;
                type = 0;
                break;
            case R.id.assign_cd:
                type = 1;
                menuStatus = VConstant.StateWO.Assign_cd;
                data = filterByItem(VConstant.StateWO.Assign_cd, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Assign_cd, false);
                break;
            case R.id.accept_cd:
                type = 2;
                menuStatus = VConstant.StateWO.Accept_cd;
                data = filterByItem(VConstant.StateWO.Accept_cd, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Accept_cd, false);
                break;
            case R.id.reject_cd:
                type = 3;
                menuStatus = VConstant.StateWO.Reject_cd;
                data = filterByItem(VConstant.StateWO.Reject_cd, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Reject_cd, false);
                break;
            case R.id.assign_ft:
                type = 4;
                menuStatus = VConstant.StateWO.Assign_ft;
                data = filterByItem(VConstant.StateWO.Assign_ft, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Assign_ft, false);
                break;
            case R.id.accept_ft:
                type = 5;
                menuStatus = VConstant.StateWO.Accept_ft;
                data = filterByItem(VConstant.StateWO.Accept_ft, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Accept_ft, false);
                break;
            case R.id.reject_ft:
                type = 6;
                menuStatus = VConstant.StateWO.Reject_ft;
                data = filterByItem(VConstant.StateWO.Reject_ft, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Reject_ft, false);
                break;
            case R.id.processing:
                type = 7;
                menuStatus = VConstant.StateWO.Processing;
                data = filterByItem(VConstant.StateWO.Processing, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Processing, false);
                break;
            case R.id.Opinion_rq:
                type = 8;
                menuStatus = VConstant.StateWO.Opinion_rq;
                data = filterByItem(VConstant.StateWO.Opinion_rq, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Opinion_rq, false);
                break;
            case R.id.ok:
                type = 9;
                menuStatus = VConstant.StateWO.Ok;
                data = filterByItem(VConstant.StateWO.Ok, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Ok, false);
                break;
            case R.id.ng:
                type = 10;
                menuStatus = VConstant.StateWO.Ng;
                data = filterByItem(VConstant.StateWO.Ng, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Ng, false);
                break;
            case R.id.done:
                type = 11;
                menuStatus = VConstant.StateWO.Done;
                data = filterByItem(VConstant.StateWO.Done, apWorkSrc, apConstructionType);
//                data = filterByStatus(VConstant.StateWO.Done, false);
                break;
        }
        return data;
    }


    @Override
    public List<ExpandableListModel<String, WoDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<WoDTO> getResponseData(WoDTOResponse result) {

        List<WoDTO> data = new ArrayList<>();
        ResultInfo resultInfo = result.getResultInfo();
        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            if (result.getLstWos() != null) {
                data = result.getLstWos();
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
        return APIType.END_URL_GET_ALL_WO;
    }

    @Override
    public Class<WoDTOResponse> responseEntityClass() {
        return WoDTOResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(WoDTO item) {
        Intent intent = new Intent(getContext(), DetailItemWoActivity.class);
        intent.putExtra("Item_WO", item);
//        intent.putExtra("Type", scheduleType);

        startActivity(intent);
    }

    @Override
    public void onItemRecyclerViewLongclick(WoDTO item) {

    }

    private void spApConstructionType() {

        adapterApContructionType = new SpinnerWoAdapter(lstApContructionType, getContext());
        spConstruction.setAdapter(adapterApContructionType);

        spConstruction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                if (position == 0) {
//                    data = listData;
//                } else {
                apConstructionType = position == 0 ? "Loại công trình" : lstApContructionType.get(position).getCode();
                data = filterByItem(menuStatus, apWorkSrc, lstApContructionType.get(position).getCode());
//                    data = filterByApContructionType(lstApContructionType.get(position).getCode());
//                }
                adapter.setListData(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void spApWorkSrc() {
        adapterApWorkSrc = new SpinnerWoAdapter(lstApWorkSrc, getContext());
        spTypeWo.setAdapter(adapterApWorkSrc);
        spTypeWo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 0){
//                    data = listData;
//                }else {
                apWorkSrc = i == 0 ? "Nguồn việc" : lstApContructionType.get(i).getCode();
                data = filterByItem(menuStatus, lstApContructionType.get(i).getCode(), apConstructionType);
//                    data = filterByApWorkSrc(lstApContructionType.get(i).getCode());
//                }
                adapter.setListData(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private List<WoDTO> filterByApContructionType(String name) {
        List<WoDTO> dataSearch = new ArrayList<>();
        for (WoDTO woDTO : listData) {
            if (name.equals(String.valueOf(woDTO.getApConstructionType()))) {
                dataSearch.add(woDTO);
            }
        }
        return dataSearch;
    }

    private List<WoDTO> filterByItem(String state, String apWorkSrc, String apConstructionType) {
        List<WoDTO> dataSearch = new ArrayList<>();
        for (WoDTO woDTO : listData) {

            if (state.equals(woDTO.getState()) && apWorkSrc.equals(String.valueOf(woDTO.getApWorkSrc())) && apConstructionType.equals(String.valueOf(woDTO.getApConstructionType()))) {
                dataSearch.add(woDTO);
            } else {
                if (state.equals(woDTO.getState()) && apWorkSrc.equals(String.valueOf(woDTO.getApWorkSrc()))) {
                    dataSearch.add(woDTO);
                } else if (apWorkSrc.equals(String.valueOf(woDTO.getApWorkSrc())) && apConstructionType.equals(String.valueOf(woDTO.getApConstructionType()))) {
                    dataSearch.add(woDTO);
                } else if (state.equals(woDTO.getState()) && apConstructionType.equals(String.valueOf(woDTO.getApConstructionType()))) {
                    dataSearch.add(woDTO);
                } else if (state.equals(woDTO.getState())) {
                    dataSearch.add(woDTO);
                } else if (apWorkSrc.equals(woDTO.getState())) {
                    dataSearch.add(woDTO);
                } else if (apConstructionType.equals(String.valueOf(woDTO.getApWorkSrc()))) {
                    dataSearch.add(woDTO);
                } else if (state.equals("Tất cả") && apWorkSrc.equals(String.valueOf(woDTO.getApWorkSrc()))) {
                    dataSearch.add(woDTO);
                } else if (state.equals("Tất cả") && apConstructionType.equals(String.valueOf(woDTO.getApConstructionType()))) {
                    dataSearch.add(woDTO);
                } else if (apWorkSrc.equals("Nguồn việc") && state.equals(woDTO.getState())) {
                    dataSearch.add(woDTO);
                } else if (apWorkSrc.equals("Nguồn việc") && apConstructionType.equals(String.valueOf(woDTO.getApConstructionType()))) {
                    dataSearch.add(woDTO);
                } else if (apConstructionType.equals("Loại công trình") && apWorkSrc.equals(String.valueOf(woDTO.getApWorkSrc()))) {
                    dataSearch.add(woDTO);
                } else if (apConstructionType.equals("Loại công trình") && state.equals(woDTO.getState())) {
                    dataSearch.add(woDTO);
                } else if (state.equals("Tất cả")) {
                    dataSearch.add(woDTO);
                } else if (apWorkSrc.equals("Nguồn việc")) {
                    dataSearch.add(woDTO);
                } else if (apConstructionType.equals("Loại công trình")) {
                    dataSearch.add(woDTO);
                }
            }
        }

        return dataSearch;
    }

    private List<WoDTO> filterByStatus(String state, boolean isByMonth) {
        List<WoDTO> dataSearch = new ArrayList<>();
        for (WoDTO woDTO : listData) {
            if (state.equals(woDTO.getState())) {
                dataSearch.add(woDTO);
            }
        }
        return dataSearch;
    }

    private List<WoDTO> filterByApWorkSrc(String name) {
        List<WoDTO> dataSearch = new ArrayList<>();
        for (WoDTO woDTO : listData) {
            if (name.equals(String.valueOf(woDTO.getApWorkSrc()))) {
                dataSearch.add(woDTO);
            }
        }
        return dataSearch;
    }

    private void getForComboBox(String parType) {

        WoDTORequest woDTORequest = new WoDTORequest();
        woDTORequest.setSysUserRequest(VConstant.getUser());
        FilterDTORequest filterDTORequest = new FilterDTORequest();
        filterDTORequest.setParType(parType);
        woDTORequest.setFilter(filterDTORequest);
        ApiManager.getInstance().getForComboBox(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                WoDTOResponse response = WoDTOResponse.class.cast(result);
                if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    if (response.getLstDataForComboBox() != null) {
                        AppParamDTO paramDTO = new AppParamDTO();
                        if (parType == VConstant.ParTypeWo.AP_WORK_SRC) {
                            lstApWorkSrc = response.getLstDataForComboBox();
                            paramDTO.setName("Nguồn việc");
                            lstApWorkSrc.add(0, paramDTO);
                            adapterApWorkSrc.setData(response.getLstDataForComboBox());
                        } else if (parType == VConstant.ParTypeWo.AP_CONSTRUCTION_TYPE) {
                            lstApContructionType = response.getLstDataForComboBox();
                            paramDTO.setName("Loại công trình");
                            lstApContructionType.add(0, paramDTO);

                            adapterApContructionType.setData(response.getLstDataForComboBox());
                        }
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getContext(), getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListWoByPlanId() {
        WoPlanDTORequest woPlanDTORequest = new WoPlanDTORequest();
        woPlanDTORequest.setSysUserRequest(VConstant.getUser());
        woPlanDTORequest.setWoPlanId(item.getId());
        woPlanDTORequest.setWoPlanDTO(item);
        ApiManager.getInstance().geListWoByPlanId(woPlanDTORequest, WoPlanDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                WoPlanDTOResponse response = WoPlanDTOResponse.class.cast(result);
                if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    if (response.getLstWosOfPlan() != null) {
                        listData = response.getLstWosOfPlan();
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                        title = "Tất cả (" + listData.size() + ")";
                        txtHeader.setText(title);
                        txtNoData.setVisibility(listData != null && listData.size() > 0 ? View.GONE : View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getContext(), getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
