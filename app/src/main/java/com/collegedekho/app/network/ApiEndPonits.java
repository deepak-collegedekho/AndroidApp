package com.collegedekho.app.network;

/**
 * Created by sureshsaini on 8/3/17.
 */

public class ApiEndPonits {

   //public static final String IP = "http://10.0.3.2";
   //public static final String IP = "http://www.launch.collegedekho.com";
   //public static final String IP = "http://www.launch2.collegedekho.com";
   public static final String IP = "https://www.collegedekho.com";
   //public static final String IP = "http://192.168.8.101";
   //public static final String IP = "http://192.168.56.1";
   //public static final String BASE_URL = IP + ":8000/api/1/";

   public static final String BASE_URL = IP + "/api/1/";

   public static final String  API_PROFILE = BASE_URL+"profile/";
   public static final String  API_REGISTER_DEVICE = BASE_URL+"register-device/";
   public static final String  API_NEW_COMMON_LOGIN = BASE_URL+"auth/new-common-login/";
   public static final String  API_L2_CHATS = BASE_URL+"l2-chats/";
   public static final String  API_UPLOAD_IMAGE = BASE_URL+"upload-image/";
   public static final String  API_FEEDS = BASE_URL + "feeds/";
   public static final String  API_LMS = BASE_URL + "lms/";
   public static final String  API_SEND_OTP = BASE_URL+"send-otp/";
   public static final String  API_STREAMS = BASE_URL+"streams/";
   public static final String  API_SUB_LEVELS = BASE_URL+"sublevels/?level=";
   public static final String  API_STREAM_YEARLY_EXAMS = BASE_URL+"stream-yearly-exams/";
   public static final String  API_PERSONALIZE_FORUMS = BASE_URL + "personalize/forums/";
   public static final String  API_PERSONALIZE_INSTITUTES = BASE_URL + "personalize/institutes/";
   public static final String  API_PERSONALIZE_CURRENCIES = BASE_URL + "personalize/institutes?=currencies";
   public static final String  API_RECOMMENDED_INSTITUTES = BASE_URL + "personalize/recommended-institutes/";
   public static final String  API_SHORTLISTED_INSTITUTES = BASE_URL + "personalize/shortlistedinstitutes/";
   public static final String  API_UNDECIDED_INSTITUTES = BASE_URL + "personalize/shortlistedinstitutes/?action=3";
   public static final String  API_BUZZLIST_INSTITUTES = BASE_URL + "personalize/recommended-institutes/?action=2";
   public static final String  API_PERSONALIZE_QNA = BASE_URL+"personalize/qna-v2/";
   public static final String  API_PERSONALIZE_NEWS = BASE_URL+"personalize/news/";
   public static final String  API_PERSONALIZE_ARTICLES = BASE_URL+"personalize/articles/";
   public static final String  API_QNA_SEARCH = BASE_URL+"questions-v2/search=";
   public static final String  API_NEWS_SEARCH = BASE_URL+"news/search=";
   public static final String  API_ARTICLES_SEARCH = BASE_URL+"articles/search=";
   public static final String  API_COLLEGE_SEARCH = BASE_URL+"colleges/search=";
   public static final String  API_INSTITUTE_BY_SLUG = BASE_URL+"institute-by-slug/";
   public static final String  API_QUESTION_BY_SLUG = BASE_URL+"question-by-slug-v2/";
   public static final String  API_NEWS_BY_SLUG = BASE_URL+"news-by-slug/";
   public static final String  API_ARTICLE_BY_SLUG = BASE_URL+"article-by-slug/";
   public static final String  API_COUNTRIES = BASE_URL+"countries/";

}
