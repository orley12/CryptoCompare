//package com.example.android.cryptocompare.ui;
//
////import android.content.AsyncTaskLoader;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.support.v4.content.AsyncTaskLoader;
//import android.util.Log;
//
//import com.example.android.cryptocompare.QueryUtils;
//import com.example.android.cryptocompare.data.CryptoContract.CryptoEntry;
//import com.example.android.cryptocompare.model.Crypto;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by SOLARIN O. OLUBAYODE on 02/11/17.
// */
//
//public class CryptoLoader extends AsyncTaskLoader<List<Crypto>> {
//
//    public static final String LOG_TAG = CryptoList.class.getName();
//
//    private String mUrl;
//    private Context mContext;
//
//    public CryptoLoader(Context context,String url) {
//        /*the superClass (AsyncTaskLoader's) constructor takes in context as its param*/
//        super(context);
//        mContext = context;
//        mUrl = url;
//    }
//
//    @Override
//    /*the onStartLoading is automatically triggered by the LoaderManager's init() method one to
//    * initiate the Loader*/
//    protected void onStartLoading() {
//        /*the forceLoad() method is usually called within the onStartLoading() */
//        forceLoad();
//        Log.i(LOG_TAG, "This activates the loader manager to start operation.");
//    }
//
//    @Override
//    /*the loadInBackground() method performs the task of retrieving data from the USGS servers in
//    the background thread then the data is Loaded to the onCreateLoader() method */
//    public List<Crypto> loadInBackground() {
//
//        // Perform the HTTP request for earthquake data and process the response.
////        List<Crypto> result = QueryUtils.fetchCryptoData(mUrl);
//        Log.i(LOG_TAG, "The loadInBackground perform all task relating to retrieving data through " +
//                "the API in the background thread before the data is sent to the loader.");
//
//        return getCryptoList();
//
//    }
//
//
//    private List<Crypto> getCryptoList(){
//
//        Map<Integer, Map<Integer, Double>> rateMap = QueryUtils.fetchCryptoData(mUrl);
//
//        List<Crypto> cryptos = getCrypto();
////        List<Crypto>  result = new ArrayList<>();
//
//        if(rateMap == null){
//            return null;
//        }else {
//
//            return getRate(cryptos, rateMap);
//        }
//    }
//
//
//    private List<Crypto> getCrypto() {
//
//        List<Crypto> mCryptoList = new ArrayList<>();
//        String[] projection = new String[]{CryptoEntry.STRING_ID, CryptoEntry.CRYPTO_CURRENCY_COLUMN,
//                CryptoEntry.CURRENCY_PRICE_COLUMN, CryptoEntry.BASE_CURRENCY_COLUMN};
//        Cursor cursor = mContext.getContentResolver().query(CryptoEntry.CONTENT_URI, projection, null, null, null);
//
//        Log.i(LOG_TAG, "moved to first" + cursor);
//        int columnIndexId = cursor.getColumnIndexOrThrow(CryptoEntry._ID);
//        int columnIndexCrypto = cursor.getColumnIndexOrThrow(CryptoEntry.CRYPTO_CURRENCY_COLUMN);
//        int columnIndexBase = cursor.getColumnIndexOrThrow(CryptoEntry.BASE_CURRENCY_COLUMN);
//        int columnIndexBasePrice = cursor.getColumnIndexOrThrow(CryptoEntry.CURRENCY_PRICE_COLUMN);
//
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(columnIndexId);
//            int crypto = cursor.getInt(columnIndexCrypto);
//            int base = cursor.getInt(columnIndexBase);
//            int basePrice = cursor.getInt(columnIndexBasePrice);
//
//
//            Crypto cryptoObject = new Crypto(id, crypto, base, basePrice);
//            mCryptoList.add(cryptoObject);
//            Log.i(LOG_TAG, "added new crypto object" + cursor);
//        }
//
//        cursor.close();
//
//        return  mCryptoList;
//    }
//
//
//    private List<Crypto> getRate(List<Crypto> rateless, Map<Integer, Map<Integer, Double>> rateDict){
//
//        List<Crypto> listWithRate = new ArrayList<>();
//
//        for(Crypto c : rateless){
//
//            Map<Integer, Double> coinMap = rateDict.get(c.getCryptoCurrency());
//
//            double rate = coinMap.get(c.getBaseCurrency());
//
//            c.setCurrencyPrice(rate);
//
//            listWithRate.add(c);
//
//        }
//
//
//
//        return  listWithRate;
//    }
//
//}
