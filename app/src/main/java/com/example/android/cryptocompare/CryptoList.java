package com.example.android.cryptocompare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cryptocompare.data.CryptoContract;
import com.example.android.cryptocompare.data.CryptoContract.CryptoEntry;
import com.example.android.cryptocompare.data.CryptoDbHelper;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.content.Loader;

public class CryptoList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Crypto>> {

    public static final String LOG_TAG = CryptoList.class.getName();


    public int mCryptoCurrency;



    public int mBaseCurrency;





    private GridLayoutManager mGridLayoutManager;


    List<Crypto> mCryptoList;

    CryptoAdapter cryptoAdapter;

    ProgressBar mProgressBar;

    private SwipeRefreshLayout swipeRefreshLayout;

    Context context = this;
    TextView noInternetTextView;

    private static final String CRYPTOCOMPARE_REQUEST_URL =
            "https://min-api.cryptocompare.com/data/pricemulti?fsyms=ETH,BTC&tsyms=NGN,USD,INR,SGD,TWD,AUD,EUR,GBP,ZAR,MXN,ILS,NZD,SEK,NOK,CHF,SAR,AED,PHP,GTQ,GHS";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_list);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_view);


        noInternetTextView = (TextView) findViewById(R.id.details_text);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if(getCrypto() > 0) {

            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null &&
                    networkInfo.isConnected()) {

                getSupportLoaderManager().initLoader(1, null, this);

            } else {
                noInternetTextView.setText("No Internet Connection");
                mProgressBar.setVisibility(View.GONE);
            }
        }else{
            noInternetTextView.setText("No card saved");
            mProgressBar.setVisibility(View.GONE);
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Crypto crypto = cryptoAdapter.getCrypto(position);

                        Intent intent = new Intent(CryptoList.this, CryptoConvert.class);

                        Bundle bundle = new Bundle();
                        bundle.putInt("cryptoCurrency", crypto.getCryptoCurrency());
                        bundle.putInt("baseCurrency", crypto.getBaseCurrency());
                        bundle.putDouble("priceCurrency", crypto.getCurrencyPrice());

                        intent.putExtras(bundle);

                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, final int position) {
                        // do whatever

                        final Crypto cryptoObject = cryptoAdapter.getCrypto(position);

                        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle("Delete")
                                .setMessage("Delete card?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        cryptoAdapter.remove(cryptoObject);
                                        cryptoAdapter.notifyItemRemoved(position);


                                        deleteCrypto(cryptoObject);
                                        refresh();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_menu_delete)
                                .show();
                    }
                })
        );



        mGridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(mGridLayoutManager);


        mCryptoList = new ArrayList<Crypto>();



        cryptoAdapter = new CryptoAdapter(mCryptoList);

        recyclerView.setAdapter(cryptoAdapter);

        final Activity context = this;

        View fab = (View) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mDialog = new AlertDialog.Builder(context);

                LayoutInflater li = context.getLayoutInflater();
                final View dialogView = li.inflate(R.layout.crypto_option, null);
                mDialog.setView(dialogView)
                        .setTitle("Select Currencies");



                Spinner cryptoSpinner = (Spinner) dialogView.findViewById(R.id.spinner_crypto);

                ArrayAdapter cryptoSpinnerAdapter = ArrayAdapter.createFromResource(context,
                        R.array.array_crypto_options, android.R.layout.simple_spinner_item);

                cryptoSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                cryptoSpinner.setAdapter(cryptoSpinnerAdapter);

                cryptoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selection)) {
                            if (selection.equals(getString(R.string.btc))) {
                                mCryptoCurrency = CryptoContract.CryptoEntry.CRYPTO_CURRENCY_BTC;
                            } else if (selection.equals(getString(R.string.eth))) {
                                mCryptoCurrency = CryptoContract.CryptoEntry.CRYPTO_CURRENCY_ETH;
                            } else {
                                mCryptoCurrency = CryptoContract.CryptoEntry.UNKNOWN;
                            }
                        }
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        mCryptoCurrency = CryptoContract.CryptoEntry.UNKNOWN; // Unknown
                    }
                });


                ArrayAdapter baseSpinnerAdapter = ArrayAdapter.createFromResource(context,
                        R.array.array_base_options, android.R.layout.simple_spinner_item);

                // Specify dropdown layout style - simple list view with 1 item per line
        /*here
        *we also call the ArrayAdapter here and call the setDropDownViewResource() method on the
         ArrayAdapter(genderSpinnerAdapter), we pass in the param of the setDropDownViewResource()
         method which is a defult layout Resource*/
                baseSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                Spinner baseSpinner = (Spinner) dialogView.findViewById(R.id.spinner_base);

                baseSpinner.setAdapter(baseSpinnerAdapter);

                baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selection)) {
                            if (selection.equals(getString(R.string.naira))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_NAIRA;
                            } else if (selection.equals(getString(R.string.usd))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_USD;
                            } else if (selection.equals(getString(R.string.rupee))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_RUPEE;
                            } else if (selection.equals(getString(R.string.sgd))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_SINGAPORE;
                            } else if (selection.equals(getString(R.string.twd))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_TAIWAN;
                            } else if (selection.equals(getString(R.string.aud))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_AUD;
                            } else if (selection.equals(getString(R.string.euro))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_EURO;
                            } else if (selection.equals(getString(R.string.gbp))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_GBP;
                            } else if (selection.equals(getString(R.string.rand))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_RAND;
                            } else if (selection.equals(getString(R.string.peso))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_MEXICO;
                            } else if (selection.equals(getString(R.string.shekel))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_ISREAL;
                            } else if (selection.equals(getString(R.string.nzd))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_NZD;
                            } else if (selection.equals(getString(R.string.korna))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_SWEDEN;
                            } else if (selection.equals(getString(R.string.korne))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_NORWAY;
                            } else if (selection.equals(getString(R.string.franc))) {
                                mBaseCurrency = CryptoContract.CryptoEntry.BASE_CURRENCY_FRANC;
                            } else if (selection.equals(getString(R.string.sar))) {
                                mBaseCurrency = CryptoEntry.BASE_CURRENCY_SAUDI;
                            } else if (selection.equals(getString(R.string.aed))) {
                                mBaseCurrency = CryptoEntry.BASE_CURRENCY_DUBAI;
                            } else if (selection.equals(getString(R.string.php))) {
                                mBaseCurrency = CryptoEntry.BASE_CURRENCY_PHILIPPIANS;
                            } else if (selection.equals(getString(R.string.gtq))) {
                                mBaseCurrency = CryptoEntry.BASE_CURRENCY_GUATEMALA;
                            } else if (selection.equals(getString(R.string.ghs))) {
                                mBaseCurrency = CryptoEntry.BASE_CURRENCY_GHANA;
                            } else {
                                mBaseCurrency = CryptoContract.CryptoEntry.UNKNOWN;
                            }
                        }
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        mBaseCurrency = CryptoContract.CryptoEntry.UNKNOWN;
                    }
                });

                mDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insertCrypto();
                    }
                })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                mDialog.show();
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.delete_all:
                deleteAllCrypto();
                break;
