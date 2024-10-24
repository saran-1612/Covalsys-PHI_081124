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
import com.covalsys.phi_scanner.data.network.models.get.GetDeliveryCustomerModel;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

/**
 * Created by Prasanth on 04-09-2020.
 */
public class DealersAdapter extends ArrayAdapter<GetDeliveryCustomerModel.ResponseObject> implements Filterable {

    private Callback mCallback;
    private List<GetDeliveryCustomerModel.ResponseObject> customerModel;
    private List<GetDeliveryCustomerModel.ResponseObject> mFilterdCustomerModel;
    private Context mContext;

    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position, GetDeliveryCustomerModel.ResponseObject model);
    }

    public DealersAdapter(@NonNull Context context, List<GetDeliveryCustomerModel.ResponseObject> wList) {
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

        GetDeliveryCustomerModel.ResponseObject myDataItem = getItem(position);
        viewHolder.items.setText(myDataItem.getCustomerName());

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
                    List<GetDeliveryCustomerModel.ResponseObject> filteredList = new ArrayList<>();
                    for (GetDeliveryCustomerModel.ResponseObject row : customerModel) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for Name or Mobile Number match
                        if (row.getCustomerName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCustomerCode().contains(constraint)) {
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
                mFilterdCustomerModel = (List<GetDeliveryCustomerModel.ResponseObject>) results.values;
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
