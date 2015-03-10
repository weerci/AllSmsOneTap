package com.production.kriate.allsmsonetap.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
 * Created by dima on 11.03.2015.
 */
public class PageSmsFragment extends Fragment {
    private static final int SMS_UPDATE = 0;
    private static final int SMS_INSERT = 1;
    private final int REQUEST_SEND_SMS = 3;
    private final static String DIALOG_SEND_SMS = "com.production.kriate.allsmsonetap.fragments.send_sms";
    private final static String CURRENT_PAGE_ID = "com.production.kriate.allsmsonetap.fragments.current_page_id";
    private ViewPager mViewPager;
    private ArrayList<DbCategory> mCategoryList;


    public static PageSmsFragment newInstance(int indexPage) {
        Bundle args = new Bundle();
        args.putSerializable(CURRENT_PAGE_ID, indexPage);
        PageSmsFragment fragment = new PageSmsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCategoryList = new ArrayList<>();
        mCategoryList.add(new DbCategory(0, getResources().getString(R.string.action_all), null));
        mCategoryList.add(new DbCategory(0, getResources().getString(R.string.action_favorite), null));
        mCategoryList.addAll(DbConnector.newInstance(getActivity()).getCategory().selectAll());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_sms_layout, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SmsPagerAdapter());
        int currentPage = (int)getArguments().getSerializable(PageSmsFragment.CURRENT_PAGE_ID);
        mViewPager.setCurrentItem(currentPage, true);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(mViewPager);
    }

    // region + Adapters
    private class SmsPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mCategoryList.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mCategoryList.get(position).getName();
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.page_sms_item, container, false);
            container.addView(v);
            final ListView listView = (ListView)v.findViewById(R.id.list_view_sms);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    SmsListAdapter adapter = (SmsListAdapter)listView.getAdapter();
                    DbSms dbSms = adapter.arrayDbSms.get(position);
                    SmsSendFragment dialog = SmsSendFragment.newInstance(dbSms);
                    dialog.setTargetFragment(PageSmsFragment.this, REQUEST_SEND_SMS);
                    dialog.show(fm, DIALOG_SEND_SMS);
                }
            });
            ArrayList<DbSms> smsList;
            switch (position) {
                case 0:
                    smsList = DbConnector.newInstance(getActivity()).getSms().selectAll();
                    break;
                case 1:
                    smsList = DbConnector.newInstance(getActivity()).getSms().selectFavorite();
                    break;
                default:
                    smsList = DbConnector.newInstance(getActivity()).getCategory().getSelectedSms(mCategoryList.get(position));
                    break;
            }

            SmsListAdapter adapter = new SmsListAdapter(smsList);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);

            return v;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    private class SmsListAdapter extends BaseAdapter {
        private ArrayList<DbSms> arrayDbSms;

        public SmsListAdapter (ArrayList<DbSms> arr) {
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

            boolean isFavorite = ds.getPriority() != 0;
            ImageView imageView = (ImageView)convertView.findViewById(R.id.sms_list_img_favorite);
            if (isFavorite) {
                imageView.setImageResource(R.drawable.ic_action_important);
            } else {
                imageView.setImageResource(R.drawable.ic_action_not_important);
            }

            return convertView;
        }
    }
    // endregion
}
