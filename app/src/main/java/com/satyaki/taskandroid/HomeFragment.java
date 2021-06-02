package com.satyaki.taskandroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import java.util.List;


public class HomeFragment extends Fragment {

    List<Users> userDetails;
    UserViewModel userViewModel;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    UserDetailsAdapter userDetailsAdapter;
    Button refresh,delete;
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

        if(userViewModel.hasNetwork()){
            retrieveREST();
        }
        else{
            retrieveRoom();
        }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Deletes data from Room Database
                userViewModel.deleteAll();
                retrieveREST();//Fetch data from REST APIs..
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.deleteAll(); //Deletes the entire Data from the Room Database
            }
        });

        return view;
    }

    //Fetch data from Room Database
    public void retrieveRoom(){

        userViewModel.getAllUsers().observe(getActivity(), new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> usersList) {

               if(!usersList.isEmpty()) {
                   userDetails = usersList;
                   userDetailsAdapter = new UserDetailsAdapter(userDetails, getActivity());
                   recyclerView.setAdapter(userDetailsAdapter);
                   textEmpty.setVisibility(View.GONE);
               }
               else{
                    recyclerView.setVisibility(View.GONE);
                    textEmpty.setVisibility(View.VISIBLE);
               }
            }
        });
    }

    //Fetch data from REST APIs..
    public void retrieveREST(){

        userViewModel.getListUserDetailsAPI().observe(getActivity(), new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> usersList) {
                userDetails=usersList;
                userDetailsAdapter=new UserDetailsAdapter(userDetails,getContext());
                recyclerView.setAdapter(userDetailsAdapter);
            }
        });
    }
}