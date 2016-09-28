package com.collegedekho.app.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.collegedekho.app.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;

class MultipartRequest extends Request<NetworkResponse> {
    private final Response.Listener<NetworkResponse> mListener;
    private final Response.ErrorListener mErrorListener;
    private Map<String, String> mHeaders;
    private final String mMimeType;
    private final byte[] mMultipartBody;

    public MultipartRequest(String url, String mimeType, byte[] multipartBody, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
        this.mMimeType = mimeType;
        this.mMultipartBody = multipartBody;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        mHeaders = new HashMap<>();
        mHeaders.put("Content-Type", "multipart/form-data");
        mHeaders.put("Accept", "application/json");
        if(MainActivity.mProfile != null)
            mHeaders.put("Authorization", "Token "+ MainActivity.mProfile.getToken());
        return (mHeaders != null) ? mHeaders : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return mMimeType;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mMultipartBody;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(
                    response,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }
}
