package com.collegedekho.app.listener;

import java.util.Map;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public interface DataLoadListener {

    public void onDataLoaded(String tag, String response);

    public void onError(String tag, String response, String url, Map<String, String> params);
}
