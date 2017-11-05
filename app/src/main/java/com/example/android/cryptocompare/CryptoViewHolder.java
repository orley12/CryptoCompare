package com.example.android.cryptocompare;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cryptocompare.data.CryptoContract;

/**
 * Created by SOLARIN O. OLUBAYODE on 01/11/17.
 */

public  class CryptoViewHolder extends RecyclerView.ViewHolder {


    protected TextView mBaseCurrency;
    protected ImageView mFlag, cryptoFlag;


    public CryptoViewHolder(View itemView) {
        super(itemView);

        mBaseCurrency = (TextView) itemView.findViewById(R.id.crypto_price);
        mFlag = (ImageView) itemView.findViewById(R.id.flag_sym);
        cryptoFlag = (ImageView) itemView.findViewById(R.id.crypto_image);
    }

    public void BindHolder(Crypto crypto) {
        mBaseCurrency.setText(String.valueOf(crypto.getCurrencyPrice()));

    

        switch (crypto.getBaseCurrency()){

            case CryptoContract.CryptoEntry.BASE_CURRENCY_NAIRA:
                mFlag.setImageResource(R.drawable.ngn);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_USD:
                mFlag.setImageResource(R.drawable.usd);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_RUPEE:
                mFlag.setImageResource(R.drawable.inr);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_SINGAPORE:
                mFlag.setImageResource(R.drawable.sgd);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_TAIWAN:
                mFlag.setImageResource(R.drawable.twd);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_AUD:
                mFlag.setImageResource(R.drawable.aud);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_EURO:
                mFlag.setImageResource(R.drawable.eur);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_GBP:
                mFlag.setImageResource(R.drawable.gbp);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_RAND:
                mFlag.setImageResource(R.drawable.zar);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_MEXICO:
                mFlag.setImageResource(R.drawable.mxn);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_ISREAL:
                mFlag.setImageResource(R.drawable.ils);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_NZD:
                mFlag.setImageResource(R.drawable.nzd);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_SWEDEN:
                mFlag.setImageResource(R.drawable.sek);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_NORWAY:
                mFlag.setImageResource(R.drawable.nok);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_FRANC:
                mFlag.setImageResource(R.drawable.chf);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_SAUDI:
                mFlag.setImageResource(R.drawable.sar);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_DUBAI:
                mFlag.setImageResource(R.drawable.aed);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_PHILIPPIANS:
                mFlag.setImageResource(R.drawable.php);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_GUATEMALA:
                mFlag.setImageResource(R.drawable.gtq);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_GHANA:
                mFlag.setImageResource(R.drawable.ghs);
                break;

                default:
                    break;
        }

        switch(crypto.getCryptoCurrency()){
            case CryptoContract.CryptoEntry.CRYPTO_CURRENCY_BTC:
                cryptoFlag.setImageResource(R.drawable.btc);
                break;

            case CryptoContract.CryptoEntry.CRYPTO_CURRENCY_ETH:
                cryptoFlag.setImageResource(R.drawable.etc);
                break;

            default:
                break;

        }
    }

}
