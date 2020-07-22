package com.example.iiatimd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @ColumnInfo
    private String naam;

    @PrimaryKey
    private int uuid;

    public Product (String naam, int uuid){
        this.naam = naam;
        this.uuid = uuid;
    }

    public String getNaam(){
        return this.naam;
    }

    public int getUuid(){
        return this.uuid;
    }


}
