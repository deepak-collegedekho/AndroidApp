package com.collegedekho.app.fragment.profileBuilding;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;

import com.collegedekho.app.R;
import com.collegedekho.app.fragment.BaseFragment;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

/**
 * Created by sureshsaini on 19/4/17.
 */

public class BaseProfileBuildingFragment extends BaseFragment {

    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    private String mInstituteCount = "20430";


    TickerView mInstituteCountTicker1, mInstituteCountTicker2, mInstituteCountTicker3,
            mInstituteCountTicker4, mInstituteCountTicker5;

    @Override
    public String getEntity() {
        return null;
    }

    protected void initIntituesCountViews(View view){

        mInstituteCountTicker1 = (TickerView) view.findViewById(R.id.institute_count_ticker1);
        mInstituteCountTicker2 = (TickerView) view.findViewById(R.id.institute_count_ticker2);
        mInstituteCountTicker3 = (TickerView) view.findViewById(R.id.institute_count_ticker3);
        mInstituteCountTicker4 = (TickerView) view.findViewById(R.id.institute_count_ticker4);
        mInstituteCountTicker5 = (TickerView) view.findViewById(R.id.institute_count_ticker5);
        TickerView mInstituteCountTickerPlus = (TickerView) view.findViewById(R.id.institute_count_ticker_plus);
        mInstituteCountTicker1.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker2.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker3.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker4.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker5.setCharacterList(NUMBER_LIST);
        mInstituteCountTickerPlus.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker1.setText("0");
        mInstituteCountTicker2.setText("3");
        mInstituteCountTicker3.setText("4");
        mInstituteCountTicker4.setText("0");
        mInstituteCountTicker5.setText("2");
        mInstituteCountTickerPlus.setText("+");
    }

    protected void setInstituteCount(final String count) {

        if(!mInstituteCount.equalsIgnoreCase(count)) {
            mInstituteCount = count;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isAdded()){
                        if( count.length() ==1) {
                            updateTickerValue(count, "0","0","0","0");
                        }else if(count.length() ==2){
                            updateTickerValue(count.substring(1,2),count.substring(0,1),"0","0","0");
                        }else if(count.length() ==3){
                            updateTickerValue(count.substring(2,3),count.substring(1,2),count.substring(0,1),"0","0");
                        }else if(count.length() ==4){
                            updateTickerValue(count.substring(3,4),count.substring(2,3),count.substring(1,2),count.substring(0,1),"0");
                        }else if(count.length() ==5){
                            updateTickerValue(count.substring(4,5),count.substring(3,4),count.substring(2,3), count.substring(1,2),count.substring(0,1));
                        }
                        if(isAdded()) {
                            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.institute_count);
                            mp.start();
                        }
                    }

                }
            }, 200);
        }
    }

    protected void updateTickerValue( String value1,String value2, String value3, String value4, String value5 ) {
        if(mInstituteCountTicker1 != null)
            mInstituteCountTicker1.setText(value1);
        if(mInstituteCountTicker2 != null)
            mInstituteCountTicker2.setText(value2);
        if(mInstituteCountTicker3 != null)
            mInstituteCountTicker3.setText(value3);
        if(mInstituteCountTicker4 != null)
            mInstituteCountTicker4.setText(value4);
        if(mInstituteCountTicker5 != null)
            mInstituteCountTicker5.setText(value5);
    }
}
