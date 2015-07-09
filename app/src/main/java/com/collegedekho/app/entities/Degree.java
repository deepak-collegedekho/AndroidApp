package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Degree implements Parcelable {
    public static final Creator<Degree> CREATOR = new Creator<Degree>() {
        @Override
        public Degree createFromParcel(Parcel source) {
            return new Degree(source);
        }

        @Override
        public Degree[] newArray(int size) {
            return new Degree[size];
        }
    };
    private String url;
    private String name;
    private String short_name;
    private String uri;
    private int level;
    private boolean i;
    private ArrayList<String> numbers;

    public Degree() {
    }

    public Degree(Parcel source) {
        url = source.readString();
        name = source.readString();
        short_name = source.readString();
        uri = source.readString();
        level = source.readInt();
        i = source.readInt() == 1;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(short_name);
        dest.writeString(uri);
        dest.writeInt(level);
        dest.writeInt(i ? 1 : 0);
    }

    @Override
    public String toString() {
        return name + ", " + url + ", " + short_name;
    }
}
