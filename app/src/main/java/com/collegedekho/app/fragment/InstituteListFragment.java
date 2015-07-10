package com.collegedekho.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.InstituteListAdapter;
import com.collegedekho.app.entities.Institute;

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

    private ArrayList<Institute> mInstitutes;

    public InstituteListFragment() {
        // Required empty public constructor
    }

    public static InstituteListFragment newInstance(ArrayList<Institute> institutes) {
        InstituteListFragment fragment = new InstituteListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInstitutes = getArguments().getParcelableArrayList(ARG_INSTITUTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_college_listing, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new InstituteListAdapter(getActivity(), mInstitutes));
        return recyclerView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnInstituteSelectedListener {
        void onCollegeSelected(int position);
    }

}
