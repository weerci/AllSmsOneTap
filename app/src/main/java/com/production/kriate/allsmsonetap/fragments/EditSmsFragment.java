package com.production.kriate.allsmsonetap.fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.production.kriate.allsmsonetap.db.DbSms;
import com.production.kriate.allsmsonetap.R;

/**
 * Created by dima on 27.03.2015.
 */
public class EditSmsFragment extends Fragment{
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
        setHasOptionsMenu(true);
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_sms_layout, container, false);
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            getActivity().finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
