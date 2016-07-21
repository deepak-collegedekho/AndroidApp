package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harshvardhan on 10/08/15.
 */
public class MyFutureBuddiesEnumeration implements Parcelable {
    public static final Creator<MyFutureBuddiesEnumeration> CREATOR = new Creator<MyFutureBuddiesEnumeration>() {
        @Override
        public MyFutureBuddiesEnumeration createFromParcel(Parcel source) {
            return new MyFutureBuddiesEnumeration(source);
        }

        @Override
        public MyFutureBuddiesEnumeration[] newArray(int size) {
            return new MyFutureBuddiesEnumeration[size];
        }
    };

    private String resource_uri;
    private String institute_name;
    private String institute_logo;
    private int comments_count;
    private int members_count;
    private int unread_count;
    private int index;
    private String city_name;
    private String state_name;

    public MyFutureBuddiesEnumeration() {
    }

    public MyFutureBuddiesEnumeration(Parcel source) {
        resource_uri = source.readString();
        institute_name = source.readString();
        institute_logo = source.readString();
        city_name = source.readString();
        state_name = source.readString();
        comments_count = source.readInt();
        members_count = source.readInt();
        index = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resource_uri);
        dest.writeString(institute_name);
        dest.writeString(institute_logo);
        dest.writeString(city_name);
        dest.writeString(state_name);
        dest.writeInt(comments_count);
        dest.writeInt(members_count);
        dest.writeInt(index);
    }

    public int getUnread_count() {
        return unread_count;
    }
    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public String getInstitute_name() {
        return institute_name;
    }

    public void setInstitute_name(String institute_name) {
        this.institute_name = institute_name;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getMembers_count() {
        return members_count;
    }

    public void setMembers_count(int members_count) {
        this.members_count = members_count;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getInstitute_logo() {
        return institute_logo;
    }

    public void setInstitute_logo(String institute_logo) {
        this.institute_logo = institute_logo;
    }
}