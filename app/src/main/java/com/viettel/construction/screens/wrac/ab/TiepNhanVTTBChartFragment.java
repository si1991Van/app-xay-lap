package com.viettel.construction.screens.wrac.ab;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.construction.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TiepNhanVTTBChartFragment extends TabLichSuVTTBBaseChart {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tiep_nhan_vttb, container, false);
    }

}
