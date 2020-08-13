package com.example.iiatimd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uuid;

    @ColumnInfo
    private String naam;

    @ColumnInfo
    private String email;

    @ColumnInfo
    private String wachtwoord;

    @ColumnInfo
    private boolean loggedIn;

    public User (int uuid, String naam, String email, String wachtwoord, boolean loggedIn){
        this.uuid = uuid;
        this.naam = naam;
        this.email = email;
        this.wachtwoord = wachtwoord;
        this.loggedIn = loggedIn;
    }

    public int getUuid(){
        return this.uuid;
    }

    public String getNaam(){
        return this.naam;
    }

    public String getEmail(){
        return this.email;
    }

    public String getWachtwoord(){
        return this.wachtwoord;
    }

    public boolean getLoggedIn(){
        return this.loggedIn;
    }
}
