package com.collegedekho.app.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.truecaller.android.sdk.TrueButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 *Created by ${sureshsaini} on ${20/11/15}.
 */
public class SplashLoginFragment extends  BaseFragment {

    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    private OnSplashLoginListener mListener;
    private Unbinder mUnBinder;
    @BindView(R.id.institute_count_ticker)
    TickerView mInstituteCountTicker;

    @BindView(R.id.institute_count_ticker1)
    TickerView mInstituteCountTicker1;

    @BindView(R.id.institute_count_ticker2)
    TickerView mInstituteCountTicker2 ;

    @BindView(R.id.institute_count_ticker3)
    TickerView mInstituteCountTicker3;

    @BindView(R.id.institute_count_ticker4)
    TickerView mInstituteCountTicker4;

    @BindView(R.id.institute_count_ticker5)
    TickerView mInstituteCountTicker5;

    @BindView(R.id.institute_count_ticker_plus)
    TickerView mInstituteCountTickerPlus;

    @BindView(R.id.existing_user_layout)
    LinearLayout mExistingUserLayout;



    public static SplashLoginFragment newInstance() {
        return new SplashLoginFragment();
    }

    public SplashLoginFragment(){
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_splash_login, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        TrueButton trueButton =(TrueButton)view.findViewById(R.id.com_truecaller_android_sdk_truebutton);
        boolean usable = trueButton.isUsable();
        if(!usable){
           mExistingUserLayout.setVisibility(View.GONE);
        }
        trueButton.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInstituteCountTicker.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker1.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker2.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker3.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker4.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker5.setCharacterList(NUMBER_LIST);
        mInstituteCountTickerPlus.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker.setText("20430");
        mInstituteCountTicker1.setText("0");
        mInstituteCountTicker2.setText("0");
        mInstituteCountTicker3.setText("0");
        mInstituteCountTicker4.setText("0");
        mInstituteCountTicker5.setText("0");
        mInstituteCountTickerPlus.setText("+");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String count = "20430";
                if( count.length() ==1) {
                    updateTickerValue(count, "0","0","0","0");
                }else if(count.length() ==2){
                    updateTickerValue("0",count.substring(0,1),"0","0","0");
                }else if(count.length() ==3){
                    updateTickerValue("0",count.substring(1,2),count.substring(0,1),"0","0");
                }else if(count.length() ==4){
                    updateTickerValue("0",count.substring(2,3),count.substring(1,2),count.substring(0,1),"0");
                }else if(count.length() ==5){
                    updateTickerValue("0",count.substring(3,4),count.substring(2,3), count.substring(1,2),count.substring(0,1));
                }
                if(isAdded()) {
                    MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.institute_count);
                    mp.start();
                }
            }
        }, 400);
    }

    private void updateTickerValue(String value1,String value2, String value3, String value4, String value5 ) {
        View v = getView();
        if(v != null) {
            mInstituteCountTicker.setText("1");
            mInstituteCountTicker1.setText(value1);
            mInstituteCountTicker2.setText(value2);
            mInstituteCountTicker3.setText(value3);
            mInstituteCountTicker4.setText(value4);
            mInstituteCountTicker5.setText(value5);
        }
  }


   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnSplashLoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSignUpListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if(mUnBinder != null)
            mUnBinder.unbind();
    }

    @OnClick(R.id.existing_user_layout)
    public void onExistingUserClick(){
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        if(mListener != null)
            mListener.onExistingUserLogin();
    }

    @OnClick(R.id.splash_login_proceed)
    public void onProceedClick(){
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        if(mListener != null)
            mListener.onSplashHelpMeLogin();
    }


    public interface OnSplashLoginListener {
        void onSplashHelpMeLogin();
        void onExistingUserLogin();
        void displayMessage(int messageId);
    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }

}
