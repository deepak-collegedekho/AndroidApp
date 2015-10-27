package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.WidgetAdapter;
import com.collegedekho.app.entities.Widget;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnWidgetInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WidgetListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WidgetListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    public static final String TITLE = "Widgets";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<Widget> widgets;

    private OnWidgetInteractionListener mListener;

    private MainActivity mMainActivity;

    public boolean mUserLearnedTouch = false;
    public static boolean READY_TO_CLOSE = false;

    public WidgetListFragment() {
        //Required empty public constructor
    }

    public static WidgetListFragment newInstance(ArrayList<Widget> widgets) {
        WidgetListFragment streamFragment = new WidgetListFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList(ARG_PARAM1, widgets);
        streamFragment.setArguments(b);

        return streamFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.widgets = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    private void touchLearned() {
        if (!this.mUserLearnedTouch) {
            // The user manually opened the drawer; store this flag to prevent auto-showing
            // the navigation drawer automatically in the future.
            this.mUserLearnedTouch = true;
            SharedPreferences sp = this.getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
            sp.edit().putBoolean(Constants.KEY_USER_LEARNED_TOUCH, true).apply();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        GridView grid = (GridView) rootView.findViewById(R.id.home_page_body);
        TextView streamTextView = (TextView) rootView.findViewById(R.id.home_page_stream_label);

        this.mUserLearnedTouch = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(Constants.KEY_USER_LEARNED_TOUCH, false);

        if (MainActivity.user != null && MainActivity.user.getStream_name() != null)
        {
            streamTextView.setVisibility(View.VISIBLE);
            streamTextView.setText(MainActivity.user.getStream_name());
        }
        else
            streamTextView.setVisibility(View.GONE);

        grid.setAdapter(new WidgetAdapter(getActivity(), widgets));
        grid.setOnItemClickListener(this);

        if (!this.mUserLearnedTouch) {
            rootView.setPadding(0, 0, 0, 0);
            rootView.findViewById(R.id.widget_touch_tutorial_layout).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.widget_touch_tutorial_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.GONE);
                    int verticalPadding = mMainActivity.getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                    int horizontalPadding = mMainActivity.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
                    ((View) v.getParent()).setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
                    touchLearned();
                    v.setOnClickListener(null);
                }
            });
        }
        else
            rootView.findViewById(R.id.widget_touch_tutorial_layout).setVisibility(View.GONE);

        return rootView;
    }

    public void onWidgetSelected(Widget widget, int position) {
        if (this.mListener != null) {
            this.mListener.onWidgetSelected(widget, position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnWidgetInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnWidgetInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        READY_TO_CLOSE = false;
        this.mMainActivity = (MainActivity) getActivity();

        if (this.mMainActivity != null) {
            this.mMainActivity.currentFragment = this;
            this.mMainActivity.mShouldDisplayHomeUp();
            this.mMainActivity.mUpdateNavigationMenuItem(0);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        READY_TO_CLOSE = true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onWidgetSelected(widgets.get(position), position+1);
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
    public interface OnWidgetInteractionListener {
        void onWidgetSelected(Widget widget, int position);
    }


}
