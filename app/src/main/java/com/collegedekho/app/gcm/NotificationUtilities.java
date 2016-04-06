package com.collegedekho.app.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.collegedekho.app.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.collegedekho.app.gcm.AirBopCommonUtilities.AIRBOP_APP_KEY;
import static com.collegedekho.app.gcm.AirBopCommonUtilities.AIRBOP_APP_SECRET;
import static com.collegedekho.app.gcm.CommonUtilities.IS_REGISTERED;
import static com.collegedekho.app.gcm.CommonUtilities.SHARED_PREFS;
import static com.collegedekho.app.gcm.CommonUtilities.TAG;
import static com.collegedekho.app.gcm.CommonUtilities.displayMessage;

//import com.google.android.gcm.GCMRegistrar;


public final class NotificationUtilities {
	private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
    GoogleCloudMessaging gcm ;
    String regId="";


    //Post Param values
    public static Location mLocation = null;
    public static String mLanguage = null;
    public static String mCountry = null;
    public static String mState = null;
    public static String mLabel = null;
    public static String mRegId = null;
    //Post Param keys
    public static final String COUNTRY = "country";
    public static final String LABEL = "label";
    public static final String LANGUAGE = "lang";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "long";
    public static final String REGISTRATION_ID = "reg";
    public static final String STATE = "state";

    //Header keys
    public static final String HEADER_APP = "x-app-key";
    public static final String HEADER_TIMESTAMP = "x-timestamp";
    public static final String HEADER_SIGNATURE = "x-signature";

    //Defines
    private  static final int AIRBOP_TIMOUT_MILLI_SECONDS = 1000 * 60;
    private static final String PREFERENCES = "com.airbop.client.data";
    public static final String OUTPUT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss z";
    /**
     * Register this account/device pair within the server.
     *
     */
    public static void register(final Context context, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        mRegId=regId;
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
//                post(serverUrl, params);//TO REGISTER ON SERVER
                String airBopServerUrl = AirBopCommonUtilities.SERVER_URL + "register";
//                postToBopServer(airBopServerUrl,true,true);
                setRegistered(context, true);
                storeRegistrationId(context, regId);
                String message = context.getString(R.string.server_registered);
                CommonUtilities.displayMessage(context, message);
                return;
            } catch (Exception e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
    }

    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFS, Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }


    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFS, Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
