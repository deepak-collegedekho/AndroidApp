package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 19/07/15
 */
public class PQuestion implements Parcelable {
    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    public String text;
    public boolean required;
    public ArrayList<String> choices;
    public String field;
    public String type;
    public ArrayList<Choice> secondary;

    public PQuestion() {
    }

    public PQuestion(Parcel source) {
        text = source.readString();
        required = source.readInt() == 1;
        choices = source.createStringArrayList();
        field = source.readString();
        type = source.readString();
        secondary = source.createTypedArrayList(Choice.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeInt(required ? 1 : 0);
        dest.writeStringList(choices);
        dest.writeString(field);
        dest.writeString(type);
        dest.writeTypedList(secondary);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setChoices(Object choices) {
        //this.choices = choices;
        System.out.println();
    }


    public void setSecondary(Object choices) {
        //this.choices = choices;
        System.out.println();
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*public void setSecondary(ArrayList<Choice> secondary) {
        this.secondary = secondary;
    }*/
}
