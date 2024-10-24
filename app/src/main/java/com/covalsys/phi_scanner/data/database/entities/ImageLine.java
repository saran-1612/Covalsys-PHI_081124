package com.covalsys.phi_scanner.data.database.entities;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ImageLine {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "ImagePath")
    public  Bitmap imagePath;

    @ColumnInfo(name = "Remarks")
    public String remark;


    public ImageLine(Bitmap imagePath, String remark) {
        this.imagePath = imagePath;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  Bitmap getImagePath() {
        return imagePath;
    }

    public void setImagePath( Bitmap imagePath) {
        this.imagePath = imagePath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
