/*
package com.collegedekho.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.BaseObject;

import java.util.List;

*/
/**
 * Created by sureshsaini on 24/5/16.
 *
 *//*

public class ProfileSpinnerAdapter extends BaseAdapter {

    private List<ProfileSpinnerObject> spinnerList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ProfileSpinnerAdapter(Context context, List<ProfileSpinnerObject> spinnerList){
        this.spinnerList = spinnerList;
        this.mContext = context;
        this.mLayoutInflater =  LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return spinnerList.size();
    }

    @Override
    public Object getItem(int position) {
        return spinnerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder ;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_profile_spinner_item, parent, false);
            holder.textView = (TextView)convertView.findViewById(R.id.profile_spinner_item_tv);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        ProfileSpinnerObject baseObject =spinnerList.get(position);
        holder.textView.setText(baseObject.getName());

        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
*/
