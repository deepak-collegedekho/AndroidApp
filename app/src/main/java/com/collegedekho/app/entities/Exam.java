package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class Exam implements Parcelable{

    private String exam;
    private ArrayList<ExamDetail> exam_details;
    private boolean isSelected;

    public Exam(){
        // required empty constructor
    }

    protected Exam(Parcel in) {
        exam = in.readString();
        exam_details = in.createTypedArrayList(ExamDetail.CREATOR);
        isSelected = in.readByte() != 0;
    }

    public static final Creator<Exam> CREATOR = new Creator<Exam>() {
        @Override
        public Exam createFromParcel(Parcel in) {
            return new Exam(in);
        }

        @Override
        public Exam[] newArray(int size) {
            return new Exam[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exam);
        dest.writeTypedList(exam_details);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public ArrayList<ExamDetail> getExam_details() {
        return exam_details;
    }

    public void setExam_details(ArrayList<ExamDetail> exam_details) {
        this.exam_details = exam_details;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
