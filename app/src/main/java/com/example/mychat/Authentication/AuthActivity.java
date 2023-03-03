package com.example.mychat.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.mychat.Home.scratchcode.HomeActivity;
import com.example.mychat.R;
import com.example.mychat.databinding.ActivityAuthBinding;


public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding;
    BroadcastReceiver broadcastReceiver=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences sharedPreferences=getSharedPreferences("auth",MODE_PRIVATE);

        boolean check=sharedPreferences.getBoolean("flag",false);

        if (check)
        {
            startActivity(new Intent(AuthActivity.this,HomeActivity.class));
            finish();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.auh_framlayout ,new LoginFragment()).commit();
        }


//        broadcastReceiver= new InternetReceiver();
//        Internetsatatus();




    }
    public void Internetsatatus()
    {
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(broadcastReceiver);
    }
}