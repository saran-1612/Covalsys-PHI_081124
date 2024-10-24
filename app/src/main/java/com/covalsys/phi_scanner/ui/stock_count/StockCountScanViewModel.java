package com.covalsys.phi_scanner.ui.stock_count;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.database.entities.StockCountHeader;
import com.covalsys.phi_scanner.data.database.entities.StockCountLine;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.Resource;
import com.covalsys.phi_scanner.data.network.models.get.GetLocationModel;
import com.covalsys.phi_scanner.data.network.models.post.BatchUpdateModel;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.ui.base.BaseViewModel;
import com.covalsys.phi_scanner.utils.DateUtils;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

import java.util.List;

public class StockCountScanViewModel extends BaseViewModel<StockCountScanNavigator> {

    public MutableLiveData<Context> mContext;

    public MutableLiveData<Resource<GetLocationModel>> liveDataLocation;

    public MutableLiveData<Boolean> progressBar;

    public MediatorLiveData<List<StockCountLine>> model = new MediatorLiveData<>();

    public StockCountScanViewModel(Repository repository, SchedulerProvider schedulerProvider, PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        this.liveDataLocation = new MutableLiveData<>();
        this.progressBar = new MutableLiveData<>();
        this.mContext = new MutableLiveData<>();
        init_();
    }

    public void createProfileInit(){
        createProfile();
    }

    public void endSessionInit(){
        getEndSession();
    }

    public void startSessionInit(){
        getStartSession();
    }

    public void updateBatchInit(String batch, String profile, String itemType, String location, String scanData, Float qty){
        updateBatch(batch, profile, itemType, location, scanData, qty);
    }

    public void getDocLineGlobal(String profileName, String location, String itemType, String batchNo, Float qty, String scanTime, String scanDate, String batchStatus, String scanData){
        getDocLine(profileName,location,itemType,batchNo,(float) 0.00,scanTime,scanDate,batchStatus,scanData);
    }

    private void getDocLine(String profileName, String location, String itemType, String batchNo, Float qty, String scanTime, String scanDate, String batchStatus, String scanData) {
        progressBar.setValue(true);
        StockCountLine countLine = new StockCountLine(profileName,location,itemType,batchNo,qty,scanTime,scanDate,batchStatus,scanData);
        getDatabase().stockCountLineDao().insertStackCountScan(countLine);
        getPreferenceHelper().setFirstName(profileName);
    }

    public LiveData<List<StockCountLine>> getScanData() {
        return getDatabase().stockCountLineDao().getStockLine();
    }

    private void init_() {
        getProfile();
        getLocation_();
    }

    private void getLocation_() {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getLocationModel()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getLocationModel -> {
                    progressBar.setValue(false);
                    if (getLocationModel.getStatusCode()==0){
                        liveDataLocation.postValue(Resource.success(getLocationModel));
                    } else {
                        liveDataLocation.postValue(Resource.error(getLocationModel.getStatusMessage(),null));
                    }
                },throwable -> {
                    progressBar.setValue(false);
                    liveDataLocation.postValue(Resource.error(throwable.toString(),null));
                }));
    }

    private void getEndSession() {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService().endSession(getPreferenceHelper().getStockDocNum())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    progressBar.setValue(false);
                    if (response.getStatusCode()==0){
                        getNavigator().onPostEndSuccess(response.getResponseObject());
                    } else {
                        getNavigator().onPostEndFailed(response.getStatusMessage());
                    }
                },throwable -> {
                    progressBar.setValue(false);
                    getNavigator().onPostEndFailed(throwable.getMessage());
                }));
    }

    private void getStartSession() {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService().startSession(getPreferenceHelper().getStockDocNum())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    progressBar.setValue(false);
                    if (response.getStatusCode()==0){
                        getNavigator().onPostStartSuccess(response.getResponseObject());
                    } else {
                        getNavigator().onPostStartFailed(response.getStatusMessage());
                    }
                },throwable -> {
                    progressBar.setValue(false);
                    getNavigator().onPostStartFailed(throwable.getMessage());
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
                        getDatabase().stockCountHeaderDao().insertStackCountHeader(new StockCountHeader(response.getResponseObject().getProfileName(), response.getResponseObject().getDocNum(), response.getResponseObject().getOpenQty(), response.getResponseObject().getClosedQty()));
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
                        getPreferenceHelper().setStockDocNum(response.getResponseObject().getDocNum());
                        getNavigator().onProfileGetSuccess(response.getResponseObject());
                        //getNavigator().onPostSuccess(response.getStatusMessage());
                    } else {
                        getNavigator().onProfileGetFailed(response.getStatusMessage());
                       // getNavigator().onPostFailed(response.getStatusMessage());
                    }
                },throwable -> {
                    progressBar.setValue(false);
                    getNavigator().onProfileGetFailed(throwable.getMessage());
                    //getNavigator().onPostFailed(throwable.getMessage());
                }));
    }

    private void updateBatch(String batch, String profile, String itemType, String location, String scanData, Float qty) {

        BatchUpdateModel model = new BatchUpdateModel(batch, getPreferenceHelper().getStockDocNum(), itemType , location, getPreferenceHelper().getSalesEmpCode(), profile, qty, scanData, DateUtils.currentDateTimeYYYY(), DateUtils.currentTime());

        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService().updateBatch(model)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    progressBar.setValue(false);
                    if (response.getStatusCode()==0){
                        getDatabase().stockCountLineDao().add(new StockCountLine(profile, location, itemType, batch, (float)response.getResponseObject().getQuantity(), DateUtils.currentTime(), DateUtils.currentDate_(), "Y", ""));
                        getNavigator().onPostSuccess(response.getStatusMessage());
                    } else {
                        getNavigator().onPostFailed(response.getStatusMessage());
                    }
                },throwable -> {
                    progressBar.setValue(false);
                    getNavigator().onPostFailed(throwable.getMessage());
                }));
    }

    public void setContext(Context context) {
        mContext.setValue(context);
    }

    public LiveData<Resource<GetLocationModel>> getLiveDataLocation(){
        return liveDataLocation;
    }

    public void clearData() {

        getDatabase().stockCountLineDao().deleteAllData();

    }
}
