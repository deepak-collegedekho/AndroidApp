package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.jr.ob.impl.DeferredMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private int position = -1;
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
    private String uri_slug = "";
    private String banner;
    private String estb_date;
    private String university_name;
    private String state_name;
    private String city_name;
    private String awards_snap;
    private String infra_snap;
    private String placement_percentage;
    private double avg_salary;
    private String near_by_joints_snap;
    private String placement;
    private double max_salary;
    private ArrayList<Facility> facilities;
    private int current_user_vote_type = -1;
    private String current_user_shortlist_url;
    private String current_user_vote_url;
    private int is_shortlisted;
    private int partner_status;
    private boolean isSelected = false;
    private int upvotes;
    private int downvotes;
    private ArrayList<String> videos;
    private ArrayList<String> streams;
    private Map<String, String> images;
    private int shortlist_count;
    private double min_fees;
    private double max_fees;
    private double min_salary;
    private String fees;
    private String l3_number;
    private String application_end_date;
    private String application_status;
    private int groups_exists;
    private int is_applied;

    private ArrayList<String> exams = new ArrayList<>();
    private ArrayList<String> user_exams = new ArrayList<>();

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
        uri_slug = source.readString();
        banner = source.readString();
        estb_date = source.readString();
        university_name = source.readString();
        state_name = source.readString();
        city_name = source.readString();
        awards_snap = source.readString();
        infra_snap = source.readString();
        placement_percentage = source.readString();
        avg_salary = source.readDouble();
        near_by_joints_snap = source.readString();
        placement = source.readString();
        max_salary = source.readDouble();
        facilities = new ArrayList<>();
        source.readTypedList(facilities, Facility.CREATOR);
        current_user_vote_type = source.readInt();
        current_user_shortlist_url = source.readString();
        current_user_vote_url = source.readString();
        is_shortlisted = source.readInt();
        upvotes = source.readInt();
        downvotes = source.readInt();
        videos = new ArrayList<>();
        streams = new ArrayList<>();
        source.readStringList(videos);
        source.readStringList(streams);
        partner_status = source.readInt();
        images = new HashMap<>();
        source.readMap(images, Map.class.getClassLoader());
        min_fees = source.readDouble();
        max_fees = source.readDouble();
        min_salary = source.readDouble();
        shortlist_count = source.readInt();
        exams = new ArrayList<>();
        source.readStringList(exams);
        user_exams=new ArrayList<>();
        source.readStringList(user_exams);
        fees = source.readString();
        l3_number=source.readString();
        application_end_date=source.readString();
        application_status=source.readString();
        groups_exists=source.readInt();
        is_applied = source.readInt();
    }

    public String getCurrent_user_vote_url() {
        return current_user_vote_url;
    }

    public void setCurrent_user_vote_url(String current_user_vote_url) {
        this.current_user_vote_url = current_user_vote_url;
    }

    public String getCurrent_user_shortlist_url() {
        return current_user_shortlist_url;
    }

    public void setCurrent_user_shortlist_url(String current_user_shortlist_url) {
        this.current_user_shortlist_url = current_user_shortlist_url;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public Double getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(Double max_salary) {
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

    public Double getAvg_salary() {
        return avg_salary;
    }

    public void setAvg_salary(Double avg_salary) {
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
        dest.writeString(uri_slug);
        dest.writeString(banner);
        dest.writeString(estb_date);
        dest.writeString(university_name);
        dest.writeString(state_name);
        dest.writeString(city_name);
        dest.writeString(awards_snap);
        dest.writeString(infra_snap);
        dest.writeString(placement_percentage);
        dest.writeDouble(avg_salary);
        dest.writeString(near_by_joints_snap);
        dest.writeString(placement);
        dest.writeDouble(max_salary);
        dest.writeTypedList(facilities);
        dest.writeInt(current_user_vote_type);
        dest.writeString(current_user_shortlist_url);
        dest.writeString(current_user_vote_url);
        dest.writeInt(upvotes);
        dest.writeInt(downvotes);
        dest.writeStringList(videos);
        dest.writeStringList(streams);
        dest.writeMap(images);
        dest.writeInt(partner_status);
        dest.writeDouble(min_fees);
        dest.writeDouble(max_fees);
        dest.writeDouble(min_salary);
        dest.writeStringList(exams);
        dest.writeStringList(user_exams);
        dest.writeInt(shortlist_count);
        dest.writeString(fees);
        dest.writeString(l3_number);
        dest.writeString(application_end_date);
        dest.writeString(application_status);
        dest.writeInt(groups_exists);
        dest.writeInt(is_applied);
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

    public String getUri_slug() {
        return uri_slug;
    }

    public void setUri_slug(String uri_slug) {
        this.uri_slug = uri_slug;
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

    public ArrayList<Facility> getFacilities() {
        return facilities;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setFacilities(ArrayList facilities) {
        if (this.facilities == null)
            this.facilities = new ArrayList<>();
        for (Object facility : facilities) {
            if (facility instanceof DeferredMap) {
                DeferredMap fdm = (DeferredMap) facility;
                Facility f = new Facility();
                if (fdm.containsKey("name") && fdm.get("name") != null)
                    f.tag = fdm.get("name").toString();
                if (fdm.containsKey("image_new") && fdm.get("image_new") != null)
                    f.image_new = fdm.get("image_new").toString();
                if (f.image_new != null)
                    this.facilities.add(f);
            }
        }
    }

    /*public void setFacilities(Map<String,String> facility){
        if(facilities==null)
            facilities = new ArrayList<>();
        Facility f = new Facility();
        f.tag = facility.get("name");
        f.image_new = facility.get("image_new");
        facilities.add(f);
    }*/

    public int getCurrent_user_vote_type() {
        return current_user_vote_type;
    }

    public void setCurrent_user_vote_type(int current_user_vote_type) {
        this.current_user_vote_type = current_user_vote_type;
    }

    public int getIs_shortlisted() {
        return is_shortlisted;
    }

    public void setIs_shortlisted(int is_shortlisted) {
        this.is_shortlisted = is_shortlisted;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    /*public void setImages(ArrayList images) {
        if (this.images == null)
            this.images = new ArrayList<>();
        for (Object image_new : images)
        {
            if (image_new instanceof DeferredMap)
            {
                DeferredMap fdm = (DeferredMap) image_new;
                Images f = new Images();
                if (fdm.containsKey("Banner"))
                    f.setBanner(fdm.get("Banner").toString());
                if (fdm.containsKey("Primary"))
                    f.setPrimary(fdm.get("Primary").toString());
                if (fdm.containsKey("Student"))
                    f.setPrimary(fdm.get("Student").toString());
                if (fdm.containsKey("Infra"))
                    f.setPrimary(fdm.get("Infra").toString());
            }
        }
    }*/

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public ArrayList<String> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<String> videos) {
        this.videos = videos;
    }

    public ArrayList<String> getStreams() {
        return streams;
    }

    public void setStreams(ArrayList<String> streams) {
        this.streams = streams;
    }

    @Override
    public String toString() {
        return "Institute [uri_id = " + uri_id + ", short_name = " + short_name + ", id = " + id + ", name = " + name + ", estb_date = " + estb_date + "]";
    }

    public int getPartner_status() {
        return partner_status;
    }

    public void setPartner_status(int partner_status) {
        this.partner_status = partner_status;
    }

    public int getShortlist_count() {
        return shortlist_count;
    }

    public void setShortlist_count(int shortlist_count) {
        this.shortlist_count = shortlist_count;
    }

    public double getMin_fees() {
        return min_fees;
    }

    public void setMin_fees(double min_fees) {
        this.min_fees = min_fees;
    }

    public double getMax_fees() {
        return max_fees;
    }

    public void setMax_fees(double max_fees) {
        this.max_fees = max_fees;
    }

    public Double getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(Double min_salary) {
        this.min_salary = min_salary;
    }

    public ArrayList<String> getExams() {
        return exams;
    }

    public void setExams(ArrayList<String> exams) {
        this.exams = exams;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getL3_number() {
        return l3_number;
    }

    public void setL3_number(String l3_number) {
        this.l3_number = l3_number;
    }

    public String getApplication_end_date() {
        return application_end_date;
    }

    public void setApplication_end_date(String application_end_date) {
        this.application_end_date = application_end_date;
    }

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    public int getGroups_exists() {
        return groups_exists;
    }

    public void setGroups_exists(int groups_exists) {
        this.groups_exists = groups_exists;
    }

    public ArrayList<String> getUser_exams() {
        return user_exams;
    }

    public void setUser_exams(ArrayList<String> user_exams) {
        this.user_exams = user_exams;
    }


    public boolean is_applied() {
        return (is_applied == 1);
    }

    public void setIs_applied(boolean is_applied) {
        this.is_applied = is_applied ? 1 : 0;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
