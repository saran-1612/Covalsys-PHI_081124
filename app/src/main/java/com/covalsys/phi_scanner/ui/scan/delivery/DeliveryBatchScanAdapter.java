package com.covalsys.phi_scanner.ui.scan.delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.DeliveryScanLine;
import com.covalsys.phi_scanner.databinding.DeliveryScanBatchListLayoutAdapterBinding;
import com.covalsys.phi_scanner.databinding.EmptyAdapterBinding;
import com.covalsys.phi_scanner.ui.base.BaseViewHolder;

import java.util.List;

public class DeliveryBatchScanAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private Callback mCallback;
    private List<DeliveryScanLine> inList;
    private Context mContext;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public interface Callback {
        void onDelete(int position , DeliveryScanLine models);
    }

    public DeliveryBatchScanAdapter(List<DeliveryScanLine> mList, Context context) {
        this.inList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                DeliveryScanBatchListLayoutAdapterBinding deliveryAdapterLayoutBinding = DeliveryScanBatchListLayoutAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

    public void addCustomers(List<DeliveryScanLine> list) {
        inList.addAll(list);
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

        DeliveryScanBatchListLayoutAdapterBinding mBinding;

        public ViewHolder(DeliveryScanBatchListLayoutAdapterBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            if (position == 0) {
                mBinding.tvSlno.setText("#");
                mBinding.tvBatch.setText("Barcode Batch");
                mBinding.tvLength.setText("Length");
                mBinding.tvWidth.setText("Width");
                mBinding.tvThickness.setText("Thickness");
                mBinding.tvQty.setText("Quantity");
                mBinding.tvRemove.setVisibility(View.VISIBLE);
                mBinding.imDelete.setVisibility(View.GONE);
                mBinding.tvRemove.setText("Action");

                mBinding.layView.setBackgroundResource(R.drawable.table_header);
                mBinding.tvSlno.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.tvBatch.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.tvLength.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.tvWidth.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.tvThickness.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.tvQty.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.tvRemove.setTextColor(mContext.getResources().getColor(R.color.white));

            }else{
                DeliveryScanLine model = inList.get(position-1);
                mBinding.tvRemove.setVisibility(View.GONE);
                mBinding.imDelete.setVisibility(View.VISIBLE);
                mBinding.tvSlno.setText(position+"");
                mBinding.tvBatch.setText(model.getBatchNo());
                mBinding.tvCoilNo.setText(model.getCoilNo());
                mBinding.tvLength.setText(String.valueOf(model.getLength()));
                mBinding.tvWidth.setText(String.valueOf(model.getWidth()));
                mBinding.tvThickness.setText(String.valueOf(model.getThick()));
                mBinding.tvQty.setText(String.valueOf(model.getQuantity()));

                //mBinding.layView.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvSlno.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvBatch.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvCoilNo.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvLength.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvWidth.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvThickness.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvQty.setBackgroundResource(R.drawable.table_bg);

                mBinding.tvSlno.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                mBinding.tvBatch.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                mBinding.tvCoilNo.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                mBinding.tvLength.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                mBinding.tvWidth.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                mBinding.tvThickness.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                mBinding.tvQty.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                mBinding.tvRemove.setTextColor(mContext.getResources().getColor(R.color.text_black_87));

                mBinding.imDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onDelete(position, model);
                    }
                });
            }

                /*DeliveryScanLine model = inList.get(position);
                mBinding.tvSlnpo.setText(String.valueOf(position+1));
                mBinding.tvBatch.setText(model.getBatchNo());
                mBinding.tvLength.setText(String.valueOf(model.getLength()));
                mBinding.tvWidth.setText(String.valueOf(model.getWidth()));
                mBinding.tvThickness.setText(String.valueOf(model.getThick()));
                mBinding.tvQty.setText(String.valueOf(model.getQuantity()));

                mBinding.imDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onDelete(position, model);
                    }
                });

                mBinding.tvSlnpo.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvBatch.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvLength.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvWidth.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvThickness.setBackgroundResource(R.drawable.table_bg);
                mBinding.tvQty.setBackgroundResource(R.drawable.table_bg);

                mBinding.tvSlnpo.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.tvBatch.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.tvLength.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.tvWidth.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.tvThickness.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.tvQty.setTextColor(mContext.getResources().getColor(R.color.gray_dark));*/

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
