package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harshvardhan on 12/08/15.
 */
public class QnAAnswers implements Parcelable {
    public static final Creator<QnAAnswers> CREATOR = new Creator<QnAAnswers>() {
        @Override
        public QnAAnswers createFromParcel(Parcel source) {
            return new QnAAnswers(source);
        }

        @Override
        public QnAAnswers[] newArray(int size) {
            return new QnAAnswers[size];
        }
    };

    public long id;
    public String resource_uri;
    public String question;
    public String answer_text;
    public int status;
    //public boolean is_spam;
    public int upvotes;
    public int current_user_vote_type;
    public int downvotes;
    //public boolean best_answer;
    public String added_on;
    public String user;
    private int index;
    private int questionIndex;


    public QnAAnswers() {}

    public QnAAnswers(Parcel source) {
        id = source.readLong();
        resource_uri = source.readString();
        question = source.readString();
        answer_text = source.readString();
        status = source.readInt();
        //is_spam = source.readString() == "true";
        upvotes = source.readInt();
        downvotes = source.readInt();
        //best_answer = source.readString() == "true";
        added_on = source.readString();
        user = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(resource_uri);
        dest.writeString(question);
        dest.writeString(answer_text);
        dest.writeInt(status);
        //dest.writeInt(is_spam ? 1 : 0);
        dest.writeInt(upvotes);
        dest.writeInt(current_user_vote_type);
        dest.writeInt(downvotes);
        //dest.writeInt(best_answer ? 1 : 0);
        dest.writeString(added_on);
        dest.writeString(user);
        dest.writeInt(index);
        dest.writeInt(questionIndex);
    }

    public long getId() {
        return id;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public int getStatus() {
        return status;
    }


    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public String getAdded_on() {
        return added_on;
    }

    public String getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public void setAdded_on(String added_on) {
        this.added_on = added_on;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public int getCurrent_user_vote_type() {
        return current_user_vote_type;
    }

    public void setCurrent_user_vote_type(int current_user_vote_type) {
        this.current_user_vote_type = current_user_vote_type;
    }
}
