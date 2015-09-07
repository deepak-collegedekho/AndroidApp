package com.collegedekho.app.fragment.pyschometricTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.collegedekho.app.listener.PsychometricAnalysisPageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by harshvardhan on 06/08/15.
 */
public class PsychometricQuestionFragment extends Fragment implements PsychometricAnalysisPageListener {

    private static JSONObject mAnswersMap;

    static
    {
        PsychometricQuestionFragment.mAnswersMap = new JSONObject();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public boolean hasSecondary() {
        return false;
    }

    @Override
    public boolean isSecondary() {
        return false;
    }

    @Override
    public Fragment getFragmentInstance() {
        return null;
    }

    @Override
    public String type() {
        return "";
    }

    @Override
    public boolean isAnswered() {
        return false;
    }

    @Override
    public void updateAndSetAnswer() {
    }

    @Override
    public boolean isAnswerDeemedForSecondary() {
        return false;
    }

    public void setAnswer(String key, Object value)
    {
        try
        {
            PsychometricQuestionFragment.mAnswersMap.put(key, value);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static JSONObject getAnswers()
    {
        return PsychometricQuestionFragment.mAnswersMap;
    }

    public static void resetAnswer()
    {
        PsychometricQuestionFragment.mAnswersMap = new JSONObject();
    }

}
