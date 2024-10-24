package com.covalsys.phi_scanner.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.covalsys.phi_scanner.data.database.entities.StockCountLine;

import java.util.List;

@Dao
public interface StockCountLineDao {

    @Query("SELECT * FROM StockCountLine")
    LiveData<List<StockCountLine>> getData();

    @Query("SELECT * FROM StockCountLine")
    List<StockCountLine> getDataList();

    @Query("SELECT count(*) FROM StockCountLine")
    int getDataSize();

    @Query("SELECT slno FROM StockCountLine where slno =:lineNo ")
    int getLineNo(int lineNo);

    @Query("SELECT count(*) FROM StockCountLine")
    int dataCount();

    @Query("SELECT sum(Quantity) FROM StockCountLine")
    Float getActualData();

    // StackCountLine
    @Insert(onConflict = REPLACE)
    void insertStackCountScan(StockCountLine stackCountLine);

    @Query("SELECT * FROM StockCountLine Order by slNo DESC ")
    LiveData<List<StockCountLine>> getStockLine();

    @Query("SELECT COUNT(slno) from StockCountLine where slno = (SELECT max(slno) FROM StockCountScanLine)")
    int tableExistNo();

    @Query("UPDATE StockCountLine set BatchStatus = ''where slno =:lineNo")
    void updateBatchInfo(int lineNo);

    @Insert
    void add(StockCountLine... stockCountLines);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateData(List<StockCountLine> stockCountLines);

    @Query("DELETE FROM StockCountLine")
    void deleteAllData();
}



