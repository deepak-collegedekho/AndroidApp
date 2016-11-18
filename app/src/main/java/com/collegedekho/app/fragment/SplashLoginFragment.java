package com.collegedekho.app.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

/**
 *Created by ${sureshsaini} on ${20/11/15}.
 */
public class SplashLoginFragment extends  BaseFragment {

    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    private OnSplashLoginListener mListener;
    private TickerView mInstituteCountTicker ;

    public static SplashLoginFragment newInstance() {
        return new SplashLoginFragment();
    }

    public SplashLoginFragment(){
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return  inflater.inflate(R.layout.fragment_splash_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TickerView mInstituteCountTicker = (TickerView) view.findViewById(R.id.institute_count_ticker);
        TickerView mInstituteCountTicker1 = (TickerView) view.findViewById(R.id.institute_count_ticker1);
        TickerView mInstituteCountTicker2 = (TickerView) view.findViewById(R.id.institute_count_ticker2);
        TickerView mInstituteCountTicker3 = (TickerView) view.findViewById(R.id.institute_count_ticker3);
        TickerView mInstituteCountTicker4 = (TickerView) view.findViewById(R.id.institute_count_ticker4);
        TickerView mInstituteCountTicker5 = (TickerView) view.findViewById(R.id.institute_count_ticker5);
        TickerView mInstituteCountTickerPlus = (TickerView) view.findViewById(R.id.institute_count_ticker_plus);
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

        view.findViewById(R.id.splash_login_help_me).setOnClickListener(this);
        view.findViewById(R.id.splash_login_i_know).setOnClickListener(this);
        view.findViewById(R.id.splash_login_skip_layout).setOnClickListener(this);
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
            TickerView mInstituteCountTicker = (TickerView) v.findViewById(R.id.institute_count_ticker);
            TickerView mInstituteCountTicker1 = (TickerView) v.findViewById(R.id.institute_count_ticker1);
            TickerView mInstituteCountTicker2 = (TickerView) v.findViewById(R.id.institute_count_ticker2);
            TickerView mInstituteCountTicker3 = (TickerView) v.findViewById(R.id.institute_count_ticker3);
            TickerView mInstituteCountTicker4 = (TickerView) v.findViewById(R.id.institute_count_ticker4);
            TickerView mInstituteCountTicker5 = (TickerView) v.findViewById(R.id.institute_count_ticker5);

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
    }

    @Override
    public void onClick(View view) {
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        switch (view.getId()) {
            case R.id.splash_login_skip_layout:
                mSplashSkipLogin();
                break;
            case R.id.splash_login_help_me:
                mSplashHelpMeLogin();
                break;
            case R.id.splash_login_i_know:
                mSplashIKnowLogin();
                break;
        }
    }

    private void mSplashSkipLogin(){
        mListener.onSplashSkipLogin();
    }

    private void mSplashHelpMeLogin(){
        mListener.onSplashHelpMeLogin();
    }

    private void mSplashIKnowLogin(){
        mListener.onSplashIKnowLogin();
    }

    public interface OnSplashLoginListener {
        void onSplashSkipLogin();
        void onSplashHelpMeLogin();
        void onSplashIKnowLogin();
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
