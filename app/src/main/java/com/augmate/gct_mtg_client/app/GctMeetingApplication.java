package com.augmate.gct_mtg_client.app;

import android.app.Application;

public class GctMeetingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.setupApplication(this);
        Log.debug("Application created");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.debug("Application terminating");
    }
}