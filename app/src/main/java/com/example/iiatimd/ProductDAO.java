package com.example.iiatimd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Insert
    void insertProduct(Product product);

    @Delete
    void delete(Product product);
}
