package com.chris.tatusafety;

import android.app.Service;
;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SyncService extends Service{
    @Override
    public int onStartCommand(Intent intent , int flags, int startId) {

        //TODO: send db items to the server
        String url="http://www.thebigzoo.co.ke/tatusafety/api.php";
        final Database db = new Database(this);
        String json = db.fetchUnsyncedRecords();
        RequestParams params = new RequestParams();
        params.put("json",  json);
        AsyncHttpClient client =new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.d("RESPONSE_DATA",response);
                //TODO update db
                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0; i<array.length(); i++)
                    {
                        JSONObject obj = array.getJSONObject(i);
                        String id = obj.getString("id");
                        db.update(id);
                        Log.d("RESPONSE_ID", id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("RESPONSE_DATA","UNSUCCESSFUL. TRY AGAIN");
            }
        });
        return Service.START_NOT_STICKY;
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
