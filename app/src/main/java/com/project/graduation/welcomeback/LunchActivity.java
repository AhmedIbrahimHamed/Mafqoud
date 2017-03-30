package com.project.graduation.welcomeback;

/**
 * created by: TODO: Enter your name here
 */

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

/*
 * this screen for show logo and determined go to sign in screen or main screen.
 */

public class LunchActivity extends AppCompatActivity {

    private final int LUNCH_DISPLAY_LENGTH = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent signInIntent = new Intent(LunchActivity.this,SignInActivity.class);
                LunchActivity.this.startActivity(signInIntent);
                LunchActivity.this.finish();
            }
        }, LUNCH_DISPLAY_LENGTH);
    }
}
