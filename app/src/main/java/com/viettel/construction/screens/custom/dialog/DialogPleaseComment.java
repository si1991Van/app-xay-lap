package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.viettel.construction.R;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.screens.wo.adapter.SpinnerWoAdapter;

import java.util.List;

/**
 * Created by manro on 2/22/2018.
 */

public class DialogPleaseComment extends BaseDialog {

    private OnClickDialogPleaseComment dialogCancel;
    private Spinner spTypePleaseComment;
    private String type;
    private String userId = "";

    public DialogPleaseComment(@NonNull Context context, List<AppParamDTO> lstDtos, OnClickDialogPleaseComment dialogCancel) {
        super(context);
        setContentView(R.layout.dialog_please_comment);
        this.dialogCancel = dialogCancel;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        edtEnter = findViewById(R.id.edtContent);
        txtCancleOfCancle =  findViewById(R.id.txt_cancle_dialog_of_cancle);
        txtConfirmOfCancle = findViewById(R.id.txt_dialog_confirm_of_cancle);
        spTypePleaseComment = findViewById(R.id.sp_type_please_comment);
        txtConfirmOfCancle.setOnClickListener(this);
        txtCancleOfCancle.setOnClickListener(this);

        initAdapter(lstDtos);
    }

    private void initAdapter(List<AppParamDTO> lst){
        SpinnerWoAdapter adapter = new SpinnerWoAdapter(lst, getContext());
        spTypePleaseComment.setAdapter(adapter);
        spTypePleaseComment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = lst.get(i).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_dialog_confirm_of_cancle) {
            dialogCancel.OnClickDialogPleaseComment(type , edtEnter.getText().toString().trim(), userId);
        } else
            dismiss();
    }

    public interface OnClickDialogPleaseComment {
        void OnClickDialogPleaseComment(String type, String content, String userId);
    }

}
