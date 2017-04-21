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
import android.widget.Button;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.SyllabusSubjectListAdapter;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.Subjects;
import com.collegedekho.app.entities.Units;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    private SyllabusSubjectListAdapter mAdapter;
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
        Button btnSubmit=(Button)rootView.findViewById(R.id.btn_submit_subjects);

        if (mSubjects.size() == 0) {
            mEmptyTextView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setText(getString(R.string.no_subjects));
            btnSubmit.setVisibility(View.GONE);
        }else{
            btnSubmit.setVisibility(View.VISIBLE);
        }
        btnSubmit.setOnClickListener(this);
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

        MainActivity mMainActivity = (MainActivity) this.getActivity();
        if (mMainActivity != null)
            mMainActivity.currentFragment = this;

        if (mSubjects.size() == 0){
            this.mEmptyTextView.setText(getString(R.string.no_subjects));
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            this.mEmptyTextView.setText("");
            this.mEmptyTextView.setVisibility(View.GONE);
        }
        updateSubjectProgress();
        mAdapter.notifyDataSetChanged();
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

            if(originalUnitsList != null && !originalUnitsList.isEmpty()){
                for (Units unitObj:originalUnitsList ) {

                    Units unit = new Units();
                    unit.setUnit_id(unitObj.getUnit_id());
                    unit.setSubject_id(unitObj.getSubject_id());
                    unit.setIs_done(unitObj.getIs_done());

                    ArrayList<Chapters> originalChapterList = unitObj.getChapters();
                    ArrayList<Chapters>  chapterList = new ArrayList<>();

                    if(originalChapterList != null && !originalChapterList.isEmpty()) {
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
        int size=mSubjects.size();
        for (int i=0;i<size;i++) {
            Subjects subjectObj = mSubjects.get(i);
            Subjects subject = mSubjectsLastStatus.get(i);
            if (subject.getSubject_id() != subjectObj.getSubject_id()) {
                continue;
            }
            ArrayList<Units> originalUnitsList = subjectObj.getUnits();
            ArrayList<Units> unitList = subject.getUnits();

            int unitsSize = originalUnitsList.size();
            for (int j = 0; j < unitsSize; j++) {
                Units unitObj = originalUnitsList.get(j);
                Units unit = unitList.get(j);

                if (unitObj.getUnit_id() != unit.getUnit_id()) {
                    continue;
                }

                ArrayList<Chapters> originalChapterList = unitObj.getChapters();
                ArrayList<Chapters> chapterList = unit.getChapters();

                if (originalChapterList != null && !originalChapterList.isEmpty()
                        && chapterList != null && !chapterList.isEmpty()) {

                    int chapterSize = originalChapterList.size();
                    for (int k = 0; k < chapterSize; k++) {
                        Chapters chapterObj = originalChapterList.get(k);
                        Chapters chapter = chapterList.get(k);
                        if (chapterObj.getId() != chapter.getId()) continue;

                        try {
                            if (chapterObj.getIs_done() != chapter.getIs_done()) {
                                subjetIdsMap.put("" + unit.getSubject_id(), "" + chapter.getId());
                            }
                            if (chapterObj.getIs_done() == 1)
                                chapterJsonArray.put(chapter.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

        if(subjetIdsMap.size()>0) {
            for (Object o : subjetIdsMap.entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                subjectJsonArray.put(pair.getKey());
            }

            try {
                parentJsonObj.put(getString(R.string.CHAPTERS), chapterJsonArray);
                parentJsonObj.put(getString(R.string.SUBJECTS), subjectJsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (listener != null)
                listener.onSyllabusChanged(parentJsonObj);
        }

    }


    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit_subjects:
//                submitSyllabusStatus();
                getActivity().onBackPressed();
                break;
        }
    }

    private void updateSubjectProgress() {
        if (this.mSubjects != null && !this.mSubjects.isEmpty()) {
            for (Subjects subject : mSubjects) {
                float donePercent = 0;
                ArrayList<Units> unites = subject.getUnits();
                if (unites != null && !unites.isEmpty()) {
                    for (Units unit : unites) {
                        donePercent += unit.getUnit_done_percent();
                    }
                    donePercent = donePercent / unites.size();
                }
                subject.setSubject_done_percent(donePercent);
            }
        }
    }

    public interface OnSubjectSelectedListener {
        void onSubjectSelected(Subjects subject, int position);
        void onSyllabusChanged(JSONObject jsonObject);
    }
}
