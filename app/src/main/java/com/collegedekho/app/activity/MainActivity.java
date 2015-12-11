package com.collegedekho.app.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.DebugLogQueue;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.MyFutureBuddiesEnumeration;
import com.collegedekho.app.entities.MyFutureBuddy;
import com.collegedekho.app.entities.MyFutureBuddyComment;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.entities.UserEducation;
import com.collegedekho.app.entities.Widget;
import com.collegedekho.app.fragment.ArticleDetailFragment;
import com.collegedekho.app.fragment.ArticleFragment;
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.fragment.MyAlertFragment;
import com.collegedekho.app.fragment.NotPreparingFragment;
import com.collegedekho.app.fragment.ProfileFragment;
import com.collegedekho.app.fragment.ExamsFragment;
import com.collegedekho.app.fragment.FilterFragment;
import com.collegedekho.app.fragment.HomeFragment;
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
import com.collegedekho.app.fragment.ProfileFragment1;
import com.collegedekho.app.fragment.PsychometricTestFragment;
import com.collegedekho.app.fragment.QnAQuestionsAndAnswersFragment;
import com.collegedekho.app.fragment.QnAQuestionsListFragment;
import com.collegedekho.app.fragment.SplashFragment;
import com.collegedekho.app.fragment.StreamFragment;
import com.collegedekho.app.fragment.TabFragment;
import com.collegedekho.app.fragment.UserEducationFragment;
import com.collegedekho.app.fragment.WidgetListFragment;
import com.collegedekho.app.fragment.pyschometricTest.PsychometricQuestionFragment;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.listener.OnApplyClickedListener;
import com.collegedekho.app.resource.Constants;

import com.collegedekho.app.resource.ContainerHolderSingleton;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;

import com.collegedekho.app.widget.CircleImageView;
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
import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

