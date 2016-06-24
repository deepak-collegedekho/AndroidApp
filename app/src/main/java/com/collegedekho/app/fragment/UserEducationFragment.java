package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserEducationFragment.OnUserEducationInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserEducationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserEducationFragment extends BaseFragment {

    private static final String TAG = "user_education_fragment";
    private OnUserEducationInteractionListener mListener;

    public UserEducationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserEducationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserEducationFragment newInstance() {
        UserEducationFragment fragment = new UserEducationFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_education, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.user_education_college).setOnClickListener(this);
        view.findViewById(R.id.user_education_school).setOnClickListener(this);
        view.findViewById(R.id.user_education_skip_button).setOnClickListener(this);
        if(MainActivity.user != null){
            String phone = MainActivity.user.getPhone_no();
            if(phone != null && !phone.isEmpty())
            ((TextView)view.findViewById(R.id.user_education_phone)).setText(":  +91-"+phone);
            else
                ((TextView)view.findViewById(R.id.user_education_phone)).setText(":  NA");
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.currentFragment = this;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        switch(view.getId())
        {
            case R.id.user_education_skip_button:
                mIKnowWhatIWhat();
                break;
            case R.id.user_education_school:
                mSelectedCurrentEducation(ProfileMacro.CURRENT_EDUCATION_SCHOOL);
                break;
            case R.id.user_education_college:
                mSelectedCurrentEducation(ProfileMacro.CURRENT_EDUCATION_COLLEGE);
                break;
            default:
                break;
        }
    }

    private void mIKnowWhatIWhat() {
        if(this.mListener !=  null)
            this.mListener.onIknowWhatIWant();
    }

    private void mSelectedCurrentEducation(int currentLevelID) {
        if(this.mListener ==  null)
            return;
        HashMap<String, String> params = new HashMap<>();
        params.put("current_sublevel_id",""+currentLevelID);

        if(currentLevelID == ProfileMacro.CURRENT_EDUCATION_SCHOOL)
            params.put("preferred_level",""+ProfileMacro.LEVEL_UNDER_GRADUATE);
        else
            params.put("preferred_level",""+ProfileMacro.LEVEL_POST_GRADUATE);

        if(MainActivity.mProfile != null){
            MainActivity.mProfile.setCurrent_sublevel_id(currentLevelID);
            if(currentLevelID == ProfileMacro.CURRENT_EDUCATION_SCHOOL)
                MainActivity.mProfile.setPreferred_level(ProfileMacro.LEVEL_UNDER_GRADUATE);
            else
               MainActivity.mProfile.setPreferred_level(ProfileMacro.LEVEL_POST_GRADUATE);
        }

        this.mListener.onSelectedCurrentEducation(params);
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
        void onIknowWhatIWant();
        void onSelectedCurrentEducation(HashMap<String, String> params);
    }

}
