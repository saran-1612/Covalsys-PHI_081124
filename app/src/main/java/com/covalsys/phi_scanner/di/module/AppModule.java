package com.covalsys.phi_scanner.di.module;

import android.app.Application;
import android.content.Context;

import com.covalsys.phi_scanner.BuildConfig;
import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomDataManager;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.network.ApiService;
import com.covalsys.phi_scanner.data.network.RequestInterceptor;
import com.covalsys.phi_scanner.data.network.repository.Repository;
import com.covalsys.phi_scanner.data.preferences.PreferenceHelper;
import com.covalsys.phi_scanner.data.preferences.PreferencesManager;
import com.covalsys.phi_scanner.di.AppContext;
import com.covalsys.phi_scanner.di.PreferenceInfo;
import com.covalsys.phi_scanner.ui.delivery.DeliveryPickListAdapter;
import com.covalsys.phi_scanner.ui.scan.delivery.DeliveryBatchScanAdapter;
import com.covalsys.phi_scanner.ui.stock_count.StockCountScanAdapter;
import com.covalsys.phi_scanner.ui.stock_count_history.StockHistoryAdapter;
import com.covalsys.phi_scanner.utils.Constants;
import com.covalsys.phi_scanner.utils.rx.AppSchedulerProvider;
import com.covalsys.phi_scanner.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prasanth on 09-07-2020.
 */
@Module(includes = ViewModelModule.class)
public class AppModule {

    @Singleton
    @Provides
    @AppContext
    Context provideContext(Application application){
        return application;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(5, TimeUnit.MINUTES);
        okHttpClient.readTimeout(5, TimeUnit.MINUTES);
        okHttpClient.writeTimeout(5, TimeUnit.MINUTES);
        okHttpClient.addInterceptor(new RequestInterceptor());
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    ApiService provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiService.class);
    }


    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Application application) {
        return AppDatabase.getDatabaseInstance(application);
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return Constants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(PreferencesManager preferencesManager) {
        return preferencesManager;
    }

    @Provides
    @Singleton
    RoomHelper provideRoomHelper (RoomDataManager dataManager) {
        return dataManager;
    }

    @Provides
    @Singleton
    Repository providesRepository(ApiService apiService){
        return new Repository(apiService);
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    DeliveryPickListAdapter providesDeliveryPickListAdapter(Application application){
        return new DeliveryPickListAdapter(new ArrayList<>(),provideContext(application));
    }

    @Provides
    @Singleton
    StockCountScanAdapter providesStockCountScanAdapter(Application application){
        return new StockCountScanAdapter(provideContext(application), new ArrayList<>());
    }

    @Provides
    @Singleton
    StockHistoryAdapter providesStockHistoryAdapter(Application application){
        return new StockHistoryAdapter(provideContext(application), new ArrayList<>());
    }

    @Provides
    @Singleton
    DeliveryBatchScanAdapter providesDeliveryBatchScanAdapter(Application application){
        return new DeliveryBatchScanAdapter(new ArrayList<>(),provideContext(application));
    }

}
