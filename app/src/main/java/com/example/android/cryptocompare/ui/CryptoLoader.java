package com.example.android.cryptocompare.ui;

//import android.content.AsyncTaskLoader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.cryptocompare.model.Crypto;
import com.example.android.cryptocompare.utilities.LocalDatabaseQueryUtils;
import com.example.android.cryptocompare.utilities.QueryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOLARIN O. OLUBAYODE on 02/11/17.
 */

/*The CryptoLoader class extends AsyncTaskLoader class, meaning it inherits all the features of
the AsyncTaskLoader with ability to override some of those features default implementation*/
public class CryptoLoader extends AsyncTaskLoader<List<List<Crypto>>> {

    public static final String LOG_TAG = CryptoList.class.getName();

    LocalDatabaseQueryUtils localDatabaseQueryUtils = new LocalDatabaseQueryUtils(getContext());

    /*global variables for url String and Context both passed in from CryptoList class*/
    private String mUrl;
    private Context mContext;

    /*The constructor of the CryptoLoader class,takes in a Context and String */
    public CryptoLoader(Context context,String url) {
        /*the superClass (AsyncTaskLoader's) constructor takes in context as its param*/
        super(context);
        /*we initialize the global variables mContext and mUrl with the arguments passed into the
         constructor accordingly */
        mContext = context;
        mUrl = url;
    }

    @Override
    /*the onStartLoading is automatically triggered by the LoaderManager's init() method one to
    * initiate the Loader*/
    protected void onStartLoading() {
        /*the forceLoad() method is usually called within the onStartLoading() */
        forceLoad();
        Log.i(LOG_TAG, "This activates the loader manager to start operation.");
    }

    @Override
    /*the loadInBackground() method performs the task of retrieving data from the CryptoCompare
    servers in the background thread then the data is Loaded to the onCreateLoader() method */
    public List<List<Crypto>> loadInBackground() {
        List<List<Crypto>> listOfListCrypto = new ArrayList<>();

        String jsonResponse = QueryUtils.fetchCryptoData(mUrl);

        List<Crypto> oldCryptoList = QueryUtils.extractFeatureFromJSON(jsonResponse, getContext());
        listOfListCrypto.add(oldCryptoList);
        List<Crypto> newCryptoList = localDatabaseQueryUtils.findAllCrypto();
        listOfListCrypto.add(newCryptoList);

        return listOfListCrypto;
    }

}
