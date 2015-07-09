package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Courses implements Parcelable {
    public static final Creator<Courses> CREATOR = new Creator<Courses>() {
        @Override
        public Courses createFromParcel(Parcel source) {
            return new Courses(source);
        }

        @Override
        public Courses[] newArray(int size) {
            return new Courses[size];
        }
    };
    public String url;
    public String name;
    public String about;
    public String stream;
    public String degree;
    public String specialization;

    public Courses() {
    }

    public Courses(Parcel source) {
        url = source.readString();
        name = source.readString();
        about = source.readString();
        stream = source.readString();
        degree = source.readString();
        specialization = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(about);
        dest.writeString(stream);
        dest.writeString(degree);
        dest.writeString(specialization);
    }


}
