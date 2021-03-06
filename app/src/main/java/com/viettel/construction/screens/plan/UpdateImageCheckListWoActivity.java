package com.viettel.construction.screens.plan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTORequest;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.wo.ImgChecklistDTO;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;
import com.viettel.construction.model.db.ImageCapture;
import com.viettel.construction.screens.commons.SelectImagesActivity;
import com.viettel.construction.screens.wo.adapter.ImageCheckListWoAdapter;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.util.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class UpdateImageCheckListWoActivity extends BaseCameraActivity {

    @BindView(R.id.rvWO)
    RecyclerView recyclerView;
    @BindView(R.id.imgClose)
    ImageView imgBack;
    @BindView(R.id.txtHeader)
    TextView txtHeader;
    @BindView(R.id.txtCode)
    TextView txtCode;
    @BindView(R.id.spStatus)
    Spinner spStatus;

    @BindView(R.id.btn_camera)
    ImageView imgCamera;
    @BindView(R.id.btnSelectImage)
    ImageView btnSelectImage;
    @BindView(R.id.btnUpdateCheckList)
    Button btnUpdateCheckList;
    @BindView(R.id.lnMass)
    LinearLayout lnMass;
    @BindView(R.id.lnStatus)
    LinearLayout lnStatus;
    @BindView(R.id.ed_total)
    EditText edTotal;
    @BindView(R.id.txt_total)
    TextView txtTotal;

    private String filePath = "";
    private List<ImgChecklistDTO> lstImg = new ArrayList<>();
    private WoMappingChecklistDTO dto;
    private WoDTO woDTO;
    private ImageCheckListWoAdapter mAdapter;
    private String state;
    private int position;

    private String[] itemStatus = {"Mới", "Kết thúc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image_check_list_wo);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dto = (WoMappingChecklistDTO) bundle.getSerializable("WoMappingChecklistDTO");
            woDTO = (WoDTO) bundle.getSerializable("WoDTO");
            state = woDTO.getState();
//            position = bundle.getInt("Position");
            if (dto.getLstImgs() != null && dto.getLstImgs().size() > 0) {
                lstImg = dto.getLstImgs();
            }
            imgCamera.setVisibility(state.equals(VConstant.StateWO.Processing) && !"1".equals(dto.getQuantityByDate()) ? View.VISIBLE : View.INVISIBLE);
            btnSelectImage.setVisibility(state.equals(VConstant.StateWO.Processing) && !"1".equals(dto.getQuantityByDate()) ? View.VISIBLE : View.INVISIBLE);
            btnUpdateCheckList.setVisibility(state.equals(VConstant.StateWO.Processing) ? View.VISIBLE : View.GONE);
            initView();
            initAdapterImage();
        }
    }

    private void initAdapterImage() {
        mAdapter = new ImageCheckListWoAdapter(lstImg, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void initView() {

        txtHeader.setText("Chi tiết đầu việc");
        txtCode.setText(dto.getChecklistName());
        imgBack.setOnClickListener(view -> {
            finish();
        });
        codeSpinner(itemStatus, spStatus, dto);
        if (dto.getQuantityByDate() != null) {
            lnMass.setVisibility("1".equals(dto.getQuantityByDate()) ? View.VISIBLE : View.GONE);
            lnStatus.setVisibility("1".equals(dto.getQuantityByDate()) ? View.GONE : View.VISIBLE);
            recyclerView.setVisibility("1".equals(dto.getQuantityByDate()) ? View.GONE : View.VISIBLE);
            edTotal.setFocusable(state.equals(VConstant.StateWO.Processing) ? true : false);
            edTotal.setEnabled(state.equals(VConstant.StateWO.Processing) ? true : false);
            txtTotal.setText(String.valueOf(dto.getQuantityLength()));
        }
    }

    private void codeSpinner(String[] item, Spinner spinner, WoMappingChecklistDTO dto) {
        ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(UpdateImageCheckListWoActivity.this, R.layout.spinner_item, item);
        langAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(langAdapter);
        spinner.setSelection(dto.getState().equals("NEW") ? 0 : 1);
        spinner.setEnabled(state.equals(VConstant.StateWO.Processing) ? true : false);
        spinner.setClickable(state.equals(VConstant.StateWO.Processing) ? true : false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (adapterView.getItemAtPosition(position).toString().equals("Mới")) {
                    dto.setState("NEW");
                } else {
                    dto.setState("DONE");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.btnSelectImage)
    public void selectedImage() {
        Intent selectImage = new Intent(this, SelectImagesActivity.class);
        startActivityForResult(selectImage, ParramConstant.SelectedImage_RequestCode);
    }

    @OnClick(R.id.btnUpdateCheckList)
    public void onClickSave() {
        if (dto.getQuantityByDate() == null){
            if (lstImg == null || lstImg.size() == 0){
                Toast.makeText(UpdateImageCheckListWoActivity.this, "Bạn phải chup it nhất một hình ảnh!",
                        Toast.LENGTH_LONG).show();
            }else {
                updateCheckList();
            }
        }else if ("1".equals(dto.getQuantityByDate())){
            if (Integer.parseInt(edTotal.getText().toString()) > woDTO.getRemainLength()){
                Toast.makeText(UpdateImageCheckListWoActivity.this, "Khối lượng không được lớn hơn tổng khối lượng hiện tại!",
                        Toast.LENGTH_LONG).show();
            }else {
                updateCheckList();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case VConstant.REQUEST_CODE_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    filePath = mPhotoFile.getPath();
                    Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                    Bitmap newBitmap = ImageUtils.drawTextOnImage(bitmap, latitude, longitude);
                    ImgChecklistDTO imgChecklistDTO = new ImgChecklistDTO();
                    imgChecklistDTO.setImgBase64(StringUtil.getStringImage(newBitmap));
                    imgChecklistDTO.setLatitude(latitude);
                    imgChecklistDTO.setLongtitude(longitude);
                    lstImg.add(imgChecklistDTO);
                    dto.setLstImgs(lstImg);
                    mAdapter.setData(lstImg);

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    /** Picture wasn't taken*/
                }
                break;
                case ParramConstant.SelectedImage_RequestCode:
                    if (data != null) {
                        List<ImageCapture> lsData =
                                (List<ImageCapture>) data.getSerializableExtra(ParramConstant.SelectedImage_Key);
                        if (lsData != null && lsData.size() > 0) {
                            for (ImageCapture img : lsData) {
                                    //Nếu đã chọn rồi thì thôi
                                    Bitmap bitmap = StringUtil.setImage(img.getImageData());
                                    Bitmap newBitmap = ImageUtils.drawTextOnImage(bitmap, img.getLattitude(), img.getLongtitude());
                                    ImgChecklistDTO imgChecklistDTO = new ImgChecklistDTO();
                                    imgChecklistDTO.setImgBase64(StringUtil.getStringImage(newBitmap));
                                    imgChecklistDTO.setLatitude(img.getLattitude());
                                    imgChecklistDTO.setLongtitude(img.getLongtitude());
                                    lstImg.add(imgChecklistDTO);
                                    dto.setLstImgs(lstImg);
                                    mAdapter.setData(lstImg);

                            }
                            mAdapter.notifyDataSetChanged();
                            filePath = "have image";// trick to pass save function
                        }
                    }
                    break;
        }

    }

    private void updateCheckList() {
        WoDTORequest woDTORequest = new WoDTORequest();
        woDTORequest.setSysUserRequest(VConstant.getUser());
        List<WoMappingChecklistDTO> woMappingChecklistDTOS = new ArrayList<>();
        if (!TextUtils.isEmpty(edTotal.getText().toString()) && Integer.parseInt(edTotal.getText().toString()) > 0) {
            dto.setAddedQuantityLength(Integer.parseInt(edTotal.getText().toString()));
            dto.setState("DONE");
        }
        woMappingChecklistDTOS.add(dto);
        woDTORequest.setLstChecklistsOfWo(woMappingChecklistDTOS);
        woDTORequest.setWoDTO(woDTO);

        ApiManager.getInstance().updateCheckListWo(woDTORequest, WoDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    WoDTOResponse response = WoDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(UpdateImageCheckListWoActivity.this, getString(R.string.update_checklist_success), Toast.LENGTH_SHORT).show();
                        setResult(1024);
                        finish();
                    } else {
                        Toast.makeText(UpdateImageCheckListWoActivity.this, getString(R.string.update_checklist_fail), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateImageCheckListWoActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(UpdateImageCheckListWoActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
