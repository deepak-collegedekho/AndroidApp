package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bashir on 10/2/16.
 */
public class VideoContentDetails implements Parcelable {
    String duration;
    String dimension;
    String definition;
    boolean caption;
    boolean licensedContent;

    public VideoContentDetails() {
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public boolean isCaption() {
        return caption;
    }

    public void setCaption(boolean caption) {
        this.caption = caption;
    }

    public boolean isLicensedContent() {
        return licensedContent;
    }

    public void setLicensedContent(boolean licensedContent) {
        this.licensedContent = licensedContent;
    }

    protected VideoContentDetails(Parcel in) {
        duration = in.readString();
        dimension = in.readString();
        definition = in.readString();
        caption = in.readByte() != 0;
        licensedContent = in.readByte() != 0;
    }

    public static final Creator<VideoContentDetails> CREATOR = new Creator<VideoContentDetails>() {
        @Override
        public VideoContentDetails createFromParcel(Parcel in) {
            return new VideoContentDetails(in);
        }

        @Override
        public VideoContentDetails[] newArray(int size) {
            return new VideoContentDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(duration);
        dest.writeString(dimension);
        dest.writeString(definition);
        dest.writeByte((byte) (caption ? 1 : 0));
        dest.writeByte((byte) (licensedContent ? 1 : 0));
    }
}
