package com.collegedekho.app.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import com.android.volley.Request;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.DebugLogQueue;
import com.collegedekho.app.R;
import com.collegedekho.app.crop.Crop;
import com.collegedekho.app.database.DataBaseHelper;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.DeviceProfile;
import com.collegedekho.app.entities.Exam;
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
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.entities.PsychometricTestQuestion;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.entities.StepByStepQuestion;
import com.collegedekho.app.entities.StepByStepResult;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.entities.Subjects;
import com.collegedekho.app.entities.VideoEntry;
import com.collegedekho.app.entities.Widget;
import com.collegedekho.app.entities.YoutubeVideoDetails;
import com.collegedekho.app.fragment.ArticleDetailFragment;
import com.collegedekho.app.fragment.ArticleFragment;
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.fragment.CDRecommendedInstituteFragment;
import com.collegedekho.app.fragment.CalendarParentFragment;
import com.collegedekho.app.fragment.ExamsFragment;
import com.collegedekho.app.fragment.FilterFragment;
import com.collegedekho.app.fragment.HomeFragment;
import com.collegedekho.app.fragment.InstituteDetailFragment;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
import com.collegedekho.app.fragment.InstituteQnAFragment;
import com.collegedekho.app.fragment.InstituteVideosFragment;
import com.collegedekho.app.fragment.PostAnonymousLoginFragment;
import com.collegedekho.app.fragment.ProfileBuildingFragment;
import com.collegedekho.app.fragment.LoginFragment;
import com.collegedekho.app.fragment.MyFutureBuddiesEnumerationFragment;
import com.collegedekho.app.fragment.MyFutureBuddiesFragment;
import com.collegedekho.app.fragment.NewsDetailFragment;
import com.collegedekho.app.fragment.NewsFragment;
import com.collegedekho.app.fragment.NotPreparingFragment;
import com.collegedekho.app.fragment.OTPVerificationFragment;
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
import com.collegedekho.app.fragment.WebViewFragment;
import com.collegedekho.app.fragment.WishlistFragment;
import com.collegedekho.app.fragment.stepByStepTest.StepByStepFragment;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.listener.OnApplyClickedListener;
import com.collegedekho.app.listener.OnArticleSelectListener;
import com.collegedekho.app.listener.OnNewsSelectListener;
import com.collegedekho.app.listener.ProfileFragmentListener;
import com.collegedekho.app.receiver.OTPReceiver;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.ContainerHolderSingleton;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.FirebaseUtils;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;
import com.collegedekho.app.widget.GifView;
import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.jr.ob.JSON;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.connecto.android.sdk.Connecto;
import io.connecto.android.sdk.Properties;
import io.connecto.android.sdk.Traits;
import io.fabric.sdk.android.Fabric;

import static com.collegedekho.app.utils.AnalyticsUtils.*;

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
        implements LoaderManager.LoaderCallbacks<Cursor>, NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnTabSelectListener, TabFragment.OnHomeItemSelectListener,
        DataLoadListener, StreamFragment.OnStreamInteractionListener,PsychometricStreamFragment.OnStreamInteractionListener,
        AdapterView.OnItemSelectedListener, ExamsFragment.OnExamsSelectListener,
        InstituteListFragment.OnInstituteSelectedListener, OnApplyClickedListener, OnNewsSelectListener,
        ProfileFragment.UserProfileListener, OnArticleSelectListener,
        InstituteQnAFragment.OnQuestionAskedListener, FilterFragment.OnFilterInteractionListener,
        InstituteOverviewFragment.OnInstituteShortlistedListener, QnAQuestionsListFragment.OnQnAQuestionSelectedListener,
        QnAQuestionDetailFragment.OnQnAAnswerInteractionListener, MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener,
        MyFutureBuddiesFragment.OnMyFBInteractionListener,  ProfileBuildingFragment.OnUserEducationInteractionListener,
        LoginFragment.OnUserLoginListener,PostAnonymousLoginFragment.OnUserPostAnonymousLoginListener,InstituteDetailFragment.OnInstituteDetailListener,
        PsychometricTestParentFragment.OnPsychometricTestSubmitListener,
        SyllabusSubjectsListFragment.OnSubjectSelectedListener, CalendarParentFragment.OnSubmitCalendarData,
        NotPreparingFragment.OnNotPreparingOptionsListener, StepByStepFragment.OnStepByStepFragmentListener,
        UserAlertsFragment.OnAlertItemSelectListener, GifView.OnGifCompletedListener, CDRecommendedInstituteFragment.OnCDRecommendedInstituteListener,
        InstituteVideosFragment.OnTitleUpdateListener,OTPVerificationFragment.OTPVerificationListener, ITrueCallback, WishlistFragment.WishlistInstituteInteractionListener {

    static {

        // to support vector drawable in prelollypop
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Constants.FilterCategoryMap.put(Constants.ID_FACILITIES, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);
        Constants.FilterCategoryMap.put(Constants.ID_HOSTEL, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);

        Constants.FilterCategoryMap.put(Constants.ID_FEE_RANGE, Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES);
        Constants.FilterCategoryMap.put(Constants.ID_INSTITUTE_TYPE, Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES);

        Constants.FilterCategoryMap.put(Constants.ID_CITY, Constants.FILTER_CATEGORY_LOCATION);
        Constants.FilterCategoryMap.put(Constants.ID_STATE, Constants.FILTER_CATEGORY_LOCATION);

        Constants.FilterCategoryMap.put(Constants.ID_SPECIALIZATION, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
        Constants.FilterCategoryMap.put(Constants.ID_DEGREE, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
        Constants.FilterCategoryMap.put(Constants.ID_EXAM, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String TAG = "MainActivity";
    public static boolean IN_FOREGROUND=false;
    private boolean IS_NETWORK_TASK_RUNNING=false;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    public static NetworkUtils mNetworkUtils;
    public static volatile BaseFragment currentFragment;
    private List<Institute> mInstituteList;
    private List<Chapters> chaptersList;
    private List<PsychometricTestQuestion> psychometricQuestionsList;
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
    private Date mTimeScreenClicked = new Date();
    public boolean fromTabFragment = false;
    private String mGTMContainerId = "www.collegedekho.com";
    public Connecto connecto = null;
    public DeviceProfile mDeviceProfile;
    public static Profile mProfile;
    static String type = "";
    static String resource_uri = "";
    private Menu menu;
    private String mYear;

    private View mHomeTabContainer;
    private TextView mCollegeTab;
    private TextView mConnectTab;
    private TextView mPrepareTab;
    private TextView mReadTab;
    public View currentBottomItem;

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
    private  boolean IS_HOME_LOADED;
    private static Context mContext;
    public static TrueClient mTrueClient;
    private Resources mResources ;
    private TextToSpeech mTextToSpeech;
    /*** ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public GoogleApiClient client;

    private SearchView mSearchView = null;
    private ProgressBar mSearchProgress;
    private Handler gcmDialogHandler=new Handler();
    private Runnable gcmDialogRunnable;
    // navigation drawer and toolbar variables
    private Toolbar mToolbar;
    private NavigationView mNavigationView ;
    private DrawerLayout mDrawerLayout ;
    public ActionBarDrawerToggle mDrawerToggle;

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
        String intentType = intent.getType();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            if (data.contains("collegedekho.com"))
            {
                this.mDeepLinkingURI = data;
                Log.e(TAG, " DeepLinking URL is  : "+ mDeepLinkingURI);
            }
        }
        else if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(intentType)) {
                this.mOtherAppSharedMessage = intent.getStringExtra(Intent.EXTRA_TEXT);
            } else if (intentType.startsWith("image/")) {
                // Handle single image being sent
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

        /*Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i(TAG, "App Link Target URL: " + targetUrl.toString());
        }*/

        this.setContentView(R.layout.activity_main);

        // init App
        init();

        // register with true SDk
        this.mRegistrationTrueSdk();

        // register with Connecto
        this.mRegistrationConnecto();

        // register with fabric Crashlytics
        this.mRegistrationFabricCrashlytics();

        // register with Apps Flayer
        this.mRegistrationAppsFlyer();

        // register with facebook Sdk
        //this.mRegistrationFacebookSdk();

        // register with GA tracker
        this.mRegistrationGATracker();

        this.mSetupGTM();

        // set up app tool bar
        this.mSetAppToolBar();

        // load splash screen
        this.mDisplayFragment(SplashFragment.newInstance(), false, SplashFragment.class.getName());

        // TODO: Move this to where you establish a user session
        logUser();

        setupOtpRequest(true);

        int amIConnectedToInternet = MainActivity.mNetworkUtils.getConnectivityStatus();
        if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED && IS_HOME_LOADED) {
            Utils.appLaunched(this);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mSearchProgress = (ProgressBar) findViewById(R.id.resource_progress_bar);

        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTextToSpeech.setLanguage(Locale.UK);
                }
            }
        });

    }

    /**
     * This method is called to initialize the app
     */
    public void init() {
        // set resource context
        this.mResources = getResources();
        //this.getBitMapResources();

        // set snackbar to show msg in snackbar display
        this.mSnackbar = Snackbar.make(this.findViewById(R.id.drawer_layout), "You are not connected to Internet", Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) mSnackbar.getView();
        layout.setBackgroundColor(getResources().getColor(R.color.primary_color));

        // create network utills
        this.mNetworkUtils = new NetworkUtils(this, this);

        // start loader for cursor
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED ) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        this.mHomeTabContainer = findViewById(R.id.bottom_button_container);
        this.mCollegeTab = (TextView)findViewById(R.id.college_list);
        this.mConnectTab = (TextView)findViewById(R.id.connect);
        this.mPrepareTab = (TextView)findViewById(R.id.prepare);
        this.mReadTab    = (TextView)findViewById(R.id.read);
        this.mCollegeTab.setOnClickListener(mClickListener);
        this.mConnectTab.setOnClickListener(mClickListener);
        this.mPrepareTab.setOnClickListener(mClickListener);
        this.mReadTab.setOnClickListener(mClickListener);

        SharedPreferences sp = this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE);
        try {
            if (sp.contains(getResourceString(R.string.KEY_USER))) {
                // load profile mDeviceProfile
                mProfile = JSON.std.beanFrom(Profile.class, sp.getString(getResourceString(R.string.KEY_USER), null));
                mNetworkUtils.setToken(MainActivity.mProfile.getToken());

                // mDeviceProfile id register
                setUserIdWithAllEvents();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.IS_USER_CREATED = sp.getBoolean(getResourceString(R.string.USER_CREATED), false);
            this.IS_HOME_LOADED = sp.getBoolean(getResourceString(R.string.USER_HOME_LOADED), false);

        }
    }

    /**
     * This method is used to register with true sdk
     */
    private void mRegistrationTrueSdk() {
        MainActivity.mTrueClient = new TrueClient(getApplicationContext(), this);
    }

    /**
     * This method is used  with connecto sdk
     */
    private void mRegistrationConnecto() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                connecto = Connecto.with(MainActivity.this);
                //this.connecto.identify("Harsh1234Vardhan", new Traits().putValue("name", "HarshVardhan"));
                //You can also track any event if you want
                //this.connecto.track("Session Started", new Properties().putValue("value", 800));
                connecto.registerWithGCM(MainActivity.this, Constants.SENDER_ID);
            }
        }).start();

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
   /* private void mRegistrationFacebookSdk()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FacebookSdk.sdkInitialize(MainActivity.this);
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
        }).start();

    }*/

    /**
     * This method is used to register Fabric Crashlytics
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                // The Dev key cab be set here or in the manifest.xml
                AppsFlyerLib.getInstance().startTracking(getApplication(), Constants.APPSFLYER_ID);

                //AppsFlyer: collecting your GCM project ID by setGCMProjectID allows you to track uninstall data in your dashboard

                AppsFlyerLib.getInstance().setGCMProjectNumber(Constants.GCM_KEY_APPS_FLYER);
                // Set the Currency
                AppsFlyerLib.getInstance().setCurrencyCode("INR");
                AppsFlyerLib.getInstance().setDebugLog(true);

                AppsFlyerLib.getInstance().registerConversionListener(MainActivity.this,new AppsFlyerConversionListener() {
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
                Looper.loop();
            }
        }).start();

    }


    public void loadInItData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            // start curser loader
            getSupportLoaderManager().initLoader(0, null, this);

            // register GCM dialog to ask mDeviceProfile'profile data
            mRegisterGcmDialog();

            if (IS_USER_CREATED) {
                // if mDeviceProfile is anonymous  then logout from facebook
                if (mProfile != null && (mProfile.getIs_anony() == ProfileMacro.ANONYMOUS_USER))
                  //  disconnectFromFacebook();

                this.mLoadUserStatusScreen();
            } else {
               // disconnectFromFacebook();
                MainActivity.this.mDisplayLoginFragment();
            }
        } else {
            getUserPermissions();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
    }

    /* private void getBitMapResources(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                BitMapHolder bitMapHolder = new BitMapHolder();
                bitMapHolder.setContext(MainActivity.this);
                bitMapHolder.getBitMapFromResource();
            }
        }).start();

    }*/
    @Override
    public void onGifCompleted() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentFragment instanceof SplashFragment)
                    ((SplashFragment) currentFragment).isInternetAvailable();
            }
        }, 10);
    }

    private void mSetAppToolBar() {

        // replace default action bar with Tool bar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // register drawer layout  and toolbar with DrawerToggle
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // set user profile image on navigation drawer layout
                CircularImageView mProfileImage = (CircularImageView)drawerView. findViewById(R.id.profile_image);
                mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
                mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);
                String profileImage = MainActivity.mProfile.getImage();

                if (profileImage == null )profileImage ="";
                mProfileImage.setImageUrl(profileImage, MySingleton.getInstance(getApplicationContext()).getImageLoader());

                // set user's name on navigation drawer
                String userName = MainActivity.mProfile.getName();
                if(userName != null ) {
                    ((TextView)drawerView.findViewById(R.id.user_name)).setText(userName);
                }
                // it will show user's profile completion progress on navigation drawer
                CircularProgressBar profileCompleted =  (CircularProgressBar)drawerView.findViewById(R.id.user_profile_progress);
                profileCompleted.setProgress(0);
                profileCompleted.setProgressWithAnimation(MainActivity.mProfile.getProgress(), 2000);

            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mToolbar.setLogo(R.drawable.ic_cd_colored);
       /* getToolbarLogoIcon(mToolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment instanceof SyllabusSubjectsListFragment)
                    ((SyllabusSubjectsListFragment) currentFragment).submitSyllabusStatus();
                else if (currentFragment instanceof CalendarParentFragment)
                    ((CalendarParentFragment) currentFragment).submitCalendarData();
                else if (currentFragment instanceof OTPVerificationFragment)
                    return;
                else if(isFromNotification ) {

                    mClearBackStack();
                    isFromNotification = false;
                    mLoadUserStatusScreen();
                    return;
                }
                mClearBackStack();
                invalidateOptionsMenu();
            }
        });*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int position = -1;
        if (id == R.id.nav_colleges) {
            position = 1;
        } else if (id == R.id.nav_connect) {
            position = 2;
        } else if (id == R.id.nav_prepare) {
            position = 3;
        } else if (id == R.id.nav_read) {
            position = 4;
        } else if (id == R.id.nav_share) {
            // request for share intent to download app  from playStore
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Explore top institutions, colleges and universities. Click here to download your personalised guide" +
                            ": https://play.google.com/store/apps/details?id=com.collegedekho.app&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if(position != -1) {
            mUpdateTabMenuItem(position);
            onTabMenuSelected(position);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        // mDrawerToggle.syncState();
        return true;
    }

    private void logUser() {
        if (MainActivity.mProfile != null) {
            Crashlytics.setUserIdentifier(MainActivity.mProfile.getId());
            Crashlytics.setUserEmail(MainActivity.mProfile.getEmail());
            Crashlytics.setUserName(MainActivity.mProfile.getName());
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
                if (MainActivity.mProfile != null)
                    MainActivity.resource_uri = MainActivity.resource_uri + "/?user_id=" + MainActivity.mProfile.getId();
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

    private void mHandleOtherAppSharedMessage(String message) {

        String resourceURI = "personalize/forums/";
        MainActivity.resource_uri = Constants.BASE_URL + resourceURI;
        MainActivity.type = Constants.TAG_FRAGMENT_MY_FB_ENUMERATION;
        this.mHandleNotifications(true);
    }

    /**
     * This is facebook login
     */
