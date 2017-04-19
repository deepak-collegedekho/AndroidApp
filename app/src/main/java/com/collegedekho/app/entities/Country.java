package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ashutosh on 18/4/17.
 */

public class Country implements Parcelable {


    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public int id;
    public String name = "";
    public String resource_uri = "";
    public String alpha2_code = "";
    public String alpha3_code = "";
    public String continent = "";
    public int order_score;
    public String image="";
    private boolean isSelected=false;

    protected Country(Parcel source) {

        id = source.readInt();
        name = source.readString();
        alpha2_code = source.readString();
        alpha3_code = source.readString();
        continent = source.readString();
        order_score = source.readInt();
        image = source.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(alpha2_code);
        dest.writeString(alpha3_code);
        dest.writeString(continent);
        dest.writeInt(order_score);
        dest.writeString(image);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
