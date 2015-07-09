package com.collegedekho.app.resource;

import com.collegedekho.app.R;

/**
 * @author Mayank Gautam
 *         Created: 29/06/15
 */
public class Constants {

    public static final int[] headImages = {R.drawable.ic_question, 0, R.drawable.ic_search, 0, R.drawable.ic_cap};
    public static final String[] headLabels = {"I am not sure where to start", "",
            "I know what I want", "",
            "College majors are my top priority"};

    public static final String[] headSubLabels = {"College search step-by-step", "",
            "Look up a college you're interested in", "", "Find college with majors you want"};
    public static final String PREFS = "sharedprefs";
    public static final String KEY_USER = "user_id";
    public static final String TAG_CREATE_USER = "create_user";
    public static final String TAG_LOAD_STREAM = "load_stream";
    public static final String KEY_STREAMS = "streams";
    public static final String COMPLETED_SECOND_STAGE = "second_stage";
    public static final String TAG_SEARCH = "search";
    public static final String BASE_URL = "http://192.168.1.56:8000/api/1/";
}
