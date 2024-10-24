package com.covalsys.phi_scanner.data.network.models.get;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemBatchModel implements Parcelable {

    private String slno;
    private String barCode;
    private String itemCode;
    private String batch;
    private String length;
    private String width;
    private String thickness;
    private String qty;
    private String price;

    public ItemBatchModel(String itemCode, String batch, String barCode, String length, String width, String thickness, String Qty) {
        this.itemCode = itemCode;
        this.batch = batch;
        this.barCode = barCode;
        this.length = length;
        this.width = width;
        this.thickness = thickness;
        this.qty = Qty;
    }

    protected ItemBatchModel(Parcel in) {
        slno = in.readString();
        barCode = in.readString();
        itemCode = in.readString();
        batch = in.readString();
        length = in.readString();
        width = in.readString();
        thickness = in.readString();
        qty = in.readString();
        price = in.readString();
    }

    public static final Creator<ItemBatchModel> CREATOR = new Creator<ItemBatchModel>() {
        @Override
        public ItemBatchModel createFromParcel(Parcel in) {
            return new ItemBatchModel(in);
        }

        @Override
        public ItemBatchModel[] newArray(int size) {
            return new ItemBatchModel[size];
        }
    };

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String width) {
        this.qty = qty;
    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(slno);
        dest.writeString(barCode);
        dest.writeString(itemCode);
        dest.writeString(batch);
        dest.writeString(length);
        dest.writeString(width);
        dest.writeString(thickness);
        dest.writeString(qty);
        dest.writeString(price);
    }
}
