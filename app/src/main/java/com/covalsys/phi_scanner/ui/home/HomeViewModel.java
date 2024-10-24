package com.covalsys.phi_scanner.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.ui.base.BaseViewModel;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    public static final String TAG = HomeViewModel.class.getSimpleName();
    public MutableLiveData<Boolean> progressBar;

    @Inject
    public HomeViewModel(Repository repository, SchedulerProvider schedulerProvider,
                         PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        progressBar = new MutableLiveData<>();
    }




}