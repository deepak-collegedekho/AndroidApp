package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Specializations implements Parcelable {
    public static final Creator<Specializations> CREATOR = new Creator<Specializations>() {
        @Override
        public Specializations createFromParcel(Parcel source) {
            return new Specializations(source);
        }

        @Override
        public Specializations[] newArray(int size) {
            return new Specializations[size];
        }
    };
    public String url;
    public String name;

    public Specializations() {
    }

    public Specializations(Parcel source) {
        url = source.readString();
        name = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
    }


}
