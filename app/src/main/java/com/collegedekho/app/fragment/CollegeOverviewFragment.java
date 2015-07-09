package com.collegedekho.app.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollegeOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollegeOverviewFragment extends Fragment {
    private static final String ARG_INSTITUTE = "param1";

    private Institute mInstitute;


    public CollegeOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param institute Parameter 1.
     * @return A new instance of fragment CollegeOverviewFragment.
     */
    public static CollegeOverviewFragment newInstance(Institute institute) {
        CollegeOverviewFragment fragment = new CollegeOverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INSTITUTE, institute);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInstitute = getArguments().getParcelable(ARG_INSTITUTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_college_overview, container, false);
        ((TextView) rootView.findViewById(R.id.textview_college_name)).setText(mInstitute.getName());
        String text = "";
        if (mInstitute.getCity_name() != null)
            text += mInstitute.getCity_name() + ", ";
        if (mInstitute.getState_name() != null)
            text += mInstitute.getState_name();
        if ((mInstitute.getState_name() != null || mInstitute.getCity_name() != null) && mInstitute.getEstb_date() != null)
            text += " | ";
        if (mInstitute.getEstb_date() != null)
            text += "Established in: " + mInstitute.getEstb_date().substring(0, 4);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.college_facility_list);
        setupFacilities(inflater, layout);
        ((TextView) rootView.findViewById(R.id.textview_college_location)).setText(text);
        ((TextView) rootView.findViewById(R.id.textview_why_join)).setText("Why join " + mInstitute.getShort_name());
        GridLayout grid = (GridLayout) rootView.findViewById(R.id.grid_why_join);
        setupInfo(inflater, grid);
        return rootView;
    }

    private void setupFacilities(LayoutInflater inflater, LinearLayout layout) {
    }

    private void setupInfo(LayoutInflater inflater, GridLayout layout) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 2, 1);
        if (mInstitute.getAwards_snap() != null && !mInstitute.getAwards_snap().isEmpty()) {
            View view = inflater.inflate(R.layout.card_college_info, layout, false);
            ((TextView) view.findViewById(R.id.textview_cinfo_tag)).setText("Achievements");
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText(mInstitute.getAwards_snap());
            view.setLayoutParams(params);
            layout.addView(view);
        }
        if (mInstitute.getInfra_snap() != null && !mInstitute.getInfra_snap().isEmpty()) {
            View view = inflater.inflate(R.layout.card_college_info, layout, false);
            ((TextView) view.findViewById(R.id.textview_cinfo_tag)).setText("Infrastucture");
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText(mInstitute.getInfra_snap());
            view.setLayoutParams(params);
            layout.addView(view);
        }
        if (mInstitute.getPlacement_percentage() != null && !mInstitute.getPlacement_percentage().isEmpty()) {
            View view = inflater.inflate(R.layout.card_college_info, layout, false);
            ((TextView) view.findViewById(R.id.textview_cinfo_tag)).setText("Placement");
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText(mInstitute.getPlacement_percentage() + "% Placement");
            view.setLayoutParams(params);
            layout.addView(view);
        }
        if (mInstitute.getNear_by_joints_snap() != null && !mInstitute.getNear_by_joints_snap().isEmpty()) {
            View view = inflater.inflate(R.layout.card_college_info, layout, false);
            ((TextView) view.findViewById(R.id.textview_cinfo_tag)).setText("Nearby Joints");
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText(mInstitute.getNear_by_joints_snap());
            view.setLayoutParams(params);
            layout.addView(view);
        }
    }
}
