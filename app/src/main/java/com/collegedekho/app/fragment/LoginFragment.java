package com.collegedekho.app.fragment;

import android.os.Bundle;

/**
 * Created by sureshsaini on 20/11/15.
 */
public class LoginFragment extends  BaseFragment{

    public LoginFragment newInstance()
    {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        return  fragment;
    }

}
