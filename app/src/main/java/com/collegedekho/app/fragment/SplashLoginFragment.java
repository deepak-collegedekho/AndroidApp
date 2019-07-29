package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.activity.SplashActivity;
import com.collegedekho.app.entities.Feed;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.greenrobot.eventbus.EventBus;

import static android.content.Context.MODE_PRIVATE;
import static com.collegedekho.app.activity.MainActivity.currentFragment;


/**
 * Created by ${sureshsaini} on ${20/11/15}.
 */
public class SplashLoginFragment extends BaseFragment {

    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    public OnSplashLoginListener listener;
    private TickerView mInstituteCountTicker;
    private TickerView mInstituteCountTicker1;
    private TickerView mInstituteCountTicker2;
    private TickerView mInstituteCountTicker3;
    private TickerView mInstituteCountTicker4;
    private TickerView mInstituteCountTicker5;
    private TickerView mInstituteCountTickerPlus;

    public SplashLoginFragment() {
        // required empty constructor
    }

    public static SplashLoginFragment newInstance() {
        return new SplashLoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInstituteCountTicker = (TickerView) view.findViewById(R.id.institute_count_ticker);
        mInstituteCountTicker1 = (TickerView) view.findViewById(R.id.institute_count_ticker1);
        mInstituteCountTicker2 = (TickerView) view.findViewById(R.id.institute_count_ticker2);
        mInstituteCountTicker3 = (TickerView) view.findViewById(R.id.institute_count_ticker3);
        mInstituteCountTicker4 = (TickerView) view.findViewById(R.id.institute_count_ticker4);
        mInstituteCountTicker5 = (TickerView) view.findViewById(R.id.institute_count_ticker5);
        mInstituteCountTickerPlus = (TickerView) view.findViewById(R.id.institute_count_ticker_plus);

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

        SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE);
        Boolean disclosureAccept = sp.getBoolean("Disclosure", false);

        if (!disclosureAccept)
            ShowDisclosureAlert();

        view.findViewById(R.id.existing_user_layout).setOnClickListener(this);
        view.findViewById(R.id.splash_login_proceed).setOnClickListener(this);
        view.findViewById(R.id.splash_privacy_policy).setOnClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String count = "20430";
                if (count.length() == 1) {
                    updateTickerValue(count, "0", "0", "0", "0");
                } else if (count.length() == 2) {
                    updateTickerValue("0", count.substring(0, 1), "0", "0", "0");
                } else if (count.length() == 3) {
                    updateTickerValue("0", count.substring(1, 2), count.substring(0, 1), "0", "0");
                } else if (count.length() == 4) {
                    updateTickerValue("0", count.substring(2, 3), count.substring(1, 2), count.substring(0, 1), "0");
                } else if (count.length() == 5) {
                    updateTickerValue("0", count.substring(3, 4), count.substring(2, 3), count.substring(1, 2), count.substring(0, 1));
                }
                if (isAdded()) {
                    MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.institute_count);
                    mp.start();
                }
            }
        }, 400);
    }


    private void updateTickerValue(String value1, String value2, String value3, String value4, String value5) {
        View v = getView();
        if (v != null) {
            mInstituteCountTicker.setText("1");
            mInstituteCountTicker1.setText(value1);
            mInstituteCountTicker2.setText(value2);
            mInstituteCountTicker3.setText(value3);
            mInstituteCountTicker4.setText(value4);
            mInstituteCountTicker5.setText(value5);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        switch (v.getId()) {
            case R.id.existing_user_layout:

                if(listener != null)
                    listener.onExistingUserLogin();
               // EventBus.getDefault().post(new Event(AllEvents.ACTION_EXISTING_USER_CLICK, null, null));
                //EventBus.getDefault().post(new Event(AllEvents.ACTION_NEW_USER_PROCEED_CLICK, null, null));
                break;
            case R.id.splash_login_proceed:
                if(listener != null)
                    listener.onSplashHelpMeLogin();
                // EventBus.getDefault().post(new Event(AllEvents.ACTION_NEW_USER_PROCEED_CLICK, null, null));
                break;
            case R.id.splash_privacy_policy:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.collegedekho.com/privacy-policy/"));
                try {
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    browserIntent.setPackage("com.android.chrome");
                    startActivity(browserIntent);
                } catch (Exception e) {
                    browserIntent.setPackage(null);
                    startActivity(browserIntent);
                    e.printStackTrace();
                }
                // listener.onSplashSelectedClick("https://www.collegedekho.com/privacy-policy/");
//change here :
            default:
                break;
        }
    }

    @Override
    public String getEntity() {
        return null;
    }

    public void ShowDisclosureAlert() {


        final SpannableString s =
                new SpannableString("https://www.collegedekho.com/privacy-policy/");
        Linkify.addLinks(s, Linkify.WEB_URLS);
        LinearLayout layout = new LinearLayout(getContext());
        TextView tvMessage = new TextView(getContext());
        TextView tvMessage2 = new TextView(getContext());

        final TextView etInput = new TextView(getContext());
        tvMessage.setText("Disclosure");
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

        tvMessage2.setText(getResources().getString(R.string.disclosure));
        tvMessage2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        etInput.setSingleLine(false);
        etInput.setText(s);
        etInput.setMovementMethod(LinkMovementMethod.getInstance());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage);
        layout.addView(tvMessage2);

        layout.addView(etInput);
        layout.setPadding(50, 40, 50, 10);

//        if (!MainActivity.this.isFinishing()) {
//        final TextView message = new TextView(getContext());
//        message.setPadding(30,0,0,0);
//        message.setTextSize(12);
//        message.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // i.e.: R.string.dialog_message =>
        // "Test this dialog following the link to dtmilano.blogspot.com"

//        message.setText(s);
//        message.setMovementMethod(LinkMovementMethod.getInstance());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder
                .setView(layout)
                .setPositiveButton("Tap to accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        (getContext()).getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE).edit().putBoolean("Disclosure", true).apply();

                    }
                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }).setCancelable(false).show();
        //.setCanceledOnTouchOutside(true/false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (OnSplashLoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSignUpListener");
        }
    }

        @Override
    public void onDetach() {
        super.onDetach();
        super.listener = null;
    }

    public interface OnSplashLoginListener extends BaseListener {
        void onSplashHelpMeLogin();
        void onExistingUserLogin();
        void displayMessage(int messageId);

        void onSplashSelectedClick(String url);

    }
}
//}

