package com.collegedekho.app.fragment.profileBuilding;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ProfileBuildingExamAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.listener.ExamOnQueryListener;
import com.collegedekho.app.listener.ExamSearchCloseListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.ProfileMacro;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sureshsaini on 20/4/17.
 */

public class ExamsSelectionFragment extends BaseProfileBuildingFragment {
    private static final String TAG = ExamsSelectionFragment.class.getSimpleName();
    private static final String PARAM1 = "PARAM1";
    private View mRootView;
    private String mEventCategory = "";
    private String mEventAction = "";
    private HashMap<String, Object> mEventValue = new HashMap<>();
    private TextView mSkipButton;
    private RecyclerView mStreamRecyclerView;
    private ProfileBuildingExamAdapter mExamAdapter;
    private ExamOnQueryListener cExamQueryListener;
    private ArrayList<Exam> mStreamExamList = new ArrayList<>();
    private SearchView mExamSearchView;
    private Animation animationFromTop;
    private Animation animationFromBottom;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param streamList
     * @return A new instance of fragment LevelSelectionFragment.
     */
    public static ExamsSelectionFragment newInstance(ArrayList<Exam> streamList) {
        ExamsSelectionFragment fragment = new ExamsSelectionFragment();
        if (streamList != null) {
            Bundle args = new Bundle();
            args.putParcelableArrayList(PARAM1, streamList);
            fragment.setArguments(args);
        }
        return fragment;
    }

    public ExamsSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            ArrayList<Exam> streamList = args.getParcelableArrayList(PARAM1);
            if (streamList != null) {
                mStreamExamList.clear();
                mStreamExamList.addAll(streamList);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRootView = inflater.inflate(R.layout.fragment_exams_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        animationFromTop = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_from_top);
        animationFromTop.setDuration(Constants.ANIM_SHORTEST_DURATION);
        animationFromBottom = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_from_bottom);
        animationFromBottom.setDuration(Constants.ANIM_SHORTEST_DURATION);
               // set current level education
        TextView currentLevelTxtView = (TextView) mRootView.findViewById(R.id.user_education_level);
        int currentLevelId = MainActivity.mProfile.getCurrent_level_id();
        if (currentLevelId == ProfileMacro.LEVEL_TWELFTH || currentLevelId == ProfileMacro.LEVEL_TENTH) {
            currentLevelTxtView.setText(getString(R.string.school));
        } else if (currentLevelId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
            currentLevelTxtView.setText(getString(R.string.college));
        } else {
            currentLevelTxtView.setText(getString(R.string.pg_college));
        }

        TextView  currentStreamTxtView = (TextView)mRootView.findViewById(R.id.user_education_stream);
        currentStreamTxtView.setText(MainActivity.mProfile.getCurrent_stream_name());

        mStreamRecyclerView = (RecyclerView)view.findViewById(R.id.user_education_recycler_view);
        mStreamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mExamAdapter = new ProfileBuildingExamAdapter(getActivity(),null, mStreamExamList);
        mStreamRecyclerView.setAdapter(mExamAdapter);

        cExamQueryListener = new ExamOnQueryListener(mStreamExamList,null,mRootView.findViewById(R.id.user_education_next_button_layout));
        mExamSearchView = (SearchView) view.findViewById(R.id.user_exam_search_view);
        mExamSearchView.setOnQueryTextListener(cExamQueryListener);
        mExamSearchView.setQuery("", false);
        mExamSearchView.setOnCloseListener(new ExamSearchCloseListener(null,mRootView.findViewById(R.id.user_education_next_button_layout)));


