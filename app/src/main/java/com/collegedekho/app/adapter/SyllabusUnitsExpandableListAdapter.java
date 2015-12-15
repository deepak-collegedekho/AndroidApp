package com.collegedekho.app.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.Units;
import com.collegedekho.app.resource.Constants;


import java.util.ArrayList;

public class SyllabusUnitsExpandableListAdapter extends BaseExpandableListAdapter {

    ArrayList<Units> mUnitsList;
    Activity mContext;

    public SyllabusUnitsExpandableListAdapter(Activity context, ArrayList<Units> unitList) {
        mUnitsList = new ArrayList<>(unitList);
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return this.mUnitsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.getGroup(groupPosition).getChapters().size();
    }

    @Override
    public Units getGroup(int groupPosition) {
        return this.mUnitsList.get(groupPosition);
    }

    @Override
    public ArrayList<Chapters> getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getChapters();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_syllabus_units, parent, false);
            holder = new ParentHolder();
            holder.label = (TextView) convertView.findViewById(R.id.syllabus_units_name);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.syllabus_units_checkbox);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.syllabus_units_progress_bar);

            convertView.setTag(holder);
        }
        else {
            holder = (ParentHolder) convertView.getTag();
        }
        Units g = getGroup(groupPosition);
        holder.label.setText(g.getUnit_name());
        holder.progressBar.setProgress(g.getUnit_done_percent());
        holder.checkBox.setSelected(g.getIs_done() == Constants.BOOLEAN_TRUE ? true : false);
        ((TextView) convertView.findViewById(R.id.indicator))
                .setText(isExpanded ? Constants.EXPANDED_INDICATOR : Constants.COLAPSED_INDICATOR);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        /*convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.syllabus_chapter_recycler_view, parent, false);
        RecyclerView chapterRecyclerView = (RecyclerView) convertView.findViewById(R.id.chapter_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chapterRecyclerView.setLayoutManager(mLayoutManager);
        SyllabusChapterListAdapter scla = new SyllabusChapterListAdapter(this.mContext, this.getGroup(groupPosition).getChapters());
        chapterRecyclerView.setAdapter(scla);

        return convertView;*/

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_syllabus_chapters, parent, false);
            holder = new ViewHolder();
            holder.chapterLabel = (TextView) convertView.findViewById(R.id.syllabus_chapters_name);
            holder.chapterCheckBox = (CheckBox) convertView.findViewById(R.id.syllabus_chapters_checkbox);
            holder.chapterLabel.setText(this.getGroup(groupPosition).getChapters().get(childPosition).getName());
            holder.chapterCheckBox.setSelected(this.getGroup(groupPosition).getChapters().get(childPosition).getIs_done() == Constants.BOOLEAN_TRUE ? true : false);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
            holder.chapterCheckBox.setSelected(this.getGroup(groupPosition).getChapters().get(childPosition).getIs_done() == Constants.BOOLEAN_TRUE ? true : false);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class ViewHolder{
        TextView chapterLabel;
        CheckBox chapterCheckBox;
    }

    static class ParentHolder{
        TextView label;
        ProgressBar progressBar;
        CheckBox checkBox;
    }
}
