package com.production.kriate.allsmsonetap.tools;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;

import com.production.kriate.allsmsonetap.R;
import com.production.kriate.allsmsonetap.db.DbSms;

import java.io.Serializable;
import java.util.ArrayList;

public class DialogContextMenu extends DialogFragment implements View.OnClickListener {
    public static final String ELEMENTS = "com.production.kriate.allsmsonetap.DialogContextMenu.ELEMENTS";

    public static DialogContextMenu newInstance(ListItemElement list){
        Bundle args = new Bundle();
        args.putSerializable(ELEMENTS, list);
        DialogContextMenu fragment = new DialogContextMenu();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                getActivity().finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public class ListItemElement implements Serializable {
        private int mId;
        private String mName;
        private String mDescription;

        public ListItemElement(int id, String name, String description){
            mId = id;
            mName = name;
            mDescription = description;
        }

        public int getmId() {
            return mId;
        }
        public String getmName() {
            return mName;
        }
        public String getmDescription() {
            return mDescription;
        }
    }
}
