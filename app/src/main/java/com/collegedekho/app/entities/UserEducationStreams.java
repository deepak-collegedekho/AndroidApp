package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harshvardhan on 26/11/15.
 */
public class UserEducationStreams implements Parcelable {
    private int id;
    private String name;

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserEducationStreams> CREATOR = new Creator<UserEducationStreams>() {
        @Override
        public UserEducationStreams createFromParcel(Parcel source) {  return new UserEducationStreams(source);   }

        @Override
        public UserEducationStreams[] newArray(int size) {  return new UserEducationStreams[size];   }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }

    public UserEducationStreams() {
    }

    protected UserEducationStreams(Parcel source)
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
