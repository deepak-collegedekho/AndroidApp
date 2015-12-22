package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.CalendarPagerAdapter;
import com.collegedekho.app.adapter.UserAlertsPagerAdapter;
import com.collegedekho.app.entities.ChapterDetails;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.MyAlertDate;
import com.collegedekho.app.resource.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Bashir on 14/12/15.
 */
public class UserAlertsParentFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private UserAlertsPagerAdapter mPagerAdapter;
    private ViewPager viewPager;
    private int numPages = 1;
    int currentPosition;
    private ArrayList<MyAlertDate> alertDatesList;

    public static UserAlertsParentFragment newInstance(int currentPosition,ArrayList<MyAlertDate> alertDatesList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("alert_dates_list",alertDatesList);
        args.putInt("position",currentPosition);
        UserAlertsParentFragment fragment = new UserAlertsParentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if (bundle!=null) {
            this.alertDatesList =bundle.getParcelableArrayList("alert_dates_list");
            this.currentPosition=bundle.getInt("position");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.calendar_parent_fragment_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        mPagerAdapter = new UserAlertsPagerAdapter(getChildFragmentManager(), numPages,alertDatesList);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(currentPosition);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }


    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPause() {
        super.onPause();
        Constants.READY_TO_CLOSE = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Constants.READY_TO_CLOSE = false;
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.currentFragment = this;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity) {
//                this.mListener = (OnSubmitCalendarData) context;
            }
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnSubmitCalendarData");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        this.mListener = null;

    }
}
