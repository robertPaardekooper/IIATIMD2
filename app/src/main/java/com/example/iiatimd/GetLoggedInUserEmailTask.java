package com.example.iiatimd;

import android.util.Log;

import java.util.List;

public class GetLoggedInUserEmailTask implements Runnable {

    AppDatabase db;
    private volatile String email;

    public GetLoggedInUserEmailTask(AppDatabase db){
        this.db = db;
    }

    @Override
    public void run() {
        email = db.userDAO().getLoggedInUser();
        //Log.d("emailLoggedInUser", email);
    }

    public String getEmail() {
        return email;
    }

}
