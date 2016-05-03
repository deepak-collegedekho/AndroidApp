package com.collegedekho.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.database.DataBaseHelper;
import com.collegedekho.app.entities.StepByStepChoice;
import com.collegedekho.app.entities.StepByStepQuestion;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bashir on 5/4/16.
 */
public class GCMDialogActivity extends AppCompatActivity implements View.OnClickListener {
    private final int TYPE_RADIO = 1;
    private final int TYPE_CHECKBOX = 2;
    private final int TYPE_SEEKBAR = 3;
    private final int TYPE_TEXT_INPUT = 4;
    private final int TYPE_NUMBER_INPUT = 5;
    private int questionType = 0;
    private LayoutInflater layoutInflater;
    private FrameLayout parentLayout;
    private Button btnOk, btnCancel;

    private TextView txtQuestionText, txtMinVal, txtCurrentVal, txtMaxVal;
    private SeekBar mSeekBar;
    private EditText edtUserInput;
    private SharedPreferences sharedPreferences;
    private ArrayList<StepByStepQuestion> stepByStepQuestions = null;
    private ArrayList<StepByStepChoice> mQuestionChoices;
    private boolean[] selected;
    private int mSelectedPosition = -1;
    private boolean isSingleChoice = false;
    private StepByStepQuestion question;
    private int selectionCount = 0;
    private Bundle userResponseBundle;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gcm_dialog_activity_layout);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFinishOnTouchOutside(false);
        parentLayout = (FrameLayout) findViewById(R.id.gcm_dialog_container);
        layoutInflater = getLayoutInflater();
        txtQuestionText = (TextView) findViewById(R.id.gcm_question);
        sharedPreferences = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
        userResponseBundle = new Bundle();
        snackbar = Snackbar.make(findViewById(R.id.gcm_dialog_container), "You are not connected to Internet", Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(getResources().getColor(R.color.primary_color));
        try {
            if (sharedPreferences.contains(Constants.QUESTIONS_LIST_KEY)) {
                stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null)));
            } else {
                stepByStepQuestions = getQuestionsList();
                String listData = JSON.std.asString(stepByStepQuestions);
                sharedPreferences.edit().putString(Constants.QUESTIONS_LIST_KEY, listData).apply();
            }
            question = getQuestion(stepByStepQuestions);
        } catch (IOException e) {
            e.printStackTrace();
        }

/*        if (stepByStepQuestions != null) {
            if (!stepByStepQuestions.isEmpty()) {
                question = stepByStepQuestions.get(0);
            } else {
                try {
                    stepByStepQuestions = getQuestionsList();
                    question = stepByStepQuestions.get(0);
                } catch (IOException e) {

                }
//                finish();
//                return;
            }
        }
        try {
            stepByStepQuestions = getQuestionsList();
            question = stepByStepQuestions.get(0);
        } catch (IOException e) {

        }*/
//setup();
//        StepByStepFragment stepByStepFragment = StepByStepFragment.newInstance(stepByStepQuestions);
//getSupportFragmentManager().beginTransaction().replace(R.id.gcm_dialog_container,stepByStepFragment).commit();

        if (question != null) {
            String type = question.getType();
            if (type != null && !type.trim().matches("")) {
                if (type.equals("single")) {
                    questionType = TYPE_RADIO;
                } else if (type.equals("multiple")) {
                    questionType = TYPE_CHECKBOX;
                } else if (type.equals("range")) {
                    questionType = TYPE_SEEKBAR;
                } else if (type.equals("text")) {
                    questionType = TYPE_TEXT_INPUT;
                } else if (type.equals("number")) {
                    questionType = TYPE_NUMBER_INPUT;
                }
            }
            if (questionType > 0) {
                View questionLayout = setupContentView(questionType);
                if (questionLayout != null) {
                    parentLayout.addView(questionLayout);
                    prepareContentView(questionType);
                }
            } else {
                finishWithData(null);
            }
        } else {
            finishWithData(null);
        }
