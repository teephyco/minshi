package com.yalianxun.mingshi.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.ProtocolActivity;
import com.yalianxun.mingshi.R;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.about_us);
        changStatusIconColor(true);
    }

    public void goBack(View view) {
        finish();
    }

    public void showProtocol(View view) {
        startActivity(new Intent(this, ProtocolActivity.class));
    }
}
