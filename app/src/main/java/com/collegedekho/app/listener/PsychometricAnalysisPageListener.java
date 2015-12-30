package com.collegedekho.app.listener;


import android.support.v4.app.Fragment;

/**
 * Created by harshvardhan on 05/08/15.
 */
public interface PsychometricAnalysisPageListener
{
    public boolean isRequired();

    public boolean hasSecondary();

    public boolean isSecondary();

    public Fragment getFragmentInstance();

    public String type();

    public boolean isAnswered();

    public void updateAndSetAnswer();

    public boolean isAnswerDeemedForSecondary();

}
