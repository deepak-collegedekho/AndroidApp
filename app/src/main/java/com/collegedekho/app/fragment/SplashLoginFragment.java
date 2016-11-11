package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;

/**
 *Created by ${sureshsaini} on ${20/11/15}.
 */
public class SplashLoginFragment extends  BaseFragment {

    private OnSplashLoginListener mListener;

    public static SplashLoginFragment newInstance() {
        return new SplashLoginFragment();
    }

    public SplashLoginFragment(){
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return  inflater.inflate(R.layout.fragment_splash_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.splash_login_help_me).setOnClickListener(this);
        view.findViewById(R.id.splash_login_i_know).setOnClickListener(this);
        view.findViewById(R.id.splash_login_skip_layout).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnSplashLoginListener) context;
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
    public void onClick(View view) {
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        switch (view.getId()) {
            case R.id.splash_login_skip_layout:
                mSplashSkipLogin();
                break;
            case R.id.splash_login_help_me:
                mSplashHelpMeLogin();
                break;
            case R.id.splash_login_i_know:
                mSplashIKnowLogin();
                break;
        }
    }

    private void mSplashSkipLogin(){
        mListener.onSplashSkipLogin();
    }

    private void mSplashHelpMeLogin(){
        mListener.onSplashHelpMeLogin();
    }

    private void mSplashIKnowLogin(){
        mListener.onSplashIKnowLogin();
    }

    public interface OnSplashLoginListener {
        void onSplashSkipLogin();
        void onSplashHelpMeLogin();
        void onSplashIKnowLogin();
        void displayMessage(int messageId);
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
