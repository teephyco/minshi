package com.yalianxun.mingshi;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yalianxun.mingshi.home.HomeActivity;
import com.yalianxun.mingshi.home.HomePageActivity;
import com.yalianxun.mingshi.others.YLXTextWatcher;
import com.yalianxun.mingshi.utils.HttpUtils;
import com.yalianxun.mingshi.utils.SharedPreferencesManager;
import com.yalianxun.mingshi.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

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
        initView();

    }

    private void initView(){
        phoneET = findViewById(R.id.login_telephone_et);
        passwordET = findViewById(R.id.login_password_et);
        ImageView iv = findViewById(R.id.head_iv);
        phoneET.addTextChangedListener(new YLXTextWatcher(iv,R.drawable.ic_head_default,R.drawable.ic_head_highlight));
        iv = findViewById(R.id.pw_iv);
        passwordET.addTextChangedListener(new YLXTextWatcher(iv,R.drawable.ic_lock_default,R.drawable.ic_lock_highlight));
    }
    public void forgetPassword(View view) {
        startActivity(new Intent(this,ForgetPasswordActivity.class));
    }

    public void goHome(View view) {
        if(!HttpUtils.checkPhoneNumber(phoneET.getText().toString())){
            if(phoneET.getText().toString().equals("")){
                ToastUtils.showTextToast(this,"请输入手机号");
            }else {
                ToastUtils.showTextToast(this,"手机号码错误");
            }
        }else {
            if(passwordET.getText().toString().equals("")){
                ToastUtils.showTextToast(this,"密码不能为空");
                return;
            }
            findViewById(R.id.background).setVisibility(View.VISIBLE);
            findViewById(R.id.pay_progress_layout).setVisibility(View.VISIBLE);
            HttpUtils.gotoLogin(phoneET.getText().toString(), passwordET.getText().toString(), "1", Build.MODEL,
                    new HttpUtils.OnNetResponseListener() {
                        @Override
                        public void onNetResponseError(String msg) {
                            runOnUiThread(() -> showBackground(msg));
                        }

                        @Override
                        public void onNetResponseSuccess(String string) {
                            runOnUiThread(() -> login(string));
                        }
                    });
        }

    }

    private void login(@NotNull String response) {
        showBackground(response);
        if(response.contains("用户密码不正确")){
            ToastUtils.showTextToast(this,"用户密码不正确");
        }else if(response.contains("不存在")){
            ToastUtils.showTextToast(this,"用户不存在");
        }else if(response.contains("success")){
            SharedPreferencesManager model = new SharedPreferencesManager(this,"YLX");
            model.saveLoginData(response,phoneET.getText().toString(),passwordET.getText().toString());
//            跳转到主界面
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showBackground(String reason){
        if(reason.contains("timeout"))
            ToastUtils.showTextToast(this,"请求超时");
        else if(reason.length()<7)
            ToastUtils.showTextToast(this,reason);
        findViewById(R.id.background).setVisibility(View.GONE);
        findViewById(R.id.pay_progress_layout).setVisibility(View.GONE);
    }
}
