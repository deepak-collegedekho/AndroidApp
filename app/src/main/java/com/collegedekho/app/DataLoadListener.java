package com.collegedekho.app;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public interface DataLoadListener {

    public void onDataLoaded(String tag, String response);

    public void onError(String tag, String response);
}
