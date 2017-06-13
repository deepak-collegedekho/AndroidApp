package com.collegedekho.app.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.QnAAnswerListAdapterNew;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sureshsaini on 24/1/17.
 */

public class QnaQuestionDetailFragmentNew extends BaseFragment {

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
    private Map<String, ArrayList<QnAQuestions>> mSimilarQuestionMap  = new HashMap<>();
    private TextView mQuestionAskedBy;
    private TextView mQuestionAskedOn;
    private CircularImageView userProfileImage;
    private ImageLoader mImageLoader;
    private LinearLayout tagsContainer;

    public static QnaQuestionDetailFragmentNew getInstance(QnAQuestions qnaQuestion){
        QnaQuestionDetailFragmentNew   sInstance = new QnaQuestionDetailFragmentNew();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, qnaQuestion);
        sInstance.setArguments(args);
        return sInstance;
    }


    public  QnaQuestionDetailFragmentNew(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mQnAQuestion = getArguments().getParcelable(ARG_PARAM1);
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
        this.mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
        this.mSimilarQnaProgress = (ProgressBar) view.findViewById(R.id.similar_questions_progress);
        this.userProfileImage = (CircularImageView) view.findViewById(R.id.image_user_profile_pic);
        this.mQuestionText = (TextView) view.findViewById(R.id.qna_question_text);
        this.mQuestionAskedBy = (TextView) view.findViewById(R.id.asked_by_user);
        this.mQuestionAskedOn = (TextView) view.findViewById(R.id.asked_on);
        this.mQnAAnswersListAdapter = new QnAAnswerListAdapterNew(getContext(), this.mQnAAnswersSet);
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
        this.tagsContainer = (LinearLayout) view.findViewById(R.id.tags_container);
        view.findViewById(R.id.question_share_button).setOnClickListener(this);
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
                case AllEvents.ACTION_GIVE_ANSWER_FIRST_CLICK:
                    showAnswerDialog();
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
            ((MainActivity) getActivity()).currentFragment = this;
            getActivity().invalidateOptionsMenu();
        }
        if(this.floatingActionButton != null){
            this.floatingActionButton.setVisibility(View.VISIBLE);
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
        if(this.floatingActionButton != null){
            float translationY  = floatingActionButton.getTranslationY();
            if(translationY > -100){
                float translationValue = 155;
                floatingActionButton.setTranslationY(-translationValue);
            }
            this.floatingActionButton.setVisibility(View.GONE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Uri app_uri_val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_QNA_QUESTION_LIST + "/personalize/qna/" + this.mQnAQuestion.getId());
        Uri web_uri_val = Uri.parse(this.mQnAQuestion.getResource_uri());

        AnalyticsUtils.AppIndexingView("CollegeDekho - QnA - " + this.mQnAQuestion.getTitle(), web_uri_val, app_uri_val, (MainActivity) this.getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();

        Uri app_uri_val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_QNA_QUESTION_LIST + "/personalize/qna/" + this.mQnAQuestion.getId());
        Uri web_uri_val = Uri.parse(this.mQnAQuestion.getResource_uri());

        AnalyticsUtils.AppIndexingView("CollegeDekho - QnA - " + this.mQnAQuestion.getTitle(), web_uri_val, app_uri_val, (MainActivity) this.getActivity(), false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabButton:
                this.showAnswerDialog();
                break;
            case R.id.question_share_button:
                this.shareQuestion();
                break;
            default:
                break;
        }
    }
    public void updateQuestion(QnAQuestions qnAQuestions) {
        this.mQnAQuestion  = qnAQuestions;
        updateQuestionDetails();
        requestForSimilarQuestions();
    }

    private void updateQuestionDetails() {
        if (this.mQnAQuestion == null)
            return;
        this.mQuestionText.setText(this.mQnAQuestion.getDesc());
        this.mQuestionAskedBy.setText(this.mQnAQuestion.getUser());
        this.mQuestionAskedOn.setText(this.mQnAQuestion.getAdded_on());
        this.tagsContainer.removeAllViews();
        this.userProfileImage.setDefaultImageResId(R.drawable.ic_profile_default_vector);
        this.userProfileImage.setImageUrl(mQnAQuestion.getUser_image(),mImageLoader);
        this.userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    EventBus.getDefault().post(new Event(AllEvents.ACTION_USER_DP_CLICK,mQnAQuestion.getId() , null));
            }
        });

        this.mQnAAnswersSet.clear();
        int answerCount = 0;
        if(mQnAQuestion.getAnswer_set() != null) {
            answerCount = mQnAQuestion.getAnswer_set().size();
        }
        if (answerCount >= 1) {
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
        ArrayList<QnAQuestions> similarQuestions = this.mSimilarQuestionMap.get(this.mQnAQuestion.getId());

        if(similarQuestions != null && !similarQuestions.isEmpty()){
            this.mQnAAnswersSet.add("Similar Questions");
            this.mQnAAnswersSet.addAll(similarQuestions);
            this.mQnAAnswersListAdapter.notifyDataSetChanged();
            this.mSimilarQnaProgress.setVisibility(View.GONE);
            return;
        }

        ArrayList<String> similarQuestionsIdList = this.mQnAQuestion.getSimilar_questions();
        if(similarQuestionsIdList == null || similarQuestionsIdList.size() <= 0) {
            this.mSimilarQnaProgress.setVisibility(View.GONE);
            return;
        }

        StringBuffer  similarQuestionsIds  = new StringBuffer();
        int count = similarQuestionsIdList.size();
        for (String id:similarQuestionsIdList) {
            similarQuestionsIds.append(id);
            count--;
            if(count !=0) {
                similarQuestionsIds.append(",");
            }
        }

        ArrayList<String> tags = this.mQnAQuestion.getTags();
        if(tags != null && tags.size() > 0)
            for(int i = 0; i < tags.size(); i++)
            {
                TextView tv = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.item_tag, null);
                tv.setText(tags.get(i));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10,0,10,0);
                tagsContainer.addView(tv,lp);
                tagsContainer.setDividerPadding(10);
            }
        else
            tagsContainer.setVisibility(View.GONE);

        this.mSimilarQnaProgress.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_SIMILAR_QUESTION, null, similarQuestionsIds.toString()));
    }

    public void updateSimilarQuestion(ArrayList<QnAQuestions> similarQuestionList){
        if(!isAdded())
            return;

        this.mSimilarQnaProgress.setVisibility(View.GONE);
        if(similarQuestionList == null || similarQuestionList.size() <=0){
            return;
        }
        this.mSimilarQuestionMap.put(this.mQnAQuestion.getId(), similarQuestionList);

        this.mQnAAnswersSet.add("Similar Questions");
        this.mQnAAnswersSet.addAll(similarQuestionList);
        this.mQnAAnswersListAdapter.notifyDataSetChanged();
    }

    public void addNewAnswer(){
        updateQuestionDetails();
        ArrayList<QnAQuestions> similarQuestions = this.mSimilarQuestionMap.get(this.mQnAQuestion.getId());

        if(similarQuestions != null){
            this.mQnAAnswersSet.add("Similar Questions");
            this.mQnAAnswersSet.addAll(similarQuestions);
            this.mQnAAnswersListAdapter.notifyDataSetChanged();
        }


    }
    private void shareQuestion(){
        if(mQnAQuestion == null)
            return;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "CollegeDekho App");
        String shareableText = "Hey I found this really awesome QnA at CollegeDekho..\n";
        shareableText = shareableText + "\n" + mQnAQuestion.getTitle() + "\n";
        shareableText = shareableText + "\n"+ mQnAQuestion.getWeb_resource_uri() + "\n\n";
        shareableText = shareableText + "CollegeDekho \nDiscover.Prepare.Achieve";
        i.putExtra(Intent.EXTRA_TEXT, shareableText);
        getActivity().startActivity(Intent.createChooser(i, "Share"));

    }

    private void showAnswerDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_qna_answer);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        this.mAnswerETV = ((EditText) dialog.findViewById(R.id.qna_answer_edittext));
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
    public String getEntity() {
        return null;
    }
}
