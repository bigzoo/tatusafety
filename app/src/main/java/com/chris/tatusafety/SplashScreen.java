package com.chris.tatusafety;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.chris.tatusafety.UI.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.chris.tatusafety.R.id.imageView;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @Bind(R.id.textView7) TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        Typeface man = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
            mText.setTypeface(man);
        final ImageView imageView = (ImageView) findViewById(R.id.busrot);
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.antirotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);


        int x=imageView.getRight()-imageView.getLeft();
        int y=imageView.getBottom()-imageView.getTop();
        final TranslateAnimation translate = new TranslateAnimation(
                Animation.ABSOLUTE,0,
                Animation.ABSOLUTE,
                x,
                Animation.ABSOLUTE,y,
                Animation.ABSOLUTE,1000);//How far it goes
        translate.setDuration(500);//speed of the animation
        translate.setFillEnabled(true);
        translate.setFillAfter(true);


        imageView.startAnimation(animation_1);
        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(translate);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.startAnimation(animation_2);

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}



