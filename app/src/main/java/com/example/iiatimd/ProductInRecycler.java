package com.example.iiatimd;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;

public class ProductInRecycler {

    private String name;

    private String barcode;

    private String expirationDate;

    private String note;

    public ProductInRecycler (String name, String barcode, String expirationDate, String note){
        this.name = name;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.note = note;
    }

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
