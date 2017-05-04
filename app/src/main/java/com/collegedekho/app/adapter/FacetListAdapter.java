package com.collegedekho.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Currency;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;

public class FacetListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Facet> mFacets;
    private ArrayList<Currency> mCurrencies;
    private Context mContext;
    private int totalSize;
    private int folderId;

    public FacetListAdapter(Context context, ArrayList<Facet> facets,int folderId,ArrayList<Currency> currencies) {
        mFacets = facets;
        this.mCurrencies = currencies;
        this.folderId = folderId;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

        if(viewType == 2 && folderId == Constants.TAG_STUDY_ABROAD_FOLDER_ID)
        {
            View convertView = inflater.inflate(R.layout.item_currency_view, parent, false);
            return new CurrencyViewHolder(convertView);
        }
        else
        {
            View convertView = inflater.inflate(R.layout.item_filter_view, parent, false);
            return new FacetHolder(convertView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.e("onBindViewHolder",mCurrencies.size()+" - position - "+position+" viewType - "+holder.getItemViewType());
        if(holder.getItemViewType()!=2)
        ((FacetHolder)holder).setUp(mFacets.get(position));
        else
        ((CurrencyViewHolder)holder).setUp(mCurrencies);

//        holder.setUp(mCurrencies.get([position]));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if(mCurrencies.size()>1)
        totalSize = mFacets.size()+1;
        else
        totalSize = mFacets.size();
        return totalSize;
    }

    public void updateFilters(ArrayList<Facet> facetList,int folder_id) {
        mFacets.clear();
        this.folderId = folder_id;
        mFacets.addAll(facetList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        Log.e("getItemViewType","My Location "+position+" Total Size"+totalSize+" Facets size"+mFacets.size());
        if(position<mFacets.size())
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    public void animateTo(ArrayList<Facet> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(ArrayList<Facet> newModels) {
        for (int i = mFacets.size() - 1; i >= 0; i--) {
            final Facet model = mFacets.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<Facet> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Facet model = newModels.get(i);
            if (!mFacets.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<Facet> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Facet model = newModels.get(toPosition);
            final int fromPosition = mFacets.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Facet removeItem(int position) {
        final Facet model = mFacets.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Facet model) {
        mFacets.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Facet model = mFacets.remove(fromPosition);
        mFacets.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
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

    class CurrencyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout currenciesChips;

        public CurrencyViewHolder(View itemView) {
            super(itemView);
            currenciesChips = (LinearLayout) itemView.findViewById(R.id.currencyList);
        }

        public void setUp(ArrayList<Currency> currencies)
        {
            ChipCloudConfig config = new ChipCloudConfig()
                    .selectMode(ChipCloud.SelectMode.single)
                    .checkedChipColor(Color.parseColor("#ddaa00"))
                    .checkedTextColor(Color.parseColor("#ffffff"))
                    .uncheckedChipColor(Color.parseColor("#efefef"))
                    .uncheckedTextColor(Color.parseColor("#666666"))
                    .useInsetPadding(true);
            ChipCloud chipCloud = new ChipCloud(mContext,currenciesChips,config);
            chipCloud.addChip("INR");
            for (Currency element:
                    currencies
                 ) {
                chipCloud.addChip(element.getShort_name());
            }
        }
    }
}
