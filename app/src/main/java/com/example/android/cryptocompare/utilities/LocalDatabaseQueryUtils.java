package com.example.android.cryptocompare.utilities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.android.cryptocompare.model.Crypto;
import com.example.android.cryptocompare.data.CryptoContract.CryptoEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOLARIN O. OLUBAYODE on 29/01/19.
 */

public class LocalDatabaseQueryUtils {

    private static final String LOG_TAG = LocalDatabaseQueryUtils.class.getSimpleName();
    private Context mContext;

    public LocalDatabaseQueryUtils(Context context) {
        mContext = context;
    }

    /*the getCrypto() method returns a List<Crypto>
    * we call an object instance of the list class
    * after we have queried for the entire Table in the dataBase and saved it as a Cursor object
    @cursor,we get each column index,we move then get the the values at each index in each row.
    * each value gotten at each index is then passed into the Crypto Object @cryptoObject
    * the Crypto Object is then added to the List
    * and returned as the expected return type of the getCrypto method*/
        public List<Crypto> findAllCrypto() {

            List<Crypto> mCryptoList = new ArrayList<>();
            String[] projection = new String[]{CryptoEntry.STRING_ID, CryptoEntry.CRYPTO_CURRENCY_COLUMN,
                    CryptoEntry.CURRENCY_PRICE_COLUMN, CryptoEntry.LOCAL_CURRENCY_COLUMN};
            Cursor cursor = mContext.getContentResolver().query(CryptoEntry.CONTENT_URI, projection, null, null, null);

            Log.i(LOG_TAG.toUpperCase(), "===> CURSOR" + cursor);

            int length = 0;
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(CryptoEntry._ID));
                String cryptoCurrencyType = cursor.getString(cursor.getColumnIndexOrThrow(CryptoEntry.CRYPTO_CURRENCY_COLUMN));
                String baseCurrencyName = cursor.getString(cursor.getColumnIndexOrThrow(CryptoEntry.LOCAL_CURRENCY_COLUMN));
                double baseValue = cursor.getDouble(cursor.getColumnIndexOrThrow(CryptoEntry.CURRENCY_PRICE_COLUMN));

                Crypto cryptoObject = new Crypto(id, cryptoCurrencyType, baseCurrencyName, baseValue);
                mCryptoList.add(cryptoObject);
                Log.i(LOG_TAG.toUpperCase(), "===> CURSOR SIZE" + length++);

            }
            cursor.close();

            return  mCryptoList;
        }

        public void upAllCrypto(ContentValues[] cryptoContentValues){

            ContentResolver cryptoContentResolver = mContext.getContentResolver();

            /* Insert our new weather data into CryptoProvider's ContentProvider */
            cryptoContentResolver.bulkInsert(
                    CryptoEntry.CONTENT_URI,
                    cryptoContentValues);
        }

}
