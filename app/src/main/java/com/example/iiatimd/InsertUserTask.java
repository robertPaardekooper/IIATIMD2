package com.example.iiatimd;

import android.util.Log;

import java.util.List;

public class InsertUserTask implements Runnable{

    AppDatabase db;
    User user;

    public InsertUserTask(AppDatabase db, User user){
        this.db = db;
        this.user = user;
    }

    @Override
    public void run() {
        db.userDAO().logOutUser();
        db.userDAO().insertUser(this.user);

        // TODO: log verwijderen
        List<User> users =  db.userDAO().getAll();
        for (int i = 0; i < users.size(); i++) {
            Log.d("User " + i, users.get(i).getName() + " - " + users.get(i).getLoggedIn());
        }
    }
}
