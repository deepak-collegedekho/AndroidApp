package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Harsh Vardhan
 *         Created: 04/09/15
 */
public class Filters implements Parcelable {
    private String key;
    private String value;

    protected Filters(Parcel in) {
    }

    public static final Creator<Filters> CREATOR = new Creator<Filters>()
    {
        @Override
        public Filters createFromParcel(Parcel in) {
            return new Filters(in);
        }

        @Override
        public Filters[] newArray(int size) {
            return new Filters[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }
}