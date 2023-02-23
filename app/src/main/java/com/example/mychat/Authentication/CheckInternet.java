package com.example.mychat.Authentication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet {

    public static String getNetworkinfo(Context context)
    {
        String status=null;
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null)
        {
            status="connected";
        }
        else {
            status="Disconnected";
        }
        return status;
    }
}
