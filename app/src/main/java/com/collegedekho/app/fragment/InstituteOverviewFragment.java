package com.collegedekho.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.MySingleton;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteOverviewFragment extends Fragment {
    private static final String ARG_INSTITUTE = "param1";
    String[] titles = {"Achievements", "Infrastucture", "Placement", "Nearby Joints"};
    private Institute mInstitute;
    private OnInstituteShortlistedListener mListener;

    public InstituteOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param institute Parameter 1.
     * @return A new instance of fragment InstituteOverviewFragment.
     */
    public static InstituteOverviewFragment newInstance(Institute institute) {
        InstituteOverviewFragment fragment = new InstituteOverviewFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_institute_overview, container, false);
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
        setupFacilities(inflater, layout, mInstitute.getFacilities());
        ((TextView) rootView.findViewById(R.id.textview_college_location)).setText(text);
        ((TextView) rootView.findViewById(R.id.textview_why_join)).setText("Why join " + mInstitute.getShort_name());
        TextView t = ((TextView) rootView.findViewById(R.id.shortlist_college));

        if (mInstitute.getIs_shortlisted() == Constants.SHORTLISTED_NO)
        {
            t.setText("Shortlist " + mInstitute.getShort_name());
            t.setBackgroundResource(R.drawable.bg_button_blue);
        }
        else
        {
            t.setText("Delete " + mInstitute.getShort_name() + " from your shortlist");
            t.setBackgroundResource(R.drawable.bg_button_grey);
        }
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                if (mListener != null)
                    mListener.onInstituteShortlisted();

            }
        });
        setupInfo((LinearLayout) rootView.findViewById(R.id.college_info_ll1)
                , (LinearLayout) rootView.findViewById(R.id.college_info_ll2));
        //getInfo(heads, details);
        //GridView grid = (GridView) rootView.findViewById(R.id.grid_why_join);
        //grid.setAdapter(new InstituteInfoAdapter(getActivity(), heads, details));
        //GridLayout grid = (GridLayout) rootView.findViewById(R.id.grid_why_join);
        //setupInfo(inflater, grid);
        return rootView;
    }

    private void setupFacilities(LayoutInflater inflater, LinearLayout facilityLayout, ArrayList<Facility> facilities) {
        ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
        for (Facility f : facilities) {
            NetworkImageView imageView = (NetworkImageView) inflater.inflate(R.layout.item_facility, facilityLayout, false);
            imageView.setImageUrl(f.image, imageLoader);
            facilityLayout.addView(imageView);
        }
    }

    private void getInfo(ArrayList<String> heads, ArrayList<String> details) {
        if (mInstitute.getAwards_snap() != null && !mInstitute.getAwards_snap().isEmpty()) {
            heads.add("Achievements");
            details.add(mInstitute.getAwards_snap());
        }
        if (mInstitute.getInfra_snap() != null && !mInstitute.getInfra_snap().isEmpty()) {
            heads.add("Infrastucture");
            details.add(mInstitute.getInfra_snap());
        }
        if (mInstitute.getPlacement_percentage() != null && !mInstitute.getPlacement_percentage().isEmpty()) {
            heads.add("Placement");
            details.add(mInstitute.getPlacement_percentage() + "% Placement");
        }
        if (mInstitute.getNear_by_joints_snap() != null && !mInstitute.getNear_by_joints_snap().isEmpty()) {
            heads.add("Nearby Joints");
            details.add(mInstitute.getNear_by_joints_snap());
        }
    }

    private void setupInfo(LinearLayout l1, LinearLayout l2) {
        setViewDetails(l1.getChildAt(0), titles[0], mInstitute.getAwards_snap());
        setViewDetails(l2.getChildAt(0), titles[1], mInstitute.getInfra_snap());
        setViewDetails(l1.getChildAt(1), titles[2], mInstitute.getPlacement_percentage() == null ? null : mInstitute.getPlacement_percentage() + "% Placement");
        setViewDetails(l2.getChildAt(1), titles[3], mInstitute.getNear_by_joints_snap());
    }

    private void setViewDetails(View view, String title, String detail) {
        ((TextView) view.findViewById(R.id.textview_cinfo_tag)).setText(title);
        if (detail != null && !detail.isEmpty())
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText(detail);
        else
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText("Not Available");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnInstituteShortlistedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Implement OnInstituteShortlistedListener");

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*private void setupInfo(LayoutInflater inflater, GridLayout layout) {
        layout.removeAllViews();
        int c = 0;
        if (mInstitute.getAwards_snap() != null && !mInstitute.getAwards_snap().isEmpty()) {
            View view = inflater.inflate(R.layout.card_college_info, layout, false);
            ((TextView) view.findViewById(R.id.textview_cinfo_tag)).setText("Achievements");
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText(mInstitute.getAwards_snap());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(0, 2, 1);
            params.rowSpec = GridLayout.spec(0, 1, 1);
            view.setLayoutParams(params);
            layout.addView(view);
            c++;
        }
        if (mInstitute.getInfra_snap() != null && !mInstitute.getInfra_snap().isEmpty()) {
            View view = inflater.inflate(R.layout.card_college_info, layout, false);
            ((TextView) view.findViewById(R.id.textview_cinfo_tag)).setText("Infrastucture");
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText(mInstitute.getInfra_snap());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec((c % 2) == 1 ? 2 : 0, 2, 1);
            params.rowSpec = GridLayout.spec(0, 1, 1);
            view.setLayoutParams(params);
            layout.addView(view);
            c++;
        }
        if (mInstitute.getPlacement_percentage() != null && !mInstitute.getPlacement_percentage().isEmpty()) {
            View view = inflater.inflate(R.layout.card_college_info, layout, false);
            ((TextView) view.findViewById(R.id.textview_cinfo_tag)).setText("Placement");
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText(mInstitute.getPlacement_percentage() + "% Placement");
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec((c % 2) == 1 ? 2 : 0, 2, 1);
            params.rowSpec = GridLayout.spec(c > 1 ? 1 : 0, 1, 1);
            view.setLayoutParams(params);
            layout.addView(view);
            c++;
        }
        if (mInstitute.getNear_by_joints_snap() != null && !mInstitute.getNear_by_joints_snap().isEmpty()) {
            View view = inflater.inflate(R.layout.card_college_info, layout, false);
            ((TextView) view.findViewById(R.id.textview_cinfo_tag)).setText("Nearby Joints");
            ((TextView) view.findViewById(R.id.textview_cinfo_about)).setText(mInstitute.getNear_by_joints_snap());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec((c % 2) == 1 ? 2 : 0, 2, 1);
            params.rowSpec = GridLayout.spec(c > 1 ? 1 : 0, 1, 1);
            view.setLayoutParams(params);
            layout.addView(view);
            c++;
        }
        if (c % 2 == 1) {
            Space space = new Space(getActivity());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec((c % 2) == 1 ? 2 : 0, 2, 1);
            params.rowSpec = GridLayout.spec(c > 1 ? 1 : 0, 1, 1);
            space.setLayoutParams(params);
            layout.addView(space);
        }
    }*/

    public void updateShortListButton() {
        View rootView = getView();
        if (rootView != null) {
            TextView t = (TextView) rootView.findViewById(R.id.shortlist_college);
            if (mInstitute.getIs_shortlisted() == Constants.SHORTLISTED_NO) {
                t.setText("Shortlist " + mInstitute.getShort_name());
                t.setBackgroundResource(R.drawable.bg_button_blue);
            } else {
                t.setText("Delete " + mInstitute.getShort_name() + " from your shortlist");
                t.setBackgroundResource(R.drawable.bg_button_grey);
            }
            t.setEnabled(true);
        }
    }


    public interface OnInstituteShortlistedListener {
        void onInstituteShortlisted();
    }
}
