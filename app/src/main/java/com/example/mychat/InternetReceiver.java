package com.example.mychat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class InternetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = CheckInternet.getNetworkinfo(context);

        if (status.equals("connected")) {
            Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
        } else if (status.equals("Disconnected")) {
            Toast.makeText(context, "network disconnected", Toast.LENGTH_SHORT).show();
        }

    }
}
