package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Harsh Vardhan
 *         Created: 17/11/15
 */
public class Images implements Parcelable {
    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
    public String Banner;
    public String Primary;
    public String Student;
    public String Infra;

    public Images() {
    }

    public Images(Parcel source) {
        Banner = source.readString();
        Primary = source.readString();
        Student = source.readString();
        Infra = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Banner);
        dest.writeString(Primary);
        dest.writeString(Student);
        dest.writeString(Infra);
    }

    public String getPrimary() {
        return Primary;
    }

    public void setPrimary(String primary) {
        Primary = primary;
    }

    public String getStudent() {
        return Student;
    }

    public void setStudent(String student) {
        Student = student;
    }

    public String getInfra() {
        return Infra;
    }

    public void setInfra(String infra) {
        Infra = infra;
    }

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String banner) {
        Banner = banner;
    }
}
