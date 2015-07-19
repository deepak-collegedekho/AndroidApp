package com.collegedekho.app.resource;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.collegedekho.app.MySingleton;
import com.collegedekho.app.listener.DataLoadListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public class NetworkUtils {

    private static final int MY_SOCKET_TIMEOUT_MS = 30000;
    RequestQueue mQueue;
    DataLoadListener mListener;
    String token;

    public NetworkUtils(Context context, DataLoadListener listener) {
        mQueue = MySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        mListener = listener;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void getData(@Nullable final String tag, final String url) {
        getOrDeleteData(tag, url, Request.Method.GET);
    }

    public void getOrDeleteData(@Nullable final String tag, final String url, int method) {
        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mListener.onDataLoaded(tag, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    json = new String(response.data);
                    json = trimMessage(json, "detail");
                }
                if (json != null)
                    mListener.onError(tag, json, url, null);
                else
                    mListener.onError(tag, "Some Error ocurred please try again.", url, null);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (token != null) {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Token " + token);
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

    public void postData(final String tag, final String url, final Map<String, String> params) {
        postOrPutData(tag, url, params, Request.Method.POST);
    }

    public void putData(final String tag, final String url, final Map<String, String> params) {
        postOrPutData(tag, url, params, Request.Method.PUT);
    }

    public void deleteData(final String tag, final String url) {
        getOrDeleteData(tag, url, Request.Method.DELETE);
    }

    public void postOrPutData(final String tag, final String url, final Map<String, String> params, int method) {
        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mListener.onDataLoaded(tag, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    json = new String(response.data);
                    json = trimMessage(json, "detail");
                }
                if (json != null)
                    mListener.onError(tag, json, url, params);
                else
                    mListener.onError(tag, "Unable to process the request.", url, params);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Token " + token);
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
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
}
