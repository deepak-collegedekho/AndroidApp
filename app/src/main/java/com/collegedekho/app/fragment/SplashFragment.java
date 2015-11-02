package com.collegedekho.app.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.collegedekho.app.R;

public class SplashFragment extends BaseFragment {

    private ImageView mLogoView;
    private TextView noInternetMessage;
    private AnimationDrawable mFrameAnimation;

    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.splash_screen, container, false);

        this.mLogoView = (ImageView) rootView.findViewById(R.id.splash_logo_view);
        this.noInternetMessage = (TextView) rootView.findViewById(R.id.splash_no_internet_info_layout);

        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
       this.mLogoView.setBackgroundResource(R.drawable.loading_animation);

        // Get the background, which has been compiled to an AnimationDrawable object.
       this.mFrameAnimation = (AnimationDrawable) this.mLogoView.getBackground();

        // Start the main animation (looped playback by default).
        this.mFrameAnimation.start();

       // WebView wv = (WebView)rootView.findViewById(R.id.spalsh_logo_animation);
       // wv.loadUrl("../assets/splash_animation.html");



        return rootView;
    }

   public void noInternetFound()
    {
        this.noInternetMessage.setVisibility(View.VISIBLE);
    }

    public void stopMainAnimation()
    {
       this.mFrameAnimation.stop();

        //Start the secondary animation
        //this.mLogoView.startAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.bounce_slow));
    }


}
