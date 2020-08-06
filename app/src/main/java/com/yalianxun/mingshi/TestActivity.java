package com.yalianxun.mingshi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yalianxun.mingshi.personal.PersonalActivity;

public class TestActivity extends BaseActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("xph"," start");
        setContentView(R.layout.activity_test);
        Log.d("xph"," start 1111111");
        WebView web = findViewById(R.id.web);
        Log.d("xph"," 1111111111");
        WebSettings ws= web.getSettings();
        Log.d("xph"," 222222222");
        ws.setJavaScriptEnabled(true);
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        ws.setDefaultTextEncodingName("utf-8");
        ws.setLoadsImagesAutomatically(true);
        ws.setSupportZoom(false);
        ws.setBuiltInZoomControls(false);
        ws.setDomStorageEnabled(true);
        ws.setAppCacheEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);

            }
        });
        Log.d("xph"," end ");
        web.loadUrl("file:///android_asset/dist/index.html");
        Log.d("xph"," finish ");
        changStatusIconCollor(true);
    }

    public void goToPersonalCenter(View view) {
        startActivity(new Intent(this, PersonalActivity.class));
    }

    public void changStatusIconCollor(boolean setDark) {

        View decorView = getWindow().getDecorView();
        int vis = decorView.getSystemUiVisibility();
        if(setDark){
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else{
            vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decorView.setSystemUiVisibility(vis);
    }
}
