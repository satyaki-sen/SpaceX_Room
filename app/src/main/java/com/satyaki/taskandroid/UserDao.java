package com.satyaki.taskandroid;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface UserDao {

    @Query("SELECT * FROM crew_table")
    public LiveData<List<Users>> getUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Users> usersList);

    @Delete
    public Completable deleteUser(Users user);

    @Query("DELETE FROM crew_table")
    public Completable deleteTable();

}
