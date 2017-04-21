package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.listener.InstituteCountListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 15/6/16.
 */

public class ExamStreamAdapter extends RecyclerView.Adapter<ExamStreamAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ProfileSpinnerItem> mStreamList;

    public ExamStreamAdapter(InstituteCountListener instituteCountListener, Context activity, ArrayList<ProfileSpinnerItem> streamList){
        this.mContext = activity;
        this.mStreamList = streamList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_user_education_stream, parent, false);
        return new ExamStreamAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ProfileSpinnerItem streamObj = mStreamList.get(position);
        holder.rootView.setTag(position);

        if(streamObj != null){
            holder.streamName.setText(streamObj.getName());
        }
        if(streamObj.isSelected()){
            holder.radioButton.setChecked(true);
        }else{
            holder.radioButton.setChecked(false);
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = mStreamList.size();
                for (int i = 0; i < count; i++) {
                    ProfileSpinnerItem objItem = mStreamList.get(i);
                    objItem.setSelected(false);
                }

                int  itemPosition = -1;
                try{
                    itemPosition = Integer.parseInt(v.getTag().toString());
                }
                catch(NumberFormatException e){
                    e.printStackTrace();
                }
                if(itemPosition == -1){
                    return;
                }
                ProfileSpinnerItem selectedItem = mStreamList.get(itemPosition);
                selectedItem.setSelected(true);
                notifyDataSetChanged();
                EventBus.getDefault().post(new Event(AllEvents.ACTION_CURRENT_STREAM_SELECTION, null, String.valueOf(selectedItem.getInstitutes_count())));

            }
        });
    }
    @Override
    public int getItemCount() {
        return mStreamList.size();
    }

    public void updateStreamList(ArrayList<ProfileSpinnerItem> streamList){
        this.mStreamList = streamList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView streamName;
        RadioButton radioButton;
        View rootView;
        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            streamName = (TextView)itemView.findViewById(R.id.tv_education_stream_item);
            radioButton = (RadioButton) itemView.findViewById(R.id.rb_education_stream_item);
        }
    }



}