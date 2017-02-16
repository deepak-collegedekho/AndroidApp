package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.CalendarItemDetailsAdapter;
import com.collegedekho.app.adapter.CalendarPagerAdapter;
import com.collegedekho.app.entities.ChapterDetails;
import com.collegedekho.app.entities.Chapters;
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
public class CalendarParentFragment extends BaseFragment implements ViewPager.OnPageChangeListener,CalendarItemDetailsAdapter.OnItemStateChangeListener {
    private CalendarPagerAdapter mPagerAdapter;
    private Button btnSubmitCalendar;
    private int numPages = 1;
    private OnSubmitCalendarData mListener;
    private ArrayList<Chapters> mChapterList;
    private LinkedHashMap<String, ArrayList<ChapterDetails>> chaptersDetailsList = new LinkedHashMap<>();
    public static CalendarParentFragment newInstance(ArrayList<Chapters> chapterList) {

        Bundle args = new Bundle();
        CalendarParentFragment fragment = new CalendarParentFragment();
        args.putParcelableArrayList("chapters_list",chapterList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        if(args!=null){
            mChapterList=args.getParcelableArrayList("chapters_list");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar_parent, container, false);
        ((ViewPager.LayoutParams) (rootView.findViewById(R.id.calendar_pager_header)).getLayoutParams()).isDecor = true;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCalendar();
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        btnSubmitCalendar=(Button)view.findViewById(R.id.btn_submit_calendar);

        mPagerAdapter = new CalendarPagerAdapter(getChildFragmentManager(), numPages,  this);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        btnSubmitCalendar.setOnClickListener(this);
    }

    private void initCalendar() {
        if (mChapterList == null || mChapterList.isEmpty()) {
            return;
        }
        Chapters chapters = mChapterList.get(0);
        ArrayList<ChapterDetails> chapterDetailsList = chapters.getChapters();
        if (chapterDetailsList == null || chapterDetailsList.isEmpty() || chapters.getDays_left() < 0) {
            numPages=1;
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
        String examDate = chapters.getExam_date();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate;
        try {
            endDate = form.parse(examDate);
        } catch (ParseException e) {
            e.printStackTrace();
            endDate = new Date();
        }
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        Calendar startCal = Calendar.getInstance();
        Date startDate = new Date();
        startCal.setTime(startDate);
        int diffYear = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
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
            if (context instanceof MainActivity)
                this.mListener = (OnSubmitCalendarData) context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnSubmitCalendarData");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;

    }
    public void submitCalendarData() {
        ArrayList<Integer>chaptersArray = new ArrayList<>();
        ArrayList<Integer>subjectsArray = new ArrayList<>();
        JSONObject object=new JSONObject();
        boolean isReadyToSubmit=false;
        if (mChapterList==null || mChapterList.isEmpty()){
            return;
        }
        Chapters chapters = mChapterList.get(0);
        String exam_id = chapters.getYearly_exam_id();
        if (chaptersDetailsList != null && !chaptersDetailsList.isEmpty()) {

            for (String key : chaptersDetailsList.keySet()) {
                ArrayList<ChapterDetails> chapterDetailses = chaptersDetailsList.get(key);
                if (chapterDetailses != null && !chapterDetailses.isEmpty()) {
                    for (ChapterDetails chapterDetails : chapterDetailses) {
                        if (chapterDetails.isSelected()) {
                            subjectsArray.add(Integer.valueOf(chapterDetails.getSubject_id()));
                            chaptersArray.add(Integer.valueOf(chapterDetails.getYearly_exam_chapter_id()));
                        }
                    }
                }
            }
            try {
                if (!chaptersArray.isEmpty() && !subjectsArray.isEmpty()){
                    isReadyToSubmit=true;
                }
                object.putOpt("chapters",new JSONArray(chaptersArray));
                object.putOpt("subjects",new JSONArray(subjectsArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(mListener!=null && isReadyToSubmit)
            mListener.onSubmitCalendarData(object,"yearly-exams/"+exam_id+"/calendar/");
        }
    }

    public void updateCalander(ArrayList<Chapters> chapterList){
        this.mChapterList = chapterList;
        initCalendar();
        if(mPagerAdapter != null) {
            btnSubmitCalendar.setEnabled(false);
            getArguments().putParcelableArrayList("chapters_list",chapterList);
            mPagerAdapter.setNumberOfPages(numPages);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit_calendar:
                submitCalendarData();
                break;
        }
    }

    @Override
    public void OnStateChanged(boolean state) {
        if(state){
            btnSubmitCalendar.setEnabled(true);
        }else{
            btnSubmitCalendar.setEnabled(false);
        }
    }

    public interface OnSubmitCalendarData{
        void onSubmitCalendarData(JSONObject object,String url);
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
