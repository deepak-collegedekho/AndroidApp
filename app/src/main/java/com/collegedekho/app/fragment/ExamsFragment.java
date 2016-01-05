package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class ExamsFragment extends BaseFragment {

    private final String TAG = "ExamsFragment";
    private static String PARAM1 = "param1";

    private ArrayList<Exam> mExamList ;
    private OnExamsSelectListener mListener;
    private boolean IS_TUTE_COMPLETED = true;

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
        IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(Constants.EXAMS_SCREEN_TUTE, false);

        //((TextView)rootView.findViewById(R.id.points_test_view)).setText("YOU HAVE EARNED FOR SHARING YOUR DETAIL");
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.exams_recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),6,GridLayoutManager.VERTICAL,false);
        layoutManager.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 5 == 0 ? 3 : 2;
            }
        });
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        /*layoutManager.setSpanCount(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                return position % 2 == 0 ? 2 : 1;
            }
        });*/

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 8, true));
        //recyclerView.setHasFixedSize(true);

        ExamsAdapter mAdapter = new ExamsAdapter(getActivity(), this.mExamList);
        recyclerView.setAdapter(mAdapter);
        rootView.findViewById(R.id.exams_submit_button).setOnClickListener(this);

//        Animation pulse = AnimationUtils.loadAnimation(getActivity(), R.anim.pulse);
//        rootView.findViewById(R.id.exams_submit_button).startAnimation(pulse);zr

        rootView.findViewById(R.id.exam_tour_guide_image).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setVisibility(View.GONE);
                IS_TUTE_COMPLETED = true;
                getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(Constants.EXAMS_SCREEN_TUTE, true).apply();
                return false;            }
        });


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


        View view =  getView();
        if(view != null ){
            if(!IS_TUTE_COMPLETED) {
                view.findViewById(R.id.exam_tour_guide_image).setVisibility(View.VISIBLE);
            }else {
                view.findViewById(R.id.exam_tour_guide_image).setVisibility(View.GONE);
            }
        }

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
        boolean isExamSelected = false;
        if(this.mListener != null) {
            JSONObject parentJsonObject=new JSONObject();
            JSONArray parentArray=new JSONArray();
            if(mExamList != null && !mExamList.isEmpty()) {
                for (Exam exam:mExamList) {
                    if(exam == null)continue;
                    if(!exam.isSelected())continue;
                    ArrayList<ExamDetail> detailList = exam.getExam_details();
                    if(detailList != null && !detailList.isEmpty()) {
                        for (ExamDetail examDetailObj:detailList) {
                            if(!examDetailObj.isSelected())continue;
                            JSONObject examHash = new JSONObject();
                            try {

                                examHash.putOpt(Constants.EXAM_ID,examDetailObj.getId());
                                examHash.putOpt(Constants.MARKS,examDetailObj.getExam_marks());
                                parentArray.put(examHash);
                               isExamSelected = true;

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
            if(!isExamSelected){
                Utils.DisplayToast(getActivity(),"Please Select a Exam");
                return;
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
