package com.covalsys.phi_scanner.di.component;

import android.app.Application;

import com.covalsys.phi_scanner.MyApplication;
import com.covalsys.phi_scanner.di.builder.ActivityBuilder;
import com.covalsys.phi_scanner.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Prasanth on 09-07-2020.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

  //  void inject(LocationService service);

    void inject(MyApplication myApplication);
}
