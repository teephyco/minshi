package com.yalianxun.mingshi;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        if(et.getText().toString().length() > 6){
            Toast.makeText(this,"设置密码成功",Toast.LENGTH_SHORT).show();
            setResult(1001);
            finish();
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
