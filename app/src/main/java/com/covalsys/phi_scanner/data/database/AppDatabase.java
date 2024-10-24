package com.covalsys.phi_scanner.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.covalsys.phi_scanner.data.database.dao.DeliveryHeaderDao;
import com.covalsys.phi_scanner.data.database.dao.DeliveryLineDao;
import com.covalsys.phi_scanner.data.database.dao.DeliveryScanLineDao;
import com.covalsys.phi_scanner.data.database.dao.ImageDao;
import com.covalsys.phi_scanner.data.database.dao.StockCountHeaderDao;
import com.covalsys.phi_scanner.data.database.dao.StockCountLineDao;
import com.covalsys.phi_scanner.data.database.dao.StockCountScanLineDao;
import com.covalsys.phi_scanner.data.database.entities.DeliveryHeader;
import com.covalsys.phi_scanner.data.database.entities.DeliveryLine;
import com.covalsys.phi_scanner.data.database.entities.DeliveryScanLine;
import com.covalsys.phi_scanner.data.database.entities.ImageLine;
import com.covalsys.phi_scanner.data.database.entities.StockCountHeader;
import com.covalsys.phi_scanner.data.database.entities.StockCountLine;
import com.covalsys.phi_scanner.data.database.entities.StockCountScanLine;
import com.covalsys.phi_scanner.utils.rx.Converters;

@Database(entities = {DeliveryLine.class, DeliveryScanLine.class, DeliveryHeader.class, StockCountLine.class,
        StockCountScanLine.class, StockCountHeader.class, ImageLine.class}, version = 6, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "DB-PHI-Scanner";
    private static AppDatabase mInstance;

    public synchronized static AppDatabase getDatabaseInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

    public abstract DeliveryLineDao deliveryLineDao();

    public abstract DeliveryScanLineDao deliveryScanLineDao();

    public abstract DeliveryHeaderDao deliveryHeaderDao();

    public abstract StockCountLineDao stockCountLineDao();

    public abstract StockCountScanLineDao stockCountScanLineDao();

    public abstract StockCountHeaderDao stockCountHeaderDao();

    public abstract ImageDao imageDao();

}
