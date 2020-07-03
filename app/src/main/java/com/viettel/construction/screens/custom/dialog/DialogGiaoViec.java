package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.plan.WoPlanDTO;
import com.viettel.construction.model.api.wo.WoSimpleFtDTO;
import com.viettel.construction.screens.wo.adapter.ImageCheckListWoAdapter;
import com.viettel.construction.screens.wo.adapter.WoSimpleFtAdapter;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manro on 2/22/2018.
 */

public class DialogGiaoViec extends BaseDialog {

    private RecyclerView recyclerView;
    private ImageView imgBack;
    private TextView txtSave;
    private TextView txtNoData;
    private EditText edtSearch;

    private WoSimpleFtAdapter mAdapter;
    private List<WoSimpleFtDTO> mList;

    private OnDialogFtId onClickDialog;

    public DialogGiaoViec(@NonNull Context context, List<WoSimpleFtDTO> list, OnDialogFtId dialogFtId) {
        super(context);
        setContentView(R.layout.dialog_giaoviec);
        mList = list;
        this.onClickDialog = dialogFtId;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        recyclerView = findViewById(R.id.rvWO);
        txtTitle = findViewById(R.id.txtHeader);
        txtSave = findViewById(R.id.txtSave);
        txtNoData = findViewById(R.id.txtNoData);
        edtSearch = findViewById(R.id.edtSearch);


        txtTitle.setText("Danh sách User");
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(view -> {
            dismiss();
        });
        txtSave.setOnClickListener(view -> {

        });
        if (mList == null || mList.size() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        initAdapterImage();
    }

    private void initAdapterImage() {
        mAdapter = new WoSimpleFtAdapter(getContext(), mList, ftId -> {
            onClickDialog.onFt(ftId);
            dismiss();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (charSequence.toString().trim().length() == 0)
                        mAdapter.setData(mList);
                    else {
                        mList = dataSearch(charSequence.toString().trim());
                        mAdapter.setData(mList);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }


    public List<WoSimpleFtDTO> dataSearch(String keyWord) {
        List<WoSimpleFtDTO> dataSearch = new ArrayList<>();
        keyWord = StringUtil.removeAccentStr(keyWord.trim()).toLowerCase();
        for (WoSimpleFtDTO dto : mList) {
            String code = "", name = "", email = "";
            if (dto.getEmployeeCode() != null) {
                code = dto.getEmployeeCode().toLowerCase();
            }
            if (dto.getFullName() != null) {
                name = StringUtil.removeAccentStr(dto.getFullName()).toLowerCase();
            }
            if (dto.getEmail() != null) {
                email = StringUtil.removeAccentStr(dto.getEmail()).toLowerCase();
            }
            if (code.contains(keyWord) || name.contains(keyWord) || email.contains(keyWord))
                dataSearch.add(dto);
        }
        return dataSearch;
    }

    public interface OnDialogFtId {

        void onFt(long ftId);
    }

}
