package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CountryAdapter;

import java.util.ArrayList;


public class CountryFragment  extends  BaseFragment{

    private final String TAG = AboutFragment.class.getSimpleName();
    private RecyclerView mcountryRC;
    private ArrayList<String> mCountryList = new ArrayList<>();

    public static CountryFragment newInstance() {
        return new CountryFragment();
    }

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mcountryRC = (RecyclerView)view.findViewById(R.id.user_country_rc);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayout.VERTICAL,false);
        this.mcountryRC.setLayoutManager(layoutManager);
        this.mcountryRC.setHasFixedSize(true);
        CountryAdapter countryAdapter = new CountryAdapter(getContext(), mCountryList);
        this.mcountryRC.setAdapter(countryAdapter);

    }

    @Override
    public String getEntity() {
        return null;
    }
}
