package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class News implements Parcelable {
    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
    public String url;
    public String title;
    public String content;
    public String image;
    public long template_type;
    public String snippet;
    public String slug;
    public String published_on;
    public String stream;
    public String institute;
    public String university;
    public String city;
    public String state;

    public News() {
    }

    public News(Parcel source) {
        url = source.readString();
        title = source.readString();
        content = source.readString();
        image = source.readString();
        template_type = source.readLong();
        snippet = source.readString();
        slug = source.readString();
        published_on = source.readString();
        stream = source.readString();
        institute = source.readString();
        university = source.readString();
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
        dest.writeString(content);
        dest.writeString(image);
        dest.writeLong(template_type);
        dest.writeString(snippet);
        dest.writeString(slug);
        dest.writeString(published_on);
        dest.writeString(stream);
        dest.writeString(institute);
        dest.writeString(university);
        dest.writeString(city);
        dest.writeString(state);
    }


}
