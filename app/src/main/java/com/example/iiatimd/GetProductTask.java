package com.example.iiatimd;

import android.util.Log;

public class GetProductTask implements Runnable {

    AppDatabase db;

    public GetProductTask(AppDatabase db){
        this.db = db;
    }

    @Override
    public void run() {
        String naam = db.productDAO().getAll().get(0).getName();
        Log.d("getProductTask", naam);
    }
}
