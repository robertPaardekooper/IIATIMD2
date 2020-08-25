package com.example.iiatimd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int uuid;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String barcode;

    @ColumnInfo
    private String expirationDate;

    @ColumnInfo
    private String note;

    public Product (int uuid, String name, String barcode, String expirationDate, String note){
        this.uuid = uuid;
        this.name = name;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.note = note;
    }

    public int getUuid(){ return this.uuid; }

    public String getName(){
        return this.name;
    }

    public String getBarcode(){
        return this.barcode;
    }

    public String getExpirationDate(){
        return this.expirationDate;
    }

    public String getNote(){
        return this.note;
    }
}
