package com.example.mychat.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.mychat.R;
import com.example.mychat.databinding.ActivityMainBinding;

public class AuthActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BroadcastReceiver broadcastReceiver=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();



//        broadcastReceiver= new InternetReceiver();
//        Internetsatatus();
        getSupportFragmentManager().beginTransaction().replace(R.id.auh_framlayout ,new LoginFragment()).commit();
//




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