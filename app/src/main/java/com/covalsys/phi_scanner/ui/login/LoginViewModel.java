package com.covalsys.phi_scanner.ui.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.Resource;
import com.covalsys.phi_scanner.data.network.models.get.GetDatabaseModel;
import com.covalsys.phi_scanner.data.network.models.get.LoginModel;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.ui.base.BaseViewModel;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Prasanth on 11-07-2020.
 */
public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public static final String TAG = "LoginViewModel";
    public MutableLiveData<String> error;
    public ObservableField<String> userName;
    public ObservableField<String> password;
    public ObservableField<String> type;
    public MutableLiveData<String> dbName;
    public MutableLiveData<String> dbId;
    public MutableLiveData<Boolean> progressBar;
    public MutableLiveData<Resource<LoginModel>> userLogin;
    public MutableLiveData<Resource<GetDatabaseModel>> dbList;

    @Inject
    public LoginViewModel(Repository repository, SchedulerProvider schedulerProvider,
                          PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);

        type = new ObservableField<>("");
        userName = new ObservableField<>("");
        password = new ObservableField<>("");
        dbName = new MutableLiveData<>();
        dbId = new MutableLiveData<>();
        progressBar = new MutableLiveData<>();
        error = new MutableLiveData<>();
        userLogin = new MutableLiveData<>();
        dbList = new MutableLiveData<>();
        //init();
    }

  /*  public void init() {
        getDatabase().cartItemDao().deleteAllItem();
        getDatabaseList();
    }*/

    public void setTypeCode(String code, String name, Context context) {
        dbName.setValue(name);
        dbId.setValue(code);
    }

    public boolean isTypeValid() {
        return TextUtils.isEmpty(type.get());
    }

    public boolean isEmailValid() {
        return TextUtils.isEmpty(userName.get());
    }

    public boolean isPasswordValid() {
        return TextUtils.isEmpty(password.get());
    }

    public MutableLiveData<Resource<GetDatabaseModel>> getDBList() {
        return dbList;
    }

    public MutableLiveData<Resource<LoginModel>> getLogin() {
        return userLogin;
    }

    public void saveDetails(List<LoginModel.ResponseObject> loginModels, Context context) {
        getPreferenceHelper().setIsLoggedIn(true);
            getPreferenceHelper().setSalesEmpCode(loginModels.get(0).getUserCode());
            getPreferenceHelper().setSalesEmpName(loginModels.get(0).getUserName());
            getPreferenceHelper().setUserCode(String.valueOf(loginModels.get(0).getDocEntry()));
            getPreferenceHelper().setEmpTypeCode(loginModels.get(0).getUserType());
    }

    public void forgotPassword() {
        getNavigator().openForgotPassword();
    }

    public void login() {

         if (isEmailValid()) {
            error.setValue("Username should not be empty");
        } else if (isPasswordValid()) {
            error.setValue("Password should not be empty");
        } else {

             /*List<LoginModel.ResponseObject> res = new ArrayList<>();
             res.add(new LoginModel.ResponseObject("", 1002, "C002", "Covalsys"));
             userLogin.postValue(Resource.success(new LoginModel(res, 0, "SUCCESS")));*/
            progressBar.setValue(true);
            getCompositeDisposable().add(getApiService()
                    .login(userName.get(), password.get())
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(loginModel -> {
                        progressBar.setValue(false);
                        if (loginModel.getStatusCode() == 0) {
                            userLogin.postValue(Resource.success(loginModel));
                        } else {
                            userLogin.postValue(Resource.error(loginModel.getStatusMessage(), null));
                        }

                    }, throwable -> {
                        progressBar.setValue(false);
                        Log.e(TAG, throwable.toString());
                        userLogin.postValue(Resource.error(throwable.toString(), null));
                    }));
        }
    }
}
