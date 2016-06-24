package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Bashir on 17/12/15.
 */
public class PsychometricTestQuestions implements Parcelable {
    private ArrayList<PsychometricTestQuestion> questions;


    public PsychometricTestQuestions() {
    }

    public PsychometricTestQuestions(Parcel in) {
        questions = in.createTypedArrayList(PsychometricTestQuestion.CREATOR);
    }

    public static final Creator<PsychometricTestQuestion> CREATOR = new Creator<PsychometricTestQuestion>() {
        @Override
        public PsychometricTestQuestion createFromParcel(Parcel in) {
            return new PsychometricTestQuestion(in);
        }

        @Override
        public PsychometricTestQuestion[] newArray(int size) {
            return new PsychometricTestQuestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(questions);
    }
}
