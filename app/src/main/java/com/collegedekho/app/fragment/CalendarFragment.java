package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CalendarAdapter;
import com.collegedekho.app.adapter.CalendarItemDetailsAdapter;
import com.collegedekho.app.adapter.SyllabusChapterListAdapter;
import com.collegedekho.app.entities.ChapterDetails;
import com.collegedekho.app.entities.Chapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Bashir on 14/12/15.
 */
public class CalendarFragment extends BaseFragment implements CalendarAdapter.OnCalendarItemSelectListener {
    private RecyclerView calendarRecyclerView;
    private RecyclerView detailsRecyclerView;
    // how many days to show, defaults to six weeks, 42 days
    private static int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate;

    private GridLayoutManager calendarLayoutManager;
    private LinearLayoutManager detailsLayoutManager;
    private static ArrayList<Chapters> chaptersList;
    private static CalendarAdapter.OnCalendarItemSelectListener mListener;
    int offSet=0;
    public static CalendarFragment newInstance(ArrayList<Chapters> chapters) {
        chaptersList = chapters;
        Bundle args = new Bundle();
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootViewGroup = (ViewGroup) inflater.inflate(R.layout.calendar_view_layout, container, false);
        return rootViewGroup;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_recycler);
        detailsRecyclerView=(RecyclerView)view.findViewById(R.id.calendar_details_recycler);
        calendarLayoutManager = new GridLayoutManager(getActivity(), 7);

        detailsLayoutManager=new LinearLayoutManager(getActivity());
        detailsLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        detailsRecyclerView.setLayoutManager(detailsLayoutManager);

        calendarLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calendarRecyclerView.setLayoutManager(calendarLayoutManager);
        currentDate=Calendar.getInstance();
        offSet=getArguments().getInt("id");
        currentDate.add(Calendar.MONTH,offSet);

        updateCalendar(currentDate);

    }

    public void updateCalendar(Calendar calendar) {
        ArrayList<Date> cells = new ArrayList<Date>();
//        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
      /*  while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }*/

        if (monthBeginningCell > 3) {
            // fill cells (42 days calendar as per our business logic)
            DAYS_COUNT = 42;
            while (cells.size() < DAYS_COUNT) {
                cells.add(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } else {
            DAYS_COUNT = 35;
            while (cells.size() < DAYS_COUNT) {
                cells.add(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        // update grid
        CalendarAdapter calendarAdapter = new CalendarAdapter(getActivity(), cells, this, offSet);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

//SyllabusChapterListAdapter

    @Override
    public void onItemSelect(int position, int startPosition,int endPosition,String itemKey) {
//        int effectivePosition=(position-startPosition)+(currentPage*42);
//        if(effectivePosition>-1 && mChapterList.get(0).getChapters().size()>=effectivePosition) {
////            Log.e("DEBUG",mChapterList.get(0).getChapters().get(effectivePosition).getChapter_name());
//
//        }
        Log.e("DEBUG", "Position " + position + " Active Position " + startPosition+" End position "+endPosition+" Effective Position "+itemKey);
        String keys=CalendarParentFragment.yearCalendar.get(itemKey);
                if(keys!=null) {
                    String[]subject_keys=keys.split(",");
                    ArrayList<ChapterDetails> chaptersList = new ArrayList<>();
                    for (int i = 0; i < subject_keys.length; i++) {
                        ArrayList<ChapterDetails> chapters = CalendarParentFragment.chaptersDetailsList.get(subject_keys[i]);
                        if (chapters != null && !chapters.isEmpty()) {
                            chaptersList.add(chapters.get(0));
                        }
                    }
                    CalendarItemDetailsAdapter calendarItemDetailsAdapter = new CalendarItemDetailsAdapter(getActivity(), chaptersList);
                    detailsRecyclerView.setAdapter(calendarItemDetailsAdapter);
                }

    }

}
