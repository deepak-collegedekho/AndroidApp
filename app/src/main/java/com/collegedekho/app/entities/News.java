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
    public String title;
    public String content;
    public String image;
    public long template_type;
    public String published_on;

    public News() {
    }

    public News(Parcel source) {
        title = source.readString();
        content = source.readString();
        image = source.readString();
        template_type = source.readLong();
        published_on = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(image);
        dest.writeLong(template_type);
        dest.writeString(published_on);
    }

    public void setTitle(String title) {
        //this.title = EntityUtils.toString(title, "UTF-8");
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTemplate_type(long template_type) {
        this.template_type = template_type;
    }

    public void setPublished_on(String published_on) {
        this.published_on = published_on;
    }
}
