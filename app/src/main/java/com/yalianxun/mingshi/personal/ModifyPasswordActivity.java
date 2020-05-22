package com.yalianxun.mingshi.personal;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.utils.HttpUtils;


public class ModifyPasswordActivity extends BaseActivity {

    private EditText et;
    private EditText et_old;
    private boolean hide = true;
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
        et.clearFocus();
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm !=null)
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
        SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
        String PASSWORD = sharedPreferences.getString("password","");
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
                        .setPositiveButton("确定", (arg0, arg1) -> modPassword())
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

    private void savePassword(){
        Toast.makeText(ModifyPasswordActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("password",et.getText().toString());
        editor.apply();
        finish();
    }

    private void modPassword(){
        Log.d("http"," updatePassword");
        HttpUtils.updatePassword(
                getSharedPreferences("YLX", Context.MODE_PRIVATE).getString("userID", ""),
                et_old.getText().toString(), et.getText().toString(),
                new HttpUtils.OnNetResponseListener() {
                    @Override
                    public void onNetResponseError(String msg) {
                        runOnUiThread(()-> Toast.makeText(ModifyPasswordActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onNetResponseSuccess(String string) {
                        runOnUiThread(()->savePassword());
                    }
                }
        );
    }
}
