package com.chris.tatusafety.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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

//public class trafficActivity extends AppCompatActivity {
////    @Bind(R.id.trafficTextView) TextView mTextView;
//    @Bind(R.id.tweetsRecyclerView) RecyclerView mRecyclerView;
//
//    public static final String TAG = trafficActivity.class.getSimpleName();
//
//    public ArrayList<Tweet> mTweets = new ArrayList<>();
//    private TweetsListAdapter mAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tweets);
//        ButterKnife.bind(this);
//        String traffic = "@Ma3Route";
//        getTweets(traffic);
//
//    }
//
//    private void getTweets(String topic) {
//        final TwitterService twitterService = new TwitterService();
//        twitterService.findTweets(topic, new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                mTweets = twitterService.processResults(response);
//                trafficActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter = new TweetsListAdapter(getApplicationContext(),mTweets);
//                        mRecyclerView.setAdapter(mAdapter);
//                        RecyclerView.LayoutManager layoutManager  = new LinearLayoutManager(trafficActivity.this);
//                        mRecyclerView.setLayoutManager(layoutManager);
//                        mRecyclerView.setHasFixedSize(true);
//                    }
//                });
//
//            }
//        });
//    }
//}
