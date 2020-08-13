package com.example.iiatimd;

import android.util.Log;

import java.util.List;

public class LogOutUserTask implements Runnable{

    AppDatabase db;

    public LogOutUserTask(AppDatabase db){
        this.db = db;
    }

    @Override
    public void run() {
        db.userDAO().logOutUser();
    }
}
