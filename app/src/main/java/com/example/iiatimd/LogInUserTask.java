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
        db.userDAO().logInUser(email);

        List<User> users =  db.userDAO().getAll();
        for (int i = 0; i < users.size(); i++) {
            Log.d("User " + i, users.get(i).getNaam() + " - " + users.get(i).getLoggedIn());
        }
    }
}
