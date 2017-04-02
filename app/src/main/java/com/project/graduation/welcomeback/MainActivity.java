package com.project.graduation.welcomeback;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            case R.id.nav_profile:
                FragmentTransaction profileTransaction = mFragmentManager.beginTransaction();
                profileTransaction.replace(R.id.containerView, new MyProfileFragment()).commit();
                setTitle("Profile");
                break;
            case R.id.nav_beta:
                FragmentTransaction betaTransaction = mFragmentManager.beginTransaction();
                betaTransaction.replace(R.id.containerView, new BetaFragment()).commit();
                setTitle("Beta");
                break;
            case R.id.nav_notification:
                FragmentTransaction notificationTransaction = mFragmentManager.beginTransaction();
                notificationTransaction.replace(R.id.containerView, new NotificationFragment()).commit();
                setTitle("Notification");
                break;
            case R.id.nav_help:
                FragmentTransaction helpTransaction = mFragmentManager.beginTransaction();
                helpTransaction.replace(R.id.containerView, new HelpFragment()).commit();
                setTitle("Help");
                break;
            case R.id.nav_contact_us:
                FragmentTransaction contactUsTransaction = mFragmentManager.beginTransaction();
                contactUsTransaction.replace(R.id.containerView, new ContactUsFragment()).commit();
                setTitle("Contact Us");
                break;
            // TODO: handel logout
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
