package com.collegedekho.app.resource;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by harshvardhan on 21/09/16.
 */

public class CustomStringRequest extends StringRequest {
    public CustomStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public CustomStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}
