package com.example.android.cryptocompare.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.cryptocompare.model.Crypto;

import java.util.List;

/**
 * Created by SOLARIN O. OLUBAYODE on 07/02/19.
 */
@Dao
public interface CryptoDao {

    @Query("SELECT * FROM crypto")
    LiveData<List<Crypto>> findAllCrypto();

    @Query("SELECT * FROM crypto")
    List<Crypto> LoadAllCrypto();

    @Update
    void updateCrypto(List<Crypto> cryptos);

    @Insert
    void insertCrypto(Crypto crypto);

    @Delete
    void deleteAllCrypto(List<Crypto> crypto);

    @Delete
    void deleteCrypto(Crypto crypto);

    @Query("SELECT * FROM crypto WHERE id = :id")
    LiveData<Crypto> findCryptoById(int id);
}
