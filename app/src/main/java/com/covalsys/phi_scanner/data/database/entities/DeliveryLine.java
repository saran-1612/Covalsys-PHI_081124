package com.covalsys.phi_scanner.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DeliveryLine {

    @PrimaryKey
    @ColumnInfo(name = "slno")
    private Integer slno;

    @ColumnInfo(name = "DocEntry")
    private Integer docEntry;

    @ColumnInfo(name = "DocNum")
    private Integer docNum;

    @ColumnInfo(name = "Line")
    private Integer line;

    @ColumnInfo(name = "ItemCode")
    private String itemCode;

    @ColumnInfo(name = "ItemName")
    private String itemName;

    @ColumnInfo(name = "BatchNo")
    private String batchNo;

    @ColumnInfo(name = "Quantity")
    private Float quantity;

    @ColumnInfo(name = "Thick")
    private String thick;

    @ColumnInfo(name = "Width")
    private String width;

    @ColumnInfo(name = "Length")
    private String length;

    @ColumnInfo(name = "Uom")
    private String uom;

    @ColumnInfo(name = "BarCodeBatch")
    private String barCodeBatch;

    @ColumnInfo(name = "BarCodeQty")
    private Float barCodeQty;

    @ColumnInfo(name = "BatchStatus")
    private String batchStatus;

    public DeliveryLine(Integer docEntry, Integer docNum, Integer line, String itemCode, String itemName, String batchNo, String thick, String width, String length, Float quantity, String batchStatus, String barCodeBatch, Float barCodeQty) {
        this.docEntry = docEntry;
        this.docNum = docNum;
        this.line = line;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batchNo = batchNo;
        this.thick = thick;
        this.width = width;
        this.length = length;
        this.quantity = quantity;
        this.barCodeBatch = barCodeBatch;
        this.barCodeQty = barCodeQty;
        this.batchStatus = batchStatus;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public Integer getDocNum() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
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

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
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

    public String getBarCodeBatch() {
        return barCodeBatch;
    }

    public void setBarCodeBatch(String barCodeBatch) {
        this.barCodeBatch = barCodeBatch;
    }

    public Float getBarCodeQty() {
        return barCodeQty;
    }

    public void setBarCodeQty(Float barCodeQty) {
        this.barCodeQty = barCodeQty;
    }


}
