//package com.example.android.cryptocompare.data;
//
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.Room;
//import android.arch.persistence.room.RoomDatabase;
//import android.content.Context;
//import android.util.Log;
//
//import com.example.android.cryptocompare.model.Crypto;
//
///**
// * Created by SOLARIN O. OLUBAYODE on 02/02/19.
// */
//
//@Database(entities = {Crypto.class}, version = 1, exportSchema = false)
//public abstract class AppDataBase extends RoomDatabase {
//
//    private static final String DATABASE_NAME = "cryptocompare";
//    private static final String LOG_TAG = AppDataBase.class.getSimpleName();
//    private static AppDataBase sInstance;
//    private static Object LOCK = new Object();
//
//    public static AppDataBase getInstance(Context context){
//        if (sInstance == null){
//            synchronized (LOCK){
//                Log.d(LOG_TAG, "Creating a new database instance");
//                sInstance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,
//                        AppDataBase.DATABASE_NAME)
//                        .allowMainThreadQueries()
//                        .build();
//            }
//        }
//        Log.d(LOG_TAG, "Getting the database instance");
//        return sInstance;
//    }
//
//    public abstract CryptoDao cryptoDao();
//
//}
