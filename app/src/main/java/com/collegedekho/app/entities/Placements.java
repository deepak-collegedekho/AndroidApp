package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by maxx on 15/04/15.
 */
public class Placements implements Parcelable {
    public static final Creator<Placements> CREATOR = new Creator<Placements>() {
        @Override
        public Placements createFromParcel(Parcel source) {
            return new Placements(source);
        }

        @Override
        public Placements[] newArray(int size) {
            return new Placements[size];
        }
    };
    public String about;
    public String highestSalary;
    public String averageSalary;
    public String placementPercentage;
    public ArrayList<String> companyLogos;

    public Placements() {

    }

    public Placements(Parcel source) {
        about = source.readString();
        highestSalary = source.readString();
        averageSalary = source.readString();
        placementPercentage = source.readString();
        companyLogos = new ArrayList<>();
        source.readStringList(companyLogos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(about);
        dest.writeString(highestSalary);
        dest.writeString(averageSalary);
        dest.writeString(placementPercentage);
        dest.writeStringList(companyLogos);
    }
}
