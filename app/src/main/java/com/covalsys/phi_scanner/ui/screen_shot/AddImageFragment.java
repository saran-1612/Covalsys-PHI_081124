package com.covalsys.phi_scanner.ui.screen_shot;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.ImageLine;
import com.covalsys.phi_scanner.databinding.AddImageFragmentBinding;
import com.covalsys.phi_scanner.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class AddImageFragment extends BaseFragment<AddImageViewModel, AddImageFragmentBinding> implements AddImageNavigator {


    Bitmap storeBitMap;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    List<ImageLine> lineList = new ArrayList<>();

    @Override
    public int getBindingVariable() {
        return BR.addImageViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.add_image_fragment;
    }

    @Override
    protected Class<AddImageViewModel> getViewModel() {
        return AddImageViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initScreens();
        return dataBinding.getRoot();
    }

    private void initScreens() {
        dataBinding.imageClick.setOnClickListener(v -> takePhoto());
        dataBinding.saveFile.setOnClickListener(v -> saveData());
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            dataBinding.imagePreview.setImageBitmap(photo);
            Log.e("TAG_image",dataBinding.imagePreview+"");
            storeBitMap = photo;
        }
    }

    private void saveData() {
        lineList = new ArrayList<>();
        String Enter_remarks = dataBinding.remarks.getText()+"";
        Log.e("TAG_Remarks",Enter_remarks+"");
        if (dataBinding.imagePreview == null) {
            Toast.makeText(requireActivity(), "Please capture an image first", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Enter_remarks.isEmpty()) {
            Toast.makeText(requireActivity(), "Please enter some remarks", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            ImageLine line = new ImageLine(storeBitMap,Enter_remarks);
            viewModel.getDatabase().imageDao().insert(line);
            Toast.makeText(requireActivity(),"Saved SuccessFully",Toast.LENGTH_SHORT).show();
            /*onBackPressed();*/
            Log.e("Tag_Ki",line+"");
        } catch (Exception e) {
            Log.e("Tag_Ki",e.getMessage()+"");
            throw new RuntimeException(e);
        }
    }

    private void onBackPressed() {

    }
}
