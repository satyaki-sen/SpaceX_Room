package com.satyaki.taskandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppDatabase db=AppDatabase.getInstance(this);

        UserDao userDao = db.userDao();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spacexdata.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Users>> repos = service.listRepos();

       repos.enqueue(new Callback<List<Users>>() {
           @Override
           public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {

               List<Users> usersList=response.body();

               int i=0;
               for(Users user:usersList){
                   Log.i("User",user.getName());
                   i+=1;
                   userDao.insertUser(user);
                   if(i==usersList.size()){
                       Toast.makeText(MainActivity.this, "Done..", Toast.LENGTH_SHORT).show();
                   }
               }

           }

           @Override
           public void onFailure(Call<List<Users>> call, Throwable t) {

           }
       });

    }

    public interface GitHubService {
        @GET("crew")
        Call<List<Users>> listRepos();
    }

}