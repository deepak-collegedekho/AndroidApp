package com.collegedekho.app.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;

/**
 * Created by sureshsaini on 8/12/15.
 */
public class MyAlertFragment extends  BaseFragment {

    private final String TAG = "MyAlert Frgament";
    private static String PARAM1 = "param1";

    public static MyAlertFragment newInstance() {
        MyAlertFragment fragment = new MyAlertFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MyAlertFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_alert, container, false);
        WheelView wheelView = (WheelView)rootView.findViewById(R.id.wheelview);
        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public Drawable getDrawable(int position) {
                //return drawable here - the position can be seen in the gifs above
                if(position == 1)
                {
                    return getDrawable(R.drawable.bg_black_circle);
                }
                return null;
            }

            @Override
            public int getCount() {
               return 2;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }
        });

        return rootView;

    }
}
