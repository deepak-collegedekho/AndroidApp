package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Cities implements Parcelable {
    public static final Creator<Cities> CREATOR = new Creator<Cities>() {
        @Override
        public Cities createFromParcel(Parcel source) {
            return new Cities(source);
        }

        @Override
        public Cities[] newArray(int size) {
            return new Cities[size];
        }
    };
    public String url;
    public String name;
    public Boolean show_in_lead_form;
    public String state;

    public Cities() {
    }

    public Cities(Parcel source) {
        url = source.readString();
        name = source.readString();
        show_in_lead_form = source.readInt() == 1;
        state = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeInt(show_in_lead_form ? 1 : 0);
        dest.writeString(state);
    }


}
