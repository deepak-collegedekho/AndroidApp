package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;

public class SplashFragment extends BaseFragment {

    private final String TAG = "Splash Fragment";

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    public void noInternetAvailable(){
          if(getView() != null) {
              getView().findViewById(R.id.splash_no_internet_info_layout).setVisibility(View.VISIBLE);
          }
    }

    @Override
    public void show() { }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {  }
}
