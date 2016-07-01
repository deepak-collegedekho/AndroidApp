package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;

import java.util.HashMap;

/**
 * Created by sureshsaini on 13/6/16.
 */

public class UserPreparingFragment extends  BaseFragment {

    private OnIsPreparingListener mListener;

    public static UserPreparingFragment newInstance(){
        UserPreparingFragment fragment = new UserPreparingFragment();
        return  fragment;
    }

    public UserPreparingFragment(){
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_preparing, container, false);

        if(MainActivity.mProfile != null){
            TextView  currentLevelTxtView = (TextView)rootView.findViewById(R.id.user_preparing_education_level);
            int currentEducationId = MainActivity.mProfile.getCurrent_level_id();
            if(currentEducationId == ProfileMacro.LEVEL_TWELFTH){
                currentLevelTxtView.setText(":  School");
            }else{
                currentLevelTxtView.setText(":  College");
            }
        }
        if(MainActivity.user != null){
            String phone = MainActivity.user.getPhone_no();
            if(phone != null && !phone.isEmpty())
                ((TextView)rootView.findViewById(R.id.user_preparing_phone)).setText(":  +91-"+phone);
            else
                ((TextView)rootView.findViewById(R.id.user_preparing_phone)).setText(":  NA");
        }

        rootView.findViewById(R.id.user_preparing_rd_btn_yes).setOnClickListener(this);
        rootView.findViewById(R.id.user_preparing_rd_btn_no).setOnClickListener(this);
        rootView.findViewById(R.id.user_preparing_education_edit_btn).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (UserPreparingFragment.OnIsPreparingListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSignUpListener");
        }
    }

    @Override
    public void onResume() {
        if(getView() != null){
            ((RadioGroup)getView().findViewById(R.id.user_preparing_rd_group)).clearCheck();
        }
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.user_preparing_rd_btn_yes:
                mPreparingForExam(ProfileMacro.PREPARING_FOR_EXAM);
                break;
            case R.id.user_preparing_rd_btn_no:
                mPreparingForExam(ProfileMacro.NOT_PREPARING_FOR_EXAM);
                break;
            case R.id.user_preparing_education_edit_btn:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }


    private void mPreparingForExam(int IS_PREPARING){

        if(MainActivity.mProfile != null)
            MainActivity.mProfile.setIs_preparing(IS_PREPARING);

        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        if(mListener == null)
            return;

        HashMap<String, String> params = new HashMap<>();
        if(getView() != null){
          int checkedRadioBtnId = ((RadioGroup)getView().findViewById(R.id.user_preparing_rd_group)).getCheckedRadioButtonId();
           String is_Preparing = getView().findViewById(checkedRadioBtnId).getTag().toString();
            params.put(ProfileMacro.IS_PREPARING,is_Preparing);
        }

        mListener.onSelectedIsPreparing(params);
    }

    public interface OnIsPreparingListener {
        void onSelectedIsPreparing(HashMap<String, String> params);
    }
}
