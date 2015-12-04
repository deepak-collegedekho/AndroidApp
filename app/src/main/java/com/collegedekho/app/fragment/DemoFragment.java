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
 * Created by sureshsaini on 27/11/15.
 */
public class DemoFragment extends  BaseFragment {

    public static DemoFragment newInstance() {
        DemoFragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DemoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_new, container, false);
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
        return rootView;
    }

}
