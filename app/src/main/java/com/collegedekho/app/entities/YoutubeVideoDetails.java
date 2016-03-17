package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Bashir on 9/2/16.
 */
public class YoutubeVideoDetails implements Parcelable {
    private String kind;
    private String etag;
    private ArrayList<Item> items;

    public YoutubeVideoDetails() {
    }

    protected YoutubeVideoDetails(Parcel in) {
        kind = in.readString();
        etag = in.readString();
        items=new ArrayList<>();
        items = in.createTypedArrayList(Item.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(etag);
        dest.writeTypedList(items);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<YoutubeVideoDetails> CREATOR = new Creator<YoutubeVideoDetails>() {
        @Override
        public YoutubeVideoDetails createFromParcel(Parcel in) {
            return new YoutubeVideoDetails(in);
        }

        @Override
        public YoutubeVideoDetails[] newArray(int size) {
            return new YoutubeVideoDetails[size];
        }
    };

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
