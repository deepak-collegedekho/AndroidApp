package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.ChapterDetails;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Bashir on 16/12/15.
 */
public class CalendarItemDetailsAdapter extends RecyclerView.Adapter<CalendarItemDetailsAdapter.CalendarItemDetailView> {
    private Context mContext;
    private ArrayList<ChapterDetails> itemList;

    public CalendarItemDetailsAdapter(Context context, ArrayList<ChapterDetails> itemList) {
        this.mContext = context;
        this.itemList = itemList;
    }

    @Override
    public CalendarItemDetailView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_syllabus_chapters, parent, false);
        return new CalendarItemDetailView(itemView);
    }

    @Override
    public void onBindViewHolder(CalendarItemDetailView holder, int position) {
        ChapterDetails chapterDetails = itemList.get(position);
        holder.chapterName.setText(chapterDetails.getChapter_name());
        holder.chapterName.setTextColor(Utils.getSubjectColor(Integer.valueOf(chapterDetails.getSubject_id())));
        holder.itemCheck.setChecked(chapterDetails.isSelected());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CalendarItemDetailView extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        TextView chapterName;
        CheckBox itemCheck;

        public CalendarItemDetailView(View itemView) {
            super(itemView);
            chapterName = (TextView) itemView.findViewById(R.id.syllabus_chapters_name);
            itemCheck = (CheckBox) itemView.findViewById(R.id.syllabus_chapters_checkbox);
            itemCheck.setOnCheckedChangeListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemCheck.setChecked(!itemCheck.isChecked());
            itemList.get(getLayoutPosition()).setSelected(itemCheck.isChecked());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            itemCheck.setChecked(isChecked);
            itemList.get(getLayoutPosition()).setSelected(isChecked);
        }
    }


}
