package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.databinding.ItemCourseBinding;
import com.collegedekho.app.entities.Courses;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 20/04/17.
 */

public class CourseSelectionAdapter extends RecyclerView.Adapter<CourseSelectionAdapter.CourseSelectionView> implements View.OnClickListener{
    private final Context mContext;
    private final ArrayList<Courses> mCourseList;
    private Courses mCourseSelected;

    public CourseSelectionAdapter(Context context, ArrayList<Courses> courseList) {
        this.mContext = context;
        this.mCourseList = courseList;
    }

    @Override
    public CourseSelectionView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemCourseBinding coursemBinding =   ItemCourseBinding.inflate(layoutInflater, parent, false);
        CourseSelectionView csView = new CourseSelectionView(coursemBinding);
        csView.itemView.setOnClickListener(this);
        return csView;
    }

    @Override
    public void onBindViewHolder(CourseSelectionView courseSelectionViewholder, int position) {
        Courses course = this.mCourseList.get(position);
        courseSelectionViewholder.itemView.setSelected(course.isSelected());
        courseSelectionViewholder.bind(course);
    }

    @Override
    public int getItemCount() {
        return this.mCourseList.size();
    }

    @Override
    public void onClick(View v) {
        if (this.mCourseSelected == null)
            EventBus.getDefault().post(new Event(AllEvents.ACTION_COURSE_SELECTED, null, null));

        if (this.mCourseSelected != null)
            this.mCourseSelected.setSelected(false);
        this.mCourseSelected = (Courses) v.getTag();
        this.mCourseSelected.setSelected(true);

        notifyDataSetChanged();
    }

    public void SetCourseUpdated()
    {
        this.mCourseSelected = null;
    }

    public Courses GetCourseSelected()
    {
        return this.mCourseSelected;
    }

    class CourseSelectionView extends RecyclerView.ViewHolder {
        private final ItemCourseBinding binding;

        public CourseSelectionView(ItemCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Courses course) {
            this.itemView.setTag(course);
            this.binding.setCourse(course);
            this.binding.executePendingBindings();
        }
    }
}
