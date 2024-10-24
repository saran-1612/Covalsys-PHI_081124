package com.covalsys.phi_scanner.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.DeliveryLine;
import com.covalsys.phi_scanner.data.network.models.get.DeliveryItemModel;
import com.covalsys.phi_scanner.databinding.FragmentDeliveryPickListBinding;
import com.covalsys.phi_scanner.ui.base.BaseFragment;
import com.covalsys.phi_scanner.ui.scan.ScanActivity;
import com.covalsys.phi_scanner.ui.scan.delivery.DeliveryBatchScanActivity;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class DeliveryPickListFragment extends BaseFragment<DeliveryPickListViewModel, FragmentDeliveryPickListBinding> implements DeliveryPickListNavigator,
        DeliveryPickListAdapter.Callback {

    public static final String TAG = DeliveryPickListFragment.class.getSimpleName();
    public boolean pallet = true;

    @Inject
    DeliveryPickListAdapter mAdapter;
    String stDocNum, stDocEntry, customerCode, customerName, businessCode, businessName;
    String locationCode;
    ArrayList<DeliveryItemModel> model = new ArrayList<>();

    @Override
    public int getBindingVariable() {
        return BR.deliveryPickListViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_delivery_pick_list;
    }

    @Override
    protected Class<DeliveryPickListViewModel> getViewModel() {
        return DeliveryPickListViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        viewModel.setNavigator(this);
        mAdapter.setOnClick(this);
        setUp();
        return dataBinding.getRoot();
    }

    private void setUp() {

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Delivery");
        }

        viewModel.setContext(requireActivity());
        LinearLayoutManager primaryManager = new LinearLayoutManager(getActivity());
        primaryManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBinding.recyclerView.setLayoutManager(primaryManager);
        dataBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataBinding.recyclerView.setAdapter(mAdapter);

        //viewModel.nextDate().removeObservers(this);
        dataBinding.layBatchScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(dataBinding.etLocation.getText()).isEmpty()){
                    showSnackBar("Document line is empty.. ");
                }else {
                    activityResultLaunch.launch(DeliveryBatchScanActivity.newIntent(requireActivity()).putExtra("DI", 1));
                }
            }
        });

        dataBinding.layAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToServer();
            }
        });

        dataBinding.layCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBinding.etLocation.setText("");
                viewModel.clearData();
            }
        });

        viewModel.getDocLine().removeObservers(this);
        viewModel.getDocLine().observe(getViewLifecycleOwner(), customerDetailsModelResource -> {
            switch (customerDetailsModelResource.status) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    hideLoading();
                    showSnackBar(customerDetailsModelResource.getMessage());
                    break;
                case ERROR:
                    hideLoading();
                    showSnackBar(customerDetailsModelResource.getMessage());
                    break;
                default:
                    break;
            }
        });

        dataBinding.frStockIvLocationScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLaunch.launch(new Intent(requireActivity(),  ScanActivity.class).putExtra("DI", 1));
            }
        });

        dataBinding.frStockTvLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(String.valueOf(dataBinding.etLocation.getText()).isEmpty()){
                    showSnackBar("Scan document..");
                }else {
                    viewModel.getDocLineGlobal(String.valueOf(dataBinding.etLocation.getText()));
                }
            }
        });

        viewModel.refreshData();
        viewModel.getScanData().observe(requireActivity(), model -> {
            mAdapter.clearList();
            if (model.isEmpty()){
                dataBinding.emptyImage.setVisibility(View.VISIBLE);
                dataBinding.listData.setVisibility(View.GONE);
                /*dataBinding.etLocation.setEnabled(true);
                dataBinding.frStockTvLoad.setEnabled(true);
                dataBinding.frStockIvLocationScan.setEnabled(true);*/

            }else {
                mAdapter.addCustomers(model);
                dataBinding.emptyImage.setVisibility(View.GONE);
                dataBinding.listData.setVisibility(View.VISIBLE);
                /*dataBinding.etLocation.setEnabled(false);
                dataBinding.frStockTvLoad.setEnabled(false);
                dataBinding.frStockIvLocationScan.setEnabled(false);*/
            }

            String text_ = String.valueOf(viewModel.getDatabase().deliveryLineDao().getLoaded());
            if(text_.equalsIgnoreCase("null")) {
                dataBinding.tvTotal.setText("0.00");
            }else{
                dataBinding.tvTotal.setText("Scanned Batch Weight: "+text_+"");
            }

            mAdapter.notifyDataSetChanged();

        });

        //headerModify();

        dataBinding.etLocation.requestFocus();
        dataBinding.etLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dataBinding.etLocation.getText().hashCode() == s.hashCode()) {
                    locationCode = s.toString();
                }
            }
        });
    }

    public void headerModify(){

        String text = viewModel.getPreferenceHelper().getDeliveryDocEntry();
        if(text.isEmpty()) {
            dataBinding.etLocation.setText(text + "");
            dataBinding.tvStatus.setVisibility(View.VISIBLE);
            dataBinding.tvTotal.setVisibility(View.VISIBLE);
            String status = viewModel.getPreferenceHelper().getDeliveryDocStatus();
            if (status.equalsIgnoreCase("C")) {
                dataBinding.layAdd.setEnabled(false);
                dataBinding.layBatchScan.setEnabled(false);
                dataBinding.tvStatus.setText("Document Status: Completed.");
                dataBinding.tvStatus.setTextColor(getResources().getColor(R.color.green_light_trans));
                dataBinding.layAdd.setBackgroundColor(getResources().getColor(R.color.md_grey_600));
                dataBinding.layBatchScan.setBackgroundColor(getResources().getColor(R.color.md_grey_600));
            } else {
                dataBinding.layAdd.setEnabled(true);
                dataBinding.layBatchScan.setEnabled(true);
                dataBinding.tvStatus.setText("Document Status: Open.");
                dataBinding.tvStatus.setTextColor(getResources().getColor(R.color.buttonHighlight));
                dataBinding.layAdd.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                dataBinding.layBatchScan.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }else{
            dataBinding.layAdd.setEnabled(true);
            dataBinding.layBatchScan.setEnabled(true);
            dataBinding.tvStatus.setVisibility(View.GONE);
            dataBinding.tvTotal.setVisibility(View.GONE);
            dataBinding.tvStatus.setText("Document Status: Open.");
            dataBinding.tvStatus.setTextColor(getResources().getColor(R.color.buttonHighlight));
            dataBinding.layAdd.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            dataBinding.layBatchScan.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    /*if (result.getResultCode() == 123) {
                        //String args = result.getData().getStringExtra("ScannedData");
                        //dataBinding.etLocation.setText(args);
                        //locationCode = args;
                        //Log.e("TAG_StockCount", args);
                    }*/
                }
            });

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Delivery");
        }
    }

    public void postToServer(){
        Float loaded = viewModel.getDatabase().deliveryLineDao().getLoaded();
        Float actual = viewModel.getDatabase().deliveryLineDao().getActualData();
        if(viewModel.getDatabase().deliveryLineDao().getDataSize() == 0){
            showSnackBar("Invalid document not able to save!...");
        }else if(Objects.equals(loaded, actual)){
            new AlertDialog.Builder(requireActivity())
                    .setMessage("Do you want save this document?...")  //Are sure want to save this document?...
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        viewModel.postDataToServer();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        }else {
            showSnackBar("Loaded material and actual material quantity does not match...");
        }
    }

    @Override
    public void onPostSuccess(String msg) {
        showSnackBar(msg);
    }

    @Override
    public void onPostFailed(String msg) {
        showSnackBar(msg);
    }

    @Override
    public void onPCustomerClick(int position, DeliveryLine models) {

    }
}
