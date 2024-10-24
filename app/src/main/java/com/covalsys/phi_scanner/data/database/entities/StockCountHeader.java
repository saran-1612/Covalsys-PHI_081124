package com.covalsys.phi_scanner.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StockCountHeader {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "slNo")
    private Integer slNo;

    @ColumnInfo(name = "OpeningQty")
    private float openingQty;

    @ColumnInfo(name = "ClosingQty")
    private float closingQty;

    @ColumnInfo(name = "DocNum")
    private String docNum;

    @ColumnInfo(name = "Profile")
    private String profile;

    public StockCountHeader(String profile, String docNum, float closingQty, float openingQty) {
        this.openingQty = openingQty;
        this.closingQty = closingQty;
        this.docNum = docNum;
        this.profile = profile;
    }

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfileName(String profile) {
        this.profile = profile;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public float getClosingQty() {
        return closingQty;
    }

    public void setClosingQty(float closingQty) {
        this.closingQty = closingQty;
    }

    public float getOpeningQty() {
        return openingQty;
    }

    public void setOpeningQty(float openingQty) {
        this.openingQty = openingQty;
    }

}
