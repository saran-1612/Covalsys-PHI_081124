package com.covalsys.phi_scanner;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.WindowManager;

import com.covalsys.phi_scanner.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * Created by CBS on 09-07-2020.
 */
public class MyApplication extends Application implements HasActivityInjector  {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static MyApplication sInstance;

    public static MyApplication getAppContext() {
        return sInstance;
    }

    private static synchronized void setInstance(MyApplication app) {
        sInstance = app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        setInstance(this);
        initializeCalligraphy();
        //setupActivityListener();
    }

    private void initializeComponent() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

    }

    private void initializeCalligraphy() {
        ViewPump.init(ViewPump.builder().addInterceptor(new CalligraphyInterceptor(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/sans.ttf")   //SourceSansPro-Regular
                .setFontAttrId(R.attr.fontPath)
                .build())).build());
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    private void setupActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);            }
            @Override
            public void onActivityStarted(Activity activity) {
            }
            @Override
            public void onActivityResumed(Activity activity) {

            }
            @Override
            public void onActivityPaused(Activity activity) {

            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }


}
