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
import android.widget.ListView;

import com.yalianxun.mingshi.adapter.DoorAdapter;
import com.yalianxun.mingshi.beans.Door;
import com.yalianxun.mingshi.personal.PersonalActivity;

import java.util.ArrayList;
import java.util.List;

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
        changStatusIconColor(true);
        List<Door> listViewData = new ArrayList<>();
        Door door = new Door("1231232412","东区",0);
        listViewData.add(door);
        door = new Door("123187678412","西区",1);
        listViewData.add(door);
        door = new Door("653233453313","写字楼A区",1);
        listViewData.add(door);
        door = new Door("653233773313","地下车库",1);
        listViewData.add(door);
        DoorAdapter adapter = new DoorAdapter(listViewData,this);
        ListView listView = findViewById(R.id.shortcut_open_lv);
        listView.setAdapter(adapter);
    }

    public void goToPersonalCenter(View view) {
        startActivity(new Intent(this, PersonalActivity.class));
    }

    public void hideOpenDoor(View view) {
        findViewById(R.id.open_door_view).setVisibility(View.GONE);
    }

    public void showOpenDoor(View view) {
        findViewById(R.id.open_door_view).setVisibility(View.VISIBLE);
    }

    public void doNothing(View view) {
    }
}
