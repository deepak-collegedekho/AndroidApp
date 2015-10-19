package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 07/07/15
 */
public class InstituteListAdapter extends RecyclerView.Adapter {

    private final ImageLoader mImageLoader;
    private ArrayList<Institute> mInstitutes;
    private Context mContext;
    // Allows to remember the last item shown on screen
    public int lastPosition = -1;

    public InstituteListAdapter(Context context, ArrayList<Institute> institutes) {
        this.mInstitutes = institutes;
        this.mContext = context;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(this.mContext).inflate(R.layout.item_institute_list, parent, false);
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
        instituteHolder.instiCourses.setText(institute.getCourse_count() + " Courses Available");
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
        instituteHolder.dislikeButton.setSelected(institute.getCurrent_user_vote_type() == 1);

        instituteHolder.likeButton.setClickable(true);
        instituteHolder.dislikeButton.setClickable(true);
        instituteHolder.likeButton.setVisibility(View.VISIBLE);
        instituteHolder.dislikeButton.setVisibility(View.VISIBLE);
        instituteHolder.likeProgressBar.setVisibility(View.GONE);
        instituteHolder.dislikeProgressBar.setVisibility(View.GONE);

        this.setAnimation(instituteHolder.container, position);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mInstitutes.size();
    }

    public void updateLikeButtons(int position) {
        this.notifyItemChanged(position);
        this.notifyDataSetChanged();
    }

    class InstituteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView instiName;
        TextView instiLocation;
        TextView instiCourses;
        ImageView likeButton;
        ImageView dislikeButton;
        LinearLayout instiFaciltyList;
        ProgressBar likeProgressBar;
        ProgressBar dislikeProgressBar;
        InstituteListFragment.OnInstituteSelectedListener mListener;
        RelativeLayout container;

        public InstituteHolder(View itemView, InstituteListFragment.OnInstituteSelectedListener listener) {
            super(itemView);
            instiName = (TextView) itemView.findViewById(R.id.textview_college_name);
            instiLocation = (TextView) itemView.findViewById(R.id.textview_college_location);
            instiCourses = (TextView) itemView.findViewById(R.id.textview_college_courses);
            instiFaciltyList = (LinearLayout) itemView.findViewById(R.id.college_facility_list);
            mListener = listener;
            likeButton = (ImageView) itemView.findViewById(R.id.button_like_college);
            dislikeButton = (ImageView) itemView.findViewById(R.id.button_dislike_college);
            likeProgressBar = (ProgressBar) itemView.findViewById(R.id.like_progressBar);
            dislikeProgressBar = (ProgressBar) itemView.findViewById(R.id.dislike_progressBar);
            container = (RelativeLayout) itemView.findViewById(R.id.item_institute_container);


            likeButton.setOnClickListener(this);
            dislikeButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void addFacilities(ArrayList<Facility> facilities) {
            if (facilities.size() > 4) {
                for (int i = 0; i < 4; i++) {
                    NetworkImageView imgView = (NetworkImageView) instiFaciltyList.getChildAt(i);
                    imgView.setImageUrl(facilities.get(i).image, mImageLoader);
                    imgView.setVisibility(View.VISIBLE);
                }
                ((TextView) instiFaciltyList.getChildAt(4)).setText("+" + (facilities.size() - 4));
                instiFaciltyList.getChildAt(4).setVisibility(View.VISIBLE);
            } else {
                int i;
                for (i = 0; i < facilities.size(); i++) {
                    NetworkImageView imgView = (NetworkImageView) instiFaciltyList.getChildAt(i);
                    imgView.setImageUrl(facilities.get(i).image, mImageLoader);
                    imgView.setVisibility(View.VISIBLE);
                }
                for (int j = i; j < 5; j++) {
                    instiFaciltyList.getChildAt(j).setVisibility(View.GONE);
                }

            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_like_college:
                    if (!v.isSelected()) {
                        likeButton.setVisibility(View.INVISIBLE);
                        likeProgressBar.setVisibility(View.VISIBLE);
                        likeButton.setClickable(false);
                        dislikeButton.setClickable(false);
                        mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.LIKE_THING);

                    }
                    else
                        Toast.makeText(mContext, "Already liked..", Toast.LENGTH_SHORT).show();
                        //mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.DELETE_LIKE);
                    break;
                case R.id.button_dislike_college:
                    if (!v.isSelected()) {
                        dislikeButton.setVisibility(View.INVISIBLE);
                        dislikeProgressBar.setVisibility(View.VISIBLE);
                        likeButton.setClickable(false);
                        dislikeButton.setClickable(false);
                        mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.DISLIKE_THING);
                    }
                    else
                        Toast.makeText(mContext, "Already disliked. You really hate this one..", Toast.LENGTH_SHORT).show();
                    //mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.DELETE_LIKE);
                    break;
                default:
                    mListener.onInstituteSelected(getAdapterPosition());
                    break;
            }
        }
    }
}
