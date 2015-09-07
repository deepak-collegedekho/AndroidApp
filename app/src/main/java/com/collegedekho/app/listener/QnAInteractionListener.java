package com.collegedekho.app.listener;

import android.net.Uri;

/**
 * Created by harshvardhan on 14/08/15.
 */
public interface QnAInteractionListener {
    void onQnAQuestionVote(String resourceURI, int voteType, int questionPosition);
    void onQnAAnswerVote(String resourceURI, int voteType, int questionPosition, int answerPostion);
    void onQnAAnswerSubmitted(String questionURI, String answerText);

}
