package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Harsh Vardhan
 *         Created: 16/12/15
 */
public class StepByStepQuestion implements Parcelable {
    public static final Creator<StepByStepQuestion> CREATOR = new Creator<StepByStepQuestion>() {
        @Override
        public StepByStepQuestion createFromParcel(Parcel source) {
            return new StepByStepQuestion(source);
        }

        @Override
        public StepByStepQuestion[] newArray(int size) {
            return new StepByStepQuestion[size];
        }
    };

    private String name;
    private String text;
    private int image;
    private boolean required;
    private ArrayList<StepByStepChoice> choices;
    private String type;
    private ArrayList<StepByStepChoice> other_choices;

    private static CurrentLevels CURRENT_LEVEL;

    public StepByStepQuestion() {
    }

    public StepByStepQuestion(Parcel source) {
        text = source.readString();
        name = source.readString();
        image = source.readInt();
        required = source.readInt() == 1;
        choices = new ArrayList<>();
        source.readTypedList(choices, StepByStepChoice.CREATOR);
        type = source.readString();
        other_choices = new ArrayList<>();
        source.readTypedList(other_choices, StepByStepChoice.CREATOR);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(name);
        dest.writeInt(image);
        dest.writeInt(required ? 1 : 0);
        dest.writeTypedList(choices);
        dest.writeString(type);
        dest.writeTypedList(other_choices);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ArrayList<StepByStepChoice> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<StepByStepChoice> choices) {
        this.choices = choices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<StepByStepChoice> getOther_choices() {
        return other_choices;
    }

    public void setOther_choices(ArrayList<StepByStepChoice> other_choices) {
        this.other_choices = other_choices;
    }

    public enum CurrentLevels {
        IN_SCHOOL, GRADUATE_COLLEGE, POSTGRADUATE_COLLEGE;

        public static String[] getValues()
        {
            String[] x = new String[values().length];

            for (int i = 0; i < x.length; i++) {
                x[i] = values()[i].name().replace("_", " ");
            }
            return x;
        }
        public static String getName(int index)
        {
            return  values()[index].name().replace("_", " ");
        }
    }

    public static CurrentLevels getCurrentLevel() {
        return StepByStepQuestion.CURRENT_LEVEL;
    }

    public static void setCurrentLevel(CurrentLevels currentLevel) {
        StepByStepQuestion.CURRENT_LEVEL = currentLevel;
    }
}
