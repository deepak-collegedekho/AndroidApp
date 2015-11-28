package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;

/**
 * Created by sureshsaini on 27/11/15.
 */
public class EntranceSummeryFragment extends  BaseFragment {

    public static EntranceSummeryFragment newInstance() {
        EntranceSummeryFragment fragment = new EntranceSummeryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public EntranceSummeryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entrance_summery, container, false);

        //ViewPager pager = (ViewPager)rootView.findViewById(R.id.)

        return rootView;
    }



}
