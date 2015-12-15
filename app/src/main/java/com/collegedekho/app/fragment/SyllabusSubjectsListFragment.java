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
import com.collegedekho.app.entities.Subjects;

import java.util.ArrayList;

import javax.security.auth.Subject;


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

    public interface OnSubjectSelectedListener {
        void onSubjectSelected(Subjects subject, int position);
        void onSubjectCheckboxSelected(Subjects subject, int position);
    }
}
