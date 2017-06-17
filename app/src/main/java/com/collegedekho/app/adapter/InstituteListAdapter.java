package com.collegedekho.app.adapter;

import android.content.Context;
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
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.CircularImageView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class InstituteListAdapter extends RecyclerView.Adapter<InstituteListAdapter.InstituteHolder> {

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
    public InstituteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(InstituteHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        loadViewHolderData(holder,position);
    }

    @Override
    public void onBindViewHolder(InstituteHolder holder, int position) {
        loadViewHolderData(holder,position);
        this.mSetAnimation(holder.instituteCard, position);
    }

    private void loadViewHolderData(InstituteHolder holder, int position){

        Institute institute = this.mInstitutes.get(position);
        if(institute == null)
            return;
        try {
            String name= new String(institute.getName().getBytes("ISO-8859-1"),"UTF-8");
            holder.instiName.setText(name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            holder.instiName.setText(institute.getName());
        }
        // set Study abroad icon
        if(institute.getIs_study_abroad() == Constants.STUDY_IN_ABROAD){
            holder.studyAbroadIcon.setVisibility(View.VISIBLE);
        }else{
            holder.studyAbroadIcon.setVisibility(View.GONE);
        }
        holder.instiCourses.setText(institute.getCourse_count() + " Courses");
        String text = "";
        if (institute.getCity_name() != null)
            text += institute.getCity_name() + ", ";
        if (institute.getState_name() != null)
            text += institute.getState_name();
        if ((institute.getState_name() != null || institute.getCity_name() != null) && institute.getEstb_date() != null)
            text += " | ";
        if (institute.getEstb_date() != null)
            text += "Established in: " + institute.getEstb_date().substring(0, 4);
        holder.instiLocation.setText(text);
        holder.addFacilities(institute.getFacilities());

        if(mViewType == Constants.VIEW_INTO_LIST)
            holder.updateInstituteLogoImage(institute.getLogo());

        holder.mShortListTV.setEnabled(true);
        holder.mShortListTV.setVisibility(View.VISIBLE);
        holder.mProgressBar.setVisibility(View.GONE);

        if (institute.getIs_shortlisted() == Constants.SHORTLISTED_NO){
            holder.mShortListTV.setText("Shortlist");
            holder.mShortListTV.setBackgroundResource(R.drawable.bg_button_blue);
        } else {
            holder.mShortListTV.setText("Shortlisted");
            holder.mShortListTV.setBackgroundResource(R.drawable.bg_button_grey);
        }

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
    public void onViewDetachedFromWindow(InstituteHolder holder) {
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


    class InstituteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView instiName;
        CardView instituteCard;
        TextView instiLocation;
        TextView instiCourses;
        LinearLayout instiFaciltyList;
        TextView mShortListTV;
        ProgressBar mProgressBar;
        CircularImageView instiLogo;
        ImageView studyAbroadIcon;
        InstituteListFragment.OnInstituteSelectedListener mListener;

        public InstituteHolder(View itemView, InstituteListFragment.OnInstituteSelectedListener listener) {
            super(itemView);

            if (mViewType == Constants.VIEW_INTO_LIST)
                instituteCard = (CardView) itemView.findViewById(R.id.institute_list_card);
            else
                instituteCard = (CardView) itemView.findViewById(R.id.institute_grid_card);

            if(mViewType == Constants.VIEW_INTO_LIST)
                instiLogo = (CircularImageView) itemView.findViewById(R.id.institute_list_logo);
            instiName = (TextView) itemView.findViewById(R.id.card_institute_name);
            instiLocation = (TextView) itemView.findViewById(R.id.card_institute_location);
            instiCourses = (TextView) itemView.findViewById(R.id.card_institute_courses);
            instiFaciltyList = (LinearLayout) itemView.findViewById(R.id.card_institute_facility_list);
            mShortListTV  = ((TextView) itemView.findViewById(R.id.card_institute_shortlist_button));
            studyAbroadIcon =(ImageView)itemView.findViewById(R.id.institute_study_abroad_icon );
            mListener = listener;
            mProgressBar = ((ProgressBar) itemView.findViewById(R.id.card_institute_shortlist_progressBar));
            (itemView.findViewById(R.id.card_institute_container)).setOnClickListener(this);
            (itemView.findViewById(R.id.card_institute_shortlist)).setOnClickListener(this);
        }

        public void updateInstituteLogoImage(String image){
            instiLogo.setDefaultImageResId(R.drawable.ic_cd);
            instiLogo.setErrorImageResId(R.drawable.ic_cd);
            if (image != null && !image.isEmpty())
                instiLogo.setImageUrl(image, mImageLoader);
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
                    imgView.setDefaultImageResId(R.drawable.ic_cd);
                    imgView.setErrorImageResId(R.drawable.ic_cd);
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
            int connectivityStatus=NetworkUtils.getConnectivityStatus(mContext);
            switch (v.getId()) {
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