package com.chris.tatusafety;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.chris.tatusafety.services.SyncService;

public class NetWatcher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info!=null)
        {
            if(info.isConnected())
            {
                Toast.makeText(context,"Tuliwasha sync service",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context,SyncService.class);
                context.startService(i);
            }
        }
    }
}