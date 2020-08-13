package com.yalianxun.mingshi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yalianxun.mingshi.utils.SharedPreferencesManager;

public class BaseActivity extends AppCompatActivity {
    public SharedPreferencesManager SP_MANAGER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarFullTransparent();
        SP_MANAGER = new SharedPreferencesManager(this,"YLX");
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

    public void changStatusIconColor(boolean setDark) {

        View decorView = getWindow().getDecorView();
        int vis = decorView.getSystemUiVisibility();
        if(setDark){
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else{
            vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decorView.setSystemUiVisibility(vis);
    }
}
