package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 07/07/15
 */
public class Institute implements Parcelable {
    public static final Creator<Institute> CREATOR = new Creator<Institute>() {
        @Override
        public Institute createFromParcel(Parcel source) {
            return new Institute(source);
        }

        @Override
        public Institute[] newArray(int size) {
            return new Institute[size];
        }
    };
    private int id;
    private int course_count;
    private String logo;
    private String acronym;
    private String main_address;
    private String website;
    private String uri_id;
    private String short_name;
    private String address;
    private String description;
    private String name;
    private String contact_name;
    private String resource_uri;
    private String banner;
    private String estb_date;
    private String university_name;
    private String state_name;
    private String city_name;
    private String awards_snap;
    private String infra_snap;
    private String placement_percentage;
    private String avg_salary;
    private String near_by_joints_snap;
    private String placement;
    private String max_salary;

    public Institute() {
    }

    public Institute(Parcel source) {
        id = source.readInt();
        course_count = source.readInt();
        logo = source.readString();
        acronym = source.readString();
        main_address = source.readString();
        website = source.readString();
        uri_id = source.readString();
        short_name = source.readString();
        address = source.readString();
        description = source.readString();
        name = source.readString();
        contact_name = source.readString();
        resource_uri = source.readString();
        banner = source.readString();
        estb_date = source.readString();
        university_name = source.readString();
        state_name = source.readString();
        city_name = source.readString();
        awards_snap = source.readString();
        infra_snap = source.readString();
        placement_percentage = source.readString();
        avg_salary = source.readString();
        near_by_joints_snap = source.readString();
        placement = source.readString();
        max_salary = source.readString();

    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(String max_salary) {
        this.max_salary = max_salary;
    }

    public String getAwards_snap() {
        return awards_snap;
    }

    public void setAwards_snap(String awards_snap) {
        this.awards_snap = awards_snap;
    }

    public String getInfra_snap() {
        return infra_snap;
    }

    public void setInfra_snap(String infra_snap) {
        this.infra_snap = infra_snap;
    }

    public String getPlacement_percentage() {
        return placement_percentage;
    }

    public void setPlacement_percentage(String placement_percentage) {
        this.placement_percentage = placement_percentage;
    }

    public String getAvg_salary() {
        return avg_salary;
    }

    public void setAvg_salary(String avg_salary) {
        this.avg_salary = avg_salary;
    }

    public String getNear_by_joints_snap() {
        return near_by_joints_snap;
    }

    public void setNear_by_joints_snap(String near_by_joints_snap) {
        this.near_by_joints_snap = near_by_joints_snap;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public int describeContents() {
        return id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(course_count);
        dest.writeString(logo);
        dest.writeString(acronym);
        dest.writeString(main_address);
        dest.writeString(website);
        dest.writeString(uri_id);
        dest.writeString(short_name);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeString(contact_name);
        dest.writeString(resource_uri);
        dest.writeString(banner);
        dest.writeString(estb_date);
        dest.writeString(university_name);
        dest.writeString(state_name);
        dest.writeString(city_name);
        dest.writeString(awards_snap);
        dest.writeString(infra_snap);
        dest.writeString(placement_percentage);
        dest.writeString(avg_salary);
        dest.writeString(near_by_joints_snap);
        dest.writeString(placement);
        dest.writeString(max_salary);
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getMain_address() {
        return main_address;
    }

    public void setMain_address(String main_address) {
        this.main_address = main_address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUri_id() {
        return uri_id;
    }

    public void setUri_id(String uri_id) {
        this.uri_id = uri_id;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public int getCourse_count() {
        return course_count;
    }

    public void setCourse_count(int course_count) {
        this.course_count = course_count;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getEstb_date() {
        return estb_date;
    }

    public void setEstb_date(String estb_date) {
        this.estb_date = estb_date;
    }

    @Override
    public String toString() {
        return "Institute [uri_id = " + uri_id + ", short_name = " + short_name + ", id = " + id + ", name = " + name + ", estb_date = " + estb_date + "]";
    }
}
