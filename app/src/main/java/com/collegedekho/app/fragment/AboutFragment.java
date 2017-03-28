package com.collegedekho.app.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.BuildConfig;
import com.collegedekho.app.R;


public class AboutFragment extends BaseFragment {

    private final String TAG = AboutFragment.class.getSimpleName();
  //  private static AboutFragment sInstance ;

    public static AboutFragment newInstance() {
        /*synchronized (AboutFragment.class){
            if(sInstance == null)*/
                return new AboutFragment();
        /*}
        return sInstance;*/
    }

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView app_version = (TextView) view.findViewById(R.id.app_version);
        app_version.setText("v" + BuildConfig.VERSION_NAME);

        view.findViewById(R.id.send_feedback).setOnClickListener(this);
        view.findViewById(R.id.rate_on_google_play).setOnClickListener(this);
        view.findViewById(R.id.term_of_services).setOnClickListener(this);
        view.findViewById(R.id.app_privacy_policy).setOnClickListener(this);
        view.findViewById(R.id.app_share).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.send_feedback:
                onSendFeedBack();
                break;
            case R.id.rate_on_google_play:
                onRateOnGooglePlay();
                break;
            case R.id.term_of_services:
                onTermOfServices();
                break;
            case R.id.app_privacy_policy:
                onPrivacyPolicy();
                break;
            case R.id.app_share:
                onAppShare();
                break;
            default:
                break;
        }
    }

    public void onSendFeedBack(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"hello@collegedekho.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        i.putExtra(Intent.EXTRA_TEXT   , "Hi CollegeDekho team,");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRateOnGooglePlay(){
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
        }
    }
    public void onTermOfServices(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.collegedekho.com/terms-and-conditions/"));
        try {
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browserIntent.setPackage("com.android.chrome");
            startActivity(browserIntent);
        } catch (Exception e) {
            browserIntent.setPackage(null);
            startActivity(browserIntent);
            e.printStackTrace();
        }
    }
    public void onPrivacyPolicy(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.collegedekho.com/privacy-policy/"));
        try {
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browserIntent.setPackage("com.android.chrome");
            startActivity(browserIntent);
        } catch (Exception e) {
            browserIntent.setPackage(null);
            startActivity(browserIntent);
            e.printStackTrace();
        }
    }

    public void onAppShare(){
        try {
            // request for share intent to download app  from playStore
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Explore top institutions, colleges and universities. Click here to download your personalised guide" +
                            ": https://play.google.com/store/apps/details?id=com.collegedekho.app&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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
