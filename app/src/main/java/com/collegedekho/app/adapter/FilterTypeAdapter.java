package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.fragment.FilterFragment;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 07/07/15
 */
public class FilterTypeAdapter extends RecyclerView.Adapter {

    private static final int SELECTED_VIEW = 0;
    private static final int NORMAL_VIEW = 1;
    private final ArrayList<Folder> mFolderList;
    Context mContext;
    int currentSelection;

    public FilterTypeAdapter(Context context, ArrayList<Folder> folderList) {
        mContext = context;
        mFolderList = folderList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_filter_type, parent, false);
        FilterTypeHolder holder = new FilterTypeHolder(rootView);
        if (viewType == SELECTED_VIEW)
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.bg_filter_item));
        else
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.bg_filter_item_us));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FilterTypeHolder) holder).title.setText(mFolderList.get(position).getLabel());
    }

    @Override
    public int getItemCount() {
        return mFolderList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == currentSelection)
            return SELECTED_VIEW;
        return NORMAL_VIEW;
    }

    class FilterTypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        public FilterTypeHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            currentSelection = getAdapterPosition();
            notifyDataSetChanged();
            ((FilterFragment.OnFilterInteractionListener) mContext).onFilterTypeChanged(currentSelection);
        }
    }
}
