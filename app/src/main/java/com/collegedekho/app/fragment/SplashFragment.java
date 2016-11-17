package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;

public class SplashFragment extends BaseFragment {

    private final String TAG = "Splash Fragment";
    private OnSplashListener mListener;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mListener != null && NetworkUtils.getConnectivityStatus() != Constants.TYPE_NOT_CONNECTED){
            mListener.onRequestForUserCreation();
        }
    }

    public void noInternetAvailable(){
          if(getView() != null) {
              getView().findViewById(R.id.splash_no_internet_info_layout).setVisibility(View.VISIBLE);
          }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnSplashListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSignUpListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void show() { }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() { }
    public interface OnSplashListener {
        void onRequestForUserCreation();
    }
}
