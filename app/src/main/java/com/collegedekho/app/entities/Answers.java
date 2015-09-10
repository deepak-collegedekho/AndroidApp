package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Answers implements Parcelable {
    public static final Creator<Answers> CREATOR = new Creator<Answers>() {
        @Override
        public Answers createFromParcel(Parcel source) {
            return new Answers(source);
        }

        @Override
        public Answers[] newArray(int size) {
            return new Answers[size];
        }
    };
    public String url;
    public String answer_text;
    public Long status;
    public Boolean is_spam;
    public long votes;
    public Boolean best_answer;
    public String added_on;
    public String user;
    public String question;

    public Answers() {
    }

    public Answers(Parcel source) {
        url = source.readString();
        answer_text = source.readString();
        status = source.readLong();
        is_spam = source.readInt() == 1;
        votes = source.readLong();
        best_answer = source.readInt() == 1;
        added_on = source.readString();
        user = source.readString();
        question = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(answer_text);
        dest.writeLong(status);
        dest.writeInt(is_spam ? 1 : 0);
        dest.writeLong(votes);
        dest.writeInt(best_answer ? 1 : 0);
        dest.writeString(added_on);
        dest.writeString(user);
        dest.writeString(question);
    }
}
