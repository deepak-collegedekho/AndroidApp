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
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;

import java.util.ArrayList;

public class StreamFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String ARG_STREAMS = "streams";
    private static final String ARG_STREAM_UPDATE = "stream_update";

    private ArrayList<Stream> streams;

    private OnStreamInteractionListener mListener;
    private boolean isStreamUpdate;

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

        if(streamObj == null)
            return;

        String resourceUri = streamObj.getResourceUri();
        String resourceUriTags[] = resourceUri.split("/");
        int streamId = -1;
        try {
            streamId = Integer.parseInt(resourceUriTags[resourceUriTags.length-1]);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(streamId == -1)
            return;

        onStreamSelected(streamId);
    }


    public void onStreamSelected(int streamId) {
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        if(getView() != null){
            getView().findViewById(R.id.user_education_top_layout).setVisibility(View.GONE);
        }

        if (mListener != null) {
            mListener.onStreamSelected(streamId);
        }
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
        outState.putBoolean(ARG_STREAM_UPDATE, isStreamUpdate);
    }


    public interface OnStreamInteractionListener {
        void onStreamSelected(int streamId);
    }

}
