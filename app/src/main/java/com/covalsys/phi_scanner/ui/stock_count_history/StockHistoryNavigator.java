package com.covalsys.phi_scanner.ui.stock_count_history;

import com.covalsys.phi_scanner.data.network.models.get.GetProfileModel;

public interface StockHistoryNavigator {

    void onPostSuccess(String msg);

    void onPostFailed(String msg);

    void onProfileGetSuccess(GetProfileModel.ResponseObject responseObject);

    void onProfileGetFailed(String mag);

}
