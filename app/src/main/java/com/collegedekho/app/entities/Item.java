package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bashir on 9/2/16.
 */
public class Item implements Parcelable {
    private String kind;
    private String etag;
    private String id;
    private Snippet snippet;
    private VideoStats statistics;
    private VideoContentDetails contentDetails;

    public Item() {
    }

    protected Item(Parcel in) {
        kind = in.readString();
        etag = in.readString();
        id = in.readString();
        snippet = in.readParcelable(Snippet.class.getClassLoader());
        statistics=in.readParcelable(VideoStats.class.getClassLoader());
        contentDetails=in.readParcelable(VideoContentDetails.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(etag);
        dest.writeString(id);
        dest.writeParcelable(snippet, flags);
        dest.writeParcelable(statistics,flags);
        dest.writeParcelable(contentDetails,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public VideoStats getStatistics() {
        return statistics;
    }

    public void setStatistics(VideoStats statistics) {
        this.statistics = statistics;
    }

    public VideoContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(VideoContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }
}
