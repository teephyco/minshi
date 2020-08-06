package com.yalianxun.mingshi.personal;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.utils.HttpUtils;
import com.yalianxun.mingshi.utils.ScreenUtils;
import com.yalianxun.mingshi.utils.ToastUtils;

import java.util.Objects;


public class ModifyPasswordActivity extends BaseActivity {

    private EditText et;
    private EditText et_old;
    private EditText et_confirm;
    private boolean hide = true;
    private boolean hide_confirm = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.modify_pw);
        et = findViewById(R.id.new_pw);
        et_old = findViewById(R.id.old_pw);
        et_confirm = findViewById(R.id.input_pw_again);
        changStatusIconColor(true);
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
        if(et_old.getText().toString().equals("1234567")){
            if(et.getText().toString().equals("")){
                showAlert("新密码不能为空");
            }else if(et.getText().toString().length()<6){
                showAlert("新密码不能少于6位");
            }else if(et.getText().toString().equals(PASSWORD)){
                showAlert("新密码不能与旧密码相同");
            }else if(!et.getText().toString().equals(et_confirm.getText().toString())){
                showAlert("两次输入密码不一致");
            }else{
                @SuppressLint("InflateParams") View alertView = LayoutInflater.from(this).inflate(R.layout.alert_dialog,null,false);
                TextView btn_agree = alertView.findViewById(R.id.alert_agree);
                TextView btn_cancel = alertView.findViewById(R.id.alert_cancel);
                alertView.findViewById(R.id.alert_et).setVisibility(View.GONE);
                alertView.findViewById(R.id.alert_tv).setVisibility(View.GONE);
                alertView.findViewById(R.id.alert_tips).setVisibility(View.VISIBLE);
                final Dialog dialog = new Dialog(Objects.requireNonNull(this),R.style.dialog);
                dialog.setContentView(alertView);
                btn_agree.setOnClickListener(v2 -> {
                    modPassword();
                    dialog.dismiss();
                });
                btn_cancel.setOnClickListener(v1 -> dialog.dismiss());
                dialog.show();
                Objects.requireNonNull(dialog.getWindow()).setLayout((ScreenUtils.getScreenWidth(this)/5*4), LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }else{
            showAlert("旧密码错误");
        }

    }

    protected void showAlert(String message){
        ToastUtils.showTextToast(this,message);
    }

    public void hidePassword(View view) {
        hide = !hide;
        ImageView iv = findViewById(R.id.hide_pw_iv);
        if(!hide){
            et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            iv.setImageResource(R.drawable.ic_see_pw);
        }else {
            et.setTransformationMethod(PasswordTransformationMethod.getInstance());
            iv.setImageResource(R.drawable.ic_hide_pw);
        }
        et.setSelection(et.getText().length());
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

    public void hideConfirmPassword(View view) {
        hide_confirm = !hide_confirm;
        ImageView iv = findViewById(R.id.hide_confirm_pw_iv);
        if(!hide_confirm){
            et_confirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            iv.setImageResource(R.drawable.ic_see_pw);
        }else {
            et_confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
            iv.setImageResource(R.drawable.ic_hide_pw);
        }
        et_confirm.setSelection(et_confirm.getText().length());
    }
}
