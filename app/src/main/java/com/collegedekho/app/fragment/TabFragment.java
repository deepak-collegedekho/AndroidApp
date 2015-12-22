package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.collegedekho.app.widget.CircularImageView;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 6/12/15.
 */
public class TabFragment extends  BaseFragment
        implements ExamDetailFragment.OnProfileListener{
    private final String TAG ="Tab Fragment";
    private static String PARAM1 = "param1";
    private static String PARAM2 = "param2";

    private int selectedTabMenuPosition =0;
    private int selectedSubMenuPosition =0;
    private  OnHomeItemSelectListener mListener;
    private ArrayList<ExamDetail> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private ExamDetail mExamDetail;
    private boolean isFistTime;

    public static TabFragment newInstance(int tabPosoition,ArrayList<ExamDetail> examList) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM1, tabPosoition);
        args.putParcelableArrayList(PARAM2, examList);
        fragment.setArguments(args);
        return fragment;
    }

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.selectedTabMenuPosition = args.getInt(PARAM1);
            this.mExamDetailList = args.getParcelableArrayList(PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        final ViewPager examPager = (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        TextView mProfileName = (TextView) rootView.findViewById(R.id.user_name);
        CircularImageView mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);

        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);
        if(MainActivity.user != null) {

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
            if (image != null && ! image.isEmpty()) {
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
                mProfileImage.setVisibility(View.VISIBLE);
            }

        }

        if(this.mExamDetailList != null && this.mExamDetailList.size() > 0) {

            examPager.setVisibility(View.VISIBLE);
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

            };

           rootView.findViewById(R.id.exam_swipe_listener_layout).setOnTouchListener(onSwipeTouchListener);
           rootView.findViewById(R.id.include_layout_home_widget).setOnTouchListener(onSwipeTouchListener);

            if(this.isFistTime) {
                this.isFistTime = false;
                int currentPosition = examPager.getCurrentItem();
                onExamTabSelected(currentPosition);
            }
        }
        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_third).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_fourth).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mUpdateSubMenuItem();
        getActivity().findViewById(R.id.bottom_tab_layout).setVisibility(View.VISIBLE);

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().findViewById(R.id.bottom_tab_layout).setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       try{
            if (context instanceof MainActivity)
                this.mListener = (OnHomeItemSelectListener)context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnHomeItemSelectListener");
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
        try{
        this.selectedSubMenuPosition =Integer.parseInt((String)view.getTag());
        }catch (Exception e){
            e.printStackTrace();
        }


        this.mSubMenuItemClickListener();
        this.mUpdateSubMenuItem();
    }

    private void onExamTabSelected(int position) {
        if(this.mListener == null || this.mExamDetailList == null || this.mExamDetailList.isEmpty())
            return;
        this.mExamDetail = this.mExamDetailList.get(position);
    }

    private void mUpdateSubMenuItem(){
        View view = getView();
        if(view ==   null)
            return;

        TextView firstSubMenuTV       = (TextView)view.findViewById(R.id.home_widget_textview_first);
        TextView secondSubMenuTV   = (TextView)view.findViewById(R.id.home_widget_textview_second);
        TextView thirdSubMenuTV    = (TextView)view.findViewById(R.id.home_widget_textview_third);
        TextView fourthSubMenuTV          = (TextView)view.findViewById(R.id.home_widget_textview_fourth);

        ImageView firstSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_first);
        ImageView secondSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_second);
        ImageView thirdSubMenuIV      = (ImageView)view.findViewById(R.id.home_widget_image_third);
        ImageView fourthSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_fourth);


        if(this.selectedTabMenuPosition == 1){
            firstSubMenuIV.setImageResource(R.drawable.ic_test_calendar);
            secondSubMenuIV.setImageResource(R.drawable.ic_syllabus);
            thirdSubMenuIV.setImageResource(R.drawable.ic_challenges);
            fourthSubMenuIV.setImageResource(R.drawable.ic_prep_path);

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
            ll.setLayoutParams(lp);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);

            firstSubMenuTV.setText("Test Calendar");
            secondSubMenuTV.setText("Syllabus");
            thirdSubMenuTV.setText("Challenges");
            fourthSubMenuTV.setText("Prep Path");

        }else   if(this.selectedTabMenuPosition == 2){
            firstSubMenuIV.setImageResource(R.drawable.ic_institute);
            secondSubMenuIV.setImageResource(R.drawable.ic_news);
            thirdSubMenuIV.setImageResource(R.drawable.ic_article);
            fourthSubMenuIV.setImageResource(R.drawable.ic_qna);

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, .5f);
            ll.setLayoutParams(lp);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.VISIBLE);

            firstSubMenuTV.setText("Institutes");
            secondSubMenuTV.setText("News");
            thirdSubMenuTV.setText("Article");
            fourthSubMenuTV.setText("Qna");

        }else   if(this.selectedTabMenuPosition == 3){

        }else   if(this.selectedTabMenuPosition == 4){
        }
    }

    private void mSubMenuItemClickListener(){

        if(selectedTabMenuPosition == 1){

            if(selectedSubMenuPosition == 2)
                if(this.mExamDetail != null)
                this.mHomeItemSelected(Constants.WIDGET_SYLLABUS, Constants.BASE_URL + "yearly-exams/"+mExamDetail.getId()+"/syllabus/",this.mExamDetail.getExam_tag());
            else if(selectedSubMenuPosition == 1)
                    if(this.mExamDetail != null)
                this.mHomeItemSelected(Constants.WIDGET_TEST_CALENDAR, Constants.BASE_URL+"yearly-exams/+"+mExamDetail.getId()+"/calendar/",this.mExamDetail.getExam_tag());
            else
                Toast.makeText(getActivity().getApplicationContext(), "Coming soon..", Toast.LENGTH_LONG).show();

        }
        else if(selectedTabMenuPosition == 2){
             if(selectedSubMenuPosition == 1){
                 if(this.mExamDetail != null)
                 this.mHomeItemSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL+"personalize/institutes",this.mExamDetail.getExam_tag());
                 else
                 this.mHomeItemSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL+"personalize/institutes", null);
             }else  if(selectedSubMenuPosition == 2){
                 this.mHomeItemSelected(Constants.WIDGET_NEWS, Constants.BASE_URL+"personalize/news", null);
             }else  if(selectedSubMenuPosition == 3){
                 this.mHomeItemSelected(Constants.WIDGET_ARTICES, Constants.BASE_URL+"personalize/articles", null);
             }else  if(selectedSubMenuPosition == 4){
                 this.mHomeItemSelected(Constants.TAG_LOAD_QNA_QUESTIONS, Constants.BASE_URL+"personalize/qna", null);
             }
        }

    }

    public void updateTabFragment(int tabPosition){
        this.selectedTabMenuPosition  = tabPosition;
        mUpdateSubMenuItem();
    }

    private void mHomeItemSelected(String requestType, String url, String tag)
    {
      if(mListener != null)
          mListener.onHomeItemSelected(requestType, url,tag);
    }

    @Override
    public void updateExamDetail(ExamSummary examSummary) {

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
    public  interface OnHomeItemSelectListener {

        void onExamTabSelected(ExamDetail tabPosition);
        void onHomeItemSelected(String requestType, String url,String examTag);
    }

}