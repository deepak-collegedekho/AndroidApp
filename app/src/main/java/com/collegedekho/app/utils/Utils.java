package com.collegedekho.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.resource.Constants;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sureshsaini on 12/10/15.
 */
public class Utils {

    public static void DisplayToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    private static boolean isValidName(CharSequence target) {
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(target);
        return ms.matches();
    }

    public static boolean checkUserName(final Activity activity) {
        User user = MainActivity.user;
        if (user == null || user.getName().isEmpty() || user.getName().equalsIgnoreCase("Anonymous user")) {
            final Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.name_dailog);
            dialog.setCanceledOnTouchOutside(true);
            TextView submit = (TextView) dialog.findViewById(R.id.name_submit);
            dialog.show();


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = ((EditText) dialog.findViewById(R.id.user_name)).getText().toString();
                    if (name == null || name.length() <= 0) {
                        Utils.DisplayToast(activity, Constants.NAME_EMPTY);
                        return;
                    } else if (!isValidName(name)) {
                        Utils.DisplayToast(activity, Constants.NAME_INVALID);
                        return;

                    } else if (name.length() <= 2) {
                        Utils.DisplayToast(activity, "Minimum length is 3");
                        return;
                    }
                    dialog.dismiss();
                    HashMap<String, String> params = new HashMap<>();
                    params.put(Constants.USER_NAME, name);
                }
            });
            return false;
        }
        return true;
    }

}
