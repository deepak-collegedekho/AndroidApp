package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.MySingleton;
import com.collegedekho.app.R;
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

    public InstituteListAdapter(Context context, ArrayList<Institute> institutes) {
        mInstitutes = institutes;
        mContext = context;
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_institute_list, parent, false);
        try {
            return new InstituteHolder(rootView, (InstituteListFragment.OnInstituteSelectedListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnInstituteSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Institute i = mInstitutes.get(position);
        InstituteHolder instituteHolder = (InstituteHolder) holder;
        instituteHolder.instiName.setText(i.getName());
        instituteHolder.instiCourses.setText(i.getCourse_count() + " Courses Available");
        String text = "";
        if (i.getCity_name() != null)
            text += i.getCity_name() + ", ";
        if (i.getState_name() != null)
            text += i.getState_name();
        if ((i.getState_name() != null || i.getCity_name() != null) && i.getEstb_date() != null)
            text += " | ";
        if (i.getEstb_date() != null)
            text += "Established in: " + i.getEstb_date().substring(0, 4);
        instituteHolder.instiLocation.setText(text);
        instituteHolder.addFacilities(i.getFacilities());
        instituteHolder.likeButton.setSelected(i.getCurrent_user_vote_type() == 0);
        instituteHolder.dislikeButton.setSelected(i.getCurrent_user_vote_type() == 1);
    }

    @Override
    public int getItemCount() {
        return mInstitutes.size();
    }

    public void updateLikeButtons(int position) {
        notifyItemChanged(position);
    }

    class InstituteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView instiName;
        TextView instiLocation;
        TextView instiCourses;
        ImageView likeButton;
        ImageView dislikeButton;
        LinearLayout instiFaciltyList;
        InstituteListFragment.OnInstituteSelectedListener mListener;

        public InstituteHolder(View itemView, InstituteListFragment.OnInstituteSelectedListener listener) {
            super(itemView);
            instiName = (TextView) itemView.findViewById(R.id.textview_college_name);
            instiLocation = (TextView) itemView.findViewById(R.id.textview_college_location);
            instiCourses = (TextView) itemView.findViewById(R.id.textview_college_courses);
            instiFaciltyList = (LinearLayout) itemView.findViewById(R.id.college_facility_list);
            mListener = listener;
            likeButton = (ImageView) itemView.findViewById(R.id.button_like_college);
            dislikeButton = (ImageView) itemView.findViewById(R.id.button_dislike_college);
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
                    if (!v.isSelected())
                        mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.LIKE_COLLEGE);
                    else
                        mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.DELETE_LIKE);
                    break;
                case R.id.button_dislike_college:
                    if (!v.isSelected())
                        mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.DISLIKE_COLLEGE);
                    else
                        mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.DELETE_LIKE);
                    break;
                default:
                    mListener.onInstituteSelected(getAdapterPosition());
                    break;
            }
        }
    }
}
