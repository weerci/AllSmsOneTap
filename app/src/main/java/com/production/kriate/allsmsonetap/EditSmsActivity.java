package com.production.kriate.allsmsonetap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.production.kriate.allsmsonetap.db.DbSms;
import com.production.kriate.allsmsonetap.fragments.EditSmsFragment;

/*Активность вызова фрагмента создания/изменения шаблона SMS*/
public class EditSmsActivity extends FragmentActivity {

    Fragment createFragment() {
        if(getIntent().hasExtra(EditSmsFragment.EXTRA_SMS)) {
            DbSms sms = (DbSms) getIntent().getSerializableExtra(EditSmsFragment.EXTRA_SMS);
            return EditSmsFragment.newInstance(sms);
        } else
            return EditSmsFragment.newInstance(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

}
