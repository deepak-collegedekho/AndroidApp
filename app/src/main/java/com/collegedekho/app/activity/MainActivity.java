package com.collegedekho.app.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
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
import com.collegedekho.app.entities.Widget;
import com.collegedekho.app.fragment.ArticleDetailFragment;
import com.collegedekho.app.fragment.ArticleFragment;
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.fragment.CalendarParentFragment;
import com.collegedekho.app.fragment.ExamsFragment;
import com.collegedekho.app.fragment.FilterFragment;
import com.collegedekho.app.fragment.InstituteDetailFragment;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
import com.collegedekho.app.fragment.InstituteQnAFragment;
import com.collegedekho.app.fragment.InstituteShortlistFragment;
import com.collegedekho.app.fragment.LoginFragment;
import com.collegedekho.app.fragment.LoginFragment1;
import com.collegedekho.app.fragment.MyFutureBuddiesEnumerationFragment;
import com.collegedekho.app.fragment.MyFutureBuddiesFragment;
import com.collegedekho.app.fragment.NewsDetailFragment;
import com.collegedekho.app.fragment.NewsFragment;
import com.collegedekho.app.fragment.NotPreparingFragment;
import com.collegedekho.app.fragment.ProfileFragment;
import com.collegedekho.app.fragment.ProfileFragment1;
import com.collegedekho.app.fragment.PsychometricTestParentFragment;
import com.collegedekho.app.fragment.QnAQuestionsAndAnswersFragment;
import com.collegedekho.app.fragment.QnAQuestionsListFragment;
import com.collegedekho.app.fragment.SplashFragment;
import com.collegedekho.app.fragment.StreamFragment;
import com.collegedekho.app.fragment.SyllabusSubjectsListFragment;
import com.collegedekho.app.fragment.SyllabusUnitListFragment;
import com.collegedekho.app.fragment.TabFragment;
import com.collegedekho.app.fragment.UserAlertsFragment;
import com.collegedekho.app.fragment.UserAlertsParentFragment;
import com.collegedekho.app.fragment.UserEducationFragment;
import com.collegedekho.app.fragment.WidgetListFragment;
import com.collegedekho.app.fragment.pyschometricTest.PsychometricQuestionFragment;
import com.collegedekho.app.fragment.stepByStepTest.StepByStepFragment;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.listener.OnApplyClickedListener;
import com.collegedekho.app.listener.OnArticleSelectListener;
import com.collegedekho.app.listener.OnNewsSelectListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.ContainerHolderSingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.fasterxml.jackson.jr.ob.JSON;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        implements  LoaderManager.LoaderCallbacks<Cursor>,ExamsFragment.OnExamsSelectListener,
        ProfileFragment.OnTabSelectListener, TabFragment.OnHomeItemSelectListener,
        DataLoadListener,
        StreamFragment.OnStreamInteractionListener,AdapterView.OnItemSelectedListener,
        InstituteListFragment.OnInstituteSelectedListener,
        InstituteShortlistFragment.OnShortlistedInstituteSelectedListener,
        OnApplyClickedListener, WidgetListFragment.OnWidgetInteractionListener,
        OnNewsSelectListener, InstituteQnAFragment.OnQuestionAskedListener,
        FilterFragment.OnFilterInteractionListener, InstituteOverviewFragment.OnInstituteShortlistedListener,
        QnAQuestionsListFragment.OnQnAQuestionSelectedListener,
        QnAQuestionsAndAnswersFragment.OnQnAAnswerInteractionListener,
        MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener,
        MyFutureBuddiesFragment.OnMyFBInteractionListener,OnArticleSelectListener,
        ProfileFragment1.onProfileUpdateListener,
        LoginFragment1.OnSignUpListener, LoginFragment.OnUserSignUpListener,
        InstituteDetailFragment.OnInstituteFooterItemSelected, UserEducationFragment.OnUserEducationInteractionListener,
        PsychometricTestParentFragment.OnPsychometricTestSubmitListener,
        SyllabusSubjectsListFragment.OnSubjectSelectedListener,CalendarParentFragment.OnSubmitCalendarData,
        NotPreparingFragment.OnNotPreparingOptionsListener, StepByStepFragment.OnStepByStepFragmentListener,UserAlertsFragment.OnAlertItemSelectListener

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
    private static final String TRACKER_ID = "UA-67752258-1";

    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    public static NetworkUtils networkUtils;
    private ActionBarDrawerToggle mToggle;
    public BaseFragment currentFragment;
    private List<Institute> mInstituteList;
    private List<Chapters>chaptersList;
    private List<PsychometricTestQuestion>psychometricQuestionsList;
    private List<Institute> mShortlistedInstituteList;
    private int currentInstitute;
    private ProgressDialog progressDialog;
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
    public static CallbackManager callbackManager;
    private Date mTimeScreenClicked = new Date();

    private String mGTMContainerId = "www.collegedekho.com";
    private Connecto connecto = null;
    // Get SENDER_ID fom GCM.
    String SENDER_ID = "864760274938";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    //private NavigationDrawerFragment mNavigationDrawerFragment;
    /*
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    // private CharSequence mTitle;
    private List<ExamDetail> mUserExamsList = null;
    public static User user;
    private User.Prefs userPref;
    static String type = "";
    static String resource_uri = "";
    private  HashMap<String, String> mUserSignUPParams;
    private Menu menu;
    private String mYear;
    private View view;
    private TextView prepBuddies       ;
    private TextView resourceBuddies   ;
    private TextView futureBuddies     ;
    private TextView myAlerts          ;

    private boolean IS_PROFILE_LOADED;
    private boolean IS_USER_CREATED;
    private List<MyAlertDate> myAlertsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Send GA Session
        MainActivity.GASessionEvent(MainActivity.TAG);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            MainActivity.type = extras.getString("screen");
            MainActivity.resource_uri = extras.getString("resource_uri");
        }

        Uri targetUrl =
                AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i(TAG, "App Link Target URL: " + targetUrl.toString());
        }

        // Set the Currency
        AppsFlyerLib.setCurrencyCode("INR");

        // The Dev key cab be set here or in the manifest.xml
        AppsFlyerLib.setAppsFlyerKey("v3bLHGLaEavK2ePfvpj6aA");
        AppsFlyerLib.sendTracking(this);

        AppsFlyerLib.registerConversionListener(this,new AppsFlyerConversionListener() {
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

        this.setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0, null, this);

        this.connecto = Connecto.with(MainActivity.this);
        //this.connecto.identify("Harsh1234Vardhan", new Traits().putValue("name", "HarshVardhan"));
        //You can also track any event if you want
        //this.connecto.track("Session Started", new Properties().putValue("value", 800));
        this.connecto.registerWithGCM(MainActivity.this, this.SENDER_ID);

        Fabric.with(this, new Crashlytics());

        this.analytics = GoogleAnalytics.getInstance(this.getApplicationContext());
        this.analytics.setLocalDispatchPeriod(1800);

        MainActivity.tracker = this.analytics.newTracker(TRACKER_ID);

        // Provide unhandled exceptions reports. Do that first after creating the tracker
        MainActivity.tracker.enableExceptionReporting(true);
        // Enable Remarketing, Demographics & Interests reports
        // https://developers.google.com/analytics/devguides/collection/android/display-features
        MainActivity.tracker.enableAdvertisingIdCollection(true);
        // Enable automatic activity tracking for your app
        MainActivity.tracker.enableAutoActivityTracking(true);

        this.mRegisterFacebookSdk();
        this.mSetupGTM();

        this.mSetUpAPPToolBar();
        this.mDisplayFragment(SplashFragment.newInstance(), false, SplashFragment.class.getName());


        prepBuddies       = (TextView)findViewById(R.id.prep_buddies);
        resourceBuddies   = (TextView)findViewById(R.id.resources_buddies);
        futureBuddies     = (TextView)findViewById(R.id.future_buddies);
        myAlerts          = (TextView)findViewById(R.id.my_alerts);

        prepBuddies.setOnClickListener(mClickListener);
        resourceBuddies.setOnClickListener(mClickListener);
        futureBuddies.setOnClickListener(mClickListener);
        myAlerts.setOnClickListener(mClickListener);
        //init();
        //this.mDisplayFragment(MyAlertFragment.newInstance(null), false, MyAlertFragment.class.getName());
        // show appBarLayout and toolBar

        // TODO: Move this to where you establish a user session
        logUser();
    }

    private void mSetUpAPPToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigation_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFragment instanceof SyllabusSubjectsListFragment)
                    ((SyllabusSubjectsListFragment) currentFragment).submitSyllabusStatus();
                else if (currentFragment instanceof CalendarParentFragment)
                    ((CalendarParentFragment)currentFragment).submitCalendarData();
                mClearBackStack();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


    private void logUser() {
        // You can call any combination of these three methods
        if (MainActivity.user != null)
        {
            Crashlytics.setUserIdentifier(MainActivity.user.getToken());
            Crashlytics.setUserEmail(MainActivity.user.getEmail());
            Crashlytics.setUserName(MainActivity.user.getName());
        }
    }

    private void mhandleNotifications()
    {
        //Toast.makeText(MainActivity.this, type + " " + resource_uri, Toast.LENGTH_LONG).show();
        switch (MainActivity.type)
        {
            case Constants.TAG_FRAGMENT_INSTITUTE_LIST:
            {
                this.mCurrentTitle = "Institute List";

                this.mMakeNetworkCall(Constants.WIDGET_INSTITUTES, MainActivity.resource_uri, null);
                break;
            }
            case Constants.TAG_FRAGMENT_NEWS_LIST:
            {
                this.mCurrentTitle = "News";

                this.mMakeNetworkCall(Constants.WIDGET_NEWS, MainActivity.resource_uri, null);
                break;
            }
            case Constants.TAG_FRAGMENT_ARTICLES_LIST:
            {
                this.mCurrentTitle = "Articles";

                this.mMakeNetworkCall(Constants.WIDGET_ARTICES, MainActivity.resource_uri, null);
                break;
            }
            case Constants.TAG_FRAGMENT_SHORTLISTED_INSTITUTE:
            {
                this.mCurrentTitle = "My Shortlist";

                this.mMakeNetworkCall(Constants.WIDGET_SHORTLIST_INSTITUTES, MainActivity.resource_uri, null);
                break;
            }
            case Constants.TAG_FRAGMENT_QNA_QUESTION_LIST:
            {
                this.mCurrentTitle = "QnA";

                this.mMakeNetworkCall(Constants.TAG_LOAD_QNA_QUESTIONS, MainActivity.resource_uri, null);
                break;
            }
            case Constants.TAG_FRAGMENT_MY_FB_ENUMERATION:
            {
                this.mCurrentTitle = "My Future Buddies";

                this.mMakeNetworkCall(Constants.WIDGET_FORUMS, MainActivity.resource_uri, null);
                break;
            }
            default:
                break;
        }
    }

    /**
     * This method is used to register and initialize facebook sdk
     */
    private void mRegisterFacebookSdk()
    {
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();

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

    private void mSetupGTM()
    {
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
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
        AppsFlyerLib.onActivityResume(this);
        System.gc();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        AppsFlyerLib.onActivityResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
        AppsFlyerLib.onActivityPause(this);
        System.gc();
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppsFlyerLib.onActivityPause(this);
    }

    @Override
    protected void onDestroy() {
        this.connecto.track("Session Ended", new Properties().putValue("session_end_datetime", new Date().toString()));

        super.onDestroy();
    }

    public void init() {
        this.networkUtils = new NetworkUtils(this, this);
        SharedPreferences sp = this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
        try {
            if (sp.contains(Constants.KEY_USER)) {
                MainActivity.user = JSON.std.beanFrom(User.class, sp.getString(Constants.KEY_USER, null));
                this.networkUtils.setToken(MainActivity.user.getToken());

                this.connecto.identify(MainActivity.user.getId(), new Traits().putValue(Constants.USER_NAME, MainActivity.user.getName()).putValue(Constants.USER_STREAM_NAME, MainActivity.user.getStream_name()).putValue(Constants.USER_LEVEL_NAME, MainActivity.user.getLevel_name()));
                this.connecto.track("Session Started", new Properties().putValue("session_start_datetime", new Date().toString()));

                AppsFlyerLib.setCustomerUserId(MainActivity.user.getId());

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

            this.IS_USER_CREATED = sp.getBoolean(Constants.USER_CREATED, false);
            this.IS_PROFILE_LOADED = sp.getBoolean(Constants.USER_PROFILE_LOADED, false);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void loadInItData()
    {
        if (IS_USER_CREATED) {
            // if user is anonymous  then logout from facebook
            if(user.is_anony())
                disconnectFromFacebook();
            this.mLoadUserStatusScreen();

        }else{
            disconnectFromFacebook();
             MainActivity.this.mDisplayFragment(LoginFragment.newInstance(), false, Constants.TAG_FRAGMENT_LOGIN);
        }
    }

    /**
     * This method is called when first time anonymous user is created
     * @param json
     */
    private void mUserCreated(String json) {
        try {
            User tempUser = MainActivity.user;
            MainActivity.user = JSON.std.beanFrom(User.class, json);
            MainActivity.user.setPref(this.userPref);
            this.networkUtils.setToken(MainActivity.user.getToken());
            if (tempUser != null){
                MainActivity.user.setPrimaryEmail(tempUser.getPrimaryEmail());
            MainActivity.user.setPrimaryPhone(tempUser.getPrimaryPhone());
            MainActivity.user.profileData = tempUser.profileData;
           }
            if(MainActivity.user.getName().isEmpty() || user.getName().equalsIgnoreCase("Anonymous user")) {

                // get name from my profile me
                if (user.profileData[0] != null) {
                    //get device id
                    String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

                    HashMap<String, String> hashMap = new HashMap<>();
                    user.setName( user.profileData[0]);
                    hashMap.put(Constants.USER_NAME, user.profileData[0]);
                    hashMap.put(Constants.USER_DEVICE_ID, deviceId);

                    this.mMakeNetworkCall(Constants.TAG_NAME_UPDATED, Constants.BASE_URL + "preferences/", hashMap);
                }
            }

            this.connecto.identify(MainActivity.user.getId(), new Traits().putValue(Constants.USER_NAME, MainActivity.user.getName()));

            String u = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.USER_CREATED, true).commit();

            this.mLoadUserStatusScreen();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method is used to load screens according to user status
     * if education and exam is selected then load profile screen
     * if education is selected but exam is not selected then load exams screen
     * if both are not selected then load education screen
     *
     */

    private void mLoadUserStatusScreen() {

        if(MainActivity.user.getEducation_set() == 1 &&  MainActivity.user.getExams_set() == 1 || IS_PROFILE_LOADED)
            this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_EXAMS_LIST,Constants.BASE_URL + "user-exams/",null,0);
        else if(MainActivity.user.getEducation_set() == 1 &&  MainActivity.user.getExams_set() == 0)
            this.mMakeNetworkCall(Constants.TAG_LOAD_EXAMS_LIST, Constants.BASE_URL + "yearly-exams/",null);
        else
            this.mMakeNetworkCall(Constants.TAG_USER_EDUCATION,  Constants.BASE_URL + "user-education/", null);

    }

    /**
     * This method is used to split response json
     * with result , filter and next tags
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
            String results=JSON.std.asString(map.get("results"));
            List<Stream> streams = JSON.std.listOfFrom(Stream.class, results);
            this.mClearBackStack();
            this.mDisplayFragment(StreamFragment.newInstance(new ArrayList(streams), addToBackstack), addToBackstack, Constants.TAG_FRAGMENT_STREAMS);
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
     */
    private void updateNextShortListedInstitutes(String response) {
        try {
            List<Institute> institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            this.mShortlistedInstituteList.addAll(institutes);

            if (currentFragment instanceof InstituteShortlistFragment) {
                ((InstituteShortlistFragment) currentFragment).updateList(institutes, next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
    /**
     * This method is used to update news list of next page
     * @param response
     */
    private void updateNextNewsList(String response) {
        try {
            List<News> news = JSON.std.listOfFrom(News.class, extractResults(response));
            this.mNewsList.addAll(news);
            this.mParseSimilarNews(this.mNewsList);

            if (this.mNewsList != null &&   currentFragment instanceof NewsFragment) {
                ((NewsFragment) currentFragment).updateNewsList(new ArrayList<>(this.mNewsList), this.next);
            }
            if (this.mNewsList != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteNews(new ArrayList<>(this.mNewsList) , this.next);
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
                ((InstituteDetailFragment) currentFragment).updateInstituteArticle(new ArrayList<>(this.mArticlesList) , this.next);
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
    }
    /**
     * This method is used to update qna list of next page
     * @param response
     */
    private void updateNextForumsList(String response) {
        try {
            List<MyFutureBuddiesEnumeration> forumsList = JSON.std.listOfFrom(MyFutureBuddiesEnumeration.class, extractResults(response));
            this.mFbEnumeration.addAll(forumsList);

            if (currentFragment instanceof MyFutureBuddiesEnumerationFragment) {
                ((MyFutureBuddiesEnumerationFragment) currentFragment).updateList(forumsList, next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mDisplayInstituteList(String response, boolean filterAllowed, boolean isHavingNextUrl) {
        try {
            String val = this.extractResults(response);
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, val);

            if (this.mFilterKeywords.size() > 0)
                this.mFilterCount = this.mFilterKeywords.size();

            if(!isHavingNextUrl)
              next = null;

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);

            if (fragment == null)
                this.mDisplayFragment(InstituteListFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next, filterAllowed, this.mFilterCount), true, Constants.TAG_FRAGMENT_INSTITUTE_LIST);
            else {
                if (fragment instanceof InstituteListFragment) {
                    ((InstituteListFragment) fragment).clearList();
                    ((InstituteListFragment) fragment).updateList(this.mInstituteList, next);
                    ((InstituteListFragment) fragment).updateFilterButton(this.mFilterCount);
                }

                this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_INSTITUTE_LIST);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
/*

   private void mDisplayShortlistedInstituteList(String response) {
        try {
            this.mShortlistedInstituteList = JSON.std.listOfFrom(Institute.class, this.extractResults(response));
            Fragment fragment = this.getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_SHORTLISTED_INSTITUTE_LIST);

            if (fragment == null)
                this.mDisplayFragment(InstituteShortlistFragment.newInstance(new ArrayList<>(this.mShortlistedInstituteList), this.mCurrentTitle, next), true, Constants.TAG_FRAGMENT_SHORTLISTED_INSTITUTE_LIST);
            else
            {
                if (fragment instanceof InstituteShortlistFragment) {
                    ((InstituteShortlistFragment) fragment).clearList();
                    ((InstituteShortlistFragment) fragment).updateList(this.mShortlistedInstituteList, next);
                }

                this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_SHORTLISTED_INSTITUTE_LIST);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
*/

    private void mShowMyFBEnumeration(String response) {
        try {
            this.mFbEnumeration = JSON.std.listOfFrom(MyFutureBuddiesEnumeration.class, this.extractResults(response));
            this.mDisplayFragment(MyFutureBuddiesEnumerationFragment.newInstance(new ArrayList<>(this.mFbEnumeration), next), true, Constants.TAG_FRAGMENT_MY_FB_ENUMERATION);
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
                JSONObject comment = new JSONObject();
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

    private void mDisplaySubjectSyllabus(Subjects subject)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(SyllabusUnitListFragment.class.getSimpleName());
        if (fragment == null)
            this.mDisplayFragment(SyllabusUnitListFragment.newInstance(subject.getUnits()), true, SyllabusUnitListFragment.class.getSimpleName());
        else
            this.mDisplayFragment(fragment, false, SyllabusUnitListFragment.class.getSimpleName());
    }

    private void mDisplayInstitute(int position) {
        this.mInstitute = this.mInstituteList.get(position);
        int  id = this.mInstituteList.get(position).getId();

        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE);
        if (fragment == null)
            this.mDisplayFragment(InstituteDetailFragment.newInstance(this.mInstitute), true, Constants.TAG_FRAGMENT_INSTITUTE);
        else
            this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_INSTITUTE);

        this.mMakeNetworkCall(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "institutecourses/" + "?institute=" + id, null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_NEWS, Constants.BASE_URL + "personalize/news/" + "?institute=" + String.valueOf(id) , null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_ARTICLE, Constants.BASE_URL + "personalize/articles/" + "?institute=" + String.valueOf(id) , null);

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
        }
        else
        {
            if(currentFragment instanceof  LoginFragment || currentFragment instanceof LoginFragment1 || currentFragment instanceof ProfileFragment1)
                currentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void mDisplayFragment(Fragment fragment, boolean addToBackstack, String tag) {
        try {
            if (this.getCurrentFocus() != null && this.getCurrentFocus() instanceof EditText) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            this.currentFragment = (BaseFragment)fragment;

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.container, fragment, tag);

            if (addToBackstack)
                fragmentTransaction.addToBackStack(fragment.toString());

            fragmentTransaction.commit();

            if ( this.currentFragment instanceof  ProfileFragment) {
                if (findViewById(R.id.app_bar_layout).getVisibility() != View.VISIBLE)
                    findViewById(R.id.app_bar_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.bottom_tab_layout).setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
        }
        finally {
            //Send GA Session
            MainActivity.GAScreenEvent(tag);

            //Appsflyer events
            HashMap<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.SCREEN_NAME, tag);
            eventValue.put(Constants.LAST_SCREEN_NAME, this.mLastScreenName);
            eventValue.put(Constants.TIME_LAPSED_SINCE_LAST_SCREEN_NAME_IN_MS, new Date().getTime() - this.mTimeScreenClicked.getTime());

            this.mTimeScreenClicked = new Date();

            this.mLastScreenName = tag;

            MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_SCREEN_SELECTED, eventValue);
        }
    }

    @Override
    public void onDataLoaded(String tag, String response) {
        String extraTag = null;
        String childIndex = null;
        String parentIndex = null;
        String like = null;
        String[] tags = tag.split("#");
        int voteType = 0;

        switch (tags[0]) {
            case Constants.TAG_SKIP_LOGIN:
                this.mUserCreated(response);
                break;
            case Constants.TAG_CREATE_FACEBOOK_ANONY_USER_:
                this.mCreatedFacebookAnonymousUser(response);
                break;
            case Constants.TAG_USER_FACEBOOK_LOGIN:
                this.mUserFacebookLoginResponse(response);
                break;
            case Constants.TAG_USER_EDUCATION:
                this.mDisplayUserEducationFragment(response);
                break;
            case Constants.TAG_EDUCATION_DETAILS_SUBMIT:
                this.mOnUserEducationResponse(response);
                break;
            case Constants.TAG_USER_EDUCATION_SET:
                this.mUserEducationStepCompleted(response);
                break;
            case Constants.TAG_EXAMS_LIST:
                this.mOnExamsLoaded(response,true);
                break;
            case Constants.TAG_LOAD_EXAMS_LIST:
                this.mOnExamsLoaded(response,false);
                break;
            case Constants.TAG_SUBMIT_EXAMS_LIST:
                this.mOnUserExamsSelected(response);
                break;
            case Constants.TAG_USER_EXAMS_SET:
                this.mUserExamStepCompleted(response);
                break;
            case Constants.TAG_EXAM_SUMMARY:
                this.mUpdateExamDetail(response);
                break;
            case Constants.TAG_LOAD_STREAM:
                this.mDisplayStreams(response, true);
                break;
            case Constants.WIDGET_SHORTLIST_INSTITUTES:
                this.mCurrentTitle = "WishList Institutes";
                this.mDisplayInstituteList(response, false, true);
                break;
            case Constants.WIDGET_INSTITUTES:
                this.mCurrentTitle = "Institutes";
                this.mDisplayInstituteList(response, true, true);
                break;
            case Constants.WIDGET_RECOMMENDED_INSTITUTES:
                this.mCurrentTitle = "Recommended Institutes";
                this.mDisplayInstituteList(response, false, false );
                break;
            case Constants.SEARCHED_INSTITUTES:
                this.mCurrentTitle = "Institutes";
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
                this.mUpdateAppliedCourses(response, extraTag, tabPosition);
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
                this.updateNextShortListedInstitutes(response);
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
            case Constants.TAG_UNSHORTLIST_INSTITUTE:
                if (tags.length == 2) {
                    extraTag = tags[1];
                    if (currentFragment instanceof InstituteShortlistFragment)
                        ((InstituteShortlistFragment) currentFragment).updateShortlistButton(Integer.parseInt(extraTag));
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
                    MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, "Answer Voted : " + String.valueOf(voteType));
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
                this.mLoadUserProfile(null);
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
                this.mDisplayStreamsSelection(response, false);
                break;
            case Constants.TAG_PSYCHOMETRIC_TEXT_COMPLETED:
                if (tags.length > 2) {
                    parentIndex = tags[1];
                    childIndex = tags[2];
                    mOnPsychometricTestCompleted(parentIndex, childIndex, response);
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
        }

        if (this.progressDialog != null && this.progressDialog.isShowing())
            this.progressDialog.dismiss();
    }



    private void mOnPsychometricTestCompleted(String streamId, String streaName, String response) {

        try {
            User userObj = JSON.std.beanFrom(User.class,response);
            if(MainActivity.user != null){
                MainActivity.user.setStream_name(streaName);
                MainActivity.user.setStream(streamId);
                MainActivity.user.setLevel_name(userObj.getLevel_name());
                MainActivity.user.setLevel(userObj.getLevel());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
       this.mClearBackStack();
        this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_EXAMS_LIST,Constants.BASE_URL + "user-exams/",null,0);

    }



    private void mUpdateExamDetail(String responseJson) {
        try {
            ExamSummary examSummary = JSON.std.beanFrom(ExamSummary.class, responseJson);
                currentFragment.updateExamSummary(examSummary );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to provide education level
     * and user will select current education  and request for exams
     * relative to his/her education
     * @param response
     */
    private void mDisplayUserEducationFragment(String response){
        try {
            response = response.substring(10, response.length() - 1);
            ArrayList<UserEducation> userEducationList = (ArrayList<UserEducation>) JSON.std.listOfFrom(UserEducation.class, response);

            this.mDisplayFragment(UserEducationFragment.newInstance(userEducationList), false, Constants.TAG_FRAGMENT_USER_EDUCATION);
            //this.mDisplayFragment(MyAlertFragment.newInstance(userEducationList), false, MyAlertFragment.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to display step by step questions
     *
     * @param response
     */
    private void mDisplayStepByStepQuestion(String response, String currentLevel, String questionSetCount){
        try {
            //set current level
            StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.valueOf(currentLevel));

            response = response.substring(13, response.length() - 1);

            ArrayList<StepByStepQuestion> stepByStepQuestions = (ArrayList<StepByStepQuestion>) JSON.std.listOfFrom(StepByStepQuestion.class, response);

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(StepByStepFragment.class.getSimpleName());

            if (fragment == null)
            {
                StepByStepFragment stepByStepFragment = StepByStepFragment.newInstance(stepByStepQuestions);
                this.mDisplayFragment(stepByStepFragment, true, StepByStepFragment.class.getSimpleName());
            }
            else {
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
    private void mStepByStepDone(String response){
        try {
            StepByStepResult sbsResult = JSON.std.beanFrom(StepByStepResult.class, response);
            //sbsResult
            MainActivity.user.setStream_name(sbsResult.getStream_name());
            MainActivity.user.setStream(String.valueOf(sbsResult.getStream_id()));

            String u = null;
            u = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

            int count = 0;
            Map<String, String> filterKeywords = new HashMap<>();

            for (int n = 0; n < sbsResult.getTags().size(); n++)
                filterKeywords.put("tag_uris[" + (count++) + "]", sbsResult.getTags().get(n));

            this.mFilterKeywords = filterKeywords;
            this.mFilterCount = this.mFilterKeywords.size();

                //save preferences.
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).commit();

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
    private void mDisplayExamSyllabusFragment(String response){

        try {
            ArrayList<Subjects> subjectsList = (ArrayList<Subjects>) JSON.std.listOfFrom(Subjects.class, response);
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(SyllabusSubjectsListFragment.class.getSimpleName());
            if (fragment == null) {
                this.mDisplayFragment(SyllabusSubjectsListFragment.newInstance(subjectsList), true, SyllabusSubjectsListFragment.class.getSimpleName());
            }
            else {
                this.mDisplayFragment(fragment, false, SyllabusSubjectsListFragment.class.getSimpleName());
            }
        } catch (IOException e) {
            Log.v(TAG, e.getMessage());
            e.printStackTrace();
        }

    }

    private void mStreamAndLevelUpdated(String response)
    {
        String token = user.getToken();
        String image = user.getImage();

        try {
            user = JSON.std.beanFrom(User.class, response);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //save the preferences locally
        user.setPref(User.Prefs.STREAMKNOWN);
        user.setToken(token);
        user.setImage(image);

        try {
            String u = null;
            u = JSON.std.asString(user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //GA Event for Stream and Level update
        MainActivity.GATrackerEvent(Constants.CATEGORY_PREFERENCE, Constants.ACTION_STREAM_UPDATED, MainActivity.user.getStream_name());
        MainActivity.GATrackerEvent(Constants.CATEGORY_PREFERENCE, Constants.ACTION_LEVEL_UPDATED, MainActivity.user.getLevel_name());

        //Send event to connecto for stream and level update
        this.connecto.track(Constants.ACTION_STREAM_UPDATED, new Properties().putValue(Constants.USER_STREAM_NAME, MainActivity.user.getStream_name()));
        this.connecto.track(Constants.ACTION_LEVEL_UPDATED, new Properties().putValue(Constants.USER_LEVEL_NAME, MainActivity.user.getLevel_name()));
    }

    //Saved on DB, now save it in shared preferences.
    private void mStreamAndLevelSelected(String response, String level, String stream, String streamName) {
        //Retrieve token from pref to save it across the pref updates

        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.USER_CREATED, true).commit();
        User tempUser = MainActivity.user;

        try {
            MainActivity.user = JSON.std.beanFrom(User.class, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //save the preferences locally
        MainActivity.user.setPref(User.Prefs.STREAMKNOWN);
        if (tempUser != null){
        MainActivity.user.setToken(tempUser.getToken());
        MainActivity.user.setImage(tempUser.getImage());
        MainActivity.user.setPrimaryEmail(tempUser.getPrimaryEmail());
        MainActivity.user.setPrimaryPhone(tempUser.getPrimaryPhone());
        MainActivity.user.profileData = tempUser.profileData;
    }

        if (streamName != "" && streamName != null)
            MainActivity.user.setStream_name(streamName);

        try
        {
            String user = "";
            user = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, user).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //GA Event for Stream and Level update
        MainActivity.GATrackerEvent(Constants.CATEGORY_PREFERENCE, Constants.ACTION_STREAM_SELECTED, user.getStream_name());
        MainActivity.GATrackerEvent(Constants.CATEGORY_PREFERENCE, Constants.ACTION_LEVEL_SELECTED, user.getLevel_name());

        //Send event to connecto for stream and level selection
        this.connecto.track("Stream Selected", new Properties().putValue(Constants.USER_STREAM_NAME, user.getStream_name()));
        this.connecto.track("Level Selected", new Properties().putValue(Constants.USER_LEVEL_NAME, user.getLevel_name()));
        this.mClearBackStack();
    }

    private void updateQuestionLikeButton(String response, String extraTag, int like) {

        if(this.mQnAQuestions == null ) return;
        QnAQuestions qnaQuestion = this.mQnAQuestions.get(Integer.parseInt(extraTag));
        if (like == Constants.NEITHER_LIKE_NOR_DISLIKE)
        {
            qnaQuestion.setCurrent_user_vote_type(Constants.NEITHER_LIKE_NOR_DISLIKE);
            qnaQuestion.setUpvotes(qnaQuestion.getUpvotes() - 1);

            this.connecto.track(Constants.ACTION_QNA_LIKING_UNBIASED, new Properties().putValue(Constants.TAG_QUESTION_LIKE_DISLIKE, Constants.NEITHER_LIKE_NOR_DISLIKE).putValue(qnaQuestion.getTitle(), Constants.NEITHER_LIKE_NOR_DISLIKE).putValue(Constants.QNA_QUESTION_RESOURCE_URI, qnaQuestion.getResource_uri()));

            //GA Event for institute liked neutralized
            MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_QNA_LIKING_UNBIASED,
                            "qna title: " + qnaQuestion.getTitle() +
                            "Disliked institute: " + String.valueOf(like));

        } else {
            if (like != Constants.NEITHER_LIKE_NOR_DISLIKE) {

                qnaQuestion.setCurrent_user_vote_type(like);
                if (like == Constants.LIKE_THING) {
                    qnaQuestion.setUpvotes(qnaQuestion.getUpvotes() + 1);

                    this.connecto.track(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_QUESTION_UPVOTED, Constants.LIKE_THING).putValue(qnaQuestion.getTitle(), Constants.LIKE_THING).putValue(Constants.QNA_QUESTION_RESOURCE_URI, qnaQuestion.getResource_uri()));

                    //GA Event for institute liked neutralized
                    MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_QUESTION_ENTITY,
                            "qna title: " + qnaQuestion.getTitle() +
                                    "Disliked institute: " + String.valueOf(like));
                } else if (like == Constants.DISLIKE_THING) {
                    qnaQuestion.setUpvotes(qnaQuestion.getUpvotes() - 1);

                    this.connecto.track(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_QUESTION_DOWNVOTED, Constants.DISLIKE_THING).putValue(qnaQuestion.getTitle(), Constants.DISLIKE_THING).putValue(Constants.QNA_QUESTION_RESOURCE_URI, qnaQuestion.getResource_uri()));

                    //GA Event for institute liked neutralized
                    MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_QUESTION_ENTITY,
                            "qna title: " + qnaQuestion.getTitle() +
                                    "Disliked institute: " + String.valueOf(like));
                }
            }
        }
        if(currentFragment instanceof  QnAQuestionsListFragment)
            currentFragment.updateLikeButtons(Integer.parseInt(extraTag));
    }


    private void updateLikeButton(String response, String extraTag, int like) {
        Institute institute = null;
        if(currentFragment instanceof InstituteShortlistFragment)
            institute = this.mShortlistedInstituteList.get(Integer.parseInt(extraTag));
        else
            institute = this.mInstituteList.get(Integer.parseInt(extraTag));
        if (like == Constants.NEITHER_LIKE_NOR_DISLIKE)
        {
            institute.setCurrent_user_vote_type(Constants.NEITHER_LIKE_NOR_DISLIKE);
            institute.setCurrent_user_vote_url(null);
            institute.setUpvotes(institute.getUpvotes() - 1);

            this.connecto.track(Constants.ACTION_INSTITUTE_LIKING_UNBIASED, new Properties().putValue(Constants.TAG_INSTITUTE_LIKE_DISLIKE, Constants.NEITHER_LIKE_NOR_DISLIKE).putValue(institute.getShort_name(), Constants.NEITHER_LIKE_NOR_DISLIKE).putValue(Constants.INSTITUTE_RESOURCE_URI, institute.getResource_uri()));

            //GA Event for institute liked neutralized
            MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_INSTITUTE_LIKING_UNBIASED,
                    "Institute ID: " + String.valueOf(institute.getId()) +
                            "Institute Name: " + institute.getShort_name() +
                            "Disliked institute: " + String.valueOf(like));

            //Appsflyer events
            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
            eventValue.put(Constants.VOTE_TYPE, Constants.NEITHER_LIKE_NOR_DISLIKE);

            MainActivity.AppsflyerTrackerEvent(this, Constants.TAG_INSTITUTE_LIKE_DISLIKE, eventValue);
        } else {
            try {
                institute.setCurrent_user_vote_type(like);

                if (like == Constants.LIKE_THING)
                {
                    institute.setUpvotes(institute.getUpvotes() + 1);

                    this.connecto.track(Constants.ACTION_INSTITUTE_LIKED, new Properties().putValue(Constants.TAG_INSTITUTE_LIKE_DISLIKE, Constants.LIKE_THING).putValue(institute.getShort_name(), Constants.LIKE_THING).putValue(Constants.INSTITUTE_RESOURCE_URI, institute.getResource_uri()));

                    //GA Event for institute liked
                    MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_INSTITUTE_LIKED,
                            "Institute ID: " + String.valueOf(institute.getId()) +
                                    "Institute Name: " + institute.getShort_name() +
                                    "Disliked institute: " + String.valueOf(like));

                    //Appsflyer events
                    Map<String, Object> eventValue = new HashMap<String, Object>();
                    eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                    eventValue.put(Constants.VOTE_TYPE, Constants.LIKE_THING);

                    MainActivity.AppsflyerTrackerEvent(this, Constants.TAG_INSTITUTE_LIKE_DISLIKE, eventValue);
                }
                else if (like == Constants.DISLIKE_THING)
                {
                    this.connecto.track(Constants.ACTION_INSTITUTE_DISLIKED, new Properties().putValue(Constants.TAG_INSTITUTE_LIKE_DISLIKE, Constants.DISLIKE_THING).putValue(institute.getShort_name(), Constants.DISLIKE_THING).putValue(Constants.INSTITUTE_RESOURCE_URI, institute.getResource_uri()));

                    //GA Event for institute disliked
                    MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_INSTITUTE_DISLIKED,
                            "Institute ID: " + String.valueOf(institute.getId()) +
                                    "Institute Name: " + institute.getShort_name() +
                                    "Disliked institute: " + String.valueOf(like));

                    //Appsflyer events
                    Map<String, Object> eventValue = new HashMap<String, Object>();
                    eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                    eventValue.put(Constants.VOTE_TYPE, Constants.DISLIKE_THING);

                    MainActivity.AppsflyerTrackerEvent(this, Constants.TAG_INSTITUTE_LIKE_DISLIKE, eventValue);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (currentFragment instanceof InstituteListFragment || currentFragment instanceof  InstituteShortlistFragment) {
            currentFragment.updateLikeButtons(Integer.parseInt(extraTag));
        }
    }

    /*private void updateShortlistInstitute(String response, String extraTag)
    {
        Institute institute = this.mInstituteList.get(Integer.parseInt(extraTag));
        String message = null;

        if (response == null) {
            institute.setIs_shortlisted(Constants.SHORTLISTED_NO);
            message = " removed from your shortlist";
            //GA Event for institute shortlisting removed
            MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_INSTITUTE_SHORTLISTED_REMOVED, "Institute Shortlisting Removed : " + String.valueOf(institute.getResource_uri()) + " Institute Name: " + institute.getName() + " Institute City: " + institute.getCity_name());
            //CONNECTO Event for institute shortlisting removed
            this.connecto.track(Constants.ACTION_INSTITUTE_SHORTLISTED_REMOVED, new Properties().putValue(Constants.ACTION_INSTITUTE_SHORTLISTED, Constants.SHORTLISTED_NO).putValue(institute.getShort_name(), Constants.SHORTLISTED_NO).putValue(Constants.INSTITUTE_RESOURCE_URI, institute.getResource_uri()));
        } else {
            try {
                institute.setIs_shortlisted(Constants.SHORTLISTED_YES);
                message = " added to your shortlist";
                //GA Event for institute shortlisted
                MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_INSTITUTE_SHORTLISTED, "Institute Shortlisted : " + String.valueOf(institute.getResource_uri()) + " Institute Name: " + institute.getName() + " Institute City: " + institute.getCity_name());
                //CONNECTO Event for institute shortlisting removed
                this.connecto.track(Constants.ACTION_INSTITUTE_SHORTLISTED, new Properties().putValue(Constants.ACTION_INSTITUTE_SHORTLISTED, Constants.SHORTLISTED_YES).putValue(institute.getShort_name(), Constants.SHORTLISTED_YES).putValue(Constants.INSTITUTE_RESOURCE_URI, institute.getResource_uri()));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (Integer.parseInt(extraTag) == currentInstitute)
            if (currentFragment instanceof InstituteDetailFragment)
                ((InstituteDetailFragment) currentFragment).updateInstituteShortlist();

        Toast.makeText(this, institute.getShort_name() + message, Toast.LENGTH_SHORT).show();
    }*/

    private void updateUnShortlistInstitute(String response, String extraTag)
    {
        Institute institute = this.mShortlistedInstituteList.get(Integer.parseInt(extraTag));
        String message = null;
                 try {
                institute.setIs_shortlisted(Constants.SHORTLISTED_YES);
                message = " added to your shortlist";
                //GA Event for institute shortlisted
                MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_INSTITUTE_SHORTLISTED, "Institute Shortlisted : " + String.valueOf(institute.getResource_uri()) + " Institute Name: " + institute.getName() + " Institute City: " + institute.getCity_name());
                //CONNECTO Event for institute shortlisting removed
                this.connecto.track(Constants.ACTION_INSTITUTE_SHORTLISTED, new Properties().putValue(Constants.ACTION_INSTITUTE_SHORTLISTED, Constants.SHORTLISTED_YES).putValue(institute.getShort_name(), Constants.SHORTLISTED_YES).putValue(Constants.INSTITUTE_RESOURCE_URI, institute.getResource_uri()));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        if (currentFragment instanceof InstituteShortlistFragment)
            ((InstituteShortlistFragment) currentFragment).updateShortlistButton(Integer.parseInt(extraTag));
        //Toast.makeText(this, institute.getShort_name() + message, Toast.LENGTH_SHORT).show();
    }

    private void updateShortlistInstitute(String response, String extraTag)
    {
        Institute institute = this.mInstituteList.get(Integer.parseInt(extraTag));
        String message = null;

        if (response == null) {
            institute.setIs_shortlisted(Constants.SHORTLISTED_NO);
            message = " removed from your shortlist";
            //GA Event for institute shortlisting removed
            MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_INSTITUTE_SHORTLISTED_REMOVED, "Institute Shortlisting Removed : " + String.valueOf(institute.getResource_uri()) + " Institute Name: " + institute.getName() + " Institute City: " + institute.getCity_name());
            //CONNECTO Event for institute shortlisting removed
            this.connecto.track(Constants.ACTION_INSTITUTE_SHORTLISTED_REMOVED, new Properties().putValue(Constants.ACTION_INSTITUTE_SHORTLISTED, Constants.SHORTLISTED_NO).putValue(institute.getShort_name(), Constants.SHORTLISTED_NO).putValue(Constants.INSTITUTE_RESOURCE_URI, institute.getResource_uri()));

            //Appsflyer events
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
            eventValue.put(Constants.TAG_SHORTLIST_INSTITUTE, Constants.SHORTLISTED_NO);

            MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_INSTITUTE_SHORTLISTED, eventValue);

        } else {
            try {
                institute.setIs_shortlisted(Constants.SHORTLISTED_YES);
                message = " added to your shortlist";
                //GA Event for institute shortlisted
                MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_INSTITUTE_SHORTLISTED, "Institute Shortlisted : " + String.valueOf(institute.getResource_uri()) + " Institute Name: " + institute.getName() + " Institute City: " + institute.getCity_name());
                //CONNECTO Event for institute shortlisting removed
                this.connecto.track(Constants.ACTION_INSTITUTE_SHORTLISTED, new Properties().putValue(Constants.ACTION_INSTITUTE_SHORTLISTED, Constants.SHORTLISTED_YES).putValue(institute.getShort_name(), Constants.SHORTLISTED_YES).putValue(Constants.INSTITUTE_RESOURCE_URI, institute.getResource_uri()));
                //Appsflyer events
                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                eventValue.put(Constants.TAG_SHORTLIST_INSTITUTE, Constants.SHORTLISTED_YES);

                MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_INSTITUTE_SHORTLISTED, eventValue);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (currentFragment instanceof InstituteListFragment) {
            int position = Integer.parseInt(extraTag);
            boolean isFilterAllowed = ((InstituteListFragment) currentFragment).getFilterAllowed();
            if(!isFilterAllowed){
                if(mInstituteList != null && mInstituteList.size() >position )
                {
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
            this.mDisplayFragment(NewsFragment.newInstance(new ArrayList<>(this.mNewsList), this.mCurrentTitle, this.next), true, Constants.TAG_FRAGMENT_NEWS_LIST);
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
            if(tempId == null)continue;;
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
            this.mDisplayFragment(ArticleFragment.newInstance(new ArrayList<>(this.mArticlesList), this.mCurrentTitle, this.next), true, Constants.TAG_FRAGMENT_ARTICLES_LIST);
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
            case Constants.TAG_CREATE_ANONY_USER:
                return "Loading....";
            case Constants.TAG_USER_REGISTRATION :
                return "Creating User Please Wait";
            case Constants.TAG_USER_LOGIN:
                return "Signing User Please Wait.....";
            case Constants.TAG_LOAD_STREAM:
            case Constants.TAG_UPDATE_STREAM:
                return "Loading Streams...";
            case Constants.TAG_EDUCATION_DETAILS_SUBMIT:
                return "Loading Exams...";
            case Constants.TAG_EXAM_SUMMARY:
                return "Loading....";
            case Constants.TAG_SUBMIT_EXAMS_LIST:
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM:
                return "Loading Profile...";
            case Constants.TAG_UPDATE_INSTITUTES:
                return "Updating Institues...";
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
        }
        return null;
    }

    /**
     * This method is used to load Home screen of this application
     * @param response
     */
    private void mUpdateHome(String response) {
        try {
            // unlock navigation drawer when home screen come
            ((DrawerLayout)findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            //  show appBarLayout and toolBar
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.container).getLayoutParams();
            params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            findViewById(R.id.container).setLayoutParams(params);

            this.mWidgets = JSON.std.listOfFrom(Widget.class, extractResults(response));
            Fragment widgetListFragment = WidgetListFragment.newInstance(new ArrayList<>(this.mWidgets));
            if (MainActivity.type != null && MainActivity.type.length() > 0)
            {
                //add to backstack without displaying
                MainActivity.this.mhandleNotifications();
                MainActivity.type = "";
            }
            this.mDisplayFragment(widgetListFragment, false, Constants.TAG_FRAGMENT_WIDGET_LIST);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mShowQnAQuestions(String response) {
        this.mDisplayFragment(QnAQuestionsListFragment.newInstance((this.parseAndReturnQnAList(response)), next), true, Constants.TAG_FRAGMENT_QNA_QUESTION_LIST);
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

            this.mArticlesList  =  JSON.std.listOfFrom(Articles.class, extractResults(response));
            this.mParseSimilarArticle(this.mArticlesList );

            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteArticle((ArrayList)this.mArticlesList , next);
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

            this.mNewsList  =  JSON.std.listOfFrom(News.class, extractResults(response));
            this.mParseSimilarNews(this.mNewsList );

            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteNews((ArrayList)this.mNewsList , next);
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
        if (progressDialog != null)
            if (progressDialog.isShowing())
                progressDialog.dismiss();

        if (!MainActivity.this.isFinishing())
        {
            //show dialog
            new AlertDialog.Builder(this)
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

    private void mHandleErrorResponse(String tag)
    {
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
            case Constants.TAG_UNSHORTLIST_INSTITUTE: {
                if (tags.length == 2)
                    extraTag = tags[1];
                    if (currentFragment instanceof InstituteShortlistFragment)
                        (currentFragment).updateLikeButtons(Integer.parseInt(extraTag));
                break;
            }
            case Constants.TAG_INSTITUTE_LIKE_DISLIKE: {
                if (tags.length >= 2)
                    extraTag = tags[1];
                if (currentFragment instanceof InstituteListFragment || currentFragment instanceof  InstituteShortlistFragment) {
                    currentFragment.updateLikeButtons(Integer.parseInt(extraTag));
                }
                break;
            }
        }
    }

    @Override
    public void onJsonObjectRequestError(final String tag, final String response, final String url, final JSONObject params, final int method) {
        if (progressDialog != null)
            if (progressDialog.isShowing())
                progressDialog.dismiss();

        if (!MainActivity.this.isFinishing())
        {
            //show dialog
            new AlertDialog.Builder(this)
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
    public void unShortListInstituteFailed(String[] tags) {
        if(tags != null && tags.length >= 2) {
            if (currentFragment instanceof InstituteShortlistFragment)
                ((InstituteShortlistFragment) currentFragment).updateShortlistButton(Integer.parseInt(tags[1]));


        }

    }

    @Override
    public void onStreamSelected(final String stream, final String streamName) {
        new AlertDialog.Builder(this)
                .setTitle("Please select a level")
                .setSingleChoiceItems(InstituteCourse.CourseLevel.getValues(), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.mOnCourseLevelSelected(which, stream, streamName);
                        dialog.dismiss();
                    }
                })
//                .setCancelable(false)
                .show();
    }

    @Override
    public void onStreamClicked() {
        this.mMakeNetworkCall(Constants.TAG_UPDATE_STREAM, Constants.BASE_URL + "streams/", null);
    }

    @Override
    public void onStreamUpdated(final String uri, final String streamName) {
        getSupportFragmentManager().popBackStack();

        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_PROFILE);

        if (fragment == null)
            this.mDisplayFragment(ProfileFragment1.newInstance(user), true, Constants.TAG_FRAGMENT_PROFILE);
        else {

            this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_PROFILE);
            if (fragment instanceof ProfileFragment1) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ProfileFragment1) fragment).updateStream(uri, streamName);
                    }
                });
            }
        }
    }

    private void mOnCourseLevelSelected(int level, String streamURI, String streamName) {
        //get device id
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //send them to DB
        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("status", user.getPref().name().toLowerCase());
        hashMap.put("user", user.getResource_uri());
        hashMap.put(Constants.USER_STREAM, streamURI);
        hashMap.put(Constants.USER_LEVEL, Constants.BASE_URL + "level/" + (level + 1) + "/");
        hashMap.put(Constants.USER_STREAM_NAME, streamName);
        hashMap.put(Constants.USER_DEVICE_ID, deviceId);

        if (streamName != null || streamName != "")
            MainActivity.user.setStream_name(streamName);

        this.mMakeNetworkCall(Constants.TAG_SUBMIT_PREFRENCES + "#" + level + "#" + streamURI + "#" + streamName, Constants.BASE_URL + "preferences/", hashMap);
          }

    @Override
    public void onInstituteSelected(int position) {
        this.currentInstitute = position;
        this.mDisplayInstitute(position);
    }
    @Override
    public void onShortListInstituteSelected(int position) {
        this.currentInstitute = position;
        this.mDisplayInstitute(position);
    }
    @Override
    public void onInstituteLikedDisliked(int position, int liked) {
        Institute   institute = this.mInstituteList.get(position);
        if (institute.getCurrent_user_vote_type() == Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //neither liked nor disliked case
            if (liked == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_INSTITUTE_LIKE_DISLIKE + "#" + position + "#" + liked, institute.getResource_uri() + "upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_INSTITUTE_LIKE_DISLIKE + "#" + position + "#" + liked, institute.getResource_uri() + "downvote/", null, Request.Method.POST);
        } else if (institute.getCurrent_user_vote_type() != Constants.NEITHER_LIKE_NOR_DISLIKE && liked != Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //either already liked or disliked case
            if (liked == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_INSTITUTE_LIKE_DISLIKE + "#" + position + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE, institute.getResource_uri() + "upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_INSTITUTE_LIKE_DISLIKE + "#" + position + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE, institute.getResource_uri() + "downvote/", null, Request.Method.POST);
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
        if (next == null) return;
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
        }else  if (qnaQuestion.getCurrent_user_vote_type() != Constants.NEITHER_LIKE_NOR_DISLIKE  && voteType != Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //neither liked nor disliked case
            if (voteType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE , qnaQuestion.getResource_uri()+"upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE , qnaQuestion.getResource_uri() + "downvote/", null, Request.Method.POST);
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
        if(user != null) {
            String name = user.getName();
            String email = user.getEmail();
            String phone = user.getPhone_no();

            if(name.equalsIgnoreCase("Anonymous user"))
                name="";

            if ((user.getName() == null || user.getName().isEmpty() || name.equalsIgnoreCase("Anonymous user")) &&  user.profileData[0] != null)
                name = user.profileData[0];
            if(phone == null || phone.isEmpty())
                phone = user.getPrimaryPhone();
            if (user.getEmail().contains("@anonymouscollegedekho.com"))
                email = user.getPrimaryEmail();

            if (name== null || name.isEmpty() || name.contains("Anonymous user") ||
                    phone == null || phone.length() <= 6 ||
                    email == null || email.contains("@anonymouscollegedekho.com")) {
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
                            Utils.DisplayToast(getApplicationContext(), Constants.NAME_EMPTY);
                            return;
                        } else if (!Utils.isValidName(name)) {
                            Utils.DisplayToast(getApplicationContext(), Constants.NAME_INVALID);
                            return;
                        } else if (phone == null || phone.isEmpty()) {
                            Utils.DisplayToast(getApplicationContext(), Constants.PHONE_EMPTY);
                            return;
                        } else if (phone.length() <= 9 || phone.length() > 12 || !Utils.isValidPhone(phone) ) {
                            Utils.DisplayToast(getApplicationContext(), Constants.PHONE_INVALID);
                            return;
                        } else if (email == null || email.isEmpty()) {
                            Utils.DisplayToast(getApplicationContext(), Constants.EMAIL_EMPTY);
                            return;
                        } else if (!Utils.isValidEmail(email)) {
                            Utils.DisplayToast(getApplicationContext(), Constants.EMAIL_INVALID);
                            return;
                        }
                        apply.dismiss();
                        params.put(Constants.USER_NAME, name);
                        params.put(Constants.USER_EMAIL, email);
                        params.put(Constants.USER_PHONE, phone);
                        params.put(Constants.APPLY_YEAR, mYear);
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
            }
            else {
                params.put(Constants.USER_NAME, name);
                params.put(Constants.USER_EMAIL, email);
                params.put(Constants.USER_PHONE, phone);

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                params.put(Constants.APPLY_YEAR, "" + year);
                appplyCourse(instituteCourse, params, position, tabPosition);
            }
        }
    }
    private void appplyCourse(InstituteCourse instituteCourse, HashMap params , int position, int tabPosition)
    {
        params.put(Constants.APPLY_COURSE, "" + instituteCourse.getId());
        if (mInstitute != null) {
            params.put("institute", "" + mInstitute.getId());
        }

        String URL = Constants.BASE_URL + "lms/";
        instituteCourseId = "" + instituteCourse.getId();
        this.mMakeNetworkCall(Constants.TAG_APPLIED_COURSE + "#" + position + "#" + tabPosition, URL, params, Request.Method.POST);

        //GA Event for course applied
        MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_COURSE_APPLIED,
                "CourseID: " + String.valueOf(instituteCourse.getId()) +
                        "Course Name: " + instituteCourse.getName() +
                        "Institute ID: " + String.valueOf(mInstitute.getId()) +
                        "Institute Name: " + mInstitute.getName());

        this.connecto.track(Constants.CATEGORY_INSTITUTES, new Properties().putValue(Constants.APPLY_COURSE_ID, String.valueOf(instituteCourse.getId())).
                putValue(Constants.APPLY_COURSE, instituteCourse.getName()).
                putValue(Constants.APPLY_INSTITUTE, mInstitute.getResource_uri()));

        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(instituteCourse.getId()));
        eventValue.put(Constants.APPLY_COURSE, instituteCourse.getName());

        MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_COURSE_APPLIED, eventValue);
    }

    public ArrayList<String> getYears(){
        ArrayList<String> years = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=0; i<5; i++){
            years.add(""+(year+i));
        }
        mYear = years.get(0);
        return years;
    }

    @Override
    public void onWidgetSelected(Widget widget, int position) {
        this.mCurrentTitle = widget.getTitle();
        if (widget.getType().equals(Constants.WIDGET_INSTITUTES)) {
            this.mFilterKeywords = this.mGetTheFilters();

            this.mMakeNetworkCall(widget.getType(), widget.getAction_url(), this.mFilterKeywords);
            return;
        }

        switch (widget.getAction_method()) {
            case Constants.METHOD_GET:
                this.mMakeNetworkCall(widget.getType(), widget.getAction_url(), null);
                break;
            case Constants.METHOD_POST:
                this.mMakeNetworkCall(widget.getType(), widget.getAction_url(), null);
                break;
        }
    }

    /**
     * This method returns
     * @return
     */
    private Map mGetTheFilters() {
        Map<String, String> map = new HashMap<>();
        SharedPreferences sp = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
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
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
        } else {
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }
    public void hideProgressDialog() {
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onNewsSelected(News news , boolean addToBackstack) {

        if (!addToBackstack) {
            this. currentFragment.updateNews(news);
        }
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_NEWS_DETAIL);
            if (fragment == null)
                this.mDisplayFragment(NewsDetailFragment.newInstance(news, this.mNewsList), addToBackstack, Constants.TAG_FRAGMENT_NEWS_DETAIL);
            else {
                if (fragment instanceof NewsDetailFragment) {
                    ((NewsDetailFragment) fragment).updateNews(news);
                }
                this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_NEWS_DETAIL);
            }
            //Send GA and Connecto event for news selected
            MainActivity.GATrackerEvent(Constants.CATEGORY_NEWS, Constants.ACTION_NEWS_SELECTED, String.valueOf(Constants.BASE_URL + "/personalize/" + Constants.WIDGET_NEWS + "/" + news.getId()));
            this.connecto.track(Constants.ACTION_NEWS_SELECTED, new Properties().putValue(Constants.ACTION_NEWS_SELECTED, news.getId()));
            //Appsflyer events
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(news.getId()));

            MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_NEWS_SELECTED, eventValue);
        }
    }

    @Override
    public void onArticleSelected(Articles article, boolean addToBackstack) {
        if(article == null)return;
        if (!addToBackstack) {
            this. currentFragment.updateArticle(article);
        }
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_ARTICLE_DETAIL);
            if (fragment == null)
                this.mDisplayFragment(ArticleDetailFragment.newInstance(article, this.mArticlesList), addToBackstack, Constants.TAG_FRAGMENT_ARTICLE_DETAIL);
            else {
                if (fragment instanceof ArticleDetailFragment) {
                    ((ArticleDetailFragment) fragment).updateArticle(article);
                }
                this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_ARTICLE_DETAIL);
            }
            //Send GA and Connecto event for article selected
            MainActivity.GATrackerEvent(Constants.CATEGORY_ARTICLE, Constants.ACTION_ARTICLE_SELECTED, String.valueOf(Constants.BASE_URL + "/personalize/" + Constants.WIDGET_ARTICES + "/" + article.getId()));

            this.connecto.track(Constants.ACTION_ARTICLE_SELECTED, new Properties().putValue(Constants.ACTION_ARTICLE_SELECTED, article.getId()));

            //Appsflyer events
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(article.getId()));
            MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_ARTICLE_SELECTED, eventValue);
        }
    }

    @Override
    public void onQuestionAsked(QnAQuestions question) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", question.getTitle());
        map.put("desc", question.getDesc());
        if(!(currentFragment instanceof  QnAQuestionsListFragment))
            map.put("institute", "" + this.mInstituteList.get(currentInstitute).getResource_uri());
        //map.put("stream", Constants.BASE_URL + "streams/1/");
        /*map.put("user", user.getResource_uri());
        //:TODO remove hard coding of streams/1
        map.put("stream", Constants.BASE_URL + "streams/1/");*/

        this.mMakeNetworkCall(Constants.TAG_POST_QUESTION, Constants.BASE_URL + "personalize/qna/", map, Request.Method.POST);
    }

    @Override
    public void onFilterApplied() {

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
        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).commit();

        //send GA and connecto event for filter applied
        MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_FILTER_APPLIED, "");

        Map<String, Object> eventValue = new HashMap<String, Object>();
        for (String key : this.mFilterKeywords.keySet())
        {
            this.connecto.track(Constants.ACTION_FILTER_APPLIED, new Properties().putValue(Constants.SELECTED_FILTERS, this.mFilterKeywords.get(key)));
            eventValue.put(Constants.SELECTED_FILTERS, this.mFilterKeywords.get(key));
        }

        //Appsflyer events
        MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_FILTER_APPLIED, eventValue);
    }

    @Override
    public void onInstituteShortlisted(int position) {

        Institute institute = this.mInstituteList.get(position);
        if (institute.getIs_shortlisted() == Constants.SHORTLISTED_NO)
            this.mMakeNetworkCall(Constants.TAG_SHORTLIST_INSTITUTE + "#" + position, institute.getResource_uri() + "shortlist/", null, Request.Method.POST);
        else
            this.mMakeNetworkCall(Constants.TAG_DELETESHORTLIST_INSTITUTE + "#" + position, institute.getResource_uri() + "shortlist/", null, Request.Method.DELETE);
    }
    @Override
    public void onInstituteUnShortlisted(int position) {

        Institute institute = this.mShortlistedInstituteList.get(position);
        this.mMakeNetworkCall(Constants.TAG_UNSHORTLIST_INSTITUTE + "#" + position, institute.getResource_uri() + "shortlist/", null, Request.Method.DELETE);
    }

    @Override
    public void onFilterCanceled(boolean clearAll) {
        onBackPressed();

        if (clearAll) {
            for (Folder f : this.mFolderList) {
                for (Facet ft : f.getFacets())
                    ft.deselect();
            }

            this.mFilterCount = 0;

            this.mFilterKeywords = new HashMap<>();

            //reset the filters in preferences
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).commit();

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
    public void onInstituteShortlisted()
    {
        Institute institute = this.mInstituteList.get(this.currentInstitute);
        if (institute.getIs_shortlisted() == Constants.SHORTLISTED_NO)
            this.mMakeNetworkCall(Constants.TAG_SHORTLIST_INSTITUTE + "#" + this.currentInstitute, institute.getResource_uri() + "shortlist/", null, Request.Method.POST);
        else
            this.mMakeNetworkCall(Constants.TAG_DELETESHORTLIST_INSTITUTE + "#" + currentInstitute, institute.getResource_uri() + "shortlist/", null, Request.Method.DELETE);
    }

    private void mMakeNetworkCall(String tag, String url, Map<String, String> params, int method) {
        this.showProgress(tag);
        this.networkUtils.networkData(tag, url, params, method);
    }

    private void mMakeNetworkCall(String tag, String url, Map<String, String> params) {
        this.showProgress(tag);
        this.networkUtils.networkData(tag, url, params);
    }

    private void mMakeJsonObjectNetworkCall(String tag, String url, JSONObject params, int method) {
        this.showProgress(tag);
        this.networkUtils.networkDataWithObjectParam(tag, url, params, method);
    }

    private void showProgress(String tag) {
        String[] tags = tag.split("#");

        String message = MainActivity.GetPersonalizedMessage(tags[0]);
        if (message != null && !(this.currentFragment instanceof SplashFragment))
            showProgressDialog(message);
    }

    @Override
    public void onQnAQuestionSelected(QnAQuestions qnaQuestion) {
        this.mDisplayFragment(new QnAQuestionsAndAnswersFragment().newInstance(qnaQuestion), true, Constants.TAG_FRAGMENT_QNA_ANSWERS_LIST);
    }

    @Override
    public void onQnAQuestionVote(String resourceURI, int voteType, int position) {
        Utils.DisplayToast(this, "Thanks for your vote");
        if (voteType == Constants.LIKE_THING)
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "upvote/", null, Request.Method.POST);
        else
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "downvote/", null, Request.Method.POST);
    }

    @Override
    public void onQnAAnswerVote(String resourceURI, int voteType, int answerPosition, int questionPosition) {
        if (voteType == Constants.LIKE_THING)
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType), resourceURI + "upvote/", null, Request.Method.POST);
        else
            this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType), resourceURI + "downvote/", null, Request.Method.POST);
    }

    @Override
    public void onQnAAnswerSubmitted(String questionURI, String answerText, int questionIndex, int answerIndex) {
        HashMap<String, String> params = new HashMap<>();
        params.put("answer_text", answerText);
        this.mMakeNetworkCall(Constants.ACTION_QNA_ANSWER_SUBMITTED + "#" + String.valueOf(questionIndex) + "#" + String.valueOf(answerIndex), questionURI + "answer/", params, Request.Method.POST);
    }

    private void mQnAQuestionVoteUpdated(int questionIndex, int voteType) {
        try
        {
            if (currentFragment instanceof QnAQuestionsAndAnswersFragment)
                ((QnAQuestionsAndAnswersFragment) currentFragment).onVotingFeedback(questionIndex, -1, voteType);

            Map<String, Object> eventValue = new HashMap<String, Object>();

            QnAQuestions question = this.mQnAQuestions.get(questionIndex);

            if (voteType == Constants.LIKE_THING)
            {
                //GA and Connecto Event for question vote up
                MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_QUESTION_UPVOTED, "Question Voted : " + String.valueOf(voteType));
                this.connecto.track(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.LIKE_THING).putValue(question.getResource_uri(), Constants.LIKE_THING));

                //Appsflyer events
                eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(question.getResource_uri()));
                eventValue.put(Constants.VOTE_TYPE, Constants.LIKE_THING);
            }
            else if (voteType == Constants.DISLIKE_THING)
            {
                //GA and Connecto Event for question vote down
                MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_QUESTION_DOWNVOTED, "Question Voted : " + String.valueOf(voteType));
                this.connecto.track(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.DISLIKE_THING).putValue(question.getResource_uri(), Constants.DISLIKE_THING));

                //Appsflyer events
                eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(question.getResource_uri()));
                eventValue.put(Constants.VOTE_TYPE, Constants.DISLIKE_THING);
            }

            MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, eventValue);
        }
        catch (Exception e)
        {
            Log.e("QnA question voting", e.getMessage());
        }


    }

    private void mQnAAnswerVoteUpdated(int questionIndex, int answerIndex, int voteType) {
        ((QnAQuestionsAndAnswersFragment) currentFragment).onVotingFeedback(questionIndex, answerIndex, voteType);
        try
        {
            Map<String, Object> eventValue = new HashMap<String, Object>();
            QnAAnswers answer = this.mQnAQuestions.get(questionIndex).getAnswer_set().get(answerIndex);

            if (voteType == Constants.LIKE_THING)
            {
                //GA and Connecto Event for answer vote up
                MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_ANSWER_UPVOTED, "Answer Voted : " + String.valueOf(voteType));
                this.connecto.track(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.LIKE_THING).putValue(answer.getResource_uri(), Constants.LIKE_THING));
                eventValue.put(Constants.TAG_RESOURCE_URI, answer.getResource_uri());
                eventValue.put(Constants.VOTE_TYPE, Constants.LIKE_THING);
            }
            else if (voteType == Constants.DISLIKE_THING)
            {
                //GA and Connecto Event for answer vote down
                MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_ANSWER_DOWNVOTED, "Answer Voted : " + String.valueOf(voteType));
                this.connecto.track(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.DISLIKE_THING).putValue(answer.getResource_uri(), Constants.DISLIKE_THING));
                eventValue.put(Constants.TAG_RESOURCE_URI, answer.getResource_uri());
                eventValue.put(Constants.VOTE_TYPE, Constants.DISLIKE_THING);
            }

            MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, eventValue);

            //GA Event for answer vote
            MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, "Answer Voted : " + String.valueOf(voteType));
        }
        catch (Exception e)
        {
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

            if (currentFragment instanceof QnAQuestionsAndAnswersFragment)
                ((QnAQuestionsAndAnswersFragment) currentFragment).answerAdded(qnaAnswer);
            else if (currentFragment instanceof InstituteQnAFragment)
                ((InstituteQnAFragment) currentFragment).instituteQnAAnswerUpdated(qnaAnswer);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        finally {
            //GA and connecto Event for answer added
            MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_QNA_ANSWER_SUBMITTED,
                    "ID: " + String.valueOf(qnaAnswer.getResource_uri()));

            this.connecto.track(Constants.ACTION_QNA_ANSWER_SUBMITTED, new Properties().putValue(Constants.QNA_ANSWER_RESOURCE_URI, qnaAnswer.getResource_uri()));

            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, qnaAnswer.getResource_uri());

            MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_QNA_ANSWER_SUBMITTED, eventValue);
        }
    }

    @Override
    public void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position, int commentsCount) {
        this.mMakeNetworkCall(Constants.TAG_LOAD_MY_FB + "#" + String.valueOf(position) + "#" + String.valueOf(commentsCount), myFutureBuddiesEnumeration.getResource_uri(), null, Request.Method.GET);

        //GA Event for FB selection
        MainActivity.GATrackerEvent(Constants.CATEGORY_MY_FB, Constants.ACTION_MY_FB_SELECTED, "FB selected: " + myFutureBuddiesEnumeration.getInstitute_name());

        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(Constants.TAG_RESOURCE_URI, myFutureBuddiesEnumeration.getResource_uri());

        MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_MY_FB_SELECTED, eventValue);
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

            //GA Event for MY FB comment added
            MainActivity.GATrackerEvent(Constants.CATEGORY_MY_FB, Constants.ACTION_MY_FB_COMMENT_SUBMITTED, "Comment Added");

            this.connecto.track(Constants.ACTION_MY_FB_COMMENT_SUBMITTED, new Properties().putValue(Constants.MY_FB_URI, this.mFbEnumeration.get(fbIndex).getResource_uri()));

            //Appsflyer events
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, this.mFbEnumeration.get(fbIndex).getResource_uri());

            MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_MY_FB_COMMENT_SUBMITTED, eventValue);
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

            //this.mQnAQuestions.add(qnaQuestion);

            //GA and connecto Event for question asked
            MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_QNA_QUESTION_ASKED,
                    "Resource URI: " + String.valueOf(qnaQuestion.getResource_uri()));

            this.connecto.track(Constants.ACTION_QNA_QUESTION_ASKED, new Properties().putValue(Constants.QNA_QUESTION_RESOURCE_URI, qnaQuestion.getResource_uri()));

            //Appsflyer events
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put(Constants.TAG_RESOURCE_URI, qnaQuestion.getResource_uri());

            MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_QNA_QUESTION_ASKED, eventValue);

            if (currentFragment instanceof InstituteDetailFragment || currentFragment instanceof  QnAQuestionsListFragment)
                 currentFragment.instituteQnAQuestionAdded(qnaQuestion);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void mInstituteQnAUpdated(String response) {
        if (currentFragment != null && currentFragment instanceof InstituteDetailFragment)
            ((InstituteDetailFragment) currentFragment).updateInstituteQnAQuestions(response);
    }

    public ArrayList<QnAQuestions> parseAndReturnQnAList(String qnaString) {
        try {
            mQnAQuestions.clear();

            QnAQuestions qnaQuestion;

            JSONObject qnaResult = new JSONObject(qnaString);
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

   /* *//**
     * This mthod used to show user profile fragment UI
     *//*
    private void mDisplayPofile()
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_PROFILE);
        if (fragment == null)
            mDisplayFragment(ProfileFragment1.newInstance(user), true, Constants.TAG_FRAGMENT_PROFILE);
        else
            mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_PROFILE);

    }*/

    /**
     * This method is used to update User Profile
     * @param hashMap user request data
     */

    @Override
    public void onProfileUpdated(HashMap<String, String> hashMap) {

        //reset the filters in preferences and update institueLists
        //TODO:: SURESH: stream and level comes selected, check if deselecting it here makes it deselected forever.
        if (this.mFolderList != null && !this.mFolderList.isEmpty()) {
            for (Folder f : this.mFolderList) {
                for (Facet ft : f.getFacets())
                    ft.deselect();
            }

            this.mFilterCount = 0;
            this.mFilterKeywords = new HashMap<>();

            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).commit();
            this.mMakeNetworkCall(Constants.TAG_UPDATE_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", null);
        }
        this.mMakeNetworkCall(Constants.TAG_UPDATE_PREFRENCES, Constants.BASE_URL + "preferences/", hashMap);

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
        int backStackCount=getSupportFragmentManager().getBackStackEntryCount();
      if(backStackCount == 0 && !Constants.READY_TO_CLOSE) {
            Constants.READY_TO_CLOSE = true;
            Utils.DisplayToast(getApplicationContext(), "Press again to close CollegeDekho");
            baskpressHandler.postDelayed(backpressRunnable,1500);
        }
        else {
            if(currentFragment instanceof SyllabusSubjectsListFragment)
                ((SyllabusSubjectsListFragment) currentFragment).submitSyllabusStatus();
            super.onBackPressed();
        }
    }

    /**
     * This method is used to clear back stack of FragmentManager
     * It will remove all fragments present in stack. and now stack
     * will be empty
     */
    private  void mClearBackStack() {
        try{
            int count = getSupportFragmentManager().getBackStackEntryCount();
            for (int i = 0; i < count ; i++)
                getSupportFragmentManager().popBackStack();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }


    /**
     * This method is called when user is not login and want to do something
     * like comment in myFb which required user login
     * @param value MyFb comment message
     */
    @Override
    public void onUserLoginRequired(String value) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_SIGNIN);
        if (fragment == null)
            mDisplayFragment(LoginFragment1.newInstance(value), true, Constants.TAG_FRAGMENT_SIGNIN);
        else
            mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_SIGNIN);
    }
    /**
     * This method is used to sign Up
     * @param url social site url
     * @param hashMap request data
     * @param msg myFb comment
     */
    @Override
    public void onUserSignUp(String url, HashMap hashMap, String msg) {
        this.mMakeNetworkCall(Constants.TAG_USER_REGISTRATION + "#" + msg, url, hashMap);}


    /**
     * This method is called when user skip
     *  registration or facebook login
     */
    @Override
    public void onSkipUserLogin() {
        this.mMakeNetworkCall(Constants.TAG_SKIP_LOGIN, Constants.BASE_URL + "users/anonymous/", new HashMap<String, String>());
    }


    /**
     * This method is used to login with facebook account
     * @param params request data
     */
    @Override
    public void onFacebookLogin(HashMap<String, String> params) {
        this.userPref = User.Prefs.STREAMKNOWN;
        this.mMakeNetworkCall(Constants.TAG_CREATE_FACEBOOK_ANONY_USER_, Constants.BASE_URL + "users/anonymous/", params);
        this.mUserSignUPParams = params;
    }

    /**
     * This method is used to create anonymous user
     * while facebook login
     * @param json
     */
    private void  mCreatedFacebookAnonymousUser(String json)
    {
        User tempUser = this.user;
        try {
            this.user = JSON.std.beanFrom(User.class, json);
          //  this.user.setPref(this.userPref);
            this.networkUtils.setToken(this.user.getToken());
            if (tempUser != null){
                MainActivity.user.setImage(tempUser.getImage());
                MainActivity.user.setPrimaryEmail(tempUser.getPrimaryEmail());
                MainActivity.user.setPrimaryPhone(tempUser.getPrimaryPhone());
                MainActivity.user.profileData = tempUser.profileData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mMakeNetworkCall(Constants.TAG_USER_FACEBOOK_LOGIN, Constants.BASE_URL + "auth/facebook/", this.mUserSignUPParams);
    }

    /**
     * Method is called when user login with facebook successfully
     * @param json
     */
    private void mUserFacebookLoginResponse(String json)
    {
        User tempUser = MainActivity.user;
        try {
            MainActivity.user = JSON.std.beanFrom(User.class, json);
            this.networkUtils.setToken(user.getToken());
           if (tempUser != null){
                MainActivity.user.setImage(tempUser.getImage());
                MainActivity.user.setPrimaryEmail(tempUser.getPrimaryEmail());
                MainActivity.user.setPrimaryPhone(tempUser.getPrimaryPhone());
                MainActivity.user.profileData = tempUser.profileData;

            }
            MainActivity.user.setPref(this.userPref);
            String u = JSON.std.asString(this.user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.USER_CREATED, true).commit();
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mLoadUserStatusScreen();
    }

    /**
     * This method is used to log out facebook account
     *  when user wants to login with different account
     *  or  have not successfully login
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
        if (tempUser != null){
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
        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
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
        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
        showMyFbMessage(msg);

    }

    private void mOnUserExamsSelected(String response) {
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        Map<String, String> params = new HashMap<>();
        params.put(Constants.USER_EXAMS_SET,"1");
        params.put(Constants.USER_DEVICE_ID, deviceId);

        this.mMakeNetworkCall(Constants.TAG_USER_EXAMS_SET,Constants.BASE_URL + "preferences/", params);
        this.mClearBackStack();
        mLoadUserProfile(response);
    }

    private void mUserExamStepCompleted(String responseJson) {

        try {
            User userObj = JSON.std.beanFrom(User.class, responseJson);
            MainActivity.user.setExams_set(userObj.getExams_set());
            String u = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

        }catch(IOException e) {

            e.printStackTrace();
        }
    }


    private void mUserEducationStepCompleted(String responseJson){

        try {
            User userObj = JSON.std.beanFrom(User.class, responseJson);
            this.user.setLevel(userObj.getLevel());
            this.user.setLevel_name(userObj.getLevel_name());
            this.user.setEducation_set(userObj.getEducation_set());
            String u = JSON.std.asString(this.user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
        }catch(IOException e) {
            e.printStackTrace();
        }
     }

    private void showMyFbMessage(String msg)
    {
        if(msg != null && !msg.isEmpty())
        {
            getSupportFragmentManager().popBackStack();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_MY_FB);
            if(fragment != null)
            {
                ((MyFutureBuddiesFragment)fragment).sendChatRequest(msg);
            }
        }
        else {
            this.mClearBackStack();
           /* Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_PROFILE);
            if(fragment != null)
            {
                ((ProfileFragment1)fragment).updateUI(MainActivity.user);
            }*/
        }
    }

    public static void GATrackerEvent(String category, String action, String label)
    {
        if(MainActivity.tracker!=null)
        {
            MainActivity.tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(label)
                    .build());
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

    public void showDialogForStreamLevel(final String tag, final String URL, JSONObject jsonObj, final Map<String, String> params)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.stream_dailog);
        dialog.setTitle("Select Your Stream and Level");
        RadioGroup streamRadioGroup = (RadioGroup)dialog. findViewById(R.id.stream_radio_group);
        RadioGroup levelRadioGroup  = (RadioGroup)dialog. findViewById(R.id.level_radio_group);
        try {
            final String stream_id = jsonObj.getString(Constants.USER_STREAM);
            final String level_id  = jsonObj.getString(Constants.USER_LEVEL);
            String streamName = jsonObj.getString(Constants.USER_STREAM_NAME);
            String levelName = jsonObj.getString(Constants.USER_LEVEL_NAME);
            if(user.getStream().equalsIgnoreCase(stream_id))
                streamRadioGroup.setVisibility(View.GONE);
            else if(user.getLevel().equalsIgnoreCase(level_id)) {
                levelRadioGroup.setVisibility(View.GONE);
                dialog.findViewById(R.id.select_level_text).setVisibility(View.GONE);
            }

            ((RadioButton)dialog.findViewById(R.id.firstStream)).setText(streamName);
            ((RadioButton)dialog.findViewById(R.id.secondStream)).setText(user.getStream_name());

            ((RadioButton)dialog.findViewById(R.id.firstLevel)).setText(levelName);
            ((RadioButton)dialog.findViewById(R.id.secondLevel)).setText(user.getLevel_name());

            // if button is clicked, close the custom dialog
            dialog.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (((RadioButton)dialog.findViewById(R.id.firstStream)).isChecked())
                        params.put(Constants.USER_STREAM, stream_id);
                    if (((RadioButton)dialog.findViewById(R.id.secondStream)).isChecked())
                        params.put(Constants.USER_STREAM, user.getStream());
                    if (((RadioButton)dialog.findViewById(R.id.firstLevel)).isChecked())
                        params.put(Constants.USER_LEVEL, level_id);
                    if (((RadioButton)dialog.findViewById(R.id.secondLevel)).isChecked())
                        params.put(Constants.USER_LEVEL,  user.getLevel());
                    mMakeNetworkCall(tag, URL, params);
                }


            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<Folder> getFilterList()
    {
        if (this.mFilters != "") {
            updateFilterList(this.mFilters);
        }
        return this.mFolderList;
    }

    public static void GASessionEvent(String screenName)
    {
        if (MainActivity.tracker!=null)
        {
            MainActivity.tracker.setScreenName(screenName);
            // Start a new session with the hit.
            MainActivity.tracker.send(new HitBuilders.ScreenViewBuilder()
                    .setNewSession()
                    .build());
        }
    }

    public static void GAScreenEvent(String screenName)
    {
        if(MainActivity.tracker!=null)
        {
            MainActivity.tracker.setScreenName(screenName);
            // Start a new session with the hit.
            MainActivity.tracker.send(new HitBuilders.ScreenViewBuilder()
                    .build());
        }
    }

    public static void AppsflyerTrackerEvent(Context context, String eventName, Map<String, Object> eventValue)
    {
        AppsFlyerLib.trackEvent(context, eventName, eventValue);
    }

    @Override
    public void onFooterVideosSelected(ArrayList<String> videos) {

        if (videos == null || videos.size() < 1)
        {
            Toast.makeText(MainActivity.this, "No videoes for this college yet", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(VideoListActivity.VIDEO_LIST_ACTIVITY, videos);

        Intent activityIntent = new Intent(MainActivity.this, VideoListActivity.class);
        activityIntent.putExtras(bundle);
        this.startActivity(activityIntent);
    }

    @Override
    public void OnFooterOtherItemsSelected(int type, int instituteID) {
        switch (type)
        {
            case InstituteDetailFragment.QnA:
            {
                this.mMakeNetworkCall(Constants.TAG_LOAD_QNA_QUESTIONS, Constants.BASE_URL + "personalize/qna/" , null);
                break;
            }
            case InstituteDetailFragment.News:
            {
                this.mMakeNetworkCall(Constants.WIDGET_NEWS, Constants.BASE_URL + "personalize/news/" + "?institute=" + String.valueOf(instituteID) , null);
                break;
            }
            case InstituteDetailFragment.Articles:
            {
                this.mMakeNetworkCall(Constants.WIDGET_ARTICES, Constants.BASE_URL + "personalize/articles/" + "?institute=" + String.valueOf(instituteID) , null);
                break;
            }
            default:
                break;
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return getProfileLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(MainActivity.user == null)
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
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        Map<String, String> params = new HashMap<>();
        params.put(Constants.USER_EDUCATION_SET,"1");
        params.put(Constants.USER_DEVICE_ID, deviceId);

        this.mMakeNetworkCall(Constants.TAG_USER_EDUCATION_SET,Constants.BASE_URL + "preferences/", params);

        User userObj = null;
        try {
            userObj = JSON.std.beanFrom(User.class, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(userObj != null && user != null){
          this.user.setSublevel(userObj.getSublevel());
          this.user.setIs_preparing(userObj.getIs_preparing());

        }
        this.mMakeNetworkCall(Constants.TAG_EXAMS_LIST, Constants.BASE_URL + "yearly-exams/",null);

        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.USER_CURRENT_SUBLEVEL, user.getSublevel());
        eventValue.put(Constants.USER_IS_PREPARING, user.getIs_preparing());

        MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_USER_PREFERENCE, eventValue);

        //GA Event
        MainActivity.GATrackerEvent(Constants.CATEGORY_PREFERENCE, Constants.ACTION_CURRENT_STREAM_SELECTED, user.getSublevel());
        MainActivity.GATrackerEvent(Constants.CATEGORY_PREFERENCE, Constants.ACTION_USER_IS_PREPARING, user.getIs_preparing());

        //Send event to connecto
        this.connecto.track(Constants.ACTION_USER_PREFERENCE, new Properties().putValue(Constants.ACTION_CURRENT_STREAM_SELECTED, user.getStream_name()).putValue(Constants.ACTION_USER_IS_PREPARING, user.getIs_preparing()));
    }

    /**
     * This method is used to handle response having exams list
     * after user education detail is submitted to server.
     * @param responseJson
     */
    private void mOnExamsLoaded(String responseJson,boolean addToBackStack) {
        try {
            List<Exam> mExamList = JSON.std.listOfFrom(Exam.class, extractResults(responseJson));
            this.mDisplayFragment(ExamsFragment.newInstance(new ArrayList<>(mExamList)),addToBackStack,ExamsFragment.class.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to send exam list to server which are
     * selected by user
     * @param params
     */
    @Override
    public void onExamsSelected(JSONObject params) {
        this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_EXAMS_LIST,Constants.BASE_URL + "yearly-exams/",params,1);
    }

    /**
     * This method is used to send user's current education
     * details to server like current level, stream and marks
     */
    @Override
    public void onUserNotPreparingSelected() {
        this.mDisplayFragment(NotPreparingFragment.newInstance(),true,NotPreparingFragment.class.toString());
    }

    /**
     * This method is load user profile after
     */
    private void mLoadUserProfile(String responseJson)  {

        if(responseJson != null && !responseJson.isEmpty()) {
            try {
                this.mUserExamsList = JSON.std.listOfFrom(ExamDetail.class, extractResults(responseJson));

                Map<String, Object> eventValue = new HashMap<String, Object>();
                String[] examNames = new String[this.mUserExamsList.size()];
                for (int n = 0; n < this.mUserExamsList.size(); n++)
                {
                    ExamDetail examDetail = this.mUserExamsList.get(n);

                    //GA Event
                    MainActivity.GATrackerEvent(Constants.CATEGORY_PREFERENCE, Constants.ACTION_USER_EXAM_SELECTED, examDetail.getExam_name());
                    //Connecto Event
                    this.connecto.track(Constants.ACTION_USER_PREFERENCE, new Properties().putValue(Constants.ACTION_USER_EXAM_SELECTED, examDetail.getExam_name()));

                    examNames[n] = examDetail.getExam_name() + "#" + examDetail.getExam_date();
                }

                eventValue.put(Constants.USER_EXAMS_SET, examNames);

                //Appsflyer events
                MainActivity.AppsflyerTrackerEvent(this, Constants.ACTION_USER_EXAM_SELECTED, eventValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //  show appBarLayout and toolBar
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.container).getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        findViewById(R.id.container).setLayoutParams(params);
        if(mUserExamsList == null)
            mUserExamsList = new ArrayList<>();
        if(this.mUserExamsList.size() <=0)
            prepBuddies.setVisibility(View.GONE);
        else
            prepBuddies.setVisibility(View.VISIBLE);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName());
        if(fragment == null)
           this.mDisplayFragment(ProfileFragment.newInstance(new ArrayList<>(mUserExamsList)),false,ProfileFragment.class.toString() );
        else{
            this.mDisplayFragment(fragment, false, ProfileFragment.class.getSimpleName());
        }
        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.USER_PROFILE_LOADED, true).commit();

    }


    @Override
    public void onExamTabSelected(ExamDetail examDetailObj) {
         if(examDetailObj == null)return;
        String id = examDetailObj.getId();
        Map<String , String> params = this.mGetTheFilters();
        if(params == null)
            params = new HashMap<>();
        params.put("tag_uris[" + (params.size()) + "]",examDetailObj.getExam_tag());

        this.mMakeNetworkCall(Constants.TAG_EXAM_SUMMARY, Constants.BASE_URL + "yearly-exams/"+id+"/summary/",params);
    }

    public void onHomeItemSelected(String requestType, String url, String tag) {

        if (requestType.equals(Constants.WIDGET_INSTITUTES)
                || requestType.equalsIgnoreCase(Constants.WIDGET_RECOMMENDED_INSTITUTES)){
                //|| requestType.equalsIgnoreCase(Constants.WIDGET_SHORTLIST_INSTITUTES)) {

           Map<String , String> params = this.mGetTheFilters();
            if(tag != null && !tag.isEmpty())
            params.put("tag_uris[" + (params.size()) + "]",tag);

            this.mMakeNetworkCall(requestType, url, params);
            return;
        }else if(requestType.equals(Constants.WIDGET_TEST_CALENDAR)){
            this.mMakeNetworkCall(requestType,url,null);
            return;
        }
        if (requestType.equals(Constants.WIDGET_SYLLABUS)) {
            this.mMakeNetworkCall(requestType, url, null);
            return;
        }
        Map<String, String> params = new HashMap<>();
        if(tag != null && !tag.isEmpty())
            params.put("tag_uris[" + (0) + "]",tag);

       this.mMakeNetworkCall(requestType,url, params);
    }

    @Override
    public void onPsychometricTest() {
        this.mMakeNetworkCall(Constants.TAG_PSYCHOMETRIC_QUESTIONS,Constants.BASE_URL+"psychometric/",null);
    }

    private void onPsychometricTestResponse(String response){

        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String val = JSON.std.asString(map.get("questions"));
            this.psychometricQuestionsList = JSON.std.listOfFrom(PsychometricTestQuestion.class, val);
            this.mDisplayFragment(PsychometricTestParentFragment.newInstance(new ArrayList(this.psychometricQuestionsList)),true,PsychometricTestParentFragment.class.toString() );

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
                        switch (which)
                        {
                            case 0:
                                StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.IN_SCHOOL);
                                MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP + "#" + StepByStepQuestion.CurrentLevels.IN_SCHOOL + "#" + "1", Constants.BASE_URL + "step-by-step/ug-ques-one/", null);
                                break;
                            case 1:
                                StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.GRADUATE_COLLEGE);
                                MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP  + "#" + StepByStepQuestion.CurrentLevels.GRADUATE_COLLEGE + "#" + "1", Constants.BASE_URL + "step-by-step/pg-ques-one/", null);
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
    }

    @Override
    public void onIknowWhatIWant() {
        this.mMakeNetworkCall(Constants.TAG_LOAD_STREAM, Constants.BASE_URL + "streams/", null);
    }

    @Override
    public void onSubmitPsychometricTest(JSONObject params) {
        this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM,Constants.BASE_URL + "psychometric/",params, 1);
    }

    public void onSubjectSelected(Subjects subject, int position) {
        this.mDisplaySubjectSyllabus(subject);
    }

    @Override
    public void onSubjectCheckboxSelected(Subjects subject, int position) {

    }

    @Override
    public void onSyllabusChanged(JSONObject jsonObject) {

       String examId = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getString(Constants.SELECTED_EXAM_ID,  "");
       if(!examId.isEmpty())
           this.mMakeJsonObjectNetworkCall(Constants.SUBMITTED_CHAPTER_STATUS, Constants.BASE_URL+"yearly-exams/"+examId+"/syllabus/",jsonObject,1);

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
                ContactsContract.Contacts.Data.MIMETYPE + " = ? OR "+ContactsContract.Contacts.Data.MIMETYPE + " = ?",
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }



    @Override
    public void onNameUpdated(HashMap params, String msg) {
        this.mMakeNetworkCall(Constants.TAG_NAME_UPDATED + "#" + msg, Constants.BASE_URL + "preferences/", params);
    }

    private void onNameUpdatedResponse(String response , String msg) {
        User tempuser = MainActivity.user;
        try {
            user = JSON.std.beanFrom(User.class, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //save the preferences locally
        user.setPref(User.Prefs.STREAMKNOWN);
        if (tempuser != null){
            user.setToken(tempuser.getToken());
            user.setImage(tempuser.getImage());
            user.setLevel_name(tempuser.getLevel_name());
            user.setStream_name(tempuser.getStream_name());
            user.setStream(tempuser.getStream());
            user.setLevel(tempuser.getLevel());
            user.setPrimaryEmail(tempuser.getPrimaryEmail());
            user.setPrimaryPhone(tempuser.getPrimaryPhone());
            user.setSublevel(tempuser.getSublevel());
            user.setIs_preparing(tempuser.getIs_preparing());
            user.profileData = tempuser.profileData;
        }

        String u = null;
        try {
            u = JSON.std.asString(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

        if(currentFragment instanceof  MyFutureBuddiesFragment)
        {
            ((MyFutureBuddiesFragment)currentFragment).sendChatRequest(msg);
        }
    }
    private void onTestCalendarResponse(String response){

        try {
//            String val = this.extractResults(response);
            this.chaptersList = JSON.std.listOfFrom(Chapters.class, response);

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(CalendarParentFragment.class.getSimpleName() );
            if(fragment == null)
                this.mDisplayFragment(CalendarParentFragment.newInstance(new ArrayList(this.chaptersList)), true, CalendarParentFragment.class.getSimpleName() );
            else {
                if (currentFragment instanceof CalendarParentFragment)
                    ((CalendarParentFragment) currentFragment).updateCalander(new ArrayList(this.chaptersList));

                this.mDisplayFragment(fragment, false, CalendarParentFragment.class.getSimpleName());

                //this.mDisplayFragment(CalendarParentFragment.newInstance(new ArrayList(this.chaptersList)), true, Constants.WIDGET_TEST_CALENDAR);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    @Override
    public void onSubmitCalendarData(JSONObject object,String url) {
        this.mMakeJsonObjectNetworkCall(Constants.TAG_PSYCHOMETRIC_RESPONSE,Constants.BASE_URL+url,object,1);
        String examId = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getString(Constants.SELECTED_EXAM_ID,  "");
        if(!examId.isEmpty())
        this.onHomeItemSelected(Constants.WIDGET_TEST_CALENDAR, Constants.BASE_URL+"yearly-exams/"+examId+"/calendar/", null);
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
           int postion = -1 ;
           try{
              postion = Integer.parseInt((String)view.getTag());
           }catch (Exception e){
               e.printStackTrace();
           }

           if(postion == 4){
                MainActivity.this.mMakeNetworkCall(Constants.TAG_MY_ALERTS,Constants.BASE_URL+"exam-alerts/",null);
                    return;
           }
           mUpdateTabMenuItem(postion);
           onTabMenuSelected(postion);
       }
   };

    public void mUpdateTabMenuItem(int tabPosition){
       prepBuddies.setSelected(false);
        resourceBuddies.setSelected(false);
        futureBuddies.setSelected(false);
        myAlerts.setSelected(false);


        if(tabPosition == 1)
            prepBuddies.setSelected(true);
        if(tabPosition == 2)
            resourceBuddies.setSelected(true);

    }

    private void onTabMenuSelected(int tabPosition) {

        //TODO::  remove this when future buddies tab are present
        if(tabPosition == 3){
            this.onHomeItemSelected(Constants.WIDGET_FORUMS, Constants.BASE_URL+"personalize/forums", null);
            return;
        }
        if(mUserExamsList == null )mUserExamsList = new ArrayList<>();

        if(this.mUserExamsList.size() <=0)
            prepBuddies.setVisibility(View.GONE);
        else
            prepBuddies.setVisibility(View.VISIBLE);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TabFragment.class.getSimpleName() );
        if(fragment == null)
            this.mDisplayFragment(TabFragment.newInstance(tabPosition,new ArrayList<>(mUserExamsList)), true, TabFragment.class.getSimpleName() );
        else {
            if (currentFragment instanceof TabFragment)
                ((TabFragment) currentFragment).updateTabFragment(tabPosition);

            this.mDisplayFragment(fragment, false, TabFragment.class.getSimpleName() );
        }
    }

    private void onMyAlertsLoaded(String response) {
//        response="{\"results\":[{\"count\":1,\"dates\":[{\"date\":\"2015-09-30\",\"exam_id\":1,\"type\":\"Advertisement Opening Date\"}],\"year\":2015,\"month\":9},{\"count\":2,\"dates\":[{\"date\":\"2015-05-19\",\"exam_id\":265,\"type\":\"Advertisement Opening Date\"},{\"date\":\"2015-05-20\",\"exam_id\":265,\"type\":\"Registration Start Date\"}],\"year\":2015,\"month\":5},{\"count\":5,\"dates\":[{\"date\":\"2015-06-08\",\"exam_id\":265,\"type\":\"Admit Card Start Date\"},{\"date\":\"2015-06-10\",\"exam_id\":265,\"type\":\"Admit Card End Date\"},{\"date\":\"2015-06-14\",\"exam_id\":265,\"type\":\"Test Date\"},{\"date\":\"2015-06-14\",\"exam_id\":265,\"type\":\"Test Start Date\"},{\"date\":\"2015-06-14\",\"exam_id\":265,\"type\":\"Test End Date\"}],\"year\":2015,\"month\":6},{\"count\":1,\"dates\":[{\"date\":\"2015-07-03\",\"exam_id\":265,\"type\":\"Test Results Date\"}],\"year\":2015,\"month\":7}]}";
        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String result = JSON.std.asString(map.get("results"));
            this.myAlertsList = JSON.std.listOfFrom(MyAlertDate.class, result);
            this.displayAlerts(new ArrayList(this.myAlertsList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayAlerts(ArrayList<MyAlertDate>dates){
        this.mDisplayFragment(UserAlertsFragment.newInstance(dates),true,UserAlertsFragment.class.toString());
    }

}