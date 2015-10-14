package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.fragment.InstituteListFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sureshsaini on 13/10/15.
 */
public class SearchInstituteAdapter extends RecyclerView.Adapter {

    private HashMap<String, List<String>> mSearchedList;
    private Context mContext;

    public SearchInstituteAdapter(Context context, HashMap<String, List<String>> list) {
        this.mSearchedList = list;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_institute_list, parent, false);
        try {
            return new ViewHolder(rootView, (InstituteListFragment.OnInstituteSelectedListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnInstituteSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView searchTitle;
        RecyclerView recyclerView;

        public ViewHolder(View itemView, InstituteListFragment.OnInstituteSelectedListener listener) {
            super(itemView);
            searchTitle = (TextView) itemView.findViewById(R.id.searched_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.searched_list_recycerView);

        }

        @Override
        public void onClick(View v) {

        }
    }

    }
