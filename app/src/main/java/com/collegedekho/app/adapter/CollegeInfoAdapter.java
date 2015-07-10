package com.collegedekho.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.collegedekho.app.R;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 10/07/15
 */
public class CollegeInfoAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<String> heads;
    ArrayList<String> details;

    public CollegeInfoAdapter(Context context, ArrayList<String> heads, ArrayList<String> details) {
        this.heads = heads;
        this.details = details;
        mContext = context;
    }

    @Override
    public int getCount() {
        return heads.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CollegeHolder collegeHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_college_info, parent, false);
            collegeHolder = new CollegeHolder(convertView);
            convertView.setTag(collegeHolder);
        } else
            collegeHolder = (CollegeHolder) convertView.getTag();
        collegeHolder.setUp(heads.get(position), details.get(position));
        return convertView;
    }

    static class CollegeHolder {

        TextView head;
        TextView detail;

        CollegeHolder(View itemView) {
            head = (TextView) itemView.findViewById(R.id.textview_cinfo_tag);
            detail = (TextView) itemView.findViewById(R.id.textview_cinfo_about);
        }

        void setUp(String headString, String detailString) {
            head.setText(headString);
            detail.setText(detailString);
        }
    }
}
