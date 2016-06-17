package com.collegedekho.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.fragment.UserExamFragment;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 15/6/16.
 */

public class ExamStreamAdapter extends RecyclerView.Adapter<ExamStreamAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ProfileSpinnerItem> mStreamList;
    private int defaultTextColor;
    private UserExamFragment.OnUserExamsSelectListener mListener;

    public ExamStreamAdapter(Context activity, ArrayList<ProfileSpinnerItem> streamList){
        this.mContext = activity;
        this.mStreamList = streamList;
        mListener = (UserExamFragment.OnUserExamsSelectListener)mContext;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_exam_stream, parent, false);
        return new ExamStreamAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       final ProfileSpinnerItem streamObj = mStreamList.get(position);
        if(streamObj != null){
            holder.streamName.setText(streamObj.getName());
        }
        if(streamObj.isSelected()){
            holder.streamName.setActivated(true);
            holder.streamName.setTextColor(Color.WHITE);
        }else{
            holder.streamName.setActivated(false);
            holder.streamName.setTextColor(defaultTextColor);
        }

        holder.streamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.isSelected()){
                    v.setSelected(false);
                    ((TextView)v).setTextColor(defaultTextColor);
                    v.setActivated(false);
                    streamObj.setSelected(false);
                }else{
                    v.setSelected(true);
                    ((TextView)v).setTextColor(Color.WHITE);
                    v.setActivated(true);
                    streamObj.setSelected(true);
                }
                if(mListener != null)
                    mListener.onRequestForExams(mStreamList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStreamList.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView streamName;
        public ViewHolder(View itemView) {
            super(itemView);
            streamName = (TextView)itemView.findViewById(R.id.tv_exam_stream);
            defaultTextColor = streamName.getTextColors().getDefaultColor();
        }
    }



}