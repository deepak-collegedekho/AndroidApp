package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

    RecyclerView mFeedRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout mEmptyLayout;
    TextView mEmptyTV;

    private ArrayList<Feed> mFeedList = new ArrayList<>();
    private String mFeedFragmentNextURL;
    private FeedAdapter mFeedAdapter;
    private static  FeedFragment sInstance;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance() {
        synchronized (FeedFragment.class) {
            if (sInstance == null) {
                sInstance = new FeedFragment();
            }
            return  sInstance;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.listType = Constants.FEED_TYPE;
        if (getArguments() != null) {
             ArrayList<Feed> feedList = getArguments().getParcelableArrayList(FEED_LIST);
            if(feedList  != null) {
                this.mFeedList.addAll(feedList);
            }
            this.mFeedFragmentNextURL = super.mNextUrl = getArguments().getString(FEED_FRAGMENT_NEXT_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mFeedRecyclerView  = (RecyclerView) view.findViewById(R.id.feed_list);
        this.mSwipeRefreshLayout  = (SwipeRefreshLayout) view.findViewById(R.id.feed_swipe_refresh_container);
        this.mEmptyLayout  = (LinearLayout)view.findViewById(R.id.feed_empty_layout);
        this.mEmptyTV  = (TextView)view.findViewById(R.id.feed_empty);
        view.findViewById(R.id.feed_empty).setOnClickListener(this);

        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        if(this.mFeedAdapter == null) {
            this.mFeedAdapter = new FeedAdapter(this.getContext(), this.mFeedList);
        }else{
            this.mFeedAdapter.notifyDataSetChanged();
        }

        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };

        super.layoutManager = new LinearLayoutManager(this.getContext());
        this.mFeedRecyclerView.setLayoutManager(super.layoutManager);
        this.mFeedRecyclerView.setAdapter(this.mFeedAdapter);
        this.mFeedRecyclerView.setHasFixedSize(true);
        this.mFeedRecyclerView.setItemAnimator(animator);
        this.mFeedRecyclerView.addOnScrollListener(super.scrollListener);
        super.mNextUrl = this.mFeedFragmentNextURL;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.mFeedList == null || this.mFeedList.size() == 0) {
            if(NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED){
                mEmptyLayout.setVisibility(View.VISIBLE);
                return;
            }
            if(getActivity() != null){
                ((MainActivity) this.getActivity()).mGetFeed(Constants.TAG_LOAD_FEED, Constants.BASE_URL + "feeds/");
                //((MainActivity) this.getActivity()).mGetFeed(Constants.TAG_LOAD_FEED, "https://api.myjson.com/bins/fpkqp");
                //((MainActivity) this.getActivity()).mGetFeed(Constants.TAG_LOAD_FEED, "https://api.myjson.com/bins/rzzqx");
            }
        }
        if(mEmptyLayout != null)
            mEmptyLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.feed_empty_refresh:
                if(NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED){
                    return;
                }
                if(getActivity() != null){
                    ((MainActivity) this.getActivity()).mGetFeed(Constants.TAG_LOAD_FEED, Constants.BASE_URL + "feeds/");
                    //((MainActivity) this.getActivity()).mGetFeed(Constants.TAG_LOAD_FEED, "https://api.myjson.com/bins/fpkqp");
                    //((MainActivity) this.getActivity()).mGetFeed(Constants.TAG_LOAD_FEED, "https://api.myjson.com/bins/rzzqx");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        if(NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED){
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        if(super.listener != null)
            ((onFeedInteractionListener) super.listener).onFeedRefreshed();
            //((onFeedInteractionListener) super.listener).onFeedRefreshed(Constants.TAG_REFRESHED_FEED, "https://api.myjson.com/bins/12ny79");
            //((onFeedInteractionListener) super.listener).onFeedRefreshed(Constants.TAG_REFRESHED_FEED, "https://api.myjson.com/bins/rzzqx");
    }

    public void feedRefreshed(List<Feed> feedList, String next, boolean hasFailed)
    {

        if (feedList != null && feedList.size() > 0 && !hasFailed)
        {
            this.mFeedList.clear();

            this.mFeedList.addAll(feedList);
            this.mFeedAdapter.notifyDataSetChanged();
            super.mNextUrl = this.mFeedFragmentNextURL = next;
        }

        this.mSwipeRefreshLayout.setRefreshing(false);

        if(this.mFeedList == null || this.mFeedList.isEmpty()){
            this.mEmptyLayout.setVisibility(View.VISIBLE);
            if(NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
                this.mEmptyTV.setText(getString(R.string.internet_not_available));
            }else{
                this.mEmptyTV.setText(getString(R.string.feed_not_found));
            }
        }else{
            this.mEmptyLayout.setVisibility(View.GONE);
        }
    }

    public void updateList(List<Feed> feedList, String next) {
        if (feedList != null && feedList.size() > 0)
        {
            this.mFeedList.addAll(feedList);
            this.mFeedAdapter.notifyDataSetChanged();
            super.mNextUrl = this.mFeedFragmentNextURL = next;

        }
        if(this.mFeedList == null || this.mFeedList.isEmpty()){
            this.mEmptyLayout.setVisibility(View.VISIBLE);
            if(NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
                this.mEmptyTV.setText(getString(R.string.internet_not_available));
            }else{
                this.mEmptyTV.setText(getString(R.string.feed_not_found));
            }
        }else{
            this.mEmptyLayout.setVisibility(View.GONE);
        }
    }

    public void removeProfileCompletionLayout() {

        if(mFeedList  != null &&  mFeedList.size() >1)
        {
            mFeedList.remove(0);
            this.mFeedAdapter.notifyDataSetChanged();
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

    public void feedAction(String type, HashMap<String, String> dataMap) {
        if (this.mFeedList != null && dataMap != null)
        {
            if (dataMap.containsKey("feedPosition"))
            {
                int institutePosition = 0;
                final int feedPosition;
                feedPosition = Integer.valueOf(dataMap.get("feedPosition"));
                if (dataMap.containsKey("position"))
                    institutePosition = Integer.valueOf(dataMap.get("position"));

                Feed feed = this.mFeedList.get(feedPosition);

                if (feed.getScreen().equals(Constants.RECOMMENDED_INSTITUTE_FEED_LIST))
                {
                    ArrayList<Institute> instituteList = Utils.parseInstituteList(feed.getResult());
                    for (Institute institute : instituteList)
                    {
                        if (institute.getId() == Integer.valueOf(dataMap.get("id")))
                        {
                            instituteList.remove(institute);

                            try {
                                if (instituteList.size() == 0)
                                {
                                    //remove the institute from the list
                                    feed.setResult(JSON.std.asString(instituteList));
                                    this.mFeedAdapter.notifyItemChanged(feedPosition);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //remove the component after a message if there is no institute left in the reco list
                                            FeedFragment.this.mFeedList.remove(feedPosition);
                                            FeedFragment.this.mFeedAdapter.notifyItemRemoved(feedPosition);

                                        }
                                    }, 5000);
                                }
                                else
                                {
                                    //remove the institute from the list
                                    feed.setResult(JSON.std.asString(instituteList));
                                    this.mFeedAdapter.notifyItemChanged(feedPosition);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }
        }
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
        void onFeedRefreshed();
        void onFeedAction(String type, HashMap<String, String> dataMap);

        @Override
        void onEndReached(String next, int type);
    }
}
