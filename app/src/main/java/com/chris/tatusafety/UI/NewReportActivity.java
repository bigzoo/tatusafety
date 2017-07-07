package com.chris.tatusafety.UI;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.tatusafety.Database;
import com.chris.tatusafety.R;
import com.chris.tatusafety.TimePickerFragment;
import com.chris.tatusafety.services.SyncService;

import java.util.Calendar;

public class NewReportActivity extends FragmentActivity{
    TextView tvLatitude, tvLongitude, tvSpeed, tvDateView, tvTimeView;
    LocationManager locManager;
    double latitude,longitude;
    float speed;
    Calendar calendar;
    int year, month, day;
    EditText road,sacco,plates,county,extras;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);
        road = (EditText) findViewById(R.id.edtRoadName);
        sacco = (EditText) findViewById(R.id.edtSacco);
        plates = (EditText) findViewById(R.id.edtPlates);
        county = (EditText) findViewById(R.id.edtCounty);
        extras = (EditText) findViewById(R.id.edtExtras);
        tvTimeView = (TextView) findViewById(R.id.tvTimeView);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReport();
            }
        });
        //datepicker action
        tvDateView = (TextView) findViewById(R.id.tvDateView);
        //tvDateView.setVisibility(tvDateView.INVISIBLE);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month, day);

        tvLatitude = (TextView) findViewById(R.id.tvLatitudeAuto);
        tvLongitude = (TextView) findViewById(R.id.tvLongitudeAuto);
        tvSpeed = (TextView) findViewById(R.id.tvSpeedAuto);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 2, new LocationListener() {

            @Override
            public void onLocationChanged(Location location){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                speed = location.getSpeed();
                speed = speed *18/5;
                tvLongitude.append(String.format("%.2f", location.getLongitude()));
                tvLatitude.append(String.format("%.2f", location.getLatitude()));
                tvSpeed.append(String.format("%.2f", speed));
                Log.d("LOGLong",String.valueOf(longitude));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(NewReportActivity.this, "GPS Updating", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(NewReportActivity.this, "GPS Enabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(NewReportActivity.this, "GPS Disabled", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @SuppressWarnings("depreciation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if (id == 999){
            return new DatePickerDialog(this,myDateListener, year,month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    showDate(year,month,day);
                }
            };
    private void showDate(int year, int month, int day) {
        tvDateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
    public void setTime(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"TimePicker");
    }
    //id,latitude,longitude,date,time,road,sacco,speed,plates,county,extras,status

    public void submitReport() {
        final String Latitude = Double.toString(latitude);
        final String Longitude = Double.toString(longitude);
        final String Date = tvDateView.getText().toString();
        final String Time = tvTimeView.getText().toString();
        final String Road = road.getText().toString().trim();
        final String Sacco = sacco.getText().toString().trim();
        final String Speed = Float.toString(speed);
        final String Plates = plates.getText().toString();
        final String County = county.getText().toString();
        final String Extras = extras.getText().toString();
        final String Status = "unsynced";
//        if (speed<0) {
//            Log.d("DATE PICKED",Date);
//            Toast.makeText(this, "You cannot submit report with a speed value less than 60!", Toast.LENGTH_SHORT).show();
//        }
//        else {
            Report report = new Report(null,Latitude,Longitude,Date,Time,Road,Sacco,Speed,Plates,County,Extras,Status);
            Database db = new Database(this);
            db.insertReport(report);
            //Toast.makeText(this, "Total Submitted is "+db.countRecords(), Toast.LENGTH_SHORT).show();
            tvDateView.setText("");
            tvTimeView.setText("");
            road.setText("");
            sacco.setText("");
            plates.setText("");
            county.setText("");
            extras.setText("");
            Intent i=new Intent(this,SyncService.class);
            this.startService(i);

            db.fetchUnsyncedRecords();
            Toast.makeText(this, "Record Saved Succesfully", Toast.LENGTH_SHORT).show();
             Log.d("JSON_DATA", db.fetchUnsyncedRecords());



//        }
    }
}