package com.satyaki.taskandroid;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.rxjava3.core.Completable;

@Dao
public interface UserDao {

    @Query("SELECT * FROM crw_table")
    public LiveData<List<Users>> getUsers();

    @Update
    public Completable insertAll(List<Users> usersList);

    @Delete
    public Completable deleteUser(Users user);

    @Query("DELETE FROM crw_table")
    public Completable deleteTable();

}
