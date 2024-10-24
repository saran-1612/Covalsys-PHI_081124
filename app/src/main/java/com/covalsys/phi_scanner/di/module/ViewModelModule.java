package com.covalsys.phi_scanner.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covalsys.phi_scanner.di.ViewModelKey;
import com.covalsys.phi_scanner.ui.ViewModelProviderFactory;
import com.covalsys.phi_scanner.ui.delivery.DeliveryPickListViewModel;
import com.covalsys.phi_scanner.ui.home.HomeViewModel;
import com.covalsys.phi_scanner.ui.login.LoginViewModel;
import com.covalsys.phi_scanner.ui.main.MainViewModel;
import com.covalsys.phi_scanner.ui.scan.delivery.DeliveryBatchScanViewModel;
import com.covalsys.phi_scanner.ui.screen_shot.AddImageViewModel;
import com.covalsys.phi_scanner.ui.screen_shot.ListImageViewModel;
import com.covalsys.phi_scanner.ui.splash.SplashScreenViewModel;
import com.covalsys.phi_scanner.ui.stock_count.StockCountScanViewModel;
import com.covalsys.phi_scanner.ui.stock_count_history.StockHistoryViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Prasanth on 09-07-2020.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsSplashScreenViewModel(SplashScreenViewModel splashScreenViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DeliveryPickListViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsDeliveryPickListViewModel(DeliveryPickListViewModel deliveryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(StockCountScanViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsStackScanCountViewModel(StockCountScanViewModel countScanViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(StockHistoryViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsStoViewModel(StockHistoryViewModel stockHistoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ListImageViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsListImageViewModel(ListImageViewModel listImageViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddImageViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsAddImageViewModel(AddImageViewModel addImageViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DeliveryBatchScanViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsDeliveryBatchScanViewModel(DeliveryBatchScanViewModel deliveryBatchScanViewModel);

    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelProviderFactory viewModelFactory);

}
