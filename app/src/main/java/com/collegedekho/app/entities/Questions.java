package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Questions implements Parcelable {
    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel source) {
            return new Questions(source);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };
    public String url;
    public String title;
    public String desc;
    public Long status;
    public String tags;
    public Boolean is_spam;
    public Long votes;
    public String uri_slug;
    public String added_on;
    public String user;
    public String degree;
    public String stream;
    public String institute;
    public String course;
    public String city;
    public String state;

    public Questions() {
    }

    public Questions(Parcel source) {
        url = source.readString();
        title = source.readString();
        desc = source.readString();
        status = source.readLong();
        tags = source.readString();
        is_spam = source.readInt() == 1;
        votes = source.readLong();
        uri_slug = source.readString();
        added_on = source.readString();
        user = source.readString();
        degree = source.readString();
        stream = source.readString();
        institute = source.readString();
        course = source.readString();
        city = source.readString();
        state = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeLong(status);
        dest.writeString(tags);
        dest.writeInt(is_spam ? 1 : 0);
        dest.writeLong(votes);
        dest.writeString(uri_slug);
        dest.writeString(added_on);
        dest.writeString(user);
        dest.writeString(degree);
        dest.writeString(stream);
        dest.writeString(institute);
        dest.writeString(course);
        dest.writeString(city);
        dest.writeString(state);
    }


}
