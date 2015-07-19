package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 19/07/15
 */
public class Choice implements Parcelable {

    public static final Creator<Choice> CREATOR = new Creator<Choice>() {
        @Override
        public Choice createFromParcel(Parcel source) {
            return new Choice(source);
        }

        @Override
        public Choice[] newArray(int size) {
            return new Choice[0];
        }
    };
    private String field;
    private ArrayList<String> choices;
    private boolean required;
    private String type;
    private String text;

    public Choice() {

    }


    public Choice(Parcel source) {
        field = source.readString();
        choices = source.createStringArrayList();
        required = source.readInt() == 1;
        type = source.readString();
        text = source.readString();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(field);
        dest.writeStringList(choices);
        dest.writeInt(required ? 1 : 0);
        dest.writeString(type);
        dest.writeString(text);
    }
}
