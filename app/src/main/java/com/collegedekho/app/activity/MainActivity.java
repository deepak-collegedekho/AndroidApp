package com.collegedekho.app.activity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.collegedekho.app.DataLoadListener;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.fragment.HomeFragment;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.fragment.NavigationDrawerFragment;
import com.collegedekho.app.fragment.StreamFragment;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.NetworkUtils;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, HomeFragment.OnHomeInteractionListener, DataLoadListener, StreamFragment.OnStreamInteractionListener, InstituteListFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    NetworkUtils mNetworkUtils;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
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
        mTitle = getTitle();
        // Set up the drawer.
    /*    mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));*/
        if (user != null) {
            if (!completedStage2)
                setUserPref();
        } else {
            onNavigationDrawerItemSelected(0);
        }
        mNetworkUtils.getData(Constants.TAG_SEARCH, Constants.BASE_URL + "institutes/");
    }

    private void init() {
        mNetworkUtils = new NetworkUtils(this, this);
        SharedPreferences sp = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
        try {
            if (sp.contains(Constants.KEY_USER))
                user = JSON.std.beanFrom(User.class, sp.getString(Constants.KEY_USER, null));
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

    public void onSectionAttached(int number) {
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
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHomeItemSelected(User.Prefs preference) {
        requestUserCreation();
        userPref = preference;
    }

    private void requestUserCreation() {
        mNetworkUtils.postData(Constants.TAG_CREATE_USER, Constants.BASE_URL + "users/anonymous/", new HashMap<String, String>());
    }

    private void setUser(String json) {
        try {
            user = JSON.std.beanFrom(User.class, json);
            user.setPref(userPref);
            String u = JSON.std.asString(user);
            getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
            setUserPref();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("status", user.getPref().name().toLowerCase());
            hashMap.put("user", user.getUrl());
            mNetworkUtils.postData("", Constants.BASE_URL + "preferences/", hashMap);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void setUserPref() {
        switch (user.getPref()) {
            case NOT_SURE:
                loadQuestionaire();
                break;
            case SURE:
                displaySearch();
                break;
            case STREAMKNOWN:
                mNetworkUtils.getData(Constants.TAG_LOAD_STREAM, Constants.BASE_URL + "streams/");
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
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,
                    StreamFragment.newInstance(new ArrayList<Stream>(streams))).commit();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void displayInstituteList(String response) {
        try {
            List<Institute> institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,
                    InstituteListFragment.newInstance(new ArrayList<Institute>(institutes)))
                    .setCustomAnimations(R.anim.slide_left_to_right, R.anim.slide_right_to_left).commit();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void displaySearch() {

    }

    private void loadQuestionaire() {

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
            case Constants.TAG_SEARCH:
                displayInstituteList(response);
                break;
        }

    }

    @Override
    public void onError(String tag, String response) {
        new AlertDialog.Builder(this).setMessage(response).show();
    }

    @Override
    public void onStreamSelected(String uri) {
        getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit().putBoolean(Constants.COMPLETED_SECOND_STAGE, true).commit();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("stream", uri);
        hashMap.put("user", user.getUrl());
        mNetworkUtils.postData("", Constants.BASE_URL + "preferences/", hashMap);
        mNetworkUtils.getData(Constants.TAG_SEARCH, Constants.BASE_URL + "search/institutes/?stream=" + uri);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
