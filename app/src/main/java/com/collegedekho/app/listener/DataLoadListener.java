package com.collegedekho.app.listener;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public interface DataLoadListener {

    public void onDataLoaded(String tag, String response);

    //public void onJsonObjectRequestDataLoaded(String tag, String response);

    public void onError(String tag, String response, String url, Map<String, String> params, int method);

    public void onJsonObjectRequestError(String tag, String response, String url, JSONObject params, int method);
}
