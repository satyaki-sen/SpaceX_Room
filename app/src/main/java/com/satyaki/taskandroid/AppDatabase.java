package com.satyaki.taskandroid;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Users.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase instance;

    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "SpaceX-Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
