package com.collegedekho.app.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.DebugLogQueue;
import com.collegedekho.app.R;
import com.collegedekho.app.common.AppUser;
import com.collegedekho.app.database.DataBaseHelper;
import com.collegedekho.app.display.crop.Crop;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.Country;
import com.collegedekho.app.entities.Courses;
import com.collegedekho.app.entities.Currency;
import com.collegedekho.app.entities.DeviceProfile;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.entities.Feed;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.Item;
import com.collegedekho.app.entities.MyAlertDate;
import com.collegedekho.app.entities.MyFutureBuddiesEnumeration;
import com.collegedekho.app.entities.MyFutureBuddy;
import com.collegedekho.app.entities.MyFutureBuddyComment;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.entities.PsychometricTestQuestion;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.entities.StepByStepQuestion;
import com.collegedekho.app.entities.StepByStepResult;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.entities.SubLevel;
import com.collegedekho.app.entities.Subjects;
import com.collegedekho.app.entities.VideoEntry;
import com.collegedekho.app.entities.YoutubeVideoDetails;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.fragment.AboutFragment;
import com.collegedekho.app.fragment.ArticleDetailFragment;
import com.collegedekho.app.fragment.ArticleFragment;
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.fragment.CDRecommendedInstituteFragment;
import com.collegedekho.app.fragment.CalendarParentFragment;
import com.collegedekho.app.fragment.CollegesDashboard;
import com.collegedekho.app.fragment.ExamsFragment;
import com.collegedekho.app.fragment.FeedFragment;
import com.collegedekho.app.fragment.FilterFragment;
import com.collegedekho.app.fragment.HomeFragment;
import com.collegedekho.app.fragment.InstituteDetailFragment;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
import com.collegedekho.app.fragment.InstituteQnAFragment;
import com.collegedekho.app.fragment.InstituteVideosFragment;
import com.collegedekho.app.fragment.MyFutureBuddiesEnumerationFragment;
import com.collegedekho.app.fragment.MyFutureBuddiesFragment;
import com.collegedekho.app.fragment.NewsDetailFragment;
import com.collegedekho.app.fragment.NewsFragment;
import com.collegedekho.app.fragment.NotPreparingFragment;
import com.collegedekho.app.fragment.NotificationSettingsFragment;
import com.collegedekho.app.fragment.ProfileFragment;
import com.collegedekho.app.fragment.PsychometricStreamFragment;
import com.collegedekho.app.fragment.PsychometricTestParentFragment;
import com.collegedekho.app.fragment.QnAQuestionsListFragment;
import com.collegedekho.app.fragment.QnaQuestionDetailFragmentNew;
import com.collegedekho.app.fragment.SplashFragment;
import com.collegedekho.app.fragment.SplashLoginFragment;
import com.collegedekho.app.fragment.StreamFragment;
import com.collegedekho.app.fragment.SyllabusSubjectsListFragment;
import com.collegedekho.app.fragment.SyllabusUnitListFragment;
import com.collegedekho.app.fragment.UserAlertsFragment;
import com.collegedekho.app.fragment.UserAlertsParentFragment;
import com.collegedekho.app.fragment.WebViewFragment;
import com.collegedekho.app.fragment.WishlistFragment;
import com.collegedekho.app.fragment.login.LoginForCounselorFragment;
import com.collegedekho.app.fragment.login.LoginFragment;
import com.collegedekho.app.fragment.login.OTPVerificationFragment;
import com.collegedekho.app.fragment.login.PostAnonymousLoginFragment;
import com.collegedekho.app.fragment.profileBuilding.CountrySelectionFragment;
import com.collegedekho.app.fragment.profileBuilding.CourseSelectionFragment;
import com.collegedekho.app.fragment.profileBuilding.LevelSelectionFragment;
import com.collegedekho.app.fragment.profileBuilding.PrefStreamSelectionFragment;
import com.collegedekho.app.fragment.profileBuilding.SpecificCourseFragment;
import com.collegedekho.app.fragment.stepByStepTest.StepByStepFragment;
import com.collegedekho.app.listener.DashBoardItemListener;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.listener.OnApplyClickedListener;
import com.collegedekho.app.listener.OnArticleSelectListener;
import com.collegedekho.app.listener.OnNewsSelectListener;
import com.collegedekho.app.listener.ProfileFragmentListener;
import com.collegedekho.app.network.ApiEndPonits;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.receiver.NetworkChangeReceiver;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;
import com.collegedekho.app.widget.GifView;
import com.collegedekho.app.widget.fab.FloatingActionMenu;
import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.jr.ob.JSON;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tagmanager.Container;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.collegedekho.app.network.NetworkUtils.getConnectivityStatus;
import static com.collegedekho.app.resource.Constants.PROFILE_IMAGE_UPLOADING;
import static com.collegedekho.app.resource.Constants.TAG_LOAD_FILTERS;
import static com.collegedekho.app.resource.Constants.TAG_REFRESH_PROFILE;
import static com.collegedekho.app.utils.AnalyticsUtils.SendAppEvent;

/*
The MIT License (MIT)

Copyright (c) 2014 Marco Granatiero

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.*/

