 package com.example.android.cryptocompare.ui;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.cryptocompare.R;
import com.example.android.cryptocompare.data.CryptoContract;
import com.example.android.cryptocompare.data.CryptoContract.CryptoEntry;
import com.example.android.cryptocompare.model.Crypto;
import com.example.android.cryptocompare.utilities.RecyclerItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

//import com.example.android.cryptocompare.data.AppDataBase;



/*This is the main Activity of this app @CryptoList, it extends the super class @AppCompatActivity
 and implements the @LoaderManager.LoaderCallbacks<> interface. */
public class CryptoList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<List<Crypto>>> {

    public static final String LOG_TAG = CryptoList.class.getName();

    /*this integer holds value of the option selected in the spinner for CryptoCurrencies,whatever value
    held in this integer is the value that will be passed into the database*/
    public String mCryptoCurrency;

    /*this integer holds value of the option selected in the spinner for BaseCurrencies,whatever value
    held in this integer is the value that will be passed into the database*/
    public String mLocalCurrency;

    /*Here we declare a global variable of the GridLayoutManager,this manager determines how many
    columns will be in the grid*/
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    /*Here we declare the CryptoAdapter a global variable*/
    CryptoAdapter cryptoAdapter;

    /*Here we declare the SwipeRefreshLayout a global variable*/
    private SwipeRefreshLayout swipeRefreshLayout;

    /*Here we initialize the context as global variable so it can be accessed through out this class*/
    Context context = this;

    LinearLayout noExchangeView;

    RecyclerView mRecyclerView;

    TextView mTimeTextView;

