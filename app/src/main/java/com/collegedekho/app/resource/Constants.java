package com.collegedekho.app.resource;

import com.collegedekho.app.R;

import java.util.HashMap;

/**
 * @author Mayank Gautam
 *         Created: 29/06/15
 */
public class Constants
{

    public static final int[] headImages = {R.drawable.ic_question, 0, R.drawable.ic_cap};
    public static final String[] headLabels = {"I am not sure where to start", "",
            "College majors are my top priority"};
    public static final String[] headSubLabels = {"College search step-by-step", "", "Find college with majors you want"};
    public static final String PREFS = "sharedprefs";
    public static final String KEY_USER = "user_id";
    public static final String TAG_CREATE_USER = "create_user";
    public static final String TAG_LOAD_STREAM = "load_stream";
    public static final String TAG_UPDATE_STREAM = "update_stream";
    public static final String KEY_STREAMS = "streams";
    public static final String COMPLETED_SECOND_STAGE = "second_stage";
    public static final String WIDGET_INSTITUTES = "institutes";
    public static final String WIDGET_COURSES = "courses";
    public static final String WIDGET_NEWS = "news";
    public static final String WIDGET_ARTICES = "articles";
    public static final String WIDGET_FORUMS = "forums";
    //public static final String IP = "http://10.0.3.2";
    //public static final String IP = "http://www.launch.collegedekho.com";
    public static final String IP = "http://www.collegedekho.com";
    //public static final String IP = "http://192.168.8.101";
    //public static final String IP = "http://192.168.56.1";
    //public static final String BASE_URL = IP + ":8000/api/1/";
    public static final String BASE_URL = IP + "/api/1/";
    public static final String TAG_LOAD_COURSES = "load_courses";
    public static final String TAG_APPLIED_COURSE = "applied_course";
    public static final String TAG_LOAD_HOME = "home";
    public static final String TAG_POST_QUESTION = "post_question";
    public static final String TAG_LOAD_FILTERS = "load_filters";
    public static final String TAG_RESOURCE_URI = "resource_uri";
    public static final String TAG_LABEL = "label";
    public static final String TAG_URI = "uri";
    public static final String TAG_ATTR = "attr";
    public static final String TAG_TAGS = "tags";
    public static final String TAG_ID = "id";
    public static final String TAG_SELECTED = "is_selected";
    public static final String TAG_NEXT_INSTITUTE = "next_institutes";
    public static final String TAG_NEXT_NEWS = "next_news";
    public static final String TAG_NEXT_ARTICLES = "next_articles";
    public static final String TAG_LIKE_DISLIKE = "like_dislike";
    public static final String TAG_SHORTLIST_INSTITUTE = "shortlist";
    public static final String TAG_DELETESHORTLIST_INSTITUTE = "delete";
    public static final int NEITHER_LIKE_NOR_DISLIKE = -1;
    public static final String WIDGET_SHORTLIST = "shortlistedinstitutes";
    public static final String TAG_LOAD_PYSCHOMETRIC_TEST = "load_pyschometric_test";
    public static final String TAG_LOAD_QNA_QUESTIONS = "qna";
    public static final String TAG_LOAD_INSTITUTE_QNA_QUESTIONS = "institute_qna";
    public static final String TAG_FRAGMENT_INSTITUTE_LIST = "fragment_institute_list";
    public static final String TAG_FRAGMENT_PYSCHOMETRIC_TEST_QUESTION_LIST = "fragment_pyschometric_test_question_list";
    public static final String QTYPE_SINGLE = "single";
    public static final String QTYPE_MULTIPLE = "multiple";
    public static final String QTYPE_DROPDOWN = "dropdown";
    public static final String QTYPE_INPUT = "input";
    public static final String QTYPE_RANGE = "range";
    public static final String TAG_LOAD_MY_FUTURE_BUDDIES = "my_fb";
    public static final String TAG_LOAD_MY_FB = "load_my_fb";
    public static final String TAG_REFRESH_MY_FB = "refresh_my_fb";
    public static final String TAG_SUBMIT_PSYCHOMETRIC_TEST = "submit_psychometric_test";
    public static final String TAG_SUBMIT_PREFRENCES = "submit_prefrences";
    public static final String TAG_UPDATE_PREFRENCES = "update_prefrences";
    public static final String TAG_UPDATE_INSTITUTES = "update_institutes";
    public static final String TAG_FRAGMENT_FILTER_LIST = "fragment_filter_list";
    public static final String TAG_FRAGMENT_QNA_QUESTION_LIST = "fragment_qna_questions_list";
    public static final String TAG_FRAGMENT_WIDGET_LIST = "fragment_widget_list";
    public static final String TAG_FRAGMENT_NEWS_LIST = "fragment_news_list";
    public static final String TAG_FRAGMENT_NEWS = "fragment_news";
    public static final String TAG_FRAGMENT_ARTICLES_LIST = "fragment_articles_list";
    public static final String TAG_FRAGMENT_ARTICLE = "fragment_article";
    public static final String TAG_FRAGMENT_INSTITUTE = "fragment_institute";
    public static final String TAG_FRAGMENT_MY_FB = "fragment_my_fb";
    public static final String TAG_FRAGMENT_MY_FB_ENUMERATION = "fragment_my_fb_enumeration";
    public static final String TAG_FRAGMENT_STREAMS = "fragment_streams";
    public static final String TAG_FRAGMENT_HOME = "fragment_home";
    public static final String TAG_FRAGMENT_PROFILE = "fragment_profile";
    public static final String TAG_FRAGMENT_QNA_ANSWERS_LIST = "fragment_qna_answers_list";
    public static final String KEY_USER_LEARNED_TOUCH = "user_touch";
    public static final String TAG_FRAGMENT_LOGIN      = "fragment_signin";
    public static final String TAG_USER_LOGIN           = "user_login";
    public static final String TAG_USER_REGISTRATION    = "user_registeration";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final int LIKE_THING = 0;
    public static final int DISLIKE_THING = 1;
    public static final int SHORTLISTED_YES = 1;
    public static final int SHORTLISTED_NO = 0;
    public static final int MAIN_ANIMATION_TIME = 6500;
    public static final int SECONDARY_ANIMATION_TIME = 2000;
    public static final String SELECTED_FILTERS = "selected_filters";
    public static volatile boolean IS_CONNECTED_TO_INTERNET;
    public static final int MY_FB_REFRESH_RATE = 5000;
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final String SERVER_FAULT = "The servers made a boo boo. Try again later";
    public static final String NO_CONNECTION_FAULT = "You are not connected to internet. Please connect and try again.";
    public static final int FILTER_CATEGORY_COURSE_AND_SPECIALIZATION = 0;
    public static final int FILTER_CATEGORY_LOCATION = 1;
    public static final int FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES = 2;
    public static final int FILTER_CATEGORY_CAMPUS_AND_HOUSING = 3;