//TODO::Put DataObserver on Filter count. If it changes, delete all exam summary to get new

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,  NavigationView.OnNavigationItemSelectedListener,
        SplashFragment.OnSplashListener,  GoogleApiClient.ConnectionCallbacks,QnAQuestionsListFragment.OnQnAQuestionSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,  DataLoadListener, StreamFragment.OnStreamInteractionListener,
        PsychometricStreamFragment.OnStreamInteractionListener, AdapterView.OnItemSelectedListener, ExamsFragment.OnExamsSelectListener,
        InstituteListFragment.OnInstituteSelectedListener, OnApplyClickedListener, OnNewsSelectListener,
        ProfileFragment.UserProfileListener, OnArticleSelectListener,  InstituteQnAFragment.OnQuestionAskedListener, FilterFragment.OnFilterInteractionListener,
        MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener, MyFutureBuddiesFragment.OnMyFBInteractionListener,
        InstituteDetailFragment.OnInstituteDetailListener,ITrueCallback,  PsychometricTestParentFragment.OnPsychometricTestSubmitListener,
        SyllabusSubjectsListFragment.OnSubjectSelectedListener, CalendarParentFragment.OnSubmitCalendarData,
        NotPreparingFragment.OnNotPreparingOptionsListener, StepByStepFragment.OnStepByStepFragmentListener,
        UserAlertsFragment.OnAlertItemSelectListener, GifView.OnGifCompletedListener, CDRecommendedInstituteFragment.OnCDRecommendedInstituteListener,  InstituteVideosFragment.OnTitleUpdateListener,
        WishlistFragment.WishlistInstituteInteractionListener, FeedFragment.onFeedInteractionListener,DashBoardItemListener {

    static {

        // to support vector drawable in pre-lollipop
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Constants.FilterCategoryMap.put(Constants.ID_FACILITIES, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);
        Constants.FilterCategoryMap.put(Constants.ID_HOSTEL, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);

        Constants.FilterCategoryMap.put(Constants.ID_INSTITUTE_TYPE, Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES);

        Constants.FilterCategoryMap.put(Constants.ID_CITY, Constants.FILTER_CATEGORY_LOCATION);
        Constants.FilterCategoryMap.put(Constants.ID_STATE, Constants.FILTER_CATEGORY_LOCATION);

        Constants.FilterCategoryMap.put(Constants.ID_SPECIALIZATION, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
        Constants.FilterCategoryMap.put(Constants.ID_DEGREE, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
        Constants.FilterCategoryMap.put(Constants.ID_EXAM, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    public static boolean IN_FOREGROUND = false;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    public static NetworkUtils mNetworkUtils;
    public static volatile BaseFragment currentFragment;
    public static volatile BaseFragment currentSubFragment;
    private List<Institute> mInstituteList = new ArrayList<>();
    private List<Chapters> chaptersList;
    private List<PsychometricTestQuestion> psychometricQuestionsList;
    private int currentInstitute;
    private ProgressDialog progressDialog;
    private ProgressDialog mTransparentProgressDialog;
    private AlertDialog mErrorDialog;
    private String mCurrentTitle;
    private String next;
    private List<MyFutureBuddiesEnumeration> mFbEnumeration;
    private MyFutureBuddy mFB;
    private ArrayList<QnAQuestions> mQnAQuestions = new ArrayList<>();
    private int mFilterCount = 0;
    private String mFilters = "";
    private String mCurrencies = "";
    private Map<String, String> mFilterKeywords = new HashMap<>();
    private ArrayList<Folder> mFolderList;
    private ArrayList<Currency> mCurrenciesList;
    private List<News> mNewsList;
    private List<Articles> mArticlesList;
    private Institute mInstitute;
    private String mLastScreenName = "";
    private Date mTimeScreenClicked = new Date();
    public DeviceProfile mDeviceProfile;
    public static Profile mProfile;
    private static String type = "";
    private static String resource_uri = "";
    private static String resource_uri_with_notification_id = "";
    private Menu menu;
    private String mYear;
    private List<MyAlertDate> myAlertsList;
    private boolean isFromNotification;
    private boolean isFromDeepLinking;
    private String mDeepLinkingURI;
    private String mOtherAppSharedMessage;
    private String mExamTag;
    private int mUndecidedInstitutesCount;
    private int mShortListInstituteCount;
    private int mFeaturedInstituteCount;
    private int mRecommendedInstituteCount;
    private Snackbar mSnackbar;
    private boolean IS_USER_CREATED;
    private boolean USER_CREATING_PROCESS;
    private boolean IS_HOME_LOADED;
    public static TrueClient mTrueClient;
    private Resources mResources;
    private TextToSpeech mTextToSpeech;
    public GoogleApiClient mGoogleApiClient;
    public static final int REQUEST_CHECK_LOCATION_SETTINGS = 999;

    private SearchView mSearchView = null;
    private ProgressBar mSearchProgress;
    // navigation drawer and toolbar variables
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    private FloatingActionMenu mFabMenu;
    private int  mFabMenuMargin;
    private HashMap<String, String> defferedFunction = new HashMap<>();
    private Map<String, QnAQuestions> mQuestionMapForAnswer;
    private NetworkChangeReceiver mNetworkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
        } catch (RuntimeException e) {
            e.printStackTrace();
            finish();
            reStartApplication();
        }

        Intent intent = this.getIntent();
        String action = intent.getAction();
        String data = intent.getDataString();
        String intentType = intent.getType();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            if (data.contains("collegedekho.com")) {
                this.mDeepLinkingURI = data;
                this.isFromNotification = true;
                this.isFromDeepLinking = true;
                Log.e(TAG, " DeepLinking URL is  : " + mDeepLinkingURI);
            }
        } else if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(intentType)) {
                this.isFromNotification = true;
                this.mOtherAppSharedMessage = intent.getStringExtra(Intent.EXTRA_TEXT);
            } else {
                Toast.makeText(this, "Sorry!! Only text content can be shared", Toast.LENGTH_SHORT).show();
            }
        }
        //TODO: changed else if to if, watch out for consequences
        if (intent.getExtras() != null) {
            Log.e(TAG, "Extras are not null");

            Bundle extras = intent.getExtras();
            if (extras.containsKey("screen") && extras.containsKey("resource_uri") && extras.containsKey("notification_id")) {
                this.isFromNotification = true;
                MainActivity.type = extras.getString("screen");
                MainActivity.resource_uri = extras.getString("resource_uri");
                MainActivity.resource_uri_with_notification_id = MainActivity.resource_uri + "?notification_id=" + extras.getString("notification_id");

                Log.e(TAG, "There is data type is : " + MainActivity.type + " Resource uri is " + MainActivity.resource_uri);

                //events params
                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(getString(R.string.TAG_RESOURCE_URI), MainActivity.resource_uri);
                eventValue.put(getString(R.string.TAG_NOTIFICATION_TYPE), MainActivity.type);
                eventValue.put(getString(R.string.TAG_APP_STATUS), getString(R.string.TAG_APP_STATUS_LAUNCHED_FROM_NOTIFICATION));
                eventValue.put(getString(R.string.TAG_NOTIFICATION_ID), extras.getString("notification_id"));

                //Events
                SendAppEvent(getString(R.string.CATEGORY_NOTIFICATIONS), getString(R.string.ACTION_NOTIFICATION_OPEN), eventValue, this);
            }
        }
        this.setContentView(R.layout.activity_main);

        Log.e(TAG, " onCreate()  step 1 time_info  " + System.currentTimeMillis());
        // set up app tool bar
        this.mSetAppToolBar();

        // register with Apps Flayer
        this.mRegistrationAppsFlyer();

        // register with GA tracker
        this.mRegistrationGATracker();

        // init App
        this.init();

        // register with true SDk
        this.mRegistrationTrueSdk();

        //this.mSetupGTM();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(AppIndex.API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        this.mLoadUserStatusScreen();

        if (NetworkUtils.getConnectivityStatus(getApplicationContext()) != Constants.TYPE_NOT_CONNECTED && IS_HOME_LOADED) {
            Utils.appLaunched(this);
        }

        mSearchProgress = (ProgressBar) findViewById(R.id.resource_progress_bar);

        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    mTextToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        Log.e(TAG, " Charset name " + Charset.defaultCharset().displayName());
    }

    /**
     * This method is used to initialize the app
     *  like create network instance, load user profile
     */
    public void init() {

        // set resource context
        MainActivity.this.mResources = getResources();
        // create network utils
        MainActivity.mNetworkUtils = new NetworkUtils(getApplicationContext(),this);

        this.mFabMenu =(FloatingActionMenu) findViewById(R.id.counselor_fab_menu);
        this.mFabMenu.setClosedOnTouchOutside(true);
        //  FAB margin needed for animation
        this.mFabMenuMargin = getResources().getDimensionPixelSize(R.dimen.fab_margin);

        View counselorCall = findViewById(R.id.counselor_call_button);
        View counselorChat = findViewById(R.id.counselor_chat_button);



        counselorCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mProfile != null) {
                    String contactNumber = mProfile.getCounselor_contact_no();
                    Uri number = Uri.parse("tel:" + contactNumber);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
                    //Events
                    Map<String, Object> eventValue = new HashMap<>();
                    eventValue.put(getString(R.string.ACTION_USER_PREFERENCE),getString(R.string.ACTION_COUNSELOR_CALL_SELECTED));
                    SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_COUNSELOR_CALL_SELECTED), eventValue, MainActivity.this);

                }
            }
        });
        counselorChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMakeNetworkCall(Constants.TAG_LOAD_COUNSELOR_CHAT , ApiEndPonits.API_L2_CHATS,null);
                //Events
                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(getString(R.string.ACTION_USER_PREFERENCE),getString(R.string.ACTION_COUNSELOR_CHAT_SELECTED));
                SendAppEvent(getString(R.string.CATEGORY_MY_FB), getString(R.string.ACTION_COUNSELOR_CHAT_SELECTED), eventValue, MainActivity.this);
            }
        });
        // start loader for cursor with zero id
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED ) {
            getSupportLoaderManager().initLoader(0, null, MainActivity.this);
        }
        SharedPreferences sp = MainActivity.this.getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE);
        try {
            if (sp.contains(getString(R.string.KEY_USER))) {
                // load profile user
                mProfile = JSON.std.beanFrom(Profile.class, sp.getString(getString(R.string.KEY_USER), null));
                // set user's token id with network instance
                // we need this token id in header for API calls.
                mNetworkUtils.setToken(MainActivity.mProfile.getToken());

                if(mProfile != null && mProfile.getStudy_abroad()==1 && mProfile.getPreferred_countries().get(0).id == 1) {
//                    Toast.makeText(this,"Case 1"+mProfile.getStudy_abroad()+mProfile.getPreferred_countries().get(0).id,Toast.LENGTH_SHORT).show();
                    Constants.FilterCategoryMap.remove(Constants.ID_FEE_ABROAD);
                    Constants.FilterCategoryMap.remove(Constants.ID_FEE_RANGE);
                    Constants.FilterCategoryMap.put(Constants.ID_COUNTRY, Constants.FILTER_CATEGORY_LOCATION);
                }
                else if(mProfile.getStudy_abroad()==1 && mProfile.getPreferred_countries().get(0).id!=1){
//                    Toast.makeText(this,"Case 2"+mProfile.getStudy_abroad()+mProfile.getPreferred_countries().get(0).id,Toast.LENGTH_SHORT).show();
                    Constants.FilterCategoryMap.put(Constants.ID_FEE_ABROAD, Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES);
                    Constants.FilterCategoryMap.put(Constants.ID_COUNTRY, Constants.FILTER_CATEGORY_LOCATION);
                    Constants.FilterCategoryMap.remove(Constants.ID_FEE_RANGE);
                }
                else if(mProfile.getStudy_abroad()==0 && mProfile.getPreferred_countries().get(0).id==1){
//                    Toast.makeText(this,"Case 3"+mProfile.getStudy_abroad()+mProfile.getPreferred_countries().get(0).id,Toast.LENGTH_SHORT).show();
                    Constants.FilterCategoryMap.remove(Constants.ID_FEE_ABROAD);
                    Constants.FilterCategoryMap.put(Constants.ID_FEE_RANGE, Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES);
                    Constants.FilterCategoryMap.remove(Constants.ID_COUNTRY);
                }
                else
                {
//                    Toast.makeText(this,"Case 4 "+mProfile.getStudy_abroad()+mProfile.getPreferred_countries().get(0).id,Toast.LENGTH_LONG).show();
                    Constants.FilterCategoryMap.remove(Constants.ID_COUNTRY);
                    Constants.FilterCategoryMap.remove(Constants.ID_FEE_ABROAD);
                    Constants.FilterCategoryMap.remove(Constants.ID_FEE_RANGE);
                }
                // sync user detail info with server
                // and also send latest app version
                HashMap<String, String> params = new HashMap<>();
                params.put(getString(R.string.user_app_version), Utils.GetAppVersion());
                requestForProfile(params);

                // user id registration
                setUserIdWithAllEvents();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MainActivity.this.IS_USER_CREATED = sp.getBoolean(getString(R.string.USER_CREATED), false);
            MainActivity.this.IS_HOME_LOADED = sp.getBoolean(getString(R.string.USER_HOME_LOADED), false);

        }
    }

    /**
     * This method is used to register this app with true caller sdk
     * After this user can login by true caller.
     */
    private void mRegistrationTrueSdk() {
        MainActivity.mTrueClient = new TrueClient(getApplicationContext(), MainActivity.this);

    }

    /**
     * This method is used to register app with GA tracker by which
     * we can track app activities
     */
    private void mRegistrationGATracker() {
        analytics = GoogleAnalytics.getInstance(getApplicationContext());
        analytics.setLocalDispatchPeriod(1800);

        MainActivity.tracker = analytics.newTracker(Constants.TRACKER_ID);

        // Provide unhandled exceptions reports. Do that first after creating the tracker
        MainActivity.tracker.enableExceptionReporting(true);
        // Enable Remarketing, Demographics & Interests reports
        // https://developers.google.com/analytics/devguides/collection/android/display-features
        MainActivity.tracker.enableAdvertisingIdCollection(true);
        // Enable automatic activity tracking for your app
        MainActivity.tracker.enableAutoActivityTracking(true);
        //Send GA Session
        MainActivity.GASessionEvent(MainActivity.TAG);

    }

    /**
     * This method is used to register Apps flyer tracker
     */
    private void mRegistrationAppsFlyer() {
        // The Dev key cab be set here or in the manifest.xml
        AppsFlyerLib.getInstance().startTracking(getApplication(), Constants.APPSFLYER_ID);

        //AppsFlyer: collecting your GCM project ID by setGCMProjectID allows you to track uninstall data in your dashboard

        AppsFlyerLib.getInstance().setGCMProjectNumber(Constants.GCM_KEY_APPS_FLYER);
        // Set the Currency
        AppsFlyerLib.getInstance().setCurrencyCode("INR");
        AppsFlyerLib.getInstance().setDebugLog(true);

        AppsFlyerLib.getInstance().registerConversionListener(MainActivity.this, new AppsFlyerConversionListener() {
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
                DebugLogQueue.getInstance().push("\nGot conversion data from server");
                for (String attrName : conversionData.keySet()) {
                    Log.d(TAG, "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }
            public void onInstallConversionFailure(String errorMessage) {
                Log.d(TAG, "error getting conversion data: " + errorMessage);
            }

            public void onAppOpenAttribution(Map<String, String> attributionData) {
                printMap(attributionData);
            }

            public void onAttributionFailure(String errorMessage) {
                Log.d(TAG, "error onAttributionFailure : " + errorMessage);
            }

            private void printMap(Map<String, String> map) {
                for (String key : map.keySet()) {
                    Log.d(TAG, key + "=" + map.get(key));
                }
            }
        });
    }

    /**
     * This method will be called  just after splash GIF animation is completed on splash screen
     */
    @Override
    public void onGifCompleted() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (IS_USER_CREATED && !IS_HOME_LOADED && currentFragment instanceof SplashFragment
                        && NetworkUtils.getConnectivityStatus(getApplicationContext()) == Constants.TYPE_NOT_CONNECTED) {
                    ((SplashFragment) currentFragment).noInternetAvailable();
                } else if (!USER_CREATING_PROCESS && !IS_USER_CREATED && NetworkUtils.getConnectivityStatus(getApplicationContext()) != Constants.TYPE_NOT_CONNECTED) {
                    USER_CREATING_PROCESS = true;
                    showProgress(Constants.TAG_CREATING_USER);
                } else {
                    mLoadUserStatusScreen();
                }
            }
        }, 10);
    }

    /**
     * This method is used to load fragments according to user status and notifications
     *  1. If user is not created then open splash login fragment
     *  2. If User level is set  load profile building fragment
     *  3. If education is selected but exam is not selected then load exams screen
     *  4. If education and exam are selected then load profile screen
     *  5. if both are not selected then load education screen
     */
    private void mLoadUserStatusScreen() {

//        Log.e("Countries"," - "+mProfile.getPreferred_countries().get(0).name);
        if (!IS_USER_CREATED) {
            // if user is not created the first user will login with any option
            this.mDisplaySplashLoginFragment();

        } else if (MainActivity.type != null && !MainActivity.type.matches("")) {
            this.mHandleNotifications();

        } else if (this.mOtherAppSharedMessage != null && !this.mOtherAppSharedMessage.isEmpty()) {
            this.mHandleOtherAppSharedMessage();

        } else if (this.mDeepLinkingURI != null && !this.mDeepLinkingURI.isEmpty()) {
            Log.e("MA: DL URL ", MainActivity.this.mDeepLinkingURI);
            this.isFromDeepLinking = true;
            this.mHandleDeepLinking();

        } else if (IS_HOME_LOADED) {
            // show App bar layout
            mShowAppBarLayout();
            // load user home screen
            mDisplayHomeFragment();
            // request to update profile info if anything is change on server
            requestForProfile(null);
//            int[] a = {1,2};
//            MainActivity.mProfile.setPreferred_countries(a);
        }else if(MainActivity.mProfile.getCurrent_level_id() < 1) {
            this.mDisplayLevelSelectionFragment();
        }else if(MainActivity.mProfile.getPreferred_countries()==null || MainActivity.mProfile.getPreferred_countries().size()<1) {
            this.mDisplayCountrySelectionFragment(false);
        }else if(MainActivity.mProfile.getPreferred_stream_id() < 1) {
            this.mDisplaySpecificCourseFragment(null);
        }else{
            this.mDisplayHomeFragment();
        }
    }

    private void mSetAppToolBar() {
        // replace default action bar with Tool bar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // set snackbar to show msg in snackbar display
        this.mSnackbar = Snackbar.make(this.findViewById(R.id.main_activity),
                getString(R.string.internet_not_available), Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) mSnackbar.getView();
        layout.setBackgroundColor(getResources().getColor(R.color.primary_color));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(MainActivity.this);

        // register drawer layout  and toolbar with DrawerToggle;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // set user profile image on navigation drawer layout
                CircularImageView mProfileImage = (CircularImageView) drawerView.findViewById(R.id.profile_image);
                mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
                mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);
                String profileImage = MainActivity.mProfile.getImage();

                if (profileImage == null) profileImage = "";
                mProfileImage.setImageUrl(profileImage, MySingleton.getInstance(getApplicationContext()).getImageLoader());

                // set user's name on navigation drawer
                String userName = MainActivity.mProfile.getName();
                if (userName != null) {
                    ((TextView) drawerView.findViewById(R.id.user_name)).setText(userName);
                }
                // it will show user's profile completion progress on navigation drawer
                CircularProgressBar profileCompleted = (CircularProgressBar) drawerView.findViewById(R.id.user_profile_progress);
                profileCompleted.setProgress(0);
                profileCompleted.setProgressWithAnimation(MainActivity.mProfile.getProgress(), 2000);

            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mToolbar.setLogo(R.drawable.ic_cd_colored);
        getToolbarLogoIcon(mToolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFragment instanceof HomeFragment){
                    if(!mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                        mDrawerLayout.openDrawer(GravityCompat.START);
                    }
                }
            }
        });
    }

    public static View getToolbarLogoIcon(Toolbar toolbar){
        //check if contentDescription previously was set
        boolean hadContentDescription = TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<>();
        //find the view based on it's content description, set programmatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setLogoDescription ensures its existence
        View logoIcon = null;
        if(potentialViews.size() > 0){
            logoIcon = potentialViews.get(0);
        }
        //Clear content description if not previously present
        if(hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int position = -1;
        if (id == R.id.nav_Feed) {
            position = 0;
        } else if (id == R.id.nav_colleges) {
            position = 1;
        } else if (id == R.id.nav_connect) {
            position = 2;
        } else if (id == R.id.nav_prepare) {
            position = 3;
        } else if (id == R.id.nav_share) {
            // request for share intent to download app  from playStore
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Explore top institutions, colleges and universities. Click here to download your personalised guide" +
                            ": https://play.google.com/store/apps/details?id=com.collegedekho.app&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }else if(id == R.id.about_app){
            mDisplayAboutFragment();
        } else if (id == R.id.nav_settings)
        {
            mDisplayNotificationSettingsFragment();
        }
        if (position != -1) {
            mClearBackStackWithoutAnimation();
            if (currentFragment instanceof HomeFragment) {
                ((HomeFragment) currentFragment).setSelectedPage(position);
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Used to Load about Fragment which has
     * information about and T&C and privacy policy
     */
    private void mDisplayAboutFragment() {
        String tag = AboutFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        boolean addToBackStack = false;
        if(fragment ==  null){
            fragment = AboutFragment.newInstance();
            addToBackStack = true;
        }
        mDisplayFragment(fragment, addToBackStack, tag);
    }

    /**
     * Used to Load notification settings Fragment which has
     * user preference for notifications
     */
    private void mDisplayNotificationSettingsFragment() {
        String tag = NotificationSettingsFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        boolean addToBackStack = false;
        if(fragment ==  null){
            fragment = NotificationSettingsFragment.newInstance();
            addToBackStack = true;
        }
        mDisplayFragment(fragment, addToBackStack, tag);
    }

    public void setNavigationDrawerItemSelected(int tabPosition){
        if(tabPosition >= 0){
            mNavigationView.getMenu().getItem(tabPosition).setChecked(true);
        } else {
            int size = mNavigationView.getMenu().size();
            for(int i = 0 ; i < size ; i++)
                mNavigationView.getMenu().getItem(i).setChecked(false);
        }
    }
    public void setToolBarScrollable(boolean isScroll){
        // Show toolbar when we are in maps mode
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.app_bar_layout);
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        if(!isScroll) {
            params.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
            appBarLayout.setLayoutParams(appBarLayoutParams);
        } else {
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            AppBarLayout.Behavior appBarLayoutBehaviour =  new AppBarLayout.Behavior();
            appBarLayoutBehaviour.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
            appBarLayoutParams.setBehavior(appBarLayoutBehaviour);
            appBarLayout.setLayoutParams(appBarLayoutParams);
        }
    }

    private void mHandleNotifications() {
        // this.isFromNotification = true;
        switch (MainActivity.type) {
            case Constants.TAG_FRAGMENT_INSTITUTE_LIST: {
                this.mCurrentTitle = "Institute List";
                // shim to open institute details page when
                // institute-by-slug/jaypee-business-school is called.
                // else it would try to open details response in list page.
                // listing page will not open in app via deep linking
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_INSTITUTES, MainActivity.resource_uri_with_notification_id, null);
                } else {
                    this.mMakeNetworkCall(Constants.WIDGET_INSTITUTES, MainActivity.resource_uri_with_notification_id, null);
                }
                break;
            }
            case Constants.TAG_FRAGMENT_INSTITUTE: {
                this.mCurrentTitle = "Institute";
                this.mMakeNetworkCall(Constants.PNS_INSTITUTES, MainActivity.resource_uri_with_notification_id, null);
                break;
            }
            case Constants.TAG_FRAGMENT_NEWS_LIST: {
                this.mCurrentTitle = "News";
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_NEWS, MainActivity.resource_uri_with_notification_id, null);
                } else {
                    this.mMakeNetworkCall(Constants.WIDGET_NEWS, MainActivity.resource_uri_with_notification_id, null);
                }
                break;
            }
            case Constants.TAG_FRAGMENT_NEWS_DETAIL: {
                this.mCurrentTitle = "News";
                this.mMakeNetworkCall(Constants.PNS_NEWS, MainActivity.resource_uri_with_notification_id, null);
                break;
            }
            case Constants.TAG_FRAGMENT_ARTICLES_LIST: {
                this.mCurrentTitle = "Articles";
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_ARTICLES, MainActivity.resource_uri_with_notification_id, null);
                } else {
                    this.mMakeNetworkCall(Constants.WIDGET_ARTICES, MainActivity.resource_uri_with_notification_id, null);
                }
                break;
            }
            case Constants.TAG_FRAGMENT_ARTICLE_DETAIL: {
                this.mCurrentTitle = "Articles";
                this.mMakeNetworkCall(Constants.PNS_ARTICLES, MainActivity.resource_uri_with_notification_id, null);
                break;
            }
            case Constants.TAG_FRAGMENT_QNA_QUESTION_LIST: {
                this.mCurrentTitle = "QnA";
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_QNA, MainActivity.resource_uri_with_notification_id, null);
                } else {
                    this.mMakeNetworkCall(Constants.TAG_LOAD_QNA_QUESTIONS, MainActivity.resource_uri_with_notification_id, null);
                }
                break;
            }
            case Constants.TAG_FRAGMENT_QNA_QUESTION_DETAIL: {
                this.mCurrentTitle = "QnA Details";
                this.mMakeNetworkCall(Constants.PNS_QNA, MainActivity.resource_uri_with_notification_id, null);
                break;
            }
            case Constants.TAG_FRAGMENT_MY_FB_ENUMERATION: {
                this.mCurrentTitle = "My Future Buddies";
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_FORUM, MainActivity.resource_uri_with_notification_id, null);
                } else {
                    this.mMakeNetworkCall(Constants.WIDGET_FORUMS, MainActivity.resource_uri_with_notification_id, null);
                }
                break;

            }
            case Constants.TAG_FRAGMENT_MY_FB: {
                this.mCurrentTitle = "MyFB";
                this.mMakeNetworkCall(Constants.PNS_FORUM, MainActivity.resource_uri_with_notification_id, null);
                break;
            }
            case Constants.TAG_FRAGMENT_SHORTLISTED_INSTITUTE: {
                this.mCurrentTitle = "My Shortlist";
                this.mMakeNetworkCall(Constants.WIDGET_SHORTLIST_INSTITUTES, MainActivity.resource_uri_with_notification_id, null);
                break;
            }
            case Constants.WIDGET_TEST_CALENDAR:
                this.mMakeNetworkCall(Constants.WIDGET_TEST_CALENDAR, MainActivity.resource_uri_with_notification_id, null);
                break;

            case Constants.TAG_MY_ALERTS:
                MainActivity.this.mMakeNetworkCall(Constants.TAG_MY_ALERTS, MainActivity.resource_uri_with_notification_id, null);
                break;

            case Constants.WIDGET_SYLLABUS:
                this.mMakeNetworkCall(Constants.WIDGET_SYLLABUS, MainActivity.resource_uri_with_notification_id, null);
                break;

            case Constants.PLAY_VIDEO_NOTIFICATION:
                if (MainActivity.resource_uri != null && !MainActivity.resource_uri.trim().matches("")) {
                    Intent intent = new Intent(this, VideoPlayerActivity.class);
                    intent.putExtra("video_id", MainActivity.resource_uri);
                    startActivityForResult(intent, Constants.RC_QUIT_VIDEO_PLAYER);
                }
                break;
            case Constants.ACTION_OPEN_WEB_URL:
                if (MainActivity.mProfile != null)
                    MainActivity.resource_uri_with_notification_id += "&&user_id=" + MainActivity.mProfile.getId();
                onDisplayWebFragment(MainActivity.resource_uri_with_notification_id);
                break;
            case Constants.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST: {
                this.mCurrentTitle = "Recommended Colleges";
                this.mMakeNetworkCall(Constants.WIDGET_RECOMMENDED_INSTITUTES, MainActivity.resource_uri_with_notification_id, null);
                break;
            }
            case Constants.TAG_PROFILE_FRAGMENT:
            case Constants.PROFILE_COMPLETION_OTP:
            {
                this.mCurrentTitle = "Profile";
                this.requestForProfileFragment();
                break;
            }
            case Constants.TAG_LOAD_STEP_BY_STEP:
            {
                this.startStepByStep();
                break;
            }
            case Constants.TAG_PSYCHOMETRIC_QUESTIONS:
            {
                this.onPsychometricTestSelected();
                break;
            }
            case Constants.TAG_FRAGMENT_COUNSELOR_CHAT:
            {
                this.mCurrentTitle = "Counselor Chat";
                this.mMakeNetworkCall(Constants.PNS_COUNSELOR_CHAT, MainActivity.resource_uri, null);
                break;
            }

            default:
                this.isFromNotification = false;
                this.isFromDeepLinking = false;
                MainActivity.type = "";
                MainActivity.resource_uri = "";
                MainActivity.resource_uri_with_notification_id = "";
                this.mDeepLinkingURI = "";
                this.mLoadUserStatusScreen();
                break;
        }

        MainActivity.type = "";
        MainActivity.resource_uri = "";
        MainActivity.resource_uri_with_notification_id = "";
        this.mDeepLinkingURI = "";
        getIntent().putExtra("screen", "");
        getIntent().putExtra("resource_uri", "");
    }

    private void mHandleDeepLinking() {
        String uri = this.mDeepLinkingURI;
        if(uri == null) return;
        //this cae is for phone content search which throws a link of following type:
        //android-app://com.collegedekho.app/https/www.collegedekho.com/fragment_institute_list/institutes/3633/
        if (uri.contains("fragment_"))
        {
            String[] uriArray = uri.split("/");
            String resourceURI = "";
            if (uriArray.length > 3) {
                MainActivity.type = uriArray[3];
                for (int i = 4; i < uriArray.length; i++) {
                    if (!uriArray[i].isEmpty())
                        resourceURI += uriArray[i] + "/";
                }
                MainActivity.resource_uri = ApiEndPonits.BASE_URL + resourceURI;
            } else
                MainActivity.type = "";
        }
        else
        {
            //this case is for web content search which throws a link of following type:
            //https://www.collegedekho.com/colleges/jaypee-business-school
            //we need to hit the API resolve-url to get the API end point for particular resource

            //article - https://www.collegedekho.com/articles/how-to-prepare-for-jee-mains-jee-advanced-while-in-class-12th/
            //college - https://www.collegedekho.com/colleges/iit-delhi
            //news - https://www.collegedekho.com/news/nobel-laureates-deliver-lectures-at-iit-gandhinagar-9583/
            //qna - https://www.collegedekho.com/qna/want-to-know-abt-college/

            if (!uri.isEmpty())
            {
                this.mMakeNetworkCall(Constants.TAG_RESOLVE_DEEPLINK_URL, ApiEndPonits.API_RESOLVE_DEEPLINK_URL + "?url=" + uri, null);
                return;
            }

            String[] slashedURL = uri.split("/");

            if (slashedURL.length > 4)
            {
                switch (slashedURL[3])
                {

                    case "colleges":
                    {
                        MainActivity.resource_uri = ApiEndPonits.API_INSTITUTE_BY_SLUG + slashedURL[4];
                        MainActivity.type = Constants.TAG_FRAGMENT_INSTITUTE_LIST;
                        break;
                    }
                    case "qna":
                    {
                        MainActivity.resource_uri = ApiEndPonits.API_QUESTION_BY_SLUG + slashedURL[4];
                        MainActivity.type = Constants.TAG_FRAGMENT_QNA_QUESTION_LIST;
                        break;
                    }
                    case "news":
                    {
                        MainActivity.resource_uri = ApiEndPonits.API_NEWS_BY_SLUG+ slashedURL[4];
                        MainActivity.type = Constants.TAG_FRAGMENT_NEWS_LIST;
                        break;
                    }
                    case "articles":
                    {
                        MainActivity.resource_uri = ApiEndPonits.API_ARTICLE_BY_SLUG + slashedURL[4];
                        MainActivity.type = Constants.TAG_FRAGMENT_ARTICLES_LIST;
                        break;
                    }
                    default:
                        MainActivity.resource_uri = "";
                        MainActivity.type = "";
                }
            }
        }

        MainActivity.resource_uri_with_notification_id = MainActivity.resource_uri;

        //events params
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.TAG_RESOURCE_URI), MainActivity.resource_uri);
        eventValue.put(getString(R.string.TAG_DEEP_LINKING_TYPE), MainActivity.type);

        //Events
        SendAppEvent(getString(R.string.CATEGORY_DEEP_LINKING), getString(R.string.ACTION_DEEP_LINKING_OPEN), eventValue, this);

        //this.mHandleNotifications(true);
        //Harsh Making false
        this.mHandleNotifications();
    }

    private void mHandleOtherAppSharedMessage() {

        MainActivity.resource_uri = ApiEndPonits.API_PERSONALIZE_FORUMS;
        MainActivity.resource_uri_with_notification_id = ApiEndPonits.API_PERSONALIZE_FORUMS;
        MainActivity.type = Constants.TAG_FRAGMENT_MY_FB_ENUMERATION;
        this.mHandleNotifications();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, " onResume()  enter time_info  " + System.currentTimeMillis());
        IN_FOREGROUND = true;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MainActivity.type = extras.getString("screen");
            MainActivity.resource_uri = extras.getString("resource_uri");
        }
        adjustFontScale(getResources().getConfiguration());
        // Logs 'install' and 'app activate' App Events.
        // AppEventsLogger.activateApp(this);
        System.gc();
        Log.e(TAG, " onResume()  exit  time_info  " + System.currentTimeMillis());
    }

    @Override
    protected void onPause() {
        super.onPause();
        IN_FOREGROUND = false;
        // Logs 'app deactivate' App Event.
        // AppEventsLogger.deactivateApp(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(appLinkReceiver);
        System.gc();
    }

    @Override
    protected void onStart() {
        Log.e(TAG, " onStart()   enter time_info  " + System.currentTimeMillis());
        super.onStart();
        Log.e(TAG, " onStart()   enter time_info  " + System.currentTimeMillis());
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        /*if(mNetworkChangeReceiver == null){
            mNetworkChangeReceiver = new NetworkChangeReceiver();
        }
        IntentFilter networkFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkChangeReceiver, networkFilter);*/

        IntentFilter linkFilter = new IntentFilter(Constants.CONTENT_LINK_FILTER);
        linkFilter.addAction(Constants.NOTIFICATION_FILTER);
        LocalBroadcastManager.getInstance(this).registerReceiver(appLinkReceiver, linkFilter);

        Log.e(TAG, " onStart()   step1 time_info  " + System.currentTimeMillis());
        mGoogleApiClient.connect();
        /*Action viewAction = Action.newAction(Action.TYPE_VIEW,
                // TODO: choose an action type.
                "CollegeDekho App", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("https://www.collegedekho.com"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse(Constants.BASE_APP_URI.toString())
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);*/
        Log.e(TAG, " onStart()   exit  time_info  " + System.currentTimeMillis());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }

    @Override
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().end(getIndexApiAction());
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
       /* if(mNetworkChangeReceiver != null){
            unregisterReceiver(mNetworkChangeReceiver);
            mNetworkChangeReceiver = null;
        }*/

        LocalBroadcastManager.getInstance(this).unregisterReceiver(appLinkReceiver);
        /*Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CollegeDekho App", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("https://www.collegedekho.com"),
                // TODO: Make sure this auto-generated app URL is correct.
                Constants.BASE_APP_URI
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);*/
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        hideProgressDialog();
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.e(TAG, "onConnected called");
        Constants.IS_LOCATION_SERVICES_ENABLED = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended called");
        Constants.IS_LOCATION_SERVICES_ENABLED = false;

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed called");
        Constants.IS_LOCATION_SERVICES_ENABLED = false;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public static void setUserToken(Profile profile) {
        MainActivity.mProfile = profile;
        if(MainActivity.mNetworkUtils != null){
            MainActivity.mNetworkUtils.setToken(profile.getToken());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                //called when the up affordance/carat in actionbar is pressed
                if (currentFragment instanceof HomeFragment) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }else {
                    onBackPressed();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // call this method to redraw tool bar items
                            invalidateOptionsMenu();
                        }
                    }, 200);
                }
                return true;
            case R.id.action_profile:
                mDisplayProfileFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            mSearchView = (SearchView) searchItem.getActionView();
            SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
            // set the hint text color
            autoComplete.setHintTextColor(Color.GRAY);
            // set the text color
            autoComplete.setTextColor(Color.BLUE);
            try {
                Field searchField = SearchView.class.getDeclaredField("mCloseButton");
                searchField.setAccessible(true);
                ImageView mSearchCloseButton = (ImageView) searchField.get(mSearchView);
                if (mSearchCloseButton != null) {
                    mSearchCloseButton.setEnabled(true);
                    AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.gravity = Gravity.CENTER;
                    mSearchCloseButton.setLayoutParams(lp);
                    mSearchCloseButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_search));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error finding close button", e);
            }
        }
        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            mSearchView.setIconifiedByDefault(true);
            mSearchView.setQueryHint("Search Institutes");
            mSearchView.setOnQueryTextListener(queryTextListener);
            mSearchView.setOnCloseListener(queryCloseListener);
            mSearchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getConnectivityStatus(getApplicationContext()) == Constants.TYPE_NOT_CONNECTED) {
                        displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
                        mSearchView.onActionViewCollapsed();
                    }
                }
            });
            mSearchView.clearFocus();
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (currentFragment instanceof ProfileFragment
                || currentFragment instanceof ExamsFragment || currentFragment instanceof StreamFragment
                || currentFragment instanceof PsychometricStreamFragment || currentFragment instanceof StepByStepFragment
                || currentFragment instanceof OTPVerificationFragment || currentFragment instanceof WebViewFragment
                || currentFragment instanceof PsychometricTestParentFragment || currentFragment instanceof LoginForCounselorFragment
                || currentFragment instanceof NotificationSettingsFragment) {
            menu.setGroupVisible(R.id.main_menu_group, false);
        } else if (menu.size() > 0){
            menu.getItem(0).setVisible(true);
        }

        mSetCounselorMenuVisibility();
        setSearchAvailable(menu);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        boolean isTuteComplete ;
        if (currentFragment instanceof WishlistFragment) {
            isTuteComplete = sharedPreferences.getBoolean(getString(R.string.WISHLIST_TUTE), false);
            showMenuGroupVIsibility(menu, isTuteComplete);
            // hide search menu bar on wishList screen
            menu.setGroupVisible(R.id.search_menu_group, false);
        } else if (currentFragment instanceof CDRecommendedInstituteFragment) {
            int tab = ((CDRecommendedInstituteFragment) currentFragment).currentTabId;
            if (tab == 1) {
                isTuteComplete = sharedPreferences.getBoolean(getString(R.string.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE), false);
                showMenuGroupVIsibility(menu, isTuteComplete);
            } else if (tab == 2) {
                isTuteComplete = sharedPreferences.getBoolean(getString(R.string.WISHLIST_TUTE), false);
                showMenuGroupVIsibility(menu, isTuteComplete);
            }
            // hide search menu bar on cd recoInstitute screen
            menu.setGroupVisible(R.id.search_menu_group, false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * It will show nad hide toolbar items
     * @param menu inflated menu layout
     * @param flag menu items will show and hide based on this flag
     */
    private void showMenuGroupVIsibility(Menu menu, boolean flag) {
        if (!flag) {
            menu.setGroupVisible(R.id.search_menu_group, false);
            menu.setGroupVisible(R.id.main_menu_group, false);
        } else {
            menu.setGroupVisible(R.id.main_menu_group, true);
            menu.setGroupVisible(R.id.search_menu_group, true);
        }
    }

    private void setSearchAvailable(Menu menu) {
        if (currentFragment != null) {
            if (currentFragment instanceof HomeFragment) {
                menu.setGroupVisible(R.id.search_menu_group, true);
                if (mSearchView != null) {
                    mSearchView.setQueryHint("Search Institutes");
                }
            } else if (currentFragment instanceof ArticleFragment) {
                menu.setGroupVisible(R.id.search_menu_group, true);
                if (mSearchView != null) {
                    mSearchView.setQueryHint("Search Articles");
                }
            } else if (currentFragment instanceof NewsFragment) {
                menu.setGroupVisible(R.id.search_menu_group, true);
                if (mSearchView != null) {
                    mSearchView.setQueryHint("Search News");
                }

            } else if (currentFragment instanceof QnAQuestionsListFragment) {

                menu.setGroupVisible(R.id.search_menu_group, true);
                if (mSearchView != null) {
                    mSearchView.setQueryHint("Search Questions");
                }
            } else if (currentFragment instanceof InstituteListFragment) {// && mCurrentTitle != null && !(mCurrentTitle.equals("WishList Institutes") || mCurrentTitle.equals("Recommended Institutes"))) {
                menu.setGroupVisible(R.id.search_menu_group, true);
                if (mSearchView != null) {
                    mSearchView.setQueryHint("Search Institutes");
                }
            } else {
                menu.setGroupVisible(R.id.search_menu_group, false);
            }
        }
    }

    /**
     * This method is used to show and hide counselor Call FAB sub menu
     *  button which is present in activity_main.xml with the help
     *  of this button user can call to the counselor
     */
    private void showCounselorCallMenu(){
        if(mProfile != null ){
            // user has a counselor contact number then show counselor call FAb button
            String contact = mProfile.getCounselor_contact_no();
            View counselorCall = findViewById(R.id.counselor_call_button);
            if(counselorCall.getVisibility() == View.GONE) {
                if (contact != null && !contact.isEmpty()) {
                    counselorCall.setVisibility(View.VISIBLE);
                } else {
                    counselorCall.setVisibility(View.GONE);
                }
            }
        }
    }

    public void mSetCounselorMenuVisibility(){

        if(this.mFabMenu != null) {
            if (this.mFabMenu.isOpened()) {
                this.mFabMenu.close(true);
            }
            if (!IS_HOME_LOADED) {
                this.mFabMenu.setVisibility(View.GONE);
            } else if (currentFragment instanceof SplashFragment) {
                this.mFabMenu.setVisibility(View.GONE);
            } else if (currentFragment instanceof StepByStepFragment) {
                this.mFabMenu.setVisibility(View.GONE);
            } else if (currentFragment instanceof MyFutureBuddiesFragment) {
                mFabMenu.setVisibility(View.GONE);
            } else if (currentFragment instanceof PsychometricTestParentFragment) {
                mFabMenu.setVisibility(View.GONE);
            } else if (currentFragment instanceof OTPVerificationFragment) {
                mFabMenu.setVisibility(View.GONE);
            } else if (currentFragment instanceof LoginForCounselorFragment) {
                mFabMenu.setVisibility(View.GONE);
            }else if (currentFragment instanceof PostAnonymousLoginFragment) {
                mFabMenu.setVisibility(View.GONE);
            }
            else if (currentFragment instanceof QnAQuestionsListFragment
                    || currentFragment instanceof QnaQuestionDetailFragmentNew) {
                mFabMenu.setVisibility(View.GONE);
            }else if (currentFragment instanceof CDRecommendedInstituteFragment) {
                mFabMenu.setVisibility(View.GONE);
            }else if (currentFragment instanceof InstituteListFragment) {
                mFabMenu.setVisibility(View.GONE);
            }else if (currentFragment instanceof InstituteDetailFragment) {
                if (mInstitute != null){
                    boolean isShow =   ((InstituteDetailFragment) currentFragment).updateFabCounselorButton();
                    if(isShow){
                        mFabMenu.setVisibility(View.GONE);
                    }else{
                        mFabMenu.setVisibility(View.VISIBLE);
                    }
                }else {
                    mFabMenu.setVisibility(View.GONE);
                }
            }else if (currentFragment instanceof HomeFragment) {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
                boolean  isTuteCompleted = sharedPreferences.getBoolean(getString(R.string.FEED_HOME_TUTE), false);
                boolean IsChatTutCompleted = sharedPreferences.getBoolean(getString(R.string.INTERACTION_HOME_TUTE), false);
                boolean IsCollegeTutCompleted = sharedPreferences.getBoolean(getString(R.string.INSTITUTES_HOME_TUTE), false);
                boolean IsPrepareTutCompleted = sharedPreferences.getBoolean(getString(R.string.PREPARE_HOME_TUTE), false);
                int tabPagerPosition = ((HomeFragment) currentFragment).getSelectedPage();
                if(isTuteCompleted && tabPagerPosition == 0) {
                    this.mFabMenu.setVisibility(View.VISIBLE);
                }else if(IsCollegeTutCompleted &&   tabPagerPosition == 1){
                    this.mFabMenu.setVisibility(View.VISIBLE);
                }else if(IsChatTutCompleted &&  tabPagerPosition == 2){
                    this.mFabMenu.setVisibility(View.VISIBLE);
                }else if(IsPrepareTutCompleted &&  tabPagerPosition == 3){
                    this.mFabMenu.setVisibility(View.VISIBLE);
                }else{
                    mFabMenu.setVisibility(View.GONE);
                }
            }else if (currentFragment instanceof FilterFragment) {
                this.mFabMenu.setVisibility(View.GONE);
            }else{
                mFabMenu.setVisibility(View.VISIBLE);
            }
        }
        onShowFabMenu();
    }

    public void mShowFabCounselor(){
        if(mFabMenu != null)
            this.mFabMenu.setVisibility(View.VISIBLE);
    }

    /**
     * This method is used to register userId with Apps Flyer
     * and GA Tracker and crashlytics
     */
    private void setUserIdWithAllEvents() {
        // register user id with apps flyer
        AppsFlyerLib.getInstance().setCustomerUserId(MainActivity.mProfile.getId());
        // register user id with GA tracker
        MainActivity.tracker.setClientId(MainActivity.mProfile.getId());
        // set user credencial with crashlytices
        Crashlytics.setUserIdentifier(MainActivity.mProfile.getId());
        Crashlytics.setUserEmail(MainActivity.mProfile.getEmail());
        Crashlytics.setUserName(MainActivity.mProfile.getName());

        // Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.SESSION_STARTED_DATE_TIME), new Date().toString());
        eventValue.put(getString(R.string.USER_ID), MainActivity.mProfile.getId());
        eventValue.put(getString(R.string.USER_EMAIL), mProfile.getEmail());
        eventValue.put(getString(R.string.USER_PHONE), mProfile.getPhone_no());
        SendAppEvent(getString(R.string.CATEGORY_BOOK_KEEPING), getString(R.string.SESSION_STARTED), eventValue, this);
    }


    public void onProfileImageUploaded(String responseJson) {
        try {
            Profile profile = JSON.std.beanFrom(Profile.class, responseJson);
            if (mProfile != null && profile != null)
                mProfile.setImage(profile.getImage());
            String u = JSON.std.asString(mProfile);
            this.getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putString(getString(R.string.KEY_USER), u).apply();

            if (currentFragment instanceof ProfileFragment) {
                ((ProfileFragment) currentFragment).updateProfileImage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO :: EDIT THIS TO MAKE POST ANONYMOUS LOGIN
    @Override
    public void onPostAnonymousLogin() {
        mDisplayPostAnonymousLoginFragment();
    }


    private void mParseProfileResponse(String response) {
        try {

            Profile profile= JSON.std.beanFrom(Profile.class, response);
            // when user level response comes back from server it remove user
            // selected stream when user already selected stream
            if(currentFragment instanceof PrefStreamSelectionFragment){
                profile.setPreferred_stream_id(mProfile.getPreferred_stream_id());
                profile.setPreferred_stream_short_name(mProfile.getPreferred_stream_short_name());
            }
            MainActivity.mProfile = profile;
            String u = JSON.std.asString(mProfile);
            this.getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putString(getString(R.string.KEY_USER), u).apply();
            AppUser.getInstance(getApplicationContext()).setUserStateSession(mProfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is called when user change anything in Institute List
     * @param response server response
     */
    private void mParseInstituteListResponse(String response) {
        try {
            String val = this.extractResults(response);
            List<Institute> instituteList = JSON.std.listOfFrom(Institute.class, val);
            if(this.mInstituteList == null) {
                this.mInstituteList = new ArrayList<>();
            }else{
                this.mInstituteList.clear();
            }
            int count = instituteList.size();
            for (int i = 0; i < count; i++) {
                Institute tempInstitute = instituteList.get(i);
                if(tempInstitute == null)continue;
                this.mInstituteList.add(tempInstitute);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to load user's profile fragment
     */
    private void mDisplayProfileFragment() {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName());
        if (fragment == null) {
            mDisplayFragment(ProfileFragment.getInstance(), true, ProfileFragment.class.getSimpleName());
        } else {
            try {
                int count = getSupportFragmentManager().getBackStackEntryCount();
                for (int i = 0; i < count - 1; i++)
                    getSupportFragmentManager().popBackStack();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            mDisplayFragment(fragment, false, ProfileFragment.class.getSimpleName());
        }
    }

    private void mDisplayCousreSelectionFragment(String extra)
    {
        this.mMakeNetworkCall(Constants.SEARCH_COURSES, ApiEndPonits.API_COURSE_SEARCH+extra+"/" , null);
    }

    @Override
    public void onRefreshProfile() {
        this.requestForUserProfileUpdate(TAG_REFRESH_PROFILE, null);
    }

    private void mParseAndRefreshProfileResponse(String response) {
        try {
            MainActivity.mProfile = JSON.std.beanFrom(Profile.class, response);
            if (currentFragment instanceof ProfileFragment)
                ((ProfileFragment) currentFragment).mRefreshProfileOnResponse(MainActivity.mProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to request for post and get user's profile data
     * @param params hashMap contains user info in key value pair
     */
    public void requestForProfile(HashMap<String, String> params) {
        if (getConnectivityStatus(getApplicationContext()) == Constants.TYPE_NOT_CONNECTED)
            return;

        if (params != null && mProfile != null) {
            // if user's name is not available or name is anonymous and user has saved his/her
            // name in me profile contacts then fetch name and save it on server
            String name = mProfile.getName();
            if (name == null || name.isEmpty()
                    || name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())) {
                if (mDeviceProfile != null && mDeviceProfile.profileData != null
                        && mDeviceProfile.profileData[0] != null && !mDeviceProfile.profileData[0].isEmpty()) {
                    params.put(getString(R.string.USER_NAME), mDeviceProfile.profileData[0]);
                }
            }
            // if user's email id is anonymous and device play store email id does not
            // exist in our server database then sever will allow to replace it with mDeviceProfile's email id
            if (mProfile.getIs_anony() == ProfileMacro.ANONYMOUS_USER) {
                String email = Utils.getDeviceEmail(getApplicationContext());
                if (email != null && !email.isEmpty())
                    params.put(getString(R.string.USER_EMAIL), email);
            }
            // if user's phone number is not available and it is saved in me profile contacts
            // then fetch it  and save it on mDeviceProfile's profile
            if (mProfile.getPhone_no() == null || mProfile.getPhone_no().length() < 10) {
                if (mDeviceProfile != null && mDeviceProfile.getPrimaryPhone() != null && !mDeviceProfile.getPrimaryPhone().isEmpty())
                    params.put(getString(R.string.USER_PHONE), mDeviceProfile.getPrimaryPhone());
            }
        }
        requestForUserProfileUpdate(Constants.TAG_UPDATE_PROFILE_OBJECT, params);
    }

    public void onRequestForLocationUpdate(HashMap<String, String> params) {
        requestForUserProfileUpdate(Constants.TAG_LOCATION_UPDATED, params);
    }


    /**
     * This method is used to update user profile whenever mDeviceProfile update
     *  his/her information about basic info , current education, preferred education details,
     *  interested exams details and other basic info
     * @param params params
     * @param TAG tag
     */
    public void requestForUserProfileUpdate(String TAG, Map<String, String> params) {

        int requestMethod = Request.Method.GET;
        if (params != null) {
            requestMethod = Request.Method.POST;
        }
        this.mMakeNetworkCall(TAG, ApiEndPonits.API_PROFILE, params, requestMethod);
    }

    /**
     * This method is called when user select exams while profile editing
     * and send a request to update user's exams
     * @param params HashMap of user selected exams with yearly_exams as key and value as exam ids
     */
    @Override
    public void onUserExamsEdited(HashMap<String, String> params) {
        this.requestForUserProfileUpdate(Constants.TAG_SUBMIT_EDITED_EXAMS_LIST, params);
    }

    @Override
    public void onUserProfileEdited(HashMap<String, String> params,int viewPosition ) {
        // update user location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                params.put("latitude", String.valueOf(mLastLocation.getLatitude()));
                params.put("longitude", String.valueOf(mLastLocation.getLongitude()));
            }
        }
        this.requestForUserProfileUpdate(Constants.TAG_UPDATE_USER_PROFILE+"#"+viewPosition, params);
    }
    /**
     * This method used to request to show user's profile screen  and update profile data ,
     * if profile data is not available then send a request to load user's profile
     */
    @Override
    public void requestForProfileFragment() {
        if(MainActivity.mProfile == null) {
            if (NetworkUtils.getConnectivityStatus(getApplicationContext()) == Constants.TYPE_NOT_CONNECTED) {
                displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
                return;
            }
            this.requestForUserProfileUpdate(Constants.TAG_LOAD_PROFILE, null);
        }else{
            mDisplayProfileFragment();
            this.requestForProfile(null);
        }
    }

    /**
     * This method is used to update user name when user does not have a name and wants to
     * chat or asks  a question and give answer
     * @param params  HashMap contains user name with key user_name
     * @param msg  chat Msg by User
     */
    @Override
    public void onNameUpdated(Map<String, String> params, String msg) {
        this.requestForUserProfileUpdate(Constants.TAG_NAME_UPDATED + "#" + msg, params);
    }


    @Override
    public void onRequestNumberVerification(String myFbURI, int index, int size, String msg) {
        this.defferedFunction.put(Constants.FRAGMENT_TYPE, MyFutureBuddiesFragment.TAG);
        this.defferedFunction.put(Constants.FRAGMENT_INDEX, "1");
        this.defferedFunction.put(Constants.FRAGMENT_ACTION, Constants.TAG_MY_FB_COMMENT_SUBMITTED);
        this.defferedFunction.put(Constants.DEFERRED_ARGUMENTS, myFbURI + Constants.LOCAL_DELIMITER + msg+
                Constants.LOCAL_DELIMITER + index+ Constants.LOCAL_DELIMITER + size);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(LoginForCounselorFragment.class.getSimpleName());
        if (fragment == null)
            mDisplayFragment(LoginForCounselorFragment.newInstance(), true, LoginForCounselorFragment.class.getSimpleName());
        else
            mDisplayFragment(fragment, false, LoginForCounselorFragment.class.getSimpleName());

    }

    private void hasNextDeferredFunction()
    {

        if (this.defferedFunction != null && this.defferedFunction.size() > 0)
        {

            String[] args = this.defferedFunction.get(Constants.DEFERRED_ARGUMENTS).split(Constants.LOCAL_DELIMITER);
            this.onMyFBCommentSubmitted(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), true);

            this.defferedFunction.clear();
        }
    }

    /**
     * This method is used to request for specialization list based on
     * stream which is selected by the user while updating his/her profile
     * @param streamId  stream Id
     * @param requestType request Type
     */
    @Override
    public void requestForSpecialization(int streamId, String requestType) {
        if (getConnectivityStatus(getApplicationContext()) != Constants.TYPE_NOT_CONNECTED) {
            this.mMakeNetworkCall(Constants.TAG_REQUEST_FOR_SPECIALIZATION + "#" + requestType, ApiEndPonits.BASE_URL + "specializations/?stream=" + streamId, null, Request.Method.GET);
        }
    }

    /**
     * This method is used to request for Degrees list based on
     * level which is selected by the user while updating his/her profile
     * @param levelId levelID
     * @param requestType request Type
     */
    @Override
    public void requestForDegrees(int levelId, String requestType) {
        int amIConnectedToInternet = getConnectivityStatus(getApplicationContext());
        if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
            this.mMakeNetworkCall(Constants.TAG_REQUEST_FOR_DEGREES + "#" + requestType, ApiEndPonits.BASE_URL + "degrees/?level=" + levelId, null, Request.Method.GET);
        }
    }

    @Override
    public void toRecommended() {
        this.mMakeNetworkCall(Constants.WIDGET_RECOMMENDED_INSTITUTES_FRON_PROFILE, ApiEndPonits.API_RECOMMENDED_INSTITUTES, null);
    }

    @Override
    public void toFeedDashboard(){
        mClearBackStack();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentFragment != null && currentFragment instanceof  HomeFragment){
                    ((HomeFragment) currentFragment).setSelectedPage(0);
                }
            }
        }, 200);
    }

    /**
     *  This method is used to update user profile info
     *  on Profile page after successfully updating any thing in profile
     * @param tag tag
     * @param responseJson responseJson
     */
    private void profileSuccessfullyUpdated(String tag, String responseJson) {
        mParseProfileResponse(responseJson);
        if(currentFragment instanceof ProfileFragment) {
            String[] TAG = tag.split("#");
            if (TAG[0].equalsIgnoreCase(Constants.TAG_UPDATE_USER_PROFILE)) {
                Utils.DisplayToast(getApplicationContext(), "Profile Updated");
                try {
                    int viewPosition = Integer.parseInt(TAG[1]);
                    ((ProfileFragment) currentFragment).profileUpdatedSuccessfully(viewPosition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (TAG[0].equalsIgnoreCase(Constants.TAG_UPDATE_PROFILE_EXAMS)) {
                ((ProfileFragment) currentFragment).profileUpdatedSuccessfully(3);
            } else {
                ((ProfileFragment) currentFragment).updateUserProfile();
            }
        }
        else if(currentFragment instanceof  HomeFragment){
            ((HomeFragment)currentFragment).updateUserInfo();
        }
    }

    private void updateUserSpecializationList(String requestType,String responseJson) {
        try {
            List<ProfileSpinnerItem> userSpecializationList = JSON.std.listOfFrom(ProfileSpinnerItem.class, responseJson);
            if(currentFragment instanceof ProfileFragment)
                ((ProfileFragment)currentFragment).updateUserSpecializationList(requestType, (ArrayList<ProfileSpinnerItem>) userSpecializationList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUserDegreesList(String requestType,String responseJson) {
        try {
            List<ProfileSpinnerItem> userDegreesList = JSON.std.listOfFrom(ProfileSpinnerItem.class, responseJson);
            if(currentFragment instanceof ProfileFragment)
                ((ProfileFragment)currentFragment).updateUserDegreesList(requestType, (ArrayList<ProfileSpinnerItem>) userDegreesList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to split response json
     * with result , filter and next tags
     * @param response responseJson
     * @return string
     */
    public String extractResults(String response) {
        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            if (map.get("next") != null)
                next = map.get("next").toString();
            else
                next = null;

            if (map.containsKey("filters"))
                this.mFilters = JSON.std.asString(map.get("filters"));

            if (map.containsKey("currencies"))
                this.mCurrencies = JSON.std.asString(map.get("currencies"));

            if (map.containsKey("undecided_count"))
                this.mUndecidedInstitutesCount = Integer.valueOf(JSON.std.asString(map.get("undecided_count")));

            if (map.containsKey("shortlist_count"))
                this.mShortListInstituteCount = Integer.valueOf(JSON.std.asString(map.get("shortlist_count")));

            if (map.containsKey("buzzlist_count"))
                this.mFeaturedInstituteCount = Integer.valueOf(JSON.std.asString(map.get("buzzlist_count")));

            if (map.containsKey("recommended_count"))
                this.mRecommendedInstituteCount = Integer.valueOf(JSON.std.asString(map.get("recommended_count")));

            return JSON.std.asString(map.get("results"));
        } catch (Exception e) {
            Log.e(TAG, "excetion occurred while extarcting result");
        }

        return null;
    }

    private void mDisplayStreams(String response, boolean addToBackstack) {
        try {
            List<Stream> streams = JSON.std.listOfFrom(Stream.class, extractResults(response));
            this.mDisplayFragment(StreamFragment.newInstance(new ArrayList<>(streams), addToBackstack), addToBackstack, Constants.TAG_FRAGMENT_STREAMS);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method is used to update institutes list of next page
     * @param response responseJson
     */
    private void updateNextInstituteList(String response, int listType) {
        try {
            List<Institute> institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            switch (listType) {
                case Constants.WISH_LIST_TYPE: {

                    this.mInstituteList.addAll(institutes);
                    if (currentFragment instanceof WishlistFragment) {
                        ((WishlistFragment) currentFragment).clearList();
                        ((WishlistFragment) currentFragment).updateLastList(mInstituteList, next);
                    } else if (currentFragment instanceof CDRecommendedInstituteFragment)
                        ((CDRecommendedInstituteFragment) currentFragment).updateWishList(mInstituteList, next);
                    break;
                }
                default: {
                    if (currentFragment instanceof InstituteListFragment) {
                        this.mInstituteList.addAll(institutes);
                        ((InstituteListFragment) currentFragment).updateList(institutes, next);
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method is used to update feed list of next page
     * @param response responseJson
     */
    private void updateNextFeedList(String response) {
        List<Feed> feedList = this.mParseFeedForRecoInstitute(response);
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).feedNextLoaded(new ArrayList<>(feedList), this.next);
        }
    }

    /**
     * This method is used to refresh feed list on swipe down gesture
     * @param response responseJson
     */
    private void mFeedRefreshed(String response) {
        List<Feed> feedList = this.mParseFeedForRecoInstitute(response);
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).feedsRefreshed(new ArrayList<>(feedList),this.next, false);
        }    }


    /**
     * This method is used to update institutes list of next page
     * @param response server response to parse qna next list
     */
    private void updateLastInstituteList(String response) {
        try {
            List<Institute> institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            this.mInstituteList.clear();
            this.mInstituteList.addAll(institutes);

            if (currentFragment instanceof InstituteListFragment) {
                ((InstituteListFragment) currentFragment).updateLastList(institutes, next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method is used to update news list of next page
     * @param response server response to parse news next list
     */
    private void updateNextNewsList(String response) {
        try {
            List<News> news = JSON.std.listOfFrom(News.class, extractResults(response));
            if(this.mNewsList ==  null)
                this.mNewsList = new ArrayList<>();

            this.mNewsList.addAll(news);
            this.mParseSimilarNews(this.mNewsList);

            if (this.mNewsList != null && currentFragment instanceof NewsFragment) {
                ((NewsFragment) currentFragment).updateNewsList(new ArrayList<>(this.mNewsList), this.next);
            }
            if (this.mNewsList != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteNews(new ArrayList<>(this.mNewsList), this.next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method is used to update articles list of next page
     * @param response server response to parse article next list
     */
    private void updateNextArticlesList(String response) {
        try {
            List<Articles> articles = JSON.std.listOfFrom(Articles.class, extractResults(response));
            this.mArticlesList.addAll(articles);

            this.mParseSimilarArticle(this.mArticlesList);
            if (this.mArticlesList != null && currentFragment instanceof ArticleFragment) {
                ((ArticleFragment) currentFragment).updateArticleList(new ArrayList<>(this.mArticlesList), next);
            }

            if (this.mArticlesList != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteArticle(new ArrayList<>(this.mArticlesList), this.next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method is used to update qna list of next page
     * @param response server response to parse qna next list
     */
    private void updateNextQnaList(String response) {
        ArrayList<QnAQuestions> qnaQuestionsList = parseAndReturnQnAList(response);

        if (currentFragment instanceof QnAQuestionsListFragment) {
            ((QnAQuestionsListFragment) currentFragment).updateList(new ArrayList<>(qnaQuestionsList), next);
        }
        else if (currentFragment instanceof InstituteDetailFragment){
            ((InstituteDetailFragment) currentFragment).updateQnasList(new ArrayList<>(qnaQuestionsList),next);
        }
    }

    /**
     * This method is used to update institute forums list of next page
     * @param response server response to parse institute forums list
     */
    private void updateNextForumsList(String response) {
        try {
            List<MyFutureBuddiesEnumeration> forumsList = JSON.std.listOfFrom(MyFutureBuddiesEnumeration.class, extractResults(response));
            this.mFbEnumeration.addAll(forumsList);

            if (currentFragment instanceof MyFutureBuddiesEnumerationFragment)
                ((MyFutureBuddiesEnumerationFragment) currentFragment).updateList(forumsList, next);

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mDisplayInstituteList(String response, boolean filterAllowed, boolean isHavingNextUrl) {
        this.mDisplayInstituteList(response, filterAllowed, isHavingNextUrl, Constants.INSTITUTE_TYPE);
    }

    private void mDisplayCDRecommendedInstituteList(String response, boolean isHavingNextUrl, Constants.CDRecommendedInstituteType cdRecommendedInstituteType, boolean isUpdate) {
        System.gc();

        if (!isHavingNextUrl)
            next = null;

        if (isUpdate) {
            if (currentFragment != null && currentFragment instanceof CDRecommendedInstituteFragment) {
                mParseInstituteListResponse(response);
                Collections.reverse(this.mInstituteList);
                if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.UNDECIDED) {
                    ((CDRecommendedInstituteFragment) currentFragment).showUndecidedInstitutes(this.mInstituteList, next);
                }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.RECOMMENDED) {
                    ((CDRecommendedInstituteFragment) currentFragment).updateRecommendedList(this.mInstituteList, next, this.mRecommendedInstituteCount);
                }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.FEATURED){
                    ((CDRecommendedInstituteFragment) currentFragment).updateBuzzList(this.mInstituteList, next, this.mFeaturedInstituteCount);
                }
            }
        } else {
            mParseInstituteListResponse(response);
            Collections.reverse(this.mInstituteList);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST));

            if (fragment == null) {
                this.mDisplayFragment(CDRecommendedInstituteFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next,
                        this.mRecommendedInstituteCount, this.mShortListInstituteCount, this.mFeaturedInstituteCount, this.mUndecidedInstitutesCount,cdRecommendedInstituteType.ordinal()),
                        !isFromNotification, getString(R.string.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST));
            } else {
                if (currentFragment instanceof  CDRecommendedInstituteFragment && fragment instanceof CDRecommendedInstituteFragment) {
                    if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.UNDECIDED) {
                        ((CDRecommendedInstituteFragment) fragment).showUndecidedInstitutes(this.mInstituteList, next);
                    }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.RECOMMENDED) {
                        ((CDRecommendedInstituteFragment) fragment).updateRecommendedList(this.mInstituteList, next, this.mRecommendedInstituteCount);
                    }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.SHORTLIST){
                        ((CDRecommendedInstituteFragment) fragment).updateWishList(this.mInstituteList, next);
                    }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.FEATURED){
                        ((CDRecommendedInstituteFragment) fragment).updateBuzzList(this.mInstituteList, next,this.mFeaturedInstituteCount);
                    }
                }

                // this.mDisplayFragment(fragment, false, getString(R.string.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST));
            }
        }
    }

    private void mDisplayInstituteList(String response, boolean filterAllowed, boolean isHavingNextUrl, int listType) {
        mParseInstituteListResponse(response);
        if (this.mFilterKeywords != null && this.mFilterKeywords.size() > 0)
            this.mFilterCount = this.mFilterKeywords.size();

        if (!isHavingNextUrl)
            next = null;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);
        if (currentFragment instanceof InstituteListFragment && fragment != null) {
            ((InstituteListFragment) fragment).clearList();
            ((InstituteListFragment) fragment).updateList(this.mInstituteList, next);
            ((InstituteListFragment) fragment).updateFilterButton(this.mFilterCount);
        }else {
            this.mDisplayFragment(InstituteListFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next, filterAllowed, this.mFilterCount,listType), !isFromNotification, Constants.TAG_FRAGMENT_INSTITUTE_LIST);
        }
    }

    private void mDisplayWishlistInstituteList(String response, boolean filterAllowed, boolean isHavingNextUrl) {

        mParseInstituteListResponse(response);
        if (!isHavingNextUrl)
            next = null;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);
        if (fragment!= null && currentFragment instanceof WishlistFragment) {
            ((WishlistFragment) fragment).clearList();
            ((WishlistFragment) fragment).updateList(this.mInstituteList, next);
        }else {
            this.mDisplayFragment(WishlistFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next, filterAllowed), !isFromNotification, Constants.TAG_FRAGMENT_WISHLIST_INSTITUTE_LIST);
        }
    }

    private void mShowMyFBEnumeration(String response) {
        try {
            this.mFbEnumeration = JSON.std.listOfFrom(MyFutureBuddiesEnumeration.class, this.extractResults(response));
            this.mDisplayFragment(MyFutureBuddiesEnumerationFragment.newInstance(new ArrayList<>(this.mFbEnumeration), next), !isFromNotification, Constants.TAG_FRAGMENT_MY_FB_ENUMERATION);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mRefreshChatRoom(String response){
        try {
            if(currentFragment instanceof  MyFutureBuddiesEnumerationFragment){
                this.mFbEnumeration = JSON.std.listOfFrom(MyFutureBuddiesEnumeration.class, this.extractResults(response));
                ((MyFutureBuddiesEnumerationFragment) currentFragment).refreshChatRoom(new ArrayList<>(this.mFbEnumeration));
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mShowMyFB(String response, int index, int commentsCount) {
        try {
            this.mFB = this.mParseAndPopulateMyFB(response, index);
            this.mDisplayFragment(MyFutureBuddiesFragment.newInstance(this.mFB, commentsCount), true, MyFutureBuddiesFragment.class.getSimpleName());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mLoadCounselorChat(String response) {

        this.mFB = new MyFutureBuddy();
        this.mFB.setResource_uri(ApiEndPonits.API_L2_CHATS);
        this.mFB.setInstitute_name("");
        this.mFB.setInstitute_logo("");
        this.mFB.setCounselor(true);
        int commentsCount ;
        try {
            JSONObject jsonObject = new JSONObject(response);
            commentsCount = jsonObject.getInt("count");
            if(commentsCount  > 0){
                String result = extractResults(response);
                List<MyFutureBuddyComment> commentList = JSON.std.listOfFrom(MyFutureBuddyComment.class, result);
                this.mFB.setFutureBuddiesCommentsSet((ArrayList<MyFutureBuddyComment>) commentList);
            }
        }catch (Exception e) {
            Log.e(TAG, "Exception occurred while parsing Counselor chat ");
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MyFutureBuddiesFragment.class.getSimpleName());
        commentsCount = this.mFB.getComments_count();
        if (fragment == null) {
            this.mDisplayFragment(MyFutureBuddiesFragment.newInstance(this.mFB, commentsCount), true, MyFutureBuddiesFragment.class.getSimpleName());
        }else{
            ((MyFutureBuddiesFragment)fragment).updateChatPings(this.mFB.getFutureBuddiesCommentsSet(), commentsCount);
        }
    }
    private void mLoadPNSCounselorChat(String response) {

        this.mFB = new MyFutureBuddy();
        this.mFB.setResource_uri(ApiEndPonits.API_L2_CHATS);
        this.mFB.setInstitute_name("");
        this.mFB.setInstitute_logo("");
        this.mFB.setCounselor(true);
        int commentsCount;
        try {
            JSONObject jsonObject = new JSONObject(response);
            commentsCount = jsonObject.getInt("count");
            if(commentsCount  > 0){
                String result = extractResults(response);
                List<MyFutureBuddyComment> commentList = JSON.std.listOfFrom(MyFutureBuddyComment.class, result);
                this.mFB.setFutureBuddiesCommentsSet((ArrayList<MyFutureBuddyComment>) commentList);
            }
        }catch (Exception e) {
            Log.e(TAG,"Exception occurred while parsing counselor chat");
        }
        boolean isAddToStack = false;
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            isAddToStack = true;
        }
        commentsCount = this.mFB.getComments_count();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MyFutureBuddiesFragment.class.getSimpleName());
        if (fragment == null) {
            this.mDisplayFragment(MyFutureBuddiesFragment.newInstance(this.mFB, commentsCount), isAddToStack, MyFutureBuddiesFragment.class.getSimpleName());
        }else if (currentFragment instanceof MyFutureBuddiesFragment){
            ((MyFutureBuddiesFragment)fragment).updateChatPings(this.mFB.getFutureBuddiesCommentsSet(), commentsCount);
        }else if (currentSubFragment instanceof MyFutureBuddiesFragment){
            ((MyFutureBuddiesFragment)fragment).updateChatPings(this.mFB.getFutureBuddiesCommentsSet(), commentsCount);
        }
    }

    private void mUpdateMyFB(String response, int index, int oldCount) {
        this.mFB = this.mParseAndPopulateMyFB(response, index);
        //if number of comments have increased
        if (this. mFB != null && this.mFB.getComments_count() > oldCount) {
            ArrayList<MyFutureBuddyComment> myFbComments = this.mFB.getFutureBuddiesCommentsSet();
            if (currentFragment instanceof MyFutureBuddiesFragment)
                ((MyFutureBuddiesFragment) currentFragment).updateChatPings(myFbComments,this.mFB.getComments_count());
            else if ( currentSubFragment instanceof MyFutureBuddiesFragment)
                ((MyFutureBuddiesFragment) currentSubFragment).updateChatPings(myFbComments,this.mFB.getComments_count());
        }
    }

    private void mUpdateCounselorChat(String response, int oldCount) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int commentsCount = jsonObject.getInt("count");
            this.mFB = new MyFutureBuddy();
            if(commentsCount  > 0){
                String result = extractResults(response);
                List<MyFutureBuddyComment> commentList = JSON.std.listOfFrom(MyFutureBuddyComment.class, result);
                this.mFB.setFutureBuddiesCommentsSet((ArrayList<MyFutureBuddyComment>) commentList);
            }
            if (this.mFB != null) {
                this.mFB.setResource_uri(ApiEndPonits.API_L2_CHATS);
                this.mFB.setInstitute_name("");
                this.mFB.setInstitute_logo("");
                this.mFB.setCounselor(true);
                this.mFB.setComments_count(commentsCount);
            }
            if (this.mFB.getComments_count() > oldCount) {
                ArrayList<MyFutureBuddyComment> myFbComments = this.mFB.getFutureBuddiesCommentsSet();
                if (currentFragment instanceof MyFutureBuddiesFragment)
                    ((MyFutureBuddiesFragment) currentFragment).updateChatPings(myFbComments,this.mFB.getComments_count());
                else if (currentSubFragment instanceof MyFutureBuddiesFragment)
                    ((MyFutureBuddiesFragment) currentSubFragment).updateChatPings(myFbComments,this.mFB.getComments_count());

            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private MyFutureBuddy mParseAndPopulateMyFB(String response, int index) {
        MyFutureBuddy myFB = new MyFutureBuddy();

        try {
            ArrayList<MyFutureBuddyComment> myFBCommentsSet = new ArrayList<>();

            JSONObject fb = new JSONObject(response);

            myFB.setComments_count(fb.getInt("comments_count"));
            myFB.setMembers_count(fb.getInt("members_count"));
            myFB.setResource_uri(fb.getString("resource_uri"));
            myFB.setInstitute_name(fb.getString("institute_name"));
            myFB.setInstitute_logo(fb.getString("institute_logo"));
            myFB.setIndex(index);
            myFB.setNext(fb.getString("next"));
            myFB.setCity_name(fb.getString("city_name"));
            myFB.setState_name(fb.getString("state_name"));
            myFB.setIs_partner_college(fb.getString("is_partner_college"));
            myFB.setL3_number(fb.getString("l3_number"));
            JSONArray commentsSet = fb.getJSONArray("instituteforumcomment_set");

            for (int i = 0; i < commentsSet.length(); i++) {
                JSONObject comment;
                MyFutureBuddyComment myFBComment = new MyFutureBuddyComment();

                comment = commentsSet.getJSONObject(i);

                myFBComment.setUser(comment.getString("user"));
                myFBComment.setComment(comment.getString("comment"));
                myFBComment.setAdded_on(comment.getString("added_on"));
                myFBComment.setToken(comment.getString("token"));
                myFBComment.setCommentSent(true);
                myFBComment.setIndex(i);
                myFBComment.setFbIndex(index);
                myFBComment.setUser_image(comment.getString("user_image"));

                myFBCommentsSet.add(myFBComment);
            }

            myFB.setFutureBuddiesCommentsSet(myFBCommentsSet);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return myFB;
    }


    @Override
    public void onInstituteSelected(int position) {
        this.currentInstitute = position;
        if(position<0 || position>=mInstituteList.size()){
            return;
        }
        this.mDisplayInstituteByEntity(this.mInstituteList.get(position), Constants.CDRecommendedInstituteType.RECOMMENDED);
    }

    private void mDisplayInstituteByEntity(Institute institute, Constants.CDRecommendedInstituteType instituteType) {
        this.mInstitute = institute;
        int id = institute.getId();

        if (instituteType == Constants.CDRecommendedInstituteType.SHORTLIST)
        {
            this.mDisplayFragment(InstituteDetailFragment.newInstance(institute, Constants.CDRecommendedInstituteType.SHORTLIST), true, Constants.TAG_FRAGMENT_INSTITUTE);
            this.mMakeNetworkCall(Constants.TAG_INSTITUTE_FORUM, ApiEndPonits.BASE_URL + "personalize/forums/"+ String.valueOf(institute.getForum_id()+"/"), null);
        }
        else
        {
            this.mDisplayFragment(InstituteDetailFragment.newInstance(institute, Constants.CDRecommendedInstituteType.RECOMMENDED), true, Constants.TAG_FRAGMENT_INSTITUTE);
            this.mMakeNetworkCall(Constants.TAG_INSTITUTE_FORUM, ApiEndPonits.BASE_URL + "personalize/forums/"+ String.valueOf(institute.getForum_id())+"/?institute="+institute.getId(), null);
        }

        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_NEWS, ApiEndPonits.BASE_URL + "personalize/news/" + "?institute=" + String.valueOf(id), null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_QNA_QUESTIONS, ApiEndPonits.BASE_URL + "personalize/qna-v2/?institute=" + String.valueOf(id)+"&source=1", null);
//        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_QNA_QUESTIONS, ApiEndPonits.BASE_URL + "personalize/qna-v2/", null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_ARTICLE, ApiEndPonits.BASE_URL + "personalize/articles/" + "?institute=" + String.valueOf(id), null);
        //TODO:: Load FutureBuddy Details
        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.INSTITUTE_RESOURCE_URI), institute.getResource_uri());
        eventValue.put(Constants.INSTITUTE_ID, String.valueOf(id));

        //Events
        SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_INSTITUTE_SELECTED), eventValue, this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if(requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK ) {
            if (currentFragment instanceof ProfileFragment) {
                ((ProfileFragmentListener) currentFragment).requestForCropProfileImage(data);
            }
        }else  if(requestCode == Crop.REQUEST_CROP){
            if (currentFragment instanceof ProfileFragment) {
                if(resultCode == RESULT_OK)
                    ((ProfileFragmentListener) currentFragment).uploadUserProfileImage(Crop.getOutput(data));
                else if(resultCode == RESULT_CANCELED)
                    ((ProfileFragmentListener) currentFragment).deleteTempImageFile();
            }
        }else if (requestCode == Constants.RC_QUIT_VIDEO_PLAYER) {
            if (currentFragment instanceof SplashFragment) {
                isFromNotification = false;
                this.mLoadUserStatusScreen();
            }
        }
        else if(mTrueClient != null && mTrueClient.onActivityResult(requestCode,resultCode,data)) {
            return;
        }
        else if(requestCode==Constants.GCM_RESULT_DATA_KEY && resultCode==RESULT_OK){
            HashMap<String, String> params = null;
            try {
                params = (HashMap<String, String>) data.getSerializableExtra(Constants.DIALOG_DATA);
            }catch (Exception e){
                e.printStackTrace();
            }
            // save GCM dialog data on profile
            if(params!=null && !params.isEmpty()) {
                requestForProfile(params);
            }
        }
        else if(requestCode== REQUEST_CHECK_LOCATION_SETTINGS){
            if(currentFragment instanceof  HomeFragment)
                currentFragment.onActivityResult(requestCode,resultCode,data);
            if(currentFragment instanceof  ProfileFragment)
                currentFragment.onActivityResult(requestCode,resultCode,data);
        }
    }
    private void mDisplaySplashLoginFragment() {
        SplashLoginFragment fragment = SplashLoginFragment.newInstance();
        try {
            currentFragment = fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.container, fragment,SplashLoginFragment.class.getSimpleName() );
            fragmentTransaction.commitAllowingStateLoss();

        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
        } finally {
            //Send GA Session
            MainActivity.GAScreenEvent(getString(R.string.TAG_FRAGMENT_SPLASH_LOGIN));
            HashMap<String, Object> eventValue = new HashMap<>();
            eventValue.put(getString(R.string.SCREEN_NAME), getString(R.string.TAG_FRAGMENT_SPLASH_LOGIN));
            eventValue.put(getString(R.string.LAST_SCREEN_NAME), this.mLastScreenName);
            eventValue.put(getString(R.string.TIME_LAPSED_SINCE_LAST_SCREEN_NAME_IN_MS), String.valueOf(new Date().getTime() - this.mTimeScreenClicked.getTime()));

            this.mTimeScreenClicked = new Date();
            this.mLastScreenName = getString(R.string.TAG_FRAGMENT_SPLASH_LOGIN);

            //Events
            SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_SCREEN_SELECTED), eventValue, this);
        }
    }

    // TODO :: EDIT THIS POST ANONYMOUS LOGIN FRAGMENT
    private void mDisplayPostAnonymousLoginFragment() {
        PostAnonymousLoginFragment fragment = PostAnonymousLoginFragment.newInstance();
        currentFragment = fragment;
        try {
            currentFragment = fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.container, fragment, getString(R.string.TAG_FRAGMENT_POST_ANONYMOUS_LOGIN));
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
            mHideAppBarLayout();
            mSetCounselorMenuVisibility();

        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
        } finally {
            //Send GA Session
            MainActivity.GAScreenEvent(getString(R.string.TAG_FRAGMENT_POST_ANONYMOUS_LOGIN));
            HashMap<String, Object> eventValue = new HashMap<>();
            eventValue.put(getString(R.string.SCREEN_NAME), getString(R.string.TAG_FRAGMENT_POST_ANONYMOUS_LOGIN));
            eventValue.put(getString(R.string.LAST_SCREEN_NAME), this.mLastScreenName);
            eventValue.put(getString(R.string.TIME_LAPSED_SINCE_LAST_SCREEN_NAME_IN_MS), String.valueOf(new Date().getTime() - this.mTimeScreenClicked.getTime()));
            this.mTimeScreenClicked = new Date();
            this.mLastScreenName = getString(R.string.TAG_FRAGMENT_POST_ANONYMOUS_LOGIN);
            //Events
            SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_SCREEN_SELECTED), eventValue, this);
        }
    }


    private void mDisplayFragment(Fragment fragment, boolean addToBackstack, String tag) {
        try {

            this.currentFragment = (BaseFragment) fragment;
            if (!(currentFragment instanceof OTPVerificationFragment)) {
                if (this.getCurrentFocus() != null && this.getCurrentFocus() instanceof EditText) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if(currentFragment instanceof SplashFragment || currentFragment instanceof HomeFragment){
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
            }else {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            }
            fragmentTransaction.replace(R.id.container, fragment, tag);
            if (addToBackstack) {
                fragmentTransaction.addToBackStack(fragment.toString());
            }

            fragmentTransaction.commitAllowingStateLoss();
            //fragmentTransaction.setAllowOptimization(true);
            //  added this line because Cd Reco fragment takes some time for card rendering
            //  in while fragment transaction user press back button app crashs
            if(currentFragment instanceof CDRecommendedInstituteFragment){
                getSupportFragmentManager().executePendingTransactions();
            }

            if (this.currentFragment instanceof HomeFragment) {
                mShowAppBarLayout();
                showCounselorCallMenu();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
            // hide counselor FAB menu
            // mSetCounselorMenuVisibility();
           /* if(currentFragment instanceof MyFutureBuddiesFragment){
                mFabMenu.setVisibility(View.GONE);
            }
           */
            mDisplayHomeAsUpEnabled();
        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
        } finally {
            //Send GA Session
            MainActivity.GAScreenEvent(tag);
            HashMap<String, Object> eventValue = new HashMap<>();
            eventValue.put(getString(R.string.SCREEN_NAME), tag);
            eventValue.put(getString(R.string.LAST_SCREEN_NAME), this.mLastScreenName);
            eventValue.put(getString(R.string.TIME_LAPSED_SINCE_LAST_SCREEN_NAME_IN_MS), String.valueOf(new Date().getTime() - this.mTimeScreenClicked.getTime()));

            this.mTimeScreenClicked = new Date();

            //Events
            SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_SCREEN_SELECTED), eventValue, this);
            this.mLastScreenName = tag;
        }
        if(!(fragment instanceof InstituteListFragment))
            invalidateOptionsMenu();
    }

    /**
     * This method is used to show back arrow icon on if home page
     * is not loaded
     */
    private void mDisplayHomeAsUpEnabled(){

        // it will change hamburger icon into a back arrow on toolbar
        // sometimes Hamburger icon does not change into back arrow
        // because Home Fragment onResume() called after mDisplayFragment()
        // when mClearBackStack() is called just before it.
        if (currentFragment instanceof HomeFragment)
            mDrawerToggle.setDrawerIndicatorEnabled(true);
        else {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
        }
        // set all menu items unchecked when tab fragment is not selected
        if(!(currentFragment instanceof CollegesDashboard)){
            int size = mNavigationView.getMenu().size();
            for (int i = 0; i < size; i++) {
                mNavigationView.getMenu().getItem(i).setChecked(false);
            }
        }
        invalidateOptionsMenu();
    }

    private void mShowAppBarLayout(){
        //  show appBarLayout and toolBar
        RelativeLayout relativeLayout  = (RelativeLayout) findViewById(R.id.main_container);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)relativeLayout.getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        findViewById(R.id.main_container).setLayoutParams(params);
        if(getSupportActionBar() != null)
            getSupportActionBar().show();

        if (findViewById(R.id.app_bar_layout).getVisibility() != View.VISIBLE)
            findViewById(R.id.app_bar_layout).setVisibility(View.VISIBLE);

    }

    private void mHideAppBarLayout(){
        //  show appBarLayout and toolBar
        RelativeLayout relativeLayout  = (RelativeLayout) findViewById(R.id.main_container);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)relativeLayout.getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        findViewById(R.id.main_container).setLayoutParams(params);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    @Override
    public void onDataLoaded(String tag, String response) {
        // check activity is running or not
        if(isFinishing()){
            return;
        }

        String extraTag = null;
        String childIndex ;
        String parentIndex ;
        String like = null;
        String[] tags = tag.split("#");
        Log.e(TAG,response);
        int voteType ;
        boolean hideProgressDialog=true;
        switch (tags[0]) {
            case Constants.TAG_CREATE_USER:
                mUserCreatedSuccessfully(response,tags[0]);
                if(USER_CREATING_PROCESS){
                    this.mLoadUserStatusScreen();
                }
                break;
            case Constants.TAG_SPLASH_LOGIN_PROCEED:
                this.mSplashLoginSuccessfully(response,tags[0]);
                break;
            case Constants.TAG_TRUE_SDK_LOGIN:
            case Constants.TAG_FACEBOOK_LOGIN:
            case Constants.TAG_PHONE_NUMBER_LOGIN:
                //Deleting exam summary on login, since old user's and new user's are merged. And profile is taken from latest user.
                // So old mDeviceProfile's exam summary gets obsolete. We need to reset the cache
                mInvalidateFeedList();
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                this.mProfileLoginSuccessfully(response, tags[0]);
                break;
            case Constants.TAG_LOCATION_UPDATED:
                if(currentFragment instanceof  HomeFragment)
                    ((HomeFragment) currentFragment).requestForYearlyExams();
                if(currentFragment instanceof  ProfileFragment)
                    ((ProfileFragment) currentFragment).requestForYearlyExams();
                break;
            case Constants.TAG_REQUEST_FOR_OTP:
                this.onResponseForOTP();
                break;
            case Constants.TAG_LOAD_SUB_LEVELS:
                onResponseForSubLevels(response);
                break;
            case Constants.TAG_LOAD_LEVEL_STREAMS:
                onResponseForLevelStreams(response);
                break;
            case Constants.TAG_LOAD_COUNTRIES:
                onResponseForCountries(response);
                break;
            case Constants.TAG_LOAD_STATES:
                onResponseForStates(response);
                break;
            case Constants.TAG_LOAD_CITIES:
                onResponseForCities(response);
                break;
            case Constants.SET_SELECTED_COURSE:
               this.onCourseSelected(response);
                break;
            case Constants.SET_PREFERRED_STREAM:
                this.onPreferredStreamSelected(response);
                break;
            case Constants.TAG_SUBMIT_EDITED_EXAMS_LIST:
               // this.requestForUserProfileUpdate(Constants.TAG_UPDATE_PROFILE_EXAMS, null);
                this.onUserExamsEditedResponse(response);
                break;
            case Constants.TAG_UPDATE_PROFILE_OBJECT:
            case Constants.TAG_UPDATE_USER_PROFILE:
            case Constants.TAG_UPDATE_PROFILE_EXAMS:
                profileSuccessfullyUpdated(tag,response);
                break;
            case Constants.TAG_LOAD_PROFILE:
                this.mParseProfileResponse(response);
                break;
            case TAG_REFRESH_PROFILE:
                this.mParseAndRefreshProfileResponse(response);
                break;
            case PROFILE_IMAGE_UPLOADING:
                onProfileImageUploaded(response);
                break;
            case Constants.TAG_EXAM_SUMMARY:
                this.mUpdateExamDetail(response, true);
                break;
            case Constants.TAG_UPDATE_COUNTRIES:
                this.mDisplaySpecificCourseFragment(response);
                break;
            case Constants.TAG_LOAD_STREAM:
                this.mDisplayStreams(response, true);
                break;
            case Constants.WIDGET_SHORTLIST_INSTITUTES:
                this.mCurrentTitle = "Shortlist Institutes";
                Constants.IS_RECOMENDED_COLLEGE = false;
                //this.mDisplayInstituteList(response, false, true, Constants.SHORTLIST_TYPE);
                this.mDisplayWishlistInstituteList(response, false, true);
                break;
            case Constants.WIDGET_INSTITUTES:
                this.mCurrentTitle = "Institutes";
                Constants.IS_RECOMENDED_COLLEGE = false;
                this.mDisplayInstituteList(response, true, true);
                break;
            case Constants.WIDGET_INSTITUTES_SBS:
                this.mCurrentTitle = "Institutes";
                Constants.IS_RECOMENDED_COLLEGE = false;
                mClearBackStack();
                mShowAppBarLayout();

                if(!IS_HOME_LOADED ){
                    IS_HOME_LOADED = true;
                    getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.USER_HOME_LOADED), true).apply();
                    isFromNotification = true;
                }
                this.mDisplayInstituteList(response, true, true);
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                this.requestForProfile(null);
                break;

            case Constants.WIDGET_TRENDING_INSTITUTES:
                this.mCurrentTitle = "Featured Colleges";
                Constants.IS_RECOMENDED_COLLEGE = false;
                this.mDisplayInstituteList(response, false, true);
                break;
            case Constants.TAKE_ME_TO_RECOMMENDED:
                isFromNotification = true;
                this.mCurrentTitle = "Recommended Institutes";
                mClearBackStack();
                // mShowAppBarLayout();
                Constants.IS_RECOMENDED_COLLEGE = true;
                this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.RECOMMENDED, false);
                break;

            case Constants.WIDGET_RECOMMENDED_INSTITUTES:
                this.mCurrentTitle = "Recommended Institutes";
                Constants.IS_RECOMENDED_COLLEGE = true;
                if (tags.length == 2 && tags[1] != null && tags[1].equals("next")) {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.RECOMMENDED, true);
                } else {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.RECOMMENDED, false);
                }
                break;
            case Constants.WIDGET_RECOMMENDED_INSTITUTES_FRON_PROFILE:
                mClearBackStack();
                this.mCurrentTitle = "Recommended Institutes";
                Constants.IS_RECOMENDED_COLLEGE = true;
                if (tags.length == 2 && tags[1] != null && tags[1].equals("next")) {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.RECOMMENDED, true);
                } else {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.RECOMMENDED, false);
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        isFromNotification = true;
                        Constants.READY_TO_CLOSE = false;
                    }
                });
                break;
            case Constants.CARD_SHORTLIST_INSTITUTES:
                this.mCurrentTitle = "Shortlisted Institutes";
                Constants.IS_RECOMENDED_COLLEGE = false;
                this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.SHORTLIST, false);
                break;
            case Constants.CARD_BUZZLIST_INSTITUTES:
                this.mCurrentTitle = "Featured Colleges";
                Constants.IS_RECOMENDED_COLLEGE = false;
                if (tags.length == 2 && tags[1] != null && tags[1].equals("next")) {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.FEATURED, true);

                } else {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.FEATURED, false);
                }
                break;
            case Constants.TAG_NEXT_WISHLIST_INSTITUTE:
                this.updateNextInstituteList(response, Constants.WISH_LIST_TYPE);
                break;
            case Constants.TAG_LAST_SHORTLIST_INSTITUTES_WHILE_REMOVING:
                this.updateLastInstituteList(response);
                break;
            case Constants.TAG_NEXT_INSTITUTE:
                this.updateNextInstituteList(response, Constants.INSTITUTE_TYPE);
                break;
            case Constants.TAG_NEXT_NEWS:
                this.updateNextNewsList(response);
                break;
            case Constants.TAG_NEXT_ARTICLES:
                this.updateNextArticlesList(response);
                break;
            case Constants.TAG_NEXT_QNA_LIST:
                this.updateNextQnaList(response);
                break;
            case Constants.TAG_NEXT_FORUMS_LIST:
                this.updateNextForumsList(response);
                break;
            case Constants.SEARCHED_INSTITUTES:
                this.mCurrentTitle = "Institutes";
                Constants.IS_RECOMENDED_COLLEGE = false;
                this.mDisplayInstituteList(response, true, true);
                break;
            case Constants.WIDGET_NEWS:
                this.mDisplayNews(response);
                break;
            case Constants.WIDGET_ARTICES:
                this.mDisplayArticles(response);
                break;
            case Constants.TAG_LOAD_QNA_QUESTIONS:
                this.mQnAQuestions.clear();
                this.mShowQnAQuestions(response);
                break;
            case Constants.WIDGET_FORUMS:
                this.mShowMyFBEnumeration(response);
                break;
            case Constants.REFRESH_CHATROOM:
                this.mRefreshChatRoom(response);
                break;
            case Constants.TAG_LOAD_COURSES:
                this.mUpdateCourses(response);
                break;
            case Constants.TAG_LOAD_INSTITUTE_NEWS:
                this.mUpdateInstituteNews(response);
                break;
            case Constants.TAG_LOAD_INSTITUTE_ARTICLE:
                this.mUpdateInstituteArticle(response);
                break;
            case Constants.TAG_INSTITUTE_FORUM:
                this.mUpdateInstituteForum(response);
                break;
            case Constants.TAG_LOAD_INSTITUTE_QNA_QUESTIONS:
                this.mInstituteQnAUpdated(response);
                break;
            case Constants.TAG_APPLIED_COURSE:
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                this.mUpdateAppliedCourses(response);
                mInvalidateFeedList();
                break;
            case Constants.TAG_WISH_LIST_APPLIED_COURSE:
                Utils.DisplayToastShort(this, getString(R.string.applied_successfully));
                mInvalidateFeedList();
                if (tags.length > 1)
                    this.mUpdateAppliedInstituteWishlist(Integer.parseInt(tags[1]));
                break;
            case Constants.TAG_POST_QUESTION:
                this.mInstituteQnAQuestionAdded(response);
                break;
            case Constants.TAG_LOAD_FILTERS:
                Log.e(TAG_LOAD_FILTERS,"response");
//                this.updateFilterList(response);
                break;
            case Constants.TAG_SHORTLIST_INSTITUTE:
                if (tags.length == 2)
                    extraTag = tags[1];
                this.updateShortlistInstitute(response, extraTag);
                this.mInvalidateFeedList();
                break;
            case Constants.TAG_DELETESHORTLIST_INSTITUTE:
                if (tags.length == 2)
                    extraTag = tags[1];
                this.updateShortlistInstitute(null, extraTag);
                break;
            case Constants.CARD_DELETE_SHORTLISTED_INSTITUTE:
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                if (tags.length == 2 ){
                    /*if (tags[1].equals("true") && next!=null)
                        onNextWishList();
                    else*/
                    RemoveInstituteFromWishlist(Integer.parseInt(tags[1]));
                }
                break;
            case Constants.TAG_INSTITUTE_LIKE_DISLIKE:
                if (tags.length == 2)
                    extraTag = tags[1];
                if (tags.length == 3) {
                    extraTag = tags[1];
                    like = tags[2];
                }
                this.updateLikeButton(response, extraTag, Integer.parseInt(like));
                break;
            case Constants.TAG_QUESTION_LIKE_DISLIKE:
                if (tags.length == 3) {
                    extraTag = tags[1];
                    like = tags[2];
                    this.updateQuestionLikeButton(Integer.parseInt(extraTag), Integer.parseInt(like));
                }
                break;

            case Constants.ACTION_VOTE_QNA_QUESTION_ENTITY:
                if (tags.length == 3) {
                    this.mQnAQuestionVoteUpdated(Integer.parseInt(tags[1]),  Integer.parseInt(tags[2]));
                }
                break;
            case Constants.ACTION_VOTE_QNA_ANSWER_ENTITY:
                if (tags.length > 3) {
                    parentIndex = tags[1];
                    childIndex = tags[2];
                    voteType = Integer.parseInt(tags[3]);

                    this.mQnAAnswerVoteUpdated(Integer.parseInt(parentIndex), Integer.parseInt(childIndex), voteType);

                    HashMap<String, Object> eventValue1 = new HashMap<>();
                    eventValue1.put(Constants.TAG_USER_LOGIN, "Answer Voted : " + String.valueOf(voteType));
                    SendAppEvent(getString(R.string.CATEGORY_QNA), Constants.ACTION_VOTE_QNA_ANSWER_ENTITY,eventValue1, this );
                }
                break;
            case Constants.TAG_LOAD_MY_FB:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    this.mShowMyFB(response, Integer.parseInt(parentIndex), Integer.parseInt(childIndex));
                }
                break;
            case Constants.TAG_LOAD_MORE_FB_COMMENT:
                this.mLoadPreviousComments(response);
                break;
            case Constants.TAG_REFRESH_MY_FB:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    this.mUpdateMyFB(response, Integer.parseInt(parentIndex), Integer.parseInt(childIndex));
                }
                break;
            case Constants.TAG_COUNSELOR_REFRESH:
                if (tags.length > 1) {
                    childIndex = tags[2];
                    this.mUpdateCounselorChat(response,  Integer.parseInt(childIndex));
                }
                break;
            case Constants.ACTION_QNA_ANSWER_SUBMITTED:
                this.mOnAnswerAdded(response);
                break;
            case Constants.ACTION_MY_FB_COMMENT_SUBMITTED:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    this.mOnMyFBCommentAdded(response, Integer.parseInt(parentIndex), Integer.parseInt(childIndex));
                }
                break;
            case Constants.TAG_REQUEST_FOR_SPECIALIZATION:
                if (tags.length > 1)
                    updateUserSpecializationList(tags[1], response);
                break;
            case Constants.TAG_REQUEST_FOR_DEGREES:
                if (tags.length > 1)
                    updateUserDegreesList(tags[1], response);
                break;
            case Constants.TAG_REQUEST_FOR_EXAMS:
               // DataBaseHelper.getInstance(this).deleteAllExamSummary();
                this.onResponseUserExamsList(response);
                break;
            case Constants.TAG_UPDATE_STREAM:
                this.mDisplayStreams(response, true);
                break;
            case Constants.TAG_UPDATE_INSTITUTES:
                this.mParseInstituteListResponse(response);
                break;
            case Constants.WIDGET_SYLLABUS:
                this.mDisplayExamSyllabusFragment(response);
                break;
            case Constants.WIDGET_TEST_CALENDAR:
                this.onTestCalendarResponse(response);
                break;
            case Constants.TAG_PSYCHOMETRIC_RESPONSE:
                if(tags[1] != null) {
                    DataBaseHelper.getInstance(this).deleteExamSummary(Integer.parseInt(tags[1]));
                }
                String examId = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getString(Constants.SELECTED_EXAM_ID, "");
                if (!examId.isEmpty()) {
                    hideProgressDialog=false;
                    this.mMakeNetworkCall(Constants.WIDGET_TEST_CALENDAR, ApiEndPonits.BASE_URL + "yearly-exams/" + examId + "/calendar/", null);
                }
                break;
            case Constants.TAG_NAME_UPDATED:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    this.onNameUpdatedResponse(response, parentIndex);
                }
                break;
            case Constants.TAG_PSYCHOMETRIC_QUESTIONS:
                this.onPsychometricTestResponse(response);
                break;
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM:
                this.mDisplayStreamsSelection(response);
                break;
            case Constants.TAG_LOAD_STEP_BY_STEP:
            case Constants.TAG_LOAD_STEP_BY_STEP_FROM_PROFILE_BUILDING:
                this.mDisplayStepByStepQuestion(response);
                break;
            case Constants.TAG_SUBMIT_SBS_EXAM:
                this.mStepByStepDone(response);
                break;
            case Constants.TAG_MY_ALERTS:
                this.onMyAlertsLoaded(response);
                break;
            case Constants.PNS_FORUM:
                this.onPnsMyFutureBuddy(response);
                break;
            case Constants.PNS_NEWS:
                this.showNewsByNotification(response);
                break;
            case Constants.PNS_QNA:
                this.showQuestionByNotification(response);
                break;
            case Constants.PNS_INSTITUTES:
                this.onPnsInstituteNews(response);
                break;
            case Constants.TAG_INSTITUTE_DETAILS:
                this.onInstituteDetailsLinkResponse(response);
                break;
            case Constants.PNS_ARTICLES:
                this.showArticleByNotification(response);
                break;
            case Constants.SEARCH_INSTITUTES:
                this.onInstituteSearchResult(response);
                break;
            case Constants.SEARCH_ARTICLES:
                this.onArticleSearchResult(response);
                break;
            case Constants.SEARCH_QNA:
                this.onQnASearchResult(response);
                break;
            case Constants.SEARCH_NEWS:
                this.onNewsSearchResult(response);
                break;
            case Constants.SEARCH_COURSES:
                this.onCoursesSearchResult(response);
                break;
            case Constants.TAG_UPDATE_VIDEO_TITLE:
                this.onUpdateTitleResponse(response);
                break;
            case Constants.TAG_RECOMMENDED_SHORTLIST_INSTITUTE:
                //Refresh Feed
                this.mInvalidateFeedList();
                this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.SHORTLIST);
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                hideProgressDialog = updateCDRecommendedFragment(tags);
                refreshCountOnShortlist(tags[2]);
                updateCountInCDRecommendedFragment();
                break;
            case Constants.TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE:
                //Refresh Feed
                this.mInvalidateFeedList();
                this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.NOT_INTERESTED);
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                hideProgressDialog = updateCDRecommendedFragment(tags);
                refreshCountOnNotInterested(tags[2]);
                updateCountInCDRecommendedFragment();
                break;
            case Constants.TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE:
                //Refresh Feed
                this.mInvalidateFeedList();
                this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.UNDECIDED);
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                hideProgressDialog = updateCDRecommendedFragment(tags);
                refreshCountOnDecideLater(tags[2]);
                updateCountInCDRecommendedFragment();
                break;
            case Constants.TAG_RECOMMENDED_APPLIED_SHORTLIST_INSTITUTE:
