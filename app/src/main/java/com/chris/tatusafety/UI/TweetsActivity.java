package com.chris.tatusafety.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.chris.tatusafety.Modules.Tweet;
import com.chris.tatusafety.R;
import com.chris.tatusafety.services.TwitterService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TweetsActivity extends AppCompatActivity {


    @Bind(R.id.tweetsRecyclerView) RecyclerView mRecyclerView;

    public static final String TAG = TweetsActivity.class.getSimpleName();

    public ArrayList<Tweet> mTweets = new ArrayList<>();
    private TweetsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);
        ButterKnife.bind(this);
        String traffic = "Ma3Route";
        getTweets(traffic);
        Toast.makeText(TweetsActivity.this,"Fetching your traffic updates.Chill Kiasi...",Toast.LENGTH_SHORT).show();


//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void getTweets(final String topic) {
        final TwitterService twitterService = new TwitterService();
        twitterService.findTweets(topic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Traffic Activity","Failed to make API call");
                Toast.makeText(TweetsActivity.this,"Check your internet connection",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                mTweets = twitterService.processResults(response);
                TweetsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new TweetsListAdapter(getApplicationContext(),mTweets);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager  = new LinearLayoutManager(TweetsActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
//                        Drawable dividerDrawable = ContextCompat.getDrawable(TweetsActivity.this,R.drawable.line_bottom);
//                       mRecyclerView.addItemDecoration(new VerticalSpacingDecoration(dividerDrawable));
                    }
                });

            }
            });

            }

    }


