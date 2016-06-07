package com.collegedekho.app.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.utils.Utils;

public class FadeInImageView extends NetworkImageView {

    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 3.5f;

    private Bitmap mLocalBitmap;

    private boolean mShowLocal;

    public void setLocalImageBitmap(Bitmap bitmap, boolean shoudlBlur) {
        this.mShowLocal = true;

        if (shoudlBlur)
            this.mLocalBitmap = Utils.FastBlur(bitmap, FadeInImageView.BITMAP_SCALE, 1);
        else
            this.mLocalBitmap = bitmap;

        requestLayout();
    }

    @Override
    public void setImageUrl(String url, ImageLoader imageLoader) {
        this.mShowLocal = false;
        super.setImageUrl(url, imageLoader);
    }

    public FadeInImageView(Context context) {
        this(context, null);
    }

    public FadeInImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FadeInImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        if (mShowLocal) {
            setImageBitmap(mLocalBitmap);
        }
    }
}