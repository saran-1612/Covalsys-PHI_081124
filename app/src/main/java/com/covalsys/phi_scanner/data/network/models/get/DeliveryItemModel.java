package com.covalsys.phi_scanner.data.network.models.get;

public class DeliveryItemModel {

    private String slno;
    private String itemCode;
    private String itemName;
    private String barCode;
    private String batch;
    private Float length;
    private Float width;
    private Float thickness;
    private Float qty;
    private Float barCodeQty;
    private String status;

    public DeliveryItemModel(String itemCode, String itemName, String batch, String barCode, Float length, Float width, Float thickness, Float Qty, Float barCodeQty, String status) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batch = batch;
        this.barCode = barCode;
        this.length = length;
        this.width = width;
        this.thickness = thickness;
        this.qty = Qty;
        this.barCodeQty = barCodeQty;
        this.status = status;
    }

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Float getThickness() {
        return thickness;
    }

    public void setThickness(Float thickness) {
        this.thickness = thickness;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public Float getBarCodeQty() {
        return barCodeQty;
    }

    public void setBarCodeQty(Float barCodeQty) {
        this.barCodeQty = barCodeQty;
    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
