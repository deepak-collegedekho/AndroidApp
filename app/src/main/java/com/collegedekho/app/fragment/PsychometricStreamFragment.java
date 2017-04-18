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
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;
import java.util.List;

public class PsychometricStreamFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String ARG_STREAMS = "streams";
    private static final String ARG_STREAM_UPDATE = "stream_update";
    private TextView txtMessage;
    private ArrayList<Stream> streams;

    private OnStreamInteractionListener mListener;
    private SpiderWebChart spiderwebchart;

    public PsychometricStreamFragment() {
        // Required empty public constructor
    }

    public static PsychometricStreamFragment newInstance(ArrayList<Stream> streams ) {
        PsychometricStreamFragment streamFragment = new PsychometricStreamFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList(ARG_STREAMS, streams);
        streamFragment.setArguments(b);
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
        grid.setAdapter(new StreamAdapter(getActivity(), new ArrayList<>(streams.subList(0,2))));
        grid.setOnItemClickListener(this);

        boolean isHomeLoaded = getActivity().getSharedPreferences(getString(R.string.PREFS),Context.MODE_PRIVATE)
                .getBoolean(getString(R.string.USER_HOME_LOADED), false);
        if(isHomeLoaded){
            rootView.findViewById(R.id.user_education_top_layout).setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.spiderwebchart = (SpiderWebChart)view.findViewById(R.id.spiderwebchart);
        this.spiderwebchart.setVisibility(View.VISIBLE);
        this.txtMessage=(TextView) view.findViewById(R.id.txt_message);
        txtMessage.setText(getString(R.string.PSYCHOMETRIC_STREAMS));
        initSpiderWebChart();
    }

    public void onStreamSelected(Stream streamObj) {
        if (mListener == null || streamObj == null)
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
        MainActivity.mProfile.setPsychometric_given(1);
        mListener.onStreamSelected(streamId);
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
        if (NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        if(getView() != null){
            getView().findViewById(R.id.user_education_top_layout).setVisibility(View.GONE);
        }

        onStreamSelected( streams.get(position));
    }

    @Override
    public String getEntity() {
        return null;
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



    public interface OnStreamInteractionListener {
        void onStreamSelected(int streamId);
    }
}
