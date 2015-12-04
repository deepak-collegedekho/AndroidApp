package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class Exam implements Parcelable{

    private String id ;
    private String exam_name;
    private String exam_year;
    private String exam_date;
    private boolean result_out;
    private boolean isSelected;

    public Exam(){
        // required empty constructor
    }

    protected Exam(Parcel in) {
        id = in.readString();
        exam_name = in.readString();
        exam_year = in.readString();
        exam_date = in.readString();
        result_out = in.readByte() != 0;
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
        dest.writeString(id);
        dest.writeString(exam_name);
        dest.writeString(exam_year);
        dest.writeString(exam_date);
        dest.writeByte((byte) (result_out ? 1 : 0));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
    }

    public String getExam_date() {
        return exam_date;
    }

    public void setExam_date(String exam_date) {
        this.exam_date = exam_date;
    }

    public boolean isResult_out() {
        return result_out;
    }

    public void setResult_out(boolean result_out) {
        this.result_out = result_out;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
