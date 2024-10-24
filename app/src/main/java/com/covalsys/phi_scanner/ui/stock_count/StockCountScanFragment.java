package com.covalsys.phi_scanner.ui.stock_count;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.network.models.get.GetLocationModel;
import com.covalsys.phi_scanner.data.network.models.get.GetProfileModel;
import com.covalsys.phi_scanner.databinding.CustomStackScreenBinding;
import com.covalsys.phi_scanner.ui.base.BaseFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StockCountScanFragment extends BaseFragment<StockCountScanViewModel, CustomStackScreenBinding> implements StockCountScanNavigator {

    public static final String TAG = StockCountScanFragment.class.getSimpleName();
    TextInputEditText tvBatchNo,tvQty;
    MaterialButton btAdd;
    View viewBottomSheet;
    public BottomSheetDialog bottomSheet;
    BottomSheetBehavior sheetBehavior;
    public StockCountScanAdapter mScanAdapter;

    @Override
    public int getBindingVariable() {
        return BR.customStackViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.custom_stack_screen;
    }

    @Override
    protected Class<StockCountScanViewModel> getViewModel() {
        return StockCountScanViewModel.class;
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
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Stock Count");
        }

        viewModel.setContext(getActivity());

        try {
                if (viewModel.getPreferenceHelper().getEmpTypeCode().equalsIgnoreCase("A")) {
                    dataBinding.createProfile.setVisibility(View.VISIBLE);
                } else {
                    dataBinding.createProfile.setVisibility(View.GONE);
                }
            }catch (Exception e){
                Log.e("TAG", e.getMessage());
            }

        LinearLayoutManager primaryManager = new LinearLayoutManager(getActivity());
        primaryManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBinding.recyclerViewList.setLayoutManager(primaryManager);
        dataBinding.recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        mScanAdapter = new StockCountScanAdapter(requireActivity(), new ArrayList<>());
        dataBinding.recyclerViewList.setAdapter(mScanAdapter);

        //dataBinding.etItemType.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.itemType)));

        dataBinding.manSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // countScanViewModel = new ViewModelProvider(requireActivity()).get(St.class);
                bottomSheet = new BottomSheetDialog(requireContext());
                viewBottomSheet = LayoutInflater.from(requireContext()).inflate(R.layout.stack_bottom_sheet,null);
                bottomSheet.setContentView(viewBottomSheet);
                bottomSheet.setCanceledOnTouchOutside(false);
                sheetBehavior = BottomSheetBehavior.from((View) viewBottomSheet.getParent());

                btAdd = viewBottomSheet.findViewById(R.id.btnAdd);
                tvBatchNo = viewBottomSheet.findViewById(R.id.etBatch);
                tvQty = viewBottomSheet.findViewById(R.id.etQty);

                btAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvBatchNo.getText().toString().isEmpty()){
                            Toast.makeText(requireContext(), "Enter Batch No.", Toast.LENGTH_SHORT).show();
                        }else if (tvQty.getText().toString().isEmpty() || Integer.parseInt(tvQty.getText().toString()) < 1){
                            Toast.makeText(requireContext(), "Enter Valid Number", Toast.LENGTH_SHORT).show();
                        }else {

                            String text = tvBatchNo.getText().toString();
                            updateServer(text, text, Float.parseFloat(tvQty.getText().toString()));
                            bottomSheet.dismiss();
                        }
                    }
                });

                    sheetBehavior.setFitToContents(true);
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //sheetBehavior.setHalfExpandedRatio(initialHeight);
                    bottomSheet.show();

                    sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {
                            switch (newState) {
                                case BottomSheetBehavior.STATE_HIDDEN:
                                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                    //sheetBehavior.setHalfExpandedRatio(initialHeight);
                                    break;
                                case BottomSheetBehavior.STATE_COLLAPSED:
                                    break;
                                case BottomSheetBehavior.STATE_EXPANDED:
                                    //bottomSheetBehavior.setFitToContents(true);
                                    break;
                                case BottomSheetBehavior.STATE_HALF_EXPANDED:
                                    //bottomSheetBehavior.setFitToContents(false);
                                    break;
                                case BottomSheetBehavior.STATE_DRAGGING:
                                    break;
                                case BottomSheetBehavior.STATE_SETTLING:
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                            Log.i("bottomSheet ===>>", slideOffset + "");
                            if (slideOffset < -0.8) {
                                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                //sheetBehavior.setHalfExpandedRatio(initialHeight);
                            }
                        }
                    });
            }
        });

        /*dataBinding.setScanner.setVisibility(View.GONE);
        dataBinding.setScanner.setFocusable(false);*/
        dataBinding.setScanner.requestFocus();
        dataBinding.setScanner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if(dataBinding.startSes.getText().toString().equalsIgnoreCase("Start Session")){
                        showSnackBar("Click Start Session then continue scanning.");
                    }else {

                        String text0 = "";
                        if (dataBinding.tvLoc.getText().toString().isEmpty()) {
                            dataBinding.tvLoc.setError("Location is Required");
                            text0 += "Location, ";
                        } else {
                            dataBinding.tvLoc.setError(null);
                        }

                        /*if (dataBinding.etItemType.getText().toString().isEmpty()) {
                            dataBinding.etItemType.setError("Item type is Required");
                            text0 += "Item Type ";
                        } else {
                            dataBinding.etItemType.setError(null);
                        }*/

                        if (!text0.isEmpty())
                            showSnackBar(" Select " + text0 + ".");

                        if (!dataBinding.tvLoc.getText().toString().isEmpty()) {
                            //&& !dataBinding.etItemType.getText().toString().isEmpty()
                            Log.e("TAG_SCAN", s.toString());

                            if (!s.toString().isEmpty()) {
                                String text = s.toString().trim();
                                if (text.contains(" ")) {
                                    String[] parts = text.split(Pattern.quote(" "));
                                    if(parts[0].contains("*")){
                                        String[] part01 = parts[0].split(Pattern.quote("*"));
                                        updateServer(part01[0].trim(), text, (float) 0.0);
                                    }else {
                                        updateServer(parts[0].trim(), text, (float) 0.0);
                                    }
                                }else if (text.contains("*")) {
                                    String[] parts = text.split(Pattern.quote("*"));
                                    updateServer(parts[0].trim(), text, (float) 0.0);
                                } else {
                                    updateServer(text.trim(), text, (float) 0.0);
                                }

                                dataBinding.setScanner.setText("");
                                Log.e("TAG_scan", text);
                            }
                        }
                    }
                }catch (Exception e){
                    Log.e("TAG", e.getMessage()+"");
                }
                Log.e("TAG", s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (dataBinding.setScanner.getText().hashCode() == s.hashCode()) {
                }*/
            }
        });

        try {
            if (viewModel.getPreferenceHelper().getBpApprover() == null) {
                dataBinding.startSes.setText("Start Session");
                viewModel.getPreferenceHelper().setBpApprover("N");
                viewModel.clearData();
            } else if (viewModel.getPreferenceHelper().getBpApprover().equalsIgnoreCase("N")) {
                dataBinding.startSes.setText("Start Session");
            } else {
                dataBinding.startSes.setText("End Session");
                dataBinding.createProfile.setClickable(false);
            }
        }catch (Exception e){
            Log.e("TAG", e.getMessage());
            dataBinding.startSes.setText("Start Session");
            viewModel.getPreferenceHelper().setBpApprover("N");
            viewModel.clearData();
        }

        dataBinding.startSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataBinding.startSes.getText().toString().equalsIgnoreCase("Start Session")) {
                    if (String.valueOf(dataBinding.setCnp.getText()).isEmpty()) {
                        showSnackBar("Create Profile...");
                    } else {

                        if(viewModel.getPreferenceHelper().getEmpTypeCode().equalsIgnoreCase("A")) {
                            viewModel.startSessionInit();
                        }else {
                            dataBinding.startSes.setText("End Session");
                            dataBinding.createProfile.setClickable(false);
                            viewModel.getPreferenceHelper().setBpApprover("Y");
                        }
                    }
                } else if (dataBinding.startSes.getText().toString().equalsIgnoreCase("End Session")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Confirmation.");
                    builder.setMessage("Are you sure want to end this session.");

                    builder.setPositiveButton("OK", (dialog, which) -> {

                        if(viewModel.getPreferenceHelper().getEmpTypeCode().equalsIgnoreCase("A")) {
                            viewModel.endSessionInit();
                            dialog.dismiss();
                        }else {
                            dataBinding.startSes.setText("Start Session");
                            dataBinding.createProfile.setClickable(true);
                            viewModel.getPreferenceHelper().setBpApprover("N");
                            viewModel.clearData();
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    });

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        viewModel.getLiveDataLocation().removeObservers(this);
        viewModel.getLiveDataLocation().observe(requireActivity(),getLocationModelResource -> {
            switch (getLocationModelResource.status){
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    hideLoading();
                    assert getLocationModelResource.data!=null;
                    setLocationData(getLocationModelResource.data.getResponseObject());
                    break;
                case ERROR:
                    hideLoading();
                    break;
                default:
                    break;

            }
        });

        dataBinding.createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.createProfileInit();
            }
        });

        viewModel.getScanData().observe(requireActivity(), model -> {
            mScanAdapter.clearList();
            mScanAdapter.addCustomers(model);
            mScanAdapter.notifyDataSetChanged();
        });
    }

    private void setLocationData(List<GetLocationModel.ResponseObject> responseObjectList){
        ArrayList<String> list = new ArrayList<>();
        for(GetLocationModel.ResponseObject ob : responseObjectList) {
            list.add(ob.getLocationName());
        }

        dataBinding.tvLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationList(list);
            }
        });
    }

    private void updateServer(String batch, String sBatch, Float qty){
        //dataBinding.etItemType.getText().toString()
        viewModel.updateBatchInit(batch, dataBinding.setCnp.getText().toString(), "", dataBinding.tvLoc.getText().toString(), sBatch, qty);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPostSuccess(String msg) {
        showSnackBar(msg);
    }

    @Override
    public void onPostEndSuccess(String msg) {
        dataBinding.startSes.setText("Start Session");
        dataBinding.createProfile.setClickable(true);
        viewModel.getPreferenceHelper().setBpApprover("N");
        viewModel.clearData();
        showSnackBar(msg);
    }

    @Override
    public void onPostStartSuccess(String msg) {
        dataBinding.startSes.setText("End Session");
        dataBinding.createProfile.setClickable(false);
        viewModel.getPreferenceHelper().setBpApprover("Y");
        showSnackBar(msg);
    }

    @Override
    public void onPostFailed(String msg) {
        showAlertBox(msg);
    }

    @Override
    public void onPostEndFailed(String msg) {
        showAlertBox(msg);
    }

    @Override
    public void onPostStartFailed(String msg) {
        showAlertBox(msg);
    }

    @Override
    public void onProfileGetSuccess(GetProfileModel.ResponseObject responseObject) {
        dataBinding.setCnp.setText(responseObject.getProfileName());
    }

    @Override
    public void onProfileGetFailed(String msg) {
        //dataBinding.setCnp.setText("");
        showSnackBar(msg);
    }

    public void getLocationList(ArrayList<String> st){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Choose Location.");
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout_list, null);
        builder.setView(customLayout);
        ListView list = customLayout.findViewById(R.id.lv1);
        SearchView sView = customLayout.findViewById(R.id.searchView);
        AlertDialog dialog = builder.create();
        dialog.show();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, st);
        list.setAdapter(adapter);

        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataBinding.tvLoc.setText(adapter.getItem(position));
                dialog.dismiss();
            }
        });

    }

}
