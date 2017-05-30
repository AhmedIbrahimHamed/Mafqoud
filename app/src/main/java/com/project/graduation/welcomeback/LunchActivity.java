package com.project.graduation.welcomeback;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class LunchActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 800;
    private FirebaseAuth mFirebaseAuth; // firebase authintication.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        mFirebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent intent;
                //if user signed in go to main otherwise go to sign in.
                if (mFirebaseAuth.getCurrentUser() != null) {
                    intent = new Intent(LunchActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(LunchActivity.this, SignInActivity.class);
                }
                startActivity(intent);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}