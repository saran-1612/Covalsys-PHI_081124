package com.covalsys.phi_scanner.di.builder;

import com.covalsys.phi_scanner.ui.login.LoginActivity;
import com.covalsys.phi_scanner.ui.main.MainActivity;
import com.covalsys.phi_scanner.ui.scan.delivery.DeliveryBatchScanActivity;
import com.covalsys.phi_scanner.ui.splash.SplashScreenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Prasanth on 09-07-2020.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract SplashScreenActivity splashScreenActivity();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract DeliveryBatchScanActivity deliveryBatchScanActivity();



}
