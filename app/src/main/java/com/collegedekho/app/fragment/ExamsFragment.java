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

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.listener.ExamFragmentListener;
import com.collegedekho.app.listener.ExamOnQueryListener;
import com.collegedekho.app.listener.ExamSearchCloseListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class ExamsFragment extends BaseFragment implements ExamFragmentListener{

    private final String TAG = "ExamsFragment";
    private static String PARAM1 = "param1";
;
    private OnExamsSelectListener mListener;
    private ExamsAdapter mExamAdapter;
    private SearchView mExamSearchView;
    private ExamOnQueryListener cExamQueryListener;
    private ArrayList<Exam> mExamList = new ArrayList<>();

    public ExamsFragment() {
        // required empty Constructor
    }

    public static ExamsFragment newInstance(ArrayList<Exam> mExamList){
        ExamsFragment fragment = new ExamsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM1, mExamList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            this.mExamList = args.getParcelableArrayList(PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_exams, container, false);

        RecyclerView mStreamRecyclerView = (RecyclerView)rootView.findViewById(R.id.exams_recycle_view);
        mStreamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mExamAdapter = new ExamsAdapter(getActivity(),  mExamList);
        mStreamRecyclerView.setAdapter(mExamAdapter);

        mExamSearchView = (SearchView) rootView.findViewById(R.id.user_exam_search_view);
        cExamQueryListener = new ExamOnQueryListener(mExamList,this, null);
        this.mExamSearchView.setOnQueryTextListener(cExamQueryListener);
        mExamSearchView.setOnCloseListener(new ExamSearchCloseListener(rootView.findViewById(R.id.user_exam_search_hint), null));

        mExamSearchView.setOnSearchClickListener(this);
        rootView.findViewById(R.id.exams_submit_button).setOnClickListener(this);
        rootView.findViewById(R.id.user_exam_search_container).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity)
                this.mListener = (OnExamsSelectListener)context;
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
        if (mainActivity != null)
            mainActivity.currentFragment = this;
    }

    @Override
    public void updateQueryExamList(ArrayList<Exam> searchResults) {
        if(mExamAdapter != null){
            mExamAdapter.updateExamsList(searchResults);
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
            case R.id.exams_submit_button:
                mUserExamsEdited();
                break;
            case R.id.user_exam_search_view:
            case R.id.user_exam_search_container:
                mExamSearchView.onActionViewExpanded();
                getView().findViewById(R.id.user_exam_search_hint).setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void mUserExamsEdited() {
        if(this.mListener == null)
            return;

        StringBuffer selectedExamsBuffer = new StringBuffer();
        selectedExamsBuffer.append("[");
        int firstTime =0;
        if(mExamList != null && !mExamList.isEmpty()) {
            for (Exam exam:mExamList) {
                if(exam == null || !exam.isSelected())continue;

                ArrayList<ExamDetail> detailList = exam.getExam_details();
                if(detailList == null && detailList.isEmpty()) continue;

                for (ExamDetail examDetailObj:detailList) {
                    if(examDetailObj == null || !examDetailObj.isSelected())continue;
                    try {
                        if(firstTime != 0){
                            selectedExamsBuffer.append(",");
                        }
                        selectedExamsBuffer.append("{\"id\":").append(examDetailObj.getId())
                                .append(",").append("\"score\":").append(examDetailObj.getScore());

                        if(examDetailObj.isResult_out()){

                            String date = examDetailObj.getExam_date();
                            selectedExamsBuffer.append(",").append("\"status\":").append(1);
                        }else{
                            selectedExamsBuffer.append(",").append("\"status\":").append(2);
                        }
                        selectedExamsBuffer.append("}");
                        firstTime++;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        selectedExamsBuffer.append("]");

        HashMap<String, String> userParams = new HashMap<>();
        userParams.put("yearly_exams", selectedExamsBuffer.toString());
        this.mListener.onUserExamsEdited(userParams);

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
    public  interface OnExamsSelectListener {
        void onUserExamsEdited(HashMap<String, String> params);
        void displayMessage(int messageId);
    }
}
