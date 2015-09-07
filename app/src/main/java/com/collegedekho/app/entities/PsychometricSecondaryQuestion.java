package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 19/07/15
 */
public class PsychometricSecondaryQuestion implements Parcelable {

    public static final Creator<PsychometricSecondaryQuestion> CREATOR = new Creator<PsychometricSecondaryQuestion>() {
        @Override
        public PsychometricSecondaryQuestion createFromParcel(Parcel source) {
            return new PsychometricSecondaryQuestion(source);
        }

        @Override
        public PsychometricSecondaryQuestion[] newArray(int size) {
            return new PsychometricSecondaryQuestion[size];
        }
    };

    private String field;
    private ArrayList<String> choices;
    private ArrayList<String> choiceIds;
    private boolean required;
    private String type;
    private String text;

    public PsychometricSecondaryQuestion() {

    }


    public PsychometricSecondaryQuestion(Parcel source) {
        field = source.readString();
        choices = source.createStringArrayList();
        choiceIds = source.createStringArrayList();
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

    public ArrayList<String> getChoiceIds() {
        return choiceIds;
    }

    public void setChoiceIds(ArrayList<String> choiceIds) {
        this.choiceIds = choiceIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(field);
        dest.writeStringList(choices);
        dest.writeStringList(choiceIds);
        dest.writeInt(required ? 1 : 0);
        dest.writeString(type);
        dest.writeString(text);
    }
}
