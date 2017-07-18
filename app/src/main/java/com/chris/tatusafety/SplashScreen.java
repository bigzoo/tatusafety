package com.chris.tatusafety;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chris.tatusafety.UI.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final ImageView imageView = (ImageView) findViewById(R.id.busrot);
        final ImageView leftBus = (ImageView) findViewById(R.id.busleft);
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.animator.antirotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.animator.rotate);
        imageView.startAnimation(animation_2);
        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.startAnimation(animation_1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                leftBus.startAnimation(animation_1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                leftBus.startAnimation(animation_2);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent i = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        },SPLASH_TIME_OUT);
    }
}
