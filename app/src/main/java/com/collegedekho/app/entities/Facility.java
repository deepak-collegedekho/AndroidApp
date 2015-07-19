package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 18/07/15
 */
public class Facility implements Parcelable {

    public static final Creator<Facility> CREATOR = new Creator<Facility>() {
        @Override
        public Facility createFromParcel(Parcel source) {
            return new Facility(source);
        }

        @Override
        public Facility[] newArray(int size) {
            return new Facility[size];
        }
    };
    public String tag;
    public String image;

    public Facility() {

    }

    public Facility(Parcel source) {
        tag = source.readString();
        image = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tag);
        dest.writeString(image);
    }
}
