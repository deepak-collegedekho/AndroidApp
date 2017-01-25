package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 24/1/17.
 */

public class QnaQuestionDetailFragmentNew extends BaseFragment {


    private static QnaQuestionDetailFragmentNew sInstance;
    private static final String ARG_PARAM1 ="PARAM1";
    private QnAQuestions mQnAQuestion;
    private ArrayList<QnAAnswers> mQnAAnswersSet = new ArrayList<>();

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
            //this.mQnAPosition = getArguments().getInt(ARG_PARAM2);
            if(this.mQnAQuestion!=null) {
                this.mQnAAnswersSet.clear();
                this.mQnAAnswersSet.addAll(mQnAQuestion.getAnswer_set());
            }
           // mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
           // mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_qna_detail_new, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView reccylerView = (RecyclerView)view.findViewById(R.id.qna_answer_list);


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
