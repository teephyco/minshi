package com.yalianxun.mingshi.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.EventAdapter;
import com.yalianxun.mingshi.beans.Event;
import com.yalianxun.mingshi.utils.DateUtil;
import com.yalianxun.mingshi.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EventListActivity extends BaseActivity {
    List<Event> unsettledData = new ArrayList<>();
    List<Event> settledData = new ArrayList<>();
    EventAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.event_list);
        listView = findViewById(R.id.event_list_lv);
//        String brand = android.os.Build.BRAND;
//        Log.d("xph","brand " + brand + " type " + Build.MODEL);
        initData();
        showListContent(findViewById(R.id.event_type2), findViewById(R.id.event_type1),unsettledData);
    }
    private void initData() {
        Event event = new Event(0,"2019-06-07 16:48:25",0,"公区报修",
                "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                "","");
        unsettledData.add(event);
        event = new Event(5,"2019-06-07 16:48:25",1,"公区报修",
                "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                "2019-06-12 16:48:25","");
        unsettledData.add(event);
        event = new Event(2,"2019-06-07 16:48:25",1,"家庭维修",
                "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                "2019-06-12 16:48:25","");
        unsettledData.add(event);
        for(int i = 0;i < 7; i++){
            event = new Event(6,"2019-06-07 16:48:25",1,"家庭维修",
                    "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                    "2019-06-12 16:48:25","");
            unsettledData.add(event);
        }
        for(int i = 0;i < 9; i++){
            event = new Event(i,"2019-06-07 16:48:25",2,"家庭维修",
                    "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                    "2019-06-12 16:48:25","2019-07-12 15:48:25");
            settledData.add(event);
        }
        //getHttps();
        HttpUtils.getEventReportList("10010345712344", "1001", "13923745307", new HttpUtils.OnNetResponseListener() {
            @Override
            public void onNetResponseError(String msg) {

            }

            @Override
            public void onNetResponseSuccess(String string) {

            }
        });


    }
    public void goBack(View view) {
        finish();
    }

    public void switchList(View view) {
        if(view.findViewById(R.id.event_type1) != null){
            showListContent(findViewById(R.id.event_type2), findViewById(R.id.event_type1),unsettledData);
        }else {
            showListContent(findViewById(R.id.event_type1), findViewById(R.id.event_type2),settledData);
        }
    }

    private void showListContent(View v1,View v2, List<Event> list){
        v1.setVisibility(View.INVISIBLE);
        v2.setVisibility(View.VISIBLE);
        adapter = new EventAdapter(list,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(this,EventDetailActivity.class);
            Event event = list.get(position);
            intent.putExtra("event",event);
            startActivity(intent);
        });
    }
}
