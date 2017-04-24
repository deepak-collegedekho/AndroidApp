package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Country;
import com.collegedekho.app.entities.Facet;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by ashutosh on 18/4/17.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private ArrayList<Country> mCountryList;
    private Context mContext;
    LayoutInflater inflater;
    private OnCountryItemSelectListener mListener;

    public CountryAdapter(Context context, ArrayList<Country> countriesList)
    {
        this.mContext = context;
        this.mCountryList = countriesList;
        inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_country,parent,false);
        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, final int position) {
        holder.countryName.setText(mCountryList.get(position).name);
        if(mCountryList.get(position).isSelected()){
            holder.countryName.setBackgroundColor(ContextCompat.getColor(mContext,R.color.primary_orange));
            (holder.countryName).setTextColor(ContextCompat.getColor(mContext,R.color.white));
        } else {
            (holder.countryName).setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
            (holder.countryName).setTextColor(ContextCompat.getColor(mContext,R.color.textPrimary));
        }
        holder.countryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCountryList.get(position).isSelected())
                mCountryList.get(position).setSelected(false);
                else
                mCountryList.get(position).setSelected(true);
                notifyItemChanged(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.mCountryList.size();
    }

    public interface OnCountryItemSelectListener{
        void onItemSelect(String itemKey);
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{

        TextView countryName;
        public CountryViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.text_country_name);
        }
    }

    public void animateTo(ArrayList<Country> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(ArrayList<Country> newModels) {
        for (int i = mCountryList.size() - 1; i >= 0; i--) {
            final Country model = mCountryList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<Country> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Country model = newModels.get(i);
            if (!mCountryList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<Country> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Country model = newModels.get(toPosition);
            final int fromPosition = mCountryList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Country removeItem(int position) {
        final Country model = mCountryList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Country model) {
        mCountryList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Country model = mCountryList.remove(fromPosition);
        mCountryList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}
