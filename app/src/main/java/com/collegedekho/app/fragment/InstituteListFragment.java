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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.InstituteListAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInstituteSelectedListener} interface
 * to handle interaction events.
 * Use the {@link InstituteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteListFragment extends BaseFragment {
    public static final String TITLE = "Institutes";
    private static final String ARG_INSTITUTE = "institute";
    private static final String ARG_FILTER_ALLOWED = "filter_allowed";
    private static final String ARG_FILTER_COUNT = "filter_count";
    private ArrayList<Institute> mInstitutes;
    private String mTitle;
    private InstituteListAdapter mAdapter;
    private boolean filterAllowed;
    private int filterCount;
    private MainActivity mMainActivity;
    private TextView mEmptyTextView;



    public InstituteListFragment() {
        // Required empty public constructor
    }

    public static InstituteListFragment newInstance(ArrayList<Institute> institutes, String title, String next, boolean filterAllowed, int filterCount) {
        InstituteListFragment fragment = new InstituteListFragment();
        Bundle args = new Bundle();

        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        args.putBoolean(ARG_FILTER_ALLOWED, filterAllowed);
        args.putInt(ARG_FILTER_COUNT, filterCount);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInstitutes = getArguments().getParcelableArrayList(ARG_INSTITUTE);
            mTitle = getArguments().getString(ARG_TITLE);
            nextUrl = getArguments().getString(ARG_NEXT);
            filterAllowed = getArguments().getBoolean(ARG_FILTER_ALLOWED);
            filterCount = getArguments().getInt(ARG_FILTER_COUNT);
            listType = Constants.INSTITUTE_TYPE;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_listing, container, false);
        ((TextView) rootView.findViewById(R.id.textview_page_title)).setText(mTitle);
        progressBarLL     =   (LinearLayout)rootView.findViewById(R.id.progressBarLL);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.institute_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new InstituteListAdapter(getActivity(), mInstitutes);

        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        if (mInstitutes.size() == 0)
            this.mEmptyTextView.setText("No Institutes");

        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(scrollListener);
        if (filterAllowed) {
            rootView.findViewById(R.id.button_filter).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onFilterButtonClicked();
                }
            });
        } else
            rootView.findViewById(R.id.button_filter).setVisibility(View.GONE);

        if (filterCount > 0)
            ((ImageView) rootView.findViewById(R.id.button_filter)).setImageResource(R.drawable.ic_filter_selected);
        else
            ((ImageView) rootView.findViewById(R.id.button_filter)).setImageResource(R.drawable.ic_filter);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnInstituteSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnInstituteSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.mMainActivity = (MainActivity) this.getActivity();

        if (this.mMainActivity != null)
            this.mMainActivity.currentFragment = this;

        if (mInstitutes.size() == 0)
        {
            this.mEmptyTextView.setText("No Institutes");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            this.mEmptyTextView.setText("");
            this.mEmptyTextView.setVisibility(View.GONE);
        }
    }

    public void clearList() {
        mInstitutes.clear();
        mAdapter.notifyDataSetChanged();
    }

    public void updateList(List<Institute> institutes, String next) {
        progressBarLL.setVisibility(View.GONE);
        mInstitutes.addAll(institutes);
        mAdapter.notifyDataSetChanged();
        loading = false;
        nextUrl = next;
    }

    public void updateButtons(int position) {
        mAdapter.updateLikeButtons(position);
    }

    public void updateFilterButton(int filterCount) {
        View v = getView();
        if (v != null) {
            if (filterCount > 0)
                ((ImageView) v.findViewById(R.id.button_filter)).setImageResource(R.drawable.ic_filter_selected);
            else
                ((ImageView) v.findViewById(R.id.button_filter)).setImageResource(R.drawable.ic_filter);
        }
    }
    public interface OnInstituteSelectedListener extends BaseListener {
        void onInstituteSelected(int position);

        void onInstituteLikedDisliked(int position, int liked);

        void onFilterButtonClicked();

        @Override
        void onEndReached(String next, int type);
    }

}
