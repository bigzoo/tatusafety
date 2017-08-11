package com.chris.tatusafety.maps;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.tatusafety.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class FindMeActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    TextView tvLatitude, tvLongitude, tvSpeed, tvAltitude;
    LocationManager locManager;
    private GoogleMap mMap;
    double latitude, longitude;
    @Bind(R.id.textView3) TextView mClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_me);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        ButterKnife.bind(this);
        mapFragment.getMapAsync(this);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvSpeed = (TextView) findViewById(R.id.tvSpeed);
        tvAltitude = (TextView) findViewById(R.id.tvAltitude);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mClick.setOnClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 2, new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                tvLongitude.setText("" + String.format("%.2f", location.getLongitude()));
                tvAltitude.setText("" + String.format("%.2f", location.getAltitude()));
                tvLatitude.setText("" + String.format("%.2f", location.getLatitude()));
                float speed = location.getSpeed();
                speed = speed * 18 / 5;
                tvSpeed.setText("" + String.format("%.2f", speed));
                Log.d("LOGLong", String.valueOf(longitude));
                onMapReady(mMap);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(FindMeActivity.this, "GPS Updating", Toast.LENGTH_SHORT).show();
                onMapReady(mMap);

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(FindMeActivity.this, "GPS Enabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(FindMeActivity.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
                Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(onGPS);

            }

        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        // Add a marker in current gps co-odinates and move the camera to that position
        LatLng locationRecieved = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(locationRecieved).title("You Are Here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationRecieved));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(FindMeActivity.this, "Please Enable GPS in permissions", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

    }
}


