package com.example.android.cryptocompare.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cryptocompare.R;
import com.example.android.cryptocompare.model.Crypto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOLARIN O. OLUBAYODE on 01/11/17.
 */

/*  CryptoAdapter extends RecyclerView.Adapter meaning it inherits all the features of the
RecyclerView.Adapter and also as the ability to override some of those features
*the RecyclerView.Adapter<> takes in as param the CryptoViewHolder Object representing each Item to
populate the UI from through the adapter*/
public class CryptoAdapter extends RecyclerView.Adapter<CryptoViewHolder> {

    private static final String LOG_TAG = CryptoAdapter.class.getSimpleName() ;

    private Context mContext;

    private ArrayList<Crypto> oldCryptos = new ArrayList<>();

    private ArrayList<Crypto> newCryptos = new ArrayList<>();

    /*the constructor of the CryptoAdapter takes in as param a List<Crypto> List of crypto Objects
    @mCryptoList then we initialize our mCryptoList global Variable with the List passed into the
    constructor*/
    public CryptoAdapter(Context context) {
        mContext = context;
    }

    /*The onBindViewHolder() method takes in two params CryptoViewHolder class and position within the
     ArrayList
     *within the onBindViewHolder() method, we call the get method on the List @mCryptoList and pass
     in Position within the List,this returns a Crypto object, so we save the return value in a Crypto
     @ci
     *we call the BindHolder() of the holder class and pass in the Crypto object from the list selected
     position */
    @Override
    public void onBindViewHolder(CryptoViewHolder holder, int position) {
//        if (newCryptos.size() < 1 || newCryptos == null) {
//            Crypto oldCrypto = oldCryptos.get(position);
//            holder.BindHolder(oldCrypto, null, mContext);
//        }
                Crypto oldCrypto = oldCryptos.get(position);
                Crypto newCrypto = newCryptos.get(position);
                holder.BindHolder(oldCrypto, newCrypto, mContext);
    }

    /*The onCreateViewHolder() method takes in two params ViewGroup and an int
    * them we inflate the layout for each item within the RecyclerView,and hold it in a View @view
    * the View is then passed into the Constructor of the CryptoViewHolder
    * the constructor is then returned as the expected return type of the onCreateViewHolder() method*/
    @Override
    public CryptoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_item, parent, false);
        return new CryptoViewHolder(view);
    }

    /*Here the getItemCount() method  returns the ArraySize of the List mCryptoList*/
    @Override
    public int getItemCount() {
        return newCryptos.size();
    }

    /*Here the clear() method  clears List mCryptoList*/
    public void clear(){
        newCryptos.clear();
    }

    public void addAll(List<List<Crypto>> cryptos){
        oldCryptos.addAll(cryptos.get(0));
        newCryptos.addAll(cryptos.get(1));
        notifyDataSetChanged();
    }

    public  Crypto getCrypto(int position){
       return  newCryptos.get(position);
    }
//
//    public void addOldList(ArrayList<Crypto> oldCryptoList) {
//        oldCryptos.addAll(oldCryptoList);
//    }
}
