package com.example.android.cryptocompare;

/**
 * Created by SOLARIN O. OLUBAYODE on 31/10/17.
 */

public class Crypto {

    private int mId;

    private int mCryptoCurrency;

    private double mCurrencyPrice;

    private int mBaseCurrency;


    public Crypto(int id, int cryptoCurrency, int baseCurrency, int baseCurrencyPrice) {
        mId = id;
        mCryptoCurrency = cryptoCurrency;
        mBaseCurrency = baseCurrency;
        mCurrencyPrice = baseCurrencyPrice;
    }

    public int getId() {
        return mId;
    }

    public int getCryptoCurrency() {
        return mCryptoCurrency;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }



    public int getBaseCurrency() {
        return mBaseCurrency;
    }

    public double getCurrencyPrice() {
        return mCurrencyPrice;
    }

    public void setCurrencyPrice(double mCurrencyPrice) {
        this.mCurrencyPrice = mCurrencyPrice;
    }

    public void setmCryptoCurrency(int mCryptoCurrency) {
        this.mCryptoCurrency = mCryptoCurrency;
    }

    public void setmBaseCurrency(int mBaseCurrency) {
        this.mBaseCurrency = mBaseCurrency;
    }

}
