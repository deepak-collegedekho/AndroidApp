package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class QuestionVotes implements Parcelable {
    public static final Creator<QuestionVotes> CREATOR = new Creator<QuestionVotes>() {
        @Override
        public QuestionVotes createFromParcel(Parcel source) {
            return new QuestionVotes(source);
        }

        @Override
        public QuestionVotes[] newArray(int size) {
            return new QuestionVotes[size];
        }
    };
    public String url;
    public Long vote_type;
    public String added_on;
    public String question;
    public String user;

    public QuestionVotes() {
    }

    public QuestionVotes(Parcel source) {
        url = source.readString();
        vote_type = source.readLong();
        added_on = source.readString();
        question = source.readString();
        user = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeLong(vote_type);
        dest.writeString(added_on);
        dest.writeString(question);
        dest.writeString(user);
    }


}
