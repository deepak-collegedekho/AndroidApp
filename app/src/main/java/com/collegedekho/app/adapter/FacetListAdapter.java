package com.collegedekho.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Currency;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;

public class FacetListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Facet> mFacets;
    private ArrayList<Facet> mRemaining;
    private ArrayList<Currency> mCurrencies;
    private Context mContext;
    private int totalSize;
    private int folderId;

    public FacetListAdapter(Context context, ArrayList<Facet> facets,int folderId,ArrayList<Currency> currencies) {
        mFacets = facets;
        mRemaining = new ArrayList<>();
        this.mCurrencies = currencies;
        this.folderId = folderId;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

        if(folderId == Constants.TAG_STUDY_ABROAD_FOLDER_ID)
        {
            if(viewType == 2)
            {
                View convertView = inflater.inflate(R.layout.item_currency_view, parent, false);
                return new CurrencyViewHolder(convertView);
            }
            else if (viewType == 0)
            {
                View convertView = inflater.inflate(R.layout.item_empty_hidden_item, parent, false);
                return new EmptyViewHolder(convertView);
            }
            else
            {
                View convertView = inflater.inflate(R.layout.item_filter_view, parent, false);
                return new FacetHolder(convertView);
            }
        }
        else
        {
            View convertView = inflater.inflate(R.layout.item_filter_view, parent, false);
            return new FacetHolder(convertView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.e("onBindViewHolder",mFacets.size()+" - position - "+position+" viewType - "+holder.getItemViewType());
        if(holder.getItemViewType()==1)
        {

//            FacetHolder facetHolder = (FacetHolder) holder;
//            facetHolder.setUp(mFacets.get(position));
//            Log.e("holder Bug"," position - "+position+" viewType - "+holder);
            if(holder instanceof FacetHolder) {
                ((FacetHolder)holder).setUp(mFacets.get(position));
            }
            else
            {
                FacetHolder newFacetHolder = new FacetHolder(holder.itemView);
                newFacetHolder.setUp(mFacets.get(position));
            }
        }
        else if(holder.getItemViewType() == 2)
        {
            ((CurrencyViewHolder)holder).setUp(mCurrencies);
        }

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

    public ArrayList<Facet> getFacetByCurrency(ArrayList<Facet> facetsList,Currency currency)
    {
        ArrayList<Facet> facets = new ArrayList<>();
        ArrayList<Facet> later = new ArrayList<>();

        for (Facet element: facetsList
             ) {

//            Log.e("ParamTesting",element.getParam());
            if(element.getLabel().contains(currency.getShort_name()))
            {
                facets.add(element);
            }
            else
            {
                later.add(element);
            }
        }
        mRemaining = new ArrayList<>();
        mRemaining.addAll(later);
        Log.e("mRemaining"," Remaining "+mRemaining.size()+" showing "+facets.size());
        facets.addAll(later);
        return  facets;
    }

    public void setByCurrencyPosition(int pos)
    {
        Currency currency = mCurrencies.get(pos);
        ArrayList<Facet> newFacets = new ArrayList<>();
        newFacets.addAll(mFacets);
        mFacets.clear();
        mFacets.addAll(getFacetByCurrency(newFacets,currency));
        updateSelectedCurrency(pos);
//        mFacets.addAll(mRemaining);
        notifyDataSetChanged();
    }

    public void updateFilters(ArrayList<Facet> facetList,int folder_id,ArrayList<Currency> currencyArrayList) {
        mFacets.clear();
        this.folderId = folder_id;
        this.mCurrencies = new ArrayList<>();
        this.mCurrencies.addAll(currencyArrayList);
        Log.e("ParamTesting",mCurrencies.size()+" - size");
        if(mCurrencies.size()>1)
        {
            Log.e("ParamTesting",mFacets.size()+" - start");
            mFacets.addAll(getFacetByCurrency(facetList,mCurrencies.get(0)));
            updateSelectedCurrency(0);
        }
        else
        {
            mFacets.addAll(facetList);
        }
        notifyDataSetChanged();
    }

    public void updateSelectedCurrency(int position)
    {
        int i = 0;
        for (Currency currency: mCurrencies
             ) {
            if(i==position)
                currency.setIs_Selected(true);
            else
                currency.setIs_Selected(false);
            i++;
        }
    }

    @Override
    public int getItemViewType(int position) {

        Log.e("getItemViewType","My Location "+position+" Total Size"+totalSize+" Facets size"+mFacets.size());
        if(position<mFacets.size())
        {
            if(position>3 && mCurrencies.size()>0)
            return 0;
            else
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

    class EmptyViewHolder extends RecyclerView.ViewHolder{

        public EmptyViewHolder(View itemView) {
            super(itemView);
//            itemView.setVisibility(View.GONE);
        }
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout currenciesChips;

        public void update(int i)
        {
            setByCurrencyPosition(i);
        }

        public CurrencyViewHolder(View itemView) {
            super(itemView);
            currenciesChips = (LinearLayout) itemView.findViewById(R.id.currencyList);
        }

        public void setUp(ArrayList<Currency> currencies)
        {
            ChipCloudConfig config = new ChipCloudConfig()
                    .selectMode(ChipCloud.SelectMode.single)
                    .checkedChipColor(ContextCompat.getColor(mContext, R.color.primary_orange))
                    .checkedTextColor(Color.parseColor("#ffffff"))
                    .uncheckedChipColor(Color.parseColor("#efefef"))
                    .uncheckedTextColor(Color.parseColor("#666666"))
                    .useInsetPadding(true);
//            Toast.makeText(mContext,mRemaining.size()+" - currencies size "+currencies.size(), Toast.LENGTH_SHORT).show();

            final ChipCloud chipCloud = new ChipCloud(mContext,currenciesChips,config);

            currenciesChips.removeAllViews();
            int i = 0;
            for (Currency element:
                    currencies
                 ) {
                chipCloud.addChip(element.getShort_name()+" - "+element.getName());
                if(element.is_Selected())
                {
                    chipCloud.setChecked(i);
                }
                else
                {
                    chipCloud.deselectIndex(i);
                }
                i++;
            }

            chipCloud.setListener(new ChipListener() {
                @Override
                public void chipCheckedChange(int i, boolean b, boolean b1) {
                    chipCloud.setChecked(i);
                    update(i);
//                    Toast.makeText(mContext, "position "+i, Toast.LENGTH_SHORT).show();
                }
            },true);

        }
    }
}
