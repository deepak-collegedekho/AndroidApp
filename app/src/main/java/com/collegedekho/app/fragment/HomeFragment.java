package com.collegedekho.app.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.FeedAdapter;
import com.collegedekho.app.adapter.pager.HomePagerAdapter;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.Feed;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.collegedekho.app.utils.AnalyticsUtils.SendAppEvent;


public class HomeFragment extends BaseFragment {
    private final static String TAG = "Home Fragment";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private HomePagerAdapter mHomePagerAdapter;
    private int mSelectedTabPosition = -1;
    private ArrayList<Feed> mFeedList;
    private int tuteCount = 1;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_custom_view_icon_text_tabs, container, false);
        this.mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        if(this.mHomePagerAdapter == null) {
            this.mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), getActivity());
        }else{
            this.mHomePagerAdapter.feedListRefreshed(this.mFeedList, super.mNextUrl, false);
        }
        this.mViewPager.setAdapter(this.mHomePagerAdapter);
        this.mViewPager.setOffscreenPageLimit(4);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if( getActivity() != null){
                    if(position ==0 ) {
                        ((MainActivity) getActivity()).setToolBarScrollable(true);
                    } else{
                        ((MainActivity) getActivity()).setToolBarScrollable(false);
                    }
                }
                HomeFragment.this.mSelectedTabPosition = position;
                HomeFragment.this.mMarkTabAsSelected(position);

                //events params
                Map<String, Object> eventValue = new HashMap<>();
                String val = "";
                if(getActivity() != null){
                    ((MainActivity)getActivity()).mSetCounselorMenuVisibility();
                }
                switch(position) {
                    case 0:
                    {
                        val = FeedAdapter.class.getSimpleName();
                        break;
                    }
                    case 1:
                    {
                        val = CollegesDashboard.class.getSimpleName();
                        break;
                    }
                    case 2:
                    {
                        val = InteractionDashboard.class.getSimpleName();
                        break;
                    }
                    case 3:
                    {
                        val = PrepareDashboard.class.getSimpleName();
                        break;
                    }
                }

                eventValue.put("action_where", val);
                //Events
                SendAppEvent(getContext().getString(R.string.CATEGORY_PREFERENCE), getContext().getString(R.string.ACTION_HOME), eventValue, HomeFragment.this.getContext());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.mTabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        this.mTabLayout.setupWithViewPager(this.mViewPager);
        this.mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.primaryColor));

        setupTabIcons();

        ImageView tuteImage = (ImageView)rootView.findViewById(R.id.home_tute_image);
        final View tabs = rootView.findViewById(R.id.tabs);
        boolean  isTuteCompleted = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getString(R.string.FEED_HOME_TUTE), false);
        if(!isTuteCompleted) {
            tuteImage.setVisibility(View.VISIBLE);
            tabs.setVisibility(View.GONE);

        } else {
            tuteImage.setVisibility(View.GONE);
            tabs.setVisibility(View.VISIBLE);
        }
        tuteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tuteCount == 1) {
                    ((ImageView) view).setImageDrawable(getResources().getDrawable(R.drawable.ic_home_tute2));
                       // ((ImageView) view).setImageBitmap(BitMapResize.decodeSampledBitmapFromResource(getResources(), view.getId(), view.getWidth(), view.getHeight()));
                }else  if(tuteCount == 2) {
                    ((ImageView) view).setImageDrawable(getResources().getDrawable(R.drawable.ic_home_tute3));
                    //((ImageView) view).setImageBitmap(BitMapResize.decodeSampledBitmapFromResource(getResources(), view.getId(), view.getWidth(), view.getHeight()));

                }else  if(tuteCount == 3) {
                    ((ImageView) view).setImageDrawable(getResources().getDrawable(R.drawable.ic_home_tute4));
                    //((ImageView) view).setImageBitmap(BitMapResize.decodeSampledBitmapFromResource(getResources(), view.getId(), view.getWidth(), view.getHeight()));

                    if(isAdded())
                        getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.FEED_HOME_TUTE), true).apply();
                }else{
                    view.setVisibility(View.GONE);
                    tabs.setVisibility(View.VISIBLE);
                    if(getActivity() != null) {
                        ((MainActivity)getActivity()).mShowFabCounselor();
                        getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.FEED_HOME_TUTE), true).apply();
                    }
                }
                tuteCount ++;

            }
        });

        return rootView;
    }

    /* This function marks the given index as selected and
     *
     */
    private void mMarkTabAsSelected(int position)
    {
        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity != null) {
            mainActivity.setNavigationDrawerItemSelected(position);
        }
        TabLayout.Tab tab = HomeFragment.this.mTabLayout.getTabAt(position);
        if (tab != null)
        {
            View customView =  tab.getCustomView();
            if (customView != null)
            {
                Drawable[] drawables = ((TextView)customView).getCompoundDrawables();
                Drawable drawable = Utils.ApplyThemeToDrawable(drawables[1], HomeFragment.this.getResources().getColor(R.color.primary_color));
                ((TextView)customView).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                customView.setSelected(true);
            }
        }
        for (int i = 0; i<=3; i++)
        {
            if (i == position)
                continue;
            tab = HomeFragment.this.mTabLayout.getTabAt(i);
            if (tab != null)
            {
                View customView =  tab.getCustomView();
                if (customView != null)
                {
                    Drawable[] drawables = ((TextView)customView).getCompoundDrawables();
                    Drawable drawable = Utils.ApplyThemeToDrawable(drawables[1], HomeFragment.this.getResources().getColor(R.color.tab_inactive_color));
                    ((TextView)customView).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);

                    customView.setSelected(false);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity != null) {
            // this will change back arrow to hamburger icon on toolbar
            if(mainActivity.mDrawerToggle != null){
                mainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
            }
            MainActivity.currentFragment = this;
            mainActivity.invalidateOptionsMenu();
        }

        if (this.mSelectedTabPosition <= 0) {
            this.mMarkTabAsSelected(0);
            if(mainActivity != null)
                mainActivity.setToolBarScrollable(true);
        }else {
            this.mMarkTabAsSelected(this.mSelectedTabPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        MainActivity mainActivity = (MainActivity)getActivity();
        if(mainActivity != null  && MainActivity.currentFragment != null &&
                !(MainActivity.currentFragment instanceof QnaQuestionDetailFragmentNew)) {
            mainActivity.setToolBarScrollable(false);
        }
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {
        TextView feedIcon = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.dashboard_tab_item, null);
        feedIcon.setText("Feed");
        feedIcon.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_feed, 0, 0);
        this.mTabLayout.getTabAt(0).setCustomView(feedIcon);
        this.mTabLayout.getTabAt(0).setTag(0);

        TextView collegeIcon = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.dashboard_tab_item, null);
        collegeIcon.setText("Colleges");
        collegeIcon.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_institute_grey, 0, 0);
        this.mTabLayout.getTabAt(1).setCustomView(collegeIcon);
        this.mTabLayout.getTabAt(1).setTag(1);

        TextView askChatIcon = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.dashboard_tab_item, null);
        askChatIcon.setText("Chat");
        askChatIcon.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_connect, 0, 0);
        this.mTabLayout.getTabAt(2).setCustomView(askChatIcon);
        this.mTabLayout.getTabAt(2).setTag(2);

        TextView prepareIcon = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.dashboard_tab_item, null);
        prepareIcon.setText("Prepare");
        prepareIcon.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_prepare, 0, 0);
        this.mTabLayout.getTabAt(3).setCustomView(prepareIcon);
        this.mTabLayout.getTabAt(3).setTag(3);
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

    public void feedsLoaded(ArrayList<Feed> feedList, String nextURL)
    {
        if (nextURL != null && !nextURL.isEmpty())
            super.mNextUrl = nextURL;
        if (this.mHomePagerAdapter != null && feedList != null) {
            this.mFeedList = feedList;
            this.mHomePagerAdapter.feedListLoaded(feedList, nextURL);
        }
    }
    public void feedLoadedFailed()
    {
        if (this.mHomePagerAdapter != null )
            this.mHomePagerAdapter.feedLoadedFailed();
    }

    public void feedsRefreshed(ArrayList<Feed> feedList, String nextURL, boolean hasFailed)
    {
        if (nextURL != null && !nextURL.isEmpty())
            super.mNextUrl = nextURL;
        if (this.mHomePagerAdapter != null && feedList != null) {
            this.mFeedList = feedList;
            this.mHomePagerAdapter.feedListRefreshed(feedList, nextURL, hasFailed);
        }
    }

    public void feedNextLoaded(ArrayList<Feed> feedList, String nextURL)
    {
        if (nextURL != null && !nextURL.isEmpty())
            super.mNextUrl = nextURL;
        if (this.mHomePagerAdapter != null && feedList != null) {

            if(mFeedList == null)
                mFeedList = new ArrayList<>();

            this.mFeedList.addAll(feedList);
            this.mHomePagerAdapter.feedListNextLoaded(feedList, nextURL);
        }
    }

    public void feedAction(String type, HashMap<String, String> dataMap) {
        if(this.mHomePagerAdapter != null)
            this.mHomePagerAdapter.feedAction(type, dataMap);
    }

    public void updateUserInfo() {
        if (this.mHomePagerAdapter != null)
            this.mHomePagerAdapter.updateUserProfile();
    }

    public void updateUserYearlyExamSummary(ExamSummary examSummary) {
        if (this.mHomePagerAdapter != null)
            this.mHomePagerAdapter.updateUserYearlyExamSummary(examSummary);
    }

    public void setSelectedPage(int position)
    {
        if ( position >=0){
           mMarkTabAsSelected(position);
            this.mViewPager.setCurrentItem(position);
        }
    }

    public int  getSelectedPage() {
        return mViewPager != null?mViewPager.getCurrentItem():0;
    }

    public void updateExamSummary(ExamSummary updateExamSummary) {
        if (this.mHomePagerAdapter != null)
            this.mHomePagerAdapter.updateExamSummary(updateExamSummary);
    }

    public void updateExamsList(ArrayList<ProfileExam> yearly_exams) {
        if (this.mHomePagerAdapter != null)
            this.mHomePagerAdapter.updateExamsList(yearly_exams);
    }

    public void removeProfileCompletionLayout() {
        if (this.mHomePagerAdapter != null)
            this.mHomePagerAdapter.removeProfileCompletionLayout();
    }
}
