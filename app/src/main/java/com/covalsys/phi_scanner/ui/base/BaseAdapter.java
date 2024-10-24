package com.covalsys.phi_scanner.ui.base;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<T> {

    public abstract void setData(List<D> data);
}
