package com.collegedekho.app.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
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

        return new ShortlistedInstituteHolder(view);
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

     class ShortlistedInstituteHolder extends RecyclerView.ViewHolder {
        TextView siName;
        NetworkImageView siImage;
        LinearLayout siItemFacilities ;

        ShortlistedInstituteHolder(View itemView) {
            super(itemView);
            this.siName = (TextView) itemView.findViewById(R.id.shortlisted_institute_name);
            this.siImage = (NetworkImageView) itemView.findViewById(R.id.shortlisted_institute_image);
            if(this.siImage != null) {
                this.siImage.setDefaultImageResId(R.drawable.default_banner);
                this.siImage.setErrorImageResId(R.drawable.default_banner);
            }
            this.siItemFacilities= (LinearLayout)itemView.findViewById(R.id.college_facility_list);
        }

        public void addFacilities(ArrayList<Facility> facilities) {
            if (facilities.size() > 4) {
                for (int i = 0; i < 4; i++) {
                    NetworkImageView imgView = (NetworkImageView) this.siItemFacilities.getChildAt(i);
                    imgView.setImageUrl(facilities.get(i).image, mImageLoader);
                    imgView.setVisibility(View.VISIBLE);
                }
            } else {
                int i;
                for (i = 0; i < facilities.size(); i++) {
                    NetworkImageView imgView = (NetworkImageView) siItemFacilities.getChildAt(i);
                    imgView.setImageUrl(facilities.get(i).image, mImageLoader);
                    imgView.setVisibility(View.VISIBLE);
                }
                for (int j = i; j < 4; j++) {
                    this.siItemFacilities.getChildAt(j).setVisibility(View.GONE);
                }

            }
        }

        void setUp(Institute institute, ImageLoader imageLoader) {
            String banner = institute.getBanner();

            if (banner != null && !banner.isEmpty() && this. siImage != null)
                this.siImage.setImageUrl(banner, imageLoader);

            if(mViewType == Constants.VIEW_INTO_GRID)
                this.siName.setText(institute.getShort_name());
            else
              this.siName.setText(institute.getName());

            addFacilities(institute.getFacilities());

        }
    }
}
