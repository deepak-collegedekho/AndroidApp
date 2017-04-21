package com.collegedekho.app.fragment.profileBuilding;

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
import com.collegedekho.app.fragment.AboutFragment;
import com.collegedekho.app.fragment.BaseFragment;

import java.util.ArrayList;


public class CountrySelectionFragment extends BaseFragment {

    private final String TAG = AboutFragment.class.getSimpleName();
    private RecyclerView mcountryRC;
    private ArrayList<String> mCountryList = new ArrayList<>();

    public static CountrySelectionFragment newInstance() {
        return new CountrySelectionFragment();
    }

    public CountrySelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mcountryRC = new RecyclerView(getContext());//(RecyclerView)view.findViewById(R.id.user_country_rc);
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
