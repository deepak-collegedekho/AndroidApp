package com.collegedekho.app.fragment.profileBuilding;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.SubLevelAdapter;
import com.collegedekho.app.entities.SubLevel;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.ProfileMacro;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by sureshsaini on 18/4/17.
 */

public class LevelSelectionFragment extends BaseProfileBuildingFragment
{
    private static final String TAG = "ProfileBuildingFragment";
    private View mRootView ;
    private SubLevelAdapter subLevelAdapter ;
    private AlertDialog subLevelDialog ;
    private int mUserSubLevelID = 0;
    private String mEventCategory = "";
    private String mEventAction = "";
    private HashMap<String, Object> mEventValue = new HashMap<>();
    private ArrayList<SubLevel> mSubLevelList;
    private TextView mSkipButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LevelSelectionFragment.
     */
    public static LevelSelectionFragment newInstance() {
        return new LevelSelectionFragment();
    }

    public LevelSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  mRootView = inflater.inflate(R.layout.fragment_level_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initIntituesCountViews(view);
        int instituteCount = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getInt(getString(R.string.pref_institute_count), 0);
        super.setInstituteCount(String.valueOf(instituteCount));

        view.findViewById(R.id.user_education_radio_button_school).setOnClickListener(this);
        view.findViewById(R.id.user_education_radio_button_college).setOnClickListener(this);
        view.findViewById(R.id.user_education_radio_button_pg).setOnClickListener(this);
        view.findViewById(R.id.user_education_next_button).setOnClickListener(this);
    }




    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            MainActivity.currentFragment = this;
        }
        this.setUserLevelStatus();
    }

    private void setUserLevelStatus(){
        int checkedRadioButtonID = ((RadioGroup)mRootView.findViewById(R.id.user_education_radio_group)).getCheckedRadioButtonId();
        if( checkedRadioButtonID == -1 && MainActivity.mProfile != null){

            int levelId = MainActivity.mProfile.getCurrent_level_id();
            if(levelId == ProfileMacro.LEVEL_TENTH || levelId == ProfileMacro.LEVEL_TWELFTH){
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_school)).setChecked(true);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_college)).setChecked(false);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_pg)).setChecked(false);
            }else if(levelId == ProfileMacro.LEVEL_UNDER_GRADUATE){
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_school)).setChecked(false);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_college)).setChecked(true);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_pg)).setChecked(false);
            }
            else if(levelId == ProfileMacro.LEVEL_POST_GRADUATE){
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_school)).setChecked(false);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_college)).setChecked(false);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_pg)).setChecked(true);

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe
    public void onEvent(Event event) {
        if (event != null) {
            switch (event.getTag()) {
                case AllEvents.ACTION_CURRENT_LEVEL_SELECTION:
                    this.onSubLevelSelected(Integer.parseInt(event.getExtra()));
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId())
        {
            case R.id.user_education_next_button:
                this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
                this.mEventAction = getString(R.string.ACTION_LEVEL_NEXT_SELECTED);
                setUserEducationLevel();
                break;
            case R.id.user_education_radio_button_school:
                mRequestForSubLevels(ProfileMacro.LEVEL_TWELFTH);
                break;
            case R.id.user_education_radio_button_college:
                mRequestForSubLevels(ProfileMacro.LEVEL_UNDER_GRADUATE);
                break;
            case R.id.user_education_radio_button_pg:
                mRequestForSubLevels(ProfileMacro.LEVEL_POST_GRADUATE);
                break;
            default:
                break;
        }
        if (!this.mEventAction.isEmpty() && this.mEventAction != ""){
            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());
            this.mResetEventVariables();
        }
    }

    private void mRequestForSubLevels(int  level) {
        EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_SUB_LEVELS, null, String.valueOf(level)));
    }

    public void mSubLevelsResponseCompleted(ArrayList<SubLevel> subLevelsList){
        if(!isAdded()){
            return;
        }
        this.mSubLevelList = subLevelsList;
        if(this.mSubLevelList != null && !this.mSubLevelList.isEmpty()){
            int instituteCount = this.mSubLevelList.get(0).getInstitutes_count();
            if(isAdded()) {
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().
                        putInt(getString(R.string.pref_institute_count), instituteCount).apply();
                setInstituteCount(String.valueOf(instituteCount));
            }
        }
        mAskForUserSubLevel(subLevelsList);
    }

    private void mAskForUserSubLevel(ArrayList<SubLevel> subLevels) {

        subLevelAdapter = new SubLevelAdapter(getActivity(),this,subLevels);
        if (getActivity() != null && !getActivity().isFinishing()) {
            subLevelDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.please_select_sub_level_year))
                    .setCancelable(false)
                    .setSingleChoiceItems(subLevelAdapter, -1,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onSubLevelSelected(which);
                                }
                            }).show();
        }
    }

    private void onSubLevelSelected(int position){
        // set user's sub level base
        mUserSubLevelID = (int)subLevelAdapter.getItemId(position);
        // Now show Next Button
        mAnimateFooterButtons();
        // dismiss dialog
        if(subLevelDialog != null && subLevelDialog.isShowing())
            subLevelDialog.dismiss();
        // check if fragment is added to activity then show dialog to ask marks
        // otherwise set default marks and request for streams
        setUserEducationLevel();
    }


    private void mAnimateFooterButtons()
    {
        View nextView = mRootView.findViewById(R.id.user_education_next_button);
        if (nextView.getAlpha() != 1)
        {
            Runnable animEnd = new Runnable() {
                @Override
                public void run() {
                    mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
                }
            };

            nextView.animate()
                    .x(mRootView.getWidth() - nextView.getWidth() - nextView.getPaddingRight())
                    .alpha(1)
                    .withEndAction(animEnd)
                    .setDuration(Constants.ANIM_AVERAGE_DURATION);

        }
    }



    private void setUserEducationLevel() {
        RadioGroup radioGroupEducation = (RadioGroup)mRootView.findViewById(R.id.user_education_radio_group);
        int selectedRadioButton = radioGroupEducation.getCheckedRadioButtonId();

        if(selectedRadioButton <= 1){
            EventBus.getDefault().post(new Event(AllEvents.ACTION_PLEASE_SELECT_LEVEL, null, null));
            return;
        }
        // setting default  current education level school
        int currentLevelID = ProfileMacro.LEVEL_TWELFTH;
        int checkedRadioGroupIndex = radioGroupEducation.indexOfChild(mRootView.findViewById(selectedRadioButton));
        if(checkedRadioGroupIndex  == 1){
            currentLevelID =  ProfileMacro.LEVEL_UNDER_GRADUATE;
        }else if(checkedRadioGroupIndex == 2){
            currentLevelID =  ProfileMacro.LEVEL_POST_GRADUATE;
        }
        // setting default preferred level ug
        // setting default preferred level ug
        String streamType = "1" ; //  0 for college and 1 for school
        int preferredLevelId = ProfileMacro.LEVEL_UNDER_GRADUATE;
        if (currentLevelID == ProfileMacro.LEVEL_UNDER_GRADUATE) {
            streamType = "0";
            preferredLevelId = ProfileMacro.LEVEL_POST_GRADUATE;
        } else if (currentLevelID == ProfileMacro.LEVEL_POST_GRADUATE) {
            streamType = "0";
            preferredLevelId = ProfileMacro.LEVEL_PHD;
        }

        EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_LEVEl_STREAMS, null, streamType));

        this.mEventAction = getString(R.string.ACTION_CURRENT_LEVEL_SELECTED);
        this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
        String name = MainActivity.mProfile.getName();
        if (name != null && !name.isEmpty())
            this.mEventValue.put(getString(R.string.USER_NAME), name);
        String phone = MainActivity.mProfile.getPhone_no();
        if (phone != null && !phone.isEmpty())
            this.mEventValue.put(getString(R.string.USER_PHONE),phone);
        this.mEventValue.put(getString(R.string.USER_CURRENT_LEVEL_ID), currentLevelID);
        this.mEventValue.put(getString(R.string.USER_CURRENT_SUBLEVEL), mUserSubLevelID);
        this.mEventValue.put(getString(R.string.USER_PREFERRED_LEVEL_ID), preferredLevelId);
        AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, mEventValue, this.getActivity());

    }

    private void mResetEventVariables()
    {
        this.mEventAction = "";
        this.mEventValue.clear();
    }

}
