package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.EventAdapter;
import com.yalianxun.mingshi.beans.Event;
import com.yalianxun.mingshi.utils.DateUtil;

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
        initData();
        adapter = new EventAdapter(unsettledData,this);
        listView = findViewById(R.id.event_list_lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,EventDetailActivity.class);
            Event event = unsettledData.get(position);
            intent.putExtra("event",event);
            startActivity(intent);
        });
    }
    private void initData() {
//        for (int i=0;i<9;i++){
//            Event notify = new Event(i+1,"",0,"","","","");
//            listData.add(notify);
//        }
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
        getHttps();


    }
    public void goBack(View view) {
        finish();
    }

    public void switchList(View view) {
        if(view.findViewById(R.id.event_type1) != null){
            findViewById(R.id.event_type2).setVisibility(View.INVISIBLE);
            findViewById(R.id.event_type1).setVisibility(View.VISIBLE);
            adapter = new EventAdapter(unsettledData,this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, v, position, id) -> {
                Intent intent = new Intent(this,EventDetailActivity.class);
                Event event = unsettledData.get(position);
                intent.putExtra("event",event);
                startActivity(intent);
            });
        }else {
            findViewById(R.id.event_type2).setVisibility(View.VISIBLE);
            findViewById(R.id.event_type1).setVisibility(View.INVISIBLE);
            adapter = new EventAdapter(settledData,this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, v, position, id) -> {
                Intent intent = new Intent(this,EventDetailActivity.class);
                Event event = settledData.get(position);
                intent.putExtra("event",event);
                startActivity(intent);
            });
        }
    }

    private void getHttps(){
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder()
                .url("http://192.168.1.108:8088/api/finger/event/getEventReportList?projectId=10010345712344&houseNum=1001&phone=13923745307")
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    if(response.body() != null){
                        //Log.d("okhttp","xph??"+response.body().string());
                        try {
                            String str = response.body().string();
                            JSONObject jsonObjectALL = new JSONObject(str);
                            JSONArray jsonArray = jsonObjectALL.getJSONArray("dataList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                // JSON数组里面的具体-JSON对象
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String content = jsonObject.optString("content", "");
                                long reportTime = jsonObject.optLong("reportTime", 0);
                                String picUrl = jsonObject.optString("picUrl", "");

                                // 日志打印结果：
                                String timestamp = DateUtil.getTime(reportTime,1);
                                Log.d("okhttp", "解析的结果：content" + content + " reportTime: " + timestamp + " picUrl:" + picUrl);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }


                } catch (IOException e) {
                    Log.d("okhttp","Fail reason : "+ e.getMessage());

                    e.printStackTrace();

                }
            }
        }).start();
    }
}
