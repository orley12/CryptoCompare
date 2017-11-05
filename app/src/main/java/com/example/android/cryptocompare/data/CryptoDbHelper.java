package com.example.android.cryptocompare.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SOLARIN O. OLUBAYODE on 31/10/17.
 */

public class CryptoDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="crypto.db";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS" + CryptoContract.CryptoEntry.TABLE_NAME;

    public CryptoDbHelper(Context context) {
        super(context, DATABASE_NAME,null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + CryptoContract.CryptoEntry.TABLE_NAME + "( " +
                CryptoContract.CryptoEntry.STRING_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                CryptoContract.CryptoEntry.CRYPTO_CURRENCY_COLUMN +" INTEGER," +
                CryptoContract.CryptoEntry.BASE_CURRENCY_COLUMN +" INTEGER," +
                CryptoContract.CryptoEntry.CURRENCY_PRICE_COLUMN +" INTEGER)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
