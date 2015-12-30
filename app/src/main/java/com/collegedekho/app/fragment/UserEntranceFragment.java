package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.TestPagerAdapter;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 27/11/15.
 */
public class UserEntranceFragment extends  BaseFragment {

    private final String TAG="User Entrance Fragment";
    private static final String PARAM1="param1";

    private ArrayList<String> mTestList;
    private boolean IS_TUTE_COMPLETED = true;

    public static UserEntranceFragment newInstance(ArrayList<String> testList) {
        UserEntranceFragment fragment = new UserEntranceFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(PARAM1,testList);
        fragment.setArguments(args);
        return fragment;
    }

    public UserEntranceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null)
        {
           this.mTestList = args.getStringArrayList(PARAM1);
        }
        //  for demo
        this.mTestList = new ArrayList<>();
        for (int i = 0; i < 6 ; i++) {
            this.mTestList.add("54251"+i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_entrances, container, false);

        ViewPager pager = (ViewPager)rootView.findViewById(R.id.user_test_pager);
        TabLayout testTablayout =  (TabLayout)rootView.findViewById(R.id.user_test_tabs);
        TestPagerAdapter mApdter = new TestPagerAdapter(getChildFragmentManager(), this.mTestList);
        pager.setAdapter(mApdter);
        testTablayout.setupWithViewPager(pager);

        return rootView;
    }



}
