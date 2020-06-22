package com.viettel.construction.screens.plan;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.plan.FilterDTORequest;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTORequest;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.plan.WoMappingPlanDTO;
import com.viettel.construction.model.api.plan.WoPlanDTO;
import com.viettel.construction.model.api.plan.WoPlanDTORequest;
import com.viettel.construction.model.api.plan.WoPlanDTOResponse;
import com.viettel.construction.screens.custom.dialog.DialogCancel;
import com.viettel.construction.screens.custom.dialog.DialogDeletePlan;
import com.viettel.construction.screens.custom.dialog.DialogShowListWO;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.service.IServerResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class CreatePlanActivity extends BaseCameraActivity implements DialogShowListWO.OnClickDialogForConfirm {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtSave)
    TextView txtSave;
    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.ed_to_date)
    TextView edToDate;
    @BindView(R.id.ed_code_wo)
    EditText edCodeWo;
    @BindView(R.id.ed_name_wo)
    EditText edNameWo;
    @BindView(R.id.sp_plan_type)
    Spinner spPlanType;
    @BindView(R.id.rcvData)
    RecyclerView rcvData;

    private WoPlanDTO item;
    private WoPlanDTORequest woPlanDTORequest = new WoPlanDTORequest();
    private DialogShowListWO dialogShowListWO;
    protected List<WoDTO> listWo = new ArrayList<>();
    protected List<WoDTO> lisAllWo = new ArrayList<>();
    String[] itemPlanType = {"Tuần", "Tháng", "Quý"};
    private String type ;
    PlanWoAdapter planWoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        ButterKnife.bind(this);
        txtHeader = findViewById(R.id.txtHeader);
        imgBack = findViewById(R.id.imgBack);
        txtSave = findViewById(R.id.txtSave);
        txtHeader.setText("Tạo mới kế hoạch");
        initView();
    }


    private void initView(){

        woPlanDTORequest.setSysUserRequest(VConstant.getUser());
        getListWo();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            spPlanType("Tuần");
            edToDate.setText(setDataToday());
            return;
        }
        if (bundle.getSerializable("EDIT_PLAN") != null) {
            item = (WoPlanDTO) bundle.getSerializable("EDIT_PLAN");
            edCodeWo.setText(item.getCode());
            edNameWo.setText(item.getName());
            spPlanType(item.getPlanType());
            edToDate.setText(item.getFromDate() == null ? setDataToday() : item.getFromDate());
            getListWoByPlanId();


        }
    }

    private void setUpView(List<WoDTO> list) {
        planWoAdapter = new PlanWoAdapter(this, list, woDTO -> {
            DialogDeletePlan dialogDeletePlan = new DialogDeletePlan(CreatePlanActivity.this, () -> {
                listWo.remove(woDTO);
                planWoAdapter.notifyDataSetChanged();
            }, true);
            dialogDeletePlan.show();
        });
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rcvData.setLayoutManager(linearLayoutManager2);
        rcvData.setAdapter(planWoAdapter);
    }


    private void setData(WoPlanDTO woPlanDTO){
        woPlanDTO.setName(edNameWo.getText().toString());
        woPlanDTO.setCode(edCodeWo.getText().toString());
        woPlanDTO.setPlanType(type);
        woPlanDTO.setFromDate(edToDate.getText().toString());
    }

    private void updateAndInsertPlan(){
        if (item == null){
            WoPlanDTO woPlanDTO = new WoPlanDTO();
            setData(woPlanDTO);
            woPlanDTORequest.setWoPlanDTO(woPlanDTO);
        }else {
            // fix cung id
            woPlanDTORequest.setWoPlanId(item.getId());
            woPlanDTORequest.setWoPlanDTO(item);
            setData(woPlanDTORequest.getWoPlanDTO());
        }
//        listWo = lisAllWo;
        woPlanDTORequest.setLstWosOfPlan(listWo);
        ApiManager.getInstance().inserAndUpdatePlan(item == null ? APIType.END_URL_INSERT_PLAN : APIType.END_URL_UPDATE_PLAN, woPlanDTORequest, WoPlanDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoPlanDTOResponse response = WoPlanDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        App.getInstance().setNeedUpdateRefund(true);
                        Toast.makeText(CreatePlanActivity.this, item == null ? getString(R.string.insert_plan) : getString(R.string.update_plan), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CreatePlanActivity.this, item == null ? getString(R.string.insert_fail_plan) : getString(R.string.update_fail_plan), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(CreatePlanActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getListWoByPlanId(){
        woPlanDTORequest.setWoPlanId(item.getId());
        woPlanDTORequest.setWoPlanDTO(item);
        setData(woPlanDTORequest.getWoPlanDTO());
        ApiManager.getInstance().geListWoByPlanId(woPlanDTORequest, WoPlanDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                WoPlanDTOResponse response = WoPlanDTOResponse.class.cast(result);
                if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    if (response.getLstWosOfPlan() != null){
                        listWo = response.getLstWosOfPlan();
                        setUpView(listWo);
                    }
                }else {
                    Toast.makeText(CreatePlanActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(CreatePlanActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListWo(){
        WoDTORequest woDTORequest = new WoDTORequest();
        woDTORequest.setSysUserRequest(VConstant.getUser());
        FilterDTORequest filterDTORequest = new FilterDTORequest();
        filterDTORequest.setState(VConstant.StateWO.Assign_ft);
        woDTORequest.setFilter(filterDTORequest);
        ApiManager.getInstance().getListWO(woDTORequest ,WoDTOResponse.class, new IServerResultListener() {
            @Override
            public void onResponse(Object result) {
                WoDTOResponse response = WoDTOResponse.class.cast(result);
                if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    if (response.getLstWos() != null){
                        lisAllWo = response.getLstWos();
                    }
                }else {
                    Toast.makeText(CreatePlanActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(CreatePlanActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void spPlanType(String name){
        ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(CreatePlanActivity.this, R.layout.spinner_item, itemPlanType );
        langAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spPlanType.setAdapter(langAdapter);
        if (!name.equals("Tuần")) {
            int position = Integer.parseInt(item.getPlanType()) - 1;
            spPlanType.setSelection(position);
        }
        spPlanType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        type = "1";
                        break;
                    case 1:
                        type = "2";
                        break;
                    case 2:
                        type = "3";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.btnAddWO)
    public void onClickAddWo(){
        dialogShowListWO = new DialogShowListWO(CreatePlanActivity.this, lisAllWo,this);
        dialogShowListWO.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogShowListWO.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogShowListWO.show();
    }

    @OnClick({R.id.lnToDate, R.id.ed_to_date})
    public void onClickSetToData(){
        setTime(edToDate);
    }


    @OnClick(R.id.txtSave)
    public void onClickSave() {
        if (TextUtils.isEmpty(edNameWo.getText().toString())){
            Toast.makeText(CreatePlanActivity.this, "Tên kế hoạch không được để trống!", Toast.LENGTH_LONG).show();
        }else {
            updateAndInsertPlan();
        }
    }


    @Override
    public void addListWo(List<WoDTO> list) {
        listWo.clear();
        listWo = list;
        setUpView(listWo);
    }
}
