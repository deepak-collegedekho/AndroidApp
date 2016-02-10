package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Harsh Vardhan
 *         Created: 10/12/15
 */
public class Units implements Parcelable {

    public static final Creator<Units> CREATOR = new Creator<Units>() {
        @Override
        public Units createFromParcel(Parcel source) {
            return new Units(source);
        }

        @Override
        public Units[] newArray(int size) {
            return new Units[size];
        }
    };

    public int unit_id;
    public int subject_id;
    public float unit_done_percent;
    public String unit_name;
    public String subject_name;
    public ArrayList<Chapters> chapters;
    public int is_done = -1;
    private int total_weight;

    public Units() {

    }

    public Units(Parcel source) {
        unit_id = source.readInt();
        unit_done_percent = source.readFloat();
        unit_name = source.readString();
        chapters = new ArrayList<Chapters>();
        total_weight=source.readInt();
        source.readTypedList(chapters, Chapters.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(unit_id);
        dest.writeFloat(unit_done_percent);
        dest.writeString(unit_name);
        dest.writeTypedList(chapters);
        dest.writeInt(total_weight);
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getUnit_done_percent() {
        return Math.round(unit_done_percent);
    }

    public void setUnit_done_percent(float unit_done_percent) {
        this.unit_done_percent = unit_done_percent;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public ArrayList<Chapters> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Chapters> chapters) {
        this.chapters = chapters;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }

    public int getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(int total_weight) {
        this.total_weight = total_weight;
    }
}
