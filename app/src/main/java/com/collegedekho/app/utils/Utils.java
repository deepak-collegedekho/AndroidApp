package com.collegedekho.app.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Patterns;
import android.widget.Toast;

import com.collegedekho.app.resource.TypeFaceTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sureshsaini on 12/10/15.
 */
public class Utils {

    public static void DisplayToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static Typeface getTypeFace(Context ctx, TypeFaceTypes mTypeFaceTypes){
        Typeface mTypeface = null;
        switch (mTypeFaceTypes) {

            case DROID_SERIF_BOLD:
                mTypeface = Typeface.createFromAsset(ctx.getAssets(),"fonts/DROID_SERIF_BOLD.ttf");
                break;
            case DROID_SERIF_BOLD_ITALIC:
                mTypeface = Typeface.createFromAsset(ctx.getAssets(),"DROID_SERIF_BOLD_ITALIC.ttf");
                break;
            case DROID_SERIF_ITALIC:
                mTypeface = Typeface.createFromAsset(ctx.getAssets(),"DROID_SERIF_ITALIC.ttf");
                break;
            case DROID_SERIF_REGULAR:
                mTypeface = Typeface.createFromAsset(ctx.getAssets(),"DROID_SERIF_REGULAR.ttf");
                break;
            case ICOMOONFREE:
                mTypeface = Typeface.createFromAsset(ctx.getAssets(),"IcoMoonFree.ttf");
                break;
            case GOTHAMBOOK:
                mTypeface = Typeface.createFromAsset(ctx.getAssets(),"GothamBook.otf");
                break;
            default:
                break;
        }
        return mTypeface;
    }

    public static int getPadding(Context context, int value)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(value * density);
    }

    /**
     * This method is used to chck user's email address
     * @param target
     * @return
     */
    public static boolean isValidEmail(CharSequence target) {
        if(target == null || target.length() <= 0)
            return false;
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * This method is used to check user's contact
     * number is having a valid format
     * @param target
     * @return
     */
    public static boolean isValidPhone(String target) {
        if(target == null || target.length() <= 6)
            return false;

        target = target.replace(" ","");
        return target != null && Patterns.PHONE.matcher(target).matches();
    }

    /**
     * This metos is used to check user's name is in valid format
     * @param target
     * @return
     */
    public static boolean isValidName(CharSequence target) {
        if(target == null || target.length() <= 3)
            return false;

        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(target);
        return ms.matches();
    }
}
