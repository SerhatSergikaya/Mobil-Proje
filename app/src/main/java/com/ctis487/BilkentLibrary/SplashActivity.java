package com.ctis487.BilkentLibrary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
Intent intent;
private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        MediaPlayer ring= MediaPlayer.create(SplashActivity.this,R.raw.opening);
        ring.start();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show your application logo or company logo
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your application's main activity which is a ListView
                intent = new Intent(SplashActivity.this, MainActivity.class);

                // Close this activity
                finish();

                startActivity(intent);
            }
        }, SPLASH_TIME_OUT);

    }
}
