package com.collegedekho.app.fragment.profileBuilding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.CountryAdapter;
import com.collegedekho.app.entities.Country;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.widget.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


public class CountrySelectionFragment extends BaseProfileBuildingFragment implements CountryAdapter.OnCountryItemSelectListener {

    private final String TAG = CountrySelectionFragment.class.getSimpleName();
    private RecyclerView mRecyclerViewCountries;
    private CountryAdapter mCountryAdapter;
    private ArrayList<Country> mCountryList,mOriginalList;
    private Button mContinueButton ;
    private SearchView mSearchView;
    private int mSelectedCount = 0;
    private int mInstituteCount = 0;

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
        mSelectedCount = 0;
        updateSelectedCount(mSelectedCount);
        this.mRequestForCountries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.initIntituesCountViews(view);
        TextView currentLevelTxtView = (TextView) view.findViewById(R.id.user_education_level);
        currentLevelTxtView.setVisibility(View.VISIBLE);
        int currentLevelId = MainActivity.mProfile.getCurrent_level_id();
        if (currentLevelId == ProfileMacro.LEVEL_TWELFTH || currentLevelId == ProfileMacro.LEVEL_TENTH) {
            currentLevelTxtView.setText(getString(R.string.school));
        } else if (currentLevelId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
            currentLevelTxtView.setText(getString(R.string.college));
        } else {
            currentLevelTxtView.setText(getString(R.string.pg_college));
        }
        mInstituteCount  = 0;
        super.setInstituteCount(String.valueOf(mInstituteCount));
        this.mRecyclerViewCountries = (RecyclerView) view.findViewById(R.id.recycler_view_countries);
        this.mContinueButton = (Button) view.findViewById(R.id.btn_continue_country_selected);
        this.mSearchView = (SearchView) view.findViewById(R.id.filter_search);
        mCountryList = new ArrayList<Country>();
        mOriginalList = new ArrayList<Country>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayout.VERTICAL,false);
        mCountryAdapter = new CountryAdapter(getContext(),this.mCountryList,this);
        this.mRecyclerViewCountries.setLayoutManager(layoutManager);
        this.mRecyclerViewCountries.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewCountries.getContext(),
                DividerItemDecoration.VERTICAL_LIST);
        mRecyclerViewCountries.addItemDecoration(dividerItemDecoration);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserCountries();
            }
        });
        this.mRecyclerViewCountries.setAdapter(mCountryAdapter);
        this.updateSelectedCount(mSelectedCount);
        this.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty() || newText.contentEquals(""))
                {
                    Log.e(TAG,"mCountryList "+mOriginalList.size());
                    mCountryList.clear();
                    mCountryList.addAll(mOriginalList);
                    mCountryAdapter.notifyDataSetChanged();
                    mRecyclerViewCountries.scrollToPosition(0);
                }
                else
                {
                    ArrayList<Country> filteredCountryList  = filter(mOriginalList,newText);
                    mCountryList.clear();
                    mCountryList.addAll(filteredCountryList);
                    mCountryAdapter.notifyDataSetChanged();
                    mRecyclerViewCountries.scrollToPosition(0);
                }
                return true;
            }
        });

        this.mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mCountryAdapter.animateTo(mCountryList);
                mRecyclerViewCountries.scrollToPosition(0);
                return true;
            }
        });

        view.findViewById(R.id.user_education_level_edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event(AllEvents.ACTION_LEVEL_EDIT_SELECTION, null, null));
            }
        });

    }

    public void updateSelectedCount(int count)
    {
        mContinueButton.setText("Continue ("+count+")");
    }

    @Override
    public String getEntity() {
        return null;
    }

    public void mCountriesResponseCompleted(ArrayList<Country> countriesList){
        this.mCountryList.clear();
        for (Country country: countriesList
             ) {
            for (Country preselected: MainActivity.mProfile.getPreferred_countries()
                 ) {
                    if(country.getId() == preselected.getId())
                    {
                        country.setSelected(true);
                        break;
                    }
            }
            this.mCountryList.add(country);
        }
        this.mOriginalList.clear();
        this.mOriginalList.addAll(countriesList);
        mCountryAdapter.notifyDataSetChanged();
        updateCountryCounter();
    }

    private void mRequestForCountries() {
        EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_COUNTRIES, null, null));
    }

    private void setUserCountries()
    {
        String selectedCountries = "[";
        for (Country element : mCountryList) {
            if(element.isSelected())
            {
                selectedCountries+=element.id+",";
            }
        }
        if(selectedCountries.length()>1)
        {
            selectedCountries = selectedCountries.substring(0, selectedCountries.length()-1);
        }
        selectedCountries+="]";
        if(selectedCountries.length()>2)
        ((MainActivity)getActivity()).setUserCountries(selectedCountries);
        else
        Toast.makeText(getContext(),"Please select atleast one country",Toast.LENGTH_LONG).show();
    }

    private ArrayList<Country> filter(ArrayList<Country> models, String query) {
        query = query.toLowerCase();

        final ArrayList<Country> filteredModelList = new ArrayList<>();
        final ArrayList<Country> selectedList = new ArrayList<>();
        final ArrayList<Country> rejectedList = new ArrayList<>();
        for (Country model : models) {
            final String text = model.name.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
            else if(model.isSelected())
            {
                selectedList.add(model);
            }
            else
            {
                rejectedList.add(model);
            }
        }
        filteredModelList.addAll(selectedList);
        filteredModelList.addAll(rejectedList);
        mSelectedCount = selectedList.size();
        updateSelectedCount(mSelectedCount);
        return filteredModelList;
    }

    @Override
    public void onItemSelect() {
            updateCountryCounter();
    }

    public void updateCountryCounter()
    {
        mSelectedCount = 0;
        mInstituteCount = 0;
        for (Country country: mCountryList) {
            if(country.isSelected())
            {
                mSelectedCount++;
                mInstituteCount+=country.institute_count;
            }
        }
        super.setInstituteCount(String.valueOf(mInstituteCount));
        updateSelectedCount(mSelectedCount);
    }
}
