package com.chris.tatusafety.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.tatusafety.Modules.Tweet;
import com.chris.tatusafety.R;
import com.chris.tatusafety.adapters.TweetsListAdapter;
import com.chris.tatusafety.services.TwitterService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class TweetsActivity extends AppCompatActivity {


    @Bind(R.id.tweetsRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.traffic)TextView mTitle;
    SwipeRefreshLayout swipeRefreshLayout;

    public static final String TAG = TweetsActivity.class.getSimpleName();

    public ArrayList<Tweet> mTweets = new ArrayList<>();
    private TweetsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);
        ButterKnife.bind(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresher);
        final String traffic = "Ma3Route";

       if (haveNetworkConnection() == true){
           getTweets(traffic);
           Toast.makeText(TweetsActivity.this, "Fetching traffic updates ... ", Toast.LENGTH_LONG).show();
           mTitle.setText("Traffic updates");

       }
       else {
           Toast.makeText(TweetsActivity.this,"Please enable your internet connection",Toast.LENGTH_SHORT).show();
           mTitle.setText("No Internet connection available.");
       }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (haveNetworkConnection() == true){
                    getTweets(traffic);
                    Toast.makeText(TweetsActivity.this, "Fetching traffic updates ... ", Toast.LENGTH_LONG).show();
                    mTitle.setText("Traffic updates");

                }
                else {
                    Toast.makeText(TweetsActivity.this,"Please enable your internet connection",Toast.LENGTH_SHORT).show();
                    mTitle.setText("No Internet connection available.");
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });



//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
    }


    private void getTweets(final String topic) {
        final TwitterService twitterService = new TwitterService();
        twitterService.findTweets(topic, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                Log.e("Traffic Activity", "Failed to make API call");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                mTweets = twitterService.processResults(response);
                TweetsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mTweets.isEmpty()) {
                            Toast.makeText(TweetsActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                        } else {
                            mAdapter = new TweetsListAdapter(getApplicationContext(), mTweets);
                            mRecyclerView.setAdapter(mAdapter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TweetsActivity.this);
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setHasFixedSize(true);
//                        Drawable dividerDrawable = ContextCompat.getDrawable(TweetsActivity.this,R.drawable.line_bottom);
//                       mRecyclerView.addItemDecoration(new VerticalSpacingDecoration(dividerDrawable));
                        }
                    }
                });

            }
        });

    }



    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}


