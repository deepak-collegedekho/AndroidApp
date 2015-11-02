package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.MySingleton;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/10/15.
 */
public class InstituteShortListAdapter1 extends RecyclerView.Adapter {

    private ArrayList<Institute> mInstitueList;
    private Context mContext;
    private ImageLoader mImageLoader;

    public InstituteShortListAdapter1(Context context, ArrayList<Institute> instituteList) {
        this.mContext = context;
        this.mInstitueList = instituteList;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_shortlisted_institute, parent, false);

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

    static class ShortlistedInstituteHolder extends RecyclerView.ViewHolder {
        TextView siName;
        NetworkImageView siImage;

        ShortlistedInstituteHolder(View itemView) {
            super(itemView);
            this.siName = (TextView) itemView.findViewById(R.id.shortlisted_institute_name);
            this.siImage = (NetworkImageView) itemView.findViewById(R.id.shortlisted_institute_image);
            this.siImage.setDefaultImageResId(R.drawable.default_banner);
            this.siImage.setErrorImageResId(R.drawable.default_banner);
        }

        void setUp(Institute institute, ImageLoader imageLoader) {
            String banner = institute.getBanner();

            if (banner != null && !banner.isEmpty())
                this.siImage.setImageUrl(banner, imageLoader);

            this.siName.setText(institute.getShort_name());
        }
    }
}
