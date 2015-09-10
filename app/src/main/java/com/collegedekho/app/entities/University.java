package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class University implements Parcelable {
    public static final Creator<University> CREATOR = new Creator<University>() {
        @Override
        public University createFromParcel(Parcel source) {
            return new University(source);
        }

        @Override
        public University[] newArray(int size) {
            return new University[size];
        }
    };
    public String url;
    public String name;
    public String type;
    public String logo;
    public String short_name;
    public String desc;
    public String vice_chancellor;
    public String year_of_establishment;
    public String accredation;
    public String university_type;
    public String location;
    public String address;
    public String contact_numbers;
    public String website;
    public String emails;
    public String total_students;
    public String part_time_students_perc;
    public String gender_ratio;
    public String student_avg_age;
    public String statewise_student_distribution;
    public String campus_type;
    public String girls_hostel_available;
    public String hostel_student_percent;
    public String hostel_capacity_boys;
    public String hostel_capacity_girls;
    public String hostel_occupancy;
    public String hostel_internet;
    public String mess_food_quality;
    public String security;
    public String facilities;
    public String health_clinic;
    public String wheelchair_accessible;
    public String visually_impaired_accessible;
    public String personal_counselling_available;
    public String career_counselling_available;
    public String learning_outside_classroom;
    public String exchange_program_available;
    public String transfer_intra_allowed;
    public String transfer_intra_application_date;
    public String transfer_intra_eligibility;
    public String transfer_inter_allowed;
    public String transfer_inter_application_date;
    public String transfer_inter_eligibility;
    public String library;
    public String awards;
    public String rating_coolness;
    public String popular_stream_1;
    public String popular_stream_2;

    public University() {
    }

    public University(Parcel source) {
        url = source.readString();
        name = source.readString();
        type = source.readString();
        logo = source.readString();
        //template_type = source.readLong();
        short_name = source.readString();
        desc = source.readString();
        vice_chancellor = source.readString();
        year_of_establishment = source.readString();
        accredation = source.readString();
        university_type = source.readString();
        location = source.readString();
        address = source.readString();
        contact_numbers = source.readString();
        website = source.readString();
        emails = source.readString();
        total_students = source.readString();
        part_time_students_perc = source.readString();
        gender_ratio = source.readString();
        student_avg_age = source.readString();
        statewise_student_distribution = source.readString();
        campus_type = source.readString();
        girls_hostel_available = source.readString();
        hostel_student_percent = source.readString();
        hostel_capacity_boys = source.readString();
        hostel_capacity_girls = source.readString();
        hostel_occupancy = source.readString();
        hostel_internet = source.readString();
        mess_food_quality = source.readString();
        facilities = source.readString();
        health_clinic = source.readString();
        wheelchair_accessible = source.readString();
        visually_impaired_accessible = source.readString();
        personal_counselling_available = source.readString();
        career_counselling_available = source.readString();
        learning_outside_classroom = source.readString();
        exchange_program_available = source.readString();
        transfer_intra_allowed = source.readString();
        transfer_intra_application_date = source.readString();
        transfer_intra_eligibility = source.readString();
        transfer_inter_allowed = source.readString();
        transfer_inter_application_date = source.readString();
        transfer_inter_eligibility = source.readString();
        library = source.readString();
        awards = source.readString();
        rating_coolness = source.readString();
        popular_stream_1 = source.readString();
        popular_stream_2 = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(logo);
        //dest.writeLong(template_type);
        dest.writeString(short_name);
        dest.writeString(desc);
        dest.writeString(vice_chancellor);
        dest.writeString(year_of_establishment);
        dest.writeString(accredation);
        dest.writeString(university_type);
        dest.writeString(location);
        dest.writeString(address);
        dest.writeString(contact_numbers);
        dest.writeString(website);
        dest.writeString(emails);
        dest.writeString(total_students);
        dest.writeString(part_time_students_perc);
        dest.writeString(gender_ratio);
        dest.writeString(student_avg_age);
        dest.writeString(statewise_student_distribution);
        dest.writeString(campus_type);
        dest.writeString(girls_hostel_available);
        dest.writeString(hostel_student_percent);
        dest.writeString(hostel_capacity_boys);
        dest.writeString(hostel_capacity_girls);
        dest.writeString(hostel_occupancy);
        dest.writeString(hostel_internet);
        dest.writeString(mess_food_quality);
        dest.writeString(security);
        dest.writeString(facilities);
        dest.writeString(health_clinic);
        dest.writeString(wheelchair_accessible);
        dest.writeString(visually_impaired_accessible);
        dest.writeString(personal_counselling_available);
        dest.writeString(career_counselling_available);
        dest.writeString(learning_outside_classroom);
        dest.writeString(exchange_program_available);
        dest.writeString(transfer_intra_allowed);
        dest.writeString(transfer_intra_application_date);
        dest.writeString(transfer_intra_eligibility);
        dest.writeString(transfer_inter_allowed);
        dest.writeString(transfer_inter_application_date);
        dest.writeString(transfer_inter_eligibility);
        dest.writeString(library);
        dest.writeString(awards);
        dest.writeString(rating_coolness);
        dest.writeString(popular_stream_1);
        dest.writeString(popular_stream_2);
    }


}
