package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.fragment.InstituteCoursesFragment;
import com.collegedekho.app.listener.OnApplyClickedListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 09/07/15
 */
public class CourseListAdapter extends RecyclerView.Adapter {
    private static ArrayList<InstituteCourse> mCourses;
    private static Context mContext;
    private static boolean mIsAppliedResponse = false; // required  not to update  view  till response doesn't come

    public CourseListAdapter(Context activity, ArrayList<InstituteCourse> courses) {
        this.mCourses = courses;
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
        TextView cApplyBtn;
        ProgressBar cProgressBar;
        OnApplyClickedListener cListener;

        public CourseHolder(View itemView, OnApplyClickedListener listener) {
            super(itemView);
            cName = (TextView) itemView.findViewById(R.id.text_course_name);
            cDegree = (TextView) itemView.findViewById(R.id.text_course_degree);
            cStream = (TextView) itemView.findViewById(R.id.text_course_stream);
            cDuration = (TextView) itemView.findViewById(R.id.text_course_duration);
            cLevel = (TextView) itemView.findViewById(R.id.text_course_level);
            cApplyBtn = (TextView) itemView.findViewById(R.id.button_apply);
            cProgressBar = (ProgressBar) itemView.findViewById(R.id.course_apply_progressBar);
            cApplyBtn.setOnClickListener(this);
            cListener = listener;
        }

        public void setCourse(InstituteCourse c) {
            cName.setText(c.getName());
            cDegree.setText(":  " + c.getDegree_name());
            cStream.setText(":  " + c.getStream_name());
            if(c.getDuration() ==0){
                cDuration.setText(": n/a");
                cDuration.setVisibility(View.GONE);
            }else {
                cDuration.setText(":  " + c.getDuration() + " " + c.duration_type_display);
            }
            cLevel.setText(":  " + InstituteCourse.CourseLevel.values()[c.getLevel()].name().replace("_", " "));
            itemView.setTag(c);

            if(c.getIs_applied() == 1){
                cApplyBtn.setText("Applied");
                cApplyBtn.setBackgroundResource(R.drawable.bg_button_grey);
            } else {
                cApplyBtn.setText("Apply Now");
                cApplyBtn.setBackgroundResource(R.drawable.bg_button_blue);
            }
            if(!mIsAppliedResponse)
            {
                cApplyBtn.setVisibility(View.VISIBLE);
                cProgressBar.setVisibility(View.GONE);
            }
        }


        @Override
        public void onClick(View v) {
            int connectivityStatus=new NetworkUtils(v.getContext(), null).getConnectivityStatus();
            if (v.getId() == R.id.button_apply) {
                if (connectivityStatus != Constants.TYPE_NOT_CONNECTED) {
                    InstituteCourse c = (InstituteCourse) itemView.getTag();
                    if (c.getIs_applied() == 1) {
                        cListener.displayMessage(R.string.ALREADY_APPLIED);
                    } else {
                        mIsAppliedResponse = true;
                        cApplyBtn.setVisibility(View.INVISIBLE);
                        cProgressBar.setVisibility(View.VISIBLE);
                        cListener.onCourseApplied(c);
                    }
                }else {
                    this.cListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
                }
            }
        }
    }

  /*  private static int isAnyCoursePaid(){
        int   result = 0;
        if(mCourses != null && mCourses.size() > 0){
            int count = mCourses.size();
            for (int i=0 ; i < count ; i++){
                InstituteCourse course = mCourses.get(i);
                if(course == null)
                    continue;
                if(course.getIs_paid() ==1){
                    result =  1;
                    break;
                }

            }

        }
        return result;
    }*/

    public void updateAdapter() {
        this.mIsAppliedResponse = false;
        this.notifyDataSetChanged();
    }
}
