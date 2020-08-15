package com.example.iiatimd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, User.class}, version = 22)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();
    public abstract UserDAO userDAO();

    private static AppDatabase instance;

    static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "iiatimd").fallbackToDestructiveMigration().build();
    }
}
