package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Accredition implements Parcelable {
    public static final Creator<Accredition> CREATOR = new Creator<Accredition>() {
        @Override
        public Accredition createFromParcel(Parcel source) {
            return new Accredition(source);
        }

        @Override
        public Accredition[] newArray(int size) {
            return new Accredition[size];
        }
    };
    public String url;
    public String name;

    public Accredition() {
    }

    public Accredition(Parcel source) {
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
