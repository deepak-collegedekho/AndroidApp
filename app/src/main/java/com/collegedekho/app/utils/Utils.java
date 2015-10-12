package com.collegedekho.app.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sureshsaini on 12/10/15.
 */
public class Utils {

    public static void displayToast(Context context,String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
