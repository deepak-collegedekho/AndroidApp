package com.collegedekho.app.utils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.BuildConfig;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.htmlparser.HtmlSpanner;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.resource.Constants;
import com.fasterxml.jackson.jr.ob.JSON;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by {Author}sureshsaini on 12/10/15.
 */
public class Utils {


    private static BroadcastReceiver mPowerKeyReceiver;
    private static boolean screenGotOff;

    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Darkens a color by a given factor.
     *
     * @param color  the color to darken
     * @param factor The factor to darken the color.
     * @return darker version of specified color.
     */
    public static int darker(int color, float factor) {
        return Color.argb(Color.alpha(color), Math.max((int) (Color.red(color) * factor), 0),
                Math.max((int) (Color.green(color) * factor), 0),
                Math.max((int) (Color.blue(color) * factor), 0));
    }

    /**
     * Lightens a color by a given factor.
     *
     * @param color  The color to lighten
     * @param factor The factor to lighten the color. 0 will make the color unchanged. 1 will make the
     *               color white.
     * @return lighter version of the specified color.
     */
    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /**
     * Check if layout direction is RTL
     *
     * @param context the current context
     * @return {@code true} if the layout direction is right-to-left
     */
    public static boolean isRtl(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
                context.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    /**
     * Return a drawable object associated with a particular resource ID.
     * <p>
     * <p>Starting in {@link Build.VERSION_CODES#LOLLIPOP}, the returned drawable will be styled for the
     * specified Context's theme.</p>
     *
     * @param id The desired resource identifier, as generated by the aapt tool.
     *           This integer encodes the package, type, and resource entry.
     *           The value 0 is an invalid identifier.
     * @return Drawable An object that can be used to draw this resource.
     */
    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        }
        return context.getResources().getDrawable(id);
    }

    public static void DisplayToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void DisplayToastShort(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 30);
        toast.show();
    }


    public static int getPadding(Context context, int value) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (value * density);
    }

    /**
     * This method is used to chck mDeviceProfile's email address
     *
     * @param target targetString
     * @return true or false
     */
    public static boolean isValidEmail(CharSequence target) {
//        if(target == null || target.length() <= 0)
//            return false;
//        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        return !(target == null || target.length() <= 0) && target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * This method is used to check mDeviceProfile's contact
     * number is having a valid format
     *
     * @param target
     * @return boolean
     */
    public static boolean isValidPhone(String target) {
        if (target == null || target.length() <= 6)
            return false;

        target = target.replace(" ", "");
        return target != null && Patterns.PHONE.matcher(target).matches();
    }

    /**
     * This metos is used to check mDeviceProfile's name is in valid format
     *
     * @param target target
     * @return boolean
     */
    public static boolean isValidName(CharSequence target) {
        if (target == null || target.length() < 3)
            return false;

        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(target);
        return ms.matches();
    }

    /**
     * Method is used to get device email
     * which is used  by the google account
     *
     * @return
     */
    public static String getDeviceEmail(final Context context) {
        if (context == null) return null;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {

            Pattern emailPattern = Patterns.EMAIL_ADDRESS;
            Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    return account.name.replaceAll("\"", "");
                }
            }
        }
        return null;
    }


    public static int GetCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int GetCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);

    }


    public static int getSubjectColor(int subjectId) {
        int[] subjectColors = {0xff003fff, 0xff0066ff, 0xffff5c33, 0xff00b300, 0xffcc0000, 0xffe5e600, 0xff001a4d, 0xff009933, 0xffcc5200, 0xffcc7a00, 0xff003300, 0xffcc0052, 0xff990033, 0xff000d33, 0xffffff00};
        int colorIndex = subjectId % 15;
        return subjectColors[colorIndex];
    }

    public static boolean isUriEndsWithNumber(String resourceUri) {
//        if (resourceUri!=null && resourceUri.length()>1)
//        return resourceUri.substring(0,resourceUri.length()-1).matches("^.*\\d");
//        return false;
        return resourceUri != null && resourceUri.length() > 1 && resourceUri.substring(0, resourceUri.length() - 1).matches("^.*\\d");
    }

    public static void SetCounterAnimation(final TextView textView, int count, final String prefix, final String suffix, long duration) {
        ValueAnimator animator = new ValueAnimator();

        animator.setObjectValues(0, count);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(prefix + String.valueOf(animation.getAnimatedValue()) + suffix);
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
        } else
            return null;
    }

    public static void renderHtml(Activity context, LinearLayout parentLayout, String sourceHtml) {
        if (parentLayout == null || context == null || sourceHtml == null || sourceHtml.isEmpty()) {
            return;
        }
        HtmlCleaner cleaner = new HtmlCleaner();
        HtmlSpanner spanner = new HtmlSpanner(context);
        TagNode result = cleaner.clean(sourceHtml);
        List<TagNode> list = result.getChildTagList();
        parentLayout.removeAllViews();
        for (TagNode node : list) {
            if (node.getName().matches("body")) {
                List<TagNode> childList = node.getChildTagList();
                for (TagNode childNode : childList) {
                    if (childNode.getName().matches("img")) {
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
                            layoutParams.setMargins(0, 0, 0, -30);
                            textView.setLayoutParams(layoutParams);
                            textView.setText(spanner.fromTagNode(childNode, null));
                            textView.setMovementMethod(AppLinkMovementMethod.getInstance());
                            parentLayout.addView(textView);
                        }
                    } else {
                        TextView textView = new TextView(context);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, -30);
                        textView.setLayoutParams(layoutParams);
                        textView.setText(spanner.fromTagNode(childNode, null));
                        textView.setMovementMethod(AppLinkMovementMethod.getInstance());
                        parentLayout.addView(textView);
                    }
                }
            } else {
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, -30);
                textView.setLayoutParams(layoutParams);
                textView.setText(spanner.fromTagNode(node, null));
                textView.setMovementMethod(AppLinkMovementMethod.getInstance());
                parentLayout.addView(textView);
            }
        }
    }

    public static void RegisterBroadcastReceiver(Context context) {
        final IntentFilter theFilter = new IntentFilter();
        /** System Defined Broadcast */
        //theFilter.addAction(Intent.ACTION_SCREEN_ON);
        theFilter.addAction(Intent.ACTION_SCREEN_OFF);

        mPowerKeyReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String strAction = intent.getAction();

                if (strAction.equals(Intent.ACTION_SCREEN_OFF)) {
                    Log.v("", "Screen OFF");
                    Utils.screenGotOff = true;
                }
                if (strAction.equals(Intent.ACTION_SCREEN_ON)) {
                    Log.v("", "Screen ON");
                    //Utils.screenGotOff = false;
                }
            }
        };

        context.registerReceiver(mPowerKeyReceiver, theFilter);
    }

    public static void UnregisterReceiver(Context context) {
        int apiLevel = Build.VERSION.SDK_INT;

        if (apiLevel >= 7) {
            try {
                context.unregisterReceiver(mPowerKeyReceiver);
            } catch (IllegalArgumentException e) {
                mPowerKeyReceiver = null;
            }
        } else {
            context.unregisterReceiver(mPowerKeyReceiver);
            mPowerKeyReceiver = null;
        }
    }

    public static boolean isScreenGotOff() {
        return screenGotOff;
    }

    public static void setScreenGotOff(boolean screenGotOff) {
        Utils.screenGotOff = screenGotOff;
    }

    public static int getDeviceWidth(Activity activity) {

        int screenWidth;
        if (Build.VERSION.SDK_INT >= 17) {
            Point size = new Point();
            try {
                activity.getWindowManager().getDefaultDisplay().getRealSize(size);
                screenWidth = size.x;
            } catch (NoSuchMethodError e) {
                screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
            }

        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            screenWidth = metrics.widthPixels;
        }
        return screenWidth;

    }

    public static int getDeviceHeight(Activity activity) {

        int screenHeight;
        if (Build.VERSION.SDK_INT >= 17) {
            Point size = new Point();
            try {
                activity.getWindowManager().getDefaultDisplay().getRealSize(size);
                screenHeight = size.y;
            } catch (NoSuchMethodError e) {
                screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
            }

        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            screenHeight = metrics.heightPixels;
        }
        return screenHeight;

    }

    public static void logApiResponseTime(Calendar calendar, String tag) {
//        if (!BuildConfig.DEBUG) {
//            return;
//        }
        if (calendar != null) {
            try {
                Calendar cal = Calendar.getInstance();
                long diff = cal.getTimeInMillis() - calendar.getTimeInMillis();
                int milliSecs = (int) diff % (1000);
                int seconds = (int) (diff / 1000) % 60;
                int minutes = (int) ((diff / (1000 * 60)) % 60);
//            int hours   = (int) ((diff / (1000*60*60)) % 24);
                String log = tag + " Started : " + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + ":" + calendar.get(Calendar.MILLISECOND) + " Stopped : " + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + ":" + cal.get(Calendar.MILLISECOND) + " Time: " + minutes + ":" + seconds + ":" + milliSecs;
                Log.e("API_PROFILE", log);
                FileLogger.writeLog(log);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getTimeInMilliSec(Calendar calendar) {
        int milliSecs = 0;
        if (calendar != null) {
            try {
                Calendar cal = Calendar.getInstance();
                long diff = cal.getTimeInMillis() - calendar.getTimeInMillis();
                milliSecs = (int) diff % (1000);
                /*int seconds = (int) (diff / 1000) % 60;
                int minutes = (int) ((diff / (1000 * 60)) % 60);*/
            } catch (Exception e) {

            }
        }
        return milliSecs;
    }

    public static String getTimeFromString(String duration) {
        // TODO Auto-generated method stub
        String time = "";
        boolean hourexists = false, minutesexists = false, secondsexists = false;
        if (duration.contains("H"))
            hourexists = true;
        if (duration.contains("M"))
            minutesexists = true;
        if (duration.contains("S"))
            secondsexists = true;
        if (hourexists) {
            String hour = "";
            hour = duration.substring(duration.indexOf("T") + 1,
                    duration.indexOf("H"));
            if (hour.length() == 1)
                hour = "0" + hour;
            time += hour + ":";
        } else {
            time += "00:";
        }
        if (minutesexists) {
            String minutes = "";
            if (hourexists)
                minutes = duration.substring(duration.indexOf("H") + 1,
                        duration.indexOf("M"));
            else
                minutes = duration.substring(duration.indexOf("T") + 1,
                        duration.indexOf("M"));
            if (minutes.length() == 1)
                minutes = "0" + minutes;
            time += minutes + ":";
        } else {
            time += "00:";
        }
        if (secondsexists) {
            String seconds = "";
            if (hourexists) {
                if (minutesexists)
                    seconds = duration.substring(duration.indexOf("M") + 1,
                            duration.indexOf("S"));
                else
                    seconds = duration.substring(duration.indexOf("H") + 1,
                            duration.indexOf("S"));
            } else if (minutesexists)
                seconds = duration.substring(duration.indexOf("M") + 1,
                        duration.indexOf("S"));
            else
                seconds = duration.substring(duration.indexOf("T") + 1,
                        duration.indexOf("S"));
            if (seconds.length() == 1)
                seconds = "0" + seconds;
            time += seconds;
        } else {
            time += "00";
        }
        return time;
    }

    public static String formatCount(double n, int iteration) {
        char[] c = new char[]{'k', 'm', 'b', 't'};
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) % 10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000 ? //this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99) ? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : formatCount(d, iteration + 1));
    }

    public static String rupeeFormatter(double rupees) {
        double rupee = rupees / 10000000;
        if (rupee >= 1) {
            return String.format("%.1f", rupee) + "Cr";
        } else {
            rupee = rupees / 100000;
            if (rupee >= 1) {
                return String.format("%.1f", rupee) + "L";
            } else {
                rupee = rupees / 1000;
                if (rupee >= 1) {
                    return String.format("%.1f", rupee) + "k";
                }
            }
        }
        return "" + rupees;
    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());

        return result;
    }

    public static void rateApplication(Context context) {

        String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void appLaunched(Context context) {
        int DAYS_UNTIL_PROMPT = 7;//Min number of days
        int LAUNCHES_UNTIL_PROMPT = 5;//Min number of launches
        SharedPreferences prefs = context.getSharedPreferences("apprater", Context.MODE_PRIVATE);
        if (prefs.getBoolean(context.getString(R.string.dont_show_again), false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();
        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_first_launch", 0);

        if (date_firstLaunch == 0 && LAUNCHES_UNTIL_PROMPT == launch_count) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_first_launch", date_firstLaunch);
            rateUsAlertDialog(context, editor);
        } else if (launch_count >= LAUNCHES_UNTIL_PROMPT) {   // Wait at least n days before opening
            if (System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                date_firstLaunch = System.currentTimeMillis();
                editor.putLong("date_first_launch", date_firstLaunch);
                rateUsAlertDialog(context, editor);
            }
        }

        editor.apply();
    }

    private static void rateUsAlertDialog(final Context context, final SharedPreferences.Editor editor) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setTitle(context.getString(R.string.APP_NAME));
        alertDialog.setMessage(context.getString(R.string.RATE_US_ON_STORE));
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.LATER), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.YES), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.rateApplication(context);
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.NEVER), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editor != null) {
                    editor.putBoolean(context.getString(R.string.dont_show_again), true);
                    editor.commit();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static void updateAppAlertDialog(final Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setTitle(context.getString(R.string.APP_NAME));
        alertDialog.setMessage(context.getString(R.string.UPDATE_APP_VERSION));
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.LATER), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.YES), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.rateApplication(context);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    /*
    *
    * Returns a Bitmap form a Drawable
    *
    */
    public static Bitmap getBitmapDrawable(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * Hide keyboard whenever soft keyboard action is completed
     * and need to hide keyboard to do any other action
     *
     * @param context get instance of activity
     */

    public static void hideKeyboard(Activity context) {
        // hide keyboard if open
        if (context != null) {
            View keyboardView = context.getCurrentFocus();
            if (keyboardView != null && keyboardView instanceof EditText) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(keyboardView.getWindowToken(), 0);
            }
        }
    }

    public static Map<String, String> GetDeviceInfo(final Context context) {
        Map<String, String> params = new HashMap<>();

        try {
            params.put(context.getString(R.string.app_version), Utils.GetAppVersion());
            params.put(context.getString(R.string.os_name), System.getProperty("os.name"));
            params.put(context.getString(R.string.os_version), Build.VERSION.RELEASE);
            params.put(context.getString(R.string.device_manufacturer), Build.MANUFACTURER);
            params.put(context.getString(R.string.device_model), Build.MODEL);
            params.put(context.getString(R.string.device_name), Build.MODEL);

            try {
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();

                String screenDensity = String.valueOf(metrics.density);
                String screenWidth = String.valueOf(metrics.widthPixels);
                String screenHeight = String.valueOf(metrics.heightPixels);

                params.put(context.getString(R.string.screen_density), screenDensity);
                params.put(context.getString(R.string.screen_width), screenWidth);
                params.put(context.getString(R.string.screen_height), screenHeight);

            } catch (Exception e) {

            }

            TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            String operatorName = telephonyManager.getNetworkOperatorName();
            params.put(context.getString(R.string.network_carrier), operatorName);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
                    == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                String mobileno = telephonyManager.getLine1Number().toString();

                Log.v(" GetDeviceInfo utlis ", "GetDeviceInfo " + mobileno);
                params.put("mobileNo", mobileno);
                return params;
            } else if((ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                            != PackageManager.PERMISSION_GRANTED)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.phone_permission)
                        .setPositiveButton(R.string.taptoaccept, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE},
                                        Constants.RC_HANDLE_READ_SMS_AND_PHONE);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                // if ((Activity)context.isFinishing()) {
                builder.show();
                // }
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS)
                    != PackageManager.PERMISSION_GRANTED ) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.Account_Email_permission)
                        .setPositiveButton(R.string.taptoaccept, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions((Activity) context,
                                        new String[]{Manifest.permission.GET_ACCOUNTS},
                                        Constants.RC_HANDLE_CONTACTS_PERM);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                //if (getActivity() != null && !getActivity().isFinishing()) {
                builder.show();
                //}
                       }
        } catch (Exception e) {

        }

        return params;
    }

    /**
     * @param context application context
     * @param size
     * @return
     */

    public static int getDimensionPixelSize(Context context, int size) {
        return (int) context.getResources().getDimension(size);
    }

    /**
     * Returns application toolbar height
     *
     * @param context application context
     * @return toolbar height
     */
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static ArrayList<Institute> parseInstituteList(String instituteString) {
        List<Institute> instituteList = null;
        try {
            instituteList = JSON.std.listOfFrom(Institute.class, instituteString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (instituteList == null)
                instituteList = new ArrayList<>();
            return (ArrayList) instituteList;
        }
    }

    /**
     * Use to get Current App version
     *
     * @return App Version
     */
    public static String GetAppVersion() {
        return BuildConfig.VERSION_NAME.substring(0, 5);
    }
}
