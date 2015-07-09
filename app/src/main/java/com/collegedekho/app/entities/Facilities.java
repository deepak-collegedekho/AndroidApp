package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Facilities implements Parcelable {
    public static final Creator<Facilities> CREATOR = new Creator<Facilities>() {
        @Override
        public Facilities createFromParcel(Parcel source) {
            return new Facilities(source);
        }

        @Override
        public Facilities[] newArray(int size) {
            return new Facilities[size];
        }
    };
    public String url;
    public String name;
    public String description;
    public String column_name;

    public Facilities() {
    }

    public Facilities(Parcel source) {
        url = source.readString();
        name = source.readString();
        description = source.readString();
        column_name = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(column_name);
    }


}
