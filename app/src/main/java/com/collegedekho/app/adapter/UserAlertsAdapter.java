package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.MyAlertDate;

import java.util.ArrayList;

/**
 * Created by Bashir on 14/12/15.
 */
public class UserAlertsAdapter extends RecyclerView.Adapter<UserAlertsAdapter.UserAlertsViewHolder> {

    private Context mContext;
    private ArrayList<MyAlertDate> itemList;
    private OnUserAlertItemSelectListener mListener;
    private String[] monthNames = {" ","JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    //    private Drawable mDrawable;
    public UserAlertsAdapter(Context context, ArrayList<MyAlertDate> itemList, OnUserAlertItemSelectListener listener) {
        this.mContext = context;
        this.itemList = itemList;
        mListener = listener;
    }

    @Override
    public UserAlertsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_syllabus_chapters, parent, false);

        return new UserAlertsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserAlertsViewHolder holder, int position) {
        MyAlertDate myAlertDate = itemList.get(position);
        holder.nameTextView.setText(monthNames[myAlertDate.getMonth()] + " " + myAlertDate.getYear());
        holder.countTextView.setText(String.valueOf(myAlertDate.getCount())+" "+getEvent(myAlertDate.getCount()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class UserAlertsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView countTextView;

        public UserAlertsViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.syllabus_chapters_name);
            countTextView=(TextView)itemView.findViewById(R.id.count_view);
            countTextView.setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.syllabus_chapters_checkbox).setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemSelect(getLayoutPosition());

        }
    }

    public interface OnUserAlertItemSelectListener {
        public void onItemSelect(int position);
    }
    private String getEvent(int count){
        if(count>1){
            return "events";
        }else if(count==1){
            return "event";

        }else {
            return "";
        }
    }
}