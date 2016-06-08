package com.collegedekho.app.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.collegedekho.app.R;
import com.collegedekho.app.utils.Utils;


/**
 * Created by sureshsaini on 5/4/16.
 */
public class BitMapHolder {

    private Drawable drawableShortList ;
    private Drawable drawableNotInterested;
    private Drawable drawableUndecided ;
    public static Bitmap SHORTLISTED_BITMAP;
    public static Bitmap UN_SHORTLISTED_BITMAP;
    public static Bitmap UNDECIDED_BITMAP;
    public static Bitmap DEFAULT_BANNER;
    private Context mContext;

    public BitMapHolder(){
        // required empty constructor
    }

   public void setContext(Context context)
   {
       this.mContext = context;
   }



    public void  getBitMapFromResource(){

        if(this.drawableShortList == null)
            this.drawableShortList = ContextCompat.getDrawable(mContext, R.drawable.move_to_wishlist);
        if(this.drawableNotInterested == null)
            this.drawableNotInterested = ContextCompat.getDrawable(mContext, R.drawable.not_interested);
        if(this.drawableUndecided == null)
            this. drawableUndecided = ContextCompat.getDrawable(mContext, R.drawable.decide_later);

        if(SHORTLISTED_BITMAP == null)
            SHORTLISTED_BITMAP   =  Utils.getBitmapDrawable(drawableShortList);
        if(UN_SHORTLISTED_BITMAP == null)
            UN_SHORTLISTED_BITMAP =  Utils.getBitmapDrawable(drawableNotInterested);
        if(UNDECIDED_BITMAP == null)
            UNDECIDED_BITMAP     =  Utils.getBitmapDrawable(drawableUndecided);
        if(DEFAULT_BANNER == null)
            DEFAULT_BANNER       =  BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_banner);

    }
}
