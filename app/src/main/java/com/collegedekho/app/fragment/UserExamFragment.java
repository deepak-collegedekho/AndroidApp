package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamStreamAdapter;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.widget.GridSpacingItemDecoration;
import com.fasterxml.jackson.jr.ob.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserExamFragment extends BaseFragment {

    private final String TAG = "User Exam Fragment";

    private ArrayList<Exam> mExamList ;
    private OnUserExamsSelectListener mListener;
    private ExamsAdapter mExamAdapter;

    public UserExamFragment() {
        // required empty Constructor
    }

    public static UserExamFragment newInstance(){
        UserExamFragment fragment = new UserExamFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_exams_new, container, false);

        if(MainActivity.mProfile != null){
            TextView  currentLevelTxtView = (TextView)rootView.findViewById(R.id.user_exam_preparing_level);
            int currentEducationId = MainActivity.mProfile.getCurrent_sublevel_id();
            if(currentEducationId == ProfileMacro.CURRENT_EDUCATION_SCHOOL){
                currentLevelTxtView.setText(":  School");
            }else{
                currentLevelTxtView.setText(":  College");
            }
            TextView  preParingTxtView = (TextView)rootView.findViewById(R.id.user_exam_is_preparing);
            int isPreparingId = MainActivity.mProfile.getIs_preparing();
            if(isPreparingId == ProfileMacro.PREPARING_FOR_EXAM){
                preParingTxtView.setText(":  Yes");
            }else{
                //TODO:: check this bug
                preParingTxtView.setText(":  Yes");
            }
        }

        RecyclerView examRecyclerView = (RecyclerView)rootView.findViewById(R.id.user_exam_recycler_view);
        examRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        examRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));
        if(mExamAdapter == null)
            mExamAdapter = new ExamsAdapter(getActivity(), new ArrayList<Exam>());
        examRecyclerView.setAdapter(mExamAdapter);

        RecyclerView streamRecyclerView = (RecyclerView)rootView.findViewById(R.id.user_exam_stream_recycler_view);
        streamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        streamRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));

        if(mExamList != null && mExamList.size() >0){
            rootView.findViewById(R.id.user_exam_recycler_view_text).setVisibility(View.GONE);
            rootView.findViewById(R.id.user_exam_recycler_view).setVisibility(View.VISIBLE);
        }

        int currentSubLevelId = MainActivity.mProfile.getPreferred_level();
        List<ProfileSpinnerItem> streamList = null;
        try {
            streamList = JSON.std.listOfFrom(ProfileSpinnerItem.class,
                    ProfileMacro.getStreamJson(currentSubLevelId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExamStreamAdapter mStreamAdapter = new ExamStreamAdapter(getActivity(), (ArrayList<ProfileSpinnerItem>)streamList);
        streamRecyclerView.setAdapter(mStreamAdapter);

        rootView.findViewById(R.id.user_exam_submit_button).setOnClickListener(this);
        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            try{
                if (context instanceof MainActivity)
                    this.mListener = (OnUserExamsSelectListener)context;
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
            case R.id.user_exam_submit_button:
                onExamsSelected();
                break;
            default:
                break;
        }
    }

    private void onExamsSelected() {
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        if(this.mListener == null)
            return;

        boolean isExamSelected = false;
        JSONObject parentJsonObject=new JSONObject();
        JSONArray parentArray=new JSONArray();
        if(mExamList != null && !mExamList.isEmpty()) {
            for (Exam exam:mExamList) {
                if(exam == null || !exam.isSelected())continue;

                ArrayList<ExamDetail> detailList = exam.getExam_details();
                if(detailList == null && detailList.isEmpty()) continue;

                for (ExamDetail examDetailObj:detailList) {
                    if(examDetailObj == null || !examDetailObj.isSelected())continue;
                    JSONObject examHash = new JSONObject();
                    try {
                        examHash.putOpt(MainActivity.getResourceString(R.string.EXAM_ID),examDetailObj.getId());
                        examHash.putOpt(MainActivity.getResourceString(R.string.SCORE),examDetailObj.getScore());
                        examHash.putOpt(MainActivity.getResourceString(R.string.STATUS),examDetailObj.getStatus());
                        parentArray.put(examHash);
                        isExamSelected = true;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            parentJsonObject.put(MainActivity.getResourceString(R.string.RESULTS),parentArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(!isExamSelected){
            mListener.displayMessage(R.string.SELECT_ONE_EXAM);
            return;
        }
        this.mListener.onUserExamSelected(parentJsonObject);


    }

    public void updateUserExams(ArrayList<Exam> examList){
        this.mExamList = examList;
        if(mExamAdapter == null)
            return;
        mExamAdapter.updateExamList(examList);
        if(examList != null && examList.size() >0
                &&getView() != null){
            getView().findViewById(R.id.user_exam_recycler_view_text).setVisibility(View.GONE);
            getView().findViewById(R.id.user_exam_recycler_view).setVisibility(View.VISIBLE);
        }else{
            ((TextView)getView().findViewById(R.id.user_exam_recycler_view_text)).setText("No Exams Found");
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

    public  interface OnUserExamsSelectListener {
        void onUserExamSelected(JSONObject examJson);
        void displayMessage(int messageId);
        void onRequestForExams(ArrayList<ProfileSpinnerItem> streamList);
    }


}
