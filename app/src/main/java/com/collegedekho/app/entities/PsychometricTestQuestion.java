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

    private String serialNumber;
    private String questionText;
    private int checkedId;
    private String answer="0";
    public PsychometricTestQuestion(){

    }

    public PsychometricTestQuestion(Parcel parcel){
        serialNumber=parcel.readString();
        questionText=parcel.readString();

    }
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serialNumber);
        dest.writeString(questionText);
    }
}
