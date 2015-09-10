package com.collegedekho.app.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * @author Mayank Gautam
 *         Created: 16/07/15
 */
public class FadeInImageView extends NetworkImageView {
    private static final long ANIM_DURATION = 500;
    private boolean shouldAnimate;

    public FadeInImageView(Context context) {
        super(context);
    }

    public FadeInImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FadeInImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (shouldAnimate) {
            ObjectAnimator.ofFloat(this, "alpha", 0, 1).setDuration(ANIM_DURATION).start();
        }
    }

    @Override
    public void setImageUrl(String url, ImageLoader imageLoader) {
        shouldAnimate = !imageLoader.isCached(url, 0, 0);
        super.setImageUrl(url, imageLoader);
    }
}
