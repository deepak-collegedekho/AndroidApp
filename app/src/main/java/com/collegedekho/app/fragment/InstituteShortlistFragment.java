package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.InstituteShortListAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnShortlistedInstituteSelectedListener} interface
 * to handle interaction events.
 * Use the {@link InstituteShortlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteShortlistFragment extends BaseFragment {
    public static final String TITLE = "ShortlistedInstitutes";
    private static final String ARG_INSTITUTE = "institute";
    private ArrayList<Institute> mShortlistedInstitutes;
    private String mTitle;
    private InstituteShortListAdapter mAdapter;
    private MainActivity mMainActivity;
    private int mViewType = Constants.VIEW_INTO_GRID;

    public InstituteShortlistFragment() {
        // Required empty public constructor
    }

    public static InstituteShortlistFragment newInstance(ArrayList<Institute> institutes, String title, String next) {
        InstituteShortlistFragment fragment = new InstituteShortlistFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mShortlistedInstitutes = getArguments().getParcelableArrayList(ARG_INSTITUTE);
            this.mTitle = getArguments().getString(ARG_TITLE);
            this.mNextUrl = getArguments().getString(ARG_NEXT);
            this.listType = Constants.INSTITUTE_TYPE;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shortlisted_institute_listing, container, false);


        (rootView).findViewById(R.id.view_into_grid).setOnClickListener(this);
        (rootView).findViewById(R.id.view_into_list).setOnClickListener(this);

        ((TextView) rootView.findViewById(R.id.shortlist_title)).setText(this.mTitle);
        this.progressBarLL = (LinearLayout) rootView.findViewById(R.id.progressBarLL);

        RecyclerView recyclerView  = (RecyclerView)rootView.findViewById(R.id.recyclerView_shortList_institute);
         if(this.mViewType == Constants.VIEW_INTO_GRID)
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        else
             recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        this.mAdapter = new InstituteShortListAdapter(getActivity(), this.mShortlistedInstitutes, this.mViewType);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,10,true));
        recyclerView.setAdapter(mAdapter);
        // recyclerView.addOnScrollListener(scrollListener);
        return rootView;
    }


  @Override
    public void onAttach(Context context) {
      super.onAttach(context);
     try {
          if(context instanceof  MainActivity)
              listener = (OnShortlistedInstituteSelectedListener) context;
      } catch (ClassCastException e) {
          throw new ClassCastException(context.toString()
                  + " must implement OnInstituteSelectedListener");
      }
  }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_INSTITUTE, this.mShortlistedInstitutes);
        outState.putString(ARG_TITLE, this.mTitle);
        outState.putString(ARG_NEXT, this.mNextUrl);
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
        View view = getView();
          if(view != null) {
              if (this.mShortlistedInstitutes.size() == 0)
                  (view.findViewById(android.R.id.empty)).setVisibility(View.VISIBLE);
               else
                  (view.findViewById(android.R.id.empty)).setVisibility(View.GONE);

          }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.view_into_grid:
                View rootView = getView();
                if(rootView != null && mViewType != Constants.VIEW_INTO_GRID) {
                    this.mViewType = Constants.VIEW_INTO_GRID;
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_shortList_institute);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    this.mAdapter = new InstituteShortListAdapter(getActivity(), this.mShortlistedInstitutes, Constants.VIEW_INTO_GRID);
                    recyclerView.setAdapter(this.mAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
                }

                break;
            case R.id.view_into_list:
                View rootView1 = getView();
                if(rootView1 != null && mViewType != Constants.VIEW_INTO_LIST) {
                    this.mViewType = Constants.VIEW_INTO_LIST;
                    RecyclerView recyclerView1 = (RecyclerView) rootView1.findViewById(R.id.recyclerView_shortList_institute);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    this.mAdapter = new InstituteShortListAdapter(getActivity(), this.mShortlistedInstitutes, Constants.VIEW_INTO_LIST);
                    recyclerView1.setAdapter(this.mAdapter);
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                }
                break;
            default:
                break;

        }
    }

    public void clearList() {
        this.mShortlistedInstitutes.clear();
        this.mAdapter.notifyDataSetChanged();
    }

    public void updateList(List<Institute> institutes, String next) {
        this.progressBarLL.setVisibility(View.GONE);
        this.mShortlistedInstitutes.addAll(institutes);
        this.mAdapter.notifyDataSetChanged();
        this.loading = false;
        this.mNextUrl = next;
    }

    public interface OnShortlistedInstituteSelectedListener extends BaseListener {

        @Override
        void onEndReached(String next, int type);
    }
}
