package com.viettel.construction.screens.wo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.plan.FilterDTORequest;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTORequest;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.plan.WoPlanDTOResponse;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.screens.custom.dialog.DialogCancel;
import com.viettel.construction.screens.custom.dialog.DialogPleaseComment;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailWOActivity extends BaseCameraActivity implements DialogCancel.OnClickDialogForCancel,
        DialogPleaseComment.OnClickDialogPleaseComment {

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtHeader)
    TextView txtHeader;
    @BindView(R.id.tv_code_wo)
    TextView tvCode;
    @BindView(R.id.tv_name_wo)
    TextView tvName;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_performer)
    TextView tvPerformer;
    @BindView(R.id.tv_Process)
    TextView tvProcess;


    @BindView(R.id.tv_handover)
    TextView tvHandover;
    @BindView(R.id.tv_Accept)
    TextView tvAccept;
    @BindView(R.id.tv_Reject)
    TextView tvReject;

    @BindView(R.id.tv_Report)
    TextView tvReport;
    @BindView(R.id.tv_Finish)
    TextView tvFinish;

    @BindView(R.id.tv_type_wo)
    TextView tvTypeWo;
    @BindView(R.id.tv_type_construction_wo)
    TextView tvTypeConstructionWo;
    @BindView(R.id.tv_catWork_wo)
    TextView tvCatWorkWo;
    @BindView(R.id.tv_createdDate)
    TextView tvCreatedDate;

    @BindView(R.id.tv_finishDate)
    TextView tvFinishDate;
    @BindView(R.id.tv_qoutaTime)
    TextView tvQoutaTime;
    @BindView(R.id.tv_totalMonthPlanName)
    TextView tvTotalMonthPlanName;
    @BindView(R.id.lnBottom)
    LinearLayout lnBottom;
    @BindView(R.id.txtCheckList)
    TextView txtCheckList;

    private WoDTO itemWoDTO;
    private DialogCancel dialogCancel;
    private DialogPleaseComment dialogPleaseComment;
    private WoDTORequest woDTORequest = new WoDTORequest();
    private String type = "";

    private List<AppParamDTO> lstParamDTOS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wo);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            itemWoDTO = (WoDTO) getIntent().getExtras().getSerializable("Item_WO");
            type = getIntent().getExtras().getString("Type");
            lnBottom.setVisibility(type.equals("1") ? View.GONE : View.VISIBLE);
            txtCheckList.setVisibility(type.equals("1") ? View.GONE : View.VISIBLE);
            txtCheckList.setPaintFlags(txtCheckList.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            if (itemWoDTO.getState().equals(VConstant.StateWO.Processing)) {
                getForComboBox();
            }
        }
        dialogCancel = new DialogCancel(this, this);
        dialogCancel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setDataView();
    }


    private void setDataView(){
        tvCode.setText(itemWoDTO.getWoCode());
        tvName.setText(itemWoDTO.getWoName());
        tvProgress.setText("3/10");
        tvPerformer.setText(VConstant.getDTO().getFullName());
        switch (itemWoDTO.getState()) {
            case VConstant.StateWO.Assign_cd:
                tvStatus.setText(getString(R.string.assign_cd));
                break;
            case VConstant.StateWO.Accept_cd:
                tvStatus.setText(getString(R.string.accept_cd));
                break;
            case VConstant.StateWO.Reject_cd:
                tvStatus.setText(getString(R.string.reject_cd));
                break;

            case VConstant.StateWO.Assign_ft:
                tvStatus.setText(getString(R.string.assign_ft));
                break;
            case VConstant.StateWO.Accept_ft:
                tvStatus.setText(getString(R.string.accept_ft));
                break;
            case VConstant.StateWO.Reject_ft:
                tvStatus.setText(getString(R.string.reject_ft));
                break;

            case VConstant.StateWO.Processing:
                tvStatus.setText(getString(R.string.processing));
                break;
            case VConstant.StateWO.Opinion_rq:
                tvStatus.setText(getString(R.string.opinion_rq));
                break;
            case VConstant.StateWO.Ok:
                tvStatus.setText(getString(R.string.ok));
                break;
            case VConstant.StateWO.Ng:
                tvStatus.setText(getString(R.string.ng));
                break;
            case VConstant.StateWO.Done:
                tvStatus.setText(getString(R.string.done));
                break;
        }
        txtHeader.setText(itemWoDTO.getWoName());
        tvTypeWo.setText(itemWoDTO.getWoTypeName());

        tvTypeConstructionWo.setText(itemWoDTO.getConstructionName());
        tvCatWorkWo.setText(itemWoDTO.getCatWorkItemTypeName());
        tvCreatedDate.setText(itemWoDTO.getCreatedDate() != null ? itemWoDTO.getCreatedDate() :
                getString(R.string.updating_date));
        tvFinishDate.setText(itemWoDTO.getFinishDate() != null ? itemWoDTO.getFinishDate() :
                getString(R.string.updating_date));
        tvQoutaTime.setText(String.valueOf(itemWoDTO.getQoutaTime()));
        tvTotalMonthPlanName.setText(itemWoDTO.getTotalMonthPlanName());
        woDTORequest.setSysUserRequest(VConstant.getUser());
        if (itemWoDTO.getFtId() == VConstant.getUser().getSysUserId()) {
            switch (itemWoDTO.getState()) {
                case VConstant.StateWO.Assign_ft:
                    tvAccept.setVisibility(View.VISIBLE);
                    tvReject.setVisibility(View.VISIBLE);
                    tvProcess.setVisibility(View.GONE);
                    tvFinish.setVisibility(View.GONE);
                    tvReport.setVisibility(View.GONE);
                    break;
                case VConstant.StateWO.Accept_ft:
                    tvProcess.setVisibility(View.VISIBLE);
                    tvAccept.setVisibility(View.GONE);
                    tvReject.setVisibility(View.GONE);
                    tvFinish.setVisibility(View.GONE);
                    tvReport.setVisibility(View.GONE);
                    break;
                case VConstant.StateWO.Processing:
                    tvFinish.setVisibility(View.VISIBLE);
                    tvReport.setVisibility(View.VISIBLE);
                    tvProcess.setVisibility(View.GONE);
                    tvAccept.setVisibility(View.GONE);
                    tvReject.setVisibility(View.GONE);
                    break;
                    default:
                        tvFinish.setVisibility(View.GONE);
                        tvReport.setVisibility(View.GONE);
                        tvProcess.setVisibility(View.GONE);
                        tvAccept.setVisibility(View.GONE);
                        tvReject.setVisibility(View.GONE);
                        break;
            }
        }else if (itemWoDTO.getFtId() != VConstant.getUser().getSysUserId()){
            // check CD
        }

    }

    private void updateWo(String state, String content, String type, String userId){
        itemWoDTO.setState(state);
        itemWoDTO.setAcceptTime(getDataToday());
//        itemWoDTO.set(content);
//        itemWoDTO.setAcceptTime(type);
//        itemWoDTO.setAcceptTime(userId);
        woDTORequest.setWoDTO(itemWoDTO);
        ApiManager.getInstance().updateWo(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoPlanDTOResponse response = WoPlanDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(DetailWOActivity.this, getString(R.string.update_plan), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(DetailWOActivity.this, getString(R.string.update_fail_plan), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DetailWOActivity.this, getString(R.string.update_fail_plan), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });

    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.tv_Accept)
        public void onClickAcceptFt() {
        updateWo(VConstant.StateWO.Accept_ft, null, null, null);
    }

    @OnClick(R.id.tv_Process)
    public void onClickProcess() {
        updateWo(VConstant.StateWO.Processing,  null, null, null);
    }

    @OnClick(R.id.tv_Finish)
    public void onClickFinish() {
        updateWo(VConstant.StateWO.Ok,  null, null, null);
    }

    @OnClick(R.id.tv_Reject)
    public void onReject(){
        dialogCancel.show();
    }

    @OnClick(R.id.tv_Report)
    public void onReport(){
        dialogPleaseComment = new DialogPleaseComment(this, lstParamDTOS,this);
        dialogPleaseComment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPleaseComment.show();
    }

    @Override
    public void onClickConfirmOfCancel(String content) {
        if (TextUtils.isEmpty(content)){
            Toast.makeText(this, "Nội dung không được để trống", Toast.LENGTH_LONG).show();
        }else {
            updateWo(VConstant.StateWO.Reject_ft, content, null, null);
            dialogCancel.dismiss();
        }
    }

    @Override
    public void OnClickDialogPleaseComment(String type, String content, String userId) {
        if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(type)){
            updateWo(VConstant.StateWO.Opinion_rq, content, type, userId);
            dialogPleaseComment.dismiss();

        }else
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "Nội dung không được để trống", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Bạn chưa chọn loại ý kiến", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.txtCheckList)
    public void onClickCheckList(){
        Intent intent = new Intent(DetailWOActivity.this, CheckListWOActivity.class);
        intent.putExtra("ITEM_WO", itemWoDTO);
        startActivity(intent);
    }


    private void getForComboBox(){
        woDTORequest.setSysUserRequest(VConstant.getUser());
        FilterDTORequest filterDTORequest = new FilterDTORequest();
        filterDTORequest.setParType("AP_OPINION_TYPE");
        woDTORequest.setFilter(filterDTORequest);

        ApiManager.getInstance().getForComboBox(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoDTOResponse response = WoDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (response.getLstDataForComboBox() != null){
                            AppParamDTO dto = new AppParamDTO();
                            lstParamDTOS = response.getLstDataForComboBox();
                            dto.setName("Loại ý kiến");
                            lstParamDTOS.add(0, dto);
                        }
                    } else {
                        Toast.makeText(DetailWOActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DetailWOActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(DetailWOActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
