package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.plan.WoDTO;
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
    private List<WoDTO> listSelect = new ArrayList<>();
    private List<WoDTO> listAll = new ArrayList<>();

    private OnClickDialogForConfirm onClickDialog;

    SelectWoAdapter adapter;

    public DialogShowListWO(@NonNull Context context, List<WoDTO> listOld, List<WoDTO> list, OnClickDialogForConfirm onClickDialog) {
        super(context);
        setContentView(R.layout.dialog_show_list_wo);
        this.onClickDialog = onClickDialog;
        this.listAll = list;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        recyclerView = findViewById(R.id.rvWO);
        txtTitle = findViewById(R.id.txtHeader);
        txtTitle.setText("Danh sách WO");
        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(view -> {
            if (listSelect != null && listSelect.size() != 0){
                onClickDialog.addListWo(listSelect);
            }
            dismiss();
        });
        initAdapter(listAll, listOld);
    }

    private void initAdapter(List<WoDTO> list, List<WoDTO> listOld) {
        //recyclerview
        adapter = new SelectWoAdapter(getContext(), listOld, list, listSelect);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setAdapter(adapter);
    }

    public interface OnClickDialogForConfirm {

        void addListWo(List<WoDTO> list);
    }

}
