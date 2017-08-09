package com.chris.tatusafety.UI;

import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        getTweets(traffic);
        Toast.makeText(TweetsActivity.this, "Disclaimer!! We(Tatu Safety) do not own or produce , neither are we directly associated with the User / Tweeter any of the tweets displayed here.", Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTweets(traffic);
                Toast.makeText(TweetsActivity.this,"Updating...",Toast.LENGTH_SHORT).show();
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

}


