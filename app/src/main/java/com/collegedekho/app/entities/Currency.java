package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.collegedekho.app.resource.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

/**
 * Created by ashutosh on 1/5/17.
 */

public class Currency implements Parcelable {

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel parcel) {
            return new Currency(parcel);
        }

        @Override
        public Currency[] newArray(int i) {
            return new Currency[i];
        }
    };

    private int id;
    private String name;
    private String short_name;
    private String image;
    private String symbol;
    private float base_conversion_value;

    public Currency(Parcel p)
    {
        id = p.readInt();
        name = p.readString();
        short_name = p.readString();
        image = p.readString();
        symbol = p.readString();
        base_conversion_value = p.readFloat();
    }

    public String getShort_name()
    {
        return this.short_name;
    }
    public Currency(String short_name,String name,int id) {
        this.id = id;
        this.short_name = short_name;
        this.name = name;
    }
    public Currency() {

    }

    public Currency(int id){
        this.id = id;
    }

    public static Currency createFromJSON(JsonParser jp,int id) throws IOException {
        Currency c = new Currency(id);
        while (jp.nextToken() != JsonToken.END_OBJECT){
            String fieldName = jp.getCurrentName();
            Log.e("createFromJSON Currency"," "+fieldName);
            jp.nextToken();
                switch (fieldName){
                    case Constants.TAG_ID:
                        c.id = jp.getIntValue();
                        break;
                    case Constants.TAG_NAME:
                        c.name = jp.getText();
                        break;
                    case Constants.TAG_SHORT_NAME:
                        c.short_name = jp.getText();
                        break;
                    default:
                        jp.skipChildren();
                }
            }
        return c;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(short_name);
        parcel.writeString(image);
        parcel.writeString(symbol);
        parcel.writeFloat(base_conversion_value);
    }
}
