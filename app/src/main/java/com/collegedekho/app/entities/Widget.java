package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 09/07/15
 */
public class Widget implements Parcelable {

    public static final Creator<Widget> WIDGET_CREATOR = new Creator<Widget>() {
        @Override
        public Widget createFromParcel(Parcel source) {
            return new Widget(source);
        }

        @Override
        public Widget[] newArray(int size) {
            return new Widget[size];
        }
    };
    private long id;
    private String resource_uri;
    private String title;
    private String action_method;
    private String action_url;
    private String type;
    private String image;

    public Widget(Parcel source) {
        id = source.readLong();
        resource_uri = source.readString();
        title = source.readString();
        action_method = source.readString();
        action_url = source.readString();
        type = source.readString();
        image = source.readString();
    }

    public Widget() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(resource_uri);
        dest.writeString(title);
        dest.writeString(action_method);
        dest.writeString(action_url);
        dest.writeString(type);
        dest.writeString(image);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction_method() {
        return action_method;
    }

    public void setAction_method(String action_method) {
        this.action_method = action_method;
    }

    public String getAction_url() {
        return action_url;
    }

    public void setAction_url(String action_url) {
        this.action_url = action_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
