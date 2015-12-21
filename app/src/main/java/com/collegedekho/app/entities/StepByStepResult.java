package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Harsh Vardhan
 *         Created: 21/12/15
 */
public class StepByStepResult implements Parcelable {

    public static final Creator<StepByStepResult> CREATOR = new Creator<StepByStepResult>() {
        @Override
        public StepByStepResult createFromParcel(Parcel source) {
            return new StepByStepResult(source);
        }

        @Override
        public StepByStepResult[] newArray(int size) {
            return new StepByStepResult[size];
        }
    };

    private int stream_id = -1;
    private String stream_name = "";
    private ArrayList<String> tags = new ArrayList<>();


    public StepByStepResult() {

    }

    public StepByStepResult(Parcel source) {
        stream_id = source.readInt();
        stream_name = source.readString();
        tags = new ArrayList<String>();
        tags = source.readArrayList(ArrayList.class.getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stream_id);
        dest.writeString(stream_name);
        dest.writeStringList(tags);
    }

    public int getStream_id() {
        return stream_id;
    }

    public void setStream_id(int stream_id) {
        this.stream_id = stream_id;
    }

    public String getStream_name() {
        return stream_name;
    }

    public void setStream_name(String stream_name) {
        this.stream_name = stream_name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
