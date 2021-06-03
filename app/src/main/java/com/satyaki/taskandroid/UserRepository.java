package com.satyaki.taskandroid;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

  private UserDao userDao;
  private LiveData<List<Users>> allUsers;
  private MutableLiveData<List<Users>> usersDetailsAPI;
  private Networking networking;
  private List<Users> lists;

  public UserRepository(Application application){

     AppDatabase appDatabase=AppDatabase.getInstance(application);
     Log.i("Chk",appDatabase.toString());
     userDao=appDatabase.userDao();
     Log.i("Ck",userDao.toString());
    // allUsers=userDao.getUsers();
     networking=RetrofitService.createService().create(Networking.class);
  }

  public void insertDataUsersAll(List<Users> list){

      Log.i("OK","Good Recieve.");

      lists=list;
      InsertData insertData=new InsertData();
      insertData.execute("");

      /* userDao.insertAll(list).subscribeWith(new CompletableObserver() {
         @Override
         public void onSubscribe(@NonNull Disposable d) {

         }

         @Override
         public void onComplete() {
            Log.i("OK","Good");
         }

         @Override
         public void onError(@NonNull Throwable e) {
            e.printStackTrace();
         }
     });*/
  }

   public LiveData<List<Users>> getAllUsers(){

      allUsers=userDao.getUsers();
      return allUsers;
   }

   public void deleteDataUsers(){

      userDao.deleteTable();
   }


   public MutableLiveData<List<Users>> getUsersREST(){

      usersDetailsAPI=new MutableLiveData<>();
      networking.listRepos().enqueue(new Callback<List<Users>>() {
          @Override
          public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {

              if(response.isSuccessful()){
                  usersDetailsAPI.setValue(response.body());
                  insertDataUsersAll(response.body());
              }
              else{
                  Log.i("Error Resp",String.valueOf(response.code()));
                  Log.i("Msg Resp",response.message());
                  Log.i("Title",response.body().toString());
              }
          }

          @Override
          public void onFailure(Call<List<Users>> call, Throwable t) {
              Log.i("FAILL","FAILL");
              Log.i("THrow",t.getMessage());
          }
      });

      return usersDetailsAPI;
   }

   public class InsertData extends AsyncTask<String,String,String>{

       @Override
       protected String doInBackground(String... strings) {
           try {
               userDao.insertAll(lists);
           }
           catch (Exception e){
               e.printStackTrace();
           }
           return null;
       }

       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);

       }
   }



}
