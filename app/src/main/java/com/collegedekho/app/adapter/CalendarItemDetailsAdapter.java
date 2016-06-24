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
    private CalendarItemDetailsAdapter.OnItemStateChangeListener mListener;
    public CalendarItemDetailsAdapter(Context context, ArrayList<ChapterDetails> itemList,CalendarItemDetailsAdapter.OnItemStateChangeListener listener) {
        this.mContext = context;
        this.itemList = itemList;
        this.mListener=listener;
    }

    @Override
    public CalendarItemDetailView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.calendar_item_detail_card_view, parent, false);
        return new CalendarItemDetailView(itemView);
    }

    @Override
    public void onBindViewHolder(CalendarItemDetailView holder, int position) {
        ChapterDetails chapterDetails = itemList.get(position);
        holder.chapterName.setText(chapterDetails.getChapter_name());
//        holder.chapterName.setTextColor(Utils.getSubjectColor(Integer.valueOf(chapterDetails.getSubject_id())));
        holder.subjectName.setText(chapterDetails.getSubject_name());
        holder.subjectName.setTextColor(Utils.getSubjectColor(Integer.valueOf(chapterDetails.getSubject_id())));
        holder.itemCheck.setChecked(chapterDetails.isSelected());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CalendarItemDetailView extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        public TextView chapterName;
        public CheckBox itemCheck;
        public TextView subjectName;
        public CalendarItemDetailView(View itemView) {
            super(itemView);
            chapterName = (TextView) itemView.findViewById(R.id.syllabus_chapters_name);
            itemCheck = (CheckBox) itemView.findViewById(R.id.syllabus_chapters_checkbox);
            subjectName=(TextView)itemView.findViewById(R.id.subject_name);
            subjectName.setVisibility(View.VISIBLE);
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
            int size=itemList.size();
            for(int i=0;i<size;i++) {
                ChapterDetails chapterDetails=itemList.get(i);
                if(chapterDetails==null){
                    continue;
                }
                if(chapterDetails.isSelected()) {
                    mListener.OnStateChanged(true);
                break;
                }
                if(i==size-1){
                    mListener.OnStateChanged(false);
                }
            }
        }
    }

    public void setListener(CalendarItemDetailsAdapter.OnItemStateChangeListener listener){
        mListener=listener;
    }
    public interface OnItemStateChangeListener{
        public void OnStateChanged(boolean state);
    }

}
