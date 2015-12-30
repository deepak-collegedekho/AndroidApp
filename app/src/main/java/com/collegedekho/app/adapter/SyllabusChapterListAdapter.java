package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.fragment.SyllabusSubjectsListFragment;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

public class SyllabusChapterListAdapter extends RecyclerView.Adapter {

    private ArrayList<Chapters> mChapters;
    private Context mContext;
    // Allows to remember the last item shown on screen
    public int lastPosition = -1;
    private int mViewType;

    public SyllabusChapterListAdapter(Context context, ArrayList<Chapters> chapters) {
        this.mChapters = chapters;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.card_syllabus_chapters, parent, false);
        try {
            return new ChapterHolder(rootView);
        } catch (ClassCastException e) {
            throw new ClassCastException(this.mContext.toString()
                    + " must implement SyllabusChapterListAdapter");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chapters chapters = this.mChapters.get(position);
        ChapterHolder chapterHolder = (ChapterHolder) holder;

        chapterHolder.mChapterName.setText(chapters.getName());
        chapterHolder.mcheckBox.setSelected(chapters.getIs_done() == Constants.BOOLEAN_TRUE ? true : false);
    }

    @Override
    public int getItemCount() {
        return mChapters.size();
    }

    class ChapterHolder extends RecyclerView.ViewHolder{
        TextView mChapterName;
        CheckBox mcheckBox;

        SyllabusSubjectsListFragment.OnSubjectSelectedListener mListener;

        public ChapterHolder(View itemView) {
            super(itemView);
            mChapterName = (TextView) itemView.findViewById(R.id.syllabus_chapters_name);
            mcheckBox = (CheckBox) itemView.findViewById(R.id.syllabus_chapters_checkbox);
        }
    }
}