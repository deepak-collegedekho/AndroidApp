package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CountryAdapter;
import com.collegedekho.app.entities.Country;

import java.util.ArrayList;

/**
 * Created by ashutosh on 18/4/17.
 */

public class CountrySelectionFragment extends BaseFragment {

    private static final String ARG_COUNTRIES = "countries";

    private ArrayList<Country> mCountryList;
    private RecyclerView mRecyclerViewCountries;
    private CountryAdapter mCountryAdapter;
    protected LinearLayoutManager layoutManager;

    public static CountrySelectionFragment newInstance(ArrayList<Country> countries){
        CountrySelectionFragment countrySelectionFragment = new CountrySelectionFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList(ARG_COUNTRIES,countries);
        countrySelectionFragment.setArguments(b);
        return countrySelectionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mCountryList = getArguments().getParcelableArrayList(ARG_COUNTRIES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_selection,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mRecyclerViewCountries = (RecyclerView) view.findViewById(R.id.recycler_view_countries);
        mCountryAdapter = new CountryAdapter(this.getContext(),mCountryList);
        super.layoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerViewCountries.setLayoutManager(layoutManager);
        mRecyclerViewCountries.setAdapter(mCountryAdapter);
    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }
}
