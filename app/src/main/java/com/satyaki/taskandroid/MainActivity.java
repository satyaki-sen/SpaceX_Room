package com.satyaki.taskandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    List<Users> userDetails;
    UserDao userDao;
    UserViewModel userViewModel;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    UserDetailsAdapter userDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler_main);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        userViewModel= new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> usersList) {
                Log.i("Hello ",usersList.get(0).getImage());
                userDetails=usersList;
                userDetailsAdapter=new UserDetailsAdapter(userDetails,MainActivity.this);
                recyclerView.setAdapter(userDetailsAdapter);
            }
        });

       // userDetailsAdapter=new UserDetailsAdapter(userDetails,this);
        //recyclerView.setAdapter(userDetailsAdapter);


  //      AppDatabase db=AppDatabase.getInstance(this);
//        userDao = db.userDao();

       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spacexdata.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Users>> repos = service.listRepos();



       repos.enqueue(new Callback<List<Users>>() {
           @Override
           public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {

               usersList=response.body();
               userDao.insertAll(usersList);
               See see=new See();
               see.execute();
           }

           @Override
           public void onFailure(Call<List<Users>> call, Throwable t) {

           }
       });*/

    }

  /* public interface GitHubService {
        @GET("crew")
        Call<List<Users>> listRepos();
    }


    public class See extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            Log.i("Hello",userDao.getUsers().get(0).getName());
            return null;
        }
    }*/

}