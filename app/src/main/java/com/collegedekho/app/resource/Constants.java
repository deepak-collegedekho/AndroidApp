package com.collegedekho.app.resource;

import android.net.Uri;

import com.collegedekho.app.R;

import java.util.HashMap;

/**
 * @author Mayank Gautam
 *         Created: 29/06/15
 */
public class Constants
{
    public static final int RC_HANDLE_ALL_PERM =1;
    public static final int RC_HANDLE_CONTACTS_PERM =2;
    public static final int RC_HANDLE_STORAGE_PERM =3;
    public static final int RC_HANDLE_SMS_PERM=4;
    public static final int RC_QUIT_VIDEO_PLAYER=5;

    public static final int[] headImages = {R.drawable.ic_question, 0, R.drawable.ic_cap};
    public static final String[] headLabels = {"I am not sure where to start", "",
            "College majors are my top priority"};
    public static final String[] headSubLabels = {"College search step-by-step", "", "Find college with majors you want"};
    public static final String PREFS = "sharedprefs";
    public static final String KEY_USER = "user_id";
    public static final String ANONYMOUS_USER = "Anonymous user";
    public static final String TAG_FACEBOOK_LOGIN = "user_facebook_login";
    public static final String TAG_TRUE_SDK_LOGIN = "true_sdk_login";
    public static final String TAG_SKIP_LOGIN = "skip_login";
    public static final String TAG_USER_LOGIN = "user_login";
    public static final String TAG_USER_REGISTRATION = "user_registration";
    public static final String TAG_EDUCATION_DETAILS_SUBMIT = "user_education_submit";
    public static final String TAG_EDIT_EDUCATION_DETAILS_SUBMIT = "edited_user_education_submit";
    public static final String TAG_NOT_PREPARING_EDUCATION_DETAILS_SUBMIT = "user_not_preparing_education_submit";
    public static final String TAG_EXAMS_LIST = "exams_list";
    public static final String TAG_EDIT_EXAMS_LIST = "edit_exams_list";
    public static final String TAG_LOAD_EXAMS_LIST = "load_exams_list";

    public static final String TAG_SUBMIT_EXAMS_LIST = "submit_exams_list";
    public static final String TAG_LOAD_USER_PREFERENCES = "load_user_preferences";
    public static final String TAG_LOAD_USER_PREFERENCES_N_BACK = "load_user_preferences_n_back";
    public static final String TAG_LAUNCH_USER_HOME = "launch_user_home";
    public static final String TAG_SUBMIT_EDITED_EXAMS_LIST = "submit_edited_exams_list";
    public static final String TAG_SUBMIT_PSYCHOMETRIC_EXAM = "submit_psychometric_exam";
    public static final String TAG_SUBMIT_EDIT_PSYCHOMETRIC_EXAM = "submit_edit_psychometric_exam";
    public static final String TAG_SUBMIT_SBS_EXAM = "submit_sbs_exam";

    public static final String TAG_LOAD_STREAM = "load_stream";
    public static final String TAG_PSYCHOMETRIC_QUESTIONS = "load_psychometric_questions";
    public static final String TAG_PSYCHOMETRIC_RESPONSE = "load_psychometric_response";
    public static final String TAG_EDIT_PSYCHOMETRIC_QUESTIONS = "load_edit_psychometric_questions";
    public static final String TAG_LOAD_STEP_BY_STEP = "load_step_by_step";
    public static final String TAG_USER_EDUCATION = "load_user_education";
    public static final String TAG_EDIT_USER_EDUCATION = "load_edit_user_education";
    public static final String TAG_UPDATE_STREAM = "update_stream";
    public static final String WIDGET_INSTITUTES = "institutes";
    public static final String PNS_INSTITUTES = "pns_institutes";
    public static final String TAG_INSTITUTE_DETAILS = "institute_details";
    public static final String PNS_NEWS = "pns_news";
    public static final String PNS_FORUM = "pns_forum";
    public static final String PNS_QNA = "pns_qna";
    public static final String PNS_ARTICLES="pns_articles";
    public static final String WIDGET_RECOMMENDED_INSTITUTES = "recommended_institutes";
    public static final String WIDGET_SHORTLIST_INSTITUTES = "shortlist_institutes";
    public static final String WIDGET_SYLLABUS = "syllabus";
    public static final String WIDGET_TEST_CALENDAR = "test_calendar";
    public static final String PLAY_VIDEO_NOTIFICATION = "play_video_notification";

