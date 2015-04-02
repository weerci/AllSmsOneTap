package com.production.kriate.allsmsonetap.fragments;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.production.kriate.allsmsonetap.R;
import com.production.kriate.allsmsonetap.db.DbCategory;
import com.production.kriate.allsmsonetap.db.DbConnector;
import com.production.kriate.allsmsonetap.db.DbSms;
import com.production.kriate.allsmsonetap.view.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Created by dima on 02.04.2015.
 */
public class EditCategoryFragment extends Fragment {
    public final static String EXTRA_CATEGORY = "com.production.kriate.allsmsonetap.EditCategoryFragment.EXTRA_CATEGORY";
    private final static String DIALOG_ADD_REMOVE_CATEGORY_TO_SMS = "com.production.kriate.allsmsonetap.EditCategoryFragment.ADD_REMOVE_CATEGORY";
    private ArrayList<DbSms> mSelectedSms;
    private ArrayList<DbSms> mAvailableSms;

    private EditText mNameField;
    private long mIdCategory;
    private ViewPager mViewPager;
    private ListView mListView;

    public static EditCategoryFragment newInstance(DbCategory dc) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CATEGORY, dc);
        EditCategoryFragment fragment = new EditCategoryFragment();
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
        View v = inflater.inflate(R.layout.edit_category_layout, container, false);

        mNameField = (EditText) v.findViewById(R.id.category_name_edit_text);
        mIdCategory = DbCategory.EMPTY_ID;

        DbCategory dbCategory = (DbCategory)getArguments().getSerializable(EditCategoryFragment.EXTRA_CATEGORY);

        if (dbCategory != null) {
            mIdCategory = dbCategory.getId();
            mNameField.setText(dbCategory.getName());
        }
        mSelectedSms = DbConnector.newInstance(getActivity()).getCategory().getSelectedSms(dbCategory);
        mAvailableSms = DbConnector.newInstance(getActivity()).getCategory().getAvailableSms();

        // Кнопки
        Button saveButton = (Button) v.findViewById(R.id.categoryButtonSave);
        Button cancelButton = (Button) v.findViewById(R.id.categoryButtonCancel);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbCategory dbCategory = new DbCategory(mIdCategory, mNameField.getText().toString(), mSelectedSms);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_CATEGORY, dbCategory);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.category_viewpager);
        mViewPager.setAdapter(new SmsPagerAdapter());

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.category_sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab, R.id.custom_tab_text_id);
        slidingTabLayout.setSelectedIndicatorColors(Color.argb(255, 123, 200, 43));
//        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mViewPager);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            DbSms dbSms = (DbSms)data.getSerializableExtra(DialogAddRemoveCategoryFragment.EXTRA_SMS);
            switch (requestCode) {
                case 1:
                    mSelectedSms.remove(dbSms);
                    mAvailableSms.add(dbSms);
                    break;
                default:
                    mAvailableSms.remove(dbSms);
                    mSelectedSms.add(dbSms);
                    break;
            }
            ((ListSmsAdapter)mListView.getAdapter()).notifyDataSetChanged();
        }
    }

    private class SmsPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return getResources().getString(R.string.category_sms_available);
                default:
                    return getResources().getString(R.string.category_sms_selected);
            }
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.page_sms_item, container, false);
            container.addView(v);
            mListView = (ListView)v.findViewById(R.id.list_view_sms);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    mListView = (ListView)parent;
                    ListSmsAdapter listSmsAdapter = (ListSmsAdapter)mListView.getAdapter();
                    DbSms dbSms = listSmsAdapter.arrayDbSms.get(position);

                    DialogAddRemoveCategoryFragment addCategoryFragment;
                    switch (mViewPager.getCurrentItem()) {
                        case 1:
                            addCategoryFragment = DialogAddRemoveCategoryFragment.newInstance(dbSms);
                            addCategoryFragment.setTargetFragment(EditCategoryFragment.this, DialogAddRemoveCategoryFragment.ADD_SMS);
                            break;
                        default:
                            addCategoryFragment = DialogAddRemoveCategoryFragment.newInstance(dbSms);
                            addCategoryFragment.setTargetFragment(EditCategoryFragment.this, DialogAddRemoveCategoryFragment.REMOVE_SMS);
                            break;
                    }
                    addCategoryFragment.show(fm, DIALOG_ADD_REMOVE_CATEGORY_TO_SMS);
                }
            });
            switch (position) {
                case 1:
                    mListView.setAdapter(new ListSmsAdapter(getActivity(), mAvailableSms));
                    break;
                default:
                    mListView.setAdapter(new ListSmsAdapter(getActivity(), mSelectedSms));
                    break;
            }
            registerForContextMenu(mListView);

            return v;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    private class ListSmsAdapter extends BaseAdapter {
        private ArrayList<DbSms> arrayDbSms;
        public ListSmsAdapter (Context ctx, ArrayList<DbSms> arr) {
            setArrayDbSms(arr);
        }
        public void setArrayDbSms(ArrayList<DbSms> arrayDbSms) {
            this.arrayDbSms = arrayDbSms;
        }
        public int getCount () {
            return arrayDbSms.size();
        }
        public Object getItem (int position) {
            return position;
        }

        public long getItemId (int position) {
            DbSms dbSms = arrayDbSms.get(position);
            if (dbSms != null) {
                return dbSms.getId();
            }
            return 0;
        }

        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.sms_list_item, null);
            }

            final DbSms ds = arrayDbSms.get(position);
            TextView titleTextView = (TextView)convertView.findViewById(R.id.sms_list_item_title_text_view);
            titleTextView.setText(ds.getTitleSms());
            TextView dateTextView = (TextView)convertView.findViewById(R.id.sms_list_item_text_text_view);
            dateTextView.setText(ds.getTextSms());
            TextView phoneTextView = (TextView)convertView.findViewById(R.id.sms_list_item_phone_text_view);
            phoneTextView.setText(getResources().getString(R.string.phone_prefix) + ds.getPhoneNumber());

            // Favorite button
            boolean isFavorite = ds.getPriority() != 0;
            ImageView imageView = (ImageView)convertView.findViewById(R.id.sms_list_img_favorite);
            if (isFavorite) {
                imageView.setImageResource(R.drawable.ic_action_important);
            } else {
                imageView.setImageResource(R.drawable.ic_action_not_important);
            }


//            imageButton.setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    View parentRow = (View) v.getParent();
//                    ListView listView = (ListView) parentRow.getParent();
//                    final int position = listView.getPositionForView(parentRow);
//                    ListAdapter listAdapter = (SmsAdapter)listView.getAdapter();
//                    DbSms dbSms = (DbSms)listAdapter.getItem(position);
//                    int priority = dbSms.getPriority() != 0 ? 0 : 1;
//                    DbSms newDbSms = new DbSms(dbSms.getId(), dbSms.getTitleSms(), dbSms.getTextSms(),
//                            dbSms.getPhoneNumber(), priority);
//                    DbSms dms =
//
//                    ListView listView = new ListView(getActivity(), null, null, null);
//                    listView.getPositionForView(v);
//                    DbSms dbSms = new DbSms(ds.getId(), ds.getTitleSms(), ds.getTextSms(), ds.getPhoneNumber(),
//                            );
//
//                }
//            });


            return convertView;
        }
    }

}
