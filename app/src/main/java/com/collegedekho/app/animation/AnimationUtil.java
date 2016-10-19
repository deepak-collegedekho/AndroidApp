package com.collegedekho.app.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by sureshsaini on 12/10/16.
 */

public class AnimationUtil {

    public static void circularReveal(final View view, boolean isShow){

        float finalRadius =  (float) Math.hypot(view.getWidth() / 2f, view.getHeight() / 2f) ;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator  revealAnimator ;
            if(isShow){
                revealAnimator = ViewAnimationUtils.createCircularReveal(view, view.getWidth()/2, view.getHeight(), 0,
                    finalRadius);
                view.setVisibility(View.VISIBLE);
            }else{
                revealAnimator = ViewAnimationUtils.createCircularReveal(view,view.getWidth()/2,view.getHeight(),
                        finalRadius, 0);
                revealAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.INVISIBLE);
                    }
                });
            }
            revealAnimator.setDuration(300);
            revealAnimator.setInterpolator(new FastOutLinearInInterpolator());
            revealAnimator.start();
        }

    }

}
