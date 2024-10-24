package com.covalsys.phi_scanner.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class StockCountLine {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "slNo")
    private Integer slNo;

    @ColumnInfo(name = "ProfileName")
    private String profileName;

    @ColumnInfo(name = "Location")
    private String location;

    @ColumnInfo(name = "ItemType")
    private String itemType;

    @ColumnInfo(name = "BatchNo")
    private String batchNo;

    @ColumnInfo(name = "Quantity")
    private Float qty;

    @ColumnInfo(name = "ScanTime")
    private String scanTime;

    @ColumnInfo(name = "ScanDate")
    private String scanDate;

    @ColumnInfo(name = "BatchStatus")
    private String batchStatus;

    @ColumnInfo(name = "ScanData")
    private String scanData;

    public StockCountLine(String profileName, String location, String itemType, String batchNo, Float qty, String scanTime, String scanDate, String batchStatus, String scanData) {
        this.profileName = profileName;
        this.location = location;
        this.itemType = itemType;
        this.batchNo = batchNo;
        this.qty = qty;
        this.scanTime = scanTime;
        this.scanDate = scanDate;
        this.batchStatus = batchStatus;
        this.scanData = scanData;
    }

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public String getScanTime() {
        return scanTime;
    }

    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getScanData() {
        return scanData;
    }

    public void setScanData(String scanData) {
        this.scanData = scanData;
    }

}
