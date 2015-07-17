package com.collegedekho.app.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.Question;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.entities.Widget;
import com.collegedekho.app.fragment.FilterFragment;
import com.collegedekho.app.fragment.HomeFragment;
import com.collegedekho.app.fragment.InstituteDetailFragment;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.fragment.InstituteQnAFragment;
import com.collegedekho.app.fragment.NavigationDrawerFragment;
import com.collegedekho.app.fragment.NewsDetailFragment;
import com.collegedekho.app.fragment.NewsListFragment;
import com.collegedekho.app.fragment.StreamFragment;
import com.collegedekho.app.fragment.WidgetListFragment;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.listener.OnApplyClickedListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.NetworkUtils;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        HomeFragment.OnHomeInteractionListener,
        DataLoadListener,
        StreamFragment.OnStreamInteractionListener,
        InstituteListFragment.OnInstituteSelectedListener,
        OnApplyClickedListener, WidgetListFragment.OnWidgetInteractionListener,
        NewsListFragment.OnNewsSelectedListener, InstituteQnAFragment.OnQuestionAskedListener,
        FilterFragment.OnFilterInteractionListener {

    private static final String TAG = "MainActivity";
    NetworkUtils mNetworkUtils;
    List<Institute> institutes;
    int currentInstitute;
    ProgressDialog progressDialog;
    String currentTitle;
    Fragment currentFragment;
    String currentPage2;
    String[] filterTypes = {"Specialization", "Location", "Degrees", "College Type", "Facilities", "Hostel"};
    String[] randomFacets = {"Relax", "Relax", "It's Just a Little", "Pin Prick", "There",
            "Is", "no", "Place", "You r receding", "The Distance", "Shift in the", "Horizon",
            "I have become", "Comfortably Numb", "Another Brick", "In the wall", "Ok"};
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    //private NavigationDrawerFragment mNavigationDrawerFragment;
    /*
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    // private CharSequence mTitle;
    private User user;
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
    private ArrayList<Folder> mFolderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);*/
        //mTitle = getTitle();
        // Set up the drawer.
    /*    mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));*/
        if (user != null) {
            if (!completedStage2)
                setUserPref();
            else {
                networkData(Constants.TAG_LOAD_HOME, Constants.BASE_URL + "widgets/", null);
            }
        } else {
            onNavigationDrawerItemSelected(0);
        }
    }

    private void init() {
        mNetworkUtils = new NetworkUtils(this, this);
        SharedPreferences sp = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
        try {
            if (sp.contains(Constants.KEY_USER)) {
                user = JSON.std.beanFrom(User.class, sp.getString(Constants.KEY_USER, null));
                mNetworkUtils.setToken(user.getToken());
            }
            completedStage2 = sp.getBoolean(Constants.COMPLETED_SECOND_STAGE, false);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeFragment.newInstance();
                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
/*        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHomeItemSelected(User.Prefs preference) {
        requestUserCreation();
        userPref = preference;
    }

    private void requestUserCreation() {
        if (user == null)
            networkData(Constants.TAG_CREATE_USER, Constants.BASE_URL + "users/anonymous/", new HashMap<String, String>());
        else
            setUserPref();
    }

    private void setUser(String json) {
        try {
            user = JSON.std.beanFrom(User.class, json);
            mNetworkUtils.setToken(user.getToken());
            user.setPref(userPref);
            String u = JSON.std.asString(user);
            getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
            setUserPref();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void setUserPref() {
        switch (user.getPref()) {
            case NOT_SURE:
                loadQuestionaire();
                break;
            case STREAMKNOWN:
                networkData(Constants.TAG_LOAD_STREAM, Constants.BASE_URL + "streams/", null);
                break;
        }
    }

    private String extractResults(String response) {
        try {
            //   JsonFactory jf = new JsonFactory();
            //Result r = Result.createFromJson(jf.createParser(json2));
            Map<String, Object> map = JSON.std.mapFrom(response);
            return JSON.std.asString(map.get("results"));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    private void displayStreams(String response) {
        try {
            List<Stream> streams = JSON.std.listOfFrom(Stream.class, extractResults(response));
            displayFragment(StreamFragment.newInstance(new ArrayList<>(streams)), false);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /*private void displaySearch() {

    }*/

    private void displayInstituteList(String response) {
        try {
            institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            displayFragment(InstituteListFragment.newInstance(new ArrayList<>(institutes), currentTitle), true);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void displayInstitute(int position) {
        displayFragment(InstituteDetailFragment.newInstance(institutes.get(position)), true);
        networkData(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "personalize/institutecourses/", null);
    }

    private void loadQuestionaire() {
        new AlertDialog.Builder(this).setMessage("This feature is coming soon!").setTitle("Coming Soon!").setNeutralButton("OK", null)
                .show();
    }

    private void displayFragment(Fragment fragment, boolean addToBackstack) {
        currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setCustomAnimations(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
        if (addToBackstack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDataLoaded(String tag, String response) {
        switch (tag) {
            case Constants.TAG_CREATE_USER:
                setUser(response);
                break;
            case Constants.TAG_LOAD_STREAM:
                displayStreams(response);
                break;
            case Constants.WIDGET_INSTITUTES:
                displayInstituteList(response);
                break;
            case Constants.WIDGET_NEWS:
                displayNews(response);
                break;
            case Constants.WIDGET_ARTICES:
                displayNews(response);
                break;
            case Constants.WIDGET_COURSES:
                displayCourses(response);
                break;
            case Constants.TAG_LOAD_COURSES:
                updateCourses(response);
                break;
            case Constants.TAG_LOAD_HOME:
                updateHome(response);
                break;
            case Constants.TAG_POST_QUESTION:
                new AlertDialog.Builder(this).setMessage("Thank you for posting your question.").show();
                break;
            case Constants.TAG_LOAD_FILTERS:
                updateFilterList(response);
                break;
        }
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void displayCourses(String response) {

    }

    private void displayNews(String response) {
        try {
            List<News> newsList = JSON.std.listOfFrom(News.class, extractResults(response));
            displayFragment(NewsListFragment.newInstance(new ArrayList<>(newsList), currentTitle), true);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private String getPersonalizedMessage(String tag) {
        switch (tag) {
            case Constants.TAG_CREATE_USER:
                return "Creating a user.";
            case Constants.TAG_LOAD_STREAM:
                return "Loading Streams.";
            case Constants.WIDGET_INSTITUTES:
                return "Loading Institutes.";
            case Constants.WIDGET_NEWS:
                return "Loading News.";
            case Constants.WIDGET_ARTICES:
                return "Loading Articles.";
            case Constants.WIDGET_COURSES:
                return "Loading Courses.";
            case Constants.TAG_LOAD_HOME:
                return "Loading";
            case Constants.TAG_POST_QUESTION:
                return "Posting your question.";
            case Constants.TAG_LOAD_FILTERS:
                return "Loading Filters";
        }
        return null;
    }

    private void updateHome(String response) {
        try {
            List<Widget> widgets = JSON.std.listOfFrom(Widget.class, extractResults(response));
            displayFragment(WidgetListFragment.newInstance(new ArrayList<>(widgets)), false);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void updateCourses(String response) {
        try {
            if (currentFragment != null && currentFragment instanceof InstituteDetailFragment) {
                List<InstituteCourse> instituteCourses = JSON.std.listOfFrom(InstituteCourse.class, extractResults(response));
                ((InstituteDetailFragment) currentFragment).updateCourses(instituteCourses);
            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onError(String tag, String response) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        new AlertDialog.Builder(this).setMessage(response).show();
    }

    @Override
    public void onStreamSelected(final String streamUri) {
        new AlertDialog.Builder(this)
                .setTitle("Please select a level")
                .setSingleChoiceItems(InstituteCourse.CourseLevel.getValues(), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onCourseLevelSelected(which, streamUri);
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void onCourseLevelSelected(int level, String streamUri) {
        getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.COMPLETED_SECOND_STAGE, true).commit();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status", user.getPref().name().toLowerCase());
        hashMap.put("stream", streamUri);
        hashMap.put("user", user.getResource_uri());
        hashMap.put("level", Constants.BASE_URL + "level/" + (level + 1) + "/");
        networkData("", Constants.BASE_URL + "preferences/", hashMap);
        networkData(Constants.TAG_LOAD_HOME, Constants.BASE_URL + "widgets/", null);
    }

    @Override
    public void onInstituteSelected(int position) {
        currentInstitute = position;
        displayInstitute(position);
    }

    @Override
    public void onInstituteLikedDisliked(int position, int liked) {
        //networkData("",);
    }

    @Override
    public void onFilterButtonClicked() {
        if (mFolderList == null)
            networkData(Constants.TAG_LOAD_FILTERS, Constants.BASE_URL + "personalize/institute_filters/", null);
        else
            displayFragment(FilterFragment.newInstance(mFolderList), true);
    }

    public void updateFilterList(String response) {
        mFolderList = new ArrayList<>();
        try {
            Folder.populateFolderList(JSON.std.getStreamingFactory().createParser(response), mFolderList);
            displayFragment(FilterFragment.newInstance(mFolderList), true);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void createDummyContent() {
        for (int i = 0; i < filterTypes.length; i++) {
            Folder f = new Folder(i, filterTypes[i]);
            Random random = new Random();
            for (String randomFacet : randomFacets) {
                int j = random.nextInt(randomFacets.length);
                int l = random.nextInt(2);
                f.addFacet(randomFacets[j], randomFacets[j].replace(" ", "-").toLowerCase(), l);
            }
            mFolderList.add(f);
        }
    }

    @Override
    public void onCourseApplied(long id) {
        /*if (id == -1) {

        } else {

        }*/
    }

    @Override
    public void onWidgetSelected(Widget widget) {
        currentTitle = widget.getTitle();
        switch (widget.getAction_method()) {
            case Constants.METHOD_GET:
                networkData(widget.getType(), widget.getAction_url(), null);
                break;
            case Constants.METHOD_POST:
                //networkData(widget.getType(),widget.getAction_url());
                break;
        }
    }

    private void networkData(String tag, String url, HashMap<String, String> params) {
        String message = getPersonalizedMessage(tag);
        if (message != null)
            showProgressDialog(message);
        if (params != null)
            mNetworkUtils.postData(tag, url, params);
        else
            mNetworkUtils.getData(tag, url);
    }

    private void showProgressDialog(String message) {
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

    @Override
    public void onNewsSelected(News news) {
        displayFragment(NewsDetailFragment.newInstance(news), true);
    }

    @Override
    public void onQuestionAsked(Question question) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", question.title);
        map.put("desc", question.content);
        map.put("institute", "" + institutes.get(currentInstitute).getResource_uri());
        map.put("user", user.getResource_uri());
        map.put("stream", Constants.BASE_URL + "streams/1/");
        networkData(Constants.TAG_POST_QUESTION, Constants.BASE_URL + "qna/questions/", map);
    }

    @Override
    public void onFilterApplied() {

    }

    @Override
    public void onFilterCanceled() {

    }

    @Override
    public void onFilterTypeChanged(int position) {
        if (currentFragment instanceof FilterFragment) {
            ((FilterFragment) currentFragment).updateFilterType(position);
        }
    }


}
