package com.project.graduation.welcomeback;

import android.app.Application;

import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;

/**
 * Created by ayyad on 5/22/2017.
 */

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new NotificationJobCreator());
        JobManager.instance().getConfig().setAllowSmallerIntervalsForMarshmallow(true); // Don't use this in production
    }
}
