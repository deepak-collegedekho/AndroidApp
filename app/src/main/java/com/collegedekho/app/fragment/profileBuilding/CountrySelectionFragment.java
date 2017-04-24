package com.collegedekho.app.fragment.profileBuilding;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.CountryAdapter;
import com.collegedekho.app.entities.Country;
import com.collegedekho.app.entities.SubLevel;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.fragment.AboutFragment;
import com.collegedekho.app.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


public class CountrySelectionFragment extends BaseProfileBuildingFragment {

    private final String TAG = CountrySelectionFragment.class.getSimpleName();
    private RecyclerView mRecyclerViewCountries;
    private CountryAdapter mCountryAdapter;
    private ArrayList<Country> mCountryList;
    private Button mContinueButton ;
    private int mNextPage=1;

    public static CountrySelectionFragment newInstance() {
        return new CountrySelectionFragment();
    }

    public CountrySelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            MainActivity.currentFragment = this;
        }
        this.mRequestForCountries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mRecyclerViewCountries = (RecyclerView) view.findViewById(R.id.recycler_view_countries);
        this.mContinueButton = (Button) view.findViewById(R.id.btn_continue_country_selected);
        mCountryList = new ArrayList<Country>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayout.VERTICAL,false);
        mCountryAdapter = new CountryAdapter(getContext(),this.mCountryList);
        this.mRecyclerViewCountries.setLayoutManager(layoutManager);
        this.mRecyclerViewCountries.setHasFixedSize(true);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Country element : mCountryList
                     ) {
                    if(element.isSelected())
                    {
                        Toast.makeText(getContext(),"name - "+element.name,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        this.mRecyclerViewCountries.setAdapter(mCountryAdapter);
        this.mRecyclerViewCountries.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && mNextPage<4) {
                    mRequestForCountries();
                }
            }
        });
    }

    @Override
    public String getEntity() {
        return null;
    }

    public void mCountriesResponseCompleted(ArrayList<Country> countriesList){
        this.mCountryList.addAll(countriesList);
        Toast.makeText(getContext(),"page - "+ mNextPage+" Fragment size "+this.mCountryList.size(),Toast.LENGTH_SHORT).show();
        if(countriesList.size()<1 && mNextPage==3)
        {
            mNextPage = -1;
        }
        else
        {
            mNextPage += 1;
        }
        mCountryAdapter.notifyDataSetChanged();
    }

    private void mRequestForCountries() {
        EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_COUNTRIES, null, String.valueOf(mNextPage)));
    }

    private ArrayList<Country> filter(ArrayList<Country> models, String query) {
        query = query.toLowerCase();

        final ArrayList<Country> filteredModelList = new ArrayList<>();
        for (Country model : models) {
            final String text = model.name.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
