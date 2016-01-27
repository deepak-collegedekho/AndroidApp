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
    private String exam_name="";
    private String exam_tag="";
    private String exam_short_name="Exam";
    private boolean isSelected;

    public boolean is_preparing() {
        return is_preparing;
    }

    public void setIs_preparing(boolean is_preparing) {
        this.is_preparing = is_preparing;
    }

    private boolean is_preparing;

    public ExamDetail(){
        // required empty cons
    }


    protected ExamDetail(Parcel in) {
        id = in.readString();
        year = in.readString();
        exam_date = in.readString();
        exam_name = in.readString();
        exam_tag = in.readString();
        exam_short_name = in.readString();
        result_out = in.readByte() != 0;
        is_preparing=in.readByte()!=0;
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
        dest.writeString(exam_name);
        dest.writeString(exam_tag);
        dest.writeString(exam_short_name);
        dest.writeByte((byte) (result_out ? 1 : 0));
        dest.writeByte((byte) (is_preparing ? 1 : 0));
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

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_short_name() {
        if(exam_short_name==null || exam_short_name.matches(""))
        return exam_name;
        return exam_short_name;
    }

    public void setExam_short_name(String exam_short_name) {
        this.exam_short_name = exam_short_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getExam_tag() {
        return exam_tag;
    }

    public void setExam_tag(String exam_tag) {
        this.exam_tag = exam_tag;
    }
}
