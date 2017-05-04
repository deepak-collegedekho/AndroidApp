package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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
    public String flag_image="";
    public int institute_count;
    private boolean isSelected=false;

    protected Country(Parcel source) {
        Log.e("Parcel Country",source.toString());
        id = source.readInt();
        name = source.readString();
        alpha2_code = source.readString();
        alpha3_code = source.readString();
        continent = source.readString();
        order_score = source.readInt();
        image = source.readString();
        flag_image = source.readString();
        institute_count = source.readInt();
    }

    public Country(){
        
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
        dest.writeString(flag_image);
        dest.writeInt(institute_count);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