//                this.mUpdateUndecidedCount(this.mUndecidedInstitutesCount, true);
//                mShortListInstituteCount++;
//                this.mUpdateShortListCount(mShortListInstituteCount,true);
                if (tags.length == 4) {
                    hideProgressDialog = updateCDRecommendedFragment(tags);
                    refreshCountOnApplied(tags[2]);
                } else {
                    mShortListInstituteCount++;
                }
                updateCountInCDRecommendedFragment();
                break;
            case Constants.TAG_LOAD_UNDECIDED_INSTITUTE:
                this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.UNDECIDED, true);
                break;

            case Constants.TAG_LOAD_BUZZLIST_INSTITUTE:
                this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.FEATURED, true);
                break;

            case Constants.TAG_USER_EXAMS_DELETE:
                MainActivity.mProfile.setYearly_exams(new ArrayList<ProfileExam>());
                break;

            case Constants.TAG_LOAD_FEED:
                this.mDisplayFeedFragment(response);
                break;
            case Constants.TAG_NEXT_FEED:
                this.updateNextFeedList(response);
                break;
            case Constants.TAG_REFRESHED_FEED:
                this.mFeedRefreshed(response);
                break;
            case Constants.TAG_LOAD_COUNSELOR_CHAT:
                this.mLoadCounselorChat(response);
                break;
            case Constants.PNS_COUNSELOR_CHAT:
                this.mLoadPNSCounselorChat(response);
                break;
            case Constants.TAG_SIMILAR_QUESTIONS:
                parseSimilarQuestions(response);
                break;
            case AllEvents.ACTION_ANSWER_FOR_QUESTION:
                onResponseAnswerForQuestion(response);
                break;
            case AllEvents.ACTION_PROFILE_COMPLETION_CLICK:
                onProfileCompletionResponse(response);
                break;
            case Constants.TAG_FEED_ACTION:
                this.mHandleFeedAction(tag, response);
                break;
           
            case Constants.TAG_LOAD_INSTITUTE:
                try {
                    Institute  institute = JSON.std.beanFrom(Institute.class, response);
                    this.mDisplayInstituteByEntity(institute, Constants.CDRecommendedInstituteType.RECOMMENDED);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Constants.TAG_RESOLVE_DEEPLINK_URL:
            {
                try {
                    JSONObject resObject = new JSONObject(response);
                    String web_resource_url = resObject.getString("web_resource_url");
                    this.resource_uri_with_notification_id = this.resource_uri = resObject.getString("resource_uri");
                    this.type = resObject.getString("screen");

                    this.mHandleNotifications();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if(hideProgressDialog)
                hideProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     * This method takes in response for course search
     * results and displays list of Courses in CourseSelectionFragment
     *
     * @param String response
     * @return void
     */
    private void onCoursesSearchResult(String response) {
        String val = this.extractResults(response);
        try {
            List<Courses> coursesList = JSON.std.listOfFrom(Courses.class, val);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(CourseSelectionFragment.class.getSimpleName());
            if (fragment == null) {
                this.mDisplayFragment(CourseSelectionFragment.newInstance((ArrayList<Courses>) coursesList, next), true, CourseSelectionFragment.class.getSimpleName());
            }else if (currentFragment instanceof CourseSelectionFragment){
                ((CourseSelectionFragment)fragment).updateCourses(coursesList, next);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method is to invalidate the feed list. For example,
     * in case of some recommended institute is marked with
     * some status in Recommended Institutes Fragment,
     * we need to remove that from reco-feed list in feed fragment.
     */
    private void mInvalidateFeedList()
    {
        //Save in SP
        this.getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean(getString(R.string.USER_FEED_INVALIDATED), true).apply();
    }

    private void mHandleFeedAction(String tag, String response) {
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        String[] tags = tag.split("#");
        switch (tags[1])
        {
            case Constants.RECOMMENDED_INSTITUTE_FEED_LIST:
            {
                switch (tags[2])
                {
                    case Constants.FEED_RECO_ACTION:
                        HashMap<String, String> dataMap = new HashMap<>();
                        if (MainActivity.currentFragment instanceof HomeFragment)
                        {
                            dataMap.put("feedActionType", Constants.FEED_RECO_ACTION);
                            dataMap.put("action", tags[3]);
                            dataMap.put("feedPosition", tags[4]);
                            dataMap.put("position",  tags[5]);
                            dataMap.put("id", tags[6]);

                            ((HomeFragment) MainActivity.currentFragment).feedAction(Constants.RECOMMENDED_INSTITUTE_FEED_LIST, dataMap);

                            try
                            {
                                if (tags[3].equals(Constants.CDRecommendedInstituteType.NOT_INTERESTED.toString()))
                                    this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.NOT_INTERESTED);
                                else if (tags[3].equals(Constants.CDRecommendedInstituteType.SHORTLIST.toString()))
                                    this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.SHORTLIST);
                                else if (tags[3].equals(Constants.CDRecommendedInstituteType.UNDECIDED.toString()))
                                    this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.UNDECIDED);
                            }
                            catch (Exception e) {
                                Log.e(TAG, "exception occured while loading feed Reco");
                            }
                        }
                        break;
                    case Constants.FEED_SHARE_ACTION:
                        break;
                }
                break;
            }

        }
    }

    private void onResponseForSubLevels(String responseJson) {
        String resultJson = extractResults(responseJson);
        try {
            ArrayList<SubLevel> subLevelList = (ArrayList<SubLevel>) JSON.std.listOfFrom(SubLevel.class, resultJson);
            if(currentFragment instanceof LevelSelectionFragment){
                ((LevelSelectionFragment) currentFragment).mSubLevelsResponseCompleted(subLevelList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserCountries(String countries) {
        HashMap<String,String> params = new HashMap<>();
        params.put("preferred_countries_ids",countries);
        requestForUserProfileUpdate(Constants.TAG_UPDATE_COUNTRIES,params);
    }

    private void onResponseForCountries(String responseJson) {

        try {
            if(currentFragment instanceof CountrySelectionFragment){
                List<Country> countryList = countryList = mParseCountries(responseJson);
                ArrayList<Country> myCountryList = new ArrayList<>();
                myCountryList.addAll(countryList);
                ((CountrySelectionFragment) currentFragment).mCountriesResponseCompleted(myCountryList);
            }else if(currentFragment instanceof  ProfileFragment){
                ArrayList<ProfileSpinnerItem> countryList = (ArrayList<ProfileSpinnerItem>) JSON.std.listOfFrom(ProfileSpinnerItem.class, responseJson);
                ((ProfileFragment) currentFragment).countriesResponseCompleted(countryList);
            } else {
                mDisplayCountrySelectionFragment(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("current_level_id", String.valueOf(MainActivity.mProfile.getCurrent_level_id()));
        params.put("current_sublevel_id", String.valueOf(MainActivity.mProfile.getCurrent_sublevel_id()));
        params.put("preferred_level", String.valueOf(MainActivity.mProfile.getPreferred_level()));
        params.put("current_score_type", String.valueOf(ProfileMacro.PERCENTAGE));
        requestForProfile(params);

    }

    private void onResponseForStates(String responseJson) {
        try {
            if(currentFragment instanceof  ProfileFragment){
                ArrayList<ProfileSpinnerItem> statesList = (ArrayList<ProfileSpinnerItem>) JSON.std.listOfFrom(ProfileSpinnerItem.class, responseJson);
                ((ProfileFragment) currentFragment).statesResponseCompleted(statesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onResponseForCities(String responseJson) {
        try {
            if(currentFragment instanceof  ProfileFragment){
                ArrayList<ProfileSpinnerItem> citiesList = (ArrayList<ProfileSpinnerItem>) JSON.std.listOfFrom(ProfileSpinnerItem.class, responseJson);
                ((ProfileFragment) currentFragment).citiesResponseCompleted(citiesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Country> mParseCountries(String response)
    {
        List<Country> countriesList = new ArrayList<>();

        //Since Jackson parser was not able to parse escaped json, stored as value in the feed item
        //Its a shim to get results in the feed
        HashMap<Integer, String> idResultHashMap = new HashMap<>();
        try {
            response = "{ \"results\" : "+ response + "}";
            Map<String, Object> map = JSON.std.mapFrom(response);
            String results = JSON.std.asString(map.get("results"));
//            countriesList = JSON.std.listOfFrom(Country.class, results);

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("results"))
            {
                JSONArray resultsJsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < resultsJsonArray.length(); i++)
                {
                    JSONObject countryObject = (JSONObject) resultsJsonArray.get(i);
                    Log.e("JSON-Countries",countryObject.get("name").toString());
                    Country newCountry = new Country();
                    newCountry.id = countryObject.getInt("id");
                    newCountry.name = countryObject.getString("name");
                    newCountry.flag_image = countryObject.getString("flag_image");
                    newCountry.alpha2_code = countryObject.getString("alpha3_code");
                    newCountry.alpha3_code = countryObject.getString("alpha2_code");
                    newCountry.image = countryObject.getString("image");
                    newCountry.institute_count = countryObject.getInt("institute_count");
                    countriesList.add(newCountry);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return (ArrayList<Country>) countriesList;
    }

    private void onResponseForLevelStreams(String responseJson) {

        String resultJson = extractResults(responseJson);
        if(currentFragment instanceof PrefStreamSelectionFragment)
        {
            try {
                ArrayList<ProfileSpinnerItem> streamList = (ArrayList<ProfileSpinnerItem>) JSON.std.listOfFrom(ProfileSpinnerItem.class, resultJson);
                mDisplayPrefStreamSelectionFragment(streamList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("current_level_id", String.valueOf(MainActivity.mProfile.getCurrent_level_id()));
        params.put("current_sublevel_id", String.valueOf(MainActivity.mProfile.getCurrent_sublevel_id()));
        params.put("preferred_level", String.valueOf(MainActivity.mProfile.getPreferred_level()));
        params.put("current_score_type", String.valueOf(ProfileMacro.PERCENTAGE));
        requestForProfile(params);

    }


    private boolean updateCDRecommendedFragment(String[] tags){
        String parentIndex;
        boolean hideProgressDialog = true;
        if (tags.length == 4) {
            parentIndex = tags[1];
            if (parentIndex.equals("true")) {
                try {
                    hideProgressDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideProgressDialog = false;
                parentIndex = tags[2];
                switch (parentIndex) {
                    case Constants.TAG_CD_RECOMMENDED:
                        this.OnCDRecommendedLoadNext();
                        break;
                    case Constants.TAG_CD_FEATURED:
                        this.OnCDRecommendedLoadMoreBuzzlist();
                        break;
                    case Constants.TAG_CD_UNDECIDED:
                        if (tags[3] != null && !tags[3].equalsIgnoreCase("null")) {
                            this.OnCDRecommendedLoadUndecidedInstitutes(tags[3]);
                        } else {
                            this.OnCDRecommendedLoadUndecidedInstitutes(ApiEndPonits.API_UNDECIDED_INSTITUTES);
                        }
                        break;
                }
            }
        }
        return hideProgressDialog;
    }

    private void refreshCountOnShortlist(String tag){
        switch (tag) {
            case Constants.TAG_CD_RECOMMENDED:
                mRecommendedInstituteCount--;
                break;
            case Constants.TAG_CD_FEATURED:
                mFeaturedInstituteCount--;
                break;
            case Constants.TAG_CD_UNDECIDED:
                this.mUndecidedInstitutesCount--;
                break;
        }
        mShortListInstituteCount++;
    }

    private void refreshCountOnNotInterested(String tag){
        switch (tag) {
            case Constants.TAG_CD_RECOMMENDED:
                mRecommendedInstituteCount--;
                break;
            case Constants.TAG_CD_FEATURED:
                mFeaturedInstituteCount--;
                break;
            case Constants.TAG_CD_UNDECIDED:
                this.mUndecidedInstitutesCount--;
                break;
        }
    }

    private void refreshCountOnDecideLater(String tag){
        switch (tag) {
            case Constants.TAG_CD_RECOMMENDED:
                mRecommendedInstituteCount--;
                this.mUndecidedInstitutesCount++;
                break;
            case Constants.TAG_CD_FEATURED:
                mFeaturedInstituteCount--;
                this.mUndecidedInstitutesCount++;
                break;
           /* case Constants.TAG_CD_UNDECIDED:

                break;*/
        }
    }

    private void refreshCountOnApplied(String tag){
        switch (tag) {
            case Constants.TAG_CD_RECOMMENDED:
                mRecommendedInstituteCount--;
                break;
            case Constants.TAG_CD_FEATURED:
                mFeaturedInstituteCount--;
                break;
            case Constants.TAG_CD_UNDECIDED:
                mUndecidedInstitutesCount--;
                break;
        }
        if(!tag.equals(Constants.TAG_CD_SHORTLIST)) {
            mShortListInstituteCount++;
        }
    }


    public int getmRecommendedInstituteCount(){
        return mRecommendedInstituteCount;
    }
    public int getUndecidedInstituteCount(){
        return mUndecidedInstitutesCount;
    }
    public int getShortlistedInstituteCount(){
        return mShortListInstituteCount;
    }
    public int getFeaturedInstituteCount(){
        return mFeaturedInstituteCount;
    }

    private void updateCountInCDRecommendedFragment() {
        if (currentFragment instanceof CDRecommendedInstituteFragment) {
            ((CDRecommendedInstituteFragment) currentFragment).updateCount(this.mUndecidedInstitutesCount, this.mShortListInstituteCount, this.mFeaturedInstituteCount, this.mRecommendedInstituteCount);
        }
    }

    /**
     *  Decide which fragment will open based on User splash login action
     * @param response server response
     * @param tag tag which action is taken by user on splash login screen
     */

    private void mSplashLoginSuccessfully(String response, String tag){
        mUserCreatedSuccessfully(response, tag);
        switch (tag) {
            case Constants.TAG_SPLASH_LOGIN_PROCEED:
                mDisplayLevelSelectionFragment();
                break;
            default:
                mLoadUserStatusScreen();
                break;
        }
    }
    /**
     *  handle response when user login by phone number or true caller
     * @param response profile json from server response
     * @param tag  which action is taken by user on login screen
     */

    private void mProfileLoginSuccessfully(String response, String tag){
        Map<String, Object> responseMap = null;
        try {
            responseMap = JSON.std.mapFrom(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responseMap != null && responseMap.containsKey("verified")) {
            if (currentFragment instanceof LoginFragment) {
                ((LoginFragment) currentFragment).onInvalidOtp();
            } else if (currentFragment instanceof PostAnonymousLoginFragment) {
                ((PostAnonymousLoginFragment) currentFragment).onInvalidOtp();
            }else if (currentFragment instanceof LoginForCounselorFragment) {
                ((LoginForCounselorFragment) currentFragment).onInvalidOtp();
            }else if (currentFragment instanceof OTPVerificationFragment) {
                ((OTPVerificationFragment) currentFragment).onInvalidOtp();
            }
        }else{
            mUserCreatedSuccessfully(response, tag);
            if (currentFragment instanceof LoginForCounselorFragment) {
                if(currentFragment.isAdded()) {
                    onBackPressed();
                }
                hasNextDeferredFunction();
                return;

            }else if(currentFragment instanceof OTPVerificationFragment){
                if(currentFragment.isAdded()) {
                    onBackPressed();
                }
                return;
            }
            // load user status screen
            this.mLoadUserStatusScreen();
        }
    }

    /**
     * This method is called when user login with true sdk , facebook, phone number
     * or skip option then user response is parse and user is created and saved in
     * shared pref . and load user status screens
     * and resp
     * @param response response form profile api
     */
    private void mUserCreatedSuccessfully(String response, String tag) {
        // parse user response and  create a user
        this.mParseProfileResponse(response);
        // if user creation is not successful then
        // request again for user creation
        if(MainActivity.mProfile == null){
            onRequestForUserCreation();
        }
        // set token in network util
        mNetworkUtils.setToken(MainActivity.mProfile.getToken());
        // set user ids with apps flyer , crashlytics, GA
        this.setUserIdWithAllEvents();

        if (!IS_USER_CREATED) {
            //Events
            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(getString(R.string.ACTION_USER_PROFILE_CREATED), HomeFragment.class.getSimpleName());
            SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_USER_PROFILE_CREATED), eventValue, this);

        }

        this.getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean(getString(R.string.USER_CREATED), true).apply();
        IS_USER_CREATED = true;

        HashMap<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.TAG_USER_LOGIN, tag);
        SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_USER_LOGIN), eventValue, this);

        mRegisterWithFCM();
    }

    private void mRegisterWithFCM(){
        String token = getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).getString(getString(R.string.FCM_TOKEN), null);
        if (token != null && !token.isEmpty())
        {
            String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            HashMap<String, String> params = (HashMap<String, String>) Utils.GetDeviceInfo(this);
            params.put(getApplicationContext().getString(R.string.USER_DEVICE_ID), deviceId);
            params.put(getApplicationContext().getString(R.string.USER_FCM_REGISTRATION_ID), token);
            params.put(getApplicationContext().getString(R.string.USER_APP_SOURCE), String.valueOf(Constants.SOURCE_COLLEGE_DEKHO_APP));

            this.mMakeNetworkCall(Constants.TAG_FCM_TOKEN_SYNC, ApiEndPonits.API_REGISTER_DEVICE, params, Request.Method.POST);
        }
    }

    private void mUpdateAppliedInstituteWishlist(int i)
    {
        this.mInstitute.setIs_applied(true);

        if (currentFragment instanceof WishlistFragment)
            ((WishlistFragment) currentFragment).UpdateAppliedStatus(i);
        else if(currentFragment instanceof CDRecommendedInstituteFragment)
            ((CDRecommendedInstituteFragment) currentFragment).UpdateAppliedStatus(i);
        else if (currentFragment instanceof InstituteDetailFragment && i < 0)
            ((InstituteDetailFragment) currentFragment).OnInstituteApplied();
    }

    private void RemoveInstituteFromWishlist(int i)
    {
        this.mInstitute.setIs_shortlisted(0);

        if (currentFragment instanceof WishlistFragment) {
            this.mInstitute.setPosition(-1);
            ((WishlistFragment) currentFragment).RemoveInstitute(i);
        } else if(currentFragment instanceof CDRecommendedInstituteFragment) {
            this.mInstitute.setPosition(-1);
            this.mShortListInstituteCount--;
            ((CDRecommendedInstituteFragment) currentFragment).removeInstituteFromShortList(i);
        } else if(currentFragment instanceof InstituteDetailFragment && i < 0)
            ((InstituteDetailFragment) currentFragment).OnInstituteRemoved();
    }

    private synchronized void mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType type) {
        //Events
        if (this.mInstitute == null)
            return;

        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.INSTITUTE_RESOURCE_URI), this.mInstitute.getResource_uri());
        eventValue.put(Constants.INSTITUTE_ID, String.valueOf(this.mInstitute.getId()));

        switch (type) {
            case UNDECIDED:
                eventValue.put(Constants.CD_RECOMMENDED_INSTITUTE_ACTION_TYPE, Constants.CDRecommendedInstituteType.UNDECIDED.toString());
                break;
            case SHORTLIST:
                eventValue.put(Constants.CD_RECOMMENDED_INSTITUTE_ACTION_TYPE, Constants.CDRecommendedInstituteType.SHORTLIST.toString());
                break;
            case NOT_INTERESTED:
                eventValue.put(Constants.CD_RECOMMENDED_INSTITUTE_ACTION_TYPE, Constants.CDRecommendedInstituteType.NOT_INTERESTED.toString());
                break;
        }
        //Events
        SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_CD_RECOMMENDED_INSTITUTE_ACTION), eventValue, this);
    }

    public void mDisplaySpecificCourseFragment(String response) {
        if(response != null && response.length()>0)
        {
            mParseProfileResponse(response);
        }
        String tag = SpecificCourseFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment ==  null){
            fragment = SpecificCourseFragment.newInstance();
        }
        mDisplayFragment(fragment, false, tag);
    }

    public void mDisplayPrefStreamSelectionFragment(ArrayList<ProfileSpinnerItem> streamList) {
        String tag = PrefStreamSelectionFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment ==  null){
            fragment = PrefStreamSelectionFragment.newInstance(streamList);
        }else{
            ((PrefStreamSelectionFragment)fragment).updateStreamList(streamList);
        }
        mDisplayFragment(fragment, false, tag);
    }

    private void mDisplayLevelSelectionFragment() {
        String tag = LevelSelectionFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment ==  null){
            fragment = LevelSelectionFragment.newInstance();
        }
        mDisplayFragment(fragment, false, tag);
    }

    private void mDisplayCountrySelectionFragment(boolean addToBackStack ) {
        String tag = CountrySelectionFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment ==  null){
            fragment = CountrySelectionFragment.newInstance();
        }
        mDisplayFragment(fragment, addToBackStack, tag);
    }

    private void mDisplayLoginFragment() {
        String tag = LoginFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        boolean addToBackStack = false;
        if(fragment ==  null){
            fragment = LoginFragment.newInstance();
            addToBackStack = true;
        }
        mDisplayFragment(fragment, addToBackStack, tag);
    }

    /**
     * If user is not preparing for any exam then user will move to not preparing fragment
     */
    private void mDisplayNotPreparingFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(NotPreparingFragment.class.getSimpleName());
        boolean addToBackStack = false;
        if(fragment == null) {
            fragment = NotPreparingFragment.newInstance();
            addToBackStack = true;
        }
        this.mDisplayFragment(fragment, addToBackStack,NotPreparingFragment.class.getSimpleName());
    }

    /**
     * This method is used to display step by step questions
     * @param response server response  to display
     */
    private void mDisplayStepByStepQuestion(String response){
        try {
            //set current level
            int currentLevel = mProfile.getCurrent_level_id();
            if(currentLevel != 0) {
                if (currentLevel == ProfileMacro.LEVEL_TWELFTH || currentLevel == ProfileMacro.LEVEL_TENTH) {
                    StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.IN_SCHOOL);
                } else if (currentLevel == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                    StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.GRADUATE_COLLEGE);
                } else {
                    StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.POSTGRADUATE_COLLEGE);
                }
            }

            response = response.substring(13, response.length() - 1);
            ArrayList<StepByStepQuestion> stepByStepQuestions = (ArrayList<StepByStepQuestion>) JSON.std.listOfFrom(StepByStepQuestion.class, response);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(StepByStepFragment.class.getSimpleName());

            if (fragment == null) {
                StepByStepFragment stepByStepFragment = StepByStepFragment.newInstance(stepByStepQuestions);
                this.mDisplayFragment(stepByStepFragment, true, StepByStepFragment.class.getSimpleName());
            } else {
                if (fragment instanceof StepByStepFragment)
                    ((StepByStepFragment) fragment).updateQuestionSet(stepByStepQuestions);

                this.mDisplayFragment(fragment, false, StepByStepFragment.class.getSimpleName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to display step by step results
     *
     * @param response server response to parse Step by Step result
     */
    private void mStepByStepDone(String response) {
        try {
            //Since after this all the filters are modified in all probabilities.
            StepByStepResult sbsResult = JSON.std.beanFrom(StepByStepResult.class, response);
            if(sbsResult != null && sbsResult.getTags() != null){
                int count = sbsResult.getTags().size();
                Map<String, String> filterKeywords = new HashMap<>();
                for (int n = 0; n < count; n++)
                    filterKeywords.put("tag_uris[" + (n) + "]", sbsResult.getTags().get(n));

                this.mFilterKeywords = filterKeywords;
                this.mFilterCount = this.mFilterKeywords.size();
            }

            //save preferences.
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit();
            editor.putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString());
            editor.apply();

            MainActivity.mProfile.setStep_by_step_given(1);
            //move to profile
            this.onHomeItemSelected(Constants.WIDGET_INSTITUTES_SBS, ApiEndPonits.API_PERSONALIZE_INSTITUTES,null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to display the syllabus
     * with the context of an exam
     *
     * @param response responseJson
     */
    private void mDisplayExamSyllabusFragment(String response) {

        try {
            ArrayList<Subjects> subjectsList = (ArrayList<Subjects>) JSON.std.listOfFrom(Subjects.class, response);
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(SyllabusSubjectsListFragment.class.getSimpleName());
            if (fragment == null) {
                this.mDisplayFragment(SyllabusSubjectsListFragment.newInstance(subjectsList), !isFromNotification, SyllabusSubjectsListFragment.class.getSimpleName());
            } else {
                this.mDisplayFragment(fragment, false, SyllabusSubjectsListFragment.class.getSimpleName());
            }
        } catch (IOException e) {
            Log.v(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateQuestionLikeButton(int position , int voteType) {

        if (this.mQnAQuestions == null) return;
        QnAQuestions qnaQuestion = this.mQnAQuestions.get(position);
        if (voteType == Constants.LIKE_THING) {
            qnaQuestion.setUpvotes(qnaQuestion.getUpvotes() + 1);

            Map<String,Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_QUESTION_LIKE_DISLIKE, Constants.LIKE_THING);
            eventValue.put(qnaQuestion.getTitle(), Constants.LIKE_THING);
            eventValue.put(getString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

            //Events
            SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);

        } else  if(voteType == Constants.DISLIKE_THING) {
            qnaQuestion.setUpvotes(qnaQuestion.getDownvotes() - 1);

            Map<String,Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_QUESTION_LIKE_DISLIKE, Constants.DISLIKE_THING);
            eventValue.put(qnaQuestion.getTitle(), Constants.DISLIKE_THING);
            eventValue.put(getString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

            //Events
            SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);

        }else{
            if (qnaQuestion.getCurrent_user_vote_type() == Constants.LIKE_THING) {
                qnaQuestion.setUpvotes(qnaQuestion.getUpvotes() - 1);
                Map<String,Object> eventValue = new HashMap<>();
                eventValue.put(getString(R.string.ACTION_VOTE_QNA_QUESTION_UPVOTED), Constants.NOT_INTERESTED_THING);
                eventValue.put(qnaQuestion.getTitle(), Constants.NOT_INTERESTED_THING);
                eventValue.put(getString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

                //Events
                SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);

            } else {

                qnaQuestion.setDownvotes(qnaQuestion.getDownvotes() - 1);
                Map<String,Object> eventValue = new HashMap<>();
                eventValue.put(getString(R.string.ACTION_VOTE_QNA_QUESTION_DOWNVOTED), Constants.NOT_INTERESTED_THING);
                eventValue.put(qnaQuestion.getTitle(), Constants.NOT_INTERESTED_THING);
                eventValue.put(getString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

                //Events
                SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);
            }
        }
        qnaQuestion.setCurrent_user_vote_type(voteType);
        if (currentFragment instanceof QnAQuestionsListFragment)
            currentFragment.updateLikeButtons(position);
    }

    private void updateLikeButton(String response, String extraTag, int like) {
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        int index=Integer.parseInt(extraTag);
        if (index < 0 || index >= mInstituteList.size()) {
            return;
        }
        Institute institute = this.mInstituteList.get(index);
        if (like == Constants.NEITHER_LIKE_NOR_DISLIKE) {
            institute.setCurrent_user_vote_type(Constants.NEITHER_LIKE_NOR_DISLIKE);
            institute.setCurrent_user_vote_url(null);
            institute.setUpvotes(institute.getUpvotes() - 1);

            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
            eventValue.put(getString(R.string.VOTE_TYPE), String.valueOf(Constants.NEITHER_LIKE_NOR_DISLIKE));
            eventValue.put(Constants.TAG_INSTITUTE_LIKE_DISLIKE, String.valueOf(Constants.NEITHER_LIKE_NOR_DISLIKE));

            //Events
            SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_INSTITUTE_LIKING_UNBIASED), eventValue, this);
        } else {
            try {
                institute.setCurrent_user_vote_type(like);

                if (like == Constants.LIKE_THING) {
                    institute.setUpvotes(institute.getUpvotes() + 1);

                    Map<String, Object> eventValue = new HashMap<>();
                    eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                    eventValue.put(getString(R.string.VOTE_TYPE), String.valueOf(Constants.LIKE_THING));
                    eventValue.put(Constants.TAG_INSTITUTE_LIKE_DISLIKE, String.valueOf(Constants.LIKE_THING));

                    //Events
                    SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_INSTITUTE_LIKED), eventValue, this);
                } else if (like == Constants.DISLIKE_THING) {
                    Map<String, Object> eventValue = new HashMap<>();
                    eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                    eventValue.put(getString(R.string.VOTE_TYPE), String.valueOf(Constants.DISLIKE_THING));
                    eventValue.put(Constants.TAG_INSTITUTE_LIKE_DISLIKE, String.valueOf(Constants.DISLIKE_THING));

                    //Events
                    SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_INSTITUTE_DISLIKED), eventValue, this);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (currentFragment instanceof InstituteListFragment) {
            currentFragment.updateLikeButtons(Integer.parseInt(extraTag));
        }
    }

    private void updateShortlistInstitute(String response, String extraTag) {
        int index=Integer.parseInt(extraTag);
        if (index < 0 || index >= mInstituteList.size()) {
            return;
        }
        Institute institute = this.mInstituteList.get(index);
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        if (response == null) {
            institute.setIs_shortlisted(Constants.SHORTLISTED_NO);
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
            eventValue.put(Constants.TAG_SHORTLIST_INSTITUTE, Constants.SHORTLISTED_NO);
            eventValue.put(getString(R.string.ACTION_INSTITUTE_SHORTLISTED), Constants.SHORTLISTED_NO);
            eventValue.put(getString(R.string.INSTITUTE_RESOURCE_URI), institute.getResource_uri());

            //Events
            SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_INSTITUTE_SHORTLISTED_REMOVED), eventValue, this);
        } else {
            try {
                institute.setIs_shortlisted(Constants.SHORTLISTED_YES);
                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                eventValue.put(Constants.TAG_SHORTLIST_INSTITUTE, Constants.SHORTLISTED_YES);
                eventValue.put(getString(R.string.ACTION_INSTITUTE_SHORTLISTED), Constants.SHORTLISTED_YES);
                eventValue.put(getString(R.string.INSTITUTE_RESOURCE_URI), institute.getResource_uri());

                //Events
                SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_INSTITUTE_SHORTLISTED), eventValue, this);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (currentFragment instanceof InstituteListFragment) {
            int position = Integer.parseInt(extraTag);
            boolean isFilterAllowed = ((InstituteListFragment) currentFragment).getFilterAllowed();
            if (!isFilterAllowed && !Constants.IS_RECOMENDED_COLLEGE && currentFragment.listType != Constants.INSTITUTE_SEARCH_TYPE) {
                if (mInstituteList != null && mInstituteList.size() > position) {
                    mInstituteList.remove(position);
                }
            }
            ((InstituteListFragment) currentFragment).updateShortlistButton(Integer.parseInt(extraTag));
        }
        else if (currentFragment instanceof MyFutureBuddiesFragment){
            ((MyFutureBuddiesFragment) currentFragment).updateShortListStatus(true);
        }
        else if (currentSubFragment instanceof  MyFutureBuddiesFragment){
            ((MyFutureBuddiesFragment) currentSubFragment).updateShortListStatus(true);
        }
    }

    private void mDisplayNews(String response) {
        try {
            this.mNewsList = JSON.std.listOfFrom(News.class, extractResults(response));
            this.mParseSimilarNews(this.mNewsList);
            this.mDisplayFragment(NewsFragment.newInstance(new ArrayList<>(this.mNewsList), this.mCurrentTitle, this.next), !isFromNotification, Constants.TAG_FRAGMENT_NEWS_LIST);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mParseSimilarNews(List newsList) {
        if (newsList == null) {
            return;
        }
        for (int i = 0; i < newsList.size(); i++) {

            News news = (News) newsList.get(i);
            String tempId = news.getSimilar_news();
            if (tempId == null) continue;
            tempId = tempId.replaceAll("L", "");
            ArrayList<String> similarNewsIds = new ArrayList<>();
            try {

                JSONArray strArray = new JSONArray(tempId);
                for (int j = 0; j < strArray.length(); j++) {

                    String id = strArray.getString(j);
                    similarNewsIds.add(id);
                }
            } catch (JSONException e) {
                Log.e(MainActivity.class.getSimpleName(), e.getMessage() + e.getCause());
            }
            news.setSimilarNewsIds(similarNewsIds);
        }
    }

    private void mDisplayArticles(String response) {
        try {
            this.mArticlesList = JSON.std.listOfFrom(Articles.class, extractResults(response));
            this.mParseSimilarArticle(mArticlesList);
            this.mDisplayFragment(ArticleFragment.newInstance(new ArrayList<>(this.mArticlesList), this.mCurrentTitle, this.next), !isFromNotification, Constants.TAG_FRAGMENT_ARTICLES_LIST);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mParseSimilarArticle(List articleList) {
        if (articleList == null) {
            return;
        }
        for (int i = 0; i < articleList.size(); i++) {

            Articles article = (Articles) articleList.get(i);
            String tempId = article.getSimilar_articles();
            tempId = tempId.replaceAll("L", "");
            ArrayList<String> similarArticlesIds = new ArrayList<>();
            try {
                JSONArray strArray = new JSONArray(tempId);
                for (int j = 0; j < strArray.length(); j++) {
                    String id = strArray.getString(j);
                    similarArticlesIds.add(id);
                }
            } catch (JSONException e) {
                Log.e(MainActivity.class.getSimpleName(), e.getMessage() + e.getCause());
            }
            article.setSimilarArticlesIds(similarArticlesIds);
        }
    }

    private void mShowQnAQuestions(String response) {
        ArrayList<QnAQuestions> qnaQuestionList = parseAndReturnQnAList(response);
        this.mDisplayFragment(QnAQuestionsListFragment.newInstance(new ArrayList<>(qnaQuestionList), next), !isFromNotification, Constants.TAG_FRAGMENT_QNA_QUESTION_LIST);
    }


    private void mUpdateCourses(String response) {
        try {
            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateCourses(response);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method is used to update institute articles
     * @param response responseJson
     */
    private void mUpdateInstituteArticle(String response) {
        try {

            this.mArticlesList = JSON.std.listOfFrom(Articles.class, extractResults(response));
            this.mParseSimilarArticle(this.mArticlesList);

            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteArticle((ArrayList<Articles>) this.mArticlesList, next);
            }
        } catch (Exception e) {
            Log.e(TAG, "exception while parsing response json for articles list"+e.getMessage());
        }
    }
    /**
     * This method is used to update institute news
     * @param response responseJson
     */
    private void mUpdateInstituteNews(String response) {
        try {

            this.mNewsList = JSON.std.listOfFrom(News.class, extractResults(response));
            this.mParseSimilarNews(this.mNewsList);

            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteNews((ArrayList<News>) this.mNewsList, next);
            }
        } catch (Exception e) {
            Log.e(TAG, "exception occurred in parsing newslist");
        }
    }

    /**
     * This method is used to update institute forum
     * @param response responseJson
     */
    private void mUpdateInstituteForum(String response) {
        try {
            this.mFB = mParseAndPopulateMyFB(response,0);

            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteMyBuddy(this.mFB);
            }
        } catch (Exception e) {
            Log.e(TAG, "exception occurred in parsing newslist");
        }
    }

    private void mInstituteQnAUpdated(String response) {
        try{
            ArrayList<QnAQuestions> instituteQnas = this.parseAndReturnQnAList(response,true);
            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment)
                ((InstituteDetailFragment) currentFragment).updateQnasList(instituteQnas,this.next);
        }
        catch (Exception e)
        {
            Log.e(TAG, "exception occurred in parsing qna");
        }

    }

    private void mUpdateAppliedCourses(String response) {
        try {
            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment)
                ((InstituteDetailFragment) currentFragment).updateAppliedCourses(response);
        } catch (Exception e) {
            Log.e(TAG, "exception occurred while updating Applied course");
        }
    }

    @Override
    public void onError(final String tag, String response, int responseCode, final String url,
                        final Map<String, String> params, final int method) {
        hideProgressDialog();
        hideErrorDialog();
        if(tag.equalsIgnoreCase("next_shortlist_institutes") && responseCode == 404){
            this.mMakeNetworkCall(Constants.TAG_LAST_SHORTLIST_INSTITUTES_WHILE_REMOVING, ApiEndPonits.API_SHORTLISTED_INSTITUTES, null);
            return;
        }else  if(tag.equalsIgnoreCase(Constants.PROFILE_IMAGE_UPLOADING)){
            Utils.DisplayToast(getApplicationContext(), "Upload failed! ");
            if(currentFragment instanceof  ProfileFragment){
                ((ProfileFragment) currentFragment).deleteTempImageFile();
                ((ProfileFragment) currentFragment).updateProfileImage();

            }
            return;
        }
        else  if(tag.equalsIgnoreCase(Constants.TAG_SIMILAR_QUESTIONS)){
            if(currentFragment instanceof  QnaQuestionDetailFragmentNew)
                ((QnaQuestionDetailFragmentNew) currentFragment).updateSimilarQuestion(null);
        }
        else  if(tag.equalsIgnoreCase(Constants.REFRESH_CHATROOM)){
            if(currentFragment instanceof  MyFutureBuddiesEnumerationFragment)
                ((MyFutureBuddiesEnumerationFragment) currentFragment).chetRoomSwipRefreshfailed();
        }
        else  if(tag.equalsIgnoreCase(Constants.TAG_LOAD_BUZZLIST_INSTITUTE)){
            Utils.DisplayToast(getApplicationContext(),"LOADING FAILED");
        }
        else if(tag.equalsIgnoreCase(Constants.TAG_LOAD_BUZZLIST_INSTITUTE)){
            Utils.DisplayToast(getApplicationContext(),"LOADING FAILED");
        }else  if(tag.equalsIgnoreCase(Constants.TAG_LOAD_FEED)){
            if(currentFragment instanceof HomeFragment){
                ((HomeFragment) currentFragment).feedLoadedFailed();
            }
        }else  if(tag.equalsIgnoreCase(TAG_REFRESH_PROFILE)){
            if(currentFragment instanceof ProfileFragment){
                ((ProfileFragment) currentFragment).mRefreshProfileOnResponse(null);
            }
        }
        else  if(tag.contains(Constants.ACTION_MY_FB_COMMENT_SUBMITTED)){
            if(currentFragment instanceof MyFutureBuddiesFragment){
                ((MyFutureBuddiesFragment) currentFragment).setmSubmittingState(false);
            }
            else if(currentSubFragment instanceof MyFutureBuddiesFragment){
                ((MyFutureBuddiesFragment) currentSubFragment).setmSubmittingState(false);
            }
        }else   if(tag.equalsIgnoreCase(Constants.TAG_CREATE_USER)){
            if(!USER_CREATING_PROCESS){
                USER_CREATING_PROCESS =true;
            }else{
                mLoadUserStatusScreen();
            }
            return;
        }

        if (!MainActivity.this.isFinishing()) {
            mErrorDialog =  new AlertDialog.Builder(this)
                    .setMessage(response)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mMakeNetworkCall(tag, url, params, method);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mHandleErrorResponse(tag);
                            dialog.dismiss();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            mHandleErrorResponse(tag);
                        }
                    })
                    .show();
        }
    }

    private void mHandleErrorResponse(String tag) {
        String extraTag = null;
        String[] tags = tag.split("#");

        switch (tags[0]) {
            case Constants.TAG_APPLIED_COURSE: {
                this.mUpdateAppliedCourses(null);
                break;
            }
            case Constants.TAG_SHORTLIST_INSTITUTE:
            case Constants.TAG_DELETESHORTLIST_INSTITUTE: {
                if (tags.length == 2)
                    extraTag = tags[1];
                    if (currentFragment instanceof InstituteListFragment)
                        ((InstituteListFragment) currentFragment).updateShortlistButton(Integer.parseInt(extraTag));
                break;
            }
            case Constants.TAG_INSTITUTE_LIKE_DISLIKE: {
                if (tags.length >= 2)
                    extraTag = tags[1];
                if (currentFragment instanceof InstituteListFragment) {
                    currentFragment.updateLikeButtons(Integer.parseInt(extraTag));
                }
                break;
            }
            case Constants.TAG_LOAD_FEED:
            {
                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).feedsLoaded(null, "");
                }
                break;
            }
            case Constants.TAG_NEXT_FEED:
            {
                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).feedNextLoaded(null, "");
                }
                break;
            }
            case Constants.TAG_REFRESHED_FEED:
            {
                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).feedsRefreshed(new ArrayList<Feed>(), "", true);
                }
                break;
            }

        }
    }

    @Override
    public void onJsonObjectRequestError(final String tag, final String response, final String url, final JSONObject params, final int method) {
        hideProgressDialog();
        hideErrorDialog();
        if (!MainActivity.this.isFinishing()) {
            //show dialog
            mErrorDialog =  new AlertDialog.Builder(this)
                    .setMessage(response)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mMakeJsonObjectNetworkCall(tag, url, params, method);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onInstituteLikedDisliked(int position, int liked) {
        if (position >= 0 && mInstituteList != null && mInstituteList.size() > position) {
            Institute institute = this.mInstituteList.get(position);
            this.onInstituteLikedDislikedByEntity(institute, liked, Constants.TAG_INSTITUTE_LIKE_DISLIKE + "#" + position, Constants.INSTITUTE_LIKE_DISLIKE);
        }
    }

    public void onInstituteLikedDislikedByEntity(Institute institute, int liked, String tag, int source) {
        HashMap<String, String> params = new HashMap<>();
        params.put("source", String.valueOf(source));
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        if (institute.getCurrent_user_vote_type() == Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //neither liked nor disliked case
            if (liked == Constants.LIKE_THING)
                this.mMakeNetworkCall(tag + "#" + liked, institute.getResource_uri() + "upvote/", params, Request.Method.POST);
            else
                this.mMakeNetworkCall(tag + "#" + liked, institute.getResource_uri() + "downvote/", params, Request.Method.POST);
        } else if (institute.getCurrent_user_vote_type() != Constants.NEITHER_LIKE_NOR_DISLIKE && liked != Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //either already liked or disliked case
            if (liked == Constants.LIKE_THING)
                this.mMakeNetworkCall(tag + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE, institute.getResource_uri() + "upvote/", params, Request.Method.POST);
            else
                this.mMakeNetworkCall(tag + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE, institute.getResource_uri() + "downvote/", params, Request.Method.POST);
        }
    }

    @Override
    public void onFilterButtonClicked() {
        if (!this.mFilters.equals("")) {
            updateFilterList(this.mFilters,this.mCurrencies);
            Log.e(TAG_LOAD_FILTERS,"response1");
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_FILTER_LIST);
            Log.e(TAG,"folderList - "+this.mFolderList.size());
            if (fragment == null)
                this.mDisplayFragment(FilterFragment.newInstance(this.mFolderList), true, Constants.TAG_FRAGMENT_FILTER_LIST);
            else
                this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_FILTER_LIST);
        }
    }

    @Override
    public void OnWishlistInstituteSelected(Institute institute, boolean isFromCard) {
        this.mDisplayInstituteByEntity(institute, Constants.CDRecommendedInstituteType.SHORTLIST);
    }

    @Override
    public void OnWishlistInstituteApplied(Institute institute, int position) {
        this.mInstitute = institute;
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        if(institute.getGroups_exists()==1) {
            String cafUrl = Constants.CAF_URL + "?institute_id=" + institute.getId() + "&&user_id=" + MainActivity.mProfile.getId();
            onDisplayWebFragment(cafUrl);
        }else {
            requestForApplyInstitute(Constants.TAG_WISH_LIST_APPLIED_COURSE + "#" + position,new HashMap<String, String>(),Constants.TAG_RECOMMENDED_APPLIED_SHORTLIST_INSTITUTE);

            if(mInstitute != null) {
                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(getString(R.string.APPLY_INSTITUTE_FROM_RECO), mInstitute.getResource_uri());
                SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_COURSE_APPLIED), eventValue, MainActivity.this);
            }
        }

    }

    @Override
    public void OnWishlistInstituteRemoved(Institute institute, int position) {
        this.mInstitute = institute;

        if (institute != null) {
            this.mMakeNetworkCall(Constants.CARD_DELETE_SHORTLISTED_INSTITUTE + "#" + position , institute.getResource_uri() + "shortlist/", null, Request.Method.DELETE);
        }
    }

    @Override
    public void onEndReached(String next, int listType) {

        if (next == null || next.equalsIgnoreCase("null")) return;
        if (listType == Constants.INSTITUTE_TYPE)
        {
            //Add institute exam tag uri, but only in case of explore option
            //Because SBS and Search shouldn't be listed as per exams filters
            Map<String , String> params = this.mGetTheFilters();

            if(this.mExamTag != null && !this.mExamTag.isEmpty())
                params.put("tag_uris[" + (params.size()) + "]",this.mExamTag);

            this.mMakeNetworkCall(Constants.TAG_NEXT_INSTITUTE, next, params);

            //TODO:: Its Dangerous. Remove common tags for search, sbs and explore institute list in
            // TODO ::case of next. Each Tag should be made unique as far as possible. And when we are stable we should trim the possible cases to common tag.
        }
        else if (listType == Constants.WISH_LIST_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_WISHLIST_INSTITUTE, next, null);
        else if (listType == Constants.NEWS_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_NEWS, next, null);
        else if (listType == Constants.ARTICLES_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_ARTICLES, next, null);
        else if (listType == Constants.QNA_LIST_TYPE || listType == Constants.INSTITUTE_QNA_LIST_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_QNA_LIST, next, null);
        else if (listType == Constants.FORUM_LIST_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_FORUMS_LIST, next, null);
        else if (listType == Constants.INSTITUTE_SEARCH_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_INSTITUTE, next, null);
        else if (listType == Constants.FEED_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_FEED, next, null);
        else if(listType == Constants.INSTITUTE_QNA_LIST_TYPE && false)
            this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_QNA_QUESTIONS,next,null);
    }


    public void updateFilterList(String response,String currenciesResponse) {
        mFolderList = new ArrayList<>();
        mCurrenciesList = new ArrayList<>();
        try {
//            mCurrencies = "{ \"currencies\" : "+mCurrencies+" }";
//            mCurrencies = mCurrencies.substring(1,mCurrencies.length()-1);
            mCurrencies = mCurrencies.replace("\"\"","null");
            Log.e("updateFilterListCompare"," mCurrencies - "+currenciesResponse);
            Log.e("updateFilterListCompare"," response - "+response);
//            Log.e("updateFilterList",mFolderList.size()+" responseL - "+response.length()+" mCurrencies - "+mCurrencies);
//            Log.e("updateFilterList",mFolderList.size()+" response - "+response+" mCurrenciesL - "+mCurrencies.length());
            JsonParser folderJP = JSON.std.getStreamingFactory().createParser(response);
            JsonParser currenciesJP = JSON.std.getStreamingFactory().createParser(currenciesResponse);
            Folder.populateFolderListWithCurrency(folderJP, mFolderList,currenciesJP,mCurrenciesList);
            Log.e("updateFilterList",mFolderList.size()+" response - "+response.length()+" mCurrencies - "+mCurrenciesList.size());
            updateCurrencies(this.mCurrenciesList);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "exception occurred in update filter list ");
        }
    }


    public void updateCurrencies(ArrayList<Currency> currencies)
    {
        for (Folder element:this.mFolderList) {
            if(element.getId() == Constants.TAG_STUDY_ABROAD_FOLDER_ID)
            {
                element.setCurrencies(currencies);
            }
        }
    }


    /**
     * This method returns
     * @return map
     */
    private Map<String, String> mGetTheFilters() {
        Map<String, String> map = new HashMap<>();
        SharedPreferences sp = getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE);
        String value = sp.getString(Constants.SELECTED_FILTERS, null);

        if (value != null && !value.equals("") ){
            //split the string to create key-value pairs
            String[] keyValuePairs = value.replaceAll("\\{", "").replaceAll("\\}", "").split(",");

            //iterate over the pairs
            for (String pair : keyValuePairs) {
                if (!pair.isEmpty() && pair != null) {
                    //split the pairs to get key and value
                    String[] entry = pair.split("=");
                    //add them to the hashmap and trim whitespaces
                    map.put(entry[0].trim(), entry[1].trim());
                }
            }
        }
        return map;
    }

    public void showProgressDialog(String message, int theme) {
        if (isFinishing())
            return;
        if(theme == Constants.THEME_TRANSPARENT){
            if (mTransparentProgressDialog == null) {
                mTransparentProgressDialog = new ProgressDialog(MainActivity.this, R.style.TransparentDialog);
                mTransparentProgressDialog.setCancelable(false);
                mTransparentProgressDialog.setIndeterminate(true);
            }

            if (!MainActivity.this.isFinishing())
                mTransparentProgressDialog.show();
        }else {

            if (progressDialog == null) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
            }
            progressDialog.setMessage(message);

            if (!MainActivity.this.isFinishing())
                progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        if(mTransparentProgressDialog != null && mTransparentProgressDialog.isShowing())
            mTransparentProgressDialog.dismiss();
    }

    public void hideErrorDialog() {
        if (mErrorDialog != null && mErrorDialog.isShowing())
            mErrorDialog.dismiss();
    }

    public void displaySnackBar(int messageId) {
        try {

            if (mSnackbar != null && !mSnackbar.isShown()) {
                mSnackbar.setText(getResources().getString(messageId));
                mSnackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewsSelected(News news , boolean addToBackStack, View view) {
        if (!addToBackStack) {
            this.currentFragment.updateNews(news);
        }
        else if (currentFragment instanceof NewsDetailFragment) {
            (currentFragment).updateNews(news);
        }
        else {
            this.mDisplayFragment(NewsDetailFragment.newInstance(news, this.mNewsList), addToBackStack, Constants.TAG_FRAGMENT_NEWS_DETAIL);
        }

        // send news selected Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(news.getId()));
        eventValue.put(getString(R.string.ACTION_NEWS_SELECTED), news.getId());
        SendAppEvent(getString(R.string.CATEGORY_NEWS), getString(R.string.ACTION_NEWS_SELECTED), eventValue, this);

    }

    @Override
    public void onArticleSelected(Articles article, boolean addToBackstack, View view) {
        if (!addToBackstack) {
            currentFragment.updateArticle(article);
        } else if (currentFragment instanceof ArticleDetailFragment) {
            (currentFragment).updateArticle(article);
        } else {
            this.mDisplayFragment(ArticleDetailFragment.newInstance(article, this.mArticlesList), addToBackstack, Constants.TAG_FRAGMENT_ARTICLE_DETAIL);
        }

        // sens article select Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(article.getId()));
        eventValue.put(getString(R.string.ACTION_ARTICLE_SELECTED), article.getId());
        SendAppEvent(getString(R.string.CATEGORY_ARTICLE), getString(R.string.ACTION_ARTICLE_SELECTED), eventValue, this);
    }

    @Override
    public void onQuestionAsked(QnAQuestions question) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", question.getTitle());
        map.put("desc", question.getDesc());
        if (!(currentFragment instanceof QnAQuestionsListFragment))
            map.put("institute", "" + this.mInstituteList.get(currentInstitute).getResource_uri());

        this.mMakeNetworkCall(Constants.TAG_POST_QUESTION, ApiEndPonits.API_PERSONALIZE_QNA, map, Request.Method.POST);
    }

    @Override
    public void onFilterApplied() {
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        int count = 0;
        Map<String, String> mFilterKeywords = new HashMap<>();
        for (Folder f : this.mFolderList) {
            for (Facet ft : f.getFacets())
                if (ft.isSelected() == 1)
                    mFilterKeywords.put("tag_uris[" + (count++) + "]", ft.getTag());
        }

        Log.e(TAG,"Filters "+mFilterKeywords.toString());
        this.mFilterKeywords = mFilterKeywords;
        this.mFilterCount = this.mFilterKeywords.size();

        this.mMakeNetworkCall(Constants.WIDGET_INSTITUTES, ApiEndPonits.API_PERSONALIZE_INSTITUTES, this.mFilterKeywords);

        this.mUpdateFilterButton();
        //save preferences.
        this.getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).apply();

        Map<String, Object> eventValue = new HashMap<>();
        for (String key : this.mFilterKeywords.keySet()) {
            eventValue.put(Constants.SELECTED_FILTERS, this.mFilterKeywords.get(key));

            //Events
            SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_FILTER_APPLIED), eventValue, this);
        }
    }

    @Override
    public void onInstituteShortlisted(int position) {
        if (mInstituteList != null && mInstituteList.size() > position && position>=0) {
            Institute institute = this.mInstituteList.get(position);
            if (institute.getIs_shortlisted() == Constants.SHORTLISTED_NO) {
                mDisplayOtpVerificationFragment();
                this.mMakeNetworkCall(Constants.TAG_SHORTLIST_INSTITUTE + "#" + position, institute.getResource_uri() + "shortlist/", null, Request.Method.POST);
            } else
                this.mMakeNetworkCall(Constants.TAG_DELETESHORTLIST_INSTITUTE + "#" + position, institute.getResource_uri() + "shortlist/", null, Request.Method.DELETE);
        }
    }



    @Override
    public void displayMessage(int messageId) {
        displaySnackBar(messageId);
    }

    @Override
    public void requestToUploadProfileImage(byte[] fileByteArray) {
        if (getConnectivityStatus(getApplicationContext()) != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(PROFILE_IMAGE_UPLOADING);
            MainActivity.mNetworkUtils.postMultiPartRequest(PROFILE_IMAGE_UPLOADING, ApiEndPonits.API_UPLOAD_IMAGE,fileByteArray);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }


    @Override
    public void onFilterCanceled(boolean clearAll) {

        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        onBackPressed();

        if (clearAll) {
            for (Folder f : this.mFolderList) {
                for (Facet ft : f.getFacets())
                    ft.deselect();
            }

            this.mFilterCount = 0;

            this.mFilterKeywords = new HashMap<>();

            //reset the filters in preferences
            this.getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).apply();

            this.mMakeNetworkCall(Constants.WIDGET_INSTITUTES, ApiEndPonits.API_PERSONALIZE_INSTITUTES, null);
        } else {
            mFilterCount = 0;
            for (Folder f : this.mFolderList) {
                for (Facet ft : f.getFacets())
                    if (ft.isSelected() == 1)
                        this.mFilterCount++;
            }

            if (this.mFilterCount == 2)
                this.mFilterCount = 0;
        }

        this.mUpdateFilterButton();
    }

    private void mUpdateFilterButton() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);
        if (fragment instanceof InstituteListFragment) {
            ((InstituteListFragment) fragment).updateFilterButton(mFilterCount);
        }
    }

    @Override
    public void onFilterTypeChanged(int position) {
        if (currentFragment instanceof FilterFragment) {
            ((FilterFragment) currentFragment).updateFilterType(position);
        }
    }


    public void requestInstituteShortlist(){
        try {
            Institute institute = this.mInstituteList.get(this.currentInstitute);
            if (institute.getIs_shortlisted() == Constants.SHORTLISTED_NO)
                this.mMakeNetworkCall(Constants.TAG_SHORTLIST_INSTITUTE + "#" + currentInstitute, institute.getResource_uri() + "shortlist/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_DELETESHORTLIST_INSTITUTE + "#" + currentInstitute, institute.getResource_uri() + "shortlist/", null, Request.Method.DELETE);
        }catch(Exception e){
            Log.e(TAG, "Exception while accessing  institute from institute list");
        }
    }

    private void mMakeNetworkCall(String tag, String url, Map<String, String> params, int method) {
        if (getConnectivityStatus(getApplicationContext()) != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            MainActivity.mNetworkUtils.networkData(tag, url, params, method);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    private void mMakeNetworkCall(String tag, String url, Map<String, String> params) {
        if (getConnectivityStatus(getApplicationContext()) != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            MainActivity.mNetworkUtils.networkData(tag, url, params);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    private void mMakeJsonObjectNetworkCall(String tag, String url, JSONObject params, int method) {
        if (getConnectivityStatus(getApplicationContext()) != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            MainActivity.mNetworkUtils.networkDataWithObjectParam(tag, url, params, method);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    public static String GetPersonalizedMessage(String tag) {
        switch (tag) {
            case Constants.WIDGET_INSTITUTES:
            case Constants.WIDGET_INSTITUTES_SBS:
            case Constants.WIDGET_TRENDING_INSTITUTES:
            case Constants.WIDGET_SHORTLIST_INSTITUTES:
            case Constants.WIDGET_RECOMMENDED_INSTITUTES:
            case Constants.WIDGET_RECOMMENDED_INSTITUTES_FRON_PROFILE:
                return "Loading Institutes";
            case Constants.TAG_UPDATE_INSTITUTES:
                return "Updating Institutes";
            case Constants.TAG_WISH_LIST_APPLIED_COURSE:
                return "Applying to institute";
            case Constants.TAG_UPDATE_STREAM:
            case Constants.TAG_SUBMIT_EDIT_PSYCHOMETRIC_EXAM:
                return "Loading Streams";
            case Constants.TAG_EDUCATION_DETAILS_SUBMIT:
                return "Loading Exams";
            case Constants.TAG_CREATING_USER:
                return "Loading...";//"Creating User...";
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM:
                return "Loading Profile";
            case PROFILE_IMAGE_UPLOADING:
                return "Uploading Image";
            case Constants.TAG_UPDATE_USER_PROFILE:
                return "Updating Profile";
            case Constants.WIDGET_NEWS:
                return "Loading News";
            case Constants.WIDGET_ARTICES:
                return "Loading Articles";
            case Constants.WIDGET_COURSES:
                return "Loading Courses";
            case Constants.TAG_POST_QUESTION:
                return "Posting your question";
            case Constants.TAG_LOAD_FILTERS:
                return "Loading Filters";
            case Constants.TAG_LOAD_QNA_QUESTIONS:
                return "Loading Questions";
            case Constants.TAG_LOAD_MY_FB:
                return "Loading your Forums chat";
            case Constants.ACTION_QNA_ANSWER_SUBMITTED:
                return "Submitting Answer";
            case Constants.ACTION_VOTE_QNA_QUESTION_ENTITY:
            case Constants.ACTION_VOTE_QNA_ANSWER_ENTITY:
                return "Processing Vote";
            case Constants.WIDGET_FORUMS:
                return "Loading Forums";
            case Constants.WIDGET_TEST_CALENDAR:
                return "Loading your plan";
            case Constants.TAG_MY_ALERTS:
                return "Loading important events";
            case Constants.TAG_REQUEST_FOR_OTP:
                return "Requesting for OTP";
            case Constants.TAG_SUBMIT_SBS_EXAM:
                return "Getting institutes for your preferences";
            case Constants.TAG_NEXT_ARTICLES:
            case Constants.TAG_NEXT_FORUMS_LIST:
            case Constants.TAG_NEXT_INSTITUTE:
            case Constants.TAG_NEXT_NEWS:
            case Constants.TAG_NEXT_QNA_LIST:
            case Constants.TAG_NEXT_WISHLIST_INSTITUTE:
            case Constants.TAG_INSTITUTE_LIKE_DISLIKE:
            case Constants.TAG_QUESTION_LIKE_DISLIKE:
            case Constants.ACTION_INSTITUTE_DISLIKED:
            case Constants.TAG_APPLIED_COURSE:
            case Constants.TAG_RECOMMENDED_SHORTLIST_INSTITUTE:
            case Constants.TAG_RECOMMENDED_APPLIED_SHORTLIST_INSTITUTE:
            case Constants.TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE:
            case Constants.TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE:
            case Constants.TAG_SHORTLIST_INSTITUTE:
            case Constants.TAG_DELETESHORTLIST_INSTITUTE:
            case Constants.TAG_LOAD_COURSES:
            case Constants.TAG_REQUEST_FOR_SPECIALIZATION:
            case Constants.TAG_REQUEST_FOR_DEGREES:
            case Constants.TAG_UPDATE_PROFILE_OBJECT:
            case Constants.TAG_UPDATE_PROFILE_EXAMS:
            case Constants.REFRESH_CHATROOM:
            case TAG_REFRESH_PROFILE:
            case Constants.ACTION_MY_FB_COMMENT_SUBMITTED:
            case Constants.TAG_CREATE_USER:
            case Constants.TAG_REFRESHED_FEED:
            case Constants.TAG_SIMILAR_QUESTIONS:
            case Constants.TAG_LOAD_STATES:
            case Constants.TAG_LOAD_CITIES:
            case "":
                return null;
            default:
                return "Loading";
        }
    }

    private int getProcessDialogTheme(String tag) {
        switch (tag) {
            case Constants.TAG_LOAD_SUB_LEVELS:
            case Constants.TAG_LOAD_LEVEL_STREAMS:
            case Constants.TAG_LOAD_STREAM:
            case Constants.TAG_REQUEST_FOR_EXAMS:
            case Constants.TAG_SPLASH_LOGIN_PROCEED:
            case Constants.TAG_USER_EXAMS_SUBMISSION:
            case Constants.TAG_LOCATION_UPDATED:
            case Constants.TAG_UPDATE_COUNTRIES:
                return Constants.THEME_TRANSPARENT;
        }
        return Constants.THEME_BACKGROUND;
    }

    private void showProgress(String tag) {
        String[] tags = tag.split("#");
        String message = MainActivity.GetPersonalizedMessage(tags[0]);
        int dialogTheme = getProcessDialogTheme(tags[0]);
        if (dialogTheme == Constants.THEME_TRANSPARENT) {
            showProgressDialog("", dialogTheme);
        } else if (message != null) {
            showProgressDialog(message, dialogTheme);
        }
    }

    @Override
    public void onQnAQuestionSelected(QnAQuestions qnaQuestion, int position) {
        this.mDisplayFragment(QnaQuestionDetailFragmentNew.getInstance(qnaQuestion), true, getString(R.string.TAG_FRAGMENT_QNA_QUESTION_DETAIL));
    }

    @Override
    public void onQnAQuestionVote(int position, int voteType) {

        QnAQuestions qnaQuestion = this.mQnAQuestions.get(position);
        //neither liked nor disliked case
        int userVotType = qnaQuestion.getCurrent_user_vote_type();
        if (userVotType != Constants.NOT_INTERESTED_THING) {
            if (userVotType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + voteType, qnaQuestion.getUri() + "downvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + voteType, qnaQuestion.getUri() + "upvote/", null, Request.Method.POST);

        } else {
            if (voteType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + voteType, qnaQuestion.getUri() + "upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + voteType, qnaQuestion.getUri() + "downvote/", null, Request.Method.POST);

        }
    }

    private void mQnAQuestionVoteUpdated(int questionIndex, int voteType) {
        try {
            /*if (currentFragment instanceof QnAQuestionDetailFragment)
                ((QnAQuestionDetailFragment) currentFragment).onVotingFeedback(questionIndex, -1, voteType);
            */
            QnAQuestions qnaQuestion = this.mQnAQuestions.get(questionIndex);
            if(qnaQuestion == null)
                return;

            Map<String, Object> eventValue = new HashMap<>();
            if (voteType == Constants.LIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(qnaQuestion.getResource_uri()));
                eventValue.put(getString(R.string.VOTE_TYPE), Constants.LIKE_THING);
                eventValue.put(qnaQuestion.getResource_uri(), Constants.LIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.LIKE_THING);

                //Events
                SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_VOTE_QNA_QUESTION_UPVOTED), eventValue, this);

            } else if (voteType == Constants.DISLIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(qnaQuestion.getResource_uri()));
                eventValue.put(getString(R.string.VOTE_TYPE), Constants.DISLIKE_THING);
                eventValue.put(qnaQuestion.getResource_uri(), Constants.DISLIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.DISLIKE_THING);

                //Events
                SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_VOTE_QNA_QUESTION_UPVOTED), eventValue, this);
            }


        } catch (Exception e) {
            Log.e("QnA question voting", e.getMessage());
        }
    }

    private void mQnAAnswerVoteUpdated(int questionIndex, int answerIndex, int voteType) {
        //((QnAQuestionDetailFragment) currentFragment).onVotingFeedback(questionIndex, answerIndex, voteType);
        try {
            Map<String, Object> eventValue = new HashMap<>();
            QnAAnswers answer = this.mQnAQuestions.get(questionIndex).getAnswer_set().get(answerIndex);

            if (voteType == Constants.LIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, answer.getResource_uri());
                eventValue.put(getString(R.string.VOTE_TYPE), Constants.LIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.LIKE_THING);
                eventValue.put(answer.getResource_uri(), Constants.LIKE_THING);

                //Events
                SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_VOTE_QNA_ANSWER_UPVOTED), eventValue, this);

            } else if (voteType == Constants.DISLIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, answer.getResource_uri());
                eventValue.put(getString(R.string.VOTE_TYPE), Constants.DISLIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.DISLIKE_THING);
                eventValue.put(answer.getResource_uri(), Constants.DISLIKE_THING);

                //Events
                SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_VOTE_QNA_ANSWER_DOWNVOTED), eventValue, this);
            }
        } catch (Exception e) {
            Log.e("QnA answer voting", e.getMessage());
        }
    }

    private void mOnAnswerAdded(String response) {

        try {
            QnAAnswers qnaAnswer = JSON.std.beanFrom(QnAAnswers.class, response);
            /*if (currentFragment instanceof QnAQuestionDetailFragment)
                ((QnAQuestionDetailFragment) currentFragment).answerAdded(qnaAnswer);
            else */if (currentFragment instanceof InstituteQnAFragment)
                ((InstituteQnAFragment) currentFragment).instituteQnAAnswerUpdated(qnaAnswer);

            //Events
            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_RESOURCE_URI, qnaAnswer.getResource_uri());
            eventValue.put(getString(R.string.QNA_ANSWER_RESOURCE_URI), qnaAnswer.getResource_uri());
            SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_QNA_ANSWER_SUBMITTED), eventValue, this);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position, int commentsCount) {
        this.mMakeNetworkCall(Constants.TAG_LOAD_MY_FB + "#" + String.valueOf(position) + "#" + String.valueOf(commentsCount), myFutureBuddiesEnumeration.getResource_uri(), null, Request.Method.GET);

        //Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.TAG_RESOURCE_URI, myFutureBuddiesEnumeration.getResource_uri());
        SendAppEvent(getString(R.string.CATEGORY_MY_FB), getString(R.string.ACTION_MY_FB_SELECTED), eventValue, this);
    }

    @Override
    public void onMyFBCommentSubmitted(String myFbURI, String commentText, int myFbIndex, int myFbCommentIndex, boolean counselor) {
        HashMap<String, String> params = new HashMap<>();
        params.put("comment", commentText);

        if(!counselor){
            myFbURI = myFbURI + "comment/";
        }
        this.mMakeNetworkCall(Constants.ACTION_MY_FB_COMMENT_SUBMITTED + "#" + String.valueOf(myFbIndex) + "#" + String.valueOf(myFbCommentIndex), myFbURI , params, Request.Method.POST);
    }

    @Override
    public void onMyFBUpdated(int commentsSize, int myFbIndex) {
        if (currentFragment instanceof MyFutureBuddiesEnumerationFragment)
            ((MyFutureBuddiesEnumerationFragment) currentFragment).updateEnumerationList(commentsSize, myFbIndex);
    }

    @Override
    public void onScrolledToTop(String next){
        this.mMakeNetworkCall(Constants.TAG_LOAD_MORE_FB_COMMENT,next, null, Request.Method.GET);
    }

    private void mLoadPreviousComments(String response){
        MyFutureBuddy mFb = this.mParseAndPopulateMyFB(response, 0);
        if(currentFragment instanceof MyFutureBuddiesFragment){
            ((MyFutureBuddiesFragment) currentFragment).mDisplayPreviousComments(mFb);
        }
        else if(currentSubFragment instanceof MyFutureBuddiesFragment){
            ((MyFutureBuddiesFragment) currentSubFragment).mDisplayPreviousComments(mFb);
        }
    }

    private void mOnMyFBCommentAdded(String response, int fbIndex, int index) {
        try {
            MyFutureBuddyComment fbComment = JSON.std.beanFrom(MyFutureBuddyComment.class, response);
            fbComment.setIndex(index);
            fbComment.setFbIndex(fbIndex);
            fbComment.setCommentSent(true);

            if (currentFragment instanceof MyFutureBuddiesFragment)
                ((MyFutureBuddiesFragment) currentFragment).commentAdded(fbComment);
            else if (currentSubFragment instanceof MyFutureBuddiesFragment)
                ((MyFutureBuddiesFragment) currentSubFragment).commentAdded(fbComment);

            if(mFbEnumeration != null && mFbEnumeration.size() >fbIndex) {
                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(Constants.TAG_RESOURCE_URI, this.mFbEnumeration.get(fbIndex).getResource_uri());
                eventValue.put(getString(R.string.MY_FB_URI), this.mFbEnumeration.get(fbIndex).getResource_uri());
                //Events
                SendAppEvent(getString(R.string.CATEGORY_MY_FB), getString(R.string.ACTION_MY_FB_COMMENT_SUBMITTED), eventValue, this);

            }

        } catch (Exception e) {
            Log.e(TAG, "mOnMyFBCommentAdded() exception while parsing FB comment ");
        }
    }

    private void mInstituteQnAQuestionAdded(String response) {

        try {
            QnAQuestions qnaQuestion = JSON.std.beanFrom(QnAQuestions.class, response);
            if(this.mQnAQuestions == null)
                this.mQnAQuestions = new ArrayList<>();

            this.mQnAQuestions.add(0, qnaQuestion);
            if (currentFragment instanceof  QnAQuestionsListFragment){
                (currentFragment).instituteQnAQuestionAdded(qnaQuestion);
            }

            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_RESOURCE_URI, qnaQuestion.getResource_uri());
            eventValue.put(getString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());
            //Events
            SendAppEvent(getString(R.string.CATEGORY_QNA), getString(R.string.ACTION_QNA_QUESTION_ASKED), eventValue, this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<QnAQuestions> parseAndReturnQnAList(String qnaString) {
        return parseAndReturnQnAList(qnaString, false);
    }

    public ArrayList<QnAQuestions> parseAndReturnQnAList(String qnaString, boolean isNewList) {
        if(mQnAQuestions == null)
            mQnAQuestions = new ArrayList<>();
        try {
            if (isNewList) {
                mQnAQuestions.clear();
            }
            String result = extractResults(qnaString);
            List<QnAQuestions> qnaQuestionList = JSON.std.listOfFrom(QnAQuestions.class, result);
            mQnAQuestions.addAll(qnaQuestionList);

        } catch (IOException e) {
            Log.e(MainActivity.class.getSimpleName(), e.getMessage() + e.getCause());
        }
        return mQnAQuestions;

    }


    /**
     * This method is called when user press device back button
     * Do these task
     * 1- If navigation drawer is open then close it
     * 2- If last fragment clear back stack FragmentManager
     * 3- If user wants to close app then show a message to press device back button again
     */
    @Override
    public void onBackPressed() {
        // if navigation drawer layout is open then on back press
        // it will close
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if(this.mFabMenu != null && this.mFabMenu.isOpened() ){
            this.mFabMenu.close(true);
            return;
        }

        if (currentFragment != null) {
            if (currentFragment instanceof SyllabusSubjectsListFragment) {
                ((SyllabusSubjectsListFragment) currentFragment).submitSyllabusStatus();
            } else if (currentFragment instanceof WebViewFragment){
                boolean canGoBack = ((WebViewFragment) currentFragment).canGoBack();
                if(canGoBack)
                    return;
            }else if (currentFragment instanceof PostAnonymousLoginFragment){
                mShowAppBarLayout();
            }
        }

        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount == 0){
            if (isFromNotification) {
                isFromNotification = false;
                mLoadUserStatusScreen();
                return;
            }else if(currentFragment instanceof HomeFragment
                    && ((HomeFragment) currentFragment).getSelectedPage() != 0 ){
                ((HomeFragment) currentFragment).setSelectedPage(0);
            } else if(!Constants.READY_TO_CLOSE){
                Constants.READY_TO_CLOSE = true;
                Utils.DisplayToast(getApplicationContext(), "Press again to close CollegeDekho");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Constants.READY_TO_CLOSE = false;
                    }
                },3000);
            }else{
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
        invalidateOptionsMenu();
    }

    /**
     * This method is used to clear back stack of FragmentManager
     * It will remove all fragments present in stack. and now stack
     * will be empty
     */
    private void mClearBackStack() {
        try {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            for (int i = 0; i < count; i++)
                getSupportFragmentManager().popBackStack();
        } catch (Exception e) {
            Log.e(TAG, "exception occurred while clearing back stack");
        }
    }

    /**
     * This method is used to clear back stack of FragmentManager
     * It will remove all fragments present in stack. and now stack
     * will be empty
     */
    private  void mClearBackStackWithoutAnimation() {
        try {
            Constants.DISABLE_FRAGMENT_ANIMATION = true;
            int count = getSupportFragmentManager().getBackStackEntryCount();
            for (int i = 0; i < count; i++)
                getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Constants.DISABLE_FRAGMENT_ANIMATION = false;
        }catch (Exception error)  {
            Log.e(TAG, "exception occurred while clearing back stack without animation");
        }
    }

    @Override
    public void onRequestForUserCreation() {
        if (mProfile == null || mProfile.getId() == null || mProfile.getId().isEmpty()) {
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_ANONYMOUS);
            onUserCommonLogin(params, Constants.TAG_CREATE_USER);
        }
    }

    public void onEventNewUserProceedClick() {
        if(IS_USER_CREATED){
            mDisplayLevelSelectionFragment();
        }else {
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_ANONYMOUS);
            onUserCommonLogin(params, Constants.TAG_SPLASH_LOGIN_PROCEED);
        }
    }


    @Override
    public void onIknowWhatIWant() {
        this.mMakeNetworkCall(Constants.TAG_LOAD_STREAM, ApiEndPonits.API_STREAMS, null);

        //Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.CHOSEN_ACTION_WHEN_NOT_PREPARING), "I_KNOW_WHAT_I_WANT");
        SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_WHEN_NOT_PREPARING), eventValue, this);
    }
