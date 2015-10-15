package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.InstituteListAdapter;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.DividerItemDecoration;
import com.collegedekho.app.widget.tag.textview.ContactsCompletionView;
import com.collegedekho.app.widget.tag.textview.FilteredArrayAdapter;
import com.collegedekho.app.widget.tag.textview.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInstituteSelectedListener} interface
 * to handle interaction events.
 * Use the {@link InstituteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteListFragment extends BaseFragment implements TokenCompleteTextView.TokenListener {
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

    private ContactsCompletionView mCompletionView;
    private ArrayAdapter<String> tolenAdapter;


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

        mCompletionView = (ContactsCompletionView)rootView.findViewById(R.id.searchView);
        mCompletionView.setAdapter(tolenAdapter);
        mCompletionView.setTokenListener(this);
        mCompletionView.setInputType(InputType.TYPE_NULL);
        mCompletionView.allowDuplicates(false);
        mCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);

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

            setFilterList();
            rootView.findViewById(R.id.button_filter).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onFilterButtonClicked();
                }
            });
        } else {

            rootView.findViewById(R.id.button_filter).setVisibility(View.GONE);
            rootView.findViewById(R.id.filter_tokenLL).setVisibility(View.GONE);
        }


        tolenAdapter = new FilteredArrayAdapter<String>(getActivity(), R.layout.contact_token, new String[]{}) {
            @Override
            protected boolean keepObject(String obj, String mask) {
                Log.e("", "");
                return false;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.contact_token, parent, false);
                }
                return convertView;
            }
        };


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
        updateFilterButton(filterCount);

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
        if(filterAllowed) {
            if (mCompletionView != null && mCompletionView.getObjects().size() > 0) {
                mCompletionView.clear();
            }
            setFilterList();
        }

    }

    public void updateButtons(int position) {
        mAdapter.updateLikeButtons(position);
    }

    public void updateFilterButton(int filterCount)
    {
        if(!filterAllowed) return;// do not need on my shortlist page
        View v = getView();
        if (v != null) {
            if (filterCount <= 2) {
                getView().findViewById(R.id.filter_tokenLL).setVisibility(View.GONE);
                ((ImageView) v.findViewById(R.id.button_filter)).setImageResource(R.drawable.ic_filter);
            }
            else {
                getView().findViewById(R.id.filter_tokenLL).setVisibility(View.VISIBLE);
                ((ImageView) v.findViewById(R.id.button_filter)).setImageResource(R.drawable.ic_filter_selected);
            }
       }
    }

    private void updateFilterTokenConfirmation(Object token) {

        ArrayList<Folder> folderList = ((MainActivity) getActivity()).getFilterList();
        for (Folder f : folderList) {
            for (Facet ft : f.getFacets())
                if(ft.getTag().equalsIgnoreCase(token.toString())) {
                    ft.deselect();
                    break;
                }
        }

        if(listener != null)
            listener.onFilterApplied();

    }

    private void setFilterList()
    {
        ArrayList<Folder> folderList = ((MainActivity) getActivity()).getFilterList();
        for (Folder f : folderList) {
            if(f.getLabel().equalsIgnoreCase("stream") || f.getLabel().equalsIgnoreCase("level"))
                continue;

            for (Facet ft : f.getFacets())
                if (ft.isSelected() == 1)
                    mCompletionView.addObject(ft.getTag());
        }

    }


    @Override
    public void onTokenAdded(Object token) {
    }

    @Override
    public void onTokenRemoved(Object token)
    {
        if(!Constants.SEND_REQUEST)return;
        updateFilterTokenConfirmation(token);
    }


    public interface OnInstituteSelectedListener extends BaseListener {
        void onInstituteSelected(int position);

        void onInstituteLikedDisliked(int position, int liked);

        void onFilterButtonClicked();

        void onFilterApplied();

        @Override
        void onEndReached(String next, int type);
    }

    public boolean getFilterAllowed()
    {
        return filterAllowed;
    }
}
