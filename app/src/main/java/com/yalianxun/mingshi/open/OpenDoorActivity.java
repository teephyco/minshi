package com.yalianxun.mingshi.open;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.DoorAdapter;
import com.yalianxun.mingshi.beans.Door;

import java.util.ArrayList;
import java.util.List;

public class OpenDoorActivity extends BaseActivity implements  View.OnTouchListener, GestureDetector.OnGestureListener{
    private GestureDetector mGestureDetector;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        List<Door> listViewData = new ArrayList<>();
        Door door = new Door("1231232412","东区",0);
        listViewData.add(door);
        door = new Door("123187678412","西区",1);
        listViewData.add(door);
        door = new Door("653233453313","写字楼A区",1);
        listViewData.add(door);
        door = new Door("653233773313","地下车库",1);
        listViewData.add(door);

        DoorAdapter adapter = new DoorAdapter(listViewData,this);
        GridView gv = findViewById(R.id.door_gv);
        gv.setAdapter(adapter);
        gv.setOnTouchListener(this);
        gv.setLongClickable(true);
        mGestureDetector = new GestureDetector(this,
                this);
    }



    public void faceManager(View view) {
        startActivity(new Intent(this,FaceManagerActivity.class));
    }

    public void tripRecord(View view) {
        startActivity(new Intent(this,TripRecordActivity.class));
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

            // Toast.makeText(this, "drag it " + (int) (e1.getX() - e2.getX()) + " velocityX : " + velocityX, Toast.LENGTH_SHORT).show();
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
