package com.collegedekho.app.entities;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Patterns;

import com.collegedekho.app.utils.Utils;

import java.util.regex.Pattern;

/**
 * @author Suresh Saini
 *   Created: 20/07/16
 */
public class DeviceProfile
{

    private String primaryEmail ="";
    private String primaryPhone ="";
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


    public void processProfileData(Cursor cursor, Context context) {
        if(cursor == null)return;

        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        if(emailPattern == null)
            return;

        cursor.moveToFirst();

        if(this.profileData == null)
            this.profileData = new String[3];

//        this.profileData[1] = "";
//        this.profileData[2] = "[";

        while (!cursor.isAfterLast()) {
            this.profileData[0] = cursor.getString(ProfileQuery.NAME);
            String data = cursor.getString(ProfileQuery.PHONE_NUMBER);
            if(data==null || data.matches("")){
                continue;
            }
            if(emailPattern.matcher(data).matches())
            {
                if(cursor.getInt(ProfileQuery.IS_PRIMARY)!=0)
                    this.primaryEmail = data.replaceAll("\"","");
                this.profileData[1]= data.replaceAll("\"","");
            }
            else
            {
                if(this.primaryPhone == null || this.primaryPhone.isEmpty()) {
                    String number=data.replaceAll(" ","");
                    if(number.length()>10){
                        number=number.substring(number.length()-10,number.length());
                    }
                    this.primaryPhone = number;
                    this.profileData[2]=number;
                }

            }
            cursor.moveToNext();
        }
        if(this.primaryEmail==null){
            this.primaryEmail = Utils.getDeviceEmail(context);
            if(this.primaryEmail!=null && (this.profileData[1]==null || !this.profileData[1].contains(this.primaryEmail)))
                this.profileData[1]=this.primaryEmail;
        }
/*        profileData[1]+="]";*/
//        profileData[2]+="]";
    }


}
