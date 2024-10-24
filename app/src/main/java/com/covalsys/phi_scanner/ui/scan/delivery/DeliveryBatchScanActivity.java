package com.covalsys.phi_scanner.ui.scan.delivery;

import static android.Manifest.permission.CAMERA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.DeliveryLine;
import com.covalsys.phi_scanner.data.database.entities.DeliveryScanLine;
import com.covalsys.phi_scanner.data.network.models.get.GetBatchInfo;
import com.covalsys.phi_scanner.databinding.DeliveryBatchScanActivityBinding;
import com.covalsys.phi_scanner.ui.ViewModelProviderFactory;
import com.covalsys.phi_scanner.ui.base.BaseActivity;
import com.covalsys.phi_scanner.utils.CommonUtils;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class DeliveryBatchScanActivity extends BaseActivity<DeliveryBatchScanViewModel, DeliveryBatchScanActivityBinding> implements DeliveryBatchScanNavigator,
        DeliveryBatchScanAdapter.Callback {

    @Inject
    ViewModelProviderFactory factory;

    private DeliveryBatchScanViewModel mViewModel;
    private CodeScanner mCodeScanner;
    @Inject
    DeliveryBatchScanAdapter mAdapter;

    @Override
    public int getBindingVariable() {
        return BR.deliveryBatchScanViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.delivery_batch_scan_activity;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, DeliveryBatchScanActivity.class);
    }

    @Override
    public DeliveryBatchScanViewModel getViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(DeliveryBatchScanViewModel.class);
        return mViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        mAdapter.setOnClick(this);
        initCameraPermission();
        setUp();
    }

    private void setUp() {

        LinearLayoutManager primaryManager = new LinearLayoutManager(this);
        primaryManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBinding.recyclerView.setLayoutManager(primaryManager);
        dataBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataBinding.recyclerView.setAdapter(mAdapter);

        mCodeScanner = new CodeScanner(this, dataBinding.scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getBarcodeFormat().toString().equalsIgnoreCase("CODE_128")) {
                            Log.e("TAG_STR", result.getText() + "");
                            String Txt = String.valueOf(result.getText()).trim();
                            if (Txt.contains("-")) {
                                String[] parts = Txt.split(Pattern.quote("-"));
                                Log.e("TAG_STR", Txt + "," + parts[0] + "");
                                mViewModel.getBatchValue(Txt + "," + parts[0]);
                            }else {
                                Log.e("TAG_STR", Txt + "");
                                mViewModel.getBatchValue(Txt);
                            }
                        } else if (result.getBarcodeFormat().toString().equalsIgnoreCase("QR_CODE")) {

                            if (!result.getText().toString().isEmpty()) {
                                String text = String.valueOf(result.getText());
                                Log.e("TAG_STR", text);
                                try {
                                    String[] parts2 = text.split(Pattern.quote("*"));
                                    if (mViewModel.getDatabase().deliveryScanLineDao().isAddToCart(parts2[0]) != 1) {
                                        mViewModel.getDatabase().deliveryScanLineDao().add(new DeliveryScanLine(0, "", "", parts2[0], "", String.valueOf(parts2[1]), String.valueOf(parts2[2]), String.valueOf(parts2[3]), Float.parseFloat(parts2[4]), Float.parseFloat(parts2[4]), Float.parseFloat(parts2[4]), ""));
                                        return;
                                    }
                                    //Toast.makeText(this, "Scanned Batch No. Already Exist.", ).show();
                                } catch (Exception e) {
                                    //Toast.makeText(this, "Scanned QR Code Invalid", 0).show();
                                }
                            } else {
                                //Toast.makeText(CustomBatchScanActivity.this, "Scanned data Empty", 0).show();
                            }
                        }

                        if(!result.getText().toString().isEmpty()) {
                            String text = String.valueOf(result.getText());
                            Log.e("TAG_STR", text );
                            try {
                                String[] parts = text.split(Pattern.quote("*"));
                                if (mViewModel.getDatabase().deliveryScanLineDao().isAddToCart(parts[0]) != 1) {
                                    mViewModel.getDatabase().deliveryScanLineDao().add(new DeliveryScanLine(0, "", "", parts[0], "", String.valueOf(parts[1]), String.valueOf(parts[2]), String.valueOf(parts[3]), Float.parseFloat(parts[4]), Float.parseFloat(parts[4]), Float.parseFloat(parts[4]), ""));
                                } else {
                                    Toast.makeText(DeliveryBatchScanActivity.this, "Scanned Batch No. Already Exist.", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                Toast.makeText(DeliveryBatchScanActivity.this, "Scanned QR Code Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(DeliveryBatchScanActivity.this, "Scanned data Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //getBatchValue
                });
            }
        });

        mViewModel.scanChanges();
        mViewModel.getScanData().observe(this, model -> {
            mAdapter.clearList();
            mAdapter.addCustomers(model);
            mAdapter.notifyDataSetChanged();
        });

        dataBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dataBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mViewModel.getDatabase().deliveryScanLineDao().getDataList().isEmpty()) {
                    Float actualQty = mViewModel.getDatabase().deliveryLineDao().getActualData();
                    Float loadedQty = mViewModel.getDatabase().deliveryScanLineDao().getLoaded();
                    if (actualQty >= loadedQty) {
                        try {
                            for (DeliveryScanLine line : mViewModel.getDatabase().deliveryScanLineDao().getDataMatchList()) {
                                mViewModel.getDatabase().deliveryScanLineDao().updateLineStatus(line.getSlno());
                                mViewModel.getDatabase().deliveryLineDao().updateBatchMatchInfo(line.getBatchNo(), line.getQuantity());
                            }

                            List<DeliveryScanLine> scan = mViewModel.getDatabase().deliveryScanLineDao().getDataNotList();
                            List<DeliveryLine> dScan = mViewModel.getDatabase().deliveryLineDao().getDataNotList();
                            if (!scan.isEmpty()) {
                                for (int i = 0; i < scan.size(); i++) {
                                    int j = dScan.size();
                                    if (j != 0) {
                                        mViewModel.getDatabase().deliveryLineDao().updateBatchInfo(dScan.get(i).getSlno(), scan.get(i).getBatchNo(), scan.get(i).getQuantity());
                                        j = j--;
                                    } else {
                                        mViewModel.getDatabase().deliveryLineDao().add(new DeliveryLine(0, 0, 0, "", "", "", String.valueOf(0), String.valueOf(0), String.valueOf(0), Float.intBitsToFloat(0), "N", scan.get(i).getBatchNo(), scan.get(i).getQuantity()));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        Bundle args = new Bundle();
                        args.putString("data", "12345");
                        setResult(123, new Intent().putExtra("ScannedData", args));
                        finish();

                    } else {
                        CommonUtils.shownSnackBarMsg(v, "Loaded material excited actual quantity...");
                    }
                }else{
                    CommonUtils.shownSnackBarMsg(v, "Dose not add empty value... ");
                }
            }
        });

        dataBinding.scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });

        dataBinding.inputText.requestFocus();
        dataBinding.inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                        Log.e("TAG_SCAN", s.toString());
                        if (!s.toString().isEmpty()) {
                            String text = s.toString().trim();
                            if (text.contains(" ")) {
                                String[] parts = text.split(Pattern.quote(" "));
                                if (text.contains("*")) {
                                    String[] part01 = (parts[0].trim()).split(Pattern.quote("*"));
                                    mViewModel.getBatchInfo(part01[0].trim());
                                }else{
                                    mViewModel.getBatchInfo(parts[0].trim());
                                }
                            }else if (text.contains("*")) {
                                String[] parts = text.split(Pattern.quote("*"));
                                mViewModel.getBatchInfo(parts[0].trim());
                            } else {
                                mViewModel.getBatchInfo(text.trim());
                            }
                            dataBinding.inputText.setText("");
                            Log.e("TAG_scan", text);
                        }
                    }catch (Exception e){
                        Log.e("TAG", e.getMessage()+"");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
    }

    @Override
    protected void onResume() {
        mCodeScanner.startPreview();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDelete(int position, DeliveryScanLine models) {
        mViewModel.getDatabase().deliveryScanLineDao().deleteData(models.getSlno());
        mViewModel.getDatabase().deliveryLineDao().updateBatchInfo(models.getSlno());
    }

    private void initCameraPermission() {

        Dexter.withContext(DeliveryBatchScanActivity.this)
            .withPermissions(CAMERA)
            .withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        //mImageInputHelper.takePhotoWithCamera();
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
    }

    @Override
    public void batchFetchSuccess(List<GetBatchInfo.ResponseObject> responseObject) {
        try {
            if (!responseObject.isEmpty()) {
                GetBatchInfo.ResponseObject item = responseObject.get(0);
                mViewModel.getDatabase().deliveryScanLineDao().add(new DeliveryScanLine(0, "", "", item.getBatchNo(), item.getCoilNo(), item.getThick(), item.getWidth(), item.getLength1(), Float.parseFloat(item.getQuantity()), Float.parseFloat(item.getQuantity()), Float.parseFloat(item.getQuantity()), ""));
            } else {
                Toast.makeText(DeliveryBatchScanActivity.this, "Scanned Batch No. Invalid.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(DeliveryBatchScanActivity.this, "Scanned Batch No. Invalid.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void batchFetchFailed(String msg) {
        showSnackBar(msg);
    }
}
