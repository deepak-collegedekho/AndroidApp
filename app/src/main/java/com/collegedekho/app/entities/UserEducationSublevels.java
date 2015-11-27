package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harshvardhan on 26/11/15.
 */
public class UserEducationSublevels implements Parcelable {
    private int id;
    private String name;

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserEducationSublevels> CREATOR = new Creator<UserEducationSublevels>() {
        @Override
        public UserEducationSublevels createFromParcel(Parcel source) {  return new UserEducationSublevels(source);   }

        @Override
        public UserEducationSublevels[] newArray(int size) {  return new UserEducationSublevels[size];   }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }

    public UserEducationSublevels() {
    }

    protected UserEducationSublevels(Parcel source)
    {
        name = source.readString();
        id = source.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
