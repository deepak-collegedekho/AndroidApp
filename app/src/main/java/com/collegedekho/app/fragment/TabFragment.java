package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.CircleImageView;

/**
 * Created by sureshsaini on 6/12/15.
 */
public class TabFragment extends  BaseFragment {
    private final String TAG ="Tab Fragment";
    private static String PARAM1 = "param1";

    private int selectedTabMenuPosition =0;
    private int selectedSubMenuPosition =0;
    private  OnHomeItemSelectListener mListener;

    public static TabFragment newInstance(int tabPosoition) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM1, tabPosoition);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        TextView mProfileName = (TextView) rootView.findViewById(R.id.user_name);
        CircleImageView mProfileImage = (CircleImageView)rootView.findViewById(R.id.profile_image);

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

        rootView.findViewById(R.id.prep_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.resources_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.future_buddies).setOnClickListener(this);
        rootView.findViewById(R.id.my_alerts).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_third).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_fourth).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mUpdateSelectedTab();
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
        switch (view.getId())
        {
            case R.id.prep_buddies:
                this.selectedTabMenuPosition = 0;
                break;
            case R.id.resources_buddies:
                this.selectedTabMenuPosition = 1;
                break;
            case R.id.future_buddies:
                this.selectedTabMenuPosition = 2;
                this.mHomeItemSelected(Constants.WIDGET_FORUMS, Constants.BASE_URL+"personalize/forums");
                break;
            case R.id.my_alerts:
                this.selectedTabMenuPosition = 3;
                Toast.makeText(getActivity().getApplicationContext(), "Coming soon..", Toast.LENGTH_LONG).show();
                return;
            case R.id.home_widget_first:
                this.selectedSubMenuPosition = 0;
                this.mUpdateSubMenuItem();
                break;
            case R.id.home_widget_second:
                this.selectedSubMenuPosition = 1;
                this.mUpdateSubMenuItem();
                break;
            case R.id.home_widget_third:
                this.selectedSubMenuPosition = 2;
                this.mUpdateSubMenuItem();
                break;
            case R.id.home_widget_fourth:
                this.selectedSubMenuPosition = 3;
                this.mUpdateSubMenuItem();
                break;
            default:
                break;
        }
        this.mUpdateSelectedTab();
    }

    private void mUpdateSelectedTab(){
        View view = getView();

        if(view ==   null)
            return;

        TextView prepBuddies       = (TextView)view.findViewById(R.id.prep_buddies);
        TextView resourceBuddies   = (TextView)view.findViewById(R.id.resources_buddies);
        TextView futureBuddies     = (TextView)view.findViewById(R.id.future_buddies);
        TextView myAlerts          = (TextView)view.findViewById(R.id.my_alerts);

        TextView firstSubMenuTV       = (TextView)view.findViewById(R.id.home_widget_textview_first);
        TextView secondSubMenuTV   = (TextView)view.findViewById(R.id.home_widget_textview_second);
        TextView thirdSubMenuTV    = (TextView)view.findViewById(R.id.home_widget_textview_third);
        TextView fourthSubMenuTV          = (TextView)view.findViewById(R.id.home_widget_textview_fourth);

        ImageView firstSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_first);
        ImageView secondSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_second);
        ImageView thirdSubMenuIV      = (ImageView)view.findViewById(R.id.home_widget_image_third);
        ImageView fourthSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_fourth);

        prepBuddies.setSelected(false);
        resourceBuddies.setSelected(false);
        futureBuddies.setSelected(false);
        myAlerts.setSelected(false);

        if(this.selectedTabMenuPosition == 0){
            prepBuddies.setSelected(true);
            firstSubMenuIV.setImageResource(R.drawable.ic_test_calendar);
            secondSubMenuIV.setImageResource(R.drawable.ic_syllabus);
            thirdSubMenuIV.setImageResource(R.drawable.ic_challenges);
            fourthSubMenuIV.setImageResource(R.drawable.ic_prep_path);

            firstSubMenuTV.setText("Test Calendar");
            secondSubMenuTV.setText("Syllabus");
            thirdSubMenuTV.setText("Challenges");
            fourthSubMenuTV.setText("Prep Path");

        }else   if(this.selectedTabMenuPosition == 1){
            resourceBuddies.setSelected(true);
            firstSubMenuIV.setImageResource(R.drawable.ic_institute);
            secondSubMenuIV.setImageResource(R.drawable.ic_news);
            thirdSubMenuIV.setImageResource(R.drawable.ic_article);
            fourthSubMenuIV.setImageResource(R.drawable.ic_qna);

            firstSubMenuTV.setText("Institutes");
            secondSubMenuTV.setText("News");
            thirdSubMenuTV.setText("Article");
            fourthSubMenuTV.setText("Qna");

        }else   if(this.selectedTabMenuPosition == 2){
            futureBuddies.setSelected(true);

        }else   if(this.selectedTabMenuPosition == 3){
            myAlerts.setSelected(true);
        }
    }

    private void mUpdateSubMenuItem(){
        if(selectedTabMenuPosition ==0 ){
            if(selectedSubMenuPosition == 1)
                this.mHomeItemSelected(Constants.WIDGET_SYLLABUS, Constants.BASE_URL + "yearly-exams/54/syllabus/");
            else
                Toast.makeText(getActivity().getApplicationContext(), "Coming soon..", Toast.LENGTH_LONG).show();


        }
        else if(selectedTabMenuPosition == 1){
             if(selectedSubMenuPosition == 0){
                 this.mHomeItemSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL+"personalize/institutes");
             }else  if(selectedSubMenuPosition == 1){
                 this.mHomeItemSelected(Constants.WIDGET_NEWS, Constants.BASE_URL+"personalize/news");
             }else  if(selectedSubMenuPosition == 2){
                 this.mHomeItemSelected(Constants.WIDGET_ARTICES, Constants.BASE_URL+"personalize/articles");
             }else  if(selectedSubMenuPosition == 3){
                 this.mHomeItemSelected(Constants.TAG_LOAD_QNA_QUESTIONS, Constants.BASE_URL+"personalize/qna");
             }
        }

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
    public  interface OnHomeItemSelectListener {
        void onHomeItemSelected(String requestType, String url);
    }

}