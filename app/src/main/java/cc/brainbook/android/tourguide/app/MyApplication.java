package cc.brainbook.android.tourguide.app;

import android.app.Application;
import android.content.Context;

import leakcanary.AppWatcher;
import leakcanary.ObjectWatcher;

public class MyApplication extends Application {
    private ObjectWatcher _objectWatcher;

    public static ObjectWatcher getObjectWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application._objectWatcher;
    }

    @Override public void onCreate() {
        super.onCreate();
        /* This is for checking memory leak: https://github.com/square/leakcanary */
        _objectWatcher = AppWatcher.INSTANCE.getObjectWatcher();
    }
}