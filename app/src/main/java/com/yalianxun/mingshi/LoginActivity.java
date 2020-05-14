package com.yalianxun.mingshi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yalianxun.mingshi.beans.UserInfo;
import com.yalianxun.mingshi.home.HomeActivity;
import com.yalianxun.mingshi.utils.HttpUtils;
import com.yalianxun.mingshi.utils.JsonUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xph on 2020/04/17.
 *
 */
public class LoginActivity extends BaseActivity {

    private EditText phoneET;
    private EditText passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneET = findViewById(R.id.login_telephone_et);
        passwordET = findViewById(R.id.login_password_et);
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(this,ForgetPasswordActivity.class));
    }

    public void goHome(View view) {
        if(!HttpUtils.checkPhoneNumber(phoneET.getText().toString())){
            if(phoneET.getText().toString().equals("")){
                Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"手机号码错误",Toast.LENGTH_SHORT).show();
            }
        }else {
            if(passwordET.getText().toString().equals("")){
                Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            findViewById(R.id.background).setVisibility(View.VISIBLE);
            findViewById(R.id.pay_progress_layout).setVisibility(View.VISIBLE);
            OkHttpClient okHttpClient = new OkHttpClient();
            String url = HttpUtils.URL + "finger/auth/login";
            RequestBody requestBody = new FormBody.Builder()
                    .add("mobile", phoneET.getText().toString())
                    .add("password", passwordET.getText().toString())
                    .add("mobileId", "1")
                    .add("mobileModel", Build.MODEL)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("http", "onFailure: " + e.getMessage());
                    String str = e.getMessage();
                    runOnUiThread(() -> {
                        assert str != null;
                        showBackground(str);
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    if(response.body() != null){
                        String string = response.body().string();
                        runOnUiThread(() -> login(string));
                    }
                }
            });
        }

    }

    private void login(@NotNull String response) {
        showBackground(response);
        if(response.contains("用户密码不正确")){
            Toast.makeText(this,"用户密码不正确",Toast.LENGTH_SHORT).show();
        }else if(response.contains("不存在")){
            Toast.makeText(this,"用户不存在",Toast.LENGTH_SHORT).show();
        }else if(response.contains("success")){
            List<UserInfo> list = JsonUtil.getJsonUtil().getUserInfoList(response);
//            跳转到主界面
            SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            if (list.size()>0){
                UserInfo userInfo = list.get(0);
                editor.putString("name",userInfo.getName());
                editor.putString("userID",userInfo.getUserId());
            }
            editor.putString("mobile",phoneET.getText().toString());
            editor.putString("password",passwordET.getText().toString());
            editor.putString("loginResponse",response);
            editor.putBoolean("login",true);
            editor.apply();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showBackground(String reason){
        if(reason.contains("timeout"))
            Toast.makeText(this,"请求超时",Toast.LENGTH_SHORT).show();
        findViewById(R.id.background).setVisibility(View.GONE);
        findViewById(R.id.pay_progress_layout).setVisibility(View.GONE);
    }
}
