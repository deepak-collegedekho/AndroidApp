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
import com.collegedekho.app.MySingleton;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Widget;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 10/07/15
 */
public class WidgetAdapter extends BaseAdapter {

    ArrayList<Widget> mWidget;
    Context mContext;
    ImageLoader mImageLoader;

    public WidgetAdapter(Context context, ArrayList<Widget> widgets) {
        mWidget = widgets;
        mContext = context;
        mImageLoader = MySingleton.getInstance(mContext).getImageLoader();
    }

    @Override
    public int getCount() {
        return mWidget.size();
    }

    @Override
    public Widget getItem(int position) {
        return mWidget.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WidgetHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_home_widget, parent, false);
            holder = new WidgetHolder(convertView);
            ((CardView) convertView).setPreventCornerOverlap(false);
            convertView.setTag(holder);
        } else {
            holder = (WidgetHolder) convertView.getTag();
        }
        holder.setUp(getItem(position), mImageLoader);
        return convertView;
    }

    static class WidgetHolder {
        TextView wName;
        NetworkImageView wImage;

        WidgetHolder(View itemView) {
            wName = (TextView) itemView.findViewById(R.id.textview_widget_name);
            wImage = (NetworkImageView) itemView.findViewById(R.id.textview_widget_image);
            wImage.setDefaultImageResId(R.drawable.ic_logo_grey);
            wImage.setErrorImageResId(R.drawable.ic_logo_grey);
        }

        void setUp(Widget w, ImageLoader imageLoader) {
            wName.setText(w.getTitle());
            if (w.getImage() != null && !w.getImage().isEmpty())
                wImage.setImageUrl(w.getImage(), imageLoader);
        }
    }
}
