package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.collegedekho.app.resource.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public class Facet implements Parcelable {


    public static final Creator<Facet> CREATOR = new Creator<Facet>() {
        @Override
        public Facet createFromParcel(Parcel parcel) {
            return new Facet(parcel);
        }

        @Override
        public Facet[] newArray(int i) {
            return new Facet[i];
        }
    };
    private String uri;
    private String resource_uri;
    private String attr;
    private String label;
    private String tag;
    private int selected = -1;
    private int count;
    //Every facet has a parent folder.
    private Folder parent;

    //    OnFacetClickListener mListener;
    public Facet(Parcel parcel) {
        uri = parcel.readString();
        resource_uri = parcel.readString();
        attr = parcel.readString();
        label = parcel.readString();
        tag = parcel.readString();
        selected = parcel.readInt();
        count = parcel.readInt();
    }

    public Facet(String label, String tag, int selected) {
        this.label = label;
        this.tag = tag;
        this.selected = selected;
    }

    public Facet() {

    }

    public Facet(Folder parent) {
        this.parent = parent;
    }

    public static Facet createFromJSON(JsonParser jp) throws IOException {
        Facet f = new Facet();
        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jp.getCurrentName();
            jp.nextToken();
            switch (fieldname) {
                case Constants.TAG_RESOURCE_URI:
                    f.resource_uri = jp.getText();
                    break;
                case Constants.TAG_ATTR:
                    f.attr = jp.getText();
                    break;
                case Constants.TAG_LABEL:
                    f.label = jp.getText();
                    break;
                case Constants.TAG_URI:
                    f.uri = jp.getText();
                    break;
                default:
                    jp.skipChildren();
                    break;
            }
        }
        return f;
    }

    public int isSelected() {
        return selected;
    }

    public void select() {
        selected = 1;
    }

    public void deselect() {
        selected = 0;
    }

    public String getLabel() {
        return label;
    }

    public String getTag() {
        return uri;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uri);
        parcel.writeString(resource_uri);
        parcel.writeString(attr);
        parcel.writeString(label);
        parcel.writeString(tag);
        parcel.writeInt(selected);
        parcel.writeInt(count);
    }

    /*
    public interface OnFacetClickListener
    {
        public void onFacetClick(boolean isSelected);
    }

    public void setListener(OnFacetClickListener listener)
    {
        mListener = listener;
    }  */
}