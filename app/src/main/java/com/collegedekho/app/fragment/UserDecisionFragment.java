package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.utils.ProfileMacro;

import java.util.ArrayList;


public class UserDecisionFragment extends BaseFragment {

    private final String TAG = "User Decision Fragment";
    private OnUserDecisionListener mListener;

    public UserDecisionFragment() {
        // required empty Constructor
    }

    public static UserDecisionFragment newInstance(){
        UserDecisionFragment fragment = new UserDecisionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_decision, container, false);

        if(MainActivity.mProfile != null) {
            TextView currentLevelTxtView = (TextView) rootView.findViewById(R.id.user_decision_education_level);
            int currentEducationId = MainActivity.mProfile.getCurrent_level_id();
            if (currentEducationId == ProfileMacro.LEVEL_TWELFTH) {
                currentLevelTxtView.setText(":  School");
            } else {
                currentLevelTxtView.setText(":  College");
            }

            String phone = MainActivity.mProfile.getPhone_no();
            if(phone != null && !phone.isEmpty())
                ((TextView)rootView.findViewById(R.id.user_decision_phone)).setText(":  +91-"+phone);
            else
                ((TextView)rootView.findViewById(R.id.user_decision_phone)).setText(":  NA");
        }
        if(MainActivity.user != null) {
            ArrayList<ExamDetail>  userExamList = MainActivity.user.getUser_exams();
            StringBuffer examsNameBuffer = new StringBuffer();
            if(userExamList != null) {
                int count = userExamList.size();
                if(count >= 4)
                    count = 4;
                for (int i = 0; i < count; i++) {
                    if(i==0)
                        examsNameBuffer.append(userExamList.get(i).getExam_name());
                    else
                        examsNameBuffer.append(", ").append(userExamList.get(i).getExam_name());

                }
                if(count < userExamList.size())
                    examsNameBuffer.append(".....");

            }

            TextView  userExamSTxtView = (TextView)rootView.findViewById(R.id.user_decision_exams);
            userExamSTxtView.setText(":  "+examsNameBuffer.toString());

        }

        ((TextView)rootView.findViewById(R.id.user_decision_is_preparing)).setText("  :Yes");

        rootView.findViewById(R.id.user_decision_to_recommended).setOnClickListener(this);
        rootView.findViewById(R.id.user_decision_to_dash_board).setOnClickListener(this);
        rootView.findViewById(R.id.user_decision_to_profile).setOnClickListener(this);
        rootView.findViewById(R.id.user_decision_education_edit_btn).setOnClickListener(this);
        rootView.findViewById(R.id.user_decision_preparing_edit_btn).setOnClickListener(this);
        rootView.findViewById(R.id.user_decision_exam_edit_btn).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity)
                this.mListener = (OnUserDecisionListener)context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnExamsSelectListener");
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

        if (mainActivity != null) {
            mainActivity.currentFragment = this;
        }

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
        switch(view.getId())
        {
            case R.id.user_decision_to_recommended:
                mTakeMeToRecommended();
                break;
            case R.id.user_decision_to_dash_board:
                mTakeMeToDashBoard();
                break;
            case R.id.user_decision_to_profile:
                mTakeMeToProfile();
                break;
            case R.id.user_decision_education_edit_btn:
                getActivity().onBackPressed();
                getActivity().onBackPressed();
                getActivity().onBackPressed();
                break;
            case R.id.user_decision_preparing_edit_btn:
                getActivity().onBackPressed();
                getActivity().onBackPressed();
                break;
            case R.id.user_decision_exam_edit_btn:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    private void mTakeMeToRecommended(){
        if(mListener == null)
            return;
        mListener.OnTakeMeToRecommended();
    }

    private void mTakeMeToDashBoard(){
        if(mListener == null)
            return;
        mListener.OnTakeMeToDashBoard();
    }

    private void mTakeMeToProfile(){
        if(mListener == null)
            return;
        mListener.OnTakeMeToProfile();
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

    public  interface OnUserDecisionListener {
        void OnTakeMeToRecommended();
        void OnTakeMeToDashBoard();
        void OnTakeMeToProfile();

    }
}