/*        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String val = bundle.getString("question_type");
            if (val != null && !val.trim().matches(""))
                try {
                    questionType = Integer.parseInt(val);
                } catch (NumberFormatException e) {

                }
            if (questionType > 0) {
                View questionLayout = setupContentView(questionType);
                if (questionLayout != null) {
                    parentLayout.addView(questionLayout);
                    prepareContentView(questionType);
                }
            } else {
                finish();
            }
        }*/

        btnCancel = (Button) findViewById(R.id.gcm_btn_cancel);
        btnCancel.setOnClickListener(this);
        btnOk = (Button) findViewById(R.id.gcm_btn_ok);
        btnOk.setOnClickListener(this);
    }

    private View setupContentView(int questionType) {
        View questionLayout = null;
        switch (questionType) {
            case TYPE_RADIO:
                questionLayout = layoutInflater.inflate(R.layout.gcm_dialog_mcq_layout, null);
                break;

            case TYPE_CHECKBOX:
                questionLayout = layoutInflater.inflate(R.layout.gcm_dialog_checkbox_layout, null);
                break;

            case TYPE_SEEKBAR:
                questionLayout = layoutInflater.inflate(R.layout.gcm_dialog_seekbar_layout, null);
                break;

            case TYPE_TEXT_INPUT:
                questionLayout = layoutInflater.inflate(R.layout.gcm_dialog_input_layout, null);
                break;

            case TYPE_NUMBER_INPUT:
                questionLayout = layoutInflater.inflate(R.layout.gcm_dialog_input_layout, null);
                break;
        }
        return questionLayout;
    }

    private void prepareContentView(int questionType) {
        switch (questionType) {
            case TYPE_RADIO:
                setupRadioDialog();
                break;

            case TYPE_CHECKBOX:
                setupCheckboxDialog();
                break;

            case TYPE_SEEKBAR:
                setupSeekBarDialog();
                break;

            case TYPE_TEXT_INPUT:
                setupInputDialog(false);
                break;

            case TYPE_NUMBER_INPUT:
                setupInputDialog(true);
                break;
        }
    }

    private void setupInputDialog(boolean isNumber) {
        edtUserInput = (EditText) findViewById(R.id.gcm_edittext_input);
        txtQuestionText.setText(question.getText());
        if (isNumber) {
            edtUserInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            edtUserInput.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        edtUserInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtUserInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupSeekBarDialog() {
        txtMinVal = (TextView) findViewById(R.id.min_value);
        txtCurrentVal = (TextView) findViewById(R.id.current_value);
        txtMaxVal = (TextView) findViewById(R.id.max_value);
        mSeekBar = (SeekBar) findViewById(R.id.gcm_seekbar_input);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtCurrentVal.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void onPositiveResponse(int questionType) {
        switch (questionType) {
            case TYPE_RADIO:
                if (mSelectedPosition >= 0) {
                    processRadioDialog();
                } else {
                    displaySnackBar(R.string.GCM_DIALOG_SELECTION_ERROR);
                }
                break;

            case TYPE_CHECKBOX:
                if (selectionCount > 0) {
                    processCheckboxDialog();
                    finishWithData(null);
                } else {
                    displaySnackBar(R.string.GCM_DIALOG_SELECTION_ERROR);
                }
                break;

            case TYPE_SEEKBAR:
                processSeekBarDialog();
                finishWithData(null);
                break;

            case TYPE_TEXT_INPUT:
                processInputDialog(false);
                break;
            case TYPE_NUMBER_INPUT:
                processInputDialog(true);
                break;
        }
    }

    private void onCancelDialog() {
        if (stepByStepQuestions != null && !stepByStepQuestions.isEmpty()) {
            StepByStepQuestion question = stepByStepQuestions.remove(0);
            stepByStepQuestions.add(question);
            try {
                String listData = JSON.std.asString(stepByStepQuestions);
                sharedPreferences.edit().putString(Constants.QUESTIONS_LIST_KEY, listData).apply();
            } catch (IOException e) {

            }
        }
    }

    private void processInputDialog(boolean isNumber) {
        String userInput = edtUserInput.getText().toString();
        if (!userInput.trim().matches("")) {
            if (isNumber) {
                if (userInput.length() < 10 || !TextUtils.isDigitsOnly(userInput)) {
                    edtUserInput.setError("Number not valid");
                } else {
                    questionAnswered(question);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(getResourceString(R.string.USER_PHONE), userInput);
                    finishWithData(hashMap);
                }
            } else {
                if (userInput.length() > 4) {
                    questionAnswered(question);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(getResourceString(R.string.USER_NAME), userInput);
                    finishWithData(hashMap);
                } else {
                    edtUserInput.setError("Name is too short");
                }
            }
        } else {
            edtUserInput.setError("Invalid Input");
        }
    }

    private void processSeekBarDialog() {

    }

    private void processCheckboxDialog() {
        if (mQuestionChoices == null || mQuestionChoices.isEmpty()) {
            return;
        }
        ArrayList<StepByStepChoice> userChoiceList = new ArrayList<>();
        int size = mQuestionChoices.size();
        for (int i = 0; i < size; i++) {
            if (selected[i]) {
                userChoiceList.add(mQuestionChoices.get(i));
            }
        }
        updateFilters(userChoiceList);
        questionAnswered(question);
    }

    private void processRadioDialog() {
        if (mQuestionChoices == null || mQuestionChoices.isEmpty() && mSelectedPosition < 0) {
            return;
        }
        ArrayList<StepByStepChoice> userChoiceList = new ArrayList<>();
        StepByStepChoice userChoice = mQuestionChoices.get(mSelectedPosition);
        userChoiceList.add(userChoice);
        questionAnswered(question);
        updateFilters(userChoiceList);
    }

    private void questionAnswered(StepByStepQuestion question) {
        if (question == null || !stepByStepQuestions.contains(question)) {
            return;
        }
        stepByStepQuestions.remove(question);
        try {
            String listData = JSON.std.asString(stepByStepQuestions);
            sharedPreferences.edit().putString(Constants.QUESTIONS_LIST_KEY, listData).apply();
        } catch (IOException e) {

        }
    }

    private void setupCheckboxDialog() {
        RecyclerView checkBoxRecycler = (RecyclerView) findViewById(R.id.gcm_checkbox_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        checkBoxRecycler.setLayoutManager(layoutManager);
        mQuestionChoices = question.getChoices();
        mQuestionChoices.addAll(question.getOther_choices());
        ChoiceTypeQuestionAdapter adapter = new ChoiceTypeQuestionAdapter(this, mQuestionChoices, question.getType());
        checkBoxRecycler.setAdapter(adapter);
        txtQuestionText.setText(question.getText());
    }

    private void setupRadioDialog() {
        RecyclerView radioRecycler = (RecyclerView) findViewById(R.id.gcm_radio_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        radioRecycler.setLayoutManager(layoutManager);
        mQuestionChoices = question.getChoices();
        mQuestionChoices.addAll(question.getOther_choices());
        ChoiceTypeQuestionAdapter adapter = new ChoiceTypeQuestionAdapter(this, mQuestionChoices, question.getType());
        radioRecycler.setAdapter(adapter);
        txtQuestionText.setText(question.getText());
    }

    public void displaySnackBar(int messageId) {
        try {
            if (snackbar != null && !snackbar.isShown()) {
                snackbar.setText(getResources().getString(messageId));
                snackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    private StepByStepQuestion getQuestion(ArrayList<StepByStepQuestion> questionArrayList) throws IOException {

        if (questionArrayList != null && !questionArrayList.isEmpty()) {
            StepByStepQuestion question = questionArrayList.get(0);
            if (question.getName().equals("gender")) {
                if (MainActivity.user.getGender() == null) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
            } else if (question.getName().equals("social_category")) {
                if (MainActivity.user.getSocial_category() == null) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
            } else if (question.getName().equals("year_of_admission")) {
                if (MainActivity.user.getYear_of_admission() == null) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
            } else if (question.getName().equals("preferred_mode")) {
                if (MainActivity.user.getPreferred_mode() == null) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
            } else if (question.getName().equals("name")) {
                if (MainActivity.user.getUsername() == null) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
            } else if (question.getName().equals("number")) {
                if (MainActivity.user.getPhone_no() == null) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
            }else if (question.getName().equals("preferred_streams")) {
                if (MainActivity.user.getStream_name() == null) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
            } else {
                return question;
            }
        }
        return null;
    }

    private ArrayList<StepByStepQuestion> getQuestionsList() throws IOException {
        String name = "{\"selected_choice\":null,\"last\":0,\"name\":\"name\",\"text\":\"What is your name?\",\"image\":0,\"required\":1,\"type\":\"text\",\"other_choices\":[],\"choices\":[]}";

        String phoneNumber = "{\"selected_choice\":null,\"last\":0,\"name\":\"number\",\"text\":\"Where should we contact you?\",\"image\":0,\"required\":1,\"type\":\"number\",\"other_choices\":[],\"choices\":[]}";

        String preferredMode = "{\"selected_choice\":null,\"last\":0,\"name\":\"preferred_mode\",\"text\":\"What is your preferred mode?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":1,\"name\":\"Regular\"},{\"image\":\"\",\"id\":2,\"name\":\"Part Time\"},{\"image\":\"\",\"id\":4,\"name\":\"Distancce / Correspondence\"},{\"image\":\"\",\"id\":5,\"name\":\"Executive\"},{\"image\":\"\",\"id\":6,\"name\":\"Online / E-Learning\"}]}";

        String yearOfAdmission = "{\"selected_choice\":null,\"last\":0,\"name\":\"year_of_admission\",\"text\":\"When did you graduate?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":2015,\"name\":\"2015\"},{\"image\":\"\",\"id\":2016,\"name\":\"2016\"},{\"image\":\"\",\"id\":2017,\"name\":\"2017\"},{\"image\":\"\",\"id\":2018,\"name\":\"2018\"},{\"image\":\"\",\"id\":2019,\"name\":\"2019\"},{\"image\":\"\",\"id\":2020,\"name\":\"2020\"}]}";

//        String loanRequired = "{\"selected_choice\":null,\"last\":0,\"name\":\"loan_required\",\"text\":\"Do you require loan?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":1,\"name\":\"Doesnt need loan\"},{\"image\":\"\",\"id\":2,\"name\":\"Needs loan\"},{\"image\":\"\",\"id\":3,\"name\":\"Maybe needs loan\"}]}";

        String gender = "{\"selected_choice\":null,\"last\":0,\"name\":\"gender\",\"text\":\"Select your gender\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":1,\"name\":\"Male\"},{\"image\":\"\",\"id\":2,\"name\":\"Female\"}]}";

        String feesRange = "{\"selected_choice\":null,\"last\":0,\"name\":\"fees_range\",\"text\":\"What is your preferred fee range?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"uri\":\"lt1\",\"id\":1,\"name\":\"< 1 Lakh\"},{\"image\":\"\",\"uri\":\"gt1_lt2\",\"id\":2,\"name\":\"< 2 Lakh\"},{\"image\":\"\",\"uri\":\"gt2_lt3\",\"id\":3,\"name\":\"< 4 Lakh\"},{\"image\":\"\",\"uri\":\"gt3_lt4\",\"id\":3,\"name\":\"< 5 Lakh\"},{\"image\":\"\",\"uri\":\"gt5\",\"id\":4,\"name\":\"Above 5 Lakh\"}]}";

        String preferredDegree = "{\"selected_choice\":null,\"last\":0,\"name\":\"preferred_degree\",\"text\":\"What degree are you looking for?\",\"image\":1,\"required\":1,\"type\":\"single\",\"other_choices\":[{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/banking-l_Yf4vlTI.png\",\"id\":1,\"name\":\"Commerce / Banking\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/design-l_V4ioQbT.png\",\"id\":2,\"name\":\"Design\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/management-l_2CUexFe.png\",\"id\":4,\"name\":\"Management\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/hospitality-l_nTFEyei.png\",\"id\":5,\"name\":\"Hospitality / Aviation\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/it-l_Sk3FDbD.png\",\"id\":6,\"name\":\"IT\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/mass-l_nJYRxT6.png\",\"id\":7,\"name\":\"Mass Comm\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/medicine-l_UXFAzql.png\",\"id\":8,\"name\":\"Medical\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/retail-l_d5b7NNv.png\",\"id\":9,\"name\":\"Retail\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/arts-l.png\",\"id\":12,\"name\":\"Arts\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/law-l_icYZJ7Z.png\",\"id\":13,\"name\":\"Law / Humanities\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/science-l_Y1CiAX7.png\",\"id\":14,\"name\":\"Sciences\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/vocational-l_w9iINmW.png\",\"id\":15,\"name\":\"Vocational\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/others-l_jYO9R6G.png\",\"id\":16,\"name\":\"Others\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/physical-l_Oh2tYYs.png\",\"id\":18,\"name\":\"Physical Education\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/multimedia-l_dyO4KwI.png\",\"id\":32,\"name\":\"Animation / Multimedia\"}],\"choices\":[{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/engineering-l_wUddKPm.png\",\"id\":3,\"name\":\"Engineering\"}]}";

        String preferredStream = "{\"selected_choice\":null,\"last\":0,\"name\":\"preferred_streams\",\"text\":\"What is your preferred stream?\",\"image\":1,\"required\":1,\"type\":\"single\",\"other_choices\":[{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/banking-l_Yf4vlTI.png\",\"uri\":\"commerce-banking\",\"id\":1,\"name\":\"Commerce / Banking\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/design-l_V4ioQbT.png\",\"uri\":\"design\",\"id\":2,\"name\":\"Design\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/engineering-l_wUddKPm.png\",\"uri\":\"engineering\",\"id\":3,\"name\":\"Engineering\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/management-l_2CUexFe.png\",\"uri\":\"mba\",\"id\":4,\"name\":\"Management\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/hospitality-l_nTFEyei.png\",\"uri\":\"hospitality-aviation\",\"id\":5,\"name\":\"Hospitality / Aviation\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/it-l_Sk3FDbD.png\",\"uri\":\"information-technology\",\"id\":6,\"name\":\"IT\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/mass-l_nJYRxT6.png\",\"uri\":\"mass-comm\",\"id\":7,\"name\":\"Mass Comm\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/medicine-l_UXFAzql.png\",\"uri\":\"medical\",\"id\":8,\"name\":\"Medical\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/retail-l_d5b7NNv.png\",\"uri\":\"retail\",\"id\":9,\"name\":\"Retail\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/arts-l.png\",\"uri\":\"arts\",\"id\":12,\"name\":\"Arts\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/law-l_icYZJ7Z.png\",\"uri\":\"law-humanities\",\"id\":13,\"name\":\"Law / Humanities\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/science-l_Y1CiAX7.png\",\"uri\":\"sciences\",\"id\":14,\"name\":\"Sciences\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/vocational-l_w9iINmW.png\",\"uri\":\"vocational\",\"id\":15,\"name\":\"Vocational\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/others-l_jYO9R6G.png\",\"uri\":\"others\",\"id\":16,\"name\":\"Others\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/physical-l_Oh2tYYs.png\",\"uri\":\"physical-education\",\"id\":18,\"name\":\"Physical Education\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/multimedia-l_dyO4KwI.png\",\"uri\":\"animation\",\"id\":32,\"name\":\"Animation / Multimedia\"}],\"choices\":[]}";

        String scoreType = "{\"selected_choice\":null,\"last\":0,\"name\":\"score_type\",\"text\":\"What is your score type?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":1,\"name\":\"Marks\"},{\"image\":\"\",\"id\":2,\"name\":\"Grades\"},{\"image\":\"\",\"id\":3,\"name\":\"Percentage\"},{\"image\":\"\",\"id\":4,\"name\":\"Rank\"},{\"image\":\"\",\"id\":5,\"name\":\"Percentile\"}]}";

        String preferredStates = "{\"selected_choice\":null,\"last\":0,\"name\":\"preferred_states\",\"text\":\"Which all states do you prefer studying at?\",\"image\":0,\"required\":0,\"type\":\"multiple\",\"other_choices\":[],\"choices\":[{\"id\":1,\"image\":\"\",\"name\":\"Andaman & Nicobar\",\"uri\":\"andaman-nicobar\"},{\"id\":2,\"image\":\"\",\"name\":\"Andhra Pradesh\",\"uri\":\"andhra_andhra_pradesh\"},{\"id\":3,\"image\":\"\",\"name\":\"Arunachal Pradesh\",\"uri\":\"arunachal_arunachal_pradesh\"},{\"id\":4,\"image\":\"\",\"name\":\"Assam\",\"uri\":\"assam\"},{\"id\":5,\"image\":\"\",\"name\":\"Bihar\",\"uri\":\"bihar\"},{\"id\":6,\"image\":\"\",\"name\":\"Chandigarh\",\"uri\":\"chandigarh\"},{\"id\":7,\"image\":\"\",\"name\":\"Chhattisgarh\",\"uri\":\"chhattisgarh\"},{\"id\":8,\"image\":\"\",\"name\":\"Dadra and Nagar Haveli\",\"uri\":\"dadra_dadra_and_nagar_haveli\"},{\"id\":9,\"image\":\"\",\"name\":\"Daman and Diu\",\"uri\":\"daman_daman_and_diu\"},{\"id\":10,\"image\":\"\",\"name\":\"Delhi\",\"uri\":\"delhi\"},{\"id\":11,\"image\":\"\",\"name\":\"Goa\",\"uri\":\"goa\"},{\"id\":12,\"image\":\"\",\"name\":\"Gujarat\",\"uri\":\"gujarat\"},{\"id\":13,\"image\":\"\",\"name\":\"Haryana\",\"uri\":\"haryana\"},{\"id\":14,\"image\":\"\",\"name\":\"Himachal Pradesh\",\"uri\":\"himachal_himachal_pradesh\"},{\"id\":15,\"image\":\"\",\"name\":\"Jammu & Kashmir\",\"uri\":\"jammu-kashmir\"},{\"id\":16,\"image\":\"\",\"name\":\"Jharkhand\",\"uri\":\"jharkhand\"},{\"id\":17,\"image\":\"\",\"name\":\"Karnataka\",\"uri\":\"karnataka\"},{\"id\":18,\"image\":\"\",\"name\":\"Kerala\",\"uri\":\"kerala\"},{\"id\":19,\"image\":\"\",\"name\":\"Lakshadweep\",\"uri\":\"lakshadweep\"},{\"id\":20,\"image\":\"\",\"name\":\"Madhya Pradesh\",\"uri\":\"madhya_madhya_pradesh\"},{\"id\":21,\"image\":\"\",\"name\":\"Maharashtra\",\"uri\":\"maharashtra\"},{\"id\":22,\"image\":\"\",\"name\":\"Manipur\",\"uri\":\"manipur\"},{\"id\":23,\"image\":\"\",\"name\":\"Meghalaya\",\"uri\":\"meghalaya\"},{\"id\":24,\"image\":\"\",\"name\":\"Mizoram\",\"uri\":\"mizoram\"},{\"id\":25,\"image\":\"\",\"name\":\"Nagaland\",\"uri\":\"nagaland\"},{\"id\":26,\"image\":\"\",\"name\":\"Orissa\",\"uri\":\"orissa\"},{\"id\":27,\"image\":\"\",\"name\":\"Pondicherry\",\"uri\":\"pondicherry\"},{\"id\":28,\"image\":\"\",\"name\":\"Punjab\",\"uri\":\"punjab\"},{\"id\":29,\"image\":\"\",\"name\":\"Rajasthan\",\"uri\":\"rajasthan\"},{\"id\":30,\"image\":\"\",\"name\":\"Sikkim\",\"uri\":\"sikkim\"},{\"id\":31,\"image\":\"\",\"name\":\"Tamil Nadu\",\"uri\":\"tamil_tamil_nadu\"},{\"id\":32,\"image\":\"\",\"name\":\"Tripura\",\"uri\":\"tripura\"},{\"id\":33,\"image\":\"\",\"name\":\"Uttaranchal\",\"uri\":\"uttaranchal\"},{\"id\":34,\"image\":\"\",\"name\":\"Uttar Pradesh\",\"uri\":\"uttar_uttar_pradesh\"},{\"id\":35,\"image\":\"\",\"name\":\"West Bengal\",\"uri\":\"west_west_bengal\"},{\"id\":36,\"image\":\"\",\"name\":\"Telangana\",\"uri\":\"telangana\"}]}";
//
        String preferredCities = readFileFromAssets("cities.txt");

        String preferredSpecialization = "{\"selected_choice\":null,\"last\":0,\"name\":\"preferred_specialization\",\"text\":\"What do you want to specialize in?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":110,\"name\":\"Civil Engineering\"},{\"image\":\"\",\"id\":117,\"name\":\"Mechanical Engineering\"},{\"image\":\"\",\"id\":469,\"name\":\"Engineering\"},{\"image\":\"\",\"id\":21,\"name\":\"Architecture\"},{\"image\":\"\",\"id\":314,\"name\":\"Nanotechnology\"},{\"image\":\"\",\"id\":196,\"name\":\"Electrical Enginedering\"},{\"image\":\"\",\"id\":1447,\"name\":\"Entrepreneurship\"},{\"image\":\"\",\"id\":105,\"name\":\"Aeronautical Engineering\"},{\"image\":\"\",\"id\":111,\"name\":\"Computer Science\"},{\"image\":\"\",\"id\":230,\"name\":\"Software Engineering\"},{\"image\":\"\",\"id\":109,\"name\":\"Chemical Engineering\"},{\"image\":\"\",\"id\":211,\"name\":\"Automobile\"},{\"image\":\"\",\"id\":795,\"name\":\"Image Processing\"},{\"image\":\"\",\"id\":610,\"name\":\"Automation\"},{\"image\":\"\",\"id\":633,\"name\":\"Mechatronics\"},{\"image\":\"\",\"id\":818,\"name\":\"Remote Sensing\"},{\"image\":\"\",\"id\":116,\"name\":\"Marine Engineering\"},{\"image\":\"\",\"id\":644,\"name\":\"Planning\"},{\"image\":\"\",\"id\":1415,\"name\":\"Computer Engineering\"},{\"image\":\"\",\"id\":264,\"name\":\"Wireless Communication\"},{\"image\":\"\",\"id\":276,\"name\":\"Telecommunication\"},{\"image\":\"\",\"id\":321,\"name\":\"Power Electronics\"},{\"image\":\"\",\"id\":639,\"name\":\"Aerospace Engineering\"},{\"image\":\"\",\"id\":467,\"name\":\"Any\"},{\"image\":\"\",\"id\":799,\"name\":\"Neural Networks\"},{\"image\":\"\",\"id\":362,\"name\":\"Petroleum Engineering\"},{\"image\":\"\",\"id\":115,\"name\":\"Industrial Engineering\"},{\"image\":\"\",\"id\":1142,\"name\":\"Mobile Repairing\"},{\"image\":\"\",\"id\":278,\"name\":\"Environmental Engineering\"},{\"image\":\"\",\"id\":586,\"name\":\"Conservation\"},{\"image\":\"\",\"id\":769,\"name\":\"Web Technology\"},{\"image\":\"\",\"id\":209,\"name\":\"Machine Design\"},{\"image\":\"\",\"id\":216,\"name\":\"Production Engineering\"},{\"image\":\"\",\"id\":767,\"name\":\"Control Systems\"},{\"image\":\"\",\"id\":310,\"name\":\"Structural Engineering\"},{\"image\":\"\",\"id\":112,\"name\":\"Electrical and Electronics Engineering\"},{\"image\":\"\",\"id\":692,\"name\":\"Construction Management\"},{\"image\":\"\",\"id\":723,\"name\":\"Operations Research\"},{\"image\":\"\",\"id\":778,\"name\":\"Telematics\"},{\"image\":\"\",\"id\":20,\"name\":\"Aircraft Maintenance Engineering\"},{\"image\":\"\",\"id\":1086,\"name\":\"Green Technology\"},{\"image\":\"\",\"id\":604,\"name\":\"Thermal Engineering\"},{\"image\":\"\",\"id\":627,\"name\":\"Fashion Technology\"},{\"image\":\"\",\"id\":1430,\"name\":\"Industrial Safety\"},{\"image\":\"\",\"id\":1444,\"name\":\"Theory\"},{\"image\":\"\",\"id\":777,\"name\":\"Electronics Engineering\"},{\"image\":\"\",\"id\":1045,\"name\":\"Transportation Engineering\"},{\"image\":\"\",\"id\":800,\"name\":\"Parallel Computing\"},{\"image\":\"\",\"id\":322,\"name\":\"Mining Engineering\"},{\"image\":\"\",\"id\":1093,\"name\":\"Industrial Training\"},{\"image\":\"\",\"id\":1110,\"name\":\"Computer Technology\"},{\"image\":\"\",\"id\":898,\"name\":\"Instrumentation and Control Engineering\"},{\"image\":\"\",\"id\":339,\"name\":\"Biomedical\"},{\"image\":\"\",\"id\":750,\"name\":\"Signal Processing\"},{\"image\":\"\",\"id\":776,\"name\":\"Highway Engineering\"},{\"image\":\"\",\"id\":780,\"name\":\"Computer Integrated Manufacturing\"},{\"image\":\"\",\"id\":113,\"name\":\"Electronics and Communication\"},{\"image\":\"\",\"id\":372,\"name\":\"Textile Engineering\"},{\"image\":\"\",\"id\":1235,\"name\":\"Hydro\"},{\"image\":\"\",\"id\":772,\"name\":\"Communication Systems\"},{\"image\":\"\",\"id\":781,\"name\":\"Electronics Circuits\"},{\"image\":\"\",\"id\":1558,\"name\":\"Dairy Technology\"},{\"image\":\"\",\"id\":794,\"name\":\"High Voltage Engineering\"},{\"image\":\"\",\"id\":1079,\"name\":\"Petrochemical Engineering\"},{\"image\":\"\",\"id\":318,\"name\":\"Manufacturing Engineering\"},{\"image\":\"\",\"id\":1176,\"name\":\"Welding Technology\"},{\"image\":\"\",\"id\":1435,\"name\":\"Reliability Engineering\"},{\"image\":\"\",\"id\":429,\"name\":\"Medical Electronics\"},{\"image\":\"\",\"id\":1549,\"name\":\"Electrical Maintenance\"},{\"image\":\"\",\"id\":791,\"name\":\"Engineering Design\"},{\"image\":\"\",\"id\":585,\"name\":\"Urban Design\"},{\"image\":\"\",\"id\":1115,\"name\":\"Information Science\"},{\"image\":\"\",\"id\":643,\"name\":\"Sustainable Architecture\"},{\"image\":\"\",\"id\":1437,\"name\":\"Applied Mechanics\"},{\"image\":\"\",\"id\":217,\"name\":\"Power Systems\"},{\"image\":\"\",\"id\":222,\"name\":\"Metallurgical Engineering\"},{\"image\":\"\",\"id\":266,\"name\":\"Soil Mechanics and Foundation Engineering\"},{\"image\":\"\",\"id\":784,\"name\":\"Control Engineering\"},{\"image\":\"\",\"id\":807,\"name\":\"Tube well\"},{\"image\":\"\",\"id\":1211,\"name\":\"Optoelectronics\"},{\"image\":\"\",\"id\":1214,\"name\":\"Power Distribution\"},{\"image\":\"\",\"id\":208,\"name\":\"Electronics and Instrumentation\"},{\"image\":\"\",\"id\":272,\"name\":\"Nuclear Engineering\"},{\"image\":\"\",\"id\":788,\"name\":\"Design for Manufacturing\"},{\"image\":\"\",\"id\":1105,\"name\":\"Biochemical\"},{\"image\":\"\",\"id\":360,\"name\":\"Ocean Engineering\"},{\"image\":\"\",\"id\":1385,\"name\":\"Ship Building\"},{\"image\":\"\",\"id\":1457,\"name\":\"Audio Engineering\"},{\"image\":\"\",\"id\":693,\"name\":\"Laser Technology\"},{\"image\":\"\",\"id\":277,\"name\":\"Water resources Engineering\"},{\"image\":\"\",\"id\":114,\"name\":\"General Engineering\"},{\"image\":\"\",\"id\":887,\"name\":\"Applied Electronics\"},{\"image\":\"\",\"id\":1149,\"name\":\"Modern Communication\"},{\"image\":\"\",\"id\":915,\"name\":\"Polymer Science\"},{\"image\":\"\",\"id\":1185,\"name\":\"Process Design\"},{\"image\":\"\",\"id\":418,\"name\":\"Ceramic Engineering\"},{\"image\":\"\",\"id\":338,\"name\":\"Earthquake Engineering\"},{\"image\":\"\",\"id\":1143,\"name\":\"Electrical Technician\"},{\"image\":\"\",\"id\":1445,\"name\":\"Communication Network\"},{\"image\":\"\",\"id\":1471,\"name\":\"Construction Engineering\"},{\"image\":\"\",\"id\":768,\"name\":\"Intelligent Systems\"},{\"image\":\"\",\"id\":605,\"name\":\"Fire Engineering\"},{\"image\":\"\",\"id\":1315,\"name\":\"Network Engineering\"},{\"image\":\"\",\"id\":1316,\"name\":\"E-Security\"},{\"image\":\"\",\"id\":325,\"name\":\"Offshore Structures\"},{\"image\":\"\",\"id\":370,\"name\":\"Energy Technology\"},{\"image\":\"\",\"id\":1183,\"name\":\"Rubber Technology\"},{\"image\":\"\",\"id\":988,\"name\":\"Analytical Techniques\"},{\"image\":\"\",\"id\":775,\"name\":\"Geotechnical\"},{\"image\":\"\",\"id\":805,\"name\":\"Tool Engineering\"},{\"image\":\"\",\"id\":1062,\"name\":\"Hydraulic Engineering\"},{\"image\":\"\",\"id\":1106,\"name\":\"Paint Technology\"},{\"image\":\"\",\"id\":340,\"name\":\"Geo Engineering\"},{\"image\":\"\",\"id\":1150,\"name\":\"Micro Electronics\"},{\"image\":\"\",\"id\":785,\"name\":\"Cyber Forensics and Information Security\"},{\"image\":\"\",\"id\":298,\"name\":\"VLSI System Design\"},{\"image\":\"\",\"id\":642,\"name\":\"Traditional Architecture\"},{\"image\":\"\",\"id\":1186,\"name\":\"Cryogenic Engineering\"},{\"image\":\"\",\"id\":1193,\"name\":\"Software System\"},{\"image\":\"\",\"id\":1202,\"name\":\"Electrical System Design\"},{\"image\":\"\",\"id\":1018,\"name\":\"Aviation Engineering\"},{\"image\":\"\",\"id\":1026,\"name\":\"Mechanical System Design\"},{\"image\":\"\",\"id\":796,\"name\":\"Infrastructure Engineering\"},{\"image\":\"\",\"id\":797,\"name\":\"Mechtronics\"},{\"image\":\"\",\"id\":1084,\"name\":\"Surface Coating Technology\"},{\"image\":\"\",\"id\":1391,\"name\":\"Harbour Engineering\"},{\"image\":\"\",\"id\":640,\"name\":\"Environmental Planning\"},{\"image\":\"\",\"id\":1234,\"name\":\"Repair & Maintenance\"},{\"image\":\"\",\"id\":1291,\"name\":\"Industrial Automation and Robotics\"},{\"image\":\"\",\"id\":1075,\"name\":\"Pre Stressed Concrete\"},{\"image\":\"\",\"id\":1078,\"name\":\"Sugar Technology\"},{\"image\":\"\",\"id\":1432,\"name\":\"Mineral Engineering\"},{\"image\":\"\",\"id\":766,\"name\":\"Computational Engineering\"},{\"image\":\"\",\"id\":1372,\"name\":\"Solid State Lighting\"},{\"image\":\"\",\"id\":608,\"name\":\"Electronics and Computer Engineering\"},{\"image\":\"\",\"id\":783,\"name\":\"Computer and Communication\"},{\"image\":\"\",\"id\":1215,\"name\":\"Heat Power Engineering\"},{\"image\":\"\",\"id\":1482,\"name\":\"Drilling Engineering\"},{\"image\":\"\",\"id\":1055,\"name\":\"Combustion Engineering\"},{\"image\":\"\",\"id\":1132,\"name\":\"Advanced Manufacturing Systems\"},{\"image\":\"\",\"id\":1440,\"name\":\"Applied Geophysics\"},{\"image\":\"\",\"id\":674,\"name\":\"Commercial Practice\"},{\"image\":\"\",\"id\":200,\"name\":\"Information Technology (IT)\"},{\"image\":\"\",\"id\":790,\"name\":\"Electrical Power Engineering\"},{\"image\":\"\",\"id\":798,\"name\":\"Microwave and Radar\"},{\"image\":\"\",\"id\":1066,\"name\":\"Propulsion Engineering\"},{\"image\":\"\",\"id\":1071,\"name\":\"Advanced Material Technology\"},{\"image\":\"\",\"id\":1144,\"name\":\"TV Technician\"},{\"image\":\"\",\"id\":1174,\"name\":\"Technology Engineering\"},{\"image\":\"\",\"id\":1254,\"name\":\"Machatronics\"},{\"image\":\"\",\"id\":771,\"name\":\"Fibre Technology\"},{\"image\":\"\",\"id\":1049,\"name\":\"Multimedia Engineering\"},{\"image\":\"\",\"id\":1194,\"name\":\"Electrical and Communication\"},{\"image\":\"\",\"id\":1044,\"name\":\"Industrial Metallurgy\"},{\"image\":\"\",\"id\":350,\"name\":\"Print and Media Technology\"},{\"image\":\"\",\"id\":353,\"name\":\"Bioinformatics Engineering\"},{\"image\":\"\",\"id\":108,\"name\":\"Bio Tech Engineering\"},{\"image\":\"\",\"id\":1446,\"name\":\"German Studies\"},{\"image\":\"\",\"id\":428,\"name\":\"CAD & CAM\"},{\"image\":\"\",\"id\":718,\"name\":\"Apparel Production Management\"},{\"image\":\"\",\"id\":218,\"name\":\"Plastic and Polymer\"},{\"image\":\"\",\"id\":773,\"name\":\"Computer Science & Technology\"},{\"image\":\"\",\"id\":269,\"name\":\"Materials Science & Engineering\"},{\"image\":\"\",\"id\":326,\"name\":\"Traffic and Transportation Planning\"},{\"image\":\"\",\"id\":329,\"name\":\"VLSI & Embedded Systems\"},{\"image\":\"\",\"id\":1187,\"name\":\"Structure Analysis and Design\"},{\"image\":\"\",\"id\":782,\"name\":\"Communication And Information System\"},{\"image\":\"\",\"id\":1304,\"name\":\"Electronics Equipment Maintenance\"},{\"image\":\"\",\"id\":584,\"name\":\"Real state Development\"},{\"image\":\"\",\"id\":359,\"name\":\"Pulp and Paper Engineering\"},{\"image\":\"\",\"id\":1191,\"name\":\"Freshman Engineering\"},{\"image\":\"\",\"id\":213,\"name\":\"Safety and Fire\"},{\"image\":\"\",\"id\":2050,\"name\":\"Sustainable Technologies\"},{\"image\":\"\",\"id\":1796,\"name\":\"Aeroplane and Power Plant\"},{\"image\":\"\",\"id\":2052,\"name\":\"Data Analytics\"},{\"image\":\"\",\"id\":1797,\"name\":\"Helicopter and Power Plant\"},{\"image\":\"\",\"id\":2053,\"name\":\"Rural Development and Technology\"},{\"image\":\"\",\"id\":774,\"name\":\"Computer Network and Information Security\"},{\"image\":\"\",\"id\":1798,\"name\":\"Avionics Stream Electrical System\"},{\"image\":\"\",\"id\":2056,\"name\":\"Sensor System Technology\"},{\"image\":\"\",\"id\":2057,\"name\":\"Automotive Electronics\"},{\"image\":\"\",\"id\":2063,\"name\":\"Digital Communication\"},{\"image\":\"\",\"id\":2065,\"name\":\"Maintenance Engineering\"},{\"image\":\"\",\"id\":786,\"name\":\"Digital Electronics Communication Systems\"},{\"image\":\"\",\"id\":2066,\"name\":\"Building Construction Technology\"},{\"image\":\"\",\"id\":19,\"name\":\"Advanced Technical Courses\"},{\"image\":\"\",\"id\":787,\"name\":\"Digital Electronics and Communication Engineering\"},{\"image\":\"\",\"id\":1556,\"name\":\"House Wiring and Coil Winding\"},{\"image\":\"\",\"id\":2068,\"name\":\"Non-Conventional Sources of Energy\"},{\"image\":\"\",\"id\":789,\"name\":\"Electronics Design Engineering\"},{\"image\":\"\",\"id\":2069,\"name\":\"Solar Cell\"},{\"image\":\"\",\"id\":793,\"name\":\"Geoinformatics & Surveying Technology\"},{\"image\":\"\",\"id\":1050,\"name\":\"Laptop and Cell Phone Technology\"},{\"image\":\"\",\"id\":1052,\"name\":\"Fitter and Electrician\"},{\"image\":\"\",\"id\":1820,\"name\":\"Wireman\"},{\"image\":\"\",\"id\":801,\"name\":\"Power and Industrial Drives\"},{\"image\":\"\",\"id\":1569,\"name\":\"Automotive Engineering\"},{\"image\":\"\",\"id\":802,\"name\":\"Power Engineering And Energy Systems\"},{\"image\":\"\",\"id\":293,\"name\":\"Photonics Science and Engineering\"},{\"image\":\"\",\"id\":808,\"name\":\"Refrigeration and Air Conditioning Engineerig\"},{\"image\":\"\",\"id\":1834,\"name\":\"Polytechnic\"},{\"image\":\"\",\"id\":1067,\"name\":\"Guidance and Navigational Control\"},{\"image\":\"\",\"id\":1323,\"name\":\"Bell Metal Utensil Manufacturing\"},{\"image\":\"\",\"id\":1836,\"name\":\"Fabrication Technology\"},{\"image\":\"\",\"id\":1837,\"name\":\"Chemical Technology\"},{\"image\":\"\",\"id\":1838,\"name\":\"Packaging Technology\"},{\"image\":\"\",\"id\":1080,\"name\":\"Dyestuff Technology\"},{\"image\":\"\",\"id\":1082,\"name\":\"Polymer Engineering and Technology\"},{\"image\":\"\",\"id\":1850,\"name\":\"Plumber\"},{\"image\":\"\",\"id\":1083,\"name\":\"Oils, Oleo Chemicals and Surfactants Technology\"},{\"image\":\"\",\"id\":1851,\"name\":\"Design Builder Software\"},{\"image\":\"\",\"id\":1852,\"name\":\"Energy Efficient Buildings\"},{\"image\":\"\",\"id\":1085,\"name\":\"Fibers and Textile Processing Technology\"},{\"image\":\"\",\"id\":1087,\"name\":\"Perfumes and Flavours Technology\"},{\"image\":\"\",\"id\":1601,\"name\":\"Electrical Machine\"},{\"image\":\"\",\"id\":1603,\"name\":\"Virtual Prototyping and Digital Manufacturing\"},{\"image\":\"\",\"id\":324,\"name\":\"Agricultural and Food Engineering\"},{\"image\":\"\",\"id\":1860,\"name\":\"Machinist\"},{\"image\":\"\",\"id\":1862,\"name\":\"Foundry Technology\"},{\"image\":\"\",\"id\":1870,\"name\":\"Security Electronic\"},{\"image\":\"\",\"id\":1871,\"name\":\"Motor Rewinding\"},{\"image\":\"\",\"id\":1872,\"name\":\"Programmable Logic Controler\"},{\"image\":\"\",\"id\":1619,\"name\":\"Construction Saftey\"},{\"image\":\"\",\"id\":1883,\"name\":\"B.Tech in Computer & Communication Engineering\"},{\"image\":\"\",\"id\":1884,\"name\":\"Iron & Steel technology\"},{\"image\":\"\",\"id\":1635,\"name\":\"Aeronautical and Marine Safety\"},{\"image\":\"\",\"id\":1636,\"name\":\"Oil & Gas Safety\"},{\"image\":\"\",\"id\":1893,\"name\":\"Electronic Mechanic\"},{\"image\":\"\",\"id\":1638,\"name\":\"Safety Engineering\"},{\"image\":\"\",\"id\":1898,\"name\":\"Draftman\"},{\"image\":\"\",\"id\":1643,\"name\":\"Personal Survival Technique\"},{\"image\":\"\",\"id\":1650,\"name\":\"Food Processing technology\"},{\"image\":\"\",\"id\":1395,\"name\":\"Biomass and Biogas Technology\"},{\"image\":\"\",\"id\":1916,\"name\":\"Electrician\"},{\"image\":\"\",\"id\":1917,\"name\":\"Fitter\"},{\"image\":\"\",\"id\":1918,\"name\":\"Craftsman\"},{\"image\":\"\",\"id\":1663,\"name\":\"Land Surveying\"},{\"image\":\"\",\"id\":1919,\"name\":\"Diesel Mechanic\"},{\"image\":\"\",\"id\":1664,\"name\":\"Mechanical and automation\"},{\"image\":\"\",\"id\":1920,\"name\":\"COPA\"},{\"image\":\"\",\"id\":1921,\"name\":\"Mechanic  Motor\"},{\"image\":\"\",\"id\":1922,\"name\":\"Garment Technology\"},{\"image\":\"\",\"id\":1923,\"name\":\"Draughtsman Civil\"},{\"image\":\"\",\"id\":1924,\"name\":\"Draughtsman Mechanical\"},{\"image\":\"\",\"id\":1925,\"name\":\"Lift Mechanic\"},{\"image\":\"\",\"id\":1927,\"name\":\"Surveyor\"},{\"image\":\"\",\"id\":1672,\"name\":\"Architectural Assistantship\"},{\"image\":\"\",\"id\":1928,\"name\":\"Workshop\"},{\"image\":\"\",\"id\":1933,\"name\":\"Instrument Mechanic\"},{\"image\":\"\",\"id\":1934,\"name\":\"Mechanic (Motor Vehicle)\"},{\"image\":\"\",\"id\":1935,\"name\":\"Information Technology & Electronics System Maintenance\"},{\"image\":\"\",\"id\":1936,\"name\":\"Plastic Processing Operator\"},{\"image\":\"\",\"id\":1939,\"name\":\"Mechanic Auto Electrical And Electronics\"},{\"image\":\"\",\"id\":1945,\"name\":\"Pump Operator\"},{\"image\":\"\",\"id\":1178,\"name\":\"Process Enmgineering\"},{\"image\":\"\",\"id\":1946,\"name\":\"Metal Worker\"},{\"image\":\"\",\"id\":1438,\"name\":\"Applied Research in Electronics\"},{\"image\":\"\",\"id\":1953,\"name\":\"Mine Surveying\"},{\"image\":\"\",\"id\":1442,\"name\":\"Artificial Intelligence (AI) and Machine Learning (ML)\"},{\"image\":\"\",\"id\":1957,\"name\":\"Painter\"},{\"image\":\"\",\"id\":1958,\"name\":\"COE & Advanced Module\"},{\"image\":\"\",\"id\":1192,\"name\":\"Microwave and Communication\"},{\"image\":\"\",\"id\":1962,\"name\":\"Lathe Operator\"},{\"image\":\"\",\"id\":1963,\"name\":\"CNC Programming\"},{\"image\":\"\",\"id\":1967,\"name\":\"Environmental Architecture\"},{\"image\":\"\",\"id\":1968,\"name\":\"Digital Architecture\"},{\"image\":\"\",\"id\":696,\"name\":\"Transitional Engineering\"},{\"image\":\"\",\"id\":210,\"name\":\"Mechnical / Civil / Automobile / Electrical\"},{\"image\":\"\",\"id\":212,\"name\":\"Digital System and Computer Electronics\"},{\"image\":\"\",\"id\":1753,\"name\":\"3D Animation - Engg\"},{\"image\":\"\",\"id\":2011,\"name\":\"Genetic Engineering\"},{\"image\":\"\",\"id\":477,\"name\":\"Others-engineering\"},{\"image\":\"\",\"id\":1245,\"name\":\"Technician Analyst\"},{\"image\":\"\",\"id\":1255,\"name\":\"Computation Ananlysis\"},{\"image\":\"\",\"id\":2037,\"name\":\"Data Mining\"},{\"image\":\"\",\"id\":1787,\"name\":\"Mechanic Diesel\"},{\"image\":\"\",\"id\":764,\"name\":\"Heat Ventilation and Air contioning\"},{\"image\":\"\",\"id\":1788,\"name\":\"Welder\"}]}";

//        String loanAmount = "{\"selected_choice\":null,\"last\":0,\"name\":\"loan_amount\",\"text\":\"How much loan are you willing to take?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":1,\"name\":\"Below 1 Lakh\"},{\"image\":\"\",\"id\":2,\"name\":\"1-3 Lakh\"},{\"image\":\"\",\"id\":3,\"name\":\"3-5 Lakh\"},{\"image\":\"\",\"id\":4,\"name\":\"Above 5 Lakh\"},{\"image\":\"\",\"id\":5,\"name\":\"To be decided\"}]}";

        String socialCategory = "{\"selected_choice\":null,\"last\":0,\"name\":\"social_category\",\"text\":\"Which social category do you belong to?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":0,\"name\":\"Not Available\"},{\"image\":\"\",\"id\":1,\"name\":\"General\"},{\"image\":\"\",\"id\":2,\"name\":\"OBC\"},{\"image\":\"\",\"id\":3,\"name\":\"SC\"},{\"image\":\"\",\"id\":4,\"name\":\"ST\"},{\"image\":\"\",\"id\":5,\"name\":\"Others\"}]}";

        ArrayList<StepByStepQuestion> questionArrayList = new ArrayList<>();
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, preferredStream));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, yearOfAdmission));
//        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, preferredSpecialization));
//        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, preferredStates));
//        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, preferredCities));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, feesRange));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, preferredMode));
////        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, loanRequired));
////        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, loanAmount));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, socialCategory));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, gender));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, phoneNumber));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, name));

        return questionArrayList;
    }

    private void updateFilters(ArrayList<StepByStepChoice> userChoiceList) {
        if (userChoiceList == null || userChoiceList.isEmpty()) {
            return;
        }
        if (question.getName().equals("fees_range") || question.getName().equals("preferred_states") || question.getName().equals("preferred_streams") || question.getName().equals("college_location_cities")) {
            Map<String, String> map = new HashMap<>();
            SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.PREFS), MODE_PRIVATE);
            String value = sp.getString(Constants.SELECTED_FILTERS, null);

            if (value != null && value != "") {
                //split the string to create key-value pairs
                String[] keyValuePairs = value.replaceAll("\\{", "").replaceAll("\\}", "").split(",");

                //iterate over the pairs
                for (String pair : keyValuePairs) {
                    if (pair != "" && pair != null) {
                        //split the pairs to get key and value
                        String[] entry = pair.split("=");
                        //add them to the hashmap and trim whitespaces
                        map.put(entry[0].trim(), entry[1].trim());
                    }
                }
            }

            int count = map.size();
            for (StepByStepChoice choice : userChoiceList) {
                String name = choice.getUri();
                map.put("tag_uris[" + (++count) + "]", name);
            }
            this.getSharedPreferences(getResources().getString(R.string.PREFS), MODE_PRIVATE).edit().putString(Constants.SELECTED_FILTERS, map.toString()).commit();
            finishWithData(null);
        } else {
            HashMap<String, String> dataMap = new HashMap<>();
            for (StepByStepChoice choice : userChoiceList) {
                dataMap.put(question.getName(), String.valueOf(choice.getId()));
                finishWithData(dataMap);
            }
            finishWithData(dataMap);
        }
    }

    private String readFileFromAssets(String fileName) throws IOException {
        StringBuilder buf = new StringBuilder();
        InputStream json = getAssets().open(fileName);
        BufferedReader in =
                new BufferedReader(new InputStreamReader(json, "UTF-8"));
        String str;

        while ((str = in.readLine()) != null) {
            buf.append(str);
        }
        in.close();
        return buf.toString();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gcm_btn_ok:
                onPositiveResponse(questionType);
                break;
            case R.id.gcm_btn_cancel:
                onCancelDialog();
                finish();
                break;
        }
    }

/*    private void finishWithData(Intent dataIntent) {
        this.setResult(Activity.RESULT_OK, dataIntent);
        finish();
    }*/

    private void finishWithData(HashMap<String, String> hashMap) {
        Intent intent = new Intent();
        if (hashMap != null) {
            intent.putExtra(Constants.DIALOG_DATA, hashMap);
            this.setResult(Activity.RESULT_OK, intent);
            int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
            if (amIConnectedToInternet == Constants.TYPE_NOT_CONNECTED ) {
                displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            }else{
                finish();
            }
        } else {
            this.setResult(Activity.RESULT_CANCELED, intent);
            DataBaseHelper.getInstance(this).deleteAllExamSummary();
            finish();
        }

    }

    public String getResourceString(int resourceId) {
        try {
            return getString(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    class ChoiceTypeQuestionAdapter extends RecyclerView.Adapter<ChoiceTypeQuestionAdapter.ChoiceTypeQuestionViewHolder> {

        ArrayList<StepByStepChoice> mQuestions;

        String choiceMode = "multiple";
        ImageLoader imageLoader;

        public ChoiceTypeQuestionAdapter(Context context, ArrayList<StepByStepChoice> questions, String choiceMode) {
            mQuestions = questions;
            this.choiceMode = choiceMode;
            selected = new boolean[questions.size()];
            imageLoader = MySingleton.getInstance(context).getImageLoader();
            isSingleChoice = choiceMode.equals("single");
        }

        @Override
        public ChoiceTypeQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.dialod_question_item, parent, false);
            return new ChoiceTypeQuestionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ChoiceTypeQuestionViewHolder holder, int position) {
            StepByStepChoice question = mQuestions.get(position);
            holder.choiceText.setText(question.getName());
            if (isSingleChoice) {
                if (mSelectedPosition == position) {
                    holder.choiceRadio.setChecked(true);
                } else {
                    holder.choiceRadio.setChecked(false);
                }
            } else {
                holder.checkCheckBox.setChecked(selected[position]);
            }
            if (question.getImage() != null && !question.getImage().isEmpty()) {
                holder.optionImage.setVisibility(View.VISIBLE);
                holder.optionImage.setImageUrl(question.getImage(), imageLoader);
            }
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }

        class ChoiceTypeQuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView choiceText;
            NetworkImageView optionImage;
            RadioButton choiceRadio;
            CheckBox checkCheckBox;

            public ChoiceTypeQuestionViewHolder(View itemView) {
                super(itemView);
                choiceText = (TextView) itemView.findViewById(R.id.single_choice_text);
                optionImage = (NetworkImageView) itemView.findViewById(R.id.single_option_image);
                choiceRadio = (RadioButton) itemView.findViewById(R.id.single_choice_radio);
                checkCheckBox = (CheckBox) itemView.findViewById(R.id.multiple_choice_checkbox);
                if (isSingleChoice) {
                    choiceRadio.setVisibility(View.VISIBLE);
                    checkCheckBox.setVisibility(View.GONE);
                } else {
                    choiceRadio.setVisibility(View.GONE);
                    checkCheckBox.setVisibility(View.VISIBLE);
                }
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                mSelectedPosition = getLayoutPosition();
                selected[mSelectedPosition] = !checkCheckBox.isChecked();
                if (checkCheckBox.isChecked()) {
                    selectionCount--;
                } else {
                    selectionCount++;
                }
                notifyDataSetChanged();
            }
        }
    }
}