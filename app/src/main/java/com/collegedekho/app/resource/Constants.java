package com.collegedekho.app.resource;

import android.net.Uri;

import java.util.HashMap;

public class Constants {
    public static final int RC_HANDLE_ALL_PERM = 1;
    public static final int RC_HANDLE_CONTACTS_PERM = 2;
    public static final int RC_HANDLE_STORAGE_PERM = 3;
    public static final int RC_HANDLE_SMS_PERM = 4;
    public static final int RC_QUIT_VIDEO_PLAYER = 5;
    public static final int RC_HANDLE_LOCATION = 6;
    public static final int RC_HANDLE_READ_SMS_AND_PHONE = 7;
    public static final int RC_HANDLE_ACCOUNTS_EMAIL = 8;

    public static final int GCM_RESULT_DATA_KEY = 201;

    public static final int APP_OLD_FLOW = 1;
    public static final int APP_NEW_FLOW = 2;

    public static final String KEY_USER = "user_id";
    public static final String ANONYMOUS_USER = "Anonymous User";
    public static final String TAG_FACEBOOK_LOGIN = "user_facebook_login";
    public static final String TAG_TRUE_SDK_LOGIN = "true_sdk_login";
    public static final String TAG_CREATE_USER = "tag_splash_create_user";
    public static final String TAG_CREATING_USER = "tag_creating_user";
    public static final String TAG_SPLASH_LOGIN_PROCEED = "tag_splash_login_proceed";
    public static final String TAG_PHONE_NUMBER_LOGIN = "phone_number_login";
    public static final String TAG_USER_LOGIN = "user_login";
    public static final String TAG_EDUCATION_DETAILS_SUBMIT = "user_education_submit";

    public static final String TAG_USER_EXAMS_SUBMISSION = "user_exam_submission";
    public static final String TAG_USER_EXAMS_DELETE = "user_exam_delete";
    public static final String TAKE_ME_TO_RECOMMENDED = "take_me_to_recommended";
    public static final String TAG_SUBMIT_EDITED_EXAMS_LIST = "submit_edited_exams_list";
    public static final String TAG_SUBMIT_PSYCHOMETRIC_EXAM = "submit_psychometric_exam";
    public static final String TAG_SUBMIT_EDIT_PSYCHOMETRIC_EXAM = "submit_edit_psychometric_exam";
    public static final String TAG_SUBMIT_SBS_EXAM = "submit_sbs_exam";

    public static final String TAG_LOAD_STREAM = "load_stream";
    public static final String TAG_PSYCHOMETRIC_QUESTIONS = "load_psychometric_questions";
    public static final String TAG_PSYCHOMETRIC_RESPONSE = "load_psychometric_response";
    public static final String TAG_LOAD_STEP_BY_STEP = "load_step_by_step";
    public static final String TAG_LOAD_STEP_BY_STEP_FROM_PROFILE_BUILDING = "load_step_by_step_from_profile_building";
    public static final String TAG_UPDATE_STREAM = "update_stream";
    public static final String WIDGET_INSTITUTES = "institutes";
    public static final String WIDGET_INSTITUTES_SBS = "institutes_sbs";
    public static final String WIDGET_TRENDING_INSTITUTES = "trending_institutes";
    public static final String PNS_INSTITUTES = "pns_institutes";
    public static final String TAG_INSTITUTE_DETAILS = "institute_details";
    public static final String PNS_NEWS = "pns_news";
    public static final String PNS_FORUM = "pns_forum";
    public static final String PNS_QNA = "pns_qna";
    public static final String PNS_ARTICLES = "pns_articles";
    public static final String WIDGET_RECOMMENDED_INSTITUTES = "recommended_institutes";
    public static final String RECOMMENDED_INSTITUTE_FEED_LIST = "recommended_institutes_list";
    public static final String PROFILE_COMPLETION_OTP = "ProfileFragment_otp";
    public static final String TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST = "recommended_institutes";
    public static final String WIDGET_RECOMMENDED_INSTITUTES_FRON_PROFILE = "recommended_institutes_from_profile";
    public static final String WIDGET_SHORTLIST_INSTITUTES = "shortlist_institutes";
    public static final String CARD_SHORTLIST_INSTITUTES = "card_shortlist_institutes";
    public static final String CARD_BUZZLIST_INSTITUTES = "card_buzzlist_institutes";
    public static final String WIDGET_SYLLABUS = "syllabus";
    public static final String WIDGET_TEST_CALENDAR = "test_calendar";
    public static final String PLAY_VIDEO_NOTIFICATION = "play_video_notification";

