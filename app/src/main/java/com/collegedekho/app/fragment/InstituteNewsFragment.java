package com.collegedekho.app.fragment;

import android.os.Bundle;

import com.collegedekho.app.entities.Institute;

/**
 * Created by sureshsaini on 19/12/15.
 */
public class InstituteNewsFragment extends  BaseFragment{
    private static String ARG_INSTITUTE ="param1";
    private Institute mInstitute;

    public InstituteNewsFragment(){
        // required empty constructor
    }

    public static InstituteNewsFragment newInstance(Institute mInstitute) {
        InstituteNewsFragment fragment = new InstituteNewsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INSTITUTE,mInstitute);
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mInstitute = getArguments().getParcelable(ARG_INSTITUTE);
        }
    }

}
