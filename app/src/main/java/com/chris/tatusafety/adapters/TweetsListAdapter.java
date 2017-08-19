package com.chris.tatusafety.adapters;

/**
 * Created by bubbles on 7/13/17.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.tatusafety.Modules.Tweet;
import com.chris.tatusafety.R;
import com.chris.tatusafety.UI.SpecificTweetActivity;
import com.chris.tatusafety.UI.TweetsActivity;


import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josephine Menge on 06/07/2017.
 */

public class TweetsListAdapter extends RecyclerView.Adapter<TweetsListAdapter.TweetViewHolder>{

    private ArrayList<Tweet> mTweets = new ArrayList<>();
    private Context mContext;
    private Tweet mTweet;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public TweetsListAdapter(Context context,ArrayList<Tweet> tweets){
        mContext = context;
        mTweets = tweets;
    }
    @Override
    public TweetsListAdapter.TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item,parent,false);
        TweetViewHolder viewHolder = new TweetViewHolder(view);
        Typeface man = Typeface.createFromAsset(viewHolder.itemView.getContext().getAssets(), "fonts/Lato-Regular.ttf");
        viewHolder.mTweetTextView.setTypeface(man);
        viewHolder.mUserTextView.setTypeface(man);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        holder.bindTweet(mTweets.get(position));
    }


    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public class TweetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.tweetTextView) TextView mTweetTextView;
        @Bind(R.id.tweetUserTextView) TextView mUserTextView;
        @Bind(R.id.tweetUserText) TextView mTweetUser;
        @Bind(R.id.dateTime)TextView mDates;


        private Context mContext;

        public TweetViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);

        }

        private void bindTweet(Tweet tweet) {
            mTweetUser.setText(tweet.getUser());
            mUserTextView.setText("@"+ tweet.getUser());
            mTweetTextView.setText(tweet.getTweetText());
            String givenDateString = tweet.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            try {
                Date mDate = sdf.parse(givenDateString);
                long timeInMilliseconds = mDate.getTime();
                long now = System.currentTimeMillis();
                final long diff = now - timeInMilliseconds;
                if (diff < MINUTE_MILLIS) {
                    mDates.setText("just now");
                } else if (diff < 2 * MINUTE_MILLIS) {
                    mDates.setText("a minute ago");
                } else if (diff < 50 * MINUTE_MILLIS) {
                    mDates.setText(diff / MINUTE_MILLIS + " minutes ago");
                } else if (diff < 90 * MINUTE_MILLIS) {
                    mDates.setText("an hour ago");
                } else if (diff < 24 * HOUR_MILLIS) {
                    mDates.setText(diff / HOUR_MILLIS + " hours ago");
                } else if (diff < 48 * HOUR_MILLIS) {
                    mDates.setText("yesterday");
                } else {
                    mDates.setText(diff / DAY_MILLIS + " days ago");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onClick(View v) {
//            if (v == mLink) {
//                if (mTweet.getExternalLink().equals("No external links")) {
//                    Toast.makeText(v.getContext(), "No external link available", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTweet.getExternalLink()));
//                    mContext.startActivity(webIntent);
//                }
//            }

                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, SpecificTweetActivity.class);
                Tweet clickedTweet = mTweets.get(itemPosition);
                intent.putExtra("tweet", Parcels.wrap(clickedTweet));
                mContext.startActivity(intent);
        }
    }

}
