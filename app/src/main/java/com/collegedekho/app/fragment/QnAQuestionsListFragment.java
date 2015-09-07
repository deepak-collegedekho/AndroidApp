package com.collegedekho.app.fragment;

import android.app.Activity;
import android.net.Uri;
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
import com.collegedekho.app.adapter.QnAQuestionsListAdapter;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;

public class QnAQuestionsListFragment extends Fragment {
    public static final String TITLE = "QnA";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<QnAQuestions> mQnAQuestions;
    private TextView mEmptyTextView;

    public static QnAQuestionsListFragment newInstance(ArrayList<QnAQuestions> qnaQuestions)
    {
        QnAQuestionsListFragment fragment = new QnAQuestionsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, qnaQuestions);
        fragment.setArguments(args);

        return fragment;
    }

    public QnAQuestionsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQnAQuestions = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_qna_questions_list, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        if (mQnAQuestions.size() == 0)
            (this.mEmptyTextView).setText("Couldn't find related questions for you. Like and Shortlist college");

        RecyclerView questionsListView = (RecyclerView) rootView.findViewById(R.id.institute_questions_list);

        questionsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionsListView.setAdapter(new QnAQuestionsListAdapter(getActivity(), mQnAQuestions));
        questionsListView.setItemAnimator(new DefaultItemAnimator());
        //questionsListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.gc();
    }

    public interface OnQnAQuestionSelectedListener {
        void onQnAQuestionSelected(QnAQuestions qnaQuestion);
    }
}
