package com.collegedekho.app.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.network.MySingleton;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by Suresh Kumar
 *  on 6/10/15.
 */
public class ApplyService extends Service {


    private static final int MY_SOCKET_TIMEOUT_MS = 30000;
    private String mToken ="";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


         new Thread(new Runnable() {
             @Override
             public void run() {

                 SharedPreferences preferences = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        String instituteId = preferences.getString(Constants.INSTITUTE_ID, null);
        if(instituteId != null) {
            Set<String> courseIdList = preferences.getStringSet(instituteId, new HashSet<String>());
            preferences.edit().putInt(Constants.KEY_APPLY_STATUS, Constants.APPLY_COMPLETE).apply();

            Iterator iterator = courseIdList.iterator();
            while (iterator.hasNext()) {
                String courseId = "" + iterator.next();
                applyCourse(courseId , instituteId);
            }
        }
             }
         }).start();

        return START_NOT_STICKY;
    }
    private void applyCourse(String instituteCourseID , String instituteID) {

        SharedPreferences preferences = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        HashMap<String, String> params = new HashMap<>();

        if (preferences.contains(Constants.KEY_USER)) {
            try {
                Profile user = JSON.std.beanFrom(Profile.class, preferences.getString(Constants.KEY_USER, null));
                if (user != null) {
                    params.put(getString( R.string.USER_NAME), user.getName());
                    params.put(getString( R.string.USER_EMAIL), user.getEmail());
                    params.put(getString( R.string.USER_PHONE), user.getPhone_no());
                    mToken = user.getToken();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            params.put(getString(R.string.APPLY_COURSE), instituteCourseID);
            params.put(getString(R.string.APPLY_INSTITUTE), "" + instituteID);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            params.put(getString(R.string.APPLY_YEAR), "" + year);

            String URL = Constants.BASE_URL + "lms/";
            if(MainActivity.mNetworkUtils != null)
            {
                MainActivity.mNetworkUtils.networkData(Constants.TAG_APPLIED_COURSE, URL, params, Request.Method.POST);
            }
            else {
                sendRequest(params, URL, Request.Method.POST);
            }
        }

    }

    private void sendRequest(final Map<String , String> params ,String URL , int method)
    {
        StringRequest request = new StringRequest(method, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        stopSelf();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        stopSelf();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Authorization", "Token " + mToken);
                params.put("Content-Type", "application/form-data");
                params.put("Accept", "application/json");

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);
        MySingleton.getInstance(getApplicationContext()).getRequestQueue().add(request);
    }


}
