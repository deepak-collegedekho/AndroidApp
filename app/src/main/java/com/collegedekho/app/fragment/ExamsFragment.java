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

import com.android.volley.Request;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.listener.ExamFragmentListener;
import com.collegedekho.app.listener.ExamOnQueryListener;
import com.collegedekho.app.listener.ExamSearchCloseListener;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class ExamsFragment extends BaseFragment implements ExamFragmentListener{

    private final String TAG = "ExamsFragment";
    private static String PARAM1 = "param1";
    private static String PARAM2 = "param2";
;
    private OnExamsSelectListener mListener;
    private boolean IS_TUTE_COMPLETED = true;
    private TextView mExamSubmitButton;
    private boolean isEditMode=false;
    private boolean isPreSelected=false;

    private ExamsAdapter mExamAdapter;
    private SearchView mExamSearchView;
    private ExamOnQueryListener cExamQueryListener;
    private ArrayList<Exam> mExamList = new ArrayList<>();
    public ExamsFragment() {
        // required empty Constructor
    }

    public static ExamsFragment newInstance(ArrayList<Exam> examsList, boolean isEditable){
        ExamsFragment fragment = new ExamsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM1,examsList);
        args.putBoolean("is_edit_mode",isEditable);
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
            this.isEditMode=args.getBoolean("is_edit_mode");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_exams, container, false);
        this.mExamSubmitButton = (TextView) rootView.findViewById(R.id.exams_submit_button);

       RecyclerView mStreamRecyclerView = (RecyclerView)rootView.findViewById(R.id.exams_recycle_view);
        mStreamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //mStreamRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));


        mExamSearchView = (SearchView) rootView.findViewById(R.id.user_exam_search_view);

        mExamAdapter = new ExamsAdapter(getActivity(),  mExamList);
        mStreamRecyclerView.setAdapter(mExamAdapter);

        cExamQueryListener = new ExamOnQueryListener(mExamList,this, null);
        this.mExamSearchView.setOnQueryTextListener(cExamQueryListener);

        mExamSearchView.setOnCloseListener(new ExamSearchCloseListener(rootView.findViewById(R.id.user_exam_search_hint), null));

        mExamSearchView.setOnSearchClickListener(this);
        mExamSubmitButton.setOnClickListener(this);
        rootView.findViewById(R.id.user_exam_search_container).setOnClickListener(this);
        // IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.EXAMS_SCREEN_TUTE), false);

        /*rootView.findViewById(R.id.exam_tour_guide_image).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setVisibility(View.GONE);
                IS_TUTE_COMPLETED = true;
                getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.EXAMS_SCREEN_TUTE), true).apply();

                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                mExamSubmitButton.animate().translationY(0).setDuration(Constants.ANIM_LONG_DURATION);
                mExamSubmitButton.setVisibility(View.VISIBLE);

                return false;
            }
        });*/

       /* if (IS_TUTE_COMPLETED)
        {
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mExamSubmitButton.animate().translationY(0).setDuration(Constants.ANIM_LONG_DURATION);
            mExamSubmitButton.setVisibility(View.VISIBLE);
        }*/

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
    public void onDestroy(){
        super.onDestroy();
        mainActivity.fromTabFragment=false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
    MainActivity mainActivity;
    @Override
    public void onResume() {
        super.onResume();

        mainActivity = (MainActivity) getActivity();

        if (mainActivity != null) {
            mainActivity.currentFragment = this;
           /* if(isEditMode){
                if(mainActivity.fromTabFragment){
                    mainActivity.isBackPressEnabled=true;
                }else {
                    mainActivity.isBackPressEnabled = false;
                }
                if(mExamList != null && !mExamList.isEmpty()) {
                    for (Exam exam:mExamList) {
                        if(exam == null)continue;
                        if(!exam.isSelected()){
                            ArrayList<ExamDetail> detailList = exam.getExam_details();
                            if(detailList != null && !detailList.isEmpty()) {
                                for (ExamDetail examDetailObj:detailList) {
                                    if (examDetailObj.is_preparing()){
                                        isPreSelected=true;
                                        mainActivity.isBackPressEnabled=true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (isPreSelected){
                            break;
                        }
                    }
                }
            }
            if(!isPreSelected){
                try {
                    MainActivity.user.setExams_set(0);
                    String u = JSON.std.asString(MainActivity.user);
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(Constants.KEY_USER, u).commit();
                }catch (Exception e){

                }

            }*/
        }
        /*View view =  getView();
        if(view != null ){
            if(!IS_TUTE_COMPLETED) {
                view.findViewById(R.id.exam_tour_guide_image).setVisibility(View.VISIBLE);
            }else {
                view.findViewById(R.id.exam_tour_guide_image).setVisibility(View.GONE);
            }
        }*/

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
                onExamsSelected();
                break;
            case R.id.user_exam_search_view:
            case R.id.user_exam_search_container:
                mExamSearchView.onActionViewExpanded();
                getView().findViewById(R.id.user_exam_search_hint).setVisibility(View.GONE);
               /* if(!mExamSearchView.isActivated()){
                    getView().findViewById(R.id.user_exam_search_hint).setVisibility(View.GONE);
                } else {
                    getView().findViewById(R.id.user_exam_search_hint).setVisibility(View.VISIBLE);
                }*/
                break;
            default:
                break;
        }
    }

    private void onExamsSelected() {
        if(this.mListener == null)
            return;

        boolean isExamSelected = false;
        //JSONObject parentJsonObject=new JSONObject();
       // JSONArray parentArray=new JSONArray();
        //ArrayList<Exam> adapterExamList = mExamAdapter.getExamsList();
        StringBuffer selectedExamsBuffer = new StringBuffer();
        selectedExamsBuffer.append("[");
        int firsttime =0;
        if(mExamList != null && !mExamList.isEmpty()) {
            for (Exam exam:mExamList) {
                if(exam == null || !exam.isSelected())continue;

                ArrayList<ExamDetail> detailList = exam.getExam_details();
                if(detailList == null && detailList.isEmpty()) continue;

                for (ExamDetail examDetailObj:detailList) {
                    if(examDetailObj == null || !examDetailObj.isSelected())continue;
                   // JSONObject examHash = new JSONObject();
                    try {
                        if(firsttime != 0){
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
                        firsttime++;
                       // examHash.putOpt(MainActivity.getResourceString(R.string.EXAM_ID),examDetailObj.getId());
                        //examHash.putOpt(MainActivity.getResourceString(R.string.MARKS),examDetailObj.getScore());
                       // parentArray.put(examHash);
                        isExamSelected = true;
                        if(mainActivity!=null){
                            mainActivity.isBackPressEnabled=true;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        selectedExamsBuffer.append("]");
       /* try {
            parentJsonObject.put(MainActivity.getResourceString(R.string.RESULTS),parentArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
       /* if(!isExamSelected){
            if(!isEditMode) {
                mListener.displayMessage(R.string.SELECT_ONE_EXAM);
                return;
            }
            if(isPreSelected) {
                mListener.onCancelExamSubmission();
            }else {
                mListener.displayMessage(R.string.SELECT_ONE_EXAM);
            }
            return;
        }*/
        /*if(!isEditMode) {
            this.mListener.onExamsSelected(parentJsonObject);
        }else {*/
          //  this.mListener.onExamsEdited(parentJsonObject);
       // }
        HashMap<String, String> userParams = new HashMap<>();
        userParams.put("yearly_exams", selectedExamsBuffer.toString());
        this.mListener.onExamsEdited(userParams);

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
       // void onExamsSelected(JSONObject params);
        void onExamsEdited(HashMap<String, String> params);
        void onCancelExamSubmission();
        void displayMessage(int messageId);
        void requestForProfile(HashMap<String, String> params, int method);
    }
}
