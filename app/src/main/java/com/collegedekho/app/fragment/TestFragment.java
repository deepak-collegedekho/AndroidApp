package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;


/**
 * Created by {sureshsaini} on {30/11/15.}
 */
public class TestFragment extends BaseFragment {

    private static final String PARAM1="param1";
    private String mTestDetail;

    public TestFragment(){
        // required empty constructor
    }

    public static TestFragment newInstance(String testList) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(PARAM1, testList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args= getArguments();
        if(args != null) {
            this.mTestDetail = args.getString(PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_detail, container, false);
        TextView similarUser = (TextView)rootView.findViewById(R.id.similar_users);
        similarUser.setText(mTestDetail);
        return rootView;
    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }
}
