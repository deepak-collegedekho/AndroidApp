package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Courses implements Parcelable {
    public static final Creator<Courses> CREATOR = new Creator<Courses>() {
        @Override
        public Courses createFromParcel(Parcel source) {
            return new Courses(source);
        }

        @Override
        public Courses[] newArray(int size) {
            return new Courses[size];
        }
    };
    private String id;
    private String name;
    private boolean isSelected;

    public Courses() {
    }

    public Courses(Parcel source) {
        id = source.readString();
        name = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
