package com.collegedekho.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;

import java.util.HashMap;

/**
 * Created by sureshsaini on 11/10/15.
 */
public class LoginFragment1 extends BaseFragment {

    private static final int SIGNUP = 1;
    private static final int SIGNIN = 0;
    private String mMessage;
    private static final String MSG ="chat_msg";
    private SignInFragment signInFragment;
   public LoginFragment1(){
       // required empty constructor
    }

    public static LoginFragment1 newInstance(String value) {
        LoginFragment1 fragment = new LoginFragment1();
        Bundle args = new Bundle();
        args.putString(MSG, value);
        fragment.setArguments(args);
        return fragment;
    }

      @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = getArguments().getString(MSG);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        LoginPagerAdapter adapter = new LoginPagerAdapter(getChildFragmentManager());
        ViewPager mPager = (ViewPager) rootView.findViewById(R.id.login_type_pager);
        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.login_tabs_layout);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.text_subhead_blue));
        tabLayout.setupWithViewPager(mPager);

        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return rootView;
    }

    class LoginPagerAdapter extends FragmentPagerAdapter
    {

        public LoginPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i)
            {
                case SIGNIN:
                     return signInFragment = SignInFragment.newInstance(mMessage);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(signInFragment != null)
        {
            signInFragment.onActivityResult(requestCode,resultCode,data);
        }

    }
    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;

    }

        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p/>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.
         */
    public interface OnSignUpListener {

        void onUserSignIn(String URL, HashMap hashMap, String msg);
        void onUserSignUp(String URL, HashMap hashMap, String msg);
    }
}
