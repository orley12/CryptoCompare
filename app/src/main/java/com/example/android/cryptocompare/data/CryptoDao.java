//package com.example.android.cryptocompare.data;
//
//import android.arch.lifecycle.LiveData;
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.Query;
//
//import com.example.android.cryptocompare.model.Crypto;
//
//import java.util.List;
//
///**
// * Created by SOLARIN O. OLUBAYODE on 02/02/19.
// */
//
//@Dao
//public interface CryptoDao {
//
//    @Query("SELECT * FROM crypto")
//    LiveData<List<Crypto>> findAll();
//
//    @Query("SELECT * FROM crypto WHERE id = :id")
//    LiveData<Crypto> findById(int id);
//
//    @Delete
//    void deleteCrypto(Crypto crypto);
//
//    @Insert
//    void addCrypto(Crypto crypto);
//
//    @Insert
//    void addAllCrypto(List<Crypto> cryptoList);
//}