    public static final String SEARCHED_INSTITUTES = "searched_institutes";
    public static final String WIDGET_COURSES = "courses";
    public static final String WIDGET_NEWS = "news";
    public static final String WIDGET_ARTICES = "articles";
    public static final String WIDGET_FORUMS = "forums";
    public static final String TAG_LOAD_QNA_QUESTIONS = "qna";
    //public static final String IP = "http://10.0.3.2";
    //public static final String IP = "http://www.launch.collegedekho.com";
    public static final String IP = "http://www.collegedekho.com";
    //public static final String IP = "http://192.168.8.101";
    //public static final String IP = "http://192.168.56.1";
    //public static final String BASE_URL = IP + ":8000/api/1/";
    public static final String BASE_URL = IP + "/api/1/";
    public static final String TAG_LOAD_COURSES = "load_courses";
    public static final String TAG_APPLIED_COURSE = "applied_course";
    public static final String TAG_POST_QUESTION = "post_question";
    public static final String TAG_LOAD_FILTERS = "load_filters";
    public static final String TAG_RESOURCE_URI = "resource_uri";
    public static final String TAG_LABEL = "label";
    public static final String TAG_URI = "uri";
    public static final String TAG_ATTR = "attr";
    public static final String TAG_TAGS = "tags";
    public static final String TAG_ID = "id";
    public static final String TAG_SELECTED = "is_selected";
    public static final String TAG_NEXT_SHORTLIST_INSTITUTE = "next_shortlist_institutes";
    public static final String TAG_NEXT_INSTITUTE = "next_institutes";
    public static final String TAG_NEXT_NEWS = "next_news";
    public static final String TAG_NEXT_ARTICLES = "next_articles";
    public static final String TAG_NEXT_QNA_LIST = "next_aqna_list";
    public static final String TAG_NEXT_FORUMS_LIST = "next_forums_list";
    public static final String TAG_INSTITUTE_LIKE_DISLIKE = "institute_like_dislike";
    public static final String TAG_SHORTLIST_INSTITUTE = "shortlist";
    public static final String TAG_RECOMMENDED_SHORTLIST_INSTITUTE = "recommended_shortlist";
    public static final String TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE = "recommended_not_interested";
    public static final String TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE = "recommended_decide_later";
    public static final String TAG_LOAD_UNDECIDED_INSTITUTE = "load_undecided_later";
    public static final String TAG_DELETESHORTLIST_INSTITUTE = "delete";
    public static final int NEITHER_LIKE_NOR_DISLIKE = -1;
    public static final String TAG_LOAD_PSYCHOMETRIC_TEST = "load_psychometric_test";
    public static final String TAG_MY_ALERTS = "my_alerts";
    public static final String TAG_LOAD_INSTITUTE_QNA_QUESTIONS = "institute_qna";
    public static final String TAG_FRAGMENT_INSTITUTE_LIST = "fragment_institute_list";
    public static final String QTYPE_SINGLE = "single";
    public static final String QTYPE_MULTIPLE = "multiple";
    public static final String QTYPE_DROPDOWN = "dropdown";
    public static final String QTYPE_INPUT = "input";
    public static final String QTYPE_RANGE = "range";
    public static final String TAG_LOAD_MY_FB = "load_my_fb";
    public static final String TAG_REFRESH_MY_FB = "refresh_my_fb";
    public static final String TAG_SUBMIT_PSYCHOMETRIC_TEST = "submit_psychometric_test";
    public static final String TAG_SUBMIT_PREFRENCES = "submit_prefrences";
    public static final String TAG_SUBMIT_EDITED_PREFRENCES = "submit_edited_prefrences";
    public static final String TAG_UPDATE_PREFRENCES = "update_prefrences";
    public static final String TAG_UPDATE_INSTITUTES = "update_institutes";
    public static final String TAG_FRAGMENT_FILTER_LIST = "fragment_filter_list";
    public static final String TAG_FRAGMENT_QNA_QUESTION_LIST = "fragment_qna_questions_list";
    public static final String TAG_FRAGMENT_NEWS_LIST = "fragment_news_list";
    public static final String TAG_FRAGMENT_NEWS_DETAIL = "fragment_news_detail";
    public static final String TAG_FRAGMENT_ARTICLES_LIST = "fragment_articles_list";
    public static final String TAG_FRAGMENT_ARTICLE_DETAIL = "fragment_article_detail";
    public static final String TAG_FRAGMENT_INSTITUTE = "fragment_institute";
    public static final String TAG_FRAGMENT_MY_FB = "fragment_my_fb";
    public static final String TAG_FRAGMENT_MY_FB_ENUMERATION = "fragment_my_fb_enumeration";
    public static final String TAG_FRAGMENT_STREAMS = "fragment_streams";
    public static final int LIKE_THING = 0;
    public static final int DISLIKE_THING = 1;
    public static final int NOTINTERESTED_THING = -1;
    public static final int SHORTLISTED_YES = 1;
    public static final int SHORTLISTED_NO = 0;
    public static final int BOOLEAN_TRUE = 1;
    public static final int BOOLEAN_FALSE = 0;
    public static final String SELECTED_FILTERS = "selected_filters";
    public static final String TAG_QUESTION_LIKE_DISLIKE = "tag_question_like_dislike";
    public static final String TAG_EXAM_SUMMARY = "exam_summary";
    public static final String TAG_NAME_UPDATED = "name_updated";
    public static final String SUBMITTED_CHAPTER_STATUS = "chapter_submitted";
    public static final String TAG_PSYCHOMETRIC_TEXT_COMPLETED = "psychometric_text_completed";
    public static final String TAG_LOAD_INSTITUTE_NEWS = "institute_news";
    public static final String TAG_LOAD_INSTITUTE_ARTICLE = "institute_article";
    public static final String TAG_USER_EDUCATION_SET = "user_education_set";
    public static final String SELECTED_EXAM_ID = "selected_exam_id";
    public static final String SEARCH_INSTITUTES = "search_institutes";
    public static final String SEARCH_ARTICLES = "search_articles";
    public static final String SEARCH_NEWS = "search_news";
    public static final String SEARCH_QNA = "search_qna";

