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
public class UserAlertItemDescriptionAdapter extends RecyclerView.Adapter<UserAlertItemDescriptionAdapter.UserAlertItemDetailView> {
    private Context mContext;
    private ArrayList<String> itemList;

    public UserAlertItemDescriptionAdapter(Context context, ArrayList<String> itemList) {
        this.mContext = context;
        this.itemList = itemList;
    }

    @Override
    public UserAlertItemDetailView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_syllabus_chapters, parent, false);
        return new UserAlertItemDetailView(itemView);
    }

    @Override
    public void onBindViewHolder(UserAlertItemDetailView holder, int position) {
        String name = itemList.get(position);
        holder.chapterName.setText(name);
        holder.chapterName.setTextColor(Utils.getSubjectColor(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class UserAlertItemDetailView extends RecyclerView.ViewHolder {

        TextView chapterName;
        CheckBox itemCheck;

        public UserAlertItemDetailView(View itemView) {
            super(itemView);
            chapterName = (TextView) itemView.findViewById(R.id.syllabus_chapters_name);
            itemCheck = (CheckBox) itemView.findViewById(R.id.syllabus_chapters_checkbox);
            itemCheck.setVisibility(View.GONE);
        }


    }


}
