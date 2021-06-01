package com.satyaki.taskandroid;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.rxjava3.core.Completable;

@Dao
public interface UserDao {

    @Query("SELECT * FROM crew_table")
    public List<Users> getUsers();

    @Insert
    void insertUser(Users user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertAll(List<Users> usersList);

    @Delete
    void deleteUser(Users user);

    @Query("DELETE FROM crew_table")
    void deleteTable();

}