    public static final String SEARCHED_INSTITUTES = "searched_institutes";
    public static final String WIDGET_COURSES = "courses";
    public static final String WIDGET_NEWS = "news";
    public static final String WIDGET_ARTICES = "articles";
    public static final String WIDGET_FORUMS = "forums";
    public static final String REFRESH_CHATROOM = "refresh_chatroom";
    public static final String TAG_LOAD_QNA_QUESTIONS = "qna";

    // TAGs for CD Recommended Fragment Volley Requests.
    public static final String TAG_CD_RECOMMENDED = "tag_cd_recommended";
    public static final String TAG_CD_FEATURED = "tag_cd_featured";
    public static final String TAG_CD_UNDECIDED = "tag_cd_undecided";
    public static final String TAG_CD_SHORTLIST = "tag_cd_shortlist";

    public static final String CAF_URL = "https://m.collegedekho.com/caf-login-signup/";
    public static final String TAG_LOAD_COURSES = "load_courses";
    public static final String TAG_LOAD_PROFILE = "load_profile";
    public static final String TAG_REFRESH_PROFILE = "refresh_profile";
    public static final String TAG_APPLIED_COURSE = "applied_course";
    public static final String TAG_WISH_LIST_APPLIED_COURSE = "wish_listLapplied_course";
    public static final String TAG_POST_QUESTION = "post_question";
    public static final String TAG_LOAD_FILTERS = "load_filters";
    public static final String TAG_RESOURCE_URI = "resource_uri";
    public static final String TAG_LABEL = "label";
    public static final String TAG_URI = "uri";
    public static final String TAG_ATTR = "attr";
    public static final String TAG_PARAM = "param";
    public static final String TAG_NAME = "name";
    public static final String TAG_SHORT_NAME = "short_name";
    public static final String TAG_TAGS = "tags";
    public static final String TAG_CURRENCIES = "currencies";
    public static final String TAG_ID = "id";
    public static final String TAG_SELECTED = "is_selected";
    public static final String TAG_NEXT_WISHLIST_INSTITUTE = "next_wish_list_institutes";
    public static final String TAG_LAST_SHORTLIST_INSTITUTES_WHILE_REMOVING = "last_shortlist_institutes_while_removing";
    public static final String TAG_NEXT_INSTITUTE = "next_institutes";
    public static final String TAG_NEXT_FEED = "next_feed";
    public static final String TAG_NEXT_NEWS = "next_news";
    public static final String TAG_NEXT_ARTICLES = "next_articles";
    public static final String TAG_NEXT_QNA_LIST = "next_aqna_list";
    public static final String TAG_NEXT_FORUMS_LIST = "next_forums_list";
    public static final String TAG_INSTITUTE_LIKE_DISLIKE = "institute_like_dislike";
    public static final String TAG_SHORTLIST_INSTITUTE = "shortlist";
    public static final String TAG_RECOMMENDED_SHORTLIST_INSTITUTE = "recommended_shortlist";
    public static final String TAG_RECOMMENDED_APPLIED_SHORTLIST_INSTITUTE = "recommended_apply_shortlist";
    public static final String TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE = "recommended_not_interested";
    public static final String TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE = "recommended_decide_later";
    public static final String TAG_LOAD_UNDECIDED_INSTITUTE = "load_undecided_later";
    public static final String TAG_LOAD_BUZZLIST_INSTITUTE = "load_more_buzzlist";
    public static final String TAG_DELETESHORTLIST_INSTITUTE = "delete";
    public static final String CARD_DELETE_SHORTLISTED_INSTITUTE = "card_shortlisted_delete";
    public static final int NEITHER_LIKE_NOR_DISLIKE = -1;
    public static final String TAG_MY_ALERTS = "my_alerts";
    public static final String TAG_LOAD_INSTITUTE_QNA_QUESTIONS = "institute_qna";
    public static final String QTYPE_SINGLE = "single";
    public static final String QTYPE_MULTIPLE = "multiple";
    public static final String QTYPE_DROPDOWN = "dropdown";
    public static final String QTYPE_INPUT = "input";
    public static final String QTYPE_RANGE = "range";
    public static final String TAG_LOAD_MY_FB = "load_my_fb";
    public static final String TAG_LOAD_MORE_FB_COMMENT = "load_more_fb_comments";
    public static final String TAG_REFRESH_MY_FB = "refresh_my_fb";
    public static final String TAG_UPDATE_USER_PROFILE = "update_user_profile";
    public static final String TAG_UPDATE_PROFILE_EXAMS = "update_profile_exams";
    public static final String TAG_UPDATE_PROFILE_OBJECT = "update_profile_object";
    public static final String TAG_REQUEST_FOR_SPECIALIZATION = "request_for_specialization";
    public static final String TAG_REQUEST_FOR_DEGREES = "request_for_degrees";
    public static final String TAG_REQUEST_FOR_EXAMS = "request_for_exams";
    public static final String TAG_UPDATE_INSTITUTES = "update_institutes";
    public static final String TAG_FCM_TOKEN_SYNC = "fcm_token_sync";
    public static final String TAG_RESOLVE_DEEPLINK_URL = "resolve_deeplink_url";

