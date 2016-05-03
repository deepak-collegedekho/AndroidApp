package com.collegedekho.app.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.DebugLogQueue;
import com.collegedekho.app.R;
import com.collegedekho.app.database.DataBaseHelper;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.Item;
import com.collegedekho.app.entities.MyAlertDate;
import com.collegedekho.app.entities.MyFutureBuddiesEnumeration;
import com.collegedekho.app.entities.MyFutureBuddy;
import com.collegedekho.app.entities.MyFutureBuddyComment;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.PsychometricTestQuestion;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.entities.StepByStepQuestion;
import com.collegedekho.app.entities.StepByStepResult;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.entities.Subjects;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.entities.UserEducation;
import com.collegedekho.app.entities.VideoEntry;
import com.collegedekho.app.entities.Widget;
import com.collegedekho.app.entities.YoutubeVideoDetails;
import com.collegedekho.app.fragment.ArticleDetailFragment;
import com.collegedekho.app.fragment.ArticleFragment;
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.fragment.CDRecommendedInstituteListFragment;
import com.collegedekho.app.fragment.CalendarParentFragment;
import com.collegedekho.app.fragment.ExamsFragment;
import com.collegedekho.app.fragment.FilterFragment;
import com.collegedekho.app.fragment.InstituteDetailFragment;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
import com.collegedekho.app.fragment.InstituteQnAFragment;
import com.collegedekho.app.fragment.InstituteVideosFragment;
import com.collegedekho.app.fragment.LoginFragment;
import com.collegedekho.app.fragment.LoginFragment1;
import com.collegedekho.app.fragment.MyFutureBuddiesEnumerationFragment;
import com.collegedekho.app.fragment.MyFutureBuddiesFragment;
import com.collegedekho.app.fragment.NewsDetailFragment;
import com.collegedekho.app.fragment.NewsFragment;
import com.collegedekho.app.fragment.NotPreparingFragment;
import com.collegedekho.app.fragment.OTPVerificationFragment;
import com.collegedekho.app.fragment.ProfileEditFragment;
import com.collegedekho.app.fragment.ProfileFragment;
import com.collegedekho.app.fragment.PsychometricStreamFragment;
import com.collegedekho.app.fragment.PsychometricTestParentFragment;
import com.collegedekho.app.fragment.QnAQuestionDetailFragment;
import com.collegedekho.app.fragment.QnAQuestionsListFragment;
import com.collegedekho.app.fragment.SplashFragment;
import com.collegedekho.app.fragment.StreamFragment;
import com.collegedekho.app.fragment.SyllabusSubjectsListFragment;
import com.collegedekho.app.fragment.SyllabusUnitListFragment;
import com.collegedekho.app.fragment.TabFragment;
import com.collegedekho.app.fragment.UserAlertsFragment;
import com.collegedekho.app.fragment.UserAlertsParentFragment;
import com.collegedekho.app.fragment.UserEducationFragment;
import com.collegedekho.app.fragment.WebViewFragment;
import com.collegedekho.app.fragment.pyschometricTest.PsychometricQuestionFragment;
import com.collegedekho.app.fragment.stepByStepTest.StepByStepFragment;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.listener.OnApplyClickedListener;
import com.collegedekho.app.listener.OnArticleSelectListener;
import com.collegedekho.app.listener.OnNewsSelectListener;
import com.collegedekho.app.resource.BitMapHolder;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.ContainerHolderSingleton;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.GifView;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fasterxml.jackson.jr.ob.JSON;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import bolts.AppLinks;
import io.connecto.android.sdk.Connecto;
import io.connecto.android.sdk.Properties;
import io.connecto.android.sdk.Traits;
import io.fabric.sdk.android.Fabric;

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

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, ExamsFragment.OnExamsSelectListener,
        ProfileFragment.OnTabSelectListener, TabFragment.OnHomeItemSelectListener,
        DataLoadListener, StreamFragment.OnStreamInteractionListener,PsychometricStreamFragment.OnStreamInteractionListener,AdapterView.OnItemSelectedListener,
        InstituteListFragment.OnInstituteSelectedListener, OnApplyClickedListener,
        OnNewsSelectListener, ProfileEditFragment.onProfileUpdateListener,
        InstituteQnAFragment.OnQuestionAskedListener, FilterFragment.OnFilterInteractionListener,
        InstituteOverviewFragment.OnInstituteShortlistedListener, QnAQuestionsListFragment.OnQnAQuestionSelectedListener,
        QnAQuestionDetailFragment.OnQnAAnswerInteractionListener, MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener,
        MyFutureBuddiesFragment.OnMyFBInteractionListener, OnArticleSelectListener,
        LoginFragment1.OnSignUpListener, LoginFragment.OnUserSignUpListener,
        UserEducationFragment.OnUserEducationInteractionListener, PsychometricTestParentFragment.OnPsychometricTestSubmitListener,
        SyllabusSubjectsListFragment.OnSubjectSelectedListener, CalendarParentFragment.OnSubmitCalendarData,
        NotPreparingFragment.OnNotPreparingOptionsListener, StepByStepFragment.OnStepByStepFragmentListener,
        UserAlertsFragment.OnAlertItemSelectListener, GifView.OnGifCompletedListener, CDRecommendedInstituteListFragment.OnCDRecommendedInstituteListener,
        InstituteVideosFragment.OnTitleUpdateListener,OTPVerificationFragment.OTPVerificationListener, ITrueCallback

