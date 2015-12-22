package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Bashir on 22/12/15.
 */
public class MyAlertDate implements Parcelable {
    private int count;
    private int year;
    private int month;
    private ArrayList<MyAlertDateDescription> dates;

    public MyAlertDate() {
    }

    protected MyAlertDate(Parcel in) {
        count = in.readInt();
        year = in.readInt();
        month = in.readInt();
        dates = in.createTypedArrayList(MyAlertDateDescription.CREATOR);
    }

    public static final Creator<MyAlertDate> CREATOR = new Creator<MyAlertDate>() {
        @Override
        public MyAlertDate createFromParcel(Parcel in) {
            return new MyAlertDate(in);
        }

        @Override
        public MyAlertDate[] newArray(int size) {
            return new MyAlertDate[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<MyAlertDateDescription> getDates() {
        return dates;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDates(ArrayList<MyAlertDateDescription> dates) {
        this.dates = dates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeTypedList(dates);
    }
}
