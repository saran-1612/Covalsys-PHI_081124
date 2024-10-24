package com.covalsys.phi_scanner.ui.stock_count_history;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.Resource;
import com.covalsys.phi_scanner.data.network.models.get.GetProfileModel;
import com.covalsys.phi_scanner.data.network.models.get.StockHistoryModel;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.ui.base.BaseViewModel;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

public class StockHistoryViewModel extends BaseViewModel<StockHistoryNavigator> {

    public MutableLiveData<Context> mContext;
    public MutableLiveData<GetProfileModel> liveDataLocation;
    public MutableLiveData<Boolean> progressBar;
    public MutableLiveData<Resource<StockHistoryModel>> model;

    public StockHistoryViewModel(Repository repository, SchedulerProvider schedulerProvider, PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        this.liveDataLocation = new MutableLiveData<>();
        this.progressBar = new MutableLiveData<>();
        this.mContext = new MutableLiveData<>();
        this.model = new MutableLiveData<>();
        init_();
    }

    public void createProfileInit(){
        createProfile();
    }

    private void init_() {
        getProfile();
        getDataList("");
    }

    public void getDataList(String text) {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService().getDataList(text)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(object -> {
                    progressBar.setValue(false);
                    model.postValue(Resource.success(object));
                }, throwable -> {
                     Log.e("TAG", throwable.getMessage()+"");
                    model.postValue(Resource.error(throwable.getMessage(), null));
                }));
    }

    private void getProfile() {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService().getProfile()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    progressBar.setValue(false);
                    if (response.getStatusCode()==0){
                        getPreferenceHelper().setStockDocNum(response.getResponseObject().getDocNum());
                        getNavigator().onProfileGetSuccess(response.getResponseObject());
                    } else {
                        getNavigator().onProfileGetFailed(response.getStatusMessage());
                    }
                },throwable -> {
                    progressBar.setValue(false);
                    getNavigator().onProfileGetFailed(throwable.getMessage());
                }));
    }

    private void createProfile() {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService().createProfile(getPreferenceHelper().getSalesEmpCode())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    progressBar.setValue(false);
                    if (response.getStatusCode()==0){
                        getNavigator().onPostSuccess(response.getStatusMessage());
                    } else {
                        getNavigator().onPostFailed(response.getStatusMessage());
                    }
                },throwable -> {
                    progressBar.setValue(false);
                    getNavigator().onPostFailed(throwable.getMessage());
                }));
    }

    public MutableLiveData<Resource<StockHistoryModel>> getLiveDataLocation(){
        return model;
    }

    public void setContext(Context context) {
        mContext.setValue(context);
    }

}
