package com.covalsys.phi_scanner.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.phi_scanner.data.database.entities.ImageLine;

import java.util.List;

@Dao
public interface ImageDao {
    @Insert
    void insert(ImageLine imageRemark);

    @Query("Select * from ImageLine")
    List<ImageLine> getAllDetails();

    @Query("Delete from ImageLine where id=:taskID")
    void delete(Integer taskID);

    @Query("Delete From ImageLine")
    Void deleteMethod();
}
