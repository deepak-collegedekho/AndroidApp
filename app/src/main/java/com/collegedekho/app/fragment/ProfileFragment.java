package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamDetailAdapter;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.listener.OnSwipeTouchListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.CircleImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 27/11/15.
 */
public class ProfileFragment extends  BaseFragment
        implements ExamDetailFragment.OnProfileListener{

    private final String TAG = "profile Frgament";
    private static String PARAM1 = "param1";

    private OnTabSelectListener mListener;
    private ArrayList<ExamDetail> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private ExamDetail mExamDetail; // detail is needs in tabs to get id of exams
    private ExamSummary mExamSummary;  // exam summary gives info about the colleges of user



    public static ProfileFragment newInstance(ArrayList<ExamDetail> examList) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM1, examList);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.mExamDetailList = args.getParcelableArrayList(PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView mProfileName    =   (TextView)rootView.findViewById(R.id.user_name);
        CircleImageView mProfileImage = (CircleImageView)rootView.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        if(MainActivity.user != null)
        {
            String name = MainActivity.user.getName();
            if(name.contains("Anonymous User"))
            {
                if(MainActivity.user.profileData[0] != null)
                {
                    mProfileName.setText(MainActivity.user.profileData[0]);
                    mProfileName.setVisibility(View.VISIBLE);
                }else {
                    mProfileName.setText("");
                    mProfileName.setVisibility(View.GONE);
                }
            }else {
                mProfileName.setText(name);
                mProfileName.setVisibility(View.VISIBLE);
            }
            String image = MainActivity.user.getImage();
            if (image != null && ! image.isEmpty())
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());

        }
        if(this.mExamDetailList != null || !this.mExamDetailList.isEmpty()) {
            final ViewPager examPager = (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
            this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this, this.mExamDetailList);
            examPager.setAdapter(this.mDetailsAdapter);

            examPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.e("","");
                }

                @Override
                public void onPageSelected(int position) {
                    onExamTabSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.e("","");
                }
            });

            OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(getActivity()) {
                @Override
                public void onSwipeLeft() {
                    int currentPosition = examPager.getCurrentItem();
                    if (mExamDetailList.size()-1 >= currentPosition)
                        examPager.setCurrentItem(currentPosition + 1);
                }

                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();

                    int currentPosition = examPager.getCurrentItem();
                    if (currentPosition > 0)
                        examPager.setCurrentItem(currentPosition - 1);
                }

               /* @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction()==MotionEvent.ACTION_DOWN) {
                        switch (v.getId()) {

                            case R.id.backup_colleges_layout_RL:
                            case R.id.wishList_colleges_layout_RL:
                            case R.id.recommended_colleges_layout_RL: {
                                mHomeItemSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes");
                                break;
                            }
                            case R.id.important_date_layout_RL:
                                Toast.makeText(getActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                    return true;
                }*/

            };

//           rootView.findViewById(R.id.backup_colleges_layout_RL).setOnTouchListener(onSwipeTouchListener);
//            rootView.findViewById(R.id.wishList_colleges_layout_RL).setOnTouchListener(onSwipeTouchListener);
//            rootView.findViewById(R.id.recommended_colleges_layout_RL).setOnTouchListener(onSwipeTouchListener);
//            rootView.findViewById(R.id.important_date_layout_RL).setOnTouchListener(onSwipeTouchListener);
            rootView.findViewById(R.id.check_gesture).setOnTouchListener(onSwipeTouchListener);
            rootView.findViewById(R.id.include_layout_home_widget).setOnTouchListener(onSwipeTouchListener);


            int currentPosition = examPager.getCurrentItem();
            onExamTabSelected(currentPosition);

        }


        rootView.findViewById(R.id.prep_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.resources_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.future_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.my_alerts).setOnClickListener(this);
        rootView.findViewById(R.id.backup_colleges_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.wishList_colleges_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.recommended_colleges_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.important_date_layout_RL).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Constants.READY_TO_CLOSE = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Constants.READY_TO_CLOSE = false;

        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity != null) {
            mainActivity.currentFragment = this;
            mainActivity.mShouldDisplayHomeUp();
            mainActivity.mUpdateNavigationMenuItem(0);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId())
        {
            case R.id.prep_buddies:;
                this.onTabMenuSelected(0);
                break;
            case R.id.resources_buddies:
                this.onTabMenuSelected(1);
                break;
            case R.id.future_buddies:
                this.onTabMenuSelected(2);
                break;
            case R.id.my_alerts:
                //this.onTabMenuSelected(3);
                Toast.makeText(getActivity().getApplicationContext(), "Coming soon..", Toast.LENGTH_LONG).show();
                return;
           case R.id.backup_colleges_layout_RL:
            case R.id.wishList_colleges_layout_RL:
            case R.id.recommended_colleges_layout_RL: {
                mHomeItemSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes");
                break;
            }
            case R.id.important_date_layout_RL:
                Toast.makeText(getActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    private void onTabMenuSelected(int tabPosition) {
        if(this.mListener != null)
                      this.mListener.onTabMenuSelected(tabPosition);

    }


    private void onExamTabSelected(int position) {

        if(this.mListener == null || this.mExamDetailList == null)
            return;

        ExamDetail examDetailObj = this.mExamDetailList.get(position);
        this.mListener.onExamTabSelected(examDetailObj);

    }


    public void updateExamSummary(ExamSummary examSummary) {
        this.mExamSummary = examSummary;

        View view = getView();
        if(view == null || this.mExamSummary == null)
            return;

        TextView backup_countTV = (TextView)view.findViewById(R.id.backup_colleges_count);
        TextView wishList_countTV =  (TextView)view.findViewById(R.id.wishList_colleges_count);
        TextView recommended_countTV =  (TextView)view.findViewById(R.id.recommended_colleges_count);
        TextView important_dateTV =  (TextView)view.findViewById(R.id.important_dates_count);
        TextView covered_syllabus =  (TextView)view.findViewById(R.id.covered_syllabus);

        backup_countTV.setText(this.mExamSummary.getBackup_count()+"/20");
        wishList_countTV.setText(this.mExamSummary.getShortlist_count()+"/20");
        recommended_countTV.setText(this.mExamSummary.getRecommended_count()+"/10");
        important_dateTV.setText(this.mExamSummary.getNext_important_date()+"/20");
        covered_syllabus.setText(""+this.mExamSummary.getSyllabus_covered()+"%");

    }

    @Override
    public void updateExamDetail(ExamSummary examSummary) {

    }

    private void mHomeItemSelected(String requestType, String url)
    {
        if(mListener != null)
            mListener.onHomeItemSelected(requestType, url);
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
    public  interface OnTabSelectListener {

        void onTabMenuSelected(int tabPosition);
        void onExamTabSelected(ExamDetail tabPosition);

        void onHomeItemSelected(String requestType, String url);
    }

}
