package com.covalsys.phi_scanner.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class DeliveryScanLine {

    @PrimaryKey
    @ColumnInfo(name = "slno")
    private Integer slno;

    @ColumnInfo(name = "DocEntry")
    private Integer docEntry;

    @ColumnInfo(name = "ItemCode")
    private String itemCode;

    @ColumnInfo(name = "ItemName")
    private String itemName;

    @ColumnInfo(name = "BatchNo")
    private String batchNo;

    @ColumnInfo(name = "CoilNo")
    private String coilNo;

    @ColumnInfo(name = "Quantity")
    private Float quantity;

    @ColumnInfo(name = "Thick")
    private String thick;

    @ColumnInfo(name = "Width")
    private String width;

    @ColumnInfo(name = "Length")
    private String length;

    @ColumnInfo(name = "NetWeigth")
    private Float netWeight;

    @ColumnInfo(name = "GrossWeigth")
    private Float grossWeight;

    @ColumnInfo(name = "Status")
    private String status;

    public DeliveryScanLine(Integer docEntry, String itemCode, String itemName, String batchNo, String coilNo, String thick, String width, String length, Float quantity, Float netWeight, Float grossWeight, String status) {
        this.docEntry = docEntry;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batchNo = batchNo;
        this.coilNo = coilNo;
        this.thick = thick;
        this.width = width;
        this.length = length;
        this.quantity = quantity;
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
        this.status = status;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCoilNo() {
        return coilNo;
    }

    public void setCoilNo(String coilNo) {
        this.coilNo = coilNo;
    }

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getThick() {
        return thick;
    }

    public void setThick(String thick) {
        this.thick = thick;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Float getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Float netWeight) {
        this.netWeight = netWeight;
    }

    public Float getGrossWeight() {
        return grossWeight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setGrossWeight(Float grossWeight) {
        this.grossWeight = grossWeight;
    }

}
