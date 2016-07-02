package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamDetailAdapter;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.listener.OnSwipeTouchListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;

import org.apache.tools.ant.Main;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 27/11/15.
 */
public class HomeFragment extends BaseFragment {

    private final String TAG = "profile Frgament";
    private static String PARAM1 = "param1";
    private ArrayList<ExamDetail> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private TextView mProfileName;
    private CircularImageView mProfileImage;
    private OnTabSelectListener mListener;
    private ExamDetail mExamDetail; // detail is needs in tabs to get id of exams
    private ExamSummary mExamSummary;  // exam summary gives info about the colleges of user
    private boolean IS_TUTE_COMPLETED = true;
    private int i = 0;

    public static HomeFragment newInstance(ArrayList<ExamDetail> examList) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM1, examList);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.mExamDetailList = args.getParcelableArrayList(PARAM1);
        }

        Utils.RegisterBroadcastReceiver(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PROFILE_SCREEN_TUTE), false);

        mProfileName  = (TextView)rootView.findViewById(R.id.user_name);
        mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        rootView.findViewById(R.id.profile_guide_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i ==0){
                    i++;
                    rootView.findViewById(R.id.profile_guide_image).setBackgroundResource(R.drawable.ic_profile_tute2);
                }else if(i ==1) {
                    i++;
                    rootView.findViewById(R.id.profile_guide_image).setBackgroundResource(R.drawable.ic_profile_tute3);
                }
                else if(i ==2) {
                    i++;
                    v.setBackgroundResource(R.drawable.ic_profile_tute4);
                }
                else if(i ==3) {
                    i++;
                    v.setBackgroundResource(R.drawable.ic_profile_tute5);
                }else {
                    v.setVisibility(View.GONE);
                    IS_TUTE_COMPLETED = true;
//                    View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
//                    bottomMenu.animate().translationY(0);
//                    bottomMenu.setVisibility(View.VISIBLE);
                    getActivity().invalidateOptionsMenu();
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.PROFILE_SCREEN_TUTE), true).apply();
                }
            }
        });

        rootView.findViewById(R.id.college_list_layout_RL).setOnClickListener(((MainActivity) getActivity()).mClickListener);
        rootView.findViewById(R.id.connect_layout_RL).setOnClickListener(((MainActivity) getActivity()).mClickListener);
        rootView.findViewById(R.id.prepare_layout_RL).setOnClickListener(((MainActivity) getActivity()).mClickListener);
        rootView.findViewById(R.id.updates_layout_RL).setOnClickListener(((MainActivity) getActivity()).mClickListener);
        rootView.findViewById(R.id.profile_image).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Constants.READY_TO_CLOSE = true;
//        View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
//        bottomMenu.animate().translationY(bottomMenu.getHeight());
//        bottomMenu.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        Utils.UnregisterReceiver(this.getActivity());
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getActivity().invalidateOptionsMenu();
        } catch (Exception e) {

        }
        View view = getView();
        if (view != null) {
            IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PROFILE_SCREEN_TUTE), false);
            View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
            if (!IS_TUTE_COMPLETED) {

                bottomMenu.animate().translationY(bottomMenu.getHeight());
                bottomMenu.setVisibility(View.GONE);

                view.findViewById(R.id.profile_guide_image).setVisibility(View.VISIBLE);
            } else {
//                bottomMenu.animate().translationY(0);
//                bottomMenu.setVisibility(View.VISIBLE);

                view.findViewById(R.id.profile_guide_image).setVisibility(View.GONE);
            }
        }

        Constants.READY_TO_CLOSE = false;

        if (MainActivity.mProfile != null) {
            String name = MainActivity.mProfile.getName();
            if (name == null || name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())) {
                mProfileName.setText("");
                mProfileName.setVisibility(View.GONE);
            } else {
               // String userName = name.substring(0, 1).toUpperCase() + name.substring(1);
                mProfileName.setText(name);
                mProfileName.setVisibility(View.VISIBLE);
            }
            String image = MainActivity.mProfile.getImage();
            if (image != null && !image.isEmpty())
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());

            // ************************Removed from fragment
