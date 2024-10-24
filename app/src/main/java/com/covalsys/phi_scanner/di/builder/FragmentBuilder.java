package com.covalsys.phi_scanner.di.builder;

import com.covalsys.phi_scanner.ui.delivery.DeliveryPickListFragment;
import com.covalsys.phi_scanner.ui.home.HomeFragment;
import com.covalsys.phi_scanner.ui.screen_shot.AddImageFragment;
import com.covalsys.phi_scanner.ui.screen_shot.ListImageFragment;
import com.covalsys.phi_scanner.ui.stock_count.StockCountScanFragment;
import com.covalsys.phi_scanner.ui.stock_count_history.StockHistoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Prasanth on 09-07-2020.
 */
@Module
public abstract class FragmentBuilder {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract DeliveryPickListFragment contributeDeliveryPickListFragment();

    @ContributesAndroidInjector
    abstract StockCountScanFragment stackCountScanFragment();

    @ContributesAndroidInjector
    abstract StockHistoryFragment stockHistoryFragment();

    @ContributesAndroidInjector
    abstract ListImageFragment imageFragment();

    @ContributesAndroidInjector
    abstract AddImageFragment addImageFragment();

}
