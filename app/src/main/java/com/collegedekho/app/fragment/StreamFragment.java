package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
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

public class StreamFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String ARG_STREAMS = "streams";
    private static final String ARG_STREAM_UPDATE = "stream_update";

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
        b.putBoolean(ARG_STREAM_UPDATE, isForUpdate);
        streamFragment.setArguments(b);
        return streamFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            streams = getArguments().getParcelableArrayList(ARG_STREAMS);
            isStreamUpdate = getArguments().getBoolean(ARG_STREAM_UPDATE);
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


    public void onStreamSelected(String uri, String name) {
        if (mListener != null) {
                mListener.onStreamSelected(uri, name);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnStreamInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
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
        Stream streamObj =  streams.get(position);
//        if(isStreamUpdate) {
//            onStreamUpdated(streamObj.resourceUri, streamObj.getName() );
//        }
//        else
        onStreamSelected(streamObj.resourceUri,streamObj.getName());
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }


    public interface OnStreamInteractionListener {
        void onStreamSelected(String stream, String streamName);
        void onEditedStreamSelected(String stream, String streamName);
        //void onStreamUpdated(String stream, String streamName);
    }


    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_STREAMS, streams);
        outState.putBoolean(ARG_STREAM_UPDATE, this.isStreamUpdate);
    }
}
