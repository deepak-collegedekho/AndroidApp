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
    public String resourseUri;
    public String name;
    public String shortName;
    public String uri;
    public String image;
    public String description;

    public Stream() {
    }

    public Stream(Parcel source) {
        resourseUri = source.readString();
        name = source.readString();
        shortName = source.readString();
        uri = source.readString();
        image = source.readString();
        description = source.readString();

    }

    public String getResourse_uri() {
        return resourseUri;
    }

    public void setResourse_uri(String resourse_uri) {
        this.resourseUri = resourse_uri;
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
        dest.writeString(resourseUri);
        dest.writeString(name);
        dest.writeString(shortName);
        dest.writeString(uri);
        dest.writeString(image);
        dest.writeString(description);
    }

    public String getShort_name() {
        return shortName;
    }

    public void setShort_name(String shortName) {
        this.shortName = shortName;
    }
}