{

    static {
        Constants.FilterCategoryMap.put(Constants.ID_FACILITIES, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);
        Constants.FilterCategoryMap.put(Constants.ID_HOSTEL, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);

        Constants.FilterCategoryMap.put(Constants.ID_FEE_RANGE, Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES);
        Constants.FilterCategoryMap.put(Constants.ID_INSTITUTE_TYPE, Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES);

        Constants.FilterCategoryMap.put(Constants.ID_CITY, Constants.FILTER_CATEGORY_LOCATION);
        Constants.FilterCategoryMap.put(Constants.ID_STATE, Constants.FILTER_CATEGORY_LOCATION);

        Constants.FilterCategoryMap.put(Constants.ID_SPECIALIZATION, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
        Constants.FilterCategoryMap.put(Constants.ID_DEGREE, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
        Constants.FilterCategoryMap.put(Constants.ID_EXAM, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
    }

    private static final String TAG = "MainActivity";
    public static boolean IN_FOREGROUND=false;
    private boolean IS_NETWORK_TASK_RUNNING=false;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    public static NetworkUtils networkUtils;
    private ActionBarDrawerToggle mToggle;
    public BaseFragment currentFragment;
    private List<Institute> mInstituteList;
    private List<Chapters> chaptersList;
    private List<PsychometricTestQuestion> psychometricQuestionsList;
    private List<Institute> mShortlistedInstituteList;
    private int currentInstitute;
    private ProgressDialog progressDialog;
    private AlertDialog mErrorDialog;
    private String mCurrentTitle;
    private String next;
    private List<MyFutureBuddiesEnumeration> mFbEnumeration;
    private MyFutureBuddy mFB;
    private ArrayList<QnAQuestions> mQnAQuestions = new ArrayList<>();
    private int mFilterCount = 0;
    private String mFilters = "";
    private Map<String, String> mFilterKeywords = new HashMap<>();
    private ArrayList<Folder> mFolderList;
    private List<News> mNewsList;
    private List<Articles> mArticlesList;
    private Institute mInstitute;
    private String mLastScreenName = "";
    private String instituteCourseId = "";
    private List<Widget> mWidgets;
    public  CallbackManager callbackManager;
    private Date mTimeScreenClicked = new Date();
    public boolean isReloadProfile = false;
    public boolean isBackPressEnabled = true;
    private String mGTMContainerId = "www.collegedekho.com";
    public static Connecto connecto = null;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    //private NavigationDrawerFragment mNavigationDrawerFragment;
    /*
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    // private CharSequence mTitle;
    private List<ExamDetail> mUserExamsList = new ArrayList<>();
    public static User user;
    private User.Prefs userPref;
    static String type = "";
    static String resource_uri = "";
    private HashMap<String, String> mUserSignUPParams;
    private Menu menu;
    private String mYear;
    private View view;
    private TextView prepBuddies;
    private TextView resourceBuddies;
    private TextView futureBuddies;
    private TextView myAlerts;

    private boolean IS_PROFILE_LOADED;
    private boolean IS_USER_CREATED;
    private List<MyAlertDate> myAlertsList;
    private boolean isFromNotification;
    private boolean isFromDeepLinking;
    private String mDeepLinkingURI;
    private String mExamTag;
    private String user_phone_number;
    private int mUndecidedCount;
    private Snackbar snackbar;
    private static Context mContext;
    public static TrueClient mTrueClient;
    /*** ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public GoogleApiClient client;

    ProgressBar searchProgress;
    Handler gcmDialogHandler=new Handler();
    Runnable gcmDialogRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e(TAG, " onCreate  enter time"+ System.currentTimeMillis());
        try {
            super.onCreate(savedInstanceState);
        }catch (RuntimeException e){
            e.printStackTrace();
            finish();
            reStartApplication();
        }

        this.mContext = this;

        Intent intent =  this.getIntent();
        String action = intent.getAction();
        String data = intent.getDataString();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            if (data.contains("www.collegedekho.com"))
            {
                this.mDeepLinkingURI = data;
                Log.e(TAG, " DeepLinking URL is  : "+ mDeepLinkingURI);
            }
        }
        else if (intent.getExtras() != null)
        {
            Bundle extras = intent.getExtras();
            if (extras.containsKey("screen") && extras.containsKey("resource_uri"))
            {
                MainActivity.type = extras.getString("screen");
                MainActivity.resource_uri = extras.getString("resource_uri");
            }
        }

        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i(TAG, "App Link Target URL: " + targetUrl.toString());
        }

        this.setContentView(R.layout.activity_main);
        this.getBitMapResources();
        snackbar = Snackbar.make(this.findViewById(R.id.drawer_layout), "You are not connected to Internet", Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(getResources().getColor(R.color.primary_color));

        this.networkUtils = new NetworkUtils(this, this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED ) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
        // register with true SDk
        MainActivity.mTrueClient = new TrueClient(getApplicationContext(), this);

        // register with Connecto
        this.mRegistrationConnecto();

        // register fabric Crashlytics
        this.mRegistrationFabricCrashlytics();

        // register with Apps Flayer
        this.mRegistrationAppsFlyer();

        // register with facebook Sdk
        this.mRegistrationFacebookSdk();

        // register with GA tracker
        this.mRegistrationGATracker();

        this.mSetupGTM();

        prepBuddies       = (TextView)findViewById(R.id.prep_buddies);
        resourceBuddies   = (TextView)findViewById(R.id.resources_buddies);
        futureBuddies     = (TextView)findViewById(R.id.future_buddies);
        myAlerts          = (TextView)findViewById(R.id.my_alerts);
        prepBuddies.setOnClickListener(mClickListener);
        resourceBuddies.setOnClickListener(mClickListener);
        futureBuddies.setOnClickListener(mClickListener);
        myAlerts.setOnClickListener(mClickListener);

        this.mSetUpAPPToolBar();
        this.mDisplayFragment(SplashFragment.newInstance(), false, SplashFragment.class.getName());

        // TODO: Move this to where you establish a user session
        logUser();
        setupOtpRequest(true);
        int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
        this.IS_PROFILE_LOADED = getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).getBoolean(getResourceString(R.string.USER_PROFILE_LOADED), false);
        if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED && IS_PROFILE_LOADED) {
            Utils.appLaunched(this);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        searchProgress = (ProgressBar) findViewById(R.id.resource_progress_bar);
        Log.e(TAG, " onCreate  exit time"+ System.currentTimeMillis());
        gcmDialogRunnable = new Runnable() {
            @Override
            public void run() {
                if(IN_FOREGROUND && IS_PROFILE_LOADED){
                    if(!IS_NETWORK_TASK_RUNNING) {
                        Intent gcmIntent = new Intent(MainActivity.this, GCMDialogActivity.class);
                        MainActivity.this.startActivityForResult(gcmIntent,Constants.GCM_RESULT_DATA_KEY);
                    }else {
                        gcmDialogHandler.removeCallbacks(gcmDialogRunnable);
                        gcmDialogHandler.postDelayed(gcmDialogRunnable,5000);
                    }
                }
            }
        };
        gcmDialogHandler.postDelayed(gcmDialogRunnable,15000);
    }

    /**
     * This method is used  with connecto sdk
     */
    private void mRegistrationConnecto() {

        this.connecto = Connecto.with(MainActivity.this);
        //this.connecto.identify("Harsh1234Vardhan", new Traits().putValue("name", "HarshVardhan"));
        //You can also track any event if you want
        //this.connecto.track("Session Started", new Properties().putValue("value", 800));
        this.connecto.registerWithGCM(MainActivity.this, Constants.SENDER_ID);
    }

    /**
     * This method is used with GA tracker
     */

    private void mRegistrationGATracker(){

        new Thread(new Runnable() {
            @Override
            public void run() {
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
        }).start();

    }
    /**
     * This method is used to register and initialize facebook sdk
     */
    private void mRegistrationFacebookSdk()
    {
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG, "Logged In ");
                showProgressDialog("Signing with facebook account");
                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token != null) {
                    RequestData(token);
                }
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "facebook login canceled");
                displayMessage(R.string.FACEBOOK_SIGNIN_FAILED);
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                Log.e(TAG, "facebook login on error");
                displayMessage(R.string.SIGNIN_ERROR);
            }
        });
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.collegedekho.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHAsh = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", keyHAsh);
            }
        }
        catch (PackageManager.NameNotFoundException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }
    }

    /**
     * This method is used to register Fabric Crashlyticsr
     */
    private void mRegistrationFabricCrashlytics() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               Fabric.with(MainActivity.this, new Crashlytics());
           }
       }).start();
    }

    /**
     * This method is used to register Apps flyer tracker
     */
    private void mRegistrationAppsFlyer(){
        // The Dev key cab be set here or in the manifest.xml
        AppsFlyerLib.getInstance().startTracking(this.getApplication(), Constants.APPSFLYER_ID);

        //AppsFlyer: collecting your GCM project ID by setGCMProjectID allows you to track uninstall data in your dashboard

        AppsFlyerLib.getInstance().setGCMProjectID(this,Constants.GCM_KEY_APPS_FLYER);
        // Set the Currency
        AppsFlyerLib.getInstance().setCurrencyCode("INR");
        AppsFlyerLib.getInstance().setDebugLog(true);

        AppsFlyerLib.getInstance().registerConversionListener(this,new AppsFlyerConversionListener() {
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
                DebugLogQueue.getInstance().push("\nGot conversion data from server");
                for (String attrName : conversionData.keySet()){
                    Log.d(TAG, "attribute: "+attrName+" = "+conversionData.get(attrName));
                }
            }

            public void onInstallConversionFailure(String errorMessage) {
                Log.d(TAG, "error getting conversion data: "+errorMessage);
            }

            public void onAppOpenAttribution(Map<String, String> attributionData) {
                printMap(attributionData);
            }

            public void onAttributionFailure(String errorMessage) {
                Log.d(TAG, "error onAttributionFailure : "+errorMessage);
            }

            private void printMap(Map<String,String> map){
                for (String key : map.keySet()) {
                    Log.d(TAG, key+"="+map.get(key));
                }
            }
        });
    }
    private void getBitMapResources(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                BitMapHolder bitMapHolder = new BitMapHolder();
                bitMapHolder.setContext(MainActivity.this);
                bitMapHolder.getBitMapFromResource();
            }
        }).start();

    }
    @Override
    public void onGifCompleted() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //loadInItData();
                if (currentFragment instanceof SplashFragment)
                    ((SplashFragment) currentFragment).isInternetAvailable();
            }
        }, 10);
    }

    private void mSetUpAPPToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_navigation_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment instanceof SyllabusSubjectsListFragment)
                    ((SyllabusSubjectsListFragment) currentFragment).submitSyllabusStatus();
                else if (currentFragment instanceof CalendarParentFragment)
                    ((CalendarParentFragment) currentFragment).submitCalendarData();
                else if (currentFragment instanceof ExamsFragment)
                    return;
                else if (currentFragment instanceof OTPVerificationFragment)
                    return;


                mClearBackStack();
                invalidateOptionsMenu();
            }
        });

        toolbar.setNavigationContentDescription("Select to go to home screen");
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void logUser() {
        // You can call any combination of these three methods
        if (MainActivity.user != null) {
            Crashlytics.setUserIdentifier(MainActivity.user.getId());
            Crashlytics.setUserEmail(MainActivity.user.getEmail());
            Crashlytics.setUserName(MainActivity.user.getName());
        }
    }

    private void mHandleNotifications(boolean isFromNotifications) {
        if (isFromNotifications) {
            this.isFromNotification = true;
            this.isFromDeepLinking = false;

        } else {
            this.isFromNotification = false;
            this.isFromDeepLinking = true;
        }

        switch (MainActivity.type) {
            case Constants.TAG_FRAGMENT_INSTITUTE_LIST: {
                this.mCurrentTitle = "Institute List";
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_INSTITUTES, MainActivity.resource_uri, null);
                } else {
                    this.mMakeNetworkCall(Constants.WIDGET_INSTITUTES, MainActivity.resource_uri, null);
                }
                break;
            }

            case Constants.TAG_FRAGMENT_NEWS_LIST: {
                this.mCurrentTitle = "News";
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_NEWS, MainActivity.resource_uri, null);
                } else {
                    this.mMakeNetworkCall(Constants.WIDGET_NEWS, MainActivity.resource_uri, null);
                }
                break;
            }

            case Constants.TAG_FRAGMENT_ARTICLES_LIST: {
                this.mCurrentTitle = "Articles";
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_ARTICLES, MainActivity.resource_uri, null);
                } else {
                    this.mMakeNetworkCall(Constants.WIDGET_ARTICES, MainActivity.resource_uri, null);
                }
                break;
            }

            case Constants.TAG_FRAGMENT_SHORTLISTED_INSTITUTE: {
                this.mCurrentTitle = "My Shortlist";
                this.mMakeNetworkCall(Constants.WIDGET_SHORTLIST_INSTITUTES, MainActivity.resource_uri, null);
                break;
            }

            case Constants.TAG_FRAGMENT_QNA_QUESTION_LIST: {
                this.mCurrentTitle = "QnA";
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_QNA, MainActivity.resource_uri, null);
                } else {
                    this.mMakeNetworkCall(Constants.TAG_LOAD_QNA_QUESTIONS, MainActivity.resource_uri, null);
                }
                break;
            }

            case Constants.TAG_FRAGMENT_MY_FB_ENUMERATION: {
                this.mCurrentTitle = "My Future Buddies";
                if (Utils.isUriEndsWithNumber(MainActivity.resource_uri)) {
                    this.mMakeNetworkCall(Constants.PNS_FORUM, MainActivity.resource_uri, null);
                } else {
                    this.mMakeNetworkCall(Constants.WIDGET_FORUMS, MainActivity.resource_uri, null);
                }
                break;
            }

            case Constants.WIDGET_TEST_CALENDAR:
                this.mMakeNetworkCall(Constants.WIDGET_TEST_CALENDAR, MainActivity.resource_uri, null);
                break;

            case Constants.TAG_MY_ALERTS:
                MainActivity.this.mMakeNetworkCall(Constants.TAG_MY_ALERTS, MainActivity.resource_uri, null);
                break;

            case Constants.WIDGET_SYLLABUS:
                this.mMakeNetworkCall(Constants.WIDGET_SYLLABUS, MainActivity.resource_uri, null);
                break;

            case Constants.PLAY_VIDEO_NOTIFICATION:
                if (MainActivity.resource_uri != null && !MainActivity.resource_uri.trim().matches("")) {
                    Intent intent = new Intent(this, VideoPlayerActivity.class);
                    intent.putExtra("video_id", MainActivity.resource_uri);
                    startActivityForResult(intent, Constants.RC_QUIT_VIDEO_PLAYER);
                }
                break;
            case Constants.ACTION_OPEN_WEB_URL:
                onDisplayWebFragment(MainActivity.resource_uri);
                break;

            default:
                this.isFromNotification = false;
                this.isFromDeepLinking = false;
                MainActivity.type = "";
                MainActivity.resource_uri = "";
                this.mDeepLinkingURI = "";
                this.mLoadUserStatusScreen();
                break;
        }

        MainActivity.type = "";
        MainActivity.resource_uri = "";
        this.mDeepLinkingURI = "";
        getIntent().putExtra("screen","");
        getIntent().putExtra("resource_uri","");
    }

    private void mHandleDeepLinking(String uri) {
        String[] uriArray = uri.split("/");
        String resourceURI = "";

        if (uriArray.length > 3)
        {
            MainActivity.type = uriArray[3];
            for (int i = 4; i < uriArray.length; i++) {
                if (!uriArray[i].isEmpty())
                    resourceURI += uriArray[i] + "/";
            }

            MainActivity.resource_uri = Constants.BASE_URL + resourceURI;
        }
        else
            MainActivity.type = "";

        this.mHandleNotifications(true);
    }



    public void RequestData(final AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        // get user profile info
                        String image = "https://graph.facebook.com/" + json.optString("id") + "/picture?type=large";

                        if (MainActivity.user == null)
                            MainActivity.user = new User();

                        MainActivity.user.setImage(image);

                        HashMap hashMap = new HashMap<>();
                        hashMap.put(getResourceString(R.string.USER_FIRST_NAME), json.getString("first_name"));
                        hashMap.put(getResourceString(R.string.USER_LAST_NAME), json.getString("last_name"));
                        hashMap.put(getResourceString(R.string.USER_VERIFIED), json.getString("verified"));
                        hashMap.put(getResourceString(R.string.USER_NAME), json.getString("name"));
                        hashMap.put(getResourceString(R.string.USER_LOCALE), json.getString("locale"));
                        hashMap.put(getResourceString(R.string.USER_GENDER), json.getString("gender"));
                        hashMap.put(getResourceString(R.string.USER_UPDATED_TIME), json.getString("updated_time"));
                        hashMap.put(getResourceString(R.string.USER_LINK), json.getString("link"));
                        hashMap.put(getResourceString(R.string.USER_ID), json.getString("id"));
                        hashMap.put(getResourceString(R.string.USER_TIMEZONE), json.getString("timezone"));
                        hashMap.put(getResourceString(R.string.USER_EMAIL), json.getString("email"));
                        hashMap.put(getResourceString(R.string.USER_IMAGE), image);
                        hashMap.put(getResourceString(R.string.USER_TOKEN), accessToken.getToken());
                        hashMap.put(getResourceString(R.string.USER_EXPIRE_AT), new SimpleDateFormat("yyyy-MM-dd").format(accessToken.getExpires()) + "T" + new SimpleDateFormat("HH:mm:ss").format(accessToken.getExpires()));

                        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                        hashMap.put(getResourceString(R.string.USER_DEVICE_ID), deviceId);
                        hashMap.put(MainActivity.getResourceString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_FACEBOOK);
                        DataBaseHelper.getInstance(MainActivity.this).deleteAllExamSummary();
                        onUserCommonLogin(hashMap, Constants.TAG_FACEBOOK_LOGIN);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,verified,name,locale,gender,updated_time,link,id,timezone,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void mSetupGTM() {
        TagManager tagManager = TagManager.getInstance(this);

        // Modify the log level of the logger to print out not only
        // warning and error messages, but also verbose, debug, info messages.
        tagManager.setVerboseLoggingEnabled(true);

        PendingResult<ContainerHolder> pending =
                tagManager.loadContainerPreferNonDefault(this.mGTMContainerId,
                        R.raw.gtm_analytics);

        // The onResult method will be called as soon as one of the following happens:
        //     1. a saved container is loaded
        //     2. if there is no saved container, a network container is loaded
        //     3. the request times out. The example below uses a constant to manage the timeout period.
        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                Container container = containerHolder.getContainer();
                if (!containerHolder.getStatus().isSuccess()) {
                    Log.e("CollegeDekho", "failure loading container");
                    return;
                }
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                ContainerLoadedCallback.registerCallbacksForContainer(container);
                containerHolder.setContainerAvailableListener(new ContainerLoadedCallback());
            }
        }, 2, TimeUnit.SECONDS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IN_FOREGROUND=true;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MainActivity.type = extras.getString("screen");
            MainActivity.resource_uri = extras.getString("resource_uri");
        }
        adjustFontScale(getResources().getConfiguration());
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
        IntentFilter linkFilter=new IntentFilter(Constants.CONTENT_LINK_FILTER);
        linkFilter.addAction(Constants.NOTIFICATION_FILTER);
        LocalBroadcastManager.getInstance(this).registerReceiver(appLinkReceiver,linkFilter);
        System.gc();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
            reStartApplication();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        IN_FOREGROUND=false;
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(appLinkReceiver);
        System.gc();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CollegeDekho App", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://www.collegedekho.com"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse(Constants.BASE_APP_URI.toString())
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://www.collegedekho.com"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.collegedekho.app/http/")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        this.connecto.track("Session Ended", new Properties().putValue("session_end_datetime", new Date().toString()));

        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(appLinkReceiver);
    }

    SearchView searchView = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
            // set the hint text color
            autoComplete.setHintTextColor(Color.GRAY);
            // set the text color
            autoComplete.setTextColor(Color.BLUE);
            try {
                Field searchField = SearchView.class.getDeclaredField("mCloseButton");
                searchField.setAccessible(true);
                ImageView mSearchCloseButton = (ImageView) searchField.get(searchView);
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
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(true);
            searchView.setQueryHint("Search Institutes");
            searchView.setOnQueryTextListener(queryTextListener);
            searchView.setOnCloseListener(queryCloseListener);
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
                    if (amIConnectedToInternet == Constants.TYPE_NOT_CONNECTED) {
                        displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
                        searchView.onActionViewCollapsed();
                    }
                }
            });
            searchView.clearFocus();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (currentFragment instanceof ProfileEditFragment || currentFragment instanceof ExamsFragment || currentFragment instanceof UserEducationFragment ||currentFragment instanceof StreamFragment || currentFragment instanceof PsychometricStreamFragment || currentFragment instanceof PsychometricTestParentFragment || currentFragment instanceof OTPVerificationFragment ||currentFragment instanceof WebViewFragment) {
            menu.setGroupVisible(R.id.main_menu_group, false);
        }else {
            if(menu.size()>0)
            menu.getItem(0).setVisible(true);
        }
        setSearchAvailable(menu);
        if (!getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getResourceString(R.string.PROFILE_SCREEN_TUTE), false)) {
            menu.setGroupVisible(R.id.main_menu_group, false);
            menu.setGroupVisible(R.id.search_menu_group, false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void showOverflowMenu(boolean showMenu){
//        if(menu == null)
//            return;
//        menu.setGroupVisible(R.id.main_menu_group, showMenu);
    }

    private void setSearchAvailable(Menu menu) {
        if (currentFragment != null) {
            if (currentFragment instanceof ProfileFragment) {
                menu.setGroupVisible(R.id.search_menu_group, true);
                if (searchView != null) {
                    searchView.setQueryHint("Search Institutes");
                }
            } else if (currentFragment instanceof ArticleFragment) {
                menu.setGroupVisible(R.id.search_menu_group, true);
                if (searchView != null) {
                    searchView.setQueryHint("Search Articles");
                }
            } else if (currentFragment instanceof NewsFragment) {
                menu.setGroupVisible(R.id.search_menu_group, true);
                if (searchView != null) {
                    searchView.setQueryHint("Search News");
                }

            } else if (currentFragment instanceof QnAQuestionsListFragment) {

                menu.setGroupVisible(R.id.search_menu_group, true);
                if (searchView != null) {
                    searchView.setQueryHint("Search Questions");
                }
            } else if (currentFragment instanceof InstituteListFragment && mCurrentTitle != null && !(mCurrentTitle.equals("WishList Institutes") || mCurrentTitle.equals("Recommended Institutes"))) {
                menu.setGroupVisible(R.id.search_menu_group, true);
                if (searchView != null) {
                    searchView.setQueryHint("Search Institutes");
                }
            } else {
                menu.setGroupVisible(R.id.search_menu_group, false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_profile) {
            mPofileEditFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void init() {
        SharedPreferences sp = this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE);
        try {
            if (sp.contains(getResourceString(R.string.KEY_USER))) {
                MainActivity.user = JSON.std.beanFrom(User.class, sp.getString(getResourceString(R.string.KEY_USER), null));
                this.networkUtils.setToken(MainActivity.user.getToken());
                // user id register
                setUserIdWithAllEvents();

                // this code for backward compatibility because in first release user stream and level
                // were saved in uri form instead of IDs. it can be remove after some releases
                if (MainActivity.user != null && MainActivity.user.getStream() != null && MainActivity.user.getLevel() != null) {
                    // this code for backward compatibility because in first release user stream and level
                    // were saved in uri form instead of IDs.
                    if (MainActivity.user.getStream().length() > 0 && Patterns.WEB_URL.matcher(MainActivity.user.getStream()).matches()) {
                        String streamId[] = MainActivity.user.getStream().split("/");
                        MainActivity.user.setStream(streamId[streamId.length - 1]);
                    }
                    if (MainActivity.user.getLevel().length() > 0 && Patterns.WEB_URL.matcher(MainActivity.user.getLevel()).matches()) {
                        String levelId[] = MainActivity.user.getLevel().split("/");
                        MainActivity.user.setLevel(levelId[levelId.length - 1]);
                    }
                }
            }

            this.IS_USER_CREATED = sp.getBoolean(getResourceString(R.string.USER_CREATED), false);
            this.IS_PROFILE_LOADED = sp.getBoolean(getResourceString(R.string.USER_PROFILE_LOADED), false);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This methos is used to register userId with Apps Flyer
     * and GA Tracker
     */
    private void setUserIdWithAllEvents(){
        // register user id with apps flyer
        AppsFlyerLib.getInstance().setCustomerUserId(MainActivity.user.getId());
        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.SESSION_STARTED_DATE_TIME), new Date().toString());
        eventValue.put(getResourceString(R.string.USER_ID), MainActivity.user.getId());
        eventValue.put(getResourceString(R.string.USER_EMAIL), user.getEmail());
        eventValue.put(getResourceString(R.string.USER_PHONE), user.getPhone_no());

        MainActivity.AppsflyerTrackerEvent(this,getResourceString(R.string.SESSION_STARTED),eventValue);
        // register user id with GA tracker
        this.tracker.setClientId(MainActivity.user.getId());
        // register user id with connecto
        // TODO:: add user phone number in connecto
        this.connecto.identify(MainActivity.user.getId(), new Traits().putValue(getResourceString(R.string.USER_NAME), MainActivity.user.getName()));
        this.connecto.track(getResourceString(R.string.SESSION_STARTED),  new Properties().putValue(getResourceString(R.string.SESSION_STARTED_DATE_TIME), new Date().toString()));

    }

    public void loadInItData() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            getSupportLoaderManager().initLoader(0, null, this);
            if (IS_USER_CREATED) {
                // if user is anonymous  then logout from facebook
                if (user.is_anony())
                    disconnectFromFacebook();

                this.mLoadUserStatusScreen();

            } else {
                disconnectFromFacebook();
                MainActivity.this.mDisplayLoginFragment();
            }

        } else {
            getUserPermissions();
        }
    }

    /**
     * This method is called when first time anonymous user is created
     *
     * @param json
     *//*
    private void mUserCreated(String json) {
        try {
            User tempUser = MainActivity.user;
            MainActivity.user = JSON.std.beanFrom(User.class, json);
            MainActivity.user.setPref(this.userPref);
            this.networkUtils.setToken(MainActivity.user.getToken());

            if (tempUser != null) {
                MainActivity.user.setPrimaryEmail(tempUser.getPrimaryEmail());
                MainActivity.user.setPrimaryPhone(tempUser.getPrimaryPhone());
                MainActivity.user.profileData = tempUser.profileData;
            }

            if (MainActivity.user.getName().isEmpty() || user.getName().equalsIgnoreCase(getResourceString(R.string.ANONYMOUS_USER))) {
                //get name from my profile me
                //get device id
                String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

                HashMap<String, String> hashMap = new HashMap<>();

                if (user.profileData[0] != null) {
                    user.setName(user.profileData[0]);
                    hashMap.put(getResourceString(R.string.USER_NAME), user.profileData[0]);
                }

                if (user.profileData[1] != null) {
                    user.setEmail(user.profileData[1]);
                    hashMap.put(getResourceString(R.string.USER_EMAIL), user.profileData[1]);
                    this.mMakeNetworkCall(Constants.TAG_NAME_UPDATED + "#" + getResourceString(R.string.ANONYMOUS_USER), Constants.BASE_URL + "preferences/", hashMap);
                }

                hashMap.put(getResourceString(R.string.USER_DEVICE_ID), deviceId);

                this.mMakeNetworkCall(Constants.TAG_NAME_UPDATED + "#name", Constants.BASE_URL + "preferences/", hashMap);
            }

            this.connecto.identify(MainActivity.user.getId(), new Traits().putValue(getResourceString(R.string.USER_NAME), MainActivity.user.getName()));

            MainActivity.tracker.setClientId(MainActivity.user.getId());

            String u = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).commit();
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean(getResourceString(R.string.USER_CREATED), true).commit();

            this.mLoadUserStatusScreen();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }*/

    /**
     * This method is used to load screens according to user status
     * if education and exam is selected then load profile screen
     * if education is selected but exam is not selected then load exams screen
     * if both are not selected then load education screen
     */
    private void mLoadUserStatusScreen() {
        if (MainActivity.type != null && !MainActivity.type.matches("")) {
            this.isFromNotification = true;
            this.mHandleNotifications(true);
        } else if (this.mDeepLinkingURI != null && !this.mDeepLinkingURI.isEmpty()) {
            Log.e("MA: DL URL ", MainActivity.this.mDeepLinkingURI);
//            Toast.makeText(MainActivity.this, this.mDeepLinkingURI, Toast.LENGTH_SHORT).show();
            this.isFromDeepLinking = true;
            this.mHandleDeepLinking(this.mDeepLinkingURI);
        } else if ((MainActivity.user.getEducation_set() == 1 && (MainActivity.user.getExams_set() == 1) || (MainActivity.user.getIs_preparing().equals("0")) && IS_PROFILE_LOADED)) {
            if (IS_PROFILE_LOADED) {
                mLoadUserProfile(null);
            }
            this.mMakeNetworkCall(Constants.TAG_LAUNCH_USER_HOME, Constants.BASE_URL + "preferences/", null);
        } else if (MainActivity.user.getEducation_set() == 1 && MainActivity.user.getExams_set() == 0 && !MainActivity.user.getIs_preparing().equals("0"))
            this.mMakeNetworkCall(Constants.TAG_LOAD_EXAMS_LIST, Constants.BASE_URL + "yearly-exams/", null);
        else
            this.mMakeNetworkCall(Constants.TAG_USER_EDUCATION, Constants.BASE_URL + "user-education/", null);
    }

    /**
     * This mthod used to show user profile fragment UI
     */
    private void mPofileEditFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileEditFragment.class.getSimpleName());
        if (fragment == null)
            mDisplayFragment(ProfileEditFragment.newInstance(), true, ProfileEditFragment.class.getSimpleName());
        else
            mDisplayFragment(fragment, false, ProfileEditFragment.class.getSimpleName());
    }

    /**
     * This method is used to split response json
     * with result , filter and next tags
     *
     * @param response
     * @return
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

            if (map.containsKey("undecided_count"))
                this.mUndecidedCount = Integer.valueOf(JSON.std.asString(map.get("undecided_count")));

            return JSON.std.asString(map.get("results"));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    private void mDisplayStreams(String response, boolean addToBackstack) {
        try {
            List<Stream> streams = JSON.std.listOfFrom(Stream.class, extractResults(response));
            this.mDisplayFragment(StreamFragment.newInstance(new ArrayList(streams), addToBackstack), addToBackstack, Constants.TAG_FRAGMENT_STREAMS);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mDisplayStreamsSelection(String response, boolean addToBackstack) {
        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String results = JSON.std.asString(map.get("results"));
            List<Stream> streams = JSON.std.listOfFrom(Stream.class, results);
            this.mClearBackStack();
            this.mDisplayFragment(PsychometricStreamFragment.newInstance(new ArrayList(streams),addToBackstack), addToBackstack, Constants.TAG_FRAGMENT_STREAMS);
//                displayOTPAlert(this);
            requestOtp();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mDisplayStreamsEditSelection(String response, boolean addToBackstack) {
        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String results = JSON.std.asString(map.get("results"));
            List<Stream> streams = JSON.std.listOfFrom(Stream.class, results);
            this.mDisplayFragment(PsychometricStreamFragment.newEditableInstance(new ArrayList(streams),addToBackstack), addToBackstack, Constants.TAG_FRAGMENT_STREAMS);
//                displayOTPAlert(this);
            requestOtp();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method is used to update institutes list of next page
     * @param response
     */
    private void updateNextInstituteList(String response) {
        try {
            List<Institute> institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            this.mInstituteList.addAll(institutes);

            if (currentFragment instanceof InstituteListFragment) {
                ((InstituteListFragment) currentFragment).updateList(institutes, next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
    /**
     * This method is used to update shorlist institutes list of next page
     * @param response
     *//*
    private void updateNextShortListedInstitutes(String response) {
        try {
            List<Institute> institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            this.mShortlistedInstituteList.addAll(institutes);

          *//*  if (currentFragment instanceof InstituteShortlistFragment) {
                ((InstituteShortlistFragment) currentFragment).updateList(institutes, next);
            }*//*
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }*/
    /**
     * This method is used to update news list of next page
     * @param response
     */
    private void updateNextNewsList(String response) {
        try {
            List<News> news = JSON.std.listOfFrom(News.class, extractResults(response));
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
     * @param response
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
     * @param response
     */
    private void updateNextQnaList(String response) {
        ArrayList<QnAQuestions> qnaQuestionsList = parseAndReturnQnAList(response);

        if (currentFragment instanceof QnAQuestionsListFragment) {
            ((QnAQuestionsListFragment) currentFragment).updateList(new ArrayList<>(qnaQuestionsList), next);
        }
    }

    /**
     * This method is used to update qna list of next page
     * @param response
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

    private void mDisplayCDRecommendedInstituteList(String response, boolean isHavingNextUrl, Constants.CDRecommendedInstituteType cdRecommendedInstituteType) {
        switch (cdRecommendedInstituteType) {
            case UNDECIDED:
                mDisplayCDRecommendedInstituteList(response, isHavingNextUrl, cdRecommendedInstituteType, true);
                break;
            case UNBAISED:
                mDisplayCDRecommendedInstituteList(response, isHavingNextUrl, cdRecommendedInstituteType, false);
                break;
        }
    }

    private void mDisplayCDRecommendedInstituteList(String response, boolean isHavingNextUrl, Constants.CDRecommendedInstituteType cdRecommendedInstituteType, boolean isUpdate) {
        try {
            //Suggesting System that its a good time to do GC
            System.gc();
            this.hideProgressDialog();
            /*final ProgressDialog progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Rendering institutes cards..");
            progress.setIndeterminate(true);*/

            if (!isHavingNextUrl)
                next = null;

            int time = 1000;

            if (isUpdate) {
                if (currentFragment != null && currentFragment instanceof CDRecommendedInstituteListFragment) {
                    String val = this.extractResults(response);
                    this.mInstituteList = JSON.std.listOfFrom(Institute.class, val);
                    if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.UNDECIDED)
                        ((CDRecommendedInstituteListFragment) currentFragment).showUndecidedInstitutes(this.mInstituteList, next);
                    else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.UNBAISED)
                        ((CDRecommendedInstituteListFragment) currentFragment).updateList(this.mInstituteList, next);
                }
            } else {
                String val = this.extractResults(response);
                this.mInstituteList = JSON.std.listOfFrom(Institute.class, val);
                /*if (mInstituteList.size() > 5) {
                    time = (20 * mInstituteList.size());
                }
                progress.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progress != null && progress.isShowing())
                            progress.dismiss();
                    }
                }, time);*/
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(getResourceString(R.string.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST));

                if (fragment == null) {
                    this.mDisplayFragment(CDRecommendedInstituteListFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next, this.mUndecidedCount,mExamTag), !isFromNotification, getResourceString(R.string.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST));
                } else {
                    if (fragment instanceof CDRecommendedInstituteListFragment) {
                        if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.UNDECIDED)
                            ((CDRecommendedInstituteListFragment) fragment).showUndecidedInstitutes(this.mInstituteList, next);
                        else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.UNBAISED)
                            ((CDRecommendedInstituteListFragment) fragment).updateList(this.mInstituteList, next);
                    }

                    this.mDisplayFragment(fragment, false, getResourceString(R.string.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST));
                }
            }
            if (MainActivity.user.getPartner_shortlist_count() > 0) {
                requestOtp();
            }
        } catch (IOException e) {
            this.hideProgressDialog();
            Log.e(TAG, e.getMessage());
        }
    }

    private void mDisplayInstituteList(String response, boolean filterAllowed, boolean isHavingNextUrl, int listType) {
        try {
            String val = this.extractResults(response);
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, val);

            if (this.mFilterKeywords != null && this.mFilterKeywords.size() > 0)
                this.mFilterCount = this.mFilterKeywords.size();

            if (!isHavingNextUrl)
                next = null;

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);

            if (currentFragment instanceof InstituteListFragment) {
                ((InstituteListFragment) fragment).clearList();
                ((InstituteListFragment) fragment).updateList(this.mInstituteList, next);
                ((InstituteListFragment) fragment).updateFilterButton(this.mFilterCount);
            }else {
                this.mDisplayFragment(InstituteListFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next, filterAllowed, this.mFilterCount,listType), !isFromNotification, Constants.TAG_FRAGMENT_INSTITUTE_LIST);
            }

            if(listType==Constants.SHORTLIST_TYPE){
                if(MainActivity.user.getPartner_shortlist_count()>0) {
                    requestOtp();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
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

    private void mShowMyFB(String response, int index, int commentsCount) {
        try {
            this.mFB = this.mParseAndPopulateMyFB(response, index);
            this.mDisplayFragment(MyFutureBuddiesFragment.newInstance(this.mFB, commentsCount), true, Constants.TAG_FRAGMENT_MY_FB);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mUpdateMyFB(String response, int index, int oldCount) {
        try {
            this.mFB = this.mParseAndPopulateMyFB(response, index);

            //if number of comments have increased
            if (this.mFB.getComments_count() > oldCount) {
                ArrayList<MyFutureBuddyComment> myFbComments = this.mFB.getFutureBuddiesCommentsSet();

                if (currentFragment instanceof MyFutureBuddiesFragment)
                    ((MyFutureBuddiesFragment) currentFragment).updateChatPings(myFbComments);
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
            myFB.setIndex(index);

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

                myFBCommentsSet.add(myFBComment);
            }

            myFB.setFutureBuddiesCommentsSet(myFBCommentsSet);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return myFB;
    }

    private void mDisplaySubjectSyllabus(Subjects subject) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(SyllabusUnitListFragment.class.getSimpleName());
        if (fragment == null)
            this.mDisplayFragment(SyllabusUnitListFragment.newInstance(subject.getUnits()), true, SyllabusUnitListFragment.class.getSimpleName());
        else
            this.mDisplayFragment(fragment, false, SyllabusUnitListFragment.class.getSimpleName());
    }

    private void mDisplayInstituteByPosition(int position) {
        if(position<0 || position>=mInstituteList.size()){
            return;
        }
        this.mInstitute = this.mInstituteList.get(position);
        this.mDisplayInstituteByEntity(this.mInstitute);
    }

    private void mDisplayInstituteByEntity(Institute institute) {
        this.mInstitute = institute;
        int id = institute.getId();

//        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE);
//
//        if (fragment == null)
        this.mDisplayFragment(InstituteDetailFragment.newInstance(institute), true, Constants.TAG_FRAGMENT_INSTITUTE);
//        else
//            this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_INSTITUTE);

        this.mMakeNetworkCall(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "institutecourses/" + "?institute=" + id, null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_NEWS, Constants.BASE_URL + "personalize/news/" + "?institute=" + String.valueOf(id), null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_ARTICLE, Constants.BASE_URL + "personalize/articles/" + "?institute=" + String.valueOf(id), null);

        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.INSTITUTE_RESOURCE_URI), institute.getResource_uri());
        eventValue.put(Constants.INSTITUTE_ID, String.valueOf(id));

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_SELECTED), eventValue, this);
    }

    private void loadPyschometricTest(String response) {
        Bundle bundle = new Bundle();
        bundle.putString(PsychometricAnalysisActivity.PSYCHOMETRIC_RESULTS, response);

        Intent activityIntent = new Intent(MainActivity.this, PsychometricAnalysisActivity.class);
        activityIntent.putExtras(bundle);
        this.startActivityForResult(activityIntent, PsychometricAnalysisActivity.GET_PSYCHOMETRIC_RESULTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PsychometricAnalysisActivity.GET_PSYCHOMETRIC_RESULTS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (PsychometricQuestionFragment.getAnswers() != null) {
                    //Bundle bundle = data.getExtras();
                    //HashMap<String, String> hashMap = (HashMap<String, String>) bundle.getSerializable(MainActivity.PSYCHOMETRIC_RESULTS);
                    JSONObject answersObject = PsychometricQuestionFragment.getAnswers();

                    //TODO: Shim to adhere to server's compliance; removing "[" and  "]" from college_types.
                    if (answersObject.has("college_types")) {
                        String str = null;
                        try {
                            str = (String) answersObject.get("college_types");
                            str = str.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "");

                            answersObject.put("college_types", str);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_PSYCHOMETRIC_TEST, Constants.BASE_URL + "psychometrictests/", answersObject, Request.Method.POST);
                }
            }
        } else if (requestCode == Constants.RC_QUIT_VIDEO_PLAYER) {
            if (currentFragment instanceof SplashFragment) {
                isFromNotification = false;
                this.mLoadUserStatusScreen();
            }
        }
        else if(mTrueClient.onActivityResult(requestCode,resultCode,data)) {
            return;
        }
        else  if(callbackManager.onActivityResult(requestCode, resultCode, data))
        {
            return;
        }else if(requestCode==Constants.GCM_RESULT_DATA_KEY && resultCode==RESULT_OK){
            try {
                HashMap<String, String> hashMap = (HashMap<String, String>) data.getSerializableExtra(Constants.DIALOG_DATA);
                if(hashMap!=null && !hashMap.isEmpty()) {
                    this.mMakeNetworkCall(Constants.TAG_UPDATE_USER_PROFILE, MainActivity.user.getPreference_uri(), hashMap, Request.Method.PUT);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void mDisplayLoginFragment() {
        LoginFragment fragment = LoginFragment.newInstance();
        try {

            this.currentFragment = fragment;

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.container, fragment, getResourceString(R.string.TAG_FRAGMENT_LOGIN));

            fragmentTransaction.commitAllowingStateLoss();

        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
        } finally {
            //Send GA Session
            MainActivity.GAScreenEvent(getResourceString(R.string.TAG_FRAGMENT_LOGIN));

            HashMap<String, Object> eventValue = new HashMap<String, Object>();

            eventValue.put(getResourceString(R.string.SCREEN_NAME), getResourceString(R.string.TAG_FRAGMENT_LOGIN));
            eventValue.put(getResourceString(R.string.LAST_SCREEN_NAME), this.mLastScreenName);
            eventValue.put(getResourceString(R.string.TIME_LAPSED_SINCE_LAST_SCREEN_NAME_IN_MS), String.valueOf(new Date().getTime() - this.mTimeScreenClicked.getTime()));

            this.mTimeScreenClicked = new Date();

            this.mLastScreenName = getResourceString(R.string.TAG_FRAGMENT_LOGIN);

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_SCREEN_SELECTED), eventValue, this);
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
            if(currentFragment instanceof SplashFragment || currentFragment instanceof ProfileFragment){
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
            }else {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            }
            fragmentTransaction.replace(R.id.container, fragment, tag);

            if (addToBackstack)
                fragmentTransaction.addToBackStack(fragment.toString());

            fragmentTransaction.commit();

            if (this.currentFragment instanceof ProfileFragment) {
                if (findViewById(R.id.app_bar_layout).getVisibility() != View.VISIBLE)
                    findViewById(R.id.app_bar_layout).setVisibility(View.VISIBLE);

                View bottomMenu = findViewById(R.id.bottom_tab_layout);
                bottomMenu.animate().translationY(0);
                bottomMenu.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
        } finally {
            //Send GA Session
            MainActivity.GAScreenEvent(tag);

            HashMap<String, Object> eventValue = new HashMap<String, Object>();

            eventValue.put(getResourceString(R.string.SCREEN_NAME), tag);
            eventValue.put(getResourceString(R.string.LAST_SCREEN_NAME), this.mLastScreenName);
            eventValue.put(getResourceString(R.string.TIME_LAPSED_SINCE_LAST_SCREEN_NAME_IN_MS), String.valueOf(new Date().getTime() - this.mTimeScreenClicked.getTime()));

            this.mTimeScreenClicked = new Date();

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_SCREEN_SELECTED), eventValue, this);

            this.mLastScreenName = tag;
        }
        if(!(fragment instanceof InstituteListFragment))
            invalidateOptionsMenu();
    }
    private boolean isUpdateStreams;
    @Override
    public void onDataLoaded(String tag, String response) {
        String extraTag = null;
        String childIndex = null;
        String parentIndex = null;
        String like = null;
        String[] tags = tag.split("#");
        int voteType = 0;
        boolean hideProgressDialog=true;
        switch (tags[0]) {
            case Constants.TAG_SKIP_LOGIN:
            case Constants.TAG_TRUE_SDK_LOGIN:
            case Constants.TAG_FACEBOOK_LOGIN:
                this.mUpdateUserPreferences(response);
                break;
            case Constants.TAG_USER_EDUCATION:
                this.mDisplayUserEducationFragment(response);
                break;
            case Constants.TAG_EDIT_USER_EDUCATION:
                this.mDisplayEditUserEducationFragment(response);
                break;
            case Constants.TAG_EDUCATION_DETAILS_SUBMIT:
                this.mOnUserEducationResponse(response);
                break;
            case Constants.TAG_EDIT_EDUCATION_DETAILS_SUBMIT:
                this.onUserEducationEdited(response);
                break;
            case Constants.TAG_NOT_PREPARING_EDUCATION_DETAILS_SUBMIT:
                this.onNotPreparingEducationResponse(response);
                break;
            case Constants.TAG_USER_EDUCATION_SET:
                this.mUserEducationStepCompleted(response);
                break;
            case Constants.TAG_EXAMS_LIST:
                this.mOnExamsLoaded(response, true);
                break;
            case Constants.TAG_EDIT_EXAMS_LIST:
                this.mOnEditExamsLoaded(response, true);
                break;
            case Constants.TAG_LOAD_EXAMS_LIST:
                this.mOnExamsLoaded(response, false);
                break;
            case Constants.TAG_SUBMIT_EXAMS_LIST:
                this.mOnUserExamsSelected(response);
                break;
            case Constants.TAG_SUBMIT_EDITED_EXAMS_LIST:
                this.onUserExamsEdited(response);
                break;
//            case Constants.TAG_USER_EXAMS_SET:
//                this.mUserExamStepCompleted(response);
//                break;
            case Constants.TAG_LAUNCH_USER_HOME:
                this.onUpdateUserPreferences(response);
                if (!IS_PROFILE_LOADED) {
                    this.mClearBackStack();
                    mLoadUserProfile(null);
                }
                break;
            case Constants.TAG_UPDATE_USER_PROFILE:
                this.onUpdatePreferences(response);
                break;
            case Constants.TAG_LOAD_USER_PREFERENCES:
                this.onUpdateUserPreferences(response);
                break;
            case Constants.TAG_LOAD_USER_PREFERENCES_N_BACK:
                isReloadProfile = true;
                this.onUpdateUserPreferences(response);
                onBackPressed();
                if (currentFragment != null && currentFragment instanceof UserEducationFragment) {
                    onBackPressed();
                }
                break;
            case Constants.TAG_EXAM_SUMMARY:
                this.mUpdateExamDetail(response, true);
                break;
            case Constants.TAG_LOAD_STREAM:
                this.mDisplayStreams(response, true);
                break;
            case Constants.WIDGET_SHORTLIST_INSTITUTES:
                this.mCurrentTitle = "WishList Institutes";
                Constants.IS_RECOMENDED_COLLEGE = false;
                this.mDisplayInstituteList(response, false, true, Constants.SHORTLIST_TYPE);
                break;
            case Constants.WIDGET_INSTITUTES:
                this.mCurrentTitle = "Institutes";
                Constants.IS_RECOMENDED_COLLEGE = false;
                this.mDisplayInstituteList(response, true, true);
                break;
            case Constants.WIDGET_RECOMMENDED_INSTITUTES:
                this.mCurrentTitle = "Recommended Institutes";
                Constants.IS_RECOMENDED_COLLEGE = true;
                if (tags.length == 2 && tags[1] != null && tags[1].equals("next")) {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.UNBAISED, true);
                } else {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.UNBAISED);
                }
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
            case Constants.TAG_LOAD_COURSES:
                this.mUpdateCourses(response);
                break;
            case Constants.TAG_LOAD_INSTITUTE_NEWS:
                this.mUpdateInstituteNews(response);
                break;
            case Constants.TAG_LOAD_INSTITUTE_ARTICLE:
                this.mUpdateInstituteArticle(response);
                break;
            case Constants.TAG_APPLIED_COURSE:
                String tabPosition = null;
                if (tags.length == 3) {
                    extraTag = tags[1];
                    tabPosition = tags[2];
                }
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                this.mUpdateAppliedCourses(response, extraTag, tabPosition);
                break;

            case Constants.TAG_WISH_LIST_APPLIED_COURSE:
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                if (currentFragment != null && currentFragment instanceof CDRecommendedInstituteListFragment) {
                    if(mInstituteList!=null && mInstituteList.size()>0 && institute!=null && mInstituteList.contains(institute)) {
                        mInstituteList.remove(institute);
                        ((CDRecommendedInstituteListFragment) currentFragment).updateList(this.mInstituteList, next);
                    }
                }
                break;
            case Constants.TAG_POST_QUESTION:
                this.mInstituteQnAQuestionAdded(response);
                break;
            case Constants.TAG_LOAD_FILTERS:
                this.updateFilterList(response);
                break;
            case Constants.TAG_NEXT_INSTITUTE:
                this.updateNextInstituteList(response);
                break;
            case Constants.TAG_NEXT_SHORTLIST_INSTITUTE:
                this.updateNextInstituteList(response);
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
            case Constants.TAG_SHORTLIST_INSTITUTE:
                if (tags.length == 2)
                    extraTag = tags[1];
                this.updateShortlistInstitute(response, extraTag);
                break;
            case Constants.TAG_DELETESHORTLIST_INSTITUTE:
                if (tags.length == 2)
                    extraTag = tags[1];
                this.updateShortlistInstitute(null, extraTag);
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
                if (tags.length == 2)
                    extraTag = tags[1];
                if (tags.length == 3) {
                    extraTag = tags[1];
                    like = tags[2];
                }
                this.updateQuestionLikeButton(response, extraTag, Integer.parseInt(like));
                break;
            case Constants.TAG_LOAD_PSYCHOMETRIC_TEST:
                this.loadPyschometricTest(response);
                break;
            case Constants.ACTION_VOTE_QNA_QUESTION_ENTITY:
                if (tags.length == 3) {
                    parentIndex = tags[1];
                    voteType = Integer.parseInt(tags[2]);
                    this.mQnAQuestionVoteUpdated(Integer.parseInt(parentIndex), voteType);
                }
                break;
            case Constants.ACTION_VOTE_QNA_ANSWER_ENTITY:
                if (tags.length > 3) {
                    parentIndex = tags[1];
                    childIndex = tags[2];
                    voteType = Integer.parseInt(tags[3]);

                    this.mQnAAnswerVoteUpdated(Integer.parseInt(parentIndex), Integer.parseInt(childIndex), voteType);
                    //GA Event for answer vote
                    MainActivity.GATrackerEvent(getResourceString(R.string.CATEGORY_QNA), Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, "Answer Voted : " + String.valueOf(voteType));
                }
                break;
            case Constants.TAG_LOAD_MY_FB:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    this.mShowMyFB(response, Integer.parseInt(parentIndex), Integer.parseInt(childIndex));
                }
                break;
            case Constants.TAG_REFRESH_MY_FB:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    this.mUpdateMyFB(response, Integer.parseInt(parentIndex), Integer.parseInt(childIndex));
                }
                break;
            case Constants.ACTION_QNA_ANSWER_SUBMITTED:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    this.mOnAnswerAdded(response, Integer.parseInt(parentIndex), Integer.parseInt(childIndex));
                }
                break;
            case Constants.ACTION_MY_FB_COMMENT_SUBMITTED:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    this.mOnMyFBCommentAdded(response, Integer.parseInt(parentIndex), Integer.parseInt(childIndex));
                }
                break;
            case Constants.TAG_LOAD_INSTITUTE_QNA_QUESTIONS:
                this.mInstituteQnAUpdated(response);
                break;

            case Constants.TAG_SUBMIT_PREFRENCES:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    if (tags.length > 3)
                        extraTag = tags[3];

                    this.mStreamAndLevelSelected(response, parentIndex, childIndex, extraTag);
                }

                break;

            case Constants.TAG_SUBMIT_EDITED_PREFRENCES:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    if (tags.length > 3)
                        extraTag = tags[3];

                    this.mStreamAndLevelSelected(response, parentIndex, childIndex, extraTag, true);
                }

                break;
            case Constants.TAG_UPDATE_PREFRENCES:
                this.mStreamAndLevelUpdated(response);
                this.mClearBackStack();
                break;
            case Constants.TAG_UPDATE_STREAM:
                this.mDisplayStreams(response, true);
                break;
            case Constants.TAG_UPDATE_INSTITUTES:
                this.mUpdateInstituteList(response);
                break;
            case Constants.TAG_USER_REGISTRATION:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                }
                this.onUserRegisteredResponse(response, parentIndex);
                break;
            case Constants.TAG_USER_LOGIN:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                }
                this.onUserSignInResponse(response, parentIndex);
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
                String examId = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getString(Constants.SELECTED_EXAM_ID, "");
                if (!examId.isEmpty()) {
                    hideProgressDialog=false;
                    this.mMakeNetworkCall(Constants.WIDGET_TEST_CALENDAR, Constants.BASE_URL + "yearly-exams/" + examId + "/calendar/", null);
                }
                break;
            case Constants.TAG_NAME_UPDATED:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    this.onNameUpdatedResponse(response, parentIndex);
                }
                break;
            case Constants.TAG_PSYCHOMETRIC_QUESTIONS:
                isUpdateStreams = false;
                this.onPsychometricTestResponse(response);
                break;

            case Constants.TAG_EDIT_PSYCHOMETRIC_QUESTIONS:
                isUpdateStreams = true;
                this.onEditPsychometricTestResponse(response);
                break;

            case Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM:
                this.mDisplayStreamsSelection(response, false);
                break;
            case Constants.TAG_SUBMIT_EDIT_PSYCHOMETRIC_EXAM:
                this.mDisplayStreamsEditSelection(response, true);
                break;
            case Constants.TAG_PSYCHOMETRIC_TEXT_COMPLETED:
                if (tags.length > 2) {
                    parentIndex = tags[1];
                    childIndex = tags[2];
                    this.mOnPsychometricTestCompleted(parentIndex, childIndex, response);
                }
                break;
            case Constants.TAG_LOAD_STEP_BY_STEP:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];
                    this.mDisplayStepByStepQuestion(response, parentIndex, childIndex);
                }
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
                this.onPNSNews(response);
                break;
            case Constants.PNS_QNA:
                this.onPnsQnA(response);
                break;
            case Constants.PNS_INSTITUTES:
                this.onPnsInstituteNews(response);
                break;
            case Constants.TAG_INSTITUTE_DETAILS:
                this.onInstituteDetailsLinkResponse(response);
                break;
            case Constants.PNS_ARTICLES:
                this.onPnsArticles(response);
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
            case Constants.TAG_UPDATE_VIDEO_TITLE:
                this.onUpdateTitleResponse(response);
                break;
            case Constants.TAG_USER_PHONE_ADDED:
                onMobileNumberSubmitted();
