package com.example.android.cryptocompare.data;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by SOLARIN O. OLUBAYODE on 09/07/18.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDataBase mAppDataBase;
    private final int mCryptoId;

    public ViewModelFactory(AppDataBase appDataBase, int cryptoId){
        mAppDataBase = appDataBase;
        mCryptoId = cryptoId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddCryptoViewModel(mAppDataBase, mCryptoId);

    }
}
