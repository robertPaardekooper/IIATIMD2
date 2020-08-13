package com.example.iiatimd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE loggedIn = 1")
    List<User> getLoggedInUser();

    @Query("UPDATE user SET loggedIn = 0 WHERE loggedIn = 1")
    void logOutUser();

    @Query("UPDATE user SET loggedIn = 1 WHERE email = :email")
    void logInUser(String email);

    @Insert
    void insertUser(User user);

    @Delete
    void delete(User user);
}