    public static boolean SEND_REQUEST = true;
    public static volatile boolean IS_CONNECTED_TO_INTERNET = true;
    public static final int MY_FB_REFRESH_RATE = 10000;
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final String SERVER_FAULT = "The servers made a boo boo. Try again later";
    public static final String NO_CONNECTION_FAULT = "You are not connected to internet. Please connect and try again.";
    public static final String UNKNOWN_ERROR = "Something went wrong. Please try again later";
    public static final int FILTER_CATEGORY_COURSE_AND_SPECIALIZATION = 0;
    public static final int FILTER_CATEGORY_LOCATION = 1;
    public static final int FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES = 2;
    public static final int FILTER_CATEGORY_CAMPUS_AND_HOUSING = 3;
    public static final String TAG_FRAGMENT_SHORTLISTED_INSTITUTE = "fragment_shortlisted_institute";

    public static final HashMap<Integer, Integer> FilterCategoryMap = new HashMap<>();


    public static final int VIEW_INTO_GRID  = 1;
    public static final int VIEW_INTO_LIST  = 2;

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

    public static final int INSTITUTE_TYPE   = 1 ;
    public static final int NEWS_TYPE        = 2 ;
    public static final int ARTICLES_TYPE    = 3 ;
    public static final int SHORTLIST_TYPE   = 4 ;
    public static final int QNA_LIST_TYPE    = 5 ;
    public static final int FORUM_LIST_TYPE  = 6 ;
    public static final int INSTITUTE_SEARCH_TYPE   = 7 ;