    public static final String TAG_FRAGMENT_FILTER_LIST = "fragment_filter_list";
    public static final String TAG_FRAGMENT_QNA_QUESTION_LIST = "fragment_qna_questions_list";
    public static final String TAG_FRAGMENT_NEWS_LIST = "fragment_news_list";
    public static final String TAG_FRAGMENT_NEWS_DETAIL = "fragment_news_detail";
    public static final String TAG_FRAGMENT_ARTICLES_LIST = "fragment_articles_list";
    public static final String TAG_FRAGMENT_ARTICLE_DETAIL = "fragment_article_detail";
    public static final String TAG_FRAGMENT_INSTITUTE = "fragment_institute";
    public static final String TAG_FRAGMENT_MY_FB = "MyFutureBuddiesFragment";
    public static final String TAG_FRAGMENT_MY_FB_ENUMERATION = "fragment_my_fb_enumeration";
    public static final String TAG_FRAGMENT_INSTITUTE_LIST = "fragment_institute_list";
    public static final String TAG_FRAGMENT_COUNSELOR_CHAT = "fragment_counselor_chat";
    public static final String TAG_FRAGMENT_WISHLIST_INSTITUTE_LIST = "fragment_wishlist_institute_list";
    public static final String TAG_FRAGMENT_STREAMS = "fragment_streams";
    public static final String TAG_PROFILE_FIX = "profile_fix";
    public static final String TAG_PROFILE_FRAGMENT = "ProfileFragment";
    public static final String TAG_COUNSELOR_REFRESH = "tag_counselor_refresh";
    public static final int LIKE_THING = 0;
    public static final int DISLIKE_THING = 1;
    public static final int NOT_INTERESTED_THING = -1;
    public static final int SHORTLISTED_YES = 1;
    public static final int SHORTLISTED_NO = 0;
    public static final int BOOLEAN_TRUE = 1;
    public static final int BOOLEAN_FALSE = 0;
    public static final String SELECTED_FILTERS = "selected_filters";
    public static final String TAG_QUESTION_LIKE_DISLIKE = "tag_question_like_dislike";
    public static final String TAG_EXAM_SUMMARY = "exam_summary";
    public static final String TAG_NAME_UPDATED = "name_updated";
    public static final String SUBMITTED_CHAPTER_STATUS = "chapter_submitted";
    public static final String TAG_LOAD_INSTITUTE_NEWS = "institute_news";
    public static final String TAG_LOAD_INSTITUTE_ARTICLE = "institute_article";
    public static final String SELECTED_EXAM_ID = "selected_exam_id";
    public static final String SEARCH_INSTITUTES = "search_institutes";
    public static final String SEARCH_ARTICLES = "search_articles";
    public static final String SEARCH_NEWS = "search_news";
    public static final String SEARCH_QNA = "search_qna";
    public static final String SEARCH_COURSES = "search_courses";
    public static final String PROFILE_IMAGE_UPLOADING = "profile_image_uploading";
    public static final String TAG_LOAD_SUB_LEVELS = "tag_load_sub_levels";
    public static final String TAG_LOAD_LEVEL_STREAMS = "tag_load_level_streams";
    public static final String TAG_LOAD_COUNTRIES = "tag_load_countries";
    public static final String TAG_LOAD_CURRENCIES = "tag_load_currencies";
    public static final String TAG_LOAD_STATES = "tag_load_states";
    public static final String TAG_LOAD_CITIES = "tag_load_cities";
    public static final String TAG_UPDATE_COUNTRIES = "tag_update_countries";
    public static final String TAG_LOCATION_UPDATED = "tag_location_updated";
    public static final String TAG_LOAD_FEED = "tag_load_feed";
    public static final String TAG_REFRESHED_FEED = "tag_refreshed_feed";
    public static final String TAG_LOAD_COUNSELOR_CHAT = "tag_load_counselor_chat";
    public static final String PNS_COUNSELOR_CHAT = "pns_counselor_chat";
    public static final String TAG_SIMILAR_QUESTIONS = "tag_similar_questions";
    public static final String TAG_FEED_ACTION = "feed_action";
    public static final String FEED_RECO_ACTION = "feed_reco_action";
    public static final String FEED_RECO_INSTITUTE_DETAILS_ACTION = "feed_reco_institute_details_action";
    public static final String FEED_SEE_ALL_ACTION = "feed_see_all_action";
    public static final String FEED_SEE_SHORTLISTED_INSTITUTES = "feed_see_shortlisted_institutes";
    public static final String FEED_SHARE_ACTION = "feed_share_action";
    public static final String ACTION_VERIFY_OTP = "action_verify_otp";
    public static final String TAG_LOAD_INSTITUTE = "tag_load_institute";
    public static final String TAG_FRAGMENT_QNA_QUESTION_DETAIL = "fragment_qna_question_detail";
    public static final String SET_SELECTED_COURSE = "set_selected_course";
    public static final int MY_FB_REFRESH_RATE = 10000;
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_BLUETOOTH = 7;
    public static final int TYPE_ETHERNET = 9;
    public static final int TYPE_VPN = 17;
    public static final int TYPE_OTHERS = 17;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final String SERVER_FAULT = "The servers made a boo boo. Try again later";
    public static final int FILTER_CATEGORY_COURSE_AND_SPECIALIZATION = 0;
    public static final int FILTER_CATEGORY_LOCATION = 1;
    public static final int FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES = 2;
    public static final int FILTER_CATEGORY_CAMPUS_AND_HOUSING = 3;
    public static final String TAG_FRAGMENT_SHORTLISTED_INSTITUTE = "fragment_shortlisted_institute";
    public static final HashMap<Integer, Integer> FilterCategoryMap = new HashMap<>();
    public static final int VIEW_INTO_GRID = 1;
    public static final int VIEW_INTO_LIST = 2;
    public static final int THEME_TRANSPARENT = 1;
    public static final int THEME_BACKGROUND = 2;
    public static final int ID_CITY = 1;
    public static final int ID_DEGREE = 2;
    public static final int ID_STREAM = 3;
    public static final int ID_INSTITUTE_TYPE = 4;
    public static final int ID_FACILITIES = 5;
    public static final int ID_STATE = 6;
    public static final int ID_SPECIALIZATION = 7;
    public static final int ID_HOSTEL = 8;
    public static final int ID_LEVEL = 9;
    public static final int ID_FEE_RANGE = 10;
    public static final int ID_FEE_ABROAD = 16;
    public static final int ID_EXAM = 11;
    public static final int ID_COUNTRY = 15;
    public static final int NUMBER_OF_COMMENTS_IN_ONE_GO = 100;
    public static final int INSTITUTE_TYPE = 1;
    public static final int NEWS_TYPE = 2;
    public static final int ARTICLES_TYPE = 3;
    // public static final int SHORTLIST_TYPE   = 4 ;
    public static final int QNA_LIST_TYPE = 5;
    public static final int FORUM_LIST_TYPE = 6;
    public static final int INSTITUTE_SEARCH_TYPE = 7;
    public static final int WISH_LIST_TYPE = 8;
    public static final int FEED_TYPE = 9;
    public static final int APPLY_COMPLETE = 0;
    public static final int APPLY_PENDING = 1;
    public static final int APPLY_UPDATING = 2;
    public static final String KEY_APPLY_STATUS = "apply_status";
    public static final String TAG_UPDATE_VIDEO_TITLE = "update_video_title";
    public static final int TAG_STUDY_ABROAD_FOLDER_ID = 16;
    public static final String ACTION_VOTE_QNA_QUESTION_ENTITY = "action_qna_question_entity_vote";
    public static final String ACTION_VOTE_QNA_ANSWER_ENTITY = "action_qna_answer_entity_vote";
    public static final String ACTION_QNA_ANSWER_SUBMITTED = "action_qna_answer_submitted";
    public static final String ACTION_MY_FB_COMMENT_SUBMITTED = "action_my_fb_comment_submitted";
    public static final String ACTION_INSTITUTE_DISLIKED = "action_institute_disliked";
    public static final String TAG_REQUEST_FOR_OTP = "user_phone_added";
    public static final String USER_OTP = "user_otp";
    public static final String OTP_INTENT_FILTER = "com.cld.user.otp.filter";
    public static final String OTP_BODY = "Dear User,\nYour OTP for college app verification is-";
    public static final String OTP_NUMBER = "CLDAPP";
    public static final String LOGIN_TYPE_ANONYMOUS = "anonymous";
    public static final String LOGIN_TYPE_TRUECALLER = "truecaller";
    public static final String LOGIN_TYPE_PHONE_NUMBER = "phone_no";
    public static final String EXPANDED_INDICATOR = "\u2013";
    public static final String COLAPSED_INDICATOR = "+";
    public static final int REMOMMENDED_INSTITUTE_ACTION = 1;
    public static final int INSTITUTE_LIKE_DISLIKE = 0;
    public static final String CD_RECOMMENDED_INSTITUTE_ACTION_TYPE = "cd_recommended_institute_action_type";
    // Google Analytics tacker ID
    public static final String TRACKER_ID = "UA-67752258-1";
    // Apps Flyer ID
    public static final String APPSFLYER_ID = "v3bLHGLaEavK2ePfvpj6aA";
    // Connecto ID for GCM
    public static final String SENDER_ID = "864760274938";
    // GCM key for Apps Flyer
    public static final String GCM_KEY_APPS_FLYER = "AIzaSyAGQbQY_dTXvNNoGs1X_JymCXNrpXdQhkk";
    public static final Uri BASE_APP_URI = Uri.parse("android-app://com.collegedekho.app/https/www.collegedekho.com/");
    public static final String NOTIFICATION_FILTER = "com.notification.filter";
    public static final String CONTENT_LINK_FILTER = "com.college.dekho.link.clicked";
    public static final String ACTION_OPEN_WEB_URL = "open_web_url";
    public static final String GCM_QUESTIONS_LIST_PREFERENCES_KEY = "gcm_questions_list_preferences";
    public static final String GCM_QUESTIONS_LIST_COMPLETED_KEY = "gcm_questions_list_completed";
    public static final String QUESTIONS_LIST_KEY = "questions_list";
    public static final String SELECTED_PREFERENCE_FILTERS = "selected_preference_filters";
    public static final String GCM_DIALOG_RESULT = "gcm_dialog_result";
    public static final String DIALOG_DATA = "gcm_dialog_data";
    public static final String TAG_MY_FB_COMMENT_SUBMITTED = "action_my_fb_comment_submitted";
    public static final String FRAGMENT_TYPE = "fragment_type";
    public static final String DEFERRED_ARGUMENTS = "deferred_arguments";
    public static final String FRAGMENT_INDEX = "fragment_index";
    public static final String FRAGMENT_ACTION = "fragment_action";
    public static final String LOCAL_DELIMITER = "#=#";
    public static final int TYPE_STANDARD = 0;
    public static final int TYPE_LONGHOLD = 1;
    public static final int TYPE_FLING = 2;
    public static final int TYPE_HOLD_AND_RELEASE = 3;
    public static final int TYPE_EXAMPLE = 4;
    //public static String YOUTUBE_DEVELOPER_KEY = "AIzaSyBIu-yzOCq2pg237na2kAGUqzlrb9su-6c";
    public static final int CODE_FAILED = -1;
    public static final int CODE_SUCCESS = 0;
    public static final int USER_COMMENT = 1;
    public static final int ADMIN_COMMENT = 0;
    public static final int STUDY_IN_ABROAD = 1;
    public static final int SOURCE_COLLEGE_DEKHO_APP = 1;
    public static final int SOURCE_COLLEGE_APP_SDK = 2;
    public static final int SOURCE_EXAM_APP_SDK = 3;
    public static final int THRESHOLD_CHARACTER_LIMIT_FOR_NOTIFICATION_TITLE = 30;
    public static final float CRUSHED_NOTIFICATION_TITLE_SIZE = 14.0f;
    public static final String CHAT_NOTIFICATION_SETTINGS = "chat_notification_settings";
    public static final String NEWS_NOTIFICATION_SETTINGS = "news_notification_settings";
    public static final String ARTICLE_NOTIFICATION_SETTINGS = "article_notification_settings";
    public static final String OTHER_NOTIFICATION_SETTINGS = "other_notification_settings";
    public static boolean SEND_REQUEST = true;
    public static volatile boolean IS_CONNECTED_TO_INTERNET = true;
    public static boolean IS_CAF_LOADED = false;
    public static String INSTITUTE_ID = "institute_id_set";
    // for close app
    public static boolean READY_TO_CLOSE = false;
    public static boolean DISABLE_FRAGMENT_ANIMATION = false;
    // for app tutorials
    public static boolean IS_RECOMENDED_COLLEGE = false;
    public static long ANIM_SHORTEST_DURATION = 100;
    public static long ANIM_SHORT_DURATION = 200;
    public static long ANIM_AVERAGE_DURATION = 500;
    public static long ANIM_LONG_DURATION = 1000;
    public static long ANIM_LONGEST_DURATION = 2000;
    public static long HOLD_ENTER_VIBRATION_DURATION = 30;
    public static long HOLD_REMOVE_VIBRATION_DURATION = 100;
    public static boolean IS_LOCATION_SERVICES_ENABLED;
    public static String MESSAGE = "message";
    public static String COLLAPSE_KEY = "collapse_key";
    public static String URL = "url";
    // You tube developer key
    public static String YOUTUBE_DEVELOPER_KEY = "AIzaSyAGQbQY_dTXvNNoGs1X_JymCXNrpXdQhkk";
    public static int PHONE_VERIFIED = 1;

    public enum CDRecommendedInstituteType {
        RECOMMENDED, FEATURED, UNDECIDED, SHORTLIST, NOT_INTERESTED
    }

    public enum CDInstituteType {
        PARTNER, NON_PARTNER
    }
}
