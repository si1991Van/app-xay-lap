package com.viettel.construction.screens.wo.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.plan.FilterDTORequest;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.plan.WoDTORequest;
import com.viettel.construction.model.api.plan.WoDTOResponse;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.screens.wo.adapter.DetailItemWoPagerAdapter;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailItemWoActivity extends BaseCameraActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private WoDTO itemWoDTO;
    private WoDTORequest woDTORequest = new WoDTORequest();
//    private String type = "";
    private List<AppParamDTO> lstParamDTOS = new ArrayList<>();
    private DetailItemWoPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_wo);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            itemWoDTO = (WoDTO) getIntent().getExtras().getSerializable("Item_WO");
//            type = getIntent().getExtras().getString("Type");
            if (itemWoDTO.getState().equals(VConstant.StateWO.Processing)) {
                getForComboBox();
            }else {
                initView();
            }
            txtHeader.setText(itemWoDTO.getWoName());
            txtHeader.setSelected(true);
        }


    }

    private void initView(){
        viewPagerAdapter = new DetailItemWoPagerAdapter(getSupportFragmentManager(), itemWoDTO, lstParamDTOS);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
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
                            initView();
                        }
                    } else {
                        Toast.makeText(DetailItemWoActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DetailItemWoActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(DetailItemWoActivity.this, getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
