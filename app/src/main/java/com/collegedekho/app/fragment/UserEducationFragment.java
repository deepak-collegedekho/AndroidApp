package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.UserEducation;
import com.collegedekho.app.utils.GaradiWindowHelper;

import java.util.ArrayList;
import java.util.HashMap;

import cn.jeesoft.widget.pickerview.CharacterPickerView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserEducationFragment.OnUserEducationInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserEducationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserEducationFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_EDUCATION_LIST = "user_education_list";

    // TODO: Rename and change types of parameters
    private String mLevelID;
    private String mSubLevelID = "";
    private String mStreamID = "";
    private String mMarks = "";
    private boolean isUserPreparing;
    private OnUserEducationInteractionListener mListener;
    private RelativeLayout.LayoutParams layoutParams;
    private ArrayList<UserEducation> mUserEducationList;

    public UserEducationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userEducationList Parameter 1.
     * @return A new instance of fragment UserEducationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserEducationFragment newInstance(ArrayList<UserEducation> userEducationList) {
        UserEducationFragment fragment = new UserEducationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(USER_EDUCATION_LIST, userEducationList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mUserEducationList = getArguments().getParcelableArrayList(USER_EDUCATION_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_education, container, false);

        RelativeLayout layout = new RelativeLayout(this.getContext());
        //setContentView(layout);

        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        CharacterPickerView pickerView = new CharacterPickerView(this.getContext());
        layout.addView(pickerView, layoutParams);

        GaradiWindowHelper.setPickerData(pickerView, this.mUserEducationList);

        pickerView.setOnOptionChangedListener(new CharacterPickerView.OnOptionChangedListener() {
            @Override
            public void onOptionChanged(CharacterPickerView view, String levelID, String subLevelID, String streamID, String marksID) {
                Log.e("test", levelID + "," + subLevelID + "," + streamID + "," + marksID);
                UserEducationFragment.this.mLevelID = levelID;
                UserEducationFragment.this.mSubLevelID = subLevelID;
                UserEducationFragment.this.mStreamID = streamID;
                UserEducationFragment.this.mMarks = marksID;
            }
        });

        ((LinearLayout) rootView.findViewById(R.id.user_education_layout)).addView(layout);

        ToggleButton preparingToggle = (ToggleButton)rootView.findViewById(R.id.is_user_preparing);

        this.isUserPreparing = preparingToggle.isChecked();
        rootView.findViewById(R.id.user_education_submit_button).setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnUserEducationInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnUserEducationInteractionListener");
        }
    }
    private void onCurrentEducationSubmitted(HashMap params) {
        if (this.mListener != null) {
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("sublevel", UserEducationFragment.this.mSubLevelID);
            map.put("stream", UserEducationFragment.this.mStreamID);
            map.put("marks", UserEducationFragment.this.mMarks);
            map.put("is_preparing", (UserEducationFragment.this.isUserPreparing ? "1":"0"));

            mListener.onEducationSelected(map);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId())
        {
            case R.id.user_education_submit_button:
                onCurrentEducationSubmitted(null);
                break;
            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnUserEducationInteractionListener {
        void onEducationSelected(HashMap<String, String> map);
    }
}
