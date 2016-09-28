package com.collegedekho.app.listener;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public interface DataLoadListener {

    void onDataLoaded(String tag, String response);

    void onProfileImageUploadFailed();

    void onError(String tag, String response, int responseCode, String url, Map<String, String> params, int method);

    void onJsonObjectRequestError(String tag, String response, String url, JSONObject params, int method);

    void showDialogForStreamLevel(String tag, String url, JSONObject jsonObj, Map<String, String> params);

}
