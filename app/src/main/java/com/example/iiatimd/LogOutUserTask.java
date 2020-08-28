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

        List<User> users =  db.userDAO().getAll();
        for (int i = 0; i < users.size(); i++) {
            Log.d("User " + i, users.get(i).getName() + " - " + users.get(i).getLoggedIn());
        }
    }
}
