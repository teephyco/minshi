package com.yalianxun.mingshi.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        Log.d("http"," start");
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
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
//                                updatePassword(getSharedPreferences("YLX", Context.MODE_PRIVATE).getString("userID",""),et_old.getText().toString(),et.getText().toString());
                                updatePassword();
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

    private void modPassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("password",et.getText().toString());
        editor.apply();
        finish();
    }

    private void updatePassword(){
        HttpUtils.updatePassword(
                getSharedPreferences("YLX", Context.MODE_PRIVATE).getString("userID",""),
                et_old.getText().toString(),et.getText().toString(),
                new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("http", "onFailure: " + e.getMessage());
                runOnUiThread(()-> {Toast.makeText(ModifyPasswordActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show();});
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.body() != null){
                    String string = response.body().string();
                    if(string.contains("success")){
                        runOnUiThread(()->{
                            Toast.makeText(ModifyPasswordActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                            modPassword();
                        });
                    }
                }
            }
        });
    }
}
