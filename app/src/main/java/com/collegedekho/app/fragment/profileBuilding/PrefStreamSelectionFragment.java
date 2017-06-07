package com.collegedekho.app.fragment.profileBuilding;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.PrefStreamAdapter;
import com.collegedekho.app.entities.Country;
import com.collegedekho.app.entities.ProfileSpinnerItem;
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
 * Created by sureshsaini on 3/5/17.
 */

public class PrefStreamSelectionFragment  extends BaseProfileBuildingFragment {
    private static final String TAG = PrefStreamSelectionFragment.class.getSimpleName();
    private static final String PARAM1 = "PARAM1";
    private View mRootView;
    private String mEventCategory = "";
    private String mEventAction = "";
    private HashMap<String, Object> mEventValue = new HashMap<>();
    private RecyclerView mStreamRecyclerView;
    private PrefStreamAdapter mStreamAdapter;
    private ArrayList<ProfileSpinnerItem> mStreamList = new ArrayList<>();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param streamList
     * @return A new instance of fragment LevelSelectionFragment.
     */
    public static PrefStreamSelectionFragment newInstance(ArrayList<ProfileSpinnerItem> streamList) {
        PrefStreamSelectionFragment fragment = new PrefStreamSelectionFragment();
        if (streamList != null) {
            Bundle args = new Bundle();
            args.putParcelableArrayList(PARAM1, streamList);
            fragment.setArguments(args);
        }
        return fragment;
    }

    public PrefStreamSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            ArrayList streamList = args.getParcelableArrayList(PARAM1);
            if (streamList != null) {
                mStreamList.clear();
                mStreamList.addAll(streamList);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRootView = inflater.inflate(R.layout.fragment_pref_stream_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView currentLevelTxtView = (TextView) view.findViewById(R.id.user_education_level);
        TextView preferredCountries = (TextView) view.findViewById(R.id.user_preferred_countries);
        int currentLevelId = MainActivity.mProfile.getCurrent_level_id();
        if (currentLevelId == ProfileMacro.LEVEL_TWELFTH ||
                currentLevelId == ProfileMacro.LEVEL_TENTH) {
            currentLevelTxtView.setText(getString(R.string.school));
        } else if (currentLevelId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
            currentLevelTxtView.setText(getString(R.string.college));
        } else {
            currentLevelTxtView.setText(getString(R.string.pg_college));
        }
        String countriesText ="";
        for (Country country: MainActivity.mProfile.getPreferred_countries()
                ) {
            countriesText+=country.getName()+", ";
        }
        if(countriesText.length()>0)
        {
            countriesText = countriesText.substring(0,countriesText.length()-2);
        }
        preferredCountries.setText(countriesText);
        super.initIntituesCountViews(view);
        int instituteCount = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).
                getInt(getString(R.string.pref_level_institute_count), 0);
        super.setInstituteCount(String.valueOf(instituteCount));

        this.checkUserAlreadySelectedStream();
        mStreamRecyclerView = (RecyclerView) view.findViewById(R.id.user_education_recycler_view);
        mStreamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mStreamAdapter = new PrefStreamAdapter(getActivity(), mStreamList);
        mStreamRecyclerView.setAdapter(mStreamAdapter);

        view.findViewById(R.id.user_education_skip_button).setOnClickListener(this);
        view.findViewById(R.id.user_education_level_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_preferred_country_edit).setOnClickListener(this);
        view.findViewById(R.id.user_education_next_button).setOnClickListener(this);

        this.showNextButton();
    }

