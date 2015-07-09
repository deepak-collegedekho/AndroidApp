package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class States implements Parcelable {
    public static final Creator<States> CREATOR = new Creator<States>() {
        @Override
        public States createFromParcel(Parcel source) {
            return new States(source);
        }

        @Override
        public States[] newArray(int size) {
            return new States[size];
        }
    };
    public String url;
    public String name;
    public String country;

    public States() {
    }

    public States(Parcel source) {
        url = source.readString();
        name = source.readString();
        country = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(country);
    }


}
