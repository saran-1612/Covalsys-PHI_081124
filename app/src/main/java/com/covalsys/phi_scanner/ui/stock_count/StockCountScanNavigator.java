package com.covalsys.phi_scanner.ui.stock_count;

import com.covalsys.phi_scanner.data.network.models.get.GetProfileModel;

public interface StockCountScanNavigator {

    void onPostSuccess(String msg);

    void onPostEndSuccess(String msg);

    void onPostStartSuccess(String msg);

    void onPostFailed(String msg);

    void onPostEndFailed(String msg);

    void onPostStartFailed(String msg);

    void onProfileGetSuccess(GetProfileModel.ResponseObject responseObject);

    void onProfileGetFailed(String mag);
}
