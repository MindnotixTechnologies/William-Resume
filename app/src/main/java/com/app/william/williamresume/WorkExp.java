package com.app.william.williamresume;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class WorkExp extends FragmentActivity implements ActionBar.TabListener {

    TabAdapter mTabAdapter;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_exp);
        setTitle("Work Experience");
        mTabAdapter = new TabAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        actionBar.addTab(
                actionBar.newTab()
                        .setText(mTabAdapter.getPageTitle(0))
                        .setTabListener(this));
        actionBar.addTab(
                actionBar.newTab()
                        .setText(mTabAdapter.getPageTitle(1))
                        .setTabListener(this));
    }




    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    public static class TabAdapter extends FragmentPagerAdapter {
        public TabAdapter(FragmentManager fm) { super(fm); }

        @Override
        public Fragment getItem(int i){
            switch (i) {
                case 1:
                    return new OpenTextFragment();
                default:
                    return new KikFragment();
            }
        }

        @Override
        public int getCount() { return 2; }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Kik Interactive";
                default:
                    return "Open Text";
            }
        }
    }

    public static class KikFragment extends Fragment {
        private String[] mKikList;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
            mKikList = getResources() .getStringArray(R.array.kik_descrip);

            View rootView = inflater.inflate(R.layout.fragment_kik, container, false);

            ListView mList = (ListView) rootView.findViewById(R.id.kik_list);

            mList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.summary_list_item, mKikList));

            return rootView;
        }
    }

    public static class OpenTextFragment extends Fragment {
        private String[] mOpenList;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.fragment_open_text, container, false);

            ListView mList = (ListView) rootView.findViewById(R.id.open_list);

            mOpenList = getResources() .getStringArray(R.array.open_descip);

            mList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.summary_list_item, mOpenList));

            return rootView;
        }
    }
}
