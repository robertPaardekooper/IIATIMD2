package com.example.iiatimd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uuid;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String email;

    @ColumnInfo
    private String password;

    @ColumnInfo
    private boolean loggedIn;

    public User (int uuid, String name, String email, String password, boolean loggedIn){
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.loggedIn = loggedIn;
    }

    public int getUuid(){
        return this.uuid;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public boolean getLoggedIn(){
        return this.loggedIn;
    }
}
