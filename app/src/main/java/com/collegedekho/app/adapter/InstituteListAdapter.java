package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;

import java.util.ArrayList;

public class InstituteListAdapter extends RecyclerView.Adapter {

    private final ImageLoader mImageLoader;
    private ArrayList<Institute> mInstitutes;
    private Context mContext;
    // Allows to remember the last item shown on screen
    public int lastPosition = -1;
    private int mViewType;

    public InstituteListAdapter(Context context, ArrayList<Institute> institutes, int viewType) {
        this.mInstitutes = institutes;
        this.mContext = context;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
        this.mViewType = viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewID =  R.layout.card_institute_list_grid_view;
        if(this.mViewType == Constants.VIEW_INTO_LIST)
            viewID = R.layout.card_institute_list_view;
        View rootView = LayoutInflater.from(mContext).inflate(viewID, parent, false);
        try {
            return new InstituteHolder(rootView, (InstituteListFragment.OnInstituteSelectedListener) this.mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(this.mContext.toString()
                    + " must implement OnInstituteSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Institute institute = this.mInstitutes.get(position);
        InstituteHolder instituteHolder = (InstituteHolder) holder;
        instituteHolder.instiName.setText(institute.getName());
        instituteHolder.instiCourses.setText(institute.getCourse_count() + " Courses");
        String text = "";
        if (institute.getCity_name() != null)
            text += institute.getCity_name() + ", ";
        if (institute.getState_name() != null)
            text += institute.getState_name();
        if ((institute.getState_name() != null || institute.getCity_name() != null) && institute.getEstb_date() != null)
            text += " | ";
        if (institute.getEstb_date() != null)
            text += "Established in: " + institute.getEstb_date().substring(0, 4);
        instituteHolder.instiLocation.setText(text);
        instituteHolder.addFacilities(institute.getFacilities());
        instituteHolder.likeButton.setSelected(institute.getCurrent_user_vote_type() == 0);
        instituteHolder.upvoteCount.setText(String.valueOf(institute.getUpvotes()));
        //instituteHolder.dislikeButton.setSelected(institute.getCurrent_user_vote_type() == 1);

        instituteHolder.likeButton.setClickable(true);
        //instituteHolder.dislikeButton.setClickable(true);
        instituteHolder.likeButton.setVisibility(View.VISIBLE);
        instituteHolder.upvoteCount.setVisibility(View.VISIBLE);
        //instituteHolder.dislikeButton.setVisibility(View.VISIBLE);
        instituteHolder.likeProgressBar.setVisibility(View.GONE);
        //instituteHolder.dislikeProgressBar.setVisibility(View.GONE);
        instituteHolder.mShortListTV.setEnabled(true);
        instituteHolder.mShortListTV.setVisibility(View.VISIBLE);
        instituteHolder.mProgressBar.setVisibility(View.GONE);

        if (institute.getIs_shortlisted() == Constants.SHORTLISTED_NO)
        {
            instituteHolder.mShortListTV.setText("Shortlist");
            instituteHolder.mShortListTV.setBackgroundResource(R.drawable.bg_button_blue);
        }
        else
        {
            instituteHolder.mShortListTV.setText("Shortlisted");
            instituteHolder.mShortListTV.setBackgroundResource(R.drawable.bg_button_grey);
        }

        if (instituteHolder.likeButton.isSelected())
            instituteHolder.likeButton.setColorFilter(ContextCompat.getColor(this.mContext, R.color.like_green_selected));
        else
            instituteHolder.likeButton.setColorFilter(ContextCompat.getColor(this.mContext, R.color.subheading_color));

        this.mSetAnimation(instituteHolder.instituteCard, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void mSetAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            if (this.mViewType == Constants.VIEW_INTO_GRID)
            {
                if (position % 2 == 0)
                {
                    Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.enter_from_left);
                    viewToAnimate.startAnimation(animation);
                }
                else
                {
                    Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.enter_from_right);
                    viewToAnimate.startAnimation(animation);
                }
            }
            else
            {
                Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.enter_from_left);
                viewToAnimate.startAnimation(animation);
            }
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return mInstitutes.size();
    }

    public void updateLikeButtons(int position) {
        this.notifyItemChanged(position);
        this.notifyDataSetChanged();
    }

    public void updateShortlistStatus(int position , boolean isFilterAllowed,int listType)
    {
        /*this.mShortListTV.setEnabled(true);
        this.mShortListTV.setVisibility(View.VISIBLE);
        this.mProgressBar.setVisibility(View.GONE);
        if (mInstitute.getIs_shortlisted() == Constants.SHORTLISTED_NO) {
            this.mShortListTV.setText("Shortlist " + mInstitute.getShort_name());
            this.mShortListTV.setBackgroundResource(R.drawable.bg_button_blue);
        } else {
            this.mShortListTV.setText("Delete " + mInstitute.getShort_name() + " from your shortlist");
            this.mShortListTV.setBackgroundResource(R.drawable.bg_button_grey);
        }*/
        if(!isFilterAllowed &&  !Constants.IS_RECOMENDED_COLLEGE && listType!=Constants.INSTITUTE_SEARCH_TYPE)
            if(mInstitutes != null && mInstitutes.size() > position)
                mInstitutes.remove(position);
        else
            this.notifyItemChanged(position);

        this.notifyDataSetChanged();
    }

    class InstituteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView instiName;
        CardView instituteCard;
        TextView instiLocation;
        TextView instiCourses;
        ImageView likeButton;
        //ImageView dislikeButton;
        LinearLayout instiFaciltyList;
        ProgressBar likeProgressBar;
        TextView mShortListTV;
        TextView upvoteCount;
        ProgressBar mProgressBar;
        //ProgressBar dislikeProgressBar;
        InstituteListFragment.OnInstituteSelectedListener mListener;

        public InstituteHolder(View itemView, InstituteListFragment.OnInstituteSelectedListener listener) {
            super(itemView);

            if (mViewType == Constants.VIEW_INTO_LIST)
                instituteCard = (CardView) itemView.findViewById(R.id.institute_list_card);
            else
                instituteCard = (CardView) itemView.findViewById(R.id.institute_grid_card);

            instiName = (TextView) itemView.findViewById(R.id.card_institute_name);
            instiLocation = (TextView) itemView.findViewById(R.id.card_institute_location);
            instiCourses = (TextView) itemView.findViewById(R.id.card_institute_courses);
            instiFaciltyList = (LinearLayout) itemView.findViewById(R.id.card_institute_facility_list);
            mShortListTV  = ((TextView) itemView.findViewById(R.id.card_institute_shortlist_button));
            upvoteCount  = ((TextView) itemView.findViewById(R.id.card_institute_like_count));
            mListener = listener;
            likeButton = (ImageView) itemView.findViewById(R.id.card_institute_button_like);
            mProgressBar = ((ProgressBar) itemView.findViewById(R.id.card_institute_shortlist_progressBar));
            //dislikeButton = (ImageView) itemView.findViewById(R.id.button_dislike_college);
            likeProgressBar = (ProgressBar) itemView.findViewById(R.id.card_institute_like_progressBar);
            //dislikeProgressBar = (ProgressBar) itemView.findViewById(R.id.dislike_progressBar);

            likeButton.setOnClickListener(this);
            (itemView.findViewById(R.id.card_institute_container)).setOnClickListener(this);
            (itemView.findViewById(R.id.card_institute_shortlist)).setOnClickListener(this);
            //dislikeButton.setOnClickListener(this);
            //itemView.setOnClickListener(this);
        }

        public void addFacilities(ArrayList<Facility> facilities) {
            if (facilities.size() > 4 && mViewType ==  Constants.VIEW_INTO_LIST) {
                for (int i = 0; i < 4; i++) {
                    NetworkImageView imgView = (NetworkImageView) instiFaciltyList.getChildAt(i);
                    imgView.setImageUrl(facilities.get(i).image_new, mImageLoader);
                    imgView.setVisibility(View.VISIBLE);
                }
                ((TextView) instiFaciltyList.getChildAt(4)).setText("+" + (facilities.size() - 4));
                instiFaciltyList.getChildAt(4).setVisibility(View.VISIBLE);
            }
            else if (facilities.size() > 2 && mViewType == Constants.VIEW_INTO_GRID) {
                int i;
                for (i = 0; i < 2; i++) {
                    NetworkImageView imgView = (NetworkImageView) instiFaciltyList.getChildAt(i);
                    imgView.setImageUrl(facilities.get(i).image_new, mImageLoader);
                    imgView.setVisibility(View.VISIBLE);
                }
                for (int j = i; j < 4; j++) {
                    instiFaciltyList.getChildAt(j).setVisibility(View.GONE);
                }
                ((TextView) instiFaciltyList.getChildAt(4)).setText("+" + (facilities.size() - 2));
                this.instiFaciltyList.getChildAt(4).setVisibility(View.VISIBLE);
            } else {
                int i;
                for (i = 0; i < facilities.size(); i++) {
                    NetworkImageView imgView = (NetworkImageView) instiFaciltyList.getChildAt(i);
                    imgView.setImageUrl(facilities.get(i).image_new, mImageLoader);
                    imgView.setVisibility(View.VISIBLE);
                }
                for (int j = i; j < 5; j++) {
                    instiFaciltyList.getChildAt(j).setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onClick(View v) {
            int connectivityStatus=new NetworkUtils(v.getContext(), null).getConnectivityStatus();
            switch (v.getId()) {
                case R.id.card_institute_button_like:
                    if (connectivityStatus != Constants.TYPE_NOT_CONNECTED){
                        if (!v.isSelected()) {
                            likeButton.setVisibility(View.GONE);
                            //upvoteCount.setVisibility(View.GONE);
                            likeProgressBar.setVisibility(View.VISIBLE);
                            likeButton.setClickable(false);
                            //dislikeButton.setClickable(false);
                            mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.LIKE_THING);
                        } else {
                            //Toast.makeText(mContext, "Already liked..", Toast.LENGTH_SHORT).show();
                            likeButton.setVisibility(View.GONE);
                            likeProgressBar.setVisibility(View.VISIBLE);
                            likeButton.setClickable(false);
                            //dislikeButton.setClickable(false);
                            mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.DISLIKE_THING);
                        }

                    }else {
                        this.mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
                    }
                    break;
//                case R.id.button_dislike_college:
//                    if (connectivityStatus != Constants.TYPE_NOT_CONNECTED) {
//                        if (!v.isSelected()) {
//                            //dislikeButton.setVisibility(View.INVISIBLE);
//                            //dislikeProgressBar.setVisibility(View.VISIBLE);
//                            likeButton.setClickable(false);
//                            //dislikeButton.setClickable(false);
//                            mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.DISLIKE_THING);
//                        } else
//                            mListener.displayMessage(R.string.ALREADY_DISLIKED);
//                    }else {
//                        this.mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
//                    }
//                    break;
                case R.id.card_institute_container:
                    this.mListener.onInstituteSelected(getAdapterPosition());
                    break;
                case R.id.card_institute_shortlist:
                    if (connectivityStatus != Constants.TYPE_NOT_CONNECTED) {
                        mShortListTV.setEnabled(false);
                        mShortListTV.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);
                        this.mListener.onInstituteShortlisted(getAdapterPosition());
                    }else {
                        this.mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
                    }
                    break;
                default:
                    mListener.onInstituteSelected(getAdapterPosition());
                    break;
            }
        }
    }
}