//            String streamName = MainActivity.mProfile.getCurrent_stream_name();

        } else if (MainActivity.user != null) {

            String name = MainActivity.user.getName();
            if (name != null && name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())) {
                mProfileName.setText("");
                mProfileName.setVisibility(View.GONE);
            } else {
                mProfileName.setText(name);
                mProfileName.setVisibility(View.VISIBLE);
            }

            String image = MainActivity.user.getImage();
            if (image != null && !image.isEmpty())
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());

//            String streamName = MainActivity.user.getStream_name();
//>>>>>>> Stashed changes
//            if(streamName != null && !streamName.isEmpty()){
//                mStreamName.setVisibility(View.VISIBLE);
//                mStreamName.setText(streamName);
//            }
//            else{
//                mStreamName.setVisibility(View.GONE);
//            }
//<<<<<<< Updated upstream
//=======
//
//>>>>>>> Stashed changes
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.currentFragment = this;
            mainActivity.mUpdateTabMenuItem(-1, 0);
        }
    }

//        updateExamSummaryHandler.postDelayed(updateExamSummaryRunnable,300);
//        if(((MainActivity)getActivity()).isReloadProfile && this.mListener!=null){
//            ((MainActivity)getActivity()).isReloadProfile=false;
//            this.mExamDetailList=MainActivity.user.getUser_exams();
//          updateUserProfile(this.mExamDetailList);
//        }
//    }

//<<<<<<< Updated upstream
//    public void updateUserProfile(ArrayList<ExamDetail> userExamsList){
//        this.mExamDetailList=userExamsList;
//        View rootView=getView();
//        if(rootView==null){
//            return;
//        }
//        if(this.mExamDetailList != null && this.mExamDetailList.size() > 0) {
//            rootView.findViewById(R.id.profile_syllabus_statusLL).setVisibility(View.VISIBLE);
//            rootView.findViewById(R.id.important_date_layout_RL).setVisibility(View.VISIBLE);
//            this.mExamTabPager.setVisibility(View.VISIBLE);
//            this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
//            this.mExamTabPager.setAdapter(this.mDetailsAdapter);
//            rootView.findViewById(R.id.check_gesture).setOnTouchListener(onSwipeTouchListener);
//            rootView.findViewById(R.id.include_layout_profile_widget).setOnTouchListener(onSwipeTouchListener);
//            rootView.findViewById(R.id.pager_strip).setVisibility(View.VISIBLE);
//
//        }else{
//=======
            public void updateUserProfile(ArrayList<ExamDetail> userExamsList){
                this.mExamDetailList=userExamsList;
                View rootView=getView();
                if(rootView==null){
                    return;
                }
//>>>>>>> Stashed changes

        }

    public void updateUserName(){
        if(MainActivity.mProfile != null){
            String name = MainActivity.mProfile.getName();
            if(name!=null && name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase()))
            {
                mProfileName.setText("");
                mProfileName.setVisibility(View.GONE);
            }else {
                mProfileName.setText(name);
                mProfileName.setVisibility(View.VISIBLE);
            }

            String image = MainActivity.mProfile.getImage();
            if (image != null && ! image.isEmpty())
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());

        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity)
                this.mListener = (OnTabSelectListener) context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnTabSelectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
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

        switch (view.getId())
        {
            case R.id.profile_image:
                ((MainActivity) getActivity()).displayProfileFrragment();
                break;
            default:
                break;
        }
    }


    @Override
    public void updateExamSummary(ExamSummary examSummary) {
        this.mExamSummary = examSummary;

        View view = getView();

        if(view == null || this.mExamSummary == null)
            return;
        CircularProgressBar profileCompleted =  (CircularProgressBar) view.findViewById(R.id.profile_image_circular_progressbar);

        //TODO:: showing progress as a profile circle
        //if(this.mExamSummary.getSyllabus_covered() ==0)
         profileCompleted.setProgress(100);
        //else
         //profileCompleted.setProgress(this.mExamSummary.getSyllabus_covered());
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
    public interface OnTabSelectListener {
        void onExamTabSelected(ExamDetail tabPosition);
        void onHomeItemSelected(String requestType, String url, String examTag);
    }

}