/*
    public void RequestData(final AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        // get mDeviceProfile profile info
                        String image = "https://graph.facebook.com/" + json.optString("id") + "/picture?type=large";

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
    }*/

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

   /* @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus)
        {
            if(currentBottomItem != null){
                currentBottomItem.animate().translationYBy(0f).setDuration(1000).start();
            }
        }
    }*/

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
       // AppEventsLogger.activateApp(this);
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
       // AppEventsLogger.deactivateApp(this);
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
                Uri.parse("https://www.collegedekho.com"),
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
                Uri.parse("https://www.collegedekho.com"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse(Constants.BASE_APP_URI.toString())
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }
    @Override
    protected void onDestroy() {
        this.connecto.track("Session Ended", new Properties().putValue("session_end_datetime", new Date().toString()));

        LocalBroadcastManager.getInstance(this).unregisterReceiver(appLinkReceiver);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                //called when the up affordance/carat in actionbar is pressed
                if(currentFragment instanceof  HomeFragment){
                    mDrawerLayout.openDrawer(GravityCompat.START);

                }else if(getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                    getSupportFragmentManager().popBackStack();
                }
                return true;
            case R.id.action_profile:
                mDisplayProfileFragment(MainActivity.mProfile, true);
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
                    int amIConnectedToInternet = MainActivity.mNetworkUtils.getConnectivityStatus();
                    if (amIConnectedToInternet == Constants.TYPE_NOT_CONNECTED) {
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
        if ( currentFragment instanceof ProfileBuildingFragment || currentFragment instanceof ProfileFragment
                || currentFragment instanceof ExamsFragment || currentFragment instanceof StreamFragment
                || currentFragment instanceof PsychometricStreamFragment || currentFragment instanceof StepByStepFragment
                || currentFragment instanceof  OTPVerificationFragment ||currentFragment instanceof WebViewFragment
                || currentFragment instanceof PsychometricTestParentFragment) {
            menu.setGroupVisible(R.id.main_menu_group, false);
        }else {
            if(menu.size()>0)
                menu.getItem(0).setVisible(true);
        }
        setSearchAvailable(menu);
//        if (!getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getResourceString(R.string.PROFILE_SCREEN_TUTE), false)) {
        if(currentFragment instanceof TabFragment){
            if(((TabFragment) currentFragment).getSelectedTab() == 1){
                boolean tute_complete = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
                if(!tute_complete){
                    menu.setGroupVisible(R.id.search_menu_group, false);
                    menu.setGroupVisible(R.id.main_menu_group, false);
                }
            } else if(((TabFragment) currentFragment).getSelectedTab() == 3){
                boolean tute_complete = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean("prepare_tute", false);
                if(!tute_complete){
                    menu.setGroupVisible(R.id.search_menu_group, false);
                    menu.setGroupVisible(R.id.main_menu_group, false);
                }
            }
        }

        if(currentFragment instanceof WishlistFragment){
            boolean tute_complete = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean("Wishlist tute", false);
            if(!tute_complete){
                menu.setGroupVisible(R.id.search_menu_group, false);
                menu.setGroupVisible(R.id.main_menu_group, false);
            }
        }

        if(currentFragment instanceof CDRecommendedInstituteFragment){
            int tab = ((CDRecommendedInstituteFragment) currentFragment).currentTabId;
            if(tab == 1){
                boolean tute_complete = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE), false);
                if(!tute_complete){
                    menu.setGroupVisible(R.id.search_menu_group, false);
                    menu.setGroupVisible(R.id.main_menu_group, false);
                }
            } else if (tab == 2){
                boolean tute_complete = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean("Wishlist tute", false);
                if(!tute_complete){
                    menu.setGroupVisible(R.id.search_menu_group, false);
                    menu.setGroupVisible(R.id.main_menu_group, false);
                }
            }
        }

        if(currentFragment instanceof  HomeFragment){
            boolean tuteCompleted = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean("Home Tute", false);
            if(!tuteCompleted){
                menu.setGroupVisible(R.id.search_menu_group, false);
                menu.setGroupVisible(R.id.main_menu_group, false);
            }else {
                menu.setGroupVisible(R.id.main_menu_group, true);
                menu.setGroupVisible(R.id.search_menu_group, true);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void setSearchAvailable(Menu menu) {
        if (currentFragment != null) {
            if (currentFragment instanceof HomeFragment || (currentFragment instanceof TabFragment )) {
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
            } else if (currentFragment instanceof InstituteListFragment && mCurrentTitle != null && !(mCurrentTitle.equals("WishList Institutes") || mCurrentTitle.equals("Recommended Institutes"))) {
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
     * This method is used to register userId with Apps Flyer
     * and GA Tracker
     */
    private void setUserIdWithAllEvents(){
        // register mDeviceProfile id with apps flyer
        AppsFlyerLib.getInstance().setCustomerUserId(MainActivity.mProfile.getId());
        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.SESSION_STARTED_DATE_TIME), new Date().toString());
        eventValue.put(getResourceString(R.string.USER_ID), MainActivity.mProfile.getId());
        eventValue.put(getResourceString(R.string.USER_EMAIL), mProfile.getEmail());
        eventValue.put(getResourceString(R.string.USER_PHONE), mProfile.getPhone_no());

        MainActivity.AppsflyerTrackerEvent(this,getResourceString(R.string.SESSION_STARTED),eventValue);
        // register mDeviceProfile id with GA tracker
        this.tracker.setClientId(MainActivity.mProfile.getId());
        // register mDeviceProfile id with connecto
        this.connecto.identify(MainActivity.mProfile.getId(), new Traits().putValue(getResourceString(R.string.USER_NAME), MainActivity.mProfile.getName()));
        this.connecto.track(getResourceString(R.string.SESSION_STARTED),  new Properties().putValue(getResourceString(R.string.SESSION_STARTED_DATE_TIME), new Date().toString()));

    }

    private void mRegisterGcmDialog(){
        gcmDialogRunnable = new Runnable() {
            @Override
            public void run() {
                if(IN_FOREGROUND && IS_HOME_LOADED){

                    if(!IS_NETWORK_TASK_RUNNING &&  currentFragment != null &&
                            !(currentFragment instanceof ProfileFragment)) {
                        Intent gcmIntent = new Intent(MainActivity.this, GCMDialogActivity.class);
                        MainActivity.this.startActivityForResult(gcmIntent,Constants.GCM_RESULT_DATA_KEY);
                    }else {
                        gcmDialogHandler.removeCallbacks(gcmDialogRunnable);
                        gcmDialogHandler.postDelayed(gcmDialogRunnable,3000);
                    }
                }else{
                    gcmDialogHandler.removeCallbacks(gcmDialogRunnable);
                    gcmDialogHandler.postDelayed(gcmDialogRunnable,90000);
                }
            }
        };
        gcmDialogHandler.postDelayed(gcmDialogRunnable,90000);
    }
    /**
     * This method is used to load screens according to mDeviceProfile status
     * if education and exam is selected then load profile screen
     * if education is selected but exam is not selected then load exams screen
     * if both are not selected then load education screen
     */
    private void mLoadUserStatusScreen() {
        if (MainActivity.type != null && !MainActivity.type.matches("")) {
            this.isFromNotification = true;
            this.mHandleNotifications(true);

        } else if (this.mOtherAppSharedMessage != null && !this.mOtherAppSharedMessage.isEmpty()) {

            this.mHandleOtherAppSharedMessage(this.mOtherAppSharedMessage);

        } else if (this.mDeepLinkingURI != null && !this.mDeepLinkingURI.isEmpty()) {
            Log.e("MA: DL URL ", MainActivity.this.mDeepLinkingURI);
            this.isFromDeepLinking = true;
            this.mHandleDeepLinking(this.mDeepLinkingURI);

        }else if ((MainActivity.mProfile.getExams_set()
                == ProfileMacro.EXAMS_SELECTED) || IS_HOME_LOADED){
            // show App bar layout
            mShowAppBarLayout();
            // load mDeviceProfile home screen
            mDisplayHomeFragment();
            // request to update profile info if anything is change on server
            requestForProfile(null);
        }else {
            mDisplayProfileBuildingFragment();
        }
    }
    public void onProfileImageUploaded(String responseJson) {
        try {
            Profile profile = JSON.std.beanFrom(Profile.class, responseJson);
            if(mProfile != null && profile != null)
                mProfile.setImage(profile.getImage());
            String u = JSON.std.asString(mProfile);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();

            if(currentFragment instanceof ProfileFragment ){//||currentFragment instanceof ProfileFragment){
               ((ProfileFragment) currentFragment).updateProfileImage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // TODO :: EDIT THIS TO MAKE POST ANONYOUS LOGIN
    @Override
    public void onPostAnonymousLogin() {
        mDisplayPostAnonymousLoginFragment();
    }


    private void mParseProfileResponse(String response){
        try {
            MainActivity.mProfile = JSON.std.beanFrom(Profile.class,response);

            String u = JSON.std.asString(mProfile);
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to load user's profile fragment
     * @param profile user profile object which contains user info
     * @param backStack fragment will add in back stack or not
     */
    private void mDisplayProfileFragment(Profile profile, boolean backStack){
        if(profile == null)
            return;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName());
        if (fragment == null) {
            mDisplayFragment(ProfileFragment.getInstance(profile), backStack, ProfileFragment.class.getSimpleName());
        } else{
            try {
                int count = getSupportFragmentManager().getBackStackEntryCount();
                for (int i = 0; i < count-1; i++)
                    getSupportFragmentManager().popBackStack();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            mDisplayFragment(fragment, false, ProfileFragment.class.getSimpleName());
        }
    }

    public void onRefreshProfile(){
        this.mMakeNetworkCall(Constants.TAG_REFRESH_PROFILE, Constants.BASE_URL + "profile/", null);
    }

    private void mParseAndRefreshProfileResponse(String response){
        try {
            MainActivity.mProfile = JSON.std.beanFrom(Profile.class,response);
            if(currentFragment instanceof  ProfileFragment)
                ((ProfileFragment) currentFragment).mRefreshProfileOnResponse(MainActivity.mProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to request for post and get mDeviceProfile's profile data
     * @param params
     */
    @Override
    public void requestForProfile(HashMap<String, String> params){
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED)
            return;

        if(params != null && mProfile != null) {
            // if mDeviceProfile's name is not available or name is anonymous and mDeviceProfile has saved his/her
            // name in me profile contacts then fetch name and save it on server
            String name = mProfile.getName();
            if(name == null || name.isEmpty()
                    || name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())) {
                if (mDeviceProfile != null && mDeviceProfile.profileData != null
                        && mDeviceProfile.profileData[0]  != null && !mDeviceProfile.profileData[0].isEmpty()){
                    params.put(getString(R.string.USER_NAME), mDeviceProfile.profileData[0]);
                }
            }
            // if mDeviceProfile's email id is anonymous and device play store email id does not
            // exist in our server database then sever will allow to replace it with mDeviceProfile's email id
            if (mProfile.getIs_anony() == ProfileMacro.ANONYMOUS_USER) {
                String email = Utils.getDeviceEmail(getApplicationContext());
                if(email != null && !email.isEmpty())
                    params.put(getString(R.string.USER_EMAIL), email);
            }
            // if mDeviceProfile's phone number is not available and it is saved in me profile contacts
            // then fetch it  and save it on mDeviceProfile's profile
            if (mProfile.getPhone_no() == null || mProfile.getPhone_no().length() < 10) {
                if (mDeviceProfile != null && mDeviceProfile.getPrimaryPhone() != null && !mDeviceProfile.getPrimaryPhone().isEmpty())
                    params.put(getString(R.string.USER_PHONE), mDeviceProfile.getPrimaryPhone());
            }
        }
        requestForUserProfileUpdate(Constants.TAG_UPDATE_PROFILE_OBJECT, params);
    }

    /**
     * This method is used to update mDeviceProfile profile whenever mDeviceProfile update
     *  his/her information about basic info , current education, preferred education details,
     *  interested exams details and other basic info
     * @param params
     * @param TAG
     */
    public void requestForUserProfileUpdate( String TAG, HashMap<String, String> params) {

        int requestMethod = Request.Method.GET;
        if(params !=  null)
            requestMethod = Request.Method.POST;

        this.mMakeNetworkCall(TAG, Constants.BASE_URL +"profile/", params, requestMethod);
    }

    /**
     * This method is called when user select exams while profile building
     * and send a request to set user's exams
     * @param params HashMap of user selected exams with yearly_exams as key and value as exam ids
     */
    @Override
    public void onUserExamSelected(HashMap<String, String> params) {
        this.requestForUserProfileUpdate(Constants.TAG_USER_EXAMS_SUBMISSION, params);
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
        this.requestForUserProfileUpdate(Constants.TAG_UPDATE_USER_PROFILE+"#"+viewPosition, params);
    }
    /**
     * This method used to request to show mDeviceProfile's profile screen  and update profile data ,
     * if profile data is not available then send a request to load mDeviceProfile's profile
     */
    @Override
    public void requestForProfileFragment() {
        if(MainActivity.mProfile == null)
            this.requestForUserProfileUpdate(Constants.TAG_LOAD_PROFILE, null);
        else{
            mDisplayProfileFragment(MainActivity.mProfile, true);
            this.requestForProfile(null);
        }
    }

    /**
     * This method is used to update mDeviceProfile name when mDeviceProfile does not have a name and wants to
     * chat or asks  a queation and give answer
     * @param params
     * @param msg
     */
    @Override
    public void onNameUpdated(HashMap params, String msg) {
        this.requestForUserProfileUpdate(Constants.TAG_NAME_UPDATED + "#" + msg, params);
    }

    /**
     * This method is used to request for specialization list based on
     * stream which is selected by the mDeviceProfile while updating his/her profile
     * @param streamId
     * @param requestType
     */
    @Override
    public void requestForSpecialization(int streamId, String requestType) {
        if (NetworkUtils.getConnectivityStatus() != Constants.TYPE_NOT_CONNECTED) {
            this.mMakeNetworkCall(Constants.TAG_REQUEST_FOR_SPECIALIZATION + "#" + requestType, Constants.BASE_URL + "specializations/?stream=" + streamId, null, Request.Method.GET);
        }
    }

    /**
     * This method is used to request for Degrees list based on
     * level which is selected by the mDeviceProfile while updating his/her profile
     * @param levelId levelID
     * @param requestType request Type
     */
    @Override
    public void requestForDegrees(int levelId, String requestType) {
        int amIConnectedToInternet = MainActivity.mNetworkUtils.getConnectivityStatus();
        if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
            this.mMakeNetworkCall(Constants.TAG_REQUEST_FOR_DEGREES + "#" + requestType, Constants.BASE_URL + "degrees/?level=" + levelId, null, Request.Method.GET);
        }
    }

    @Override
    public void toRecommended() {
        this.mMakeNetworkCall(Constants.WIDGET_RECOMMENDED_INSTITUTES_FRON_PROFILE, Constants.BASE_URL + "personalize/recommended-institutes/", null);
    }

    @Override
    public void toDashboard(){
        mClearBackStack();
    }

    /**
     *  This method is used to update mDeviceProfile profile info
     *  on Profile page after successfully updating any thing in profile
     * @param tag tag
     * @param responseJson responseJson
     */

    private void profileSuccessfullyUpdated(String tag, String responseJson) {
        try {
            String[] TAG = tag.split("#");
            Profile profile = JSON.std.beanFrom(Profile.class, responseJson);
            if(profile == null)
                return;
            ProfileFragment.mProfile= profile;
            MainActivity.mProfile = profile;

            if(currentFragment instanceof ProfileFragment) {
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
            }else if(currentFragment instanceof ProfileBuildingFragment){
                ((ProfileBuildingFragment) currentFragment).profileImageUploadedSuccessfully();
            }
            else if(currentFragment instanceof  HomeFragment){

                ((HomeFragment)currentFragment).updateUserInfo();
            }else if(currentFragment instanceof  TabFragment){

                ((TabFragment)currentFragment).updateUserInfo();
            }

        } catch (IOException e) {
            e.printStackTrace();
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
            Log.e(TAG, e.getMessage());
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
     * This method is used to update institutes list of next page
     * @param response server response to parse qna next list
     */
    private void updateLastInstituteList(String response) {
        try {
            List<Institute> institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            this.mInstituteList = institutes;

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
        try {
            System.gc();
            this.hideProgressDialog();

            if (!isHavingNextUrl)
                next = null;

            if (isUpdate) {
                if (currentFragment != null && currentFragment instanceof CDRecommendedInstituteFragment) {
                    String val = this.extractResults(response);
                    this.mInstituteList = JSON.std.listOfFrom(Institute.class, val);
                    if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.UNDECIDED) {
                        ((CDRecommendedInstituteFragment) currentFragment).showUndecidedInstitutes(this.mInstituteList, next);
                    }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.RECOMMENDED) {
                        ((CDRecommendedInstituteFragment) currentFragment).updateRecommendedList(this.mInstituteList, next, this.mRecommendedInstituteCount);
                    }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.FEATURED){
                        ((CDRecommendedInstituteFragment) currentFragment).updateBuzzList(this.mInstituteList, next, this.mFeaturedInstituteCount);
                    }
                }
            } else {
                String val = this.extractResults(response);
                this.mInstituteList = JSON.std.listOfFrom(Institute.class, val);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(getResourceString(R.string.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST));

                if (fragment == null) {
                    this.mDisplayFragment(CDRecommendedInstituteFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next,
                            this.mRecommendedInstituteCount, this.mShortListInstituteCount, this.mFeaturedInstituteCount, this.mUndecidedInstitutesCount,cdRecommendedInstituteType.ordinal()),
                            !isFromNotification, getResourceString(R.string.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST));
                } else {
                    if (fragment instanceof CDRecommendedInstituteFragment) {
                        if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.UNDECIDED) {
                            ((CDRecommendedInstituteFragment) fragment).showUndecidedInstitutes(this.mInstituteList, next);
                        }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.RECOMMENDED) {
                            ((CDRecommendedInstituteFragment) fragment).updateRecommendedList(this.mInstituteList, next, this.mRecommendedInstituteCount);
                        }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.SHORTLIST){
                            ((CDRecommendedInstituteFragment) currentFragment).updateWishList(this.mInstituteList, next);
                        }else if (cdRecommendedInstituteType == Constants.CDRecommendedInstituteType.FEATURED){
                            ((CDRecommendedInstituteFragment) currentFragment).updateBuzzList(this.mInstituteList, next,this.mFeaturedInstituteCount);
                        }
                    }

                    this.mDisplayFragment(fragment, false, getResourceString(R.string.TAG_FRAGMENT_CD_RECOMMENDED_INSTITUTE_LIST));
                }
            }
            // TODO::
           /* if (MainActivity.mDeviceProfile.getPartner_shortlist_count() > 0) {
                mDisplayOtpVerificationFragment();
            }*/
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

            if (currentFragment instanceof InstituteListFragment && fragment != null) {
                ((InstituteListFragment) fragment).clearList();
                ((InstituteListFragment) fragment).updateList(this.mInstituteList, next);
                ((InstituteListFragment) fragment).updateFilterButton(this.mFilterCount);
            }else {
                this.mDisplayFragment(InstituteListFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next, filterAllowed, this.mFilterCount,listType), !isFromNotification, Constants.TAG_FRAGMENT_INSTITUTE_LIST);
            }

            if(listType==Constants.WISH_LIST_TYPE){
                //TODO::
               /* if(MainActivity.mDeviceProfile.getPartner_shortlist_count()>0) {
                    mDisplayOtpVerificationFragment();
                }*/
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mDisplayWishlistInstituteList(String response, boolean filterAllowed, boolean isHavingNextUrl, int listType) {
        try {
            String val = this.extractResults(response);
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, val);

            if (!isHavingNextUrl)
                next = null;

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);

            if (fragment!= null && currentFragment instanceof WishlistFragment) {
                ((WishlistFragment) fragment).clearList();
                ((WishlistFragment) fragment).updateList(this.mInstituteList, next);
            }else {
                this.mDisplayFragment(WishlistFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next, filterAllowed), !isFromNotification, Constants.TAG_FRAGMENT_WISHLIST_INSTITUTE_LIST);
            }

            /*if(listType==Constants.WISH_LIST_TYPE){
                //TODO::
                if(MainActivity.mDeviceProfile.getPartner_shortlist_count()>0) {
                    mDisplayOtpVerificationFragment();
                }
            }*/
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
            this.mDisplayFragment(MyFutureBuddiesFragment.newInstance(this.mFB, commentsCount), true, Constants.TAG_FRAGMENT_MY_FB);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mUpdateMyFB(String response, int index, int oldCount) {
        try {
            this.mFB = this.mParseAndPopulateMyFB(response, index);

            //if number of comments have increased
//            this.speakMessageForAccessibility("You have "+String.valueOf(this.mFB.getComments_count()-oldCount)+" Comments");
            if (this.mFB.getComments_count() > oldCount) {
                ArrayList<MyFutureBuddyComment> myFbComments = this.mFB.getFutureBuddiesCommentsSet();

                if (currentFragment instanceof MyFutureBuddiesFragment)
                    ((MyFutureBuddiesFragment) currentFragment).updateChatPings(myFbComments,this.mFB.getComments_count());
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
            myFB.setNext(fb.getString("next"));
            myFB.setCity_name(fb.getString("city_name"));
            myFB.setState_name(fb.getString("state_name"));
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

//        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE);
//
//        if (fragment == null)
        if (instituteType == Constants.CDRecommendedInstituteType.SHORTLIST)
            this.mDisplayFragment(InstituteDetailFragment.newInstance(institute, Constants.CDRecommendedInstituteType.SHORTLIST), true, Constants.TAG_FRAGMENT_INSTITUTE);
        else
            this.mDisplayFragment(InstituteDetailFragment.newInstance(institute, Constants.CDRecommendedInstituteType.RECOMMENDED), true, Constants.TAG_FRAGMENT_INSTITUTE);
//        else
//            this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_INSTITUTE);

        //this.mMakeNetworkCall(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "institutecourses/" + "?institute=" + id, null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_NEWS, Constants.BASE_URL + "personalize/news/" + "?institute=" + String.valueOf(id), null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_ARTICLE, Constants.BASE_URL + "personalize/articles/" + "?institute=" + String.valueOf(id), null);

        //Appsflyer events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.INSTITUTE_RESOURCE_URI), institute.getResource_uri());
        eventValue.put(Constants.INSTITUTE_ID, String.valueOf(id));

        //Events
        SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_SELECTED), eventValue, this);
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
        else if(mTrueClient.onActivityResult(requestCode,resultCode,data)) {
            return;
        }
        /*else  if(callbackManager.onActivityResult(requestCode, resultCode, data))
        {
            return;
        }*/else if(requestCode==Constants.GCM_RESULT_DATA_KEY && resultCode==RESULT_OK){
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
    }

    // TODO :: EDIT THIS POST ANONYMOUS LOGIN FRAGMENT
    private void mDisplayPostAnonymousLoginFragment() {
        PostAnonymousLoginFragment fragment = PostAnonymousLoginFragment.newInstance(mProfile.getPhone_no());
        currentFragment = fragment;
        try {
            this.currentFragment = fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.container, fragment, getResourceString(R.string.TAG_FRAGMENT_POST_ANONYMOUS_LOGIN));
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
            mHideAppBarLayout();

        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
        } finally {
            //Send GA Session
            MainActivity.GAScreenEvent(getResourceString(R.string.TAG_FRAGMENT_POST_ANONYMOUS_LOGIN));
            HashMap<String, Object> eventValue = new HashMap<>();
            eventValue.put(getResourceString(R.string.SCREEN_NAME), getResourceString(R.string.TAG_FRAGMENT_POST_ANONYMOUS_LOGIN));
            eventValue.put(getResourceString(R.string.LAST_SCREEN_NAME), this.mLastScreenName);
            eventValue.put(getResourceString(R.string.TIME_LAPSED_SINCE_LAST_SCREEN_NAME_IN_MS), String.valueOf(new Date().getTime() - this.mTimeScreenClicked.getTime()));
            this.mTimeScreenClicked = new Date();
            this.mLastScreenName = getResourceString(R.string.TAG_FRAGMENT_POST_ANONYMOUS_LOGIN);
            //Events
            SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_SCREEN_SELECTED), eventValue, this);
        }
    }

    private void mDisplayLoginFragment() {
        LoginFragment fragment = LoginFragment.newInstance();
        try {
            currentFragment = fragment;
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
            HashMap<String, Object> eventValue = new HashMap<>();
            eventValue.put(getResourceString(R.string.SCREEN_NAME), getResourceString(R.string.TAG_FRAGMENT_LOGIN));
            eventValue.put(getResourceString(R.string.LAST_SCREEN_NAME), this.mLastScreenName);
            eventValue.put(getResourceString(R.string.TIME_LAPSED_SINCE_LAST_SCREEN_NAME_IN_MS), String.valueOf(new Date().getTime() - this.mTimeScreenClicked.getTime()));

            this.mTimeScreenClicked = new Date();
            this.mLastScreenName = getResourceString(R.string.TAG_FRAGMENT_LOGIN);

            //Events
            SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_SCREEN_SELECTED), eventValue, this);
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
            if (addToBackstack)
                fragmentTransaction.addToBackStack(fragment.toString());
            //fragmentTransaction.commit();
            fragmentTransaction.commitAllowingStateLoss();

            if (this.currentFragment instanceof HomeFragment) {

                mShowAppBarLayout();

                View bottomMenu = findViewById(R.id.bottom_tab_layout);
                bottomMenu.animate().translationY(0);
                bottomMenu.setVisibility(View.VISIBLE);
            }
            mDisplayHomeAsUpEnabled();
        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
        } finally {
            //Send GA Session
            MainActivity.GAScreenEvent(tag);
            HashMap<String, Object> eventValue = new HashMap<>();
            eventValue.put(getResourceString(R.string.SCREEN_NAME), tag);
            eventValue.put(getResourceString(R.string.LAST_SCREEN_NAME), this.mLastScreenName);
            eventValue.put(getResourceString(R.string.TIME_LAPSED_SINCE_LAST_SCREEN_NAME_IN_MS), String.valueOf(new Date().getTime() - this.mTimeScreenClicked.getTime()));

            this.mTimeScreenClicked = new Date();

            //Events
            SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_SCREEN_SELECTED), eventValue, this);

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
        if (currentFragment instanceof HomeFragment)
            mDrawerToggle.setDrawerIndicatorEnabled(true);
        else
            mDrawerToggle.setDrawerIndicatorEnabled(false);


        // set all menu items unchecked when tab fragment is not selected
        if(!(currentFragment instanceof TabFragment)){
            int size = mNavigationView.getMenu().size();
            for (int i = 0; i < size; i++) {
                mNavigationView.getMenu().getItem(i).setChecked(false);
            }
        }
    }

    private void mShowAppBarLayout(){
        //  show appBarLayout and toolBar
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.main_container).getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        findViewById(R.id.main_container).setLayoutParams(params);
        if(getSupportActionBar() != null)
        getSupportActionBar().show();

        if (findViewById(R.id.app_bar_layout).getVisibility() != View.VISIBLE)
            findViewById(R.id.app_bar_layout).setVisibility(View.VISIBLE);

    }

    private void mHideAppBarLayout(){
        //  show appBarLayout and toolBar
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.main_container).getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        findViewById(R.id.main_container).setLayoutParams(params);

        if(getSupportActionBar() != null)
        getSupportActionBar().hide();
    }

    @Override
    public void onDataLoaded(String tag, String response) {
        String extraTag = null;
        String childIndex ;
        String parentIndex ;
        String like = null;
        String[] tags = tag.split("#");
        int voteType ;
        boolean hideProgressDialog=true;
        switch (tags[0]) {
            case Constants.TAG_SKIP_LOGIN:
            case Constants.TAG_TRUE_SDK_LOGIN:
            case Constants.TAG_FACEBOOK_LOGIN:
            case Constants.TAG_PHONE_NUMBER_LOGIN:
                //Deleting exam summary on login, since old mDeviceProfile's and new mDeviceProfile's are merged. And profile is taken from latest mDeviceProfile.
                // So old mDeviceProfile's exam summary gets obsolete. We need to reset the cache
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                this.mUserCreatedSuccessfully(response);
                break;
            case Constants.TAG_USER_PHONE_ADDED:
                onMobileNumberSubmitted();
                break;
            case Constants.TAG_VERIFY_USER_PHONE:
                this.onOTPVerified(response);
                break;
            case Constants.TAG_USER_EXAMS_SUBMISSION:
                this.mOnUserExamsSubmitted(response);
                break;
            case Constants.TAG_SUBMIT_EDITED_EXAMS_LIST:
                mMakeNetworkCall(Constants.TAG_UPDATE_PROFILE_EXAMS, Constants.BASE_URL +"profile/", null);
                this.onUserExamsEdited(response);
                break;
            case Constants.TAG_UPDATE_PROFILE_OBJECT:
            case Constants.TAG_UPDATE_USER_PROFILE:
            case Constants.TAG_UPDATE_PROFILE_EXAMS:
                profileSuccessfullyUpdated(tag,response);
                break;
            case Constants.TAG_LOAD_PROFILE:
                this.mParseProfileResponse(response);
                break;
            case Constants.TAG_REFRESH_PROFILE:
                this.mParseAndRefreshProfileResponse(response);
                break;
            case Constants.PROFILE_IMAGE_UPLOADING:
                onProfileImageUploaded(response);
                break;
            case Constants.TAG_EXAM_SUMMARY:
                this.mUpdateExamDetail(response, true);
                break;
            case Constants.TAG_LOAD_STREAM:
                this.mDisplayStreams(response, true);
                break;
            case Constants.WIDGET_SHORTLIST_INSTITUTES:
                this.mCurrentTitle = "Shortlist Institutes";
                Constants.IS_RECOMENDED_COLLEGE = false;
                //this.mDisplayInstituteList(response, false, true, Constants.SHORTLIST_TYPE);
                this.mDisplayWishlistInstituteList(response, false, true, Constants.WISH_LIST_TYPE);
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
                break;

            case Constants.WIDGET_TRENDING_INSTITUTES:
                this.mCurrentTitle = "Featured Colleges";
                Constants.IS_RECOMENDED_COLLEGE = false;
                this.mDisplayInstituteList(response, false, true);
                break;
            case Constants.TAKE_ME_TO_RECOMMENDED:
                isFromNotification = true;
                this.mCurrentTitle = "Recommended Institutes";
                if(currentFragment instanceof ProfileBuildingFragment)
                    ((ProfileBuildingFragment)currentFragment).hideNavigationIcon();
                mShowAppBarLayout();
                Constants.IS_RECOMENDED_COLLEGE = true;
                if (tags.length == 2 && tags[1] != null && tags[1].equals("next")) {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.RECOMMENDED, true);
                } else {
                    this.mDisplayCDRecommendedInstituteList(response, true, Constants.CDRecommendedInstituteType.RECOMMENDED, false);
                }
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
            case Constants.TAG_APPLIED_COURSE:
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                this.mUpdateAppliedCourses(response);
                break;
            case Constants.TAG_WISH_LIST_APPLIED_COURSE:
                Utils.DisplayToastShort(MainActivity.mContext, getResourceString(R.string.applied_successfully));
                if (tags.length > 1)
                    this.mUpdateAppliedInstituteWishlist(Integer.parseInt(tags[1]));
                break;
            case Constants.TAG_POST_QUESTION:
                this.mInstituteQnAQuestionAdded(response);
                break;
            case Constants.TAG_LOAD_FILTERS:
                this.updateFilterList(response);
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

            case Constants.TAG_REQUEST_FOR_SPECIALIZATION:
                if (tags.length > 1)
                    updateUserSpecializationList(tags[1], response);
                break;
            case Constants.TAG_REQUEST_FOR_DEGREES:
                if (tags.length > 1)
                    updateUserDegreesList(tags[1], response);
                break;
            case Constants.TAG_REQUEST_FOR_EXAMS:
                onResponseUserExamsList(response);
                break;
            case Constants.TAG_UPDATE_STREAM:
                this.mDisplayStreams(response, true);
                break;
            case Constants.TAG_UPDATE_INSTITUTES:
                this.mUpdateInstituteList(response);
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
                this.onPsychometricTestResponse(response);
                break;
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM:
                this.mDisplayStreamsSelection(response);
                break;
            case Constants.TAG_LOAD_STEP_BY_STEP:
                this.mDisplayStepByStepQuestion(response);
                break;
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
            case Constants.TAG_RECOMMENDED_SHORTLIST_INSTITUTE:
                this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.SHORTLIST);
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                hideProgressDialog = updateCDRecommendedFragment(tags);
                refreshCountOnShortlist(tags[2]);
                updateCountInCDRecommendedFragment();
                break;
            case Constants.TAG_RECOMMENDED_NOT_INTEREST_INSTITUTE:
                this.mSendCDRecommendationInstituteActionEvents(Constants.CDRecommendedInstituteType.NOT_INTERESTED);
                DataBaseHelper.getInstance(this).deleteAllExamSummary();
                hideProgressDialog = updateCDRecommendedFragment(tags);
                refreshCountOnNotInterested(tags[2]);
                updateCountInCDRecommendedFragment();
                break;
            case Constants.TAG_RECOMMENDED_DECIDE_LATER_INSTITUTE:
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
        }
        try {
            if(hideProgressDialog)
                hideProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            this.OnCDRecommendedLoadUndecidedInstitutes(Constants.BASE_URL + "personalize/shortlistedinstitutes/?action=3");
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

    public void decreaseRecommendedCount(){
        this.mRecommendedInstituteCount--;
    }
    public void decreaseUndecidedCount(){
        this.mUndecidedInstitutesCount--;
    }
    public void decreaseFeaturedCount(){
        this.mFeaturedInstituteCount--;
    }
   /* public void decreaseShortlistCount(){
        this.mShortListInstituteCount--;
    }*/

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
    public int getShortlistInstituteCount(){
        return mShortListInstituteCount;
    }

    private void updateCountInCDRecommendedFragment() {
        if (currentFragment instanceof CDRecommendedInstituteFragment) {
            ((CDRecommendedInstituteFragment) currentFragment).updateCount(this.mUndecidedInstitutesCount, this.mShortListInstituteCount, this.mFeaturedInstituteCount, this.mRecommendedInstituteCount);
        }
    }

    /**
     * This method is called when mDeviceProfile login with true sdk , facebook, phone number
     * or skip option then mDeviceProfile response is parse and mDeviceProfile is created and saved in
     * shared pref . and load mDeviceProfile status screens
     * and resp
     * @param response response
     */
    private void mUserCreatedSuccessfully(String response) {
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
            }

        } else {
            //Remove the BroadcastReciever for SMS
            ComponentName component = new ComponentName(this, OTPReceiver.class);

            int status = this.getPackageManager().getComponentEnabledSetting(component);
            if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                Log.d("MainActivity", "receiver is enabled");
                //Disable
                this.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }

            // parse mDeviceProfile response and  create a mDeviceProfile
            this.mParseProfileResponse(response);
            // set token in network utill
            mNetworkUtils.setToken(MainActivity.mProfile.getToken());
            // set mDeviceProfile ids with apps flyer
            this.setUserIdWithAllEvents();
            // load mDeviceProfile status screeen
            this.mLoadUserStatusScreen();

            if (!IS_USER_CREATED) {
                //Events
                Map<String, Object> eventValue = new HashMap<>();
                SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_USER_PROFILE_CREATED), eventValue, this);
                eventValue.put(getResourceString(R.string.ACTION_USER_PROFILE_CREATED), HomeFragment.class.getSimpleName());
            }
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean(getResourceString(R.string.USER_CREATED), true).apply();
            IS_USER_CREATED = true;

            HashMap<String, Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_USER_LOGIN, Constants.TAG_PHONE_NUMBER_LOGIN);
            SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_USER_LOGIN), eventValue, this);
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
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.INSTITUTE_RESOURCE_URI), this.mInstitute.getResource_uri());
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
        SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_CD_RECOMMENDED_INSTITUTE_ACTION), eventValue, this);
    }

    private void mUpdateExamDetail(String responseJson, boolean update) {
        try {
            ExamSummary examSummary = JSON.std.beanFrom(ExamSummary.class, responseJson);
            if (examSummary.getPreference_uri() != null) {

                String dbSummary = DataBaseHelper.getInstance(this).getExamSummary(Integer.parseInt(examSummary.getYearly_exam_id()));
                if (dbSummary == null) {
                    DataBaseHelper.getInstance(this).addExamSummary(Integer.parseInt(examSummary.getYearly_exam_id()), responseJson);
                } else if (update) {
                    DataBaseHelper.getInstance(this).updateExamSummary(Integer.parseInt(examSummary.getYearly_exam_id()), responseJson);
                }

                String u = JSON.std.asString(MainActivity.mProfile);
                this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).commit();
            }
            currentFragment.updateExamSummary(examSummary);
            if(update){
                if(currentFragment instanceof  TabFragment)
                    ((TabFragment) currentFragment).updateCollegeCountFromVolley(update);
            }
            if (MainActivity.type != null && !MainActivity.type.matches("") && MainActivity.resource_uri != null && !MainActivity.resource_uri.matches("")) {
                mHandleNotifications(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * This method is used to provide education level and
     * mDeviceProfile will select current education or he/she can skip,
     * if mDeviceProfile don't skip then ask for thestream .
     * specialization , degrees
     */
    private void mDisplayProfileBuildingFragment() {
        Fragment fragment = ProfileBuildingFragment.newInstance();
        this.mDisplayFragment(fragment, false,ProfileBuildingFragment.class.getSimpleName());
    }

    /**
     * This method is used to display step by step questions
     *
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
            DataBaseHelper.getInstance(this).deleteAllExamSummary();

            StepByStepResult sbsResult = JSON.std.beanFrom(StepByStepResult.class, response);

            this.requestForProfile(null);

            int count = 0;
            Map<String, String> filterKeywords = new HashMap<>();

            for (int n = 0; n < sbsResult.getTags().size(); n++)
                filterKeywords.put("tag_uris[" + (count++) + "]", sbsResult.getTags().get(n));

            this.mFilterKeywords = filterKeywords;
            this.mFilterCount = this.mFilterKeywords.size();

            //save preferences.
            getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getResourceString(R.string.PROFILE_SCREEN_TUTE), true).apply();
            this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).apply();

            MainActivity.mProfile.setStep_by_step_given(1);
            //move to profile
            this.onHomeItemSelected(Constants.WIDGET_INSTITUTES_SBS, Constants.BASE_URL + "personalize/institutes/",null);

//            this.mDisplayHomeFragment(null);
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
            eventValue.put(getResourceString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

            //Events
            SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);

        } else  if(voteType == Constants.DISLIKE_THING) {
                qnaQuestion.setUpvotes(qnaQuestion.getDownvotes() - 1);

                Map<String,Object> eventValue = new HashMap<>();
                eventValue.put(Constants.TAG_QUESTION_LIKE_DISLIKE, Constants.DISLIKE_THING);
                eventValue.put(qnaQuestion.getTitle(), Constants.DISLIKE_THING);
                eventValue.put(getResourceString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

                //Events
                SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);

        }else{
            if (qnaQuestion.getCurrent_user_vote_type() == Constants.LIKE_THING) {
                     qnaQuestion.setUpvotes(qnaQuestion.getUpvotes() - 1);
                    Map<String,Object> eventValue = new HashMap<>();
                    eventValue.put(getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_UPVOTED), Constants.NOT_INTERESTED_THING);
                    eventValue.put(qnaQuestion.getTitle(), Constants.NOT_INTERESTED_THING);
                    eventValue.put(getResourceString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

                    //Events
                    SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);

                } else {

                    qnaQuestion.setDownvotes(qnaQuestion.getDownvotes() - 1);
                    Map<String,Object> eventValue = new HashMap<>();
                    eventValue.put(getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_DOWNVOTED), Constants.NOT_INTERESTED_THING);
                    eventValue.put(qnaQuestion.getTitle(), Constants.NOT_INTERESTED_THING);
                    eventValue.put(getResourceString(R.string.QNA_QUESTION_RESOURCE_URI), qnaQuestion.getResource_uri());

                    //Events
                    SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_ENTITY), eventValue, this);
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
            eventValue.put(getResourceString(R.string.VOTE_TYPE), String.valueOf(Constants.NEITHER_LIKE_NOR_DISLIKE));
            eventValue.put(Constants.TAG_INSTITUTE_LIKE_DISLIKE, String.valueOf(Constants.NEITHER_LIKE_NOR_DISLIKE));

            //Events
            SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_LIKING_UNBIASED), eventValue, this);
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
                    SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_LIKED), eventValue, this);
                } else if (like == Constants.DISLIKE_THING) {
                    Map<String, Object> eventValue = new HashMap<>();
                    eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                    eventValue.put(getResourceString(R.string.VOTE_TYPE), String.valueOf(Constants.DISLIKE_THING));
                    eventValue.put(Constants.TAG_INSTITUTE_LIKE_DISLIKE, String.valueOf(Constants.DISLIKE_THING));

                    //Events
                    SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_DISLIKED), eventValue, this);
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
            eventValue.put(getResourceString(R.string.ACTION_INSTITUTE_SHORTLISTED), Constants.SHORTLISTED_NO);
            eventValue.put(getResourceString(R.string.INSTITUTE_RESOURCE_URI), institute.getResource_uri());

            //Events
            SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_SHORTLISTED_REMOVED), eventValue, this);
        } else {
            try {
                institute.setIs_shortlisted(Constants.SHORTLISTED_YES);
                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(Constants.TAG_RESOURCE_URI, institute.getResource_uri());
                eventValue.put(Constants.TAG_SHORTLIST_INSTITUTE, Constants.SHORTLISTED_YES);
                eventValue.put(getResourceString(R.string.ACTION_INSTITUTE_SHORTLISTED), Constants.SHORTLISTED_YES);
                eventValue.put(getResourceString(R.string.INSTITUTE_RESOURCE_URI), institute.getResource_uri());

                //Events
                SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_INSTITUTE_SHORTLISTED), eventValue, this);
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

    public static String GetPersonalizedMessage(String tag) {
        switch (tag) {
            case Constants.TAG_WISH_LIST_APPLIED_COURSE:
                return "Applying to institute";
            case Constants.TAG_LOAD_STREAM:
            case Constants.TAG_UPDATE_STREAM:
            case Constants.TAG_SUBMIT_EDIT_PSYCHOMETRIC_EXAM:
                return "Loading Streams";
            case Constants.TAG_EDUCATION_DETAILS_SUBMIT:
                return "Loading Exams";
            case Constants.TAG_EXAM_SUMMARY:
            case Constants.TAG_SUBMIT_EDITED_EXAMS_LIST:
            case Constants.WIDGET_SYLLABUS:
            case Constants.CARD_BUZZLIST_INSTITUTES:
                return "Loading";
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM:
                return "Loading Profile";
            case Constants.TAG_UPDATE_INSTITUTES:
                return "Updating Institutes";
            case Constants.TAG_UPDATE_USER_PROFILE:
                return "Updating Profile";
            case Constants.WIDGET_INSTITUTES:
            case Constants.WIDGET_INSTITUTES_SBS:
            case Constants.WIDGET_TRENDING_INSTITUTES:
            case Constants.WIDGET_SHORTLIST_INSTITUTES:
            case Constants.WIDGET_RECOMMENDED_INSTITUTES:
            case Constants.WIDGET_RECOMMENDED_INSTITUTES_FRON_PROFILE:
                return "Loading Institutes";
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
            /*case Constants.ACTION_MY_FB_COMMENT_SUBMITTED:
                return "Submitting Comment...";*/
            case Constants.ACTION_QNA_ANSWER_SUBMITTED:
                return "Submitting Answer";
            case Constants.ACTION_VOTE_QNA_QUESTION_ENTITY:
            case Constants.ACTION_VOTE_QNA_ANSWER_ENTITY:
                return "Processing Vote";
            case Constants.WIDGET_FORUMS:
                return "Loading Forums";
            case Constants.SEARCHED_INSTITUTES:
                return "Loading";
            case Constants.TAG_PSYCHOMETRIC_QUESTIONS:
                return "Loading";
            case Constants.WIDGET_TEST_CALENDAR:
                return "Loading your plan";
            case Constants.TAG_MY_ALERTS:
                return "Loading important events";
            case Constants.TAG_VERIFY_USER_PHONE:
                return "Verifying OTP";
            case Constants.TAG_USER_PHONE_ADDED:
                return "Requesting OTP";
            case Constants.PROFILE_IMAGE_UPLOADING:
                return "Uploading Image";
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
            case Constants.TAG_REQUEST_FOR_EXAMS:
            case Constants.REFRESH_CHATROOM:
            case Constants.TAG_REFRESH_PROFILE:
                return null;
            case "":
                return null;
            case Constants.TAG_SUBMIT_SBS_EXAM:
                return "Getting institutes for your preferences";
            default:
                return "Loading";
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
            Log.e(TAG, e.getMessage());
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
            Log.e(TAG, e.getMessage());
        }
    }

    private void mUpdateAppliedCourses(String response) {
        try {
            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment)
                ((InstituteDetailFragment) currentFragment).updateAppliedCourses(response);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onError(final String tag, String response, int responseCode, final String url,
                        final Map<String, String> params, final int method) {
        hideProgressDialog();
        hideErrorDialog();
        if(tag.equalsIgnoreCase("next_shortlist_institutes") && responseCode == 404){
            this.mMakeNetworkCall(Constants.TAG_LAST_SHORTLIST_INSTITUTES_WHILE_REMOVING, Constants.BASE_URL + "personalize/shortlistedinstitutes", null);
            return;
        }

        if(tag.equalsIgnoreCase(Constants.TAG_LOAD_BUZZLIST_INSTITUTE)){
            Utils.DisplayToast(getApplicationContext(),"LOADING FAILED");
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
                eventValue.put(getResourceString(R.string.APPLY_INSTITUTE_FROM_RECO), mInstitute.getResource_uri());
                SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_COURSE_APPLIED), eventValue, MainActivity.this);
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
        else if (listType == Constants.QNA_LIST_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_QNA_LIST, next, null);
        else if (listType == Constants.FORUM_LIST_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_FORUMS_LIST, next, null);
        else if (listType == Constants.INSTITUTE_SEARCH_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_INSTITUTE, next, null);
    }


    public void updateFilterList(String response) {
        mFolderList = new ArrayList<>();
        try {
            Folder.populateFolderList(JSON.std.getStreamingFactory().createParser(response), mFolderList);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }




    /**
     * This method returns
     * @return map
     */
    private Map mGetTheFilters() {
        Map<String, String> map = new HashMap<>();
        SharedPreferences sp = getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE);
        String value = sp.getString(Constants.SELECTED_FILTERS, null);

        if (value != null && !value.equals("") ){
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

            if (mSnackbar != null && !mSnackbar.isShown()) {
                mSnackbar.setText(getResources().getString(messageId));
                mSnackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideSnackBar() {

        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
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
            SendAppEvent(getResourceString(R.string.CATEGORY_NEWS), getResourceString(R.string.ACTION_NEWS_SELECTED), eventValue, this);
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
            SendAppEvent(getResourceString(R.string.CATEGORY_ARTICLE), getResourceString(R.string.ACTION_ARTICLE_SELECTED), eventValue, this);
        }

    }

    @Override
    public void onQuestionAsked(QnAQuestions question) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", question.getTitle());
        map.put("desc", question.getDesc());
        if (!(currentFragment instanceof QnAQuestionsListFragment))
            map.put("institute", "" + this.mInstituteList.get(currentInstitute).getResource_uri());

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

        Map<String, Object> eventValue = new HashMap<>();
        for (String key : this.mFilterKeywords.keySet()) {
            eventValue.put(Constants.SELECTED_FILTERS, this.mFilterKeywords.get(key));

            //Events
            SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_FILTER_APPLIED), eventValue, this);
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
        int amIConnectedToInternet = MainActivity.mNetworkUtils.getConnectivityStatus();
        if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(Constants.PROFILE_IMAGE_UPLOADING);
            this.mNetworkUtils.postMultiPartRequest(Constants.PROFILE_IMAGE_UPLOADING,Constants.BASE_URL+"upload-image/",fileByteArray);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }
    @Override
    public void onProfileImageUploadFailed() {

        Utils.DisplayToast(getApplicationContext(), "Upload failed! ");
        if(currentFragment instanceof  ProfileFragment){
            ((ProfileFragment) currentFragment).deleteTempImageFile();
            ((ProfileFragment) currentFragment).updateProfileImage();

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
        if (NetworkUtils.getConnectivityStatus() != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            MainActivity.mNetworkUtils.networkData(tag, url, params, method);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    private void mMakeNetworkCall(String tag, String url, Map<String, String> params) {
        if (NetworkUtils.getConnectivityStatus() != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            MainActivity.mNetworkUtils.networkData(tag, url, params);
        } else {
            displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    private void mMakeJsonObjectNetworkCall(String tag, String url, JSONObject params, int method) {
        if (NetworkUtils.getConnectivityStatus() != Constants.TYPE_NOT_CONNECTED) {
            this.showProgress(tag);
            MainActivity.mNetworkUtils.networkDataWithObjectParam(tag, url, params, method);
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
        this.mDisplayFragment(QnAQuestionDetailFragment.newInstance(qnaQuestion), true, getResourceString(R.string.TAG_FRAGMENT_QNA_QUESTION_DETAIL));
    }

    @Override
    public void onQnAAnswerVote(int voteType, int answerPosition, int questionPosition) {
        QnAAnswers qnaAnswer = mQnAQuestions.get(questionPosition).getAnswer_set().get(answerPosition);
        int userVotType = qnaAnswer.getCurrent_user_vote_type();
        String resourceURI = qnaAnswer.getUri();
        if (userVotType != Constants.NOT_INTERESTED_THING) {
            if (userVotType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType),
                        resourceURI + "downvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType),
                        resourceURI + "upvote/", null, Request.Method.POST);

        } else {
            if (voteType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType),
                        resourceURI + "upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType),
                        resourceURI + "downvote/", null, Request.Method.POST);
        }
    }

    @Override
    public void onQnAQuestionVoteFromDetail(int voteType, int position) {
        displayMessage(R.string.THANKS_FOR_VOTE);
        QnAQuestions qnaQuestion = this.mQnAQuestions.get(position);
        int userVotType = qnaQuestion.getCurrent_user_vote_type();
        String resourceURI = qnaQuestion.getUri();
        if (userVotType != Constants.NOT_INTERESTED_THING) {
            if (userVotType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "downvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "upvote/", null, Request.Method.POST);
        } else {
            if (voteType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "downvote/", null, Request.Method.POST);
        }
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

            QnAQuestions qnaQuestion = this.mQnAQuestions.get(questionIndex);
            if(qnaQuestion == null)
                return;

            Map<String, Object> eventValue = new HashMap<>();
            if (voteType == Constants.LIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(qnaQuestion.getResource_uri()));
                eventValue.put(getResourceString(R.string.VOTE_TYPE), Constants.LIKE_THING);
                eventValue.put(qnaQuestion.getResource_uri(), Constants.LIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.LIKE_THING);

                //Events
                SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_UPVOTED), eventValue, this);

            } else if (voteType == Constants.DISLIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(qnaQuestion.getResource_uri()));
                eventValue.put(getResourceString(R.string.VOTE_TYPE), Constants.DISLIKE_THING);
                eventValue.put(qnaQuestion.getResource_uri(), Constants.DISLIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.DISLIKE_THING);

                //Events
                SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_QUESTION_UPVOTED), eventValue, this);
            }


        } catch (Exception e) {
            Log.e("QnA question voting", e.getMessage());
        }
    }

    private void mQnAAnswerVoteUpdated(int questionIndex, int answerIndex, int voteType) {
        ((QnAQuestionDetailFragment) currentFragment).onVotingFeedback(questionIndex, answerIndex, voteType);
        try {
            Map<String, Object> eventValue = new HashMap<>();
            QnAAnswers answer = this.mQnAQuestions.get(questionIndex).getAnswer_set().get(answerIndex);

            if (voteType == Constants.LIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, answer.getResource_uri());
                eventValue.put(getResourceString(R.string.VOTE_TYPE), Constants.LIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.LIKE_THING);
                eventValue.put(answer.getResource_uri(), Constants.LIKE_THING);

                //Events
                SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_ANSWER_UPVOTED), eventValue, this);

            } else if (voteType == Constants.DISLIKE_THING) {
                eventValue.put(Constants.TAG_RESOURCE_URI, answer.getResource_uri());
                eventValue.put(getResourceString(R.string.VOTE_TYPE), Constants.DISLIKE_THING);
                eventValue.put(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.DISLIKE_THING);
                eventValue.put(answer.getResource_uri(), Constants.DISLIKE_THING);

                //Events
                SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_VOTE_QNA_ANSWER_DOWNVOTED), eventValue, this);
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
            qnaAnswer.setUri(ans.getString("uri"));
            qnaAnswer.setUser_image(ans.getString("user_image"));
            qnaAnswer.setUser_id(ans.getString("user_id"));
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
            SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_QNA_ANSWER_SUBMITTED), eventValue, this);
        }
    }

    @Override
    public void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position, int commentsCount) {
        this.mMakeNetworkCall(Constants.TAG_LOAD_MY_FB + "#" + String.valueOf(position) + "#" + String.valueOf(commentsCount), myFutureBuddiesEnumeration.getResource_uri(), null, Request.Method.GET);

        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.TAG_RESOURCE_URI, myFutureBuddiesEnumeration.getResource_uri());

        //Events
        SendAppEvent(getResourceString(R.string.CATEGORY_MY_FB), getResourceString(R.string.ACTION_MY_FB_SELECTED), eventValue, this);
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

    @Override
    public void onScrolledToTop(String next){
        this.mMakeNetworkCall(Constants.TAG_LOAD_MORE_FB_COMMENT,next, null, Request.Method.GET);
    }

    private void mLoadPreviousComments(String response){
        try {
            MyFutureBuddy mFb = this.mParseAndPopulateMyFB(response, 0);
            if(currentFragment instanceof MyFutureBuddiesFragment){
                ((MyFutureBuddiesFragment) currentFragment).mDisplayPreviousComments(mFb);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
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

            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(Constants.TAG_RESOURCE_URI, this.mFbEnumeration.get(fbIndex).getResource_uri());
            eventValue.put(getResourceString(R.string.MY_FB_URI), this.mFbEnumeration.get(fbIndex).getResource_uri());

            //Events
            SendAppEvent(getResourceString(R.string.CATEGORY_MY_FB), getResourceString(R.string.ACTION_MY_FB_COMMENT_SUBMITTED), eventValue, this);

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
            qnaQuestion.setUri(qns.getString("uri"));
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
            SendAppEvent(getResourceString(R.string.CATEGORY_QNA), getResourceString(R.string.ACTION_QNA_QUESTION_ASKED), eventValue, this);

            if(this.mQnAQuestions == null)
                this.mQnAQuestions = new ArrayList<>();

            this.mQnAQuestions.add(0, qnaQuestion);
            if (currentFragment instanceof  QnAQuestionsListFragment){
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
                qnaQuestion.setUri(qns.getString("uri"));
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
                    qnaAnswer.setUri(ans.getString("uri"));
                    qnaAnswer.setUser_image(ans.getString("user_image"));
                    qnaAnswer.setUser_id(ans.getString("user_id"));
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
     * This method is called when mDeviceProfile change anything in Institute List
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
     * This method is called when mDeviceProfile press device back button
     * Do these task
     * 1- If navigation drawer is open then close it
     * 2- If last fragment clear back stack FragmentManager
     * 3- If mDeviceProfile wants to close app then show a message to press device back button again
     */
    @Override
    public void onBackPressed() {
        // if navigation drawer layout is open then on back press
        // it will close
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if(currentBottomItem != null){
            translateAnimation(null,currentBottomItem);
        }

        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (currentFragment != null) {
            if (currentFragment instanceof SyllabusSubjectsListFragment) {
                ((SyllabusSubjectsListFragment) currentFragment).submitSyllabusStatus();
            } else if (currentFragment instanceof OTPVerificationFragment && MainActivity.mProfile.getIs_verified() == 0) {
                setupOtpRequest(false);
            }else if (currentFragment instanceof WebViewFragment){
                boolean canGoBack = ((WebViewFragment) currentFragment).canGoBack();
                if(canGoBack)
                    return;
            }else if (currentFragment instanceof PostAnonymousLoginFragment){
                mShowAppBarLayout();
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
     * This method is called when mDeviceProfile skip
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
        this.mMakeNetworkCall(TAG, Constants.BASE_URL + "auth/new-common-login/", params);

        //Events
        HashMap<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.TAG_USER_LOGIN, TAG);
        SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_USER_LOGIN), eventValue, this);
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
            params.put(MainActivity.getResourceString(R.string.USER_PHONE), phone);
        }

        if( trueProfile.email == null) {
            trueProfile.email = MainActivity.mProfile.getEmail();

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
     * when mDeviceProfile wants to login with different account
     * or  have not successfully login
     */
   /* private void disconnectFromFacebook() {
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
    }*/


    /**
     *  This method is called when mDeviceProfile's exams are edited
     * @param responseJson user profile json
     */
    private void onUserExamsEdited(String responseJson) {
        try {
            DataBaseHelper.getInstance(this).deleteAllExamSummary();
            mParseProfileResponse(responseJson);
            onBackPressed();

            // if mDeviceProfile has removed all exams and when go back to tab
            // screen has and make first tab selected because third tab
            //  gives exam preparation details
            if(currentFragment instanceof  TabFragment){
                ((TabFragment) currentFragment).setSelectedTab(3);
                ((TabFragment) currentFragment).updateExamsList(mProfile.getYearly_exams());
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     *  If mDeviceProfile login with any social site like facebook and stream and level has conflict
     *  it shows a dialog to choose stream and level.
     * @param tag Tag
     * @param URL Api Url according to login
     * @param jsonObj response json
     * @param params request data send to api
     */
    public void showDialogForStreamLevel(final String tag, final String URL, JSONObject jsonObj, final Map<String, String> params) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_stream_conflict_dailog);
        dialog.setTitle("Select Your Stream and Level");
        RadioGroup streamRadioGroup = (RadioGroup) dialog.findViewById(R.id.stream_radio_group);
        RadioGroup levelRadioGroup = (RadioGroup) dialog.findViewById(R.id.level_radio_group);
        try {
            final String stream_id = jsonObj.getString(getResourceString(R.string.USER_STREAM));
            final String level_id = jsonObj.getString(getResourceString(R.string.USER_LEVEL));
            String streamName = jsonObj.getString(getResourceString(R.string.USER_STREAM_NAME));
            String levelName = jsonObj.getString(getResourceString(R.string.USER_LEVEL_NAME));
            if (mProfile.getPreferred_stream_id()== Integer.parseInt(stream_id))
                streamRadioGroup.setVisibility(View.GONE);

            ((RadioButton) dialog.findViewById(R.id.firstStream)).setText(streamName);
            ((RadioButton) dialog.findViewById(R.id.secondStream)).setText(mProfile.getPreferred_stream_short_name());

            ((RadioButton) dialog.findViewById(R.id.firstLevel)).setText(levelName);
            ((RadioButton) dialog.findViewById(R.id.secondLevel)).setText(mProfile.getPreferred_level_name());

            // if button is clicked, close the custom dialog
//            dialog.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                    if (((RadioButton) dialog.findViewById(R.id.firstStream)).isChecked())
//                        params.put(getResourceString(R.string.USER_STREAM), stream_id);
//                    if (((RadioButton) dialog.findViewById(R.id.secondStream)).isChecked())
//                        params.put(getResourceString(R.string.USER_STREAM), mProfile.getPreferred_stream_id());
//                    if (((RadioButton) dialog.findViewById(R.id.firstLevel)).isChecked())
//                        params.put(getResourceString(R.string.USER_LEVEL), level_id);
//                    if (((RadioButton) dialog.findViewById(R.id.secondLevel)).isChecked())
//                        params.put(getResourceString(R.string.USER_LEVEL), mDeviceProfile.getLevel());
//                    mMakeNetworkCall(tag, URL, params);
//                }
//
//
//            });
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Folder> getFilterList() {
        if (!this.mFilters.equals("")) {
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
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        if (fragment == null)
            this.mDisplayFragment(HomeFragment.newInstance(), false, HomeFragment.class.toString());
        else {
            this.mDisplayFragment(fragment, false, HomeFragment.class.getSimpleName());
        }
        IS_HOME_LOADED = true;
        this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean(getResourceString(R.string.USER_HOME_LOADED), true).apply();
    }


    @Override
    public void onExamTabSelected(ProfileExam examDetailObj) {
        if (examDetailObj == null) return;
        int id = examDetailObj.getId();
        String dbSummary = DataBaseHelper.getInstance(this).getExamSummary(id);
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

    @Override
    public void onChatRoomSwipedDown(String requestType, String url){
        Map<String, String> params = new HashMap<>();
        this.mMakeNetworkCall(requestType, url, params);
    }

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
        }
        if (requestType.equals(Constants.WIDGET_SYLLABUS)) {
            this.mMakeNetworkCall(requestType, url, null);
            return;
        }
        if (requestType.equals(Constants.WIDGET_FORUMS) || requestType.equals(Constants.TAG_LOAD_QNA_QUESTIONS)) {
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
            String results = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getString("psychometric_report", null);
            if(results != null) {
                List<Stream> streams = JSON.std.listOfFrom(Stream.class, results);
                mClearBackStack();
                this.mDisplayFragment(PsychometricStreamFragment.newInstance(new ArrayList(streams)), true, Constants.TAG_FRAGMENT_STREAMS);
            }
        } catch (Exception e){
            Utils.DisplayToast(getApplicationContext(),"Cannot load psychometric results.");
        }
    }

    @Override
    public void onHomeStepByStep() {
        this.startStepByStep();
    }




    @Override
    public void onHomePsychometricReport(){
        try {
            String results = getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).getString("psychometric_report", null);
            if(results != null) {
                List<Stream> streams = JSON.std.listOfFrom(Stream.class, results);
                mClearBackStack();
                this.mDisplayFragment(PsychometricStreamFragment.newInstance((ArrayList<Stream>)(streams)), true, Constants.TAG_FRAGMENT_STREAMS);
            }
        } catch (Exception e){
            Utils.DisplayToast(getApplicationContext(),"Cannot load psychometric results.");
        }
    }

    @Override
    public void onPsychometricTestSelected()
    {
        this.mMakeNetworkCall(Constants.TAG_PSYCHOMETRIC_QUESTIONS, Constants.BASE_URL + "psychometric/", null);
        //Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.CHOSEN_ACTION_WHEN_NOT_PREPARING), PsychometricTestQuestion.class.getSimpleName());
        SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_WHEN_NOT_PREPARING), eventValue, this);
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
        this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM, Constants.BASE_URL + "star-psychometric/", params, 1);
    }

    private void mDisplayStreamsSelection(String response) {
        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            String results = JSON.std.asString(map.get("results"));
            getSharedPreferences(getResourceString(R.string.PREFS), Context.MODE_PRIVATE).edit().putString("psychometric_report", results).apply();
            List<Stream> streams = JSON.std.listOfFrom(Stream.class, results);
            this.mClearBackStack();
            this.mDisplayFragment(PsychometricStreamFragment.newInstance(new ArrayList(streams)), true, Constants.TAG_FRAGMENT_STREAMS);
            mDisplayOtpVerificationFragment();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onStreamSelected(int streamId) {
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        HashMap<String, String> params = new HashMap<>();
        params.put(getResourceString(R.string.preferred_stream_id), ""+streamId);
        params.put(getResourceString(R.string.USER_DEVICE_ID), deviceId);

        this.requestForProfile(params);
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
            MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP, Constants.BASE_URL + "step-by-step/" + urlPart + "ug-ques-" + startWithQuestionNumber + "/", null);
        }else if(currentLevel == ProfileMacro.LEVEL_POST_GRADUATE || currentLevel == ProfileMacro.LEVEL_UNDER_GRADUATE ){
            MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP, Constants.BASE_URL + "step-by-step/" + urlPart + "pg-ques-" + startWithQuestionNumber + "/", null);
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Currently Studying at?")
                    .setSingleChoiceItems(StepByStepQuestion.CurrentLevels.getValues(), -1,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String urlPart = "";
                                    int streamID = MainActivity.mProfile.getCurrent_stream_id();

                                    String startWithQuestionNumber = "one";

                                    if (streamID > 0)
                                    {
                                        urlPart = streamID + "/";
                                        startWithQuestionNumber = "two";
                                    }

                                    switch (which) {
                                        case 0:
                                            StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.IN_SCHOOL);
                                            MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP, Constants.BASE_URL + "step-by-step/" + urlPart + "ug-ques-" + startWithQuestionNumber + "/", null);
                                            break;
                                        case 1:
                                            StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.GRADUATE_COLLEGE);
                                            MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP, Constants.BASE_URL + "step-by-step/" + urlPart + "pg-ques-" + startWithQuestionNumber + "/", null);
                                            break;
                                        case 2:
                                            StepByStepQuestion.setCurrentLevel(StepByStepQuestion.CurrentLevels.POSTGRADUATE_COLLEGE);
                                            MainActivity.this.mMakeNetworkCall(Constants.TAG_LOAD_STEP_BY_STEP , Constants.BASE_URL + "step-by-step/" + urlPart + "pg-ques-" + startWithQuestionNumber + "/", null);
                                            break;
                                        default:
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            })
                    .show();
        }
        //Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.CHOSEN_ACTION_WHEN_NOT_PREPARING), StepByStepFragment.class.getSimpleName());
        SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_WHEN_NOT_PREPARING), eventValue, this);
    }

    @Override
    public void onIknowWhatIWant() {
        this.mMakeNetworkCall(Constants.TAG_LOAD_STREAM, Constants.BASE_URL + "streams/", null);

        //Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getResourceString(R.string.CHOSEN_ACTION_WHEN_NOT_PREPARING), "I_KNOW_WHAT_I_WANT");
        SendAppEvent(getResourceString(R.string.CATEGORY_PREFERENCE), getResourceString(R.string.ACTION_WHEN_NOT_PREPARING), eventValue, this);
    }

    @Override
    public void onSkipSelectedInProfileBuilding() {
        this.mDisplayFragment(NotPreparingFragment.newInstance(), true, NotPreparingFragment.class.toString());
    }

    /**
     * This method is used to get exams list based on preferred level
     * and preferred stream if preferred stream is not available then we send
     * current stream and
     */
    @Override
    public void onRequestForUserExams() {
        // request for yearly exam based on preferred level
        StringBuffer  examUrl = new StringBuffer(Constants.BASE_URL);
        examUrl.append("stream-yearly-exams/?preferred_level="+mProfile.getPreferred_level());

        int userPreferredStreamId = mProfile.getPreferred_stream_id();
        if(userPreferredStreamId > 0){
            examUrl.append("&preferred_stream="+userPreferredStreamId);
        }else{
            examUrl.append("&current_stream="+mProfile.getCurrent_stream_id());
        }

        this.mMakeNetworkCall(Constants.TAG_REQUEST_FOR_EXAMS, examUrl.toString(), null, Request.Method.GET);
    }


    @Override
    public void onRequestForLevelStreams(int levelId, int levelType) {

    }


    private void mOnUserExamsSubmitted(String responseJson) {
        // parse mDeviceProfile response
        this.mParseProfileResponse(responseJson);

        // update mDeviceProfile exam on profile building fragment
        if(currentFragment instanceof ProfileBuildingFragment)
            ((ProfileBuildingFragment)currentFragment).onExamSubmittedSuccessfully();
    }

    @Override
    public void OnTakeMeToRecommended() {
        mClearBackStack();
        this.mMakeNetworkCall(Constants.TAKE_ME_TO_RECOMMENDED, Constants.BASE_URL + "personalize/recommended-institutes/",null);

    }

    @Override
    public void OnTakeMeToDashBoard() {
        this.mClearBackStack();
        mDisplayHomeFragment();
    }

    @Override
    public void OnTakeMeToProfile() {
        mClearBackStack();
        mDisplayProfileFragment(MainActivity.mProfile, false);
        getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.USER_HOME_LOADED), true).apply();
        if(currentFragment instanceof ProfileBuildingFragment)
            ((ProfileBuildingFragment)currentFragment).hideNavigationIcon();
        mShowAppBarLayout();
        isFromNotification = true;

    }

    /**
     * This method is used to handle response having exams
     * @param responseJson server response which contains exams list json
     */
    private void onResponseUserExamsList(String responseJson) {
        try {
            List<Exam> mExamList = JSON.std.listOfFrom(Exam.class, extractResults(responseJson));
            if(currentFragment instanceof ProfileBuildingFragment) {

                ((ProfileBuildingFragment) currentFragment).updateExamsList((ArrayList<Exam>) mExamList);
            }else{
               // mHomeTabContainer.setVisibility(View.VISIBLE);
                if(mExamList.size() <= 0){
                    Utils.DisplayToast(getApplicationContext(),"No Exams Found for your preferences");
                    return;
                }
                this.mDisplayFragment(ExamsFragment.newInstance(new ArrayList<>(mExamList)), true, ExamsFragment.class.toString());
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
    public void onSBSTestFinish(String url, JSONObject answerObject) {
        this.mMakeJsonObjectNetworkCall(Constants.TAG_SUBMIT_SBS_EXAM, url, answerObject, Request.Method.POST);
    }

    @Override
    public void onItemSelected(int position) {
        if(this.myAlertsList!=null && !this.myAlertsList.isEmpty()){
            this.mDisplayFragment(UserAlertsParentFragment.newInstance(position,new ArrayList(this.myAlertsList)),true,UserAlertsParentFragment.class.toString());
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
        if (MainActivity.mProfile.getIs_verified() != ProfileMacro.NUMBER_VERIFIED && setupOtpRequest(true)) {
            Constants.IS_CAF_LOADED = true;
            mDisplayOtpVerificationFragment();
        }
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
        this.mMakeNetworkCall(Constants.CARD_BUZZLIST_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/?action=2",null);
    }
    @Override
    public void OnAppliedInstitute(Institute institute, boolean islastcard, String cardCategory) {
        this.mInstitute = institute;
        Map<String, Object> eventValue = new HashMap<>();
        DataBaseHelper.getInstance(this).deleteAllExamSummary();
        if(institute.getGroups_exists() == 1)
        {
            eventValue.put(MainActivity.getResourceString(R.string.APPLY_INSTITUTE), Constants.CDInstituteType.PARTNER.toString());

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
            eventValue.put(MainActivity.getResourceString(R.string.APPLY_INSTITUTE), Constants.CDInstituteType.NON_PARTNER.toString());
            requestForApplyInstitute(Constants.TAG_WISH_LIST_APPLIED_COURSE,new HashMap<String, String>(),Constants.TAG_RECOMMENDED_APPLIED_SHORTLIST_INSTITUTE + "#" + islastcard + "#" + cardCategory + "#null");
        }

        if(this.mInstitute != null) {
            eventValue.put(getResourceString(R.string.APPLY_INSTITUTE_FROM_WISHLIST), this.mInstitute.getResource_uri());
            SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_COURSE_APPLIED), eventValue, MainActivity.this);
        }
//        Log.e("DEBUG_APPLIED",institute.getName());
    }

    @Override
    public void onClickBuzzList() {
        Map<String, String> params = this.mGetTheFilters();

        if(this.mExamTag != null && !this.mExamTag.isEmpty())
            params.put("tag_uris[" + (params.size()) + "]", this.mExamTag);

        this.mMakeNetworkCall(Constants.CARD_BUZZLIST_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/?action=2", params);
    }

    @Override
    public void onClickWishList() {
        this.mMakeNetworkCall(Constants.CARD_SHORTLIST_INSTITUTES, Constants.BASE_URL + "personalize/shortlistedinstitutes", null);
    }

    @Override
    public void onNextBuzzList() {
        if(next!=null)
            this.mMakeNetworkCall(Constants.CARD_BUZZLIST_INSTITUTES+"#next", next, null);
    }

    @Override
    public void onClickRecommendedList() {
        onHomeItemSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/", mExamTag);
    }

    @Override
    public void OnCDRecommendedLoadNext() {
        Map<String , String> params = this.mGetTheFilters();
        if(this.mExamTag != null && !this.mExamTag.isEmpty())
            params.put("tag_uris[" + (params.size()) + "]", this.mExamTag);
        this.mMakeNetworkCall(Constants.WIDGET_RECOMMENDED_INSTITUTES + "#" + "next", Constants.BASE_URL + "personalize/recommended-institutes/", params);
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
        params.put(getResourceString(R.string.APPLY_COURSE), "" + instituteCourse.getId());

        requestForApplyInstitute( Constants.TAG_APPLIED_COURSE,params, "");

        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(Constants.TAG_RESOURCE_URI, String.valueOf(instituteCourse.getId()));
        eventValue.put(getResourceString(R.string.APPLY_COURSE), instituteCourse.getName());
        if (mInstitute != null)
            eventValue.put(getResourceString(R.string.APPLY_INSTITUTE), mInstitute.getResource_uri());
        eventValue.put(getResourceString(R.string.APPLY_COURSE_ID), String.valueOf(instituteCourse.getId()));

        //Events
        SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_COURSE_APPLIED), eventValue, this);

    }

    private void requestForApplyInstitute(final String TAG , final  HashMap params, final String ShortlistedTag) {

        if(mProfile == null)
            return;

        if (mInstitute != null) {
            params.put("institute", String.valueOf(mInstitute.getId()));
        }

        String name = mProfile.getName();
        String email = mProfile.getEmail();
        String phone = mProfile.getPhone_no();

        // get mDeviceProfile name for apply course
        if (name == null || name.isEmpty() || name.equalsIgnoreCase(getResourceString(R.string.ANONYMOUS_USER)))
        {
            name = getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).getString(mResources.getString(R.string.user_apply_course_name), "");
        }
        // get mDeviceProfile email for apply course
        if (email == null || email.isEmpty() || email.contains("@anonymouscollegedekho.com")){
            email = getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).getString(mResources.getString(R.string.user_apply_course_email),"");

            if(email.isEmpty())
                email = Utils.getDeviceEmail(getApplicationContext());
        }
        // get mDeviceProfile email for apply course
        if (phone == null || phone.isEmpty()){
            if(mDeviceProfile != null && mDeviceProfile.getPrimaryPhone() != null && !mDeviceProfile.getPrimaryPhone().isEmpty())
                phone = mDeviceProfile.getPrimaryPhone();
            else
                phone = getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).getString(mResources.getString(R.string.user_apply_course_phone),"");
        }

        if ( name.isEmpty() || phone.length() < 10 || email.isEmpty()) {
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

                    getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(mResources.getString(R.string.user_apply_course_name),name).apply();
                    getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(mResources.getString(R.string.user_apply_course_email),email).apply();
                    getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(mResources.getString(R.string.user_apply_course_phone),phone).apply();
                    apply.dismiss();
                    params.put(getResourceString(R.string.USER_NAME), name);
                    params.put(getResourceString(R.string.USER_EMAIL), email);
                    params.put(getResourceString(R.string.USER_PHONE), phone);
                    params.put(getResourceString(R.string.APPLY_YEAR), mYear);
                    mMakeNetworkCall( TAG, Constants.BASE_URL + "lms/", params, Request.Method.POST);


                    // update mDeviceProfile profile  also with apply form data
                    final HashMap<String, String> profileParams = new HashMap<>();

                    if(mProfile.getName().equalsIgnoreCase(getResourceString(R.string.ANONYMOUS_USER)))
                        profileParams.put(getResourceString(R.string.USER_NAME), name);

                    if(mProfile.getIs_anony() == ProfileMacro.ANONYMOUS_USER)
                        profileParams.put(getResourceString(R.string.USER_EMAIL), email);

                    if(mProfile.getPhone_no() == null ||  mProfile.getPhone_no().length() < 10)
                        profileParams.put(getResourceString(R.string.USER_PHONE), phone);

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
            params.put(getResourceString(R.string.USER_NAME), name);
            params.put(getResourceString(R.string.USER_EMAIL), email);
            params.put(getResourceString(R.string.USER_PHONE), phone);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            params.put(getResourceString(R.string.APPLY_YEAR), "" + year);
            this.mMakeNetworkCall( TAG,Constants.BASE_URL + "lms/", params, Request.Method.POST);

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
            this.mMakeNetworkCall(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "institutecourses/" + "?institute=" + mInstitute.getId(), null);
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
                eventValue.put(getResourceString(R.string.APPLY_INSTITUTE_FROM_WISHLIST), mInstitute.getResource_uri());
                SendAppEvent(getResourceString(R.string.CATEGORY_INSTITUTES), getResourceString(R.string.ACTION_COURSE_APPLIED), eventValue, MainActivity.this);
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
            if (NetworkUtils.getConnectivityStatus() != Constants.TYPE_NOT_CONNECTED) {
                mNetworkUtils.simpleGetData(Constants.TAG_UPDATE_VIDEO_TITLE, url);
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
    public void onSubmitPhoneNumber(String phoneNumber) {
        HashMap<String, String> params = new HashMap<>();
        params.put(getResourceString(R.string.USER_PHONE), phoneNumber);
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
    public void requestForProfile(HashMap<String, String> params, int method) {

    }


    @Override
    public void onOtpReceived(String mobileNumber, String otp) {
        HashMap<String, String> params = new HashMap<>();
        params.put(getResourceString(R.string.USER_PHONE), mobileNumber);
        params.put(getResourceString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_PHONE_NUMBER);
        params.put(Constants.OTP_CODE, otp);
        this.onUserCommonLogin(params, Constants.TAG_PHONE_NUMBER_LOGIN);
    }

    @Override
    public void onResendOTP(String mobileNumber) {
        HashMap<String, String> params = new HashMap<>();
        params.put(getResourceString(R.string.USER_PHONE), mobileNumber);
        this.mMakeNetworkCall(Constants.TAG_RESEND_OTP, Constants.BASE_URL + "send-otp/", params);
    }


    private void onOTPVerified(String response) {

        try {
            JSONObject responseObject = new JSONObject(response);
            if (responseObject.optBoolean("verified")) {
                displayMessage(R.string.otp_verified);
                MainActivity.mProfile.setIs_verified(1);
                String u = JSON.std.asString(MainActivity.mProfile);
                this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.KEY_USER), u).apply();

                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put(getResourceString(R.string.ACTION_OTP_VERIFIED), "Success");

                //Events
                SendAppEvent(Constants.OTP_VERIFICATION, getResourceString(R.string.ACTION_OTP_VERIFIED), eventValue, this);

                if(currentFragment instanceof  OTPVerificationFragment)
                    onBackPressed();
            } else {
                if (currentFragment != null && currentFragment instanceof OTPVerificationFragment) {
                    Map<String, Object> eventValue = new HashMap<>();
                    eventValue.put(getResourceString(R.string.ACTION_OTP_VERIFIED), "Failed");

                    //Events
                    SendAppEvent(Constants.OTP_VERIFICATION, getResourceString(R.string.ACTION_OTP_VERIFIED), eventValue, this);

                    ((OTPVerificationFragment) currentFragment).onInvalidOtp();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void onMobileNumberSubmitted() {
        if (currentFragment instanceof OTPVerificationFragment) {
            ((OTPVerificationFragment) currentFragment).displayOTPLayout();
        }
    }

    private void mDisplayOtpVerificationFragment() {
        if (MainActivity.mProfile.getIs_verified() != ProfileMacro.NUMBER_VERIFIED && setupOtpRequest(true)) {
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
        }else {
            if (!canRequest) {
                this.getSharedPreferences(getResourceString(R.string.PREFS), MODE_PRIVATE).edit().putString(getResourceString(R.string.CAN_ASK_OTP_TODAY), today).apply();
            }
        }
        return true;
    }


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
                // Retrieve data rows for the device mDeviceProfile's 'profile' contact.
                Uri.withAppendedPath(
                        ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
                DeviceProfile.ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE + " = ? OR " + ContactsContract.Contacts.Data.MIMETYPE + " = ?",
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the mDeviceProfile hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }




    private void onNameUpdatedResponse(String response, String msg) {
        mParseProfileResponse(response);
        if (currentFragment instanceof MyFutureBuddiesFragment) {
            ((MyFutureBuddiesFragment) currentFragment).sendChatRequest(msg);
        }
    }

    private void onTestCalendarResponse(String response) {

        try {
            this.chaptersList = JSON.std.listOfFrom(Chapters.class, response);

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(CalendarParentFragment.class.getSimpleName() );
            if(fragment == null)
                this.mDisplayFragment(CalendarParentFragment.newInstance(new ArrayList<>(this.chaptersList)), !isFromNotification, CalendarParentFragment.class.getSimpleName());
            else {
                if (currentFragment instanceof CalendarParentFragment)
                    ((CalendarParentFragment) currentFragment).updateCalander(new ArrayList<>(this.chaptersList));

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

    public View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int postion = -1;
            try {
                postion = Integer.parseInt((String) view.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mUpdateTabMenuItem(postion);
            onTabMenuSelected(postion);
        }
    };

    public void mUpdateTabMenuItem(int tabPosition) {
        mCollegeTab.setSelected(false);
        mConnectTab.setSelected(false);
        mPrepareTab.setSelected(false);
        mReadTab.setSelected(false);

        mHomeTabContainer.setVisibility(View.VISIBLE);

        View viewToMoveUp = null;
        View viewToMoveDown = null;

        if (tabPosition == 1) {
            mCollegeTab.setSelected(true);
            if(currentBottomItem != mCollegeTab){
                viewToMoveUp = mCollegeTab;
            }
            if(currentBottomItem != null && currentBottomItem != mCollegeTab){
                viewToMoveDown = currentBottomItem;
            }
            currentBottomItem = mCollegeTab;
        }else if (tabPosition == 2) {
            mConnectTab.setSelected(true);
            if(currentBottomItem != mConnectTab) {
                viewToMoveUp = mConnectTab;
            }
            if(currentBottomItem != null && currentBottomItem != mConnectTab){
                viewToMoveDown = currentBottomItem;
            }
            currentBottomItem = mConnectTab;
        }else if (tabPosition == 3) {
            mPrepareTab.setSelected(true);
            if(currentBottomItem != mPrepareTab) {
                viewToMoveUp = mPrepareTab;
            }
            if(currentBottomItem != null && currentBottomItem != mPrepareTab){
                viewToMoveDown = currentBottomItem;
            }
            currentBottomItem = mPrepareTab;
        }else if (tabPosition == 4) {
            mReadTab.setSelected(true);
            if(currentBottomItem != mReadTab) {
                viewToMoveUp = mReadTab;
            }
            if(currentBottomItem != null && currentBottomItem != mReadTab){
                viewToMoveDown = currentBottomItem;
            }
            currentBottomItem = mReadTab;
        }else if(tabPosition == -2){
            viewToMoveDown = currentBottomItem;
            currentBottomItem = null;
        }else {
            mHomeTabContainer.setVisibility(View.GONE);
            currentBottomItem = null;
        }
        if(tabPosition > 0){
            mNavigationView.getMenu().getItem(tabPosition-1).setChecked(true);
        } else {
            int size = mNavigationView.getMenu().size();
            for(int i = 0 ; i < size ; i++)
                mNavigationView.getMenu().getItem(i).setChecked(false);
        }
        translateAnimation(viewToMoveUp,viewToMoveDown);
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

    private void onTabMenuSelected(int tabPosition) {

        //TODO::  remove this when future buddies tab are present
        if(mProfile != null) {
            ArrayList<ProfileExam> userExamList = mProfile.getYearly_exams();

            if (tabPosition == 3 && userExamList != null &&
                    userExamList.size() <= 0) {
                fromTabFragment = true;
                onRequestForUserExams();
                mHomeTabContainer.setVisibility(View.GONE);
                return;
            }
        }

        if (currentFragment instanceof TabFragment) {

            ((TabFragment) currentFragment).updateTabFragment(tabPosition);
        } else{
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TabFragment.class.getSimpleName());
            if (fragment == null) {
                ArrayList<ProfileExam> list = new ArrayList<>();
                if(mProfile != null && mProfile.getYearly_exams() != null) {
                    list.addAll(mProfile.getYearly_exams());
                }

                this.mDisplayFragment(TabFragment.newInstance(tabPosition, list), true, TabFragment.class.getSimpleName());
            }else {
                this.mDisplayFragment(fragment, false, TabFragment.class.getSimpleName());
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
                this.mDisplayFragment(InstituteDetailFragment.newInstance(this.mInstitute, Constants.CDRecommendedInstituteType.RECOMMENDED), false, Constants.TAG_FRAGMENT_INSTITUTE);

                //this.mMakeNetworkCall(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "institutecourses/" + "?institute=" + id, null);
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
                qnaAnswer.setUri(ans.getString("uri"));
                qnaAnswer.setUser_image(ans.getString("user_image"));
                qnaAnswer.setUser_id(ans.getString("user_id"));
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
            this.mDisplayFragment(QnAQuestionDetailFragment.newInstance(qnaQuestion), false, getResourceString(R.string.TAG_FRAGMENT_QNA_QUESTION_DETAIL));
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
            this.mExamTag = "";
            this.mMakeNetworkCall(Constants.SEARCH_INSTITUTES, Constants.BASE_URL + "colleges/search=" + searchString, null);
        } else if (currentFragment != null && currentFragment instanceof QnAQuestionsListFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_QNA, Constants.BASE_URL + "questions/search=" + searchString + "/", null);
        } else if (currentFragment != null && currentFragment instanceof NewsFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_NEWS, Constants.BASE_URL + "news/search=" + searchString + "/", null);
        } else if (currentFragment != null && currentFragment instanceof ArticleFragment) {

            this.mMakeNetworkCall(Constants.SEARCH_ARTICLES, Constants.BASE_URL + "articles/search=" + searchString + "/", null);
        } else if ((currentFragment != null && currentFragment instanceof HomeFragment) || (currentFragment != null && currentFragment instanceof TabFragment)) {
            this.mExamTag = "";
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

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
                                mSearchView.onActionViewExpanded();
                                mSearchView.setQuery(searchString, false);
                                mSearchView.clearFocus();
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
                                mSearchView.onActionViewExpanded();
                                mSearchView.setQuery(searchString, false);
                                mSearchView.clearFocus();
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
        Bundle bundle=intent.getExtras();
        if(bundle!=null) {
            if (bundle.containsKey("screen") && bundle.containsKey("resource_uri")) {
                MainActivity.type = bundle.getString("screen");
                MainActivity.resource_uri = bundle.getString("resource_uri");
                mHandleNotifications(false);
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
        if(currentFragment instanceof SplashFragment){
            return super.dispatchPopulateAccessibilityEvent(event);
        }else {
            return true;
        }
    }

    public String getOtherAppSharedMessage(){
        return  this.mOtherAppSharedMessage;
    }

    public void setOtherAppSharedMessage(String message){
        this.mOtherAppSharedMessage = message;
    }
}