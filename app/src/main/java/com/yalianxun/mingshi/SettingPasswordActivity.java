package com.yalianxun.mingshi;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.yalianxun.mingshi.utils.HttpUtils;
import com.yalianxun.mingshi.utils.ToastUtils;


public class SettingPasswordActivity extends BaseActivity {

    private EditText et;
    private EditText input_again_et;
    private boolean hide = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        et = findViewById(R.id.set_pw_et);
        input_again_et = findViewById(R.id.input_again);
    }


    public void goBack(View view) {
        finish();
    }

    public void complete(View view) {
        et.clearFocus();
        input_again_et.clearFocus();
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm !=null)
            imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
        if(et.getText().toString().length() == 0){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else if(et.getText().toString().length() < 6){
            Toast.makeText(this,"密码需大于6位",Toast.LENGTH_SHORT).show();
        }else if(!et.getText().toString().equals(input_again_et.getText().toString())){
            Toast.makeText(this,"前后密码不一致",Toast.LENGTH_SHORT).show();
        }else {
            if(et.getText().toString().length() >= 6){
                String oldPassword = SP_MANAGER.getValue("password","");
                String userId = SP_MANAGER.getValue("userID","");
                HttpUtils.updatePassword(
                        userId,
                        oldPassword, et.getText().toString(),userId,
                        new HttpUtils.OnNetResponseListener() {
                            @Override
                            public void onNetResponseError(String msg) {
                                runOnUiThread(()-> Toast.makeText(SettingPasswordActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show());
                            }

                            @Override
                            public void onNetResponseSuccess(String string) {
                                runOnUiThread(()-> {
                                    setSuccess(string);
                                });
                            }
                        });
            }
        }

    }

    private void setSuccess(String value){
        if(value.equals("修改失败") || value.contains("登录已过期")){
            ToastUtils.showTextToast(SettingPasswordActivity.this,"请联系管理处修改密码");
        } else {
            ToastUtils.showTextToast(SettingPasswordActivity.this, "修改密码成功");
            setResult(1001);
            finish();
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
