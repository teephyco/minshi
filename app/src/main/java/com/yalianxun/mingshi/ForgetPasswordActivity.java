package com.yalianxun.mingshi;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mob.MobSDK;
import com.yalianxun.mingshi.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
/**
 * Created by xph on 2020/04/23.
 *
 */
public class ForgetPasswordActivity extends BaseActivity {

    private EditText et;
    private int time = 120;
    private TextView nextBtn;
    private TextView verifyTv;
    private EditText phoneEt;
    @SuppressLint("HandlerLeak")
    private  Handler myHandler = new Handler(){
        public void handleMessage(@NotNull Message msg) {
            String str;
            switch (msg.what) {
                case 1:
                    str= time +"秒后重新获取";
                    verifyTv.setText(str);
                    verifyTv.setTextColor(Color.parseColor("#999999"));
                    verifyTv.setClickable(false);
                    break;
                case 0:
                    myHandler.removeMessages(1);
                    str = "获取验证码";
                    verifyTv.setText(str);
                    verifyTv.setTextColor(Color.parseColor("#199ED8"));
                    verifyTv.setClickable(true);
                    time = 0;
                    break;
            }
        }
    };
    private EventHandler eh = new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    runOnUiThread(() -> {
                        phoneEt.setText("");
                        et.setText("");
                        myHandler.sendEmptyMessage(0);
                        startActivityForResult(new Intent(ForgetPasswordActivity.this,SettingPasswordActivity.class), 1000);
                    });

                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    runOnUiThread(() -> {
                        nextBtn.setBackground(getDrawable(R.drawable.bolder_blue_tv));
                        nextBtn.setTextColor(Color.parseColor("#f6f6f6"));
                        nextBtn.setClickable(true);
                    });

                }
            }else{
                Log.i("xph"," number is" + data.getClass() + "  " + data );
                ((Throwable)data).printStackTrace();
                runOnUiThread(() -> {
                    if(data.toString().contains("校验的验证码为空")){
                        Toast.makeText(ForgetPasswordActivity.this,"校验的验证码为空",Toast.LENGTH_SHORT).show();
                    }
                    if(data.toString().contains("需要校验的验证码错误")){
                        Toast.makeText(ForgetPasswordActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                    }
                    //验证失败3次，验证码失效，请重新请求
                    if(data.toString().contains("请重新请求")){
                        Toast.makeText(ForgetPasswordActivity.this,"验证失败3次，验证码失效，请重新请求",Toast.LENGTH_SHORT).show();
                        String str = "获取验证码";
                        verifyTv.setText(str);
                        verifyTv.setTextColor(Color.parseColor("#199ED8"));
                        verifyTv.setClickable(true);
                    }
                    if(data.toString().contains("当前手机号发送短信的数量超过限额")){
                        Toast.makeText(ForgetPasswordActivity.this,"今日验证次数已达上限",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        et = findViewById(R.id.verification_et);
        nextBtn = findViewById(R.id.go_next_btn);
        nextBtn.setBackground(getDrawable(R.drawable.bolder_gray));
        nextBtn.setTextColor(Color.parseColor("#333333"));
        nextBtn.setClickable(false);
        phoneEt = findViewById(R.id.forget_telephone_et);
        verifyTv = findViewById(R.id.get_verify_tv);
    }


    public void next(View view) {
        if(HttpUtils.checkPhoneNumber(phoneEt.getText().toString())){
            SMSSDK.submitVerificationCode("86", phoneEt.getText().toString(), et.getText().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == 1001)
        finish();
    }

    public void getVerify(View view) {

        MobSDK.submitPolicyGrantResult(true, null);

        SMSSDK.registerEventHandler(eh);
        if(HttpUtils.checkPhoneNumber(phoneEt.getText().toString())){
            //Log.i("xph"," number is" + phoneEt.getText().toString() );
            SMSSDK.getVerificationCode("86", phoneEt.getText().toString());
            if (time <= 0){
                time = 120;
            }
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    time--;
                    if (time <= 0) {
                        myHandler.sendEmptyMessage(0);
                    } else {
                        myHandler.sendEmptyMessage(1);
                        myHandler.postDelayed(this, 1000);
                    }
                }
            };
            new Thread(runnable).start();
        }else {
            if(phoneEt.getText().toString().equals("")){
                Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"手机号码错误",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void goBack(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time=0;
        Log.i("xph"," thread run end");
        SMSSDK.unregisterEventHandler(eh);
    }

}
