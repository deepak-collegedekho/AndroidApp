package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CalendarAdapter;
import com.collegedekho.app.adapter.UserAlertItemDescriptionAdapter;
import com.collegedekho.app.adapter.UserAlertsMonthAdapter;
import com.collegedekho.app.entities.MyAlertDateDescription;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by Bashir on 22/12/15.
 */
public class UserAlertDetailsFragment extends BaseFragment implements UserAlertsMonthAdapter.OnCalendarItemSelectListener {

    private RecyclerView calendarRecyclerView;
    private RecyclerView detailsRecyclerView;
    // how many days to show, defaults to six weeks, 42 days
    private static int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;
    int offSet = 0;

    // current displayed month
    private Calendar currentDate;
    private LinearLayout.LayoutParams params;
    private GridLayoutManager calendarLayoutManager;
    private LinearLayoutManager detailsLayoutManager;
    LinkedHashMap<String, String> yearCalendar;
    private static CalendarAdapter.OnCalendarItemSelectListener mListener;
    private ArrayList<MyAlertDateDescription> dates;
    int month;
    int year;

    public static UserAlertDetailsFragment newInstance(ArrayList<MyAlertDateDescription> dates, int month, int year) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("dates_list", dates);
        args.putInt("month", month - 1);
        args.putInt("year", year);

        UserAlertDetailsFragment fragment = new UserAlertDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.dates = bundle.getParcelableArrayList("dates_list");
            this.month = bundle.getInt("month");
            this.year = bundle.getInt("year");

        }
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
        detailsRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_details_recycler);
        calendarLayoutManager = new GridLayoutManager(getActivity(), 7);
        detailsLayoutManager = new LinearLayoutManager(getActivity());
        detailsLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        detailsRecyclerView.setLayoutManager(detailsLayoutManager);

        calendarLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calendarRecyclerView.setLayoutManager(calendarLayoutManager);
        currentDate = Calendar.getInstance();
        Bundle bundle = getArguments();
        if (bundle != null) {
            currentDate.set(Calendar.MONTH, month);
            currentDate.set(Calendar.YEAR, year);
        }
        params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight=0.9f;
        detailsRecyclerView.setLayoutParams(params);
        updateCalendar(currentDate);
    }


    public void updateCalendar(Calendar calendar) {
        ArrayList<Date> cells = new ArrayList<Date>();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        UserAlertsMonthAdapter calendarAdapter = new UserAlertsMonthAdapter(getActivity(), cells, this, this.dates);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @Override
    public void onItemSelect(int position, int startPosition, int endPosition, String itemKey) {
        if(itemKey==null){
            return;
        }
        int count = dates.size();
        ArrayList<MyAlertDateDescription> details = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MyAlertDateDescription description = dates.get(i);
            if (description.getDate().equals(itemKey)) {
                details.add(description);
            }
        }
        UserAlertItemDescriptionAdapter alertItemDescriptionAdapter=new UserAlertItemDescriptionAdapter(getActivity(),details);
        detailsRecyclerView.setAdapter(alertItemDescriptionAdapter);
    }
}
