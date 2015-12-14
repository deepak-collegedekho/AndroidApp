package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Subjects;
import com.collegedekho.app.fragment.SyllabusSubjectsListFragment;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

public class SyllabusSubjectListAdapter extends RecyclerView.Adapter {

    private ArrayList<Subjects> mSubjects;
    private Context mContext;
    // Allows to remember the last item shown on screen
    public int lastPosition = -1;
    private int mViewType;

    public SyllabusSubjectListAdapter(Context context, ArrayList<Subjects> subjects) {
        this.mSubjects = subjects;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.card_syllabus_subject, parent, false);
        rootView.setSelected(true);
        CardView cardView = (CardView) rootView.findViewById(R.id.card_syllabus_subject);
        cardView.setSelected(true);
        try {
            return new SubjectHolder(rootView, (SyllabusSubjectsListFragment.OnSubjectSelectedListener) this.mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(this.mContext.toString()
                    + " must implement SyllabusSubjectListAdapter");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Subjects subjects = this.mSubjects.get(position);
        SubjectHolder subjectHolder = (SubjectHolder) holder;
        subjectHolder.mSubjectName.setText(subjects.getSubject_name());
        subjectHolder.mProgressBar.setProgress(subjects.getSubject_done_percent());

        if (subjects.getIs_done() == Constants.BOOLEAN_TRUE)
            subjectHolder.mcheckBox.setSelected(true);
        else
            subjectHolder.mcheckBox.setSelected(false);
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

    public void updateLikeButtons(int position) {
        this.notifyItemChanged(position);
        this.notifyDataSetChanged();
    }

    class SubjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mSubjectName;
        ProgressBar mProgressBar;
        CheckBox mcheckBox;

        SyllabusSubjectsListFragment.OnSubjectSelectedListener mListener;

        public SubjectHolder(View itemView, SyllabusSubjectsListFragment.OnSubjectSelectedListener listener) {
            super(itemView);
            mSubjectName = (TextView) itemView.findViewById(R.id.syllabus_subject_name);
            mProgressBar = ((ProgressBar) itemView.findViewById(R.id.syllabus_subject_progress_bar));
            mcheckBox = (CheckBox) itemView.findViewById(R.id.syllabus_subject_checkbox);
            mListener = listener;

            mSubjectName.setOnClickListener(this);
            mProgressBar.setOnClickListener(this);
            mcheckBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.syllabus_subject_checkbox:
                    mListener.onSubjectCheckboxSelected(SyllabusSubjectListAdapter.this.mSubjects.get(getAdapterPosition()));
                    break;
                case R.id.syllabus_subject_progress_bar:
                case R.id.syllabus_subject_name:
                    mListener.onSubjectCheckboxSelected(SyllabusSubjectListAdapter.this.mSubjects.get(getAdapterPosition()));
                    break;
                default:
                    break;
            }
        }
    }
}