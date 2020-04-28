package com.yalianxun.mingshi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends BaseActivity implements  View.OnTouchListener, GestureDetector.OnGestureListener{

    private ImageView iv;
    private TextView tv;
    private int index = 0;
    private GestureDetector mGestureDetector;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.welcome_iv);
        iv.setImageResource(R.drawable.welcome1);
        tv = findViewById(R.id.welcome_tv);
        //实现左右滑动跳转
        LinearLayout mLinearLayout = findViewById(R.id.drag_l);
        mLinearLayout.setOnTouchListener(this);


        mLinearLayout.setLongClickable(true);

        mGestureDetector = new GestureDetector(this,
                this);
    }
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

        final int FLING_MIN_DISTANCE = 200;

        final int FLING_MIN_VELOCITY = 200;

        if (Math.abs((int) (e1.getX() - e2.getX())) >
                FLING_MIN_DISTANCE && Math.abs(velocityX) >
                FLING_MIN_VELOCITY) {//左滑右滑皆可

            //Toast.makeText(this, "drag it " + (int) (e1.getX() - e2.getX()) + " velocityX : " + velocityX, Toast.LENGTH_SHORT).show();
            int dragDistance = (int) (e1.getX() - e2.getX());
            if(dragDistance > 0){
                //Toast.makeText(this, "drag it left" ,Toast.LENGTH_SHORT).show();
                if(index == 0){
                    iv.setImageResource(R.drawable.welcome2);
                    index = 1;
                }else if(index == 1){
                    iv.setImageResource(R.drawable.welcome3);
                    tv.setVisibility(View.VISIBLE);
                    index = 2;
                }


            }else{
                //Toast.makeText(this, "drag it right" ,Toast.LENGTH_SHORT).show();
                if(index == 2){
                    tv.setVisibility(View.GONE);
                    iv.setImageResource(R.drawable.welcome2);
                    index = 1;
                }else if(index == 1){
                    iv.setImageResource(R.drawable.welcome1);
                    index = 0;
                }
            }
        }

        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public void login(View view) {
        Log.i("xph"," Login");
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
