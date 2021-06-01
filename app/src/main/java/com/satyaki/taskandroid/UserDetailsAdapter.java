package com.satyaki.taskandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.ViewHolder> {

    private List<Users> usersList;
    private Context context;

    public UserDetailsAdapter(List<Users> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_recycler_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDetailsAdapter.ViewHolder holder, int position) {

        Users user=(Users) usersList.get(position);
        holder.textName.setText(user.getName());
        holder.textAgency.setText(user.getAgency());
        holder.textWiki.setText(user.getWikipedia());
        holder.textStatus.setText(user.getStatus());
        Picasso.get().load(user.getImage()).into(holder.imageCrew);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageCrew;
        TextInputEditText textName,textStatus,textWiki,textAgency;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCrew=itemView.findViewById(R.id.image_crew);
            textName=itemView.findViewById(R.id.textEdit_Name);
            textAgency=itemView.findViewById(R.id.textEdit_Agency);
            textStatus=itemView.findViewById(R.id.textEdit_Status);
            textWiki=itemView.findViewById(R.id.textEdit_Wiki);
        }
    }

}
