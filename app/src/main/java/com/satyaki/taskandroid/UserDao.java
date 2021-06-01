package com.satyaki.taskandroid;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM crew_table")
    List<Users> getUsers();

    @Insert
    void insertUser(Users user);

    @Delete
    void deleteUser(Users user);

    @Query("DELETE FROM crew_table")
    void deleteTable();

}
