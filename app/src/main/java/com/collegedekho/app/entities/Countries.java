package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Countries implements Parcelable {
    public static final Creator<Countries> CREATOR = new Creator<Countries>() {
        @Override
        public Countries createFromParcel(Parcel source) {
            return new Countries(source);
        }

        @Override
        public Countries[] newArray(int size) {
            return new Countries[size];
        }
    };
    public String url;
    public String name;

    public Countries() {
    }

    public Countries(Parcel source) {
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
