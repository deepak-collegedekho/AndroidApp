package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bashir on 9/2/16.
 */
public class Snippet implements Parcelable {
    private String publishedAt;
    private String channelId;
    private String title;
    private String description;

    public Snippet() {
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected Snippet(Parcel in) {
        publishedAt = in.readString();
        channelId = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<Snippet> CREATOR = new Creator<Snippet>() {
        @Override
        public Snippet createFromParcel(Parcel in) {
            return new Snippet(in);
        }

        @Override
        public Snippet[] newArray(int size) {
            return new Snippet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(publishedAt);
        dest.writeString(channelId);
        dest.writeString(title);
        dest.writeString(description);
    }
}

