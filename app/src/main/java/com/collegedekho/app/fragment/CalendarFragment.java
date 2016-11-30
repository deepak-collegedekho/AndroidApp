package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CalendarAdapter;
import com.collegedekho.app.adapter.CalendarItemDetailsAdapter;
import com.collegedekho.app.entities.ChapterDetails;
import com.collegedekho.app.entities.Chapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Bashir on 14/12/15.
 */
public class CalendarFragment extends BaseFragment implements CalendarAdapter.OnCalendarItemSelectListener {
    private RecyclerView calendarRecyclerView;
    private RecyclerView detailsRecyclerView;
    private CalendarItemDetailsAdapter calendarItemDetailsAdapter;
    private LinkedHashMap<String, String> mYearCalendar;
    private LinkedHashMap<String, ArrayList<ChapterDetails>> chaptersDetailsList = new LinkedHashMap<>();
    // current displayed month
    private Calendar currentDate;
    private static CalendarItemDetailsAdapter.OnItemStateChangeListener mListener;
    int offSet = 0;
    private ArrayList<Chapters> mChapterList;


    public static CalendarFragment newInstance(CalendarItemDetailsAdapter.OnItemStateChangeListener listener) {
        mListener=listener;
        Bundle args = new Bundle();
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args=getParentFragment().getArguments();
        if(args!=null){
            mChapterList=args.getParcelableArrayList("chapters_list");
            initCalendar();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_recycler);
        detailsRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_details_recycler);
        GridLayoutManager calendarLayoutManager = new GridLayoutManager(getActivity(), 7);

        LinearLayoutManager detailsLayoutManager = new LinearLayoutManager(getActivity());
        detailsLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        detailsRecyclerView.setLayoutManager(detailsLayoutManager);

        calendarLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calendarRecyclerView.setLayoutManager(calendarLayoutManager);
        currentDate = Calendar.getInstance();
        offSet = getArguments().getInt("id");
        currentDate.add(Calendar.MONTH, offSet);
        updateCalendar(currentDate);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(calendarItemDetailsAdapter!=null){
                calendarItemDetailsAdapter.notifyDataSetChanged();
            }
        }
    }

    public void updateCalendar(Calendar calendar) {
        ArrayList<Date> cells = new ArrayList<>();
//        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // how many days to show, defaults to six weeks, 42 days
        int DAYS_COUNT = 42;
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        CalendarAdapter calendarAdapter = new CalendarAdapter(getActivity(), cells, this, mYearCalendar);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    @Override
    public void onItemSelect(String itemKey) {
        ArrayList<ChapterDetails> chaptersList = new ArrayList<>();
        if (itemKey != null) {
            String keys = mYearCalendar.get(itemKey);
            if (keys != null) {
                String[] subject_keys = keys.split(",");
                for (String subKey:subject_keys) {
                    ArrayList<ChapterDetails> chapters = chaptersDetailsList.get(subKey);
                    if (chapters != null && !chapters.isEmpty()) {
                        chaptersList.add(chapters.get(0));
                    }
                }
            }
            calendarItemDetailsAdapter = new CalendarItemDetailsAdapter(getActivity(), chaptersList, mListener);
            detailsRecyclerView.setAdapter(calendarItemDetailsAdapter);
        }
    }

    private void initCalendar() {
        if (mChapterList == null || mChapterList.isEmpty()) {
            return;
        }
        mYearCalendar=new LinkedHashMap<>();
        Chapters chapters = mChapterList.get(0);
        ArrayList<ChapterDetails> chapterDetailsList = chapters.getChapters();
        if (chapterDetailsList == null || chapterDetailsList.isEmpty()) {
            return;
        }

        HashMap<String, String> subjectsMap = new LinkedHashMap<>();
        for (ChapterDetails chapterDetails : chapterDetailsList) {
            String subjectId = chapterDetails.getSubject_id();
            String subjectValue = subjectsMap.get(subjectId);
            if (subjectValue == null) {
                subjectsMap.put(subjectId, chapterDetails.getDays_to_complete());
            } else {
                float total = Float.valueOf(subjectValue) + Float.valueOf(chapterDetails.getDays_to_complete());
                subjectsMap.put(subjectId, String.valueOf(total));
            }
            ArrayList<ChapterDetails> detailsList = chaptersDetailsList.get(subjectId);
            if (detailsList == null) {
                detailsList = new ArrayList<>();
                detailsList.add(chapterDetails);
                chaptersDetailsList.put(subjectId, detailsList);
            } else {
                detailsList.add(chapterDetails);
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date today = new Date();
        for (String key : subjectsMap.keySet()) {
            int dayCount = (int) Math.ceil(Double.valueOf(subjectsMap.get(key)));
            for (int i = 0; i < dayCount; i++) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                String day_key = String.valueOf(calendar.get(Calendar.YEAR) + "_" + String.valueOf(calendar.get(Calendar.DAY_OF_YEAR)));
                String day_value = mYearCalendar.get(day_key);
                if (day_value == null) {
                    mYearCalendar.put(day_key, key);
                } else if (!day_value.contains(key)) {
                    mYearCalendar.put(day_key, day_value + "," + key);
                }
            }
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
    }

    public void updateData( ){
        Bundle args=getParentFragment().getArguments();
        if(args!=null){
            mChapterList=args.getParcelableArrayList("chapters_list");
            initCalendar();
            updateCalendar(currentDate);
        }
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
}
