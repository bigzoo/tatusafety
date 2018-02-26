package com.chris.tatusafety.UI;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.chris.tatusafety.Stage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class SpeedActivity extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {


        private static final long INTERVAL = 1000 * 4;
        private static final long FASTEST_INTERVAL = 1000 * 1;
        LocationRequest mLocationRequest;
        GoogleApiClient mGoogleApiClient;
        Location mCurrentLocation, lStart, lEnd;
        static double distance = 0;
        double speed;


        private final IBinder mBinder = new LocalBinder();

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            createLocationRequest();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mGoogleApiClient.connect();
            return mBinder;
        }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onConnected(Bundle bundle) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
        }
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        distance = 0;
    }


    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {
        Stage.locate.dismiss();
        mCurrentLocation = location;
        if (lStart == null) {
            lStart = mCurrentLocation;
            lEnd = mCurrentLocation;
        } else
            lEnd = mCurrentLocation;

        //Calling the method below updates the  live values of distance and speed to the TextViews.
        updateUI();
        //calculating the speed with getSpeed method it returns speed in m/s so we are converting it into kmph
        speed = location.getSpeed() * 18 / 5;

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public class LocalBinder extends Binder {

        public SpeedActivity getService() {
            return SpeedActivity.this;
        }


    }

    //The live feed of Distance and Speed are being set in the method below .
    private void updateUI() {
        if (Stage.p == 0) {
            distance = distance + (lStart.distanceTo(lEnd) / 1000.00);
            Stage.endTime = System.currentTimeMillis();
            long diff = Stage.endTime - Stage.startTime;
            diff = TimeUnit.MILLISECONDS.toMinutes(diff);
            Stage.time.setText("Total Time: " + diff + " minutes");
            if (speed > 0.0)
                Stage.speed.setText("Current speed: " + new DecimalFormat("#.##").format(speed) + " km/hr");
            else
                Stage.speed.setText(".......");

            Stage.dist.setText(new DecimalFormat("#.###").format(distance) + " Km.");

            lStart = lEnd;

        }

    }


    @Override
    public boolean onUnbind(Intent intent) {
        stopLocationUpdates();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        lStart = null;
        lEnd = null;
        distance = 0;
        return super.onUnbind(intent);
    }
}

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_speed);
//        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//
//        this.onLocationChanged(null);
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        TextView txt = (TextView) this.findViewById(R.id.textView5);
//
//        if(location == null) {
//            txt.setText("-.- m/s");
//        } else {
//            float nCurrentSpeed = location.getSpeed();
//            txt.setText(nCurrentSpeed + " m/s");
//        }
//
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
