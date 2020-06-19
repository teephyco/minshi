package com.yalianxun.mingshi;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yalianxun.mingshi.home.HomeActivity;


public class MainActivity extends BaseActivity {


    private Handler mHandler = new Handler(msg -> {
        switch (msg.what){
            case 1:
                startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
                finish();
                break;
            case 2:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
                break;
        }
        return false;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check 是否是第一次启动
        SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
        boolean launcher = sharedPreferences.getBoolean("firstLauncher",false);
        if (launcher){
            //判断是否已经登陆
            boolean login = sharedPreferences.getBoolean("login",false);
            if(!login){
                mHandler.sendEmptyMessageDelayed(1,500);
            }else {
                //进入主界面
                mHandler.sendEmptyMessageDelayed(3,500);
            }
        }else {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("firstLauncher",true);
            editor.apply();
            //动态申请权限
            final String[] permissionsGroup=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.CAMERA};
            requestDangerousPermissions(permissionsGroup,500);
        }
    }
    /**
     * 请求权限
     */
    public void requestDangerousPermissions(String[] permissions, int requestCode) {
        if (checkDangerousPermissions(permissions)){
            handlePermissionResult(requestCode, true);
            return;
        }
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    /**
     * 检查是否已被授权危险权限
     * @param permissions
     * @return
     */
    public boolean checkDangerousPermissions(String[] permissions) {

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean granted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                granted = false;
                break;
            }
        }
        boolean finish = handlePermissionResult(requestCode, granted);
        if (!finish){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            //跳转到欢饮界面
            mHandler.sendEmptyMessage(1);
        }
    }

    /**
     * 处理请求危险权限的结果
     * @return
     */
    public boolean handlePermissionResult(int requestCode, boolean granted) {
        return false;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    protected void setStatusBarFullTransparent(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

}
