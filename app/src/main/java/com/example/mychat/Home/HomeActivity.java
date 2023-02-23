package com.example.mychat.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mychat.Authentication.UserModelClass;
import com.example.mychat.R;
import com.example.mychat.databinding.ActivityHomeBinding;
import com.example.mychat.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    DatabaseReference databaseReference;
    UserslistAdapter userslistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        userslistAdapter=new UserslistAdapter(this);

        binding.userRecyclerview.setAdapter(userslistAdapter);
        binding.userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userslistAdapter.claear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                        String uid=dataSnapshot.getKey();
                        if (!uid.equals(FirebaseAuth.getInstance().getUid()))
                        {
                            System.out.println(dataSnapshot);
                            UserModelClass userModelClass=dataSnapshot.getValue(UserModelClass.class);
                            userslistAdapter.add(userModelClass);

                        }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}