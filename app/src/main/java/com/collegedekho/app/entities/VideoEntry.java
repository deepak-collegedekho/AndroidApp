package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bashir on 11/2/16.
 */
public class VideoEntry implements Parcelable{
    public String text="Video";
    public String videoId;
    public String duration="00:00:00";
    public String viewCount="0";
    public String publishedOn;

    public VideoEntry(){

    }
    protected VideoEntry(Parcel in) {
        text = in.readString();
        videoId = in.readString();
        duration = in.readString();
        viewCount = in.readString();
        publishedOn=in.readString();
    }

    public static final Creator<VideoEntry> CREATOR = new Creator<VideoEntry>() {
        @Override
        public VideoEntry createFromParcel(Parcel in) {
            return new VideoEntry(in);
        }

        @Override
        public VideoEntry[] newArray(int size) {
            return new VideoEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(videoId);
        dest.writeString(duration);
        dest.writeString(viewCount);
        dest.writeString(publishedOn);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }
}
