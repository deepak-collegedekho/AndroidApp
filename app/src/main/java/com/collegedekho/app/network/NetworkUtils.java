package com.collegedekho.app.network;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
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
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.collegedekho.app.activity.MainActivity.mProfile;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public class NetworkUtils {

    private static final int MY_SOCKET_TIMEOUT_MS = 30000;
    private RequestQueue mQueue;
    private DataLoadListener mListener;
    private String mToken;
    private Context mApplicationContext;
    private HashMap<String, String> mHeaders;
    private HttpURLConnection con;

    public NetworkUtils(Context context, DataLoadListener listener) {
        mQueue = MySingleton.getInstance(context).getRequestQueue();
        mListener = listener;
        mApplicationContext = context.getApplicationContext();
    }

    public NetworkUtils(String mCDToken)
    {
        this.mToken = mCDToken;
    }

    public static int getConnectivityStatus(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isAvailable()
                && activeNetwork.isConnected()) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return Constants.TYPE_WIFI;
            else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return Constants.TYPE_MOBILE;
            else if(activeNetwork.getType() == ConnectivityManager.TYPE_VPN)
                return Constants.TYPE_VPN;
            else if(activeNetwork.getType() == ConnectivityManager.TYPE_BLUETOOTH)
                return Constants.TYPE_BLUETOOTH;
            else if(activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET)
                return Constants.TYPE_ETHERNET;
            else{
                return Constants.TYPE_OTHERS;
            }
        }
        return Constants.TYPE_NOT_CONNECTED;
    }

    public void setToken(String token) {
        this.mToken = token;
        this.mHeaders = null;
    }



    private void getOrDeleteData(@Nullable final String tag, final String url, final int method)
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
//        ((MainActivity)mApplicationContext).showProgressDialog(MainActivity.GetPersonalizedMessage(tag), Constants.THEME_BACKGROUND);
        simpleGetData(tag, url, Request.Method.GET);
    }

    private void simpleGetData(@Nullable final String tag, final String url, final int method) {
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

    private void postOrPutData(final String tag, final String url, final Map<String, String> params, final int method)
    {
        final Calendar calendar=Calendar.getInstance();
        StringRequest request = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)  {
                        Utils.logApiResponseTime(calendar, tag + " " + url);
                        mHandleSuccessResponse(tag, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
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

        if (tag != null) request.setTag(tag);
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
                        if (NetworkUtils.getConnectivityStatus(mApplicationContext) == Constants.TYPE_NOT_CONNECTED) {
                            mListener.onJsonObjectRequestError(tag, mApplicationContext.getString(R.string.no_internet_please_try_again), url, params, method);

                        }else if (volleyError.networkResponse == null && volleyError.getClass().equals(TimeoutError.class)) {
                            mListener.onJsonObjectRequestError(tag, mApplicationContext.getString(R.string.server_timeout_error),  url, params, method);
                        }
                        else  if(json != null) {
                            mListener.onJsonObjectRequestError(tag, mApplicationContext.getString(R.string.server_fault),  url, params, method);

                        }else {
                            mListener.onJsonObjectRequestError(tag, mApplicationContext.getString(R.string.unknown_server_error), url, params, method);
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

        if (tag != null)  request.setTag(tag);
        mQueue.add(request);
    }

    /**
     *  This method is used to set headers to request on server
     * @return return headers
     */

    private HashMap<String, String > getHeaders(){
        mHeaders = new HashMap<>();
        // set user'token if user token is not set
        if((this.mToken == null ||this.mToken.isEmpty()) && mProfile != null)
           this.mToken = mProfile.getToken();

        if(this.mToken != null && !this.mToken.isEmpty())
        {
            mHeaders.put("Authorization", "Token " + this.mToken);
            Log.e("TOKEN",mToken);
        }

        mHeaders.put("Content-Type", "application/form-data");
        mHeaders.put("Accept", "application/json");
       return  mHeaders;
    }


    private final String twoHyphens = "--";
    private final String lineEnd = "\r\n";
    private final String boundary = "androidclient-" + System.currentTimeMillis();
    private final String mimeType = "multipart/form-data; boundary=" + boundary;
    private byte[] multipartBody;

    public void postMultiPartRequest(final String TAG, final String url, byte fileData[]){

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


       RequestQueue requestQueue = Volley.newRequestQueue(mApplicationContext);
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
                mHandleErrorResponse(error, TAG, url, null, 0);
            }
        });

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(multipartRequest);
    }


    /**
     * This method is used to write to MultiPart request file in data output stream
     * @param dataOutputStream dataOutput Stream
     * @param fileData byte array of image file
     * @param fileName image file  name
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

    private String trimMessage(String json, String key) {
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
        if(url == null || url.isEmpty()){
            return;
        }
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

        SharedPreferences preferences = mApplicationContext.getSharedPreferences(mApplicationContext.getString(R.string.PREFS), Context.MODE_PRIVATE);
        String instituteId  = params.get(mApplicationContext.getString(R.string.APPLY_INSTITUTE));

        Set<String> idList = preferences.getStringSet(instituteId, new HashSet<String>());
        idList.add(params.get(mApplicationContext.getString(R.string.APPLY_COURSE)));

        preferences.edit().putString(Constants.INSTITUTE_ID, instituteId)
                .putStringSet(instituteId, idList)
                .putInt(Constants.KEY_APPLY_STATUS, Constants.APPLY_PENDING)
                .apply();
        Toast.makeText(mApplicationContext, "Failed to apply the course, will retry after sometime.", Toast.LENGTH_LONG).show();
    }

    /**
     * This method is used to handle success response  when volly
     *  request an api
     * @param tag  tag for which request has been sent to server
     * @param response server response
     */
    private void mHandleSuccessResponse(String tag, String response) {
        if(mListener != null) {
            mListener.onDataLoaded(tag, response);
        }
    }
    /**
     * This method is used to handle error response  when volly
     *  request an api
     * @param volleyError vollyError
     * @param tag  tag for which request has been sent to server
     * @param url server url
     * @param method method Type post or get
     */
    private void mHandleErrorResponse(VolleyError volleyError, String tag, String url, Map<String, String> params, int method){

        // set volly error on crashlytics;
        Crashlytics.logException(volleyError);
        Log.e("Network Utill", "your url is "+url);
        String json = null;
        int responseCode = -1;
        NetworkResponse response = volleyError.networkResponse;
        if (response != null && response.data != null)
        {
            responseCode = response.statusCode;
            json = new String(response.data);
        }

        String[] tags = tag.split("#");
       /* if(tags[0].equalsIgnoreCase(Constants.TAG_TRUE_SDK_LOGIN)  )
        {
            try {
                JSONObject jsonObj = new JSONObject(json);
                if(json != null) {
                    String code = jsonObj.getString("Code");
                    if (Integer.parseInt(code) == ErrorCode.LOGIN_PREFERENCE_CONFLICT) {
                         showDialogForStreamLevel(tag, url, jsonObj, params, method);
                        return;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else*/
        if(tags[0].equalsIgnoreCase(Constants.TAG_APPLIED_COURSE))
        {
            saveToSharedPref(params);
        }
        json = trimMessage(json, "detail");
        if (NetworkUtils.getConnectivityStatus(mApplicationContext) == Constants.TYPE_NOT_CONNECTED) {
            mListener.onError(tag, mApplicationContext.getString(R.string.no_internet_please_try_again), responseCode,url, params, method);

        }else if (volleyError.networkResponse == null && volleyError.getClass().equals(TimeoutError.class)) {
            mListener.onError(tag, mApplicationContext.getString(R.string.server_timeout_error), responseCode, url, params, method);
        }
        else  if(volleyError instanceof NoConnectionError || json != null) {
            mListener.onError(tag, mApplicationContext.getString(R.string.server_fault), responseCode,  url, params, method);

        }else {
            mListener.onError(tag, mApplicationContext.getString(R.string.unknown_server_error), responseCode, url, params, method);
        }
    }

    public String postData(String url, String data){
        String response =  null;
        try {
            URL u = new URL(url);
            //Crashlytics.setString("last_url", url);
            con = (HttpURLConnection) u.openConnection();
            //    con.setConnectTimeout(30000);
            //    con.setReadTimeout(30000);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Token " + this.mToken);
            if (Thread.interrupted())
                return null;
            try {
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setUseCaches(false);
                OutputStream out = new BufferedOutputStream(con.getOutputStream());
                writeStream(out, data);
                out.flush();
                InputStream in = new BufferedInputStream(con.getInputStream());
                response = readStream(in);
            } catch (IOException e1) {
                InputStream in = new BufferedInputStream(con.getErrorStream());
                response = readStream(in);
                switch (con.getResponseCode()) {
                    case HttpURLConnection.HTTP_INTERNAL_ERROR:
                        response = "ERROR 500: Server Error";
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        response = "ERROR 404: Link does not Exist";
                        break;
                    case HttpURLConnection.HTTP_BAD_REQUEST:
                        response = "ERROR::" + response;
                        break;
                }
            } finally {
                con.disconnect();
            }

        } catch (UnsupportedEncodingException e) {
            //Utils.logException(TAG,e);
//            Utils.sendException(mTracker, TAG, "UnsupportedEncodingException", url + ":" + e.getMessage());
            return "ERROR";
        } catch (IOException e) {
            //Utils.logException(TAG,e);
//            Utils.sendException(mTracker, TAG, "IOException", url + ":" + e.getMessage());
            return "ERROR";
        } catch (Exception e) {
            //Utils.logException(TAG,e);
//            Utils.sendException(mTracker, TAG, "Exception", url + ":" + e.getMessage());
            return "ERROR";
        }
        return response;
    }

    private void writeStream(OutputStream out, String data) throws IOException {
        BufferedWriter printWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        printWriter.write(data);
        printWriter.flush();
    }

    private String readStream(InputStream in) throws IOException {
        String response = "";
        byte[] buffer = new byte[8192];
        int read = in.read(buffer);
        while (read != -1 && !Thread.interrupted()) {
            response += new String(buffer, 0, read);
            read = in.read(buffer);
        }
        return response;
    }

    /**
     *  If user login with any social site like facebook and stream and level has conflict
     *  it shows a dialog to choose stream and level.
     * @param tag Tag
     * @param URL Api Url according to login
     * @param jsonObj response json
     * @param params request data send to api
     */
    public void showDialogForStreamLevel(final String tag, final String URL, JSONObject jsonObj, final Map<String, String> params, int method) {
        final Dialog dialog = new Dialog(mApplicationContext);
        dialog.setContentView(R.layout.layout_stream_conflict_dailog);
        dialog.setTitle("Select Your Stream and Level");
        RadioGroup streamRadioGroup = (RadioGroup) dialog.findViewById(R.id.stream_radio_group);
        RadioGroup levelRadioGroup = (RadioGroup) dialog.findViewById(R.id.level_radio_group);
        try {
            final String stream_id = jsonObj.getString(mApplicationContext.getString(R.string.USER_STREAM));
            final String level_id = jsonObj.getString(mApplicationContext.getString(R.string.USER_LEVEL));
            String streamName = jsonObj.getString(mApplicationContext.getString(R.string.USER_STREAM_NAME));
            String levelName = jsonObj.getString(mApplicationContext.getString(R.string.USER_LEVEL_NAME));
            if (mProfile.getPreferred_stream_id()== Integer.parseInt(stream_id))
                streamRadioGroup.setVisibility(View.GONE);

            ((RadioButton) dialog.findViewById(R.id.firstStream)).setText(streamName);
            ((RadioButton) dialog.findViewById(R.id.secondStream)).setText(mProfile.getPreferred_stream_short_name());

            ((RadioButton) dialog.findViewById(R.id.firstLevel)).setText(levelName);
            ((RadioButton) dialog.findViewById(R.id.secondLevel)).setText(mProfile.getPreferred_level_name());

            // if button is clicked, close the custom dialog
//            dialog.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                    if (((RadioButton) dialog.findViewById(R.id.firstStream)).isChecked())
//                        params.put(getResourceString(R.string.USER_STREAM), stream_id);
//                    if (((RadioButton) dialog.findViewById(R.id.secondStream)).isChecked())
//                        params.put(getResourceString(R.string.USER_STREAM), mProfile.getPreferred_stream_id());
//                    if (((RadioButton) dialog.findViewById(R.id.firstLevel)).isChecked())
//                        params.put(getResourceString(R.string.USER_LEVEL), level_id);
//                    if (((RadioButton) dialog.findViewById(R.id.secondLevel)).isChecked())
//                        params.put(getResourceString(R.string.USER_LEVEL), mDeviceProfile.getLevel());
//                    networkData(tag, URL, params, method);
//                }
//
//
//            });
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
