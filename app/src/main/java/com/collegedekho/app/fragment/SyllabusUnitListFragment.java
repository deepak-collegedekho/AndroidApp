package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.SyllabusUnitsExpandableListAdapter;
import com.collegedekho.app.entities.Units;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SyllabusUnitListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SyllabusUnitListFragment extends BaseFragment {
    public static final String TITLE = "SyllabusUnitListFragment";
    private static final String ARG_UNIT_LIST = "unitList";
    private ArrayList<Units> mUnits;
    private String mTitle;
    private SyllabusUnitsExpandableListAdapter mAdapter;
    private MainActivity mMainActivity;
    private TextView mEmptyTextView;
    private Button btnSubmit;
    private static int prev = -1;

    public SyllabusUnitListFragment() {
        // Required empty public constructor
    }

    public static SyllabusUnitListFragment newInstance(ArrayList<Units> units) {
        SyllabusUnitListFragment fragment = new SyllabusUnitListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_UNIT_LIST, units);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUnits = getArguments().getParcelableArrayList(ARG_UNIT_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_syllabus_units, container, false);
        setUp(rootView);
        return rootView;
    }

    public void setUp(View view){
        final ExpandableListView elv = (ExpandableListView) view.findViewById(R.id.units_expandable_list);
        btnSubmit=(Button)view.findViewById(R.id.btn_submit_units);
        btnSubmit.setOnClickListener(this);
        btnSubmit.setVisibility(View.GONE);
        if(elv != null) {
            mAdapter = new SyllabusUnitsExpandableListAdapter(this.getActivity(), this.mUnits);

            elv.setAdapter(mAdapter);
            elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (prev != -1 && prev != groupPosition) {
                        elv.collapseGroup(prev);
                    }
                    prev = groupPosition;
                }
            });
            if (this.mUnits.size() < 2)
                elv.expandGroup(0);
        }
        if(mUnits != null && !mUnits.isEmpty()){
            ((TextView)view.findViewById(R.id.unit_page_title)).setText(mUnits.get(0).getSubject_name());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

/*
        this.mMainActivity = (MainActivity) this.getActivity();

        if (this.mMainActivity != null)
            this.mMainActivity.currentFragment = this;

        if (mUnits.size() == 0)
        {
            this.mEmptyTextView.setText("No Subjects");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            this.mEmptyTextView.setText("");
            this.mEmptyTextView.setVisibility(View.GONE);
        }
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit_units:
                ((MainActivity)getActivity()).onBackPressed();
                break;
        }
    }
}
