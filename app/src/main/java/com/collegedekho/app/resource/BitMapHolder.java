package com.collegedekho.app.resource;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
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
    private Context mContext;

    public BitMapHolder(){
        // required empty constructor
    }

   public void setContext(Context context)
   {
       this.mContext = context;
   }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public void  getBitMapFromResource(){
        if(this.drawableShortList == null)
            this.drawableShortList = ContextCompat.getDrawable(mContext, R.drawable.ic_shortlist);
        if(this.drawableNotIntrested == null)
            this.drawableNotIntrested = ContextCompat.getDrawable(mContext, R.drawable.ic_not_interested);
        if(this.drawableUndecided == null)
            this. drawableUndecided = ContextCompat.getDrawable(mContext, R.drawable.ic_undecided);
        if(SHORTLISTED_BITMAP == null)
           SHORTLISTED_BITMAP = getBitmap((VectorDrawable)this.drawableShortList);
        if(UNSHORTLISTED_BITMAP == null)
           UNSHORTLISTED_BITMAP = getBitmap((VectorDrawable)this.drawableNotIntrested);
        if(UNDECIDED_BITMAP == null)
           UNDECIDED_BITMAP = getBitmap((VectorDrawable)this.drawableUndecided);

    }
}
