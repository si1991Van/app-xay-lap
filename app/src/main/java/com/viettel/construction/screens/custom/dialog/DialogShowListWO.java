package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.plan.WoMappingPlanDTO;
import com.viettel.construction.screens.plan.PlanWoAdapter;
import com.viettel.construction.screens.plan.SelectWoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by manro on 2/22/2018.
 */

public class DialogShowListWO extends BaseDialog {

    private RecyclerView recyclerView;
    private ImageView imgClose;

    private OnClickDialogForConfirm onClickDialog;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getContext().setTheme(R.style.dialogFullScreen);
//    }

    public DialogShowListWO(@NonNull Context context, OnClickDialogForConfirm onClickDialog) {
        super(context);
        setContentView(R.layout.dialog_show_list_wo);
        this.onClickDialog = onClickDialog;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        recyclerView = findViewById(R.id.rvWO);
        txtTitle = findViewById(R.id.txtHeader);
        txtTitle.setText("Danh sách ");
        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(view -> {
            dismiss();
        });
        initAdapter();

    }

    private void initAdapter() {
        //recyclerview
        List<WoMappingPlanDTO> listData = new ArrayList<>();
        // fix data wo
        if (listData.size() == 0){
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
            listData.add(new WoMappingPlanDTO());
        }
        SelectWoAdapter adapter = new SelectWoAdapter(getContext(), listData);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setAdapter(adapter);
    }

    public interface OnClickDialogForConfirm {
        void onClickConfirmOfConfirm();
    }

}
