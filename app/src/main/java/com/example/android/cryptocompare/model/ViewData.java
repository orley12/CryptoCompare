package com.example.android.cryptocompare.model;

import com.example.android.cryptocompare.R;

/**
 * Created by SOLARIN O. OLUBAYODE on 08/02/19.
 */

public enum ViewData {

    BTC(R.string.btc, R.drawable.btc),
    ETH(R.string.eth, R.drawable.eth),
    USD(R.string.usd, R.drawable.usd),
    INR(R.string.inr, R.drawable.inr),
    SDG(R.string.sgd, R.drawable.sgd),
    TWD(R.string.twd, R.drawable.twd),
    AUD(R.string.aud, R.drawable.aud),
    EUR(R.string.eur, R.drawable.eur),
    GBP(R.string.gbp, R.drawable.gbp),
    ZAR(R.string.zar, R.drawable.zar),
    MXN(R.string.mxn, R.drawable.mxn),
    ILS(R.string.ils, R.drawable.ils),
    NZD(R.string.nzd, R.drawable.nzd),
    SEK(R.string.sek, R.drawable.sek),
    CHF(R.string.chf, R.drawable.chf),
    NOK(R.string.nok, R.drawable.nok),
    NGN(R.string.ngn, R.drawable.ngn),
    SAR(R.string.sar, R.drawable.sar),
    AED(R.string.aed, R.drawable.aed),
    PHP(R.string.php, R.drawable.php),
    GTQ(R.string.gtq, R.drawable.gtq),
    GHS(R.string.ghs, R.drawable.ghs);

    private int mCurrencyName;

    private int mCurrencyFlag;

    ViewData(int currencyName, int currencyFlag){
        mCurrencyName = currencyName;
        mCurrencyFlag = currencyFlag;
    }

    public int getCurrencyName() {
        return mCurrencyName;
    }

    public int getCurrencyFlag() {
        return mCurrencyFlag;
    }
}
