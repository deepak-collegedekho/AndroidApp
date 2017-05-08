package com.collegedekho.app.fragment.profileBuilding;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.utils.ProfileMacro;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sureshsaini on 3/5/17.
 */

public class SpecificCourseFragment extends BaseProfileBuildingFragment implements View.OnClickListener {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SpecificCourseFragment.
     */
    public static SpecificCourseFragment newInstance() {
        SpecificCourseFragment fragment = new SpecificCourseFragment();
        return fragment;
    }

    public SpecificCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_course, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView currentLevelTxtView = (TextView) view.findViewById(R.id.user_education_level);
        currentLevelTxtView.setVisibility(View.VISIBLE);
        int currentLevelId = MainActivity.mProfile.getCurrent_level_id();
        if (currentLevelId == ProfileMacro.LEVEL_TWELFTH || currentLevelId == ProfileMacro.LEVEL_TENTH) {
            currentLevelTxtView.setText(getString(R.string.school));
        } else if (currentLevelId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
            currentLevelTxtView.setText(getString(R.string.college));
        } else {
            currentLevelTxtView.setText(getString(R.string.pg_college));
        }
        super.initIntituesCountViews(view);
        int instituteCount = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE)
                .getInt(getString(R.string.pref_level_institute_count), 0);
        super.setInstituteCount(String.valueOf(instituteCount));
        SearchView courseSearchView =(SearchView) view.findViewById(R.id.course_search_view);
        courseSearchView.requestFocus(0, new Rect());
        courseSearchView.setOnClickListener(this);
        courseSearchView.setOnSearchClickListener(this);
        view.findViewById(R.id.course_selection_skip_layout).setOnClickListener(this);
        view.findViewById(R.id.user_education_level_edit_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.course_search_view:
                EventBus.getDefault().post(new Event(AllEvents.ACTION_SPECIFIC_COURSE_CLICK, null, null));
                break;
            case R.id.course_selection_skip_layout:
                EventBus.getDefault().post(new Event(AllEvents.ACTION_COURSE_SELECTION_SKIP_CLICK, null, null));
                break;
            case R.id.user_education_level_edit_btn:
                EventBus.getDefault().post(new Event(AllEvents.ACTION_LEVEL_EDIT_SELECTION, null, null));
                break;
            default:
                break;
        }
    }
}
