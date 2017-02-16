package com.collegedekho.app.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.fasterxml.jackson.jr.ob.JSON;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class PassiveProfileSyncTask extends AsyncTask<Void, Void, Integer> {

    private final String mCDToken;
    private final Context mContext;

    private JSONObject mParams;
    private Profile mProfile;

    public PassiveProfileSyncTask(Profile profile, String cdToken, Context context, Map<Object, Object> map) {
        this.mCDToken = cdToken;
        this.mProfile = profile;
        this.mContext = context;
        this.mParams = new JSONObject(map);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(Void... ps) {
        NetworkUtils utils = new NetworkUtils(this.mCDToken);

        String buff = utils.postData(Constants.BASE_URL+ "profile/", this.mParams.toString());
        if(buff == null || buff.contains("ERROR")){
            return Constants.CODE_FAILED;
        }
        return Constants.CODE_SUCCESS;
    }

    @Override
    protected void onPostExecute(Integer errorCode) {
        if (Constants.CODE_SUCCESS == errorCode)
        {
            String u = null;
            try {
                u = JSON.std.asString(this.mProfile);
                this.mContext.getSharedPreferences(this.mContext.getString(R.string.PREFS), MODE_PRIVATE).edit().putString(this.mContext.getString(R.string.KEY_USER), u).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}