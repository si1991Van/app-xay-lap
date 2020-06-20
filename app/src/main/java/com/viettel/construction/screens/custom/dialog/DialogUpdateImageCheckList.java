package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.plan.WoDTO;
import com.viettel.construction.screens.plan.SelectWoAdapter;
import com.viettel.construction.screens.wo.ImageCheckListWoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manro on 2/22/2018.
 */

public class DialogUpdateImageCheckList extends BaseDialog {

    private RecyclerView recyclerView;
    private ImageView imgClose;

    private List<String> mList = new ArrayList<>();
    private ImageCheckListWoAdapter mAdapter;

    private OnClickDialogForConfirm onClickDialog;

    public DialogUpdateImageCheckList(@NonNull Context context, List<String> list, OnClickDialogForConfirm onClickDialog) {
        super(context);
        setContentView(R.layout.dialog_show_list_wo);
        this.onClickDialog = onClickDialog;
        this.mList = list;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        recyclerView = findViewById(R.id.rvWO);
        txtTitle = findViewById(R.id.txtHeader);
        txtTitle.setText("Cập nhật ảnh");
        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(view -> {

            dismiss();
        });
        initAdapterImage();
    }

    private void initAdapterImage(){
        mAdapter = new ImageCheckListWoAdapter(mList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public interface OnClickDialogForConfirm {

        void addImage();
    }

}
