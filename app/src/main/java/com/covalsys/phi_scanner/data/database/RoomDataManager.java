package com.covalsys.phi_scanner.data.database;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Prasanth on 20-12-2021.
 */
@Singleton
public class RoomDataManager implements RoomHelper{

    private AppDatabase mDatabase;

    @Inject
    public RoomDataManager(AppDatabase appDatabase) {
        this.mDatabase = appDatabase;
    }

}
