package com.viettel.construction.screens.plan;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatePlanActivity extends BaseCameraActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtSave)
    TextView txtSave;
    @BindView(R.id.txtHeader)
    TextView txtHeader;
    private ConstructionAcceptanceCertDetailDTO item;

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
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        if (bundle.getSerializable("EDIT_PLAN") != null) {
            item = (ConstructionAcceptanceCertDetailDTO) bundle.getSerializable("EDIT_PLAN");
        }

//        imgBack.setOnClickListener((v) -> {
//            finish();
//        });
//        txtSave.setOnClickListener((v) ->{
//            Toast.makeText(CreatePlanActivity.this, "Luu thanh cong", Toast.LENGTH_LONG).show();
//        });
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.txtSave)
    public void onClickSave() {
        Toast.makeText(CreatePlanActivity.this, "Luu thanh cong", Toast.LENGTH_LONG).show();
    }
}
