package com.production.kriate.allsmsonetap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.production.kriate.allsmsonetap.db.DbCategory;
import com.production.kriate.allsmsonetap.fragments.EditCategoryFragment;

/**
 * Активность вызова фрагмента создания/редактирования категорий
 */
public class EditCategoryActivity extends FragmentActivity {

    private Fragment createFragment() {
        getIntent().hasExtra(EditCategoryFragment.EXTRA_CATEGORY);
        DbCategory category = (DbCategory) getIntent().getSerializableExtra(EditCategoryFragment.EXTRA_CATEGORY);
        return EditCategoryFragment.newInstance(category);
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
