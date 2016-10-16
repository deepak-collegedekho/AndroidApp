package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harshvardhan on 04/10/16.
 */
public class NotificationPayload implements Parcelable{

    //if this is not populated, notification_news title will be shown in place of this
    private String big_title = "";
    //if this is not populated, expanded notification_news will not be shown
    //if this is wrongly populated, default image i.e. collegedekho banner will be shown.
    private String big_image = "";
    //if this is not populated, body will be shown in place of this
    private String big_body = "";
    //if this is not populated, notification_news will not be shown
    private String title = "";
    //
    private String body = "";
    //if this is not populated, nothing will open up, but just the app home page
    //if this is wrongly populated, there will be error 404 most probably
    private String resource_uri = "";
    //
    private String icon = "";
    //if this is not populated, nothing will open up, but just the app home page
    private String screen = "";
    //this is required for stuff n ol
    private String notification_id = "";

    public NotificationPayload(){
        // required empty contructor
    }

    protected NotificationPayload(Parcel in) {
        title = in.readString();
        body = in.readString();
        resource_uri = in.readString();
        notification_id = in.readString();
        big_title = in.readString();
        big_body = in.readString();
        big_image = in.readString();
        icon = in.readString();
        screen = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(resource_uri);
        dest.writeString(notification_id);
        dest.writeString(big_title);
        dest.writeString(big_body);
        dest.writeString(big_image);
        dest.writeString(icon);
        dest.writeString(screen);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationPayload> CREATOR = new Creator<NotificationPayload>() {
        @Override
        public NotificationPayload createFromParcel(Parcel in) {
            return new NotificationPayload(in);
        }

        @Override
        public NotificationPayload[] newArray(int size) {
            return new NotificationPayload[size];
        }
    };

    public String getBig_title() {
        return big_title;
    }

    public void setBig_title(String big_title) {
        this.big_title = big_title;
    }

    public String getBig_image() {
        return big_image;
    }

    public void setBig_image(String big_image) {
        this.big_image = big_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getBig_body() {
        return big_body;
    }

    public void setBig_body(String big_body) {
        this.big_body = big_body;
    }
}
