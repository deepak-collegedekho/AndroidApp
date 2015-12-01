package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class Exam implements Parcelable{

    private String mExamName;
    private String mYear;

    public Exam(){
        // required empty constructor
    }

    protected Exam(Parcel in) {
        mExamName = in.readString();
        mYear = in.readString();
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
        dest.writeString(mExamName);
        dest.writeString(mYear);
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getmExamName() {
        return mExamName;
    }

    public void setmExamName(String mExamName) {
        this.mExamName = mExamName;
    }
}
