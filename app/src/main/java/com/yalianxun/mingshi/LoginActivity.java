package com.yalianxun.mingshi;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yalianxun.mingshi.home.HomeActivity;

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
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
