package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;

import com.android.volley.Request;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;

import java.util.HashMap;

/**
 * Created by sureshsaini on 27/11/15.
 */
public class HomeFragment extends BaseFragment {

    private final String TAG = "profile Frgament";
    private OnTabSelectListener mListener;
    private View mRootView;
    private boolean IS_TUTE_COMPLETED = true;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.RegisterBroadcastReceiver(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

        String psychometricResults = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getString("psychometric_report", null);

        if (MainActivity.mProfile.getPsychometric_given() == 1 && psychometricResults != null) {
            mRootView.findViewById(R.id.btn_home_psychometric_test).setVisibility(View.GONE);
            mRootView.findViewById(R.id.btn_home_psychometric_report).setVisibility(View.VISIBLE);
        } else {
            mRootView.findViewById(R.id.btn_home_psychometric_test).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.btn_home_psychometric_report).setVisibility(View.GONE);
        }

//        if (MainActivity.mProfile.getStep_by_step_given() == 1)
//            mRootView.findViewById(R.id.btn_home_step_by_step).setVisibility(View.GONE);

        this.IS_TUTE_COMPLETED = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean("Home Tute", false);
        if(!IS_TUTE_COMPLETED) {
            mRootView.findViewById(R.id.recommended_tute_image).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.recommended_tute_frame).setVisibility(View.VISIBLE);
        }
        else {
            mRootView.findViewById(R.id.recommended_tute_image).setVisibility(View.GONE);
            mRootView.findViewById(R.id.recommended_tute_frame).setVisibility(View.GONE);
        }

        mRootView.findViewById(R.id.college_list_layout_RL).setOnClickListener(((MainActivity) getActivity()).mClickListener);
        mRootView.findViewById(R.id.connect_layout_RL).setOnClickListener(((MainActivity) getActivity()).mClickListener);
        mRootView.findViewById(R.id.prepare_layout_RL).setOnClickListener(((MainActivity) getActivity()).mClickListener);
        mRootView.findViewById(R.id.updates_layout_RL).setOnClickListener(((MainActivity) getActivity()).mClickListener);
        mRootView.findViewById(R.id.profile_image_edit_button).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_home_psychometric_test).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_home_psychometric_report).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_home_step_by_step).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_image).setOnClickListener(this);
        mRootView.findViewById(R.id.recommended_tute_image).setOnClickListener(this);
        mRootView.findViewById(R.id.recommended_tute_frame).setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Constants.READY_TO_CLOSE = true;
    }

    @Override
    public void onDestroy() {
        Utils.UnregisterReceiver(this.getActivity());
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getActivity().invalidateOptionsMenu();
        } catch (Exception e) {
           e.printStackTrace();
        }

        Constants.READY_TO_CLOSE = false;
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {

            // this will change back arrow to hamburger icon on toolbar
            if(mainActivity.getSupportActionBar() != null && mainActivity.mDrawerToggle != null){
                mainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
            }
            mainActivity.currentFragment = this;
            mainActivity.mUpdateTabMenuItem(-1);
        }
        // update mDeviceProfile info
        updateUserInfo();
        mainActivity.speakMessageForAccessibility("Welcome To your Dashboard.");
    }




    public void updateUserInfo(){

        if(mRootView == null || MainActivity.mProfile == null)
            return;

        TextView mProfileName  = (TextView)mRootView.findViewById(R.id.user_name);
        TextView mProfileNumber  = (TextView)mRootView.findViewById(R.id.user_phone);
        CircularImageView mProfileImage = (CircularImageView)mRootView.findViewById(R.id.profile_image);

        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        Profile profile = MainActivity.mProfile;
        String name = profile.getName();
        if (name == null || name.isEmpty() || name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())) {
            mProfileName.setText("Name : Anonymous DeviceProfile");
        } else {
            String userName = name.substring(0, 1).toUpperCase() + name.substring(1);
            mProfileName.setText("Name : "+userName);
        }

        String phone = profile.getPhone_no();
        if (phone == null || phone.isEmpty() || phone == "null") {
            mProfileNumber.setText("Phone : Not Set");
        } else {
            mProfileNumber.setText("Phone : " + phone);
        }

        CircularProgressBar profileCompleted =  (CircularProgressBar) mRootView.findViewById(R.id.user_profile_progress);
        profileCompleted.setProgress(0);
        profileCompleted.setProgressWithAnimation(MainActivity.mProfile.getProgress(), 2000);

        String image = MainActivity.mProfile.getImage();
        if (image != null && !image.isEmpty())
            mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity)
                this.mListener = (OnTabSelectListener) context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnTabSelectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId())
        {
            case R.id.recommended_tute_image:
            case R.id.recommended_tute_frame:
                view.setVisibility(View.GONE);
                if(getView() != null){
                    getView().findViewById(R.id.recommended_tute_image).setVisibility(View.GONE);
                    getView().findViewById(R.id.recommended_tute_frame).setVisibility(View.GONE);
                }
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean("Home Tute", true).apply();
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.profile_image:
            case R.id.profile_image_edit_button:
                if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
                    ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
                    return;
                }
                if(mListener != null)
                    mListener.requestForProfileFragment();
                break;
            case R.id.btn_home_psychometric_test:
                mListener.onPsychometricTestSelected();
                break;
            case R.id.btn_home_psychometric_report:
                mListener.onHomePsychometricReport();
                break;
            case R.id.btn_home_step_by_step:
                mListener.onHomeStepByStep();
                break;
            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTabSelectListener {
        void onHomeItemSelected(String requestType, String url, String examTag);
        void onHomeStepByStep();
        void requestForProfileFragment();
        void onPsychometricTestSelected();
        void onHomePsychometricReport();
    }

}
