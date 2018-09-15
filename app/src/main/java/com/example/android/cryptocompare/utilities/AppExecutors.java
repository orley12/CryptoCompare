//package com.example.android.cryptocompare.utilities;
//
//import android.os.Looper;
//import android.support.annotation.NonNull;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
///**
// * Created by SOLARIN O. OLUBAYODE on 02/02/19.
// */
//
//public class AppExecutors {
//
//    private static final Object LOCK = new Object();
//    private static AppExecutors sInstance;
//
//    private final Executor diskIO;
//    private final Executor networkIO;
//    private final Executor mainThread;
//
//    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread){
//        this.diskIO = diskIO;
//        this.networkIO = networkIO;
//        this.mainThread = mainThread;
//    }
//
//    public static AppExecutors getInstance(){
//        if (sInstance == null){
//            synchronized (LOCK){
//                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
//                        Executors.newFixedThreadPool(3),new MainthreadExecutor());
//            }
//        }
//        return sInstance;
//    }
//
//    public Executor DiskIO() {
//        return diskIO;
//    }
//
//    public Executor NetworkIO() {
//        return networkIO;
//    }
//
//    public Executor MainThread() {
//        return mainThread;
//    }
//
//
//    private static class MainthreadExecutor implements Executor {
//        private android.os.Handler mainThreadLoooper = new android.os.Handler(Looper.getMainLooper());
//        @Override
//        public void execute(@NonNull Runnable command) {
//            mainThreadLoooper.post(command);
//        }
//    }
//}
