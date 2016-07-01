package com.collegedekho.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.ErrorCode;
import com.collegedekho.app.resource.MySingleton;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public class NetworkUtils {

    private static final int MY_SOCKET_TIMEOUT_MS = 30000;
    private RequestQueue mQueue;
    private DataLoadListener mListener;
    private String mtoken;
    private static Context mContext;

    public NetworkUtils(Context context, DataLoadListener listener) {
        mQueue = MySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        mListener = listener;
        mContext = context;
    }

    public static int getConnectivityStatus()
    {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null
                && activeNetwork.isAvailable()
                && activeNetwork.isConnected()) {

            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return Constants.TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return Constants.TYPE_MOBILE;
        }

        return Constants.TYPE_NOT_CONNECTED;
    }

    public void setToken(String token) {
        this.mtoken = token;
    }

    public void getData(@Nullable final String tag, final String url) {
        getOrDeleteData(tag, url, Request.Method.GET);
    }

    public void getOrDeleteData(@Nullable final String tag, final String url, final int method)
    {
        final Calendar calendar=Calendar.getInstance();
        StringRequest request = new StringRequest(method, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Utils.logApiResponseTime(calendar,tag+" "+url);
                        mListener.onDataLoaded(tag, response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Utils.logApiResponseTime(calendar,tag+" "+url);
                        Crashlytics.logException(error);

                        String json = null;
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null)
                        {
                            json = new String(response.data);
                            json = trimMessage(json, "detail");
                        }
                        if (json != null) {
                            mListener.onError(tag, Constants.SERVER_FAULT, getResponseCode(response) , url, null, method);
                        } else {
                            int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
                            if (amIConnectedToInternet == Constants.TYPE_NOT_CONNECTED) {
                                mListener.onError(tag, Constants.NO_CONNECTION_FAULT,getResponseCode(response) , url, null, method);
                            } else {
                                mListener.onError(tag, Constants.UNKNOWN_ERROR,getResponseCode(response) , url, null, method);
                            }
                        }
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (mtoken != null)
                {
                    Map<String, String> params = new HashMap<>();

                    params.put("Authorization", "Token " + mtoken);
                    params.put("Accept", "application/json");

                    return params;
                }
                return super.getHeaders();
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);
        if (tag != null)
            request.setTag(tag);
        mQueue.add(request);
    }

    public void simpleGetData(@Nullable final String tag, final String url) {
        ((MainActivity)mContext).showProgressDialog(MainActivity.GetPersonalizedMessage(tag));
        simpleGetData(tag, url, Request.Method.GET);
    }

    public void simpleGetData(@Nullable final String tag, final String url, final int method) {
        final Calendar calendar = Calendar.getInstance();
        StringRequest request = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.logApiResponseTime(calendar, tag + " " + url);
                        mListener.onDataLoaded(tag, response);
                        ((MainActivity)mContext).hideProgressDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.logApiResponseTime(calendar, tag + " " + url);
                        Crashlytics.logException(error);
                        try {
                            String json = null;
                            NetworkResponse response = error.networkResponse;
                            if (response != null && response.data != null) {
                                json = new String(response.data);
                                json = trimMessage(json, "detail");
                            }
                            if (json != null) {
                                mListener.onError(tag, Constants.SERVER_FAULT,getResponseCode(response) , url, null, method);
                            } else {
                                int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
                                if (amIConnectedToInternet == Constants.TYPE_NOT_CONNECTED) {
                                    mListener.onError(tag, Constants.NO_CONNECTION_FAULT,getResponseCode(response) , url, null, method);
                                } else {
                                    mListener.onError(tag, Constants.UNKNOWN_ERROR,getResponseCode(response) , url, null, method);
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }) {
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);
        if (tag != null)
            request.setTag(tag);
        mQueue.add(request);
    }
    public void postData(final String tag, final String url, final Map<String, String> params) {
        postOrPutData(tag, url, params, Request.Method.POST);
    }

    public void putData(final String tag, final String url, final Map<String, String> params)
    {
        postOrPutData(tag, url, params, Request.Method.PUT);
    }

    public void deleteData(final String tag, final String url)
    {
        getOrDeleteData(tag, url, Request.Method.DELETE);
    }

    public void postOrPutData(final String tag, final String url, final Map<String, String> params, final int method)
    {
        final Calendar calendar=Calendar.getInstance();
        StringRequest request = new StringRequest(method, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Utils.logApiResponseTime(calendar, tag + " " + url);
                        mListener.onDataLoaded(tag, response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Utils.logApiResponseTime(calendar, tag + " " + url);
                        Crashlytics.logException(error);

                        String json = null;
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null)
                        {
                            json = new String(response.data);
                        }
                        String[] tags = tag.split("#");
                        if(tags[0].equalsIgnoreCase(Constants.TAG_TRUE_SDK_LOGIN) || tags[0].equalsIgnoreCase(Constants.TAG_FACEBOOK_LOGIN) )
                        {
                            if(json != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject(json);
                                    String code = jsonObj.getString("Code");
                                    if(Integer.parseInt(code) == ErrorCode.LOGIN_PREFERENCE_CONFLICT) {
                                        mListener.showDialogForStreamLevel(tag, url, jsonObj, params);
                                        return;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else if(tags[0].equalsIgnoreCase(Constants.TAG_APPLIED_COURSE))
                        {
                            saveToSharedPref(params);
                        }

                        json = trimMessage(json, "detail");
                        if (json != null) {
                            mListener.onError(tag, Constants.SERVER_FAULT,getResponseCode(response), url, params, method);
                        } else {
                            int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
                            if (amIConnectedToInternet == Constants.TYPE_NOT_CONNECTED) {
                                mListener.onError(tag, Constants.NO_CONNECTION_FAULT,getResponseCode(response) , url, params, method);
                            } else {
                                mListener.onError(tag, Constants.UNKNOWN_ERROR, getResponseCode(response) ,url, params, method);
                            }
                        }
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();

                if (mtoken != null)
                    params.put("Authorization", "Token " + mtoken);

                params.put("Content-Type", "application/form-data");
                params.put("Accept", "application/json");

                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy( MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);

        if (tag != null)
            request.setTag(tag);

        mQueue.add(request);
    }

    private void postOrPutData(final String tag, final String url, final JSONObject params, final int method)
    {
        final Calendar calendar=Calendar.getInstance();
        JsonObjectRequest request = new JsonObjectRequest(method,
                url, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Utils.logApiResponseTime(calendar,tag+" "+url);
                        mListener.onDataLoaded(tag, response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Utils.logApiResponseTime(calendar,tag+" "+url);
                        Crashlytics.logException(error);

                        String json = null;
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null)
                        {
                            json = new String(response.data);

                        }
                        json = trimMessage(json, "detail");
                        if (json != null) {
                            int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
                            if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {

                                mListener.onError(tag, Constants.NO_CONNECTION_FAULT, getResponseCode(response),url, null, method);
                            } else {
                                mListener.onJsonObjectRequestError(tag, Constants.SERVER_FAULT, url, params, method);
                            }
                        }else
                        {
                            int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
                            if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {

                                mListener.onError(tag, Constants.NO_CONNECTION_FAULT, getResponseCode(response),url, null, method);
                            } else {
                                mListener.onError(tag, Constants.UNKNOWN_ERROR, getResponseCode(response),url, null, method);
                            }
                        }
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                if (mtoken != null)
                    params.put("Authorization", "Token " + mtoken);

                params.put("Content-Type", "application/form-data");
                params.put("Accept", "application/json");
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);

        if (tag != null)
            request.setTag(tag);

        mQueue.add(request);
    }



    public String trimMessage(String json, String key) {
        String trimmedString;
        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public void networkData(String tag, String url, Map<String, String> params, int method)
    {
        switch (method) {
            case Request.Method.GET:
            case Request.Method.DELETE:
                this.getOrDeleteData(tag, url, method);
                break;
            case Request.Method.POST:
            case Request.Method.PUT:
                this.postOrPutData(tag, url, params, method);
                break;
        }
    }
    public void imageUpload(String tag, String url, Map<String, String> params, int method) {

        this.postOrPutData(tag, url, params, method);
    }

    public void networkDataWithObjectParam(String tag, String url, JSONObject params, int method)
    {
        this.postOrPutData(tag, url, params, method);
    }

    public void networkData(String tag, String url, Map<String, String> params)
    {
        if (params != null)
            networkData(tag, url, params, Request.Method.POST);
        else
            networkData(tag, url, null, Request.Method.GET);
    }



    private void saveToSharedPref(Map<String , String> params)
    {

        SharedPreferences preferences = mContext.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
        String instituteId  = params.get(MainActivity.getResourceString(R.string.APPLY_INSTITUTE));

        Set<String> idList = preferences.getStringSet(instituteId, new HashSet<String>());
        idList.add(params.get(MainActivity.getResourceString(R.string.APPLY_COURSE)));

        preferences.edit().putString(Constants.INSTITUTE_ID, instituteId)
                .putStringSet(instituteId, idList)
                .putInt(Constants.KEY_APPLY_STATUS, Constants.APPLY_PENDING)
                .apply();
        Toast.makeText(mContext, "Failed to apply the course, will retry after sometime.", Toast.LENGTH_LONG).show();

    }

    private int getResponseCode(NetworkResponse response){
        int responseCode = -1;
        if(response !=  null)
            responseCode = response.statusCode;
        return  responseCode;
    }


}
