package com.sooch.framework;

import android.app.Application;

/**
 * Created by Takashi Sou on 2016/09/12.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;
    private AppComponent appComponent;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
