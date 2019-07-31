package com.collegedekho.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.widget.GifView;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;

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
        Profile mProfile=null;
        SharedPreferences sp = getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE);

        findViewById(R.id.splash_gif_view).setBackground(getResources().getDrawable(R.drawable.college_logo));
        try {
             mProfile = JSON.std.beanFrom(Profile.class, sp.getString(getString(R.string.KEY_USER), null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("Time", false)) {

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("Time", true);
            editor.apply();

           sp.edit().clear().apply();

            Intent i = new Intent(SplashActivity.this,
                    MainActivity.class);
            // any info loaded can during splash_show
            // can be passed to main activity using
            // below
            i.putExtra("loaded_info", " ");
            i.putExtra("show_login",true);
            startActivity(i);
            finish();

        }
        else
        if(mProfile!=null&&mProfile.getToken()!=null) {
            Intent i = new Intent(SplashActivity.this,
                    MainActivity.class);
            // any info loaded can during splash_show
            // can be passed to main activity using
            // below
            i.putExtra("loaded_info", " ");
            startActivity(i);
            finish();
        }
        else{
            Intent i = new Intent(SplashActivity.this,
                    MainActivity.class);
            // any info loaded can during splash_show
            // can be passed to main activity using
            // below
            i.putExtra("loaded_info", " ");
            i.putExtra("show_login",true);
            startActivity(i);
            finish();
        }
    }

}
