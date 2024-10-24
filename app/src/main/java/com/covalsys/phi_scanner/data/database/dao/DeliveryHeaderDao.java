package com.covalsys.phi_scanner.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.phi_scanner.data.database.entities.DeliveryHeader;

import java.util.List;

@Dao
public interface DeliveryHeaderDao {

    @Query("SELECT * FROM DeliveryHeader Order by slNo DESC")
    LiveData<List<DeliveryHeader>> getData();

    @Query("SELECT * FROM DeliveryHeader")
    DeliveryHeader getDocument();

    @Insert(onConflict = REPLACE)
    void insertDeliveryHeader(DeliveryHeader deliveryHeader);

    @Insert
    void add(DeliveryHeader... deliveryHeaders);

    @Query("DELETE FROM DeliveryHeader")
    void deleteAllData();

}



