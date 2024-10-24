package com.covalsys.phi_scanner.data.network;

import com.covalsys.phi_scanner.data.network.models.get.BusinessTypeModel;
import com.covalsys.phi_scanner.data.network.models.get.GetBaseResponsModel;
import com.covalsys.phi_scanner.data.network.models.get.GetBatchInfo;
import com.covalsys.phi_scanner.data.network.models.get.GetDeliveryCustomerModel;
import com.covalsys.phi_scanner.data.network.models.get.GetDeliveryDocument;
import com.covalsys.phi_scanner.data.network.models.get.GetDeliveryHeaderModel;
import com.covalsys.phi_scanner.data.network.models.get.GetDeliveryLineDetail;
import com.covalsys.phi_scanner.data.network.models.get.GetLocationModel;
import com.covalsys.phi_scanner.data.network.models.get.GetProfileModel;
import com.covalsys.phi_scanner.data.network.models.get.LoginModel;
import com.covalsys.phi_scanner.data.network.models.get.StockHistoryModel;
import com.covalsys.phi_scanner.data.network.models.post.BasePostResponse;
import com.covalsys.phi_scanner.data.network.models.post.BatchUpdateModel;
import com.covalsys.phi_scanner.data.network.models.post.PostDeliveryModel;
import com.covalsys.phi_scanner.data.network.models.post.PostStockCountModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Prasanth on 09-07-2020.
 */

public interface ApiService {

    @GET("api/login/validateLogin")
    Single<LoginModel> login(@Query("UserName") String userName,
                             @Query("Password") String password);

    @GET("api/Delivery/getCustomers")
    Observable<GetDeliveryCustomerModel> getDeliveryCustomerList(@Query("BType") String bType);

    @GET("api/Delivery/getLocation")
    Observable<GetLocationModel> getLocationModel();

    @GET("api/Delivery/getBusinessType")
    Observable<BusinessTypeModel> getBusinessType();

    @GET("api/Delivery/getBatchInfo")
    Observable<GetBatchInfo> getBatchDetails(@Query("batchNo") String str);

    @GET("api/Delivery/getBatchInfo_1")
    Observable<GetBatchInfo> getBatchInfo(@Query("batchNo") String str);

    @GET("api/Delivery/SelectProfile")
    Observable<GetProfileModel> getProfile();

    @GET("api/Delivery/getDeliveryDocuments")
    Observable<GetDeliveryDocument> getDeliveryDocument(@Query("CustomerCode") String customerCode,
                                                        @Query("BType") String bType,
                                                        @Query("sDate") String sDate);

    @GET("api/Delivery/getDeliveryDocumentLine")
    Observable<GetDeliveryLineDetail> getDeliveryDocumentLine(@Query("docEntry") String docEntry);

    @GET("api/Delivery/getDeliveryDocument")
    Observable<GetDeliveryHeaderModel> getDeliveryDocument(@Query("docNum") String docNum);

    @POST("api/Delivery/updateBatchStockCount")
    Observable<GetBaseResponsModel> updateBatch(@Body BatchUpdateModel model);

    @POST("api/Delivery/createProfile")
    Observable<GetProfileModel> createProfile(@Query("userCode") String str);

    @GET("api/Delivery/endScanning")
    Observable<BasePostResponse> endSession(@Query("docNum") String str);

    @GET("/api/Delivery/startScanning")
    Observable<BasePostResponse> startSession(@Query("docNum") String str);

    @GET("api/Delivery/OpenProfileHistory")
    Observable<StockHistoryModel> getDataList(@Query("text") String text);

    @POST("api/Delivery/addDelivery")
    Observable<BasePostResponse> addDelivery(@Body PostDeliveryModel delivery);

    @POST("api/Delivery/addStockCount")
    Observable<BasePostResponse> addStockCount(@Body ArrayList<PostStockCountModel.PostStockCountModelItem> stock);



}

