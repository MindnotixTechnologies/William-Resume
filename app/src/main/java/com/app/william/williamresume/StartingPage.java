package com.app.william.williamresume;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;


public class StartingPage extends Activity {
    private String[] mSectionnames;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private FrameLayout mFrameLayout;
    private Fragment currentTab;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);

        mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mSectionnames = getResources().getStringArray(R.array.section_names);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mActionBar = getActionBar();

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mSectionnames));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        currentTab = new SummaryFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.content_frame, currentTab).commit();

        mDrawerList.setItemChecked(0, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_starting_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Intent intent;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment nFragment;
        nFragment = new SummaryFragment();

        if(position == 0) {
            nFragment = new SummaryFragment();
        } else if(position == 1){
            intent = new Intent(this, WorkExp.class);
            startActivity(intent);
        } else if (position == 2){
            nFragment = new ProjectsFragment();
        } else if (position ==3){
            nFragment = new TechnicalFragment();
        } else {
            nFragment = new InterestsFragment();
        }

        if(position != 1) {
            transaction.replace(R.id.content_frame, nFragment);
            transaction.commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mSectionnames[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    public static class SummaryFragment extends Fragment {
        private String[] mSummary;

        public SummaryFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            mSummary = getResources().getStringArray(R.array.summary_list);

            View v = inflater.inflate(R.layout.fragment_summary, container, false);

            ListView summaryList = (ListView) v.findViewById(R.id.summary_list);

            summaryList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.summary_list_item, mSummary));

            return v;
        }
    }

    public static class TechnicalFragment extends Fragment {
        private String[] mProficientList;
        private String[] mFamiliarList;

        public TechnicalFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_technical_skills, container, false);
            mProficientList = getResources().getStringArray(R.array.proficient_list);
            mFamiliarList = getResources().getStringArray(R.array.familiar_list);

            ListView profiList = (ListView) v.findViewById(R.id.proficient_list);
            ListView familiList = (ListView) v.findViewById(R.id.familiar_list);

            profiList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.skills_list_items, mProficientList));
            familiList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.skills_list_items, mFamiliarList));

            return v;
        }
    }

    public static class InterestsFragment extends Fragment {
        private ListView mInterestsList;
        private String[] mInterestsListNames;

        public InterestsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_interests, container, false);

            mInterestsList = (ListView) rootView.findViewById(R.id.interests_list);
            mInterestsListNames = getResources().getStringArray(R.array.list_of_interests);

            mInterestsList.setAdapter(new CardAdapter(getActivity(), R.layout.interests_list_item, mInterestsListNames));

            return rootView;
        }
    }

    public static class ProjectsFragment extends Fragment {

        private ListView mProjectsList;
        private String[] mProjectsListTitles;
        private String[] mProjectsListInfos;

        public ProjectsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_interests, container, false);

            mProjectsList = (ListView) rootView.findViewById(R.id.interests_list);
            mProjectsListTitles = getResources().getStringArray(R.array.list_projects);
            mProjectsListInfos = getResources().getStringArray(R.array.list_projects_desrip);

            mProjectsList.setAdapter(new ProjectsAdapter(getActivity(), R.layout.project_list_item, mProjectsListTitles, mProjectsListInfos));

            return rootView;
        }
    }

    public static class CardAdapter extends ArrayAdapter<String>{
        private static String[] listInterests;
        private int mResource;

        public CardAdapter(Context context, int resource, String[] object){


            super(context, resource, object);

            listInterests = object;
            mResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;

            if(v == null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.interests_list_item, null);
            }

            String s = listInterests[position];

            if(s != null){
                TextView mTextView = (TextView) v.findViewById(R.id.info_text);

                mTextView.setText(listInterests[position]);
            }



            return v;
        }
    }

    public static class ProjectsAdapter extends ArrayAdapter<String>{
        private static String[] listProject;
        private static String[] listProjectDescrip;
        private int mResource;

        public ProjectsAdapter(Context context, int resource, String[] object, String[] object2){


            super(context, resource, object);

            listProject = object;
            listProjectDescrip = object2;
            mResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;

            if(v == null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.project_list_item, null);
            }

            String s = listProject[position];

            if(s != null){
                TextView mTitle = (TextView) v.findViewById(R.id.project_title);
                TextView mText = (TextView) v.findViewById(R.id.project_info);
                mTitle.setText(listProject[position]);
                mText.setText(listProjectDescrip[position]);

            }



            return v;
        }
    }
}