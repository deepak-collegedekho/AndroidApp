package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.InstituteShortlistAdapter;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.tag.textview.ContactsCompletionView;
import com.collegedekho.app.widget.tag.textview.FilteredArrayAdapter;
import com.collegedekho.app.widget.tag.textview.TokenCompleteTextView;

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
    private static final String ARG_FILTER_ALLOWED = "filter_allowed";
    private static final String ARG_FILTER_COUNT = "filter_count";
    private ArrayList<Institute> mShortlistedInstitutes;
    private String mTitle;
    private InstituteShortlistAdapter mAdapter;
    private boolean filterAllowed;
    private int filterCount;
    private MainActivity mMainActivity;
    private TextView mEmptyTextView;

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

        ((TextView) rootView.findViewById(R.id.shortlist_title)).setText(this.mTitle);
        this.progressBarLL = (LinearLayout) rootView.findViewById(R.id.progressBarLL);
        GridView grid = (GridView) rootView.findViewById(R.id.shortlist_container);

        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        if (this.mShortlistedInstitutes.size() == 0)
            this.mEmptyTextView.setText("No Shortlisted Institutes");

        this.mAdapter = new InstituteShortlistAdapter(getActivity(), this.mShortlistedInstitutes);
        grid.setAdapter(this.mAdapter);

        return rootView;
    }


  @Override
    public void onAttach(Context context) {
      super.onAttach(context);
/*      try {
          if(context instanceof  MainActivity)
              listener = (OnShortlistedInstituteSelectedListener) context;
      } catch (ClassCastException e) {
          throw new ClassCastException(context.toString()
                  + " must implement OnInstituteSelectedListener");
      }*/
  }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_INSTITUTE, this.mShortlistedInstitutes);
        outState.putString(ARG_TITLE, this.mTitle);
        outState.putString(ARG_NEXT, this.mNextUrl);
        outState.putBoolean(ARG_FILTER_ALLOWED, this.filterAllowed);
        outState.putInt(ARG_FILTER_COUNT, this.filterCount);
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

        if (this.mShortlistedInstitutes.size() == 0)
        {
            this.mEmptyTextView.setText("No Shortlisted Institutes");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            this.mEmptyTextView.setText("");
            this.mEmptyTextView.setVisibility(View.GONE);
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
        void onInstituteSelected(int position);

        void onInstituteLikedDisliked(int position, int liked);

        void onFilterButtonClicked();

        void onFilterApplied();

        @Override
        void onEndReached(String next, int type);
    }
}
