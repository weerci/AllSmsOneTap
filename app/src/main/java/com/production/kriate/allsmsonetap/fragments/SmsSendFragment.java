package com.production.kriate.allsmsonetap.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.production.kriate.allsmsonetap.db.DbSms;
import com.production.kriate.allsmsonetap.R;


public class SmsSendFragment extends DialogFragment{
    public static final String EXTRA_SMS = "com.production.kriate.allsmsonetap.SmsSendFragment.EXTRA_SMS";
    private DbSms mDbSms;

    public SmsSendFragment(){}
    public static SmsSendFragment newInstance(DbSms dbSms){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SMS, dbSms);
        SmsSendFragment fragment = new SmsSendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_send_sms_layout, null);

        mDbSms = (DbSms)getArguments().getSerializable(EXTRA_SMS);

        TextView textView = (TextView) v.findViewById(R.id.dialog_send_sms_text);
        String s = String.format(getResources().getString(R.string.sms_sender_text), mDbSms.getTextSms(), mDbSms.getPhoneNumber());
        textView.setText(s);

        return new AlertDialog.Builder(getActivity()/*, R.style.CustomDialogTheme*/)
                .setView(v)
                .setTitle(R.string.sms_sender_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult();
                            }
                        }
                )
                .setNegativeButton(android.R.string.no, null)
                .create();
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
