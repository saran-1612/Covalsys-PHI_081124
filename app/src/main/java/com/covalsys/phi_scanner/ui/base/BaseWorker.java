package com.covalsys.phi_scanner.ui.base;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public abstract class BaseWorker<V extends ViewModel> extends Worker {

    private Context mContext;

    protected V viewModel;

    public abstract int getBindingVariable();

    @LayoutRes
    public abstract int getLayoutRes();

    protected abstract Class<V> getViewModel();

    public BaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
    }
}
