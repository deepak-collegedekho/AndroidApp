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
    private  int mViewType = Constants.VIEW_INTO_LIST;

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
            mNextUrl = getArguments().getString(ARG_NEXT);
            listType = Constants.SHORTLIST_TYPE;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shortlisted_institute_listing, container, false);

        if (this.mShortlistedInstitutes == null || this.mShortlistedInstitutes.size() <= 0) {
            (rootView.findViewById(android.R.id.empty)).setVisibility(View.VISIBLE);
            return rootView;
        }

        (rootView).findViewById(R.id.view_into_grid).setOnClickListener(this);
        (rootView).findViewById(R.id.view_into_list).setOnClickListener(this);

        ((TextView) rootView.findViewById(R.id.shortlist_title)).setText(this.mTitle);
        this.progressBarLL = (LinearLayout) rootView.findViewById(R.id.progressBarLL);

        RecyclerView recyclerView  = (RecyclerView)rootView.findViewById(R.id.recyclerView_shortList_institute);
         if(this.mViewType == Constants.VIEW_INTO_GRID) {
             layoutManager = new GridLayoutManager(getActivity(), 2);
             recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 3, false));
         }
        else {
             layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
             recyclerView.setItemAnimator(new DefaultItemAnimator());
         }
        recyclerView.setLayoutManager(layoutManager);
        updateViewTypeIcon(rootView, this.mViewType);
        this.mAdapter = new InstituteShortListAdapter(getActivity(), this.mShortlistedInstitutes, this.mViewType);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(scrollListener);
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
                    layoutManager = new GridLayoutManager(getActivity(), 2);
                    recyclerView.setLayoutManager(layoutManager);
                    this.mAdapter = new InstituteShortListAdapter(getActivity(), this.mShortlistedInstitutes, Constants.VIEW_INTO_GRID);
                    recyclerView.setAdapter(this.mAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 3, true));
                    recyclerView.addOnScrollListener(scrollListener);
                }

                break;
            case R.id.view_into_list:
                View rootView1 = getView();
                if(rootView1 != null && mViewType != Constants.VIEW_INTO_LIST) {
                    this.mViewType = Constants.VIEW_INTO_LIST;
                    RecyclerView recyclerView1 = (RecyclerView) rootView1.findViewById(R.id.recyclerView_shortList_institute);
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView1.setLayoutManager(layoutManager);
                    this.mAdapter = new InstituteShortListAdapter(getActivity(), this.mShortlistedInstitutes, Constants.VIEW_INTO_LIST);
                    recyclerView1.setAdapter(this.mAdapter);
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                    recyclerView1.addOnScrollListener(scrollListener);
                }
                break;
            default:
                break;
        }
        updateViewTypeIcon(getView(), this.mViewType);
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

    public void updateLikeButtons(int position) {
        mAdapter.updateLikeButtons(position);
    }
    public void updateShortlistButton(int position)
    {
        mAdapter.updateShortlistStatus(position);
    }

    public interface OnShortlistedInstituteSelectedListener extends BaseListener {
        void onShortListInstituteSelected(int position);

        void onInstituteUnShortlisted(int position);
        @Override
        void onEndReached(String next, int type);

        @Override
        void onInstituteLikedDisliked(int position, int liked);
    }
}
