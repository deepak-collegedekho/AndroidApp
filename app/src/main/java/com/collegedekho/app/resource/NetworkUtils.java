package com.collegedekho.app.resource;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.collegedekho.app.DataLoadListener;
import com.collegedekho.app.MySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public class NetworkUtils {

    RequestQueue mQueue;
    DataLoadListener mListener;

    public NetworkUtils(Context context, DataLoadListener listener) {
        mQueue = MySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        mListener = listener;
    }


    public void getData(@Nullable final String tag, String url) {
        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mListener.onDataLoaded(tag, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "Error ";
                if (error.networkResponse != null)
                    errorMessage += error.networkResponse.statusCode;
                errorMessage += ": " + error.getLocalizedMessage();
                mListener.onError(tag, errorMessage);
            }
        });
        request.setShouldCache(true);
        if (tag != null)
            request.setTag(tag);
        mQueue.add(request);
    }

    public void postData(final String tag, String url, final Map<String, String> params) {
        final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mListener.onDataLoaded(tag, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "Error ";
                if (error.networkResponse != null)
                    errorMessage += error.networkResponse.statusCode;
                errorMessage += ": " + error.getMessage();
                mListener.onError(tag, errorMessage);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        request.setShouldCache(true);
        if (tag != null)
            request.setTag(tag);
        mQueue.add(request);
    }
}
