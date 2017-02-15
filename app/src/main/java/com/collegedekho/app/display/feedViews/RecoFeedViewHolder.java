package com.collegedekho.app.display.feedViews;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.RecoFeedInstituteListAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.FeedFragment;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.util.ArrayList;
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

    public RecoFeedViewHolder(View itemView, Context context) {
        super(itemView);

        this.feedCard = (CardView) itemView.findViewById(R.id.reco_feed_card);
        this.mItemView = itemView;
        this.mListener = (FeedFragment.onFeedInteractionListener) context;
        this.mContext = context;
        this.mInstitutes = new ArrayList<>();

        this.mRecoFeedInstitutesAdapter = new RecoFeedInstituteListAdapter(this.mContext, this.mInstitutes);

        this.mRecoFeedInstitutesRecyclerView = (RecyclerView) itemView.findViewById(R.id.feed_reco_list);
        this.mRecoFeedInstitutesRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, LinearLayoutManager.HORIZONTAL, true));
        this.mRecoFeedInstitutesRecyclerView.setAdapter(this.mRecoFeedInstitutesAdapter);
        this.mRecoFeedInstitutesRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void recoFeedInstitutesUpdate(String institutesResponse)
    {
        try {
            List<Institute> instituteList = JSON.std.listOfFrom(Institute.class, institutesResponse);
            if (this.mInstitutes == null)
                this.mInstitutes = new ArrayList<>();
            this.mInstitutes.addAll(instituteList);
            this.mRecoFeedInstitutesAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
