package com.satyaki.taskandroid;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {

  private UserDao userDao;
  private LiveData<List<Users>> allUsers;

  public UserRepository(Application application){
     AppDatabase appDatabase=AppDatabase.getInstance(application);
     userDao=appDatabase.userDao();
     allUsers=userDao.getUsers();
  }

  public void insertDataUsersAll(List<Users> list){
      userDao.insertAll(list);
  }

   public LiveData<List<Users>> getAllUsers(){
      return allUsers;
   }

   public void deleteDataUsers(){
      userDao.deleteTable();
   }

}