//                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void refresh(){
        getSupportLoaderManager().restartLoader(1, null, this);
    }

    private void insertCrypto() {

        /*here we create a ContentValues object instance with name Values,and call the put method of
        on the Content values and pass in the column name and the string we got from the Edit text
        field
         *we getContentResolver() method to get the contentResolver which resolves the
         ContentProvider to handle the Uri,and when the CRUD methods of the contentResolver are
         , called on the contentResolver inturn calls the CRUD methods of the appropriate provider which
         also inturn calls the CRUD methods on the dataBase
         *the insert method which is called on the contentResolver,this takes in  params the Uri and
          ContentValues which we save in a Uri dataType we name newUri
         *finally if uri == null we set toast else we set different toast message*/


        ContentValues values = new ContentValues();
        values.put(CryptoContract.CryptoEntry.CRYPTO_CURRENCY_COLUMN, mCryptoCurrency);
        values.put(CryptoContract.CryptoEntry.BASE_CURRENCY_COLUMN, mBaseCurrency);


        Uri newUri = getContentResolver().insert(CryptoContract.CryptoEntry.CONTENT_URI, values);
        Log.i(LOG_TAG, "inserted " + newUri);

        if (newUri == null) {
            Toast.makeText(this, "Failed To Insert Card", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Card Inserted", Toast.LENGTH_SHORT).show();
        }

            getSupportLoaderManager().restartLoader(1, null, this);
    }

    public int deleteAllCrypto() {

        int deleteResult = getContentResolver().delete(CryptoEntry.CONTENT_URI, null, null);
        cryptoAdapter.notifyDataSetChanged();

        return deleteResult;
    }

    public void deleteCrypto(Crypto crypto){
        String selection = CryptoEntry.BASE_CURRENCY_COLUMN +"=? and "+ CryptoEntry.CRYPTO_CURRENCY_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(crypto.getBaseCurrency()), String.valueOf(crypto.getCryptoCurrency())};

        int result = getContentResolver().delete(CryptoEntry.CONTENT_URI, selection, selectionArgs);



    }

    private int getCrypto() {

        String[] projection = new String[]{CryptoEntry.STRING_ID, CryptoEntry.CRYPTO_CURRENCY_COLUMN,
                CryptoEntry.CURRENCY_PRICE_COLUMN, CryptoEntry.BASE_CURRENCY_COLUMN};
        Cursor cursor = getContentResolver().query(CryptoEntry.CONTENT_URI, projection, null, null, null);

        int number = 0;

try {
    while (cursor.moveToNext()) {

        number++;

        Log.i(LOG_TAG, "added new crypto object" + number);
    }
}catch (NullPointerException ex){
    Log.e(LOG_TAG, ex.getMessage());
}


        if(cursor != null)
        cursor.close();

        return number;
    }

    @Override
    public Loader<List<Crypto>> onCreateLoader(int id, Bundle args) {
        mProgressBar.setVisibility(View.VISIBLE);
        return new CryptoLoader(this, CRYPTOCOMPARE_REQUEST_URL );
    }

    @Override
    public void onLoadFinished(Loader<List<Crypto>> loader, List<Crypto> data) {

        if(data != null) {
            cryptoAdapter.clear();
            cryptoAdapter.addAll(data);
            mProgressBar.setVisibility(View.GONE);
            noInternetTextView.setText("");

        }else{
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Cant load feed", Toast.LENGTH_SHORT).show();
        }

        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onLoaderReset(Loader<List<Crypto>> loader) {
            cryptoAdapter.clear();
    }



}

