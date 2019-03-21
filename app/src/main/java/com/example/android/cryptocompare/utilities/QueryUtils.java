package com.example.android.cryptocompare.utilities;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.cryptocompare.data.AppDataBase;
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
import java.util.ArrayList;
import java.util.List;

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
    public static void fetchCryptoData(String requestUrl, Context pContext) {
        Context context = pContext;


        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        extractFeatureFromJSON(jsonResponse, context);
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
        Log.i(LOG_TAG, "Response: " + jsonResponse);
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
    private static void extractFeatureFromJSON(final String cryptoJSON, final Context context) {
        final AppDataBase dB = AppDataBase.getInstance(context);
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(cryptoJSON)) {
            return;
        }

        Log.i(LOG_TAG.toUpperCase(), "extractFeatureFromJSON ===> " + cryptoJSON);

        List<Crypto> savedCryptos = dB.cryptoDao().LoadAllCrypto();
        List<Crypto> cryptosToBePersisted = new ArrayList<>();
        if (savedCryptos.size() > 0) {
            try {
                JSONObject baseJSONResponse = new JSONObject(cryptoJSON);
                for (Crypto crypto : savedCryptos) {
                    JSONObject cryptoObject = baseJSONResponse.getJSONObject(crypto.getCryptoCurrencyName());
                    double currencyValue = cryptoObject.getDouble(crypto.getLocalCurrencyName());
                    Crypto cryptoToPersist = new Crypto(crypto.getId()
                            , crypto.getCryptoCurrencyName()
                            , crypto.getLocalCurrencyName()
                            , currencyValue
                            , crypto.getCryptoCurrencyName() + crypto.getLocalCurrencyName());
                    cryptosToBePersisted.add(cryptoToPersist);
                }
                dB.cryptoDao().updateCrypto(cryptosToBePersisted);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    }
