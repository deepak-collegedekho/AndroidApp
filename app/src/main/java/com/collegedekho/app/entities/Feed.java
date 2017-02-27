package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harshvardhan on 15/11/16.
 */

public class Feed implements Parcelable {

    private String title = "";
    //if this is not populated, nothing will open up, but just the app home page
    //if this is wrongly populated, there will be error 404 most probably
    private String resource_uri = "";
    private String web_resource_uri = "";
    private String icon = "";
    private String image = "";
    private String description = "";
    private String feed_time = "";
    //if this is not populated, nothing will open up, but just the app home page
    private String screen = "";
    //this is required for stuff n ol
    private int id = 0;
    private String feed_background_color = "";
    private String title_color = "";
    private String time_color = "";
    private String description_color = "";
    private String result = "";

    public Feed(){

    }

    public Feed(Parcel in) {
        title = in.readString();
        resource_uri = in.readString();
        web_resource_uri = in.readString();
        id = in.readInt();
        image = in.readString();
        icon = in.readString();
        screen = in.readString();
        description = in.readString();
        feed_time = in.readString();
        feed_background_color = in.readString();
        title_color = in.readString();
        time_color = in.readString();
        description_color = in.readString();
        result = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(resource_uri);
        parcel.writeString(web_resource_uri);
        parcel.writeInt(id);
        parcel.writeString(image);
        parcel.writeString(icon);
        parcel.writeString(screen);
        parcel.writeString(description);
        parcel.writeString(feed_time);
        parcel.writeString(feed_background_color);
        parcel.writeString(title_color);
        parcel.writeString(time_color);
        parcel.writeString(description_color);
        parcel.writeString(result);

    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public String getWeb_resource_uri() {
        return web_resource_uri;
    }

    public void setWeb_resource_uri(String web_resource_uri) {
        this.web_resource_uri = web_resource_uri;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getFeed_time() {
        return feed_time;
    }

    public void setFeed_time(String feed_time) {
        this.feed_time = feed_time;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeed_background_color() {
        return feed_background_color;
    }

    public void setFeed_background_color(String feed_background_color) {
        this.feed_background_color = feed_background_color;
    }

    public String getTitle_color() {
        return title_color;
    }

    public void setTitle_color(String title_color) {
        this.title_color = title_color;
    }

    public String getTime_color() {
        return time_color;
    }

    public void setTime_color(String time_color) {
        this.time_color = time_color;
    }

    public String getDescription_color() {
        return description_color;
    }

    public void setDescription_color(String description_color) {
        this.description_color = description_color;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
