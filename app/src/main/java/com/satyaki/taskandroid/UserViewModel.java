package com.satyaki.taskandroid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<Users>> listUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository=new UserRepository(application);
        listUsers=userRepository.getAllUsers();
    }

    public void insertUsersAll(List<Users> list){
        userRepository.insertDataUsersAll(list);
    }

    public void deleteAll(){
        userRepository.deleteDataUsers();
    }

    public LiveData<List<Users>> getAllUsers(){
        return listUsers;
    }

}