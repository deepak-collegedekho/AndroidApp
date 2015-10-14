package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.SearchInstituteAdapter;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sureshsaini on 13/10/15.
 */
public class SearchInstitutes  extends  BaseFragment{



    public SearchInstitutes(){
        //required empty constructor
    }

    public  SearchInstitutes newInstance()
    {
        SearchInstitutes fragment = new SearchInstitutes();
        Bundle args = getArguments();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  rootView = (View)inflater.inflate(R.layout.fragment_search_institutes, container , false);
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.search_recycerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        SearchInstituteAdapter mAdapter = new SearchInstituteAdapter(getActivity(),getSearchedList());

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator()) ;
        return  rootView;
    }

    // demo list
    private HashMap<String,List<String>> getSearchedList()
    {
        HashMap<String, List<String>> list = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<String> childList = new ArrayList();
            for (int j = 0; j < 5; j++) {
                childList.add("child "+i+" "+j);
            }
            list.put("name"+i,childList);
        }
        return list;
    }


}
