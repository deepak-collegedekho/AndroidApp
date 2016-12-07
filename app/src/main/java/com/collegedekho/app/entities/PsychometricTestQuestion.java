package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bashir on 11/12/15.
 */
public class PsychometricTestQuestion implements Parcelable{

    public static final Creator<PsychometricTestQuestion> CREATOR = new Creator<PsychometricTestQuestion>() {
        @Override
        public PsychometricTestQuestion createFromParcel(Parcel source) {
            return new PsychometricTestQuestion(source);
        }

        @Override
        public PsychometricTestQuestion[] newArray(int size) {
            return new PsychometricTestQuestion[size];
        }
    };
    private String question;
    private String id;
    private String stream_id;
    private int que_no;
    private int checkedId;
    private String answer="0";

    public PsychometricTestQuestion(){

    }

    public PsychometricTestQuestion(Parcel parcel){
        question=parcel.readString();
        id=parcel.readString();
        stream_id=parcel.readString();

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStream_id() {
        return stream_id;
    }

    public void setStream_id(String stream_id) {
        this.stream_id = stream_id;
    }

    public int getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(int ckeckId) {
        this.checkedId = ckeckId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getQue_no() {
        return que_no;
    }

    public void setQue_no(int que_no) {
        this.que_no = que_no;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(id);
        dest.writeString(stream_id);
    }
}
