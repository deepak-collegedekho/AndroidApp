package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sureshsaini on 14/12/15.
 */
public class ExamSummary implements Parcelable {



    private String yearly_exam_id;
    private int syllabus_covered;
    private int backup_count;
    private int shortlist_count;
    private int  recommended_count;
    private String  next_important_date;

    public String getPreference_uri() {
        return preference_uri;
    }

    public void setPreference_uri(String preference_uri) {
        this.preference_uri = preference_uri;
    }

    private String preference_uri;

    public ExamSummary(){
        // required empty constructor
    }


    protected ExamSummary(Parcel in) {
        yearly_exam_id = in.readString();
        syllabus_covered = in.readInt();
        backup_count = in.readInt();
        shortlist_count = in.readInt();
        recommended_count = in.readInt();
        next_important_date = in.readString();
        preference_uri=in.readString();
    }

    public static final Creator<ExamSummary> CREATOR = new Creator<ExamSummary>() {
        @Override
        public ExamSummary createFromParcel(Parcel in) {
            return new ExamSummary(in);
        }

        @Override
        public ExamSummary[] newArray(int size) {
            return new ExamSummary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(yearly_exam_id);
        dest.writeInt(syllabus_covered);
        dest.writeInt(backup_count);
        dest.writeInt(shortlist_count);
        dest.writeInt(recommended_count);
        dest.writeString(next_important_date);
        dest.writeString(preference_uri);
    }

    public String getYearly_exam_id() {
        return yearly_exam_id;
    }

    public void setYearly_exam_id(String yearly_exam_id) {
        this.yearly_exam_id = yearly_exam_id;
    }

    public int getSyllabus_covered() {
        return syllabus_covered;
    }

    public void setSyllabus_covered(int syllabus_covered) {
        this.syllabus_covered = syllabus_covered;
    }

    public int getBackup_count() {
        return backup_count;
    }

    public void setBackup_count(int backup_count) {
        this.backup_count = backup_count;
    }

    public int getShortlist_count() {
        return shortlist_count;
    }

    public void setShortlist_count(int shortlist_count) {
        this.shortlist_count = shortlist_count;
    }

    public int getRecommended_count() {
        return recommended_count;
    }

    public void setRecommended_count(int recommended_count) {
        this.recommended_count = recommended_count;
    }

    public String getNext_important_date() {
        return next_important_date;
    }

    public void setNext_important_date(String next_important_date) {
        this.next_important_date = next_important_date;
    }
}
