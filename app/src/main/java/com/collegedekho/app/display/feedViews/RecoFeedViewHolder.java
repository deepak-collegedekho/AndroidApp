package com.collegedekho.app.display.feedViews;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.RecoFeedInstituteListAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.FeedFragment;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by harshvardhan on 02/02/17.
 */

public class RecoFeedViewHolder extends RecyclerView.ViewHolder {

    public FeedFragment.onFeedInteractionListener mListener;
    public ArrayList<Institute> mInstitutes;
    public CardView feedCard;
    private RecyclerView mRecoFeedInstitutesRecyclerView;
    private Context mContext;
    private RecoFeedInstituteListAdapter mRecoFeedInstitutesAdapter;
    private View mItemView;
    private int mFeedPosition = -1;
    private TextView mInstituteMessageView;
    private TextView mInstituteSeeAll;
    private LinearLayout mInstituteLinearLayout;

    public RecoFeedViewHolder(View itemView, Context context) {
        super(itemView);

        this.feedCard = (CardView) itemView.findViewById(R.id.reco_feed_card);
        this.mItemView = itemView;
        this.mListener = (FeedFragment.onFeedInteractionListener) context;
        this.mContext = context;
        this.mInstitutes = new ArrayList<>();
        this.mRecoFeedInstitutesAdapter = new RecoFeedInstituteListAdapter(this.mContext, this.mInstitutes);

        this.mRecoFeedInstitutesRecyclerView = (RecyclerView) itemView.findViewById(R.id.feed_reco_list);
        this.mInstituteMessageView = (TextView) itemView.findViewById(R.id.feed_reco_message);
        this.mInstituteSeeAll = (TextView) itemView.findViewById(R.id.feed_reco_see_all);
        this.mInstituteLinearLayout = (LinearLayout) itemView.findViewById(R.id.feed_reco_body);
        this.mRecoFeedInstitutesRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, LinearLayoutManager.HORIZONTAL, false));
        this.mRecoFeedInstitutesRecyclerView.setAdapter(this.mRecoFeedInstitutesAdapter);
        this.mRecoFeedInstitutesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        this.mInstituteSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();

                map.put("feedPosition", String.valueOf(RecoFeedViewHolder.this.mFeedPosition));
                map.put("feedActionType", Constants.FEED_SEE_ALL_ACTION);

                RecoFeedViewHolder.this.mListener.onFeedAction(Constants.WIDGET_RECOMMENDED_INSTITUTES, map);
            }
        });
    }

    public void recoFeedInstitutesUpdate(String institutesResponse, int feedPosition)
    {
        this.mRecoFeedInstitutesAdapter.setFeedPosition(feedPosition);
        this.mFeedPosition = feedPosition;
        if (this.mInstitutes == null) {
            this.mInstitutes = new ArrayList<>();
        }
        if (this.mInstitutes.size() > 0)
            this.mInstitutes.clear();

        this.mInstitutes.addAll(Utils.parseInstituteList(institutesResponse));

        if (this.mInstitutes.size() == 0)
        {
            this.mInstituteLinearLayout.animate().alpha(0f);
            this.mInstituteLinearLayout.setVisibility(View.GONE);
            this.mInstituteMessageView.setVisibility(View.VISIBLE);
            this.mInstituteMessageView.animate().alpha(1.0f);
        }
        else
        {
            this.mInstituteLinearLayout.setVisibility(View.VISIBLE);
            this.mInstituteLinearLayout.animate().alpha(1.0f);
            this.mInstituteMessageView.animate().alpha(0f);
            this.mInstituteMessageView.setVisibility(View.GONE);
        }
        this.mRecoFeedInstitutesAdapter.notifyDataSetChanged();
    }
}
