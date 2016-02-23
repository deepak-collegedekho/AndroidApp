package com.collegedekho.app.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.BuildConfig;
import com.collegedekho.app.htmlparser.HtmlSpanner;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.resource.TypeFaceTypes;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sureshsaini on 12/10/15.
 */
public class Utils {

    private static BroadcastReceiver mPowerKeyReceiver;
    private static boolean screenGotOff;

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
//                            textView.setMovementMethod(LinkMovementMethod.getInstance());
                            textView.setText(spanner.fromTagNode(childNode, null));
                            parentLayout.addView(textView);
                        }
                    } else {
                        TextView textView = new TextView(context);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, -30);
                        textView.setLayoutParams(layoutParams);
//                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                        textView.setText(spanner.fromTagNode(childNode, null));
                        parentLayout.addView(textView);
                    }
                }
            } else {
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, -30);
                textView.setLayoutParams(layoutParams);
//                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setText(spanner.fromTagNode(node, null));
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
                    Log.v("","Screen OFF");
                    Utils.screenGotOff = true;
                }
                if (strAction.equals(Intent.ACTION_SCREEN_ON)) {
                    Log.v("","Screen ON");
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
            }
            catch (IllegalArgumentException e) {
                mPowerKeyReceiver = null;
            }
        }
        else {
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

    public static void logApiResponseTime(Calendar calendar, String tag){
        if(!BuildConfig.DEBUG){
            return;
        }
        if(calendar !=null){
            try {
                Calendar cal = Calendar.getInstance();
                long diff = cal.getTimeInMillis() - calendar.getTimeInMillis();
                int milliSecs = (int) diff % (1000);
                int seconds = (int) (diff / 1000) % 60;
                int minutes = (int) ((diff / (1000 * 60)) % 60);
//            int hours   = (int) ((diff / (1000*60*60)) % 24);
                String log=tag + " Started : " + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + ":" + calendar.get(Calendar.MILLISECOND) + " Stopped : " + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + ":" + cal.get(Calendar.MILLISECOND) + " Time: " + minutes + ":" + seconds + ":" + milliSecs;
                Log.e("API_PROFILE", log);
                FileLogger.writeLog(log);
            }catch (Exception e){

            }
        }
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
        }else {
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
        }else {
            time += "00";
        }
        return time;
    }
//    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
//    static {
//        suffixes.put(1_000L, "k");
//        suffixes.put(1_000_000L, "M");
//        suffixes.put(1_000_000_000L, "G");
//        suffixes.put(1_000_000_000_000L, "T");
//        suffixes.put(1_000_000_000_000_000L, "P");
//        suffixes.put(1_000_000_000_000_000_000L, "E");
//    }
//    public static String formatCount(long value) {
//        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
//        if (value == Long.MIN_VALUE) return formatCount(Long.MIN_VALUE + 1);
//        if (value < 0) return "-" + formatCount(-value);
//        if (value < 1000) return Long.toString(value); //deal with easy case
//
//        Map.Entry<Long, String> e = suffixes.floorEntry(value);
//        Long divideBy = e.getKey();
//        String suffix = e.getValue();
//
//        long truncated = value / (divideBy / 10); //the number part of the output times 10
//        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
//        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
//    }

    public static String formatCount(double n, int iteration) {
        char[] c = new char[]{'k', 'm', 'b', 't'};
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) %10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000? //this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99)? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : formatCount(d, iteration+1));
    }

    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;
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
}
