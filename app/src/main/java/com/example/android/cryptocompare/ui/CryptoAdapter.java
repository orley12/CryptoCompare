package com.example.android.cryptocompare.ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cryptocompare.R;
import com.example.android.cryptocompare.model.Crypto;

import java.util.List;

/**
 * Created by SOLARIN O. OLUBAYODE on 01/11/17.
 */

public class CryptoAdapter extends RecyclerView.Adapter<CryptoViewHolder> {

    private static final String LOG_TAG =CryptoAdapter.class.getSimpleName() ;
    private List<Crypto> cryptoList;

    public CryptoAdapter(List<Crypto> cryptoList) {
        this.cryptoList = cryptoList;
    }

    @Override
    public void onBindViewHolder(CryptoViewHolder holder, int position) {
        Crypto ci = cryptoList.get(position);
        Log.i(LOG_TAG.toUpperCase(),"=====> " + ci.getCurrencyValue());
        holder.BindHolder(ci);
    }

    @Override
    public CryptoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_item, parent, false);
        return new CryptoViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }


    public void clear(){
        cryptoList.clear();
    }

    public void addAll(List<Crypto> cryptos){
        cryptoList.addAll(cryptos);
        notifyDataSetChanged();
    }


    public void remove(Crypto crypto){
        cryptoList.remove(crypto);
    }


    public  Crypto getCrypto(int position){
       return  cryptoList.get(position);
    }

}
