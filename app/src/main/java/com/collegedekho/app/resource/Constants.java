package com.collegedekho.app.resource;

import com.collegedekho.app.R;

/**
 * @author Mayank Gautam
 *         Created: 29/06/15
 */
public class Constants {

    public static final int[] headImages = {R.drawable.ic_question, 0, R.drawable.ic_cap};
    public static final String[] headLabels = {"I am not sure where to start", "",
            "College majors are my top priority"};

    public static final String[] headSubLabels = {"College search step-by-step", "", "Find college with majors you want"};
    public static final String PREFS = "sharedprefs";
    public static final String KEY_USER = "user_id";
    public static final String TAG_CREATE_USER = "create_user";
    public static final String TAG_LOAD_STREAM = "load_stream";
    public static final String KEY_STREAMS = "streams";
    public static final String COMPLETED_SECOND_STAGE = "second_stage";
    public static final String WIDGET_INSTITUTES = "institutes";
    public static final String WIDGET_COURSES = "courses";
    public static final String WIDGET_NEWS = "news";
    public static final String WIDGET_ARTICES = "articles";
    //    public static final String IP = "http://10.0.3.2";
    public static final String IP = "http://www.launch.collegedekho.com";
    //public static final String IP = "http://192.168.8.100";
    //public static final String IP = "http://192.168.1.56";
    public static final String BASE_URL = IP + ":8000/api/1/";
    public static final String TAG_LOAD_COURSES = "load_courses";
    public static final String TAG_LOAD_HOME = "home";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final int LIKE_COLLEGE = 0;
    public static final int DISLIKE_COLLEGE = 1;
    public static final String TAG_POST_QUESTION = "post_question";
    public static final String TAG_LOAD_FILTERS = "load_filters";
    public static final String TAG_RESOURCE_URI = "resource_uri";
    public static final String TAG_LABEL = "label";
    public static final String TAG_URI = "uri";
    public static final String TAG_ATTR = "attr";
    public static final String TAG_TAGS = "tags";
    public static final String TAG_NEXT_INSTITUTE = "next_institutes";
    public static final String TAG_LIKE_DISLIKE = "like_dislike";
    public static final String TAG_SHORTLIST_INSTITUTE = "shortlist";
    public static final String TAG_DELETESHORTLIST_INSTITUTE = "delete";
    public static final int DELETE_LIKE = -1;
    public static final String WIDGET_SHORTLIST = "shortlistedinstitutes";
    public static final String TAG_LOAD_QUESTIONNAIRE = "load_questionnaire";
    public static final String TAG_FRAGMENT_INSTITUTE_LIST = "fragment_institute_list";

    //public static final String BASE_URL = "http://192.168.1.56:8000/api/1/";
}
