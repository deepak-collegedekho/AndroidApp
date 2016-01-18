package com.collegedekho.app.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.htmlparser.HtmlSpanner;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.resource.TypeFaceTypes;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.List;
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
            case PROXIMA_NOVA_REGULAR:
                mTypeface = Typeface.createFromAsset(ctx.getAssets(),"proxima_nova_regular.ttf");
                break;
            case GOTHAM_BOOK:
                mTypeface = Typeface.createFromAsset(ctx.getAssets(),"gotham-book.ttf");
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
        if(target == null || target.length() < 3)
            return false;

        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(target);
        return ms.matches();
    }

    /**
     * Method is used to get device email
     * which is used  by the google account
     * @return
     */
    public static String getDeviceEmail(Context context){
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return null;
    }
    /**
     * Method is used to get device phone number
     * if it is enable in phone setting
     * @return
     */

    private String getDevicePhone(Context context){
        Pattern phonePattern = Patterns.PHONE;
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (phonePattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return null;
    }

    public static int getSubjectColor(int subjectId) {
        int[] subjectColors = {0xff003fff, 0xff0066ff, 0xffff5c33, 0xff00b300, 0xffcc0000, 0xffe5e600, 0xff001a4d, 0xff009933, 0xffcc5200, 0xffcc7a00, 0xff003300, 0xffcc0052, 0xff990033, 0xff000d33, 0xffffff00};
        int colorIndex = subjectId % 15;
        return subjectColors[colorIndex];
    }
    public static boolean isUriEndsWithNumber(String resourceUri){
        if (resourceUri!=null && resourceUri.length()>1)
        return resourceUri.substring(0,resourceUri.length()-1).matches("^.*\\d");
        return false;
    }

    public static void SetCounterAnimation(final TextView textView, int count, final String suffix, long duration)
    {
        ValueAnimator animator = new ValueAnimator();

        animator.setObjectValues(0, count);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(String.valueOf(animation.getAnimatedValue()) + suffix);
            }
        });

        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });

        animator.setDuration(duration);
        animator.start();
    }

    public static Drawable ApplyThemeToDrawable(Drawable image, int color) {
        if (image != null) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(color,
                    PorterDuff.Mode.SRC_ATOP);

            image.setColorFilter(porterDuffColorFilter);

            return image;
        }
        else
            return null;
    }

    public static void renderHtml(Context context, LinearLayout parentLayout, String sourceHtml) {
        if (parentLayout == null || context == null || sourceHtml == null || sourceHtml.isEmpty()) {
            return;
        }
        HtmlCleaner cleaner = new HtmlCleaner();
        HtmlSpanner spanner = new HtmlSpanner();
        TagNode result = cleaner.clean(sourceHtml);
        List<TagNode> list = result.getChildTagList();
        parentLayout.removeAllViews();
        for (TagNode node : list) {
            if (node.getName().matches("body")) {
                List<TagNode> childList = node.getChildTagList();
                for (TagNode childNode : childList) {
                    if (childNode.getName().matches("img")) {
                        Log.i("DEBUG", "Image found");
                        NetworkImageView imageView = new NetworkImageView(context);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.gravity = Gravity.CENTER;
                        imageView.setLayoutParams(layoutParams);
                        parentLayout.addView(imageView);
                        String src = childNode.getAttributeByName("src");
                        if (src != null) {
                            imageView.setImageUrl(src, MySingleton.getInstance(context).getImageLoader());
                        }
                    } else if (childNode.getName().matches("p")) {
                        TagNode att = childNode.findElementByName("img", false);
                        if (att != null) {
                            Log.i("DEBUG", "Image found");
                            NetworkImageView imageView = new NetworkImageView(context);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.gravity = Gravity.CENTER;
                            imageView.setLayoutParams(layoutParams);
                            parentLayout.addView(imageView);
                            String src = att.getAttributeByName("src");
                            if (src != null) {
                                imageView.setImageUrl(src, MySingleton.getInstance(context).getImageLoader());
                            }
                        } else {
                            TextView textView = new TextView(context);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                            layoutParams.setMargins(0, 0, 0, -25);
                            textView.setLayoutParams(layoutParams);
//                            textView.setMovementMethod(LinkMovementMethod.getInstance());
                            textView.setText(spanner.fromTagNode(childNode, null));
                            parentLayout.addView(textView);
                        }
                    } else {
                        TextView textView = new TextView(context);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(0, 0, 0, -25);
                        textView.setLayoutParams(layoutParams);
//                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                        textView.setText(spanner.fromTagNode(childNode, null));
                        parentLayout.addView(textView);
                    }
                }
            } else {
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(0, 0, 0, -25);
                textView.setLayoutParams(layoutParams);
//                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setText(spanner.fromTagNode(node, null));
                parentLayout.addView(textView);
            }
        }
    }
}