    public static final HashMap<Integer, Integer> FilterCategoryMap = new HashMap<Integer, Integer>();

    public static final int TYPE_SIMILARLAR_NEWS = 2;
    public static final int TYPE_NEWS = 1;
    public static final int TYPE_SIMILARLAR_ARTICLES = 2;
    public static final int TYPE_ARTCLES = 1;

    public static final int ID_CITY = 1;
    public static final int ID_EXAM = 11;
    public static final int ID_DEGREE = 2;
    public static final int ID_STREAM = 3;
    public static final int ID_LEVEL = 9;
    public static final int ID_SPECIALIZATION = 7;
    public static final int ID_FEE_RANGE = 10;
    public static final int ID_FACILITIES = 5;
    public static final int ID_STATE = 6;
    public static final int ID_INSTITUTE_TYPE = 4;
    public static final int ID_HOSTEL = 8;

    public static final int NUMBER_OF_COMMENTS_IN_ONE_GO = 100;

    public static final int INSTITUTE_TYPE   = 1;
    public static final int NEWS_TYPE        = 2;
    public static final int ARTICLES_TYPE    = 3;

    //User Attributes
    public static final String USER_NAME        = "name";
    public static final String USER_FIRST_NAME  = "first_name";
    public static final String USER_LAST_NAME   = "last_name";
    public static final String USER_FAMILY_NAME = "family_name";
    public static final String USER_PASSWORD    = "password";
    public static final String USER_ID          = "id";
    public static final String USER_TOKEN       = "token";
    public static final String USER_EMAIL       = "email";
    public static final String USER_VERIFIED_EMAIL = "verified_email";
    public static final String USER_STREAM      = "stream";
    public static final String USER_LEVEL       = "level";
    public static final String USER_STREAM_NAME = "stream_name";
    public static final String USER_LEVEL_NAME  = "level_name";
    public static final String USER_RESOURCE_URI = "user";
    public static final String USER_PHONE       = "phone_no";
    public static final String USER_IMAGE       = "user_image";
    public static final String USER_VERIFIED= "verified";
    public static final String USER_LOCALE = "locale";
    public static final String USER_GENDER = "gender";
    public static final String USER_TIMEZONE = "timezone";
    public static final String USER_UPDATED_TIME = "updated_time";
    public static final String USER_LINK = "link";
    public static final String USER_EXPIRE_AT = "expires_at";


