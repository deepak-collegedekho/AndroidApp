package com.collegedekho.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.WidgetAdapter;
import com.collegedekho.app.entities.Widget;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnWidgetInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WidgetListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WidgetListFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String TITLE = "Widgets";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<Widget> widgets;

    private OnWidgetInteractionListener mListener;

    private MainActivity mMainActivity;

    public WidgetListFragment() {
        // Required empty public constructor
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
            widgets = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView grid = (GridView) inflater.inflate(R.layout.fragment_home, container, false);
        grid.setAdapter(new WidgetAdapter(getActivity(), widgets));
        grid.setOnItemClickListener(this);
        return grid;
    }

    public void onWidgetSelected(Widget widget) {
        if (mListener != null) {
            mListener.onWidgetSelected(widget);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnWidgetInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnWidgetInteractionListener");
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onWidgetSelected(widgets.get(position));
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
        void onWidgetSelected(Widget widget);
    }


}
