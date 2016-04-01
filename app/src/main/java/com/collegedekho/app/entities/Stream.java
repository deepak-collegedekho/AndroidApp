package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Stream implements Parcelable {
    public static final Creator<Stream> CREATOR = new Creator<Stream>() {
        @Override
        public Stream createFromParcel(Parcel source) {
            return new Stream(source);
        }

        @Override
        public Stream[] newArray(int size) {
            return new Stream[size];
        }
    };
    public String resourceUri;
    public String name;
    public String shortName;
    public String uri;
    public String image;
    public String description;
    public int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Stream() {
    }

    public Stream(Parcel source) {
        resourceUri = source.readString();
        name = source.readString();
        shortName = source.readString();
        uri = source.readString();
        image = source.readString();
        description = source.readString();
        score=source.readInt();
    }

    public String getResource_uri() {
        return resourceUri;
    }

    public void setResource_uri(String resource_uri) {
        this.resourceUri = resource_uri;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resource_uri) {
        this.resourceUri = resource_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resourceUri);
        dest.writeString(name);
        dest.writeString(shortName);
        dest.writeString(uri);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeInt(score);
    }

    public String getShort_name() {
        return shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public void setShort_name(String shortName) {
        this.shortName = shortName;
    }
}
