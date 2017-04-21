package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sureshsaini on 24/5/16.
 */
public class ProfileSpinnerItem implements Parcelable{

    private int id;
    private String name;
    private boolean isSelected;
    private int institutes_count;

    public ProfileSpinnerItem(){
        // required empty constructor
    }

    protected ProfileSpinnerItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        isSelected = in.readByte() != 0;
        institutes_count = in.readInt();
    }

    public static final Creator<ProfileSpinnerItem> CREATOR = new Creator<ProfileSpinnerItem>() {
        @Override
        public ProfileSpinnerItem createFromParcel(Parcel in) {
            return new ProfileSpinnerItem(in);
        }

        @Override
        public ProfileSpinnerItem[] newArray(int size) {
            return new ProfileSpinnerItem[size];
        }
    };

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getInstitutes_count() {
        return institutes_count;
    }

    public void setInstitutes_count(int institutes_count) {
        this.institutes_count = institutes_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeInt(institutes_count);
    }
}
