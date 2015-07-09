package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 07/07/15
 */
public class InstituteListAdapter extends RecyclerView.Adapter {

    private ArrayList<Institute> mInstitutes;
    private Context mContext;

    public InstituteListAdapter(Context context, ArrayList<Institute> institutes) {
        mInstitutes = institutes;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_college_list, parent, false);
        return new InstituteHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Institute i = mInstitutes.get(position);
        InstituteHolder instituteHolder = (InstituteHolder) holder;
        instituteHolder.instiName.setText(i.getName());
        instituteHolder.instiCourses.setText(i.getCourse_count() + " Courses Available");
        String text = "";
        if (i.getCity_name() != null)
            text += i.getCity_name() + ", ";
        if (i.getState_name() != null)
            text += i.getState_name();
        if ((i.getState_name() != null || i.getCity_name() != null) && i.getEstb_date() != null)
            text += " | ";
        if (i.getEstb_date() != null)
            text += "Established in: " + i.getEstb_date().substring(0, 4);
        instituteHolder.instiLocation.setText(text);
    }

    @Override
    public int getItemCount() {
        return mInstitutes.size();
    }

    static class InstituteHolder extends RecyclerView.ViewHolder {

        TextView instiName;
        TextView instiLocation;
        TextView instiCourses;

        public InstituteHolder(View itemView) {
            super(itemView);
            instiName = (TextView) itemView.findViewById(R.id.textview_college_name);
            instiLocation = (TextView) itemView.findViewById(R.id.textview_college_location);
            instiCourses = (TextView) itemView.findViewById(R.id.textview_college_courses);
        }

    }
}
