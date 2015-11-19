package com.collegedekho.app.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.InstituteShortlistFragment;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/10/15.
 */
public class InstituteShortListAdapter extends RecyclerView.Adapter {

    private ArrayList<Institute> mInstitueList;
    private Context mContext;
    private ImageLoader mImageLoader;
    private int mViewType;

    public InstituteShortListAdapter(Context context, ArrayList<Institute> instituteList, int viewType) {
        this.mContext = context;
        this.mInstitueList = instituteList;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
        this.mViewType = viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewID =  R.layout.card_shortlisted_institute_gridview;
        if(this.mViewType == Constants.VIEW_INTO_LIST)
            viewID = R.layout.card_shortlisted_institute_listview;
        View view = LayoutInflater.from(mContext).inflate(viewID, parent, false);

        return new ShortlistedInstituteHolder(view, (InstituteShortlistFragment.OnShortlistedInstituteSelectedListener)this.mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ShortlistedInstituteHolder listHolder=(ShortlistedInstituteHolder)holder;
        listHolder.setUp(mInstitueList.get(position), mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mInstitueList.size();
    }


    public void updateLikeButtons(int position) {
        this.notifyItemChanged(position);
        this.notifyDataSetChanged();
    }

     class ShortlistedInstituteHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
         TextView siName;
         NetworkImageView siImage;
         LinearLayout siItemFacilities;
         TextView siLocation;
         TextView siCourses;
         ImageView siLikeButton;
        // TextView siShortListTV;
         TextView siUpvoteCount;
         ProgressBar siLikeProgressBar;
        // ProgressBar siShortListProgressBar;
        InstituteShortlistFragment.OnShortlistedInstituteSelectedListener mListener;

         ShortlistedInstituteHolder(View itemView,  InstituteShortlistFragment.OnShortlistedInstituteSelectedListener  listener  ) {
             super(itemView);
             this.mListener = listener;
             this.siName        = (TextView) itemView.findViewById(R.id.card_institute_name);
             this.siImage       = (NetworkImageView) itemView.findViewById(R.id.card_institute_image);
             this.siLocation    = (TextView) itemView.findViewById(R.id.card_institute_location);
             this.siCourses     = (TextView) itemView.findViewById(R.id.card_institute_cources);
             this.siLikeButton  = (ImageView) itemView.findViewById(R.id.card_item_button_like);
             //this.siShortListTV    = (TextView) itemView.findViewById(R.id.card_institute_shortlist_button);
             this.siUpvoteCount    = (TextView) itemView.findViewById(R.id.card_item_like_count);
             this.siLikeProgressBar = (ProgressBar) itemView.findViewById(R.id.card_item_like_progressBar);
            // this.siShortListProgressBar = (ProgressBar) itemView.findViewById(R.id.card_institute_shortlist_progressBar);
             if (this.siImage != null) {
                 this.siImage.setDefaultImageResId(R.drawable.default_banner);
                 this.siImage.setErrorImageResId(R.drawable.default_banner);
             }
             this.siItemFacilities = (LinearLayout) itemView.findViewById(R.id.card_college_facility_list);

             itemView.findViewById(R.id.card_institute_like).setOnClickListener(this);


         }

         public void addFacilities(ArrayList<Facility> facilities) {
             if (facilities.size() > 4 && mViewType == Constants.VIEW_INTO_LIST) {
                 for (int i = 0; i < 4; i++) {
                     NetworkImageView imgView = (NetworkImageView) siItemFacilities.getChildAt(i);
                     imgView.setImageUrl(facilities.get(i).image, mImageLoader);
                     imgView.setVisibility(View.VISIBLE);
                 }
                 ((TextView) siItemFacilities.getChildAt(4)).setText("+" + (facilities.size() - 4));
                 this.siItemFacilities.getChildAt(4).setVisibility(View.VISIBLE);
             } else if (facilities.size() > 3 && mViewType == Constants.VIEW_INTO_GRID) {
                 int i;
                 for (i = 0; i < 3; i++) {
                     NetworkImageView imgView = (NetworkImageView) siItemFacilities.getChildAt(i);
                     imgView.setImageUrl(facilities.get(i).image, mImageLoader);
                     imgView.setVisibility(View.VISIBLE);
                 }
                 for (int j = i; j < 4; j++) {
                     siItemFacilities.getChildAt(j).setVisibility(View.GONE);
                 }
                 ((TextView) siItemFacilities.getChildAt(4)).setText("+" + (facilities.size() - 3));
                 this.siItemFacilities.getChildAt(4).setVisibility(View.VISIBLE);
             } else {
                 int i;
                 for (i = 0; i < facilities.size(); i++) {
                     NetworkImageView imgView = (NetworkImageView) siItemFacilities.getChildAt(i);
                     imgView.setImageUrl(facilities.get(i).image, mImageLoader);
                     imgView.setVisibility(View.VISIBLE);
                 }
                 for (int j = i; j < 5; j++) {
                     siItemFacilities.getChildAt(j).setVisibility(View.GONE);
                 }
             }
         }

         void setUp(Institute institute, ImageLoader imageLoader) {
             String banner = institute.getBanner();

             if (banner != null && !banner.isEmpty() && this.siImage != null)
                 this.siImage.setImageUrl(banner, imageLoader);

             if (mViewType == Constants.VIEW_INTO_GRID)
                 this.siName.setText(institute.getShort_name());
             else
                 this.siName.setText(institute.getName());


             this.siCourses.setText(institute.getCourse_count() + " Courses Available");
             String text = "";
             if (institute.getCity_name() != null)
                 text += institute.getCity_name() + ", ";
             if (institute.getState_name() != null)
                 text += institute.getState_name();
             if ((institute.getState_name() != null || institute.getCity_name() != null) && institute.getEstb_date() != null)
                 text += " | ";
             if (institute.getEstb_date() != null)
                 text += "Established in: " + institute.getEstb_date().substring(0, 4);
             this.siLocation.setText(text);

             siUpvoteCount.setText(String.valueOf(institute.getUpvotes()));
             addFacilities(institute.getFacilities());

             siLikeButton.setSelected(institute.getCurrent_user_vote_type() == 0);
             siLikeButton.setClickable(true);

             if (siLikeButton.isSelected()) {

                 siLikeButton.setVisibility(View.VISIBLE);
                 siLikeProgressBar.setVisibility(View.GONE);
                 siLikeButton.setColorFilter(ContextCompat.getColor(mContext, R.color.like_green_selected));

             }else {
                 siLikeButton.setColorFilter(ContextCompat.getColor(mContext, R.color.subheading_color));
             }

         }

         @Override
         public void onClick(View v) {
             switch (v.getId()) {
                 case R.id.card_item_button_like:
                         siLikeButton.setVisibility(View.GONE);
                         siLikeProgressBar.setVisibility(View.VISIBLE);
                         siLikeButton.setClickable(false);
                        mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.LIKE_THING);

                     break;
                 case R.id.layout_item_expand:
                     // this.mListener.onInstituteSelected(getAdapterPosition());
                     break;
                /* case R.id.card_institute_shortlist:
                         siShortListTV.setVisibility(View.GONE);
                         siLikeProgressBar.setVisibility(View.VISIBLE);

                   //  this.mListener.onInstituteShortlisted(getAdapterPosition());
                     break;*/
                 default:
                     //mListener.onInstituteSelected(getAdapterPosition());
                     break;
             }
         }
     }
}
