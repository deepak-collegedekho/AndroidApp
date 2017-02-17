package com.collegedekho.app.listener;

import java.util.HashMap;

/**
 * Created by sureshsaini on 17/2/17.
 */

public interface UserLoginListener {

        void onRequestForOTP(String phoneNumber);
        void onVerifyOTP(String phoneNumber, String otp);
        void displayMessage(int messageId);
        void requestForProfile(HashMap<String, String> params);
}
