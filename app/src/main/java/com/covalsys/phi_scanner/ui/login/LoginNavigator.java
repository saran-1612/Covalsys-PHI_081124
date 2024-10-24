package com.covalsys.phi_scanner.ui.login;


import com.covalsys.phi_scanner.data.network.models.get.LoginModel;

import java.util.List;

/**
 * Created by Prasanth on 11-07-2020.
 */
public interface LoginNavigator {

    void openForgotPassword();

    void showLoading();

    void hideLoading();

    void onPostSuccess(List<LoginModel> loginModel);

    void onPostFailed(String msg);

}
