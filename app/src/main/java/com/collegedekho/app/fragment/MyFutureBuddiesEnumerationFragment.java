package com.collegedekho.app.fragment;

import android.app.Activity;
import android.net.Uri;
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
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.MyFBEnumerationAdapter;
import com.collegedekho.app.adapter.QnAQuestionsListAdapter;
import com.collegedekho.app.entities.MyFutureBuddiesEnumeration;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;

public class MyFutureBuddiesEnumerationFragment extends Fragment {
    public static final String TITLE = "Forums";

    private static final String ARG_PARAM1 = "param1";

    private ArrayList<MyFutureBuddiesEnumeration> mFbEnumeration;
    private MyFBEnumerationAdapter mMyFBEnumerationAdapter;
    private TextView mEmptyTextView;

    public static MyFutureBuddiesEnumerationFragment newInstance(ArrayList<MyFutureBuddiesEnumeration> fbEnumeration) {
        MyFutureBuddiesEnumerationFragment fragment = new MyFutureBuddiesEnumerationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, fbEnumeration);
        fragment.setArguments(args);
        return fragment;
    }

    public MyFutureBuddiesEnumerationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFbEnumeration = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_future_buddies_enumeration, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        if (mFbEnumeration.size() == 0)
            (this.mEmptyTextView).setText("Couldn't find related groups for you. Like and Shortlist colleges.");

        RecyclerView fbEnumerationView = (RecyclerView) rootView.findViewById(R.id.fb_enumeration);

        this.mMyFBEnumerationAdapter = new MyFBEnumerationAdapter(getActivity(), mFbEnumeration);
        fbEnumerationView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fbEnumerationView.setAdapter(this.mMyFBEnumerationAdapter);
        fbEnumerationView.setItemAnimator(new DefaultItemAnimator());
        //fbEnumerationView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.gc();
    }

    public interface OnMyFBSelectedListener {
        void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position);
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }

}
