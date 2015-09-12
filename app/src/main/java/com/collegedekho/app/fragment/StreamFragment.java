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
import com.collegedekho.app.adapter.StreamAdapter;
import com.collegedekho.app.entities.Stream;

import java.util.ArrayList;

public class StreamFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_STREAMS = "streams";

    private ArrayList<Stream> streams;

    private OnStreamInteractionListener mListener;
    private static boolean isStreamUpdate;

    public StreamFragment() {
        // Required empty public constructor
    }

    public static StreamFragment newInstance(ArrayList<Stream> streams , boolean isForUpdate) {
        StreamFragment streamFragment = new StreamFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList(ARG_STREAMS, streams);
        streamFragment.setArguments(b);
        isStreamUpdate = isForUpdate;
        return streamFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            streams = getArguments().getParcelableArrayList(ARG_STREAMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_streams, container, false);
        GridView grid = (GridView) rootView.findViewById(R.id.stream_grid);
        grid.setAdapter(new StreamAdapter(getActivity(), streams));
        grid.setOnItemClickListener(this);
        return rootView;
    }

    public void onStreamUpdated(String uri , String name) {
        if (mListener != null) {
            mListener.onStreamUpdated(uri, name);
        }
    }
    public void onStreamSelected(String uri) {
        if (mListener != null) {
            mListener.onStreamSelected(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnStreamInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStreamInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(isStreamUpdate) {
            Stream streamObj =  streams.get(position);
            onStreamUpdated(streamObj.resourceUri, streamObj.getName() );
        }
        else
        onStreamSelected(streams.get(position).resourceUri);
    }


    public interface OnStreamInteractionListener {
        void onStreamSelected(String uri);

        void onStreamUpdated(String uri , String streamName);
    }


    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }
}
