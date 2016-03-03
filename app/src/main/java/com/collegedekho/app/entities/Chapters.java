package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Bashir on 14/12/15.
 */
public class Chapters implements Parcelable {
    String exam_date;
    String yearly_exam_id;
    String days_left;
    public float weightage;
    public int should_be_done;
    public int is_done;
    public int id;
    public String name;
    ArrayList<ChapterDetails> chapters;

    public Chapters(){}


    protected Chapters(Parcel in) {
        exam_date = in.readString();
        yearly_exam_id = in.readString();
        days_left = in.readString();
        weightage = in.readFloat();
        should_be_done = in.readInt();
        is_done = in.readInt();
        id = in.readInt();
        name = in.readString();
        chapters = in.createTypedArrayList(ChapterDetails.CREATOR);
    }

    public static final Creator<Chapters> CREATOR = new Creator<Chapters>() {
        @Override
        public Chapters createFromParcel(Parcel in) {
            return new Chapters(in);
        }

        @Override
        public Chapters[] newArray(int size) {
            return new Chapters[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exam_date);
        dest.writeString(yearly_exam_id);
        dest.writeString(days_left);
        dest.writeFloat(weightage);
        dest.writeInt(should_be_done);
        dest.writeInt(is_done);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(chapters);
    }

    public String getExam_date() {
        return exam_date;
    }

    public void setExam_date(String exam_date) {
        this.exam_date = exam_date;
    }

    public String getYearly_exam_id() {
        return yearly_exam_id;
    }

    public void setYearly_exam_id(String yearly_exam_id) {
        this.yearly_exam_id = yearly_exam_id;
    }

    public String getDays_left() {
        return days_left;
    }

    public void setDays_left(String days_left) {
        this.days_left = days_left;
    }

    public float getWeightage() {
        return weightage;
    }

    public void setWeightage(float weightage) {
        this.weightage = weightage;
    }

    public int getShould_be_done() {
        return should_be_done;
    }

    public void setShould_be_done(int should_be_done) {
        this.should_be_done = should_be_done;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ChapterDetails> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<ChapterDetails> chapterDetails) {
        this.chapters = chapterDetails;
    }
}