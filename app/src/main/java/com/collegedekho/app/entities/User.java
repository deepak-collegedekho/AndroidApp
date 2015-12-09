package com.collegedekho.app.entities;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Patterns;

import com.collegedekho.app.utils.Utils;

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

    private String primaryEmail;
    private String primaryPhone;
    public String[] profileData = new String[3];

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

    public void processProfileData(Cursor cursor, Context context) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        if(emailPattern == null)return;

        cursor.moveToFirst();
        if(this.profileData==null)
            this.profileData = new String[3];
        this.profileData[1] = "[";
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
                this.profileData[1]+="\""+this.primaryEmail+"\",";
        }
        profileData[1]+="]";
        profileData[2]+="]";
    }

}