    /*we also initialize the Url link to the send request to the CryptoCompare request to the server
    and put the link in a String global variable @CRYPTOCOMPARE_REQUEST_URL*/
    private static final String CRYPTOCOMPARE_REQUEST_URL =
            "https://min-api.cryptocompare.com/data/pricemulti?fsyms=ETH,BTC&tsyms=NGN,USD,INR,SGD,TWD,AUD,EUR,GBP,ZAR,MXN,ILS,NZD,SEK,NOK,CHF,SAR,AED,PHP,GTQ,GHS";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_list);

        mTimeTextView = (TextView) findViewById(R.id.updateTimeText);
        /*we initialize the SwipeRefreshLayout by finding View by id ,and assigning the appropriate
        view.*/
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_view);

        noExchangeView = (LinearLayout) findViewById(R.id.no_exchange_view);

        /*if Cursor size from the query within the getCrypto() method is greater than 0
        * we check if there is connectivity via the ConnectivityManager, if there is connectivity
        we initialize the Loader by calling the initLoader() method on the getSupportLoaderManager()
        method */
        if(getCrypto() > 0) {
            getSupportLoaderManager().initLoader(1,null, this);
        }else {
            noExchangeView.setVisibility(View.VISIBLE);
        }

        /*we initialize the mRecyclerView by finding View by id ,and assigning the appropriate
        view. */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /*Here on the mRecyclerView we add an OnItemTouchListener who's interface calls us back when
        a touch event occurs on any of the items in the mRecyclerView
        * we set the RecyclerItemClickListener passing in the required arguments a context, the
        RecyclerView Object ,and an OnItemClickListener.
        * within the RecyclerItemClickListener method we override the onItemClick, and onLongItemClick
        methods*/
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView,new RecyclerItemClickListener.OnItemClickListener() {

                    /*within the onItemClick() method of the mRecyclerView we call the getCrypto()
                    method, of the cryptoAdapter class return type is a Crypto object in the ArrayList,
                    that populate the item selected at that position, we call the getCrypto() on
                    cryptoAdapter and pass in position as its argument this returns a Crypto object,
                    and we save this in a Crypto object @crypto.
                    *we then instantiate the Intent class passing in the required arguments current
                    activity and the activity to navigate to
                    *we instantiate the Bundle class @bundle and call the put() method on the bundle
                    class passing the key and value pairs the value of the is the Crypto Object @crypto
                    gotten from the selected position
                    *we call the putExtras() method on the Intent class @intent and pass in Bundle @bundle
                    *finally we call startActivity() method and pass in the Intent @intent.  */
                    @Override public void onItemClick(View view, int position) {

                        Crypto crypto = cryptoAdapter.getCrypto(position);
                        Log.i(LOG_TAG.toUpperCase(),"========>" + crypto.getCurrencyValue() + "");

                        Intent intent = new Intent(CryptoList.this, CryptoConvert.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("cryptoCurrency", crypto.getCryptoCurrencyName());
                        bundle.putString("localCurrency", crypto.getLocalCurrencyName());
                        bundle.putDouble("currencyValue", crypto.getCurrencyValue());
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }

                    /*Within the onLongItemClick method we pass in the params,the View,which is each
                     items in the mRecyclerView, and each position in the ArrayList*/
                    @Override public void onLongItemClick(View view, final int position) {

                        /*we call the getCrypto() method, of the cryptoAdapter class who's return type is a
                         Crypto object in the ArrayList,that populate the item selected at that position,
                         we call the getCrypto() on cryptoAdapter and pass in position as its argument
                         this returns a Crypto object, and we save this in a Crypto object @crypto*/
                        final Crypto cryptoObject = cryptoAdapter.getCrypto(position);

                        /*we instantiate the AlertDialog.Builder class @dialog, we set its title,
                        set a message, then set the positiveButton we passin the params for the
                        setPositiveButton() method a String representing button Title,and an OnClickListener
                        *within the onClick() method the Crypto object @cryptoObject is passed into
                        the remove() method of the cryptoAdapter class
                        *we call the notifyItemRemoved() method on the cryptoAdapter class,and pass
                        in position in the ArrayList as its param
                        *we also call the deleteCrypto method here to delete the data having anything
                        related to the Crypto object passed in from the dataBase,
                        *then we call the refresh method to refresh the Loaders data */
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle("Delete")
                                .setMessage("Delete card?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

//                                        cryptoAdapter.remove(cryptoObject);
//                                        cryptoAdapter.notifyItemRemoved(position);
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

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        /*Here we call setLayoutManager() method on the mRecyclerView and pass in the GridLayoutManager
        as argument*/
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);

        /*we initialize the instance of the global CryptoAdapter class @cryptoAdapter*/
        cryptoAdapter = new CryptoAdapter(context);

        /*Here we call setAdapter() method, on the RecyclerView and pass in CryptoAdapter as its argument*/
        mRecyclerView.setAdapter(cryptoAdapter);

        /*Here we initialize the context so it can be access within the fab button onClick() method*/
        final Activity context = this;

        /*we initialize the fab button View by finding View by id ,and assigning the appropriate
        view.
        *then we call the setOnClickListener() method on the fab button, passing in an object instance
        of the OnClickListener(), then we override the onClick() method .
        * within the onClick() method of the View class @fab we instantiate the AlertDialog.Builder
        class @mDialog,pass in a Context as argument.
        *then call the getLayoutInflater() method on the Context and put it in a LayoutInflater object
        we also call the inflate() method on the LayoutInflater object,passing in as argument,the custom
        layout we created and null,we save this in a View object @dialogView
        *on the AlertDialog.Builder object @mDialog we call on setView() on it and pass in thw View we
        want to inflate @dialogView,*/
        View fab = (View) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mDialog = new AlertDialog.Builder(context);

                LayoutInflater li = context.getLayoutInflater();
                final View dialogView = li.inflate(R.layout.crypto_option, null);
                mDialog.setView(dialogView)
                        .setTitle("Select Currencies");

                /*we initialize the Spinner View by finding View by id ,and assigning the appropriate
                view. */
                Spinner cryptoSpinner = (Spinner) dialogView.findViewById(R.id.spinner_crypto);

                /*we create an instance of the default ArrayAdapter object and name it @cryptoSpinnerAdapter
                 and call the createFromResource() method on the ArrayAdapter object, passing into its
                 constructor context,Array resource and the default spinner layout*/
                ArrayAdapter cryptoSpinnerAdapter = ArrayAdapter.createFromResource(context,
                        R.array.array_crypto_options, android.R.layout.simple_spinner_item);

                /*here we also call the ArrayAdapter here and call the setDropDownViewResource() method on the
                 ArrayAdapter(cryptoSpinnerAdapter), we pass in the param of the setDropDownViewResource()
                 method which is a default layout Resource*/
                // Specify dropdown layout style - simple list view with 1 item per line
                cryptoSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                // Apply the adapter to the spinner
                cryptoSpinner.setAdapter(cryptoSpinnerAdapter);

                // Set the integer mCryptoCurrency to the constant values
                cryptoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selection)) {
                            mCryptoCurrency = selection;
                        }
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                /*we create an instance of the default ArrayAdapter object and name it @cryptoSpinnerAdapter
                 and call the createFromResource() method on the ArrayAdapter object, passing into its
                 constructor context,Array resource and the default spinner layout*/
                ArrayAdapter baseSpinnerAdapter = ArrayAdapter.createFromResource(context,
                        R.array.array_base_options, android.R.layout.simple_spinner_item);

                /*here we also call the ArrayAdapter here and call the setDropDownViewResource() method on the
                 ArrayAdapter @baseSpinnerAdapter, we pass in the param of the setDropDownViewResource()
                 method which is a default layout Resource*/
                // Specify dropdown layout style - simple list view with 1 item per line
                baseSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                /*we initialize the Spinner View by finding View by id ,and assigning the appropriate
                view. */
                Spinner baseSpinner = (Spinner) dialogView.findViewById(R.id.spinner_base);

                // Apply the adapter to the spinner
                baseSpinner.setAdapter(baseSpinnerAdapter);

                // Set the String mCryptoCurrency to the constant values
                baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selection)) {
                            mLocalCurrency = selection;
                        }
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                /*we set the positiveButton on the AlertDialog.Builder object @mDialog, we pass in
                the params for the setPositiveButton() method a String representing button Title,and
                an OnClickListener
                *within the onClick() method we call the insertCrypto() method which help us insert
                data into the dataBase*/
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

        /*we call the setOnRefreshListener() method on the SwipeRefreshLayout object and pass in as
        param OnRefreshListener which listens for refresh actions on the UI
        *we override the onRefresh() method and within the onRefresh() method we call the refresh()
        which calls a restartLoader() method on the loaderManager*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


    }

    /*here we override the onCreateOptionsMenu() method, which takes in as param a Menu dataType
     named @menu
    *we call the getMenuInflater() method and also call the inflate() method on the
     getMenuInflater() method, the inflate() method takes in the xml activity name of the holding
     the menu layout
    *finally we return true because the methods return type is a boolean*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /*here we override the onOptionsItemSelected() method,which takes in as param a MenuItem dataType
    named @Item.
   *then we call the switch case statement we then pass into the switch case statement we pass in
    the MenuItem named item,then call the getItemId() method to get the item selected
   *we return true for each switch statement as the expected boolean return value of the
    onOptionsItemSelected()
   *finally we return onOptionsItemSelected() method and pass in the item which was selected*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.delete_all:
                deleteAllCrypto();
                break;
            case R.id.info:
                launchInfoActivity();
                break;
//                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchInfoActivity() {
        Intent intent = new Intent(CryptoList.this, InfoActivity.class);
                startActivity(intent);
    }

    /*with the onRefresh() method we call the a restartLoader() method on the loaderManager, to restart
    the loader and reload data*/
    public void refresh(){
        getSupportLoaderManager().restartLoader(1, null, this);
        noExchangeView.setVisibility(View.GONE);
    }

