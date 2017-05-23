package com.collegedekho.app.entities;
import android.os.Parcel;
import android.os.Parcelable;

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

    private long id;
    private String resource_uri;
    private String uri;
    private String question;
    private String answer_text;
    private int status;
    private int upvotes;
    private int current_user_vote_type;
    private int downvotes;
    private String added_on;
    private String user;
    private String user_id;
    private String user_image;
    private String user_role;
    private int index;
    private int questionIndex;


    public QnAAnswers() {}

    public QnAAnswers(Parcel source) {
        id = source.readLong();
        resource_uri = source.readString();
        uri = source.readString();
        question = source.readString();
        answer_text = source.readString();
        status = source.readInt();
        upvotes = source.readInt();
        downvotes = source.readInt();
        added_on = source.readString();
        user = source.readString();
        user_id = source.readString();
        user_image = source.readString();
        user_role = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(resource_uri);
        dest.writeString(uri);
        dest.writeString(question);
        dest.writeString(answer_text);
        dest.writeInt(status);
        dest.writeInt(upvotes);
        dest.writeInt(current_user_vote_type);
        dest.writeInt(downvotes);
        dest.writeString(added_on);
        dest.writeString(user);
        dest.writeString(user_id);
        dest.writeString(user_image);
        dest.writeString(user_role);
        dest.writeInt(index);
        dest.writeInt(questionIndex);
    }

    public long getId() {
        return id;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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
/*
    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }*/

    public int getCurrent_user_vote_type() {
        return current_user_vote_type;
    }

    public void setCurrent_user_vote_type(int current_user_vote_type) {
        this.current_user_vote_type = current_user_vote_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
