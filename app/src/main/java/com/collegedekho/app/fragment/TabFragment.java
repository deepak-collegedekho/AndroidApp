package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.CircleImageView;

/**
 * Created by sureshsaini on 6/12/15.
 */
public class TabFragment extends  BaseFragment {
    private final String TAG ="Tab Fragment";
    private static String PARAM1 = "param1";

    private int selectedTabMenuPosition =0;
    private int selectedSunMenuPosition =0;

    public static TabFragment newInstance(int tabPosoition) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM1, tabPosoition);
        fragment.setArguments(args);
        return fragment;
    }

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.selectedTabMenuPosition = args.getInt(PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        CircleImageView mProfileImage = (CircleImageView)rootView.findViewById(R.id.profile_image);
        String image = MainActivity.user.getImage();
        if (image != null && ! image.isEmpty()) {
            mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
            mProfileImage.setVisibility(View.VISIBLE);
        }
        else
            mProfileImage.setVisibility(View.GONE);

        rootView.findViewById(R.id.prep_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.resources_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.future_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.my_alerts).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_third).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_fourth).setOnClickListener(this);

        return rootView;
    }

    private void mUpdateSelectedTab(){
        View view = getView();
        if(view ==   null) return;
        TextView prepBuddies       = (TextView)view.findViewById(R.id.prep_buddies);
        TextView resourceBuddies   = (TextView)view.findViewById(R.id.resources_buddies);
        TextView futureBuddies     = (TextView)view.findViewById(R.id.future_buddies);
        TextView myAlerts          = (TextView)view.findViewById(R.id.my_alerts);

        prepBuddies.setSelected(false);
        resourceBuddies.setSelected(false);
        futureBuddies.setSelected(false);
        myAlerts.setSelected(false);
        if(this.selectedTabMenuPosition == 0){
           prepBuddies.setSelected(true);
        }else   if(this.selectedTabMenuPosition == 1){
            resourceBuddies.setSelected(true);
        }else   if(this.selectedTabMenuPosition == 2){
            futureBuddies.setSelected(true);
        }else   if(this.selectedTabMenuPosition == 3){
            myAlerts.setSelected(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mUpdateSelectedTab();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.prep_buddies:
                this.selectedTabMenuPosition = 0;
                break;
            case R.id.resources_buddies:
                this.selectedTabMenuPosition = 1;
                break;
            case R.id.future_buddies:
                this.selectedTabMenuPosition = 2;
                break;
            case R.id.my_alerts:
                this.selectedTabMenuPosition = 3;
                break;
            case R.id.home_widget_first:
                this.selectedTabMenuPosition = 0;
                break;
            case R.id.resources_buddies:
                this.selectedTabMenuPosition = 1;
                break;
            case R.id.future_buddies:
                this.selectedTabMenuPosition = 2;
                break;
            case R.id.my_alerts:
                this.selectedTabMenuPosition = 3;
                break;
            default:
                break;
        }
        this.mUpdateSelectedTab();
    }
}