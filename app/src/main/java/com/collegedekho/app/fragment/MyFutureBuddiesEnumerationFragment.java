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
import com.collegedekho.app.entities.MyFutureBuddy;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;

public class MyFutureBuddiesEnumerationFragment extends BaseFragment {
    public static final String TITLE = "Forums";

    private static final String ARG_PARAM1 = "param1";

    private ArrayList<MyFutureBuddiesEnumeration> mFbEnumeration;
    private MyFBEnumerationAdapter mMyFBEnumerationAdapter;
    private TextView mEmptyTextView;
    private MainActivity mMainActivity;

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
            this.mFbEnumeration = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_future_buddies_enumeration, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);


        RecyclerView fbEnumerationView = (RecyclerView) rootView.findViewById(R.id.fb_enumeration);

        this.mMyFBEnumerationAdapter = new MyFBEnumerationAdapter(getActivity(), this.mFbEnumeration);
        fbEnumerationView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fbEnumerationView.setAdapter(this.mMyFBEnumerationAdapter);
        fbEnumerationView.setItemAnimator(new DefaultItemAnimator());
        //fbEnumerationView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        if (this.mFbEnumeration.size() == 0)
        {
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setText("Couldn't find related groups for you. Like and Shortlist colleges.");
            fbEnumerationView.setVisibility(View.GONE);
        }
        else
        {
            this.mEmptyTextView.setVisibility(View.GONE);
            fbEnumerationView.setVisibility(View.VISIBLE);
        }

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

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        //mark it current fragment here
        this.mMainActivity = (MainActivity) this.getActivity();

        if (this.mMainActivity != null)
            this.mMainActivity.currentFragment = this;
    }

    public interface OnMyFBSelectedListener {
        void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position, int commentsCount);
    }

    public void updateEnumerationList(int commentsSetSize, int myFbEnumerationIndex)
    {
        (this.mFbEnumeration.get(myFbEnumerationIndex)).setComments_count((this.mFbEnumeration.get(myFbEnumerationIndex)).getComments_count() + commentsSetSize);

        this.mMyFBEnumerationAdapter.notifyItemChanged(myFbEnumerationIndex);
        this.mMyFBEnumerationAdapter.notifyDataSetChanged();
    }
}
