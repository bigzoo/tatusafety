package com.chris.tatusafety.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.tatusafety.PrefManager;
import com.chris.tatusafety.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Welcome_Activity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.btn_next)Button mNext;
    @Bind(R.id.btn_skip) Button mSkip;
    @Bind(R.id.view_pager) ViewPager mViewPager;
    @Bind(R.id.layoutDots)
    TextView[] mDots;

    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private TextView[] dots;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        // checking for first time launch
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        //Making notification bar transparent
        if (Build.VERSION.SDK_INT >=21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);
        mNext.setOnClickListener(this);
        mSkip.setOnClickListener(this);
        layouts = new int[]{
                R.layout.welcome_slider_1,
                R.layout.welcome_slider_2,
                R.layout.welcome_slider_3};
        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();
        myViewPagerAdapter = new MyViewPagerAdapter();
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mSkip) {
            launchHomeScreen();
        } else if (v == mNext) {
            // checking for last page
            // if last page home screen will be launched
            int current = getItem(+1);
            if (current < layouts.length) {
                // move to next screen
                mViewPager.setCurrentItem(current);
            } else {
                launchHomeScreen();
            }
        }
    }

    private void launchHomeScreen() {
        prefManager.setIsFirstTimeLaunch(false);
        startActivity(new Intent(Welcome_Activity.this,LoginActivity.class));
        finish();
    }

    private void addBottomDots(int currentPage) {
        mDots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInActive = getResources().getIntArray(R.array.array_dot_inactive);
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInActive[currentPage]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length>0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }
    private  int getItem(int i ) {
        return mViewPager.getCurrentItem() + i;
    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if(position == layouts.length -1) {
                //last page make button text NEXT to GOT IT
                mNext.setText(getString(R.string.start));
                mSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                mNext.setText(getString(R.string.next));
                mSkip.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter{
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {

        }
        @Override
        public Object instantiateItem(ViewGroup container, int position ) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position],container,false);
            container.addView(view);
            return view;
        }
        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
//http://go.redirectro.com/v1/hostedsearch?pid=252428&subid=9655019&keyword=chapo