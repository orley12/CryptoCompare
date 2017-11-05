package com.example.android.cryptocompare.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by SOLARIN O. OLUBAYODE on 31/10/17.
 */

public class CryptoProvider extends ContentProvider {

    public static final String LOG_TAG = CryptoProvider.class.getName();

    private CryptoDbHelper mCryptoDbHelper;

    public static final int CRYPTO_ALL = 10000;
    public static final int CRYPTO_ID = 10001;

    public static final UriMatcher sUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUrimatcher.addURI(CryptoContract.CONTENT_AUTHORITY, CryptoContract.CRYPTO_PATH, CRYPTO_ALL);
        sUrimatcher.addURI(CryptoContract.CONTENT_AUTHORITY, CryptoContract.CRYPTO_PATH + "/#",
                CRYPTO_ID);
    }


    @Override
    public boolean onCreate() {
        mCryptoDbHelper = new CryptoDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mCryptoDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUrimatcher.match(uri);
        switch (match) {
            case CRYPTO_ALL:
                cursor = database.query(CryptoContract.CryptoEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case CRYPTO_ID:
                selection = CryptoContract.CryptoEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(CryptoContract.CryptoEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("cannot query unknown Uri" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType( Uri uri) {
        return null;
    }

    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        final int match = sUrimatcher.match(uri);
        switch (match) {
            case CRYPTO_ALL:
                return insertCrypto(uri, values);
            default:
                throw new IllegalArgumentException("insertion is not supported for" + uri);
        }
    }

    private Uri insertCrypto(Uri uri, ContentValues values) {
        SQLiteDatabase db = mCryptoDbHelper.getWritableDatabase();

        long id = db.insert(CryptoContract.CryptoEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for" + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }
    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mCryptoDbHelper.getWritableDatabase();
        int cursor;
        int match = sUrimatcher.match(uri);
        switch (match) {
            case CRYPTO_ALL:
                cursor = database.delete(CryptoContract.CryptoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CRYPTO_ID:
                selection = CryptoContract.CryptoEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.delete(CryptoContract.CryptoEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("cannot query unknown Uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return cursor;
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
