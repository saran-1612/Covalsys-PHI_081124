package com.covalsys.phi_scanner.ui.splash;

import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.ui.base.BaseViewModel;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

import javax.inject.Inject;


public class SplashScreenViewModel extends BaseViewModel<SplashScreenNavigator> {

    @Inject
    public SplashScreenViewModel(Repository repository, SchedulerProvider schedulerProvider,
                                 PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
    }



}
