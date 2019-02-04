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

        public static final String LOCAL_CURRENCY_COLUMN ="base";

        public static final String CURRENCY_PRICE_COLUMN ="base_price";

        public static final String CURRENCY_TAG ="tag";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(Uri.parse(BASE_CONTENT_URI), CRYPTO_PATH);

    }
}
