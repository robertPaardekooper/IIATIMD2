package com.example.iiatimd;

import android.util.Log;

public class InsertProductTask implements Runnable{

    AppDatabase db;
    Product product;

    public InsertProductTask(AppDatabase db, Product product){
        this.db = db;
        this.product = product;
    }
    @Override
    public void run() {
        db.productDAO().insertProduct(this.product);
        String naam = db.productDAO().getAll().get(0).getNaam();
        Log.d("NaInsert", naam);
    }
}
