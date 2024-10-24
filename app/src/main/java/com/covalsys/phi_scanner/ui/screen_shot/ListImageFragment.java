package com.covalsys.phi_scanner.ui.screen_shot;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.ImageLine;
import com.covalsys.phi_scanner.databinding.ListImageFragmentBinding;
import com.covalsys.phi_scanner.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ListImageFragment extends BaseFragment<ListImageViewModel,ListImageFragmentBinding> implements ListImageNavigator, ListImageAdapter.Callback {


   /* FloatingActionButton actionButton;*/
    ListImageAdapter imageAdapter;
    List<ImageLine> lineList = new ArrayList<>();

    @Override
    public int getBindingVariable() {
        return BR.listImageViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.list_image_fragment;
    }

    @Override
    protected Class<ListImageViewModel> getViewModel() {
        return ListImageViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        /*actionButton = view.findViewById(R.id.floating);
        recyclerView = view.findViewById(R.id.list_item);*/
        setHasOptionsMenu(true);
        viewModel.setNavigator(this);
        initScreens();
        return dataBinding.getRoot();
    }

    private void initScreens() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBinding.listItem.setLayoutManager(layoutManager);
        dataBinding.listItem.setItemAnimator(new DefaultItemAnimator());
        imageAdapter = new ListImageAdapter(getActivity(),getImageList());
        dataBinding.listItem.setAdapter(imageAdapter);
        lineList = new ArrayList<>();
        lineList= getImageList();
        imageAdapter.setList(lineList);
        imageAdapter.setOnClick(this);
        imageAdapter.notifyDataSetChanged();
        dataBinding.floating.setOnClickListener(v -> floatOpen());
    }

    private void floatOpen() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_2,new AddImageFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private List<ImageLine> getImageList() {
       // database.imageDao().getAllDetails();
         return viewModel.getDatabase().imageDao().getAllDetails();
    }

    @Override
    public void onClickDelete(int position, ImageLine line) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Image Remarks");
        dialog.setMessage("Are you Sure Delete");
        dialog.setIcon(R.drawable.delete);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Deleted Successfully!!!...",Toast.LENGTH_LONG).show();
                deleteTask(line);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Cancel The Item!!!..",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteTask(ImageLine line) {
        viewModel.progressBar.postValue(true);
        viewModel.getDatabase().imageDao().delete(line.getId());
    }
}
