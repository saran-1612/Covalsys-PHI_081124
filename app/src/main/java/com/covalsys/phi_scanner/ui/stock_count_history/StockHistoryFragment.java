package com.covalsys.phi_scanner.ui.stock_count_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.network.models.get.GetProfileModel;
import com.covalsys.phi_scanner.data.network.models.get.StockHistoryModel;
import com.covalsys.phi_scanner.databinding.StockHistoryFragmentBinding;
import com.covalsys.phi_scanner.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class StockHistoryFragment extends BaseFragment<StockHistoryViewModel, StockHistoryFragmentBinding> implements StockHistoryNavigator {

    StockHistoryAdapter historyAdapter;

    @Override
    public int getBindingVariable() {
        return BR.stockHistoryViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.stock_history_fragment;
    }

    @Override
    protected Class<StockHistoryViewModel> getViewModel() {
        return StockHistoryViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewModel.setNavigator(this);
        initScreens();
        return dataBinding.getRoot();
    }

    private void initScreens() {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Stock History");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewModel.setContext(requireActivity());
        LinearLayoutManager primaryManager = new LinearLayoutManager(getActivity());
        primaryManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBinding.recyclerViewList.setLayoutManager(primaryManager);
        dataBinding.recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        historyAdapter = new StockHistoryAdapter(requireActivity(), new ArrayList<>());
        dataBinding.recyclerViewList.setAdapter(historyAdapter);

        dataBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                viewModel.getDataList(newText);

                return false;
            }
        });

        viewModel.getLiveDataLocation().removeObservers(this);
        viewModel.getLiveDataLocation().observe(getViewLifecycleOwner(), customerDetailsModelResource -> {
            switch (customerDetailsModelResource.status) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    hideLoading();
                    if(customerDetailsModelResource.data.getResponseObject() == null){
                        setBatchList(new ArrayList<>());
                        Toast.makeText(requireActivity(),customerDetailsModelResource.data.getStatusMessage(),Toast.LENGTH_SHORT).show();
                    }else {
                        setBatchList(customerDetailsModelResource.data.getResponseObject());
                    }
                    break;
                case ERROR:
                    hideLoading();
                    break;
                default:
                    break;
            }
        });

    }

    private void setBatchList(List<StockHistoryModel.ResponseObject> responseObjectList){
        historyAdapter.clearList();
        historyAdapter.addCustomers(responseObjectList);
        historyAdapter.notifyDataSetChanged();
    }
    /*private void setBatchList(List<StockHistoryModel.ResponseObject> responseObjectList){
        historyAdapter.clearList();
        historyAdapter.addCustomers(responseObjectList);
        historyAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onPostSuccess(String msg) {
            showSnackBar(msg);
    }

    /*@Override
    public void onPostSuccess(String msg) {
        showSnackBar(msg);
    }*/

    /*@Override
    public void onPostSuccess(String msg) {
        showSnackBar(msg);
    }*/

    /*@Override
    public void onPostSuccess(String msg) {
        showSnackBar(msg);
    }*/

    @Override
    public void onPostFailed(String msg) {
         showSnackBar(msg);
    }

    @Override
    public void onProfileGetSuccess(GetProfileModel.ResponseObject responseObject) {
        dataBinding.setCnp.setText(responseObject.getProfileName());
    }

    @Override
    public void onProfileGetFailed(String mag) {
        dataBinding.setCnp.setText("");
    }

}
