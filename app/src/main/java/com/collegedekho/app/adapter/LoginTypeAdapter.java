package com.collegedekho.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.collegedekho.app.fragment.SignInFragment;
import com.collegedekho.app.fragment.SignUpFragment;

/**
 * Created by sureshsaini on 20/10/15.
 */
public class LoginTypeAdapter extends FragmentPagerAdapter
{
    private static final int SIGNUP = 1;
    private static final int SIGNIN = 0;
    private String mMessage;

    public LoginTypeAdapter(FragmentManager fm , String mMessage) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case SIGNIN:
                return  SignInFragment.newInstance(mMessage);
            case SIGNUP:
                return SignUpFragment.newInstance(mMessage);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case SIGNIN:
                return "SIGN IN";
            case SIGNUP:
                return "SIGN UP";
        }
        return super.getPageTitle(position);
    }
}
