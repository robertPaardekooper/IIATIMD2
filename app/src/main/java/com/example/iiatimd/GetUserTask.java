package com.example.iiatimd;

import android.util.Log;

import java.util.List;

public class GetUserTask implements Runnable {

    AppDatabase db;

    public GetUserTask(AppDatabase db){
        this.db = db;
    }

    @Override
    public void run() {
        List<User> users =  db.userDAO().getAll();
        for (int i = 0; i < users.size(); i++) {
            Log.d("User " + i, users.get(i).getNaam() + " - " + users.get(i).getLoggedIn());
        }
    }
}
