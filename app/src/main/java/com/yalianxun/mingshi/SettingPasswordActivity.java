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
                    oldPassword, et.getText().toString(),
                    new HttpUtils.OnNetResponseListener() {
                        @Override
                        public void onNetResponseError(String msg) {
                            runOnUiThread(()-> Toast.makeText(SettingPasswordActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show());
                        }

                        @Override
                        public void onNetResponseSuccess(String string) {
                            runOnUiThread(()-> {
                                Toast.makeText(SettingPasswordActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                                setResult(1001);
                                finish();
                            });
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
