package com.covalsys.phi_scanner.ui.delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.network.models.get.BusinessTypeModel;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

/**
 * Created by Prasanth on 04-09-2020.
 */
public class BusinessTypeAdapter extends ArrayAdapter<BusinessTypeModel.ResponseObject> implements Filterable {

    private Callback mCallback;
    private List<BusinessTypeModel.ResponseObject> customerModel;
    private List<BusinessTypeModel.ResponseObject> mFilterdCustomerModel;
    private Context mContext;

    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position, BusinessTypeModel.ResponseObject model);
    }

    public BusinessTypeAdapter(@NonNull Context context, List<BusinessTypeModel.ResponseObject> wList) {
        super(context, R.layout.custom_search,wList);
        this.customerModel = wList;
        this.mContext = context;
        this.mFilterdCustomerModel = wList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.custom_search, null);
            viewHolder = new ViewHolder();
            viewHolder.items =  convertView.findViewById(R.id.tvCode);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BusinessTypeModel.ResponseObject myDataItem = getItem(position);
        viewHolder.items.setText(myDataItem.getBusinessName());

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mFilterdCustomerModel = customerModel;
                } else {
                    List<BusinessTypeModel.ResponseObject> filteredList = new ArrayList<>();
                    for (BusinessTypeModel.ResponseObject row : customerModel) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for Name or Mobile Number match
                        if (row.getBusinessName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getBusinessCode().contains(constraint)) {
                            filteredList.add(row);
                        }
                    }
                    mFilterdCustomerModel = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterdCustomerModel;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilterdCustomerModel = (List<BusinessTypeModel.ResponseObject>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder {
        TextView items;
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }
}
