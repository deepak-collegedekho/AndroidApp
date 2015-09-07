package com.collegedekho.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Facet;

import java.util.ArrayList;

public class FacetListAdapter extends RecyclerView.Adapter<FacetListAdapter.FacetHolder> {
    private ArrayList<Facet> mFacets;
    private Context mContext;

    public FacetListAdapter(Context context, ArrayList<Facet> facets) {
        mFacets = facets;
        mContext = context;
    }

    @Override
    public FacetHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.item_filter_view, parent, false);

        return new FacetHolder(convertView);
    }

    @Override
    public void onBindViewHolder(FacetHolder holder, int position) {
        holder.setUp(mFacets.get(position));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return mFacets.size();
    }

    public void updateFilters(ArrayList<Facet> facetList) {
        mFacets.clear();
        mFacets.addAll(facetList);
        notifyDataSetChanged();
    }

    class FacetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox label;
        Facet f;

        public FacetHolder(View itemView) {
            super(itemView);
            label = (CheckBox) itemView;
            itemView.setOnClickListener(this);
        }

        public void setUp(Facet f) {
            this.f = f;
            label.setText(f.getLabel());
            label.setChecked(f.isSelected() == 1);
        }

        @Override
        public void onClick(View v) {
            if (f.isSelected() == 1)
                f.deselect();
            else
                f.select();
        }
    }

}
