package com.collegedekho.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.network.MySingleton;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public class StreamAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Stream> streams;
    ImageLoader mImageLoader;

    public StreamAdapter(Context context, ArrayList<Stream> streamList) {
        mContext = context;
        streams = streamList;
        mImageLoader = MySingleton.getInstance(context.getApplicationContext()).getImageLoader();
    }

    @Override
    public int getCount() {
        return streams.size();
    }

    @Override
    public Stream getItem(int position) {
        return streams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_stream, parent, false);
            holder = new ViewHolder();
            holder.tag = (TextView) convertView.findViewById(R.id.stream_tag);
            holder.image = (NetworkImageView) convertView.findViewById(R.id.stream_logo);
            holder.image.setDefaultImageResId(R.drawable.ic_default_image);
            holder.image.setErrorImageResId(R.drawable.ic_default_image);

            //convertView.setOnClickListener();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Stream s = getItem(position);
        holder.tag.setText(s.getShort_name());
        if (s.getImage() != null && !s.getImage().isEmpty())
            holder.image.setImageUrl(s.getImage(), mImageLoader);
        return convertView;
    }

    static class ViewHolder {
        TextView tag;
        NetworkImageView image;
    }
}
