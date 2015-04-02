package com.production.kriate.allsmsonetap.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.production.kriate.allsmsonetap.R;
import com.production.kriate.allsmsonetap.db.DbSms;

/**
 * Created by dima on 01.04.2015.
 */
public class DialogSmsSendFragment extends DialogFragment implements View.OnClickListener {
    public static final String EXTRA_SMS = "com.production.kriate.allsmsonetap.SmsSendFragment.EXTRA_SMS";
    public Button mButtonYes, mButtonNo;
    private DbSms mDbSms;

    public static DialogSmsSendFragment newInstance(DbSms dbSms){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SMS, dbSms);
        DialogSmsSendFragment fragment = new DialogSmsSendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        mDbSms = (DbSms)getArguments().getSerializable(EXTRA_SMS);
        TextView titleDialog = (TextView) dialog.findViewById(R.id.txt_dia);
        titleDialog.setText(R.string.sms_sender_title);
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_send_sms_text);
        String s = String.format(getResources().getString(R.string.sms_sender_text), mDbSms.getTextSms(), mDbSms.getPhoneNumber());
        textView.setText(s);

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
                sendResult();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void sendResult() {
        if (getTargetFragment() == null) {
            return;
        }

        Intent i = new Intent();
        i.putExtra(EditSmsFragment.EXTRA_SMS, mDbSms);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }

}