        super.initIntituesCountViews(view);
        int instituteCount = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE)
                .getInt(getString(R.string.pref_stream_institute_count), 0);
        super.setInstituteCount(String.valueOf(instituteCount));

        view.findViewById(R.id.user_education_skip_button).setOnClickListener(this);
        view.findViewById(R.id.user_education_level_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_stream_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_next_button).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            MainActivity.currentFragment = this;
        }
        checkStreamExamList();
    }

    private void checkStreamExamList(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(mStreamExamList.isEmpty()) {
                    EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_STREAM_YEARLY_EXAMS, null, null));
                }else{
                    showNextButton();
                }
            }
        },300);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId())
        {
            case R.id.user_education_skip_button:
                this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
                this.mEventAction = getString(R.string.ACTION_EXAM_NOT_PREPARING_SELECTED);
                EventBus.getDefault().post(new Event(AllEvents.ACTION_SKIP_EXAM_SELECTION, null, null));
                break;
            case R.id.user_education_next_button:
                this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
                this.mEventAction = getString(R.string.ACTION_EXAM_NEXT_SELECTED);
                this.setUserEducationExams();
                break;
            case R.id.user_education_level_edit_btn:
                EventBus.getDefault().post(new Event(AllEvents.ACTION_LEVEL_EDIT_SELECTION, null, null));
                break;
            case R.id.user_education_stream_edit_btn:
                EventBus.getDefault().post(new Event(AllEvents.ACTION_STREAM_EDIT_SELECTION, null, null));
                break;
            default:
                break;
        }
        if (!this.mEventAction.isEmpty() && this.mEventAction != ""){
            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());
        }
    }

    public void hideNavigationIcon(){
        mRootView.findViewById(R.id.navigation_cd_icon).setVisibility(View.GONE);
    }

    @Subscribe
    public void onEvent(Event event) {
        if (event != null) {
            switch (event.getTag()) {
                case AllEvents.ACTION_STREAM_EXAM_SELECTION:
                   this.updateInstituteCount();
                   this.showNextButton();
                   break;
            }
        }
    }

    private void updateInstituteCount()
    {
        int instituteCount = 0;
        boolean isAnyExamSelected = false;
        if(mStreamExamList != null && !mStreamExamList.isEmpty()){
            for (Exam exam :mStreamExamList ) {
                if(exam == null )continue;
                ArrayList<ExamDetail> examDetailList = exam.getExam_details();
                if(examDetailList != null) {
                    int count = examDetailList.size();
                    for (int i = 0; i < count; i++) {
                        ExamDetail examDetailObj = examDetailList.get(i);
                        if (examDetailObj == null) continue;
                        if (examDetailObj.isSelected()) {
                            isAnyExamSelected = true;
                            if (instituteCount < examDetailObj.getInstitutes_count())
                                instituteCount = examDetailObj.getInstitutes_count();
                        }
                    }
                }
            }
        }
        if(!isAnyExamSelected){
            if(isAdded()) {
                instituteCount = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).
                        getInt(getString(R.string.pref_stream_institute_count), instituteCount);
            }
        }
        if(instituteCount > 0){
           super.setInstituteCount(String.valueOf(instituteCount));
        }
    }

    private void showNextButton() {
        final View nextView = mRootView.findViewById(R.id.user_education_next_button);
        if (nextView.getAlpha() != 1) {

            nextView.setVisibility(View.VISIBLE);
            nextView.setAlpha(0);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    nextView.animate()
                            .x(mRootView.getWidth()- nextView.getWidth() - nextView.getPaddingRight())
                            .alpha(1f)
                            .setDuration(Constants.ANIM_AVERAGE_DURATION);
                }
            }, Constants.ANIM_SHORT_DURATION);


            View skipView = mRootView.findViewById(R.id.user_education_skip_button);
            skipView.setVisibility(View.VISIBLE);
            skipView.setAlpha(0f);

            skipView.animate()
                    .alpha(1f)
                    .x(mRootView.findViewById(R.id.user_education_next_button_layout).getPaddingLeft())
                    .setStartDelay(Constants.ANIM_SHORT_DURATION)
                    .setDuration(Constants.ANIM_AVERAGE_DURATION);

        }
    }


    public void updateStreamExamsList(ArrayList<Exam> mAllExamList){

        if(isAdded()) {
          //  getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit()
            //        .putInt(getString(R.string.pref_institute_count), Integer.parseInt(mInstituteCount)).apply();
        }
        if(mAllExamList == null || mAllExamList.isEmpty()){
            mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
            TextView emptyText = (TextView) mRootView.findViewById(R.id.empty) ;
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(getString(R.string.no_exam_found));
            return;
        }
        // add all stream Exam on the top
        int examCount = mAllExamList.size();
        int i=0;
        while(i < examCount) {
            Exam exam = mAllExamList.get(i);
            if(exam == null || exam.getExam_type() == ProfileMacro.STREAM_EXAM){
                i++;
                continue;
            }else {
                examCount--;
                mAllExamList.remove(i);
                mAllExamList.add(exam);
            }
        }

        this.mStreamExamList.clear();
        this.mStreamExamList.addAll(mAllExamList);

        this.showNextButton();

        if(mStreamExamList != null && mStreamExamList.size() >0){
            mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.VISIBLE);
        }else{
            TextView emptyText = (TextView) mRootView.findViewById(R.id.empty) ;
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(getString(R.string.no_exam_found));
            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
        }

        // show searchView for exam
        View examSearchView = mRootView.findViewById(R.id.user_exam_search_container);
        if(examSearchView.getVisibility() == View.GONE) {
            examSearchView.setVisibility(View.VISIBLE);
            examSearchView.startAnimation(animationFromTop);
        }

        // show  next button
        View nextButtonLayout = mRootView.findViewById(R.id.user_education_next_button_layout);
        if(nextButtonLayout.getVisibility() == View.GONE){
            nextButtonLayout.setVisibility(View.VISIBLE);
            nextButtonLayout.startAnimation(animationFromBottom);
        }

        if(mExamAdapter != null){
            mExamAdapter.setShowAllExams(true);
            mExamAdapter.updateExamsList(mStreamExamList);
        }
    }

    private void setUserEducationExams() {

        if( this.mExamAdapter == null)
            return;

        // if mDeviceProfile does not have any exam and want to further proceed
        // then next button works like "Not Preparing Button"
        if(this.mStreamExamList != null && this.mStreamExamList.size() <=0){
            mClearUserExams();
            EventBus.getDefault().post(new Event(AllEvents.ACTION_SKIP_EXAM_SELECTION, null, null));
            return;
        }

        boolean isExamSelected = false;
        StringBuilder selectedExamsBuffer = new StringBuilder();
        selectedExamsBuffer.append("[");
        int firstTime =0;
        if(mStreamExamList != null && !mStreamExamList.isEmpty()) {
            for (Exam exam:mStreamExamList) {
                if(exam == null || !exam.isSelected())continue;

                ArrayList<ExamDetail> detailList = exam.getExam_details();
                if(detailList == null || detailList.isEmpty()) continue;

                for (ExamDetail examDetailObj:detailList) {
                    if(examDetailObj == null || !examDetailObj.isSelected())continue;

                    if(firstTime != 0)
                        selectedExamsBuffer.append(",");
                    firstTime++;

                    selectedExamsBuffer.append("{\"id\":").append(examDetailObj.getId())
                            .append(",").append("\"score\":").append(examDetailObj.getScore());

                    if(examDetailObj.isResult_out()){
                        //TODO :: set  to exam status as given or preparing base on
                        // TODO::  result out date  and exam's date
                        //String date = examDetailObj.getExam_date();
                        selectedExamsBuffer.append(",").append("\"status\":").append(1);
                    }else{
                        selectedExamsBuffer.append(",").append("\"status\":").append(2);
                    }
                    selectedExamsBuffer.append("}");

                    isExamSelected = true;

                }
            }
        }
        selectedExamsBuffer.append("]");

        if(!isExamSelected){
            EventBus.getDefault().post(new Event(AllEvents.ACTION_PLEASE_SELECT_ATLEAST_ONE_EXAM, null, null));
            return;
        }

        HashMap<String, String> userParams = new HashMap<>();
        userParams.put("yearly_exams", selectedExamsBuffer.toString());
        EventBus.getDefault().post(new Event(AllEvents.ACTION_EXAMS_SELECTION, userParams, null));


        this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
        this.mEventAction = getString(R.string.ACTION_USER_EXAM_SELECTED);
        try
        {
            //Events
            this.mEventValue.put(getString(R.string.USER_EXAM_SELECTED), selectedExamsBuffer.toString());//examDetail.get(getString(R.string.EXAM_ID)) + "#" + examDetail.get(getString(R.string.SCORE)) + "#" + examDetail.get(getString(R.string.STATUS)));
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());
            this.mEventValue.clear();
        }catch(Exception e){
            Log.e(TAG,"exception in sending events");
        }
    }

    private void mClearUserExams()
    {

        if(MainActivity.mProfile != null) {
            ArrayList<ProfileExam> userExamList = MainActivity.mProfile.getYearly_exams();
            if(userExamList != null) {
                int size =userExamList.size();
                if (size >= 1) {
                    HashMap<String, String> userParams = new HashMap<>();
                    userParams.put("yearly_exams", "[]");
                    EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_PROFILE, userParams, null));
                    MainActivity.mProfile.setYearly_exams(new ArrayList<ProfileExam>());
                }
            }
        }
    }

}
