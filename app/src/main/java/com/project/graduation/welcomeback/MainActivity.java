package com.project.graduation.welcomeback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.evernote.android.job.JobManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    private FirebaseAuth mFirebaseAuth;
    private TextView mName;
    private TextView mEmail;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JobManager.create(this).addJobCreator(new NotificationJobCreator());
        NotificationJob.schedulePeriodic();


        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //we are inflating the TabFragment as the first Fragment when we start
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
        mFirebaseAuth = FirebaseAuth.getInstance();

        View header = navigationView.getHeaderView(0);


        mName = (TextView) header.findViewById(R.id.username_drawer);
        mEmail = (TextView) header.findViewById(R.id.email_drawer);


        mUser = mFirebaseAuth.getCurrentUser();


        String email = mUser.getEmail();
        String name = mUser.getDisplayName();
        //update name and email of the drawer by the current user information


        mEmail.setText(email);
        mName.setText(name);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // handel Item Selected in nav
    private void displaySelectedScreen(int itemId) {
        switch (itemId) {
            case R.id.nav_Home:
                FragmentTransaction homeTransaction = mFragmentManager.beginTransaction();
                homeTransaction.replace(R.id.containerView, new HomeFragment()).commit();
                setTitle("Home");
                break;

            case R.id.nav_notification:
                //todo add intent
                Intent notificationIntent = new Intent(this, NotificationActivity.class);
                startActivity(notificationIntent);
                setTitle("Notification");
                break;
            case R.id.nav_help:
                FragmentTransaction helpTransaction = mFragmentManager.beginTransaction();
                helpTransaction.replace(R.id.containerView, new HelpFragment()).commit();
                setTitle("Help");
                break;
            case R.id.nav_about_us:
                Intent aboutIntent = new Intent(this, AboutUsActivity.class);
                startActivity(aboutIntent);
                setTitle("Notification");
                setTitle("About Us");
                break;
            case R.id.nav_logout:
                mFirebaseAuth.signOut();
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        return true;
    }
}