/*

    @Override
    public void onSkipSelectedInProfileBuilding() {
        mDisplayNotPreparingFragment();
    }
*/

    private void onRequestForSubLevels(String level){
        mMakeNetworkCall(Constants.TAG_LOAD_SUB_LEVELS,ApiEndPonits.API_SUB_LEVELS+level, null);
    }

    private void onRequestForCountries(){
        mMakeNetworkCall(Constants.TAG_LOAD_COUNTRIES,ApiEndPonits.API_COUNTRIES, null);
    }
    private void onRequestForStates(String extraCountryIds){
        mMakeNetworkCall(Constants.TAG_LOAD_STATES,ApiEndPonits.API_STATES+extraCountryIds, null);
    }
    private void onRequestForCities(String extraStateIds){
        mMakeNetworkCall(Constants.TAG_LOAD_CITIES,ApiEndPonits.API_CITIES+extraStateIds, null);
    }

    public void onRequestForLevelStreams(String level){
        mMakeNetworkCall(Constants.TAG_LOAD_LEVEL_STREAMS,ApiEndPonits.API_STREAMS+"?preferred_level="+mProfile.getPreferred_level()+"&is_extra="+level, null);
    }

    public void onRequestForMyFB(String institute)
    {

    }

    /**
     * This method is used to get exams list based on preferred level
     * and preferred stream if preferred stream is not available then we send
     * current stream
     */
    private void onRequestForYearlyExams() {
        // request for yearly exam based on preferred level
        StringBuilder examUrl = new StringBuilder(ApiEndPonits.API_STREAM_YEARLY_EXAMS);
        examUrl.append("?preferred_level=").append(mProfile.getPreferred_level());

        int userPreferredStreamId = mProfile.getPreferred_stream_id();
        if(userPreferredStreamId > 0){
            examUrl.append("&preferred_stream=").append(userPreferredStreamId);
        }else{
            examUrl.append("&current_stream=").append(mProfile.getCurrent_stream_id());
        }

        this.mMakeNetworkCall(Constants.TAG_REQUEST_FOR_EXAMS, examUrl.toString(), null, Request.Method.GET);
    }

    /**
     * This method is used to login with facebook account
     * @param params request data
     */
    private void onUserCommonLogin(HashMap<String, String> params , String TAG) {
        params.put(getString(R.string.app_version), Utils.GetAppVersion());
        this.mMakeNetworkCall(TAG, ApiEndPonits.API_NEW_COMMON_LOGIN, params);
    }

    @Override
    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {

        HashMap<String , String> params = new HashMap<>();

        if( trueProfile.zipcode == null)
            trueProfile.zipcode ="";

        if( trueProfile.city == null)
            trueProfile.city ="";

        if( trueProfile.avatarUrl == null)
            trueProfile.avatarUrl ="";

        if( trueProfile.facebookId == null)
            trueProfile.facebookId ="";

        String phone =  trueProfile.phoneNumber;
        if(phone != null){
            phone = phone.replace("+91", "");
            params.put(getString(R.string.USER_PHONE), phone);
        }

        if( trueProfile.email == null) {
            if(mProfile != null && mProfile.getEmail() != null) {
                trueProfile.email = MainActivity.mProfile.getEmail();
            }else{
                trueProfile.email="";
            }

        }
        params.put(getString(R.string.USER_NAME),trueProfile.firstName+" "+trueProfile.lastName);
        params.put(getString(R.string.USER_EMAIL), trueProfile.email);
        params.put(getString(R.string.USER_GENDER),trueProfile.gender);
        params.put(getString(R.string.USER_CITY), trueProfile.city);
        params.put(getString(R.string.USER_ZIP_CODE),trueProfile.zipcode);
        params.put(getString(R.string.USER_IMAGE), trueProfile.avatarUrl);
        params.put(getString(R.string.USER_COUNTRY_CODE), trueProfile.countryCode);
        params.put(getString(R.string.USER_FACEBOOK_ID), trueProfile.facebookId);

        //String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //params.put(getString(R.string.USER_DEVICE_ID), deviceId);
        params.put(getString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_TRUECALLER);

        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        onUserCommonLogin(params,Constants.TAG_TRUE_SDK_LOGIN);
    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {

        if(trueError.getErrorType() == TrueError.ERROR_TYPE_NETWORK){
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
        else if(trueError.getErrorType() == TrueError.ERROR_TYPE_USER_DENIED){
            displaySnackBar(R.string.TRUE_SDK_USER_DENIED);
        }
        else if(trueError.getErrorType() == TrueError.ERROR_TYPE_INTERNAL){
            displaySnackBar(R.string.TRUE_SDK_INTERNAL_ERROR);
        }
        else
            displaySnackBar(R.string.TRUE_SDK_INVALID_USER);
    }

    /**
     *  This method is called when user's exams are edited
     * @param responseJson user profile json
     */
    private void onUserExamsEditedResponse(String responseJson) {
        try {
            DataBaseHelper.getInstance(this).deleteAllExamSummary();
            mParseProfileResponse(responseJson);
            onBackPressed();

            // if user has removed all exams and when go back to tab
            // screen has and make first tab selected because third tab
            //  gives exam preparation details
            if(currentFragment instanceof  HomeFragment){
                ((HomeFragment) currentFragment).updateExamsList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Folder> getFilterList() {
        if (!this.mFilters.equals("")) {
            Log.e(TAG_LOAD_FILTERS,"response2");
            updateFilterList(this.mFilters,this.mCurrencies);
        }
        return this.mFolderList;
    }

    public static void GASessionEvent(String screenName) {
        if (MainActivity.tracker != null) {
            MainActivity.tracker.setScreenName(screenName);
            // Start a new session with the hit.
            MainActivity.tracker.send(new HitBuilders.ScreenViewBuilder()
                    .setNewSession()
                    .build());
        }
    }

    public static void GAScreenEvent(String screenName) {
        if (MainActivity.tracker != null) {
            MainActivity.tracker.setScreenName(screenName);
            // Start a new session with the hit.
            MainActivity.tracker.send(new HitBuilders.ScreenViewBuilder()
                    .build());
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return getProfileLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (this.mDeviceProfile == null)
            this.mDeviceProfile = new DeviceProfile();
        this.mDeviceProfile.processProfileData(cursor, this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * This method is used to load home screen after profile building is done.
     */
    private void mDisplayHomeFragment() {
        mClearBackStack();
        IS_HOME_LOADED = true;
        this.getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean(getString(R.string.USER_HOME_LOADED), true).apply();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        if (fragment == null)
            this.mDisplayFragment(HomeFragment.newInstance(), false, HomeFragment.class.getSimpleName());
        else {
            this.mDisplayFragment(fragment, false, HomeFragment.class.getSimpleName());
        }
    }

    private void mDisplayFeedFragment(String response) {
        List<Feed> feedList = this.mParseFeedForRecoInstitute(response);
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).feedsLoaded(new ArrayList<>(feedList), this.next);
        }
    }



    private List<Feed> mParseFeedForRecoInstitute(String response)
    {
        List<Feed> feedList = new ArrayList<>();

        //Since Jackson parser was not able to parse escaped json, stored as value in the feed item
        //Its a shim to get results in the feed
        HashMap<Integer, String> idResultHashMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("results"))
            {
                JSONArray resultsJsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < resultsJsonArray.length(); i++)
                {
                    JSONObject feedObject = (JSONObject) resultsJsonArray.get(i);
                    if (feedObject.getString("screen").equals(Constants.RECOMMENDED_INSTITUTE_FEED_LIST))
                        idResultHashMap.put(feedObject.getInt("id"), feedObject.getString("results"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            feedList = JSON.std.listOfFrom(Feed.class, this.extractResults(response));
            for (Feed feed : feedList)
            {
                if (idResultHashMap.containsKey(feed.getId()))
                    feed.setResult(idResultHashMap.get(feed.getId()));
            }

        } catch (IOException e) {
            Log.e(TAG, "exception occurred on display feed fragment");
        }

        return feedList;
    }

    @Override
    public void onExamTabSelected(ProfileExam examDetailObj) {
        if (examDetailObj == null) return;
        int id = examDetailObj.getId();
        String dbSummary = DataBaseHelper.getInstance(this).getExamSummary(id);
        if(dbSummary != null) {
            mUpdateExamDetail(dbSummary, false);
            return;
        }
        Map<String, String> params = this.mGetTheFilters();
        if (params == null)
            params = new HashMap<>();

        params.put("tag_uris[" + (params.size()) + "]", examDetailObj.getExam_tag());
        this.mMakeNetworkCall(Constants.TAG_EXAM_SUMMARY, ApiEndPonits.BASE_URL + "yearly-exams/" + id + "/summary/", params);
    }


    private void mUpdateExamDetail(String responseJson, boolean update) {
        try {
            ExamSummary examSummary = JSON.std.beanFrom(ExamSummary.class, responseJson);
            if (examSummary.getPreference_uri() != null) {
                DataBaseHelper db = DataBaseHelper.getInstance(this);
                String dbSummary = db.getExamSummary(Integer.parseInt(examSummary.getYearly_exam_id()));
                if (dbSummary == null) {
                    db.addExamSummary(Integer.parseInt(examSummary.getYearly_exam_id()), responseJson);
                } else if (update) {
                    db.updateExamSummary(Integer.parseInt(examSummary.getYearly_exam_id()), responseJson);
                }
            }
            if(currentFragment instanceof  HomeFragment)
                ((HomeFragment) currentFragment).updateUserYearlyExamSummary(examSummary);
            if (MainActivity.type != null && !MainActivity.type.matches("") && MainActivity.resource_uri != null && !MainActivity.resource_uri.matches("")) {
                this.mHandleNotifications();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onChatRoomSwipedDown(String requestType, String url){
        this.mMakeNetworkCall(requestType, url, null);
    }

    @Override
    public void onHomeItemSelected(String requestType, String url, String tag) {
        if (requestType.equalsIgnoreCase(Constants.WIDGET_INSTITUTES)
                || requestType.equalsIgnoreCase(Constants.WIDGET_RECOMMENDED_INSTITUTES)
                || requestType.equalsIgnoreCase(Constants.WIDGET_RECOMMENDED_INSTITUTES_FRON_PROFILE)
                || requestType.equalsIgnoreCase(Constants.WIDGET_TRENDING_INSTITUTES)
                || requestType.equalsIgnoreCase(Constants.WIDGET_INSTITUTES_SBS)){
            //Suggesting System that its a good time to do GC
            System.gc();


            Map<String , String> params = this.mGetTheFilters();
            if(tag != null && !tag.isEmpty())
            {
                params.put("tag_uris[" + (params.size()) + "]",tag);
                this.mExamTag = tag;
            }
            this.mMakeNetworkCall(requestType, url, params);
            return;
        } else if (requestType.equals(Constants.WIDGET_TEST_CALENDAR)) {
            this.mMakeNetworkCall(requestType, url, null);
            return;
        } else if (requestType.equals(Constants.TAG_MY_ALERTS)) {

            this.mMakeNetworkCall(requestType, url, null);
            return;
        }else  if (requestType.equals(Constants.WIDGET_SHORTLIST_INSTITUTES)) {
            this.mMakeNetworkCall(requestType, url, null);
            return;
        }
        if (requestType.equals(Constants.WIDGET_SYLLABUS)) {
            this.mMakeNetworkCall(requestType, url, null);
            return;
        }
        if (requestType.equals(Constants.WIDGET_FORUMS) || requestType.equals(Constants.TAG_LOAD_COUNSELOR_CHAT) ||
                requestType.equals(Constants.TAG_LOAD_QNA_QUESTIONS)) {
            this.mMakeNetworkCall(requestType, url, null);
            return;
        }

        Map<String, String> params = new HashMap<>();
        if (tag != null && !tag.isEmpty())
            params.put("tag_uris[" + (0) + "]", tag);

        this.mMakeNetworkCall(requestType, url, params);
    }

    @Override
    public void onTabStepByStep() {
        this.startStepByStep();
    }

    @Override
    public void onTabPsychometricReport(){
        try {
            String results = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getString("psychometric_report", null);
            if(results != null) {
                ArrayList<Stream> streams = (ArrayList<Stream>) JSON.std.listOfFrom(Stream.class, results);
                if(streams == null)
                    return;
                mClearBackStack();
                this.mDisplayFragment(PsychometricStreamFragment.newInstance(streams), true, Constants.TAG_FRAGMENT_STREAMS);
            }
        } catch (Exception e){
            Utils.DisplayToast(getApplicationContext(),"Cannot load psychometric results.");
        }
    }

    public void mGetFeed(String tag, String url)
    {
        this.mMakeNetworkCall(tag, url, null);
    }

    @Override
    public void onPsychometricTestSelected()
    {
        this.mMakeNetworkCall(Constants.TAG_PSYCHOMETRIC_QUESTIONS, ApiEndPonits.BASE_URL + "star-psychometric/2/", null);
        //Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.CHOSEN_ACTION_WHEN_NOT_PREPARING), PsychometricTestQuestion.class.getSimpleName());
        SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_WHEN_NOT_PREPARING), eventValue, this);
    }

    private void onPsychometricTestResponse(String response) {
        try {

            Map<String, Object> map = JSON.std.mapFrom(response);
            String val = JSON.std.asString(map.get("questions"));
            this.psychometricQuestionsList = JSON.std.listOfFrom(PsychometricTestQuestion.class, val);
            this.mDisplayFragment(PsychometricTestParentFragment.newInstance(new ArrayList<>(this.psychometricQuestionsList)), true, PsychometricTestParentFragment.class.toString());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onSubmitPsychometricTest(JSONObject params) {
        this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM, ApiEndPonits.BASE_URL + "star-psychometric/2/", params, 1);
    }

    private void mDisplayStreamsSelection(String response) {
        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String results = JSON.std.asString(map.get("results"));
            getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putString("psychometric_report", results).apply();
            List<Stream> streams = JSON.std.listOfFrom(Stream.class, results);
            this.mClearBackStack();
            this.mDisplayFragment(PsychometricStreamFragment.newInstance(new ArrayList<>(streams)), true, Constants.TAG_FRAGMENT_STREAMS);
            mDisplayOtpVerificationFragment();
        } catch (IOException e) {
            Log.e(TAG, "Exception occurred on display stream fragment");
        }
    }

    @Override
    public void onStreamSelected(int streamId) {
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        HashMap<String, String> params = new HashMap<>();
        params.put(getString(R.string.preferred_stream_id), ""+streamId);
        params.put(getString(R.string.USER_DEVICE_ID), deviceId);
        this.requestForProfile(params);
        // if home is already loaded then just clear back stack
        if(IS_HOME_LOADED){
            mClearBackStack();
            return;
        }
        this.mDisplayHomeFragment();

    }

    @Override
    public void onStepByStep() {
        this.startStepByStep();
    }

    private void startStepByStep()
    {
        String urlPart = "";
        int streamID = MainActivity.mProfile.getCurrent_stream_id();
        String startWithQuestionNumber = "one";
        if (streamID > 0)
        {
            urlPart = streamID + "/";
            startWithQuestionNumber = "two";
        }

        int currentLevel = mProfile.getCurrent_level_id();
        if(currentLevel == ProfileMacro.LEVEL_TWELFTH || currentLevel == ProfileMacro.LEVEL_TENTH) {
            MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP, ApiEndPonits.BASE_URL + "step-by-step/" + urlPart + "ug-ques-" + startWithQuestionNumber + "/", null);
        }else if(currentLevel == ProfileMacro.LEVEL_POST_GRADUATE || currentLevel == ProfileMacro.LEVEL_UNDER_GRADUATE ){
            MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP, ApiEndPonits.BASE_URL + "step-by-step/" + urlPart + "pg-ques-" + startWithQuestionNumber + "/", null);
        }
        else {
            if (!MainActivity.this.isFinishing()) {
                new AlertDialog.Builder(this)
                        .setTitle("Currently Studying at?")
                        .setSingleChoiceItems(StepByStepQuestion.CurrentLevels.getValues(), -1,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String urlPart = "";
                                        int streamID = MainActivity.mProfile.getCurrent_stream_id();

                                        String startWithQuestionNumber = "one";

                                        if (streamID > 0) {
                                            urlPart = streamID + "/";
                                            startWithQuestionNumber = "two";
                                        }

                                        switch (which) {
                                            case 0:
                                                StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.IN_SCHOOL);
                                                MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP, ApiEndPonits.BASE_URL + "step-by-step/" + urlPart + "ug-ques-" + startWithQuestionNumber + "/", null);
                                                break;
                                            case 1:
                                                StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.GRADUATE_COLLEGE);
                                                MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP, ApiEndPonits.BASE_URL + "step-by-step/" + urlPart + "pg-ques-" + startWithQuestionNumber + "/", null);
                                                break;
                                            case 2:
                                                StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.POSTGRADUATE_COLLEGE);
                                                MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP, ApiEndPonits.BASE_URL + "step-by-step/" + urlPart + "pg-ques-" + startWithQuestionNumber + "/", null);
                                                break;
                                            default:
                                                break;
                                        }
                                        dialog.dismiss();
                                    }
                                })
                        .show();
            }
        }
        //Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.CHOSEN_ACTION_WHEN_NOT_PREPARING), StepByStepFragment.class.getSimpleName());
        SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_WHEN_NOT_PREPARING), eventValue, this);
    }


    private void onPreferredStreamSelected(String responseJson) {
        // parse user response
        this.mParseProfileResponse(responseJson);

        // update user exam on profile building fragment
        if(currentFragment instanceof PrefStreamSelectionFragment) {
            ((PrefStreamSelectionFragment) currentFragment).hideNavigationIcon();
            mDisplayHomeFragment();
        }
    }
    
    private void onCourseSelected(String responseJson) {
        // parse user response
        this.mParseProfileResponse(responseJson);
        // load home screen
        this.mDisplayHomeFragment();
    }
    
    @Subscribe
    public void onEvent(Event event) {
        if (event != null) {
            switch (event.getTag()) {
                case AllEvents.ACTION_EXISTING_USER_CLICK:
                    this.mDisplayLoginFragment();
                    break;
                case AllEvents.ACTION_NEW_USER_PROCEED_CLICK:
                    this.onEventNewUserProceedClick();
                    break;
                case AllEvents.ACTION_REQUEST_FOR_SUB_LEVELS:
                    this.onRequestForSubLevels(event.getExtra());
                    break;
                case AllEvents.ACTION_REQUEST_FOR_LEVEl_STREAMS:
                    this.onRequestForLevelStreams(event.getExtra());
                    break;
                case AllEvents.ACTION_REQUEST_FOR_COUNTRIES:
                    this.onRequestForCountries();
                    break;
                case AllEvents.ACTION_REQUEST_FOR_STATES:
                    this.onRequestForStates(event.getExtra());
                    break;
                case AllEvents.ACTION_REQUEST_FOR_CITIES:
                    this.onRequestForCities(event.getExtra());
                    break;
                case AllEvents.ACTION_LEVEL_EDIT_SELECTION:
                    this.mDisplayLevelSelectionFragment();
                    break;
                case AllEvents.ACTION_PLEASE_SELECT_LEVEL:
                    this.displaySnackBar(R.string.please_select_your_level);
                    break;
                case AllEvents.ACTION_SPECIFIC_COURSE_CLICK:
                    this.mDisplayCousreSelectionFragment("");
                    break;
                case AllEvents.ACTION_COURSES_SELECTION_SEARCH:
                    this.mDisplayCousreSelectionFragment(event.getExtra());
                    break;
                case AllEvents.ACTION_COURSE_SELECTION_SKIP_CLICK:
                    this.mDisplayPrefStreamSelectionFragment(null);
                    break;
                case AllEvents.ACTION_PLEASE_SELECT_STREAM:
                    this.displaySnackBar(R.string.please_select_your_stream);
                    break;
                case AllEvents.ACTION_SKIP_STREAM_SELECTION:
                    this.mDisplayNotPreparingFragment();
                    break;
                case AllEvents.ACTION_STREAM_EDIT_SELECTION:
                    this.mDisplayPrefStreamSelectionFragment( null);
                    break;
                case AllEvents.ACTION_PLEASE_SELECT_ATLEAST_ONE_EXAM:
                    this.displaySnackBar(R.string.SELECT_ONE_EXAM);
                    break;
                case AllEvents.ACTION_SKIP_EXAM_SELECTION:
                    this.mDisplayNotPreparingFragment();
                    break;
               case AllEvents.ACTION_REQUEST_FOR_YEARY_EXAMS:
                    this.onRequestForYearlyExams();
                    break;
                case AllEvents.ACTION_ON_LOCATION_UPDATE:
                    this.onRequestForLocationUpdate((HashMap<String, String>) event.getObj());
                    break;
                case AllEvents.ACTION_ON_PREFERRED_STREAM_SELECTED:
                     HashMap<String, String> params = new HashMap<>();
                    params.put("preferred_stream_id", String.valueOf(MainActivity.mProfile.getPreferred_stream_id()));
                    requestForUserProfileUpdate(Constants.SET_PREFERRED_STREAM,params);
                    break;
                case AllEvents.ACTION_PROFILE_COMPLETION_CLICK:
                    String name = mProfile.getName();
                    String phone = mProfile.getPhone_no();
                    if (phone == null || phone.length() < 10 || name == null || name.isEmpty()
                            || name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())){
                        mAskForNameAndPhone();
                    }//else{
                    mDisplayProfileFragment();
                    //}
                    break;
                case AllEvents.ACTION_REQUEST_FOR_OTP:
                    onRequestForOTP((HashMap<String, String>) event.getObj());
                    break;
                case AllEvents.ACTION_VERIFY_OTP:
                    onVerifyOTP((HashMap<String, String>) event.getObj());
                    break;
                case AllEvents.ACTION_OTP_VERIFIED_BY_PROFILE_COMPLETION:
                    //TODO:: code when otp is verified by profile completion
                    DataBaseHelper.getInstance(this).deleteAllExamSummary();
                    if(NetworkUtils.getConnectivityStatus(getApplicationContext()) != Constants.TYPE_NOT_CONNECTED){
                        onFeedRefreshed();
                        if(currentFragment instanceof ProfileFragment){
                            ((ProfileFragment) currentFragment).updateUserProfile();
                        }
                    }
                    break;
                case AllEvents.ACTION_REQUEST_FOR_PROFILE:
                    requestForProfile((HashMap<String, String>) event.getObj());
                    break;
                case AllEvents.ACTION_REMOVE_PROFILE_COMPLETION_CLICK:
                    if(currentFragment instanceof HomeFragment)
                        ((HomeFragment)currentFragment).removeProfileCompletionLayout();
                    break;
                case AllEvents.ACTION_REQUEST_SIMILAR_QUESTION:
                    mMakeNetworkCall(Constants.TAG_SIMILAR_QUESTIONS, ApiEndPonits.API_PERSONALIZE_QNA+"?question_ids="+event.getExtra(),null);
                    break;
                case AllEvents.ACTION_ANSWER_FOR_QUESTION:{
                    QnAQuestions qnAQuestions = (QnAQuestions) event.getObj();
                    String answerText  =  event.getExtra();
                    HashMap<String, String> params2 = new HashMap<>();
                    params2.put("answer_text", answerText);
                    this.mMakeNetworkCall(AllEvents.ACTION_ANSWER_FOR_QUESTION , qnAQuestions.getResource_uri() + "answer/", params2, Request.Method.POST);

                    if(this.mQuestionMapForAnswer == null)
                        this.mQuestionMapForAnswer = new HashMap<>();
                    this.mQuestionMapForAnswer.put(qnAQuestions.getId(),qnAQuestions);

                    break;
                }
                case AllEvents.ACTION_COURSE_FINALIZED:
                {
                    HashMap<String, String> params1 = new HashMap<>();
                    params1.put("preferred_course", (String) event.getObj());
                    this.requestForUserProfileUpdate(Constants.SET_SELECTED_COURSE,params1);
                    break;
                }
                case AllEvents.ACTION_USER_DP_CLICK:
                    sendDpClickEvent();
                    break;
                case AllEvents.ACTION_INSTITUTE_SHORTLIST:
                    requestInstituteShortlist();
                    break;
                default:
                    break;
            }
        }
    }
    private void parseSimilarQuestions(String response){
        try {
            String result = extractResults(response);
            ArrayList<QnAQuestions> questionLIst = (ArrayList<QnAQuestions>) JSON.std.listOfFrom(QnAQuestions.class, result);
            ((QnaQuestionDetailFragmentNew) currentFragment).updateSimilarQuestion(questionLIst);
        }catch (Exception e){
            Log.e(TAG, "exception occurred while parsing similar questions");
        }
    }

    private void onResponseAnswerForQuestion(String response) {
        if(this.mQuestionMapForAnswer == null)
            return;

        try {
            QnAAnswers qnaAnswer = JSON.std.beanFrom(QnAAnswers.class, response);
            if(qnaAnswer == null)
                return;

            String questionUrl = qnaAnswer.getQuestion();
            if(questionUrl == null)
                return;

            String questionUrlTags[] = questionUrl.split("/");
            String questionId = questionUrlTags[questionUrlTags.length-1];
            QnAQuestions question = mQuestionMapForAnswer.get(questionId);
            if(question != null) {
                question.getAnswer_set().add(qnaAnswer);
            }
            if(currentFragment instanceof  QnaQuestionDetailFragmentNew){
                ((QnaQuestionDetailFragmentNew) currentFragment).addNewAnswer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to handle response having exams
     * @param responseJson server response which contains exams list json
     */
    private void onResponseUserExamsList(String responseJson) {
        try {
            ArrayList<Exam> mExamList = (ArrayList<Exam>) JSON.std.listOfFrom(Exam.class, extractResults(responseJson));
            if (mExamList.size() <= 0) {
                Utils.DisplayToast(getApplicationContext(), "No Exams Found for your preferences");
                return;
            }
            if(currentFragment instanceof ProfileFragment
                    || currentFragment instanceof HomeFragment) {
                this.mDisplayFragment(ExamsFragment.newInstance(new ArrayList<>(mExamList)), true, ExamsFragment.class.getSimpleName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSubjectSelected(Subjects subject, int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(SyllabusUnitListFragment.class.getSimpleName());
        if (fragment == null)
            this.mDisplayFragment(SyllabusUnitListFragment.newInstance(subject.getUnits()), true, SyllabusUnitListFragment.class.getSimpleName());
        else
            this.mDisplayFragment(fragment, false, SyllabusUnitListFragment.class.getSimpleName());

    }

    @Override
    public void onSyllabusChanged(JSONObject jsonObject) {

        String examId = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getString(Constants.SELECTED_EXAM_ID,  "");
        if(!examId.isEmpty()) {
            this.mMakeJsonObjectNetworkCall(Constants.SUBMITTED_CHAPTER_STATUS+"#"+examId, ApiEndPonits.BASE_URL + "yearly-exams/" + examId + "/syllabus/", jsonObject, 1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mYear = (String)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onFetchQuestions(String tag, String url) {
        this.mMakeNetworkCall(tag, url, null, Request.Method.GET);
    }

    @Override
    public void onSBSTestFinish(String url, JSONObject answerObject) {
        this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_SBS_EXAM, url, answerObject, Request.Method.POST);
    }

    @Override
    public void onItemSelected(int position) {
        if(this.myAlertsList!=null && !this.myAlertsList.isEmpty()){
            this.mDisplayFragment(UserAlertsParentFragment.newInstance(position,new ArrayList<>(this.myAlertsList)),true,UserAlertsParentFragment.class.toString());
        }
    }


    @Override
    public void OnCDRecommendedInstituteSelected(Institute institute) {
        this.mDisplayInstituteByEntity(institute, Constants.CDRecommendedInstituteType.RECOMMENDED);
        Log.e("CD-RE", "Selected:CD Reco Institute is : " + institute.getId());
    }

    @Override
    public void OnCDRecommendedInstituteLiked(Institute institute, boolean isLastCard, String cardCategory, String nextUrl) {
        this.mInstitute = institute;
        Log.e("CD-RE", "Like:CD Reco Institute is : " + institute.getId());
        HashMap<String, String> params = new HashMap<>();
        params.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
        params.put("action", String.valueOf("1"));
        if (MainActivity.mProfile.getIs_verified() != ProfileMacro.NUMBER_VERIFIED){
            Constants.IS_CAF_LOADED = true;
        }
        mDisplayOtpVerificationFragment();
        this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_SHORTLIST_INSTITUTE + "#" + isLastCard + "#" + cardCategory + "#" + nextUrl, institute.getResource_uri() + "shortlist/", params, Request.Method.POST);
    }

    @Override
    public void OnCDRecommendedInstituteDislike(Institute institute, boolean isLastCard, String cardCategory, String nextUrl) {
        this.mInstitute = institute;
        Log.e("CD-RE", "Dislike:CD Reco Institute is : " + institute.getId());
        HashMap<String, String> params = new HashMap<>();
        params.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
        params.put("action", String.valueOf("2"));
        this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE + "#" + isLastCard + "#" + cardCategory + "#" + nextUrl, institute.getResource_uri() + "shortlist/", params, Request.Method.POST);
    }

    @Override
    public void OnCDRecommendedInstituteDecideLater(Institute institute, boolean isLastCard, String cardCategory, String nextUrl) {
        this.mInstitute = institute;
        Log.e("CD-RE", "Decide Later:CD Reco Institute is : " + institute.getId());
        HashMap<String, String> params = new HashMap<>();
        params.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
        params.put("action", String.valueOf("3"));
        this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE + "#" + isLastCard + "#" + cardCategory + "#" + nextUrl, institute.getResource_uri() + "shortlist/", params, Request.Method.POST);
    }

    @Override
    public void OnCDRecommendedLoadUndecidedInstitutes(String url) {
        this.mMakeNetworkCall(Constants.TAG_LOAD_UNDECIDED_INSTITUTE, url, null, Request.Method.GET);
    }

    @Override
    public void OnCDRecommendedLoadMoreBuzzlist() {
        this.mMakeNetworkCall(Constants.CARD_BUZZLIST_INSTITUTES, ApiEndPonits.API_BUZZLIST_INSTITUTES,null);
    }
    @Override
    public void OnAppliedInstitute(Institute institute, boolean islastcard, String cardCategory) {
        this.mInstitute = institute;
        Map<String, Object> eventValue = new HashMap<>();
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        if(institute.getGroups_exists() == 1)
        {
            eventValue.put(getString(R.string.APPLY_INSTITUTE), Constants.CDInstituteType.PARTNER.toString());

            String cafUrl = Constants.CAF_URL + "?institute_id=" + institute.getId() + "&&user_id=" + MainActivity.mProfile.getId();

            Utils.DisplayToast(getApplicationContext(),"College added to shortlist");
            onDisplayWebFragment(cafUrl);

            HashMap<String, String> params = new HashMap<>();
            params.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
            params.put("action", String.valueOf("1"));

            this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_APPLIED_SHORTLIST_INSTITUTE + "#" + islastcard + "#" + cardCategory + "#null" , institute.getResource_uri() + "shortlist/", params, Request.Method.POST);
        }
        else
        {
            eventValue.put(getString(R.string.APPLY_INSTITUTE), Constants.CDInstituteType.NON_PARTNER.toString());
            requestForApplyInstitute(Constants.TAG_WISH_LIST_APPLIED_COURSE,new HashMap<String, String>(),Constants.TAG_RECOMMENDED_APPLIED_SHORTLIST_INSTITUTE + "#" + islastcard + "#" + cardCategory + "#null");
        }

        if(this.mInstitute != null) {
            eventValue.put(getString(R.string.APPLY_INSTITUTE_FROM_WISHLIST), this.mInstitute.getResource_uri());
            SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_COURSE_APPLIED), eventValue, MainActivity.this);
        }
//        Log.e("DEBUG_APPLIED",institute.getName());
    }

    @Override
    public void onClickBuzzList() {
        Map<String, String> params = this.mGetTheFilters();

        if(this.mExamTag != null && !this.mExamTag.isEmpty())
            params.put("tag_uris[" + (params.size()) + "]", this.mExamTag);

        this.mMakeNetworkCall(Constants.CARD_BUZZLIST_INSTITUTES, ApiEndPonits.API_BUZZLIST_INSTITUTES, params);
    }

    @Override
    public void onClickWishList() {
        this.mMakeNetworkCall(Constants.CARD_SHORTLIST_INSTITUTES, ApiEndPonits.API_SHORTLISTED_INSTITUTES, null);
    }

    @Override
    public void onClickRecommendedList() {
        onHomeItemSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, ApiEndPonits.API_RECOMMENDED_INSTITUTES, mExamTag);
    }

    @Override
    public void OnCDRecommendedLoadNext() {
        Map<String , String> params = this.mGetTheFilters();
        if(this.mExamTag != null && !this.mExamTag.isEmpty())
            params.put("tag_uris[" + (params.size()) + "]", this.mExamTag);
        this.mMakeNetworkCall(Constants.WIDGET_RECOMMENDED_INSTITUTES + "#" + "next", ApiEndPonits.API_RECOMMENDED_INSTITUTES, params);
    }

    @Override
    public void onCourseApplied(final InstituteCourse instituteCourse) {
        if(instituteCourse == null)
            return;

        if (mInstitute != null  && mInstitute.getGroups_exists()==1 ) {
            String cafUrl = Constants.CAF_URL + "?institute_id=" + mInstitute.getId() + "&&user_id=" + MainActivity.mProfile.getId();
            onDisplayWebFragment(cafUrl);
            return;
        }

        final HashMap<String, String> params = new HashMap<>();
        params.put(getString(R.string.APPLY_COURSE), "" + instituteCourse.getId());

        requestForApplyInstitute( Constants.TAG_APPLIED_COURSE,params, "");

        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(instituteCourse.getId()));
        eventValue.put(getString(R.string.APPLY_COURSE), instituteCourse.getName());
        if (mInstitute != null)
            eventValue.put(getString(R.string.APPLY_INSTITUTE), mInstitute.getResource_uri());
        eventValue.put(getString(R.string.APPLY_COURSE_ID), String.valueOf(instituteCourse.getId()));

        //Events
        SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_COURSE_APPLIED), eventValue, this);

    }

    private void requestForApplyInstitute(final String TAG , final  HashMap<String, String> params, final String ShortlistedTag) {

        if(mProfile == null)
            return;

        if (mInstitute != null) {
            params.put("institute", String.valueOf(mInstitute.getId()));
        }

        String name = mProfile.getName();
        String email = mProfile.getEmail();
        String phone = mProfile.getPhone_no();

        // get user name for apply course
        if (name == null || name.isEmpty() || name.equalsIgnoreCase(getString(R.string.ANONYMOUS_USER)))
        {
            name = getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).getString(mResources.getString(R.string.user_apply_course_name), "");
        }
        // get user email for apply course
        if (email == null || email.isEmpty() || email.contains("@anonymouscollegedekho.com")){
            email = getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).getString(mResources.getString(R.string.user_apply_course_email),"");

            if(email.isEmpty())
                email = Utils.getDeviceEmail(getApplicationContext());
        }
        // get user email for apply course
        if (phone == null || phone.isEmpty()){
            if(mDeviceProfile != null && mDeviceProfile.getPrimaryPhone() != null && !mDeviceProfile.getPrimaryPhone().isEmpty())
                phone = mDeviceProfile.getPrimaryPhone();
            else
                phone = getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).getString(mResources.getString(R.string.user_apply_course_phone),"");
        }

        if ( name.isEmpty() || phone.length() < 10 || email == null || email.isEmpty()) {
            final View view = getLayoutInflater().inflate(R.layout.dialog_apply, null, false);
            ((TextView) view.findViewById(R.id.apply_name)).setText(name);
            ((TextView) view.findViewById(R.id.apply_email)).setText(email);
            ((TextView) view.findViewById(R.id.apply_phone)).setText(phone);
            Spinner datePicker = (Spinner) view.findViewById(R.id.apply_year);
            datePicker.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner, getYears()));
            datePicker.setOnItemSelectedListener(this);
            final Dialog apply = new android.app.AlertDialog.Builder(this)
                    .setView(view)
                    .create();
            apply.setCanceledOnTouchOutside(false);
            if (!MainActivity.this.isFinishing()) {
                apply.show();
            }
            (view.findViewById(R.id.submit_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    String name = ((TextView) view.findViewById(R.id.apply_name)).getText().toString().trim();
                    String email = ((TextView) view.findViewById(R.id.apply_email)).getText().toString().trim();
                    String phone = ((TextView) view.findViewById(R.id.apply_phone)).getText().toString().trim();
                    if (name.isEmpty()) {
                        displayMessage(R.string.NAME_EMPTY);
                        return;
                    } else if (!Utils.isValidName(name)) {
                        displayMessage(R.string.NAME_INVALID);
                        return;
                    }  else if (email.isEmpty()) {
                        displayMessage(R.string.EMAIL_EMPTY);
                        return;
                    } else if (!Utils.isValidEmail(email)) {
                        displayMessage(R.string.EMAIL_INVALID);
                        return;
                    }else if (phone.isEmpty()) {
                        displayMessage(R.string.PHONE_EMPTY);
                        return;
                    } else if (phone.length() <= 9 || phone.length() > 12 || !Utils.isValidPhone(phone)) {
                        displayMessage(R.string.PHONE_INVALID);
                        return;
                    }

                    getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putString(mResources.getString(R.string.user_apply_course_name),name).apply();
                    getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putString(mResources.getString(R.string.user_apply_course_email),email).apply();
                    getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putString(mResources.getString(R.string.user_apply_course_phone),phone).apply();
                    apply.dismiss();
                    params.put(getString(R.string.USER_NAME), name);
                    params.put(getString(R.string.USER_EMAIL), email);
                    params.put(getString(R.string.USER_PHONE), phone);
                    params.put(getString(R.string.APPLY_YEAR), mYear);
                    mMakeNetworkCall( TAG, ApiEndPonits.API_LMS , params, Request.Method.POST);

                    // update mDeviceProfile profile  also with apply form data
                    final HashMap<String, String> profileParams = new HashMap<>();

                    if(mProfile.getName().equalsIgnoreCase(getString(R.string.ANONYMOUS_USER)))
                        profileParams.put(getString(R.string.USER_NAME), name);

                    if(mProfile.getIs_anony() == ProfileMacro.ANONYMOUS_USER)
                        profileParams.put(getString(R.string.USER_EMAIL), email);

                    if(mProfile.getPhone_no() == null ||  mProfile.getPhone_no().length() < 10)
                        profileParams.put(getString(R.string.USER_PHONE), phone);

                    requestForProfile(profileParams);

                    // remove top card from CD reco and shortlist after  successfully  applying for the institute
                    if (currentFragment != null && currentFragment instanceof CDRecommendedInstituteFragment) {
                        ((CDRecommendedInstituteFragment) currentFragment).removeTopCard(mInstitute);

                        HashMap<String, String> shortlistParams = new HashMap<>();
                        shortlistParams.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
                        shortlistParams.put("action", String.valueOf("1"));

                        mMakeNetworkCall(ShortlistedTag , mInstitute.getResource_uri() + "shortlist/", shortlistParams, Request.Method.POST);
                    }
                }
            });

            (view.findViewById(R.id.cancel_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apply.dismiss();
                    if (currentFragment != null && currentFragment instanceof InstituteDetailFragment)
                        ((InstituteDetailFragment) currentFragment).cancleAppliedRequest();
                }
            });
        } else {
            params.put(getString(R.string.USER_NAME), name);
            params.put(getString(R.string.USER_EMAIL), email);
            params.put(getString(R.string.USER_PHONE), phone);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            params.put(getString(R.string.APPLY_YEAR), "" + year);
            this.mMakeNetworkCall( TAG,ApiEndPonits.API_LMS, params, Request.Method.POST);

            // remove top card from CD reco after  successfully  applying for the institute
            if (currentFragment != null && currentFragment instanceof CDRecommendedInstituteFragment) {
                ((CDRecommendedInstituteFragment) currentFragment).removeTopCard(mInstitute);

                HashMap<String, String> shortlistparams = new HashMap<>();
                shortlistparams.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
                shortlistparams.put("action", String.valueOf("1"));

                mMakeNetworkCall(ShortlistedTag , mInstitute.getResource_uri() + "shortlist/", shortlistparams, Request.Method.POST);

            }
        }
    }
    @Override
    public void requestForCoursesUpdate(){
        if(mInstitute != null)
            this.mMakeNetworkCall(Constants.TAG_LOAD_COURSES, ApiEndPonits.BASE_URL + "institutecourses/" + "?institute=" + mInstitute.getId(), null);
    }

    @Override
    public void OnInstituteAppliedFromDetail(Institute institute) {
        this.mInstitute = institute;
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        if(institute.getGroups_exists()==1) {
            String cafUrl = Constants.CAF_URL + "?institute_id=" + institute.getId() + "&&user_id=" + MainActivity.mProfile.getId();
            onDisplayWebFragment(cafUrl);
        }else {
            requestForApplyInstitute(Constants.TAG_WISH_LIST_APPLIED_COURSE + "#" + "-1", new HashMap<String, String>(),Constants.TAG_RECOMMENDED_APPLIED_SHORTLIST_INSTITUTE);

            if(this.mInstitute != null) {
                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(getString(R.string.APPLY_INSTITUTE_FROM_WISHLIST), mInstitute.getResource_uri());
                SendAppEvent(getString(R.string.CATEGORY_INSTITUTES), getString(R.string.ACTION_COURSE_APPLIED), eventValue, MainActivity.this);
            }
        }
    }

    @Override
    public void OnInstituteRemovedFromDetail(Institute institute) {
        this.mInstitute = institute;
        if (this.mInstitute != null) {
            this.mMakeNetworkCall(Constants.CARD_DELETE_SHORTLISTED_INSTITUTE + "#" + "-1" , institute.getResource_uri() + "shortlist/", null, Request.Method.DELETE);
        }
    }

    public ArrayList<String> getYears() {
        ArrayList<String> years = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 5; i++) {
            years.add("" + (year + i));
        }
        mYear = years.get(0);
        return years;
    }

    private List<VideoEntry> videoList;
    private InstituteVideosFragment videosFragment;

    @Override
    public void onUpdate(List<VideoEntry> videoList, String url, InstituteVideosFragment fragment) {
        videosFragment = fragment;
        if (videoList != null && !videoList.isEmpty()) {
            this.videoList = videoList;
            if (getConnectivityStatus(getApplicationContext()) != Constants.TYPE_NOT_CONNECTED) {
                mNetworkUtils.simpleGetData(Constants.TAG_UPDATE_VIDEO_TITLE, url);
            } else {
                displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            }

        }
    }

    public void onClickClose(@SuppressWarnings("unused") View view) {
        /*if (videosFragment != null) {
            videosFragment.onClickClose(view);
        }*/
    }

    private void onUpdateTitleResponse(String response) {
        if (this.videoList != null && response != null) {
            try {
                YoutubeVideoDetails details = JSON.std.beanFrom(YoutubeVideoDetails.class, response);
                ArrayList<Item> items = details.getItems();
                int detailsSize = items.size();
                int listSize = videoList.size();

                if (detailsSize == listSize) {
                    for (int i = 0; i < listSize; i++) {
                        Item item = items.get(i);
                        VideoEntry entry = videoList.get(i);
                        entry.setText(item.getSnippet().getTitle());
                        entry.setDuration(Utils.getTimeFromString(item.getContentDetails().getDuration()));
                        entry.setViewCount(Utils.formatCount(item.getStatistics().getViewCount(), 0));
                        entry.setPublishedOn(item.getSnippet().getPublishedAt());
                    }
                }
                if (videosFragment != null) {
                    videosFragment.updateVideosList(videoList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onRequestForOTP(HashMap<String, String> params) {
        this.mMakeNetworkCall(Constants.TAG_REQUEST_FOR_OTP, ApiEndPonits.API_SEND_OTP, params);
    }

    public void onVerifyOTP(HashMap<String, String> params) {
        this.onUserCommonLogin(params, Constants.TAG_PHONE_NUMBER_LOGIN);
    }

    private void onResponseForOTP() {
        if (currentFragment instanceof OTPVerificationFragment) {
            ((OTPVerificationFragment) currentFragment).displayOTPLayout();
        }else if (currentFragment instanceof PostAnonymousLoginFragment) {
            ((PostAnonymousLoginFragment) currentFragment).displayOTPLayout();
        }else if (currentFragment instanceof LoginForCounselorFragment) {
            ((LoginForCounselorFragment) currentFragment).displayOTPLayout();
        }else if (currentFragment instanceof LoginFragment) {
            ((LoginFragment) currentFragment).displayOTPLayout();
        }
    }

    private void mDisplayOtpVerificationFragment() {
        if (MainActivity.mProfile.getIs_verified() != ProfileMacro.NUMBER_VERIFIED && setupOtpRequest()) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(OTPVerificationFragment.class.getSimpleName());
            if (fragment == null)
                mDisplayFragment(OTPVerificationFragment.newInstance(), true, OTPVerificationFragment.class.getSimpleName());
            else
                mDisplayFragment(fragment, false, OTPVerificationFragment.class.getSimpleName());
        }
    }

    private boolean setupOtpRequest() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String today = df.format(calendar.getTime());
        String oldDate = getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).getString(getString(R.string.CAN_ASK_OTP_TODAY), null);
        if (oldDate != null && oldDate.equals(today)) {
            return false;
        }else {
            this.getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putString(getString(R.string.CAN_ASK_OTP_TODAY), today).apply();
            return true;
        }
    }

    public GoogleApiClient getGoogleClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onFeedSelected(Feed feed) {
        MainActivity.type = feed.getScreen();
        MainActivity.resource_uri = feed.getResource_uri();
        //TODO:: temporary adding v2 in qna url
        if( MainActivity.resource_uri != null && MainActivity.resource_uri.contains("/qna/")){
            MainActivity.resource_uri = MainActivity.resource_uri.replace("/qna/","/qna-v2/");
        }

        if (MainActivity.resource_uri.contains("?"))
            MainActivity.resource_uri_with_notification_id = MainActivity.resource_uri + "&feed_id=" + feed.getId();
        else
            MainActivity.resource_uri_with_notification_id = MainActivity.resource_uri + "?feed_id=" + feed.getId();

        //events params
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.TAG_RESOURCE_URI), MainActivity.resource_uri);
        eventValue.put(getString(R.string.TAG_FEED_TYPE), MainActivity.type);
        eventValue.put(getString(R.string.TAG_FEED_ID), feed.getId());

        //Events
        SendAppEvent(getString(R.string.CATEGORY_FEED), getString(R.string.ACTION_FEED_SELECTED), eventValue, this);
        this.mHandleNotifications();
    }

    @Override
    public void onFeedRefreshed() {
        this.mMakeNetworkCall(Constants.TAG_REFRESHED_FEED, ApiEndPonits.API_FEEDS, null);
    }


    public void onFeedAction(String type, HashMap<String, String> dataMap) {
        switch (type)
        {
            case Constants.RECOMMENDED_INSTITUTE_FEED_LIST:
            {
                switch (dataMap.get("feedActionType"))
                {
                    case Constants.FEED_RECO_ACTION:
                    {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
                        if (dataMap.get("action").equals(Constants.CDRecommendedInstituteType.SHORTLIST.toString()))
                        {
                            params.put("action", "1");
                        }
                        else if (dataMap.get("action").equals(Constants.CDRecommendedInstituteType.NOT_INTERESTED.toString()))
                        {
                            params.put("action", "2");

                        }
                        else if (dataMap.get("action").equals(Constants.CDRecommendedInstituteType.UNDECIDED.toString()))
                        {
                            params.put("action", "3");
                        }

                        if (dataMap.containsKey("institute"))
                        {
                            try {
                                Institute institute = JSON.std.beanFrom(Institute.class, dataMap.get("institute"));
                                this.mInstitute = institute;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        this.mMakeNetworkCall(Constants.TAG_FEED_ACTION + "#" + Constants.RECOMMENDED_INSTITUTE_FEED_LIST + "#" + Constants.FEED_RECO_ACTION + "#" + dataMap.get("action") + "#" + dataMap.get("feedPosition") + "#" + dataMap.get("position") + "#" + dataMap.get("id"), dataMap.get("url"), params);
                        break;
                    }
                    case Constants.FEED_SEE_ALL_ACTION:
                    {
                        this.mMakeNetworkCall(Constants.WIDGET_RECOMMENDED_INSTITUTES, ApiEndPonits.API_RECOMMENDED_INSTITUTES, null);
                        break;
                    }
                    case Constants.FEED_RECO_INSTITUTE_DETAILS_ACTION:
                    {
                        if (dataMap.containsKey("url"))
                            this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE, dataMap.get("url"), null);
                        break;
                    }
                    case Constants.FEED_SEE_SHORTLISTED_INSTITUTES:
                    {
                        this.mMakeNetworkCall(Constants.WIDGET_SHORTLIST_INSTITUTES, ApiEndPonits.API_SHORTLISTED_INSTITUTES, null);
                        break;
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        return Actions.newView("Main", "http://[ENTER-YOUR-URL-HERE]");
    }
/*

    private static class ContainerLoadedCallback implements ContainerHolder.ContainerAvailableListener {
        @Override
        public void onContainerAvailable(ContainerHolder containerHolder, String containerVersion) {
            // We load each container when it becomes available.
            Container container = containerHolder.getContainer();
            registerCallbacksForContainer(container);
        }

        public static void registerCallbacksForContainer(Container container) {
            // Register two custom function ic_call_vector macros to the container.
            container.registerFunctionCallMacroCallback("increment", new CustomMacroCallback());
            container.registerFunctionCallMacroCallback("mod", new CustomMacroCallback());
            // Register a custom function ic_call_vector tag to the container.
            container.registerFunctionCallTagCallback("custom_tag", new CustomTagCallback());
        }
    }
*/

    private static class CustomMacroCallback implements Container.FunctionCallMacroCallback {
        private int numCalls;

        @Override
        public Object getValue(String name, Map<String, Object> parameters) {
            if ("increment".equals(name)) {
                return ++numCalls;
            } else if ("mod".equals(name)) {
                return (Long) parameters.get("key1") % Integer.valueOf((String) parameters.get("key2"));
            } else {
                throw new IllegalArgumentException("Custom macro name: " + name + " is not supported.");
            }
        }
    }

    private static class CustomTagCallback implements Container.FunctionCallTagCallback {
        @Override
        public void execute(String tagName, Map<String, Object> parameters) {
            // The code for firing this custom tag.
            Log.i("CollegeDekho", "Custom function ic_call_vector tag :" + tagName + " is fired.");
        }
    }

    private CursorLoader getProfileLoader() {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(
                        ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
                DeviceProfile.ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE + " = ? OR " + ContactsContract.Contacts.Data.MIMETYPE + " = ?",
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    private void onNameUpdatedResponse(String response, String msg) {
        mParseProfileResponse(response);
        if (currentFragment instanceof MyFutureBuddiesFragment) {
            ((MyFutureBuddiesFragment) currentFragment).sendChatRequest(msg);
        }
        else if (currentSubFragment instanceof MyFutureBuddiesFragment) {
            ((MyFutureBuddiesFragment) currentSubFragment).sendChatRequest(msg);
        }
    }

    private void onTestCalendarResponse(String response) {

        try {
            this.chaptersList = JSON.std.listOfFrom(Chapters.class, response);
            if (currentFragment instanceof CalendarParentFragment) {
                ((CalendarParentFragment) currentFragment).updateCalander(new ArrayList<>(this.chaptersList));
                return;
            }
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(CalendarParentFragment.class.getSimpleName() );
            if(fragment == null)
                this.mDisplayFragment(CalendarParentFragment.newInstance(new ArrayList<>(this.chaptersList)), !isFromNotification, CalendarParentFragment.class.getSimpleName());
            else {
                this.mDisplayFragment(fragment, false, CalendarParentFragment.class.getSimpleName());
            }
        } catch (IOException e) {
            Log.e(TAG, "Exception occurred on display calender fragment");
        }
    }

    @Override
    public void onSubmitCalendarData(JSONObject object,String url) {
        String examId = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getString(Constants.SELECTED_EXAM_ID,  "");
        this.mMakeJsonObjectNetworkCall(Constants.TAG_PSYCHOMETRIC_RESPONSE+"#"+examId,ApiEndPonits.BASE_URL+url,object,1);
    }



    public void translateAnimation(View viewToMoveUp, View viewToMoveDown) {
        Animation translateUp= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        Animation translateDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);


        if(viewToMoveDown != viewToMoveUp) {
            if (viewToMoveUp != null) {
                viewToMoveUp.startAnimation(translateUp);
            }
            if (viewToMoveDown != null) {
                viewToMoveDown.startAnimation(translateDown);
            }
        }
    }


    private void onMyAlertsLoaded(String response) {
        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String result = JSON.std.asString(map.get("results"));
            this.myAlertsList = JSON.std.listOfFrom(MyAlertDate.class, result);
            this.displayAlerts(new ArrayList<>(this.myAlertsList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayAlerts(ArrayList<MyAlertDate> dates) {
        this.mDisplayFragment(UserAlertsFragment.newInstance(dates), !isFromNotification, UserAlertsFragment.class.toString());
    }

    public void adjustFontScale(Configuration configuration) {
        if (configuration.fontScale > 1.15) {
            configuration.fontScale = 1.20f;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
        }
    }


    private void showNewsByNotification(String response) {
        try {
            List<News> newsList = JSON.std.listOfFrom(News.class, "[" + response + "]");
            this.mParseSimilarNews(newsList);
            if (newsList != null && !newsList.isEmpty()) {
                if (currentFragment instanceof NewsDetailFragment) {
                    (currentFragment).updateNews(newsList.get(0));
                }
                else {
                    boolean isAddToStack = false;
                    if(!isFromNotification){
                        isAddToStack = true;
                    }
                    if(this.mNewsList == null){
                        this.mNewsList = new ArrayList<>();
                    }
                    Fragment fragment = NewsDetailFragment.newInstance(newsList.get(0), new ArrayList<>(this.mNewsList));
                    this.mDisplayFragment(fragment, isAddToStack, Constants.TAG_FRAGMENT_NEWS_DETAIL);
                }
            } else {
                isFromNotification = false;
                mLoadUserStatusScreen();
            }
        } catch (IOException e) {
            e.printStackTrace();
            isFromNotification = false;
            mLoadUserStatusScreen();
        }
    }

    private void showArticleByNotification(String response) {
        try {
            List<Articles> articlesList = JSON.std.listOfFrom(Articles.class, "[" + response + "]");
            this.mParseSimilarArticle(articlesList);
            if (articlesList != null && !articlesList.isEmpty()) {
                if (currentFragment instanceof ArticleDetailFragment) {
                    (currentFragment).updateArticle(articlesList.get(0));
                }
                else {
                    boolean isAddToStack = false;
                    if(!isFromNotification){
                        isAddToStack = true;
                    }
                    if(this.mArticlesList == null){
                        this.mArticlesList = new ArrayList<>();
                    }
                    Fragment fragment = ArticleDetailFragment.newInstance(articlesList.get(0), this.mArticlesList);
                    this.mDisplayFragment(fragment, isAddToStack, Constants.TAG_FRAGMENT_ARTICLE_DETAIL);
                }
            } else {
                isFromNotification = false;
                mLoadUserStatusScreen();
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            isFromNotification = false;
            mLoadUserStatusScreen();
        }
    }

    private void onPnsInstituteNews(String response) {
        try {
            List<Institute> instituteList = JSON.std.listOfFrom(Institute.class, "[" + response + "]");
            if (instituteList != null && !instituteList.isEmpty()) {
                this.mInstitute = instituteList.get(0);
                int id = instituteList.get(0).getId();
                int forum_id = instituteList.get(0).getForum_id();
                boolean isAddToStack = false;
                if(!isFromNotification){
                    isAddToStack = true;
                }

                if(currentFragment instanceof InstituteDetailFragment){
                    ((InstituteDetailFragment) currentFragment).updateInstitutedFromNotification(this.mInstitute, Constants.CDRecommendedInstituteType.RECOMMENDED);
                }else {
                    Fragment fragment = InstituteDetailFragment.newInstance(this.mInstitute, Constants.CDRecommendedInstituteType.RECOMMENDED);
                    this.mDisplayFragment(fragment, isAddToStack, Constants.TAG_FRAGMENT_INSTITUTE);
                }
                this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_NEWS, ApiEndPonits.BASE_URL + "personalize/news/" + "?institute=" + String.valueOf(id), null);
                this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_ARTICLE, ApiEndPonits.BASE_URL + "personalize/articles/" + "?institute=" + String.valueOf(id), null);
                this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_QNA_QUESTIONS,ApiEndPonits.BASE_URL +"personalize/qna-v2/?institute=" + String.valueOf(id),null);
                //TODO :: network Call for Institutes QNAs
//                this.mMakeNetworkCall(Constants.TAG_INSTITUTE_FORUM,ApiEndPonits.BASE_URL +"personalize/qna-v2/?institute="+String.valueOf(id)+"&source=1",null);
                //TODO :: network Call for Institutes Forum
            } else {
                isFromNotification = false;
                mLoadUserStatusScreen();
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            isFromNotification = false;
            mLoadUserStatusScreen();
        }
    }

    private void onPnsMyFutureBuddy(String response) {
        try {
            this.mFB = this.mParseAndPopulateMyFB(response, 0);
            boolean isAddToStack = false;
            if(!isFromNotification){
                isAddToStack = true;
            }
            if(currentFragment instanceof MyFutureBuddiesFragment){
                ((MyFutureBuddiesFragment) currentFragment).updateMyFBFromNotification(this.mFB);
            }else if(currentSubFragment instanceof MyFutureBuddiesFragment){
                ((MyFutureBuddiesFragment) currentSubFragment).updateMyFBFromNotification(this.mFB);
            }else {
                Fragment fragment = MyFutureBuddiesFragment.newInstance(this.mFB, 0);
                this.mDisplayFragment(fragment, isAddToStack, MyFutureBuddiesFragment.class.getSimpleName());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            isFromNotification = false;
            mLoadUserStatusScreen();
        }
    }

    private void showQuestionByNotification(String response) {
        try {
            QnAQuestions qnaQuestion = JSON.std.beanFrom(QnAQuestions.class,response);
            boolean isAddToStack = false;
            if (!this.isFromNotification) {
                isAddToStack = true;
            }
            if(this.mQnAQuestions == null)
                this.mQnAQuestions = new ArrayList<>();
            else
                this.mQnAQuestions.clear();

            this.mQnAQuestions.add(qnaQuestion);
            if (currentFragment instanceof QnaQuestionDetailFragmentNew) {
                ((QnaQuestionDetailFragmentNew) currentFragment).updateQuestion(qnaQuestion);
            } else {
                Fragment fragment = QnaQuestionDetailFragmentNew.getInstance(qnaQuestion);
                this.mDisplayFragment(fragment, isAddToStack, getString(R.string.TAG_FRAGMENT_QNA_QUESTION_DETAIL));
            }
        }catch (Exception e) {
            e.printStackTrace();
            isFromNotification = false;
            mLoadUserStatusScreen();
        }
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        String mSearchString = "";

        public boolean onQueryTextChange(String newText) {
            mSearchString = newText;
            if (!mSearchString.trim().matches("")) {
                try {
                    mSearchString = URLEncoder.encode(newText, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                doSearches(mSearchString);
                stopSearchProgress();
                startSearchProgress();
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            mSearchString = query;
            if (!mSearchString.trim().matches("")) {
                try {
                    mSearchString = URLEncoder.encode(query, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                doSearches(mSearchString);
            }
            return true;
        }
    };

    SearchView.OnCloseListener queryCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            closeSearch();
            return false;
        }
    };

    private void doSearch(String searchString) {

        if (currentFragment != null && currentFragment instanceof InstituteListFragment) {
            this.mExamTag = "";
            this.mMakeNetworkCall(Constants.SEARCH_INSTITUTES, ApiEndPonits.API_COLLEGE_SEARCH + searchString+"/" , null);
        } else if (currentFragment != null && currentFragment instanceof QnAQuestionsListFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_QNA, ApiEndPonits.API_QNA_SEARCH + searchString + "/", null);
        } else if (currentFragment != null && currentFragment instanceof NewsFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_NEWS, ApiEndPonits.API_NEWS_SEARCH + searchString + "/", null);
        } else if (currentFragment != null && currentFragment instanceof ArticleFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_ARTICLES, ApiEndPonits.API_ARTICLES_SEARCH + searchString + "/", null);
        } else if ((currentFragment != null && currentFragment instanceof HomeFragment)) {
            this.mExamTag = "";
            this.mMakeNetworkCall(Constants.SEARCH_INSTITUTES, ApiEndPonits.API_COLLEGE_SEARCH + searchString + "/", null);
        } else if ((currentFragment != null && currentFragment instanceof CourseSelectionFragment)) {
            this.mExamTag = "";
            this.mMakeNetworkCall(Constants.SEARCH_COURSES, ApiEndPonits.API_COURSE_SEARCH + searchString + "/", null);
        }
    }

    private void closeSearch() {

        if (currentFragment != null && currentFragment instanceof InstituteListFragment) {
            this.mMakeNetworkCall(Constants.SEARCH_INSTITUTES, ApiEndPonits.API_PERSONALIZE_INSTITUTES, null);
        } else if (currentFragment != null && currentFragment instanceof QnAQuestionsListFragment) {
            this.mMakeNetworkCall(Constants.SEARCH_QNA, ApiEndPonits.API_PERSONALIZE_QNA, null);
        } else if (currentFragment != null && currentFragment instanceof NewsFragment) {
            this.mMakeNetworkCall(Constants.SEARCH_NEWS, ApiEndPonits.API_PERSONALIZE_NEWS, null);
        } else if (currentFragment != null && currentFragment instanceof ArticleFragment) {
            this.mMakeNetworkCall(Constants.SEARCH_ARTICLES, ApiEndPonits.API_PERSONALIZE_ARTICLES, null);
        }
    }

    Runnable searchRunnable;
    Runnable searchProgressRunnable;

    private void doSearches(final String query) {
        if (searchRunnable != null) {
            searchHandler.removeCallbacks(searchRunnable);
        }
        searchRunnable = new Runnable() {
            @Override
            public void run() {
                doSearch(query);
            }
        };
        searchHandler.postDelayed(searchRunnable, 1000);
    }


    private void startSearchProgress() {
        mSearchProgress.setVisibility(View.VISIBLE);
        searchProgressRunnable = new Runnable() {
            @Override
            public void run() {
                if(mSearchProgress.getProgress()<100) {
                    mSearchProgress.setProgress(mSearchProgress.getProgress() + 10);
                    searchProgressHandler.postDelayed(searchProgressRunnable, 100);
                }else {
                    stopSearchProgress();
                }
            }
        };
        searchProgressHandler.post(searchProgressRunnable);
    }

    private void stopSearchProgress(){
        if (searchProgressRunnable != null) {
            mSearchProgress.setProgress(0);
            searchProgressHandler.removeCallbacks(searchProgressRunnable);
            mSearchProgress.setVisibility(View.GONE);
        }
    }

    Handler searchHandler = new Handler();
    Handler searchProgressHandler=new Handler();
    private void onNewsSearchResult(String response) {
        try {
            this.mNewsList = JSON.std.listOfFrom(News.class, extractResults(response));
            this.mParseSimilarNews(this.mNewsList);

            if (this.mNewsList != null && currentFragment instanceof NewsFragment) {
                ((NewsFragment) currentFragment).updateNewsList(new ArrayList<>(this.mNewsList), this.next);
            }
            if (this.mNewsList != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteNews(new ArrayList<>(this.mNewsList), this.next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void onQnASearchResult(String response) {
        ArrayList<QnAQuestions> qnaQuestionsList = parseAndReturnQnAList(response, true);

        if (currentFragment instanceof QnAQuestionsListFragment) {
            ((QnAQuestionsListFragment) currentFragment).updateList(new ArrayList<>(qnaQuestionsList), next);

        }
    }

    private void onInstituteSearchResult(String response) {

        mCurrentTitle = "Institutes";
        mParseInstituteListResponse(response);

        if (currentFragment instanceof InstituteListFragment) {
            ((InstituteListFragment) currentFragment).updateSearchList(this.mInstituteList, next);
        } else {
            mDisplayInstituteList(response, false, true, Constants.INSTITUTE_SEARCH_TYPE);
        }
    }

    private void onArticleSearchResult(String response) {
        try {
            this.mArticlesList = JSON.std.listOfFrom(Articles.class, extractResults(response));

            this.mParseSimilarArticle(this.mArticlesList);
            if (this.mArticlesList != null && currentFragment instanceof ArticleFragment) {
                ((ArticleFragment) currentFragment).updateArticleList(new ArrayList<>(this.mArticlesList), next);
            }

            if (this.mArticlesList != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteArticle(new ArrayList<>(this.mArticlesList), this.next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText || v instanceof SearchView.SearchAutoComplete) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if (v instanceof SearchView.SearchAutoComplete) {
                    ((SearchView.SearchAutoComplete) v).performCompletion();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Map<String, Object> eventValue = new HashMap<>();
        switch (requestCode) {
            case Constants.RC_HANDLE_CONTACTS_PERM:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //events params
                    eventValue.put(getString(R.string.ACTION_CONTACT_PERMISSION_ALLOW), getString(R.string.ACTION_CONTACT_PERMISSION_ALLOW));
                    // start curser loader if not started with id "0"
                    getSupportLoaderManager().initLoader(0, null, this);
                }else{
                    //events params
                    eventValue.put(getString(R.string.ACTION_CONTACT_PERMISSION_DENY), getString(R.string.ACTION_CONTACT_PERMISSION_DENY));
                }
                break;
            case Constants.RC_HANDLE_STORAGE_PERM:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(currentFragment instanceof ProfileFragment)
                        ((ProfileFragment) currentFragment).mRequestForImageCapture();
                    //events params
                    eventValue.put(getString(R.string.ACTION_MEDIA_ACCESS_PERMISSION_ALLOW), getString(R.string.ACTION_MEDIA_ACCESS_PERMISSION_ALLOW));
                }else {
                    showPermissionErrorDialog("Permission Not Granted, You can't upload profile image without this permission");
                    //events params
                    eventValue.put(getString(R.string.ACTION_MEDIA_ACCESS_PERMISSION_DENY), getString(R.string.ACTION_MEDIA_ACCESS_PERMISSION_DENY));
                }
                break;
            case Constants.RC_HANDLE_SMS_PERM:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(currentFragment instanceof  OTPVerificationFragment){
                        ((OTPVerificationFragment) currentFragment).onRequestForOTP();
                    }else if(currentFragment instanceof PostAnonymousLoginFragment){
                        ((PostAnonymousLoginFragment) currentFragment).onRequestForOTP();
                    }else if(currentFragment instanceof LoginForCounselorFragment){
                        ((LoginForCounselorFragment) currentFragment).onRequestForOTP();
                    }else if(currentFragment instanceof LoginFragment){
                        ((LoginFragment) currentFragment).onRequestForOTP();
                    }else if(currentFragment instanceof HomeFragment){
                        if(mProfile.getPhone_no() != null  && mProfile.getPhone_no().length() == 10
                                && mProfile.getIs_verified() != ProfileMacro.NUMBER_VERIFIED) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put(getString(R.string.USER_PHONE), mProfile.getPhone_no());
                            onRequestForOTP(params);
                        }

                    }
                    //events params
                    eventValue.put(getString(R.string.ACTION_SMS_PERMISSION_ALLOW), getString(R.string.ACTION_SMS_PERMISSION_ALLOW));
                }else{
                    showPermissionErrorDialog("Permission Not Granted, OTP will not be receive without this permission");
                    //events params
                    eventValue.put(getString(R.string.ACTION_SMS_PERMISSION_DENY), getString(R.string.ACTION_SMS_PERMISSION_DENY));
                }
                break;
            case Constants.RC_HANDLE_LOCATION:
                if (grantResults.length > 0    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (currentFragment instanceof HomeFragment){
                        ((HomeFragment) currentFragment).askForLocationSetting();
                    }
                    if (currentFragment instanceof ProfileFragment){
                        ((ProfileFragment) currentFragment).askForLocationSetting();
                    }
                    //events params
                    eventValue.put(getString(R.string.ACTION_LOCATION_PERMISSION_ALLOW), getString(R.string.ACTION_LOCATION_PERMISSION_ALLOW));
                }else{
                    if(currentFragment instanceof  HomeFragment)
                       ((HomeFragment) currentFragment).requestForYearlyExams();
                    if(currentFragment instanceof  ProfileFragment)
                        ((ProfileFragment) currentFragment).requestForYearlyExams();
                    //events params
                    eventValue.put(getString(R.string.ACTION_LOCATION_PERMISSION_DENY), getString(R.string.ACTION_LOCATION_PERMISSION_DENY));
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        //Events
        if(eventValue.size() > 0) {
            SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_EVENT_PERMISSION), eventValue, this);
        }
    }
    private void showPermissionErrorDialog(String msg){
        if (!MainActivity.this.isFinishing()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("CollegeDekho")
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }


    private void reStartApplication(){
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    BroadcastReceiver appLinkReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Constants.CONTENT_LINK_FILTER:
                    String link=intent.getStringExtra("captured_link");
                    if(link!=null && link.length()>0){
                        if(link.lastIndexOf("search%3D")!=-1) {
                            link = link.substring(0, link.length() - 1);
                            String searchString = link.substring(link.lastIndexOf("/") + 1, link.length());
                            mMakeNetworkCall(Constants.SEARCH_INSTITUTES, ApiEndPonits.BASE_URL + "colleges/" + searchString, null);
                            if (searchString != null && searchString.length() > 0 && searchString.startsWith("search%3D")) {
                                searchString = searchString.replace("search%3D", "");
                                try {
                                    searchString = URLDecoder.decode(searchString, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                if (menu != null) {
                                    menu.setGroupVisible(R.id.search_menu_group, true);
                                }
                                mSearchView.onActionViewExpanded();
                                mSearchView.setQuery(searchString, false);
                                mSearchView.clearFocus();
                            }
                        }
                        else if(link.lastIndexOf("search=")!=-1){
                            link = link.substring(0, link.length() - 1);
                            String searchString = link.substring(link.lastIndexOf("/") + 1, link.length());
                            mMakeNetworkCall(Constants.SEARCH_INSTITUTES, ApiEndPonits.BASE_URL + "colleges/" + searchString, null);
                            if (searchString != null && searchString.length() > 0 && searchString.startsWith("search=")) {
                                searchString = searchString.replace("search=", "");
                                try {
                                    searchString = URLDecoder.decode(searchString, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                if (menu != null) {
                                    menu.setGroupVisible(R.id.search_menu_group, true);
                                }
                                mSearchView.onActionViewExpanded();
                                mSearchView.setQuery(searchString, false);
                                mSearchView.clearFocus();
                            }
                        }
                        else if(link.lastIndexOf("/colleges/")!=-1){
                            String collegeId = link.substring(link.lastIndexOf("/")+1,link.length());
                            mMakeNetworkCall(Constants.TAG_INSTITUTE_DETAILS, ApiEndPonits.API_INSTITUTE_BY_SLUG+collegeId+"/",null);
                        }
                        else if(link.lastIndexOf("/news/")!=-1) {
                            String[] newsUrlTags = link.split("/");
                            if (newsUrlTags != null) {
                                String newsSlug = newsUrlTags[newsUrlTags.length-1];
                                mMakeNetworkCall(Constants.PNS_NEWS, ApiEndPonits.API_NEWS_BY_SLUG + newsSlug + "/", null);
                            }
                        }
                        else if(link.lastIndexOf("/articles/")!=-1) {
                            String[] articleUrlTags = link.split("/");
                            if (articleUrlTags != null) {
                                String articleSlug = articleUrlTags[articleUrlTags.length-1];
                                mMakeNetworkCall(Constants.PNS_ARTICLES, ApiEndPonits.API_ARTICLE_BY_SLUG + articleSlug + "/", null);
                            }
                        }
                        else if(link.lastIndexOf("/qna/")!=-1) {
                            String[] qnaUrlTags = link.split("/");
                            if (qnaUrlTags != null) {
                                String qnaSlug = qnaUrlTags[qnaUrlTags.length-1];
                                mMakeNetworkCall(Constants.PNS_QNA, ApiEndPonits.API_QUESTION_BY_SLUG + qnaSlug + "/", null);
                            }
                        }
                    }
                    break;

                case Constants.NOTIFICATION_FILTER:
                    MainActivity.type=intent.getStringExtra("screen");
                    MainActivity.resource_uri=intent.getStringExtra("resource_uri");
                    if(MainActivity.resource_uri!=null && !MainActivity.resource_uri.trim().matches("") && MainActivity.type!=null && !MainActivity.type.trim().matches("")) {
                        MainActivity.this.mHandleNotifications();
                    }
                    break;
            }
        }
    };

    private void onInstituteDetailsLinkResponse(String response) {
        try {
            List<Institute> list = JSON.std.listOfFrom(Institute.class, "[" + response + "]");
            if (list != null && !list.isEmpty()) {
                Institute institute = list.get(0);
                this.mDisplayInstituteByEntity(institute, Constants.CDRecommendedInstituteType.RECOMMENDED);
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void onDisplayWebFragment(String url){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.ACTION_OPEN_WEB_URL);

        if(fragment != null && currentFragment instanceof WebViewFragment){
            ((WebViewFragment) currentFragment).loadUrl(url);
        }
        else {
            mDisplayFragment(WebViewFragment.newInstance(url), !isFromNotification, Constants.ACTION_OPEN_WEB_URL);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent == null)
            return;

        Bundle bundle=intent.getExtras();
        String action = intent.getAction();
        String data = intent.getDataString();
        String intentType = intent.getType();

        this.isFromNotification = false;
        if (action != null && action.equals(Intent.ACTION_VIEW) && data != null) {
            if (data.contains("collegedekho.com")) {
                this.mDeepLinkingURI = data;
                this.isFromDeepLinking = true;
                mHandleDeepLinking();
            }
        } else if (action != null && action.equals(Intent.ACTION_SEND)) {
            this.isFromDeepLinking = false;
            if ("text/plain".equals(intentType)) {
                this.mOtherAppSharedMessage = intent.getStringExtra(Intent.EXTRA_TEXT);
                this.mHandleOtherAppSharedMessage();
            } else {
                Toast.makeText(this, "Sorry!! Only text content can be shared", Toast.LENGTH_SHORT).show();
            }
            // isFromNotification = false;
        }else if(bundle != null) {
            if (bundle.containsKey("screen") && bundle.containsKey("resource_uri") && bundle.containsKey("notification_id")) {
                MainActivity.type = bundle.getString("screen");
                MainActivity.resource_uri = bundle.getString("resource_uri");
                MainActivity.resource_uri_with_notification_id = MainActivity.resource_uri + "?notification_id=" + bundle.getString("notification_id");

                //events params
                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(getString(R.string.TAG_RESOURCE_URI), MainActivity.resource_uri);
                eventValue.put(getString(R.string.TAG_NOTIFICATION_ID), bundle.getString("notification_id"));
                eventValue.put(getString(R.string.TAG_NOTIFICATION_TYPE), MainActivity.type);
                eventValue.put(getString(R.string.TAG_APP_STATUS), getString(R.string.TAG_APP_STATUS_ALREADY_RUNNING));

                //Events
                SendAppEvent(getString(R.string.CATEGORY_NOTIFICATIONS), getString(R.string.ACTION_NOTIFICATION_OPEN), eventValue, this);

                // changed by suresh
                /*if(getSupportFragmentManager().getBackStackEntryCount() >= 1){
                    this.mHandleNotifications();
                    isFromDeepLinking = false;
                    isFromNotification = false;
                }else {*/

                this.isFromDeepLinking = false;
                this.mHandleNotifications();  // change to true by suresh
                // }
            }
        }
    }

    public Institute getCurrentInstitute() {
        return mInstitute;
    }

    public void setCurrentInstitute(Institute mInstitute) {
        this.mInstitute = mInstitute;
    }

    public void speakMessageForAccessibility(String message) {
        if (message != null){
            AccessibilityManager am = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
            if(am != null){
                boolean isAccessibilityEnabled = am.isEnabled();
                boolean isExploreByTouchEnabled = am.isTouchExplorationEnabled();
                if (isAccessibilityEnabled && isExploreByTouchEnabled) {
                    mTextToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return !(currentFragment instanceof SplashFragment) || super.dispatchPopulateAccessibilityEvent(event);
    }

    public String getOtherAppSharedMessage(){
        return this.mOtherAppSharedMessage;
    }

    public void setOtherAppSharedMessage(String message){
        this.mOtherAppSharedMessage = message;
    }

    public void onHideFabMenu(){
        this.mFabMenu.animate()
                .translationY(mFabMenu.getHeight()+this.mFabMenuMargin)
                .setInterpolator(new AccelerateInterpolator(3))
                .start();
    }
    public void onShowFabMenu(){
        this.mFabMenu.animate()
                .translationY(0)
                .setInterpolator(new AccelerateInterpolator(3))
                .start();
    }

    private void mAskForNameAndPhone(){
        // show dialog for name if user name is not present
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_name_phone);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("Complete Your Profile");
        TextView submit = (TextView) dialog.findViewById(R.id.name_submit);
        final EditText nameEditText = (EditText) dialog.findViewById(R.id.user_name);
        final EditText phoneEditText = (EditText) dialog.findViewById(R.id.user_phone);

        String name = mProfile.getName();
        String phone = mProfile.getPhone_no();
        if (phone != null && phone.length() == 10){
            phoneEditText.setText(phone);
        }
        if(name != null && !name.isEmpty() && !name.equalsIgnoreCase(Constants.ANONYMOUS_USER)){
            nameEditText.setText(name);
        }
        if (!MainActivity.this.isFinishing()) {
            dialog.show();
        }
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameEditText.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneEditText.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString();
                if (name.length() <= 0) {
                    nameEditText.requestFocus();
                    nameEditText.setError(getString(R.string.NAME_EMPTY));
                    return;
                } else if (!Utils.isValidName(name)) {
                    nameEditText.requestFocus();
                    nameEditText.setError(getString(R.string.NAME_INVALID));
                    return;
                }
                String phone = phoneEditText.getText().toString();
                if ( phone.length() <= 0) {
                    phoneEditText.requestFocus();
                    phoneEditText.setError( getString(R.string.PHONE_EMPTY));
                    return;
                }else if (phone.length() <= 9 || !Utils.isValidPhone(phone)) {
                    phoneEditText.requestFocus();
                    phoneEditText.setError( getString(R.string.PHONE_INVALID));
                    return;
                }

                dialog.dismiss();
                HashMap<String, String> params = new HashMap<>();
                params.put(getString(R.string.USER_NAME), name);
                params.put(getString(R.string.USER_PHONE), phone);
                requestForUserProfileUpdate(AllEvents.ACTION_PROFILE_COMPLETION_CLICK,params);

            }
        });
    }

    private void onProfileCompletionResponse(String response) {
        mParseProfileResponse(response);
        if(mProfile == null)return;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.sms_permission)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS},
                                    Constants.RC_HANDLE_SMS_PERM);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            if (!MainActivity.this.isFinishing()) {
                builder.show();
            }

        } else if(mProfile.getPhone_no() != null  && mProfile.getPhone_no().length() == 10
                && mProfile.getIs_verified() != ProfileMacro.NUMBER_VERIFIED) {
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.USER_PHONE), mProfile.getPhone_no());
            onRequestForOTP(params);
        }
    }

    private void sendDpClickEvent()
    {
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.KEY_USER),this.mProfile.getId());

        //Events
        SendAppEvent(getString(R.string.CATEGORY_USER_DP), getString(R.string.ACTION_USER_DP_CLICK), eventValue, this);
    }

}


