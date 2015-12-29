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
import com.collegedekho.app.entities.MyAlertDateDescription;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Bashir on 16/12/15.
 */
public class UserAlertItemDescriptionAdapter extends RecyclerView.Adapter<UserAlertItemDescriptionAdapter.UserAlertItemDetailView> {
    private Context mContext;
    private ArrayList<MyAlertDateDescription> itemList;

    public UserAlertItemDescriptionAdapter(Context context, ArrayList<MyAlertDateDescription> itemList) {
        this.mContext = context;
        this.itemList = itemList;
    }

    @Override
    public UserAlertItemDetailView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.user_alert_item_detail_card_view, parent, false);
        return new UserAlertItemDetailView(itemView);
    }

    @Override
    public void onBindViewHolder(UserAlertItemDetailView holder, int position) {
        MyAlertDateDescription description = itemList.get(position);
        holder.chapterName.setText(description.getType());
//        holder.chapterName.setTextColor(Utils.getSubjectColor(description.getExam_id()));
        holder.subjectName.setText(description.getExam_name());
        holder.subjectName.setTextColor(Utils.getSubjectColor(description.getExam_id()));
        holder.subjectName.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class UserAlertItemDetailView extends RecyclerView.ViewHolder {

        public TextView chapterName;
        public CheckBox itemCheck;
        public TextView subjectName;
        public UserAlertItemDetailView(View itemView) {
            super(itemView);
            chapterName = (TextView) itemView.findViewById(R.id.syllabus_chapters_name);
            itemCheck = (CheckBox) itemView.findViewById(R.id.syllabus_chapters_checkbox);
            subjectName=(TextView)itemView.findViewById(R.id.subject_name);
            itemCheck.setVisibility(View.GONE);
        }


    }


}
