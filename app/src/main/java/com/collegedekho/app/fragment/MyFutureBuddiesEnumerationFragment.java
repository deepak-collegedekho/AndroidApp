package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.MyFBEnumerationAdapter;
import com.collegedekho.app.entities.MyFutureBuddiesEnumeration;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;
import java.util.List;

public class MyFutureBuddiesEnumerationFragment extends BaseFragment {
    public static final String TITLE = "Forums";

    private static final String ARG_PARAM1 = "param1";

    private ArrayList<MyFutureBuddiesEnumeration> mFbEnumeration;
    private MyFBEnumerationAdapter mMyFBEnumerationAdapter;
    private TextView mEmptyTextView;
    private boolean IS_TUTE_COMPLETED = true;

    public static MyFutureBuddiesEnumerationFragment newInstance(ArrayList<MyFutureBuddiesEnumeration> fbEnumeration, String next) {
        MyFutureBuddiesEnumerationFragment fragment = new MyFutureBuddiesEnumerationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, fbEnumeration);
        args.putString(ARG_NEXT, next);
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
            mNextUrl = getArguments().getString(ARG_NEXT);
            listType = Constants.FORUM_LIST_TYPE;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_future_buddies_enumeration, container, false);
        IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(Constants.MY_FB_SCREEN_TUTE, false);

        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        this.progressBarLL = (LinearLayout) rootView.findViewById(R.id.progressBarLL);


        RecyclerView fbEnumerationView = (RecyclerView) rootView.findViewById(R.id.fb_enumeration);

        this.mMyFBEnumerationAdapter = new MyFBEnumerationAdapter(getActivity(), this.mFbEnumeration);
        layoutManager = new LinearLayoutManager(getActivity());
        fbEnumerationView.setLayoutManager(layoutManager);
        fbEnumerationView.setAdapter(this.mMyFBEnumerationAdapter);
        fbEnumerationView.setItemAnimator(new DefaultItemAnimator());
        fbEnumerationView.addOnScrollListener(scrollListener);

        if (this.mFbEnumeration.size() == 0)
        {
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setText("Uh oh! You need to shortlist or like colleges to interact and chat with fellow aspirants & seniors of a college.");
            fbEnumerationView.setVisibility(View.GONE);
        }
        else
        {
            this.mEmptyTextView.setVisibility(View.GONE);
            fbEnumerationView.setVisibility(View.VISIBLE);
        }

        rootView.findViewById(R.id.myfb_tour_guide_image).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setVisibility(View.GONE);
                IS_TUTE_COMPLETED = true;
                getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(Constants.MY_FB_SCREEN_TUTE, true).apply();
                return false;
            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof  MainActivity)
                listener = (OnMyFBSelectedListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMyFBSelectedListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        System.gc();
    }

    @Override
    public void onPause() {
        super.onPause();
        loading=false;
    }

    @Override
    public void onResume() {
        super.onResume();

        //mark it current fragment here
        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;

        View view =  getView();
        if(view != null ){
            if(!IS_TUTE_COMPLETED)
                view.findViewById(R.id.myfb_tour_guide_image).setVisibility(View.VISIBLE);
            else
                view.findViewById(R.id.myfb_tour_guide_image).setVisibility(View.GONE);
        }
    }

    public void updateList(List<MyFutureBuddiesEnumeration> myfbenumrationList, String next) {
        this.progressBarLL.setVisibility(View.GONE);
        this.mFbEnumeration.addAll(myfbenumrationList);
        this.mMyFBEnumerationAdapter.notifyDataSetChanged();
        this.loading = false;
        this.mNextUrl = next;
    }

    public interface OnMyFBSelectedListener extends BaseListener{
        void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position, int commentsCount);
        void onNoInternetConnection();
        @Override
        void onEndReached(String next, int type);
    }

    public void updateEnumerationList(int commentsSetSize, int myFbEnumerationIndex)
    {
        (this.mFbEnumeration.get(myFbEnumerationIndex)).setComments_count((this.mFbEnumeration.get(myFbEnumerationIndex)).getComments_count() + commentsSetSize);

        this.mMyFBEnumerationAdapter.notifyItemChanged(myFbEnumerationIndex);
        this.mMyFBEnumerationAdapter.notifyDataSetChanged();

    }
}
