/*
package com.collegedekho.app.widget.tag.textview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.collegedekho.app.R;

public class TokenActivity extends Activity implements TokenCompleteTextView.TokenListener {
    ContactsCompletionView completionView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new FilteredArrayAdapter<String>(this, R.layout.person_layout, new String[]{}) {
            @Override
            protected boolean keepObject(String obj, String mask) {
                Log.e("", "");
                return false;
            }

           @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.contact_token, parent, false);
                }

                return convertView;
            }


        };

        completionView = (ContactsCompletionView)findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);

    }

    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token: completionView.getObjects()) {
            sb.append(token.toString());
            //sb.append("\n");
        }
    }


    @Override
    public void onTokenAdded(Object token) {
       // updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Object token) {
        updateTokenConfirmation();
    }
}
*/
