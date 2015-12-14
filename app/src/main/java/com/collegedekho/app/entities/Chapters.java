package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Chapters implements Parcelable {

    public static final Creator<Chapters> CREATOR = new Creator<Chapters>() {
        @Override
        public Chapters createFromParcel(Parcel source) {
            return new Chapters(source);
        }

        @Override
        public Chapters[] newArray(int size) {
            return new Chapters[size];
        }
    };

    public int weightage;
    public int should_be_done;
    public int is_done;
    public int id;
    public String name;

    public Chapters() {

    }

    public Chapters(Parcel source) {
        weightage = source.readInt();
        should_be_done = source.readInt();
        is_done = source.readInt();
        id = source.readInt();
        name = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(weightage);
        dest.writeInt(should_be_done);
        dest.writeInt(is_done);
        dest.writeInt(id);
        dest.writeString(name);
    }

    public int getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = weightage;
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

    public int getShould_be_done() {
        return should_be_done;
    }

    public void setShould_be_done(int should_be_done) {
        this.should_be_done = should_be_done;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }
}
