package com.example.mychat.Home.Users;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mychat.Authentication.UserModelClass;
import com.example.mychat.Home.scratchcode.ChatActivity;
import com.example.mychat.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserslistAdapter extends RecyclerView.Adapter<UserslistAdapter.userlistvieholder> {

    Context context;
    List<UserModelClass> userModelClassList;


    public UserslistAdapter(Context context) {
        this.context = context;
        userModelClassList =new ArrayList<>();
    }


    public void adduser(UserModelClass userModelClass)
    {
        userModelClassList.add(userModelClass);
        notifyDataSetChanged();

    }
    public void claear()
    {
        userModelClassList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public userlistvieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_single_card,parent,false);
        return new userlistvieholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userlistvieholder holder, int position) {

        UserModelClass userModelClass=userModelClassList.get(position);

        holder.usernames.setText(userModelClass.getUserName());

        Glide.with(context).load(userModelClass.getUserProfile()).into(holder.usersimageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("Userid",userModelClass.getUserId());
                intent.putExtra("Username",userModelClass.getUserName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userModelClassList.size();
    }

    public class userlistvieholder extends RecyclerView.ViewHolder{

        CircleImageView usersimageView;
        TextView usernames;
        public userlistvieholder(@NonNull View itemView) {
            super(itemView);
            usersimageView=itemView.findViewById(R.id.users_profile_card);
            usernames=itemView.findViewById(R.id.users_name_card);

        }
    }
}
