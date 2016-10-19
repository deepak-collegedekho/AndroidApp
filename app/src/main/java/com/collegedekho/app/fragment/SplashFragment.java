package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;

public class SplashFragment extends BaseFragment {

    private final String TAG = "Splash Fragment";

    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash_screen, container, false);
    }


    public void onSplashGifCompleted(){
        int amIConnectedToInternet = NetworkUtils.getConnectivityStatus();
        SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        boolean IS_USER_CREATED = sp.getBoolean(getString(R.string.USER_CREATED), false);
        boolean IS_HOME_LOADED = sp.getBoolean(getString(R.string.USER_HOME_LOADED), false);

        if (amIConnectedToInternet == Constants.TYPE_NOT_CONNECTED
                && IS_USER_CREATED && !IS_HOME_LOADED ) {
            getView().findViewById(R.id.splash_no_internet_info_layout).setVisibility(View.VISIBLE);
        }else{
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.loadInItData();
            }
        }
    }


    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }
}
