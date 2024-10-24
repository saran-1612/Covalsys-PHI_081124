package com.covalsys.phi_scanner.ui.scan.stock;

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
import com.covalsys.phi_scanner.data.database.entities.StockCountScanLine;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StockCountBatchScanActivity extends AppCompatActivity implements StockCountBatchScanAdapter.Callback {

    private CodeScanner mCodeScanner;
    StockCountBatchScanAdapter adapter;
    EditText etN;

    private AppDatabase mDatabase;
    private RoomHelper helper;
    public MediatorLiveData<List<StockCountScanLine>> scanModel;

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
        adapter = new StockCountBatchScanAdapter(new ArrayList<>(), StockCountBatchScanActivity.this);
        adapter.setOnClick(this);
        view.setAdapter(adapter);

        imView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG_STR", "132" );
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
                            String[] parts = text.split(Pattern.quote("*"));
                            if(mDatabase.stockCountScanLineDao().isAddToCart(parts[0]) != 1) {
                                mDatabase.stockCountScanLineDao().add(new StockCountScanLine(0, parts[0], Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]), Float.parseFloat(parts[4]), Float.parseFloat(parts[4]), Float.parseFloat(parts[4])));
                            }else{
                                Toast.makeText(StockCountBatchScanActivity.this, "Scanned Batch No. Already Exist.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(StockCountBatchScanActivity.this, "Scanned data Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    List<StockCountScanLine> scan = mDatabase.stockCountScanLineDao().getDataList();
                    for (StockCountScanLine line : scan) {
                        if (mDatabase.stockCountLineDao().dataCount() >= line.getSlno()) {
                            int lineNo = mDatabase.stockCountLineDao().getLineNo(line.getSlno());
                            mDatabase.stockCountLineDao().updateBatchInfo(lineNo);
                        } else {
                           // mDatabase.stockCountLineDao().add(new StockCountLine(0, 0, line.getBatchNo(), line.getThick(), line.getWidth(), line.getLength(), line.getQuantity(), DateUtils.currentTime(), DateUtils.currentDate()));
                        }
                    }

                    Bundle args = new Bundle();
                    args.putString("data", "12345");
                    setResult(123, new Intent().putExtra("ScannedData", args));
                    finish();
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        LiveData<List<StockCountScanLine>> listLiveData = mDatabase.stockCountScanLineDao().getData();
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
        Dexter.withContext(StockCountBatchScanActivity.this)
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
    public void onDelete(int position, StockCountScanLine models) {
        mDatabase.stockCountScanLineDao().deleteData(models.getSlno());
        mDatabase.stockCountLineDao().updateBatchInfo(models.getSlno());
    }
}
