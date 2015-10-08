package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class InstituteCourse implements Parcelable {
    public static final Creator<InstituteCourse> CREATOR = new Creator<InstituteCourse>() {
        @Override
        public InstituteCourse createFromParcel(Parcel source) {
            return new InstituteCourse(source);
        }

        @Override
        public InstituteCourse[] newArray(int size) {
            return new InstituteCourse[size];
        }
    };
    public long id;
    public String name;
    public String specialization;
    public String stream_name;
    public String degree_name;
    public int duration;
    public int level;
    public String duration_type_display;
    public int is_applied;
    public InstituteCourse() {
    }

    public InstituteCourse(Parcel source) {
        id = source.readLong();
        name = source.readString();
        specialization = source.readString();
        stream_name = source.readString();
        degree_name = source.readString();
        duration = source.readInt();
        level = source.readInt();
        duration_type_display = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(specialization);
        dest.writeString(stream_name);
        dest.writeString(degree_name);
        dest.writeInt(duration);
        dest.writeInt(level);
        dest.writeString(duration_type_display);
    }

    public int getIs_applied() {
        return is_applied;
    }

    public void setIs_applied(int is_applied) {
        this.is_applied = is_applied;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getStream_name() {
        return stream_name;
    }

    public void setStream_name(String stream_name) {
        this.stream_name = stream_name;
    }

    public String getDegree_name() {
        return degree_name;
    }

    public void setDegree_name(String degree_name) {
        this.degree_name = degree_name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getLevel() {
        return level;
    }

    public void setDuration_type_display(String duration_type_display) {
        this.duration_type_display = duration_type_display;
    }

    public void setDegree_level(int degree_level) {
        level = degree_level - 1;
    }

    public enum CourseLevel {
        UNDER_GRADUATE, POST_GRADUATE, DIPLOMA, PHD, OTHERS;

        public static String[] getValues()
        {
            String[] x = new String[values().length];

            for (int i = 0; i < x.length; i++) {
                x[i] = values()[i].name().replace("_", " ");
            }
            return x;
        }
        public static String getName(int index)
        {
            return  values()[index].name().replace("_", " ");
        }
    }
}
