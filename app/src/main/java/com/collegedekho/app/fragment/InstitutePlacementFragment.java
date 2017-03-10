package com.collegedekho.app.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.Placements;
import com.collegedekho.app.network.MySingleton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstitutePlacementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstitutePlacementFragment extends BaseFragment {
    private static final String ARG_PLACEMENT = "placement";
    private static final String ARG_INSTITUTE = "institute";

    private Placements mPlacement;
    private Institute mInstitute;

    public InstitutePlacementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param placement Parameter 1.
     * @return A new instance of fragment InstituteOverviewFragment.
     */
    public static InstitutePlacementFragment newInstance(Placements placement, Institute institute) {
        InstitutePlacementFragment fragment = new InstitutePlacementFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PLACEMENT, placement);
        args.putParcelable(ARG_INSTITUTE, institute);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mPlacement = getArguments().getParcelable(ARG_PLACEMENT);
            this.mInstitute = getArguments().getParcelable(ARG_INSTITUTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_placement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            if(mPlacement == null)
                mPlacement = new Placements();

        if (mPlacement.about != null)
            ((TextView) view.findViewById(R.id.about_placement)).setText(Html.fromHtml(mPlacement.about));
        else
            ((TextView) view.findViewById(R.id.about_placement)).setText("No Placement Data Available");

        if (mPlacement.placementPercentage != null && !mPlacement.placementPercentage.isEmpty()) {
            ((TextView) view.findViewById(R.id.text_placement_perc)).setText(mPlacement.placementPercentage);
            view.findViewById(R.id.placement_container).setContentDescription("Placement " + mPlacement.placementPercentage + " percent");
        } else {
            view.findViewById(R.id.text_placement_perc).setVisibility(View.GONE);
            view.findViewById(R.id.subhead_placement).setVisibility(View.GONE);
            view.findViewById(R.id.placement_container).setVisibility(View.GONE);
        }

        if (mPlacement.highestSalary != null && mPlacement.highestSalary > 0) {
            double salary = mPlacement.highestSalary / 100000;
            ((TextView) view.findViewById(R.id.text_highest_package)).setText("" + salary+" Lac");
            view.findViewById(R.id.highest_package_container).setContentDescription("Highest Package offered " + salary + " Lac");
        }else {
            view.findViewById(R.id.text_highest_package).setVisibility(View.GONE);
            view.findViewById(R.id.subhead_highest_package).setVisibility(View.GONE);
            view.findViewById(R.id.highest_package_container).setVisibility(View.GONE);
        }

        if (mPlacement.averageSalary != null && mPlacement.averageSalary > 0) {
            double avgSalary = mPlacement.averageSalary / 100000;
            ((TextView) view.findViewById(R.id.text_average_package)).setText("" + avgSalary + " Lac");
            view.findViewById(R.id.average_package_container).setContentDescription("Average Package offered " + avgSalary + " Lac");
        } else {
            view.findViewById(R.id.text_average_package).setVisibility(View.GONE);
            view.findViewById(R.id.subhead_average_package).setVisibility(View.GONE);
            view.findViewById(R.id.average_package_container).setVisibility(View.GONE);
        }

        NetworkImageView imageView = ((NetworkImageView) view.findViewById(R.id.placement_image));
        imageView.setDefaultImageResId(R.drawable.default_banner);
        imageView.setErrorImageResId(R.drawable.default_banner);
        if (this.mInstitute.getImages().get("Primary") != null) {
            ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
            imageView.setImageUrl(this.mInstitute.getImages().get("Primary"), imageLoader);
        }

        if(mInstitute != null && mInstitute.getShort_name() != null && !mInstitute.getShort_name().equalsIgnoreCase("null") ) {
            imageView.setContentDescription(mInstitute.getShort_name() + " Placement Image");
        } else if (mInstitute != null && mInstitute.getName() != null && !mInstitute.getName().equalsIgnoreCase("null")){
            String description =  mInstitute.getName();
            if(mInstitute.getCity_name() != null && !mInstitute.getCity_name().equalsIgnoreCase("null")){
                description = description + " " + mInstitute.getCity_name();
            } else if(mInstitute.getState_name() != null && !mInstitute.getState_name().equalsIgnoreCase("null")){
                description = description + " " + mInstitute.getState_name();
            }
            imageView.setContentDescription(description + " Placement Image");
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
}
