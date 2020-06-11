package com.viettel.construction.screens.plan;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.plan.WoMappingPlanDTO;
import com.viettel.construction.model.api.plan.WoPlanDTO;
import com.viettel.construction.model.api.plan.WoPlanDTORequest;
import com.viettel.construction.model.api.plan.WoPlanDTOResponse;
import com.viettel.construction.screens.custom.dialog.DialogDeletePlan;
import com.viettel.construction.screens.custom.dialog.DialogShowListWO;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatePlanActivity extends BaseCameraActivity implements DialogShowListWO.OnClickDialogForConfirm {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtSave)
    TextView txtSave;
    @BindView(R.id.txtHeader)
    TextView txtHeader;
    @BindView(R.id.ed_from_date)
    TextView edFromDate;
    @BindView(R.id.ed_to_date)
    TextView edToDate;
    @BindView(R.id.ed_code_wo)
    EditText edCodeWo;
    @BindView(R.id.ed_name_wo)
    EditText edNameWo;
    @BindView(R.id.rcvData)
    RecyclerView rcvData;

    private WoPlanDTO item;
    private WoPlanDTORequest woPlanDTORequest = new WoPlanDTORequest();
    private DialogShowListWO dialogShowListWO;

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
        dialogShowListWO = new DialogShowListWO(CreatePlanActivity.this, this);
        dialogShowListWO.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogShowListWO.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        if (bundle.getSerializable("EDIT_PLAN") != null) {
            item = (WoPlanDTO) bundle.getSerializable("EDIT_PLAN");
            edCodeWo.setText(item.getCode());
            edNameWo.setText(item.getName());
            setUpView();
        }

    }

    private void setUpView() {
        //recyclerview
        List<WoMappingPlanDTO> listData = new ArrayList<>();
        // fix data wo
        if (listData.size() == 0){
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
        }
        PlanWoAdapter adapter = new PlanWoAdapter(this, listData);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rcvData.setLayoutManager(linearLayoutManager2);
        rcvData.setAdapter(adapter);
    }

    private void setData(WoPlanDTO woPlanDTO){
        woPlanDTO.setName(edNameWo.getText().toString());
        woPlanDTO.setCode(edCodeWo.getText().toString());
        woPlanDTO.setPlanType("2");
        woPlanDTO.setFromDate(edFromDate.getText().toString());
        woPlanDTO.setToDate(edToDate.getText().toString());
    }

    private void updateAndInsertPlan(){
        woPlanDTORequest.setSysUserRequest(VConstant.getUser());
        if (item == null){
            WoPlanDTO woPlanDTO = new WoPlanDTO();
            setData(woPlanDTO);
            woPlanDTORequest.setWoPlanDTO(woPlanDTO);
        }else {
            // fix cung id
            item.setId(57);
            item.setStaffId(1234);
            woPlanDTORequest.setWoPlanId(item.getId());
            woPlanDTORequest.setWoPlanDTO(item);
            setData(woPlanDTORequest.getWoPlanDTO());
        }

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

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.btnAddWO)
    public void onClickAddWo(){
        dialogShowListWO.show();
    }

    @OnClick(R.id.lnFromDate)
    public void onClickSetFromData(){
        setTime(edFromDate);
    }

    @OnClick(R.id.lnToDate)
    public void onClickSetToData(){
        setTime(edToDate);
    }


    @OnClick(R.id.txtSave)
    public void onClickSave() {
        updateAndInsertPlan();
    }

    @Override
    public void onClickConfirmOfConfirm() {
        dialogShowListWO.dismiss();
    }
}
