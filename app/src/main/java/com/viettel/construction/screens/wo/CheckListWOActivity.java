package com.viettel.construction.screens.wo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTORequest;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.wo.ImgeChecklistDTO;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.screens.plan.UpdateImageCheckListWoActivity;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckListWOActivity extends BaseCameraActivity {


    @Nullable
    @BindView(R.id.progressBar)
    public CustomProgress progressBar;

    @Nullable
    @BindView(R.id.imgBack)
    public ImageView imgBack;

    @Nullable
    @BindView(R.id.txtHeader)
    public TextView txtHeader;

    @Nullable
    @BindView(R.id.txtSave)
    public TextView txtSave;
    @Nullable
    @BindView(R.id.rcvData)
    public RecyclerView rcvData;


    private WoDTO woDTO;
    private WoDTORequest woDTORequest = new WoDTORequest();
    private List<WoMappingChecklistDTO> lstChecklistsOfWo = new ArrayList<>();
    private WoMappingChecklistDTO woMappingChecklistDTO;
    private ChecklistsWoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_wo);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if (getIntent().getExtras() != null) {
                woDTO = (WoDTO) getIntent().getExtras().getSerializable("ITEM_WO");
                txtSave.setVisibility(woDTO.getState().equals(VConstant.StateWO.Processing) ? View.VISIBLE : View.INVISIBLE);
            }
        }
        woDTORequest.setSysUserRequest(VConstant.getUser());
        txtHeader.setText(getString(R.string.txt_title_lstchecklists));

        initAdapterCheckLists();
        getCheckListWo();
    }

    private void initAdapterCheckLists(){
        adapter = new ChecklistsWoAdapter(this, lstChecklistsOfWo, woDTO.getState(), (object) -> {
            woMappingChecklistDTO = object;
            Intent intent = new Intent(CheckListWOActivity.this, UpdateImageCheckListWoActivity.class);
            intent.putExtra("WoMappingChecklistDTO", woMappingChecklistDTO);
            startActivityForResult(intent, 123);

        });
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rcvData.setLayoutManager(linearLayoutManager2);
        rcvData.setAdapter(adapter);
    }

    private void getCheckListWo(){
        progressBar.setLoading(true);
        woDTORequest.setWoDTO(woDTO);
        ApiManager.getInstance().checkListWo(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoDTOResponse response = WoDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (response.getLstChecklistsOfWo() != null){
                            lstChecklistsOfWo = response.getLstChecklistsOfWo();
                            adapter.setListData(lstChecklistsOfWo);
                        }
                    } else {
                        Toast.makeText(CheckListWOActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CheckListWOActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                }
                progressBar.setLoading(false);
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(CheckListWOActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                progressBar.setLoading(false);
            }
        });
    }

    private void updateCheckList(){
        woDTORequest.setLstChecklistsOfWo(lstChecklistsOfWo);
        ApiManager.getInstance().updateCheckListWo(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoDTOResponse response = WoDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(CheckListWOActivity.this, getString(R.string.update_checklist_success), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CheckListWOActivity.this, getString(R.string.update_checklist_fail), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CheckListWOActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                }
                progressBar.setLoading(false);
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(CheckListWOActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                progressBar.setLoading(false);
            }
        });

    }

    @OnClick({R.id.imgBack})
    public void onClickBack(){
        finish();
    }

    @OnClick({R.id.txtSave})
    public void onClickSave(){
        updateCheckList();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1024){
            Bundle bundle = data.getExtras();
            woMappingChecklistDTO = (WoMappingChecklistDTO) bundle.getSerializable("KEY_IMAGE_CHECK_LIST");
//            woDTORequest.get
        }
    }
}
