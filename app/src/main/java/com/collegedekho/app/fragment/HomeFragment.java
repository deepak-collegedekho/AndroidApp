package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.resource.Constants;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHomeInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    public static final String TITLE = "Home";


    private OnHomeInteractionListener mListener;

    private  MainActivity mMainActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ViewGroup parent = ((ViewGroup) rootView.findViewById(R.id.home_frame));
        for (int i = 0; i < parent.getChildCount(); i += 2) {
            View v = parent.getChildAt(i);
            //TODO: remove this condition to see the step-by-step option
            if (i == 0)
                v.setVisibility(View.GONE);
            v.setTag(User.Prefs.values()[i / 2]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelected((User.Prefs) v.getTag());
                }
            });
            ((ImageView) v.findViewById(R.id.home_image)).setImageResource(Constants.headImages[i]);
            ((TextView) v.findViewById(R.id.home_label_head)).setText(Constants.headLabels[i]);
            ((TextView) v.findViewById(R.id.home_label_subhead)).setText(Constants.headSubLabels[i]);
        }
        return rootView;
    }

    public void onItemSelected(User.Prefs pref) {
        if (mListener != null) {
            mListener.onHomeItemSelected(pref);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnHomeInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHomeInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    @Override
    public void onResume() {
        super.onResume();

        this.mMainActivity = (MainActivity) getActivity();

        if (this.mMainActivity != null)
            this.mMainActivity.currentFragment = this;

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnHomeInteractionListener {
        void onHomeItemSelected(User.Prefs preference);
    }

}
