package com.covalsys.phi_scanner.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DeliveryHeader {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "slNo")
    private Integer slNo;

    @ColumnInfo(name = "DocNum")
    private String docNum;

    @ColumnInfo(name = "DocEntry")
    private String docEntry;

    @ColumnInfo(name = "CusCode")
    private String cusCode;

    @ColumnInfo(name = "CusName")
    private String cusName;

    @ColumnInfo(name = "DocType")
    private String docType;

    @ColumnInfo(name = "DocStatus")
    private String docStatus;

    @ColumnInfo(name = "AppStatus")
    private String appStatus;

    public DeliveryHeader(String docEntry, String docNum, String cusCode, String cusName, String docType, String docStatus, String appStatus) {
        this.docNum = docNum;
        this.docEntry = docEntry;
        this.cusCode = cusCode;
        this.cusName = cusName;
        this.docType = docType;
        this.docStatus = docStatus;
        this.appStatus = appStatus;
    }

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public String getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(String docEntry) {
        this.docEntry = docEntry;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getCusCode() {
        return cusCode;
    }

    public void setCusCode(String cusCode) {
        this.cusCode = cusCode;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }
}
