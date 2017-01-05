package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 14/08/15.
 */
public class MyFutureBuddy implements Parcelable {
    public static final Creator<MyFutureBuddy> CREATOR = new Creator<MyFutureBuddy>() {
        @Override
        public MyFutureBuddy createFromParcel(Parcel source) {
            return new MyFutureBuddy(source);
        }

        @Override
        public MyFutureBuddy[] newArray(int size) {
            return new MyFutureBuddy[size];
        }
    };

    private String resource_uri;
    private String institute_name;
    private String city_name;
    private String state_name;
    private int comments_count;
    private int members_count;
    private int index;
    private ArrayList<MyFutureBuddyComment> futureBuddiesCommentsSet;
    private String next;
    private boolean isCounselor;


    public MyFutureBuddy() {
    }

    public MyFutureBuddy(Parcel source) {
        resource_uri = source.readString();
        institute_name = source.readString();
        city_name = source.readString();
        state_name = source.readString();
        comments_count = source.readInt();
        members_count = source.readInt();
        index = source.readInt();
        futureBuddiesCommentsSet = source.createTypedArrayList(MyFutureBuddyComment.CREATOR);
        if(futureBuddiesCommentsSet == null)
            futureBuddiesCommentsSet=new ArrayList<>();
        source.readTypedList(futureBuddiesCommentsSet, MyFutureBuddyComment.CREATOR);
        next = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resource_uri);
        dest.writeString(institute_name);
        dest.writeString(city_name);
        dest.writeString(state_name);
        dest.writeInt(comments_count);
        dest.writeInt(members_count);
        dest.writeInt(index);
        dest.writeTypedList(futureBuddiesCommentsSet);
        dest.writeString(next);
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

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
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

    public ArrayList<MyFutureBuddyComment> getFutureBuddiesCommentsSet() {
        return futureBuddiesCommentsSet;
    }

    public void setFutureBuddiesCommentsSet(ArrayList<MyFutureBuddyComment> futureBuddiesCommentsSet) {
        this.futureBuddiesCommentsSet = futureBuddiesCommentsSet;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCounselor() {
        return isCounselor;
    }

    public void setCounselor(boolean counselor) {
        isCounselor = counselor;
    }
}