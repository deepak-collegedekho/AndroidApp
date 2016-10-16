package com.collegedekho.app.service;

import android.os.AsyncTask;

import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class DeviceFcmRegistrationTask extends AsyncTask<Void, Void, Integer> {

    private final String mCDToken;
    private final String mDeviceID;
    private final String mFCMToken;

    public DeviceFcmRegistrationTask(String FCMToken, String cdToken, String deviceID) {
        this.mFCMToken = FCMToken;
        this.mCDToken = cdToken;
        this.mDeviceID = deviceID;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(Void... ps) {
        NetworkUtils utils = new NetworkUtils(this.mCDToken);

        JSONObject params = new JSONObject();
        try {
            params.put("device_id", mDeviceID);
            params.put("registration_id", mFCMToken);
            params.put("app_type", Constants.SOURCE_COLLEGE_DEKHO_APP);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String buff = utils.postData(Constants.BASE_URL+ "register-device/", params.toString());
        if(buff == null || buff.contains("ERROR")){
            return Constants.CODE_FAILED;
        }
        return Constants.CODE_SUCCESS;
    }

    @Override
    protected void onPostExecute(Integer errorCode) {

    }
}