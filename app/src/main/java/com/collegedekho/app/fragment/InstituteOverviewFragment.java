package com.collegedekho.app.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteOverviewFragment extends BaseFragment {
    private static final String ARG_INSTITUTE = "param1";
    String[] titles = {"Achievements", "Infrastucture", "Placement", "Nearby Joints"};
    private Institute mInstitute;
    private OnInstituteShortlistedListener mListener;
    private TextView mShortListTV;
    private ProgressBar mProgressBar;

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
            this.mInstitute = getArguments().getParcelable(ARG_INSTITUTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_overview, container, false);
        ((TextView) rootView.findViewById(R.id.textview_college_name)).setText(mInstitute.getName());
        String text = "";
        if (this.mInstitute.getCity_name() != null)
            text += this.mInstitute.getCity_name() + ", ";
        if (this.mInstitute.getState_name() != null)
            text += this.mInstitute.getState_name();
        if ((this.mInstitute.getState_name() != null || this.mInstitute.getCity_name() != null) && this.mInstitute.getEstb_date() != null)
            text += " | ";
        if (this.mInstitute.getEstb_date() != null)
            text += "Established in: " + this.mInstitute.getEstb_date().substring(0, 4);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.college_facility_list);
        setupFacilities(inflater, layout, this.mInstitute.getFacilities());

        NetworkImageView imageView = ((NetworkImageView) rootView.findViewById(R.id.overview_image));
        imageView.setDefaultImageResId(R.drawable.default_banner);
        imageView.setErrorImageResId(R.drawable.default_banner);
        if (this.mInstitute.getImages().get("Banner") != null) {
            ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
            imageView.setImageUrl(this.mInstitute.getImages().get("Banner"), imageLoader);
        }

        if(mInstitute != null && mInstitute.getShort_name() != null && !mInstitute.getShort_name().equalsIgnoreCase("null") ) {
            imageView.setContentDescription(mInstitute.getShort_name() + " Overview Image");
        } else if (mInstitute != null && mInstitute.getName() != null && !mInstitute.getName().equalsIgnoreCase("null")){
            String description =  mInstitute.getName();
            if(mInstitute.getCity_name() != null && !mInstitute.getCity_name().equalsIgnoreCase("null")){
                description = description + " " + mInstitute.getCity_name();
            } else if(mInstitute.getState_name() != null && !mInstitute.getState_name().equalsIgnoreCase("null")){
                description = description + " " + mInstitute.getState_name();
            }
            imageView.setContentDescription(description + " Overview Image");
        }

        ((TextView) rootView.findViewById(R.id.textview_college_location)).setText(text);
        Spanned spanned = Html.fromHtml(this.mInstitute.getDescription());
        if(spanned != null){
            ((TextView) rootView.findViewById(R.id.overview_text)).setText(spanned);
        }

        ((TextView) rootView.findViewById(R.id.textview_why_join)).setText("Why join " + mInstitute.getShort_name());
        this.mShortListTV  = ((TextView) rootView.findViewById(R.id.shortlist_college));
        this.mProgressBar = ((ProgressBar) rootView.findViewById(R.id.shortList_college_progressBar));

        if (this.mInstitute.getIs_shortlisted() == Constants.SHORTLISTED_NO)
        {
            this.mShortListTV.setText("Shortlist " + mInstitute.getShort_name());
            this.mShortListTV.setBackgroundResource(R.drawable.bg_button_blue);
        }
        else
        {
            this.mShortListTV.setText("Delete " + mInstitute.getShort_name() + " from your shortlist");
            this.mShortListTV.setBackgroundResource(R.drawable.bg_button_grey);
        }
        this.mShortListTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    v.setEnabled(false);
                    mShortListTV.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mListener.onInstituteShortlisted();
                }
            }
        });
        this.setupInfo((LinearLayout) rootView.findViewById(R.id.college_info_ll1)
                , (LinearLayout) rootView.findViewById(R.id.college_info_ll2));

        return rootView;
    }

    private void setupFacilities(LayoutInflater inflater, LinearLayout facilityLayout, ArrayList<Facility> facilities) {
        ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();

        if (facilities != null && facilities.size() <= 0)
            facilityLayout.setVisibility(View.GONE);
        else
            facilityLayout.setVisibility(View.VISIBLE);

        if (facilities != null)
            for (Facility f : facilities) {
                NetworkImageView imageView = (NetworkImageView) inflater.inflate(R.layout.item_facility_38dp, facilityLayout, false);
                imageView.setDefaultImageResId(R.drawable.ic_cd);
                imageView.setErrorImageResId(R.drawable.ic_cd);
                imageView.setImageUrl(f.image_new, imageLoader);
                imageView.setContentDescription(f.tag);
                facilityLayout.addView(imageView);
            }
    }

    /*private void getInfo(ArrayList<String> heads, ArrayList<String> details) {
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
    }*/

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
    public void onAttach(Context activity) {
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

        this.mShortListTV.setEnabled(true);
        this.mShortListTV.setVisibility(View.VISIBLE);
        this.mProgressBar.setVisibility(View.GONE);
            if (mInstitute.getIs_shortlisted() == Constants.SHORTLISTED_NO) {
                this.mShortListTV.setText("Shortlist " + mInstitute.getShort_name());
                this.mShortListTV.setBackgroundResource(R.drawable.bg_button_blue);
            } else {
                this.mShortListTV.setText("Delete " + mInstitute.getShort_name() + " from your shortlist");
                this.mShortListTV.setBackgroundResource(R.drawable.bg_button_grey);
            }
    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }


    public interface OnInstituteShortlistedListener {
        void onInstituteShortlisted();
    }


}
