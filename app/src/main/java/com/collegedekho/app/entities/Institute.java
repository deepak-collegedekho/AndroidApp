package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.jr.ob.impl.DeferredMap;

import java.util.ArrayList;
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
    private int course_count;
    private int shortlist_count;
    private int not_interested_count;
    private int undecided_count;
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

    public Institute() {
    }

    public Institute(Parcel source) {
        id = source.readInt();
        shortlist_count = source.readInt();
        not_interested_count = source.readInt();
        undecided_count = source.readInt();
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
        facilities = new ArrayList<>();
        source.readTypedList(facilities, Facility.CREATOR);
        current_user_vote_type = source.readInt();
        current_user_shortlist_url = source.readString();
        current_user_vote_url = source.readString();
        is_shortlisted = source.readInt();
        upvotes = source.readInt();
        downvotes = source.readInt();
        videos = source.readArrayList(ArrayList.class.getClassLoader());
        streams = source.readArrayList(ArrayList.class.getClassLoader());
        partner_status=source.readInt();
        source.readMap(images, Map.class.getClassLoader());
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
        dest.writeInt(shortlist_count);
        dest.writeInt(not_interested_count);
        dest.writeInt(undecided_count);
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
                if (fdm.containsKey("name") && fdm.get("name")!=null)
                    f.tag = fdm.get("name").toString();
                if (fdm.containsKey("image") && fdm.get("image")!=null)
                    f.image = fdm.get("image").toString();
                if (f.image != null)
                    this.facilities.add(f);
            }
        }
    }

    /*public void setFacilities(Map<String,String> facility){
        if(facilities==null)
            facilities = new ArrayList<>();
        Facility f = new Facility();
        f.tag = facility.get("name");
        f.image = facility.get("image");
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
        for (Object image : images)
        {
            if (image instanceof DeferredMap)
            {
                DeferredMap fdm = (DeferredMap) image;
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

    public int getShortlist_count() {
        return shortlist_count;
    }

    public void setShortlist_count(int shortlist_count) {
        this.shortlist_count = shortlist_count;
    }

    public int getNot_interested_count() {
        return not_interested_count;
    }

    public void setNot_interested_count(int not_interested_count) {
        this.not_interested_count = not_interested_count;
    }

    public int getUndecided_count() {
        return undecided_count;
    }

    public void setUndecided_count(int undecided_count) {
        this.undecided_count = undecided_count;
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
}
