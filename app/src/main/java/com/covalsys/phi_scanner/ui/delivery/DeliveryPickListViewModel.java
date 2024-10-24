package com.covalsys.phi_scanner.ui.delivery;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.database.entities.DeliveryHeader;
import com.covalsys.phi_scanner.data.database.entities.DeliveryLine;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.Resource;
import com.covalsys.phi_scanner.data.network.models.get.BusinessTypeModel;
import com.covalsys.phi_scanner.data.network.models.get.GetDeliveryCustomerModel;
import com.covalsys.phi_scanner.data.network.models.get.GetDeliveryDocument;
import com.covalsys.phi_scanner.data.network.models.get.GetDeliveryHeaderModel;
import com.covalsys.phi_scanner.data.network.models.get.GetDeliveryLineDetail;
import com.covalsys.phi_scanner.data.network.models.post.PostDeliveryModel;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.ui.base.BaseViewModel;
import com.covalsys.phi_scanner.utils.DateUtils;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class DeliveryPickListViewModel extends BaseViewModel<DeliveryPickListNavigator> {

    public static final String TAG = DeliveryPickListViewModel.class.getSimpleName();
    public MutableLiveData<Boolean> progressBar;
    public MutableLiveData<String> nextDate;
    public MutableLiveData<Context> mContext;
    public MutableLiveData<Resource<GetDeliveryCustomerModel>> primaryCustomers;
    public MutableLiveData<Resource<BusinessTypeModel>> bTypeList;
    public MutableLiveData<Resource<GetDeliveryDocument>> dDoc;
    public MutableLiveData<Resource<GetDeliveryLineDetail>> dDocLine;

    public MediatorLiveData<List<DeliveryLine>> model = new MediatorLiveData<>();
    public MediatorLiveData<List<DeliveryHeader>> mH = new MediatorLiveData<>();

    @Inject
    public DeliveryPickListViewModel(Repository repository, SchedulerProvider schedulerProvider,
                             PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        progressBar = new MutableLiveData<>();
        nextDate = new MutableLiveData<>();
        mContext = new MutableLiveData<>();
        primaryCustomers = new MutableLiveData<>();
        dDoc = new MutableLiveData<>();
        dDocLine = new MutableLiveData<>();
        bTypeList = new MutableLiveData<>();
    }

    public void refreshData(){
        deliveryList();
    }

    public void setContext(Context context) {
        mContext.setValue(context);
    }

    public void documentDate() {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            nextDate.postValue(DateUtils.dateFormat().format(myCalendar.getTime()));
        };

        DatePickerDialog datePicker = new DatePickerDialog(mContext.getValue(), R.style.DialogTheme, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        //datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
        //datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

    public LiveData<String> nextDate() {
        return nextDate;
    }

    public LiveData<Resource<BusinessTypeModel>> getBusiness() {
        return bTypeList;
    }

    public LiveData<Resource<GetDeliveryCustomerModel>> getCustomers() {
        return primaryCustomers;
    }

    public LiveData<Resource<GetDeliveryDocument>> getDoc() {
        return dDoc;
    }

    public LiveData<Resource<GetDeliveryLineDetail>> getDocLine() {
        return dDocLine;
    }

    private void deliveryList() {
        LiveData<List<DeliveryLine>> listLiveData = getDatabase().deliveryLineDao().getData();
        model.addSource(listLiveData, pallets -> {
            model.setValue(pallets);
        });
    }

    /*private void deliveryHeader() {
        LiveData<List<DeliveryHeader>> listLiveData = getDatabase().deliveryHeaderDao().getData();
        mH.addSource(listLiveData, pallets -> {
            mH.setValue(pallets);
        });
    }*/

    public MediatorLiveData<List<DeliveryLine>> getScanData() {
        return model;
    }

    /*public MediatorLiveData<List<DeliveryHeader>> getScanDataHeader() {
        return mH;
    }*/

    private void getBusinessType() {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getBusinessType()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(customerDetailsModel1 -> {
                    progressBar.setValue(false);
                    if (customerDetailsModel1.getStatusCode() == 0) {
                        bTypeList.postValue(Resource.success(customerDetailsModel1));
                    } else {
                        bTypeList.postValue(Resource.error(customerDetailsModel1.getStatusMessage(), null));
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                    bTypeList.postValue(Resource.error(throwable.toString(), null));
                }));
    }

    private void getCustomersDetails(String bType) {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getDeliveryCustomerList(bType)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(customerDetailsModel1 -> {
                    progressBar.setValue(false);
                    if (customerDetailsModel1.getStatusCode() == 0) {
                        primaryCustomers.postValue(Resource.success(customerDetailsModel1));
                    } else {
                        primaryCustomers.postValue(Resource.error(customerDetailsModel1.getStatusMessage(), null));
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                    primaryCustomers.postValue(Resource.error(throwable.toString(), null));
                }));
    }

    public void getCustomerGlobal(String bType){
        getCustomersDetails(bType);
    }

    public void getDocGlobal(String customerCode, String bType, String sDate){
        getDoc(customerCode, bType, sDate);
    }

    public void getDocLineGlobal(String docEntry){
        getDocLine(docEntry);
        getDocHeader(docEntry);
    }

    private void getDoc(String customerCode, String bType, String sDate) {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getDeliveryDocument(customerCode, bType, sDate)
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

    private void getDocLine(String docEntry) {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getDeliveryDocumentLine(docEntry)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getDeliveryLineDetail -> {
                    progressBar.setValue(false);
                    if (getDeliveryLineDetail.getStatusCode() == 0) {
                        getDatabase().deliveryLineDao().deleteAllData();
                        for (GetDeliveryLineDetail.ResponseObject object: getDeliveryLineDetail.getResponseObject()) {
                            getDatabase().deliveryLineDao().add(new DeliveryLine(Integer.parseInt(object.getDocEntry()), 0, Integer.parseInt(object.getLine()), object.getItemCode(), object.getItemName(), object.getBatchNo(), object.getThick(), object.getWidth(), object.getLength1(), Float.parseFloat(object.getQuantity()), "", "", Float.intBitsToFloat(0)));
                        }
                        dDocLine.postValue(Resource.success(getDeliveryLineDetail));
                    } else {
                        dDocLine.postValue(Resource.error(getDeliveryLineDetail.getStatusMessage(), null));
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                    dDocLine.postValue(Resource.error(throwable.toString(), null));
                }));
    }

    private void getDocHeader(String docEntry) {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getDeliveryDocument(docEntry)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getDeliveryHeaderModel -> {
                    progressBar.setValue(false);
                    if (getDeliveryHeaderModel.getStatusCode() == 0) {
                        //getDatabase().deliveryHeaderDao().deleteAllData();
                        GetDeliveryHeaderModel.ResponseObject model = getDeliveryHeaderModel.getResponseObject();
                        getPreferenceHelper().setDeliveryDocEntry(model.getDocEntry());
                        getPreferenceHelper().setDeliveryDocStatus(model.getDocStatus());
                        /*GetDeliveryHeaderModel.ResponseObject model = getDeliveryHeaderModel.getResponseObject();
                        getDatabase().deliveryHeaderDao().insertDeliveryHeader(new DeliveryHeader(model.getDocEntry(), model.getDocEntry(), model.getCusCode(), model.getCusName(), model.getDocType(), model.getDocStatus(), model.getAppStatus()));*/
                    } else {
                        Log.e("TAG", getDeliveryHeaderModel.getStatusMessage());
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                }));
    }

    public void postDataToServer(){

        ArrayList<PostDeliveryModel.Line> CList = new ArrayList<>();
        List<DeliveryLine> listData = getDatabase().deliveryLineDao().getDataList();
            for (DeliveryLine item : listData) {
                String status = "N";
                if(Objects.equals(item.getQuantity(), item.getBarCodeQty()) && item.getBatchNo().equalsIgnoreCase(item.getBarCodeBatch())){
                    status = "Y";
                }else{
                    status = "N";
                }
                CList.add(new PostDeliveryModel.Line(item.getBarCodeBatch(), item.getBarCodeQty().toString(), item.getBatchNo(), "", item.getItemCode(), item.getItemName(), item.getLength(), item.getLength(), item.getLine().toString(), item.getQuantity().toString(), status, item.getWidth(), item.getThick()));
            }

        DeliveryHeader docData = getDatabase().deliveryHeaderDao().getDocument();
        PostDeliveryModel model = new PostDeliveryModel(DateUtils.currentDateYYYY(), "", "", DateUtils.currentDateYYYY(), docData.getDocEntry(), docData.getDocEntry(), CList, getPreferenceHelper().getSalesEmpCode(), "O", DateUtils.currentDateYYYY());

            progressBar.setValue(true);
            getCompositeDisposable().add(getApiService().addDelivery(model)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(basePostResponse -> {
                        progressBar.setValue(false);
                        if (basePostResponse.getStatusCode() == 0) {
                            getDatabase().deliveryLineDao().deleteAllData();
                            getDatabase().deliveryScanLineDao().deleteAllData();
                            getDatabase().deliveryHeaderDao().deleteAllData();
                            getNavigator().onPostSuccess(basePostResponse.getResponseObject());
                        } else {
                            getNavigator().onPostFailed(basePostResponse.getResponseObject());
                        }
                    }, throwable -> {
                        progressBar.setValue(false);
                        getNavigator().onPostFailed(throwable.getMessage());

                    }));
        }

    public void clearData() {
        getDatabase().deliveryScanLineDao().deleteAllData();
        getDatabase().deliveryLineDao().deleteAllData();
        getPreferenceHelper().setDeliveryDocStatus("");
        getPreferenceHelper().setDeliveryDocEntry("");
        //getDatabase().deliveryHeaderDao().deleteAllData();
    }
}
