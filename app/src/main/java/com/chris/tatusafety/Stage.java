package com.chris.tatusafety;


import android.*;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chris.tatusafety.UI.AccountActivity;
import com.chris.tatusafety.UI.HelpActivity;
import com.chris.tatusafety.UI.HistoryActivity;
import com.chris.tatusafety.UI.NewReportActivity;
import com.chris.tatusafety.UI.SettingsActivity;
import com.chris.tatusafety.UI.SpeedActivity;
import com.chris.tatusafety.UI.TweetsActivity;
import com.chris.tatusafety.maps.FindMeActivity;

/**
 * Created by Josephine Menge on 05/07/2017.
 */

public class Stage extends AppCompatActivity {

    SpeedActivity myService;
    static boolean status;
    LocationManager locationManager;
    public static TextView dist, time, speed;
    Button start, pause, stop;
    public static long startTime, endTime;
    ImageView image;
    public static ProgressDialog locate;
    public static int p = 0;

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SpeedActivity.LocalBinder binder = (SpeedActivity.LocalBinder) service;
            myService = binder.getService();
            status = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            status = false;
        }
    };

    void bindService() {
        if (status == true)
            return;
        Intent i = new Intent(getApplicationContext(), SpeedActivity.class);
        bindService(i, sc, BIND_AUTO_CREATE);
        status = true;
        startTime = System.currentTimeMillis();
    }

    void unbindService() {
        if (status == false)
            return;
        Intent i = new Intent(getApplicationContext(), SpeedActivity.class);
        unbindService(sc);
        status = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (status == true)
            unbindService();
    }

    @Override
    public void onBackPressed() {
//        if (status == false)
//            super.onBackPressed();
//        else
//            moveTaskToBack(true);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stages);


        dist = (TextView) findViewById(R.id.distancetext);
        time = (TextView) findViewById(R.id.timetext);
        speed = (TextView) findViewById(R.id.speedtext);

        start = (Button) findViewById(R.id.start);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);

        image = (ImageView) findViewById(R.id.image);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 21) {
                ActivityCompat.requestPermissions(Stage.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
                ActivityCompat.requestPermissions(Stage.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
            return;
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //The method below checks if Location is enabled on device or not. If not, then an alert dialog box appears with option
                //to enable gps.
                checkGps();
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    return;
                }


                if (status == false)
                    //Here, the Location Service gets bound and the GPS Speedometer gets Active.
                    bindService();
                locate = new ProgressDialog(Stage.this);
                locate.setIndeterminate(true);
                locate.setCancelable(false);
                locate.setMessage("Getting Location...");
                locate.show();
                start.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                pause.setText("Pause");
                stop.setVisibility(View.VISIBLE);


            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause.getText().toString().equalsIgnoreCase("pause")) {
                    pause.setText("Resume");
                    p = 1;

                } else if (pause.getText().toString().equalsIgnoreCase("Resume")) {
                    checkGps();
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    pause.setText("Pause");
                    p = 0;

                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == true)
                    unbindService();
                start.setVisibility(View.VISIBLE);
                pause.setText("Pause");
                pause.setVisibility(View.GONE);
                stop.setVisibility(View.GONE);
                p = 0;
            }
        });
    }


    //This method leads you to the alert dialog box.
    void checkGps() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            showGPSDisabledAlertToUser();
        }
    }

    //This method configures the Alert Dialog box.
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Enable GPS to use application")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}

