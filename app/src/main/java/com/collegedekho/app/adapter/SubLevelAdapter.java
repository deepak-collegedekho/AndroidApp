package com.collegedekho.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.SubLevel;
import com.collegedekho.app.fragment.ProfileBuildingFragment;
import com.collegedekho.app.listener.ProfileFragmentListener;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 12/10/16.
 */

public class SubLevelAdapter extends BaseAdapter {

    private ArrayList<SubLevel> mSublevelList;
    private Context mContext;
    private LayoutInflater mInflater;
    private Animation mAnimFromRight;
    private ProfileFragmentListener mListener;

   public SubLevelAdapter(Context context, ProfileFragmentListener listener, ArrayList<SubLevel> sublevelList){

       this. mContext = context;
       this.mSublevelList = sublevelList;
       this.mInflater = LayoutInflater.from(mContext);
       this.mAnimFromRight = AnimationUtils.loadAnimation(this.mContext, R.anim.enter_from_right);
       this.mListener =  listener;
    }
    @Override
    public int getCount() {
        return mSublevelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSublevelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mSublevelList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.item_sub_level, parent,false);
        convertView.startAnimation(mAnimFromRight);
        SubLevel subLevel = (SubLevel) getItem(position);
        final  RadioButton radioButton = (RadioButton)convertView.findViewById(R.id.sub_level_radio_button);
        TextView nameTV = (TextView)convertView.findViewById(R.id.sub_level_name);
        nameTV.setText(subLevel.getName());
        nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    radioButton.setChecked(true);
            }
        });
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ((ProfileBuildingFragment) mListener).onSubLevelSelected(position);
                }
            }
        });

       return convertView;
    }
}
