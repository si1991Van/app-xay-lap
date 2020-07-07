package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;

public class DialogRattingWO extends BaseDialog{
    private OnClickDialogForCancel dialogCancel;

    private EditText edContent;

    public DialogRattingWO(Context context, OnClickDialogForCancel dialogCancel) {
        super(context);
        setContentView(R.layout.dialog_ratting_wo);
        this.dialogCancel = dialogCancel;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        txtCancleOfCancle = findViewById(R.id.txt_cancle_dialog_of_cancle);
        txtConfirmOfCancle = findViewById(R.id.txt_dialog_confirm_of_cancle);
        edContent = findViewById(R.id.edContent);

        mRadioReasonAvaiableReason = findViewById(R.id.radio_reason_group);

        txtConfirmOfCancle.setOnClickListener(this);
        txtCancleOfCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_dialog_confirm_of_cancle) {
            int idRadioItemSelect = mRadioReasonAvaiableReason.getCheckedRadioButtonId();
            String message = (idRadioItemSelect == R.id.radio_done) ? VConstant.StateWO.Ok : VConstant.StateWO.Ng;
            if (message.equals(VConstant.StateWO.Ng)){
                if (TextUtils.isEmpty(edContent.getText().toString())){
                    Toast.makeText(getContext(), "Nội dung không được để trống!", Toast.LENGTH_LONG).show();
                }else {
                    dialogCancel.onClickConfirmOfCancel(message, edContent.getText().toString());
                    dismiss();
                }
            }else {
                dialogCancel.onClickConfirmOfCancel(message, null);
                dismiss();
            }
        } else
            dismiss();
    }

    public interface OnClickDialogForCancel {
        void onClickConfirmOfCancel(String status, String content);
    }
}
