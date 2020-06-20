package com.viettel.construction.screens.plan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;
import com.viettel.construction.screens.wo.ImageCheckListWoAdapter;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateImageCheckListWoActivity extends BaseCameraActivity {

    @BindView(R.id.rvWO)
    RecyclerView recyclerView;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private String filePath = "";
    private List<String> lstImg = new ArrayList<>();
    private WoMappingChecklistDTO dto;
    private ImageCheckListWoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image_check_list_wo);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            dto = (WoMappingChecklistDTO) bundle.getSerializable("WoMappingChecklistDTO");
            if (dto.getLstImgs() != null && dto.getLstImgs().size() > 0){
                lstImg = dto.getLstImgs();
            }
        }
        initView();
        initAdapterImage();
    }


    private void initAdapterImage(){
        mAdapter = new ImageCheckListWoAdapter(lstImg, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void initView(){
        try {
                if (checkRuntimePermission()) {
                    accessLocation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        txtHeader.setText("Cập nhật ảnh");
        imgBack.setOnClickListener(view -> {
            if (lstImg != null || lstImg.size() > 0){
                Intent resultIntent = new Intent();
                resultIntent.putExtra("KEY_IMAGE_CHECK_LIST", dto);
                setResult(1024, resultIntent);
                finish();
            }else {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VConstant.REQUEST_CODE_CAMERA){
            if (resultCode == Activity.RESULT_OK) {
                filePath = mPhotoFile.getPath();
                Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                lstImg.add(StringUtil.getStringImage(bitmap));
                dto.setLstImgs(lstImg);
                mAdapter.setData(lstImg);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                /** Picture wasn't taken*/
            }
        }
    }

}
