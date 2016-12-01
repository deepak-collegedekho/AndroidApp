package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.HomePagerAdapter;

import java.util.ArrayList;

public class DashboardFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private HomePagerAdapter mDashBoardAdapter;

    public static DashboardFragment newInstance() {

        Bundle args = new Bundle();

        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_custom_view_icon_text_tabs, container, false);
        this.mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        this.mDashBoardAdapter = new HomePagerAdapter(getFragmentManager(), (MainActivity) this.getActivity());
        this.mViewPager.setAdapter(this.mDashBoardAdapter);

        this.mTabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        this.mTabLayout.setupWithViewPager(this.mViewPager);

        setupTabIcons();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity != null) {
            mainActivity.currentFragment = this;
        }
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {
        TextView feedIcon = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.dashboard_tab_item, null);
        feedIcon.setText("Feed");
        feedIcon.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_news, 0, 0);
        mTabLayout.getTabAt(0).setCustomView(feedIcon);

        TextView collegeIcon = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.dashboard_tab_item, null);
        collegeIcon.setText("Colleges");
        collegeIcon.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_institute, 0, 0);
        mTabLayout.getTabAt(1).setCustomView(collegeIcon);

        TextView askChatIcon = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.dashboard_tab_item, null);
        askChatIcon.setText("Ask & Chat");
        askChatIcon.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_connect, 0, 0);
        mTabLayout.getTabAt(2).setCustomView(askChatIcon);

        TextView prepareIcon = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.dashboard_tab_item, null);
        prepareIcon.setText("Prepare");
        prepareIcon.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_prepare, 0, 0);
        mTabLayout.getTabAt(3).setCustomView(prepareIcon);
    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }

    public void feedsLoaded(ArrayList feedList, String nextURL)
    {
        if (this.mDashBoardAdapter != null && feedList != null)
            this.mDashBoardAdapter.feedListLoaded(feedList, nextURL);
    }

    public void feedsRefreshed(ArrayList feedList, String nextURL)
    {
        if (this.mDashBoardAdapter != null && feedList != null)
            this.mDashBoardAdapter.feedListRefreshed(feedList, nextURL);
    }

    public void feedNextLoaded(ArrayList feedList, String nextURL)
    {
        if (this.mDashBoardAdapter != null && feedList != null)
            this.mDashBoardAdapter.feedListNextLoaded(feedList, nextURL);
    }
}
