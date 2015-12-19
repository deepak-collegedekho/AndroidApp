package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.MySingleton;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 16/11/15.
 */
public class InstituteInfrastructureFragment extends BaseFragment {

    private static final String ARG_INFRA = "infra";

    private Institute mInstitute;

    public InstituteInfrastructureFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param institute Parameter 1.
     * @return A new instance of fragment InstituteInfrastructureFragment.
     */
    public static InstituteInfrastructureFragment newInstance(Institute institute) {
        InstituteInfrastructureFragment fragment = new InstituteInfrastructureFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INFRA, institute);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mInstitute = getArguments().getParcelable(ARG_INFRA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_infrastructure, container, false);

        NetworkImageView imageView = ((NetworkImageView) rootView.findViewById(R.id.infra_image));
        imageView.setDefaultImageResId(R.drawable.default_banner);
        imageView.setErrorImageResId(R.drawable.default_banner);
        if (this.mInstitute.getImages().get("Infra") != null) {
            ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
            imageView.setImageUrl(this.mInstitute.getImages().get("Infra"), imageLoader);
        }

        ((TextView) rootView.findViewById(R.id.infra_about)).setText(this.mInstitute.getInfra_snap());

        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.infrastructure_college_facility_list);
        setupFacilities(inflater, layout, this.mInstitute.getFacilities());

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


}