package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.StreamAdapter;
import com.collegedekho.app.display.androidcharts.diagram.SpiderWebChart;
import com.collegedekho.app.display.androidcharts.series.TitleValueEntity;
import com.collegedekho.app.entities.Stream;

import java.util.ArrayList;
import java.util.List;

public class PsychometricStreamFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String ARG_STREAMS = "streams";
    private static final String ARG_STREAM_UPDATE = "stream_update";
    private TextView txtMessage;
    private ArrayList<Stream> streams;

    private OnStreamInteractionListener mListener;
    private static boolean isStreamUpdate;
    private boolean isEditMode;
    private SpiderWebChart spiderwebchart;
    public PsychometricStreamFragment() {
        // Required empty public constructor
    }

    public static PsychometricStreamFragment newInstance(ArrayList<Stream> streams , boolean isForUpdate) {
        PsychometricStreamFragment streamFragment = new PsychometricStreamFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList(ARG_STREAMS, streams);
        b.putBoolean(ARG_STREAM_UPDATE, isForUpdate);
        streamFragment.setArguments(b);
        return streamFragment;
    }

    public static PsychometricStreamFragment newEditableInstance(ArrayList<Stream> streams , boolean isForUpdate) {
        PsychometricStreamFragment streamFragment = new PsychometricStreamFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList(ARG_STREAMS, streams);
        b.putBoolean(ARG_STREAM_UPDATE, isForUpdate);
        b.putBoolean("is_edit_mode",true);
        streamFragment.setArguments(b);
        return streamFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            streams = getArguments().getParcelableArrayList(ARG_STREAMS);
            isStreamUpdate = getArguments().getBoolean(ARG_STREAM_UPDATE);
            isEditMode=getArguments().getBoolean("is_edit_mode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_streams, container, false);
        GridView grid = (GridView) rootView.findViewById(R.id.stream_grid);
        grid.setAdapter(new StreamAdapter(getActivity(), new ArrayList(streams.subList(0,2))));
        grid.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.spiderwebchart = (SpiderWebChart)view.findViewById(R.id.spiderwebchart);
        this.spiderwebchart.setVisibility(View.VISIBLE);
        this.txtMessage=(TextView) view.findViewById(R.id.txt_message);
        txtMessage.setText(MainActivity.getResourceString(R.string.PSYCHOMETRIC_STREAMS));
        initSpiderWebChart();
    }

    public void onStreamSelected(String uri, String name) {
        if (mListener != null) {
            if (!isEditMode) {
                mListener.onStreamSelected(uri, name);
            }else {
                mListener.onEditedStreamSelected(uri,name);
            }
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
    private void initSpiderWebChart() {
//        spiderwebchart.setDisplayBorder(false);
        List<TitleValueEntity> data1 = new ArrayList<>();
        for (Stream stream:streams ) {
            data1.add(new TitleValueEntity(stream.getShortName().split("/")[0], stream.getScore()));
        }
        List<List<TitleValueEntity>> data = new ArrayList<>();
        data.add(data1);
        spiderwebchart.setFontSize(getActivity().getResources().getDimension(R.dimen.chart_font_size));
        spiderwebchart.setLatitudeNum(data1.size());
        spiderwebchart.setLongitudeNum(data1.size());
        spiderwebchart.setData(data);
    }
}
