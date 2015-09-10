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

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.FacetListAdapter;
import com.collegedekho.app.adapter.FilterTypeAdapter;
import com.collegedekho.app.entities.Folder;
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
public class FilterFragment extends Fragment {
    private static final String ARG_FOLDER_LIST = "param1";

    private ArrayList<Folder> mFolderList;

    private OnFilterInteractionListener mListener;
    private FacetListAdapter mFacetAdapter;

    private int currentPos;
    private RecyclerView l;

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
            mFolderList = getArguments().getParcelableArrayList(ARG_FOLDER_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        RecyclerView r = (RecyclerView) rootView.findViewById(R.id.filter_type_list);
        r.setItemAnimator(new DefaultItemAnimator());
        r.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        r.setLayoutManager(new LinearLayoutManager(getActivity()));
        r.setAdapter(new FilterTypeAdapter(getActivity(), mFolderList));
        l = (RecyclerView) rootView.findViewById(R.id.filter_list);
        mFacetAdapter = new FacetListAdapter(getActivity(), new ArrayList<>(mFolderList.get(currentPos).getFacets()));
        l.setAdapter(mFacetAdapter);
        l.setItemAnimator(new DefaultItemAnimator());
        l.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        l.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        currentPos = position;
        mFacetAdapter.updateFilters(mFolderList.get(position).getFacets());
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
}
