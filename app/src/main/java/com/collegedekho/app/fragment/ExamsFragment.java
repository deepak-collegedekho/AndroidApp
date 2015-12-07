package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class ExamsFragment extends BaseFragment {

    private final String TAG = "Exam Frgament";
    private static String PARAM1 = "param1";

    private ArrayList<Exam> mExamList ;
    private OnExamsSelectListener mListener;

    public ExamsFragment() {
        // required empty Constructor
    }

    public static ExamsFragment newInstance(ArrayList<Exam> examsList){
        ExamsFragment fragment = new ExamsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM1,examsList);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null)
        {
            this.mExamList = args.getParcelableArrayList(PARAM1);
        }
      /*  // for demo
        this.mExamList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Exam obj = new Exam();
            obj.setExam("EXAM "+i);
            ArrayList<ExamDetail> detailList = new ArrayList<ExamDetail>();
            for (int j = 0; j <4 ; j++) {
                ExamDetail detailObj = new ExamDetail();
                detailObj.setYear("2015+j");
                detailObj.setExam_date("2015-06-14T00:00:00");
                if(i%2 ==0)detailObj.setResult_out(true);
                detailList.add(detailObj);
            }
            obj.setExam_details(detailList);
            if(i%2 ==0)obj.setSelected(true);
            this.mExamList.add(obj);
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_exams, container, false);
        ((TextView)rootView.findViewById(R.id.points_test_view)).setText("YOU  HAVE EARNED FOR SHARING YOUR DETAIL");
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.exams_recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(5, 10, true));
        recyclerView.setHasFixedSize(true);

        ExamsAdapter mAdapter = new ExamsAdapter(getActivity(), this.mExamList);
        recyclerView.setAdapter(mAdapter);
        rootView.findViewById(R.id.exams_submit_button).setOnClickListener(this);

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
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId())
        {
            case R.id.exams_submit_button:
                onExamsSelected();
                break;
            default:
                break;
        }
    }

    private void onExamsSelected() {
        if(this.mListener != null) {

            HashMap<String, String> params = new HashMap<>();
            if(mExamList != null && !mExamList.isEmpty()) {
                for (Exam exam:mExamList) {
                    if(exam == null)continue;
                   ArrayList<ExamDetail> detailList = exam.getExam_details();
                    if(detailList != null && !detailList.isEmpty()) {
                        for (ExamDetail examDetailObj:detailList) {

                        }
                        }

                }
            }
            this.mListener.onExamsSelected(params);
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
    public  interface OnExamsSelectListener {
        void onExamsSelected(HashMap<String, String> params);
    }

}