package com.example.jihyun_myorder.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Order.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    // to create only one obj, using multiple thread
    private static volatile AppDB db;
    private static final int NUMBER_OF_THREAD = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD) ;

    public abstract OrderDAO orderDAO();

    public static AppDB getDatabase(final Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "db_order")
                    .fallbackToDestructiveMigration()
            .build();
        }
        return db;
    }

}
