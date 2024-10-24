package com.covalsys.phi_scanner.ui.scan.batch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.DeliveryLine;
import com.covalsys.phi_scanner.databinding.DeliveryAdapterLayoutBinding;
import com.covalsys.phi_scanner.databinding.EmptyAdapterBinding;
import com.covalsys.phi_scanner.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Prasanth Muthu on 16-10-2020.
 */
public class BatchScanItemAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private Callback mCallback;
    private List<DeliveryLine> inList;
    private Context mContext;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public interface Callback {
        //void onPCustomerClick(int position , GateEntryLine models);
    }

    public BatchScanItemAdapter(List<DeliveryLine> mList, Context context) {
        this.inList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                DeliveryAdapterLayoutBinding deliveryAdapterLayoutBinding = DeliveryAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

   /* public void addCustomers(List<GateEntryLine> list) {
        inList.addAll(list);
    }*/

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

        DeliveryAdapterLayoutBinding mBinding;

        public ViewHolder(DeliveryAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            if (position == 0) {

                mBinding.batchCode.setText("Batch");
                mBinding.itemCode.setText("Item Code");
                mBinding.series.setText("S.No.");
                mBinding.docNum.setText("Doc Num");
                mBinding.docStatus.setText("Doc Status");
                mBinding.itemName.setText("Item Name");
                mBinding.itemUom.setText("UOM");
                mBinding.quantity.setText("Quantity");

                mBinding.batchCode.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.itemCode.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.series.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.docNum.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.docStatus.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.itemName.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.itemUom.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.quantity.setBackgroundResource(R.drawable.table_header_cell_bg);

                mBinding.batchCode.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.itemCode.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.series.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.docNum.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.docStatus.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.itemName.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.itemUom.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.quantity.setTextColor(mContext.getResources().getColor(R.color.white));
            }else {
             /*   GateEntryLine model = inList.get(position-1);

                mBinding.batchCode.setText(model.getBatchNo());
                mBinding.itemCode.setText(model.getItemCode());
                mBinding.series.setText(String.valueOf(model.getSlno()));
                mBinding.docNum.setText(String.valueOf(model.getDocNum()));
                if(model.getBatchStatus().equals("Success")) {
                    mBinding.docStatus.setText(String.valueOf(model.getBatchStatus()));
                    mBinding.docStatus.setTextColor(mContext.getResources().getColor(R.color.green));
                }else if(model.getBatchStatus().equals("Pending")){
                    mBinding.docStatus.setText(String.valueOf(model.getBatchStatus()));
                    mBinding.docStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
                }
                mBinding.itemName.setText(String.valueOf(model.getItemName()));
                mBinding.itemUom.setText(String.valueOf(model.getUom()));
                mBinding.quantity.setText(String.valueOf(model.getQuantity()));

                mBinding.batchCode.setBackgroundResource(R.drawable.table_bg);
                mBinding.itemCode.setBackgroundResource(R.drawable.table_bg);
                mBinding.series.setBackgroundResource(R.drawable.table_bg);
                mBinding.docNum.setBackgroundResource(R.drawable.table_bg);
                mBinding.docStatus.setBackgroundResource(R.drawable.table_bg);
                mBinding.itemName.setBackgroundResource(R.drawable.table_bg);
                mBinding.itemUom.setBackgroundResource(R.drawable.table_bg);
                mBinding.quantity.setBackgroundResource(R.drawable.table_bg);

                mBinding.batchCode.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.itemCode.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.series.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.docNum.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.itemName.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.itemUom.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.quantity.setTextColor(mContext.getResources().getColor(R.color.gray_dark));

                mBinding.cardView.setOnClickListener(v -> mCallback.onPCustomerClick(position, model));*/
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
