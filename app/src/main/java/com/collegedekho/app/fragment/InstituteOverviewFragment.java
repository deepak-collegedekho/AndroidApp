package com.collegedekho.app.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        View view = inflater.inflate(R.layout.fragment_institute_overview, container, false);
        LinearLayout facilityLayout = (LinearLayout) view.findViewById(R.id.college_facility_list);
        View facilityLayoutScrollView =  view.findViewById(R.id.college_facility_list_scrollview);
        ArrayList<Facility> facilityList = this.mInstitute.getFacilities();
        if (facilityList != null && facilityList.size() <= 0) {
            facilityLayoutScrollView.setVisibility(View.GONE);
        }else {
            facilityLayoutScrollView.setVisibility(View.VISIBLE);
            setupFacilities(inflater, facilityLayout, this.mInstitute.getFacilities());
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.textview_college_name)).setText(mInstitute.getName());
        String text = "";
        if (this.mInstitute.getCity_name() != null)
            text += this.mInstitute.getCity_name() + ", ";
        if (this.mInstitute.getState_name() != null)
            text += this.mInstitute.getState_name();
        if ((this.mInstitute.getState_name() != null || this.mInstitute.getCity_name() != null) && this.mInstitute.getEstb_date() != null)
            text += " | ";
        if (this.mInstitute.getEstb_date() != null)
            text += "Established in: " + this.mInstitute.getEstb_date().substring(0, 4);

        NetworkImageView imageView = ((NetworkImageView) view.findViewById(R.id.overview_image));
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

        ((TextView) view.findViewById(R.id.textview_college_location)).setText(text);
        if(this.mInstitute.getDescription() != null) {
            Spanned spanned = Html.fromHtml(this.mInstitute.getDescription());
            if (spanned != null) {
                ((TextView) view.findViewById(R.id.overview_text)).setText(spanned);
            }
        }

        ((TextView) view.findViewById(R.id.textview_why_join)).setText("Why join " + mInstitute.getShort_name());
         this.setupInfo((LinearLayout) view.findViewById(R.id.college_info_ll1)
                , (LinearLayout) view.findViewById(R.id.college_info_ll2));
        view.findViewById(R.id.institute_share_button).setOnClickListener(this);
    }

    private void setupFacilities(LayoutInflater inflater, LinearLayout facilityLayout, ArrayList<Facility> facilities) {
        ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();

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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case  R.id.institute_share_button:
                this.shareInstitute();
                break;
            default:
                break;
        }
    }

    private void shareInstitute(){
        if(mInstitute == null)
            return;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "CollegeDekho App");
        String shareableText = "Please see this College I found at CollegeDekho..\n";
        shareableText = shareableText + "\n" + mInstitute.getName() + "\n";
        shareableText = shareableText + "\nhttps://www.collegedekho.com/colleges/"+ mInstitute.getUri_slug() + "\n\n";
        shareableText = shareableText + "CollegeDekho \nDiscover.Prepare.Achieve";
        i.putExtra(Intent.EXTRA_TEXT, shareableText);
        getActivity().startActivity(Intent.createChooser(i, "Share"));

    }


    @Override
    public String getEntity() {
        return null;
    }

}
