package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sureshsaini on 29/5/16.
 */

public class ProfileExam implements Parcelable{

    private int status ;
    private String exam_short_name ="";
    private String exam_name ="";
    private String exam_tag ="";
    private int score;
    private int year;
    private int id;

    public ProfileExam(){
        //required empty constructor
    }

    protected ProfileExam(Parcel in) {
        status = in.readInt();
        exam_short_name = in.readString();
        exam_name = in.readString();
        exam_tag = in.readString();
        score = in.readInt();
        year = in.readInt();
        id = in.readInt();
    }

    public static final Creator<ProfileExam> CREATOR = new Creator<ProfileExam>() {
        @Override
        public ProfileExam createFromParcel(Parcel in) {
            return new ProfileExam(in);
        }

        @Override
        public ProfileExam[] newArray(int size) {
            return new ProfileExam[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExam_short_name() {
        return exam_short_name;
    }

    public void setExam_short_name(String exam_short_name) {
        this.exam_short_name = exam_short_name;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_tag() {
        return exam_tag;
    }

    public void setExam_tag(String exam_tag) {
        this.exam_tag = exam_tag;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(exam_short_name);
        dest.writeString(exam_name);
        dest.writeString(exam_tag);
        dest.writeInt(score);
        dest.writeInt(year);
        dest.writeInt(id);
    }
}
