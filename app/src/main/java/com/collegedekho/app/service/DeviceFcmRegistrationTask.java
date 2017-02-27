package com.collegedekho.app.service;

import android.os.AsyncTask;

import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.network.NetworkUtils;

import org.json.JSONObject;

import java.util.Map;

public class DeviceFcmRegistrationTask extends AsyncTask<Void, Void, Integer> {

    private final String mCDToken;

    private JSONObject params;

    public DeviceFcmRegistrationTask(Map<String, String> map, String cdToken) {
        this.mCDToken = cdToken;
        this.params = new JSONObject(map);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(Void... ps) {
        NetworkUtils utils = new NetworkUtils(this.mCDToken);

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