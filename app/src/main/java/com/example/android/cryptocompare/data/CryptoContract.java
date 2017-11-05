package com.example.android.cryptocompare.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by SOLARIN O. OLUBAYODE on 31/10/17.
 */

public class CryptoContract  {
    private CryptoContract(){}

    public static final String CRYPTO_PATH = "crypto";

    public static final String BASE_CONTENT_URI = "content://com.example.android.crypto";

    public static final String CONTENT_AUTHORITY = "com.example.android.crypto";

    public static final class CryptoEntry implements BaseColumns{

        public static final String TABLE_NAME = "crypto";

        public static final String STRING_ID = BaseColumns._ID;

        public static final String CRYPTO_CURRENCY_COLUMN = "crypto";

        public static final String BASE_CURRENCY_COLUMN ="base";

        public static final String CURRENCY_PRICE_COLUMN ="base_price";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(Uri.parse(BASE_CONTENT_URI), CRYPTO_PATH);


        public static final int BASE_CURRENCY_NAIRA = 1;
        public static final int BASE_CURRENCY_USD = 2;
        public static final int BASE_CURRENCY_RUPEE = 3;
        public static final int BASE_CURRENCY_SINGAPORE = 4;
        public static final int BASE_CURRENCY_TAIWAN = 5;
        public static final int BASE_CURRENCY_AUD = 6;
        public static final int BASE_CURRENCY_EURO = 7;
        public static final int BASE_CURRENCY_GBP = 8;
        public static final int BASE_CURRENCY_RAND = 9;
        public static final int BASE_CURRENCY_MEXICO = 10;
        public static final int BASE_CURRENCY_ISREAL = 11;
        public static final int BASE_CURRENCY_NZD = 12;
        public static final int BASE_CURRENCY_SWEDEN = 13;
        public static final int BASE_CURRENCY_NORWAY = 14;
        public static final int BASE_CURRENCY_FRANC = 15;
        public static final int BASE_CURRENCY_SAUDI = 16;
        public static final int BASE_CURRENCY_DUBAI = 17;
        public static final int BASE_CURRENCY_PHILIPPIANS = 18;
        public static final int BASE_CURRENCY_GUATEMALA = 19;
        public static final int BASE_CURRENCY_GHANA = 20;
        public static final int UNKNOWN = 00;
        public static final int CRYPTO_CURRENCY_BTC = 1;
        public static final int CRYPTO_CURRENCY_ETH = 2;



    }
}
