package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.MyFBEnumerationAdapter;
import com.collegedekho.app.entities.MyFutureBuddiesEnumeration;
import com.collegedekho.app.network.ApiEndPonits;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;
import java.util.List;

public class MyFutureBuddiesEnumerationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TITLE = "Forums";

    private static final String ARG_PARAM1 = "param1";

    private ArrayList<MyFutureBuddiesEnumeration> mFbEnumeration;
    private MyFBEnumerationAdapter mMyFBEnumerationAdapter;
    private boolean IS_TUTE_COMPLETED = true;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private OnMyFBSelectedListener mListener;

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
        View mRootView = inflater.inflate(R.layout.fragment_my_future_buddies_enumeration, container, false);
        IS_TUTE_COMPLETED = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getString(R.string.MY_FB_SCREEN_TUTE), false);

        TextView mEmptyTextView = (TextView) mRootView.findViewById(android.R.id.empty);
        this.progressBarLL = (LinearLayout) mRootView.findViewById(R.id.progressBarLL);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.fb_enumeration_swipe_refresh_container);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView fbEnumerationView = (RecyclerView) mRootView.findViewById(R.id.fb_enumeration);

        this.mMyFBEnumerationAdapter = new MyFBEnumerationAdapter(getActivity(), this.mFbEnumeration);
        layoutManager = new LinearLayoutManager(getActivity());
        fbEnumerationView.setLayoutManager(layoutManager);
        fbEnumerationView.setAdapter(this.mMyFBEnumerationAdapter);
        fbEnumerationView.setItemAnimator(new DefaultItemAnimator());
        fbEnumerationView.addOnScrollListener(scrollListener);

        if (this.mFbEnumeration.size() == 0){
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText("Uh oh! You need to shortlist or like colleges to interact and chat with fellow aspirants & seniors of a college.");
            fbEnumerationView.setVisibility(View.GONE);
        } else  {
            mEmptyTextView.setVisibility(View.GONE);
            fbEnumerationView.setVisibility(View.VISIBLE);
        }

        mRootView.findViewById(R.id.myfb_tour_guide_image).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setVisibility(View.GONE);
                IS_TUTE_COMPLETED = true;
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.MY_FB_SCREEN_TUTE), true).apply();
                return false;
            }
        });

        return mRootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof  MainActivity) {
                listener = (OnMyFBSelectedListener) context;
                mListener = (OnMyFBSelectedListener) context;
            }
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
        {
            mMainActivity.currentFragment = this;
            if (mMainActivity.getOtherAppSharedMessage() != null && !mMainActivity.getOtherAppSharedMessage().trim().isEmpty())
                Toast.makeText(this.getContext(), "Please select a college to share the content with", Toast.LENGTH_SHORT).show();
        }

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


    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void onRefresh() {
        if(NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
           return;
        }
        this.mRefreshChatRoom(Constants.REFRESH_CHATROOM, ApiEndPonits.API_PERSONALIZE_FORUMS);
    }

    private void mRefreshChatRoom(String requestType, String url)
    {
        if(mListener != null)
            this.mListener.onChatRoomSwipedDown(requestType, url);
    }

    public void refreshChatRoom(ArrayList<MyFutureBuddiesEnumeration> mFbEnumeration) {
        this.mMyFBEnumerationAdapter.setUpEnumerationData(mFbEnumeration);
        this.mMyFBEnumerationAdapter.notifyDataSetChanged();
        this.mSwipeRefreshLayout.setRefreshing(false);
    }
    public  void chetRoomSwipRefreshfailed(){
        if(isAdded() && this.mSwipeRefreshLayout != null) {
            this.mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public interface OnMyFBSelectedListener extends BaseListener{
        void onMyFBSelected(MyFutureBuddiesEnumeration myFutureBuddiesEnumeration, int position, int commentsCount);
        void onChatRoomSwipedDown(String requestType, String url);
        void displayMessage(int messageId);
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
