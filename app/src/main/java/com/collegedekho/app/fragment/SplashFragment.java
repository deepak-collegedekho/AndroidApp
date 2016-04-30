package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;

public class SplashFragment extends BaseFragment {

    private ImageView mLogoView;
    private AnimationDrawable mFrameAnimation;
    private boolean IS_FIRST_TIME = true;   // check id splash animation is already executed

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.splash_screen, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //stopMainAnimation();
    }

    public void isInternetAvailable(){
        int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
        SharedPreferences sp = getActivity().getSharedPreferences(MainActivity.getResourceString(R.string.PREFS), Context.MODE_PRIVATE);
        boolean IS_USER_CREATED = sp.getBoolean(MainActivity.getResourceString(R.string.USER_CREATED), false);
        boolean IS_PROFILE_LOADED = sp.getBoolean(MainActivity.getResourceString(R.string.USER_PROFILE_LOADED), false);

        if (amIConnectedToInternet == Constants.TYPE_NOT_CONNECTED && IS_USER_CREATED && !IS_PROFILE_LOADED ) {
            getView().findViewById(R.id.splash_no_internet_info_layout).setVisibility(View.VISIBLE);
        }else{
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.loadInItData();
            }
        }
    }

    public void stopMainAnimation()
    {
        if(this.mFrameAnimation != null)
            this.mFrameAnimation.stop();

        //Start the secondary animation
        //this.mLogoView.startAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.bounce_slow));
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
