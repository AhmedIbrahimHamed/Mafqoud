package com.project.graduation.welcomeback;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by ayyad on 5/22/2017.
 */

public class NotificationJobCreator implements JobCreator {
    @Override
    public Job create(String tag) {
        switch (tag) {
            case NotificationJob.TAG:
                return new NotificationJob();
            default:
                return null;
        }
    }
}
