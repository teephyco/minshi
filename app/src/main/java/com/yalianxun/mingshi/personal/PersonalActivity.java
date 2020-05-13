package com.yalianxun.mingshi.personal;

import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.LoginActivity;
import com.yalianxun.mingshi.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalActivity extends BaseActivity implements  View.OnTouchListener, GestureDetector.OnGestureListener{

    private CircleImageView headIv;
    private GestureDetector mGestureDetector;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        headIv = findViewById(R.id.head);
        SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
        String path = sharedPreferences.getString("profilePicture","");
        if(!path.equals("")){
            Glide.with(getContext())
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_pic)
                    .into(headIv);
        }
        FrameLayout fl = findViewById(R.id.drag_fl);
        fl.setOnTouchListener(this);


        fl.setLongClickable(true);

        mGestureDetector = new GestureDetector(this,
                this);
    }


    public void exit(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("login",false);
        editor.apply();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void modifyPassword(View view) {
        startActivityForResult(new Intent(this,ModifyPasswordActivity.class),1000);
    }

    public void aboutUs(View view) {
        startActivity(new Intent(this,AboutUsActivity.class));
    }

    public void showMyProfile(View view) {
        startActivityForResult(new Intent(this,ProfileActivity.class),1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == 1001 && data != null){
            String path = data.getStringExtra("path");
            if(path != null)
            Glide.with(getContext())
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(headIv);
        }
    }

    public Context getContext(){
        return this;
    }

    //add for

    @Override
    public boolean
    onDown(MotionEvent e) {

        return false;
    }

    //短按触摸屏

    @Override
    public void
    onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {

    }
    @Override
    public boolean
    onFling(MotionEvent e1, MotionEvent e2, float velocityX, float
            velocityY) {

        final int FLING_MIN_DISTANCE = 100;

        final int FLING_MIN_VELOCITY = 200;

        if (Math.abs((int) (e1.getX() - e2.getX())) >
                FLING_MIN_DISTANCE && Math.abs(velocityX) >
                FLING_MIN_VELOCITY && Math.abs((int) (e1.getY() - e2.getY())) <
                70) {//左滑右滑皆可
                finish();
        }

        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
