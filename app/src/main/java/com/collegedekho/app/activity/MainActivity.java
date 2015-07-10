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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.entities.Widget;
import com.collegedekho.app.fragment.CollegeDetailFragment;
import com.collegedekho.app.fragment.HomeFragment;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.fragment.NavigationDrawerFragment;
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


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        HomeFragment.OnHomeInteractionListener,
        DataLoadListener,
        StreamFragment.OnStreamInteractionListener,
        InstituteListFragment.OnInstituteSelectedListener,
        OnApplyClickedListener, WidgetListFragment.OnWidgetInteractionListener {

    private static final String TAG = "MainActivity";
    NetworkUtils mNetworkUtils;
    CollegeDetailFragment cd;
    List<Institute> institutes;
    ProgressDialog progressDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //    Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        //    setSupportActionBar(toolbar);
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
            else
                networkData(Constants.TAG_LOAD_HOME, Constants.BASE_URL + "widgets/", null);
        } else {
            onNavigationDrawerItemSelected(0);
        }
    }

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
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("status", user.getPref().name().toLowerCase());
            hashMap.put("user", user.getUrl());
            networkData("", Constants.BASE_URL + "preferences/", hashMap);
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

    private void displayInstituteList(String response) {
        try {
            institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            displayFragment(InstituteListFragment.newInstance(new ArrayList<>(institutes)), true);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void displayInstitute(int position) {
        cd = CollegeDetailFragment.newInstance(institutes.get(position));
        displayFragment(cd, true);
        networkData(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "personalize/institutecourses/", null);
    }

    /*private void displaySearch() {

    }*/

    private void loadQuestionaire() {
        new AlertDialog.Builder(this).setMessage("This feature is coming soon!").setTitle("Coming Soon!").setNeutralButton("OK", null)
                .show();
    }

    private void displayFragment(Fragment fragment, boolean addToBackstack) {
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
            case Constants.TAG_LOAD_COURSES:
                updateCourses(response);
                break;
            case Constants.TAG_LOAD_HOME:
                updateHome(response);
                break;
        }
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private String getPersonalizedMessage(String tag) {
        switch (tag) {
            case Constants.TAG_CREATE_USER:
                return "Creating a user.";
            case Constants.TAG_LOAD_STREAM:
                return "Loading Streams.";
            case Constants.WIDGET_INSTITUTES:
                return "Loading Institutes.";
            case Constants.TAG_LOAD_HOME:
                return "Loading";
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
            if (cd != null) {
                List<InstituteCourse> instituteCourses = JSON.std.listOfFrom(InstituteCourse.class, extractResults(response));
                cd.updateCourses(instituteCourses);
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
        hashMap.put("stream", streamUri);
        hashMap.put("user", user.getUrl());
        hashMap.put("level", "" + level);
        networkData("", Constants.BASE_URL + "preferences/", hashMap);
        networkData(Constants.TAG_LOAD_HOME, Constants.BASE_URL + "widgets/", null);
    }

    @Override
    public void onCollegeSelected(int position) {
        displayInstitute(position);
    }

    @Override
    public void onCourseApplied(long id) {
        /*if (id == -1) {

        } else {

        }*/
    }

    @Override
    public void onWidgetSelected(Widget widget) {
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

}
