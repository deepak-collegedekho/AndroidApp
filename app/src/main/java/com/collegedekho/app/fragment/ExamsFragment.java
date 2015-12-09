package com.collegedekho.app.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class ExamsFragment extends BaseFragment {

    private final String TAG = "ExamsFragment";
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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_exams, container, false);
        //((TextView)rootView.findViewById(R.id.points_test_view)).setText("YOU HAVE EARNED FOR SHARING YOUR DETAIL");
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.exams_recycle_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        /*layoutManager.setSpanCount(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                return position % 2 == 0 ? 2 : 1;
            }
        });*/

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        //recyclerView.setHasFixedSize(true);

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
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();

        if (mainActivity != null)
            mainActivity.currentFragment = this;

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
            JSONObject parentJsonObject=new JSONObject();
            JSONArray parentArray=new JSONArray();
            if(mExamList != null && !mExamList.isEmpty()) {
                for (Exam exam:mExamList) {
                    if(exam == null)continue;
                        ArrayList<ExamDetail> detailList = exam.getExam_details();
                            if(detailList != null && !detailList.isEmpty()) {
                                for (ExamDetail examDetailObj:detailList) {
                                    JSONObject examHash = new JSONObject();
                                    try {
                                        examHash.putOpt(Constants.EXAM_ID,examDetailObj.getId());
                                        examHash.putOpt(Constants.MARKS,examDetailObj.getExam_marks());
                                        parentArray.put(examHash);

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }
                            }
                }
            }
            try {
                parentJsonObject.put(Constants.RESULTS,parentArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.mListener.onExamsSelected(parentJsonObject);
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
        void onExamsSelected(JSONObject params);
    }

}
