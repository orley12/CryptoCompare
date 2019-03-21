package com.example.android.cryptocompare.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cryptocompare.R;
import com.example.android.cryptocompare.model.Crypto;
import com.example.android.cryptocompare.model.ViewData;

/**
 * Created by SOLARIN O. OLUBAYODE on 01/11/17.
 */

public  class CryptoViewHolder extends RecyclerView.ViewHolder {


    private TextView mLocalCurrencyName, mCryptoCurrencyName, mCurrencyValue;
    private ImageView mLocalCurrencyFlag, mCryptoCurrencyFlag;


    public CryptoViewHolder(View itemView) {
        super(itemView);

        mCryptoCurrencyName = (TextView) itemView.findViewById(R.id.text_crypto_name);
        mLocalCurrencyName = (TextView) itemView.findViewById(R.id.text_local_name);
        mCurrencyValue = (TextView) itemView.findViewById(R.id.text_currency_value);
        mCryptoCurrencyFlag = (ImageView) itemView.findViewById(R.id.img_crypto);
        mLocalCurrencyFlag = (ImageView) itemView.findViewById(R.id.img_local);
    }

    public void BindHolder(Crypto crypto) {
        mCryptoCurrencyName.setText(crypto.getCryptoCurrencyName());
        mLocalCurrencyName.setText(crypto.getLocalCurrencyName());
        mCurrencyValue.setText(String.valueOf(crypto.getCurrencyValue()));
        for (ViewData viewData : ViewData.values()){
            if (viewData.name().equals(crypto.getCryptoCurrencyName())){
                mCryptoCurrencyFlag.setImageResource(viewData.getCurrencyFlag());
            }
            if (viewData.name().equals(crypto.getLocalCurrencyName())){
                mLocalCurrencyFlag.setImageResource(viewData.getCurrencyFlag());
            }
        }
    }

}
