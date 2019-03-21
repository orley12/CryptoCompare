package com.example.android.cryptocompare.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.android.cryptocompare.R;
import com.example.android.cryptocompare.data.AppDataBase;
import com.example.android.cryptocompare.data.CryptoViewModel;
import com.example.android.cryptocompare.model.Crypto;
import com.example.android.cryptocompare.utilities.AppExecutors;
import com.example.android.cryptocompare.utilities.QueryUtils;
import com.example.android.cryptocompare.utilities.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.content.Loader;

public class CryptoList extends AppCompatActivity {

    public static final String LOG_TAG = CryptoList.class.getName();

    public String mCryptoCurrencyName;

    public String mLocalCurrencyName;

    private GridLayoutManager mGridLayoutManager;

    List<Crypto> mCryptoList;

    CryptoAdapter mCryptoAdapter;

    LinearLayout mNoExchangeView;

    SwipeRefreshLayout.OnRefreshListener mRefreshListener;

    private SwipeRefreshLayout swipeRefreshLayout;

    Context context = this;

    AppDataBase mDb;

    private static final String CRYPTOCOMPARE_REQUEST_URL =
            "https://min-api.cryptocompare.com/data/pricemulti?fsyms=ETH,BTC&tsyms=NGN,USD,INR,SGD,TWD,AUD,EUR,GBP,ZAR,MXN,ILS,NZD,SEK,NOK,CHF,SAR,AED,PHP,GTQ,GHS";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_list);

        mDb = AppDataBase.getInstance(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_view);

        mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AppExecutors.getInstance().NetworkIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        QueryUtils.fetchCryptoData(CRYPTOCOMPARE_REQUEST_URL, CryptoList.this);
                    }
                });
            }
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mNoExchangeView = (LinearLayout) findViewById(R.id.no_exchange_view);

        updateUi();

        refresh();

        swipeRefreshLayout.setOnRefreshListener(mRefreshListener);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Crypto crypto = mCryptoAdapter.getCrypto(position);

                        Intent intent = new Intent(CryptoList.this, CryptoConvert.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("cryptoCurrencyName", crypto.getCryptoCurrencyName());
                        bundle.putString("localCurrencyName", crypto.getLocalCurrencyName());
                        bundle.putDouble("currencyValue", crypto.getCurrencyValue());

                        Log.i(LOG_TAG,"===> " + position);

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, final int position) {
                        // do whatever

                        final Crypto cryptoObject = mCryptoAdapter.getCrypto(position);

                        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle("Delete")
                                .setMessage("Delete card?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        mCryptoAdapter.remove(cryptoObject);
//                                        mCryptoAdapter.notifyItemRemoved(position);
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



        mCryptoAdapter = new CryptoAdapter(mCryptoList);

        recyclerView.setAdapter(mCryptoAdapter);

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
                        mCryptoCurrencyName = (String) parent.getItemAtPosition(position);
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
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
                        mLocalCurrencyName = (String) parent.getItemAtPosition(position);
                    }
                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
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
            }
        });
    }

    private void insertCrypto() {
        AppExecutors.getInstance().DiskIO().execute(new Runnable() {
            @Override
            public void run() {
                try{
                    mDb.cryptoDao().insertCrypto(new Crypto(mCryptoCurrencyName, mLocalCurrencyName,
                            0.0, mCryptoCurrencyName + mLocalCurrencyName));
                }catch (Exception e){
                    Log.e(LOG_TAG.toUpperCase(), "Insertion Error ====> " + e.getMessage());
                }
            }
        });
        refresh();
    }

    private void deleteCrypto(final Crypto crypto) {
        AppExecutors.getInstance().DiskIO().execute(new Runnable() {
            @Override
            public void run() {
                try{
                    mDb.cryptoDao().deleteCrypto(crypto);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        refresh();
    }

        public void deleteAllCrypto() {
        AppExecutors.getInstance().DiskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Crypto> cryptos = mDb.cryptoDao().LoadAllCrypto();
                try {
                    mDb.cryptoDao().deleteAllCrypto(cryptos);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
                mRefreshListener.onRefresh();
                updateUi();
            }
        });
    }

    private void updateUi() {
        CryptoViewModel cryptoViewModel = ViewModelProviders.of(this).get(CryptoViewModel.class);
        cryptoViewModel.getCryptos().observe(CryptoList.this, new Observer<List<Crypto>>() {
            @Override
            public void onChanged(@Nullable List<Crypto> cryptoList) {
                if (cryptoList != null && cryptoList.size() > 0) {
                    mCryptoAdapter.clear();
                    mCryptoAdapter.addAll(cryptoList);
                } else {
                    mNoExchangeView.setVisibility(View.VISIBLE);
                }
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

