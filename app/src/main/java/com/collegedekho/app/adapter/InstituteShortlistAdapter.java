package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.MySingleton;

import java.util.ArrayList;

public class InstituteShortlistAdapter extends BaseAdapter {
    private final ImageLoader mImageLoader;
    private ArrayList<Institute> mInstitutes;
    private Context mContext;

    public InstituteShortlistAdapter(Context context, ArrayList<Institute> institutes) {
        this.mInstitutes = institutes;
        this.mContext = context;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
    }

    @Override
    public int getCount() {
        return this.mInstitutes.size();
    }

    @Override
    public Institute getItem(int position) {
        return this.mInstitutes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShortlistedInstituteHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_shortlisted_institute, parent, false);
            holder = new ShortlistedInstituteHolder(convertView);
            ((CardView) convertView).setPreventCornerOverlap(false);
            convertView.setTag(holder);
        } else {
            holder = (ShortlistedInstituteHolder) convertView.getTag();
        }
        holder.setUp(getItem(position), mImageLoader);
        return convertView;
    }

    static class ShortlistedInstituteHolder {
        TextView siName;
        NetworkImageView siImage;

        ShortlistedInstituteHolder(View itemView) {
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
