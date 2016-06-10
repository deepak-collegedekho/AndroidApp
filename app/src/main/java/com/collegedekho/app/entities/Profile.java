package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 18/5/16.
 */
public class Profile implements Parcelable{

    private String name =   "";
    private String phone_no =   "";
    private String email =  "";
    private String image =  "";
    private String state_name = "";
    private int state_id = -1;
    private int is_verified = 0;
    private String city_name =  "";
    private int city_id = -1;
    private String mother_tongue_name = "";
    private int mother_tongue = -1;
    private String social_category_name =   "";
    private int social_category = -1;
    private String current_sublevel_name =  "";
    private int current_sublevel_id = -1;
    private String current_mode_name ="";
    private int current_mode = -1;
    private String current_stream_name ="";
    private String current_stream_short_name ="";
    private int current_stream_id = -1;
    private String current_degree_name ="";
    private String current_degree_short_name ="";
    private int current_degree_id = -1;
    private String current_specialization_name ="";
    private int current_specialization_id =  -1;
    private int current_score = -1;
    private String current_score_type_name ="";
    private int current_score_type = -1;
    private int current_passing_year = -1;
    private int preferred_year_of_admission = -1;
    private String preferred_mode_name ="";
    private int preferred_mode = -1;
    private String preferred_stream_short_name ="";
    private int preferred_stream_id = -1;
    private String preferred_specialization_name ="";
    private int preferred_specialization_id = -1;
    private ArrayList<String> preferred_degrees_names ;
    private ArrayList<String> preferred_degrees_short_names;
    private ArrayList<Integer> preferred_degrees_ids;
    private String preferred_level_name  ="";
    private int preferred_level = -1 ;
    private ArrayList<String> preferred_states_names;
    private ArrayList<Integer> preferred_states_ids;
    private ArrayList<String> preferred_cities_names;
    private ArrayList<Integer> preferred_cities_ids;
    private int preferred_fee_range_max ;
    private String preferred_loan_required_name  ="";
    private int preferred_loan_required = -1;
    private String preferred_loan_amount_needed_name  ="";
    private int preferred_loan_amount_needed = -1;
    private ArrayList<ProfileExam> yearly_exams;
    private String fathers_name  ="";
    private String mothers_name  ="";
    private String coaching_institute  ="";

    public Profile(){
        // required empty contructor
    }

