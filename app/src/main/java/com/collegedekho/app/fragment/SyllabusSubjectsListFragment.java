package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.SyllabusSubjectListAdapter;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.Subjects;
import com.collegedekho.app.entities.Units;
import com.collegedekho.app.resource.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSubjectSelectedListener} interface
 * to handle interaction events.
 * Use the {@link SyllabusSubjectsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SyllabusSubjectsListFragment extends BaseFragment {
    public static final String TITLE = "SyllabusSubjectsListFragment";
    private static final String ARG_SUBJECT_LIST = "subjectList";
    private ArrayList<Subjects> mSubjects;
    private String mTitle;
    private SyllabusSubjectListAdapter mAdapter;
    private MainActivity mMainActivity;
    private TextView mEmptyTextView;
    private OnSubjectSelectedListener listener;
    private ArrayList<Subjects> mSubjectsLastStatus;

    public SyllabusSubjectsListFragment() {
        // Required empty public constructor
    }

    public static SyllabusSubjectsListFragment newInstance(ArrayList<Subjects> subjects) {
        SyllabusSubjectsListFragment fragment = new SyllabusSubjectsListFragment();
        Bundle args = new Bundle();

        args.putParcelableArrayList(ARG_SUBJECT_LIST, subjects);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSubjects = getArguments().getParcelableArrayList(ARG_SUBJECT_LIST);
            storeChaptersStatus();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_syllabus_subjects, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.syllabus_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new SyllabusSubjectListAdapter(getActivity(), mSubjects);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        if (mSubjects.size() == 0) {
            mEmptyTextView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setText("No Subjects");
        }

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity)
                listener = (OnSubjectSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSubjectSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(ARG_SUBJECT_LIST, this.mSubjects);
        outState.putString(ARG_TITLE, this.mTitle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.mMainActivity = (MainActivity) this.getActivity();

        if (this.mMainActivity != null)
            this.mMainActivity.currentFragment = this;

        if (mSubjects.size() == 0)
        {
            this.mEmptyTextView.setText("No Subjects");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            this.mEmptyTextView.setText("");
            this.mEmptyTextView.setVisibility(View.GONE);
        }
    }

    private void storeChaptersStatus(){

        if(this.mSubjects == null || this.mSubjects.isEmpty())
            return;

        this.mSubjectsLastStatus = new ArrayList<>();
        for (Subjects subjectObj:mSubjects) {

            Subjects subject = new Subjects();
            subject.setSubject_id(subjectObj.getSubject_id());

            ArrayList<Units> originalUnitsList = subjectObj.getUnits();
            ArrayList<Units> unitList = new ArrayList<>();

            if(originalUnitsList != null || !originalUnitsList.isEmpty()){
                for (Units unitObj:originalUnitsList ) {

                    Units unit = new Units();
                    unit.setUnit_id(unitObj.getUnit_id());
                    unit.setSubject_id(unitObj.getSubject_id());
                    unit.setIs_done(unitObj.getIs_done());

                    ArrayList<Chapters> originalChapterList = unitObj.getChapters();
                    ArrayList<Chapters>  chapterList = new ArrayList<>();

                    if(originalChapterList != null || !originalChapterList.isEmpty()) {
                        for (Chapters chapterObj : originalChapterList) {

                            Chapters chapter = new Chapters();
                            chapter.setId(chapterObj.getId());
                            chapter.setIs_done(chapterObj.getIs_done());
                            chapterList.add(chapter);
                        }
                    }
                    unit.setChapters(chapterList);
                    unitList.add(unit);
                }
            }
            subject.setUnits(unitList);
            mSubjectsLastStatus.add(subject);
        }
    }

    public void submitSyllabusStatus(){

        if(this.mSubjectsLastStatus == null || this.mSubjectsLastStatus.isEmpty()
                || this.mSubjects == null || this.mSubjects.isEmpty())
            return;

        JSONObject parentJsonObj = new JSONObject();
        JSONArray chapterJsonArray = new JSONArray();
        JSONArray subjectJsonArray = new JSONArray();
        HashMap<String, String> subjetIdsMap = new HashMap<>();
        for (Subjects subjectObj:mSubjects) {
            for (Subjects subject :mSubjectsLastStatus){
                if(subject.getSubject_id() != subjectObj.getSubject_id())continue;

                ArrayList<Units> originalUnitsList = subjectObj.getUnits();
                ArrayList<Units> unitList = subject.getUnits();

                 if (originalUnitsList != null || !originalUnitsList.isEmpty()
                     ||unitList != null || !unitList.isEmpty()) {

                     for (Units unitObj : originalUnitsList) {
                         for (Units unit : unitList) {
                             if (unitObj.getUnit_id() != unit.getUnit_id()) continue;

                             ArrayList<Chapters> originalChapterList = unitObj.getChapters();
                             ArrayList<Chapters> chapterList = unit.getChapters();

                             if (originalChapterList != null || !originalChapterList.isEmpty()
                                     || chapterList != null || !chapterList.isEmpty()) {

                                 for (Chapters chapterObj : originalChapterList) {
                                     for (Chapters chapter : chapterList) {
                                         if (chapterObj.getId() != chapter.getId()) continue;

                                         try {
                                             if (chapterObj.getIs_done() != chapter.getIs_done()) ;
                                             subjetIdsMap.put(""+unit.getSubject_id(),""+chapter.getId());

                                             if (chapterObj.getIs_done() == 1)
                                                 chapterJsonArray.put(chapter.getId());
                                         }catch (Exception e){
                                             e.printStackTrace();
                                         }

                                     }
                                 }
                             }

                         }
                     }
                 }
            }
        }

        Iterator it = subjetIdsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            subjectJsonArray.put(pair.getKey());
        }

        try {
            parentJsonObj.put(Constants.CHAPTERS,chapterJsonArray);
            parentJsonObj.put(Constants.SUBJECTS,subjectJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(listener != null)
            listener.onSyllabusChanged(parentJsonObj);

    }

    public interface OnSubjectSelectedListener {
        void onSubjectSelected(Subjects subject, int position);
        void onSubjectCheckboxSelected(Subjects subject, int position);
        void onSyllabusChanged(JSONObject jsonObject);
    }
}
