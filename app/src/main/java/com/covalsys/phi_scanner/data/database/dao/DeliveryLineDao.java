package com.covalsys.phi_scanner.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.phi_scanner.data.database.entities.DeliveryLine;

import java.util.List;

@Dao
public interface DeliveryLineDao {

    @Query("SELECT * FROM DeliveryLine")
    LiveData<List<DeliveryLine>> getData();

    @Query("SELECT * FROM DeliveryLine")
    List<DeliveryLine> getDataList();

    @Query("SELECT count(*) FROM DeliveryLine")
    int getDataSize();

    @Query("SELECT slno FROM DeliveryLine where slno =:lineNo ")
    int getLineNo(int lineNo);

    @Query("SELECT count(*) FROM DeliveryLine")
    int dataCount();

    @Query("SELECT sum(Quantity) FROM DeliveryLine")
    Float getActualData();

    @Query("SELECT sum(ifnull(BarCodeQty, 0)) FROM DeliveryLine")
    Float getLoaded();

    @Query("SELECT count(*) FROM DeliveryLine where DocNum =:docNum")
    int dataCount(String docNum);

    @Query("SELECT COUNT(slno) from DeliveryLine where slno = (SELECT max(slno) FROM DeliveryScanLine)")
    int tableExistNo();

    @Query("UPDATE DeliveryLine set BatchStatus = 'N', BarCodeBatch = :barCode, BarCodeQty = :barCodeQty  where ItemCode = :itemCode AND BatchNo = :batch")
    void updateBatchInfo(String barCode, String barCodeQty, String itemCode, String batch);

    @Query("UPDATE DeliveryLine set BatchStatus = 'N', BarCodeBatch = :barCode, BarCodeQty = :barCodeQty  where slno =:lineNo")
    void updateBatchInfo(int lineNo, String barCode, Float barCodeQty);

    @Query("UPDATE DeliveryLine set BatchStatus = '', BarCodeBatch = '', BarCodeQty = 0  where slno =:lineNo")
    void updateBatchInfo(int lineNo);

    @Insert
    void add(DeliveryLine... deliveryLines);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<DeliveryLine> deliveryMasters);

    @Query("DELETE FROM DeliveryLine")
    void deleteAllData();

    @Query("SELECT * FROM DeliveryLine where BatchStatus != 'Y'")
    List<DeliveryLine> getDataNotList();

    @Query("UPDATE DeliveryLine set BatchStatus = 'Y', BarCodeBatch =:batchNo, BarCodeQty =:quantity  where BatchNo =:batchNo")
    void updateBatchMatchInfo(String batchNo, Float quantity);
}



