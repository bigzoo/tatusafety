package com.chris.tatusafety.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.chris.tatusafety.MainActivity;
import com.chris.tatusafety.R;
import com.chris.tatusafety.maps.FindMeActivity;
import com.chris.tatusafety.maps.MapsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.find) Button mFindButton;
    @Bind(R.id.stages) Button mStagesButton;
    @Bind(R.id.twitterUpdates) Button mUpdates;
    @Bind(R.id.main) Button mMainActivity;
    @Bind(R.id.report) Button mReportButton;
    @Bind(R.id.mainView) RelativeLayout mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        mFindButton.setOnClickListener(this);
        mStagesButton.setOnClickListener(this);
        mMainActivity.setOnClickListener(this);
        mUpdates.setOnClickListener(this);
        mReportButton.setOnClickListener(this);
        Toast.makeText(HelpActivity.this,"Here guide to using this application",Toast.LENGTH_SHORT).show();
        Typeface man = Typeface.createFromAsset(getAssets(),"fonts/am.ttf");
        mFindButton.setTypeface(man);
        mUpdates.setTypeface(man);
        mStagesButton.setTypeface(man);
        mMainActivity.setTypeface(man);
        mReportButton.setTypeface(man);

    }

    @Override
    public void onClick(View v) {
        if (v == mFindButton) {
            Intent intent = new Intent(HelpActivity.this, MapsActivity.class);
            startActivity(intent);
            Toast.makeText(HelpActivity.this,"Travelling ...",Toast.LENGTH_SHORT).show();
        }
        if (v == mStagesButton) {
            Intent intent = new Intent(HelpActivity.this, StagesActivity.class);
            startActivity(intent);
            Toast.makeText(HelpActivity.this,"Travelling ...",Toast.LENGTH_SHORT).show();
        }
        if (v == mMainActivity) {
            Intent intent = new Intent(HelpActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(HelpActivity.this,"Travelling ...",Toast.LENGTH_SHORT).show();
        }
        if (v == mReportButton) {
            Intent intent = new Intent(HelpActivity.this, NewReportActivity.class);
            startActivity(intent);
            Toast.makeText(HelpActivity.this,"Travelling ...",Toast.LENGTH_SHORT).show();
        }
        if (v == mUpdates) {
            Intent intent = new Intent(HelpActivity.this, TweetsActivity.class);
            startActivity(intent);
            Toast.makeText(HelpActivity.this,"Travelling ...",Toast.LENGTH_SHORT).show();


        }


    }
}
