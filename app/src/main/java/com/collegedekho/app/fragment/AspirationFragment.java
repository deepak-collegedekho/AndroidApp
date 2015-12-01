package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;


public class AspirationFragment extends BaseFragment {


        private final String TAG = "Aspiration Fragment";

        public AspirationFragment() {
            // required empty Constructor
        }

        public static AspirationFragment newInstance(){
            AspirationFragment fragment = new AspirationFragment();
            Bundle args = new Bundle();
            return fragment;

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user_aspirational, container, false);
            ((TextView) rootView.findViewById(R.id.points_test_view)).setText("YOU  HAVE EARNED FOR SELECTING YOUR EXAMS");
            return rootView;
        }

}
