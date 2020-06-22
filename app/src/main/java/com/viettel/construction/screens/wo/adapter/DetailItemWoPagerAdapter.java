package com.viettel.construction.screens.wo.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.screens.wo.fragment.CheckListWoFragment;
import com.viettel.construction.screens.wo.fragment.InfoItemWoFragment;
import com.viettel.construction.screens.wo.fragment.LogWorkWoFragment;

import java.util.List;

public class DetailItemWoPagerAdapter extends FragmentStatePagerAdapter {

    private WoDTO dto;
    private List<AppParamDTO> lstParamDTOS;

    public DetailItemWoPagerAdapter(FragmentManager fm, WoDTO dto, List<AppParamDTO> list) {
        super(fm);
        this.dto = dto;
        this.lstParamDTOS = list;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment ;
        switch (position){
            case 0:
                fragment = new InfoItemWoFragment(dto, lstParamDTOS);
                return fragment;
            case 1:
                fragment = new CheckListWoFragment(dto);
                return fragment;
            case 2:
                fragment = new LogWorkWoFragment(dto);
                return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Thông Tin";
            case 1:
                return "Đầu Việc";
            case 2:
                return "Lịch Sử";
        }
        return null;
    }
}
