package com.example.android.cryptocompare.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.cryptocompare.model.Crypto;

/**
 * Created by SOLARIN O. OLUBAYODE on 09/07/18.
 */

public class AddCryptoViewModel extends ViewModel {

    LiveData<Crypto> cryptoLiveData;

    public AddCryptoViewModel(AppDataBase mAppDataBase, int mCryptoId) {
        cryptoLiveData = mAppDataBase.cryptoDao().findCryptoById(mCryptoId);
    }

    public LiveData<Crypto> getCryptoLiveData() {
        return cryptoLiveData;
    }
}
