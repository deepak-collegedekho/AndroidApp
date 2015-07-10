package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.listener.OnApplyClickedListener;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 09/07/15
 */
public class CourseListAdapter extends RecyclerView.Adapter {
    ArrayList<InstituteCourse> mCourses;
    private Context mContext;

    public CourseListAdapter(Context activity, ArrayList<InstituteCourse> courses) {
        mCourses = courses;
        mContext = activity;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.card_course, parent, false);
        return new CourseHolder(rootView, (OnApplyClickedListener) mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CourseHolder) holder).setCourse(mCourses.get(position));
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    static class CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cName;
        TextView cDegree;
        TextView cStream;
        TextView cDuration;
        TextView cLevel;
        OnApplyClickedListener mListener;

        public CourseHolder(View itemView, OnApplyClickedListener listener) {
            super(itemView);
            cName = (TextView) itemView.findViewById(R.id.text_course_name);
            cDegree = (TextView) itemView.findViewById(R.id.text_course_degree);
            cStream = (TextView) itemView.findViewById(R.id.text_course_stream);
            cDuration = (TextView) itemView.findViewById(R.id.text_course_duration);
            cLevel = (TextView) itemView.findViewById(R.id.text_course_level);
            itemView.findViewById(R.id.button_apply).setOnClickListener(this);
            mListener = listener;
        }

        public void setCourse(InstituteCourse c) {
            cName.setText(c.getName());
            cDegree.setText(":  " + c.getDegree_name());
            cStream.setText(":  " + c.getStream_name());
            cDuration.setText(":  " + c.getDuration() + " Years");
            cLevel.setText(":  " + InstituteCourse.CourseLevel.values()[c.getLevel()].name());
            itemView.setTag(c.id);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button_apply) {
                mListener.onCourseApplied((Long) itemView.getTag());
            }
        }
    }
}
