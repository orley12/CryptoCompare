package com.example.android.cryptocompare.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cryptocompare.R;
import com.example.android.cryptocompare.model.Crypto;

import java.text.DecimalFormat;

/**
 * Created by SOLARIN O. OLUBAYODE on 01/11/17.
 */

/* CryptoViewHolder extends RecyclerView.ViewHolder meaning it inherits all the features of the
RecyclerView.ViewHolder and also as the ability to override some of those features*/
public  class CryptoViewHolder extends RecyclerView.ViewHolder {

    /*Here we declare a TextView to hold the text of the currency value to be displayed on screen */
    private TextView mTextCurrencyValue;
    private TextView mTextLocalCurrency;
    private TextView mTextCryptoCurrency;

    Context context;

    /*Here we declare two ImageViews to hold the Images of the Crypto currency  and based currency Flags
    values to be displayed on screen */
    protected ImageView mImageLocalCurrency, mImageCryptoCurrency;

    /*the constructor for the CryptoViewHolder takes in as param a View @itemView which is the Card
    for each item in the mRecyclerView*/
    public CryptoViewHolder(View itemView) {
        super(itemView);

        /*Here we initialize the TextView holding the current exchange rate in the card by finding the
        appropriate View by id from the View passed into the constructor @itemView*/
        mTextCurrencyValue = (TextView) itemView.findViewById(R.id.text_rate);
        /*Here we initialize the ImageView holding the base exchange rate Image in the card by finding the
        appropriate View by id from the View passed into the constructor @itemView*/
        mTextLocalCurrency = (TextView) itemView.findViewById(R.id.local_text_input);
        /*Here we initialize the ImageView holding the current exchange rate Image in the card by finding the
        appropriate View by id from the View passed into the constructor @itemView*/
        mTextCryptoCurrency = (TextView) itemView.findViewById(R.id.text_crypto_name);

        mImageCryptoCurrency = (ImageView) itemView.findViewById(R.id.img_crypto_currency);

        mImageLocalCurrency = (ImageView) itemView.findViewById(R.id.img_local_currency);

    }

    /*the BindHolder() method, takes in as param a Crypto object which is passed from CrytoAdapter
    class as a result of the Crypto Object in position selected in the ArrayList
    *then we call the getCurrencyValue() on the Crypto object and whatever the String value is we set
    it on the TextView @mCurrencyValue*/
    public void BindHolder(Crypto oldCrypto, Crypto newCrypto, Context context) {
//        Crypto crypto = null;
//        if (newCrypto == null){
//            crypto = oldCrypto;
//        }

        DecimalFormat decimalFormatter = new DecimalFormat();
        String moneyString = decimalFormatter.format(newCrypto.getCurrencyValue());

        mTextCurrencyValue.setText(moneyString);

        mTextLocalCurrency.setText(newCrypto.getLocalCurrencyName());

        mTextCryptoCurrency.setText(newCrypto.getCryptoCurrencyName());

        mImageLocalCurrency.setImageResource(newCrypto.getViewDataLocal().getCurrencyFlag());

        mImageCryptoCurrency.setImageResource(newCrypto.getViewDataCrypto().getCurrencyFlag());

        textDecor(oldCrypto, newCrypto, context);
    }

    private void textDecor(Crypto oldCrypto, Crypto newCrypto, Context context){
            if (oldCrypto.getCurrencyValue() == newCrypto.getCurrencyValue()) {
                mTextCurrencyValue.setTextColor(context.getResources().getColor(android.R.color.background_dark));
            }else if (oldCrypto.getCurrencyValue() < newCrypto.getCurrencyValue()){
                mTextCurrencyValue.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            }else {
                mTextCurrencyValue.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
            }
    }

}
