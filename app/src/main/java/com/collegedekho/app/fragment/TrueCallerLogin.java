package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.truecaller.android.sdk.TrueButton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by sureshsaini on 29/12/16.
 */

public class TrueCallerLogin extends  BaseFragment {

    private Unbinder mUnBinder;

    public static TrueCallerLogin newInstance()
    {
        return new TrueCallerLogin();
    }

    public TrueCallerLogin(){
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        this.mUnBinder = ButterKnife.bind(this, rootView);
        TrueButton trueButton =(TrueButton)rootView.findViewById(R.id.com_truecaller_android_sdk_truebutton);
        boolean usable = trueButton.isUsable();

        if (usable) {
            trueButton.setTrueClient(MainActivity.mTrueClient);
            rootView.findViewById(R.id.login_or_line_layout).setVisibility(View.VISIBLE);
        }else {
            trueButton.setVisibility(View.GONE);
            rootView.findViewById(R.id.login_or_line_layout).setVisibility(View.GONE);
        }

        return rootView;
    }
    @OnClick(R.id.sign_up_skip_layout)
    public void onSkipClick(){
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        if(getActivity() != null)
            ((MainActivity)getActivity()).onSplashHelpMeLogin();
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

    @Override
    public void onDetach() {
        super.onDetach();
        this.mUnBinder.unbind();
    }
}
