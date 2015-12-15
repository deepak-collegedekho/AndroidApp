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
import com.collegedekho.app.entities.Chapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bashir on 14/12/15.
 */
public class CalendarFragment extends BaseFragment {
    private RecyclerView recyclerView;
    // how many days to show, defaults to six weeks, 42 days
    private static int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate;

    private GridLayoutManager layoutManager;
    private static ArrayList<Chapters> chaptersList;
    private static CalendarAdapter.OnCalendarItemSelectListener mListener;
    int offSet=0;
    public static CalendarFragment newInstance(ArrayList<Chapters> chapters, CalendarAdapter.OnCalendarItemSelectListener listener) {
        chaptersList = chapters;
        Bundle args = new Bundle();
        mListener = listener;
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
        recyclerView = (RecyclerView) view.findViewById(R.id.calendar_recycler);
        layoutManager = new GridLayoutManager(getActivity(), 7);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
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
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        /*if (monthBeginningCell > 3) {
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
        }*/
        // update grid
        CalendarAdapter calendarAdapter = new CalendarAdapter(getActivity(), cells, mListener, offSet);
        recyclerView.setAdapter(calendarAdapter);
    }


}
