package com.covalsys.phi_scanner.ui.scan.delivery;


import com.covalsys.phi_scanner.data.network.models.get.GetBatchInfo;

import java.util.List;

public interface DeliveryBatchScanNavigator {

    void batchFetchSuccess(List<GetBatchInfo.ResponseObject> responseObject);

    void batchFetchFailed(String msg);

}
