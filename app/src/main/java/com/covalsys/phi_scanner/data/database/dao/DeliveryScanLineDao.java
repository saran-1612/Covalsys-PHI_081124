package com.covalsys.phi_scanner.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.phi_scanner.data.database.entities.DeliveryScanLine;

import java.util.List;

/**
 * Created by Prasanth on 23-10-2023.
 */
@Dao
public interface DeliveryScanLineDao {

    @Query("SELECT * FROM DeliveryScanLine")
    LiveData<List<DeliveryScanLine>> getData();

    @Query("SELECT * FROM DeliveryScanLine")
    List<DeliveryScanLine> getDataList();

    @Query("SELECT A.* FROM DeliveryScanLine A inner join DeliveryLine B ON A.BatchNo = B.BatchNo")
    List<DeliveryScanLine> getDataMatchList();

    @Query("SELECT * FROM DeliveryScanLine where Status != 'Y'")
    List<DeliveryScanLine> getDataNotList();

    @Query("SELECT count(*) FROM DeliveryScanLine")
    int getDataSize();

    @Query("SELECT sum(Quantity) FROM DeliveryScanLine")
    Float getLoaded();

    @Query("SELECT EXISTS (SELECT 1 FROM DeliveryScanLine WHERE BatchNo =:id)")
    int isAddToCart(String id);

    @Query("UPDATE DeliveryScanLine set Status = 'Y' where slno =:slno")
    void updateLineStatus(int slno);
    @Insert
    void add(DeliveryScanLine... deliveryScanLines);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<DeliveryScanLine> deliveryScanLines);

    @Query("DELETE FROM DeliveryScanLine")
    void deleteAllData();

    @Query("DELETE FROM DeliveryScanLine where slno =:slno")
    void deleteData(int slno);
}



