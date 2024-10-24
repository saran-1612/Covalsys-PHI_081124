package com.covalsys.phi_scanner.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class StockCountScanLine {

    @PrimaryKey
    @ColumnInfo(name = "slno")
    private Integer slno;

    @ColumnInfo(name = "DocEntry")
    private Integer docEntry;

    @ColumnInfo(name = "BatchNo")
    private String batchNo;

    @ColumnInfo(name = "Quantity")
    private Float quantity;

    @ColumnInfo(name = "Thick")
    private Float thick;

    @ColumnInfo(name = "Width")
    private Float width;

    @ColumnInfo(name = "Length")
    private Float length;

    @ColumnInfo(name = "NetWeigth")
    private Float netWeight;

    @ColumnInfo(name = "GrossWeigth")
    private Float grossWeight;


    public StockCountScanLine(Integer docEntry, String batchNo, Float thick, Float width, Float length, Float quantity, Float netWeight, Float grossWeight) {
        this.docEntry = docEntry;
        this.batchNo = batchNo;
        this.thick = thick;
        this.width = width;
        this.length = length;
        this.quantity = quantity;
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getThick() {
        return thick;
    }

    public void setThick(Float thick) {
        this.thick = thick;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
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

    public void setGrossWeight(Float grossWeight) {
        this.grossWeight = grossWeight;
    }

}
