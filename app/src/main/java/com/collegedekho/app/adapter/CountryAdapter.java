package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Country;

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

        holder.countryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountryList.get(position).setSelected(!mCountryList.get(position).isSelected());
                if(mCountryList.get(position).isSelected()){
                    (v).setBackgroundColor(ContextCompat.getColor(mContext,R.color.primary_orange));
                    ((TextView) v).setTextColor(ContextCompat.getColor(mContext,R.color.white));
                } else {
                    (v).setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                    ((TextView) v).setTextColor(ContextCompat.getColor(mContext,R.color.textPrimary));
                }
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
}
