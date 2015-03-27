package com.production.kriate.allsmsonetap.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.production.kriate.allsmsonetap.db.DbSms;

/**
 * Created by dima on 27.03.2015.
 */
public class EditSmsFragment extends Fragment {
    public static final String EXTRA_SMS = "com.production.kriate.allsmsonetap.EditSmsFragment.EXTRA_SMS";

    public static EditSmsFragment newInstance(DbSms ds) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SMS, ds);
        EditSmsFragment fragment = new EditSmsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
