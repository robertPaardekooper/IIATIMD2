package com.example.iiatimd;

import android.util.Log;

import java.util.List;

public class LogInUserTask implements Runnable {

    AppDatabase db;
    String email;

    public LogInUserTask(AppDatabase db, String email){
        this.db = db;
        this.email = email;
    }

    @Override
    public void run() {
        db.userDAO().logOutUser();
        db.userDAO().logInUser(email);
    }
}
