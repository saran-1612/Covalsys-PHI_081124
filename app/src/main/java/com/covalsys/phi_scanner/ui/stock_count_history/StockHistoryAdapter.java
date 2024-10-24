package com.covalsys.phi_scanner.ui.stock_count_history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.network.models.get.StockHistoryModel;
import com.covalsys.phi_scanner.databinding.EmptyAdapterBinding;
import com.covalsys.phi_scanner.databinding.StockHistoryAdapterLayoutBinding;
import com.covalsys.phi_scanner.ui.base.BaseViewHolder;
import com.covalsys.phi_scanner.utils.DateUtils;

import java.util.List;

public class StockHistoryAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    List<StockHistoryModel.ResponseObject> list;
    private Context mContext;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public StockHistoryAdapter(Context context, List<StockHistoryModel.ResponseObject> mList) {
        this.list = mList;
        this.mContext = context;
    }

    public void clearList() {
        list.clear();
    }

    public void addCustomers(List<StockHistoryModel.ResponseObject> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                StockHistoryAdapterLayoutBinding historyAdapterLayoutBinding = StockHistoryAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolder(historyAdapterLayoutBinding);
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
        if (list != null && !list.isEmpty()) {
            return list.size();
        } else {
            return 0;
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

    public class ViewHolder extends BaseViewHolder {

        StockHistoryAdapterLayoutBinding mBinding;

        public ViewHolder(StockHistoryAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

                StockHistoryModel.ResponseObject model = list.get(position);
                mBinding.batchNo.setText(model.getScanBatch());
                mBinding.sLoc.setText(model.getScanLocation());
                mBinding.itemType.setText(model.getType());
                mBinding.scanDate.setText(DateUtils.convertDate(model.getScanDate()));
                mBinding.loggedUser.setText(model.getLoggedUser());
                mBinding.qty.setText(model.getScanQty()+"");
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
            mBinding.imageViewEmpty.setBackgroundResource(R.drawable.ic_undraw_empty);
        }
    }
}
