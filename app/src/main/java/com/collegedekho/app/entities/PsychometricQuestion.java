package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Harsh Vardhan
 *         Created: 20/08/15
 */
public class PsychometricQuestion implements Parcelable {
    public static final Creator<PsychometricQuestion> CREATOR = new Creator<PsychometricQuestion>() {
        @Override
        public PsychometricQuestion createFromParcel(Parcel source) {
            return new PsychometricQuestion(source);
        }

        @Override
        public PsychometricQuestion[] newArray(int size) {
            return new PsychometricQuestion[size];
        }
    };

    private String text;
    private String name;
    private boolean required;
    private HashMap<String, String> choiceMap;
    private ArrayList<String> field;
    private String type;
    private ArrayList<PsychometricQuestion> secondary;
    private boolean isSecondary = false;

    public PsychometricQuestion() {
    }

    public PsychometricQuestion(Parcel source) {
        text = source.readString();
        name = source.readString();
        required = source.readInt() == 1;
        field = source.createStringArrayList();
        type = source.readString();
        //choiceMap = source.readHashMap();

        secondary = source.createTypedArrayList(PsychometricQuestion.CREATOR);
        source.readTypedList(secondary, PsychometricQuestion.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(name);
        dest.writeInt(required ? 1 : 0);
        dest.writeStringList(field);
        dest.writeString(type);
        dest.writeTypedList(secondary);
        //dest.write
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public HashMap<String, String> getChoiceMap() {
        return choiceMap;
    }

    public void setChoiceMap(HashMap<String, String> choiceMap) {
        this.choiceMap = choiceMap;
    }

    public ArrayList<String> getField() {
        return field;
    }

    public void setField(ArrayList<String> field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<PsychometricQuestion> getSecondary() {
        return secondary;
    }

    public void setSecondary(ArrayList<PsychometricQuestion> secondary) {
        this.secondary = secondary;
    }

    public boolean isSecondary() {
        return isSecondary;
    }

    public void setIsSecondary(boolean isSecondary) {
        this.isSecondary = isSecondary;
    }

    public boolean getHasSecondary()
    {
        return secondary.size() > 0 ;
    }
}
