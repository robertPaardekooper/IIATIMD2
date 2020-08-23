package com.example.iiatimd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int uuid;

    @ColumnInfo
    private String naam;

    @ColumnInfo
    private String barcode;

    @ColumnInfo
    private String soort;

    @ColumnInfo
    private String houdbaarheidsdatum;

    @ColumnInfo
    private String notitie;

    public Product (int uuid, String naam, String barcode, String soort, String houdbaarheidsdatum, String notitie){
        this.uuid = uuid;
        this.naam = naam;
        this.barcode = barcode;
        this.soort = soort;
        this.houdbaarheidsdatum = houdbaarheidsdatum;
        this.notitie = notitie;
    }

    public int getUuid(){ return this.uuid; }

    public String getNaam(){
        return this.naam;
    }

    public String getBarcode(){
        return this.barcode;
    }

    public String getSoort(){
        return this.soort;
    }

    public String getHoudbaarheidsdatum(){
        return this.houdbaarheidsdatum;
    }

    public String getNotitie(){
        return this.notitie;
    }
}
