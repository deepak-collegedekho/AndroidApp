package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.jr.ob.impl.DeferredMap;

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

    public void setChoices(ArrayList<ArrayList<Object>> choices) {
        this.choices = new ArrayList<>();
        for (ArrayList c : choices) {
            this.choices.add(c.get(1).toString());
        }
        //this.choices = choices;
        System.out.println();
    }


    public void setSecondary(ArrayList<Object> secondary) {
        this.secondary = new ArrayList<>();
        for (Object o : secondary) {
            DeferredMap d = (DeferredMap) o;
            Choice c = new Choice();
            c.setField(d.get("field").toString());
            c.setRequired(Boolean.parseBoolean(d.get("required").toString()));
            c.setType(d.get("type").toString());
            c.setText(d.get("text").toString());
            ArrayList<String> choice = new ArrayList<>();
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
                        }
                    }
                }
            }
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

    /*public void setSecondary(ArrayList<Choice> secondary) {
        this.secondary = secondary;
    }*/
}
