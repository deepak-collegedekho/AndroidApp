package com.collegedekho.app.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;

public class SplashFragment extends BaseFragment {

    private ImageView mLogoView;
    private AnimationDrawable mFrameAnimation;
    private boolean IS_FIRST_TIME = true;   // check id splash animation is already executed

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
        ((MainActivity)getActivity()).init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.splash_screen, container, false);
        return rootView;
    }


    public void stopMainAnimation()
    {
        if(this.mFrameAnimation != null)
            this.mFrameAnimation.stop();

        //Start the secondary animation
        //this.mLogoView.startAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.bounce_slow));
    }

    @Override
    public void onResume() {
        super.onResume();
        final View view = getView();
        if(view != null)
        {
            FrameLayout layout =(FrameLayout) getActivity().findViewById(R.id.container);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();
            params.setBehavior(null);

            int splashTime = 6500;

            if(IS_FIRST_TIME) {
                IS_FIRST_TIME = false;
                this.mLogoView = (ImageView) view.findViewById(R.id.splash_logo_view);
                // Load the ImageView that will host the animation and
                // set its background to our AnimationDrawable XML resource.
                this.mLogoView.setBackgroundResource(R.drawable.loading_animation);

                // Get the background, which has been compiled to an AnimationDrawable object.
                this.mFrameAnimation = (AnimationDrawable) this.mLogoView.getBackground();

                // Start the main animation (looped playback by default).
                this.mFrameAnimation.start();
            }
            else
            {
                splashTime=0;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Stop the Main Animation and Start Secondary
                    stopMainAnimation();
                    //check if this device is connected to internet
                    int amIConnectedToInternet = MainActivity.networkUtils.getConnectivityStatus();
                    if (amIConnectedToInternet != Constants.TYPE_NOT_CONNECTED) {
                        view.findViewById(R.id.splash_no_internet_info_layout).setVisibility(View.GONE);

                        Constants.IS_CONNECTED_TO_INTERNET = true;
                        //Initialize the app
                        MainActivity mainActivity = (MainActivity)getActivity();
                        if(mainActivity != null ) {
                            mainActivity.loadInItDtata();
                        }

                    } else
                        view.findViewById(R.id.splash_no_internet_info_layout).setVisibility(View.VISIBLE);
                }
            }, splashTime);
        }
    }
}
