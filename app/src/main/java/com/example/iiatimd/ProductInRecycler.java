package com.example.iiatimd;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;

public class ProductInRecycler {

    private String naam;

    private String barcode;

    private String soort;

    private Date houdbaarheidsdatum;

    private String notitie;

    public ProductInRecycler (String naam, String barcode, String soort, Date houdbaarheidsdatum, String notitie){
        this.naam = naam;
        this.barcode = barcode;
        this.soort = soort;
        this.houdbaarheidsdatum = houdbaarheidsdatum;
        this.notitie = notitie;
    }

    public String getNaam(){
        return this.naam;
    }

    public String getBarcode(){
        return this.barcode;
    }

    public String getSoort(){
        return this.soort;
    }

    public Date getHoudbaarheidsdatum(){
        return this.houdbaarheidsdatum;
    }

    public String getNotitie(){
        return this.notitie;
    }
}
