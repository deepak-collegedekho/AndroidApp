package com.collegedekho.app.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.collegedekho.app.R;

/**
 * Created by sureshsaini on 5/4/16.
 */
public class BitMapHolder {

    private Drawable drawableShortList ;
    private Drawable drawableNotIntrested ;
    private Drawable drawableUndecided ;
    public static Bitmap SHORTLISTED_BITMAP;
    public static Bitmap UNSHORTLISTED_BITMAP;
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


    private Bitmap getBitmapDrawable(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void  getBitMapFromResource(){

        if(this.drawableShortList == null)
            this.drawableShortList = ContextCompat.getDrawable(mContext, R.drawable.ic_shortlist_vector);
        if(this.drawableNotIntrested == null)
            this.drawableNotIntrested = ContextCompat.getDrawable(mContext, R.drawable.ic_not_interested_vector);
        if(this.drawableUndecided == null)
            this. drawableUndecided = ContextCompat.getDrawable(mContext, R.drawable.ic_undecided_vector);

        if(SHORTLISTED_BITMAP == null)
            SHORTLISTED_BITMAP   =  getBitmapDrawable(drawableShortList);
        if(UNSHORTLISTED_BITMAP == null)
            UNSHORTLISTED_BITMAP =  getBitmapDrawable(drawableNotIntrested);
        if(UNDECIDED_BITMAP == null)
            UNDECIDED_BITMAP     =  getBitmapDrawable(drawableUndecided);
        if(DEFAULT_BANNER == null)
            DEFAULT_BANNER       =  BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_banner);

    }
}
