package com.production.kriate.allsmsonetap.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.production.kriate.allsmsonetap.R;
import com.production.kriate.allsmsonetap.db.DbSms;

/**
 * Фрагмент реализующий возможность добавления категорий
 */
public class DialogAddRemoveCategoryFragment extends DialogFragment implements View.OnClickListener{
    public final static int ADD_SMS = 0;
    public final static int REMOVE_SMS = 1;
    public final static String EXTRA_SMS = "com.production.kriate.allsmsontype.DialogAddRemoveCategoryFragment.EXTRA_SMS";

    public Button mButtonYes, mButtonNo;
    private DbSms mDbSms;

    public static DialogAddRemoveCategoryFragment newInstance(DbSms sms) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SMS, sms);
        DialogAddRemoveCategoryFragment fragment = new DialogAddRemoveCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        mDbSms = (DbSms)getArguments().getSerializable(DialogAddRemoveCategoryFragment.EXTRA_SMS);

        TextView textView = (TextView) dialog.findViewById(R.id.dialog_send_sms_text);
        TextView titleDialog = (TextView) dialog.findViewById(R.id.txt_dia);
        titleDialog.setText(R.string.category_dialog_add);
        switch (getTargetRequestCode()) {
            case ADD_SMS:
                textView.setText(getResources().getString(R.string.category_add_sms));
                break;
            default:
                textView.setText(getResources().getString(R.string.category_remove_sms));
                break;
        }
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
        i.putExtra(DialogAddRemoveCategoryFragment.EXTRA_SMS, mDbSms);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }
}
