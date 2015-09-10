package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.jr.ob.impl.DeferredMap;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 19/07/15
 */
public class PsychometricPrimaryQuestion implements Parcelable {
    public static final Creator<PsychometricPrimaryQuestion> CREATOR = new Creator<PsychometricPrimaryQuestion>() {
        @Override
        public PsychometricPrimaryQuestion createFromParcel(Parcel source) {
            return new PsychometricPrimaryQuestion(source);
        }

        @Override
        public PsychometricPrimaryQuestion[] newArray(int size) {
            return new PsychometricPrimaryQuestion[size];
        }
    };

    public String text;
    public boolean required;
    public ArrayList<String> choices;
    public ArrayList<String> choiceIDs;
    public String field;
    public String type;
    public ArrayList<PsychometricSecondaryQuestion> secondary;

    public PsychometricPrimaryQuestion() {
    }

    public PsychometricPrimaryQuestion(Parcel source) {
        text = source.readString();
        required = source.readInt() == 1;
        choices = source.createStringArrayList();
        choiceIDs = source.createStringArrayList();
        field = source.readString();
        type = source.readString();
        secondary = source.createTypedArrayList(PsychometricSecondaryQuestion.CREATOR);
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
        dest.writeStringList(choiceIDs);
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

    public void setChoices(ArrayList<ArrayList<Object>> choices) {
        this.choices = new ArrayList<>();
        this.choiceIDs = new ArrayList<>();
        for (ArrayList c : choices) {
            this.choices.add(c.get(1).toString());
            this.choiceIDs.add(c.get(0).toString());
        }
        //this.choices = choices;
        System.out.println();
    }

    public ArrayList<String> getChoiceIDs() {
        return choiceIDs;
    }

    public void setChoiceIDs(ArrayList<ArrayList<Object>> choiceIDs) {
        this.choiceIDs = new ArrayList<>();
        for (ArrayList c : choiceIDs) {
            this.choices.add(c.get(0).toString());
        }
    }


    public void setSecondary(ArrayList<Object> secondary) {
        this.secondary = new ArrayList<>();
        for (Object o : secondary) {
            DeferredMap d = (DeferredMap) o;
            PsychometricSecondaryQuestion c = new PsychometricSecondaryQuestion();
            c.setField(d.get("field").toString());
            c.setRequired(Boolean.parseBoolean(d.get("required").toString()));
            c.setType(d.get("type").toString());
            c.setText(d.get("text").toString());
            ArrayList<String> choice = new ArrayList<>();
            ArrayList<String> choiceIds = new ArrayList<>();
            if (c.getType().equals("range")) {
                Object obj = d.get("range");
                if (obj instanceof ArrayList) {
                    for (Object obj1 : (ArrayList) obj) {
                        choice.add(obj1.toString());
                    }
                }
            } else {
                Object obj = d.get("choices");
                if (obj instanceof ArrayList) {
                    for (Object obj1 : (ArrayList) obj) {
                        if (obj1 instanceof ArrayList) {
                            choice.add(((ArrayList) obj1).get(1).toString());
                            choiceIds.add(((ArrayList) obj1).get(0).toString());
                        }
                    }
                }
            }
            c.setChoiceIds(choiceIds);
            c.setChoices(choice);
            this.secondary.add(c);
        }
        //this.choices = choices;
        System.out.println();
    }

    /*public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }*/

    public void setField(String field) {
        this.field = field;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public boolean isRequired() {
        return required;
    }

    public String getField() {
        return field;
    }

    public String getType() {
        return type;
    }

    public ArrayList<PsychometricSecondaryQuestion> getSecondary() {
        return secondary;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    /*public void setSecondary(ArrayList<PsychometricSecondaryQuestion> secondary) {
        this.secondary = secondary;
    }*/
}
