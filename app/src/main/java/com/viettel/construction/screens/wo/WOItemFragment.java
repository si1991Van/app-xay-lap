package com.viettel.construction.screens.wo;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.plan.WoPlanDTO;
import com.viettel.construction.server.api.APIType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WOItemFragment extends FragmentListBase<WoDTO,
        WoDTOResponse> {

    @BindView(R.id.sp_construction)
    Spinner spConstruction;
    @BindView(R.id.sp_type_wo)
    Spinner spTypeWo;
    @BindView(R.id.ln_file)
    LinearLayout lnFile;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;

    String[] itemConstruction = {"HNM", "SL2", "TEST206", "COGTRIH3005"};
    String[] itemType = {"dong", "mo", "noi bo"};
    private String scheduleType = "0";

    private WoPlanDTO item;

    @Override
    public void initData() {
        super.initData();
        if (getArguments() != null) {
            scheduleType = getArguments().getString("type");
            item = (WoPlanDTO) getArguments().getSerializable("PLAN_WO");
            lnFile.setVisibility(scheduleType.equals("1") ? View.GONE : View.VISIBLE);
            imgFilter.setVisibility(scheduleType.equals("1") ? View.INVISIBLE : View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            loadData();
            codeSpinner(itemConstruction, spConstruction);
            codeSpinner(itemType, spTypeWo);
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
    String title ;
    @Override
    public String getHeaderTitle() {
        Log.e("Tag: ", "1");

        switch (type){
            case 0:
                title = "Tất cả (" + listData.size() + ")";
                break;
            case 1:
                title =  getString(R.string.assign_cd) + "(" + data.size() + ")";
                break;
            case 2:
                title =  getString(R.string.accept_cd) + "(" + data.size() + ")";
                break;
            case 3:
                title =  getString(R.string.reject_cd) + "(" + data.size() + ")";
                break;
            case 4:
                title =  getString(R.string.assign_ft) + "(" + data.size() + ")";
                break;
            case 5:
                title =  getString(R.string.accept_ft) + "(" + data.size() + ")";
                break;

            case 6:
                title =  getString(R.string.reject_ft) + "(" + data.size() + ")";
                break;
            case 7:
                title =  getString(R.string.processing) + "(" + data.size() + ")";
                break;
            case 8:
                title =  getString(R.string.opinion_rq) + "(" + data.size() + ")";
                break;
            case 9:
                title =  getString(R.string.ok) + "(" + data.size() + ")";
                break;
            case 10:
                title =  getString(R.string.ng) + "(" + data.size() + ")";
                break;
            case 11:
                title =  getString(R.string.done) + "(" + data.size() + ")";
                break;
        }
        return title;
    }

    @Override
    public int getMenuID() {
        return R.menu.menu_wo_status;
    }
    List<WoDTO> data;
    @Override
    public List<WoDTO> menuItemClick(int menuItem) {
        //

        switch (menuItem) {
            default://case R.id.all:
                data = listData;
                type = 0;
                break;
            case R.id.assign_cd:
                type = 1;
                data = filterByStatus(VConstant.StateWO.Assign_cd, false);
                break;
            case R.id.accept_cd:
                type = 2;
                data = filterByStatus(VConstant.StateWO.Accept_cd, false);
                break;
            case R.id.reject_cd:
                type = 3;
                data = filterByStatus(VConstant.StateWO.Reject_cd, false);
                break;
            case R.id.assign_ft:
                type = 4;
                data = filterByStatus(VConstant.StateWO.Assign_ft, false);
                break;
            case R.id.accept_ft:
                type = 5;
                data = filterByStatus(VConstant.StateWO.Accept_ft, false);
                break;
            case R.id.reject_ft:
                type = 6;
                data = filterByStatus(VConstant.StateWO.Reject_ft, false);
                break;
            case R.id.processing:
                type = 7;
                data = filterByStatus(VConstant.StateWO.Processing, false);
                break;
            case R.id.Opinion_rq:
                type = 8;
                data = filterByStatus(VConstant.StateWO.Opinion_rq, false);
                break;
            case R.id.ok:
                type = 9;
                data = filterByStatus(VConstant.StateWO.Ok, false);
                break;
            case R.id.ng:
                type = 10;
                data = filterByStatus(VConstant.StateWO.Ng, false);
                break;
            case R.id.done:
                type = 11;
                data = filterByStatus(VConstant.StateWO.Done, false);
                break;
        }
        return data;
    }

    private List<WoDTO> filterByStatus(String state, boolean isByMonth) {
        List<WoDTO> dataSearch = new ArrayList<>();
        for (WoDTO woDTO : listData) {
            if (state.equals(woDTO.getState())){
                dataSearch.add(woDTO);
            }
        }
        return dataSearch;
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
        Intent intent = new Intent(getContext(), DetailWOActivity.class);
        intent.putExtra("Item_WO", item);
        intent.putExtra("Type", scheduleType);

        startActivity(intent);
    }

    @Override
    public void onItemRecyclerViewLongclick(WoDTO item) {

    }

    private void codeSpinner(String[] item, Spinner spinner){
        ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, item );
        langAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(langAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//
                listData = filterByConstruction(adapterView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private List<WoDTO> filterByConstruction(String name){
        List<WoDTO> dataSearch = new ArrayList<>();
        for (WoDTO woDTO : listData) {
            if (name.equals(woDTO.getWoName())){
                dataSearch.add(woDTO);
            }
        }
        return dataSearch;
    }


}
