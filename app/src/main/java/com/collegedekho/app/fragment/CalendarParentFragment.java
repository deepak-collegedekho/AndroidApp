package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CalendarAdapter;
import com.collegedekho.app.adapter.CalendarPagerAdapter;
import com.collegedekho.app.entities.ChapterDetails;
import com.collegedekho.app.entities.Chapters;

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
public class CalendarParentFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private CalendarPagerAdapter mPagerAdapter;
    private ViewPager viewPager;
    private int numPages = 1;
    private static ArrayList<Chapters> mChapterList;
    public static HashMap<String,String>subjectsMap;
    public static LinkedHashMap<String,String>yearCalendar=new LinkedHashMap<>();
    public static LinkedHashMap<String,ArrayList<ChapterDetails>>chaptersDetailsList=new LinkedHashMap<>();

    public static CalendarParentFragment newInstance(ArrayList<Chapters> chapterList) {

        Bundle args = new Bundle();
        mChapterList = chapterList;
        CalendarParentFragment fragment = new CalendarParentFragment();
        fragment.setArguments(args);
        return fragment;
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
        initCalendar();
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        mPagerAdapter = new CalendarPagerAdapter(getChildFragmentManager(), numPages);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    private void initCalendar() {
        Chapters chapters = mChapterList.get(0);
        String examDate = chapters.getExam_date();
        ArrayList<ChapterDetails> chapterDetailsList = chapters.getChapters();
        if (chapterDetailsList == null || chapterDetailsList.isEmpty()) {
            return;
        }
        subjectsMap = new LinkedHashMap<>();
        for (ChapterDetails chapterDetails : chapterDetailsList) {
            String id = subjectsMap.get(chapterDetails.getSubject_id());
            if (id == null) {
                subjectsMap.put(chapterDetails.getSubject_id(), chapterDetails.getDays_to_complete());
            } else {
                float total = Float.valueOf(subjectsMap.get(chapterDetails.getSubject_id())) + Float.valueOf(chapterDetails.getDays_to_complete());
                subjectsMap.put(chapterDetails.getSubject_id(), String.valueOf(total));
            }
                ArrayList<ChapterDetails> detailsList = chaptersDetailsList.get(chapterDetails.getSubject_id());
                if (detailsList == null) {
                    detailsList = new ArrayList<>();
                    detailsList.add(chapterDetails);
                    chaptersDetailsList.put(chapterDetails.getSubject_id(), detailsList);
                } else {
                    chaptersDetailsList.get(chapterDetails.getSubject_id()).add(chapterDetails);
                }
        }
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        Date today=new Date();
        for(String key:subjectsMap.keySet()){
            int dayCount=(int)Math.ceil(Double.valueOf(CalendarParentFragment.subjectsMap.get(key)));
                for (int i=0;i<dayCount;i++){
                    calendar.add(Calendar.DAY_OF_YEAR,1);
                    String day_key=String.valueOf(calendar.get(Calendar.YEAR)+"_"+String.valueOf(calendar.get(Calendar.DAY_OF_YEAR)));
                    String day_value=yearCalendar.get(day_key);
                    if(day_value==null) {
                        yearCalendar.put(day_key, key);
                    }else if(!day_value.contains(key)){
                        yearCalendar.put(day_key, day_value+","+key);
                    }
                }
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_YEAR,-1);

        }
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate;
        Date startDate = new Date();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        try {
            endDate = form.parse(examDate);
        } catch (ParseException e) {
            e.printStackTrace();
            endDate = new Date();
        }
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int diffYear = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
//        long diff = startCal.getTimeInMillis() - endCal.getTimeInMillis();
//        long days = diff / (24 * 60 * 60 * 1000);
        numPages = diffMonth + 1;
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
}
