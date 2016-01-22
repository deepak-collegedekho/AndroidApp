package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 26/11/15.
 */
public class UserEducation implements Parcelable {
    private String name;
    private int value;
    private String sublevel;
    private boolean is_preparing;
    private String stream;

    private String marks;
    private ArrayList<UserEducationStreams> streams;
    private ArrayList<UserEducationSublevels> sublevels;

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getSublevel() {
        return sublevel;
    }

    public void setSublevel(String sublevel) {
        this.sublevel = sublevel;
    }

    public boolean is_preparing() {
        return is_preparing;
    }

    public void setIs_preparing(boolean is_preparing) {
        this.is_preparing = is_preparing;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserEducation> CREATOR = new Creator<UserEducation>() {
        @Override
        public UserEducation createFromParcel(Parcel source) {  return new UserEducation(source);   }

        @Override
        public UserEducation[] newArray(int size) {  return new UserEducation[size];   }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(value);
        dest.writeTypedList(streams);
        dest.writeTypedList(sublevels);
        dest.writeString(sublevel);
        dest.writeString(stream);
        dest.writeByte((byte) (is_preparing ? 1 : 0));
    }

    public UserEducation() {
    }

    protected UserEducation(Parcel source) {
        name = source.readString();
        value = source.readInt();

        streams = new ArrayList<>();
        source.readTypedList(streams, UserEducationStreams.CREATOR);

        sublevels = new ArrayList<>();
        source.readTypedList(sublevels, UserEducationSublevels.CREATOR);
        is_preparing=source.readByte()!=0;
        stream=source.readString();
        sublevel=source.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ArrayList<UserEducationStreams> getStreams() {
        return streams;
    }

    public void setStreams(ArrayList<UserEducationStreams> streams) {
        this.streams = streams;
    }

    public ArrayList<UserEducationSublevels> getSublevels() {
        return sublevels;
    }

    public void setSublevels(ArrayList<UserEducationSublevels> sublevels) {
        this.sublevels = sublevels;
    }
}
