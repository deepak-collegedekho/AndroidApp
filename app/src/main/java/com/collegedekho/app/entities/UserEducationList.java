package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 26/11/15.
 */
public class UserEducationList implements Parcelable {
    private ArrayList<UserEducation> levels;

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserEducationList> CREATOR = new Creator<UserEducationList>() {
        @Override
        public UserEducationList createFromParcel(Parcel source) {  return new UserEducationList(source);   }

        @Override
        public UserEducationList[] newArray(int size) {  return new UserEducationList[size];   }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(levels);
    }

    public UserEducationList() {
    }

    protected UserEducationList(Parcel source) {
        levels = new ArrayList<>();
        source.readTypedList(levels, UserEducation.CREATOR);
    }

    public ArrayList<UserEducation> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<UserEducation> levels) {
        this.levels = levels;
    }
}
