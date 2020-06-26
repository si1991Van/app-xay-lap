package com.viettel.construction.screens.wo.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTORequest;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.screens.custom.dialog.DialogCancel;
import com.viettel.construction.screens.custom.dialog.DialogPleaseComment;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoItemWoFragment extends Fragment {

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


    private WoDTO itemWoDTO;
    private WoDTORequest woDTORequest = new WoDTORequest();
    private List<AppParamDTO> lstParamDTOS;
//    private String type;
    private DialogCancel dialogCancel;
    private DialogPleaseComment dialogPleaseComment;

    public  InfoItemWoFragment(WoDTO dto, List<AppParamDTO> lst)  {
        super();
        this.itemWoDTO = dto;
        this.lstParamDTOS = lst;
//        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_item_wo, container, false);
        ButterKnife.bind(this, view);
//        lnBottom.setVisibility(type.equals("1") ? View.GONE : View.VISIBLE);
        setDataView(itemWoDTO);
        return view;
    }

    private void setDataView(WoDTO itemWoDTO){
        if (itemWoDTO == null) return;
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
                    tvProcess.setVisibility(itemWoDTO.isInPlan() ? View.VISIBLE : View.GONE);
                    tvAccept.setVisibility(View.GONE);
                    tvReject.setVisibility(View.GONE);
                    tvFinish.setVisibility(View.GONE);
                    tvReport.setVisibility(View.GONE);
                    break;
                case VConstant.StateWO.Processing:
                    tvFinish.setVisibility(itemWoDTO.isCanFinish() ? View.VISIBLE : View.GONE);
                    tvReport.setVisibility(View.VISIBLE);
                    tvProcess.setVisibility(View.GONE);
                    tvAccept.setVisibility(View.GONE);
                    tvReject.setVisibility(View.GONE);
                    break;
                default:
                    lnBottom.setVisibility(View.GONE);
                    break;
            }
        }else if (itemWoDTO.getFtId() != VConstant.getUser().getSysUserId()){
            // check CD
        }

    }


    private void updateWo(String state, String content){
        itemWoDTO.setState(state);
        itemWoDTO.setAcceptTime(getDataToday());
        woDTORequest.setOpinionContent(content);
        woDTORequest.setWoDTO(itemWoDTO);
        ApiManager.getInstance().updateWo(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoDTOResponse response = WoDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(getContext(), getString(R.string.updated), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });

    }

    private void updateProcessing(String state, String content, String type, String userId){
        itemWoDTO.setState(state);
        itemWoDTO.setAcceptTime(getDataToday());
        woDTORequest.setOpinionContent(content);
        woDTORequest.setOpinionType(type);
        woDTORequest.setOpinionObject(userId);
        woDTORequest.setWoDTO(itemWoDTO);


        ApiManager.getInstance().updateOpinion(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoDTOResponse response = WoDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(getContext(), getString(R.string.updated), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });
    }

    @OnClick(R.id.tv_Accept)
    public void onClickAcceptFt() {
        updateWo(VConstant.StateWO.Accept_ft, null);
    }

    @OnClick(R.id.tv_Process)
    public void onClickProcess() {
        updateWo(VConstant.StateWO.Processing,  null);
    }

    @OnClick(R.id.tv_Finish)
    public void onClickFinish() {
        updateWo(VConstant.StateWO.Ok,  null);
    }

    @OnClick(R.id.tv_Reject)
    public void onReject(){
        dialogCancel = new DialogCancel(getContext(), s -> {
            if (TextUtils.isEmpty(s)){
                Toast.makeText(getContext(), "Nội dung không được để trống", Toast.LENGTH_LONG).show();
            }else {
                updateWo(VConstant.StateWO.Reject_ft, s);
                dialogCancel.dismiss();
            }

        });
        dialogCancel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCancel.show();
    }

    @OnClick(R.id.tv_Report)
    public void onReport(){
        dialogPleaseComment = new DialogPleaseComment(getContext(), lstParamDTOS, new DialogPleaseComment.OnClickDialogPleaseComment() {
            @Override
            public void OnClickDialogPleaseComment(String type, String content, String userId) {
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(getContext(), "Nội dung không được để trống", Toast.LENGTH_LONG).show();
                }else if (type.equals("Loại ý kiến")){
                    Toast.makeText(getContext(), "Phải chọn loại ý kiến!", Toast.LENGTH_LONG).show();
                }else {
                    updateProcessing(VConstant.StateWO.Opinion_rq, content, type, String.valueOf(VConstant.getUser().getSysUserId()));
                    dialogPleaseComment.dismiss();
                }
            }
        });
        dialogPleaseComment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPleaseComment.show();
    }



    private String getDataToday(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(currentTime);
        return currentDateandTime;
    }

}
