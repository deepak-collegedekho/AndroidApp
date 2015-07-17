package com.collegedekho.app.resource;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
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

    public void getData(@Nullable final String tag, String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                    mListener.onError(tag, json);
                else
                    mListener.onError(tag, "Unidentified Error");
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
        request.setShouldCache(true);
        if (tag != null)
            request.setTag(tag);
        mQueue.add(request);
    }

    public void postData(final String tag, String url, final Map<String, String> params) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                    mListener.onError(tag, json);
                else
                    mListener.onError(tag, "Unidentified Error");
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
