package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bashir on 14/12/15.
 */
public class ChapterDetails implements Parcelable {
    String chapter_number;
    String subject_id;
    String chapter_id;
    String subject_name;
    String chapter_name;
    String yearly_exam_chapter_id;
    String days_to_complete;
    private boolean isSelected;

    public static final Creator<ChapterDetails> CREATOR = new Creator<ChapterDetails>() {
        @Override
        public ChapterDetails createFromParcel(Parcel in) {
            return new ChapterDetails(in);
        }

        @Override
        public ChapterDetails[] newArray(int size) {
            return new ChapterDetails[size];
        }
    };

    public ChapterDetails(){

    }
    public ChapterDetails(Parcel in) {
        chapter_number = in.readString();
        subject_id = in.readString();
        chapter_id = in.readString();
        subject_name = in.readString();
        chapter_name = in.readString();
        yearly_exam_chapter_id = in.readString();
        days_to_complete = in.readString();
        isSelected=in.readByte()!=0;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getChapter_number() {
        return chapter_number;
    }

    public void setChapter_number(String chapter_number) {
        this.chapter_number = chapter_number;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getYearly_exam_chapter_id() {
        return yearly_exam_chapter_id;
    }

    public void setYearly_exam_chapter_id(String yearly_exam_chapter_id) {
        this.yearly_exam_chapter_id = yearly_exam_chapter_id;
    }

    public String getDays_to_complete() {
        return days_to_complete;
    }

    public void setDays_to_complete(String days_to_complete) {
        this.days_to_complete = days_to_complete;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chapter_number);
        dest.writeString(subject_id);
        dest.writeString(chapter_id);
        dest.writeString(days_to_complete);
        dest.writeString(subject_name);
        dest.writeString(chapter_name);
        dest.writeString(yearly_exam_chapter_id);
        dest.writeString(days_to_complete);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
