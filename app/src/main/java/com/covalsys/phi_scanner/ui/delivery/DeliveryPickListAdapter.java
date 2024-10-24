package com.covalsys.phi_scanner.ui.delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.DeliveryLine;
import com.covalsys.phi_scanner.databinding.DeliveryPickListAdapterLayoutBinding;
import com.covalsys.phi_scanner.databinding.EmptyAdapterBinding;
import com.covalsys.phi_scanner.ui.base.BaseViewHolder;

import java.util.List;
import java.util.Objects;

/**
 * Created by Prasanth on 16-10-2020.
 */
public class DeliveryPickListAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private Callback mCallback;
    private List<DeliveryLine> inList;
    private Context mContext;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public interface Callback {
        void onPCustomerClick(int position , DeliveryLine models);
    }

    public DeliveryPickListAdapter(List<DeliveryLine> mList, Context context) {
        this.inList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                DeliveryPickListAdapterLayoutBinding deliveryAdapterLayoutBinding = DeliveryPickListAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolder(deliveryAdapterLayoutBinding);
            case VIEW_TYPE_EMPTY:
            default:
                EmptyAdapterBinding emptyViewBinding = EmptyAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (inList != null && inList.size() > 0) {
            return inList.size()+1;
        } else {
            return 1;
        }
    }

    public void addCustomers(List<DeliveryLine> list) {
        this.inList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (inList != null && !inList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void clearList() {
        inList.clear();
    }

    public class ViewHolder extends BaseViewHolder {

        DeliveryPickListAdapterLayoutBinding mBinding;

        public ViewHolder(DeliveryPickListAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            if (position == 0) {

                mBinding.batchCode.setText("Batch");
                mBinding.itemCode.setText("Item Code");
                mBinding.series.setText("S.No.");
                mBinding.itemName.setText("Item Name");
                mBinding.thick.setText("Thick");
                mBinding.width.setText("Width");
                mBinding.length.setText("Length");
                mBinding.quantity.setText("Quantity");
                mBinding.barCodeBatch.setText("Barcode Batch");
                mBinding.BarCodeQty.setText("Bar Code Qty");
                mBinding.Status.setText("Status");

                mBinding.batchCode.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.itemCode.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.series.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.thick.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.width.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.itemName.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.length.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.quantity.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.barCodeBatch.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.BarCodeQty.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.Status.setBackgroundResource(R.drawable.table_header_cell_bg);

                mBinding.batchCode.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.itemCode.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.series.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.thick.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.width.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.itemName.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.length.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.quantity.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.BarCodeQty.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.barCodeBatch.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.Status.setTextColor(mContext.getResources().getColor(R.color.white));
            }else {
                DeliveryLine model = inList.get(position-1);

                mBinding.batchCode.setText(model.getBatchNo());
                mBinding.itemCode.setText(model.getItemCode());
                mBinding.series.setText(String.valueOf(position));
                mBinding.thick.setText(String.valueOf(model.getThick()));
                mBinding.width.setText(String.valueOf(model.getWidth()));
                mBinding.length.setText(String.valueOf(model.getLength()));
                mBinding.itemName.setText(String.valueOf(model.getItemName()));
                mBinding.quantity.setText(String.valueOf(model.getQuantity()));
                mBinding.BarCodeQty.setText(String.valueOf(model.getBarCodeQty()));
                mBinding.barCodeBatch.setText(String.valueOf(model.getBarCodeBatch()));
                if(Objects.equals(model.getQuantity(), model.getBarCodeQty()) && model.getBatchNo().equalsIgnoreCase(model.getBarCodeBatch())){
                    mBinding.Status.setText(String.valueOf("Y"));
                }else {
                    mBinding.Status.setText(String.valueOf(model.getBatchStatus()));
                }

                mBinding.batchCode.setBackgroundResource(R.drawable.table_bg);
                mBinding.itemCode.setBackgroundResource(R.drawable.table_bg);
                mBinding.series.setBackgroundResource(R.drawable.table_bg);
                mBinding.thick.setBackgroundResource(R.drawable.table_bg);
                mBinding.width.setBackgroundResource(R.drawable.table_bg);
                mBinding.length.setBackgroundResource(R.drawable.table_bg);
                mBinding.itemName.setBackgroundResource(R.drawable.table_bg);
                mBinding.BarCodeQty.setBackgroundResource(R.drawable.table_bg);
                mBinding.barCodeBatch.setBackgroundResource(R.drawable.table_bg);
                mBinding.Status.setBackgroundResource(R.drawable.table_bg);
                mBinding.quantity.setBackgroundResource(R.drawable.table_bg);

                mBinding.batchCode.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.itemCode.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.series.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.thick.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.itemName.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.width.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.length.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.quantity.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.BarCodeQty.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.barCodeBatch.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.Status.setTextColor(mContext.getResources().getColor(R.color.gray_dark));

                mBinding.cardView.setOnClickListener(v -> mCallback.onPCustomerClick(position, model));
            }
        }
    }

    public static class EmptyViewHolder extends BaseViewHolder{

        private EmptyAdapterBinding mBinding;

        public EmptyViewHolder(EmptyAdapterBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            this.mBinding.imageViewEmpty.setBackgroundResource(R.drawable.ic_undraw_empty);
        }
    }

    public void setOnClick(Callback onClick) {
        this.mCallback = onClick;
    }
}
