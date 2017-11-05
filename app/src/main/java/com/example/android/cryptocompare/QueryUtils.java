package com.example.android.cryptocompare;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.cryptocompare.data.CryptoContract;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SOLARIN O. OLUBAYODE on 01/11/17.
 */

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {

    }

    /**
     * Query the USGS dataset and return an {@link Crypto} object to represent a single earthquake.
     */
    public static Map<Integer, Map<Integer, Double>> fetchCryptoData(String requestUrl) {


        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        Map<Integer, Map<Integer, Double>> crypto = extractFeatureFromJSON(jsonResponse);



        // Return the {@link Event}
        return crypto;
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

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() >= 100) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
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
     * Return a list of {@link Crypto} objects that has been built up from
     * parsing a JSON response.
     */
    private static Map<Integer, Map<Integer, Double>> extractFeatureFromJSON(String cryptoJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(cryptoJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        Map<Integer, Map<Integer, Double>> MotherMap = new HashMap<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject baseJSONResponse = new JSONObject(cryptoJSON);
            JSONObject ETHJSONObject = baseJSONResponse.getJSONObject("ETH");
            JSONObject BTCJSONObject = baseJSONResponse.getJSONObject("BTC");

                double eNGNDouble = ETHJSONObject.getDouble("NGN");
                double eUSDDouble = ETHJSONObject.getDouble("USD");
                double eINRDouble = ETHJSONObject.getDouble("INR");
                double eSGDDouble = ETHJSONObject.getDouble("SGD");
                double eTWDDouble = ETHJSONObject.getDouble("TWD");
                double eAUDDouble = ETHJSONObject.getDouble("AUD");
                double eEURDouble = ETHJSONObject.getDouble("EUR");
                double eGBPDouble = ETHJSONObject.getDouble("GBP");
                double eZARDouble = ETHJSONObject.getDouble("ZAR");
                double eMXNDouble = ETHJSONObject.getDouble("MXN");
                double eILSDouble = ETHJSONObject.getDouble("ILS");
                double eNZDDouble = ETHJSONObject.getDouble("NZD");
                double eSEKDouble = ETHJSONObject.getDouble("SEK");
                double eNOKDouble = ETHJSONObject.getDouble("NOK");
                double eCHFDouble = ETHJSONObject.getDouble("CHF");
                double eSARDouble = ETHJSONObject.getDouble("SAR");
                double eAEDDouble = ETHJSONObject.getDouble("AED");
                double ePHPDouble = ETHJSONObject.getDouble("PHP");
                double eGTQDouble = ETHJSONObject.getDouble("GTQ");
                double eGHSDouble = ETHJSONObject.getDouble("GHS");

                double bNGNDouble = BTCJSONObject.getDouble("NGN");
                double bUSDDouble = BTCJSONObject.getDouble("USD");
                double bINRDouble = BTCJSONObject.getDouble("INR");
                double bSGDDouble = BTCJSONObject.getDouble("SGD");
                double bTWDDouble = BTCJSONObject.getDouble("TWD");
                double bAUDDouble = BTCJSONObject.getDouble("AUD");
                double bEURDouble = BTCJSONObject.getDouble("EUR");
                double bGBPDouble = BTCJSONObject.getDouble("GBP");
                double bZARDouble = BTCJSONObject.getDouble("ZAR");
                double bMXNDouble = BTCJSONObject.getDouble("MXN");
                double bILSDouble = BTCJSONObject.getDouble("ILS");
                double bNZDDouble = BTCJSONObject.getDouble("NZD");
                double bSEKDouble = BTCJSONObject.getDouble("SEK");
                double bNOKDouble = BTCJSONObject.getDouble("NOK");
                double bCHFDouble = BTCJSONObject.getDouble("CHF");
                double bSARDouble = BTCJSONObject.getDouble("SAR");
                double bAEDDouble = BTCJSONObject.getDouble("AED");
                double bPHPDouble = BTCJSONObject.getDouble("PHP");
                double bGTQDouble = BTCJSONObject.getDouble("GTQ");
                double bGHSDouble = BTCJSONObject.getDouble("GHS");


            Map<Integer, Double> EtherMap = new HashMap<>();
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_NAIRA, eNGNDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_USD, eUSDDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_RUPEE, eINRDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_SINGAPORE, eSGDDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_TAIWAN, eTWDDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_AUD, eAUDDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_EURO, eEURDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_GBP, eGBPDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_RAND, eZARDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_MEXICO, eMXNDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_ISREAL, eILSDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_NZD, eNZDDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_SWEDEN, eSEKDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_NORWAY, eNOKDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_FRANC, eCHFDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_SAUDI, eSARDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_DUBAI, eAEDDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_PHILIPPIANS, ePHPDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_GUATEMALA, eGTQDouble);
            EtherMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_GHANA, eGHSDouble);

            Map<Integer, Double> BitcoinMap = new HashMap<>();
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_NAIRA, bNGNDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_USD, bUSDDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_RUPEE, bINRDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_SINGAPORE, bSGDDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_TAIWAN, bTWDDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_AUD, bAUDDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_EURO, bEURDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_GBP, bGBPDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_RAND, bZARDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_MEXICO, bMXNDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_ISREAL, bILSDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_NZD, bNZDDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_SWEDEN, bSEKDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_NORWAY, bNOKDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_FRANC, bCHFDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_SAUDI, bSARDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_DUBAI, bAEDDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_PHILIPPIANS, bPHPDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_GUATEMALA, bGTQDouble);
            BitcoinMap.put(CryptoContract.CryptoEntry.BASE_CURRENCY_GHANA, bGHSDouble);


            MotherMap.put(1, BitcoinMap);
            MotherMap.put(2, EtherMap);


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the exchange results", e);
        }


        return MotherMap;
    }
}
