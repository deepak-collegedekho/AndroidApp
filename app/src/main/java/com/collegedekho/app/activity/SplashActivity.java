package com.collegedekho.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.collegedekho.app.R;
import com.collegedekho.app.widget.GifView;

/**
 * Created by sureshsaini on 16/12/16.
 */

public class SplashActivity extends AppCompatActivity implements GifView.OnGifCompletedListener{

    private static final String TAG = "Splash Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onGifCompleted() {
        findViewById(R.id.splash_gif_view).setBackground(getResources().getDrawable(R.drawable.college_logo));
        Intent i = new Intent(SplashActivity.this,
                MainActivity.class);
        // any info loaded can during splash_show
        // can be passed to main activity using
        // below
        i.putExtra("loaded_info", " ");
        startActivity(i);
    }

}
