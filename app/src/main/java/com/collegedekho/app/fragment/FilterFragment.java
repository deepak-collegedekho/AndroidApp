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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.FacetListAdapter;
import com.collegedekho.app.adapter.FilterTypeAdapter;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFilterInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_FOLDER_LIST = "param1";

    private ArrayList<Folder> mFolderList;
    private ArrayList<Folder> mCurrentFolders;

    private OnFilterInteractionListener mListener;
    private FacetListAdapter mFacetAdapter;
    private FilterTypeAdapter mFilterTypeAdapter;

    private int currentPos;
    private RecyclerView mFilterRecyclerView;
    private RecyclerView mFilterTypeRecyclerView;
    private LinearLayout mCategoryListContainer;

    private TextView mCategoryButtonCourseAndSpeciality;
    private TextView mCategoryButtonLocation;
    private TextView mCategoryButtonCampusAndHousing;
    private TextView mCategoryButtonTypeAndSupportServices;

    public FilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilterFragment.
     */
    public static FilterFragment newInstance(ArrayList<Folder> folderList) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_FOLDER_LIST, folderList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mFolderList = getArguments().getParcelableArrayList(ARG_FOLDER_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mCurrentFolders = this.mGetFolderOfCategory(Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);

        this.mCategoryButtonCourseAndSpeciality = (TextView) rootView.findViewById(R.id.filter_category_c_and_s);
        this.mCategoryButtonLocation = (TextView) rootView.findViewById(R.id.filter_category_location);
        this.mCategoryButtonTypeAndSupportServices = (TextView) rootView.findViewById(R.id.filter_category_t_and_ss);
        this.mCategoryButtonCampusAndHousing = (TextView) rootView.findViewById(R.id.filter_category_c_and_h);

        this.mCategoryButtonCourseAndSpeciality.setOnClickListener(this);
        this.mCategoryButtonLocation.setOnClickListener(this);
        this.mCategoryButtonTypeAndSupportServices.setOnClickListener(this);
        this.mCategoryButtonCampusAndHousing.setOnClickListener(this);

        this.mCategoryButtonCourseAndSpeciality.setSelected(true);

        this.mCategoryListContainer = (LinearLayout) rootView.findViewById(R.id.filter_category_list);

        this.mFilterTypeAdapter = new FilterTypeAdapter(getActivity(), this.mCurrentFolders);

        this.mFilterTypeRecyclerView = (RecyclerView) rootView.findViewById(R.id.filter_type_list);
        this.mFilterTypeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mFilterTypeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        this.mFilterTypeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mFilterTypeRecyclerView.setAdapter(this.mFilterTypeAdapter);

        this.mFacetAdapter = new FacetListAdapter(getActivity(), new ArrayList<>(this.mCurrentFolders.get(this.currentPos).getFacets()));

        this.mFilterRecyclerView = (RecyclerView) rootView.findViewById(R.id.filter_list);
        this.mFilterRecyclerView.setAdapter(this.mFacetAdapter);
        this.mFilterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mFilterRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        this.mFilterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rootView.findViewById(R.id.button_filter_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFilterApplied();
            }
        });
        rootView.findViewById(R.id.button_filter_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFilterCanceled(false);
            }
        });
        rootView.findViewById(R.id.button_filter_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFilterCanceled(true);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFilterInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFilterInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        //mFolderList = null;
        System.gc();
    }

    public void updateFilterType(int position) {
        this.currentPos = position;
        this.mFacetAdapter.updateFilters(this.mCurrentFolders.get(position).getFacets());
    }

    @Override
    public void onClick(View v) {

        this.mCurrentFolders.clear();

        this.mDeselectAll();

        (v.getRootView().findViewById(v.getId())).setSelected(true);

        switch (v.getId())
        {
            case R.id.filter_category_c_and_s:
                this.mCurrentFolders.addAll(this.mGetFolderOfCategory(Constants.FILTER_CATEGORY_COURSE_AND_SPECIALIZATION));
                break;
            case R.id.filter_category_location:
                this.mCurrentFolders.addAll(this.mGetFolderOfCategory(Constants.FILTER_CATEGORY_LOCATION));
                break;
            case R.id.filter_category_t_and_ss:
                this.mCurrentFolders.addAll(this.mGetFolderOfCategory(Constants.FILTER_CATEGORY_TYPE_AND_SUPPORT_SERVICES));
                break;
            case R.id.filter_category_c_and_h:
                this.mCurrentFolders.addAll(this.mGetFolderOfCategory(Constants.FILTER_CATEGORY_CAMPUS_AND_HOUSING));
                break;
            default:
                break;
        }

        this.mFilterTypeAdapter.notifyDataSetChanged();

        this.updateFilterType(0);
        //this.mFacetAdapter.updateFilters(this.mCurrentFolders.get(0).getFacets());
    }

    private void mDeselectAll()
    {
        this.mCategoryButtonCourseAndSpeciality.setSelected(false);
        this.mCategoryButtonLocation.setSelected(false);
        this.mCategoryButtonTypeAndSupportServices.setSelected(false);
        this.mCategoryButtonCampusAndHousing.setSelected(false);
    }

    private ArrayList<Folder> mGetFolderOfCategory(int category)
    {
        ArrayList<Folder> currentFolders = new ArrayList<>();

        for (Folder f : this.mFolderList)
        {
            if (Constants.FilterCategoryMap.containsKey(f.getId()))
                if (Constants.FilterCategoryMap.get(f.getId()) == category)
                    currentFolders.add(f);
        }

        return currentFolders;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFilterInteractionListener {
        void onFilterApplied();

        void onFilterCanceled(boolean clearAll);

        void onFilterTypeChanged(int position);
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }
}
