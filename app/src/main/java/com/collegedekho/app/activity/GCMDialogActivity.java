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
import com.collegedekho.app.utils.ProfileMacro;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gcm_dialog_activity_layout);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFinishOnTouchOutside(false);
        parentLayout = (FrameLayout) findViewById(R.id.gcm_dialog_container);
        layoutInflater = getLayoutInflater();
        txtQuestionText = (TextView) findViewById(R.id.gcm_question);

        sharedPreferences = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
        userResponseBundle = new Bundle();
        snackbar = Snackbar.make(findViewById(R.id.gcm_dialog_container),
                "You are not connected to Internet", Snackbar.LENGTH_SHORT);
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
        }else{
            edtUserInput.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
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
                if (userInput.length() >= 3) {
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

        if (MainActivity.mProfile == null || questionArrayList == null || questionArrayList.isEmpty())
            return  null;

        StepByStepQuestion question = questionArrayList.get(0);

        if (question.getName().equals("name")) {
            String name = MainActivity.mProfile.getName();
            if ( name == null || name.length() == 0  ||
                    name.equalsIgnoreCase(getResourceString(R.string.ANONYMOUS_USER))) {
                return question;
            } else {
                questionAnswered(question);
                return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
            }
        } else if (question.getName().equals("gender")) {
                if (MainActivity.mProfile.getGender() == ProfileMacro.GENDER_NOT_PROVIDED) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
        } else if (question.getName().equals("social_category")) {
                if (MainActivity.mProfile.getSocial_category() == 0) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
        } else if (question.getName().equals("current_passing_year")) {
                if (MainActivity.mProfile.getCurrent_passing_year() == 0) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
        } else if (question.getName().equals("preferred_mode")) {
                if (MainActivity.mProfile.getPreferred_mode() == -1) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
        } else  if (question.getName().equals("phone_no")) {
                if (MainActivity.mProfile.getPhone_no() == null || MainActivity.mProfile.getPhone_no().isEmpty()) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
        }else if (question.getName().equals("preferred_streams")) {
                if (MainActivity.mProfile.getCurrent_stream_id() == -1) {
                    return question;
                } else {
                    questionAnswered(question);
                    return getQuestion(stepByStepQuestions = new ArrayList<>(JSON.std.listOfFrom(StepByStepQuestion.class, sharedPreferences.getString(Constants.QUESTIONS_LIST_KEY, null))));
                }
        } else {
                return question;
        }
    }

    private ArrayList<StepByStepQuestion> getQuestionsList() throws IOException {
        String name = "{\"selected_choice\":null,\"last\":0,\"name\":\"name\",\"text\":\"What is your name?\",\"image\":0,\"required\":1,\"type\":\"text\",\"other_choices\":[],\"choices\":[]}";

        String phoneNumber = "{\"selected_choice\":null,\"last\":0,\"name\":\"phone_no\",\"text\":\"Where should we contact you?\",\"image\":0,\"required\":1,\"type\":\"number\",\"other_choices\":[],\"choices\":[]}";

        String preferredMode = "{\"selected_choice\":null,\"last\":0,\"name\":\"preferred_mode\",\"text\":\"What is your preferred mode?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":1,\"name\":\"Regular\"},{\"image\":\"\",\"id\":2,\"name\":\"Part Time\"},{\"image\":\"\",\"id\":4,\"name\":\"Distancce / Correspondence\"},{\"image\":\"\",\"id\":5,\"name\":\"Executive\"},{\"image\":\"\",\"id\":6,\"name\":\"Online / E-Learning\"}]}";

        String yearOfAdmission = "{\"selected_choice\":null,\"last\":0,\"name\":\"current_passing_year\",\"text\":\"When did you graduate?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":2014,\"name\":\"Before 2015\"},{\"image\":\"\",\"id\":2015,\"name\":\"2015\"},{\"image\":\"\",\"id\":2016,\"name\":\"2016\"},{\"image\":\"\",\"id\":2017,\"name\":\"After 2016\"}]}";


        String gender = "{\"selected_choice\":null,\"last\":0,\"name\":\"gender\",\"text\":\"Select your gender\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":1,\"name\":\"Male\"},{\"image\":\"\",\"id\":2,\"name\":\"Female\"}]}";

        String feesRange = "{\"selected_choice\":null,\"last\":0,\"name\":\"preferred_fee_range_max\",\"text\":\"What is your preferred fee range?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"uri\":\"lt1\",\"id\":100000,\"name\":\"0-1 Lakh\"},{\"image\":\"\",\"uri\":\"gt1_lt2\",\"id\":200000,\"name\":\"1-2 Lakh\"},{\"image\":\"\",\"uri\":\"gt2_lt3\",\"id\":300000,\"name\":\"2-3 Lakh\"},{\"image\":\"\",\"uri\":\"gt2_lt3\",\"id\":400000,\"name\":\"3-4 Lakh\"},{\"image\":\"\",\"uri\":\"gt3_lt4\",\"id\":500000,\"name\":\"Above 4 Lakh\"}]}";


        String preferredStream = "{\"selected_choice\":null,\"last\":0,\"name\":\"preferred_streams\",\"text\":\"What is your preferred stream?\",\"image\":1,\"required\":1,\"type\":\"single\",\"other_choices\":[{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/banking-l_Yf4vlTI.png\",\"uri\":\"commerce-banking\",\"id\":1,\"name\":\"Commerce / Banking\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/design-l_V4ioQbT.png\",\"uri\":\"design\",\"id\":2,\"name\":\"Design\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/engineering-l_wUddKPm.png\",\"uri\":\"engineering\",\"id\":3,\"name\":\"Engineering\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/management-l_2CUexFe.png\",\"uri\":\"mba\",\"id\":4,\"name\":\"Management\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/hospitality-l_nTFEyei.png\",\"uri\":\"hospitality-aviation\",\"id\":5,\"name\":\"Hospitality / Aviation\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/it-l_Sk3FDbD.png\",\"uri\":\"information-technology\",\"id\":6,\"name\":\"IT\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/mass-l_nJYRxT6.png\",\"uri\":\"mass-comm\",\"id\":7,\"name\":\"Mass Comm\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/medicine-l_UXFAzql.png\",\"uri\":\"medical\",\"id\":8,\"name\":\"Medical\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/retail-l_d5b7NNv.png\",\"uri\":\"retail\",\"id\":9,\"name\":\"Retail\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/arts-l.png\",\"uri\":\"arts\",\"id\":12,\"name\":\"Arts\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/law-l_icYZJ7Z.png\",\"uri\":\"law-humanities\",\"id\":13,\"name\":\"Law / Humanities\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/science-l_Y1CiAX7.png\",\"uri\":\"sciences\",\"id\":14,\"name\":\"Sciences\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/vocational-l_w9iINmW.png\",\"uri\":\"vocational\",\"id\":15,\"name\":\"Vocational\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/others-l_jYO9R6G.png\",\"uri\":\"others\",\"id\":16,\"name\":\"Others\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/physical-l_Oh2tYYs.png\",\"uri\":\"physical-education\",\"id\":18,\"name\":\"Physical Education\"},{\"image\":\"https://d1wvhegsi0wquf.cloudfront.net/media/img/stream/multimedia-l_dyO4KwI.png\",\"uri\":\"animation\",\"id\":32,\"name\":\"Animation / Multimedia\"}],\"choices\":[]}";


        String socialCategory = "{\"selected_choice\":null,\"last\":0,\"name\":\"social_category\",\"text\":\"Which social category do you belong to?\",\"image\":0,\"required\":1,\"type\":\"single\",\"other_choices\":[],\"choices\":[{\"image\":\"\",\"id\":0,\"name\":\"Not Available\"},{\"image\":\"\",\"id\":1,\"name\":\"General\"},{\"image\":\"\",\"id\":2,\"name\":\"OBC\"},{\"image\":\"\",\"id\":3,\"name\":\"SC\"},{\"image\":\"\",\"id\":4,\"name\":\"ST\"},{\"image\":\"\",\"id\":5,\"name\":\"Others\"}]}";

        ArrayList<StepByStepQuestion> questionArrayList = new ArrayList<>();
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, name));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, gender));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, phoneNumber));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, yearOfAdmission));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, preferredStream));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, feesRange));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, preferredMode));
        questionArrayList.add(JSON.std.beanFrom(StepByStepQuestion.class, socialCategory));

        return questionArrayList;
    }

    private void updateFilters(ArrayList<StepByStepChoice> userChoiceList) {
        if (userChoiceList == null || userChoiceList.isEmpty()) {
            return;
        }
        if (question.getName().equals("fees_range") || question.getName().equals("preferred_streams")){
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
            int amIConnectedToInternet = MainActivity.mNetworkUtils.getConnectivityStatus();
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