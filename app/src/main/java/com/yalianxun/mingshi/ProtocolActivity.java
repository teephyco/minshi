package com.yalianxun.mingshi;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class ProtocolActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        TextView textView = findViewById(R.id.av_title);
        textView.setText("用户协议与隐私政策");
        WebView webView = findViewById(R.id.web);
        webView.loadUrl("file:///android_asset/privacy_policy.html");
        changStatusIconColor(true);
    }

    public void goBack(View view) {
        finish();
    }
}