    public static final String APPLY_YEAR = "year_of_admission";
    public static final String APPLY_COURSE= "institute_course";
    public static final String APPLY_INSTITUTE = "institute";


    public static final int APPLY_COMPLETE  = 0;
    public static final int APPLY_PENDING   = 1;
    public static final int APPLY_UPDATING  = 2;
    public static final String KEY_APPLY_STATUS = "apply_status";
    public static String INSTITUTE_ID ="institute_id_set";

    //GA Categories
    public static final String CATEGORY_PREFERENCE = "category_preference";
    public static final String CATEGORY_QNA = "category_qna";
    public static final String CATEGORY_MY_FB = "category_my_fb";
    public static final String CATEGORY_INSTITUTES = "category_institutes";

    //Actions
    public static final String ACTION_STREAM_CHANGED = "action_stream_changed";
    public static final String ACTION_MY_FB_SELECTED = "action_my_fb_selected";
    public static final String ACTION_QNA_QUESTION_ASKED = "action_qna_question_asked";
    public static final String ACTION_COURSE_APPLIED = "action_course_applied";
    public static final String ACTION_VOTE_QNA_QUESTION_ENTITY = "action_qna_question_entity_vote";
    public static final String ACTION_VOTE_QNA_ANSWER_ENTITY = "action_qna_answer_entity_vote";
    public static final String ACTION_QNA_ANSWER_SUBMITTED = "action_qna_answer_submitted";
    public static final String ACTION_MY_FB_COMMENT_SUBMITTED = "action_my_fb_comment_submitted";
    public static final String ACTION_INSTITUTE_SHORTLISTED = "action_institute_shortlisted";
    public static final String ACTION_INSTITUTE_SHORTLISTED_REMOVED = "action_institute_shortlisting_removed";

    //String Msg

    public static final String EMAIL_PASSOWRD_NOT_EXISTS ="The e-mail address and/or password you specified are not correct.";
    public static final String EMAIL_PASSOWRD_ALREADY_EXISTS = "A user is already registered with this e-mail address.";
    public static final String NAME_EMPTY       = "Please enter your name";
    public static final String NAME_INVALID     = "Please enter a valid name";
    public static final String PHONE_EMPTY      = "Please enter your phone number";
    public static final String PHONE_INVALID    = "Please enter a valid phone number";
    public static final String EMAIL_EMPTY      = "Please enter your email";
    public static final String EMAIL_INVALID    = "Please enter a valid email";
    public static final String PASSWORD_EMPTY   = "Please enter a password";

}
