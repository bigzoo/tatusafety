package com.chris.tatusafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chris.tatusafety.UI.Report;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.UUID;

public class Database extends SQLiteOpenHelper {

    private static final int VERSION = 4;
    private static final String DB_NAME = "tatusafety";
    private static final String ID = "id";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String ROAD = "road";
    private static final String SACCO = "sacco";
    private static final String SPEED = "speed";
    private static final String PLATES = "plates";
    private static final String COUNTY = "county";
    private static final String EXTRAS = "extras";
    private static final String STATUS = "status";
    private static final String UUID = "uuid";


    private static final String TABLE = "reports";
    Context context;
    public Database(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS reports "
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " latitude TEXT, "
                + " longitude TEXT, "
                + " date TEXT, "
                + " time TEXT, "
                + " road TEXT, "
                + " sacco TEXT, "
                + " speed TEXT, "
                + " plates TEXT, "
                + " county TEXT, "
                + " extras TEXT, "
                + " status TEXT, "
                + " uuid TEXT)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS reports";
        db.execSQL(sql);
        String sql2 = "CREATE TABLE IF NOT EXISTS reports "
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " latitude TEXT, "
                + " longitude TEXT, "
                + " date TEXT, "
                + " time TEXT, "
                + " road TEXT, "
                + " sacco TEXT, "
                + " speed TEXT, "
                + " plates TEXT, "
                + " county TEXT, "
                + " extras TEXT, "
                + " status TEXT, "
                + " uuid TEXT)";
        db.execSQL(sql2);
    }
    public void insertReport(Report report) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID,report.getId());
        values.put(LATITUDE, report.getLatitude());
        values.put(LONGITUDE, report.getLongitude());
        values.put(DATE, report.getDate());
        values.put(TIME, report.getTime());
        values.put(ROAD, report.getRoad());
        values.put(SACCO, report.getSacco());
        values.put(SPEED, report.getSpeed());
        values.put(PLATES, report.getPlates());
        values.put(COUNTY, report.getCounty());
        values.put(EXTRAS, report.getExtras());
        values.put(STATUS, "unsynced");
        values.put(UUID,report.getUuid());
        db.insert(TABLE, null, values);
        db.close();
    }
    public String fetchUnsyncedRecords(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Report> data=new ArrayList<Report>();
        Cursor cursor=db.rawQuery("SELECT * FROM reports WHERE status='"+"unsynced"+"'", null);
        Log.d("TOTAL_DATA","FETCHED "+cursor.getCount()) ;
        if(cursor.moveToFirst()){
            do
            {
                String id= cursor.getString(0);
                String latitude= cursor.getString(1);
                String longitude= cursor.getString(2);
                String date= cursor.getString(3);
                String time = cursor.getString(4);
                String road = cursor.getString(5);
                String sacco= cursor.getString(6);
                String speed= cursor.getString(7);
                String plates= cursor.getString(8);
                String county= cursor.getString(9);
                String extras= cursor.getString(10);
                String status= cursor.getString(11);
                String uuid= cursor.getString(12);
                data.add(new Report(id,latitude,longitude,date,time,road,sacco,speed,plates,county,extras,status,uuid));
//                Log.d('DATA',data);

            }while (cursor.moveToNext());
        }
        Gson g=new Gson();
        String json = g.toJson(data);
        Log.d("JSONDATA",json);
        return json;

    }

    public String getLastID()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT id FROM reports ORDER BY id DESC LIMIT 1", null);
        if(cursor.moveToFirst())
        {
            String id=cursor.getString(0);
            return id;
        }
        return "";
    }
    public void update(String uuid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("UPDATE reports SET status='synced' WHERE uuid='"+uuid+"'", null);
        if (cursor.moveToFirst()){

        }
    }


    public int countRecords()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT id FROM reports", null);
        return cursor.getCount();
    }
    public ArrayList getAllRecords(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Report> data=new ArrayList<Report>();
        Cursor cursor=db.rawQuery("SELECT * FROM reports ", null);
        Log.d("TOTAL_DATA","FETCHED "+cursor.getCount()) ;
        if(cursor.moveToFirst()){
            do
            {
                String id= cursor.getString(0);
                String latitude= cursor.getString(1);
                String longitude= cursor.getString(2);
                String date= cursor.getString(3);
                String time = cursor.getString(4);
                String road = cursor.getString(5);
                String sacco= cursor.getString(6);
                String speed= cursor.getString(7);
                String plates= cursor.getString(8);
                String county= cursor.getString(9);
                String extras= cursor.getString(10);
                String status= cursor.getString(11);
                String uuid = cursor.getString(12);
                data.add(new Report(id,latitude,longitude,date,time,road,sacco,speed,plates,county,extras,status,uuid));

            }while (cursor.moveToNext());
        } return data;
    }
    public void deleteRecord(String id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "DELETE from reports where id="+id;
        Log.d("query",updateQuery);
        database.delete("reports","id="+id,null);
        database.close();
    }

}




