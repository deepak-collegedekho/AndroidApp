package com.collegedekho.app.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.QnAAnswerListAdapterNew;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.TempData;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.fasterxml.jackson.jr.ob.JSON;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sureshsaini on 24/1/17.
 */

public class QnaQuestionDetailFragmentNew extends BaseFragment {

    private static QnaQuestionDetailFragmentNew sInstance;
    private static final String ARG_PARAM1 ="PARAM1";
    private QnAQuestions mQnAQuestion;
    public TextView mQuestionText;
    private ArrayList<? super Object> mQnAAnswersSet = new ArrayList<>();
    private RecyclerView mAnswersListRC;
    private QnAAnswerListAdapterNew mQnAAnswersListAdapter;
    private EditText mAnswerETV;
    private FloatingActionButton floatingActionButton;
    private ProgressBar mSimilarQnaProgress;
    private LinearLayoutManager linearLayoutManager;
    private Map<String, ArrayList<QnAQuestions>> mSimilarQuestionList = new HashMap<>();

    public static QnaQuestionDetailFragmentNew getInstance(QnAQuestions qnaQuestion){
        synchronized (QnaQuestionDetailFragmentNew.class){
            if(sInstance == null){
                sInstance = new QnaQuestionDetailFragmentNew();
            }
            Bundle args = new Bundle();
            args.putParcelable(ARG_PARAM1, qnaQuestion);
            sInstance.setArguments(args);
            return sInstance;
        }
    }


    public  QnaQuestionDetailFragmentNew(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mQnAQuestion = getArguments().getParcelable(ARG_PARAM1);
            //TODO::delete it when similar question are available
            try {
                mQnAQuestion = JSON.std.beanFrom(QnAQuestions.class, TempData.QuestionJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return   inflater.inflate(R.layout.fragment_qna_detail_new, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mSimilarQnaProgress = (ProgressBar) view.findViewById(R.id.similar_questions_progress);
        this.mQuestionText = (TextView) view.findViewById(R.id.qna_question_text);
        this.mQnAAnswersListAdapter = new QnAAnswerListAdapterNew(getActivity(), this.mQnAAnswersSet);
        this.mAnswersListRC = (RecyclerView) view.findViewById(R.id.qna_answer_list);
        this.linearLayoutManager = new LinearLayoutManager(getActivity());
        this.mAnswersListRC.setLayoutManager(this.linearLayoutManager);
        this.mAnswersListRC.setAdapter(this.mQnAAnswersListAdapter);
        this.mAnswersListRC.setItemAnimator(new DefaultItemAnimator());

        this.floatingActionButton = (FloatingActionButton)getActivity().findViewById(R.id.fabButton);
        this.floatingActionButton.setOnClickListener(this);
        this.floatingActionButton.setImageResource(R.drawable.ic_edit);
        this.floatingActionButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.fab_background_color));
        this.floatingActionButton.setContentDescription("Answer this question");

        updateQuestionDetails();
        requestForSimilarQuestions();
    }

    @Subscribe
    public void onEvent(Event event) {
        if (event != null) {
            switch (event.getTag()){
                case AllEvents.ACTION_SIMILAR_QUESTION_CLICK:

                    this.linearLayoutManager.scrollToPosition(0);
                    this.mQnAQuestion = (QnAQuestions) event.getObj();
                    updateQuestionDetails();
                    requestForSimilarQuestions();
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if(getActivity() != null) {
            ((MainActivity) getActivity()).setToolBarScrollable(true);
        }
        if(floatingActionButton != null){
            floatingActionButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

        if(getActivity() != null){
            ((MainActivity) getActivity()).setToolBarScrollable(false);
        }
        if(floatingActionButton != null){
            floatingActionButton.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabButton:
                showAnswerDialog();
                break;
            default:
                break;
        }
    }

    private void updateQuestionDetails() {
        if (mQnAQuestion == null)
            return;
        mQuestionText.setText(mQnAQuestion.getDesc());
        this.mQnAAnswersSet.clear();
        int answerCount = mQnAQuestion.getAnswers_count();
        if (answerCount > 1) {
            this.mQnAAnswersSet.add(String.valueOf(answerCount) + "+ Answer");
            this.mQnAAnswersSet.addAll(mQnAQuestion.getAnswer_set());
        } else {
            this.mQnAAnswersSet.add(getString(R.string.be_the_first_one_to_answer));
        }
        this.mQnAAnswersListAdapter.notifyDataSetChanged();
    }

    private void  requestForSimilarQuestions(){
        if(this.mQnAQuestion == null)
            return;
        ArrayList<QnAQuestions> similarQuestions = this.mSimilarQuestionList.get(this.mQnAQuestion.getId());

        if(similarQuestions != null && !similarQuestions.isEmpty()){
            this.mQnAAnswersSet.add("Similar Questions");
            this.mQnAAnswersSet.addAll(similarQuestions);
            this.mQnAAnswersListAdapter.notifyDataSetChanged();
            return;
        }

        ArrayList<String> similarQuestionsIds = this.mQnAQuestion.getSimilar_questions();
        if(similarQuestionsIds == null || similarQuestionsIds.size() <= 0)
            return;

        Map<String, String[]> params = new HashMap<>();
        params.put(getString(R.string.similar_question),
                similarQuestionsIds.toArray(new String[similarQuestionsIds.size()]));

        this.mSimilarQnaProgress.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_SIMILAR_QUESTION, params, null));
    }

    public void updateSimilarQuestion(ArrayList<QnAQuestions> similarQuestionList){
        if(!isAdded())
            return;

        this.mSimilarQnaProgress.setVisibility(View.GONE);
        if(similarQuestionList == null || similarQuestionList.size() <=0){
            return;
        }
        this.mSimilarQuestionList.put(this.mQnAQuestion.getId(), similarQuestionList);

        this.mQnAAnswersSet.add("Similar Questions");
        this.mQnAAnswersSet.addAll(similarQuestionList);
        this.mQnAAnswersListAdapter.notifyDataSetChanged();
    }

    public void addNewAnswer(){
        updateQuestionDetails();
        ArrayList<QnAQuestions> similarQuestions = this.mSimilarQuestionList.get(this.mQnAQuestion.getId());

        if(similarQuestions != null){
            this.mQnAAnswersSet.addAll(similarQuestions);
            this.mQnAAnswersListAdapter.notifyDataSetChanged();
        }


    }

    private void showAnswerDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_qna_answer);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        mAnswerETV = ((EditText) dialog.findViewById(R.id.qna_answer_edittext));
        dialog.findViewById(R.id.answer_question_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.qna_answer_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
                    Utils.DisplayToast(getContext(), getString(R.string.INTERNET_CONNECTION_ERROR));
                    return;
                }
                String value = mAnswerETV.getText().toString().trim();
                if (value.isEmpty()) {
                    Utils.DisplayToast(getContext(),getString(R.string.ENTER_YOUR_ANSWER));
                    return;
                }
                mAnswerETV.setText("");
                EventBus.getDefault().post(new Event(AllEvents.ACTION_ANSWER_FOR_QUESTION, QnaQuestionDetailFragmentNew.this.mQnAQuestion, value));
                dialog.dismiss();
            }
        });

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
