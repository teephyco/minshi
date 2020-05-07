package com.yalianxun.mingshi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yalianxun.mingshi.home.HomeActivity;
import com.yalianxun.mingshi.utils.ScreenUtils;

/**
 * Created by xph on 2020/04/17.
 *
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(this,ForgetPasswordActivity.class));
    }

    public void goHome(View view) {
        //跳转到主界面
        SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("login",true);
        editor.apply();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
