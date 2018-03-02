package com.production.kriate.allsmsonetap.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.production.kriate.allsmsonetap.MainActivity;
import com.production.kriate.allsmsonetap.R;
import com.production.kriate.allsmsonetap.db.DbSms;
import com.production.kriate.allsmsonetap.tools.AppSettings;

/**
 * Created by dima on 02.03.2018.
 */

public class DialogAuthOnStart extends DialogFragment implements View.OnClickListener {

    public Button mButtonYes, mButtonNo;
    public CheckBox mCheckAuth;

    public static DialogAuthOnStart newInstance(){
        return new DialogAuthOnStart();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        TextView titleDialog = (TextView) dialog.findViewById(R.id.txt_dia);
        titleDialog.setText(R.string.preferences_sync_title);
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_send_sms_text);
        textView.setText(R.string.preferences_sync_question);

        mCheckAuth = (CheckBox) dialog.findViewById(R.id.dialog_check_auth);
        mCheckAuth.setVisibility(View.VISIBLE);
        mCheckAuth.setText(R.string.preferences_confirm_hide);

        mButtonYes = (Button) dialog.findViewById(R.id.btn_yes);
        mButtonNo = (Button) dialog.findViewById(R.id.btn_no);
        mButtonYes.setOnClickListener(this);
        mButtonNo.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                if (mCheckAuth.isChecked())
                    AppSettings.Item().setAuthOnStart(false).Save(getActivity());

                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.getAuthResult(true);
                break;
            case R.id.btn_no:
                if (mCheckAuth.isChecked())
                    AppSettings.Item().setAuthOnStart(false).Save(getActivity());
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
