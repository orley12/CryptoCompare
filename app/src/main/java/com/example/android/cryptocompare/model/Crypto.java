package com.example.android.cryptocompare.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by SOLARIN O. OLUBAYODE on 31/10/17.
 */
@Entity(indices = {@Index(value = {"currency_tag"},unique = true)})
public class Crypto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String cryptoCurrencyName;

    private String localCurrencyName;

    private double currencyValue;

    @ColumnInfo(name = "currency_tag")
    private String currencyTag;

    @Ignore
    public Crypto(String cryptoCurrencyName, String localCurrencyName, double currencyValue, String currencyTag) {
        this.id = id;
        this.cryptoCurrencyName = cryptoCurrencyName;
        this.localCurrencyName = localCurrencyName;
        this.currencyValue = currencyValue;
        this.currencyTag = currencyTag;
    }

    public Crypto(int id, String cryptoCurrencyName, String localCurrencyName, double currencyValue, String currencyTag) {
        this.id = id;
        this.cryptoCurrencyName = cryptoCurrencyName;
        this.localCurrencyName = localCurrencyName;
        this.currencyValue = currencyValue;
        this.currencyTag = currencyTag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCryptoCurrencyName() {
        return cryptoCurrencyName;
    }

    public void setCryptoCurrencyName(String cryptoCurrencyName) {
        this.cryptoCurrencyName = cryptoCurrencyName;
    }

    public String getLocalCurrencyName() {
        return localCurrencyName;
    }

    public void setLocalCurrencyName(String localCurrencyName) {
        this.localCurrencyName = localCurrencyName;
    }

    public double getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(double currencyValue) {
        this.currencyValue = currencyValue;
    }

    public String getCurrencyTag() {
        return currencyTag;
    }

    public void setCurrencyTag(String currencyTag) {
        this.currencyTag = currencyTag;
    }
}
