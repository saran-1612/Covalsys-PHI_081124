package com.covalsys.phi_scanner.ui.screen_shot;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.database.entities.ImageLine;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.ui.base.BaseViewModel;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

public class AddImageViewModel extends BaseViewModel<AddImageNavigator> {

    public MutableLiveData<Context> mContext;
    public MutableLiveData<Boolean> progressBar;
    public MutableLiveData<ImageLine> liveData = new MutableLiveData<>();

    public AddImageViewModel(Repository repository, SchedulerProvider schedulerProvider, PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        this.mContext = new MutableLiveData<>();
        this.progressBar = new MutableLiveData<>();
        init_();
    }

    private void init_() {

    }
}