import bolts.AppLinks;
import io.connecto.android.sdk.Connecto;
import io.connecto.android.sdk.Properties;
import io.connecto.android.sdk.Traits;

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
        implements  NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<Cursor>,ExamsFragment.OnExamsSelectListener,
        ProfileFragment.OnTabSelectListener, TabFragment.OnHomeItemSelectListener,
        HomeFragment.OnHomeInteractionListener, DataLoadListener,
        StreamFragment.OnStreamInteractionListener,
        InstituteListFragment.OnInstituteSelectedListener,
        InstituteShortlistFragment.OnShortlistedInstituteSelectedListener,
        OnApplyClickedListener, WidgetListFragment.OnWidgetInteractionListener,
        NewsFragment.OnNewsSelectedListener, InstituteQnAFragment.OnQuestionAskedListener,
        FilterFragment.OnFilterInteractionListener, InstituteOverviewFragment.OnInstituteShortlistedListener,
        QnAQuestionsListFragment.OnQnAQuestionSelectedListener,
        QnAQuestionsAndAnswersFragment.OnQnAAnswerInteractionListener,
        MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener,
        MyFutureBuddiesFragment.OnMyFBInteractionListener,ArticleFragment.OnArticleSelectedListener,
        ProfileFragment1.onProfileUpdateListener,
        LoginFragment1.OnSignUpListener, LoginFragment.OnUserSignUpListener,
        InstituteDetailFragment.OnInstituteFooterItemSelected, UserEducationFragment.OnUserEducationInteractionListener,NotPreparingFragment.OnTestOptionsListener,
        PsychometricTestFragment.OnPsychometricTestSubmitListener
{

    static {
        Constants.FilterCategoryMap.put(Constants.ID_HOSTEL, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);
        Constants.FilterCategoryMap.put(Constants.ID_FACILITIES, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);

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
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    public BaseFragment currentFragment;
    private List<Institute> mInstituteList;
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
    private String instituteCourseId = "";
    private List<Widget> mWidgets;
    public static CallbackManager callbackManager;

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
    public static User user;
    private User.Prefs userPref;
    private boolean completedStage2;
    static String type = "";
    static String resource_uri = "";
    private  HashMap<String, String> mUserSignUPParams;
    private Menu menu;

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

        //Fabric.with(this, new Crashlytics());

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

        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(this.mToolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.mSetNavigationListener();

         this.mDisplayFragment(SplashFragment.newInstance(), false, SplashFragment.class.getName());
        //init();
        //this.mDisplayFragment(MyAlertFragment.newInstance(), false, MyAlertFragment.class.getName());
        // show appBarLayout and toolBar

        // TODO: Move this to where you establish a user session
        //logUser();
    }


    /*private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        if (MainActivity.user != null)
        {
            Crashlytics.setUserIdentifier(MainActivity.user.getToken());
            Crashlytics.setUserEmail(MainActivity.user.getEmail());
            Crashlytics.setUserName(MainActivity.user.getName());
        }
    }*/

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

                this.mMakeNetworkCall(Constants.WIDGET_SHORTLIST, MainActivity.resource_uri, null);
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

            this.completedStage2 = sp.getBoolean(Constants.COMPLETED_SECOND_STAGE, false);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }
    public void loadInItData()
    {
        if (user != null && user.getPref() == User.Prefs.STREAMKNOWN) {
            // if user is anonymous  then logout from facebook
            if(user.is_anony())
                disconnectFromFacebook();

            if (!completedStage2)
                MainActivity.this.mSetUserPref();
            else
                mLoadUserProfile();
        } else {
            disconnectFromFacebook();
             MainActivity.this.mDisplayFragment(LoginFragment.newInstance(), false, Constants.TAG_FRAGMENT_LOGIN);
        }
    }

    /**
     * This method is called when Navigation drawer item is selected
     * @param item  selected position of navigation drawer item
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        Fragment fragment = null;
        int position=0;
        switch (id) {
            case R.id.nav_home:
                mClearBackStack();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_institutes:
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);
                position = 1;
                break;
            case R.id.nav_news:
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_NEWS_LIST);
                position = 2;
                break;
            case R.id.nav_articles:
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_ARTICLES_LIST);
                position = 3;
                break;
            case R.id.nav_my_qna:
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_QNA_QUESTION_LIST);
                position = 4;
                break;
            case R.id.nav_future_buddies:
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_MY_FB_ENUMERATION);
                position = 5;
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        if(fragment != null) {
            this.mUpdateNavigationMenuItem(position);
            int count = getSupportFragmentManager().getBackStackEntryCount();
            for (int i = 0; i < count-1; i++) {
                getSupportFragmentManager().popBackStack();
            }
            return true;
        }
        if(position  == 1){
            onHomeItemSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL+"personalize/institutes");
        }else  if(position  == 2){
            onHomeItemSelected(Constants.WIDGET_NEWS, Constants.BASE_URL+"personalize/news");
        }else  if(position  == 3){
            onHomeItemSelected(Constants.WIDGET_ARTICES, Constants.BASE_URL+"personalize/articles");
        }else  if(position  == 4){
            onHomeItemSelected(Constants.TAG_LOAD_QNA_QUESTIONS, Constants.BASE_URL+"personalize/qna");
        }else  if(position  == 5){
            onHomeItemSelected(Constants.WIDGET_FORUMS, Constants.BASE_URL+"personalize/forums");
        }
        if(position==1){
            hideMenuOption(R.id.action_home);
        }else {
            showMenuOption(R.id.action_home);
        }
        return true;
    }

    /**
     * This method is used to update menu item on navigation drawer
     * @param position
     */
    public void mUpdateNavigationMenuItem(int position) {

        ((NavigationView) findViewById(R.id.nav_view)).getMenu().getItem(position).setChecked(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }*/

        getMenuInflater().inflate(R.menu.main, menu);
        this.menu=menu;
    hideMenuOption(R.id.action_home);
        return super.onCreateOptionsMenu(menu);
    }

    private void hideMenuOption(int id)
    {
        MenuItem item = this.menu.findItem(id);
        item.setVisible(false);
        invalidateOptionsMenu();
    }

    private void showMenuOption(int id)
    {
        MenuItem item = this.menu.findItem(id);
        item.setVisible(true);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(currentFragment instanceof ProfileFragment) {
            menu.getItem(0).setVisible(false);
        }
         else {
            menu.getItem(0).setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_home) {
            this.mClearBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onHomeItemSelected(User.Prefs preference) {
        this.userPref = preference;
        requestUserCreation();
    }

    private void requestUserCreation() {
        if (MainActivity.user == null)
            this.mMakeNetworkCall(Constants.TAG_CREATE_USER, Constants.BASE_URL + "users/anonymous/", new HashMap<String, String>());
        else {
            MainActivity.user.setPref(this.userPref);
            this.mSetUserPref();
        }
    }

    private void mUserCreated(String json) {
        try {
            MainActivity.user = JSON.std.beanFrom(User.class, json);
            MainActivity.user.setPref(this.userPref);
            this.networkUtils.setToken(MainActivity.user.getToken());

            this.connecto.identify(MainActivity.user.getId(), new Traits().putValue(Constants.USER_NAME, MainActivity.user.getName()));

            String u = JSON.std.asString(MainActivity.user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

            this.mSetUserPref();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    private void mSetUserPref() {
        switch (user.getPref()) {
            case NOT_SURE:
                this.mMakeNetworkCall(Constants.TAG_LOAD_PYSCHOMETRIC_TEST, Constants.BASE_URL + "psychometrictests/", null);
                break;
            case STREAMKNOWN:
                //this.mMakeNetworkCall(Constants.TAG_LOAD_STREAM, Constants.BASE_URL + "streams/", null);
                this.mMakeNetworkCall(Constants.TAG_USER_EDUCATION, Constants.BASE_URL + "user-education/", null);

                break;
        }
    }

    public String extractResults(String response) {
        try {
            Map<String, Object> map = JSON.std.mapFrom(response);
            if (map.get("next") != null)
                next = map.get("next").toString();
            else
                next = null;

            if (map.containsKey("filters"))
                this.mFilters = JSON.std.asString(map.get("filters"));

            String val = JSON.std.asString(map.get("results"));
            return val;
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

            if (currentFragment instanceof NewsFragment) {
                ((NewsFragment) currentFragment).updateList(news, this.next);
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
            if (currentFragment instanceof ArticleFragment) {
                ((ArticleFragment) currentFragment).updateList(articles, next);
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

    private void mDisplayInstituteList(String response, boolean filterAllowed) {
        try {
            String val = this.extractResults(response);
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, val);

            if (this.mFilterKeywords.size() > 0)
                this.mFilterCount = this.mFilterKeywords.size();

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);

            // used to create new fragment if shortlist institute fragment
            // is clicked in navigation drawer
            if (fragment != null && fragment instanceof InstituteListFragment)
            {
                if(((InstituteListFragment) fragment).getFilterAllowed() != filterAllowed)
                    fragment = null;
            }

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

    private void mDisplayInstitute(int position) {

        int  id ;
        if(currentFragment instanceof InstituteShortlistFragment)
        {
            this.mInstitute = this.mShortlistedInstituteList.get(position);
            id = this.mShortlistedInstituteList.get(position).getId();
        }
        else {
            this.mInstitute = this.mInstituteList.get(position);
            id = this.mInstituteList.get(position).getId();
        }

        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE);
        if (fragment == null)
            this.mDisplayFragment(InstituteDetailFragment.newInstance(this.mInstitute), true, Constants.TAG_FRAGMENT_INSTITUTE);
        else
            this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_INSTITUTE);

        this.mMakeNetworkCall(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "institutecourses/" + "?institute=" + id, null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_QNA_QUESTIONS, mInstitute.getResource_uri() + "qna/", null, Request.Method.GET);
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

            if (this.currentFragment instanceof WidgetListFragment || this.currentFragment instanceof  ProfileFragment) {
                if (findViewById(R.id.app_bar_layout).getVisibility() != View.VISIBLE)
                    findViewById(R.id.app_bar_layout).setVisibility(View.VISIBLE);
            }
            this.mShouldDisplayHomeUp();

        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
        }
        finally {
            //Send GA Session
            MainActivity.GAScreenEvent(tag);
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
            case Constants.TAG_CREATE_USER:
                this.mUserCreated(response);
                break;
            case Constants.TAG_CREATE_ANONY_USER:
                this.mCreatedAnonymousUser(response);
                break;
            case Constants.TAG_CREATE_FACEBOOK_ANONY_USER_:
                this.mCreatedFacebookAnonymousUser(response);
                break;
            case Constants.TAG_USER_SIGNUP:
                this.mUserSignUpResponse(response);
                break;
            case Constants.TAG_USER_FACEBOOK_LOGIN:
                this.mUserFacebookLoginResponse(response);
                break;
            case Constants.TAG_LOAD_STREAM:
                this.mDisplayStreams(response, false);
                break;
            case Constants.WIDGET_SHORTLIST:
                this.mDisplayShortlistedInstituteList(response);
                break;
            case Constants.WIDGET_INSTITUTES:
                this.mDisplayInstituteList(response, true);
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
            case Constants.TAG_APPLIED_COURSE:
                String tabPosition = null;
                if (tags.length == 3) {
                    extraTag = tags[1];
                    tabPosition = tags[2];
                }
                this.mUpdateAppliedCourses(response, extraTag, tabPosition);
                break;
            case Constants.TAG_LOAD_HOME:
                this.mUpdateHome(response);
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
            case Constants.TAG_LOAD_PYSCHOMETRIC_TEST:
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
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_TEST:
                System.out.print(response);
                this.mUpdateUserPref(response);
                break;
            case Constants.TAG_SUBMIT_PREFRENCES:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    if (tags.length > 3)
                        extraTag = tags[3];

                    this.mStreamAndLevelSelected(response, parentIndex, childIndex, extraTag);
                }
                this.mLoadUserProfile();
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
            case Constants.SEARCHED_INSTITUTES:
                this.mDisplayInstituteList(response, true);
                break;
            case Constants.TAG_USER_EDUCATION:
                this.mDisplayUserEducationFragment(response);
                break;
            case Constants.TAG_EDUCATION_DETAILS_SUBMIT:
                this.mOnUserEducationResponse();
                break;
            case Constants.TAG_EXAMS_LIST:
                this.mOnExamsLoaded(response);
                break;
            case Constants.TAG_SUBMIT_EXAMS_LIST:
                this.mOnExamsSubmitted(response);
                break;
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM:
//                this.mOnExamsSubmitted(response);
                break;
        }

        if (this.progressDialog != null && this.progressDialog.isShowing())
            this.progressDialog.dismiss();
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
        } catch (IOException e) {
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

        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.COMPLETED_SECOND_STAGE, true).commit();

        String token = user.getToken();
        String image = user.getImage();
        //TODO: May be we can make a new pref entry for token
        try {
            MainActivity.user = JSON.std.beanFrom(User.class, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //save the preferences locally
        MainActivity.user.setPref(User.Prefs.STREAMKNOWN);
        MainActivity.user.setToken(token);
        MainActivity.user.setImage(image);

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

    }

    private void mUpdateUserPref(String response) {
        JSONObject psychometricResult;

        try {
            String stream = "";
            String level = "";

            psychometricResult = new JSONObject(response);

            HashMap<String, String> hashMap = new HashMap<>();

            if (psychometricResult.has("stream") && !psychometricResult.isNull("stream")) {
                stream = psychometricResult.getString("stream");

                user.setPref(User.Prefs.STREAMKNOWN);
                user.setStream(stream);
            }
            if (psychometricResult.has("course_levels") && !psychometricResult.isNull("course_levels")) {
                level = psychometricResult.getString("course_levels");

                user.setLevel(level);
            }

            if (stream != "" && level != "") {
                this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.COMPLETED_SECOND_STAGE, true).commit();

                try {
                    String u = JSON.std.asString(user);
                    this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

                    hashMap.put("status", user.getPref().name().toLowerCase());
                    hashMap.put("stream", Constants.BASE_URL + "streams/" + user.getStream() + "/");
                    hashMap.put("level", Constants.BASE_URL + "level/" + user.getLevel() + "/");

                    this.mMakeNetworkCall(Constants.TAG_SUBMIT_PREFRENCES + "#" + user.getLevel() + "#" + user.getStream(), Constants.BASE_URL + "preferences/", hashMap);
                    this.mMakeNetworkCall(Constants.TAG_LOAD_HOME, Constants.BASE_URL + "widgets/", null);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            } else if (stream != "" && level == "") {
                this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.COMPLETED_SECOND_STAGE, true).commit();

                try {
                    String u = JSON.std.asString(user);
                    getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

                    hashMap.put("status", user.getPref().name().toLowerCase());
                    hashMap.put("stream",Constants.BASE_URL + "streams/" + user.getStream() + "/");

                    this.mMakeNetworkCall(Constants.TAG_SUBMIT_PREFRENCES + "#" + user.getLevel() + "#" + user.getStream(), Constants.BASE_URL + "preferences/", hashMap);
                    this.mMakeNetworkCall(Constants.TAG_LOAD_HOME, Constants.BASE_URL + "widgets/", null);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateQuestionLikeButton(String response, String extraTag, int like) {

        if(this.mQnAQuestions == null ) return;
        QnAQuestions qnaQuestion = this.mQnAQuestions.get(Integer.parseInt(extraTag));
        if(like != Constants.NEITHER_LIKE_NOR_DISLIKE){

            qnaQuestion.setCurrent_user_vote_type(like);
            if(like == Constants.LIKE_THING){
            qnaQuestion.setUpvotes(qnaQuestion.getUpvotes()+1);
        }
        else if(like == Constants.DISLIKE_THING){
                qnaQuestion.setUpvotes(qnaQuestion.getUpvotes()-1);
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

                }
                else if (like == Constants.DISLIKE_THING)
                {
                    this.connecto.track(Constants.ACTION_INSTITUTE_DISLIKED, new Properties().putValue(Constants.TAG_INSTITUTE_LIKE_DISLIKE, Constants.DISLIKE_THING).putValue(institute.getShort_name(), Constants.DISLIKE_THING).putValue(Constants.INSTITUTE_RESOURCE_URI, institute.getResource_uri()));

                    //GA Event for institute disliked
                    MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_INSTITUTE_DISLIKED,
                            "Institute ID: " + String.valueOf(institute.getId()) +
                                    "Institute Name: " + institute.getShort_name() +
                                    "Disliked institute: " + String.valueOf(like));
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
        if (currentFragment instanceof InstituteListFragment)
            ((InstituteListFragment) currentFragment).updateShortlistButton(Integer.parseInt(extraTag));
        //Toast.makeText(this, institute.getShort_name() + message, Toast.LENGTH_SHORT).show();
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
            case Constants.TAG_CREATE_USER:
            case Constants.TAG_CREATE_ANONY_USER:
            case Constants.TAG_USER_SIGNUP:
                return "Creating a user...";
            case Constants.TAG_USER_REGISTRATION :
                return "Creating User Please Wait";
            case Constants.TAG_USER_LOGIN:
                return "Signing User Please Wait.....";
            case Constants.TAG_SKIP_LOGIN:
                return "Loading Streams.....";
            case Constants.TAG_LOAD_STREAM:
            case Constants.TAG_UPDATE_STREAM:
                return "Loading Streams...";
            case Constants.TAG_UPDATE_INSTITUTES:
                return "Updating Institues...";
            case Constants.TAG_UPDATE_PREFRENCES:
                return "Updating Profile...";
            case Constants.WIDGET_INSTITUTES:
            case Constants.WIDGET_SHORTLIST:
                return "Loading Institutes...";
            case Constants.WIDGET_NEWS:
                return "Loading News...";
            case Constants.WIDGET_ARTICES:
                return "Loading Articles...";
            case Constants.WIDGET_COURSES:
                return "Loading Courses...";
            case Constants.TAG_LOAD_HOME:
                return "Loading...";
            case Constants.TAG_POST_QUESTION:
                return "Posting your question...";
            case Constants.TAG_LOAD_FILTERS:
                return "Loading Filters...";
            case Constants.TAG_LOAD_PYSCHOMETRIC_TEST:
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
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_TEST:
                return "Submitting psychometric analysis...";
            case Constants.SEARCHED_INSTITUTES:
                return "Loading...";
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
                .setCancelable(false)
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
        hashMap.put("status", user.getPref().name().toLowerCase());
        hashMap.put("user", user.getResource_uri());
        hashMap.put(Constants.USER_STREAM, streamURI);
        hashMap.put(Constants.USER_LEVEL, Constants.BASE_URL + "level/" + (level + 1) + "/");
        hashMap.put(Constants.USER_STREAM_NAME, streamName);
        hashMap.put(Constants.USER_DEVICE_ID, deviceId);

        if (streamName != null || streamName != "")
            MainActivity.user.setStream_name(streamName);

        this.mMakeNetworkCall(Constants.TAG_SUBMIT_PREFRENCES + "#" + level + "#" + streamURI + "#" + streamName, Constants.BASE_URL + "preferences/", hashMap);
        //this.mMakeNetworkCall(Constants.TAG_LOAD_HOME, Constants.BASE_URL + "widgets/", null);
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
        Institute institute = null;
        if(currentFragment instanceof InstituteShortlistFragment)
            institute = this.mShortlistedInstituteList.get(position);
        else
            institute = this.mInstituteList.get(position);
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
        if (qnaQuestion.getCurrent_user_vote_type() != Constants.NEITHER_LIKE_NOR_DISLIKE  && voteType != Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //neither liked nor disliked case
            if (voteType == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + voteType, qnaQuestion.getResource_uri()+"upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_QUESTION_LIKE_DISLIKE + "#" + position + "#" + voteType, qnaQuestion.getResource_uri() + "downvote/", null, Request.Method.POST);
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
    public void onCourseApplied(int position, int tabPosition, InstituteCourse instituteCourse) {

        HashMap<String, String> map = new HashMap<>();

        if (user != null) {
            map.put(Constants.USER_NAME, user.getName());
            map.put(Constants.USER_EMAIL, user.getEmail());
        }

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        if (mPhoneNumber != null) {
            map.put(Constants.USER_PHONE, mPhoneNumber);
        } else {
            if (user != null) map.put("phone_no", user.getPhone_no());
        }

        map.put(Constants.APPLY_COURSE, "" + instituteCourse.getId());
        if (mInstitute != null) {
            map.put("institute", "" + mInstitute.getId());
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        map.put(Constants.APPLY_YEAR, "" + year);

        String URL = Constants.BASE_URL + "lms/";
        instituteCourseId = "" + instituteCourse.getId();
        this.mMakeNetworkCall(Constants.TAG_APPLIED_COURSE + "#" + position + "#" + tabPosition, URL, map, Request.Method.POST);

        //GA Event for course applied
        MainActivity.GATrackerEvent(Constants.CATEGORY_INSTITUTES, Constants.ACTION_COURSE_APPLIED,
                "CourseID: " + String.valueOf(instituteCourse.getId()) +
                        "Course Name: " + instituteCourse.getName() +
                        "Institute ID: " + String.valueOf(mInstitute.getId()) +
                        "Institute Name: " + mInstitute.getName());

        this.connecto.track(Constants.CATEGORY_INSTITUTES, new Properties().putValue(Constants.APPLY_COURSE_ID, String.valueOf(instituteCourse.getId())).
                putValue(Constants.APPLY_COURSE, instituteCourse.getName()).
                putValue(Constants.APPLY_INSTITUTE, mInstitute.getResource_uri()));
    }

    @Override
    public void onWidgetSelected(Widget widget, int position) {
        this.mCurrentTitle = widget.getTitle();
        this.mUpdateNavigationMenuItem(position);
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
        }
    }

    @Override
    public void onArticleSelected(Articles article, boolean addToBackstack) {
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

        for (String key : this.mFilterKeywords.keySet())
            this.connecto.track(Constants.ACTION_FILTER_APPLIED, new Properties().putValue(Constants.SELECTED_FILTERS, this.mFilterKeywords.get(key)));
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

            QnAQuestions question = this.mQnAQuestions.get(questionIndex);

            if (voteType == Constants.LIKE_THING)
            {
                //GA and Connecto Event for question vote up
                MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_QUESTION_UPVOTED, "Question Voted : " + String.valueOf(voteType));
                this.connecto.track(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.LIKE_THING).putValue(question.getResource_uri(), Constants.LIKE_THING));
            }
            else if (voteType == Constants.DISLIKE_THING)
            {
                //GA and Connecto Event for question vote down
                MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_QUESTION_DOWNVOTED, "Question Voted : " + String.valueOf(voteType));
                this.connecto.track(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_QUESTION_ENTITY, Constants.DISLIKE_THING).putValue(question.getResource_uri(), Constants.DISLIKE_THING));
            }

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
            QnAAnswers answer = this.mQnAQuestions.get(questionIndex).getAnswer_set().get(answerIndex);

            if (voteType == Constants.LIKE_THING)
            {
                //GA and Connecto Event for answer vote up
                MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_ANSWER_UPVOTED, "Answer Voted : " + String.valueOf(voteType));
                this.connecto.track(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.LIKE_THING).putValue(answer.getResource_uri(), Constants.LIKE_THING));
            }
            else if (voteType == Constants.DISLIKE_THING)
            {
                //GA and Connecto Event for answer vote down
                MainActivity.GATrackerEvent(Constants.CATEGORY_QNA, Constants.ACTION_VOTE_QNA_ANSWER_DOWNVOTED, "Answer Voted : " + String.valueOf(voteType));
                this.connecto.track(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, new Properties().putValue(Constants.ACTION_VOTE_QNA_ANSWER_ENTITY, Constants.DISLIKE_THING).putValue(answer.getResource_uri(), Constants.DISLIKE_THING));
            }

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
        }
    }

    @Override
    public void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position, int commentsCount) {
        this.mMakeNetworkCall(Constants.TAG_LOAD_MY_FB + "#" + String.valueOf(position) + "#" + String.valueOf(commentsCount), myFutureBuddiesEnumeration.getResource_uri(), null, Request.Method.GET);

        //GA Event for FB selection
        MainActivity.GATrackerEvent(Constants.CATEGORY_MY_FB, Constants.ACTION_MY_FB_SELECTED, "FB selected: " + myFutureBuddiesEnumeration.getInstitute_name());
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

    public void mShouldDisplayHomeUp(){

        if (this.currentFragment instanceof  ProfileFragment || this.currentFragment instanceof WidgetListFragment ||
                this.currentFragment instanceof HomeFragment || this.currentFragment instanceof SplashFragment||
                this.currentFragment instanceof UserEducationFragment || this.currentFragment instanceof ExamsFragment) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            this.mToggle.syncState();


        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
     * This method is called when user press navigation back arrow
     *  present on the toolbar
     */
    private void mSetNavigationListener() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mToggle = new ActionBarDrawerToggle(
                this, drawer, this.mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                String name  = MainActivity.user.getName();
                TextView mProfileName    =   (TextView)findViewById(R.id.nav_header_name);
                TextView mStreamName    =   (TextView)findViewById(R.id.nav_header_stream);
                CircleImageView mProfileImage = (CircleImageView)findViewById(R.id.nav_header_profile_image);
                mProfileImage.setDefaultImageResId(R.drawable.ic_default_image);
                mProfileImage.setErrorImageResId(R.drawable.ic_default_image);
                if(name.equalsIgnoreCase("Anonymous User"))
                {
                    mProfileName.setText("");
                    mProfileName.setVisibility(View.INVISIBLE);
                }else {
                    mProfileName.setText(name);
                    mProfileName.setVisibility(View.VISIBLE);
                }
                mStreamName.setText(MainActivity.user.getStream_name());

                String image = MainActivity.user.getImage();
                if (image != null && ! image.isEmpty()) {
                    mProfileImage.setImageUrl(image, MySingleton.getInstance(getApplicationContext()).getImageLoader());
                    mProfileImage.setVisibility(View.VISIBLE);
                }
                else
                    mProfileImage.setVisibility(View.GONE);
            }
        };
        drawer.setDrawerListener(this.mToggle);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        this.mToggle.syncState();
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }*/
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count >= 1) {
                         getSupportFragmentManager().popBackStack();
                } else {
                    /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    if(!drawer.isDrawerOpen(GravityCompat.START))
                        drawer.openDrawer(GravityCompat.START);*/
                }
            }
        });
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(this.isLastFragment())
        {
            this.mClearBackStack();
        }
        else if(!Constants.READY_TO_CLOSE) {
            Constants.READY_TO_CLOSE = true;
            Utils.DisplayToast(getApplicationContext(), "Press again to close CollegeDekho");
            baskpressHandler.postDelayed(backpressRunnable,1500);
        }
        else {
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
     * This method is used to check about fragment is second last
     * in  back stack fragmentManager
     * @return boolean
     */
    private boolean isLastFragment()
    {
        if (currentFragment instanceof  InstituteListFragment || currentFragment instanceof  NewsFragment
                || currentFragment instanceof  ArticleFragment || currentFragment instanceof QnAQuestionsListFragment
                || currentFragment instanceof  MyFutureBuddiesEnumerationFragment || currentFragment instanceof InstituteDetailFragment)
            return true;
        else
            return false;
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
     * This method is used to sign Up User
     * @param params request data
     */
    @Override
    public void onSplashUserSignUp(HashMap<String, String> params) {
        this.userPref = User.Prefs.STREAMKNOWN;
        this.mMakeNetworkCall(Constants.TAG_CREATE_ANONY_USER, Constants.BASE_URL + "users/anonymous/", params);
        this.mUserSignUPParams = params;

    }
    /**
     * This method is called when user skip
     *  registration or facebook login
     */
    @Override
    public void onSkipUserLogin() {
        this.userPref = User.Prefs.STREAMKNOWN;
        this.mMakeNetworkCall(Constants.TAG_SKIP_LOGIN, Constants.BASE_URL + "users/anonymous/", new HashMap<String, String>());
    }

    private void  mCreatedAnonymousUser(String json)
    {
        try {
            this.user = JSON.std.beanFrom(User.class, json);
           // this.user.setPref(this.userPref);
            this.networkUtils.setToken(this.user.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mMakeNetworkCall(Constants.TAG_USER_SIGNUP, Constants.BASE_URL + "auth/register/", this.mUserSignUPParams);
    }


    private void mUserSignUpResponse(String json)
    {
        User tempUser = this.user;
        try {
            this.user = JSON.std.beanFrom(User.class, json);
            this.user.setToken(tempUser.getToken());
            this.user.setPref(this.userPref);
            String u = JSON.std.asString(this.user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mSetUserPref();
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
            this.user.setImage(tempUser.getImage());
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
        User tempUser = this.user;
        try {
            this.user = JSON.std.beanFrom(User.class, json);
            this.networkUtils.setToken(user.getToken());
            this.user.setImage(tempUser.getImage());
            this.user.setPref(this.userPref);
            String u = JSON.std.asString(this.user);
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(this.user.getLevel() == null || this.user.getStream() == null )
            this.mSetUserPref();
        else
            this.mMakeNetworkCall(Constants.TAG_USER_EDUCATION,  Constants.BASE_URL + "user-education/", null);

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
        this.user.setStream(tempUser.getStream());
        this.user.setLevel(tempUser.getLevel());
        this.user.setStream_name(tempUser.getStream_name());
        this.user.setLevel_name(tempUser.getLevel_name());
        this.user.setToken(tempUser.getToken());
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
     * This method is used to send user's current education
     * details to server like current level, stream and marks
     */
    @Override
    public void onUserNotPreparingSelected() {
//        this.mMakeNetworkCall(Constants.TAG_LOAD_STREAM, Constants.BASE_URL + "streams/", null);
        this.mDisplayFragment(NotPreparingFragment.newInstance(),false,NotPreparingFragment.class.toString() );
    }


    /**
     * This method is used to handle response having exams list
     * after user education detail is submitted to server.
     */
    private void mOnUserEducationResponse() {
        this.mMakeNetworkCall(Constants.TAG_EXAMS_LIST, Constants.BASE_URL + "yearly-exams/",null);

    }
    /**
     * This method is used to handle response having exams list
     * after user education detail is submitted to server.
     * @param responseJson
     */
    private void mOnExamsLoaded(String responseJson) {
        try {
            List<Exam> mExamList = JSON.std.listOfFrom(Exam.class, extractResults(responseJson));
            this.mDisplayFragment(ExamsFragment.newInstance(new ArrayList<>(mExamList)),false,ExamsFragment.class.toString());
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
     * This method is load user profile after
     */
    public void mLoadUserProfile() {
        // unlock navigation drawer when home screen come
        //((DrawerLayout)findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.COMPLETED_SECOND_STAGE, true).commit();

        //  show appBarLayout and toolBar
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.container).getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        findViewById(R.id.container).setLayoutParams(params);

        this.mDisplayFragment(ProfileFragment.newInstance(),false,ProfileFragment.class.toString() );

    }

    @Override
    public void onTabSelected(int tabPosition) {
        //TODO::  remove this when future buddies tab are present
        if(tabPosition == 2){
            this.onHomeItemSelected(Constants.WIDGET_FORUMS, Constants.BASE_URL+"personalize/forums");
            return;
        }
        this.mDisplayFragment(TabFragment.newInstance(tabPosition),true,TabFragment.class.toString() );
    }

    @Override
    public void onHomeItemSelected(String requestType, String url) {

        if (requestType.equals(Constants.WIDGET_INSTITUTES)) {
            this.mFilterKeywords = this.mGetTheFilters();
            this.mMakeNetworkCall(requestType, url, this.mFilterKeywords);
            return;
        }
       this.mMakeNetworkCall(requestType,url, null);
    }

    @Override
    public void onPsychometricTest() {
        this.mDisplayFragment(PsychometricTestFragment.newInstance(),false,NotPreparingFragment.class.toString() );
    }

    @Override
    public void onIDontKnow() {

    }

    @Override
    public void onIknowWhatIWant() {
        this.mMakeNetworkCall(Constants.TAG_LOAD_STREAM, Constants.BASE_URL + "streams/", null);
    }

    @Override
    public void onSubmitSubmit(HashMap<String,String> params) {
//        this.mMakeNetworkCall(Constants.TAG_SUBMIT_PSYCHOMETRIC_EXAM,Constants.BASE_URL + "yearly-exams/",params, 1);
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

    private void mOnExamsSubmitted(String response){

        mLoadUserProfile();

    }

    Handler baskpressHandler=new Handler();
    Runnable backpressRunnable=new Runnable() {
        @Override
        public void run() {
      Constants.READY_TO_CLOSE=false;
        }
    };

}