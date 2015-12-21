package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Harsh Vardhan
 *         Created: 16/12/15
 */
public class StepByStepChoice implements Parcelable {
    public static final Creator<StepByStepChoice> CREATOR = new Creator<StepByStepChoice>() {
        @Override
        public StepByStepChoice createFromParcel(Parcel source) {
            return new StepByStepChoice(source);
        }

        @Override
        public StepByStepChoice[] newArray(int size) {
            return new StepByStepChoice[size];
        }
    };

    private int id;
    private String name;
    private String image;

    public StepByStepChoice() {
    }

    public StepByStepChoice(Parcel source) {
        id = source.readInt();
        name = source.readString();
        image = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
