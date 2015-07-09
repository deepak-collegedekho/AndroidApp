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
import com.collegedekho.app.adapter.StreamAdapter;
import com.collegedekho.app.entities.Stream;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnStreamInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StreamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StreamFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Stream> streams;

    private OnStreamInteractionListener mListener;

    public StreamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StreamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StreamFragment newInstance(ArrayList<Stream> streams) {
        StreamFragment streamFragment = new StreamFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList(ARG_PARAM1, streams);
        streamFragment.setArguments(b);
        return streamFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            streams = getArguments().getParcelableArrayList(ARG_PARAM1);
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
        onStreamSelected(streams.get(position).uri);
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
    public interface OnStreamInteractionListener {
        void onStreamSelected(String uri);
    }


}
