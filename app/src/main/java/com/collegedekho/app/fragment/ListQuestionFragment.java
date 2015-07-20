package com.collegedekho.app.fragment;

import android.app.Activity;
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
import com.collegedekho.app.entities.PQuestion;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.DividerItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ListQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListQuestionFragment extends Fragment {
    public static final int TYPE_PRIMARY = 1;
    public static final int TYPE_SECONDARY = 2;
    private static final String ARG_QUESTION = "question";
    private static final String ARG_TYPE = "type";
    private PQuestion pQuestion;
    private int mType;

    public ListQuestionFragment() {
        // Required empty public constructor
    }

    public static ListQuestionFragment newInstance(PQuestion question, int type) {
        ListQuestionFragment fragment = new ListQuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION, question);
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pQuestion = getArguments().getParcelable(ARG_QUESTION);
            mType = getArguments().getInt(ARG_TYPE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questionnaire, container, false);
        ((TextView) rootView.findViewById(R.id.question_title)).setText(pQuestion.text);
        if (mType == TYPE_PRIMARY) {
            if (pQuestion.type.equals(Constants.QTYPE_RANGE))
                rootView.findViewById(R.id.answer_list).setVisibility(View.GONE);
        }
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.answer_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnButtonClickedListener {
        void onButtonClicked(int choice);
    }

}