    protected Profile(Parcel in) {
        name = in.readString();
        phone_no = in.readString();
        email = in.readString();
        image = in.readString();
        state_name = in.readString();
        state_id = in.readInt();
        is_verified = in.readInt();
        city_name = in.readString();
        city_id = in.readInt();
        mother_tongue_name = in.readString();
        mother_tongue = in.readInt();
        social_category_name = in.readString();
        social_category = in.readInt();
        current_sublevel_name = in.readString();
        current_sublevel_id = in.readInt();
        current_mode_name = in.readString();
        current_mode = in.readInt();
        current_stream_name = in.readString();
        current_stream_short_name = in.readString();
        current_stream_id = in.readInt();
        current_degree_name = in.readString();
        current_degree_short_name = in.readString();
        current_degree_id = in.readInt();
        current_specialization_name = in.readString();
        current_specialization_id = in.readInt();
        current_score = in.readInt();
        current_score_type_name = in.readString();
        current_score_type = in.readInt();
        current_passing_year = in.readInt();
        preferred_year_of_admission = in.readInt();
        preferred_mode_name = in.readString();
        preferred_mode = in.readInt();
        preferred_stream_short_name = in.readString();
        preferred_stream_id = in.readInt();
        preferred_specialization_name = in.readString();
        preferred_specialization_id = in.readInt();
        preferred_degrees_names = in.createStringArrayList();
        preferred_degrees_short_names = in.createStringArrayList();
        preferred_level_name = in.readString();
        preferred_level = in.readInt();
        preferred_states_names = in.createStringArrayList();
        preferred_cities_names = in.createStringArrayList();
        preferred_fee_range_max = in.readInt();
        preferred_loan_required_name = in.readString();
        preferred_loan_required = in.readInt();
        preferred_loan_amount_needed_name = in.readString();
        preferred_loan_amount_needed = in.readInt();
        fathers_name = in.readString();
        mothers_name = in.readString();
        coaching_institute = in.readString();
        yearly_exams = in.createTypedArrayList(ProfileExam.CREATOR);
        if(yearly_exams == null)
            yearly_exams = new ArrayList<>();
        in.readTypedList(yearly_exams, ProfileExam.CREATOR);
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone_no);
        dest.writeString(email);
        dest.writeString(image);
        dest.writeString(state_name);
        dest.writeInt(state_id);
        dest.writeInt(is_verified);
        dest.writeString(city_name);
        dest.writeInt(city_id);
        dest.writeString(mother_tongue_name);
        dest.writeInt(mother_tongue);
        dest.writeString(social_category_name);
        dest.writeInt(social_category);
        dest.writeString(current_sublevel_name);
        dest.writeInt(current_sublevel_id);
        dest.writeString(current_mode_name);
        dest.writeInt(current_mode);
        dest.writeString(current_stream_name);
        dest.writeString(current_stream_short_name);
        dest.writeInt(current_stream_id);
        dest.writeString(current_degree_name);
        dest.writeString(current_degree_short_name);
        dest.writeInt(current_degree_id);
        dest.writeString(current_specialization_name);
        dest.writeInt(current_specialization_id);
        dest.writeInt(current_score);
        dest.writeString(current_score_type_name);
        dest.writeInt(current_score_type);
        dest.writeInt(current_passing_year);
        dest.writeInt(preferred_year_of_admission);
        dest.writeString(preferred_mode_name);
        dest.writeInt(preferred_mode);
        dest.writeString(preferred_stream_short_name);
        dest.writeInt(preferred_stream_id);
        dest.writeString(preferred_specialization_name);
        dest.writeInt(preferred_specialization_id);
        dest.writeStringList(preferred_degrees_names);
        dest.writeStringList(preferred_degrees_short_names);
        dest.writeString(preferred_level_name);
        dest.writeInt(preferred_level);
        dest.writeStringList(preferred_states_names);
        dest.writeStringList(preferred_cities_names);
        dest.writeInt(preferred_fee_range_max);
        dest.writeString(preferred_loan_required_name);
        dest.writeInt(preferred_loan_required);
        dest.writeString(preferred_loan_amount_needed_name);
        dest.writeInt(preferred_loan_amount_needed);
        dest.writeString(fathers_name);
        dest.writeString(mothers_name);
        dest.writeString(coaching_institute);
        dest.writeTypedList(yearly_exams);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public int getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(int is_verified) {
        this.is_verified = is_verified;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getMother_tongue_name() {
        return mother_tongue_name;
    }

    public void setMother_tongue_name(String mother_tongue_name) {
        this.mother_tongue_name = mother_tongue_name;
    }

    public int getMother_tongue() {
        return mother_tongue;
    }

    public void setMother_tongue(int mother_tongue) {
        this.mother_tongue = mother_tongue;
    }

    public String getSocial_category_name() {
        return social_category_name;
    }

    public void setSocial_category_name(String social_category_name) {
        this.social_category_name = social_category_name;
    }

    public int getSocial_category() {
        return social_category;
    }

    public void setSocial_category(int social_category) {
        this.social_category = social_category;
    }

    public String getCurrent_sublevel_name() {
        return current_sublevel_name;
    }

    public void setCurrent_sublevel_name(String current_sublevel_name) {
        this.current_sublevel_name = current_sublevel_name;
    }

    public int getCurrent_sublevel_id() {
        return current_sublevel_id;
    }

    public void setCurrent_sublevel_id(int current_sublevel_id) {
        this.current_sublevel_id = current_sublevel_id;
    }

    public String getCurrent_mode_name() {
        return current_mode_name;
    }

    public void setCurrent_mode_name(String current_mode_name) {
        this.current_mode_name = current_mode_name;
    }

    public int getCurrent_mode() {
        return current_mode;
    }

    public void setCurrent_mode(int current_mode) {
        this.current_mode = current_mode;
    }

    public String getCurrent_stream_name() {
        return current_stream_name;
    }

    public void setCurrent_stream_name(String current_stream_name) {
        this.current_stream_name = current_stream_name;
    }

    public String getCurrent_stream_short_name() {
        return current_stream_short_name;
    }

    public void setCurrent_stream_short_name(String current_stream_short_name) {
        this.current_stream_short_name = current_stream_short_name;
    }

    public int getCurrent_stream_id() {
        return current_stream_id;
    }

    public void setCurrent_stream_id(int current_stream_id) {
        this.current_stream_id = current_stream_id;
    }

    public String getCurrent_degree_name() {
        return current_degree_name;
    }

    public void setCurrent_degree_name(String current_degree_name) {
        this.current_degree_name = current_degree_name;
    }

    public String getCurrent_degree_short_name() {
        return current_degree_short_name;
    }

    public void setCurrent_degree_short_name(String current_degree_short_name) {
        this.current_degree_short_name = current_degree_short_name;
    }

    public int getCurrent_degree_id() {
        return current_degree_id;
    }

    public void setCurrent_degree_id(int current_degree_id) {
        this.current_degree_id = current_degree_id;
    }

    public String getCurrent_specialization_name() {
        return current_specialization_name;
    }

    public void setCurrent_specialization_name(String current_specialization_name) {
        this.current_specialization_name = current_specialization_name;
    }

    public int getCurrent_specialization_id() {
        return current_specialization_id;
    }

    public void setCurrent_specialization_id(int current_specialization_id) {
        this.current_specialization_id = current_specialization_id;
    }

    public int getCurrent_score() {
        return current_score;
    }

    public void setCurrent_score(int current_score) {
        this.current_score = current_score;
    }

    public String getCurrent_score_type_name() {
        return current_score_type_name;
    }

    public void setCurrent_score_type_name(String current_score_type_name) {
        this.current_score_type_name = current_score_type_name;
    }

    public int getCurrent_score_type() {
        return current_score_type;
    }

    public void setCurrent_score_type(int current_score_type) {
        this.current_score_type = current_score_type;
    }

    public int getCurrent_passing_year() {
        return current_passing_year;
    }

    public void setCurrent_passing_year(int current_passing_year) {
        this.current_passing_year = current_passing_year;
    }

    public int getPreferred_year_of_admission() {
        return preferred_year_of_admission;
    }

    public void setPreferred_year_of_admission(int preferred_year_of_admission) {
        this.preferred_year_of_admission = preferred_year_of_admission;
    }

    public String getPreferred_mode_name() {
        return preferred_mode_name;
    }

    public void setPreferred_mode_name(String preferred_mode_name) {
        this.preferred_mode_name = preferred_mode_name;
    }

    public int getPreferred_mode() {
        return preferred_mode;
    }

    public void setPreferred_mode(int preferred_mode) {
        this.preferred_mode = preferred_mode;
    }

    public String getPreferred_stream_short_name() {
        return preferred_stream_short_name;
    }

    public void setPreferred_stream_short_name(String preferred_stream_short_name) {
        this.preferred_stream_short_name = preferred_stream_short_name;
    }

    public int getPreferred_stream_id() {
        return preferred_stream_id;
    }

    public void setPreferred_stream_id(int preferred_stream_id) {
        this.preferred_stream_id = preferred_stream_id;
    }

    public String getPreferred_specialization_name() {
        return preferred_specialization_name;
    }

    public void setPreferred_specialization_name(String preferred_specialization_name) {
        this.preferred_specialization_name = preferred_specialization_name;
    }

    public int getPreferred_specialization_id() {
        return preferred_specialization_id;
    }

    public void setPreferred_specialization_id(int preferred_specialization_id) {
        this.preferred_specialization_id = preferred_specialization_id;
    }

    public ArrayList<String> getPreferred_degrees_names() {
        return preferred_degrees_names;
    }

    public void setPreferred_degrees_names(ArrayList<String> preferred_degrees_names) {
        this.preferred_degrees_names = preferred_degrees_names;
    }

    public ArrayList<String> getPreferred_cities_names() {
        return preferred_cities_names;
    }

    public void setPreferred_cities_names(ArrayList<String> preferred_cities_names) {
        this.preferred_cities_names = preferred_cities_names;
    }

    public ArrayList<Integer> getPreferred_cities_ids() {
        return preferred_cities_ids;
    }

    public void setPreferred_cities_ids(ArrayList<Integer> preferred_cities_ids) {
        this.preferred_cities_ids = preferred_cities_ids;
    }

    public ArrayList<String> getPreferred_degrees_short_names() {
        return preferred_degrees_short_names;
    }

    public void setPreferred_degrees_short_names(ArrayList<String> preferred_degrees_short_names) {
        this.preferred_degrees_short_names = preferred_degrees_short_names;
    }

    public ArrayList<Integer> getPreferred_degrees_ids() {
        return preferred_degrees_ids;
    }

    public void setPreferred_degrees_ids(ArrayList<Integer> preferred_degrees_ids) {
        this.preferred_degrees_ids = preferred_degrees_ids;
    }

    public int getPreferred_level() {
        return preferred_level;
    }

    public void setPreferred_level(int preferred_level) {
        this.preferred_level = preferred_level;
    }

    public ArrayList<String> getPreferred_states_names() {
        return preferred_states_names;
    }

    public void setPreferred_states_names(ArrayList<String> preferred_states_names) {
        this.preferred_states_names = preferred_states_names;
    }

    public ArrayList<Integer> getPreferred_states_ids() {
        return preferred_states_ids;
    }

    public void setPreferred_states_ids(ArrayList<Integer> preferred_states_ids) {
        this.preferred_states_ids = preferred_states_ids;
    }

    public int getPreferred_fee_range_max() {
        return preferred_fee_range_max;
    }

    public void setPreferred_fee_range_max(int preferred_fee_range_max) {
        this.preferred_fee_range_max = preferred_fee_range_max;
    }

    public String getPreferred_loan_required_name() {
        return preferred_loan_required_name;
    }

    public void setPreferred_loan_required_name(String preferred_loan_required_name) {
        this.preferred_loan_required_name = preferred_loan_required_name;
    }

    public int getPreferred_loan_required() {
        return preferred_loan_required;
    }

    public void setPreferred_loan_required(int preferred_loan_required) {
        this.preferred_loan_required = preferred_loan_required;
    }

    public String getPreferred_loan_amount_needed_name() {
        return preferred_loan_amount_needed_name;
    }

    public void setPreferred_loan_amount_needed_name(String preferred_loan_amount_needed_name) {
        this.preferred_loan_amount_needed_name = preferred_loan_amount_needed_name;
    }

    public int getPreferred_loan_amount_needed() {
        return preferred_loan_amount_needed;
    }

    public void setPreferred_loan_amount_needed(int preferred_loan_amount_needed) {
        this.preferred_loan_amount_needed = preferred_loan_amount_needed;
    }

    public String getFathers_name() {
        return fathers_name;
    }

    public void setFathers_name(String fathers_name) {
        this.fathers_name = fathers_name;
    }

    public String getMothers_name() {
        return mothers_name;
    }

    public void setMothers_name(String mothers_name) {
        this.mothers_name = mothers_name;
    }

    public String getCoaching_institute() {
        return coaching_institute;
    }

    public void setCoaching_institute(String coaching_institute) {
        this.coaching_institute = coaching_institute;
    }

    public String getPreferred_level_name() {
        return preferred_level_name;
    }

    public void setPreferred_level_name(String preferred_level_name) {
        this.preferred_level_name = preferred_level_name;
    }

    public ArrayList<ProfileExam> getYearly_exams() {
        return yearly_exams;
    }

    public void setYearly_exams(ArrayList<ProfileExam> yearly_exams) {
        this.yearly_exams = yearly_exams;
    }
}
