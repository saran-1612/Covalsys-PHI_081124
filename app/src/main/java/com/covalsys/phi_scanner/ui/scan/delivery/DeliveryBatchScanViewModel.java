package com.covalsys.phi_scanner.ui.scan.delivery;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.database.entities.DeliveryScanLine;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.Resource;
import com.covalsys.phi_scanner.data.network.models.get.GetBatchInfo;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.ui.base.BaseViewModel;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

public class DeliveryBatchScanViewModel extends BaseViewModel<DeliveryBatchScanNavigator> {

    public static final String TAG = DeliveryBatchScanViewModel.class.getSimpleName();
    public MutableLiveData<Boolean> progressBar;
    public MediatorLiveData<List<DeliveryScanLine>> scanModel;
    public MutableLiveData<Resource<GetBatchInfo>> dDoc;

    @Inject
    public DeliveryBatchScanViewModel(Repository repository, SchedulerProvider schedulerProvider,
                                      PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        progressBar = new MutableLiveData<>();
        scanModel = new MediatorLiveData<>();
        dDoc = new MutableLiveData<>();
    }

    public void scanChanges() {
        LiveData<List<DeliveryScanLine>> listLiveData = getDatabase().deliveryScanLineDao().getData();
        scanModel.addSource(listLiveData, pallets -> {
            scanModel.setValue(pallets);
        });
    }

    public MediatorLiveData<List<DeliveryScanLine>> getScanData() {
        return scanModel;
    }

    public void getBatchValue(String batch){
        getBarcodeBatch(batch);
    }

    public void getBatchInfo(String batch){
        Log.e("TTG", getDatabase().deliveryScanLineDao().isAddToCart(batch)+"");
        if(getDatabase().deliveryScanLineDao().isAddToCart(batch) != 1) {
            getBarcodeBatchInfo(batch);
            Log.e("TAGH", "0");
        }else{
            getNavigator().batchFetchFailed("Scanned Batch No. Already Exist.");
            Log.e("TAGH", "1");
        }
    }

    private void getBarcodeBatch(String batchNo) {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getBatchDetails(batchNo)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(customerDetailsModel1 -> {
                    progressBar.setValue(false);
                    if (customerDetailsModel1.getStatusCode() == 0) {
                        dDoc.postValue(Resource.success(customerDetailsModel1));
                    } else {
                        dDoc.postValue(Resource.error(customerDetailsModel1.getStatusMessage(), null));
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                    dDoc.postValue(Resource.error(throwable.toString(), null));
                }));
    }

    private void getBarcodeBatchInfo(String batchNo) {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getBatchDetails(batchNo)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getBatchInfo -> {
                    progressBar.setValue(false);
                    if (getBatchInfo.getStatusCode() == 0) {
                        getNavigator().batchFetchSuccess(getBatchInfo.getResponseObject());
                    } else {
                        getNavigator().batchFetchFailed(getBatchInfo.getStatusMessage());
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                    getNavigator().batchFetchFailed(throwable.getMessage());
                }));
    }
}
