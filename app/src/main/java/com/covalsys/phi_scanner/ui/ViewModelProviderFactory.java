package com.covalsys.phi_scanner.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
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
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository mRepository;
    private final PreferenceHelper mPreferenceHelper;
    private final AppDatabase mDatabase;
    private final RoomHelper mHelper;
    private final ApiService mApiService;
    private final SchedulerProvider mSchedulerProvider;

    @Inject
    public ViewModelProviderFactory(Repository repository, SchedulerProvider schedulerProvider,
                                    PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        this.mRepository = repository;
        this.mPreferenceHelper = preferenceHelper;
        this.mDatabase = database;
        this.mHelper = helper;
        this.mApiService = service;
        this.mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)){
            //noinspection unchecked
            return (T) new HomeViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        }else if (modelClass.isAssignableFrom(LoginViewModel.class)){
            //noinspection unchecked
            return (T) new LoginViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        }else if (modelClass.isAssignableFrom(MainViewModel.class)){
            //noinspection unchecked
            return (T) new MainViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(SplashScreenViewModel.class)){
            //noinspection unchecked
            return (T) new SplashScreenViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(DeliveryPickListViewModel.class)){
            //noinspection unchecked
            return (T) new DeliveryPickListViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(DeliveryBatchScanViewModel.class)){
            //noinspection unchecked
            return (T) new DeliveryBatchScanViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(StockCountScanViewModel.class)){
            //noinspection unchecked
            return (T) new StockCountScanViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(StockHistoryViewModel.class)){
            //noinspection unchecked
            return (T) new StockHistoryViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        }else if (modelClass.isAssignableFrom(AddImageViewModel.class)){
            //noinspection unchecked
            return (T) new AddImageViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        }else if (modelClass.isAssignableFrom(ListImageViewModel.class)){
            //noinspection unchecked
            return (T) new ListImageViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
