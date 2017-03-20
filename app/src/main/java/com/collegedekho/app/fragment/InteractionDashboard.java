package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.listener.DashBoardItemListener;
import com.collegedekho.app.network.ApiEndPonits;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;

import java.util.HashMap;
import java.util.Map;

import static com.collegedekho.app.utils.AnalyticsUtils.SendAppEvent;

/**
 * Created by harshvardhan on 28/11/16.
 */

public class InteractionDashboard extends BaseFragment {

    private DashBoardItemListener mListener;

    public static InteractionDashboard newInstance() {
        return new InteractionDashboard();
    }

    public void InteractionDashboard() {  }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        rootView.findViewById(R.id.exams_tab_layout).setVisibility(View.GONE);
        this.updateUserProfile(rootView, MainActivity.mProfile);
        this.mUpdateSubMenuItem(rootView);

        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_third).setOnClickListener(this);
        rootView.findViewById(R.id.profile_image).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_step_by_step).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_psychometric_test).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_psychometric_report).setOnClickListener(this);
        ImageView tuteImage = (ImageView)rootView.findViewById(R.id.home_tute_image);
        tuteImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_tute6));
        tuteImage.setOnClickListener(this);
        //tuteImage.setImageBitmap(BitMapResize.decodeSampledBitmapFromResource(getResources(), tuteImage.getId(), tuteImage.getWidth(), tuteImage.getHeight()));


        rootView.findViewById(R.id.include_image_layout).findViewById(R.id.profile_image_edit_button).setOnClickListener(this);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        String psychometricResults = sharedPreferences.getString("psychometric_report", null);

        if (MainActivity.mProfile != null && MainActivity.mProfile.getPsychometric_given() == 1 && psychometricResults != null) {
            rootView.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.GONE);
            rootView.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.VISIBLE);
        } else {
            rootView.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity) {
                this.mListener = (DashBoardItemListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement OnHomeItemSelectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    private void updateUserProfile(View view, Profile profile) {
        if (view == null || profile == null)
            return;

        TextView mProfileName = (TextView) view.findViewById(R.id.user_name);
        TextView mProfileNumber = (TextView) view.findViewById(R.id.user_phone);
        mProfileName.setOnClickListener(this);
        mProfileNumber.setOnClickListener(this);
        mProfileName.setVisibility(View.VISIBLE);
        mProfileNumber.setVisibility(View.VISIBLE);

        String name = profile.getName();
        if (name == null || name.isEmpty() || name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())) {
            mProfileName.setText("Name : Anonymous User");
        } else {
            String userName = name.substring(0, 1).toUpperCase() + name.substring(1);
            mProfileName.setText("Name : " + userName);
        }

        String phone = profile.getPhone_no();
        if (phone == null || phone.isEmpty() || phone == "null") {
            mProfileNumber.setText("Phone : Not Set");
        } else {
            mProfileNumber.setText("Phone : " + phone);
        }

        CircularImageView mProfileImage = (CircularImageView) view.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);
        String image = profile.getImage();
        if (image != null && !image.isEmpty()) {
            mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
            mProfileImage.setVisibility(View.VISIBLE);
        }
        CircularProgressBar profileCompleted = (CircularProgressBar) view.findViewById(R.id.user_profile_progress);
        profileCompleted.setProgress(0);
        profileCompleted.setProgressWithAnimation(MainActivity.mProfile.getProgress(), 2000);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.user_name:
            case R.id.user_phone:
            case R.id.profile_image:
            case R.id.profile_image_edit_button:
                this.mListener.requestForProfileFragment();
                break;
            case R.id.btn_tab_psychometric_test:
                this.mListener.onPsychometricTestSelected();
                break;
            case R.id.btn_tab_psychometric_report:
                this.mListener.onTabPsychometricReport();
                break;
            case R.id.btn_tab_step_by_step:
                this.mListener.onTabStepByStep();
                break;
            case R.id.home_tute_image:
                view.setVisibility(View.GONE);
                if(getActivity() != null) {
                    ((MainActivity) getActivity()).mShowFabCounselor();
                    getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.INTERACTION_HOME_TUTE), true).apply();
                }
                break;
            default:
                try {
                    int mSelectedSubMenuPosition = Integer.parseInt((String) view.getTag());
                    this.mSubMenuItemClickListener(mSelectedSubMenuPosition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }


    private void mHomeWidgetSelected(String requestType, String url, String tag) {
        if (this.mListener != null)
            this.mListener.onHomeItemSelected(requestType, url, tag);
    }

    private void mSubMenuItemClickListener(int position) {
        if (position == 1) {
            this.mHomeWidgetSelected(Constants.WIDGET_FORUMS, ApiEndPonits.API_PERSONALIZE_FORUMS, null);
        } else if (position == 2) {
            this.mHomeWidgetSelected(Constants.TAG_LOAD_QNA_QUESTIONS, ApiEndPonits.API_PERSONALIZE_QNA, null);
        }else if (position == 3){
            this.mHomeWidgetSelected(Constants.TAG_LOAD_COUNSELOR_CHAT , ApiEndPonits.API_L2_CHATS,null);
            //Events
            Map<String, Object> eventValue = new HashMap<>();
            eventValue.put(getString(R.string.ACTION_USER_PREFERENCE),getString(R.string.ACTION_COUNSELOR_CHAT_SELECTED));
            SendAppEvent(getString(R.string.CATEGORY_MY_FB), getString(R.string.ACTION_COUNSELOR_CHAT_SELECTED), eventValue, getActivity());
        }
    }

    private void mUpdateSubMenuItem(View view){
        if(view ==   null)    return;

        TextView firstSubMenuTV     = (TextView)view.findViewById(R.id.home_widget_textview_first);
        TextView secondSubMenuTV    = (TextView)view.findViewById(R.id.home_widget_textview_second);
        TextView thirdSubMenuTV    = (TextView)view.findViewById(R.id.home_widget_textview_third);
        ImageView firstSubMenuIV    = (ImageView)view.findViewById(R.id.home_widget_image_first);
        ImageView secondSubMenuIV   = (ImageView)view.findViewById(R.id.home_widget_image_second);
        ImageView thirdSubMenuIV   = (ImageView)view.findViewById(R.id.home_widget_image_third);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        boolean IsTutECompleted = sharedPreferences.getBoolean(getString(R.string.INTERACTION_HOME_TUTE), false);
        if(!IsTutECompleted) {
            view.findViewById(R.id.home_tute_image).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.home_tute_image).setVisibility(View.GONE);
        }
        view.findViewById(R.id.home_widget_fourth).setVisibility(View.GONE);

        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) view.findViewById(R.id.home_widget_third).getLayoutParams();
        int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45 , getResources().getDisplayMetrics());
        lp2.setMargins(marginInDp,0,marginInDp,0);
        view.findViewById(R.id.home_widget_third).setLayoutParams(lp2);

        firstSubMenuIV.setImageResource(R.drawable.ic_chat_bubble_40dp);
        secondSubMenuIV.setImageResource(R.drawable.ic_qna);
        thirdSubMenuIV.setImageResource(R.drawable.ic_counselor_chat_vector);
        firstSubMenuTV.setText("Future Buddies");
        firstSubMenuTV.setContentDescription("Click to chat with your Future mates");
        secondSubMenuTV.setText("Q & A");
        secondSubMenuTV.setContentDescription("Click to ask questions");
        thirdSubMenuTV.setText("Counselor Chat");
        thirdSubMenuTV.setContentDescription("Click to chat with your Counselor");


    }

    @Override
    public void show() {  }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {  }


}
