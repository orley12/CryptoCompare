package com.example.android.cryptocompare.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.cryptocompare.data.CryptoContract.CryptoEntry;
import com.example.android.cryptocompare.model.Crypto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by SOLARIN O. OLUBAYODE on 01/11/17.
 */

/*this class  @QueryUtils is where we send request to the Cryptoompare servers get back a responses
in json format and process this responds so it can be displayed for the users to see*/
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     * @param
     */
    private QueryUtils() {
    }


    /*Query the CryptoCompare dataSet and return a map @crypto which in turn holds two other maps.
     *within this method @fetchCryptoData,the url String @requestUrl passed in from the
     doInBackground() method of the Loader,
     * is passed into the createUrl() method, which converts the String and returns a URL dataType,
     saved with the name @url with dataType URL.
     * the url is then passed into the makeHttpRequest() method which returns a String of the JSON
     responds returned from the server ,the responds is saved with name @jsonResponse and String
     dataType.
     * the jsonResponse is passed into the extractFeatureFromJSON method which returns a Map object
     we save the Map returned in a with name @crypto and dataType Map
     *we return crypto as the expected return dataType of the @fetchCryptoData() method.
     */
    public static String fetchCryptoData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return jsonResponse;

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            Log.e("URL created success ", stringUrl);


        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),but for this api it could be >=100
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() >= 100) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.e(LOG_TAG, jsonResponse);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Crypto JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        Log.e(LOG_TAG, "Error response code: " + jsonResponse);
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * The extractFeatureFromJSON() method takes in as param @cryptoJSON which is the JSON responds
     from the server,this method also returns a Map dataType which with this Map are two other maps.
     * if the JSON responds from the server is empty asIn @cryptoJSON  we return null; else we parse
     JSON
     * before we start parsing the JSON responds we call an instance of the Map class this Map will
     be the mother mat@
     * parsing a JSON response.
     */
    public static List<Crypto> extractFeatureFromJSON(String cryptoJSON, Context context) {
        LocalDatabaseQueryUtils mLocalDatabaseQueryUtils = new LocalDatabaseQueryUtils(context);
        List<Crypto> cryptoList = mLocalDatabaseQueryUtils.findAllCrypto();
        Log.i(LOG_TAG.toUpperCase(), String.valueOf("RESULT STRING ====> "+ TextUtils.isEmpty(cryptoJSON)));

        // Try to parse the JSON response @cryptoJSON. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        if (!cryptoJSON.isEmpty()) {
            try {
                JSONObject baseJSONResponse = new JSONObject(cryptoJSON);

                ContentValues[] cryptoContentValues = new ContentValues[cryptoList.size()];

                int index = 0;
                for (Crypto singleCryptoObject : cryptoList) {
                    JSONObject cryptoCurrencyJsonObject = baseJSONResponse
                            .getJSONObject(singleCryptoObject.getCryptoCurrencyName());
                    double currencyValue = cryptoCurrencyJsonObject
                            .getDouble(singleCryptoObject.getLocalCurrencyName());

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(CryptoEntry.CRYPTO_CURRENCY_COLUMN, singleCryptoObject.getCryptoCurrencyName());
                    contentValues.put(CryptoEntry.LOCAL_CURRENCY_COLUMN, singleCryptoObject.getLocalCurrencyName());
                    contentValues.put(CryptoEntry.CURRENCY_TAG, singleCryptoObject.getCryptoCurrencyName() + singleCryptoObject.getLocalCurrencyName());
                    contentValues.put(CryptoEntry.CURRENCY_PRICE_COLUMN, currencyValue);

                    cryptoContentValues[index++] = contentValues;

                }
                mLocalDatabaseQueryUtils.upAllCrypto(cryptoContentValues);

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the exchange results", e);
            }
        }
        return cryptoList;
    }
}
