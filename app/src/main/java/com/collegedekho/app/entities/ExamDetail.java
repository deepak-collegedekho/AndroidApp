package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by sureshsaini on 4/12/15.
 */
public class ExamDetail implements Parcelable
{
    private String id ;
    private String year;
    private String exam_date;
    private boolean result_out;
    private String exam_marks="";

    public ExamDetail(){
        // required empty cons
    }


    protected ExamDetail(Parcel in) {
        id = in.readString();
        year = in.readString();
        exam_date = in.readString();
        result_out = in.readByte() != 0;
    }

    public static final Creator<ExamDetail> CREATOR = new Creator<ExamDetail>() {
        @Override
        public ExamDetail createFromParcel(Parcel in) {
            return new ExamDetail(in);
        }

        @Override
        public ExamDetail[] newArray(int size) {
            return new ExamDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(year);
        dest.writeString(exam_date);
        dest.writeByte((byte) (result_out ? 1 : 0));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public void setExam_marks(String marks){
        this.exam_marks=marks;
    }

    public String getExam_marks(){
        return exam_marks;
    }
}
