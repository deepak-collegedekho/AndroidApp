package com.collegedekho.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Articles;
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
import com.collegedekho.app.entities.Widget;
import com.collegedekho.app.fragment.ArticleDetailFragment;
import com.collegedekho.app.fragment.ArticleListFragment;
import com.collegedekho.app.fragment.FilterFragment;
import com.collegedekho.app.fragment.HomeFragment;
import com.collegedekho.app.fragment.InstituteDetailFragment;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
import com.collegedekho.app.fragment.InstituteQnAFragment;
import com.collegedekho.app.fragment.MyFutureBuddiesEnumerationFragment;
import com.collegedekho.app.fragment.MyFutureBuddiesFragment;
import com.collegedekho.app.fragment.NavigationDrawerFragment;
import com.collegedekho.app.fragment.NewsDetailFragment;
import com.collegedekho.app.fragment.NewsListFragment;
import com.collegedekho.app.fragment.ProfileFragment;
import com.collegedekho.app.fragment.QnAQuestionsAndAnswersFragment;
import com.collegedekho.app.fragment.QnAQuestionsListFragment;
import com.collegedekho.app.fragment.SignUpFragment;
import com.collegedekho.app.fragment.SplashFragment;
import com.collegedekho.app.fragment.StreamFragment;
import com.collegedekho.app.fragment.WidgetListFragment;
import com.collegedekho.app.fragment.pyschometricTest.PsychometricQuestionFragment;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.listener.OnApplyClickedListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.NetworkUtils;
import com.crashlytics.android.Crashlytics;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.fasterxml.jackson.jr.ob.JSON;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import io.connecto.android.sdk.Connecto;
import io.connecto.android.sdk.Properties;
import io.connecto.android.sdk.Traits;
import io.fabric.sdk.android.Fabric;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements  NavigationDrawerFragment.NavigationDrawerCallbacks,
        HomeFragment.OnHomeInteractionListener,
        DataLoadListener,
        StreamFragment.OnStreamInteractionListener,
        InstituteListFragment.OnInstituteSelectedListener,
        OnApplyClickedListener, WidgetListFragment.OnWidgetInteractionListener,
        NewsListFragment.OnNewsSelectedListener, InstituteQnAFragment.OnQuestionAskedListener,
        FilterFragment.OnFilterInteractionListener, InstituteOverviewFragment.OnInstituteShortlistedListener,
        QnAQuestionsListFragment.OnQnAQuestionSelectedListener,
        QnAQuestionsAndAnswersFragment.OnQnAAnswerInteractionListener,
        MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener,
        MyFutureBuddiesFragment.OnMyFBInteractionListener,ArticleListFragment.OnArticleSelectedListener,
        ProfileFragment.onProfileUpdateListener,SignUpFragment.OnSignUpListener {

    static {
        Constants.FilterCategoryMap.put(Constants.ID_HOSTEL, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);
        Constants.FilterCategoryMap.put(Constants.ID_FACILITIES, Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING);

        Constants.FilterCategoryMap.put(Constants.ID_FEE_RANGE, Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES);
        Constants.FilterCategoryMap.put(Constants.ID_INSTITUTE_TYPE, Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES);

        Constants.FilterCategoryMap.put(Constants.ID_CITY, Constants.FILTER_CATEGORY_LOCATION);
        Constants.FilterCategoryMap.put(Constants.ID_STATE, Constants.FILTER_CATEGORY_LOCATION);

        Constants.FilterCategoryMap.put(Constants.ID_LEVEL, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
        Constants.FilterCategoryMap.put(Constants.ID_SPECIALIZATION, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
        Constants.FilterCategoryMap.put(Constants.ID_DEGREE, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
        Constants.FilterCategoryMap.put(Constants.ID_EXAM, Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);
    }

    public static final int GET_PSYCHOMETRIC_RESULTS = 1;
    public static final String PSYCHOMETRIC_RESULTS = "psychometric_results";
    private static final String TAG = "MainActivity";
    private static final String TRACKER_ID = "UA-67752258-1";

    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    public NetworkUtils networkUtils;
    public Toolbar mToolbar;
    public Fragment currentFragment;
    private List<Institute> mInstituteList;
    private int currentInstitute;
    private ProgressDialog progressDialog;
    private String mCurrentTitle;
    private String next;
    private String mTitle;
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

    private Connecto connecto = null;
    // Get SENDER_ID fom GCM.
    private String SENDER_ID = "244741229152";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
   private NavigationDrawerFragment mNavigationDrawerFragment;
    /*
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    // private CharSequence mTitle;
    public static User user;
    private User.Prefs userPref;
    private boolean completedStage2;

   /*public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = HomeFragment.TITLE;
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(mTitle);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        analytics = GoogleAnalytics.getInstance(this.getApplicationContext());
        analytics.setLocalDispatchPeriod(1800);

        connecto = Connecto.with(MainActivity.this);
        connecto.identify("RandomUser", new Traits().putValue("name", "Harsh"));
        //You can also track any event if you want
        connecto.track("Session Started", new Properties().putValue("value", 800));
        connecto.registerWithGCM(MainActivity.this, SENDER_ID);

        MainActivity.tracker = analytics.newTracker(TRACKER_ID);
        MainActivity.tracker.enableExceptionReporting(true);
        MainActivity.tracker.enableAdvertisingIdCollection(true);
        MainActivity.tracker.enableAutoActivityTracking(true);

        mRegisterFacebookSdk();

        this.setContentView(R.layout.activity_main);

        this.mToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        this.mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        setSupportActionBar(mToolbar);
        this.mTitle = getTitle().toString();
        this.mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));


        //this.mDisplayFragment(SignInFragment.newInstance(), false, SignInFragment.class.getName());

       this.mDisplayFragment(SplashFragment.newInstance(), false, SplashFragment.class.getName());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Stop the Main Animation and Start Secondary
                if (currentFragment instanceof SplashFragment)
                    ((SplashFragment) currentFragment).stopMainAnimation();

                //Initialize the app
                init();

                //check if this device is connected to internet
                int amIConnectedToInternet = networkUtils.getConnectivityStatus();

                if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
                    Constants.IS_CONNECTED_TO_INTERNET = true;

                    if (user != null && user.getPref() == User.Prefs.STREAMKNOWN) {
                        if (!completedStage2)
                            mSetUserPref();
                        else
                            mMakeNetworkCall(Constants.TAG_LOAD_HOME, Constants.BASE_URL + "widgets/", null);
                    } else {
;
                        mDisplayFragment(HomeFragment.newInstance(), false, Constants.TAG_FRAGMENT_HOME);
                    }
                } else if (currentFragment instanceof SplashFragment)
                    ((SplashFragment) currentFragment).noInternetFound();


            }
        }, Constants.MAIN_ANIMATION_TIME);
    }

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

        } catch (NoSuchAlgorithmException e) {
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private void init() {
        this.networkUtils = new NetworkUtils(this, this);

        SharedPreferences sp = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);

        try {
            if (sp.contains(Constants.KEY_USER)) {
                user = JSON.std.beanFrom(User.class, sp.getString(Constants.KEY_USER, null));
                this.networkUtils.setToken(user.getToken());
            }

            this.completedStage2 = sp.getBoolean(Constants.COMPLETED_SECOND_STAGE, false);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
    public  void mUpdateNavigationItem(int position)
    {
        if (mNavigationDrawerFragment != null) {
            mNavigationDrawerFragment.updateNavigationItem(position);
        }

    }
    @Override
    public void onNavigationDrawerItemSelected(int position , int currentposition) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        if(position ==  0) {
            mUpdateNavigationItem(position);
            mClearBackStack();
            return;
        }

        switch (position) {
            case 1:
                if(currentposition == 1)
                    fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);
                break;
            case 2:
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_NEWS_LIST);
                break;
            case 3:
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_ARTICLES_LIST);
                break;
            case 4:
                if(currentposition == 4)
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);
                break;
            case 5:
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_QNA_QUESTION_LIST);
                break;
            case 6:
                fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_MY_FB_ENUMERATION);
                break;

        }
        if(fragment != null)
        {

            mUpdateNavigationItem(position);
            int count = getSupportFragmentManager().getBackStackEntryCount();
            for (int i = 0; i < count-1; i++) {
                getSupportFragmentManager().popBackStack();
            }
            return;

        }

        if(this.mWidgets == null || this.mWidgets.size() <= position-1)
            return;

        onWidgetSelected(mWidgets.get(position - 1), position);

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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // to hide the keyboard if visible
        if (this.getCurrentFocus() != null && this.getCurrentFocus() instanceof EditText) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        if (currentFragment instanceof ProfileFragment || currentFragment instanceof StreamFragment) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);

        } else if (currentFragment instanceof WidgetListFragment) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        } else {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_profile) {

            mDisplayPofile();
            return true;

        } else if (id == R.id.action_home) {
            this.mClearBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onHomeItemSelected(User.Prefs preference) {
        userPref = preference;
        requestUserCreation();
    }


    private void requestUserCreation() {
        if (user == null)
            this.mMakeNetworkCall(Constants.TAG_CREATE_USER, Constants.BASE_URL + "users/anonymous/", new HashMap<String, String>());
        else {
            user.setPref(userPref);
            this.mSetUserPref();
        }
    }

    private void mUserCreated(String json) {
        try {
            user = JSON.std.beanFrom(User.class, json);
            user.setPref(userPref);

            networkUtils.setToken(user.getToken());

            String u = JSON.std.asString(user);
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
                this.mMakeNetworkCall(Constants.TAG_LOAD_STREAM, Constants.BASE_URL + "streams/", null);
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

            return JSON.std.asString(map.get("results"));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    private void mDisplayStreams(String response, boolean addToBackstack) {
        try {
            List<Stream> streams = JSON.std.listOfFrom(Stream.class, extractResults(response));
            mDisplayFragment(StreamFragment.newInstance(new ArrayList<>(streams), addToBackstack), addToBackstack, Constants.TAG_FRAGMENT_STREAMS);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void updateInstituteList(String response) {
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

    private void updateNewsList(String response) {
        try {
            List<News> news = JSON.std.listOfFrom(News.class, extractResults(response));
            this.mNewsList.addAll(news);
            this.mParseSimilarNews(this.mNewsList);
            if (currentFragment instanceof NewsListFragment) {
                ((NewsListFragment) currentFragment).updateList(news, this.next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void updateArticlesList(String response) {
        try {
            List<Articles> articles = JSON.std.listOfFrom(Articles.class, extractResults(response));
            this.mArticlesList.addAll(articles);

            mParseSimilarArticle(this.mArticlesList);
            if (currentFragment instanceof ArticleListFragment) {
                ((ArticleListFragment) currentFragment).updateList(articles, next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mDisplayInstituteList(String response, boolean filterAllowed) {
        try {
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, this.extractResults(response));

            if (this.mFilterKeywords.size() > 0)
                this.mFilterCount = this.mFilterKeywords.size();


            Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);


            if (fragment instanceof InstituteListFragment)
            {
                if(((InstituteListFragment) fragment).getFilterAllowed() != filterAllowed);
                fragment = null;
            }


            if (fragment == null)
                this.mDisplayFragment(InstituteListFragment.newInstance(new ArrayList<>(this.mInstituteList), this.mCurrentTitle, next, filterAllowed, this.mFilterCount), true, Constants.TAG_FRAGMENT_INSTITUTE_LIST);
            else {
                if (fragment instanceof InstituteListFragment) {
                    ((InstituteListFragment) fragment).clearList();
                    ((InstituteListFragment) fragment).updateList(this.mInstituteList, next);
                }

                this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_INSTITUTE_LIST);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mShowMyFBEnumeration(String response) {
        try {
            this.mFbEnumeration = JSON.std.listOfFrom(MyFutureBuddiesEnumeration.class, this.extractResults(response));
            this.mDisplayFragment(MyFutureBuddiesEnumerationFragment.newInstance(new ArrayList<>(this.mFbEnumeration)), true, Constants.TAG_FRAGMENT_MY_FB_ENUMERATION);
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
        mInstitute = this.mInstituteList.get(position);

        this.mDisplayFragment(InstituteDetailFragment.newInstance(this.mInstitute), true, Constants.TAG_FRAGMENT_INSTITUTE);

        this.mMakeNetworkCall(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "institutecourses/" + "?institute=" + this.mInstituteList.get(position).getId(), null);
        this.mMakeNetworkCall(Constants.TAG_LOAD_INSTITUTE_QNA_QUESTIONS, mInstitute.getResource_uri() + "qna/", null, Request.Method.GET);
    }

    private void loadPyschometricTest(String response) {
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.PSYCHOMETRIC_RESULTS, response);

        Intent activityIntent = new Intent(MainActivity.this, PsychometricAnalysisActivity.class);
        activityIntent.putExtras(bundle);

        this.startActivityForResult(activityIntent, MainActivity.GET_PSYCHOMETRIC_RESULTS);
    }

  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == MainActivity.GET_PSYCHOMETRIC_RESULTS) {
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
            if(currentFragment instanceof SignUpFragment)
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }
  }

    private void mDisplayFragment(Fragment fragment, boolean addToBackstack, String tag) {
       try {

           if (this.getCurrentFocus() != null && this.getCurrentFocus() instanceof EditText) {
               InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
               imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
           }

            currentFragment = fragment;

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.container, fragment, tag);

            if (addToBackstack)
                fragmentTransaction.addToBackStack(fragment.toString());

            fragmentTransaction.commit();

            if (currentFragment instanceof WidgetListFragment) {
                if (mToolbar.getVisibility() != View.VISIBLE)
                    mToolbar.setVisibility(View.VISIBLE);

            }


            if (currentFragment instanceof WidgetListFragment || currentFragment instanceof HomeFragment || currentFragment instanceof SplashFragment) {
                mToolbar.setNavigationIcon(null);
                mToolbar.setOnClickListener(null);

            } else {
                mShowNavigationBackListener();
            }
        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "mDisplayFragment is an issue");
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
            case Constants.TAG_CREATE_USER:
                this.mUserCreated(response);
                break;
            case Constants.TAG_LOAD_STREAM:
                this.mDisplayStreams(response, false);
                break;
            case Constants.WIDGET_SHORTLIST:
                this.mDisplayInstituteList(response, false);
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
            case Constants.WIDGET_COURSES:
                this.displayCourses(response);
                break;
            case Constants.TAG_LOAD_COURSES:
                this.mUpdateCourses(response);
                break;
            case Constants.TAG_APPLIED_COURSE:
                String tabposition = null;
                if (tags.length == 3) {
                    extraTag = tags[1];
                    tabposition = tags[2];
                }
                this.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(instituteCourseId, instituteCourseId).commit();
                this.mUpdateAppliedCourses(response, extraTag, tabposition);
                break;
            case Constants.TAG_LOAD_HOME:
                this.mUpdateHome(response);
                this.mNavigationDrawerFragment.setDrawerState(true);
                break;
            case Constants.TAG_POST_QUESTION:
                this.mInstituteQnAQuestionAdded(response);
                break;
            case Constants.TAG_LOAD_FILTERS:
                this.updateFilterList(response);
                break;
            case Constants.TAG_NEXT_INSTITUTE:
                this.updateInstituteList(response);
                break;
            case Constants.TAG_NEXT_NEWS:
                this.updateNewsList(response);
                break;
            case Constants.TAG_NEXT_ARTICLES:
                this.updateArticlesList(response);
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
            case Constants.TAG_LIKE_DISLIKE:
                if (tags.length == 2)
                    extraTag = tags[1];
                if (tags.length == 3) {
                    extraTag = tags[1];
                    like = tags[2];
                }
                this.updateLikeButton(response, extraTag, Integer.parseInt(like));
                break;
            case Constants.TAG_LOAD_PYSCHOMETRIC_TEST:
                this.loadPyschometricTest(response);
                break;
            case Constants.TAG_LOAD_QNA_QUESTIONS:
                this.mQnAQuestions.clear();
                this.mShowQnAQuestions(response);
                break;
            case Constants.WIDGET_FORUMS:
                this.mShowMyFBEnumeration(response);
                break;
            case Constants.TAG_VOTE_QNA_QUESTION_ENTITY:
                if (tags.length == 3) {
                    parentIndex = tags[1];
                    voteType = Integer.parseInt(tags[2]);
                    this.mQnAQuestionVoteUpdated(Integer.parseInt(parentIndex), voteType);
                }
                break;
            case Constants.TAG_VOTE_QNA_ANSWER_ENTITY:
                if (tags.length > 3) {
                    parentIndex = tags[1];
                    childIndex = tags[2];
                    voteType = Integer.parseInt(tags[3]);

                    this.mQnAAnswerVoteUpdated(Integer.parseInt(parentIndex), Integer.parseInt(childIndex), voteType);
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
            case Constants.TAG_QNA_ANSWER_SUBMITTED:
                if (tags.length > 1) {
                    parentIndex = tags[1];
                    childIndex = tags[2];

                    this.mOnAnswerAdded(response, Integer.parseInt(parentIndex), Integer.parseInt(childIndex));
                }
                break;
            case Constants.TAG_MY_FB_COMMENT_SUBMITTED:
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

                    this.mStreamAndCourseSelected(response, parentIndex, childIndex, extraTag);
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
            case Constants.TAG_USER_SIGNUP:
                if (tags.length > 0) {
                    parentIndex = tags[1];
                }
                    this.onSignUpResponse(response, parentIndex);
                break;
        }

        if (this.progressDialog != null && this.progressDialog.isShowing())
            this.progressDialog.dismiss();
    }


    private void mStreamAndLevelUpdated(String response){
        String token = user.getToken();
        String image = user.getImage();
        try {
            user = JSON.std.beanFrom(User.class, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //save the preferences locally
        user.setPref(User.Prefs.STREAMKNOWN);
        user.setToken(token);
        user.setImage(image);
        String u = null;
        try {
            u = JSON.std.asString(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
    }

    //Saved on DB, now save it in shared preferences.
    private void mStreamAndCourseSelected(String response, String levelURI, String streamURI, String streamName) {
        //Retrieve token from pref to save it across the pref updates
        String token = user.getToken();

        //TODO: May be we can make a new pref entry for token
        try {
            user = JSON.std.beanFrom(User.class, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //save the preferences locally
        user.setPref(User.Prefs.STREAMKNOWN);
        user.setLevel(levelURI);
        user.setStream(streamURI);
        user.setToken(token);

        if (streamName != "" && streamName != null)
            user.setStream_name(streamName);

        String u = null;
        try {
            u = JSON.std.asString(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
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
                user.setStream(Constants.BASE_URL + "streams/" + stream + "/");
            }
            if (psychometricResult.has("course_levels") && !psychometricResult.isNull("course_levels")) {
                level = psychometricResult.getString("course_levels");

                user.setLevel(Constants.BASE_URL + "level/" + level + "/");
            }

            if (stream != "" && level != "") {
                this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.COMPLETED_SECOND_STAGE, true).commit();

                try {
                    String u = JSON.std.asString(user);
                    this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();

                    hashMap.put("status", user.getPref().name().toLowerCase());
                    hashMap.put("stream", user.getStream());
                    hashMap.put("level", user.getLevel());

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
                    hashMap.put("stream", user.getStream());

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

    private void updateLikeButton(String response, String extraTag, int like) {
        Institute i = this.mInstituteList.get(Integer.parseInt(extraTag));
        if (like == Constants.NEITHER_LIKE_NOR_DISLIKE) {
            i.setCurrent_user_vote_type(Constants.NEITHER_LIKE_NOR_DISLIKE);
            i.setCurrent_user_vote_url(null);
        } else {
            try {
                i.setCurrent_user_vote_type(like);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (currentFragment instanceof InstituteListFragment) {
            ((InstituteListFragment) currentFragment).updateButtons(Integer.parseInt(extraTag));
        }
    }

    private void updateShortlistInstitute(String response, String extraTag) {
        Institute i = this.mInstituteList.get(Integer.parseInt(extraTag));
        String message = null;

        if (response == null) {
            i.setIs_shortlisted(Constants.SHORTLISTED_NO);
            message = " removed from your shortlist";
        } else {
            try {
                i.setIs_shortlisted(Constants.SHORTLISTED_YES);
                message = " added to your shortlist";
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (Integer.parseInt(extraTag) == currentInstitute)
            if (currentFragment instanceof InstituteDetailFragment)
                ((InstituteDetailFragment) currentFragment).updateInstituteShortlist();

        Toast.makeText(this, i.getShort_name() + message, Toast.LENGTH_SHORT).show();
    }

    private void displayCourses(String response) {

    }

    private void mDisplayNews(String response) {
        try {
            this.mNewsList = JSON.std.listOfFrom(News.class, extractResults(response));
            this.mParseSimilarNews(this.mNewsList);
            this.mDisplayFragment(NewsListFragment.newInstance(new ArrayList<>(this.mNewsList), this.mCurrentTitle, this.next), true, Constants.TAG_FRAGMENT_NEWS_LIST);
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
            this.mDisplayFragment(ArticleListFragment.newInstance(new ArrayList<>(this.mArticlesList), this.mCurrentTitle, this.next), true, Constants.TAG_FRAGMENT_ARTICLES_LIST);
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
                return "Creating a user...";
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
            /*case Constants.TAG_MY_FB_COMMENT_SUBMITTED:
                return "Submitting Comment...";*/
            case Constants.TAG_QNA_ANSWER_SUBMITTED:
                return "Submitting Answer...";
            case Constants.TAG_VOTE_QNA_QUESTION_ENTITY:
            case Constants.TAG_VOTE_QNA_ANSWER_ENTITY:
                return "Processing Vote...";
            case Constants.WIDGET_FORUMS:
                return "Loading Forums...";
            case Constants.TAG_SUBMIT_PSYCHOMETRIC_TEST:
                return "Submitting psychometric analysis...";
            case Constants.TAG_USER_SIGNUP:
                return "Signing User Please Wait.....";
        }
        return null;
    }

    private void mUpdateHome(String response) {
        try {
            this.mWidgets = JSON.std.listOfFrom(Widget.class, extractResults(response));
            this.mDisplayFragment(WidgetListFragment.newInstance(new ArrayList<>(this.mWidgets)), false, Constants.TAG_FRAGMENT_WIDGET_LIST);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mShowQnAQuestions(String response) {
        this.mDisplayFragment(QnAQuestionsListFragment.newInstance((this.parseAndReturnQnAList(response))), true, Constants.TAG_FRAGMENT_QNA_QUESTION_LIST);
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
        if (extraTag == null || tabPosition == null) return;

        try {
            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateAppliedCourses(response, Integer.parseInt(extraTag), Integer.parseInt(tabPosition));
            }

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
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void mHandleErrorResponse(String tag)
    {
        String extraTag = null;
        String childIndex = null;
        String parentIndex = null;
        String like = null;
        String[] tags = tag.split("#");

        switch (tags[0]) {
            case Constants.TAG_APPLIED_COURSE:
                String tabposition = null;
                if (tags.length == 3) {
                    extraTag = tags[1];
                    tabposition = tags[2];
                }
                this.mUpdateAppliedCourses(null, extraTag, tabposition);
                break;
            case Constants.TAG_SHORTLIST_INSTITUTE:
            case Constants.TAG_DELETESHORTLIST_INSTITUTE:
                if (tags.length == 2)
                    extraTag = tags[1];
            if (Integer.parseInt(extraTag) == currentInstitute)
                if (currentFragment instanceof InstituteDetailFragment)
                    ((InstituteDetailFragment) currentFragment).updateInstituteShortlist();
                break;
            case Constants.TAG_LIKE_DISLIKE:
                if (tags.length >= 2)
                    extraTag = tags[1];
                if (currentFragment instanceof InstituteListFragment) {
                    ((InstituteListFragment) currentFragment).updateButtons(Integer.parseInt(extraTag));
                }
                break;
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
    public void onStreamSelected(final String streamUri, final String streamName) {
        new AlertDialog.Builder(this)
                .setTitle("Please select a level")
                .setSingleChoiceItems(InstituteCourse.CourseLevel.getValues(), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOnCourseLevelSelected(which, streamUri, streamName);
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onStreamUpdated(final String uri, final String streamName) {
        getSupportFragmentManager().popBackStack();

        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_PROFILE);

        if (fragment == null)
            mDisplayFragment(ProfileFragment.newInstance(user), true, Constants.TAG_FRAGMENT_PROFILE);
        else {

            mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_PROFILE);
            if (fragment instanceof ProfileFragment) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ProfileFragment) fragment).updateStream(uri, streamName);
                    }
                });

            }
        }


    }

    private void mOnCourseLevelSelected(int level, String streamUri, String streamName) {
        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.COMPLETED_SECOND_STAGE, true).commit();

        String levelURI = Constants.BASE_URL + "level/" + (level + 1) + "/";

        //send them to DB
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("status", user.getPref().name().toLowerCase());
        hashMap.put("stream", streamUri);
        hashMap.put("user", user.getResource_uri());
        hashMap.put("level", levelURI);
        hashMap.put("stream_name", streamName);

        if (streamName != null || streamName != "")
            MainActivity.user.setStream_name(streamName);

        this.mMakeNetworkCall(Constants.TAG_SUBMIT_PREFRENCES + "#" + levelURI + "#" + streamUri + "#" + streamName, Constants.BASE_URL + "preferences/", hashMap);

        this.mMakeNetworkCall(Constants.TAG_LOAD_HOME, Constants.BASE_URL + "widgets/", null);
    }

    @Override
    public void onInstituteSelected(int position) {
        currentInstitute = position;
        this.mDisplayInstitute(position);
    }

    @Override
    public void onInstituteLikedDisliked(int position, int liked) {
        Institute institute = this.mInstituteList.get(position);
        if (institute.getCurrent_user_vote_type() == Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //neither liked nor disliked case
            if (liked == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_LIKE_DISLIKE + "#" + position + "#" + liked, institute.getResource_uri() + "upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_LIKE_DISLIKE + "#" + position + "#" + liked, institute.getResource_uri() + "downvote/", null, Request.Method.POST);
        } else if (institute.getCurrent_user_vote_type() != Constants.NEITHER_LIKE_NOR_DISLIKE && liked != Constants.NEITHER_LIKE_NOR_DISLIKE) {
            //either already liked or disliked case
            if (liked == Constants.LIKE_THING)
                this.mMakeNetworkCall(Constants.TAG_LIKE_DISLIKE + "#" + position + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE, institute.getResource_uri() + "upvote/", null, Request.Method.POST);
            else
                this.mMakeNetworkCall(Constants.TAG_LIKE_DISLIKE + "#" + position + "#" + Constants.NEITHER_LIKE_NOR_DISLIKE, institute.getResource_uri() + "downvote/", null, Request.Method.POST);
        }
    }

    @Override
    public void onFilterButtonClicked() {
        if (this.mFilters != "") {
            updateFilterList(this.mFilters);

            //mDisplayFragment(FilterFragment.newInstance(mFolderList), false);

            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_FILTER_LIST);
            if (fragment == null)
                mDisplayFragment(FilterFragment.newInstance(mFolderList), true, Constants.TAG_FRAGMENT_FILTER_LIST);
            else
                mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_FILTER_LIST);
        }
    }

    @Override
    public void onEndReached(String next, int listType) {
        if (next == null) return;
        if (listType == Constants.INSTITUTE_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_INSTITUTE, next, this.mFilterKeywords);
        else if (listType == Constants.NEWS_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_NEWS, next, null);
        else if (listType == Constants.ARTICLES_TYPE)
            this.mMakeNetworkCall(Constants.TAG_NEXT_ARTICLES, next, null);

    }

    public void updateFilterList(String response) {
        mFolderList = new ArrayList<>();
        try {
            Folder.populateFolderList(JSON.std.getStreamingFactory().createParser(response), mFolderList);
            //mDisplayFragment(FilterFragment.newInstance(mFolderList), false, Constants.TAG_FRAGMENT_FILTER_LIST);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onCourseApplied(int position, int tabPosition, InstituteCourse instituteCourse) {

        HashMap<String, String> map = new HashMap<>();
        /*SharedPreferences sp = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
        User  user = null;
        try {
            user = JSON.std.beanFrom(User.class, sp.getString(Constants.KEY_USER, null));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        if (user != null) {
            map.put("name", user.getName());
            map.put("email", user.getEmail());
        }

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        if (mPhoneNumber != null) {
            map.put("phone_no", mPhoneNumber);
        } else {
            if (user != null) map.put("phone_no", user.getPhone());
        }

        map.put("institute_course", "" + instituteCourse.getId());
        if (mInstitute != null) {
            map.put("institute", "" + mInstitute.getId());
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        map.put("year_of_admission", "" + year);

        String URL = Constants.BASE_URL + "lms/";
        instituteCourseId = "" + instituteCourse.getId();
        this.mMakeNetworkCall(Constants.TAG_APPLIED_COURSE + "#" + position + "#" + tabPosition, URL, map, Request.Method.POST);
    }

    @Override
    public void onWidgetSelected(Widget widget, int position) {
        this.mCurrentTitle = widget.getTitle();

        this.mUpdateNavigationItem(position);

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
        {
            progressDialog.dismiss();
        }

    }

    @Override
    public void onNewsSelected(News news, boolean addToBackstack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_NEWS);
        if (fragment == null)
            mDisplayFragment(NewsDetailFragment.newInstance(news, mNewsList), addToBackstack, Constants.TAG_FRAGMENT_NEWS);
        else {
            if (fragment instanceof NewsDetailFragment) {
                ((NewsDetailFragment) fragment).updateNews(news);
            }

            this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_NEWS);
        }
    }

    @Override
    public void onArticleSelected(Articles article, boolean addToBackstack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_ARTICLE);
        if (fragment == null)
            mDisplayFragment(ArticleDetailFragment.newInstance(article, mArticlesList), addToBackstack, Constants.TAG_FRAGMENT_ARTICLE);
        else {
            if (fragment instanceof ArticleDetailFragment) {
                ((ArticleDetailFragment) fragment).updateArticle(article);
            }

            this.mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_ARTICLE);
        }
    }

    @Override
    public void onQuestionAsked(QnAQuestions question) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", question.getTitle());
        map.put("desc", question.getDesc());
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

        for (Folder f : this.mFolderList) {
            for (Facet ft : f.getFacets())
                if (ft.isSelected() == 1)
                    this.mFilterKeywords.put("tag_uris[" + (count++) + "]", ft.getTag());
        }

        mFilterCount = this.mFilterKeywords.size();

        this.mMakeNetworkCall(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", this.mFilterKeywords);

        onBackPressed();

        this.mUpdateFilterButton();

        //save preferences.
        this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).commit();

       /* try
        {
            //Filters filters = JSON.std.beanFrom(Filters.class, this.mFilterKeywords.toString());

            //String f = JSON.std.asString(filters);
        }
        catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onFilterCanceled(boolean clearAll) {
        onBackPressed();

        if (clearAll) {
            for (Folder f : this.mFolderList) {
                for (Facet ft : f.getFacets())
                    ft.deselect();
            }

            mFilterCount = 0;

            this.mFilterKeywords = new HashMap<>();

            //reset the filters in preferences
            this.getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, this.mFilterKeywords.toString()).commit();

            this.mMakeNetworkCall(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", null);
        } else {
            mFilterCount = 0;
            for (Folder f : this.mFolderList) {
                for (Facet ft : f.getFacets())
                    if (ft.isSelected() == 1)
                        mFilterCount++;
            }

            if (mFilterCount == 2)
                mFilterCount = 0;
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
        Institute i = mInstituteList.get(currentInstitute);
        if (i.getIs_shortlisted() == Constants.SHORTLISTED_NO) {
            this.mMakeNetworkCall(Constants.TAG_SHORTLIST_INSTITUTE + "#" + currentInstitute, i.getResource_uri() + "shortlist/", null, Request.Method.POST);
        } else {
            this.mMakeNetworkCall(Constants.TAG_DELETESHORTLIST_INSTITUTE + "#" + currentInstitute, i.getResource_uri() + "shortlist/", null, Request.Method.DELETE);
        }
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
        Toast.makeText(MainActivity.this, resourceURI + " " + voteType, Toast.LENGTH_SHORT).show();
        if (voteType == Constants.LIKE_THING)
            this.mMakeNetworkCall(Constants.TAG_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "upvote/", null, Request.Method.POST);
        else
            this.mMakeNetworkCall(Constants.TAG_VOTE_QNA_QUESTION_ENTITY + "#" + String.valueOf(position) + "#" + String.valueOf(voteType), resourceURI + "downvote/", null, Request.Method.POST);
    }

    @Override
    public void onQnAAnswerVote(String resourceURI, int voteType, int answerPosition, int questionPosition) {
        if (voteType == Constants.LIKE_THING)
            this.mMakeNetworkCall(Constants.TAG_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType), resourceURI + "upvote/", null, Request.Method.POST);
        else
            this.mMakeNetworkCall(Constants.TAG_VOTE_QNA_ANSWER_ENTITY + "#" + String.valueOf(questionPosition) + "#" + String.valueOf(answerPosition) + "#" + String.valueOf(voteType), resourceURI + "downvote/", null, Request.Method.POST);
    }

    @Override
    public void onQnAAnswerSubmitted(String questionURI, String answerText, int questionIndex, int answerIndex) {
        HashMap<String, String> params = new HashMap<>();
        params.put("answer_text", answerText);
        this.mMakeNetworkCall(Constants.TAG_QNA_ANSWER_SUBMITTED + "#" + String.valueOf(questionIndex) + "#" + String.valueOf(answerIndex), questionURI + "answer/", params, Request.Method.POST);
    }

    private void mQnAQuestionVoteUpdated(int questionIndex, int voteType) {
        ((QnAQuestionsAndAnswersFragment) currentFragment).onVotingFeedback(questionIndex, -1, voteType);
    }

    private void mQnAAnswerVoteUpdated(int questionIndex, int answerIndex, int voteType) {
        ((QnAQuestionsAndAnswersFragment) currentFragment).onVotingFeedback(questionIndex, answerIndex, voteType);
    }

    private void mOnAnswerAdded(String response, int questionIndex, int index) {
        try {
            JSONObject ans = new JSONObject(response);

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
            qnaAnswer.setIndex(index);
            qnaAnswer.setQuestionIndex(questionIndex);

            if (currentFragment instanceof QnAQuestionsAndAnswersFragment)
                ((QnAQuestionsAndAnswersFragment) currentFragment).answerAdded(qnaAnswer);
            else if (currentFragment instanceof InstituteQnAFragment)
                ((InstituteQnAFragment) currentFragment).instituteQnAAnswerUpdated(qnaAnswer);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position, int commentsCount) {
        this.mMakeNetworkCall(Constants.TAG_LOAD_MY_FB + "#" + String.valueOf(position) + "#" + String.valueOf(commentsCount), myFutureBuddiesEnumeration.getResource_uri(), null, Request.Method.GET);
    }

    @Override
    public void onMyFBCommentSubmitted(String myFbURI, String commentText, int myFbIndex, int myFbCommentIndex) {
        HashMap<String, String> params = new HashMap<>();
        params.put("comment", commentText);
        this.mMakeNetworkCall(Constants.TAG_MY_FB_COMMENT_SUBMITTED + "#" + String.valueOf(myFbIndex) + "#" + String.valueOf(myFbCommentIndex), myFbURI + "comment/", params, Request.Method.POST);
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
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void mInstituteQnAQuestionAdded(String response) {
        try {
            ArrayList<String> tagArrayList = new ArrayList<>();
            ArrayList<QnAAnswers> qnaAnswers = new ArrayList<>();

            JSONObject qns = new JSONObject(response);

            QnAQuestions qnaQuestion = new QnAQuestions();

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
            //qnaQuestion.setIndex(i);

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
                //qnaAnswer.setQuestionIndex(i);

                qnaAnswers.add(qnaAnswer);
            }

            JSONArray tagsList = qns.getJSONArray("tags");

            for (int k = 0; k < tagsList.length(); k++) {
                tagArrayList.add(tagsList.getString(k));
            }

            qnaQuestion.setAnswer_set(qnaAnswers);
            qnaQuestion.setTags(tagArrayList);


            if (currentFragment instanceof InstituteDetailFragment)
                ((InstituteDetailFragment) currentFragment).instituteQnAQuestionAdded(qnaQuestion);

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

    private void mDisplayPofile()
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_PROFILE);
        if (fragment == null)
            mDisplayFragment(ProfileFragment.newInstance(user), true, Constants.TAG_FRAGMENT_PROFILE);
        else
            mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_PROFILE);

    }


    @Override
    public void onProfileUpdated(HashMap<String, String> hashMap) {

        //reset the filters in preferences and update institueLists
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

    @Override
    public void onStreamClicked() {

        this.mMakeNetworkCall(Constants.TAG_UPDATE_STREAM, Constants.BASE_URL + "streams/", null);
    }

    private void mShowNavigationBackListener() {
        mToolbar.setNavigationIcon(R.drawable.arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                if (isLastFragment()) {
                    mClearBackStack();
                } else {
                    int count = getSupportFragmentManager().getBackStackEntryCount();
                    if (count >= 1) {
                        getSupportFragmentManager().popBackStack();
                    }
                }

            }
        });
    }

    private void mUpdateInstituteList(String response) {
        try {
            this.mInstituteList = JSON.std.listOfFrom(Institute.class, this.extractResults(response));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        if(this.mNavigationDrawerFragment !=  null && this.mNavigationDrawerFragment.isDrawerOpen())
                this.mNavigationDrawerFragment.closeDrawer();
        else if(isLastFragment())
        {
            mClearBackStack();
        }
        else
            super.onBackPressed();
    }


   private  void mClearBackStack() {
       int count = getSupportFragmentManager().getBackStackEntryCount();
       for (int i = 0; i < count ; i++) {
           getSupportFragmentManager().popBackStack();
       }
    }

    /**
     * This method is used to check about fragment is last in arrow_back stack
     * @return
     */
    private boolean isLastFragment()
    {
        if(currentFragment instanceof  InstituteListFragment || currentFragment instanceof  NewsListFragment
                || currentFragment instanceof  ArticleListFragment || currentFragment instanceof QnAQuestionsListFragment
                || currentFragment instanceof  MyFutureBuddiesEnumerationFragment)
            return true;
        else
            return false;
    }

    @Override
    public void onUserSignUp(String value) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_SIGNUP);
        if (fragment == null)
            mDisplayFragment(SignUpFragment.newInstance(value,true), true, Constants.TAG_FRAGMENT_SIGNUP);
        else
            mDisplayFragment(fragment, false, Constants.TAG_FRAGMENT_SIGNUP);
    }

    @Override
    public void onUserSignedIn(HashMap hashMap, String msg) {
        if(hashMap == null)return;
       if(user != null) {
           hashMap.put(Constants.USER_STREAM, user.getStream());
           hashMap.put(Constants.USER_RESOURCE_URI, user.getResource_uri());
           hashMap.put(Constants.USER_LEVEL, user.getLevel());
           hashMap.put(Constants.USER_STREAM_NAME, user.getStream_name());
           hashMap.put(Constants.USER_LEVEL_NAME, user.getLevel_name());

           //TODO:: remove when server side is done
           user.setImage(""+hashMap.get(Constants.USER_IMAGE));

       }
            this.mMakeNetworkCall(Constants.TAG_USER_SIGNUP+"#"+msg,  Constants.BASE_URL + "preferences/", hashMap);

    }

    private void onSignUpResponse(String response, String msg)
    {
        this.mStreamAndLevelUpdated(response);
        if(msg != null && !msg.isEmpty())
        {
            getSupportFragmentManager().popBackStack();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.TAG_FRAGMENT_MY_FB);
            if(fragment != null)
            {
                ((MyFutureBuddiesFragment)fragment).sendChatRequest(msg);
            }
        }
    }

    public static void SendEvent(String category, String action, String label)
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
}