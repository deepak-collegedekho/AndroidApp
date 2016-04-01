package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.facebook.login.LoginManager;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by sureshsaini on 20/11/15.
 */
public class LoginFragment extends  BaseFragment {

    private OnUserSignUpListener mListener;

    public static LoginFragment newInstance()
    {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return  fragment;
    }

    public LoginFragment(){
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_splash_login,container, false);
        TrueButton trueButton =(TrueButton)rootView.findViewById(R.id.com_truecaller_android_sdk_truebutton);
        boolean usable = trueButton.isUsable();

        if (usable)
            trueButton.setTrueClient(MainActivity.mTrueClient);
        else
           trueButton.setVisibility(View.GONE);

        rootView.findViewById(R.id.sign_up_skip).setOnClickListener(this);
        (rootView.findViewById(R.id.fb_login)).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnUserSignUpListener) context;
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
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        switch (view.getId()) {
            case R.id.sign_up_skip:
                mSkipUserLogin();
                break;

            case R.id.fb_login:
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends", "email", "user_likes", "user_education_history"));
                break;
        }
    }

    private void mSkipUserLogin()
    {
        if(mListener != null) {
            HashMap<String, String> params = new HashMap<>();
            String deviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
            params.put(MainActivity.getResourceString(R.string.USER_DEVICE_ID), deviceId);
            params.put(MainActivity.getResourceString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_ANONYMOUS);

            mListener.onSkipUserLogin(params);
        }
    }

    public interface OnUserSignUpListener {

        void onSkipUserLogin(HashMap<String, String> params);
        void displayMessage(int messageId);

    }

}
