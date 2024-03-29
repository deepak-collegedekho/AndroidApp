package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bashir on 22/12/15.
 */
public class MyAlertDateDescription implements Parcelable {
    private int exam_id;
    private String type;
    private String date;
    private String exam_name;
    private String exam_short_name;

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_short_name() {
        return exam_short_name;
    }

    public void setExam_short_name(String exam_short_name) {
        this.exam_short_name = exam_short_name;
    }

    public MyAlertDateDescription() {
    }

    protected MyAlertDateDescription(Parcel in) {
        exam_id = in.readInt();
        type = in.readString();
        date = in.readString();
        exam_name=in.readString();
        exam_short_name=in.readString();
    }

    public static final Creator<MyAlertDateDescription> CREATOR = new Creator<MyAlertDateDescription>() {
        @Override
        public MyAlertDateDescription createFromParcel(Parcel in) {
            return new MyAlertDateDescription(in);
        }

        @Override
        public MyAlertDateDescription[] newArray(int size) {
            return new MyAlertDateDescription[size];
        }
    };

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(type);
        dest.writeInt(exam_id);
        dest.writeString(exam_name);
        dest.writeString(exam_short_name);

    }
}
