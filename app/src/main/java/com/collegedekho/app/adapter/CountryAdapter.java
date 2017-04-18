package com.collegedekho.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.collegedekho.app.entities.Country;

import java.util.ArrayList;

/**
 * Created by ashutosh on 18/4/17.
 */

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Country> countries;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
