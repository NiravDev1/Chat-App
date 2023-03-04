package com.example.mychat.Home.scratchcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mychat.Home.Call.CallFragment;
import com.example.mychat.Home.Chats.ChatsFragment;
import com.example.mychat.Home.Setting.SettingFragment;
import com.example.mychat.Home.Users.UsersFragment;
import com.example.mychat.Home.Users.UserslistAdapter;
import com.example.mychat.R;
import com.example.mychat.databinding.ActivityHomeBinding;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    DatabaseReference databaseReference;
    UserslistAdapter userslistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.main_framlayout_id,new ChatsFragment()).commit();
        binding.mainBottomNaviViewId.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;

                switch (item.getItemId())
                {
                    case R.id.users_menu_id:
                        fragment=new UsersFragment();
                        break;
                    case R.id.chats_menu_id:
                        fragment=new ChatsFragment();
                        break;
                    case R.id.call_menu_id:
                        fragment=new CallFragment();
                        break;
                    case R.id.setting_menu_id:
                        fragment=new SettingFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_framlayout_id,fragment).commit();

                return true;
            }
        });




    }
}