package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class Exam implements Parcelable{

    private String exam_name;
    private String exam_short_name;

    private ArrayList<ExamDetail> exam_details;
    private boolean isSelected;
    private boolean isPreparing;

    public Exam(){
        // required empty constructor
    }

    protected Exam(Parcel in) {
        exam_name = in.readString();
        exam_details=new ArrayList<>();
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
        dest.writeString(exam_name);
        dest.writeString(exam_short_name);
        dest.writeTypedList(exam_details);
        dest.writeByte((byte) (isSelected ? 1 : 0));
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

    public String getExam_short_name() {
        return exam_short_name;
    }

    public void setExam_short_name(String exam_short_name) {
        this.exam_short_name = exam_short_name;
    }

    public boolean isPreparing() {
        return isPreparing;
    }

    public void setPreparing(boolean preparing) {
        isPreparing = preparing;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }
}
