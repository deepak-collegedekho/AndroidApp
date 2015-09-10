package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.collegedekho.app.resource.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;

public class Folder implements Parcelable {
    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel parcel) {
            return new Folder(parcel);
        }

        @Override
        public Folder[] newArray(int i) {
            return new Folder[i];
        }
    };
    //    private int FLAG_SELECTED = 1 << 0;
//    private int flags;
    private int guid;
    private int id;
    private String uri;
    private String resource_uri;
    private String attr;
    private String label;
    private int selectedCount = 0;
    private ArrayList<Facet> facets;

    public Folder(int guid) {
        this.guid = guid;
        facets = new ArrayList<>();
    }

    public Folder(Parcel p) {
        guid = p.readInt();
        id = p.readInt();
        uri = p.readString();
        resource_uri = p.readString();
        attr = p.readString();
        label = p.readString();
        selectedCount = p.readInt();
        facets = new ArrayList<Facet>();
        p.readTypedList(facets, Facet.CREATOR);
        setParent();
    }

    public Folder(int index, String label) {
        guid = index;
        this.label = label;
        facets = new ArrayList<Facet>();
    }

    public static void populateFolderList(JsonParser jp, ArrayList<Folder> folderList) throws IOException {
        int i = 0;
        jp.nextToken();
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            folderList.add(createFromJSON(jp, i));
            i++;
        }
    }

    private static Folder createFromJSON(JsonParser jp, int guid) throws IOException {
        Folder f = new Folder(guid);
        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jp.getCurrentName();
            jp.nextToken();
            switch (fieldName) {
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
                case Constants.TAG_TAGS:
                    f.populateFacetList(jp);
                    break;
                case Constants.TAG_ID:
                    f.id = jp.getIntValue();
                    break;
                default:
                    jp.skipChildren();
            }
        }
        return f;
    }

    public void addFacet(String label, String tag, int selected) {
        facets.add(new Facet(label, tag, selected));
    }

    private void populateFacetList(JsonParser jp) throws IOException {
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            Facet f = Facet.createFromJSON(jp);
            facets.add(f);
        }
    }

    private void setParent() {
        for (Facet f : facets)
            f.setParent(this);
    }

    public ArrayList<Facet> getFacets() {
        return facets;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //    public void setName(String name) {
//        this.name = name;
//    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(guid);
        parcel.writeInt(id);
        parcel.writeString(uri);
        parcel.writeString(resource_uri);
        parcel.writeString(attr);
        parcel.writeString(label);
        parcel.writeInt(selectedCount);
        parcel.writeTypedList(facets);
    }

}
