package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.listener.ExamOnQueryListener;
import com.collegedekho.app.listener.ExamSearchCloseListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class UserExamFragment extends BaseFragment implements SearchView.OnQueryTextListener{

    private final String TAG = "User Exam Fragment";

    SearchView mExamSearchView;
    SearchView mStreamSearchView;

    ExamOnQueryListener cExamListener;
    //StreamOnQueryListener cStreamListener;
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
            TextView  currentLevelTxtView = (TextView)rootView.findViewById(R.id.user_exam_education_level);
            int currentEducationId = MainActivity.mProfile.getCurrent_level_id();
            if(currentEducationId == ProfileMacro.LEVEL_TWELFTH){
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

            String phone = MainActivity.mProfile.getPhone_no();
            if(phone != null && !phone.isEmpty())
                ((TextView)rootView.findViewById(R.id.user_exam_phone)).setText(":  +91-"+phone);
            else
                ((TextView)rootView.findViewById(R.id.user_exam_phone)).setText(":  NA");
        }

        RecyclerView examRecyclerView = (RecyclerView)rootView.findViewById(R.id.user_exam_recycler_view);
        examRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        examRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));
        if(mExamAdapter == null)
            mExamAdapter = new ExamsAdapter(getActivity(), new ArrayList<Exam>());
        examRecyclerView.setAdapter(mExamAdapter);

        mExamSearchView = (SearchView) rootView.findViewById(R.id.auto_search_exam);
        cExamListener = new ExamOnQueryListener(mExamList,this);
        this.mExamSearchView.setOnQueryTextListener(cExamListener);
        mExamSearchView.setOnSearchClickListener(this);
        mExamSearchView.setOnCloseListener(new ExamSearchCloseListener(rootView.findViewById(R.id.search_exam_hint)));

        if(mExamList != null && mExamList.size() >0){
            rootView.findViewById(R.id.user_exam_recycler_view_text).setVisibility(View.GONE);
            rootView.findViewById(R.id.user_exam_recycler_view).setVisibility(View.VISIBLE);
        }

      /*  RecyclerView streamRecyclerView = (RecyclerView)rootView.findViewById(R.id.user_exam_stream_recycler_view);
        streamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        streamRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));


        int currentSubLevelId = MainActivity.mProfile.getPreferred_level();
        List<ProfileSpinnerItem> mStreamList = null;
        try {
            mStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class,
                    ProfileMacro.getStreamJson(currentSubLevelId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExamStreamAdapter mStreamAdapter = new ExamStreamAdapter(getActivity(), (ArrayList<ProfileSpinnerItem>)mStreamList,mExamSearchView);
        streamRecyclerView.setAdapter(mStreamAdapter);

        mStreamSearchView = (SearchView) rootView.findViewById(R.id.auto_search_stream);
        //cStreamListener = new StreamOnQueryListener((ArrayList<ProfileSpinnerItem>) mStreamList,mStreamAdapter);
        //this.mStreamSearchView.setOnQueryTextListener(cStreamListener);
        mStreamSearchView.setOnSearchClickListener(this);
        mStreamSearchView.setOnCloseListener(new StreamSearchCloseListener(rootView.findViewById(R.id.search_stream_hint)));
        mStreamAdapter.setmStreamSearchView(mStreamSearchView);*/

        rootView.findViewById(R.id.user_exam_submit_button).setOnClickListener(this);

        rootView.findViewById(R.id.user_exam_education_edit_btn).setOnClickListener(this);
        rootView.findViewById(R.id.user_exam_preparing_edit_btn).setOnClickListener(this);

        return rootView;
    }

    /**
     *
     * @param searchResults
     */
    public void updateExamList(ArrayList<Exam> searchResults) {
        if(searchResults != null && searchResults.size() >0){
            getView().findViewById(R.id.empty).setVisibility(View.GONE);
            getView().findViewById(R.id.user_education_recycler_view).setVisibility(View.VISIBLE);
        }else{
            getView().findViewById(R.id.empty).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
        }

        if(mExamAdapter != null){
            mExamAdapter.updateExamList(searchResults);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try{

            ArrayList<Exam> searchResults = new ArrayList<>();
            for(Exam exam : mExamList){
                if((exam.getExam_name().toLowerCase()).contains((newText.toString().toLowerCase())) || (exam.getExam_short_name().toLowerCase()).contains((newText.toString().toLowerCase()))){
                    searchResults.add(exam);
                }
            }
//            if(searchResults.size() != 0){
            mExamAdapter.updateExamList(searchResults);
//            } else {
//
//            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
            case R.id.user_exam_education_edit_btn:
                getActivity().onBackPressed();
                getActivity().onBackPressed();
                break;
            case R.id.user_exam_preparing_edit_btn:
                getActivity().onBackPressed();
                break;
            case R.id.auto_search_stream:
//                super.onClick(view);
                if(!view.isActivated()){
                    getView().findViewById(R.id.search_stream_hint).setVisibility(View.GONE);
                } else {
                    getView().findViewById(R.id.search_stream_hint).setVisibility(View.VISIBLE);
                }
                break;
            case R.id.auto_search_exam:
                if(!view.isActivated()){
                    getView().findViewById(R.id.search_exam_hint).setVisibility(View.GONE);
                } else {
                    getView().findViewById(R.id.search_exam_hint).setVisibility(View.VISIBLE);
                }
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
        cExamListener.setExamList(examList);
        if(mExamAdapter == null)
            return;
        mExamAdapter.updateExamList(examList);
        if(examList != null && examList.size() >0
                &&getView() != null){

            getView().findViewById(R.id.user_exam_recycler_view_text).setVisibility(View.GONE);
            getView().findViewById(R.id.user_exam_recycler_view).setVisibility(View.VISIBLE);
            mExamSearchView.setVisibility(View.VISIBLE);
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
