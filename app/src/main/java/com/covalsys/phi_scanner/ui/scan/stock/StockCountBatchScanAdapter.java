package com.covalsys.phi_scanner.ui.scan.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.StockCountScanLine;
import com.covalsys.phi_scanner.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class StockCountBatchScanAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<StockCountScanLine> mModel;
    private Context mContext;
    private Callback mCallback;

    public StockCountBatchScanAdapter(ArrayList<StockCountScanLine> mInvoiceModel, Context mContext) {
        this.mModel = mInvoiceModel;
        this.mContext = mContext;
    }

    public void setOnClick(Callback onClick) {
        this.mCallback = onClick;
    }

    public interface Callback {
        void onDelete(int position , StockCountScanLine models);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_batch_list_adapter_layout, parent, false);
        return new InvoiceViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mModel.size()+1;
    }
    public List<StockCountScanLine> getList() {
        return mModel;
    }

    public void addData(List<StockCountScanLine> list) {
        this.mModel = list;
    }

    public void clearList() {
        mModel.clear();
    }

    public class InvoiceViewHolder extends BaseViewHolder {
        public AppCompatTextView tvSlno, tvBatch, tvLength, tvWidth, tvThickness, tvRemove, tvQty;
        ImageView imDel;
        LinearLayout layView;

        public InvoiceViewHolder(View itemView) {
            super(itemView);
            tvSlno = itemView.findViewById(R.id.tvSlnpo);
            imDel = itemView.findViewById(R.id.imDelete);
            tvRemove = itemView.findViewById(R.id.tvRemove);
            tvBatch = itemView.findViewById(R.id.tvBatch);
            tvLength = itemView.findViewById(R.id.tvLength);
            tvWidth = itemView.findViewById(R.id.tvWidth);
            tvThickness = itemView.findViewById(R.id.tvThickness);
            tvQty = itemView.findViewById(R.id.tvQty);
            layView = itemView.findViewById(R.id.layView);
        }

        @Override
        public void onBind(int position) {
            if (position == 0) {
                tvSlno.setText("Sl.No.");
                tvBatch.setText("Bar Code Batch");
                tvLength.setText("Length");
                tvWidth.setText("Width");
                tvThickness.setText("Thickness");
                tvQty.setText("Quantity");
                tvRemove.setVisibility(View.VISIBLE);
                imDel.setVisibility(View.GONE);
                tvRemove.setText("Action");

                layView.setBackgroundResource(R.drawable.table_header);
                tvSlno.setTextColor(mContext.getResources().getColor(R.color.white));
                tvBatch.setTextColor(mContext.getResources().getColor(R.color.white));
                tvLength.setTextColor(mContext.getResources().getColor(R.color.white));
                tvWidth.setTextColor(mContext.getResources().getColor(R.color.white));
                tvThickness.setTextColor(mContext.getResources().getColor(R.color.white));
                tvQty.setTextColor(mContext.getResources().getColor(R.color.white));
                tvRemove.setTextColor(mContext.getResources().getColor(R.color.white));

            }else{
                StockCountScanLine model = mModel.get(position-1);
                tvRemove.setVisibility(View.GONE);
                imDel.setVisibility(View.VISIBLE);
                tvSlno.setText(position+"");
                tvBatch.setText(model.getBatchNo());
                tvLength.setText(String.valueOf(model.getLength()));
                tvWidth.setText(String.valueOf(model.getWidth()));
                tvThickness.setText(String.valueOf(model.getThick()));
                tvQty.setText(String.valueOf(model.getQuantity()));

                layView.setBackgroundResource(R.drawable.table_bg);
                tvSlno.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                tvBatch.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                tvLength.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                tvWidth.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                tvThickness.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                tvQty.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                tvRemove.setTextColor(mContext.getResources().getColor(R.color.text_black_87));

                imDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onDelete(position, model);
                    }
                });
            }
            /*invoiceNumber.setText(String.valueOf(model.getDocNum()));
            invoiceAmount.setText(String.valueOf("₹ " + CommonUtils.doubleToStringNoDecimal(model.getBalance())));*/
        }
    }
}
