package com.example.android.cryptocompare.model;

import com.example.android.cryptocompare.R;

/**
 * Created by SOLARIN O. OLUBAYODE on 30/01/19.
 */

public enum ViewData {

    BTC(R.string.bitcoin, R.drawable.btc),
    ETH(R.string.ethereum, R.drawable.eth),
    NGN(R.string.nigerian_naira, R.drawable.ngn),
    USD(R.string.usd_dollar, R.drawable.usd),
    INR(R.string.indian_rupee, R.drawable.inr),
    SGD(R.string.singapore_dollar, R.drawable.sgd),
    TWD(R.string.taiwan_dollar, R.drawable.twd),
    AUD(R.string.australian_dollar, R.drawable.aud),
    EUR(R.string.euro_, R.drawable.eur),
    GBP(R.string.great_britain_pound, R.drawable.gbp),
    ZAR(R.string.south_african_rand, R.drawable.zar),
    MXN(R.string.mexican_peso, R.drawable.mxn),
    ILS(R.string.israel_shekel, R.drawable.ils),
    NZD(R.string.new_zealand_dollar, R.drawable.nzd),
    SEK(R.string.swedish_krona, R.drawable.sek),
    CHF(R.string.swiss_franc, R.drawable.chf),
    NOK(R.string.norwegian_krone, R.drawable.nok),
    SAR(R.string.saudi_arabia_riyal, R.drawable.sar),
    AED(R.string.arab_emirates_dirham, R.drawable.aed),
    PHP(R.string.philippines_peso, R.drawable.php),
    GTQ(R.string.guatemala_quetzal, R.drawable.gtq),
    GHS(R.string.ghana_new_cedi, R.drawable.ghs);

    private int mLocalCurrencyName;

    private int mLocalCurrencyFlag;

    ViewData(int localCurrencyName , int localCurrencyFlag) {
        mLocalCurrencyName = localCurrencyName;
        mLocalCurrencyFlag = localCurrencyFlag;
    }

    public int getLocalCurrencyName() {
        return mLocalCurrencyName;
    }

    public int getCurrencyFlag() {
        return mLocalCurrencyFlag;
    }
}
