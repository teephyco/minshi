package com.yalianxun.mingshi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.yalianxun.mingshi.adapter.YLXPagerAdapter;
import com.yalianxun.mingshi.beans.ImageInfo;

import java.util.ArrayList;


public class WelcomeActivity extends BaseActivity{

    int oldPosition = 0;
    ArrayList<ImageInfo> imageList = new ArrayList<>();
    private int currentPosition;
    private LinearLayout ll_dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_dots = findViewById(R.id.ll_dots);
        ViewPager viewPager = findViewById(R.id.view_pager);
        findViewById(R.id.welcome_iv).setVisibility(View.INVISIBLE);
        initDate();
        initDots();

        YLXPagerAdapter adapter = new YLXPagerAdapter(this,imageList);
        adapter.setCallback(this::goToLogin);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                //设置小点监听
                changeDots();
            }

        });


    }

    private void initDate(){
        imageList.add(new ImageInfo("1","人脸识别 ·自动放行"));
        imageList.add(new ImageInfo("2","物业报修 ·聊天缴费"));
        imageList.add(new ImageInfo("3","资讯详情 ·实时更新"));
    }
    private void changeDots() {
        int dotPosition = currentPosition;
        for (int i = 0; i < ll_dots.getChildCount(); i++) {
            ImageView imageView = (ImageView) ll_dots.getChildAt(i);
            if (i == dotPosition) {
                // 从线性布局中取出对应小点,设置点亮
                imageView.setImageResource(R.drawable.ic_image_selected);
            } else {
                imageView.setImageResource(R.drawable.ic_image_unselected);
            }
        }
    }

    private void initDots() {
        for (int i = 0; i < imageList.size(); i++) {
            ImageView imageView = new ImageView(this);
            // 如果是第一页,设置为选中图片
            if (i == 0) {
                imageView.setImageResource(R.drawable.ic_image_selected);
            } else {
                imageView.setImageResource(R.drawable.ic_image_unselected);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 20);
            params.setMargins(5, 5, 5, 5);
            ll_dots.addView(imageView, params);
        }
    }
    public void goToLogin() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
