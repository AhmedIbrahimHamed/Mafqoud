package com.project.graduation.welcomeback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(getString(R.string.about_us_description))
                .setImage(R.drawable.logo_about)
                .addItem(new Element().setTitle("Mafqoud"))
                .addEmail("muhammad.mattia@gmail.com")
                .addGitHub("MuhammadAttia")
                .addFacebook("MuhammadAttia95")
                .addTwitter("MuhammadAttia95")
                .addPlayStore("com.ideashower.readitlater.pro")
                .create();

        setContentView(aboutPage);
    }


}
