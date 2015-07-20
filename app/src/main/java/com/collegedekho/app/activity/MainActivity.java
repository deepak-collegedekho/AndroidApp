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
import android.widget.Toast;

import com.android.volley.Request;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.PQuestion;
import com.collegedekho.app.entities.Question;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.entities.Widget;
import com.collegedekho.app.fragment.FilterFragment;
import com.collegedekho.app.fragment.HomeFragment;
import com.collegedekho.app.fragment.InstituteDetailFragment;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
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
        FilterFragment.OnFilterInteractionListener, InstituteOverviewFragment.OnInstituteShortlistedListener {

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
    String next;
    // String previousCall;
    // String previousTag;
    List<PQuestion> questions;
    int filterCount;
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
    //private Map<String, String> previousParams;
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
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        /*mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);*/
        //mTitle = getTitle();
        // Set up the drawer.
    /*    mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));*/
        if (user != null && user.getPref() == User.Prefs.STREAMKNOWN) {
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
        userPref = preference;
        requestUserCreation();
    }

    private void requestUserCreation() {
        if (user == null)
            networkData(Constants.TAG_CREATE_USER, Constants.BASE_URL + "users/anonymous/", new HashMap<String, String>());
        else {
            user.setPref(userPref);
            setUserPref();
        }
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

    /*private void displaySearch() {

    }*/

    private String extractResults(String response) {
        try {
            //   JsonFactory jf = new JsonFactory();
            //Result r = Result.createFromJson(jf.createParser(json2));
            Map<String, Object> map = JSON.std.mapFrom(response);
            if (map.get("next") != null)
                next = map.get("next").toString();
            else
                next = null;
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

    private void updateInstituteList(String response) {
        try {
            List<Institute> institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            this.institutes.addAll(institutes);
            if (currentFragment instanceof InstituteListFragment) {
                ((InstituteListFragment) currentFragment).updateList(institutes, next);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void displayInstituteList(String response, boolean filterAllowed) {
        try {
            institutes = JSON.std.listOfFrom(Institute.class, extractResults(response));
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);
            if (fragment == null)
                displayFragment(InstituteListFragment.newInstance(new ArrayList<>(institutes), currentTitle, next, filterAllowed), true, Constants.TAG_FRAGMENT_INSTITUTE_LIST);
            else {
                if (fragment instanceof InstituteListFragment) {
                    ((InstituteListFragment) fragment).clearList();
                    ((InstituteListFragment) fragment).updateList(institutes, next);
                }
                displayFragment(fragment, false, Constants.TAG_FRAGMENT_INSTITUTE_LIST);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void displayQuestionnaire(String response) {
        /*new AlertDialog.Builder(this).setMessage("This feature is coming soon!").setTitle("Coming Soon!").setNeutralButton("OK", null)
                .show();*/
        try {
            questions = JSON.std.listOfFrom(PQuestion.class,response);
//            new AlertDialog.Builder(this).setMessage("This feature is coming soon!").setTitle("Coming Soon!").setNeutralButton("OK", null)
//                .show();
            //displayFragment(InstituteListFragment.newInstance(new ArrayList<>(institutes), currentTitle), false);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    //TODO change to personalize institute course
    private void displayInstitute(int position) {
        displayFragment(InstituteDetailFragment.newInstance(institutes.get(position)), true);
        networkData(Constants.TAG_LOAD_COURSES, Constants.BASE_URL + "institutecourses/" + "?institute=" + institutes.get(position).getId(), null);
    }

    private void loadQuestionaire() {
        networkData(Constants.TAG_LOAD_QUESTIONNAIRE, Constants.BASE_URL + "questionnaire/", null);
//        new AlertDialog.Builder(this).setMessage("This feature is coming soon!").setTitle("Coming Soon!").setNeutralButton("OK", null)
//                .show();
    }

    private void displayFragment(Fragment fragment, boolean addToBackstack, String tag) {
        currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container, fragment, tag);
        if (addToBackstack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void displayFragment(Fragment fragment, boolean addToBackstack) {
        displayFragment(fragment, addToBackstack, null);
    }

    @Override
    public void onDataLoaded(String tag, String response) {
        String[] tags = tag.split("#");
        String extraTag = null;
        String like = null;
        if (tags.length == 2)
            extraTag = tags[1];
        if (tags.length == 3) {
            extraTag = tags[1];
            like = tags[2];
        }
        switch (tags[0]) {
            case Constants.TAG_CREATE_USER:
                setUser(response);
                break;
            case Constants.TAG_LOAD_STREAM:
                displayStreams(response);
                break;
            case Constants.WIDGET_SHORTLIST:
                displayInstituteList(response, false);
                break;
            case Constants.WIDGET_INSTITUTES:
                displayInstituteList(response, true);
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
            case Constants.TAG_NEXT_INSTITUTE:
                updateInstituteList(response);
                break;
            case Constants.TAG_SHORTLIST_INSTITUTE:
                updateShortlistInstitute(response, extraTag);
                break;
            case Constants.TAG_DELETESHORTLIST_INSTITUTE:
                updateShortlistInstitute(null, extraTag);
                break;
            case Constants.TAG_LIKE_DISLIKE:
                updateLikeButton(response, extraTag, Integer.parseInt(like));
                break;
            case Constants.TAG_LOAD_QUESTIONNAIRE:
                displayQuestionnaire(response);
                break;
        }
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void updateLikeButton(String response, String extraTag, int like) {
        Institute i = institutes.get(Integer.parseInt(extraTag));
        if (like == -1) {
            i.setCurrent_user_vote_type(-1);
            i.setCurrent_user_vote_url(null);
        } else {
            try {
                Map<String, Object> m = JSON.std.mapFrom(response);
                i.setCurrent_user_vote_url(m.get("resource_uri").toString());
                i.setCurrent_user_vote_type(like);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (currentFragment instanceof InstituteListFragment) {
            ((InstituteListFragment) currentFragment).updateButtons(Integer.parseInt(extraTag));
        }
        /*String message;
        switch (like){
            case Constants.LIKE_COLLEGE:
                message = "Liked ";
                break;
            case Constants.DISLIKE_COLLEGE:
                message = "Disliked ";
                break;
            default: message = null;
                break;
        }
        if(message!=null)
            Toast.makeText(this,message+i.getShort_name(),Toast.LENGTH_SHORT).show();*/
    }

    private void updateShortlistInstitute(String response, String extraTag) {
        Institute i = institutes.get(Integer.parseInt(extraTag));
        String message = null;
        if (response == null) {
            i.setCurrent_user_shortlist_url(null);
            message = " removed from your shortlist";
        } else {
            try {
                message = " added to your shortlist";
                Map<String, Object> m = JSON.std.mapFrom(response);
                i.setCurrent_user_shortlist_url(m.get("resource_uri").toString());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (Integer.parseInt(extraTag) == currentInstitute)
            if (currentFragment instanceof InstituteDetailFragment) {
                ((InstituteDetailFragment) currentFragment).updateInstituteShortlist();
            }

        Toast.makeText(this, i.getShort_name() + message, Toast.LENGTH_SHORT).show();
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
            case Constants.TAG_LOAD_QUESTIONNAIRE:
                return "Loading Questionnaire";

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
    public void onError(final String tag, String response, final String url, final Map<String, String> params) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        new AlertDialog.Builder(this)
                .setMessage(response)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        networkData(tag, url, params);
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
        if (institutes.get(position).getCurrent_user_vote_type() == -1) {
            Map<String, String> params = new HashMap<>();
            params.put("vote_type", "" + liked);
            params.put("institute", institutes.get(position).getResource_uri());
            params.put("user", user.getResource_uri());
            networkData(Constants.TAG_LIKE_DISLIKE + "#" + position + "#" + liked, Constants.BASE_URL + "institutevotes/", params);
        } else if (institutes.get(position).getCurrent_user_vote_type() != -1 && liked != -1) {
            Map<String, String> params = new HashMap<>();
            params.put("vote_type", "" + liked);
            params.put("institute", institutes.get(position).getResource_uri());
            params.put("user", user.getResource_uri());
            networkData(Constants.TAG_LIKE_DISLIKE + "#" + position + "#" + liked, institutes.get(position).getCurrent_user_vote_url(), params, Request.Method.PUT);
        } else {
            networkData(Constants.TAG_LIKE_DISLIKE + "#" + position + "#" + liked, institutes.get(position).getCurrent_user_vote_url(), null, Request.Method.DELETE);
        }

    }

    @Override
    public void onFilterButtonClicked() {
        if (mFolderList == null)
            networkData(Constants.TAG_LOAD_FILTERS, Constants.BASE_URL + "personalize/institutes/filters/", null);
        else
            displayFragment(FilterFragment.newInstance(mFolderList), true);
    }

    @Override
    public void onEndReached(String next) {
        if (next != null)
            networkData(Constants.TAG_NEXT_INSTITUTE, next, null);
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

    private void networkData(String tag, String url, Map<String, String> params, int method) {
        // previousCall = url;
//        previousParams = params;
//        previousTag = tag;
        String message = getPersonalizedMessage(tag);
        if (message != null)
            showProgressDialog(message);
        switch (method) {
            case Request.Method.GET:
            case Request.Method.DELETE:
                mNetworkUtils.getOrDeleteData(tag, url, method);
                break;
            case Request.Method.POST:
            case Request.Method.PUT:
                mNetworkUtils.postOrPutData(tag, url, params, method);
                break;
        }
    }

    private void networkData(String tag, String url, Map<String, String> params) {
        if (params != null)
            networkData(tag, url, params, Request.Method.POST);
        else
            networkData(tag, url, null, Request.Method.GET);
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
        Map<String, String> map = new HashMap<>();
        int count = 0;
        for (Folder f : mFolderList) {
            for (Facet ft : f.getFacets())
                if (ft.isSelected() == 1)
                    map.put("tag_uris[" + (count++) + "]", ft.getTag());
        }
        filterCount = map.size();
        networkData(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", map);
        onBackPressed();
        updateFilterButton();
    }

    @Override
    public void onFilterCanceled(boolean clearAll) {
        onBackPressed();
        if (clearAll) {
            for (Folder f : mFolderList) {
                for (Facet ft : f.getFacets())
                    ft.deselect();
            }
            filterCount = 0;
            networkData(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", null);
        } else {
            filterCount = 0;
            for (Folder f : mFolderList) {
                for (Facet ft : f.getFacets())
                    if (ft.isSelected() == 1)
                        filterCount++;
            }
        }
        updateFilterButton();
    }

    private void updateFilterButton() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(Constants.TAG_FRAGMENT_INSTITUTE_LIST);
        if (fragment instanceof InstituteListFragment) {
            ((InstituteListFragment) fragment).updateFilterButton(filterCount);
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
        Institute i = institutes.get(currentInstitute);
        if (i.getCurrent_user_shortlist_url() == null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("institute", i.getResource_uri());
            params.put("user", user.getResource_uri());
            networkData(Constants.TAG_SHORTLIST_INSTITUTE + "#" + currentInstitute, Constants.BASE_URL + "shortlistedinstitutes/", params);
        } else {
            networkData(Constants.TAG_DELETESHORTLIST_INSTITUTE + "#" + currentInstitute, i.getCurrent_user_shortlist_url(), null, Request.Method.DELETE);
        }
    }
}
