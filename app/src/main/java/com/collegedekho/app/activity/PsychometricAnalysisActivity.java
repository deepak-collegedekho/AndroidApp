package com.collegedekho.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.PsychometricAnalysisAdapter;
import com.collegedekho.app.display.NonSwipeableViewPager;
import com.collegedekho.app.entities.PsychometricQuestion;
import com.collegedekho.app.fragment.pyschometricTest.PsychometricQuestionFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PsychometricAnalysisActivity extends FragmentActivity{

    public static final int GET_PSYCHOMETRIC_RESULTS = 1;
    public static final String PSYCHOMETRIC_RESULTS = "psychometric_results";
    private static final String TAG = "PsychometricAnalysisActivity";
    private ProgressDialog progressDialog;
    private TextView mPreviousButton;
    private TextView mNextButton;
    private PsychometricAnalysisAdapter mQuestionAdapter;
    private NonSwipeableViewPager mViewPager;
    private ArrayList<PsychometricQuestion> mPsychometricQuestions;
    private String mPsychometricResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychometric_analysis);

        this.mPsychometricResult = getIntent().getExtras().getString(PsychometricAnalysisActivity.PSYCHOMETRIC_RESULTS);

        this.mViewPager = (NonSwipeableViewPager) findViewById(R.id.pager);
        this.mViewPager.setOffscreenPageLimit(0);

        //this.mPreviousButton = (TextView) findViewById(R.id.button_previous);
        this.mNextButton = (TextView) findViewById(R.id.button_next);

        /*this.mPreviousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Load previous fragment
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() == 0 ? 0 : mViewPager.getCurrentItem() - 1);
            }
        });*/

        this.mNextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean carryOnToNext = false;
                int skipBy = 1;
                int currentIndex;
                boolean isRequired;
                boolean isAnswered;
                PsychometricQuestionFragment inContextFragment;

                if (mNextButton.getText().toString() == "Finish") {
                    mFinishTest();
                    return;
                }

                currentIndex = mViewPager.getCurrentItem();

                if (mViewPager.getCurrentItem() == mQuestionAdapter.getCount() - 1)
                    inContextFragment = mQuestionAdapter.getCurrentQuestionFragment();
                else
                    inContextFragment = mQuestionAdapter.getLastQuestionFragment();

                isRequired = inContextFragment.isRequired();
                isAnswered = inContextFragment.isAnswered();

                if (isAnswered) {
                    inContextFragment.updateAndSetAnswer();

                    //check if it is secondary
                    if (!inContextFragment.isSecondary()) {
                        //if it is not secondary
                        //check if answer should lead to secondary questions
                        if (inContextFragment.isAnswerDeemedForSecondary()) {
                            //show secondary question
                            //see if this question has secondary questions
                            if (inContextFragment.hasSecondary()) {
                                //TODO: Leaving this code here so that afterwards when the shim is removed, we can come back to the optimized solution
                                //shim: sroing secondary questions in the primary question's list only
                                //yes it has secondary questions
                                //add secondary questions in adapter and notify
                                //ArrayList<PsychometricQuestion> secondaryList = (mPsychometricQuestions.get(currentIndex)).getSecondary();

                                //if (secondaryList.size() > 0)
                                //mViewPager.removeViews(currentIndex + 1, mViewPager.getChildCount() - 1);

                                /*for (int i = 0; i < secondaryList.size(); i++)
                                {
                                    //mPsychometricQuestions.add(currentIndex + i + 1, secondaryList.get(i));
                                    mQuestionAdapter.addSecondary(currentIndex + i + 1, secondaryList.get(i));
                                    //notify the adapter has changed
                                    mQuestionAdapter.notifyDataSetChanged();
                                    //mViewPager.setCurrentItem((currentIndex + i + 1) + 1);

                                    mViewPager.invalidate();
                                }*/

                                //View view1 = mViewPager.getChildAt(currentIndex + 1);
                                //View view2 = mViewPager.getChildAt(currentIndex + 2);

                                carryOnToNext = true;
                            } else {
                                //Doesn't have Secondary
                                if (!isRequired) {
                                    skipBy = ((mPsychometricQuestions.get(currentIndex)).getSecondary()).size() + 1;
                                    carryOnToNext = true;
                                }
                            }
                        } else {
                            //Answer not Deemed For Secondary
                            //should skip the secondary questions
                            skipBy = ((mPsychometricQuestions.get(currentIndex)).getSecondary()).size() + 1;
                            carryOnToNext = true;
                        }
                    } else {
                        //it is secondary
                        carryOnToNext = true;
                    }
                } else {
                    //If not Answered
                    if (!isRequired) {
                        skipBy = ((mPsychometricQuestions.get(currentIndex)).getSecondary()).size() + 1;
                        carryOnToNext = true;
                    }
                }

                if (carryOnToNext) {
                    //change next button's label to finish
                    if (mViewPager.getCurrentItem() == mQuestionAdapter.getCount() - 1)
                        mNextButton.setText("Finish");

                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() == mQuestionAdapter.getCount() - 1 ? mQuestionAdapter.getCount() - 1 : mViewPager.getCurrentItem() + skipBy);
                } else {
                    mViewPager.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    mViewPager.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.shake));
                }
            }
        });

        //this.mPreviousButton.setEnabled(false);
        this.mNextButton.setEnabled(false);

        this.mPsychometricQuestions = new ArrayList<PsychometricQuestion>();

        this.mDisplayPsychometricTest(this.mPsychometricResult);
    }

    private void mFinishTest()
    {
        //Intent result = new Intent();
        //result.putExtra(MainActivity.PSYCHOMETRIC_RESULTS, jsonObject);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_psychometric_analysis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showProgress(String tag)
    {
        String message = MainActivity.GetPersonalizedMessage(tag);
        if (message != null)
            this.showProgressDialog(message);
    }

    private void showProgressDialog(String message) {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(this);
            this.progressDialog.setCancelable(false);
            this.progressDialog.setMessage(message);
            this.progressDialog.setIndeterminate(true);
        } else {
            this.progressDialog.setMessage(message);
        }
        this.progressDialog.show();
    }

    private void mDisplayPsychometricTest(String response) {
        /*new AlertDialog.Builder(this).setMessage("This feature is coming soon!").setTitle("Coming Soon!").setNeutralButton("OK", null)
                .show();*/
        try {
            //List<PsychometricPrimaryQuestion> questions = JSON.std.listOfFrom(PsychometricPrimaryQuestion.class, response);
            //System.out.print("hello");
            this.mPsychometricQuestions = this.mParseResponse(response);
            this.mQuestionAdapter = new PsychometricAnalysisAdapter(getSupportFragmentManager(), getApplicationContext(), this.mPsychometricQuestions);
            this.mViewPager.setAdapter(this.mQuestionAdapter);
            //mQuestionAdapter.getItem();
            //mQuestionAdapter.notifyDataSetChanged();

            //Enable the buttons
            this.mNextButton.setEnabled(true);
            //this.mPreviousButton.setEnabled(true);
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
    }

    //Recursive Method to parse the psychometric test response
    private ArrayList<PsychometricQuestion> mParseResponse(String response)
    {
        ArrayList<PsychometricQuestion> psychometricQuestions = new ArrayList<PsychometricQuestion>();
        try
        {
            JSONArray resultsArray = new JSONArray(response);

            for (int i = 0; i < resultsArray.length(); i++)
            {
                PsychometricQuestion psychometricQuestion = new PsychometricQuestion();
                JSONArray psychometricSecondaryQuestionsList = new JSONArray();
                JSONArray fields;
                ArrayList<String> fieldsArray = new ArrayList<String>();
                ArrayList<PsychometricQuestion> psychometricSecondaryQuestions = new ArrayList<PsychometricQuestion>();
                JSONArray choiceArray = new JSONArray();
                HashMap<String, String> choiceMap = new HashMap<>();

                JSONObject ques = resultsArray.getJSONObject(i);

                if (ques.has("name"))
                {
                    psychometricQuestion.setName(ques.getString("name"));
                }
                psychometricQuestion.setText(ques.getString("text"));
                psychometricQuestion.setType(ques.getString("type"));
                psychometricQuestion.setRequired(ques.getBoolean("required"));

                //Handle fields
                fields = ques.getJSONArray("field");

                for (int k = 0; k < fields.length(); k++)
                {
                    fieldsArray.add(fields.getString(k));
                }

                //Handle choices
                if (ques.has("choices"))
                {
                    choiceArray = ques.getJSONArray("choices");

                    for (int j = 0; j < choiceArray.length(); j++)
                    {
                        JSONArray choice;

                        choice = choiceArray.getJSONArray(j);

                        choiceMap.put(choice.getString(1), choice.getString(0));
                    }
                }

                psychometricQuestion.setField(fieldsArray);
                psychometricQuestion.setChoiceMap(choiceMap);

                //Handle Secondary
                if (ques.has("secondary"))
                {
                    psychometricSecondaryQuestionsList = ques.getJSONArray("secondary");
                    if (psychometricSecondaryQuestionsList != null && psychometricSecondaryQuestionsList.length() > 0)
                    {
                        psychometricQuestion.setIsSecondary(false);
                        psychometricSecondaryQuestions = this.mParseResponse(psychometricSecondaryQuestionsList.toString());
                    }
                }
                else
                    psychometricQuestion.setIsSecondary(true);

                psychometricQuestion.setSecondary(psychometricSecondaryQuestions);

                psychometricQuestions.add(psychometricQuestion);

                //TODO : Shim to show secondary questions in the primary question lists
                //add secondary questions to the list of primary questions
                for (int l = 0; l < psychometricSecondaryQuestions.size(); l++)
                {
                    psychometricQuestions.add(psychometricSecondaryQuestions.get(l));
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return psychometricQuestions;
    }
}