    public static final int APPLY_COMPLETE  = 0;
    public static final int APPLY_PENDING   = 1;
    public static final int APPLY_UPDATING  = 2;
    public static final String KEY_APPLY_STATUS = "apply_status";
    public static String INSTITUTE_ID ="institute_id_set";
    public static String YOUTUBE_DEVELOPER_KEY = "AIzaSyAGQbQY_dTXvNNoGs1X_JymCXNrpXdQhkk";
//    public static String YOUTUBE_DEVELOPER_KEY = "AIzaSyBIu-yzOCq2pg237na2kAGUqzlrb9su-6c";
    public static final String TAG_UPDATE_VIDEO_TITLE = "update_video_title";

    public static final String ACTION_VOTE_QNA_QUESTION_ENTITY = "action_qna_question_entity_vote";
    public static final String ACTION_VOTE_QNA_ANSWER_ENTITY = "action_qna_answer_entity_vote";
    public static final String ACTION_QNA_ANSWER_SUBMITTED = "action_qna_answer_submitted";
    public static final String ACTION_MY_FB_COMMENT_SUBMITTED = "action_my_fb_comment_submitted";
    public static final String ACTION_INSTITUTE_DISLIKED = "action_institute_disliked";
//    public static final String ACTION_WHEN_NOT_PREPARING = "action_when_not_preparing";
    public static final String TAG_USER_PHONE_ADDED = "user_phone_added";
    public static final String TAG_RESEND_OTP = "resend_otp";
    public static final String TAG_VERIFY_USER_PHONE = "action_user_login";
    public static final String USER_OTP = "user_otp";
    public static final String OTP_INTENT_FILTER = "com.cld.user.otp.filter";
    public static final String OTP_BODY = "Dear User,\nYour OTP for college app verification is-";
    public static final String OTP_CODE = "code";
    public static final String OTP_VERIFICATION = "otp_verification";
    public static final String LOGIN_TYPE_ANONYMOUS = "anonymous";
    public static final String LOGIN_TYPE_FACEBOOK  = "facebook";
    public static final String LOGIN_TYPE_TRUECALLER = "truecaller";

    // for close app
    public static boolean READY_TO_CLOSE = false;

    public static final String EXPANDED_INDICATOR = "\u2013";
    public static final String COLAPSED_INDICATOR = "+";

    // for app tutorials
    public static boolean IS_RECOMENDED_COLLEGE = false;
    public static final int REMOMMENDED_INSTITUTE_ACTION = 1;
    public static final int INSTITUTE_LIKE_DISLIKE = 0;

    public static long ANIM_SHORTEST_DURATION = 100;
    public static long ANIM_SHORT_DURATION = 200;
    public static long ANIM_AVERAGE_DURATION = 500;
    public static long ANIM_LONG_DURATION =  1000;

    public static final Uri BASE_APP_URI = Uri.parse("android-app://com.collegedekho.app/http/www.collegedekho.com/");

    public static final String CD_RECOMMENDED_INSTITUTE_ACTION_TYPE = "cd_recommended_institute_action_type";

    public enum CDRecommendedInstituteType {
        UNDECIDED, SHORTLISTED, NOT_INERESTED, UNBAISED
    }

    public static String MESSAGE="message";
    public static String COLLAPSE_KEY="collapse_key";
    public static String URL="url";

    // GA tacker ID
    public static final String TRACKER_ID = "UA-67752258-1";
    // Apps Flyer ID
    public static final String APPSFLYER_ID = "v3bLHGLaEavK2ePfvpj6aA" ;
    // Connecto ID for GCM
    public static final String SENDER_ID = "864760274938";

}
