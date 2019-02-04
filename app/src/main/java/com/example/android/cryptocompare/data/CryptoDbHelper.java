package com.example.android.cryptocompare.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.cryptocompare.data.CryptoContract.CryptoEntry;

/**
 * Created by SOLARIN O. OLUBAYODE on 31/10/17.
 */

public class CryptoDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="crypto.db";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS" + CryptoEntry.TABLE_NAME;

    public CryptoDbHelper(Context context) {
        super(context, DATABASE_NAME,null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + CryptoEntry.TABLE_NAME + "( " +
                CryptoEntry.STRING_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                CryptoEntry.CURRENCY_PRICE_COLUMN +" REAL DEFAULT 0.0," +
                CryptoEntry.CRYPTO_CURRENCY_COLUMN +" TEXT," +
                CryptoEntry.CURRENCY_TAG +" TEXT," +
                CryptoEntry.LOCAL_CURRENCY_COLUMN +" TEXT," +
                " UNIQUE (" + CryptoEntry.CURRENCY_TAG + ") ON CONFLICT REPLACE);";
        ;

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
