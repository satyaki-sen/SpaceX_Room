package com.satyaki.taskandroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class HomeFragment extends Fragment {

    List<Users> userDetails;
    UserViewModel userViewModel;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    UserDetailsAdapter userDetailsAdapter;
    FloatingActionButton refresh,delete;
    TextView textEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recycler_main);
        refresh=view.findViewById(R.id.floatingRefresh);
        delete=view.findViewById(R.id.floatingDelete);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        textEmpty=view.findViewById(R.id.textEmpty);

        userViewModel= new ViewModelProvider(getActivity()).get(UserViewModel.class);

        if(hasNetwork()){
            Toast.makeText(getActivity(), "Online..", Toast.LENGTH_SHORT).show();
            retrieveREST();
        }
        else{
            retrieveRoom();
        }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                //Deletes data from Room Database
                textEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                userViewModel.deleteAll();
                retrieveREST();//Fetch data from REST APIs..
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
                userViewModel.deleteAll(); //Deletes the entire Data from the Room Database
                recyclerView.setVisibility(View.GONE);
                textEmpty.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    //Fetch data from Room Database
    public void retrieveRoom(){

        userViewModel.getAllUsers().observe(getActivity(), new Observer<List<Users>>() {

            @Override
            public void onChanged(List<Users> usersList) {

                recyclerView.setVisibility(View.VISIBLE);
                userDetails = usersList;
                Log.i("Chek",userDetails.get(0).getName());
                userDetailsAdapter = new UserDetailsAdapter(userDetails, getActivity());
                 //  userDetailsAdapter.notifyDataSetChanged();

                recyclerView.setAdapter(userDetailsAdapter);
                userDetailsAdapter.notifyDataSetChanged();
                   textEmpty.setVisibility(View.GONE);


            }
        });
    }

    //Fetch data from REST APIs..
    public void retrieveREST(){

        userViewModel.getListUserDetailsAPI().observe(getActivity(), new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> usersList) {

                textEmpty.setVisibility(View.GONE);
                userDetails=usersList;
                userDetailsAdapter=new UserDetailsAdapter(userDetails,getContext());
                recyclerView.setAdapter(userDetailsAdapter);
            }
        });
    }


    public Boolean hasNetwork(){

        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}