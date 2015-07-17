package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.InstituteListFragment;
import com.collegedekho.app.resource.Constants;

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
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_institute_list, parent, false);
        try {
            return new InstituteHolder(rootView, (InstituteListFragment.OnInstituteSelectedListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnInstituteSelectedListener");
        }
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

    static class InstituteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView instiName;
        TextView instiLocation;
        TextView instiCourses;
        ImageView likeButton;
        ImageView dislikeButton;
        InstituteListFragment.OnInstituteSelectedListener mListener;

        public InstituteHolder(View itemView, InstituteListFragment.OnInstituteSelectedListener listener) {
            super(itemView);
            instiName = (TextView) itemView.findViewById(R.id.textview_college_name);
            instiLocation = (TextView) itemView.findViewById(R.id.textview_college_location);
            instiCourses = (TextView) itemView.findViewById(R.id.textview_college_courses);
            mListener = listener;
            likeButton = (ImageView) itemView.findViewById(R.id.button_like_college);
            dislikeButton = (ImageView) itemView.findViewById(R.id.button_dislike_college);
            likeButton.setOnClickListener(this);
            dislikeButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_like_college:
                    mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.LIKE_COLLEGE);
                    break;
                case R.id.button_dislike_college:

                    mListener.onInstituteLikedDisliked(getAdapterPosition(), Constants.DISLIKE_COLLEGE);
                    break;
                default:
                    mListener.onInstituteSelected(getAdapterPosition());
                    break;
            }
        }
    }
}
