package com.yalianxun.mingshi.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;

public class ModifyPasswordActivity extends BaseActivity {

    private EditText et;
    private EditText et_old;
    private boolean hide = true;
    private String PASSWORD = "QW1234CC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.modify_pw);
        et = findViewById(R.id.new_pw);
        et_old = findViewById(R.id.old_pw);
    }

    public void goBack(View view) {
        finish();
    }

    public void sure(View view) {
        if(et_old.getText().toString().equals(PASSWORD)){
            if(et.getText().toString().equals("")){
                showAlert("新密码不能为空");
            }else if(et.getText().toString().length()<6){
                showAlert("新密码不能少于6位");
            }else if(et.getText().toString().equals(PASSWORD)){
                showAlert("新密码不能与旧密码相同");
            }else{
                new AlertDialog.Builder(this)
                        .setMessage("确定修改密码吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        }else{
            showAlert("旧密码错误");
        }

    }

    protected void showAlert(String message){
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
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
