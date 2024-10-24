package com.covalsys.phi_scanner.ui.main;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.database.entities.StockCountHeader;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.ui.base.BaseViewModel;
import com.covalsys.phi_scanner.ui.home.HomeNavigator;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Prasanth on 09-07-2020.
 */
public class MainViewModel extends BaseViewModel<HomeNavigator> {

    private static final String TAG = "MainViewModel";

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;

    private final ObservableField<String> appVersion = new ObservableField<>();

    private final ObservableField<String> userEmail = new ObservableField<>();

    private final ObservableField<String> userName = new ObservableField<>();

    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();


    @Inject
    public MainViewModel(Repository repository, SchedulerProvider schedulerProvider,
                         PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
    }

    public String getUserName(){
        return getPreferenceHelper().getSalesEmpName();
    }

    public String getUserMobile(){
        return getPreferenceHelper().getMobileNo();
    }

    public String getUserType(){
        return getPreferenceHelper().getEmpTypeCode();
    }

    public String getUserCode(){
        return getPreferenceHelper().getUserCode();
    }

    public LiveData<List<StockCountHeader>> getItemCount() {
        return getDatabase().stockCountHeaderDao().getData();
    }

    public void deleteLocal() {
        getPreferenceHelper().setIsLoggedIn(false);
        getDatabase().stockCountScanLineDao().deleteAllData();
        getDatabase().stockCountLineDao().deleteAllData();
        getDatabase().stockCountHeaderDao().deleteAllData();
    }
}

