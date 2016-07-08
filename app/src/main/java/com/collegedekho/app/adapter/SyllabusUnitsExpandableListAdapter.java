package com.collegedekho.app.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.Units;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class SyllabusUnitsExpandableListAdapter extends BaseExpandableListAdapter {

    ArrayList<Units> mUnitsList;
    Activity mContext;
    // Hashmap for keeping track of our checkbox check states
    // Initialize our hashmap containing our check states here
    private static HashMap<Integer, boolean[]> mChildCheckStates = new HashMap<>();

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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_syllabus_units, parent, false);
        parentViewHolder = new ParentViewHolder();
        parentViewHolder.parentUnitLabel = (TextView) convertView.findViewById(R.id.syllabus_units_name);
        parentViewHolder.subPercentage=(TextView)convertView.findViewById(R.id.sub_percentage);
        parentViewHolder.parentUnitCheckBox = (CheckBox) convertView.findViewById(R.id.syllabus_units_checkbox);
        parentViewHolder.parentUnitProgressBar = (ProgressBar) convertView.findViewById(R.id.syllabus_units_progress_bar);
        parentViewHolder.parentUnitCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SyllabusUnitsExpandableListAdapter.this.getGroup(groupPosition).setIs_done(isChecked ? Constants.BOOLEAN_TRUE : Constants.BOOLEAN_FALSE);
                ArrayList<Chapters> chaptersList = SyllabusUnitsExpandableListAdapter.this.getGroup(groupPosition).getChapters();
                for (int i = 0; i < chaptersList.size(); i++)
                    SyllabusUnitsExpandableListAdapter.this.getGroup(groupPosition).getChapters().get(i).setIs_done(isChecked ? Constants.BOOLEAN_TRUE : Constants.BOOLEAN_FALSE);

                SyllabusUnitsExpandableListAdapter.this.notifyDataSetChanged();
            }
        });
        /*holder.parentUnitCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = SyllabusUnitsExpandableListAdapter.this.getGroup(groupPosition).getIs_done() == Constants.BOOLEAN_TRUE ? Constants.BOOLEAN_FALSE : Constants.BOOLEAN_TRUE;
                SyllabusUnitsExpandableListAdapter.this.getGroup(groupPosition).setIs_done(flag);
                ArrayList<Chapters> chaptersList = SyllabusUnitsExpandableListAdapter.this.getGroup(groupPosition).getChapters();
                for (int i = 0; i < chaptersList.size(); i++)
                    SyllabusUnitsExpandableListAdapter.this.getGroup(groupPosition).getChapters().get(i).setIs_done(flag);

                SyllabusUnitsExpandableListAdapter.this.notifyDataSetChanged();
            }
        });*/

        convertView.setTag(parentViewHolder);
        Units g = getGroup(groupPosition);
        parentViewHolder.parentUnitLabel.setText(g.getUnit_name());
        parentViewHolder.subPercentage.setText(String.valueOf(g.getUnit_done_percent()));
        parentViewHolder.parentUnitProgressBar.setProgress(g.getUnit_done_percent());
        parentViewHolder.parentUnitCheckBox.setChecked(g.getIs_done() == Constants.BOOLEAN_TRUE);
        ((TextView) convertView.findViewById(R.id.indicator))
                .setText(isExpanded ? Constants.EXPANDED_INDICATOR : Constants.COLAPSED_INDICATOR);

        return convertView;
    }

    public HashMap<Integer, boolean[]> getmChildCheckStates() {
        return mChildCheckStates;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ChildViewHolder childViewHolder = new ChildViewHolder();

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_syllabus_chapters, parent, false);
        childViewHolder.childChapterLabel = (TextView) convertView.findViewById(R.id.syllabus_chapters_name);
        childViewHolder.childChapterCheckBox = (CheckBox) convertView.findViewById(R.id.syllabus_chapters_checkbox);

        final Units units=this.getGroup(groupPosition);
        final ArrayList<Chapters>  allChapters = units.getChapters();
        final Chapters chapters=allChapters.get(childPosition);

        childViewHolder.childChapterLabel.setText(chapters.getName());
        childViewHolder.childChapterCheckBox.setChecked(chapters.getIs_done() == Constants.BOOLEAN_TRUE);


        /*
		 * You have to set the onCheckChangedListener to null
		 * before restoring check states because each ic_call_vector to
		 * "setChecked" is accompanied by a ic_call_vector to the
		 * onCheckChangedListener
		*/
        childViewHolder.childChapterCheckBox.setOnCheckedChangeListener(null);

       /* if (mChildCheckStates.containsKey(mGroupPosition)) {

			 *//* if the hashmap mChildCheckStates<Integer, Boolean[]> contains
			 * the value of the parent view (group) of this child (aka, the key),
			 * then retrive the boolean array getChecked[]
			 *//*

            boolean getChecked[] = mChildCheckStates.get(mGroupPosition);

            // set the check state of this position's checkbox based on the 
            // boolean value of getChecked[position]
            childViewHolder.childChapterCheckBox.setChecked(getChecked[mChildPosition]);
        } else {

			 *//* if the hashmap mChildCheckStates<Integer, Boolean[]> does not
			 * contain the value of the parent view (group) of this child (aka, the key),
			 * (aka, the key), then initialize getChecked[] as a new boolean array
			 *  and set it's size to the total number of children associated with 
			 *  the parent group
			 *//*

            boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];

            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
            mChildCheckStates.put(mGroupPosition, getChecked);

            // set the check state of this position's checkbox based on the 
            // boolean value of getChecked[position]
            //childViewHolder.childChapterCheckBox.setChecked(false);
        }*/

        childViewHolder.childChapterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                   /* boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);*/
                    getGroup(groupPosition).getChapters().get(childPosition).setIs_done(1);

                } else {
                    getGroup(groupPosition).getChapters().get(childPosition).setIs_done(0);
                   /* boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);*/
                }
                float totalWeight=0;
                float doneWeight=0;
                for(Chapters chapter:allChapters){
                    if(chapter.getIs_done()==1){
                        doneWeight+=chapter.getWeightage();
                    }
                    totalWeight+=chapter.getWeightage();
                }
                if(totalWeight>0) {
                    float donePercentage = ((doneWeight/totalWeight) * 100);
                    getGroup(groupPosition).setUnit_done_percent(donePercentage);
                }
                notifyDataSetChanged();
            }
        });



        childViewHolder.childChapterLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childViewHolder.childChapterCheckBox.setChecked((getGroup(groupPosition).getChapters().get(childPosition).getIs_done() == 1 ? false : true));
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class ChildViewHolder {
        TextView childChapterLabel;
        CheckBox childChapterCheckBox;
    }

    static class ParentViewHolder {
        TextView parentUnitLabel;
        TextView subPercentage;
        ProgressBar parentUnitProgressBar;
        CheckBox parentUnitCheckBox;
    }
}
