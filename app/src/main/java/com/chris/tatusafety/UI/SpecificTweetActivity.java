package com.chris.tatusafety.UI;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.tatusafety.Modules.Tweet;
import com.chris.tatusafety.R;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpecificTweetActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.createdAt) TextView mCreated;
    @Bind(R.id.tweeterTextView) TextView mUser;
    @Bind(R.id.extLinkTextView) TextView mLinkTextView;
    @Bind(R.id.tweetsHashTagTextView) TextView mHashTags;
    @Bind(R.id.tweetContentTextView) TextView mTextContent;
    private Tweet mTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_tweet);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mTweet = Parcels.unwrap(intent.getParcelableExtra("tweet"));
        mUser.setText("@" + mTweet.getUser());
        mHashTags.setText("#"+ android.text.TextUtils.join(" # ",mTweet.getHashTags()));
        mCreated.setText("Created at " + mTweet.getDate());
        mTextContent.setText(mTweet.getTweetText());
        mLinkTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mLinkTextView) {
            if (mTweet.getExternalLink().equals("No external links")){
                Toast.makeText(this,"No external link available",Toast.LENGTH_SHORT).show();

            }else {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTweet.getExternalLink()));
                startActivity(webIntent);
            }
        }
    }
}
