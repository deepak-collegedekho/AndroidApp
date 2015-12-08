package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sureshsaini on 27/11/15.
 */
public class ProfileFragment extends  BaseFragment {

    private final String TAG = "profile Frgament";
    private static String PARAM1 = "param1";

    private OnTabSelectListener mListener;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        if(MainActivity.user != null)
        {
            String name = MainActivity.user.getName();
            TextView mProfileName    =   (TextView)rootView.findViewById(R.id.user_name);
            CircleImageView mProfileImage = (CircleImageView)rootView.findViewById(R.id.profile_image);
            mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
            mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);
            if(name.equalsIgnoreCase("Anonymous User"))
            {
                mProfileName.setText("");
                mProfileName.setVisibility(View.INVISIBLE);
            }else {
                mProfileName.setText(name);
                mProfileName.setVisibility(View.VISIBLE);
            }
            String image = MainActivity.user.getImage();
            if (image != null && ! image.isEmpty()) {
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
                mProfileImage.setVisibility(View.VISIBLE);
            }
            else
                mProfileImage.setVisibility(View.GONE);
        }

        rootView.findViewById(R.id.prep_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.resources_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.future_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.my_alerts).setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();
        Constants.READY_TO_CLOSE = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Constants.READY_TO_CLOSE = false;

        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity != null) {
            mainActivity.currentFragment = this;
            mainActivity.mShouldDisplayHomeUp();
            mainActivity.mUpdateNavigationMenuItem(0);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity)
                this.mListener = (OnTabSelectListener) context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnTabSelectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int position =0;
        switch (view.getId())
        {
            case R.id.prep_buddies:
                position = 0;
                break;
            case R.id.resources_buddies:
                position = 1;
                break;
            case R.id.future_buddies:
                position = 2;
                break;
            case R.id.my_alerts:
                position = 3;
                break;
            default:
                break;
        }
        this.onTabSelected(position);
    }

    private void onTabSelected(int tabPosition) {
        if(this.mListener != null) {
                      this.mListener.onTabSelected(tabPosition);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public  interface OnTabSelectListener {
        void onTabSelected(int tabPosition);
    }

}