//                displayGetOTPAlert(this);
                break;
            case Constants.TAG_VERIFY_USER_PHONE:
                this.onOTPVerified(response);
                break;
            case Constants.TAG_RECOMMENDED_SHORTLIST_INSTITUTE:
                this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.SHORTLISTED);
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                if (tags.length == 3) {
                    this.mUpdateUndecidedCount(this.mUndecidedCount, true);
                }  if (tags.length == 2)
            {
                this.mUpdateUndecidedCount(this.mUndecidedCount, true);
                parentIndex = tags[1];
                if (parentIndex.equals("true"))
                    this.OnCDRecommendedLoadNext();
            }
                break;
            case Constants.TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE:
                this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.NOT_INERESTED);
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                if (tags.length == 3) {
                    this.mUpdateUndecidedCount(this.mUndecidedCount, true);
                }
                if (tags.length == 2) {
                    this.mUpdateUndecidedCount(this.mUndecidedCount, true);
                    parentIndex = tags[1];
                    if (parentIndex.equals("true"))
                        this.OnCDRecommendedLoadNext();
                }
                break;
            case Constants.TAG_LOAD_UNDECIDED_INSTITUTE:
                if (tags.length == 3) {

                }
                if (tags.length == 2) {
                    ++this.mUndecidedCount;
                    this.mUpdateUndecidedCount(this.mUndecidedCount, false);
                    this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.UNDECIDED);
                    parentIndex = tags[1];
                    if (parentIndex.equals("true"))
                        this.OnCDRecommendedLoadNext();
                }
                else if (tags.length == 1)
                {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.UNDECIDED);
                }
                break;

            case Constants.TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE:
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                if (tags.length == 3) {

                }
                if (tags.length == 2) {
                    ++this.mUndecidedCount;
                    this.mUpdateUndecidedCount(this.mUndecidedCount, false);
                    this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.UNDECIDED);
                    parentIndex = tags[1];
                    if (parentIndex.equals("true"))
                        this.OnCDRecommendedLoadNext();
                }
                else if (tags.length == 1)
                {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.UNDECIDED);
                }
                break;
            case Constants.SUBMITTED_CHAPTER_STATUS:
                if (tags.length>1)
                    DataBaseHelper.getInstance(this).deleteExamSummary(Integer.parseInt(tags[1]));
                break;
        }
        try {
            if(hideProgressDialog)
            hideProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType type) {
        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.INSTITUTE_RESOURCE_URI), this.mInstitute.getResource_uri());
        eventValue.put(Constants.INSTITUTE_ID, String.valueOf(this.mInstitute.getId()));

        //GA Event
        //String[] lables = new String[3];
        //lables[0] = String.valueOf(this.mInstitute.getId());
        //lables[1] = this.mInstitute.getResource_uri();

        //Connecto
        //Properties properties = new Properties();

        switch (type) {
            case UNDECIDED:
                eventValue.put(Constants.CD_RECOMMENDED_INSTITUTE_ACTION_TYPE, Constants.CDRecommendedInstituteType.UNDECIDED.toString());
                break;
            case SHORTLISTED:
                eventValue.put(Constants.CD_RECOMMENDED_INSTITUTE_ACTION_TYPE, Constants.CDRecommendedInstituteType.SHORTLISTED.toString());
                break;
            case NOT_INERESTED:
                eventValue.put(Constants.CD_RECOMMENDED_INSTITUTE_ACTION_TYPE, Constants.CDRecommendedInstituteType.NOT_INERESTED.toString());
                break;
        }

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_CD_RECOMMENDED_INSTITUTE_ACTION), eventValue, this);
    }

    private void mUpdateUndecidedCount(int i, boolean isIncremented) {
        if (currentFragment instanceof CDRecommendedInstituteListFragment)
            ((CDRecommendedInstituteListFragment) currentFragment).mUpdateUndecidedCount(i, isIncremented);
    }

    private void mOnPsychometricTestCompleted(String streamId, String streamName, String response) {
        try {
            User userObj = JSON.std.beanFrom(User.class, response);
            if (MainActivity.user != null) {
                MainActivity.user.setStream_name(streamName);
                MainActivity.user.setStream(streamId);
                MainActivity.user.setLevel_name(userObj.getLevel_name());
                MainActivity.user.setLevel(userObj.getLevel());
                MainActivity.user.setUser_education(userObj.getUser_education());
                MainActivity.user.setCollegedekho_recommended_streams(userObj.getCollegedekho_recommended_streams());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!isUpdateStreams) {
            this.mClearBackStack();
            this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_EXAMS_LIST, Constants.BASE_URL + "user-exams/", null, 0);
        } else {
            this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_EDITED_EXAMS_LIST, Constants.BASE_URL + "user-exams/", null, 0);
        }
    }

    private void mUpdateExamDetail(String responseJson, boolean update) {
        try {
            ExamSummary examSummary = JSON.std.beanFrom(ExamSummary.class, responseJson);
            if (examSummary.getPreference_uri() != null) {
                MainActivity.user.setResource_uri(examSummary.getPreference_uri());
                String u = JSON.std.asString(MainActivity.user);
                String dbSummary = DataBaseHelper.getInstance(this).getExamSummary(Integer.parseInt(examSummary.getYearly_exam_id()));
                if (dbSummary == null) {
                    DataBaseHelper.getInstance(this).addExamSummary(Integer.parseInt(examSummary.getYearly_exam_id()), responseJson);
                } else if (update) {
                    DataBaseHelper.getInstance(this).updateExamSummary(Integer.parseInt(examSummary.getYearly_exam_id()), responseJson);
                }
                this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).commit();
            }
            this.currentFragment.updateExamSummary(examSummary);
            if (MainActivity.type != null && !MainActivity.type.matches("") && MainActivity.resource_uri != null && !MainActivity.resource_uri.matches("")) {
                mHandleNotifications(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
    }

    /**
     * This method is used to provide education level
     * and user will select current education  and request for exams
     * relative to his/her education
     * @param response
     */
    private void mDisplayUserEducationFragment(String response) {
        try {
            JSONObject responseObject=new JSONObject(response);
            JSONArray levelsArray=responseObject.getJSONArray("levels");
            if(levelsArray!=null && levelsArray.length()>0) {
                ArrayList<UserEducation> userEducationList = (ArrayList<UserEducation>) JSON.std.listOfFrom(UserEducation.class, levelsArray.toString());

                this.mDisplayFragment(UserEducationFragment.newInstance(userEducationList), false, getResourceString(R.string.TAG_FRAGMENT_USER_EDUCATION));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to display step by step questions
     *
     * @param response
     */
    private void mDisplayStepByStepQuestion(String response, String currentLevel, String questionSetCount) {
        try {
            //set current level
            StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.valueOf(currentLevel));

            response = response.substring(13, response.length() - 1);

            ArrayList<StepByStepQuestion> stepByStepQuestions = (ArrayList<StepByStepQuestion>) JSON.std.listOfFrom(StepByStepQuestion.class, response);

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(StepByStepFragment.class.getSimpleName());

            if (fragment == null) {
                StepByStepFragment stepByStepFragment = StepByStepFragment.newInstance(stepByStepQuestions);
                this.mDisplayFragment(stepByStepFragment, true, StepByStepFragment.class.getSimpleName());
            } else {
                if (fragment instanceof StepByStepFragment)
                    ((StepByStepFragment) fragment).updateQuestionSet(stepByStepQuestions, Integer.parseInt(questionSetCount));

                this.mDisplayFragment(fragment, false, StepByStepFragment.class.getSimpleName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to display step by step results
     *
     * @param response
     */
    private void mStepByStepDone(String response) {
        try {
            StepByStepResult sbsResult = JSON.std.beanFrom(StepByStepResult.class, response);

            //sbsResult
            MainActivity.user.setStream_name(sbsResult.getStream_name());
            MainActivity.user.setStream(String.valueOf(sbsResult.getStream_id()));

            String u = null;
            u = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).commit();

            int count = 0;
            Map<String, String> filterKeywords = new HashMap<>();

            for (int n = 0; n < sbsResult.getTags().size(); n++)
                filterKeywords.put("tag_uris[" + (count++) + "]", sbsResult.getTags().get(n));

            this.mFilterKeywords = filterKeywords;
            this.mFilterCount = this.mFilterKeywords.size();

            //save preferences.
            getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getResourceString(R.string.PROFILE_SCREEN_TUTE), true).apply();
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).apply();

            //move to profile
            this.mClearBackStack();
            this.mLoadUserProfile(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to display the syllabus
     * with the context of an exam
     *
     * @param response
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

    private void mStreamAndLevelUpdated(String response) {
        User tempUser = MainActivity.user;
        try {
            tempUser = JSON.std.beanFrom(User.class, response);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //save the preferences locally
        MainActivity.user.setPref(User.Prefs.STREAMKNOWN);
        if (tempUser != null) {
            MainActivity.user.setPhone_no(tempUser.getPhone_no());
            MainActivity.user.setName(tempUser.getName());
        }
        try {
            String u = null;
            u = JSON.std.asString(user);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.USER_STREAM_NAME), MainActivity.user.getStream_name());

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_STREAM_UPDATED), eventValue, this);

        eventValue.clear();
        eventValue.put(getResourceString(R.string.USER_LEVEL_NAME), MainActivity.user.getLevel_name());

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_LEVEL_UPDATED), eventValue, this);
    }

    private void mStreamAndLevelSelected(String response, String level, String stream, String streamName) {
        this.mStreamAndLevelSelected(response, level, stream, streamName, false);
    }

    //Saved on DB, now save it in shared preferences.
    private void mStreamAndLevelSelected(String response, String level, String stream, String streamName, boolean isUpdateStreams) {
        //Retrieve token from pref to save it across the pref updates

        this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean(getResourceString(R.string.USER_CREATED), true).apply();
        User tempUser = MainActivity.user;
        if (!isUpdateStreams) {

            try {
                MainActivity.user = JSON.std.beanFrom(User.class, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //save the preferences locally
            MainActivity.user.setPref(User.Prefs.STREAMKNOWN);
            if (tempUser != null) {
                MainActivity.user.setToken(tempUser.getToken());
                MainActivity.user.setImage(tempUser.getImage());
                MainActivity.user.setPrimaryEmail(tempUser.getPrimaryEmail());
                MainActivity.user.setPrimaryPhone(tempUser.getPrimaryPhone());
                MainActivity.user.profileData = tempUser.profileData;
            }

            if (streamName != "" && streamName != null)
                MainActivity.user.setStream_name(streamName);

            try {
                String user = "";
                user = JSON.std.asString(MainActivity.user);
                this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), user).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(getResourceString(R.string.USER_STREAM_NAME), MainActivity.user.getStream_name());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_STREAM_SELECTED), eventValue, this);

            eventValue.clear();
            eventValue.put(getResourceString(R.string.USER_LEVEL_NAME), MainActivity.user.getLevel_name());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_LEVEL_SELECTED), eventValue, this);

            this.mClearBackStack();
            this.mLoadUserProfile(null);
        } else {
            try {
                User user = JSON.std.beanFrom(User.class, response);
                if (streamName != "" && streamName != null)
                    MainActivity.user.setStream_name(streamName);
                MainActivity.user.setPsychometric_given(user.getPsychometric_given());
                MainActivity.user.setToken(tempUser.getToken());
                MainActivity.user.setImage(tempUser.getImage());
                MainActivity.user.setUser_education(user.getUser_education());
                MainActivity.user.setCollegedekho_recommended_streams(user.getCollegedekho_recommended_streams());
                MainActivity.user.setPrimaryEmail(tempUser.getPrimaryEmail());
                MainActivity.user.setPrimaryPhone(tempUser.getPrimaryPhone());
                MainActivity.user.profileData = tempUser.profileData;
                String u = "";
                u = JSON.std.asString(MainActivity.user);
                this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                onBackPressed();
                onBackPressed();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void updateQuestionLikeButton(String response, String extraTag, int like) {

        if (this.mQnAQuestions == null) return;
        QnAQuestions qnaQuestion = this.mQnAQuestions.get(Integer.parseInt(extraTag));
        if (like == Constants.NEITHER_LIKE_NOR_DISLIKE) {
            qnaQuestion.setCurrent_user_vote_type(Constants.NEITHER_LIKE_NOR_DISLIKE);
            qnaQuestion.setUpvotes(qnaQuestion.getUpvotes() - 1);

            Map<String,Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_QUESTION_LIKE_DISLIKE, Constants.NEITHER_LIKE_NOR_DISLIKE);
            eventValue.put(qnaQuestion.getTitle(), Constants.NEITHER_LIKE_NOR_DISLIKE);
            eventValue.put(getResourceString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);

        } else {
            if (like != Constants.NEITHER_LIKE_NOR_DISLIKE) {

                qnaQuestion.setCurrent_user_vote_type(like);
                if (like == Constants.LIKE_THING) {
                    qnaQuestion.setUpvotes(qnaQuestion.getUpvotes() + 1);

                    Map<String,Object> eventValue = new HashMap<>();
                    eventValue.put(getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_UPVOTED), Constants.LIKE_THING);
                    eventValue.put(qnaQuestion.getTitle(), Constants.LIKE_THING);
                    eventValue.put(getResourceString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

                    //Events
                    AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);

                } else if (like == Constants.DISLIKE_THING) {
                    qnaQuestion.setUpvotes(qnaQuestion.getUpvotes() - 1);

                    Map<String,Object> eventValue = new HashMap<>();
                    eventValue.put(getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_DOWNVOTED), Constants.DISLIKE_THING);
                    eventValue.put(qnaQuestion.getTitle(), Constants.DISLIKE_THING);
                    eventValue.put(getResourceString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

                    //Events
                    AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);
                }
            }
        }
        if (currentFragment instanceof QnAQuestionsListFragment)
            currentFragment.updateLikeButtons(Integer.parseInt(extraTag));
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
            eventValue.put(getResourceString(R.string.VOTE_TYPE), String.valueOf(Constants.NEITHER_LIKE_NOR_DISLIKE));
            eventValue.put(Constants.TAG_INSTITUTE_LIKE_DISLIKE, String.valueOf(Constants.NEITHER_LIKE_NOR_DISLIKE));

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_LIKING_UNBIASED), eventValue, this);
        } else {
            try {
                institute.setCurrent_user_vote_type(like);

                if (like == Constants.LIKE_THING) {
                    institute.setUpvotes(institute.getUpvotes() + 1);

                    Map<String, Object> eventValue = new HashMap<>();
                    eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                    eventValue.put(getResourceString(R.string.VOTE_TYPE), String.valueOf(Constants.LIKE_THING));
                    eventValue.put(Constants.TAG_INSTITUTE_LIKE_DISLIKE, String.valueOf(Constants.LIKE_THING));

                    //Events
                    AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_LIKED), eventValue, this);
                } else if (like == Constants.DISLIKE_THING) {
                    Map<String, Object> eventValue = new HashMap<>();
                    eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                    eventValue.put(getResourceString(R.string.VOTE_TYPE), String.valueOf(Constants.DISLIKE_THING));
                    eventValue.put(Constants.TAG_INSTITUTE_LIKE_DISLIKE, String.valueOf(Constants.DISLIKE_THING));

                    //Events
                    AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_DISLIKED), eventValue, this);
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
        String message = null;
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        if (response == null) {
            institute.setIs_shortlisted(Constants.SHORTLISTED_NO);
            message = " removed from your shortlist";

            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
            eventValue.put(Constants.TAG_SHORTLIST_INSTITUTE, Constants.SHORTLISTED_NO);
            eventValue.put(getResourceString(R.string.ACTION_INSTITUTE_SHORTLISTED), Constants.SHORTLISTED_NO);
            eventValue.put(getResourceString(R.string.INSTITUTE_RESOURCE_URI), institute.getResource_uri());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_SHORTLISTED_REMOVED), eventValue, this);
        } else {
            try {
                institute.setIs_shortlisted(Constants.SHORTLISTED_YES);
                message = " added to your shortlist";

                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                eventValue.put(Constants.TAG_SHORTLIST_INSTITUTE, Constants.SHORTLISTED_YES);
                eventValue.put(getResourceString(R.string.ACTION_INSTITUTE_SHORTLISTED), Constants.SHORTLISTED_YES);
                eventValue.put(getResourceString(R.string.INSTITUTE_RESOURCE_URI), institute.getResource_uri());

                //Events
                AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_SHORTLISTED), eventValue, this);
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
            ArrayList<String> similarNewsIds = new ArrayList<String>();
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
            ArrayList<String> similarArticlesIds = new ArrayList<String>();
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

    public static String GetPersonalizedMessage(String tag) {
        switch (tag) {

            case Constants.TAG_SKIP_LOGIN:
            case Constants.TAG_EDIT_EXAMS_LIST:
            case Constants.TAG_EDIT_PSYCHOMETRIC_QUESTIONS:
            case Constants.TAG_EDIT_USER_EDUCATION:
            case Constants.TAG_LOAD_USER_PREFERENCES:
            case Constants.TAG_LOAD_USER_PREFERENCES_N_BACK:
            case Constants.TAG_PSYCHOMETRIC_RESPONSE:
            case Constants.TAG_EDIT_EDUCATION_DETAILS_SUBMIT:
            case Constants.TAG_UPDATE_VIDEO_TITLE:
                return "Loading....";
            case Constants.TAG_USER_REGISTRATION:
                return "Creating User Please Wait";
            case Constants.TAG_USER_LOGIN:
                return "Signing User Please Wait.....";
            case Constants.TAG_LOAD_STREAM:
            case Constants.TAG_UPDATE_STREAM:
            case Constants.TAG_SUBMIT_EDIT_PSYCHOMETRIC_EXAM:
                return "Loading Streams...";
            case Constants.TAG_EDUCATION_DETAILS_SUBMIT:
                return "Loading Exams...";
            case Constants.TAG_EXAM_SUMMARY:
            case Constants.TAG_SUBMIT_EDITED_EXAMS_LIST:
            case Constants.WIDGET_SYLLABUS:
                return "Loading....";
            case Constants.TAG_SUBMIT_EXAMS_LIST:
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM:
                return "Loading Profile...";
            case Constants.TAG_UPDATE_INSTITUTES:
                return "Updating Institutes...";
            case Constants.TAG_UPDATE_PREFRENCES:
                return "Updating Profile...";
            case Constants.WIDGET_INSTITUTES:
            case Constants.WIDGET_SHORTLIST_INSTITUTES:
            case Constants.WIDGET_RECOMMENDED_INSTITUTES:
                return "Loading Institutes...";
            case Constants.WIDGET_NEWS:
                return "Loading News...";
            case Constants.WIDGET_ARTICES:
                return "Loading Articles...";
            case Constants.WIDGET_COURSES:
                return "Loading Courses...";
            case Constants.TAG_POST_QUESTION:
                return "Posting your question...";
            case Constants.TAG_LOAD_FILTERS:
                return "Loading Filters...";
            case Constants.TAG_LOAD_PSYCHOMETRIC_TEST:
                return "Loading Questionnaire...";
            case Constants.TAG_LOAD_QNA_QUESTIONS:
                return "Loading Questions...";
            case Constants.TAG_LOAD_MY_FB:
                return "Loading your Forums chat...";
            /*case Constants.ACTION_MY_FB_COMMENT_SUBMITTED:
                return "Submitting Comment...";*/
            case Constants.ACTION_QNA_ANSWER_SUBMITTED:
                return "Submitting Answer...";
            case Constants.ACTION_VOTE_QNA_QUESTION_ENTITY:
            case Constants.ACTION_VOTE_QNA_ANSWER_ENTITY:
                return "Processing Vote...";
            case Constants.WIDGET_FORUMS:
                return "Loading Forums...";
            case Constants.SEARCHED_INSTITUTES:
                return "Loading...";
            case Constants.TAG_PSYCHOMETRIC_QUESTIONS:
                return "Loading ....";
            case Constants.WIDGET_TEST_CALENDAR:
                return "Loading your plan...";
            case Constants.TAG_MY_ALERTS:
                return "Loading important events...";
            case Constants.TAG_VERIFY_USER_PHONE:
                return "Verifying OTP...";
            case Constants.TAG_USER_PHONE_ADDED:
                return "Requesting OTP...";
            case Constants.TAG_NEXT_ARTICLES:
            case Constants.TAG_NEXT_FORUMS_LIST:
            case Constants.TAG_NEXT_INSTITUTE:
            case Constants.TAG_NEXT_NEWS:
            case Constants.TAG_NEXT_QNA_LIST:
            case Constants.TAG_NEXT_SHORTLIST_INSTITUTE:
            case Constants.TAG_INSTITUTE_LIKE_DISLIKE:
            case Constants.TAG_QUESTION_LIKE_DISLIKE:
            case Constants.ACTION_INSTITUTE_DISLIKED:
            case Constants.TAG_APPLIED_COURSE:
            case Constants.TAG_RECOMMENDED_SHORTLIST_INSTITUTE:
            case Constants.TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE:
            case Constants.TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE:
            case Constants.TAG_SHORTLIST_INSTITUTE:
            case Constants.TAG_DELETESHORTLIST_INSTITUTE:
            case "":
                return null;
            default:
                return "Loading...";
        }
//        return null;
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
     * @param response
     */
    private void mUpdateInstituteArticle(String response) {
        try {

            this.mArticlesList = JSON.std.listOfFrom(Articles.class, extractResults(response));
            this.mParseSimilarArticle(this.mArticlesList);

            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteArticle((ArrayList) this.mArticlesList, next);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
    /**
     * This method is used to update institute news
     * @param response
     */
    private void mUpdateInstituteNews(String response) {
        try {

            this.mNewsList = JSON.std.listOfFrom(News.class, extractResults(response));
            this.mParseSimilarNews(this.mNewsList);

            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteNews((ArrayList) this.mNewsList, next);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mUpdateAppliedCourses(String response, String extraTag, String tabPosition) {
        try {
            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment)
                ((InstituteDetailFragment) currentFragment).updateAppliedCourses(response);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onError(final String tag, String response, final String url, final Map<String, String> params, final int method) {
        hideProgressDialog();
        hideErrorDialog();

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
        String extraTag2 = null;
        String[] tags = tag.split("#");

        switch (tags[0]) {
            case Constants.TAG_APPLIED_COURSE: {

                if (tags.length == 3) {
                    extraTag = tags[1];
                    extraTag2 = tags[2];
                }
                this.mUpdateAppliedCourses(null, extraTag, extraTag2);
                break;
            }
            case Constants.TAG_SHORTLIST_INSTITUTE:
            case Constants.TAG_DELETESHORTLIST_INSTITUTE: {
                if (tags.length == 2)
                    extraTag = tags[1];
                if (Integer.parseInt(extraTag) == currentInstitute)
                    if (currentFragment instanceof InstituteDetailFragment)
                        ((InstituteDetailFragment) currentFragment).updateInstituteShortlist();
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
    public void onStreamSelected(final String stream, final String streamName) {
//        new AlertDialog.Builder(this)
//                .setTitle("Please select a level")
//                .setSingleChoiceItems(InstituteCourse.CourseLevel.getValues(), -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
        MainActivity.this.mOnCourseLevelSelected(/*which*/0, stream, streamName);
//                        dialog.dismiss();
//                    }
//                })
////                .setCancelable(false)
//                .show();
    }

    @Override
    public void onEditedStreamSelected(final String stream, final String streamName) {
//        new AlertDialog.Builder(this)
//                .setTitle("Please select a level")
//                .setSingleChoiceItems(InstituteCourse.CourseLevel.getValues(), -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
        MainActivity.this.mOnCourseLevelSelected(/*which*/0, stream, streamName, true);
//                        dialog.dismiss();
//                    }
//                })
////                .setCancelable(false)
//                .show();
    }

    private void mOnCourseLevelSelected(int level, String streamURI, String streamName) {
        this.mOnCourseLevelSelected(level, streamURI, streamName, false);
    }

    private void mOnCourseLevelSelected(int level, String streamURI, String streamName, boolean isEdited) {
        //get device id
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //send them to DB
        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("status", user.getPref().name().toLowerCase());
        hashMap.put("user", user.getResource_uri());
        hashMap.put(getResourceString(R.string.USER_STREAM), streamURI);
//        hashMap.put(Constants.USER_LEVEL, Constants.BASE_URL + "level/" + (MainActivity.user.getLevel()) + "/");
        hashMap.put(getResourceString(R.string.USER_STREAM_NAME), streamName);
        hashMap.put(getResourceString(R.string.USER_DEVICE_ID), deviceId);
        getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getResourceString(R.string.PROFILE_SCREEN_TUTE), true).apply();

        if (streamName != null || streamName != "")
            MainActivity.user.setStream_name(streamName);
        if (!isEdited) {
            this.mMakeNetworkCall(Constants.TAG_SUBMIT_PREFRENCES + "#" + level + "#" + streamURI + "#" + streamName, MainActivity.user.getPreference_uri(), hashMap, Request.Method.PUT);
        } else {
            this.mMakeNetworkCall(Constants.TAG_SUBMIT_EDITED_PREFRENCES + "#" + level + "#" + streamURI + "#" + streamName, MainActivity.user.getPreference_uri(), hashMap, Request.Method.PUT);

        }
    }

    @Override
    public void onInstituteSelected(int position) {
        this.currentInstitute = position;
        this.mDisplayInstituteByPosition(position);
    }
    /*@Override
    public void onShortListInstituteSelected(int position) {
        this.currentInstitute = position;
        this.mDisplayInstituteByPosition(position);
    }*/
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
        if (this.mFilters != "") {
            updateFilterList(this.mFilters);

            FragmentManager fragmentManager = this.getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_FILTER_LIST);
            if (fragment == null)
                this.mDisplayFragment(FilterFragment.newInstance(this.mFolderList), true, Constants.TAG_FRAGMENT_FILTER_LIST);
            else
                this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_FILTER_LIST);
        }
    }

    @Override
    public void onEndReached(String next, int listType) {
        if (next == null || next.equalsIgnoreCase("null")) return;
        if (listType == Constants.INSTITUTE_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_INSTITUTE, next, this.mFilterKeywords);
        else if (listType == Constants.SHORTLIST_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_SHORTLIST_INSTITUTE, next, null);
        else if (listType == Constants.NEWS_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_NEWS, next, null);
        else if (listType == Constants.ARTICLES_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_ARTICLES, next, null);
        else if (listType == Constants.QNA_LIST_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_QNA_LIST, next, null);
        else if (listType == Constants.FORUM_LIST_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_FORUMS_LIST, next, null);
        else if (listType == Constants.INSTITUTE_SEARCH_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_INSTITUTE, next, null);
    }

    @Override
    public void onQnAQuestionVote(int position, int voteType) {

        QnAQuestions qnaQuestion = this.mQnAQuestions.get(position);
        if (qnaQuestion.getCurrent_user_vote_type() == Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //neither liked nor disliked case
            if (voteType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + voteType, qnaQuestion.getResource_uri() + "upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + voteType, qnaQuestion.getResource_uri() + "downvote/", null, Request.Method.POST);
        } else if (qnaQuestion.getCurrent_user_vote_type() != Constants.NEITHER_LIKE_NOR_DISLIKE && voteType != Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //neither liked nor disliked case
            if (voteType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE, qnaQuestion.getResource_uri() + "upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE, qnaQuestion.getResource_uri() + "downvote/", null, Request.Method.POST);
        }
    }

    public void updateFilterList(String response) {
        mFolderList = new ArrayList<>();
        try {
            Folder.populateFolderList(JSON.std.getStreamingFactory().createParser(response), mFolderList);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onCourseApplied(final int position, final int tabPosition, final InstituteCourse instituteCourse) {

        final HashMap<String, String> params = new HashMap<>();
        if (user != null) {
            String name = user.getName();
            String email = user.getEmail();
            String phone = user.getPhone_no();

            if (name.equalsIgnoreCase(getResourceString(R.string.ANONYMOUS_USER)))
                name = "";

            if ((user.getName() == null || user.getName().isEmpty() || name.equalsIgnoreCase(getResourceString(R.string.ANONYMOUS_USER))) && user.profileData[0] != null)
                name = user.profileData[0];
            if (phone == null || phone.isEmpty())
                phone = user.getPrimaryPhone();
            if (user.getEmail().contains("@anonymouscollegedekho.com"))
                email = user.getPrimaryEmail();

            if (name == null || name.isEmpty() || name.toLowerCase().contains(getResourceString(R.string.ANONYMOUS_USER).toLowerCase()) ||
                    phone == null || phone.length() <= 6 ||
                    email == null || email.isEmpty() || email.contains("@anonymouscollegedekho.com")) {
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
                apply.show();
                (view.findViewById(R.id.submit_button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = ((TextView) view.findViewById(R.id.apply_name)).getText().toString();
                        String email = ((TextView) view.findViewById(R.id.apply_email)).getText().toString();
                        String phone = ((TextView) view.findViewById(R.id.apply_phone)).getText().toString();
                        if (name == null || name.isEmpty()) {
                            displayMessage(R.string.NAME_EMPTY);
                            return;
                        } else if (!Utils.isValidName(name)) {
                            displayMessage(R.string.NAME_INVALID);
                            return;
                        } else if (phone == null || phone.isEmpty()) {
                            displayMessage(R.string.PHONE_EMPTY);
                            return;
                        } else if (phone.length() <= 9 || phone.length() > 12 || !Utils.isValidPhone(phone)) {
                            displayMessage(R.string.PHONE_INVALID);
                            return;
                        } else if (email == null || email.isEmpty()) {
                            displayMessage(R.string.EMAIL_EMPTY);
                            return;
                        } else if (!Utils.isValidEmail(email)) {
                            displayMessage(R.string.EMAIL_INVALID);
                            return;
                        }
                        apply.dismiss();
                        params.put(getResourceString(R.string.USER_NAME), name);
                        params.put(getResourceString(R.string.USER_EMAIL), email);
                        params.put(getResourceString(R.string.USER_PHONE), phone);
                        params.put(getResourceString(R.string.APPLY_YEAR), mYear);
                        appplyCourse(instituteCourse, params, position, tabPosition);
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
                params.put(getResourceString(R.string.USER_NAME), name);
                params.put(getResourceString(R.string.USER_EMAIL), email);
                params.put(getResourceString(R.string.USER_PHONE), phone);

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                params.put(getResourceString(R.string.APPLY_YEAR), "" + year);
                appplyCourse(instituteCourse, params, position, tabPosition);
            }
        }
    }

    private void appplyCourse(InstituteCourse instituteCourse, HashMap params, int position, int tabPosition) {
        params.put(getResourceString(R.string.APPLY_COURSE), "" + instituteCourse.getId());
        if (mInstitute != null) {
            params.put("institute", "" + mInstitute.getId());
        }

        String URL = Constants.BASE_URL + "lms/";
        instituteCourseId = "" + instituteCourse.getId();
        this.mMakeNetworkCall(Constants.TAG_APPLIED_COURSE + "#" + position + "#" + tabPosition, URL, params, Request.Method.POST);

        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(instituteCourse.getId()));
        eventValue.put(getResourceString(R.string.APPLY_COURSE), instituteCourse.getName());
        eventValue.put(getResourceString(R.string.APPLY_INSTITUTE), mInstitute.getResource_uri());
        eventValue.put(getResourceString(R.string.APPLY_COURSE_ID), String.valueOf(instituteCourse.getId()));

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_COURSE_APPLIED), eventValue, this);
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

    /**
     * This method returns
     * @return
     */
    private Map mGetTheFilters() {
        Map<String, String> map = new HashMap<>();
        SharedPreferences sp = getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE);
        String value = sp.getString(Constants.SELECTED_FILTERS, null);

        if (value != null && value != "") {
            //split the string to create key-value pairs
            String[] keyValuePairs = value.replaceAll("\\{", "").replaceAll("\\}", "").split(",");

            //iterate over the pairs
            for (String pair : keyValuePairs) {
                if (pair != "" && pair != null) {
                    //split the pairs to get key and value
                    String[] entry = pair.split("=");
                    //add them to the hashmap and trim whitespaces
                    map.put(entry[0].trim(), entry[1].trim());
                }
            }
        }
        return map;
    }

    public void showProgressDialog(String message) {
        Log.e("MainActivity", "Message to show is : " + message);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog.setMessage(message);
        }
        if(!isFinishing())
        progressDialog.show();
        IS_NETWORK_TASK_RUNNING=true;
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        IS_NETWORK_TASK_RUNNING=false;
    }

    public void hideErrorDialog() {
        if (mErrorDialog != null && mErrorDialog.isShowing())
            mErrorDialog.dismiss();
    }

    public void displaySnackBar(int messageId) {
        try {
            if (snackbar != null && !snackbar.isShown()) {
                snackbar.setText(getResources().getString(messageId));
                snackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    @Override
    public void onNewsSelected(News news , boolean addToBackStack, View view) {
        if (!addToBackStack) {
            this.currentFragment.updateNews(news);
        }
        else {
            if (currentFragment instanceof NewsDetailFragment) {
                (currentFragment).updateNews(news);
            }else {
/*
                     Fragment fragment = NewsDetailFragment.newInstance(news, this.mNewsList);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fragment.setSharedElementEnterTransition(new DetailsTransition());
                        fragment.setEnterTransition(new Fade());
                        fragment.setExitTransition(new Fade());
                        fragment.setSharedElementReturnTransition(new DetailsTransition());
                    }

                    FragmentTransaction fragmentTransaction  = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addSharedElement(view, getResources().getString(R.string.news_image_transaction));
                    fragmentTransaction.replace(R.id.container, fragment);//, Constants.TAG_FRAGMENT_NEWS_DETAIL);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

                    fragmentTransaction.addToBackStack(fragment.toString());
                    fragmentTransaction.commit();*/
                this.mDisplayFragment(NewsDetailFragment.newInstance(news, this.mNewsList), addToBackStack, Constants.TAG_FRAGMENT_NEWS_DETAIL);
            }

            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(news.getId()));
            eventValue.put(getResourceString(R.string.ACTION_NEWS_SELECTED), news.getId());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_NEWS), getResourceString(R.string.ACTION_NEWS_SELECTED), eventValue, this);
        }
    }

    @Override
    public void onArticleSelected(Articles article, boolean addToBackstack, View view) {
        if (article == null) return;
        if (!addToBackstack) {
            this.currentFragment.updateArticle(article);
        } else {
            if (currentFragment instanceof ArticleDetailFragment) {
                (currentFragment).updateArticle(article);
            }else {

               /* Fragment fragment = ArticleDetailFragment.newInstance(article, this.mArticlesList);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fragment.setSharedElementEnterTransition(new DetailsTransition());
                    fragment.setEnterTransition(new Fade());
                    fragment.setExitTransition(new Fade());
                    fragment.setSharedElementReturnTransition(new DetailsTransition());
                }

                FragmentTransaction fragmentTransaction  = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addSharedElement(view, getResources().getString(R.string.article_image_transaction));
                fragmentTransaction.replace(R.id.container, fragment);//, Constants.TAG_FRAGMENT_NEWS_DETAIL);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();*/
                this.mDisplayFragment(ArticleDetailFragment.newInstance(article, this.mArticlesList), addToBackstack, Constants.TAG_FRAGMENT_ARTICLE_DETAIL);
            }
            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(article.getId()));
            eventValue.put(getResourceString(R.string.ACTION_ARTICLE_SELECTED), article.getId());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_ARTICLE), getResourceString(R.string.ACTION_ARTICLE_SELECTED), eventValue, this);
        }

    }

    @Override
    public void onQuestionAsked(QnAQuestions question) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", question.getTitle());
        map.put("desc", question.getDesc());
        if (!(currentFragment instanceof QnAQuestionsListFragment))
            map.put("institute", "" + this.mInstituteList.get(currentInstitute).getResource_uri());
        //map.put("stream", Constants.BASE_URL + "streams/1/");
        /*map.put("user", user.getResource_uri());
        //:TODO remove hard coding of streams/1
        map.put("stream", Constants.BASE_URL + "streams/1/");*/

        this.mMakeNetworkCall(Constants.TAG_POST_QUESTION, Constants.BASE_URL + "personalize/qna/", map, Request.Method.POST);
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
        this.mFilterKeywords = mFilterKeywords;
        this.mFilterCount = this.mFilterKeywords.size();

        this.mMakeNetworkCall(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", this.mFilterKeywords);

        this.mUpdateFilterButton();
        //save preferences.
        this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).apply();

        Map<String, Object> eventValue = new HashMap<String, Object>();
        for (String key : this.mFilterKeywords.keySet()) {
            eventValue.put(Constants.SELECTED_FILTERS, this.mFilterKeywords.get(key));

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_FILTER_APPLIED), eventValue, this);
        }
    }

    @Override
    public void onInstituteShortlisted(int position) {
        if (mInstituteList != null && mInstituteList.size() > position && position>=0) {
            Institute institute = this.mInstituteList.get(position);
            if (institute.getIs_shortlisted() == Constants.SHORTLISTED_NO) {
                    requestOtp();
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
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).apply();

            this.mMakeNetworkCall(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", null);
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

    @Override
    public void onInstituteShortlisted() {
        try {
            Institute institute = this.mInstituteList.get(this.currentInstitute);
            if (institute.getIs_shortlisted() == Constants.SHORTLISTED_NO)
                this.mMakeNetworkCall(Constants.TAG_SHORTLIST_INSTITUTE + "#" + this.currentInstitute, institute.getResource_uri() + "shortlist/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_DELETESHORTLIST_INSTITUTE + "#" + currentInstitute, institute.getResource_uri() + "shortlist/", null, Request.Method.DELETE);
        }catch(Exception e){

        }
    }

    private void mMakeNetworkCall(String tag, String url, Map<String, String> params, int method) {
        int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
        if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            this.networkUtils.networkData(tag, url, params, method);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    private void mMakeNetworkCall(String tag, String url, Map<String, String> params) {
        int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
        if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            this.networkUtils.networkData(tag, url, params);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    private void mMakePreferenceNetworkCall(String tag, String url, Map<String, String> params) {
        int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
        if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            this.networkUtils.putData(tag, url, params);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    private void mMakeJsonObjectNetworkCall(String tag, String url, JSONObject params, int method) {
        int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
        if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            this.networkUtils.networkDataWithObjectParam(tag, url, params, method);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    private void showProgress(String tag) {
        String[] tags = tag.split("#");

        String message = MainActivity.GetPersonalizedMessage(tags[0]);
        if (message != null && !(this.currentFragment instanceof SplashFragment))
            showProgressDialog(message);
    }

    @Override
    public void onQnAQuestionSelected(QnAQuestions qnaQuestion) {
        this.mDisplayFragment(new QnAQuestionDetailFragment().newInstance(qnaQuestion), true, getResourceString(R.string.TAG_FRAGMENT_QNA_QUESTION_DETAIL));
    }

    @Override
    public void onQnAQuestionVote(String resourceURI, int voteType, int position) {
        displayMessage(R.string.THANKS_FOR_VOTE);
        if (voteType == Constants.LIKE_THING) {
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "upvote/", null, Request.Method.POST);
        } else if (voteType == Constants.DISLIKE_THING) {
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "downvote/", null, Request.Method.POST);
        } else {
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI, null, Request.Method.POST);
        }
    }

    @Override
    public void onQnAAnswerVote(String resourceURI, int voteType, int answerPosition, int questionPosition) {
        if (voteType == Constants.LIKE_THING) {
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType), resourceURI + "upvote/", null, Request.Method.POST);
        } else if (voteType == Constants.DISLIKE_THING) {
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType), resourceURI + "downvote/", null, Request.Method.POST);
        } else {
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType), resourceURI, null, Request.Method.POST);
        }
    }

    @Override
    public void onQnAAnswerSubmitted(String questionURI, String answerText, int questionIndex, int answerIndex) {
        HashMap<String, String> params = new HashMap<>();
        params.put("answer_text", answerText);
        this.mMakeNetworkCall(Constants.ACTION_QNA_ANSWER_SUBMITTED + "#" + String.valueOf(questionIndex) + "#" + String.valueOf(answerIndex), questionURI + "answer/", params, Request.Method.POST);
    }

    private void mQnAQuestionVoteUpdated(int questionIndex, int voteType) {
        try {
            if (currentFragment instanceof QnAQuestionDetailFragment)
                ((QnAQuestionDetailFragment) currentFragment).onVotingFeedback(questionIndex, -1, voteType);

            Map<String, Object> eventValue = new HashMap<String, Object>();

            QnAQuestions question = this.mQnAQuestions.get(questionIndex);

            if (voteType == Constants.LIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(question.getResource_uri()));
                eventValue.put(getResourceString(R.string.VOTE_TYPE), Constants.LIKE_THING);
                eventValue.put(question.getResource_uri(), Constants.LIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.LIKE_THING);

                //Events
                AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_UPVOTED), eventValue, this);

            } else if (voteType == Constants.DISLIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(question.getResource_uri()));
                eventValue.put(getResourceString(R.string.VOTE_TYPE), Constants.DISLIKE_THING);
                eventValue.put(question.getResource_uri(), Constants.DISLIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.DISLIKE_THING);

                //Events
                AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_UPVOTED), eventValue, this);
            }

        } catch (Exception e) {
            Log.e("QnA question voting", e.getMessage());
        }
    }

    private void mQnAAnswerVoteUpdated(int questionIndex, int answerIndex, int voteType) {
        ((QnAQuestionDetailFragment) currentFragment).onVotingFeedback(questionIndex, answerIndex, voteType);
        try {
            Map<String, Object> eventValue = new HashMap<String, Object>();
            QnAAnswers answer = this.mQnAQuestions.get(questionIndex).getAnswer_set().get(answerIndex);

            if (voteType == Constants.LIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, answer.getResource_uri());
                eventValue.put(getResourceString(R.string.VOTE_TYPE), Constants.LIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.LIKE_THING);
                eventValue.put(answer.getResource_uri(), Constants.LIKE_THING);

                //Events
                AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_ANSWER_UPVOTED), eventValue, this);

            } else if (voteType == Constants.DISLIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, answer.getResource_uri());
                eventValue.put(getResourceString(R.string.VOTE_TYPE), Constants.DISLIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.DISLIKE_THING);
                eventValue.put(answer.getResource_uri(), Constants.DISLIKE_THING);

                //Events
                AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_ANSWER_DOWNVOTED), eventValue, this);
            }
        } catch (Exception e) {
            Log.e("QnA answer voting", e.getMessage());
        }
    }

    private void mOnAnswerAdded(String response, int questionIndex, int index) {
        QnAAnswers qnaAnswer = new QnAAnswers();
        try {

            JSONObject ans = new JSONObject(response);

            qnaAnswer.setUser(ans.getString("user"));
            qnaAnswer.setDownvotes(ans.getInt("downvotes"));
            qnaAnswer.setUpvotes(ans.getInt("upvotes"));
            qnaAnswer.setCurrent_user_vote_type(ans.getInt("current_user_vote_type"));
            qnaAnswer.setAnswer_text(ans.getString("answer_text"));
            qnaAnswer.setAdded_on(ans.getString("added_on"));
            qnaAnswer.setId(ans.getLong("id"));
            qnaAnswer.setQuestion(ans.getString("question"));
            qnaAnswer.setResource_uri(ans.getString("resource_uri"));
            qnaAnswer.setIndex(index);
            qnaAnswer.setQuestionIndex(questionIndex);

            if (currentFragment instanceof QnAQuestionDetailFragment)
                ((QnAQuestionDetailFragment) currentFragment).answerAdded(qnaAnswer);
            else if (currentFragment instanceof InstituteQnAFragment)
                ((InstituteQnAFragment) currentFragment).instituteQnAAnswerUpdated(qnaAnswer);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, qnaAnswer.getResource_uri());
            eventValue.put(getResourceString(R.string.QNA_ANSWER_RESOURCE_URI), qnaAnswer.getResource_uri());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_QNA_ANSWER_SUBMITTED), eventValue, this);
        }
    }

    @Override
    public void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position, int commentsCount) {
        this.mMakeNetworkCall(Constants.TAG_LOAD_MY_FB + "#" + String.valueOf(position) + "#" + String.valueOf(commentsCount), myFutureBuddiesEnumeration.getResource_uri(), null, Request.Method.GET);

        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(Constants.TAG_RESOURCE_URI, myFutureBuddiesEnumeration.getResource_uri());

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_MY_FB), getResourceString(R.string.ACTION_MY_FB_SELECTED), eventValue, this);
    }

    @Override
    public void onMyFBCommentSubmitted(String myFbURI, String commentText, int myFbIndex, int myFbCommentIndex) {
        HashMap<String, String> params = new HashMap<>();
        params.put("comment", commentText);

        this.mMakeNetworkCall(Constants.ACTION_MY_FB_COMMENT_SUBMITTED + "#" + String.valueOf(myFbIndex) + "#" + String.valueOf(myFbCommentIndex), myFbURI + "comment/", params, Request.Method.POST);
    }

    @Override
    public void onMyFBUpdated(int commentsSize, int myFbIndex) {
        if (currentFragment instanceof MyFutureBuddiesEnumerationFragment)
            ((MyFutureBuddiesEnumerationFragment) currentFragment).updateEnumerationList(commentsSize, myFbIndex);
    }

    private void mOnMyFBCommentAdded(String response, int fbIndex, int index) {
        try {
            JSONObject comment = new JSONObject(response);

            MyFutureBuddyComment fbComment = new MyFutureBuddyComment();

            fbComment.setComment(comment.getString("comment"));
            fbComment.setAdded_on(comment.getString("added_on"));
            fbComment.setUser(comment.getString("user"));
            fbComment.setToken(comment.getString("token"));
            fbComment.setIndex(index);
            fbComment.setFbIndex(fbIndex);
            fbComment.setCommentSent(true);

            if (currentFragment instanceof MyFutureBuddiesFragment)
                ((MyFutureBuddiesFragment) currentFragment).commentAdded(fbComment);

            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, this.mFbEnumeration.get(fbIndex).getResource_uri());
            eventValue.put(getResourceString(R.string.MY_FB_URI), this.mFbEnumeration.get(fbIndex).getResource_uri());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_MY_FB), getResourceString(R.string.ACTION_MY_FB_COMMENT_SUBMITTED), eventValue, this);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mInstituteQnAQuestionAdded(String response) {
        ArrayList<String> tagArrayList = new ArrayList<>();
        ArrayList<QnAAnswers> qnaAnswers = new ArrayList<>();
        QnAQuestions qnaQuestion = new QnAQuestions();
        try {

            JSONObject qns = new JSONObject(response);

            qnaQuestion.setUser(qns.getString("user"));
            qnaQuestion.setView_count(qns.getInt("view_count"));
            qnaQuestion.setTitle(qns.getString("title"));
            qnaQuestion.setDesc(qns.getString("desc"));
            qnaQuestion.setDownvotes(qns.getInt("downvotes"));
            qnaQuestion.setUpvotes(qns.getInt("upvotes"));
            qnaQuestion.setCurrent_user_vote_type(qns.getInt("current_user_vote_type"));
            qnaQuestion.setResource_uri(qns.getString("resource_uri"));
            qnaQuestion.setAdded_on(qns.getString("added_on"));
            qnaQuestion.setAnswers_count(qns.getInt("answers_count"));
            //qnaQuestion.setIndex(this.mQnAQuestions.size());

            JSONArray answerList = qns.getJSONArray("answer_set");

            for (int j = 0; j < answerList.length(); j++) {
                JSONObject ans = answerList.getJSONObject(j);

                QnAAnswers qnaAnswer = new QnAAnswers();

                qnaAnswer.setUser(ans.getString("user"));
                qnaAnswer.setDownvotes(ans.getInt("downvotes"));
                qnaAnswer.setUpvotes(ans.getInt("upvotes"));
                qnaAnswer.setCurrent_user_vote_type(ans.getInt("current_user_vote_type"));
                qnaAnswer.setAnswer_text(ans.getString("answer_text"));
                qnaAnswer.setAdded_on(ans.getString("added_on"));
                qnaAnswer.setId(ans.getLong("id"));
                qnaAnswer.setQuestion(ans.getString("question"));
                qnaAnswer.setResource_uri(ans.getString("resource_uri"));
                qnaAnswer.setIndex(j);
                //qnaAnswer.setQuestionIndex(this.mQnAQuestions.size());

                qnaAnswers.add(qnaAnswer);
            }

            JSONArray tagsList = qns.getJSONArray("tags");

            for (int k = 0; k < tagsList.length(); k++) {
                tagArrayList.add(tagsList.getString(k));
            }

            qnaQuestion.setAnswer_set(qnaAnswers);
            qnaQuestion.setTags(tagArrayList);

            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_RESOURCE_URI, qnaQuestion.getResource_uri());
            eventValue.put(getResourceString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_QNA_QUESTION_ASKED), eventValue, this);

            if (currentFragment instanceof InstituteDetailFragment) {
                (currentFragment).instituteQnAQuestionAdded(qnaQuestion);
            }else if (currentFragment instanceof  QnAQuestionsListFragment){
                (currentFragment).instituteQnAQuestionAdded(qnaQuestion);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void mInstituteQnAUpdated(String response) {
        if (currentFragment != null && currentFragment instanceof InstituteDetailFragment)
            ((InstituteDetailFragment) currentFragment).updateInstituteQnAQuestions(response);
    }

    public ArrayList<QnAQuestions> parseAndReturnQnAList(String qnaString) {
        return parseAndReturnQnAList(qnaString, false);
    }

    public ArrayList<QnAQuestions> parseAndReturnQnAList(String qnaString, boolean isNewList) {
        try {
            QnAQuestions qnaQuestion;
            if (isNewList) {
                mQnAQuestions.clear();
            }
            JSONObject qnaResult = new JSONObject(qnaString);
            next = qnaResult.getString("next");
            JSONArray resultArray = qnaResult.getJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {
                ArrayList<String> tagArrayList = new ArrayList<>();
                qnaQuestion = new QnAQuestions();
                ArrayList<QnAAnswers> qnaAnswers = new ArrayList<>();

                JSONObject qns = resultArray.getJSONObject(i);

                qnaQuestion.setUser(qns.getString("user"));
                qnaQuestion.setView_count(qns.getInt("view_count"));
                qnaQuestion.setTitle(qns.getString("title"));
                qnaQuestion.setDesc(qns.getString("desc"));
                qnaQuestion.setDownvotes(qns.getInt("downvotes"));
                qnaQuestion.setUpvotes(qns.getInt("upvotes"));
                qnaQuestion.setCurrent_user_vote_type(qns.getInt("current_user_vote_type"));
                qnaQuestion.setResource_uri(qns.getString("resource_uri"));
                qnaQuestion.setAdded_on(qns.getString("added_on"));
                qnaQuestion.setAnswers_count(qns.getInt("answers_count"));
                qnaQuestion.setIndex(i);

                JSONArray answerList = qns.getJSONArray("answer_set");

                for (int j = 0; j < answerList.length(); j++) {
                    JSONObject ans = answerList.getJSONObject(j);

                    QnAAnswers qnaAnswer = new QnAAnswers();

                    qnaAnswer.setUser(ans.getString("user"));
                    qnaAnswer.setDownvotes(ans.getInt("downvotes"));
                    qnaAnswer.setUpvotes(ans.getInt("upvotes"));
                    qnaAnswer.setCurrent_user_vote_type(ans.getInt("current_user_vote_type"));
                    qnaAnswer.setAnswer_text(ans.getString("answer_text"));
                    qnaAnswer.setAdded_on(ans.getString("added_on"));
                    qnaAnswer.setId(ans.getLong("id"));
                    qnaAnswer.setQuestion(ans.getString("question"));
                    qnaAnswer.setResource_uri(ans.getString("resource_uri"));
                    qnaAnswer.setIndex(j);
                    qnaAnswer.setQuestionIndex(i);

                    qnaAnswers.add(qnaAnswer);
                }

                JSONArray tagsList = qns.getJSONArray("tags");

                for (int k = 0; k < tagsList.length(); k++) {
                    tagArrayList.add(tagsList.getString(k));
                }

                qnaQuestion.setAnswer_set(qnaAnswers);
                qnaQuestion.setTags(tagArrayList);

                mQnAQuestions.add(qnaQuestion);
            }
        } catch (JSONException e) {
            Log.e(MainActivity.class.getSimpleName(), e.getMessage() + e.getCause());
        } finally {
            return mQnAQuestions;
        }
    }

    /**
     * This method is called when user change anything in Institute List
     * @param response server response
     */
    private void mUpdateInstituteList(String response) {
        try {
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, this.extractResults(response));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
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
        if (!isBackPressEnabled) {
            return;
        }
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (currentFragment != null) {
            if (currentFragment instanceof SyllabusSubjectsListFragment) {
                ((SyllabusSubjectsListFragment) currentFragment).submitSyllabusStatus();
            } else if (currentFragment instanceof OTPVerificationFragment && MainActivity.user.getIs_otp_verified() == 0) {
                if (MainActivity.user.getBlocking_otp() == 1) {
                    return;
                }
                setupOtpRequest(false);
            }else if (currentFragment instanceof WebViewFragment && ((WebViewFragment) currentFragment).canGoBack()){
                return;
            }
        }

        if (backStackCount == 0 && !Constants.READY_TO_CLOSE) {
            if (isFromNotification) {
                isFromNotification = false;
                mLoadUserStatusScreen();
                return;
            }
            Constants.READY_TO_CLOSE = true;
            Utils.DisplayToast(getApplicationContext(), "Press again to close CollegeDekho");
            baskpressHandler.postDelayed(backpressRunnable, 1500);
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
            Log.e(TAG, e.getMessage());
        }
    }


    /**
     * This method is called when user is not login and want to do something
     * like comment in myFb which required user login
     *
     * @param value MyFb comment message
     */
    @Override
    public void onUserLoginRequired(String value) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(getResourceString(R.string.TAG_FRAGMENT_SIGNIN));
        if (fragment == null)
            mDisplayFragment(LoginFragment1.newInstance(value), true, getResourceString(R.string.TAG_FRAGMENT_SIGNIN));
        else
            mDisplayFragment(fragment, false, getResourceString(R.string.TAG_FRAGMENT_SIGNIN));
    }

    /**
     * This method is used to sign Up
     * @param url social site url
     * @param hashMap request data
     * @param msg myFb comment
     */
    @Override
    public void onUserSignUp(String url, HashMap hashMap, String msg) {
        this.mMakeNetworkCall(Constants.TAG_USER_REGISTRATION + "#" + msg, url, hashMap);
    }


    /**
     * This method is called when user skip
     *  registration or facebook login
     */
    @Override
    public void onSkipUserLogin(HashMap<String, String> params) {
        onUserCommonLogin(params,Constants.TAG_SKIP_LOGIN);
    }

    /**
     * This method is used to login with facebook account
     * @param params request data
     */
    public void onUserCommonLogin(HashMap<String, String> params , String TAG) {
        this.mMakeNetworkCall(TAG, Constants.BASE_URL + "auth/common-login/", params);

        HashMap<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.TAG_USER_LOGIN, TAG);

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_USER_LOGIN), eventValue, this);
    }

    private void mUpdateUserPreferences(String jsonResponse)
    {
        User tempUser = MainActivity.user;
        try {
            MainActivity.user = JSON.std.beanFrom(User.class, jsonResponse);
            this.networkUtils.setToken(user.getToken());
            if (tempUser != null){
                MainActivity.user.setImage(tempUser.getImage());
                MainActivity.user.setPrimaryEmail(tempUser.getPrimaryEmail());
                MainActivity.user.setPrimaryPhone(tempUser.getPrimaryPhone());
                MainActivity.user.profileData = tempUser.profileData;
            }
            setUserIdWithAllEvents();
            String u = JSON.std.asString(this.user);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean(getResourceString(R.string.USER_CREATED), true).apply();
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.IS_PROFILE_LOADED = false;
        this.mLoadUserStatusScreen();
    }

    @Override
    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {

        HashMap params = new HashMap<>();

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
            params.put(MainActivity.getResourceString(R.string.USER_PHONE), phone);
        }

        if( trueProfile.email == null) {
             trueProfile.email = MainActivity.user.getEmail();

        }
        params.put(MainActivity.getResourceString(R.string.USER_NAME),trueProfile.firstName+" "+trueProfile.lastName);
        params.put(MainActivity.getResourceString(R.string.USER_EMAIL), trueProfile.email);
        params.put(MainActivity.getResourceString(R.string.USER_GENDER),trueProfile.gender);
        params.put(MainActivity.getResourceString(R.string.USER_CITY), trueProfile.city);
        params.put(MainActivity.getResourceString(R.string.USER_ZIP_CODE),trueProfile.zipcode);
        params.put(MainActivity.getResourceString(R.string.USER_IMAGE), trueProfile.avatarUrl);
        params.put(MainActivity.getResourceString(R.string.USER_COUNTRY_CODE), trueProfile.countryCode);
        params.put(MainActivity.getResourceString(R.string.USER_FACEBOOK_ID), trueProfile.facebookId);

        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        params.put(MainActivity.getResourceString(R.string.USER_DEVICE_ID), deviceId);
        params.put(MainActivity.getResourceString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_TRUECALLER);

        //TODO :: delete this after image_new handle at server side
        if(MainActivity.user != null && trueProfile.avatarUrl != null){
            MainActivity.user.setImage(trueProfile.avatarUrl);
        }
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
     * This method is used to log out facebook account
     * when user wants to login with different account
     * or  have not successfully login
     */
    private void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
    }

    /**
     * This method is used to sign in with any social site
     * @param url social site url
     * @param hashMap request data
     * @param msg myFb comment
     */
    @Override
    public void onUserSignIn(String url, HashMap hashMap, String msg) {
        this.mMakeNetworkCall(Constants.TAG_USER_LOGIN + "#" + msg, url, hashMap);
    }

    /***
     * This method is called when user sign Up successfully
     * @param response response json send  by server
     * @param msg MyFb comment message
     */
    public void onUserRegisteredResponse(String response, String msg) {
        User tempUser = this.user;
        try {
            this.user = JSON.std.beanFrom(User.class, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //save the preferences locally
        this.user.setPref(User.Prefs.STREAMKNOWN);
        if (tempUser != null) {
            MainActivity.user.setStream(tempUser.getStream());
            MainActivity.user.setLevel(tempUser.getLevel());
            MainActivity.user.setStream_name(tempUser.getStream_name());
            MainActivity.user.setLevel_name(tempUser.getLevel_name());
            MainActivity.user.setToken(tempUser.getToken());
            MainActivity.user.setImage(tempUser.getImage());
            MainActivity.user.setPrimaryEmail(tempUser.getPrimaryEmail());
            MainActivity.user.setPrimaryPhone(tempUser.getPrimaryPhone());
            MainActivity.user.profileData = tempUser.profileData;
        }
        this.networkUtils.setToken(user.getToken());
        String u = null;
        try {
            u = JSON.std.asString(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
        showMyFbMessage(msg);
    }

    /***
     * This method is called when user sign in successfully
     * @param response response json send  by server
     * @param msg MyFb comment message
     */
    public void onUserSignInResponse(String response, String msg) {

        User tempUser = user;
        try {
            user = JSON.std.beanFrom(User.class, response);
            this.networkUtils.setToken(user.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //save the preferences locally
        user.setPref(User.Prefs.STREAMKNOWN);
        user.setImage(tempUser.getImage());
        String u = null;
        try {
            u = JSON.std.asString(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
        showMyFbMessage(msg);

    }

    private void mOnUserExamsSelected(String response) {
        this.mMakeNetworkCall(Constants.TAG_LOAD_USER_PREFERENCES, Constants.BASE_URL + "preferences/", null);
        this.mClearBackStack();
        mLoadUserProfile(response);
    }

    private void onUserExamsEdited(String response) {
        isReloadProfile = true;
        try {
            DataBaseHelper.getInstance(this).deleteAllExamSummary();
            onUpdateUserExams(response);
            onBackPressed();
            if (currentFragment instanceof UserEducationFragment) {
                onBackPressed();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onUpdateUserPreferences(String responseJson) {

        try {
            JSONObject prefObject = new JSONObject(responseJson);
            User userObj = JSON.std.beanFrom(User.class, prefObject.optJSONArray("results").get(0).toString());
            MainActivity.user.setExams_set(userObj.getExams_set());
            MainActivity.user.setUser_exams(userObj.getUser_exams());
            MainActivity.user.setUser_education(userObj.getUser_education());
            MainActivity.user.setCollegedekho_recommended_streams(userObj.getCollegedekho_recommended_streams());
            MainActivity.user.setLevel(userObj.getLevel());
            MainActivity.user.setLevel_name(userObj.getLevel_name());
            MainActivity.user.setEducation_set(userObj.getEducation_set());
            MainActivity.user.setPsychometric_given(userObj.getPsychometric_given());
            MainActivity.user.setStream_name(userObj.getStream_name());
            MainActivity.user.setPhone_no(userObj.getPhone_no());
            MainActivity.user.setIs_preparing(userObj.getIs_preparing());
            MainActivity.user.setResource_uri(userObj.getResource_uri());
            MainActivity.user.setIs_otp_verified(userObj.getIs_otp_verified());
            MainActivity.user.setPartner_shortlist_count(userObj.getPartner_shortlist_count());
            MainActivity.user.setBlocking_otp(user.getBlocking_otp());
            MainActivity.user.setApp_version(userObj.getApp_version());
            MainActivity.user.setGender(userObj.getGender());
            MainActivity.user.setSocial_category(userObj.getSocial_category());
            MainActivity.user.setYear_of_admission(userObj.getYear_of_admission());
            MainActivity.user.setPreferred_mode(userObj.getPreferred_mode());

            this.mUserExamsList=MainActivity.user.getUser_exams();
            String u = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();

            String currentVersionName = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            if (currentVersionName!=null) {
                currentVersionName = currentVersionName.replace(".", "");
                int currentVersion = Integer.valueOf(currentVersionName);
                String releasedVersionName = userObj.getApp_version();
                releasedVersionName = releasedVersionName.replace(".", "");
                int releasedVersion = Integer.parseInt(releasedVersionName);
                if (releasedVersion > currentVersion) {
                    Utils.updateAppAlertDialog(this);
                }
            }

        }catch(IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    private void onUpdatePreferences(String responseJson) {
        try {
            User userObj = JSON.std.beanFrom(User.class, responseJson);
            MainActivity.user.setExams_set(userObj.getExams_set());
            MainActivity.user.setUser_exams(userObj.getUser_exams());
            MainActivity.user.setUser_education(userObj.getUser_education());
            MainActivity.user.setCollegedekho_recommended_streams(userObj.getCollegedekho_recommended_streams());
            MainActivity.user.setLevel(userObj.getLevel());
            MainActivity.user.setLevel_name(userObj.getLevel_name());
            MainActivity.user.setEducation_set(userObj.getEducation_set());
            MainActivity.user.setPsychometric_given(userObj.getPsychometric_given());
            MainActivity.user.setStream_name(userObj.getStream_name());
            MainActivity.user.setPhone_no(userObj.getPhone_no());
            MainActivity.user.setIs_preparing(userObj.getIs_preparing());
            MainActivity.user.setResource_uri(userObj.getResource_uri());
            MainActivity.user.setIs_otp_verified(userObj.getIs_otp_verified());
            MainActivity.user.setPartner_shortlist_count(userObj.getPartner_shortlist_count());
            MainActivity.user.setBlocking_otp(user.getBlocking_otp());
            MainActivity.user.setApp_version(userObj.getApp_version());
            MainActivity.user.setGender(userObj.getGender());
            MainActivity.user.setSocial_category(userObj.getSocial_category());
            MainActivity.user.setYear_of_admission(userObj.getYear_of_admission());
            MainActivity.user.setPreferred_mode(userObj.getPreferred_mode());
            this.mUserExamsList=MainActivity.user.getUser_exams();
            String u = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void onUpdateUserExams(String response) throws IOException {
        this.mUserExamsList = JSON.std.listOfFrom(ExamDetail.class, extractResults(response));
        MainActivity.user.setUser_exams(new ArrayList<>(mUserExamsList));
        MainActivity.user.setExams_set(1);
        String u = JSON.std.asString(MainActivity.user);

        this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
        Map<String, Object> eventValue = new HashMap<String, Object>();
        String[] examNames = new String[this.mUserExamsList.size()];

        for (int n = 0; n < this.mUserExamsList.size(); n++) {
            ExamDetail examDetail = this.mUserExamsList.get(n);

            eventValue = new HashMap<>();
            eventValue.put(getResourceString(R.string.USER_EXAM_SELECTED), examDetail.getExam_name() + "#" + examDetail.getExam_date());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_USER_EXAM_SELECTED), (Map) eventValue, this);
        }
    }

    private void mUserEducationStepCompleted(String responseJson) {

        try {
            User userObj = JSON.std.beanFrom(User.class, responseJson);
            this.user.setLevel(userObj.getLevel());
            this.user.setLevel_name(userObj.getLevel_name());
            this.user.setEducation_set(userObj.getEducation_set());
            this.user.setUser_exams(userObj.getUser_exams());
            this.user.setCollegedekho_recommended_streams(userObj.getCollegedekho_recommended_streams());
            this.user.setPsychometric_given(userObj.getPsychometric_given());
            this.user.setStream_name(userObj.getStream_name());
            this.user.setPhone_no(userObj.getPhone_no());
            this.user.setIs_preparing(userObj.getIs_preparing());
            this.user.setExams_set(userObj.getExams_set());
            String u = JSON.std.asString(this.user);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMyFbMessage(String msg) {
        if (msg != null && !msg.isEmpty()) {
            getSupportFragmentManager().popBackStack();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_MY_FB);
            if (fragment != null) {
                ((MyFutureBuddiesFragment) fragment).sendChatRequest(msg);
            }
        } else {
            this.mClearBackStack();
        }
    }

    public static void GATrackerEvent(String category, String action, String label) {
        if (MainActivity.tracker != null) {
            MainActivity.tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(label)
                    .build());
        }
    }

    public static void GATrackerEvent(String category, String action, String[] labels) {
        if (MainActivity.tracker != null) {
            HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
            eventBuilder.setCategory(category);
            eventBuilder.setAction(action);

            for (String label : labels)
                eventBuilder.setLabel(label);

            MainActivity.tracker.send(eventBuilder.build());
        }
    }

    /**
     *  If user login with any social site like facebook and stream and level has conflict
     *  it shows a dialog to choose stream and level.
     * @param tag Tag
     * @param URL Api Url according to login
     * @param jsonObj response json
     * @param params request data send to api
     */
    public void showDialogForStreamLevel(final String tag, final String URL, JSONObject jsonObj, final Map<String, String> params) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.stream_dailog);
        dialog.setTitle("Select Your Stream and Level");
        RadioGroup streamRadioGroup = (RadioGroup) dialog.findViewById(R.id.stream_radio_group);
        RadioGroup levelRadioGroup = (RadioGroup) dialog.findViewById(R.id.level_radio_group);
        try {
            final String stream_id = jsonObj.getString(getResourceString(R.string.USER_STREAM));
            final String level_id = jsonObj.getString(getResourceString(R.string.USER_LEVEL));
            String streamName = jsonObj.getString(getResourceString(R.string.USER_STREAM_NAME));
            String levelName = jsonObj.getString(getResourceString(R.string.USER_LEVEL_NAME));
            if (user.getStream().equalsIgnoreCase(stream_id))
                streamRadioGroup.setVisibility(View.GONE);

            ((RadioButton) dialog.findViewById(R.id.firstStream)).setText(streamName);
            ((RadioButton) dialog.findViewById(R.id.secondStream)).setText(user.getStream_name());

            ((RadioButton) dialog.findViewById(R.id.firstLevel)).setText(levelName);
            ((RadioButton) dialog.findViewById(R.id.secondLevel)).setText(user.getLevel_name());

            // if button is clicked, close the custom dialog
            dialog.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (((RadioButton) dialog.findViewById(R.id.firstStream)).isChecked())
                        params.put(getResourceString(R.string.USER_STREAM), stream_id);
                    if (((RadioButton) dialog.findViewById(R.id.secondStream)).isChecked())
                        params.put(getResourceString(R.string.USER_STREAM), user.getStream());
                    if (((RadioButton) dialog.findViewById(R.id.firstLevel)).isChecked())
                        params.put(getResourceString(R.string.USER_LEVEL), level_id);
                    if (((RadioButton) dialog.findViewById(R.id.secondLevel)).isChecked())
                        params.put(getResourceString(R.string.USER_LEVEL), user.getLevel());
                    mMakeNetworkCall(tag, URL, params);
                }


            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Folder> getFilterList() {
        if (this.mFilters != "") {
            updateFilterList(this.mFilters);
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

    public static void AppsflyerTrackerEvent(Context context, String eventName, Map<String, Object> eventValue)
    {
        AppsFlyerLib.getInstance().trackEvent(context, eventName, eventValue);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return getProfileLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (MainActivity.user == null)
            MainActivity.user = new User();
        user.processProfileData(cursor, this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * This method is used to send user's current education
     * details to server like current level, stream and marks
     * @param params
     */
    @Override
    public void onEducationSelected(HashMap<String, String> params) {
        this.mMakeNetworkCall(Constants.TAG_EDUCATION_DETAILS_SUBMIT, Constants.BASE_URL + "user-education/", params);
    }

    /**
     * This method is used to handle response having exams list
     * after user education detail is submitted to server.
     */
    private void mOnUserEducationResponse(String response) {
        User userObj = null;
        try {
            userObj = JSON.std.beanFrom(User.class, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userObj != null && user != null) {
            try {
                UserEducation education = JSON.std.beanFrom(UserEducation.class, response);
                this.user.setSublevel(userObj.getSublevel());
                this.user.setStream(userObj.getStream());
                this.user.setIs_preparing(userObj.getIs_preparing());
                this.user.setUser_education(education);
                String u = JSON.std.asString(this.user);
                this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
            } catch (Exception e) {

            }
        }
        this.mMakeNetworkCall(Constants.TAG_EXAMS_LIST, Constants.BASE_URL + "yearly-exams/", null);

        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.USER_CURRENT_SUBLEVEL), user.getSublevel());

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_CURRENT_STREAM_SELECTED), eventValue, this);

        eventValue.clear();
        eventValue.put(getResourceString(R.string.USER_IS_PREPARING), user.getIs_preparing());

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_USER_IS_PREPARING), eventValue, this);

    }

    private void onUserEducationEdited(String response) {
        try {
            UserEducation userEducation = JSON.std.beanFrom(UserEducation.class, response);
            MainActivity.user.setUser_education(userEducation);
            String u = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
            if (this.user.getIs_preparing().equals("0")) {
                onBackPressed();
            } else {
                onEditUserExams();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to handle response having exams list
     * after user education detail is submitted to server.
     *
     * @param responseJson
     */
    private void mOnExamsLoaded(String responseJson, boolean addToBackStack) {
        try {
            List<Exam> mExamList = JSON.std.listOfFrom(Exam.class, extractResults(responseJson));
            this.mDisplayFragment(ExamsFragment.newInstance(new ArrayList<>(mExamList)), addToBackStack, ExamsFragment.class.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mOnEditExamsLoaded(String responseJson, boolean addToBackStack) {
        try {
            List<Exam> mExamList = JSON.std.listOfFrom(Exam.class, extractResults(responseJson));
            this.mDisplayFragment(ExamsFragment.newEditableInstance(new ArrayList<>(mExamList)), addToBackStack, ExamsFragment.class.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to send exam list to server which are
     * selected by user
     *
     * @param params
     */
    @Override
    public void onExamsSelected(JSONObject params) {
        this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_EXAMS_LIST, Constants.BASE_URL + "yearly-exams/", params, 1);
    }

    @Override
    public void onExamsEdited(JSONObject params) {
        this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_EDITED_EXAMS_LIST, Constants.BASE_URL + "yearly-exams/", params, 1);
    }

    @Override
    public void onCancelExamSubmission() {
        this.mMakeNetworkCall(Constants.TAG_LOAD_USER_PREFERENCES_N_BACK, Constants.BASE_URL + "preferences/", null);
    }

    /**
     * This method is used to send user's current education
     * details to server like current level, stream and marks
     */
    @Override
    public void onUserNotPreparingSelected(HashMap<String, String> map) {
        this.mMakeNetworkCall(Constants.TAG_NOT_PREPARING_EDUCATION_DETAILS_SUBMIT, Constants.BASE_URL + "user-education/", map);
    }

    private void onNotPreparingEducationResponse(String response) {
        this.mDisplayFragment(NotPreparingFragment.newInstance(), true, NotPreparingFragment.class.toString());
        try {
            UserEducation education = JSON.std.beanFrom(UserEducation.class, response);
            MainActivity.user.setIs_preparing("0");
            MainActivity.user.setEducation_set(1);
            MainActivity.user.setSublevel(education.getSublevel());
            MainActivity.user.setUser_education(education);
            MainActivity.user.setStream(education.getStream());
            String u = JSON.std.asString(user);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();
        } catch (Exception e) {

        }
        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.USER_CURRENT_SUBLEVEL), user.getSublevel());

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_CURRENT_STREAM_SELECTED), eventValue, this);

        eventValue.clear();
        eventValue.put(getResourceString(R.string.USER_IS_PREPARING), user.getIs_preparing());

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_USER_IS_PREPARING), eventValue, this);
    }

    /**
     * This method is load user profile after
     */
    private void mLoadUserProfile(String responseJson) {

        if (responseJson != null && !responseJson.isEmpty()) {
            try {
                onUpdateUserExams(responseJson);
            } catch (IOException e) {

            }
        }

        //  show appBarLayout and toolBar
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.main_container).getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        findViewById(R.id.main_container).setLayoutParams(params);
        mUserExamsList = MainActivity.user.getUser_exams();
        if (mUserExamsList == null)
            mUserExamsList = new ArrayList<>();
        if (this.mUserExamsList.size() <= 0)
            prepBuddies.setVisibility(View.GONE);
        else
            prepBuddies.setVisibility(View.VISIBLE);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName());
        if (fragment == null)
            this.mDisplayFragment(ProfileFragment.newInstance(new ArrayList<>(mUserExamsList)), false, ProfileFragment.class.toString());
        else {
            this.mDisplayFragment(fragment, false, ProfileFragment.class.getSimpleName());
        }
        if(!IS_USER_CREATED){
            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(getResourceString(R.string.ACTION_USER_PROFILE_CREATED), ProfileFragment.class.getSimpleName());

            //Events
            AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_USER_PROFILE_CREATED), eventValue, this);
        }

        this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean(getResourceString(R.string.USER_PROFILE_LOADED), true).apply();

    }

    @Override
    public void onExamTabSelected(ExamDetail examDetailObj) {
        if (examDetailObj == null) return;
        String id = examDetailObj.getId();
        String dbSummary = DataBaseHelper.getInstance(this).getExamSummary(Integer.parseInt(id));
        if (dbSummary != null) {
            mUpdateExamDetail(dbSummary, false);
            return;
        }
        Map<String, String> params = this.mGetTheFilters();

        if (params == null)
            params = new HashMap<>();

        params.put("tag_uris[" + (params.size()) + "]", examDetailObj.getExam_tag());

        this.mMakeNetworkCall(Constants.TAG_EXAM_SUMMARY, Constants.BASE_URL + "yearly-exams/" + id + "/summary/", params);
    }

    public void onHomeItemSelected(String requestType, String url, String tag) {
        if (requestType.equalsIgnoreCase(Constants.WIDGET_INSTITUTES)
                || requestType.equalsIgnoreCase(Constants.WIDGET_RECOMMENDED_INSTITUTES)){
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
        }
        if (requestType.equals(Constants.WIDGET_SYLLABUS)) {
            this.mMakeNetworkCall(requestType, url, null);
            return;
        }
        Map<String, String> params = new HashMap<>();
        if (tag != null && !tag.isEmpty())
            params.put("tag_uris[" + (0) + "]", tag);

        this.mMakeNetworkCall(requestType, url, params);
    }

    @Override
    public void onPsychometricTest() {
        this.mMakeNetworkCall(Constants.TAG_PSYCHOMETRIC_QUESTIONS, Constants.BASE_URL + "psychometric/", null);

        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.CHOSEN_ACTION_WHEN_NOT_PREPARING), PsychometricTestQuestion.class.getSimpleName());

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_WHEN_NOT_PREPARING), eventValue, this);
    }

    @Override
    public void onSubmitEditedEducation(HashMap<String, String> params) {
        this.mMakeNetworkCall(Constants.TAG_EDIT_EDUCATION_DETAILS_SUBMIT, Constants.BASE_URL + "user-education/", params);
    }

    private void onPsychometricTestResponse(String response) {

        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String val = JSON.std.asString(map.get("questions"));
            this.psychometricQuestionsList = JSON.std.listOfFrom(PsychometricTestQuestion.class, val);
            this.mDisplayFragment(PsychometricTestParentFragment.newInstance(new ArrayList(this.psychometricQuestionsList)), true, PsychometricTestParentFragment.class.toString());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void onEditPsychometricTestResponse(String response) {

        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String val = JSON.std.asString(map.get("questions"));
            this.psychometricQuestionsList = JSON.std.listOfFrom(PsychometricTestQuestion.class, val);
            this.mDisplayFragment(PsychometricTestParentFragment.newEditableInstance(new ArrayList(this.psychometricQuestionsList)), true, PsychometricTestParentFragment.class.toString());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onStepByStep() {
        new AlertDialog.Builder(this)
                .setTitle("Currently Studying at?")
                .setSingleChoiceItems(StepByStepQuestion.CurrentLevels.getValues(), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.IN_SCHOOL);
                                MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP + "#" + StepByStepQuestion.CurrentLevels.IN_SCHOOL + "#" + "1", Constants.BASE_URL + "step-by-step/ug-ques-one/", null);
                                break;
                            case 1:
                                StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.GRADUATE_COLLEGE);
                                MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP + "#" + StepByStepQuestion.CurrentLevels.GRADUATE_COLLEGE + "#" + "1", Constants.BASE_URL + "step-by-step/pg-ques-one/", null);
                                break;
                            case 2:
                                StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.POSTGRADUATE_COLLEGE);
                                MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP + "#" + StepByStepQuestion.CurrentLevels.POSTGRADUATE_COLLEGE + "#" + "1", Constants.BASE_URL + "step-by-step/pg-ques-one/", null);
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.CHOSEN_ACTION_WHEN_NOT_PREPARING), StepByStepFragment.class.getSimpleName());

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_WHEN_NOT_PREPARING), eventValue, this);
    }

    @Override
    public void onIknowWhatIWant() {
        this.mMakeNetworkCall(Constants.TAG_LOAD_STREAM, Constants.BASE_URL + "streams/", null);

        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.CHOSEN_ACTION_WHEN_NOT_PREPARING), "I_KNOW_WHAT_I_WANT");

        //Events
        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_WHEN_NOT_PREPARING), eventValue, this);
    }

    @Override
    public void onSubmitPsychometricTest(JSONObject params,boolean fromEditProfile) {
        if(fromEditProfile){
            this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_EDIT_PSYCHOMETRIC_EXAM, Constants.BASE_URL + "star-psychometric/", params, 1);
        }else {
            this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM, Constants.BASE_URL + "star-psychometric/", params, 1);
        }
    }

    public void onSubjectSelected(Subjects subject, int position) {
        this.mDisplaySubjectSyllabus(subject);
    }

    @Override
    public void onSubjectCheckboxSelected(Subjects subject, int position) {

    }

    @Override
    public void onSyllabusChanged(JSONObject jsonObject) {

        String examId = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getString(Constants.SELECTED_EXAM_ID,  "");
        if(!examId.isEmpty()) {
            this.mMakeJsonObjectNetworkCall(Constants.SUBMITTED_CHAPTER_STATUS+"#"+examId, Constants.BASE_URL + "yearly-exams/" + examId + "/syllabus/", jsonObject, 1);
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
    public void onSBSTestFinish(String tag, String url, JSONObject answerObject) {
        this.mMakeJsonObjectNetworkCall(tag, url, answerObject, Request.Method.POST);
    }

    @Override
    public void onItemSelected(int position) {
        if(this.myAlertsList!=null && !this.myAlertsList.isEmpty()){
            this.mDisplayFragment(UserAlertsParentFragment.newInstance(position,new ArrayList(this.myAlertsList)),true,UserAlertsParentFragment.class.toString());
        }
    }

    @Override
    public void OnCDRecommendedLoadNext()
    {
        Map<String , String> params = this.mGetTheFilters();

        if(this.mExamTag != null && !this.mExamTag.isEmpty())
            params.put("tag_uris[" + (params.size()) + "]", this.mExamTag);

        this.mMakeNetworkCall(Constants.WIDGET_RECOMMENDED_INSTITUTES + "#" + "next", Constants.BASE_URL + "personalize/recommended-institutes/", params);
    }

    @Override
    public void OnCDRecommendedInstituteSelected(Institute institute) {
        this.mDisplayInstituteByEntity(institute);
        Log.e("CD-RE", "Selected:CD Reco Institute is : " + institute.getId());
    }

    @Override
    public void OnCDRecommendedInstituteLiked(Institute institute, boolean isLastCard, boolean isUndecided) {
        this.mInstitute = institute;
        Log.e("CD-RE", "Like:CD Reco Institute is : " + institute.getId());
        HashMap<String, String> params = new HashMap<>();
        params.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
        params.put("action", String.valueOf("1"));
//        if (institute.getPartner_status() > 0) {
            requestOtp();
//        }
        if (isUndecided)
            this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_SHORTLIST_INSTITUTE + "#" + isLastCard + "#" + isUndecided, institute.getResource_uri() + "shortlist/", params, Request.Method.POST);
        else
            this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_SHORTLIST_INSTITUTE + "#" + isLastCard, institute.getResource_uri() + "shortlist/", params, Request.Method.POST);

    }


    @Override
    public void OnCDRecommendedInstituteDislike(Institute institute, boolean isLastCard, boolean isUndecided) {
        this.mInstitute = institute;
        Log.e("CD-RE", "Dislike:CD Reco Institute is : " + institute.getId());
        HashMap<String, String> params = new HashMap<>();
        params.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
        params.put("action", String.valueOf("2"));
        if (isUndecided)
            this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE + "#" + isLastCard + "#" + isUndecided, institute.getResource_uri() + "shortlist/", params, Request.Method.POST);
        else
            this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE + "#" + isLastCard, institute.getResource_uri() + "shortlist/", params, Request.Method.POST);

    }

    @Override
    public void OnCDRecommendedInstituteDecideLater(Institute institute, boolean isLastCard, boolean isUndecided) {
        this.mInstitute = institute;
        Log.e("CD-RE", "Decide Later:CD Reco Institute is : " + institute.getId());
        HashMap<String, String> params = new HashMap<>();
        params.put("source", String.valueOf(Constants.REMOMMENDED_INSTITUTE_ACTION));
        params.put("action", String.valueOf("3"));

        if (isUndecided)
            this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE + "#" + isLastCard + "#" + isUndecided, institute.getResource_uri() + "shortlist/", params, Request.Method.POST);
        else
            this.mMakeNetworkCall(Constants.TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE + "#" + isLastCard, institute.getResource_uri() + "shortlist/", params, Request.Method.POST);
    }

    @Override
    public void OnCDRecommendedLoadUndecidedInstitutes(String url) {
        this.mMakeNetworkCall(Constants.TAG_LOAD_UNDECIDED_INSTITUTE, url, null, Request.Method.GET);
    }

    @Override
    public void OnAppliedInstitute(Institute institute) {
        if(institute.getGroups_exists()==1) {
            String cafUrl = "https://m.collegedekho.com/caf-login-signup/?institute_id=" + institute.getId();
            onDisplayWebFragment(cafUrl);
        }else {
            onWishListCourseApplied(institute);
        }
    }

    Institute institute;
    public void onWishListCourseApplied(final Institute institute) {
        this.institute=institute;
        final HashMap<String, String> params = new HashMap<>();
        if (user != null) {
            String name = user.getName();
            String email = user.getEmail();
            String phone = user.getPhone_no();

            if (name.equalsIgnoreCase(getResourceString(R.string.ANONYMOUS_USER)))
                name = "";

            if ((user.getName() == null || user.getName().isEmpty() || name.equalsIgnoreCase(getResourceString(R.string.ANONYMOUS_USER))) && user.profileData[0] != null)
                name = user.profileData[0];
            if (phone == null || phone.isEmpty())
                phone = user.getPrimaryPhone();
            if (user.getEmail().contains("@anonymouscollegedekho.com"))
                email = user.getPrimaryEmail();

            if (name == null || name.isEmpty() || name.toLowerCase().contains(getResourceString(R.string.ANONYMOUS_USER).toLowerCase()) ||
                    phone == null || phone.length() <= 6 ||
                    email == null || email.isEmpty() || email.contains("@anonymouscollegedekho.com")) {
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
                apply.show();
                (view.findViewById(R.id.submit_button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = ((TextView) view.findViewById(R.id.apply_name)).getText().toString();
                        String email = ((TextView) view.findViewById(R.id.apply_email)).getText().toString();
                        String phone = ((TextView) view.findViewById(R.id.apply_phone)).getText().toString();
                        if (name == null || name.isEmpty()) {
                            displayMessage(R.string.NAME_EMPTY);
                            return;
                        } else if (!Utils.isValidName(name)) {
                            displayMessage(R.string.NAME_INVALID);
                            return;
                        } else if (phone == null || phone.isEmpty()) {
                            displayMessage(R.string.PHONE_EMPTY);
                            return;
                        } else if (phone.length() <= 9 || phone.length() > 12 || !Utils.isValidPhone(phone)) {
                            displayMessage(R.string.PHONE_INVALID);
                            return;
                        } else if (email == null || email.isEmpty()) {
                            displayMessage(R.string.EMAIL_EMPTY);
                            return;
                        } else if (!Utils.isValidEmail(email)) {
                            displayMessage(R.string.EMAIL_INVALID);
                            return;
                        }
                        apply.dismiss();
                        params.put(getResourceString(R.string.USER_NAME), name);
                        params.put(getResourceString(R.string.USER_EMAIL), email);
                        params.put(getResourceString(R.string.USER_PHONE), phone);
                        params.put(getResourceString(R.string.APPLY_YEAR), mYear);
                        params.put("institute", "" + institute.getId());

                        String URL = Constants.BASE_URL + "lms/";
                        mMakeNetworkCall(Constants.TAG_WISH_LIST_APPLIED_COURSE , URL, params, Request.Method.POST);

                        Map<String, Object> eventValue = new HashMap<String, Object>();
                        eventValue.put(getResourceString(R.string.APPLY_INSTITUTE_FROM_RECO), institute.getResource_uri());
                        //Events
                        AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_COURSE_APPLIED), eventValue, MainActivity.this);

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
                params.put(getResourceString(R.string.USER_NAME), name);
                params.put(getResourceString(R.string.USER_EMAIL), email);
                params.put(getResourceString(R.string.USER_PHONE), phone);

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                params.put(getResourceString(R.string.APPLY_YEAR), "" + year);
                params.put("institute", "" + institute.getId());

                String URL = Constants.BASE_URL + "lms/";
                mMakeNetworkCall(Constants.TAG_WISH_LIST_APPLIED_COURSE , URL, params, Request.Method.POST);

                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(getResourceString(R.string.APPLY_INSTITUTE_FROM_RECO), institute.getResource_uri());
                //Events
                AnalyticsUtils.SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_COURSE_APPLIED), eventValue, MainActivity.this);

            }
        }
    }

    private List<VideoEntry> videoList;
    private InstituteVideosFragment videosFragment;

    @Override
    public void onUpdate(List<VideoEntry> videoList, String url, InstituteVideosFragment fragment) {
        videosFragment = fragment;
        if (videoList != null && !videoList.isEmpty()) {
            this.videoList = videoList;
            int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
            if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
                networkUtils.simpleGetData(Constants.TAG_UPDATE_VIDEO_TITLE, url);
            } else {
                displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            }

        }
    }

    public void onClickClose(@SuppressWarnings("unused") View view) {
        if (videosFragment != null) {
//            videosFragment.onClickClose(view);
        }
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

    @Override
    public void onSubmitMobileNumber(String mobileNumber) {
        HashMap<String, String> params = new HashMap<>();
        params.put(getResourceString(R.string.USER_PHONE), mobileNumber);
        this.mMakeNetworkCall(Constants.TAG_USER_PHONE_ADDED, Constants.BASE_URL + "send-otp/", params);
    }

    @Override
    public void onSubmitOTP(String mobileNumber, String otp) {
        HashMap<String, String> params = new HashMap<>();
        params.put(getResourceString(R.string.USER_PHONE), mobileNumber);
        params.put(Constants.OTP_CODE, otp);
        this.mMakeNetworkCall(Constants.TAG_VERIFY_USER_PHONE, Constants.BASE_URL + "verify-otp/", params);
    }

    @Override
    public void onResendOTP(String mobileNumber) {
        HashMap<String, String> params = new HashMap<>();
        params.put(getResourceString(R.string.USER_PHONE), mobileNumber);
        this.mMakeNetworkCall(Constants.TAG_RESEND_OTP, Constants.BASE_URL + "send-otp/", params);
    }

    private static class ContainerLoadedCallback implements ContainerHolder.ContainerAvailableListener {
        @Override
        public void onContainerAvailable(ContainerHolder containerHolder, String containerVersion) {
            // We load each container when it becomes available.
            Container container = containerHolder.getContainer();
            registerCallbacksForContainer(container);
        }

        public static void registerCallbacksForContainer(Container container) {
            // Register two custom function call macros to the container.
            container.registerFunctionCallMacroCallback("increment", new CustomMacroCallback());
            container.registerFunctionCallMacroCallback("mod", new CustomMacroCallback());
            // Register a custom function call tag to the container.
            container.registerFunctionCallTagCallback("custom_tag", new CustomTagCallback());
        }
    }

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
            Log.i("CollegeDekho", "Custom function call tag :" + tagName + " is fired.");
        }
    }

    private CursorLoader getProfileLoader() {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(
                        ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
                User.ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE + " = ? OR " + ContactsContract.Contacts.Data.MIMETYPE + " = ?",
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }



    @Override
    public void onNameUpdated(HashMap params, String msg) {
        this.mMakeNetworkCall(Constants.TAG_NAME_UPDATED + "#" + msg, MainActivity.user.getPreference_uri(), params, Request.Method.PUT);
    }

    private void onNameUpdatedResponse(String response, String msg) {
        try {
            User tempuser = JSON.std.beanFrom(User.class, response);

            //save the preferences locally
            user.setPref(User.Prefs.STREAMKNOWN);
            if (tempuser != null) {
//            user.setToken(tempuser.getToken());
//            user.setImage(tempuser.getImage());
//            user.setLevel_name(tempuser.getLevel_name());
//            user.setStream_name(tempuser.getStream_name());
//            user.setStream(tempuser.getStream());
//            user.setLevel(tempuser.getLevel());
                user.setPrimaryEmail(tempuser.getPrimaryEmail());
                user.setPrimaryPhone(tempuser.getPrimaryPhone());
                String userName = tempuser.getName();
                if (userName != null && !userName.trim().matches("") && !userName.equalsIgnoreCase(getResourceString(R.string.ANONYMOUS_USER))) {
                    user.setName(userName);
                    user.profileData[0] = userName;
                }
//            user.setSublevel(tempuser.getSublevel());
//            user.setIs_preparing(tempuser.getIs_preparing());
                user.setResource_uri(tempuser.getResource_uri());
//            user.profileData = tempuser.profileData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String u = null;
        try {
            u = JSON.std.asString(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();

        if (currentFragment instanceof MyFutureBuddiesFragment) {
            ((MyFutureBuddiesFragment) currentFragment).sendChatRequest(msg);
        }
    }

    private void onTestCalendarResponse(String response) {

        try {
//            String val = this.extractResults(response);
            this.chaptersList = JSON.std.listOfFrom(Chapters.class, response);

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(CalendarParentFragment.class.getSimpleName() );
            if(fragment == null)
                this.mDisplayFragment(CalendarParentFragment.newInstance(new ArrayList(this.chaptersList)), !isFromNotification, CalendarParentFragment.class.getSimpleName());
            else {
                if (currentFragment instanceof CalendarParentFragment)
                    ((CalendarParentFragment) currentFragment).updateCalander(new ArrayList(this.chaptersList));

                this.mDisplayFragment(fragment, false, CalendarParentFragment.class.getSimpleName());

            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    @Override
    public void onSubmitCalendarData(JSONObject object,String url) {
        String examId = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getString(Constants.SELECTED_EXAM_ID,  "");
        this.mMakeJsonObjectNetworkCall(Constants.TAG_PSYCHOMETRIC_RESPONSE+"#"+examId,Constants.BASE_URL+url,object,1);
    }

    Handler baskpressHandler=new Handler();
    Runnable backpressRunnable = new Runnable() {
        @Override
        public void run() {
            Constants.READY_TO_CLOSE = false;
        }
    };

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int postion = -1;
            try {
                postion = Integer.parseInt((String) view.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (postion == 3) {
                MainActivity.this.onHomeItemSelected(Constants.WIDGET_FORUMS, Constants.BASE_URL + "personalize/forums", null);
                return;
            }
            if (postion == 4) {
                MainActivity.this.mMakeNetworkCall(Constants.TAG_MY_ALERTS, Constants.BASE_URL + "exam-alerts/", null);
                return;
            }
            mUpdateTabMenuItem(postion);
            onTabMenuSelected(postion);
        }
    };

    public void mUpdateTabMenuItem(int tabPosition) {
        prepBuddies.setSelected(false);
        resourceBuddies.setSelected(false);
        futureBuddies.setSelected(false);
        myAlerts.setSelected(false);

        if (tabPosition == 1)
            prepBuddies.setSelected(true);
        if (tabPosition == 2)
            resourceBuddies.setSelected(true);

    }

    private void onTabMenuSelected(int tabPosition) {

        //TODO::  remove this when future buddies tab are present

        if (mUserExamsList == null) mUserExamsList = new ArrayList<>();

        if (this.mUserExamsList.size() <= 0)
            prepBuddies.setVisibility(View.GONE);
        else
            prepBuddies.setVisibility(View.VISIBLE);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TabFragment.class.getSimpleName());
        if (fragment == null)
            this.mDisplayFragment(TabFragment.newInstance(tabPosition, new ArrayList<>(mUserExamsList)), true, TabFragment.class.getSimpleName());
        else {
            if (currentFragment instanceof TabFragment)
                ((TabFragment) currentFragment).updateTabFragment(tabPosition);

            this.mDisplayFragment(fragment, false, TabFragment.class.getSimpleName());
        }
    }

    private void onMyAlertsLoaded(String response) {
        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String result = JSON.std.asString(map.get("results"));
            this.myAlertsList = JSON.std.listOfFrom(MyAlertDate.class, result);
            this.displayAlerts(new ArrayList(this.myAlertsList));
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


    @Override
    public void onProfileUpdated(HashMap<String, String> hashMap) {
        this.mMakeNetworkCall(Constants.TAG_UPDATE_PREFRENCES, MainActivity.user.getPreference_uri(), hashMap, Request.Method.PUT);

    }

    @Override
    public void onEditUserEducation() {
        this.mMakeNetworkCall(Constants.TAG_EDIT_USER_EDUCATION, Constants.BASE_URL + "user-education/", null);
    }

    private void mDisplayEditUserEducationFragment(String response) {
        try {
            JSONObject responseObject=new JSONObject(response);
            JSONArray levelsArray=responseObject.getJSONArray("levels");
            if(levelsArray!=null && levelsArray.length()>0) {
                ArrayList<UserEducation> userEducationList = (ArrayList<UserEducation>) JSON.std.listOfFrom(UserEducation.class, levelsArray.toString());
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(getResourceString(R.string.TAG_FRAGMENT_USER_EDUCATION));
//            if(fragment!=null && fragment instanceof UserEducationFragment){
//                ((UserEducationFragment)fragment).setForEditEducation();
//                this.mDisplayFragment(fragment, true, getResourceString(R.string.TAG_FRAGMENT_USER_EDUCATION));
//
//            }else {
                this.mDisplayFragment(UserEducationFragment.newEditableInstance(userEducationList), true, getResourceString(R.string.TAG_FRAGMENT_USER_EDUCATION));
//            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEditUserStreams() {
        Toast.makeText(this, "Edit Streams", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEditUserExams() {
        this.mMakeNetworkCall(Constants.TAG_EDIT_EXAMS_LIST, Constants.BASE_URL + "yearly-exams/", null);
    }

    @Override
    public void onTakePsychometric() {
        this.mMakeNetworkCall(Constants.TAG_EDIT_PSYCHOMETRIC_QUESTIONS, Constants.BASE_URL + "psychometric/", null);
    }

    private void onPNSNews(String response) {
        try {
            this.mNewsList = JSON.std.listOfFrom(News.class, "[" + response + "]");
            this.mParseSimilarNews(this.mNewsList);
            if (this.mNewsList != null && !this.mNewsList.isEmpty()) {
                this.mDisplayFragment(NewsDetailFragment.newInstance(this.mNewsList.get(0), new ArrayList<>(this.mNewsList)), false, Constants.PNS_NEWS);
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

    private void onPnsArticles(String response) {
        try {
            this.mArticlesList = JSON.std.listOfFrom(Articles.class, "[" + response + "]");
            this.mParseSimilarArticle(mArticlesList);
            if (this.mArticlesList != null && !this.mArticlesList.isEmpty()) {
                this.mDisplayFragment(ArticleDetailFragment.newInstance(mArticlesList.get(0), this.mArticlesList), false, Constants.TAG_FRAGMENT_ARTICLE_DETAIL);
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
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, "[" + response + "]");
            if (this.mInstituteList != null && !this.mInstituteList.isEmpty()) {
                this.mInstitute = this.mInstituteList.get(0);
                int id = this.mInstituteList.get(0).getId();
                this.mDisplayFragment(InstituteDetailFragment.newInstance(this.mInstitute), false, Constants.TAG_FRAGMENT_INSTITUTE);

                this.mMakeNetworkCall(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "institutecourses/" + "?institute=" + id, null);
                this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_NEWS, Constants.BASE_URL + "personalize/news/" + "?institute=" + String.valueOf(id), null);
                this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_ARTICLE, Constants.BASE_URL + "personalize/articles/" + "?institute=" + String.valueOf(id), null);

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
            this.mDisplayFragment(MyFutureBuddiesFragment.newInstance(this.mFB, 0), false, Constants.TAG_FRAGMENT_MY_FB);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            isFromNotification = false;
            mLoadUserStatusScreen();
        }
    }

    private void onPnsQnA(String response) {
        try {
            QnAQuestions qnaQuestion = new QnAQuestions();
            ArrayList<String> tagArrayList = new ArrayList<>();
            ArrayList<QnAAnswers> qnaAnswers = new ArrayList<>();

            JSONObject qns = new JSONObject(response);

            qnaQuestion.setUser(qns.getString("user"));
            qnaQuestion.setView_count(qns.getInt("view_count"));
            qnaQuestion.setTitle(qns.getString("title"));
            qnaQuestion.setDesc(qns.getString("desc"));
            qnaQuestion.setDownvotes(qns.getInt("downvotes"));
            qnaQuestion.setUpvotes(qns.getInt("upvotes"));
            qnaQuestion.setCurrent_user_vote_type(qns.getInt("current_user_vote_type"));
            qnaQuestion.setResource_uri(qns.getString("resource_uri"));
            qnaQuestion.setAdded_on(qns.getString("added_on"));
            qnaQuestion.setAnswers_count(qns.getInt("answers_count"));
            qnaQuestion.setIndex(0);

            JSONArray answerList = qns.getJSONArray("answer_set");

            for (int j = 0; j < answerList.length(); j++) {
                JSONObject ans = answerList.getJSONObject(j);

                QnAAnswers qnaAnswer = new QnAAnswers();

                qnaAnswer.setUser(ans.getString("user"));
                qnaAnswer.setDownvotes(ans.getInt("downvotes"));
                qnaAnswer.setUpvotes(ans.getInt("upvotes"));
                qnaAnswer.setCurrent_user_vote_type(ans.getInt("current_user_vote_type"));
                qnaAnswer.setAnswer_text(ans.getString("answer_text"));
                qnaAnswer.setAdded_on(ans.getString("added_on"));
                qnaAnswer.setId(ans.getLong("id"));
                qnaAnswer.setQuestion(ans.getString("question"));
                qnaAnswer.setResource_uri(ans.getString("resource_uri"));
                qnaAnswer.setIndex(j);
                qnaAnswer.setQuestionIndex(0);

                qnaAnswers.add(qnaAnswer);
            }

            JSONArray tagsList = qns.getJSONArray("tags");

            for (int k = 0; k < tagsList.length(); k++) {
                tagArrayList.add(tagsList.getString(k));
            }

            qnaQuestion.setAnswer_set(qnaAnswers);
            qnaQuestion.setTags(tagArrayList);
            this.mDisplayFragment(new QnAQuestionDetailFragment().newInstance(qnaQuestion), false, getResourceString(R.string.TAG_FRAGMENT_QNA_QUESTION_DETAIL));
        } catch (Exception e) {
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

            this.mMakeNetworkCall(Constants.SEARCH_INSTITUTES, Constants.BASE_URL + "colleges/search=" + searchString, null);
        } else if (currentFragment != null && currentFragment instanceof QnAQuestionsListFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_QNA, Constants.BASE_URL + "questions/search=" + searchString + "/", null);
        } else if (currentFragment != null && currentFragment instanceof NewsFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_NEWS, Constants.BASE_URL + "news/search=" + searchString + "/", null);
        } else if (currentFragment != null && currentFragment instanceof ArticleFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_ARTICLES, Constants.BASE_URL + "articles/search=" + searchString + "/", null);
        } else if (currentFragment != null && currentFragment instanceof ProfileFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_INSTITUTES, Constants.BASE_URL + "colleges/search=" + searchString + "/", null);
        }
    }

    private void closeSearch() {

        if (currentFragment != null && currentFragment instanceof InstituteListFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", null);
        } else if (currentFragment != null && currentFragment instanceof QnAQuestionsListFragment) {
            this.mMakeNetworkCall(Constants.SEARCH_QNA, Constants.BASE_URL + "personalize/qna/", null);

        } else if (currentFragment != null && currentFragment instanceof NewsFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_NEWS, Constants.BASE_URL + "personalize/news", null);
        } else if (currentFragment != null && currentFragment instanceof ArticleFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_ARTICLES, Constants.BASE_URL + "personalize/articles", null);
        } else {
//            this.mMakeNetworkCall(Constants.SEARCH_INSTITUTES, Constants.BASE_URL+"personalize/institutes/", null);
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
        searchProgress.setVisibility(View.VISIBLE);
        searchProgressRunnable = new Runnable() {
            @Override
            public void run() {
                if(searchProgress.getProgress()<100) {
                    searchProgress.setProgress(searchProgress.getProgress() + 10);
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
            searchProgress.setProgress(0);
            searchProgressHandler.removeCallbacks(searchProgressRunnable);
            searchProgress.setVisibility(View.GONE);
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
        try {
            mCurrentTitle = "Institutes";
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, extractResults(response));

            if (currentFragment instanceof InstituteListFragment) {
                ((InstituteListFragment) currentFragment).updateSearchList(this.mInstituteList, next);
            } else {
                mDisplayInstituteList(response, false, true, Constants.INSTITUTE_SEARCH_TYPE);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case Constants.RC_HANDLE_ALL_PERM:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Read Contacts permission granted -");
                    restartApplication();
                    return;
                }
                break;
            case Constants.RC_HANDLE_CONTACTS_PERM:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Read Contacts permission granted -");
                    restartApplication();
                    return;
                }
                break;

            case Constants.RC_HANDLE_STORAGE_PERM:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Read Contacts permission granted -");
                    restartApplication();
                    return;
                }
                break;
            case Constants.RC_HANDLE_SMS_PERM:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Read Contacts permission granted -");
                    restartApplication();
                    return;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }


        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CollegeDekho")
                .setMessage("Permission Not Granted, Application will quit now.")
                .setPositiveButton("Ok", listener)
                .show();
    }

    private void restartApplication() {
        loadInItData();
    }

    private void getUserPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECEIVE_SMS},
                    Constants.RC_HANDLE_ALL_PERM);
        } else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    Constants.RC_HANDLE_CONTACTS_PERM);
        } else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.RC_HANDLE_STORAGE_PERM);
        } else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    Constants.RC_HANDLE_SMS_PERM);
        }
    }

    private void displayOTPAlert(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final boolean[] gotUserResponse = new boolean[1];
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.otp_dialog_layout, null);
        dialogBuilder.setView(dialogView);

        TextView dialog_ok=(TextView)dialogView.findViewById(R.id.btn_dialog_positive);
        TextView dialog_cancel=(TextView)dialogView.findViewById(R.id.btn_dialog_negative);
        (dialogView.findViewById(R.id.plus_nine_one)).setVisibility(View.VISIBLE);
        final EditText edt_phone_number=(EditText)dialogView.findViewById(R.id.user_phone_number);

        edt_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edt_phone_number.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final AlertDialog alertDialog = dialogBuilder.create();
        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marks = edt_phone_number.getText().toString();
                if (marks != null && !marks.trim().equals("") && marks.trim().length() == 10) {
                    gotUserResponse[0] = true;
                    alertDialog.dismiss();
                    user_phone_number = marks;
                } else {
                    edt_phone_number.setError("Enter Valid Mobile Number");
                }
            }
        });
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotUserResponse[0] = false;
                alertDialog.dismiss();
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!gotUserResponse[0]) {
                    setupOtpRequest(false);
                } else {
                    MainActivity.this.onSubmitMobileNumber(edt_phone_number.getText().toString());
                }
            }
        });
        alertDialog.show();
    }

    private void displayGetOTPAlert(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final int[] gotUserResponse = new int[1];
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.otp_dialog_layout, null);
        dialogBuilder.setView(dialogView);
        ((TextView) dialogView.findViewById(R.id.dialog_title)).setText(getResourceString(R.string.otp_verification));
        ((TextView) dialogView.findViewById(R.id.dialog_message)).setText(getResourceString(R.string.enter_6_digit_OTP));
        final EditText edt_phone_number = (EditText) dialogView.findViewById(R.id.user_phone_number);
        final BroadcastReceiver otpReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String otp = intent.getStringExtra(Constants.USER_OTP);
                edt_phone_number.setText(otp);
            }
        };
        IntentFilter intentFilter = new IntentFilter(Constants.OTP_INTENT_FILTER);
        LocalBroadcastManager.getInstance(context).registerReceiver(otpReceiver, intentFilter);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(6);
        edt_phone_number.setFilters(FilterArray);

        final TextView dialog_ok = (TextView) dialogView.findViewById(R.id.btn_dialog_positive);
        TextView dialog_cancel = (TextView) dialogView.findViewById(R.id.btn_dialog_negative);
        TextView dialog_neural = (TextView) dialogView.findViewById(R.id.btn_dialog_nutral);

        (dialogView.findViewById(R.id.resend_otp_layout)).setVisibility(View.VISIBLE);
        dialog_ok.setText(getResourceString(R.string.verify));
        edt_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edt_phone_number.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final AlertDialog alertDialog = dialogBuilder.create();
        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marks = edt_phone_number.getText().toString();
                if (marks != null && !marks.trim().equals("") && marks.trim().length() == 6) {
                    gotUserResponse[0] = 1;
                    alertDialog.dismiss();
                } else {
                    edt_phone_number.setError(getResourceString(R.string.invalid_otp));
                }
            }
        });
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotUserResponse[0] = 0;
                alertDialog.dismiss();
            }
        });
        dialog_neural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotUserResponse[0] = 2;
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (gotUserResponse[0] == 0) {

                } else if (gotUserResponse[0] == 1) {
                    if (user_phone_number != null && !user_phone_number.matches("")) {
                        MainActivity.this.onSubmitOTP(user_phone_number, edt_phone_number.getText().toString());
                    }
                } else {
                    MainActivity.this.onSubmitMobileNumber(user_phone_number);

                }
                LocalBroadcastManager.getInstance(context).unregisterReceiver(otpReceiver);
            }
        });
        alertDialog.show();
    }

    private void onOTPVerified(String response) {
        try {
            JSONObject responseObject = new JSONObject(response);
            if (responseObject.optBoolean("verified")) {
                displayMessage(R.string.otp_verified);
                MainActivity.user.setIs_otp_verified(1);
                String u = JSON.std.asString(MainActivity.user);
                this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();

                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(getResourceString(R.string.ACTION_OTP_VERIFIED), "Success");

                //Events
                AnalyticsUtils.SendAppEvent(Constants.OTP_VERIFICATION, getResourceString(R.string.ACTION_OTP_VERIFIED), eventValue, this);

                onBackPressed();
            } else {
                if (currentFragment != null && currentFragment instanceof OTPVerificationFragment) {
                    Map<String, Object> eventValue = new HashMap<>();
                    eventValue.put(getResourceString(R.string.ACTION_OTP_VERIFIED), "Failed");

                    //Events
                    AnalyticsUtils.SendAppEvent(Constants.OTP_VERIFICATION, getResourceString(R.string.ACTION_OTP_VERIFIED), eventValue, this);

                    ((OTPVerificationFragment) currentFragment).onInvalidOtp();
                }
            }
        } catch (Exception e) {

        }
    }

    private void onMobileNumberSubmitted() {
        if (currentFragment instanceof OTPVerificationFragment) {
            ((OTPVerificationFragment) currentFragment).displayOTPLayout();
        }
    }

    private void requestOtp() {
        if (MainActivity.user.getIs_otp_verified() == 0 && setupOtpRequest(true)) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(OTPVerificationFragment.class.getSimpleName());
            if (fragment == null)
                mDisplayFragment(OTPVerificationFragment.newInstance(), true, OTPVerificationFragment.class.getSimpleName());
            else
                mDisplayFragment(fragment, false, OTPVerificationFragment.class.getSimpleName());
        }
    }

    private boolean setupOtpRequest(boolean canRequest) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String today = df.format(calendar.getTime());
        String oldDate = getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).getString(getResourceString(R.string.CAN_ASK_OTP_TODAY), null);
        if (oldDate != null && oldDate.equals(today)) {
            return false;
        }
        //TODO: Merge if with else
        else {
            if (!canRequest) {
                this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.CAN_ASK_OTP_TODAY), today).apply();
            }
        }
        return true;
    }

    public static String getResourceString(int resourceId) {
        try {
            return mContext.getString(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
                            mMakeNetworkCall(Constants.SEARCH_INSTITUTES, Constants.BASE_URL + "colleges/" + searchString, null);
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
                                searchView.onActionViewExpanded();
                                searchView.setQuery(searchString, false);
                                searchView.clearFocus();
                            }
                        }
                        else if(link.lastIndexOf("search=")!=-1){
                            link = link.substring(0, link.length() - 1);
                            String searchString = link.substring(link.lastIndexOf("/") + 1, link.length());
                            mMakeNetworkCall(Constants.SEARCH_INSTITUTES, Constants.BASE_URL + "colleges/" + searchString, null);
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
                                searchView.onActionViewExpanded();
                                searchView.setQuery(searchString, false);
                                searchView.clearFocus();
                            }
                        }
                        else if(link.lastIndexOf("/colleges/")!=-1){
                            String collegeId = link.substring(link.lastIndexOf("/")+1,link.length());
                            mMakeNetworkCall(Constants.TAG_INSTITUTE_DETAILS, Constants.BASE_URL + "institute-by-slug/"+collegeId+"/",null);
                        }else if(link.lastIndexOf("/colleges/")!=-1){

                        }else {

                        }
                    }
                    break;

                case Constants.NOTIFICATION_FILTER:
                    MainActivity.type=intent.getStringExtra("screen");
                    MainActivity.resource_uri=intent.getStringExtra("resource_uri");
                    if(MainActivity.resource_uri!=null && !MainActivity.resource_uri.trim().matches("") && MainActivity.type!=null && !MainActivity.type.trim().matches("")) {
                        mHandleNotifications(false);
                    }
                    break;
            }
        }
    };

    private void onInstituteDetailsLinkResponse(String response) {
        try {
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, "[" + response + "]");
            if (this.mInstituteList != null && !this.mInstituteList.isEmpty()) {
                this.mInstitute = this.mInstituteList.get(0);
                mDisplayInstituteByEntity(this.mInstitute);
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void onDisplayWebFragment(String url){
        if(currentFragment instanceof WebViewFragment){
            ((WebViewFragment) currentFragment).loadUrl(url);
        }else {
            mDisplayFragment(WebViewFragment.newInstance(url),!isFromNotification,Constants.ACTION_OPEN_WEB_URL);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle=intent.getExtras();
        if(bundle!=null) {
            if (bundle.containsKey("screen") && bundle.containsKey("resource_uri")) {
                MainActivity.type = bundle.getString("screen");
                MainActivity.resource_uri = bundle.getString("resource_uri");
                mHandleNotifications(false);
            }
        }
    }
}