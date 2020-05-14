package com.yalianxun.mingshi;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.yalianxun.mingshi.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SettingPasswordActivity extends BaseActivity {

    private EditText et;
    private boolean hide = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_password);
        et = findViewById(R.id.set_pw_et);
    }


    public void goBack(View view) {
        finish();
    }

    public void complete(View view) {
        et.clearFocus();
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm !=null)
            imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
        if(et.getText().toString().length() >= 6){
            SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
            String oldPassword = sharedPreferences.getString("password","");
            String userId = sharedPreferences.getString("userID","");
            HttpUtils.updatePassword(
                    userId,
                    oldPassword,et.getText().toString(),
                    new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d("http", "onFailure: " + e.getMessage());
                            runOnUiThread(()-> Toast.makeText(SettingPasswordActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            if(response.body() != null){
                                String string = response.body().string();
                                if(string.contains("success")){
                                    runOnUiThread(()->{
                                        Toast.makeText(SettingPasswordActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                                        setResult(1001);
                                        finish();
                                    });
                                }
                            }
                        }
                    });

        }else if(et.getText().toString().length() == 0){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    public void hidePassword(View view) {
        hide = !hide;
        if(!hide){
            et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
