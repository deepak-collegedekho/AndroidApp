package com.collegedekho.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.ErrorCode;
import com.collegedekho.app.resource.MySingleton;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    private HashMap<String, String> mHeaders;

    public NetworkUtils(Context context, DataLoadListener listener) {
        mQueue = MySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        mListener = listener;
        mContext = context;
    }

    public static int getConnectivityStatus()
    {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isAvailable()
                && activeNetwork.isConnected()) {

            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return Constants.TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return Constants.TYPE_MOBILE;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_VPN)
                return Constants.TYPE_VPN;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_BLUETOOTH)
                return Constants.TYPE_BLUETOOTH;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET)
                return Constants.TYPE_ETHERNET;
        }

        return Constants.TYPE_NOT_CONNECTED;
    }

    public void setToken(String token) {
        this.mtoken = token;
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
                        mHandleSuccessResponse(tag, response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        Utils.logApiResponseTime(calendar,tag+" "+url);
                        mHandleErrorResponse(volleyError, tag, url, null, method);
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return (mHeaders != null && mHeaders.size() >3)? mHeaders :NetworkUtils.this.getHeaders();
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
                        mHandleSuccessResponse(tag, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Utils.logApiResponseTime(calendar, tag + " " + url);
                        mHandleErrorResponse(volleyError, tag, url, null , method);
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
    public void getData(@Nullable final String tag, final String url) {
        getOrDeleteData(tag, url, Request.Method.GET);
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
                        mHandleSuccessResponse(tag, response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        Utils.logApiResponseTime(calendar, tag + " " + url);
                        mHandleErrorResponse(volleyError, tag, url, params, method);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return (mHeaders != null && mHeaders.size() >3) ? mHeaders :NetworkUtils.this.getHeaders();
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
                        mHandleSuccessResponse(tag, response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        Utils.logApiResponseTime(calendar,tag+" "+url);

                        String json = null;
                        NetworkResponse response = volleyError.networkResponse;
                        if (response != null && response.data != null)
                        {
                            json = new String(response.data);

                        }
                        json = trimMessage(json, "detail");
                        int amIConnectedToInternet = MainActivity.mNetworkUtils.getConnectivityStatus();
                        if (json != null  && amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {

                            Crashlytics.logException(volleyError);
                                mListener.onJsonObjectRequestError(tag, Constants.SERVER_FAULT, url, params, method);
                        }else
                        {
                            mHandleErrorResponse(volleyError, tag, url, null, method);
                        }
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                return (mHeaders != null && mHeaders.size() >3) ? mHeaders :NetworkUtils.this.getHeaders();
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);

        if (tag != null)
            request.setTag(tag);

        mQueue.add(request);
    }

    /**
     *  This method is used to set headers to request on server
     * @return
     */

    private HashMap<String, String > getHeaders(){

        mHeaders = new HashMap<>();
        // set user'token if user token is not set
        if((mtoken == null || mtoken.isEmpty()) && MainActivity.mProfile != null)
            mtoken = MainActivity.mProfile.getToken();

        if(mtoken != null)
            mHeaders.put("Authorization", "Token " + mtoken);

        mHeaders.put("Content-Type", "application/form-data");
        mHeaders.put("Accept", "application/json");
       return  mHeaders;
    }


    private final String twoHyphens = "--";
    private final String lineEnd = "\r\n";
    private final String boundary = "androidclient-" + System.currentTimeMillis();
    private final String mimeType = "multipart/form-data; boundary=" + boundary;
    private byte[] multipartBody;

    public void postMultiPartRequest(final String TAG, String url, byte fileData[]){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            // file added
            buildPart(dos, fileData,"ic_"+System.currentTimeMillis()+"_profile.png");
            // send multipart form data necessary after file data
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // pass to multipart body
            multipartBody = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }


       RequestQueue requestQueue = Volley.newRequestQueue(mContext);
       MultipartRequest multipartRequest = new MultipartRequest(url, mimeType, multipartBody, new Response.Listener<NetworkResponse>() {
           @Override
            public void onResponse(NetworkResponse response) {
                try {
                    String responseJson = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    mListener.onDataLoaded(TAG, responseJson);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                   mListener.onProfileImageUploadFailed();

            }
        });

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(multipartRequest);
    }


    /**
     * This method is used to write to MutliPart request file in data output stream
     * @param dataOutputStream
     * @param fileData
     * @param fileName
     * @throws IOException
     */
    private void buildPart(DataOutputStream dataOutputStream, byte[] fileData, String fileName) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"" + lineEnd);
       // dataOutputStream.writeBytes("Content-Type: image/jpeg" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write it into form...
        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
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

        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.PREFS), Context.MODE_PRIVATE);
        String instituteId  = params.get(MainActivity.getResourceString(R.string.APPLY_INSTITUTE));

        Set<String> idList = preferences.getStringSet(instituteId, new HashSet<String>());
        idList.add(params.get(MainActivity.getResourceString(R.string.APPLY_COURSE)));

        preferences.edit().putString(Constants.INSTITUTE_ID, instituteId)
                .putStringSet(instituteId, idList)
                .putInt(Constants.KEY_APPLY_STATUS, Constants.APPLY_PENDING)
                .apply();
        Toast.makeText(mContext, "Failed to apply the course, will retry after sometime.", Toast.LENGTH_LONG).show();

    }
    /**
     * This method is used to handle success response  when volly
     *  request an api
     * @param tag
     * @param response
     */
    private void mHandleSuccessResponse(String tag, String response) {
        if(mListener != null) {
            mListener.onDataLoaded(tag, response);
        }
    }

    /**
     * This method is used to handle error response  when volly
     *  request an api
     * @param volleyError
     * @param tag
     * @param url
     * @param method
     */
    private void mHandleErrorResponse(VolleyError volleyError, String tag, String url, Map<String, String> params, int method){

        // set volly error on crashlytics;
        Crashlytics.logException(volleyError);

        String json = null;
        int responseCode = -1;
        NetworkResponse response = volleyError.networkResponse;
        if (response != null && response.data != null)
        {
            responseCode = response.statusCode;
            json = new String(response.data);
        }

        String[] tags = tag.split("#");
        if(tags[0].equalsIgnoreCase(Constants.TAG_TRUE_SDK_LOGIN) || tags[0].equalsIgnoreCase(Constants.TAG_FACEBOOK_LOGIN) )
        {
            try {
                JSONObject jsonObj = new JSONObject(json);
                if(json != null) {
                    String code = jsonObj.getString("Code");
                    if (Integer.parseInt(code) == ErrorCode.LOGIN_PREFERENCE_CONFLICT) {
                        mListener.showDialogForStreamLevel(tag, url, jsonObj, params);
                        return;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(tags[0].equalsIgnoreCase(Constants.TAG_APPLIED_COURSE))
        {
            saveToSharedPref(params);
        }

        json = trimMessage(json, "detail");

        int amIConnectedToInternet = MainActivity.mNetworkUtils.getConnectivityStatus();
        if (amIConnectedToInternet == Constants.TYPE_NOT_CONNECTED) {
            mListener.onError(tag, mContext.getString(R.string.no_internet_please_try_again), responseCode,url, params, method);

        }else if (volleyError.networkResponse == null && volleyError.getClass().equals(TimeoutError.class)) {
            mListener.onError(tag, mContext.getString(R.string.server_timeout_error), responseCode, url, params, method);
        }
        else  if(json != null) {
            mListener.onError(tag,mContext.getString(R.string.server_fault), responseCode,  url, params, method);

        }else {
            mListener.onError(tag,mContext.getString(R.string.unknown_server_error), responseCode, url, params, method);
        }
    }


}
