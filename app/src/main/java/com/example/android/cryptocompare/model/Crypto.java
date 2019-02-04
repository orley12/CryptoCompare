package com.example.android.cryptocompare.model;

import android.arch.persistence.room.Entity;

/**
 * Created by SOLARIN O. OLUBAYODE on 31/10/17.
 */

@Entity
public class Crypto {

//    @PrimaryKey(autoGenerate = true)
    private int mId;

    private String cryptoCurrencyName;

    private String localCurrencyName;

    private double currencyValue;

//    @Ignore
    private ViewData mViewDataCrypto;

//    @Ignore
    private ViewData mViewDataLocal;

//    @Ignore
//    public Crypto(String cryptoCurrencyName, String localCurrencyName, double currencyValue) {
//        this.cryptoCurrencyName = cryptoCurrencyName;
//        this.localCurrencyName = localCurrencyName;
//        this.currencyValue = currencyValue;
//    }

    public Crypto(int id, String cryptoCurrencyName, String localCurrencyName, double currencyValue) {
        mId = id;
        this.cryptoCurrencyName = cryptoCurrencyName;
        this.localCurrencyName = localCurrencyName;
        this.currencyValue = currencyValue;
        setViewDataLocal(this.localCurrencyName);
        setViewDataCrypto(this.cryptoCurrencyName);
    }

    public int getId() {
        return mId;
    }

    public String getCryptoCurrencyName() {
        return cryptoCurrencyName;
    }

    public String getLocalCurrencyName() {
        return localCurrencyName;
    }

    public double getCurrencyValue() {
        return currencyValue;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setCurrencyValue(double currencyPrice) {
        this.currencyValue = currencyPrice;
    }

    public void setCryptoCurrencyName(String cryptoCurrencyName) {
        this.cryptoCurrencyName = cryptoCurrencyName;
    }

    public void setLocalCurrencyName(String localCurrencyName) {
        this.localCurrencyName = localCurrencyName;
    }


    public ViewData getViewDataLocal() {
        return mViewDataLocal;
    }

    private void setViewDataLocal(String baseCurrencyName) {
        for (ViewData viewData : ViewData.values()) {
            if (baseCurrencyName.equals(viewData.name())) {
                mViewDataLocal = viewData;
            }
        }
    }

    public ViewData getViewDataCrypto() {
        return mViewDataCrypto;
    }

    private void setViewDataCrypto(String cryptoCurrencyName) {
        for (ViewData viewData : ViewData.values()) {
            if (cryptoCurrencyName.equals(viewData.name())) {
                mViewDataCrypto = viewData;
            }
        }
    }
}
