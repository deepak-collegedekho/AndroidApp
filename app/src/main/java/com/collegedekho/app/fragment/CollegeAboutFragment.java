package com.collegedekho.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollegeAboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollegeAboutFragment extends Fragment {
    private static final String ARG_SHORT_NAME = "short_name";
    private static final String ARG_ABOUT = "about";

    private String mShortName;
    private String mAbout;


    public CollegeAboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param shortName Parameter 1.
     * @param about     Parameter 2.
     * @return A new instance of fragment CollegeOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CollegeAboutFragment newInstance(String shortName, String about) {
        CollegeAboutFragment fragment = new CollegeAboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHORT_NAME, shortName);
        args.putString(ARG_ABOUT, about);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShortName = getArguments().getString(ARG_SHORT_NAME);
            mAbout = getArguments().getString(ARG_ABOUT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_college_about, container, false);
        ((TextView) rootView.findViewById(R.id.textview_college_shortname)).setText(mShortName);
        ((TextView) rootView.findViewById(R.id.textview_college_about)).setText(Html.fromHtml(mAbout));
        return rootView;
    }


}
