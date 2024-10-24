package com.covalsys.phi_scanner.ui.stock_count;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.StockCountLine;
import com.covalsys.phi_scanner.databinding.EmptyAdapterBinding;
import com.covalsys.phi_scanner.databinding.StackCountScanAdapterLayoutBinding;
import com.covalsys.phi_scanner.ui.base.BaseViewHolder;

import java.util.List;
import java.util.Objects;

public class StockCountScanAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<StockCountLine> list;
    private Context mContext;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public StockCountScanAdapter(Context context, List<StockCountLine> mList) {
        this.list = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                StackCountScanAdapterLayoutBinding deliveryAdapterLayoutBinding = StackCountScanAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolder(deliveryAdapterLayoutBinding);
            case VIEW_TYPE_EMPTY:
            default:
                EmptyAdapterBinding emptyViewBinding = EmptyAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new StockCountScanAdapter.EmptyViewHolder(emptyViewBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (list != null && !list.isEmpty()) {
            return list.size()+1;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list != null && !list.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void addCustomers(List<StockCountLine> list) {
        this.list = list;
    }

    public void clearList() {
        list.clear();
    }

    public class ViewHolder extends BaseViewHolder {

        StackCountScanAdapterLayoutBinding mBinding;

        public ViewHolder(StackCountScanAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            if (position == 0) {

                mBinding.series.setText("#");
                mBinding.batchNo.setText("Batch No");
                mBinding.batchQty.setText("Quantity");
                mBinding.status.setText("S");

                mBinding.series.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.batchNo.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.batchQty.setBackgroundResource(R.drawable.table_header_cell_bg);
                mBinding.status.setBackgroundResource(R.drawable.table_header_cell_bg);

                mBinding.series.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.batchNo.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.batchQty.setTextColor(mContext.getResources().getColor(R.color.white));
                mBinding.status.setTextColor(mContext.getResources().getColor(R.color.white));

            }else {

                StockCountLine model = list.get(position-1);

                mBinding.series.setText(String.valueOf(position));
                mBinding.batchNo.setText(String.valueOf(model.getBatchNo()));
                mBinding.batchQty.setText(String.valueOf(model.getQty()));

                if(Objects.isNull(model.getBatchNo())){
                    mBinding.status.setText(String.valueOf("Y"));
                }else {
                    mBinding.status.setText(String.valueOf(model.getBatchStatus()));
                }

                mBinding.series.setBackgroundResource(R.drawable.table_bg);
                mBinding.batchNo.setBackgroundResource(R.drawable.table_bg);
                mBinding.batchQty.setBackgroundResource(R.drawable.table_bg);
                mBinding.status.setBackgroundResource(R.drawable.table_bg);

                mBinding.series.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.batchNo.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.batchQty.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                mBinding.status.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
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
}
