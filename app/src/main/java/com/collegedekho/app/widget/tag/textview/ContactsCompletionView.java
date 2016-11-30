package com.collegedekho.app.widget.tag.textview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;

/**
 * Sample token completion view for basic contact info
 *
 * Created on 9/12/13.
 * @author mgod
 */
public class ContactsCompletionView extends TokenCompleteTextView<String> {

    public ContactsCompletionView(Context context) {
        super(context);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(String text) {

        LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout)l.inflate(R.layout.contact_token, (ViewGroup)ContactsCompletionView.this.getParent(), false);
        TextView tv  =((TextView) view.findViewById(R.id.name));
        tv.setText(text);
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross_icon, 0);

        return view;
    }

    @Override
    protected String defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        String text = completionText;
        return text;
    }
}
