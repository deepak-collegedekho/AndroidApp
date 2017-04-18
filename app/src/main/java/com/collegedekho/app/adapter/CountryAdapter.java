package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 17/4/17.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private Context mContext;
    private ArrayList<String> mCountryList ;

    public  CountryAdapter(Context context , ArrayList<String> countryList){
        this.mContext = context;
        this.mCountryList = countryList;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View itemView = inflater.inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {

        String name = mCountryList.get(position);
        holder.countryName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mCountryList == null ? 0 : mCountryList.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder{

        TextView countryName ;

        public CountryViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView)itemView.findViewById(R.id.country_name);
        }
    }
}
