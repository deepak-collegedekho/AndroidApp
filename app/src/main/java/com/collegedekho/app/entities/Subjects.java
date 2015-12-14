package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Harsh Vardhan
 *         Created: 10/12/15
 */
public class Subjects implements Parcelable {

    public static final Creator<Subjects> CREATOR = new Creator<Subjects>() {
        @Override
        public Subjects createFromParcel(Parcel source) {
            return new Subjects(source);
        }

        @Override
        public Subjects[] newArray(int size) {
            return new Subjects[size];
        }
    };

    public int subject_id;
    public String subject_name;
    public int yearly_exam;
    public float subject_done_percent;
    public ArrayList<Units> units;
    public int is_done = -1;

    public Subjects() {

    }

    public Subjects(Parcel source) {
        subject_id = source.readInt();
        subject_name = source.readString();
        yearly_exam = source.readInt();
        subject_done_percent = source.readFloat();
        units = new ArrayList<Units>();
        source.readTypedList(units, Units.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(subject_id);
        dest.writeString(subject_name);
        dest.writeInt(yearly_exam);
        dest.writeFloat(subject_done_percent);
        dest.writeTypedList(units);
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getYearly_exam() {
        return yearly_exam;
    }

    public void setYearly_exam(int yearly_exam) {
        this.yearly_exam = yearly_exam;
    }

    public ArrayList<Units> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Units> units) {
        this.units = units;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }

    public int getSubject_done_percent() {
        return Math.round(subject_done_percent);
    }

    public void setSubject_done_percent(float subject_done_percent) {
        this.subject_done_percent = subject_done_percent;
    }
}