//            return "";
        }
        return registrationId;
    }

    public static void setRegistered(Context context, boolean status) {
        final SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFS, Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IS_REGISTERED, status);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }


    public static boolean isRegistered(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_REGISTERED, false);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

    public static String computeHash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();

        byte[] byteData = digest.digest(input.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < byteData.length; i++){
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private static String constructSignatureURI(
            final String method
            , final String url
            , final String timestamp
            , final String body) {
        /**
         * The signature is constructed using the following value:
         * "POST" + request_uri + AIRBOP_APP_KEY + timestamp + request.body + AIRBOP_APP_SECRET
         */

        StringBuilder uriBuilder = new StringBuilder();
        // METHOD
        uriBuilder.append(method);
        // URL
        uriBuilder.append(url);
        // APP_KEY
        uriBuilder.append(AIRBOP_APP_KEY);
        // timestamp
        uriBuilder.append(timestamp);
        // body
        uriBuilder.append(body);
        // ssssshhhhhh
        uriBuilder.append(AIRBOP_APP_SECRET);

        return uriBuilder.toString();

    }

    private static String constructSendSignatureURI(
            final String method
            , final String url
            , final String timestamp
            , final String body) {
        /**
         * The signature is constructed using the following value:
         * "POST" + request_uri + AIRBOP_APP_KEY + timestamp + request.body + AIRBOP_APP_SECRET
         */

        StringBuilder uriBuilder = new StringBuilder();
        // METHOD
        uriBuilder.append(method);
        // URL
        uriBuilder.append(url);
        // APP_KEY
        uriBuilder.append(AIRBOP_APP_KEY);
        // timestamp
        uriBuilder.append(timestamp);
        // body
        uriBuilder.append(body);
        // ssssshhhhhh
        uriBuilder.append("1c6526ac81f2f46099ac9561d0197110d1d9c2e9aca195e29644f782508b05f8");

        return uriBuilder.toString();

    }

    public static String getBodyAsJSON(final boolean isRegister){
        List<Pair<String, String>> list_params = new ArrayList<>();
        fillAlphaPairList(list_params, isRegister);

        StringBuilder bodyBuilder = new StringBuilder();
        //Starting bracket
        bodyBuilder.append("{");
        Iterator<Pair<String, String>> iterator = list_params.iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Pair<String, String> keyValue = iterator.next();
            // name
            bodyBuilder.append('"').append(keyValue.first)
                    .append("\":");
            // value
            bodyBuilder.append('"').append(keyValue.second)
                    .append('"');
            //Do we need the comma?
            if (iterator.hasNext()) {
                bodyBuilder.append(',');
            }
        }
        //ending bracket
        bodyBuilder.append("}");

        return bodyBuilder.toString();
    }

    public static void fillAlphaPairList(List<Pair<String, String>> list_params
            , final boolean isRegister) {

        if ((isRegister) && (mCountry != null)) {
            list_params.add(Pair.create(COUNTRY, mCountry));
        }

        if ((isRegister) && (mLabel != null)) {
            list_params.add(Pair.create(LABEL, mLabel));
        }

        if ((isRegister) && (mLanguage != null)) {
            list_params.add(Pair.create(LANGUAGE, mLanguage));
        }

        if ((isRegister) && (mLocation != null)) {
            Float latitude = Float.valueOf(Double.toString(mLocation.getLatitude()));
            Float longitude = Float.valueOf(Double.toString(mLocation.getLongitude()));

            list_params.add(Pair.create(LATITUDE, latitude.toString()));
            list_params.add(Pair.create(LONGITUDE, longitude.toString()));
        }

        if (mRegId != null) {
            list_params.add(Pair.create(REGISTRATION_ID, mRegId));
        }

        if ((isRegister) && (mState != null)) {
            list_params.add(Pair.create(STATE, mState));
        }
    }
    public static String getCurrentTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static void postToBopServer(String url_endpoint
            , final boolean isRegister
            , final boolean asJSON)
            throws Exception{
        URL url;
        try {
            url = new URL(url_endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + url_endpoint);
        }

        //Timestamp
        String timestamp = getCurrentTimestamp();
        String body = getBody(isRegister, asJSON);
        String signature = constructSignatureURI("POST"
                , url_endpoint
                , timestamp
                , body);
        String signature_hash = signature;
        try {
            signature_hash = computeHash(signature);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.v(TAG, "Posting '" + body + "' to " + url);

        byte[] bytes = body.getBytes();

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(AIRBOP_TIMOUT_MILLI_SECONDS);
            conn.setReadTimeout (AIRBOP_TIMOUT_MILLI_SECONDS);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");

            if (asJSON) {
                conn.setRequestProperty("Content-Type",
                        "application/json;charset=UTF-8");
            } else {
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
            }
            // X Headers
            conn.addRequestProperty(HEADER_TIMESTAMP, timestamp);
            conn.addRequestProperty(HEADER_APP, AIRBOP_APP_KEY);
            conn.addRequestProperty(HEADER_SIGNATURE, signature_hash);

            Log.i(TAG, "About to post");

            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if ((status != 200)
                    && (status != 201)
                    && (status != 202)){
                if ((status >=500) && (status <= 599)) { //500 codes
                    throw new Exception(conn.getResponseMessage());
                } else if ((status >=400) && (status <= 499)) { //400 codes
                    throw new Exception(conn.getResponseMessage());
                }
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static void postMessageToBopServer(String url_endpoint,String message)
            throws Exception{
        URL url;
        try {
            url = new URL(url_endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + url_endpoint);
        }

        //Timestamp
        String timestamp = getCurrentTimestamp();
        JSONObject messageObject=new JSONObject();
        messageObject.putOpt("mode",1);
        JSONObject bodyObject=new JSONObject();
        bodyObject.putOpt("title","New message");
        bodyObject.putOpt("message",message);
        messageObject.putOpt("body",bodyObject);
        messageObject.putOpt("schedule",1);
        String body = messageObject.toString();
        String signature = constructSendSignatureURI("POST"
                , url_endpoint
                , timestamp
                , body);
        String signature_hash = signature;
        try {
            signature_hash = computeHash(signature);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.v(TAG, "Posting '" + body + "' to " + url);

        byte[] bytes = body.getBytes();

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
//            conn.setConnectTimeout(AIRBOP_TIMOUT_MILLI_SECONDS);
//            conn.setReadTimeout (AIRBOP_TIMOUT_MILLI_SECONDS);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");

            // X Headers
            conn.setRequestProperty("Content-Type",
                        "application/json;charset=UTF-8");
            conn.addRequestProperty(HEADER_APP, AIRBOP_APP_KEY);
            conn.addRequestProperty(HEADER_TIMESTAMP, timestamp);
            conn.addRequestProperty(HEADER_SIGNATURE, signature_hash);

            Log.i(TAG, "About to post");

            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if ((status != 200)
                    && (status != 201)
                    && (status != 202)){
                if ((status >=500) && (status <= 599)) { //500 codes
                    throw new Exception(conn.getResponseMessage());
                } else if ((status >=400) && (status <= 499)) { //400 codes
                    throw new Exception(conn.getResponseMessage());
                }
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String getBody(final boolean isRegister
            , final boolean asJSON) {
        if (asJSON) {
            return getBodyAsJSON(isRegister);
        } else {
            return getBodyAsUrlEncoded(isRegister);
        }
    }

    public static String getBodyAsUrlEncoded(final boolean isRegister){
        List<Pair<String, String>> list_params = new ArrayList<>();
        fillAlphaPairList(list_params, isRegister);

        StringBuilder bodyBuilder = new StringBuilder();
        //Starting bracket
        Iterator<Pair<String, String>> iterator = list_params.iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Pair<String, String> keyValue = iterator.next();
            bodyBuilder.append(keyValue.first).append('=')
                    .append(keyValue.second);
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }

        return bodyBuilder.toString();
    }


    public static void postBopMessage(final String endpoint, final String message)
            throws IOException {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    postMessageToBopServer(endpoint,message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(null, null, null);

    }
}
