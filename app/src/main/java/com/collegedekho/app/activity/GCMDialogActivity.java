package com.collegedekho.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.collegedekho.app.R;

/**
 * Created by Bashir on 5/4/16.
 */
public class GCMDialogActivity extends Activity {
    private final int TYPE_MCQ = 1;
    private final int TYPE_CHECKBOX = 2;
    private final int TYPE_SEEKBAR = 3;
    private final int TYPE_INPUT = 4;

    LayoutInflater layoutInflater;
    FrameLayout parentLayout;
    Button btnOk, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gcm_dialog_activity_layout);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setFinishOnTouchOutside(false);
        parentLayout = (FrameLayout) findViewById(R.id.gcm_dialog_container);
        layoutInflater = getLayoutInflater();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bundle = bundle.getBundle("data");
            if (bundle != null) {
                String val=bundle.getString("question_type");
                int questionType =0;
                if(val!=null && !val.trim().matches(""))
                    try {
                        questionType=Integer.parseInt(val);
                    }catch (NumberFormatException e){

                    }
                if (questionType > 0) {
                    View questionLayout = setupContentView(questionType);
                    if (questionLayout != null) {
                        parentLayout.addView(questionLayout);
                    }
                }
            }
        }

        btnCancel = (Button) findViewById(R.id.gcm_btn_cancel);
        btnCancel.setOnClickListener(clickListener);
        btnOk = (Button) findViewById(R.id.gcm_btn_ok);
        btnOk.setOnClickListener(clickListener);
    }

    private View setupContentView(int questionType) {
        View questionLayout = null;
        switch (questionType) {
            case TYPE_MCQ:
                questionLayout = layoutInflater.inflate(R.layout.gcm_dialog_mcq_layout, null);
                setupMcqDialog();
                break;

            case TYPE_CHECKBOX:
                questionLayout = layoutInflater.inflate(R.layout.gcm_dialog_checkbox_layout, null);
                setupCheckboxDialog();
                break;

            case TYPE_SEEKBAR:
                questionLayout = layoutInflater.inflate(R.layout.gcm_dialog_seekbar_layout, null);
                setupSeekBarDialog();
                break;

            case TYPE_INPUT:
                questionLayout = layoutInflater.inflate(R.layout.gcm_dialog_input_layout, null);
                setupInputDialog();
                break;
        }
        return questionLayout;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.gcm_btn_ok:
                    finish();
                    break;
                case R.id.gcm_btn_cancel:
                    finish();
                    break;
            }
        }
    };

    private void setupInputDialog() {

    }

    private void setupSeekBarDialog() {

    }

    private void setupCheckboxDialog() {

    }

    private void setupMcqDialog() {

    }

    @Override
    public void onBackPressed() {

    }
}
