package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

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
    private String uri;
    private String label;
    private boolean isSelected;

    public StepByStepChoice() {
    }

    public StepByStepChoice(Parcel source) {
        id = source.readInt();
        name = source.readString();
        image = source.readString();
        uri=source.readString();
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
        dest.writeString(uri);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.name = label;
    }
}
