package com.covalsys.phi_scanner.ui.scan.batch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.budiyev.android.codescanner.CodeScanner;
import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.databinding.CustomItemScanActivityBinding;
import com.covalsys.phi_scanner.ui.ViewModelProviderFactory;
import com.covalsys.phi_scanner.ui.base.BaseActivity;
import com.covalsys.phi_scanner.ui.login.LoginActivity;

import javax.inject.Inject;

public class BatchScanActivity extends BaseActivity<BatchScanViewModel, CustomItemScanActivityBinding> implements BatchScanNavigator,
        BatchScanItemAdapter.Callback {

    @Inject
    ViewModelProviderFactory factory;

    private BatchScanViewModel mViewModel;
    private CodeScanner mCodeScanner;
    @Inject
    BatchScanItemAdapter mAdapter;

    @Override
    public int getBindingVariable() {
        return BR.batchScanViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.custom_item_scan_activity;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public BatchScanViewModel getViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(BatchScanViewModel.class);
        return mViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        mAdapter.setOnClick(this);
        setUp();
    }

    private void setUp() {

        LinearLayoutManager primaryManager = new LinearLayoutManager(this);
        primaryManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBinding.recyclerView.setLayoutManager(primaryManager);
        dataBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataBinding.recyclerView.setAdapter(mAdapter);

    }

    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 123) {

                       // viewModel.getDatabase().deliveryDataDao().add(new DeliveryHeader(result.getData().getStringExtra("ScannedData"), "It", "ItemName", "10", "Nos"));

                    }
                }
            });


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
