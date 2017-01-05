package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.collegedekho.app.adapter.FeedAdapter;
import com.collegedekho.app.entities.Feed;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link onFeedInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static String TITLE = "Home";

    // the fragment initialization parameters
    private static final String FEED_LIST = "feed_list";
    private static final String FEED_FRAGMENT_NEXT_URL = "feed_fragment_next_url";

    @BindView(R.id.feed_list)
    RecyclerView mFeedRecyclerView;

    @BindView(R.id.feed_swipe_refresh_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.feed_empty_layout)
    LinearLayout mEmptyLayout;

    @BindView(R.id.feed_empty)
    TextView mEmptTV;

    private ArrayList<Feed> mFeedList;
    private String mFeedFragmentNextURL;
    private FeedAdapter mFeedAdapter;
    private Unbinder mUnbinder;


    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param feedArrayList Parameter 1.
     * @param nextURL Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    public static FeedFragment newInstance(ArrayList<Feed> feedArrayList, String nextURL) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(FEED_LIST, feedArrayList);
        args.putString(FEED_FRAGMENT_NEXT_URL, nextURL);
        fragment.setArguments(args);

        return fragment;
    }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.listType = Constants.FEED_TYPE;
        if (getArguments() != null) {
            this.mFeedList = getArguments().getParcelableArrayList(FEED_LIST);
            this.mFeedFragmentNextURL = super.mNextUrl = getArguments().getString(FEED_FRAGMENT_NEXT_URL);
        }else {
            this.mFeedList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        this.mUnbinder = ButterKnife.bind(this,rootView);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        if(this.mFeedAdapter == null) {
            this.mFeedAdapter = new FeedAdapter(this.getContext(), this.mFeedList);
        }else{
            this.mFeedAdapter.updateFeedList(this.mFeedList);
        }

        super.layoutManager = new LinearLayoutManager(this.getContext());
        this.mFeedRecyclerView.setLayoutManager(super.layoutManager);
        this.mFeedRecyclerView.setAdapter(this.mFeedAdapter);
        this.mFeedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mFeedRecyclerView.addOnScrollListener(super.scrollListener);
        super.mNextUrl = this.mFeedFragmentNextURL;
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            super.listener = (onFeedInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onFeedInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        super.listener = null;
        if(this.mUnbinder != null)
            this.mUnbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.mFeedList == null || this.mFeedList.size() == 0) {
            if(NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED){
                mEmptyLayout.setVisibility(View.VISIBLE);
                return;
            }
            if(getActivity() != null){
                ((MainActivity) this.getActivity()).mGetFeed(Constants.TAG_LOAD_FEED, Constants.BASE_URL + "feeds/");
            }
        }
        if(mEmptyLayout != null)
            mEmptyLayout.setVisibility(View.GONE);
    }
    @OnClick(R.id.feed_empty_layout)
    public void mLoadFeed(){
        if(NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED){
            return;
        }
        if(getActivity() != null){
            ((MainActivity) this.getActivity()).mGetFeed(Constants.TAG_LOAD_FEED, Constants.BASE_URL + "feeds/");
        }

    }

    @Override
    public void onRefresh() {
        if(NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED){
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        if(super.listener != null)
            ((onFeedInteractionListener) super.listener).onFeedRefreshed(Constants.TAG_REFRESHED_FEED, Constants.BASE_URL + "feeds/");
    }

    public void feedRefreshed(List<Feed> feedList, String next, boolean hasFailed)
    {
        if (feedList != null && feedList.size() > 0 && !hasFailed)
        {
            if(this.mFeedList == null){
                this.mFeedList = new ArrayList<>();
            }else {
                this.mFeedList.clear();
            }

            this.mFeedList.addAll(feedList);
            this.mFeedAdapter.updateFeedList(this.mFeedList);
            super.mNextUrl = this.mFeedFragmentNextURL = next;
        }
        this.mSwipeRefreshLayout.setRefreshing(false);
    }

    public void updateList(List<Feed> feedList, String next) {
        if (feedList != null && feedList.size() > 0)
        {
            if(this.mFeedList == null) {
                this.mFeedList = new ArrayList<>();
            }
            this.mFeedList.addAll(feedList);
            this.mFeedAdapter.updateFeedList(this.mFeedList);
            super.mNextUrl = this.mFeedFragmentNextURL = next;

        }
        if(this.mFeedList == null || this.mFeedList.isEmpty()){
            this.mEmptyLayout.setVisibility(View.VISIBLE);
            if(NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
                this.mEmptTV.setText(getString(R.string.internet_not_available));
            }else{
                this.mEmptTV.setText(getString(R.string.feed_not_found));
            }
        }else{
            this.mEmptyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface onFeedInteractionListener extends BaseListener{
        // TODO: Update argument type and name
        void onFeedSelected(Feed feed);
        void onFeedRefreshed(String tag, String url);

        @Override
        void onEndReached(String next, int type);
    }
}
