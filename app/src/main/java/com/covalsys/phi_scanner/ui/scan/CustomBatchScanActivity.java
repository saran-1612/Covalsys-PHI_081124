package com.covalsys.phi_scanner.ui.scan;

import static android.Manifest.permission.CAMERA;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.AppDatabase;
import com.covalsys.phi_scanner.data.database.RoomDataManager;
import com.covalsys.phi_scanner.data.database.RoomHelper;
import com.covalsys.phi_scanner.data.database.entities.DeliveryLine;
import com.covalsys.phi_scanner.data.database.entities.DeliveryScanLine;
import com.covalsys.phi_scanner.data.network.models.get.ItemBatchModel;
import com.covalsys.phi_scanner.utils.CommonUtils;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomBatchScanActivity extends AppCompatActivity implements CustomBatchScanAdapter.Callback {

    private CodeScanner mCodeScanner;
    CustomBatchScanAdapter adapter;
    EditText etN;
    ArrayList<ItemBatchModel> model = new ArrayList<>();

    private AppDatabase mDatabase;
    private RoomHelper helper;
    public MediatorLiveData<List<DeliveryScanLine>> scanModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_item_scan_activity_1);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        ImageView imView = findViewById(R.id.okText);
        mCodeScanner = new CodeScanner(this, scannerView);
        mDatabase = AppDatabase.getDatabaseInstance(this);
        helper = new RoomDataManager(mDatabase);
        scanModel = new MediatorLiveData<>();
        initCameraPermission();

        RecyclerView view = (RecyclerView) findViewById(R.id.recyclerView);
        etN = (EditText)findViewById(R.id.inputText);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(linearLayoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        adapter = new CustomBatchScanAdapter(new ArrayList<>(), CustomBatchScanActivity.this);
        adapter.setOnClick(this);
        /*model = new ArrayList<>();
        adapter = new CustomBatchScanAdapter(model, CustomBatchScanActivity.this);*/
        view.setAdapter(adapter);

        imView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG_STR", "132" );
                /*if(etN.getText().toString().isEmpty()){
                    Toast.makeText(CustomBatchScanActivity.this, "Enter valid data.", Toast.LENGTH_SHORT).show();
                }else {
                    adapter.addData(new ItemBatchModel("", etN.getText().toString(), etN.getText().toString(), "", "", "", ""));
                    adapter.notifyDataSetChanged();
                }*/
            }
        });

        findViewById(R.id.scanClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.manual_la).setVisibility(View.GONE);
                findViewById(R.id.scanner_view).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.manualClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.manual_la).setVisibility(View.VISIBLE);
                findViewById(R.id.scanner_view).setVisibility(View.GONE);
            }
        });

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!result.getText().toString().isEmpty()) {
                            String text = String.valueOf(result.getText());
                            Log.e("TAG_STR", text );
                            try {
                                String[] parts = text.split(Pattern.quote("*"));
                                if (mDatabase.deliveryScanLineDao().isAddToCart(parts[0]) != 1) {
                                    mDatabase.deliveryScanLineDao().add(new DeliveryScanLine(0, "", "", parts[0], "", String.valueOf(parts[1]), String.valueOf(parts[2]), String.valueOf(parts[3]), Float.parseFloat(parts[4]), Float.parseFloat(parts[4]), Float.parseFloat(parts[4]), ""));
                                } else {
                                    Toast.makeText(CustomBatchScanActivity.this, "Scanned Batch No. Already Exist.", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                Toast.makeText(CustomBatchScanActivity.this, "Scanned QR Code Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(CustomBatchScanActivity.this, "Scanned data Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float actualQty = mDatabase.deliveryLineDao().getActualData();
                Float loadedQty = mDatabase.deliveryScanLineDao().getLoaded();
                    if(actualQty >= loadedQty) {
                    List<DeliveryScanLine> scan = mDatabase.deliveryScanLineDao().getDataList();
                    for (DeliveryScanLine line : scan) {
                        if (mDatabase.deliveryLineDao().dataCount() >= line.getSlno()) {
                            int lineNo = mDatabase.deliveryLineDao().getLineNo(line.getSlno());
                            mDatabase.deliveryLineDao().updateBatchInfo(lineNo, line.getBatchNo(), line.getQuantity());
                        } else {
                            mDatabase.deliveryLineDao().add(new DeliveryLine(0, 0, 0, "", "", "", String.valueOf(0), String.valueOf(0), String.valueOf(0), Float.intBitsToFloat(0), "N", line.getBatchNo(), line.getQuantity()));
                        }
                    }

                    Bundle args = new Bundle();
                    args.putString("data", "12345");
                    //args.putSerializable("array", adapter.getList());
                    setResult(123, new Intent().putExtra("ScannedData", args));
                    finish();
                }else{

                    CommonUtils.shownSnackBarMsg(v, "Loaded material excited actual quantity...");
                    /*new AlertDialog.Builder(CustomBatchScanActivity.this)
                            .setMessage("Loaded material excited actual quantity...")
                            .setCancelable(false)
                            .setPositiveButton("Ok", (dialog, id) -> dialog.dismiss())
                            .show();*/
                }
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        LiveData<List<DeliveryScanLine>> listLiveData = mDatabase.deliveryScanLineDao().getData();
        scanModel.addSource(listLiveData, pallets -> {
            scanModel.setValue(pallets);
        });

        scanModel.observe(this, model -> {
            adapter.clearList();
            adapter.addData(model);
            adapter.notifyDataSetChanged();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initCameraPermission() {
        Dexter.withContext(CustomBatchScanActivity.this)
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
    public void onDelete(int position, DeliveryScanLine models) {
        mDatabase.deliveryScanLineDao().deleteData(models.getSlno());
        mDatabase.deliveryLineDao().updateBatchInfo(models.getSlno());
    }
}
