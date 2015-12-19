package com.collegedekho.app.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class NumberPicker extends android.widget.NumberPicker {

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    public void updateView(View view) {
        if(view instanceof EditText){
            ((EditText) view).setSingleLine(false);
            ((EditText) view).setTextSize(16);
            ((EditText) view).setEllipsize(TextUtils.TruncateAt.END);
            ((EditText) view).setInputType(EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE | EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE);
            ((EditText) view).setLines(2);
            ((EditText) view).setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
            ((EditText) view).setHorizontallyScrolling(false);
        }
    }

}