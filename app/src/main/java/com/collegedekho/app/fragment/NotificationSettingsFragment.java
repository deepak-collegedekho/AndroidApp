package com.collegedekho.app.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.collegedekho.app.R;
import com.collegedekho.app.resource.Constants;

public class NotificationSettingsFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    private SwitchCompat chatNotificationSwitch,
                         newsNotificationSwitch,
                         articleNotificationSwitch,
                         otherNotificationSwitch;

    private SharedPreferences mSharedPreferences;

    public static NotificationSettingsFragment newInstance() {
        return new NotificationSettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chatNotificationSwitch = (SwitchCompat) view.findViewById(R.id.switch_chat_notification);
        newsNotificationSwitch = (SwitchCompat) view.findViewById(R.id.switch_news_notification);
        articleNotificationSwitch = (SwitchCompat) view.findViewById(R.id.switch_article_notification);
        otherNotificationSwitch = (SwitchCompat) view.findViewById(R.id.switch_other_notification);
        mSharedPreferences = getContext().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        initListeners();
        setupView();
    }

    private void initListeners(){
        chatNotificationSwitch.setOnCheckedChangeListener(this);
        newsNotificationSwitch.setOnCheckedChangeListener(this);
        articleNotificationSwitch.setOnCheckedChangeListener(this);
        otherNotificationSwitch.setOnCheckedChangeListener(this);
    }

    public void setupView()
    {
        chatNotificationSwitch.setChecked(mSharedPreferences.getBoolean(Constants.CHAT_NOTIFICATION_SETTINGS,true));
        newsNotificationSwitch.setChecked(mSharedPreferences.getBoolean(Constants.NEWS_NOTIFICATION_SETTINGS,true));
        articleNotificationSwitch.setChecked(mSharedPreferences.getBoolean(Constants.ARTICLE_NOTIFICATION_SETTINGS,true));
        otherNotificationSwitch.setChecked(mSharedPreferences.getBoolean(Constants.OTHER_NOTIFICATION_SETTINGS,true));
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId())
        {
            case R.id.switch_chat_notification: saveNotificationSettings(Constants.CHAT_NOTIFICATION_SETTINGS,isChecked); break;
            case R.id.switch_news_notification: saveNotificationSettings(Constants.NEWS_NOTIFICATION_SETTINGS,isChecked); break;
            case R.id.switch_article_notification: saveNotificationSettings(Constants.ARTICLE_NOTIFICATION_SETTINGS,isChecked); break;
            case R.id.switch_other_notification: saveNotificationSettings(Constants.OTHER_NOTIFICATION_SETTINGS,isChecked); break;
        }
    }

    public void saveNotificationSettings(String notificationSettings,boolean status){
        mSharedPreferences.edit().putBoolean(notificationSettings,status).apply();
    }


    @Override
    public String getEntity() {
        return null;
    }
}