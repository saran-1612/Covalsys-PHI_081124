package com.covalsys.phi_scanner.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.phi_scanner.data.database.entities.StockCountHeader;

import java.util.List;

@Dao
public interface StockCountHeaderDao {

    @Query("SELECT * FROM StockCountHeader Order by slNo DESC")
    LiveData<List<StockCountHeader>> getData();

    @Insert(onConflict = REPLACE)
    void insertStackCountHeader(StockCountHeader stockCountHeader);

    @Insert
    void add(StockCountHeader... stockCountHeaders);

    @Query("DELETE FROM StockCountHeader")
    void deleteAllData();
}