//      this method helps us insert data into the DB
    private void insertCrypto() {
            ContentValues values = new ContentValues();
            values.put(CryptoContract.CryptoEntry.CRYPTO_CURRENCY_COLUMN, mCryptoCurrency);
            values.put(CryptoContract.CryptoEntry.LOCAL_CURRENCY_COLUMN, mLocalCurrency);
            values.put(CryptoEntry.CURRENCY_TAG, mCryptoCurrency + mLocalCurrency);

            getContentResolver().insert(CryptoContract.CryptoEntry.CONTENT_URI, values);
            refresh();
    }

//    this method helps us delete the entire data in the DB
    public void deleteAllCrypto() {
        /*the delete method which is called on the contentResolver,takes in params the Uri and
         two null values representing the selection,and selection args.
         *we also call a notifyDataSetChanged() method on the cryptoAdapter to notify the adapter of
         the change in the data */
        int deleteResult = getContentResolver().delete(CryptoEntry.CONTENT_URI, null, null);
        cryptoAdapter.notifyDataSetChanged();
        refresh();
    }

    //    this method helps us delete a single row of data in the DB
    public void deleteCrypto(Crypto crypto){
        /*we set our selection,as the columns we want to delete from
        *we get selectionArgs by getting the values base and crypto currencies passed in from the Crypto
        object.
        the delete method which is called on the contentResolver,takes in params the Uri and
         the selection,and selection args.*/
        String selection = CryptoEntry.LOCAL_CURRENCY_COLUMN +"=? and "+ CryptoEntry.CRYPTO_CURRENCY_COLUMN +"=?";
        String[] selectionArgs = {String.valueOf(crypto.getLocalCurrencyName()), String.valueOf(crypto.getCryptoCurrencyName())};

        int result = getContentResolver().delete(CryptoEntry.CONTENT_URI, selection, selectionArgs);
    }

    /*this method returns the number of rows in the entire table*/
    private int getCrypto() {

        String[] projection = new String[]{CryptoEntry.STRING_ID, CryptoEntry.CRYPTO_CURRENCY_COLUMN,
                CryptoEntry.CURRENCY_PRICE_COLUMN, CryptoEntry.LOCAL_CURRENCY_COLUMN};
        Cursor cursor = getContentResolver()
                .query(CryptoEntry.CONTENT_URI, projection, null, null,
                null);

        int number = 0;

        try {
            while (cursor.moveToNext()) {
                number++;
            }
        }catch (NullPointerException ex){
            Log.e(LOG_TAG, ex.getMessage());
        }
        if(cursor != null)
        cursor.close();
        return number;
    }

    /*the onCreateLoader() takes in two param of dataType int(i) ,and Bundle(bundle)
     *it then returns a Loader holding the data of List<Earthquake>
     *within the method we call the constructor of CryptoLoader and pass in the appropriate args
     Context and the correct url,"this is what will return the data the doInBackground() method
     as received from the CryptoCompare server,as a List<Crypto> object"
     we also set the progress bar visibility to visible here*/
    @Override
    public Loader<List<List<Crypto>>> onCreateLoader(int id, Bundle args) {
//        LocalDatabaseQueryUtils localDatabaseQueryUtils = new LocalDatabaseQueryUtils(this);
//        cryptoAdapter.addOldList((ArrayList<Crypto>) localDatabaseQueryUtils.findAllCrypto());
//        for (Crypto crypto : localDatabaseQueryUtils.findAllCrypto())
//        Log.i(LOG_TAG.toUpperCase(),"====> " + crypto.getCryptoCurrencyName());

        return new CryptoLoader(CryptoList.this, CRYPTOCOMPARE_REQUEST_URL );
    }
    /*the onLoadFinished() method takes in two arguments Loader<List<Crypto>> "the created
   Loader holding the List<Crypto> data",with name @loader,and second argument the
   <List<Earthquake> with name @data*/
    @Override
    public void onLoadFinished(Loader<List<List<Crypto>>> loader, List<List<Crypto>> data) {

        /*if List<Crypto> (data) is not null*/
        if(data != null) {
            /*we clear the List<Crypto> data in the mAdapter*/
            cryptoAdapter.clear();
            /*we addAll data the List<Crypto> data in the mAdapter*/
            cryptoAdapter.addAll(data);

            mTimeTextView.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));

            runLayoutAnimation();
        }

        swipeRefreshLayout.setRefreshing(false);
    }

    /*once the Loader created is obsolete the onLoadReset() method is called to clear out the data
     in the Loader
     *it takes in as argument the Loader holding the List<Crypto>*/
    @Override
    public void onLoaderReset(Loader<List<List<Crypto>>> loader) {
        /*we clear the List<Crypto> data in the mAdapter*/
        cryptoAdapter.clear();
    }

    private void runLayoutAnimation(){
        final Context context = mRecyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);

        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
    }

}

