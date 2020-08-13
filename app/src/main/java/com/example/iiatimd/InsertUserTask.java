package com.example.iiatimd;

import android.util.Log;

public class InsertUserTask implements Runnable{

    AppDatabase db;
    User user;

    public InsertUserTask(AppDatabase db, User user){
        this.db = db;
        this.user = user;
    }

    @Override
    public void run() {
        db.userDAO().insertUser(this.user);
        String naam = db.userDAO().getLoggedInUser().get(0).getNaam();
        Log.d("NaInsert", naam);
    }
}
