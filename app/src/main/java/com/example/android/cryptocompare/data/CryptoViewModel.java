package com.example.android.cryptocompare.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.cryptocompare.model.Crypto;

import java.util.List;

/**
 * Created by SOLARIN O. OLUBAYODE on 09/07/18.
 */

public class CryptoViewModel extends AndroidViewModel {

    LiveData<List<Crypto>> cryptos;

    public CryptoViewModel(@NonNull Application application) {
        super(application);
        cryptos = AppDataBase.getInstance(application).cryptoDao().findAllCrypto();
    }

    public LiveData<List<Crypto>> getCryptos(){
        return cryptos;
    }
}