    public void hideNavigationIcon(){
        mRootView.findViewById(R.id.navigation_cd_icon).setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            MainActivity.currentFragment = this;
        }
        this.requestForLevelStreams();
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
                case AllEvents.ACTION_CURRENT_STREAM_SELECTION:
                    super.setInstituteCount(event.getExtra());
                    break;
            }
        }
    }

    private void checkUserAlreadySelectedStream() {
        if (MainActivity.mProfile == null) {
            return;
        }

        int userStreamId = MainActivity.mProfile.getCurrent_stream_id();
        int count = mStreamList.size();
        for (int i = 0; i < count; i++) {
            ProfileSpinnerItem streamOj = mStreamList.get(i);
            if (streamOj == null) continue;
            if (streamOj.getId() == userStreamId) {
                streamOj.setSelected(true);
                int instituteCount = streamOj.getInstitutes_count();
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit()
                        .putInt(getString(R.string.pref_stream_institute_count), instituteCount).apply();
                // update institute count for this stream
                super.setInstituteCount(String.valueOf(instituteCount));
                showNextButton();
            } else {
                streamOj.setSelected(false);
            }
        }
    }


    private void requestForLevelStreams() {
        if (mStreamList.isEmpty()) {
            String streamType = "1"; //  0 for college and 1 for school
            if (MainActivity.mProfile != null) {
                int currentLevelID = MainActivity.mProfile.getCurrent_level_id();
                if (currentLevelID == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                    streamType = "0";
                } else if (currentLevelID == ProfileMacro.LEVEL_POST_GRADUATE) {
                    streamType = "0";
                }
            }
            EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_LEVEl_STREAMS, null, streamType));
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.user_education_skip_button:
                this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
                this.mEventAction = getString(R.string.ACTION_STREAM_SKIP_SELECTED);
                EventBus.getDefault().post(new Event(AllEvents.ACTION_SKIP_STREAM_SELECTION, null, null));
                break;
            case R.id.user_education_next_button:
                this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
                this.mEventAction = getString(R.string.ACTION_STREAM_NEXT_SELECTED);
                setUserEducationStream();
                break;
            case R.id.user_education_level_edit_btn:
                EventBus.getDefault().post(new Event(AllEvents.ACTION_LEVEL_EDIT_SELECTION, null, null));
                break;
            case R.id.user_preferred_country_edit:
                EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_COUNTRIES, null, null));
                break;
            default:
                break;
        }
        if (!this.mEventAction.isEmpty() && this.mEventAction != "") {
            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());
        }
    }

    private void setUserEducationStream() {

        this.requestForLevelStreams();
        if (mStreamList.isEmpty()) {
            return;
        }
        int count = mStreamList.size();
        // check user has been selected a stream
        int currentStreamId = 0;
        String currentStreamName = "";
        boolean isStreamSelected = false;
        for (int i = 0; i < count; i++) {
            ProfileSpinnerItem objItem = mStreamList.get(i);
            if (!objItem.isSelected()) continue;
            currentStreamId = objItem.getId();
            currentStreamName = objItem.getName();
            isStreamSelected = true;
            break;
        }
        if (!isStreamSelected) {
            EventBus.getDefault().post(new Event(AllEvents.ACTION_PLEASE_SELECT_STREAM, null, null));
            return;
        }
        showNextButton();

        MainActivity.mProfile.setPreferred_stream_id(currentStreamId);
        MainActivity.mProfile.setPreferred_stream_short_name(currentStreamName);

        EventBus.getDefault().post(new Event(AllEvents.ACTION_ON_PREFERRED_STREAM_SELECTED, null, null));

        // send Events
        this.mEventAction = getString(R.string.ACTION_CURRENT_STREAM_SELECTED);
        String name = MainActivity.mProfile.getName();
        if (name != null && !name.isEmpty())
            this.mEventValue.put(getString(R.string.USER_NAME), name);
        String phone = MainActivity.mProfile.getPhone_no();
        if (phone != null && !phone.isEmpty())
            this.mEventValue.put(getString(R.string.USER_PHONE), name);
        this.mEventValue.put(getString(R.string.USER_CURRENT_STREAM_ID), String.valueOf(currentStreamId));
        this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
        AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, mEventValue, this.getActivity());
    }

    public void updateStreamList(ArrayList<ProfileSpinnerItem> streamList) {
        if (isAdded()) {
            this.mStreamList.clear();
            this.mStreamList.addAll(streamList);
            checkUserAlreadySelectedStream();
            if (mStreamAdapter != null) {
                mStreamAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showNextButton() {
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
        final View nextView = mRootView.findViewById(R.id.user_education_next_button);
        if (nextView.getAlpha() != 1) {

            nextView.setVisibility(View.VISIBLE);
            nextView.setAlpha(0);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    nextView.animate()
                            .x(mRootView.getWidth() - nextView.getWidth() - nextView.getPaddingRight())
                            .alpha(1f)
                            .setDuration(Constants.ANIM_AVERAGE_DURATION);
                }
            }, Constants.ANIM_SHORT_DURATION);


            View skipView = mRootView.findViewById(R.id.user_education_skip_button);
            skipView.setVisibility(View.VISIBLE);
            skipView.setAlpha(0f);

            skipView.animate()
                    .alpha(1f)
                    .x(skipView.getWidth() + mRootView.findViewById(R.id.user_education_next_button_layout).getPaddingLeft())
                    .setStartDelay(Constants.ANIM_SHORT_DURATION)
                    .setDuration(Constants.ANIM_AVERAGE_DURATION);

        }
    }

}
