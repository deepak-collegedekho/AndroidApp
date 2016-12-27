package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harshvardhan on 14/08/15.
 */
public class MyFutureBuddyComment implements Parcelable {
    public static final Creator<MyFutureBuddyComment> CREATOR = new Creator<MyFutureBuddyComment>() {
        @Override
        public MyFutureBuddyComment createFromParcel(Parcel source) {
            return new MyFutureBuddyComment(source);
        }

        @Override
        public MyFutureBuddyComment[] newArray(int size) {
            return new MyFutureBuddyComment[size];
        }
    };

    private String user;
    private String comment;
    private String added_on;
    private int index;
    private int fbIndex;
    private int is_user;
    private String token;
    private boolean commentSent;
    private String  user_image;

    public MyFutureBuddyComment() {
    }

    public MyFutureBuddyComment(Parcel source) {
        user = source.readString();
        comment = source.readString();
        added_on = source.readString();
        index = source.readInt();
        is_user = source.readInt();
        fbIndex = source.readInt();
        token = source.readString();
        user_image = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(comment);
        dest.writeString(added_on);
        dest.writeInt(index);
        dest.writeInt(is_user);
        dest.writeInt(fbIndex);
        dest.writeString(token);
        dest.writeString(user_image);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAdded_on() {
        return added_on;
    }

    public void setAdded_on(String added_on) {
        this.added_on = added_on;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFbIndex() {
        return fbIndex;
    }

    public void setFbIndex(int fbIndex) {
        this.fbIndex = fbIndex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCommentSent() {
        return commentSent;
    }

    public void setCommentSent(boolean commentSent) {
        this.commentSent = commentSent;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public int getIs_user() {
        return is_user;
    }

    public void setIs_user(int is_user) {
        this.is_user = is_user;
    }
}