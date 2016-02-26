package com.collegedekho.app.entities;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Patterns;

import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public class User
{
    private String id = "";
    private String resource_uri = "";
    private String email = "";
    private String username = "";
    private String name = "";
    private String added_on = "";
    private String image = "";
    private String token = "";
    private boolean is_anony;
    private Prefs pref;
    private String stream = "";
    private String level = "";
    private String phone_no = "";
    private String stream_name = "";
    private String level_name = "";
    private String  is_preparing= "";
    private String sublevel = "";
    private int education_set = 0;
    private int exams_set = 0;
    private int progress = 0;
    private ArrayList<ExamDetail> user_exams;
    private UserEducation user_education;
    private String preference_uri;
    private int is_otp_verified;
    private int partner_shortlist_count;
    private int blocking_otp;

    public String getPreference_uri() {
        return preference_uri;
    }

    public void setPreference_uri(String preference_uri) {
        this.preference_uri = preference_uri;
    }

    public ArrayList<String> getCollegedekho_recommended_streams() {
        return collegedekho_recommended_streams;
    }

    public void setCollegedekho_recommended_streams(ArrayList<String> collegedekho_recommended_streams) {
        this.collegedekho_recommended_streams = collegedekho_recommended_streams;
    }

    private ArrayList<String>collegedekho_recommended_streams;
    private int psychometric_given;

    private String primaryEmail;
    private String primaryPhone;
    public String[] profileData = new String[3];

    public ArrayList<ExamDetail> getUser_exams() {
        return user_exams;
    }

    public void setUser_exams(ArrayList<ExamDetail> user_exams) {
        this.user_exams = user_exams;
    }

    public UserEducation getUser_education() {
        return user_education;
    }

    public void setUser_education(UserEducation user_education) {
        this.user_education = user_education;
    }

    public interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int NAME = 0;
        int PHONE_NUMBER = 1;
        int IS_PRIMARY = 2;
    }


    public int getPsychometric_given() {
        return psychometric_given;
    }

    public void setPsychometric_given(int psychometric_given) {
        this.psychometric_given = psychometric_given;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdded_on() {
        return added_on;
    }

    public void setAdded_on(String added_on) {
        this.added_on = added_on;
    }

    public boolean is_anony() {
        return is_anony;
    }

    public Prefs getPref() {
        return pref;
    }

    public void setPref(Prefs pref) {
        this.pref = pref;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getIs_anony() {
        return is_anony;
    }

    public void setIs_anony(boolean is_anony) {
        this.is_anony = is_anony;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public enum Prefs {
        NOT_SURE, STREAMKNOWN
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStream_name() {
        return stream_name;
    }

    public void setStream_name(String stream_name) {
        this.stream_name = stream_name;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getIs_preparing() {
        return is_preparing;
    }

    public void setIs_preparing(String is_preparing) {
        this.is_preparing = is_preparing;
    }

    public String getSublevel() {
        return sublevel;
    }

    public void setSublevel(String sublevel) {
        this.sublevel = sublevel;
    }

    public int getEducation_set() {
        return education_set;
    }

    public void setEducation_set(int education_set) {
        this.education_set = education_set;
    }

    public int getExams_set() {
        return exams_set;
    }

    public void setExams_set(int exams_set) {
        this.exams_set = exams_set;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void processProfileData(Cursor cursor, Context context) {
        if(cursor == null)return;

        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        if(emailPattern == null)
            return;

        cursor.moveToFirst();

        if(this.profileData == null)
            this.profileData = new String[3];

        this.profileData[1] = "";
        this.profileData[2] = "[";

        while (!cursor.isAfterLast()) {
            this.profileData[0] = cursor.getString(ProfileQuery.NAME);
            String data = cursor.getString(ProfileQuery.PHONE_NUMBER);
            if(emailPattern.matcher(data).matches())
            {
                if(cursor.getInt(ProfileQuery.IS_PRIMARY)!=0)
                    this.primaryEmail = data;
                this.profileData[1]+="\""+data+"\",";
            }
            else
            {
                if(this.primaryPhone==null)
                    this.primaryPhone = data;
                this.profileData[2]+="\""+data+"\",";
            }
            cursor.moveToNext();
        }
        if(this.primaryEmail==null){
            this.primaryEmail = Utils.getDeviceEmail(context);
            if(this.primaryEmail!=null && !this.profileData[1].contains(this.primaryEmail))
                this.profileData[1]+=this.primaryEmail;
        }
/*        profileData[1]+="]";*/
        profileData[2]+="]";
    }

    public int getIs_otp_verified() {
        return is_otp_verified;
    }

    public void setIs_otp_verified(int is_otp_verified) {
        this.is_otp_verified = is_otp_verified;
    }

    public int getPartner_shortlist_count() {
        return partner_shortlist_count;
    }

    public void setPartner_shortlist_count(int partner_shortlist_count) {
        this.partner_shortlist_count = partner_shortlist_count;
    }

    public int getBlocking_otp() {
        return blocking_otp;
    }

    public void setBlocking_otp(int blocking_otp) {
        this.blocking_otp = blocking_otp;
    }
}
