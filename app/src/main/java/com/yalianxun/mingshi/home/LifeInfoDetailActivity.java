package com.yalianxun.mingshi.home;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;

public class LifeInfoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_info_detail);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.detail);
    }

    public void goBack(View view) {
        finish();
    }
}
