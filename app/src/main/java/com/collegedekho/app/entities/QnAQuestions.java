package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 10/08/15.
 */
public class QnAQuestions implements Parcelable {
    public static final Creator<QnAQuestions> CREATOR = new Creator<QnAQuestions>() {
        @Override
        public QnAQuestions createFromParcel(Parcel source) {
            return new QnAQuestions(source);
        }

        @Override
        public QnAQuestions[] newArray(int size) {
            return new QnAQuestions[size];
        }
    };

    private String resource_uri;
    private String uri;
    private String id;
    private int answers_count = 0;
    private ArrayList<String> tags;
    private String title;
    private String desc;
    private int status;
    private boolean is_spam;
    private int upvotes;
    private int downvotes;
    private int current_user_vote_type;
    private int view_count;
    private String uri_slug;
    private String added_on;
    private String user;
    private String user_image;
    private String degree;
    private String stream;
    private String institute;
    private String course;
    private String city;
    private String state;
    private ArrayList<QnAAnswers> answer_set;
    private int index;
    private ArrayList<String> similar_questions;
    private int is_study_abroad = 0;

    public QnAQuestions() {
    }

    public QnAQuestions(Parcel source) {
        resource_uri = source.readString();
        uri = source.readString();
        id = source.readString();
        answers_count = source.readInt();
        title = source.readString();
        desc = source.readString();
        status = source.readInt();
        tags = source.createStringArrayList();
        is_spam = source.readString() == "true";
        upvotes = source.readInt();
        current_user_vote_type = source.readInt();
        downvotes = source.readInt();
        view_count = source.readInt();
        uri_slug = source.readString();
        added_on = source.readString();
        user = source.readString();
        user_image = source.readString();
        degree = source.readString();
        stream = source.readString();
        institute = source.readString();
        course = source.readString();
        city = source.readString();
        state = source.readString();
        source.readStringList(similar_questions);
        answer_set = source.createTypedArrayList(QnAAnswers.CREATOR);
        if(answer_set == null)
            answer_set = new ArrayList<>();
        source.readTypedList(answer_set, QnAAnswers.CREATOR);
        is_study_abroad = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resource_uri);
        dest.writeString(uri);
        dest.writeString(id);
        dest.writeInt(answers_count);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeInt(status);
        dest.writeStringList(tags);
        dest.writeInt(is_spam ? 1 : 0);
        dest.writeInt(upvotes);
        dest.writeInt(current_user_vote_type);
        dest.writeInt(downvotes);
        dest.writeInt(view_count);
        dest.writeString(uri_slug);
        dest.writeString(added_on);
        dest.writeString(user);
        dest.writeString(user_image);
        dest.writeString(degree);
        dest.writeString(stream);
        dest.writeString(institute);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeTypedList(answer_set);
        dest.writeStringList(similar_questions);
        dest.writeInt(index);
        dest.writeInt(is_study_abroad);
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAnswers_count() {
        return answers_count;
    }

    public void setAnswers_count(int answers_count) {
        this.answers_count = answers_count;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean is_spam() {
        return is_spam;
    }

    public void setIs_spam(boolean is_spam) {
        this.is_spam = is_spam;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public String getUri_slug() {
        return uri_slug;
    }

    public void setUri_slug(String uri_slug) {
        this.uri_slug = uri_slug;
    }

    public String getAdded_on() {
        return added_on;
    }

    public void setAdded_on(String added_on) {
        this.added_on = added_on;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<QnAAnswers> getAnswer_set() {
        if(answer_set == null)
            answer_set = new ArrayList<>();
        return answer_set;
    }

    public void setAnswer_set(ArrayList<QnAAnswers> answer_set) {
        this.answer_set = answer_set;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCurrent_user_vote_type() {
        return current_user_vote_type;
    }

    public void setCurrent_user_vote_type(int current_user_vote_type) {
        this.current_user_vote_type = current_user_vote_type;
    }

    public ArrayList<String> getSimilar_questions() {
        return similar_questions;
    }

    public void setSimilar_questions(ArrayList<String> similar_questions) {
        this.similar_questions = similar_questions;
    }

    public int getIs_study_abroad() {
        return is_study_abroad;
    }

    public void setIs_study_abroad(int is_study_abroad) {
        this.is_study_abroad = is_study_abroad;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}