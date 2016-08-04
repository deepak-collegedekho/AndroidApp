package com.collegedekho.app.listener;


import android.support.v4.app.Fragment;

/**
 * Created by harshvardhan on 05/08/15.
 */
public interface PsychometricAnalysisPageListener
{
    boolean isSkippable();

    boolean isRequired();

    boolean hasSecondary();

    boolean isSecondary();

    Fragment getFragmentInstance();

    String type();

    boolean isAnswered();

    void updateAndSetAnswer();

    boolean isAnswerDeemedForSecondary();
}
