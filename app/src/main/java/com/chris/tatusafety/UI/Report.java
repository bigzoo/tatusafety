package com.chris.tatusafety.UI;
//,id,latitude,longitude,date,time,road,sacco,speed,plates,county,extras,status
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Report {
    public String id, latitude, longitude, date, time, road, sacco, speed, plates, county, extras, status ;

    public Report(String date, String time, String road, String sacco) {
        this.date = date;
        this.time = time;
        this.road = road;
        this.sacco = sacco;
    }
    public Report(String latitude, String longitude, String date, String time, String road, String sacco, String speed, String plates, String county, String extras, String status) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.time = time;
        this.road = road;
        this.sacco = sacco;
        this.speed =speed;
        this.plates = plates;
        this.county =county;
        this.extras =extras;
        this.status =status;
    }

    public Report(String id, String latitude, String longitude, String date, String time, String road, String sacco, String speed, String plates, String county, String extras, String status)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.time = time;
        this.road = road;
        this.sacco = sacco;
        this.speed = speed;
        this.plates = plates;
        this.county = county;
        this.extras = extras;
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
    public String getRoad(){
        return road;
    }
    public String getSacco(){
        return sacco;
    }
    public String getSpeed(){
        return speed;
    }
    public String getPlates(){
        return plates;
    }
    public String getCounty(){
        return county;
    }
    public String getExtras(){
        return extras;
    }
    public String getStatus() {
        return status;
    }
}

