package com.collegedekho.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.InstituteListAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInstituteSelectedListener} interface
 * to handle interaction events.
 * Use the {@link InstituteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteListFragment extends Fragment {
    private static final String ARG_INSTITUTE = "institute";
    private static final String ARG_TITLE = "title";

    private ArrayList<Institute> mInstitutes;
    private String mTitle;
    private OnInstituteSelectedListener mListener;

    public InstituteListFragment() {
        // Required empty public constructor
    }

    public static InstituteListFragment newInstance(ArrayList<Institute> institutes, String title) {
        InstituteListFragment fragment = new InstituteListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInstitutes = getArguments().getParcelableArrayList(ARG_INSTITUTE);
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_listing, container, false);
        ((TextView) rootView.findViewById(R.id.textview_page_title)).setText(mTitle);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.institute_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new InstituteListAdapter(getActivity(), mInstitutes));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        rootView.findViewById(R.id.button_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onFilterButtonClicked();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnInstituteSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnInstituteSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnInstituteSelectedListener {
        void onInstituteSelected(int position);

        void onInstituteLikedDisliked(int position, int liked);

        void onFilterButtonClicked();
    }

}
