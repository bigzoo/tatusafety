//package com.chris.tatusafety.UI;
//
//import android.net.Uri;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.chris.tatusafety.Constants;
//import com.chris.tatusafety.MainActivity;
//import com.chris.tatusafety.R;
//import com.twitter.sdk.android.core.DefaultLogger;
//import com.twitter.sdk.android.core.Twitter;
//import com.twitter.sdk.android.core.TwitterAuthConfig;
//import com.twitter.sdk.android.core.TwitterConfig;
//import com.twitter.sdk.android.tweetcomposer.BuildConfig;
//import com.twitter.sdk.android.tweetcomposer.TweetComposer;
//
//import java.io.File;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
//
//public class Compose_Tweet extends AppCompatActivity implements View.OnClickListener {
//    Uri imageUri = FileProvider.getUriForFile(Compose_Tweet.this,
//            BuildConfig.APPLICATION_ID + ".file_provider",
//            new File("/path/to/image"));
//   @Bind(R.id.send)Button mSending;
//    @Bind(R.id.messageTwitter)EditText mComposer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        TwitterConfig config = new TwitterConfig.Builder(this)
////                .logger(new DefaultLogger(Log.DEBUG))
////                .twitterAuthConfig(new TwitterAuthConfig(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET))
////                .debug(true)
////                .build();
////        Twitter.initialize(config);
//        ButterKnife.bind(this);
//        setContentView(R.layout.activity_compose__tweet);
//        mSending.setOnClickListener(this);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        String token ="<Your access token>";
//        String secret = "<Your access token secret>";
//        AccessToken a = new AccessToken(token,secret);
//        Twitter twitter = new TwitterFactory().getInstance();
//        twitter.setOAuthConsumer("<Your consumer key>", "<Your consumer secret>");
//        twitter.setOAuthAccessToken(a);
//        try {
//            twitter.updateStatus(message.getText().toString());
//        } catch (TwitterException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//});
//
//    }
//}
