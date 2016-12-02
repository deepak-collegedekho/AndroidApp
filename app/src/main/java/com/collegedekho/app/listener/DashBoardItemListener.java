package com.collegedekho.app.listener;

import com.collegedekho.app.entities.ProfileExam;

/**
 * Created by sureshsaini on 2/12/16.
 */

public interface DashBoardItemListener {

    void onExamTabSelected(ProfileExam tabPosition);
    void onHomeItemSelected(String requestType, String url,String examTag);
    void requestForProfileFragment();
    void onTabStepByStep();
    void onPsychometricTestSelected();
    void onTabPsychometricReport();
}